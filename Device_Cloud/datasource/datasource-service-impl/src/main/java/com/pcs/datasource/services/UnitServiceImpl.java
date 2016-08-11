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

import static com.pcs.datasource.enums.PhyQuantityFields.CONVERSION;
import static com.pcs.datasource.enums.PhyQuantityFields.IS_SI;
import static com.pcs.datasource.enums.PhyQuantityFields.NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.PHY_QUAN;
import static com.pcs.datasource.enums.PhyQuantityFields.P_NAME;
import static com.pcs.datasource.enums.PhyQuantityFields.UNIT;
import static com.pcs.datasource.enums.PhyQuantityFields.UN_SUPPORTED;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateResult;
import static com.pcs.devicecloud.enums.Status.ACTIVE;
import static com.pcs.devicecloud.enums.Status.INACTIVE;
import static com.pcs.devicecloud.enums.Status.SUCCESS;
import static com.pcs.devicecloud.enums.Status.UNSUPPORTED;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.Unit;
import com.pcs.datasource.repository.UnitRepo;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * This service implementation class is responsible for defining all the
 * services related to units of physical quantity. This class is responsible for
 * persisting,updating,deleting (soft delete) and fetching units the physical
 * quantity details.
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */

@Service
public class UnitServiceImpl implements UnitService {

	@Autowired
	@Qualifier("unitNeo")
	private UnitRepo unitRepo;

	@Autowired
	private PhyQuantityService phyQuantityService;

	@Autowired
	private StatusUtil statusUtil;

	/**
	 * method to save unit information of particular physical quantity
	 * 
	 * @param unit
	 * @return {@link StatusMessageDTO}
	 */
	@Override
	public StatusMessageDTO createUnit(Unit unit) {
		validateMandatoryFields(unit, NAME, P_NAME, IS_SI);
		validateUnit(unit);
		PhysicalQuantity physicalQuantity = null;
		try {
			physicalQuantity = phyQuantityService.getPhyQuantity(unit
			        .getpName());
		} catch (DeviceCloudException e1) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        P_NAME.getDescription());
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		// unit.setpUuid(physicalQuantity.getId());
		try {
			if (physicalQuantity != null) {
				if ((physicalQuantity.getStatus().equals(UNSUPPORTED
				        .getStatus())) && (unit.getIsSi())) {
					physicalQuantity.setStatus(ACTIVE.getStatus());
					physicalQuantity.setStatusKey(statusUtil.getStatus(ACTIVE
					        .name()));
				}
				unit.setStatus(Status.ACTIVE.getStatus());
				unit.setStatusKey(statusUtil.getStatus(ACTIVE.name()));
				unitRepo.persistUnit(unit, physicalQuantity);
			} else {
				unitRepo.persistUnit(unit);
			}
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * method to get unit information of particular physical quantity
	 * 
	 * @param phyQuantityName
	 * @return {@link List}
	 */

	@Override
	public List<Unit> getUnitDeatils(String phyQuantityName) {
		validateMandatoryField(PHY_QUAN.getDescription(), phyQuantityName);
		try {
			phyQuantityName = URLDecoder.decode(phyQuantityName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		List<Unit> units = unitRepo.getUnits(phyQuantityName);
		validateResult(units);
		return units;
	}

	/**
	 * method to update unit information of particular physical quantity
	 * 
	 * @param unit
	 * @return {@link StatusMessageDTO}
	 */

	@Override
	public StatusMessageDTO updateUnit(String unitName, String phyQtyName,
	        Unit unit) {
		validateMandatoryField(UNIT.getDescription(), unitName);
		validateMandatoryField(PHY_QUAN.getDescription(), phyQtyName);
		validateMandatoryFields(unit, NAME, IS_SI);
		unit.setpName(phyQtyName);
		validateUnit(unit);
		PhysicalQuantity physicalQuantity = null;
		try {
			physicalQuantity = phyQuantityService.getPhyQuantity(phyQtyName);
		} catch (DeviceCloudException e1) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        P_NAME.getDescription());
		}
		Unit oldUnit = unitRepo.getUnitDetails(unitName);
		if (oldUnit == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        UNIT.getDescription());
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		try {
			if ((physicalQuantity.getStatus().equals(UNSUPPORTED.getStatus()))
			        && (unit.getIsSi())) {
				physicalQuantity.setStatus(ACTIVE.getStatus());
				physicalQuantity.setStatusKey(statusUtil.getStatus(ACTIVE
				        .name()));
				unit.setId(oldUnit.getId());
				phyQuantityService.updatePhyQuantity(phyQtyName,
				        physicalQuantity);
				unitRepo.updateUnit(oldUnit.getId().toString(), unit);
			} else if ((physicalQuantity.getStatus().equals(ACTIVE.getStatus()))
			        && (oldUnit.getIsSi()) && (!unit.getIsSi())) {
				physicalQuantity.setStatus(UN_SUPPORTED.name());
				physicalQuantity.setStatusKey(statusUtil.getStatus(UN_SUPPORTED
				        .name()));
				unit.setId(oldUnit.getId());
				phyQuantityService.updatePhyQuantity(phyQtyName,
				        physicalQuantity);
				unitRepo.updateUnit(oldUnit.getId().toString(), unit);
			} else {
				unit.setId(oldUnit.getId());
				unitRepo.updateUnit(unitName, unit);
			}
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * method to delete unit information (soft delete) of particular physical
	 * quantity
	 * 
	 * @param uuid
	 * @return {@link StatusMessageDTO}
	 */

	@Override
	public StatusMessageDTO deleteUnit(String unitName, String PhyQuantityName) {
		validateMandatoryField(UNIT.getDescription(), unitName);
		validateMandatoryField(PHY_QUAN.getDescription(), PhyQuantityName);
		Unit unit = unitRepo.getUnitDetails(unitName);
		if (unit == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        UNIT.getDescription());
		}
		PhysicalQuantity physicalQuantity = phyQuantityService
		        .getPhyQuantity(PhyQuantityName);
		try {
			unit.setStatus(INACTIVE.name());
			unit.setStatusKey(statusUtil.getStatus(INACTIVE.name()));
			if (unit.getIsSi()) {
				physicalQuantity.setStatus(UN_SUPPORTED.name());
				physicalQuantity.setStatusKey(statusUtil.getStatus(UN_SUPPORTED
				        .name()));
				// unitRepo.deleteUnit(unit.getId().toString(),
				// physicalQuantity);
				unitRepo.deleteUnit(unit);
			} else {
				unitRepo.deleteUnit(unit);
			}
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.PERSISTENCE_EXCEPTION, e);
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * Method to validate unit information from input
	 * 
	 * @param unit
	 */
	private void validateUnit(Unit unit) {
		if (isExistsUnit(unit.getName(), unit.getpName())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
			        NAME.getDescription());
		}
		if (!unit.getIsSi()) {
			validateMandatoryField(CONVERSION.getDescription(),
			        unit.getConversion());
		} else {
			if ((isExistsSIUnitByPQname(unit.getpName()))
			        || (unitRepo.isSIUnit(unit.getName()))) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.FIELD_ALREADY_EXIST,
				        IS_SI.getDescription());
			}
		}
	}

