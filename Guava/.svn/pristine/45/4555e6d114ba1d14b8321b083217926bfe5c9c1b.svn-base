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
package com.pcs.guava.driver.serviceImpl;

import static com.pcs.guava.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.guava.constant.CommonConstants.DRIVER_TEMPLATE;
import static com.pcs.guava.driver.enums.DriverDataFields.DATE_OF_BIRTH;
import static com.pcs.guava.driver.enums.DriverDataFields.DRIVER_ID;
import static com.pcs.guava.driver.enums.DriverDataFields.DRIVER_IDENTIFIER;
import static com.pcs.guava.driver.enums.DriverDataFields.EMPLOYEE_CODE;
import static com.pcs.guava.driver.enums.DriverDataFields.ENTITY_TEMPLATE;
import static com.pcs.guava.driver.enums.DriverDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.guava.driver.enums.DriverDataFields.IDENTIFIER;
import static com.pcs.guava.driver.enums.DriverDataFields.KEY;
import static com.pcs.guava.driver.enums.DriverDataFields.MOBILE_NUMBER;
import static com.pcs.guava.driver.enums.DriverDataFields.NAME;
import static com.pcs.guava.driver.enums.DriverDataFields.NATIONALITY;
import static com.pcs.guava.driver.enums.DriverDataFields.STATUS;
import static com.pcs.guava.driver.enums.DriverDataFields.VALUE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.guava.commons.dto.DomainDTO;
import com.pcs.guava.commons.dto.EntityDTO;
import com.pcs.guava.commons.dto.EntityTemplateDTO;
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.ReturnFieldsDTO;
import com.pcs.guava.commons.dto.SearchFieldsDTO;
import com.pcs.guava.commons.dto.StatusDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.service.SubscriptionProfileService;
import com.pcs.guava.driver.dto.Driver;
import com.pcs.guava.driver.enums.DriverDataFields;
import com.pcs.guava.driver.services.DriverService;
import com.pcs.guava.enums.Status;
import com.pcs.guava.rest.client.BaseClient;
import com.pcs.guava.rest.exception.GalaxyRestException;

/**
 * Service Implementation for Driver Related APIs
 * 
 * @author Twinkle (PCSEG297)
 * @date April 2016
 */
