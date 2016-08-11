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
package com.pcs.avocado.services.impl;

import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.avocado.enums.FacilityDataFields.BUILDING_STRUCTURE;
import static com.pcs.avocado.enums.FacilityDataFields.BUILDING_TYPE;
import static com.pcs.avocado.enums.FacilityDataFields.CITY;
import static com.pcs.avocado.enums.FacilityDataFields.CLIENT_NAME;
import static com.pcs.avocado.enums.FacilityDataFields.COUNTRY;
import static com.pcs.avocado.enums.FacilityDataFields.DOMAIN_NAME;
import static com.pcs.avocado.enums.FacilityDataFields.EMIRATE;
import static com.pcs.avocado.enums.FacilityDataFields.ENTITY_TEMPLATE;
import static com.pcs.avocado.enums.FacilityDataFields.ENTITY_TEMPLATE_NAME;
import static com.pcs.avocado.enums.FacilityDataFields.FACILITY;
import static com.pcs.avocado.enums.FacilityDataFields.FACILITY_NAME;
import static com.pcs.avocado.enums.FacilityDataFields.KEY;
import static com.pcs.avocado.enums.FacilityDataFields.MAX_VALUE;
import static com.pcs.avocado.enums.FacilityDataFields.PRE_ECM_END_DATE;
import static com.pcs.avocado.enums.FacilityDataFields.RFS_DATE;
import static com.pcs.avocado.enums.FacilityDataFields.SAVINGS_TARGET;
import static com.pcs.avocado.enums.FacilityDataFields.SQUARE_FEET;
import static com.pcs.avocado.enums.FacilityDataFields.START_DATE;
import static com.pcs.avocado.enums.FacilityDataFields.TEMPLATE;
import static com.pcs.avocado.enums.FacilityDataFields.TIME_ZONE;
import static com.pcs.avocado.enums.FacilityDataFields.VERTICAL;
import static com.pcs.avocado.enums.FacilityDataFields.WEATHER_STATION;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.ReturnFieldsDTO;
import com.pcs.avocado.commons.dto.SearchFieldsDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.service.SubscriptionProfileService;
import com.pcs.avocado.commons.util.ObjectBuilderUtil;
import com.pcs.avocado.commons.validation.ValidationUtils;
import com.pcs.avocado.dto.PayloadDTO;
import com.pcs.avocado.dto.SiteDTO;
import com.pcs.avocado.enums.FacilityDataFields;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.rest.exception.GalaxyRestException;
import com.pcs.avocado.services.FacilityService;

/**
 * 
 * @description This class is responsible for the FacilityServiceImpl
 * 
 * @author Twinkle (PCSEG297)
 * @date January 2015
 */
@Service
public class FacilityServiceImpl implements FacilityService {

	@Qualifier("avocadoEsbClient")
	@Autowired
	private BaseClient avocadoEsbClient;

	@Qualifier("platformEsbClient")
	@Autowired
	private BaseClient platformEsbClient;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	@Value("${avocado.esb.facility.context}")
	private String facilityUrlCtx;

	@Value("${avocado.esb.create.facility}")
	private String createFacilityUrl;

	@Value("${avocado.esb.update.facility}")
	private String updateFacilityUrl;

	@Value("${platform.esb.marker.search}")
	private String searchFacilityUrl;

	@Value("${platform.esb.marker.list}")
	private String listFacilityUrl;

	@Value("${platform.esb.marker.find}")
	private String findFacilityUrl;

	@Autowired
	private SubscriptionProfileService profileService;

	@Autowired
	private ValidationUtils validationUtils;

	/***
	 * @Description This method is responsible to create a facility/site
	 * 
	 * @param facilityEntity
	 * @return facilityEntity
	 */
	@Override
	public IdentityDTO createFacility(EntityDTO facilityEntity) {

		IdentityDTO obj = avocadoEsbClient.post(facilityUrlCtx, getJwtToken(),
		        getPayload(facilityEntity, false), IdentityDTO.class);
		return obj;
	}