	/**
	 * Method to check whether the unit exists or not
	 * 
	 * @param phyQuantityName
	 * @return boolean
	 */

	private boolean isExistsUnit(String name, String phyQuantity) {
		return unitRepo.getUnitDetails(name) == null ? false : true;
	}

	/**
	 * Method to check whether the SI unit exists or not
	 * 
	 * @param phyQuantityName
	 * @return boolean
	 */

	private boolean isExistsSIUnitByPQname(String name) {
		return unitRepo.getSIUnitByPQName(name) == null ? false : true;

	}

	/**
	 * Method to get unit informations for particular physical quantity by
	 * physical quantity Id
	 * 
	 * @param uuid
	 * @return {@link List}
	 */
	@Override
	public List<Unit> getUnits(String phyQuantityId) {
		validateMandatoryField(PHY_QUAN.getDescription(), phyQuantityId);
		return unitRepo.getUnits(phyQuantityId);
	}

	/**
	 * Method to get Unit information by name for particular physical qunatity
	 * 
	 * @param name
	 * @param phyQuantity
	 * @return {@link Unit}
	 */
	@Override
	public Unit getUnitDetails(String name, String phyQuantity) {
		return unitRepo.getUnitDetails(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.datasource.services.UnitService#isSIUnit(java.lang.String)
	 */
	@Override
	public StatusMessageDTO isSIUnit(String unitName) {
		validateMandatoryField(UNIT, unitName);

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FALSE);
		if (unitRepo.isSIUnit(unitName)) {
			statusMessageDTO.setStatus(Status.TRUE);
		}
		return statusMessageDTO;
	}

	@Override
	public boolean isUnitExist(String unitName) {
		return unitRepo.isUnitExist(unitName);
	}

}
