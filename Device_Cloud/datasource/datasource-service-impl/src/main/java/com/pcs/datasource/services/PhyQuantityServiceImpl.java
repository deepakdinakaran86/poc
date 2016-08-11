/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.services;

import static com.pcs.datasource.enums.PhyQuantityFields.DATASTORE;
import static com.pcs.datasource.enums.PhyQuantityFields.DATA_TYPE;
import static com.pcs.datasource.enums.PhyQuantityFields.NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.PHYQUANTITY_CONFIG_CACHE;
import static com.pcs.datasource.enums.PhyQuantityFields.PHY_QUAN;
import static com.pcs.datasource.enums.PhyQuantityFields.UN_SUPPORTED;
import static com.pcs.datasource.enums.PhyQuantityFields.WARNING;
import static com.pcs.datasource.enums.ResponseMessage.CONVERSION_NOT_SPECIFIED;
import static com.pcs.datasource.enums.ResponseMessage.DUPLICATES_FOUND;
import static com.pcs.datasource.enums.ResponseMessage.ISSI_NOT_SPECIFIED;
import static com.pcs.datasource.enums.ResponseMessage.NAME_NOT_SPECIFIED;
import static com.pcs.datasource.enums.ResponseMessage.NO_SI_SPECIFIED;
import static com.pcs.datasource.enums.ResponseMessage.SI_UNIT_EXISTS;
import static com.pcs.datasource.enums.ResponseMessage.UNIT_EXISTS;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.enums.Status.ACTIVE;
import static com.pcs.devicecloud.enums.Status.FAILURE;
import static com.pcs.devicecloud.enums.Status.SUCCESS;
import static com.pcs.devicecloud.enums.Status.UNSUPPORTED;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.utils.UUIDs;
import com.hazelcast.core.HazelcastException;
import com.pcs.datasource.dto.PhyQuantityResponse;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.repository.PhyQuantityRepo;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.datasource.services.utils.CacheUtility;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;
import com.pcs.deviceframework.cache.Cache;

