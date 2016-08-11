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
package com.pcs.galaxy.autoclaim;

import static com.pcs.galaxy.constants.FieldValueConstants.ASSET_TEMPLATE;
import static com.pcs.galaxy.constants.FieldValueConstants.DEVICE_TEMPLATE;
import static com.pcs.galaxy.constants.FieldValueConstants.ENTITY_NAME;
import static com.pcs.galaxy.constants.FieldValueConstants.IDENTIFIER;
import static com.pcs.galaxy.constants.FieldValueConstants.TAGS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.galaxy.dto.ConfigPoint;
import com.pcs.galaxy.dto.DomainDTO;
import com.pcs.galaxy.dto.EntityDTO;
import com.pcs.galaxy.dto.EntityTemplateDTO;
import com.pcs.galaxy.dto.FieldMapDTO;
import com.pcs.galaxy.dto.IdentityDTO;
import com.pcs.galaxy.dto.PointRelationship;
import com.pcs.galaxy.dto.ReturnFieldsDTO;
import com.pcs.galaxy.dto.SearchFieldsDTO;
import com.pcs.galaxy.dto.Status;
import com.pcs.galaxy.dto.StatusMessageDTO;
import com.pcs.galaxy.token.TokenProvider;

/**
 * MapPoints
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Component
public class MapPoints {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AssignDevice.class);

	@Value("${autoclaim.login.clientid}")
	private String clientId;
	@Value("${autoclaim.login.clientsecret}")
	private String secret;
	@Value("${autoclaim.login.username}")
	private String userName;
	@Value("${autoclaim.login.password}")
	private String password;
	@Value("${autoclaim.supertenant}")
	private String superTenantDomain;
	@Value("${autoclaim.domain}")
	private String supportDomain;
	@Value("${autoclaim.point.config.count}")
	private String pointCout;

	@Value("${autoclaim.alpine.asset.mappoints}")
	private String mapPointsURL;
	@Value("${autoclaim.alpine.marker.search}")
	private String getMarkerURL;
	@Value("${autoclaim.alpine.asset.find}")
	private String findAssetURL;
	@Value("${autoclaim.alpine.asset.update}")
	private String updateAssetURL;
	@Value("${autoclaim.saffron.device.getpoints}")
	private String getPointsURL;

	@Autowired
	@Qualifier("saffronClient")
	private BaseClient saffronClient;

	@Autowired
	@Qualifier("avocadoClient")
	private BaseClient avocadoClient;

	@Autowired
	@Qualifier("alpineClient")
	private BaseClient alpineClient;

	private List<EntityDTO> pointToEntityDtos(List<ConfigPoint> configPoints,
	        String sourceId, String equipName, IdentityDTO asset) {

		List<EntityDTO> points = new ArrayList<EntityDTO>();
		for (ConfigPoint configPoint : configPoints) {

			EntityDTO point = new EntityDTO();

			List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();

			FieldMapDTO dataType = new FieldMapDTO();
			dataType.setKey("dataType");
			dataType.setValue(configPoint.getType());
			fields.add(dataType);

			FieldMapDTO displayName = new FieldMapDTO();
			displayName.setKey("displayName");
			displayName.setValue(configPoint.getDisplayName());
			fields.add(displayName);

			FieldMapDTO physicalQuantity = new FieldMapDTO();
			physicalQuantity.setKey("physicalQuantity");
			physicalQuantity.setValue(configPoint.getPhysicalQuantity());
			fields.add(physicalQuantity);

			FieldMapDTO pointId = new FieldMapDTO();
			pointId.setKey("pointId");
			pointId.setValue(configPoint.getPointId());
			fields.add(pointId);

			FieldMapDTO pointName = new FieldMapDTO();
			pointName.setKey("pointName");
			pointName.setValue(configPoint.getPointName());
			fields.add(pointName);

			FieldMapDTO unit = new FieldMapDTO();
			unit.setKey("unit");
			unit.setValue(configPoint.getUnit());
			fields.add(unit);

			FieldMapDTO dataSourceName = new FieldMapDTO();
			dataSourceName.setKey("dataSourceName");
			dataSourceName.setValue(sourceId);
			fields.add(dataSourceName);

			FieldMapDTO equipNameField = new FieldMapDTO();
			equipNameField.setKey("equipName");
			equipNameField.setValue(equipName);
			fields.add(equipNameField);

			FieldMapDTO equipTemplate = new FieldMapDTO();
			equipTemplate.setKey("equipTemplate");
			equipTemplate.setValue(ASSET_TEMPLATE);
			fields.add(equipTemplate);

			FieldMapDTO equipIdentifier = new FieldMapDTO();
			equipIdentifier.setKey("equipIdentifier");
			equipIdentifier.setValue(asset.getIdentifier().getValue());
			fields.add(equipIdentifier);

			point.setFieldValues(fields);
			point.setIsSelected(true);
			points.add(point);
		}

		return points;

	}

	public StatusMessageDTO mapPoints(String sourceId,
	        List<ConfigPoint> configPoints) {

		if (CollectionUtils.isEmpty(configPoints)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.INCOMPLETE_REQUEST);
		}
		if (StringUtils.isEmpty(sourceId)) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        "Source Id");
		}
		sourceId = sourceId.toLowerCase();
		LOGGER.info("Map points started for the device : {}", sourceId);
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);

		ReturnFieldsDTO device = null;
		try {
			device = getDevice(sourceId);
			LOGGER.info("$$ Device available $$");
		} catch (Exception e) {
			LOGGER.info("Device doesnt exist with sourceId : {}", sourceId);
			return status;
		}
		IdentityDTO deviceIdentity = getIdentityDTO(device);

		IdentityDTO assetIdentity = null;
		FieldMapDTO tag = new FieldMapDTO();
		String assetName = null;
		tag.setKey(TAGS);
		tag = fetchField(device.getReturnFields(), tag);
		if (tag.getValue() != null && !tag.getValue().isEmpty()) {
			String[] splitTag = tag.getValue().split(",");

			if (splitTag.length >= 10) {

				String domain = getOwnerName(splitTag) + supportDomain;
				assetName = splitTag[9];
				try {
					assetIdentity = getAsset(assetName.toLowerCase(), domain);
					LOGGER.info("$$ Asset Available $$");
				} catch (Exception e) {
					LOGGER.info(
					        "Asset doesnt exist with Asset Name : {} in Domain : {}",
					        assetName, domain);
					return status;
				}
			} else {
				LOGGER.error("No enough asset details in device tag : "
				        + tag.getValue());
				return status;
			}
		} else {
			LOGGER.error("Device Tag is Null/Empty");
			return status;
		}

		PointRelationship mapPoint = new PointRelationship();
		mapPoint.setDevice(deviceIdentity);
		mapPoint.setEquipment(assetIdentity);
		List<EntityDTO> points = pointToEntityDtos(configPoints, sourceId,
		        assetName, assetIdentity);

		int conf = Integer.parseInt(pointCout);
		int cycle = points.size() / conf;
		int mode = points.size() % conf;

		if (mode > 0) {
			cycle++;
		}
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);
		try {
			for (int i = 0; i < cycle; i++) {
				List<EntityDTO> subList = null;
				if (mode > 0 && i == (cycle - 1)) {
					subList = points.subList(i * conf, (i * conf) + mode);
				} else {
					subList = points.subList(i * conf, (i + 1) * conf);
				}
				mapPoint.setPoints(subList);
				try {
					status = avocadoClient.post(mapPointsURL, header, mapPoint,
					        StatusMessageDTO.class);
				} catch (Exception e) {
					LOGGER.info(e.getMessage());
				}
				try {
					LOGGER.info("Mapped {} points in loop - {} out of {} ",
					        subList.size(), i + 1, cycle);
					if (i + 1 < cycle) {
						Thread.sleep(5000);
					}
				} catch (InterruptedException e) {
					LOGGER.info(e.getMessage());
				}
			}

			LOGGER.info("*** Points are mapped successfully ***");
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return status;
	}

	private String getOwnerName(String[] tags) {
		String superTenant = tags[0];
		String parentTenant = tags[1];
		String ownerTenant = tags[2];
		String realOwner = "";
		if (!StringUtils.isEmpty(superTenant)) {
			realOwner = superTenant;
			if (!StringUtils.isEmpty(parentTenant)) {
				realOwner = parentTenant;
			}
			if (!StringUtils.isEmpty(ownerTenant)) {
				realOwner = ownerTenant;
			}
		}
		return realOwner.toLowerCase();
	}

	@SuppressWarnings("unchecked")
	private ReturnFieldsDTO getDevice(String sourceId) {
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		SearchFieldsDTO search = new SearchFieldsDTO();
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		FieldMapDTO field = new FieldMapDTO();
		field.setKey("entityName");
		field.setValue(sourceId);
		fields.add(field);
		search.setSearchFields(fields);

		DomainDTO domainDto = new DomainDTO();
		domainDto.setDomainName(superTenantDomain);
		search.setDomain(domainDto);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
		search.setEntityTemplate(entityTemplate);

		ArrayList<String> fieldList = new ArrayList<String>();
		fieldList.add(ENTITY_NAME);
		fieldList.add(TAGS);
		search.setReturnFields(fieldList);

		List<ReturnFieldsDTO> entities = alpineClient.post(getMarkerURL,
		        header, search, List.class, ReturnFieldsDTO.class);
		if (entities != null) {
			return entities.get(0);
		}
		return null;
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {

		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	@SuppressWarnings("unchecked")
	private IdentityDTO getAsset(String assetName, String domain) {
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		SearchFieldsDTO search = new SearchFieldsDTO();
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		FieldMapDTO field = new FieldMapDTO();
		field.setKey("assetName");
		field.setValue(assetName);
		fields.add(field);
		search.setSearchFields(fields);

		DomainDTO domainDto = new DomainDTO();
		domainDto.setDomainName(domain);
		search.setDomain(domainDto);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(ASSET_TEMPLATE);
		search.setEntityTemplate(entityTemplate);

		ArrayList<String> fieldList = new ArrayList<String>();
		fieldList.add(IDENTIFIER);
		search.setReturnFields(fieldList);

		List<ReturnFieldsDTO> entities = alpineClient.post(getMarkerURL,
		        header, search, List.class, ReturnFieldsDTO.class);
		if (entities != null && !entities.isEmpty()) {
			IdentityDTO identity = getIdentityDTO(entities.get(0));
			return identity;
		}
		return null;
	}

	private IdentityDTO getIdentityDTO(ReturnFieldsDTO entity) {
		IdentityDTO identity = null;
		identity = new IdentityDTO();
		identity.setIdentifier(entity.getIdentifier());
		identity.setDomain(entity.getDomain());
		identity.setEntityTemplate(entity.getEntityTemplate());
		identity.setPlatformEntity(entity.getPlatformEntity());
		return identity;
	}

}