@Service
public class DriverServiceImpl implements DriverService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DriverServiceImpl.class);

	@Autowired
	private SubscriptionProfileService profileService;

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformESBClient;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;

	@Value("${platform.esb.marker.find}")
	private String findMarkerPlatformEndpoint;

	@Value("${platform.esb.marker.search}")
	private String listMarkersByTypePlatformEndpoint;

	@Value("${platform.esb.marker.list}")
	private String listMarkersPlatformEndpoint;

	@Override
	public Driver createDriver(Driver driver) {
		validateCreateDriverPayload(driver);
		validateDomain(driver);
		return createDriverEntity(driver);
	}

	@Override
	public Driver getDriver(IdentityDTO driverIdentity) {
		validateDriverIdentifier(driverIdentity);
		EntityDTO driverEntity = getDriverEntityFromPlatform(driverIdentity);
		Driver driver = convertDriverDTOFromEntity(driverEntity);
		return driver;
	}

	@Override
	public List<Driver> findAll(String domainName) {

		if (isBlank(domainName)) {
			domainName = profileService.getEndUserDomain();
		}

		EntityDTO entityDTO = new EntityDTO();

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(DRIVER_TEMPLATE);

		entityDTO.setEntityTemplate(entityTemplateDTO);

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(domainName);
		entityDTO.setDomain(domainDTO);

		@SuppressWarnings("unchecked") List<EntityDTO> entities = platformESBClient
		        .post(listMarkersPlatformEndpoint,
		                profileService.getJwtToken(), entityDTO, List.class,
		                EntityDTO.class);

		List<Driver> drivers = new ArrayList<Driver>();
		if (CollectionUtils.isNotEmpty(entities)) {
			for (EntityDTO entity : entities) {
				Driver driver = new Driver();
				for (FieldMapDTO fm : entity.getDataprovider()) {
					String value = fm.getValue();
					DriverDataFields driverDataFields = DriverDataFields
					        .getEnum(fm.getKey());
					switch (driverDataFields) {
						case NAME :
							driver.setName(value);
							break;
						case EMPLOYEE_CODE :
							driver.setEmployeeCode(value);
							break;
						case NATIONALITY :
							driver.setNationality(value);
							break;
						case DATE_OF_BIRTH :
							driver.setDateOfBirth(new Long(value));
							break;
						case MOBILE_NUMBER :
							driver.setMobileNumber(value);
							break;
						case STATUS :
							driver.setStatus(value);
							break;
						case DRIVER_ID :
							driver.setDriverId(value);
							break;
						default:
							LOGGER.debug("switch default case for "
							        + driverDataFields);
							break;
					}
				}
				driver.setDriverIdentifier(entity.getIdentifier().getValue());
				drivers.add(driver);
			}
		}
		return drivers;
	}

	@Override
	public StatusMessageDTO updateDriver(Driver driverPayload) {
		validateUpdatePayload(driverPayload);
		validateDomain(driverPayload);
		return updateDriverEntity(driverPayload);
	}

	private EntityDTO getDriverEntityFromPlatform(IdentityDTO driverIdentity) {
		EntityDTO driverEntity = platformESBClient.post(
		        findMarkerPlatformEndpoint, profileService.getJwtToken(),
		        driverIdentity, EntityDTO.class);
		return driverEntity;
	}

	private StatusMessageDTO updateDriverEntity(Driver driver) {

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(Status.FAILURE);
		EntityDTO driveEntityToBeUpdated = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(driver.getDomainName());
		driveEntityToBeUpdated.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(DRIVER_TEMPLATE);
		driveEntityToBeUpdated.setEntityTemplate(entityTemplate);

		List<FieldMapDTO> fiedValues = new ArrayList<FieldMapDTO>();
		fiedValues.add(new FieldMapDTO(DRIVER_ID.getFieldName(), driver
		        .getDriverId()));
		fiedValues.add(new FieldMapDTO(NAME.getFieldName(), driver.getName()));
		fiedValues.add(new FieldMapDTO(NATIONALITY.getFieldName(), driver
		        .getNationality()));
		fiedValues.add(new FieldMapDTO(MOBILE_NUMBER.getFieldName(), driver
		        .getMobileNumber()));
		fiedValues.add(new FieldMapDTO(STATUS.getFieldName(), driver
		        .getStatus()));
		if (isNotBlank(driver.getEmployeeCode())) {
			fiedValues.add(new FieldMapDTO(EMPLOYEE_CODE.getFieldName(), driver
			        .getEmployeeCode()));
		}
		if (driver.getDateOfBirth() != null) {
			fiedValues.add(new FieldMapDTO(DATE_OF_BIRTH.getFieldName(), driver
			        .getDateOfBirth().toString()));
		}

		StatusDTO status = new StatusDTO();
		status.setStatusName(new FieldMapDTO(STATUS.getFieldName(), driver
		        .getStatus()).getValue());
		driveEntityToBeUpdated.setEntityStatus(status);
		driveEntityToBeUpdated.setFieldValues(fiedValues);
		driveEntityToBeUpdated.setIdentifier(new FieldMapDTO(IDENTIFIER
		        .getFieldName(), driver.getDriverIdentifier()));

		Map<String, String> jwtToken = profileService.getJwtToken();

		statusMessageDTO = platformESBClient.put(markerPlatformEndpoint,
		        jwtToken, driveEntityToBeUpdated, StatusMessageDTO.class);

		if (!statusMessageDTO.getStatus().equals(Status.SUCCESS)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.CUSTOM_ERROR,
			        "Data couldnot be updated."); 
		}
		return statusMessageDTO;
	}

	private void validateUpdatePayload(Driver driverPayload) {
		validateMandatoryFields(driverPayload, DRIVER_ID, NAME, NATIONALITY,
		        MOBILE_NUMBER, STATUS, DRIVER_IDENTIFIER);
	}

	private void validateCreateDriverPayload(Driver driverPayload) {
		validateMandatoryFields(driverPayload, DRIVER_ID, NAME, NATIONALITY,
		        MOBILE_NUMBER, STATUS);
	}

	private void validateDomain(Driver driver) {
		if (isBlank(driver.getDomainName())) {
			driver.setDomainName(profileService.getEndUserDomain());
		}
	}

	@SuppressWarnings("unchecked")
	private void validateDriverId(Driver driver) {

		// search driver with driverId
		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName(driver.getDomainName());

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(DRIVER_TEMPLATE);

		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		searchFieldsDTO.setDomain(domainDTO);
		FieldMapDTO driverIDFM = new FieldMapDTO(DRIVER_ID.getFieldName(),
		        driver.getDriverId());
		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(driverIDFM);
		searchFieldsDTO.setSearchFields(searchFields);
		searchFieldsDTO.setEntityTemplate(entityTemplateDTO);
		List<String> returnFields = new ArrayList<String>();
		returnFields.add(IDENTIFIER.getFieldName());
		searchFieldsDTO.setReturnFields(returnFields);

		List<ReturnFieldsDTO> drivers = null;
		try {
			drivers = platformESBClient.post(listMarkersByTypePlatformEndpoint,
			        profileService.getJwtToken(), searchFieldsDTO, List.class,
			        ReturnFieldsDTO.class);
		} catch (GalaxyRestException gre) {
			if (gre.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {

			} else {
				throw gre;
			}
		}
		if (CollectionUtils.isNotEmpty(drivers)) {
			if (drivers.size() >= 1) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.FIELD_IS_NOT_UNIQUE,
				        DRIVER_ID.getDescription());
			}
		}
	}

	private Driver createDriverEntity(Driver driver) {

		EntityDTO driveEntityToBeCreated = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(driver.getDomainName());
		driveEntityToBeCreated.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(DRIVER_TEMPLATE);
		driveEntityToBeCreated.setEntityTemplate(entityTemplate);

		List<FieldMapDTO> fiedValues = new ArrayList<FieldMapDTO>();
		fiedValues.add(new FieldMapDTO(DRIVER_ID.getFieldName(), driver
		        .getDriverId()));
		fiedValues.add(new FieldMapDTO(NAME.getFieldName(), driver.getName()));
		fiedValues.add(new FieldMapDTO(NATIONALITY.getFieldName(), driver
		        .getNationality()));
		fiedValues.add(new FieldMapDTO(MOBILE_NUMBER.getFieldName(), driver
		        .getMobileNumber()));
		fiedValues.add(new FieldMapDTO(STATUS.getFieldName(), driver
		        .getStatus()));
		if (isNotBlank(driver.getEmployeeCode())) {
			fiedValues.add(new FieldMapDTO(EMPLOYEE_CODE.getFieldName(), driver
			        .getEmployeeCode()));
		}
		if (isNotBlank(driver.getEmployeeCode())) {
			fiedValues.add(new FieldMapDTO(DATE_OF_BIRTH.getFieldName(), driver
			        .getDateOfBirth().toString()));
		}

		driveEntityToBeCreated.setFieldValues(fiedValues);

		StatusDTO status = new StatusDTO();
		status.setStatusName(new FieldMapDTO(STATUS.getFieldName(), driver
		        .getStatus()).getValue());
		driveEntityToBeCreated.setEntityStatus(status);

		Map<String, String> jwtToken = profileService.getJwtToken();
		// save entity
		EntityDTO driverCreatedEntity = platformESBClient.post(
		        markerPlatformEndpoint, jwtToken, driveEntityToBeCreated,
		        EntityDTO.class);

		driver.setDriverIdentifier(driverCreatedEntity.getIdentifier()
		        .getValue());
		return driver;
	}

	private void validateDriverIdentifier(IdentityDTO identityDTO) {
		validateMandatoryFields(identityDTO, ENTITY_TEMPLATE, IDENTIFIER);
		validateMandatoryFields(identityDTO.getIdentifier(), KEY, VALUE);
		validateMandatoryFields(identityDTO.getEntityTemplate(),
		        ENTITY_TEMPLATE_NAME);
	}

	private Driver convertDriverDTOFromEntity(EntityDTO driverEntity) {
		Driver driver = new Driver();
		driver.setStatus(driverEntity.getEntityStatus().getStatusName());
		driver.setDomainName(driverEntity.getDomain().getDomainName());
		driver.setDriverIdentifier(driverEntity.getIdentifier().getValue());
		List<FieldMapDTO> fieldValues = driverEntity.getFieldValues();
		for (FieldMapDTO fieldMapDTO : fieldValues) {
			if (fieldMapDTO.getKey().equals(NAME.getFieldName())) {
				driver.setName(fieldMapDTO.getValue());
				continue;
			}
			if (fieldMapDTO.getKey().equals(NATIONALITY.getFieldName())) {
				driver.setNationality(fieldMapDTO.getValue());
				continue;
			}
			if (fieldMapDTO.getKey().equals(MOBILE_NUMBER.getFieldName())) {
				driver.setMobileNumber(fieldMapDTO.getValue());
				continue;
			}
			if (StringUtils.isNotBlank(fieldMapDTO.getKey())
			        && fieldMapDTO.getKey()
			                .equals(EMPLOYEE_CODE.getFieldName())) {
				driver.setEmployeeCode(fieldMapDTO.getValue());
				continue;
			}
			if (StringUtils.isNotBlank(fieldMapDTO.getKey())
			        && fieldMapDTO.getKey()
			                .equals(DATE_OF_BIRTH.getFieldName())) {
				driver.setDateOfBirth(new Long(fieldMapDTO.getValue()));
				continue;
			}
			if (fieldMapDTO.getKey().equals(DRIVER_ID.getFieldName())) {
				driver.setDriverId(fieldMapDTO.getValue());
				continue;
			}
		}
		return driver;
	}

}