/**
 * This service interface is responsible for defining all the services related
 * to physical quantity in which stores device data and also manages cache for
 * the same. This class is responsible for retrieving columnfamily details of
 * particular physical quantity from the device data. If this mapping
 * information already present cache then retrieve from cache otherwise access
 * DB for the same and also support basic CRUD operation on physical quantity
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

@Service
public class PhyQuantityServiceImpl implements PhyQuantityService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(PhyQuantityServiceImpl.class);

	@Autowired
	@Qualifier("pqNeo")
	private PhyQuantityRepo phyQuantityRepo;

	@Autowired
	private UnitService unitService;

	@Autowired
	private StatusUtil statusUtil;

	@Autowired
	private CacheUtility cacheUtility;

	/**
	 * Method to get details of all active physical quantities optional filter
	 * by data type
	 * 
	 * @return {@link PhysicalQuantity}
	 */
	@Override
	public List<PhysicalQuantity> getAllPhyQunatity(String dataType) {
		// validateMandatoryField(DATA_TYPE.getDescription(), dataType);
		List<PhysicalQuantity> physicalQuantities = new ArrayList<PhysicalQuantity>();
		physicalQuantities = phyQuantityRepo.getAllPhysicalQuantity(dataType);
		if (CollectionUtils.isEmpty(physicalQuantities)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
		return physicalQuantities;
	}

	/**
	 * Method to persist physical quantities, units are not manadatory
	 * 
	 * @param phyQuantityDTO
	 * @return {@link StatusMessageDTO}
	 */
	@Override
	public PhyQuantityResponse createPhyQuantity(PhysicalQuantity phyQuantity) {
		PhyQuantityResponse phyQuantityResponse = new PhyQuantityResponse();
		if (CollectionUtils.isNotEmpty(validatePhyQuantity(phyQuantity,
		        phyQuantityResponse))) {
			return phyQuantityResponse;
		}

		phyQuantity.setId(UUIDs.timeBased());
		if (CollectionUtils.isEmpty(phyQuantity.getUnits())) {
			phyQuantityResponse.setStatus(WARNING.name());
			phyQuantityResponse.setMessage(NO_SI_SPECIFIED.getMessage());
			phyQuantity.setStatusKey(statusUtil.getStatus(UN_SUPPORTED.name()));
			phyQuantity.setStatus(UNSUPPORTED.getStatus());
		}
		try {
			phyQuantityRepo.savePhyQuantity(phyQuantity);
			for (Unit unit : phyQuantity.getUnits()) {
				unit.setpName(phyQuantity.getName());
				unitService.createUnit(unit);
			}
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		return phyQuantityResponse;
	}

	/**
	 * Service method to get mapping of physical quantity and columnfamily in DB
	 * 
	 * @param phyQuantityName
	 * @return {@link PhysicalQuantity}
	 * 
	 */

	@Override
	public PhysicalQuantity getPhyQuantityByName(String phyQuantityName) {
		PhysicalQuantity phyQuantity = null;
		phyQuantity = getPhyQuantityFromCache(phyQuantityName);
		if (phyQuantity == null) {
			try {
				phyQuantity = phyQuantityRepo.getPhyQuantityDetails(
				        phyQuantityName, ACTIVE);
				if (phyQuantity != null) {
					putPhyQuantityToCache(phyQuantityName, phyQuantity);
				}
			} catch (InvalidQueryException e) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
				        PHY_QUAN.getDescription());
			}
		}
		return phyQuantity;
	}

	/**
	 * Method to get ACTIVE physical quantity details
	 * 
	 * @param phyQuantityName
	 * @return {@link PhyQuantityDTO}
	 */

	@Override
	public PhysicalQuantity getActivePhyQuantity(String phyQuantityName) {
		validateMandatoryField(PHY_QUAN.getDescription(), phyQuantityName);
		PhysicalQuantity phyQuantity = phyQuantityRepo.getPhyQuantityDetails(
		        phyQuantityName, ACTIVE);
		if (phyQuantity == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
		return phyQuantity;
	}

	/**
	 * Method to get physical quantity details
	 * 
	 * @param phyQuantityName
	 * @return {@link PhyQuantityDTO}
	 */

	@Override
	public PhysicalQuantity getPhyQuantity(String phyQuantityName) {
		validateMandatoryField(PHY_QUAN.getDescription(), phyQuantityName);
		try {
			phyQuantityName = URLDecoder.decode(phyQuantityName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		PhysicalQuantity phyQuantity = phyQuantityRepo
		        .getPhyQuantityDetails(phyQuantityName);
		if (phyQuantity == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
		return phyQuantity;
	}

	/**
	 * Method to update physical quantities, units are not mandatory
	 * 
	 * @param phyQuantityDTO
	 * @return {@link StatusMessageDTO}
	 */

	@Override
	public StatusMessageDTO updatePhyQuantity(String existingPhyQtyName,
	        PhysicalQuantity newPhyQty) {
		validateMandatoryField(PHY_QUAN.getDescription(), existingPhyQtyName);
		validateMandatoryFields(newPhyQty, NAME);
		PhysicalQuantity existingPhyQty = phyQuantityRepo
		        .getPhyQuantityDetails(existingPhyQtyName);
		if (existingPhyQty == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.NON_EXISTING_DATA_CANNOT_BE_UPDATED,
			        "Physical quantity " + existingPhyQtyName + " don't exist.");
		}
		// checking new phyqty name already exists or not
		if (isExistsPhyQuantity(newPhyQty.getName())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
			        PHY_QUAN.getDescription() + " " + newPhyQty.getName() + " ");
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		List<Unit> units;
		try {
			units = unitService.getUnitDeatils(existingPhyQtyName);
			if (CollectionUtils.isNotEmpty(units)) {
				existingPhyQty.setUnits(units);
			}
		} catch (DeviceCloudException e1) {
			units = null;
		}
		try {
			phyQuantityRepo.updatePhyQuantity(existingPhyQty.getName(),
			        newPhyQty);
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * Method to delete (soft delete) physical quantities.
	 * 
	 * @param uuid
	 * @return {@link StatusMessageDTO}
	 */
	@Override
	public StatusMessageDTO deletePhyQuantity(String physicalQuantity) {
		validateMandatoryField(PHY_QUAN.getDescription(), physicalQuantity);
		PhysicalQuantity quantity = phyQuantityRepo
		        .getPhyQuantityDetails(physicalQuantity);
		if (quantity == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        PHY_QUAN.getDescription());
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		List<Unit> units;
		try {
			units = unitService.getUnitDeatils(physicalQuantity);
			quantity.setUnits(units);
		} catch (DeviceCloudException e1) {
			units = null;
		}
		try {
			phyQuantityRepo.deletePhyQuantity(quantity);
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	@Override
	public boolean isPhysicalQuantityExist(String physicalQuantity) {
		return isExistsPhyQuantity(physicalQuantity);
	}

	@Override
	public boolean isPhysicalQuantityValid(String physicalQuantity, String unit) {
		return isValidPhyQuantity(physicalQuantity, unit);
	}

	@Override
	public boolean isPhysicalQuantityValid(String physicalQuantity,
	        String unit, DataTypes dataType) {
		return phyQuantityRepo.isPhysicalQuantityValid(physicalQuantity, unit,
		        dataType);
	}

	/**
	 * Method to get physical quantity details from cache
	 * 
	 * @param phyQuantityName
	 * @return {@link PhysicalQuantity}
	 */

	private PhysicalQuantity getPhyQuantityFromCache(String phyQuantityName) {
		PhysicalQuantity physicalQuantity = null;
		try {
			Cache phyQuantityConfigCache = cacheUtility.getCacheProvider()
			        .getCache(PHYQUANTITY_CONFIG_CACHE.getFieldName());
			if (phyQuantityConfigCache != null) {
				physicalQuantity = phyQuantityConfigCache.get(phyQuantityName,
				        PhysicalQuantity.class);
			}
		} catch (IllegalStateException | HazelcastException e) {
			LOGGER.debug("<<-- Exception occured in cache-->>", e);
			return null;
		}
		return physicalQuantity;
	}

	/**
	 * method to put physical quantity to cache
	 * 
	 * @param phyQuantityName
	 * @param physicalQuantity
	 */

	private void putPhyQuantityToCache(String phyQuantityName,
	        PhysicalQuantity physicalQuantity) {
		try {
			Cache phyQuantityConfigCache = cacheUtility.getCacheProvider()
			        .getCache(PHYQUANTITY_CONFIG_CACHE.getFieldName());
			if (phyQuantityConfigCache != null) {
				phyQuantityConfigCache.putIfAbsent(phyQuantityName,
				        physicalQuantity);
			}
		} catch (IllegalStateException | HazelcastException e) {
			LOGGER.debug("<<-- Exception occured in cache-->>", e);
		}
	}

	/**
	 * Method to validate input physicalQuantityDTO
	 * 
	 * @param phyQuantityDTO
	 */
	private List<Unit> validatePhyQuantity(PhysicalQuantity phyQuantity,
	        PhyQuantityResponse phyQuantityResponse) {
		// added datatype validations
		validateMandatoryFields(phyQuantity, NAME, DATASTORE, DATA_TYPE);
		List<Unit> omittedUnits = null;

		if (isExistsPhyQuantity(phyQuantity.getName())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
			        PHY_QUAN.getDescription());
		}

		if (isNotEmpty(phyQuantity.getUnits())) {
			List<Unit> units = phyQuantity.getUnits();
			List<Unit> unitsOut = new ArrayList<Unit>();
			Unit siUnit = null;
			omittedUnits = new ArrayList<Unit>();

			for (Unit unit : units) {
				if (!unitsOut.contains(unit)) {
					String name = unit.getName();

					if ((isNotEmpty(name)) && (!(unit.getIsSi() == null))) {
						Boolean status = isExistsUnit(name,
						        phyQuantity.getName());
						if (unit.getIsSi()) {
							if (siUnit == null && !isExistsSIUnit(name)) {
								siUnit = unit;
								unitsOut.add(unit);
							} else {
								phyQuantityResponse.setStatus(FAILURE.name());
								omittedUnits.add(unit);
								phyQuantityResponse.setMessage(SI_UNIT_EXISTS
								        .getMessage());
								phyQuantityResponse.setFailedUnit(omittedUnits);
								break;
							}
						} else if (StringUtils.isNotEmpty(unit.getConversion())
						        && !status) {
							unitsOut.add(unit);
						} else {

							if (StringUtils.isEmpty(unit.getConversion())) {
								phyQuantityResponse.setStatus(FAILURE.name());
								phyQuantityResponse
								        .setMessage(CONVERSION_NOT_SPECIFIED
								                .getMessage());
								phyQuantityResponse.setFailedUnit(omittedUnits);
								omittedUnits.add(unit);
								break;
							}
							if (status) {
								phyQuantityResponse.setStatus(FAILURE.name());
								phyQuantityResponse.setMessage(UNIT_EXISTS
								        .getMessage());
								phyQuantityResponse.setFailedUnit(omittedUnits);
								omittedUnits.add(unit);
								break;
							}

						}
					} else {
						if (StringUtils.isEmpty(name)) {
							phyQuantityResponse.setStatus(FAILURE.name());
							phyQuantityResponse.setMessage(NAME_NOT_SPECIFIED
							        .getMessage());
							phyQuantityResponse.setFailedUnit(omittedUnits);
							omittedUnits.add(unit);
							break;
						}
						if (unit.getIsSi() == null) {
							phyQuantityResponse.setStatus(FAILURE.name());
							phyQuantityResponse.setMessage(ISSI_NOT_SPECIFIED
							        .getMessage());
							phyQuantityResponse.setFailedUnit(omittedUnits);
							omittedUnits.add(unit);
							break;
						}

					}
				} else {
					phyQuantityResponse.setStatus(FAILURE.name());
					phyQuantityResponse.setMessage(DUPLICATES_FOUND
					        .getMessage());
					phyQuantityResponse.setFailedUnit(omittedUnits);
					omittedUnits.add(unit);
					break;
				}
			}

			if (isNotEmpty(omittedUnits)) {
				return omittedUnits;
			}

			if (siUnit == null) {
				phyQuantityResponse.setStatus(WARNING.name());
				phyQuantityResponse.setMessage(NO_SI_SPECIFIED.getMessage());
				phyQuantity.setStatusKey(statusUtil.getStatus(UN_SUPPORTED
				        .name()));
				phyQuantity.setStatus(UNSUPPORTED.getStatus());
			} else {
				phyQuantityResponse.setStatus(SUCCESS.name());
				phyQuantity.setStatusKey(statusUtil.getStatus(ACTIVE.name()));
				phyQuantity.setStatus(ACTIVE.getStatus());
			}
		}
		return omittedUnits;

	}

	/**
	 * Method to check whether the physical quantity exists or not by name
	 * 
	 * @param phyQuantityName
	 * @return boolean
	 */
	private boolean isExistsPhyQuantity(String phyQuantityName) {
		return phyQuantityRepo.isPhyQuantityExist(phyQuantityName);
	}

	/**
	 * Method to check whether the physical quantity is valid or not
	 * 
	 * @param phyQuantityName
	 * @return boolean
	 */
	private boolean isValidPhyQuantity(String phyQuantityName, String unit) {
		return phyQuantityRepo.isPhysicalQuantityValid(phyQuantityName, unit);
	}

	/**
	 * Method to check whether the SI unit exists or not
	 * 
	 * @param phyQuantityName
	 * @return boolean
	 */

	private boolean isExistsSIUnit(String name) {
		return unitService.isSIUnit(name).getStatus().equals(Status.TRUE);
	}

	/**
	 * Method to check whether the unit exists or not
	 * 
	 * @param phyQuantityName
	 * @return boolean
	 */

	private boolean isExistsUnit(String name, String phyQuantity) {
		return unitService.getUnitDetails(name, phyQuantity) == null
		        ? false
		        : true;

	}

	@Override
	public boolean isPhysicalQuantityValidByDataType(String physicalQuantity,
	        String unit, DataTypes dataType) {
		return phyQuantityRepo.isPhysicalQuantityValidByDataType(
		        physicalQuantity, unit, dataType);
	}

}