	private PayloadDTO getPayload(EntityDTO facilityEntity, Boolean update) {
		PayloadDTO payloadDTO = null;
		if (update) {
			payloadDTO = validateAndCreatePayloadForUpdate(facilityEntity);
		} else {
			payloadDTO = validateAndCreatePayload(facilityEntity);

			// check if Facility is already existing, then do not create again
			try {

				SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
				searchFieldsDTO.setDomain(facilityEntity.getDomain());
				searchFieldsDTO.setEntityTemplate(facilityEntity
				        .getEntityTemplate());

				List<FieldMapDTO> searchField = new ArrayList<FieldMapDTO>();
				searchField.add(new FieldMapDTO(FACILITY_NAME.getFieldName(),
				        fieldValue(FACILITY_NAME.getFieldName(),
				                facilityEntity.getFieldValues())));

				searchFieldsDTO.setSearchFields(searchField);
				@SuppressWarnings("unchecked") List<ReturnFieldsDTO> list = platformEsbClient
				        .post(searchFacilityUrl, getJwtToken(),
				                searchFieldsDTO, List.class);
				if (list.size() > 0) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
					        FACILITY.getDescription());
				}
			} catch (GalaxyRestException ge) {
				if (!ge.getMessage().equals(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
					throw new GalaxyException(
					        GalaxyCommonErrorCodes.FIELD_ALREADY_EXIST,
					        FACILITY.getDescription());

				}
			}

		}
		return payloadDTO;
	}

	private PayloadDTO validateAndCreatePayload(EntityDTO facilityEntity) {

		SiteDTO siteDTO = new SiteDTO();
		List<FieldMapDTO> fieldValues = facilityEntity.getFieldValues();

		siteDTO.setBuildingStructure(fieldValue(
		        BUILDING_STRUCTURE.getFieldName(), fieldValues));
		siteDTO.setBuildingType(fieldValue(BUILDING_TYPE.getFieldName(),
		        fieldValues));
		siteDTO.setCity(fieldValue(CITY.getFieldName(), fieldValues));
		siteDTO.setClientName(fieldValue(CLIENT_NAME.getFieldName(),
		        fieldValues));
		siteDTO.setCountry(fieldValue(COUNTRY.getFieldName(), fieldValues));
		siteDTO.setEmirate(fieldValue(EMIRATE.getFieldName(), fieldValues));

		String facilityName = fieldValue(FACILITY_NAME.getFieldName(),
		        fieldValues);
		if (StringUtils.isBlank(facilityName)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        FACILITY_NAME.getDescription());
		}
		siteDTO.setFacilityName(facilityName);
		siteDTO.setMaxValueEnergyProfile(fieldValue(MAX_VALUE.getFieldName(),
		        fieldValues));
		siteDTO.setPreEcmEndDate(fieldValue(PRE_ECM_END_DATE.getFieldName(),
		        fieldValues));
		siteDTO.setRfsDate(fieldValue(RFS_DATE.getFieldName(), fieldValues));
		siteDTO.setSavingsTarget(fieldValue(SAVINGS_TARGET.getFieldName(),
		        fieldValues));
		siteDTO.setSquareFeet(fieldValue(SQUARE_FEET.getFieldName(),
		        fieldValues));
		siteDTO.setStartDate(fieldValue(START_DATE.getFieldName(), fieldValues));
		siteDTO.setTimeZone(fieldValue(TIME_ZONE.getFieldName(), fieldValues));
		siteDTO.setVertical(fieldValue(VERTICAL.getFieldName(), fieldValues));
		siteDTO.setWeatherStation(fieldValue(WEATHER_STATION.getFieldName(),
		        fieldValues));
		// in case of create request, value must be false
		siteDTO.setUpdate("false");

		PayloadDTO payloadDTO = new PayloadDTO();
		payloadDTO.setMarkerPayload(facilityEntity);
		payloadDTO.setSkysparkPayload(siteDTO);

		return payloadDTO;
	}

	private String fieldValue(String fieldName, List<FieldMapDTO> fieldValues) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldName);
		int fieldIndex = fieldValues.indexOf(field);

		String value = null;
		if (fieldIndex != -1) {
			value = fieldValues.get(fieldIndex).getValue();
			return value;
		} else {
			return value;
		}
	}

	@Override
	public EntityDTO getFacility(String templateName, String facilityName,
	        String domainName) {

		DomainDTO domainDTO = checkDomainAndTemplate(domainName, templateName);
		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		searchFieldsDTO.setDomain(domainDTO);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(templateName);
		searchFieldsDTO.setEntityTemplate(entityTemplateDTO);

		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(new FieldMapDTO(FACILITY_NAME.getFieldName(),
		        facilityName));
		searchFieldsDTO.setSearchFields(searchFields);

		// service should return all the dataprovider
		@SuppressWarnings("unchecked") List<ReturnFieldsDTO> returnFields = platformEsbClient
		        .post(searchFacilityUrl, getJwtToken(), searchFieldsDTO,
		                List.class, ReturnFieldsDTO.class);

		ReturnFieldsDTO dto = (ReturnFieldsDTO)returnFields.get(0);

		EntityDTO entityDto = new EntityDTO();
		entityDto.setDataprovider(dto.getDataprovider());
		entityDto.setDomain(dto.getDomain());
		entityDto.setEntityTemplate(dto.getEntityTemplate());
		entityDto.setPlatformEntity(dto.getPlatformEntity());
		entityDto.setIdentifier(dto.getIdentifier());
		entityDto.setEntityStatus(dto.getEntityStatus());
		return entityDto;
	}

	private IdentityDTO createIdentityDTO(EntityDTO facilityEntity) {
		IdentityDTO identityDTO = new IdentityDTO();
		identityDTO.setEntityTemplate(facilityEntity.getEntityTemplate());
		identityDTO.setDomain(facilityEntity.getDomain());
		identityDTO.setIdentifier(facilityEntity.getIdentifier());
		return identityDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> getAllFacility(String templateName, String domainName) {
		IdentityDTO identityDTO = new IdentityDTO();

		DomainDTO domainDTO = checkDomainAndTemplate(domainName, templateName);
		domainDTO.setDomainName(domainName);
		identityDTO.setDomain(domainDTO);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(templateName);

		identityDTO.setEntityTemplate(entityTemplateDTO);

		List<EntityDTO> list = platformEsbClient.post(listFacilityUrl,
		        getJwtToken(), identityDTO, List.class);
		return list;
	}

	@Override
	public StatusMessageDTO updateFacility(String facilityName,
	        String domainName, EntityDTO facilityEntity) {

		DomainDTO domainDTO = new DomainDTO();
		if (StringUtils.isNotBlank(domainName)) {
			domainDTO.setDomainName(domainName);
		} else {
			domainDTO.setDomainName(profileService.getEndUserDomain());
		}
		facilityEntity.setDomain(domainDTO);

		Set<FieldMapDTO> fieldValues = new HashSet<>();
		fieldValues.addAll(facilityEntity.getFieldValues());

		FieldMapDTO facilityNameFM = new FieldMapDTO(
		        FACILITY_NAME.getFieldName(), facilityName);
		if (!fieldValues.add(facilityNameFM)) {
			fieldValues.remove(facilityNameFM);
			fieldValues.add(facilityNameFM);
		}

		facilityEntity.setFieldValues(null);
		List<FieldMapDTO> lst = new ArrayList<FieldMapDTO>();
		lst.addAll(fieldValues);
		facilityEntity.setFieldValues(lst);

		Object obj = avocadoEsbClient.put(updateFacilityUrl, getJwtToken(),
		        getPayload(facilityEntity, true), Object.class);
		if (obj instanceof List) {
			return new StatusMessageDTO(Status.SUCCESS);
		}
		return new StatusMessageDTO(Status.FAILURE);
	}

	@SuppressWarnings("incomplete-switch")
	private PayloadDTO validateAndCreatePayloadForUpdate(
	        EntityDTO facilityEntity) {

		// facilityName and clientName cannot be updated
		checkFacilityNameAndClientName(facilityEntity);

		SiteDTO siteDTO = new SiteDTO();
		List<FieldMapDTO> fieldValues = facilityEntity.getFieldValues();
		for (FieldMapDTO fMap : fieldValues) {
			if (fieldValueNoExcpRequired(fMap.getKey(), fieldValues) != null) {

				FacilityDataFields str = FacilityDataFields.getEnum(fMap
				        .getKey());

				switch (str) {
					case BUILDING_STRUCTURE :
						siteDTO.setBuildingStructure(fieldValue(
						        BUILDING_STRUCTURE.getFieldName(), fieldValues));
						break;
					case BUILDING_TYPE :
						siteDTO.setBuildingType(fieldValue(
						        BUILDING_TYPE.getFieldName(), fieldValues));
						break;
					case CITY :
						siteDTO.setCity(fieldValue(CITY.getFieldName(),
						        fieldValues));
						break;
					case CLIENT_NAME :
						siteDTO.setClientName(fieldValue(
						        CLIENT_NAME.getFieldName(), fieldValues));
						break;
					case COUNTRY :
						siteDTO.setCountry(fieldValue(COUNTRY.getFieldName(),
						        fieldValues));
						break;
					case EMIRATE :
						siteDTO.setEmirate(fieldValue(EMIRATE.getFieldName(),
						        fieldValues));
						break;
					case FACILITY_NAME :
						siteDTO.setFacilityName(fieldValue(
						        FACILITY_NAME.getFieldName(), fieldValues));
						break;
					case MAX_VALUE :
						siteDTO.setMaxValueEnergyProfile(fieldValue(
						        MAX_VALUE.getFieldName(), fieldValues));
						break;
					case PRE_ECM_END_DATE :
						siteDTO.setPreEcmEndDate(fieldValue(
						        PRE_ECM_END_DATE.getFieldName(), fieldValues));
						break;
					case RFS_DATE :
						siteDTO.setRfsDate(fieldValue(RFS_DATE.getFieldName(),
						        fieldValues));
						break;
					case SAVINGS_TARGET :
						siteDTO.setSavingsTarget(fieldValue(
						        SAVINGS_TARGET.getFieldName(), fieldValues));
						break;
					case SQUARE_FEET :
						siteDTO.setSquareFeet(fieldValue(
						        SQUARE_FEET.getFieldName(), fieldValues));
						break;
					case START_DATE :
						siteDTO.setStartDate(fieldValue(
						        START_DATE.getFieldName(), fieldValues));
						break;
					case TIME_ZONE :
						siteDTO.setTimeZone(fieldValue(
						        TIME_ZONE.getFieldName(), fieldValues));
						break;
					case VERTICAL :
						siteDTO.setVertical(fieldValue(VERTICAL.getFieldName(),
						        fieldValues));
						break;
					case WEATHER_STATION :
						siteDTO.setWeatherStation(fieldValue(
						        WEATHER_STATION.getFieldName(), fieldValues));
						break;
				}
			}
		}
		// in case of update request, value must be "true"
		siteDTO.setUpdate("true");

		PayloadDTO payloadDTO = new PayloadDTO();
		payloadDTO.setMarkerPayload(facilityEntity);
		payloadDTO.setSkysparkPayload(siteDTO);

		return payloadDTO;
	}

	private String fieldValueNoExcpRequired(String fieldName,
	        List<FieldMapDTO> fieldValues) {
		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldName);
		int fieldIndex = fieldValues.indexOf(field);

		String value = null;
		if (fieldIndex != -1) {
			value = fieldValues.get(fieldIndex).getValue();
			return value;
		}
		return value;
	}

	@SuppressWarnings({
            "static-access", "unchecked"})
	private void checkFacilityNameAndClientName(EntityDTO facilityEntity) {

		// template name must be present in payload and identifier key-value
		// must also present, identity fields will be used to fetch an entity
		// and compare and throw error on FacilityNAme and ClientName update
		validationUtils
		        .validateMandatoryFields(facilityEntity, ENTITY_TEMPLATE);
		validationUtils.validateMandatoryFields(
		        facilityEntity.getEntityTemplate(), ENTITY_TEMPLATE_NAME);

		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		searchFieldsDTO.setDomain(facilityEntity.getDomain());

		searchFieldsDTO.setEntityTemplate(facilityEntity.getEntityTemplate());

		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO fieldMapDTO = new FieldMapDTO(FACILITY_NAME.getFieldName(),
		        fieldValue(FACILITY_NAME.getFieldName(),
		                facilityEntity.getFieldValues()));
		searchFields.add(fieldMapDTO);
		searchFieldsDTO.setSearchFields(searchFields);
		List<ReturnFieldsDTO> returnFields = null;
		try {
			returnFields = platformEsbClient
			        .post(searchFacilityUrl, getJwtToken(), searchFieldsDTO,
			                List.class, ReturnFieldsDTO.class);
		} catch (GalaxyRestException ge) {
			if (ge.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
				        FACILITY_NAME.getDescription());

			} else if (ge.getMessage().equals(
			        KEY.getDescription()
			                + GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED
			                        .getMessage())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
				        DOMAIN_NAME.getDescription());
			}
			throw ge;
		}
		ReturnFieldsDTO dto = (ReturnFieldsDTO)returnFields.get(0);

		if (dto == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		// EntityDTO entityDto = platformEsbClient.post(findFacilityUrl,
		// getJwtToken(), createIdentityDTO(facilityEntity),
		// EntityDTO.class);
		// EntityDTO entityDto = new EntityDTO();
		// entityDto.setDataprovider(dto.getDataprovider());
		// entityDto.setDomain(dto.getDomain());
		// entityDto.setEntityTemplate(dto.getEntityTemplate());
		// entityDto.setPlatformEntity(dto.getPlatformEntity());
		// entityDto.setIdentifier(dto.getIdentifier());
		// entityDto.setEntityStatus(dto.getEntityStatus());

		List<FieldMapDTO> dbFieldValues = facilityEntity.getFieldValues();

		List<FieldMapDTO> inputFieldValues = dto.getDataprovider();

		// facilityName and clientName cannot be updated
		if (!fieldValue(FACILITY_NAME.getFieldName(), dbFieldValues).equals(
		        fieldValue(FACILITY_NAME.getFieldName(), inputFieldValues))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
			        FACILITY_NAME.getDescription());
		}
		if (!fieldValue(CLIENT_NAME.getFieldName(), dbFieldValues).equals(
		        fieldValue(CLIENT_NAME.getFieldName(), inputFieldValues))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_CANNOT_BE_UPDATED,
			        CLIENT_NAME.getDescription());
		}
		facilityEntity.setIdentifier(dto.getIdentifier());
	}

	private DomainDTO checkDomainAndTemplate(String domainName,
	        String templateName) {
		DomainDTO domainDTO = new DomainDTO();
		if (StringUtils.isNotBlank(domainName)) {
			domainDTO.setDomainName(domainName);
		} else {
			domainDTO.setDomainName(profileService.getEndUserDomain());
		}

		if (StringUtils.isBlank(templateName)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        TEMPLATE.getDescription());
		}
		return domainDTO;
	}
}
