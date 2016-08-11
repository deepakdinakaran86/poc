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
package com.pcs.avocado.serviceImpl;

import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getContext;
import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.getJwtToken;
import static com.pcs.avocado.commons.dto.SubscriptionContextHolder.isSuperAdmin;
import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.NOT_A_ORG_SUPER_ADMIN_USER;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateResult;
import static com.pcs.avocado.constans.WebConstants.ACTIVE;
import static com.pcs.avocado.constans.WebConstants.ALLOCATED;
import static com.pcs.avocado.constans.WebConstants.ASSET_LOC_MODE;
import static com.pcs.avocado.constans.WebConstants.ASSET_MODE;
import static com.pcs.avocado.constans.WebConstants.CONFIGURATION;
import static com.pcs.avocado.constans.WebConstants.DATASOURCE_NAME;
import static com.pcs.avocado.constans.WebConstants.DATASOURCE_NAME_FIELD;
import static com.pcs.avocado.constans.WebConstants.DATA_TYPE;
import static com.pcs.avocado.constans.WebConstants.DEVICENAME;
import static com.pcs.avocado.constans.WebConstants.DEVICE_IP;
import static com.pcs.avocado.constans.WebConstants.DEVICE_PORT;
import static com.pcs.avocado.constans.WebConstants.DEVICE_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.DEVICE_TYPE;
import static com.pcs.avocado.constans.WebConstants.DISPLAY_NAME;
import static com.pcs.avocado.constans.WebConstants.ENTITY_NAME;
import static com.pcs.avocado.constans.WebConstants.EQUIP_IDENTIFIER;
import static com.pcs.avocado.constans.WebConstants.EQUIP_NAME;
import static com.pcs.avocado.constans.WebConstants.EQUIP_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.GEO_POINT_FIELD;
import static com.pcs.avocado.constans.WebConstants.GMTOFFSET;
// import static com.pcs.avocado.constans.WebConstants.IDENTIFIER;
import static com.pcs.avocado.constans.WebConstants.INACTIVE;
import static com.pcs.avocado.constans.WebConstants.LATITUDE;
import static com.pcs.avocado.constans.WebConstants.LONGITUDE;
import static com.pcs.avocado.constans.WebConstants.MAKE;
import static com.pcs.avocado.constans.WebConstants.MARKER;
import static com.pcs.avocado.constans.WebConstants.MODEL;
import static com.pcs.avocado.constans.WebConstants.NETWORK_PROTOCOL;
import static com.pcs.avocado.constans.WebConstants.PASSWORD;
import static com.pcs.avocado.constans.WebConstants.POINT_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.PROTOCOL;
import static com.pcs.avocado.constans.WebConstants.PUBLISH;
import static com.pcs.avocado.constans.WebConstants.SAF_LOCATION;
import static com.pcs.avocado.constans.WebConstants.SLOT;
import static com.pcs.avocado.constans.WebConstants.TAGS;
import static com.pcs.avocado.constans.WebConstants.TENANT;
import static com.pcs.avocado.constans.WebConstants.TENANT_ID;
import static com.pcs.avocado.constans.WebConstants.TENANT_TEMPLATE;
import static com.pcs.avocado.constans.WebConstants.TIMEZONE;
import static com.pcs.avocado.constans.WebConstants.URL;
import static com.pcs.avocado.constans.WebConstants.USERNAME;
import static com.pcs.avocado.constans.WebConstants.VERSION;
import static com.pcs.avocado.constans.WebConstants.WRITEBACK_PORT;
import static com.pcs.avocado.constant.CommonConstants.ASSET_NAME;
import static com.pcs.avocado.constant.CommonConstants.ASSET_TEMPLATE;
import static com.pcs.avocado.constant.CommonConstants.ASSET_TYPE;
import static com.pcs.avocado.enums.CommonFields.ACTOR;
import static com.pcs.avocado.enums.CommonFields.IDENTIFIER;
import static com.pcs.avocado.enums.CommonFields.IDENTIFIER_KEY;
import static com.pcs.avocado.enums.CommonFields.IDENTIFIER_VALUE;
import static com.pcs.avocado.enums.CommonFields.SUBJECTS;
import static com.pcs.avocado.enums.DeviceDataFields.CLIENT;
import static com.pcs.avocado.enums.DeviceDataFields.CLINT_DEVICES;
import static com.pcs.avocado.enums.DeviceDataFields.DATA_SOURCES_LIST;
import static com.pcs.avocado.enums.DeviceDataFields.DEVICES;
import static com.pcs.avocado.enums.DeviceDataFields.DEVICE_MAKE;
import static com.pcs.avocado.enums.DeviceDataFields.DEVICE_MODEL;
import static com.pcs.avocado.enums.DeviceDataFields.DEVICE_NAME;
import static com.pcs.avocado.enums.DeviceDataFields.DEVICE_PROTOCOL;
import static com.pcs.avocado.enums.DeviceDataFields.DEVICE_VERSION;
import static com.pcs.avocado.enums.DeviceDataFields.DOMAIN;
import static com.pcs.avocado.enums.DeviceDataFields.ENTITY_TEMPLATE;
import static com.pcs.avocado.enums.DeviceDataFields.MODE;
import static com.pcs.avocado.enums.DeviceDataFields.NW_PROTOCOL;
import static com.pcs.avocado.enums.DeviceDataFields.OWNER;
import static com.pcs.avocado.enums.DeviceDataFields.PARENT;
import static com.pcs.avocado.enums.DeviceDataFields.PLATFORM_ENTITY;
import static com.pcs.avocado.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.avocado.enums.DeviceDataFields.TYPE;
import static com.pcs.avocado.enums.Status.FAILURE;
import static com.pcs.avocado.enums.Status.SUCCESS;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pcs.avocado.commons.dto.DeviceLocationDTO;
import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityAssignDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.HierarchyDTO;
import com.pcs.avocado.commons.dto.HierarchySearchDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.LatestDataResultDTO;
import com.pcs.avocado.commons.dto.LatestDataSearchDTO;
import com.pcs.avocado.commons.dto.PlatformEntityDTO;
import com.pcs.avocado.commons.dto.ReturnFieldsDTO;
import com.pcs.avocado.commons.dto.SearchFieldsDTO;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.dto.StatusMessageErrorDTO;
import com.pcs.avocado.commons.dto.Subscription;
import com.pcs.avocado.commons.dto.SubscriptionContextHolder;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.validation.ValidationUtils;
import com.pcs.avocado.constans.WebConstants;
import com.pcs.avocado.dto.AlarmDataResponse;
import com.pcs.avocado.dto.AlarmMessage;
import com.pcs.avocado.dto.ClaimDeviceDTO;
import com.pcs.avocado.dto.ClientDeviceDTO;
import com.pcs.avocado.dto.Device;
import com.pcs.avocado.dto.DeviceLocationParam;
import com.pcs.avocado.dto.DeviceSaffron;
import com.pcs.avocado.dto.DeviceTag;
import com.pcs.avocado.dto.DeviceVersion;
import com.pcs.avocado.dto.ESBDeleteDevice;
import com.pcs.avocado.dto.ESBDevice;
import com.pcs.avocado.dto.EntityRangeDTO;
import com.pcs.avocado.dto.GeneralBatchResponse;
import com.pcs.avocado.dto.GeneralResponse;
import com.pcs.avocado.dto.GensetAlarmHistoryDTO;
import com.pcs.avocado.dto.NetworkProtocol;
import com.pcs.avocado.dto.OwnerDeviceDTO;
import com.pcs.avocado.dto.SearchDTO;
import com.pcs.avocado.dto.TemplateAssign;
import com.pcs.avocado.enums.DeviceDataFields;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.rest.exception.GalaxyRestException;
import com.pcs.avocado.services.DeviceService;

/**
 * Service Implementation for Device Related APIs
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date January 2016
 * @since Avocado-1.0.0
 */
@Service
public class DeviceServiceImpl implements DeviceService {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DeviceServiceImpl.class);

	@Autowired
	@Qualifier("avocadoEsbClient")
	private BaseClient esbClient;

	@Autowired
	@Qualifier("platformEsbClient")
	private BaseClient platformClient;

	@Autowired
	@Qualifier("saffronClient")
	private BaseClient saffronClient;

	@Value("${avocado.esb.device.create}")
	private String createDeviceESBEndpoint;

	@Value("${avocado.esb.device.update}")
	private String updateDeviceESBEndpoint;

	@Value("${avocado.esb.device.claim}")
	private String claimDeviceESBEndpoint;

	@Value("${avocado.esb.device.delete}")
	private String deleteDeviceESBEndpoint;

	@Value("${platform.esb.marker}")
	private String markerPlatformEndpoint;

	@Value("${platform.esb.hierarchy.attach.parents}")
	private String attachHierarchyParentEndpoint;

	@Value("${platform.esb.marker.find}")
	private String findMarkerPlatformEndpoint;

	@Value("${platform.esb.marker.list}")
	private String listMarkersPlatformEndpoint;

	@Value("${platform.esb.hierarchy.parents.immediate}")
	private String hierarchyParentsEndpoint;

	@Value("${platform.esb.marker.fields.search}")
	private String markerPlatformSearchFieldsEndpoint;

	@Value("${saffron.as.data.latest}")
	private String latestLocationEndpoint;

	@Value("${datasource.alarms.history}")
	private String alarmsHistoryDatasourceEndpoint;

	@Value("${datasource.alarms.latest}")
	private String alarmsLiveDatasourceEndpoint;

	@Value("${platform.esb.hierarchy.owned}")
	private String hierarchyOwnedDevicesEndpoint;

	@Value("${platform.esb.hierarchy.children.immediate}")
	private String hierarchyChildrenEndpoint;

	@Value("${saffron.as.device.claim}")
	private String claimDeviceSaffronEndpoint;

	@Value("${platform.esb.hierarchy.range}")
	private String getHierarchyRangeEndpoint;

	private static String TRUE_STRING = "true";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.avocado.services.DeviceService#createDevice(com.pcs.avocado.dto
	 * .Device)
	 */
	@Override
	public StatusMessageDTO createDevice(Device device) {

		if (!isSuperAdmin()) {
			throw new GalaxyException(NOT_A_ORG_SUPER_ADMIN_USER);
		}

		// set Datasource name as sourceId as default nature
		device.setIsPublish(true);
		device.setDsName(device.getSourceId());

		DeviceSaffron saffronDevice = createSaffronDevice(device);
		ESBDevice esbDevice = new ESBDevice();
		esbDevice.setSaffronDevice(saffronDevice);
		EntityDTO alpineDevice = constructAlpineDevice(device, false);
		esbDevice.setAlpineDevice(alpineDevice);
		EntityDTO entityDTO = null;
		GeneralBatchResponse response = null;
		if (isNotEmpty(device.getConfiguration())) {
			esbDevice.setAssignTemplate(getAssignTemplatePayload(device));
			response = esbClient.post(createDeviceESBEndpoint, getJwtToken(),
			        esbDevice, GeneralBatchResponse.class);
		} else {
			entityDTO = esbClient.post(createDeviceESBEndpoint, getJwtToken(),
			        esbDevice, EntityDTO.class);
		}

		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(FAILURE);
		if ((entityDTO != null && entityDTO.getIdentifier() != null)) {
			statusMessageDTO.setStatus(SUCCESS);
		} else if (response != null) {
			List<GeneralResponse> responses = response.getResponses();
			if (isNotEmpty(responses)) {
				GeneralResponse generalResponse = responses.get(0);
				if (generalResponse.getStatus().equals(SUCCESS)) {
					statusMessageDTO.setStatus(SUCCESS);
				}
			} else {
				LOGGER.error("Error in Assigning Template : ", responses);
			}

		}
		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.avocado.services.DeviceService#updateDevice(java.lang.String,
	 * com.pcs.avocado.dto.Device)
	 */
	@Override
	public StatusMessageDTO updateDevice(String sourceId, Device device) {
		DeviceSaffron saffronDevice = createSaffronDevice(device);
		ESBDevice esbDevice = new ESBDevice();
		esbDevice.setSaffronDevice(saffronDevice);
		device.setSourceId(sourceId);
		EntityDTO alpineDevice = constructAlpineDevice(device, true);
		esbDevice.setAlpineDevice(alpineDevice);
		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(IDENTIFIER.getFieldName());
		identifier.setValue(device.getIdentifier());
		alpineDevice.setIdentifier(identifier);

		// TODO to change the implementation as it will not work with subtenants
		if (!isSuperAdmin()) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(getContext().getSubscription()
			        .getEndUserDomain());
			alpineDevice.setDomain(domain);
		}

		if (TRUE.toString().equalsIgnoreCase(device.getAllocated())) {
			esbDevice.setUpdateHierarchy(true);
		}
		StatusMessageDTO statuMessageDTO = null;
		GeneralBatchResponse response = null;
		String updateEndpoint = updateDeviceESBEndpoint.replace("{sourceId}",
		        sourceId);
		if (isNotEmpty(device.getConfiguration())) {
			esbDevice.setAssignTemplate(getAssignTemplatePayload(device));
			response = esbClient.put(updateEndpoint, getJwtToken(), esbDevice,
			        GeneralBatchResponse.class);
		} else {
			statuMessageDTO = esbClient.put(updateEndpoint, getJwtToken(),
			        esbDevice, StatusMessageDTO.class);
		}

		if (response != null) {
			List<GeneralResponse> responses = response.getResponses();
			if (isNotEmpty(responses)) {
				GeneralResponse generalResponse = responses.get(0);
				if (generalResponse.getStatus().equals(SUCCESS)) {
					statuMessageDTO = new StatusMessageDTO(SUCCESS);
				}
			}
			LOGGER.error("Error in Assigning Template : ", responses);

		}
		if (statuMessageDTO == null) {
			statuMessageDTO = new StatusMessageDTO(FAILURE);
		}
		return statuMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.avocado.services.DeviceService#claimDevice(java.lang.String,
	 * com.pcs.avocado.dto.Device)
	 */
	@Override
	public StatusMessageDTO claimDevice(String sourceId, Device device) {

		// set Datasource name as sourceId as default nature
		device.setIsPublish(true);
		device.setDsName(device.getSourceId());
		DeviceSaffron saffronDevice = createSaffronDevice(device);
		ESBDevice esbDevice = new ESBDevice();
		esbDevice.setSaffronDevice(saffronDevice);
		device.setSourceId(sourceId);
		EntityDTO alpineDevice = constructAlpineDevice(device, false);
		esbDevice.setAlpineDevice(alpineDevice);
		EntityDTO entityDTO = null;
		GeneralBatchResponse response = null;
		String claimEndpoint = claimDeviceESBEndpoint.replace("{sourceId}",
		        sourceId);
		if (isNotEmpty(device.getConfiguration())) {
			esbDevice.setAssignTemplate(getAssignTemplatePayload(device));
			response = esbClient.put(claimEndpoint, getJwtToken(), esbDevice,
			        GeneralBatchResponse.class);
		} else {
			entityDTO = esbClient.put(claimEndpoint, getJwtToken(), esbDevice,
			        EntityDTO.class);
		}
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO(FAILURE);
		if ((entityDTO != null && entityDTO.getIdentifier() != null)) {
			statusMessageDTO.setStatus(SUCCESS);
		} else if (response != null) {
			List<GeneralResponse> responses = response.getResponses();
			if (isNotEmpty(responses)) {
				GeneralResponse generalResponse = responses.get(0);
				if (generalResponse.getStatus().equals(SUCCESS)) {
					statusMessageDTO.setStatus(SUCCESS);
				}
			} else {
				LOGGER.error("Error in Assigning Template : ", responses);
			}
		}
		return statusMessageDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.avocado.services.DeviceService#claimDevice(java.lang.String,
	 * com.pcs.avocado.dto.Device)
	 */
	@Override
	public StatusMessageDTO claimDevices(ClaimDeviceDTO claimDevice) {
		validateMandatoryFields(claimDevice, DEVICES, CLIENT);
		if (!isSuperAdmin()) {
			throw new GalaxyException(GalaxyCommonErrorCodes.NO_ACCESS);
		}
		List<EntityDTO> deviceEntities = new ArrayList<EntityDTO>();
		StatusMessageDTO status = new StatusMessageDTO(SUCCESS);
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(getContext().getSubscription().getEndUserDomain());
		for (Device device : claimDevice.getDevices()) {
			validateMandatoryFields(device, SOURCE_ID, DEVICE_NAME,
			        DEVICE_MAKE, TYPE, DEVICE_MODEL, DEVICE_PROTOCOL,
			        DEVICE_VERSION, NW_PROTOCOL);
			device.setAllocated(TRUE_STRING);
			String claimDeviceSaffron = claimDeviceSaffronEndpoint.replace(
			        "{source_id}", device.getSourceId());
			try {
				saffronClient.put(claimDeviceSaffron, getJwtToken(), null,
				        StatusMessageDTO.class);
			} catch (Exception e) {
				LOGGER.error(
				        "Error while claim device in saffron : {}, Reason : {}",
				        device.getSourceId(), e.getMessage());
				continue;
			}

			EntityDTO alpineDevice = constructAlpineDevice(device, true);
			alpineDevice.setDomain(domain);
			EntityDTO deviceResponse = null;
			try {
				deviceResponse = esbClient.post(markerPlatformEndpoint,
				        getJwtToken(), alpineDevice, EntityDTO.class);
			} catch (Exception e) {
				LOGGER.error(
				        "Error while create device in alpine : {}, Reason : {}",
				        device.getSourceId(), e.getMessage());
			}
			if (deviceResponse != null) {
				deviceEntities.add(deviceResponse);
			}
		}
		IdentityDTO owner = getIdentityFromEntity(claimDevice.getClient());
		List<IdentityDTO> parents = getParentsOfHierarchy(owner, null);

		if (CollectionUtils.isNotEmpty(deviceEntities)) {
			if (CollectionUtils.isNotEmpty(parents)) {
				// No need to assign super tenant. (ex : PCS)
				parents.remove(0);
				for (IdentityDTO identtiy : parents) {
					EntityDTO parent = getEntityFromIdentity(identtiy);
					HierarchyDTO hierarchy = new HierarchyDTO();
					hierarchy.setActor(parent);
					hierarchy.setSubjects(deviceEntities);
					try {
						platformClient.post(attachHierarchyParentEndpoint,
						        getJwtToken(), hierarchy,
						        StatusMessageDTO.class);
					} catch (Exception e) {
						LOGGER.error(
						        "Error while assign devices to clients : {}",
						        e.getMessage());
						throw e;
					}
				}
			}
		} else {
			status.setStatus(FAILURE);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.avocado.services.DeviceService#assignDevice(com.pcs.avocado.commons
	 * .dto.HierarchyDTO)
	 */
	@Override
	public StatusMessageDTO assignBulkDevice(OwnerDeviceDTO ownerDevices) {
		StatusMessageDTO status = new StatusMessageDTO(Status.SUCCESS);
		validateMandatoryFields(ownerDevices, CLINT_DEVICES, OWNER);
		IdentityDTO owner = ownerDevices.getOwner();
		HashMap<String, IdentityDTO> identityMalp = new HashMap<>();
		HashMap<String, List<EntityDTO>> clientDeivceMalp = new HashMap<>();
		for (ClientDeviceDTO clientDevices : ownerDevices.getClientDevices()) {
			validateMandatoryFields(clientDevices, DEVICES, PARENT);
			List<EntityDTO> devices = updateDeviceAssigned(clientDevices
			        .getDevices());
			if (CollectionUtils.isNotEmpty(devices)) {
				IdentityDTO parent = clientDevices.getParent();
				List<IdentityDTO> parents = getParentsOfHierarchy(owner, parent);
				if (CollectionUtils.isNotEmpty(parents)) {
					// No need to assign own devices.
					parents.remove(0);
					for (IdentityDTO identityDTO : parents) {
						String key = identityDTO.getIdentifier().getValue();
						if (identityMalp.containsKey(key)) {
							List<EntityDTO> list = clientDeivceMalp.get(key);
							List<EntityDTO> deviceList = new ArrayList<EntityDTO>();
							deviceList.addAll(devices);
							list.addAll(deviceList);
						} else {
							identityMalp.put(key, identityDTO);
							List<EntityDTO> deviceList = new ArrayList<EntityDTO>();
							deviceList.addAll(devices);
							clientDeivceMalp.put(key, deviceList);
						}
					}
				}
			}
		}
		for (String clientId : clientDeivceMalp.keySet()) {

			List<EntityDTO> deviceList = clientDeivceMalp.get(clientId);

			IdentityDTO identityDTO = identityMalp.get(clientId);

			HierarchyDTO hierarchy = new HierarchyDTO();
			hierarchy.setActor(getEntityFromIdentity(identityDTO));
			hierarchy.setSubjects(deviceList);
			try {
				platformClient.post(attachHierarchyParentEndpoint,
				        getJwtToken(), hierarchy, StatusMessageDTO.class);
			} catch (Exception e) {
				LOGGER.error("Error while assign devices to clients : {}",
				        e.getMessage());
				throw e;
			}
		}
		return status;
	}

	private List<EntityDTO> updateDeviceAssigned(List<EntityDTO> entities) {
		List<EntityDTO> deviceList = new ArrayList<EntityDTO>();
		for (EntityDTO entityDTO : entities) {
			IdentityDTO identity = getIdentityFromEntity(entityDTO);
			EntityDTO device = null;
			try {
				device = platformClient.post(findMarkerPlatformEndpoint,
				        getJwtToken(), identity, EntityDTO.class);
			} catch (Exception e) {
				LOGGER.error("Error while fetching device : " + e.getMessage());
			}
			if (device != null) {
				deviceList.add(device);
				List<FieldMapDTO> fieldValues = device.getFieldValues();
				if (CollectionUtils.isNotEmpty(fieldValues)) {
					FieldMapDTO fieldMapDTO = new FieldMapDTO();
					fieldMapDTO.setKey(ALLOCATED);
					fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
					if (StringUtils.isNotEmpty(fieldMapDTO.getValue())) {
						if (Boolean.TRUE.toString().equals(
						        fieldMapDTO.getValue())) {
							continue;
						}
					}
					fieldValues.remove(fieldMapDTO);
					fieldMapDTO.setValue(Boolean.TRUE.toString());
					fieldValues.add(fieldMapDTO);
					platformClient.put(markerPlatformEndpoint, getJwtToken(),
					        device, StatusMessageDTO.class);
				}
			}
		}
		return deviceList;
	}

	private IdentityDTO getIdentityFromEntity(EntityDTO entity) {
		IdentityDTO identity = null;
		if (entity != null) {
			identity = new IdentityDTO();
			identity.setDomain(entity.getDomain());
			identity.setIdentifier(entity.getIdentifier());
			identity.setPlatformEntity(entity.getPlatformEntity());
			identity.setEntityTemplate(entity.getEntityTemplate());
		}
		return identity;
	}

	private EntityDTO getEntityFromIdentity(IdentityDTO identity) {
		EntityDTO entity = null;
		if (identity != null) {
			entity = new EntityDTO();
			entity.setDomain(identity.getDomain());
			entity.setIdentifier(identity.getIdentifier());
			entity.setPlatformEntity(identity.getPlatformEntity());
			entity.setEntityTemplate(identity.getEntityTemplate());
		}
		return entity;
	}

	private List<IdentityDTO> getParentsOfHierarchy(IdentityDTO owner,
	        IdentityDTO parent) {
		List<IdentityDTO> parents = null;
		EntityRangeDTO range = new EntityRangeDTO();
		range.setStartEntity(parent);
		range.setEndEntity(owner);
		try {
			parents = esbClient.post(getHierarchyRangeEndpoint, getJwtToken(),
			        range, List.class, IdentityDTO.class);
		} catch (Exception e) {
			LOGGER.error("Error while fetching hierarchy Range : "
			        + e.getMessage());
		}
		return parents;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.avocado.services.DeviceService#assignDevice(com.pcs.avocado.commons
	 * .dto.HierarchyDTO)
	 */
	@Override
	public GeneralBatchResponse assignDevice(HierarchyDTO hierarchy) {

		validateMandatoryFields(hierarchy, ACTOR, SUBJECTS);

		// Set entity template as tenant template
		EntityTemplateDTO tenantTemplate = new EntityTemplateDTO();
		tenantTemplate.setEntityTemplateName(TENANT_TEMPLATE);
		hierarchy.getActor().setEntityTemplate(tenantTemplate);

		// Set actor type as TENANT
		PlatformEntityDTO tenantType = new PlatformEntityDTO();
		tenantType.setPlatformEntityType(TENANT);
		hierarchy.getActor().setPlatformEntity(tenantType);

		GeneralBatchResponse batchResponse = new GeneralBatchResponse();
		List<GeneralResponse> responses = new ArrayList<GeneralResponse>();
		batchResponse.setResponses(responses);

		PlatformEntityDTO markerType = new PlatformEntityDTO();
		markerType.setPlatformEntityType(MARKER);

		EntityTemplateDTO deviceTemplate = new EntityTemplateDTO();
		deviceTemplate.setEntityTemplateName(DEVICE_TEMPLATE);

		List<EntityDTO> hierarchySubjects = new ArrayList<EntityDTO>();
		boolean hasError = false;
		for (EntityDTO subject : hierarchy.getSubjects()) {

			FieldMapDTO identifier = subject.getIdentifier();
			GeneralResponse response = new GeneralResponse();
			responses.add(response);
			if (identifier != null) {
				String identifierValue = identifier.getValue();
				response.setReference(identifierValue);
				EntityDTO deviceEntity = null;
				try {
					deviceEntity = getDeviceDetails(identifierValue);
				} catch (Exception e) {
					LOGGER.info("Error in Fetching Device Details");
				}
				if (deviceEntity != null) {
					FieldMapDTO fieldMapDTO = new FieldMapDTO();
					fieldMapDTO.setKey(ALLOCATED);
					List<FieldMapDTO> fieldValues = deviceEntity
					        .getFieldValues();
					fieldMapDTO = fetchField(fieldValues, fieldMapDTO);
					fieldValues.remove(fieldMapDTO);
					fieldMapDTO.setValue(Boolean.TRUE.toString());
					fieldValues.add(fieldMapDTO);
					StatusMessageDTO updateAlpineDevice = platformClient.put(
					        markerPlatformEndpoint, getJwtToken(),
					        deviceEntity, StatusMessageDTO.class);
					if (updateAlpineDevice != null) {
						response.setStatus(updateAlpineDevice.getStatus());

						// Set subjects entity type and device template
						subject.setPlatformEntity(markerType);
						subject.setEntityTemplate(deviceTemplate);
						hierarchySubjects.add(subject);

						continue;
					} else {
						hasError = true;
						addFailureWithAReason(response,
						        "Error in Updating Device");
						continue;
					}
				} else {
					hasError = true;
					addFailureWithAReason(response, "Invalid identifier");
					continue;
				}
			} else {
				hasError = true;
				addFailureWithAReason(response, "No Identifier");
				continue;
			}

		}
		hierarchy.setSubjects(hierarchySubjects);

		GeneralResponse createHierarchyRes = new GeneralResponse();
		createHierarchyRes.setReference("Hierarchy Update");
		responses.add(createHierarchyRes);
		if (hasError) {
			createHierarchyRes.setStatus(FAILURE);
			createHierarchyRes.setRemarks("No hieararchy was created");
			return batchResponse;
		}

		StatusMessageDTO createHierarchy = platformClient.post(
		        attachHierarchyParentEndpoint, getJwtToken(), hierarchy,
		        StatusMessageDTO.class);

		if (createHierarchy == null) {
			createHierarchyRes.setStatus(FAILURE);
			createHierarchyRes.setRemarks("Error in Creating Hierarchy");
		} else {
			createHierarchyRes.setStatus(SUCCESS);
		}
		return batchResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.avocado.services.DeviceService#getDeviceDetails(java.lang.String)
	 */
	@Override
	public EntityDTO getDeviceDetails(String identifierValue) {

		FieldMapDTO field = new FieldMapDTO(IDENTIFIER.getFieldName(),
		        identifierValue);
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setIdentifier(field);
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(DEVICE_TEMPLATE);
		identityDto.setEntityTemplate(entityTemplateDTO);

		// TODO to change the implementation as it will not work with sub
		// tenants
		if (!isSuperAdmin()) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(getContext().getSubscription()
			        .getEndUserDomain());
			identityDto.setDomain(domain);
		}

		return platformClient.post(findMarkerPlatformEndpoint, getJwtToken(),
		        identityDto, EntityDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.avocado.services.DeviceService#getAllDevices(com.pcs.avocado.
	 * commons.dto.IdentityDTO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<EntityDTO> getAllDevices(IdentityDTO tenantIdentity) {

		HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
		hierarchySearchDTO.setSearchEntityType(MARKER);
		hierarchySearchDTO.setSearchTemplateName(DEVICE_TEMPLATE);
		hierarchySearchDTO.setActor(tenantIdentity);

		Subscription subscription = SubscriptionContextHolder.getContext()
		        .getSubscription();
		if (tenantIdentity != null) {
			validateMandatoryFields(tenantIdentity, DeviceDataFields.IDENTIFIER);

			// Set Platform entity type
			PlatformEntityDTO tenantEntityType = new PlatformEntityDTO();
			tenantEntityType.setPlatformEntityType(TENANT);
			tenantIdentity.setPlatformEntity(tenantEntityType);

			// // Set the domain
			// if (tenantIdentity.getDomain() != null
			// && isBlank(tenantIdentity.getDomain().getDomainName())) {
			// DomainDTO domain = new DomainDTO();
			// domain.setDomainName(subscription.getEndUserDomain());
			// }

		} else if (isSuperAdmin()) {
			// Check if superAdmin to get the markers of a domain of
			// Device
			EntityDTO entityDTO = new EntityDTO();
			entityDTO.setEntityTemplate(getDeviceTemplate());
			return platformClient.post(listMarkersPlatformEndpoint,
			        getJwtToken(), entityDTO, List.class, EntityDTO.class);
		} else {
			// If not a super admin , get parents of type
			// Device
			tenantIdentity = new IdentityDTO();
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(subscription.getEndUserDomain());
			tenantIdentity.setDomain(domain);
			tenantIdentity.setEntityTemplate(getDeviceTemplate());
			PlatformEntityDTO platformEntity = new PlatformEntityDTO();
			platformEntity.setPlatformEntityType(TENANT);
			tenantIdentity.setPlatformEntity(platformEntity);
			FieldMapDTO identifier = new FieldMapDTO(TENANT_ID,
			        subscription.getTenantId());
			tenantIdentity.setIdentifier(identifier);
			hierarchySearchDTO.setActor(tenantIdentity);
		}

		return platformClient.post(hierarchyParentsEndpoint, getJwtToken(),
		        hierarchySearchDTO, List.class, EntityDTO.class);
	}

	@Override
	public List<DeviceLocationDTO> getDevicesLocation(String domainName,
	        String locationMode) {
		return getLocation(domainName, locationMode);
	}

	// @SuppressWarnings("unchecked")
	// private void getLatLng(String domainName, EntityDTO entityDTO,
	// DeviceLocationDTO deviceLocation) {
	// HierarchySearchDTO hierarchySearchDTO = new HierarchySearchDTO();
	// hierarchySearchDTO.setSearchEntityType(MARKER);
	// hierarchySearchDTO.setSearchTemplateName(POINT_TEMPLATE);
	// IdentityDTO identity = new IdentityDTO();
	//
	// identity.setEntityTemplate(entityDTO.getEntityTemplate());
	// PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
	// platformEntityDTO.setPlatformEntityType(MARKER);
	// identity.setPlatformEntity(platformEntityDTO);
	//
	// DomainDTO domain = new DomainDTO();
	// domain.setDomainName(domainName);
	// identity.setDomain(domain);
	// identity.setIdentifier(entityDTO.getIdentifier());
	//
	// hierarchySearchDTO.setActor(identity);
	// deviceLocation.setTemplateName(entityDTO.getEntityTemplate()
	// .getEntityTemplateName());
	//
	// List<EntityDTO> points = new ArrayList<EntityDTO>();
	//
	// points = platformClient.post(hierarchyChildrenEndpoint, getJwtToken(),
	// hierarchySearchDTO, List.class, EntityDTO.class);
	//
	// HierarchySearchDTO hierarchySearch = new HierarchySearchDTO();
	// hierarchySearch.setSearchEntityType(MARKER);
	// hierarchySearch.setSearchTemplateName(DEVICE_TEMPLATE);
	// IdentityDTO pointId = new IdentityDTO();
	//
	// EntityTemplateDTO pointTemplate = new EntityTemplateDTO();
	// pointTemplate.setEntityTemplateName(POINT_TEMPLATE);
	// pointId.setEntityTemplate(pointTemplate);
	// PlatformEntityDTO pointMarker = new PlatformEntityDTO();
	// pointMarker.setPlatformEntityType(MARKER);
	// pointId.setPlatformEntity(pointMarker);
	//
	// pointId.setDomain(domain);
	// pointId.setIdentifier(points.get(0).getIdentifier());
	//
	// hierarchySearch.setActor(pointId);
	//
	// List<EntityDTO> device = platformClient.post(hierarchyParentsEndpoint,
	// getJwtToken(), hierarchySearch, List.class, EntityDTO.class);
	// FieldMapDTO latField = new FieldMapDTO();
	// latField.setKey(LATITUDE);
	// FieldMapDTO lat = fetchField(device.get(0).getDataprovider(), latField);
	// deviceLocation.setLatitude(lat.getValue());
	//
	// FieldMapDTO lngField = new FieldMapDTO();
	// lngField.setKey(LONGITUDE);
	// FieldMapDTO lng = fetchField(device.get(0).getDataprovider(), lngField);
	// deviceLocation.setLongitude(lng.getValue());
	//
	// FieldMapDTO dsMap = new FieldMapDTO();
	// dsMap.setKey(DATASOURCE_NAME);
	// dsMap = fetchField(device.get(0).getDataprovider(), dsMap);
	// deviceLocation.setDatasourceName(dsMap.getValue());
	//
	// }

	@SuppressWarnings("unchecked")
	private List<EntityDTO> getAssets(String domainName, String assetTemplate) {

		EntityDTO entityDTO = new EntityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		entityDTO.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(assetTemplate);

		entityDTO.setDomain(domain);
		entityDTO.setEntityTemplate(entityTemplate);
		List<EntityDTO> assets = new ArrayList<EntityDTO>();

		try {
			assets = platformClient.post(listMarkersPlatformEndpoint,
			        getJwtToken(), entityDTO, List.class, EntityDTO.class);
		} catch (GalaxyRestException galaxyException) {
			if (!(galaxyException.getCode()
			        .equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()))) {
				throw galaxyException;
			}
			LOGGER.debug("exception", galaxyException);
		}
		return assets;
	}

	@SuppressWarnings("unchecked")
	private List<ReturnFieldsDTO> getPointSearchFields(String domainName,
	        String locationType) {
		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		// Set point template
		EntityTemplateDTO searchTemplate = new EntityTemplateDTO();
		searchTemplate.setEntityTemplateName(POINT_TEMPLATE);
		searchFieldsDTO.setEntityTemplate(searchTemplate);

		// Set search fields, locationType can be either LATITUDE or LONGIUDE
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(DATA_TYPE);
		fieldMapDTO.setValue(locationType);

		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(fieldMapDTO);
		searchFieldsDTO.setSearchFields(searchFields);

		// Set the return fields
		List<String> returnFields = new ArrayList<String>();
		returnFields.add(DATASOURCE_NAME_FIELD);
		returnFields.add(EQUIP_NAME);
		returnFields.add(EQUIP_IDENTIFIER);
		returnFields.add(EQUIP_TEMPLATE);
		returnFields.add(DISPLAY_NAME);
		returnFields.add(DATA_TYPE);
		searchFieldsDTO.setReturnFields(returnFields);

		// Set the domain
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		searchFieldsDTO.setDomain(domain);

		// Get the datasource name and equip fields
		List<ReturnFieldsDTO> pointList = new ArrayList<ReturnFieldsDTO>();

		try {
			// Get points having data type as latitude
			pointList = platformClient.post(markerPlatformSearchFieldsEndpoint,
			        getJwtToken(), searchFieldsDTO, List.class,
			        ReturnFieldsDTO.class);
		} catch (GalaxyRestException galaxyException) {
			if (!(galaxyException.getCode()
			        .equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()))) {
				LOGGER.debug("exception", galaxyException);
				throw galaxyException;
			}
			// If latitude point not available, then it will be fetched from the
			// device demographics
		}
		return pointList;
	}

	private void addFailureWithAReason(GeneralResponse response, String remarks) {
		response.setStatus(FAILURE);
		response.setRemarks(remarks);
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
	        FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	private TemplateAssign getAssignTemplatePayload(Device device) {
		TemplateAssign templateAssign = new TemplateAssign();
		templateAssign.setTemplateName(device.getConfiguration());
		List<String> devices = new ArrayList<String>();
		devices.add(device.getSourceId());
		templateAssign.setSourceIds(devices);
		return templateAssign;
	}

	private List<FieldMapDTO> createFieldMaps(Device device, boolean isUpdate) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(ENTITY_NAME, device.getSourceId()));
		fieldMaps.add(createFieldMap(CONFIGURATION, device.getConfiguration()));
		fieldMaps.add(createFieldMap(TAGS, device.getTags()));
		fieldMaps.add(createFieldMap(MAKE, device.getMake()));
		fieldMaps.add(createFieldMap(DEVICE_TYPE, device.getDeviceType()));
		fieldMaps.add(createFieldMap(MODEL, device.getModel()));
		fieldMaps.add(createFieldMap(PROTOCOL, device.getProtocol()));
		fieldMaps.add(createFieldMap(VERSION, device.getVersion()));
		fieldMaps.add(createFieldMap(NETWORK_PROTOCOL, device.getNwProtocol()));
		fieldMaps.add(createFieldMap(DEVICE_IP, device.getDeviceIp()));
		fieldMaps.add(createFieldMap(DEVICE_PORT, device.getDevicePort()));
		fieldMaps.add(createFieldMap(WRITEBACK_PORT, device.getWbPort()));
		fieldMaps.add(createFieldMap(DATASOURCE_NAME, device.getDsName()));
		fieldMaps.add(createFieldMap(LONGITUDE, device.getLongitude()));
		fieldMaps.add(createFieldMap(LATITUDE, device.getLatitude()));
		fieldMaps.add(createFieldMap(URL, device.getUrl()));
		fieldMaps.add(createFieldMap(SLOT, device.getSlot()));
		fieldMaps.add(createFieldMap(USERNAME, device.getUserName()));
		fieldMaps.add(createFieldMap(PASSWORD, device.getPassword()));
		fieldMaps.add(createFieldMap(TIMEZONE, device.getTimeZone()));
		fieldMaps.add(createFieldMap(GMTOFFSET, device.getGmtOffset()));
		fieldMaps.add(createFieldMap(DEVICENAME, device.getDeviceName()));
		if (isUpdate) {
			fieldMaps.add(createFieldMap(ALLOCATED, device.getAllocated()));
		} else {
			fieldMaps.add(createFieldMap(ALLOCATED, FALSE.toString()));
		}
		fieldMaps.add(createFieldMap(PUBLISH,
		        String.valueOf(device.getIsPublish())));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	private EntityDTO constructAlpineDevice(Device device, boolean isUpdate) {
		EntityDTO deviceAlpine = new EntityDTO();
		List<FieldMapDTO> fiedValues = createFieldMaps(device, isUpdate);
		deviceAlpine.setFieldValues(fiedValues);
		deviceAlpine.setEntityTemplate(getDeviceTemplate());
		StatusDTO status = new StatusDTO();
		if (device.getStatus()) {
			status.setStatusName(ACTIVE);
		} else {
			status.setStatusName(INACTIVE);
		}
		deviceAlpine.setEntityStatus(status);
		return deviceAlpine;
	}

	private DeviceSaffron createSaffronDevice(Device device) {

		DeviceSaffron deviceSaffron = new DeviceSaffron();
		deviceSaffron.setSourceId(device.getSourceId());
		deviceSaffron.setDeviceName(device.getDeviceName());

		DeviceVersion version = new DeviceVersion();
		version.setMake(device.getMake());
		version.setDeviceType(device.getDeviceType());
		version.setModel(device.getModel());
		version.setProtocol(device.getProtocol());
		version.setVersion(device.getVersion());
		deviceSaffron.setVersion(version);

		deviceSaffron.setIp(device.getDeviceIp());
		if (StringUtils.isNotBlank(device.getDevicePort())) {
			deviceSaffron.setConnectedPort(Integer.parseInt(device
			        .getDevicePort()));
		}
		if (StringUtils.isNotBlank(device.getWbPort())) {
			deviceSaffron
			        .setWriteBackPort(Integer.parseInt(device.getWbPort()));
		}
		if (StringUtils.isNotBlank(device.getDevicePort())) {
			deviceSaffron.setConnectedPort(Integer.parseInt(device
			        .getDevicePort()));
		}
		if (StringUtils.isNotBlank(device.getLongitude())) {
			deviceSaffron.setLongitude(device.getLongitude());
		}
		if (StringUtils.isNotBlank(device.getLatitude())) {
			deviceSaffron.setLatitude(device.getLatitude());
		}
		if (StringUtils.isNotBlank(device.getUrl())) {
			deviceSaffron.setUrl(device.getUrl());
		}
		if (StringUtils.isNotBlank(device.getUserName())) {
			deviceSaffron.setUserName(device.getUserName());
		}
		if (StringUtils.isNotBlank(device.getPassword())) {
			deviceSaffron.setPassword(device.getPassword());
		}
		if (StringUtils.isNotBlank(device.getTimeZone())) {
			deviceSaffron.setTimeZone(device.getTimeZone());
		}
		if (device.getGmtOffset() != null) {
			deviceSaffron.setGmtOffset(Integer.parseInt(device.getGmtOffset()));
		}

		deviceSaffron.setIsPublishing(device.getIsPublish());
		deviceSaffron.setDatasourceName(device.getDsName());

		NetworkProtocol nwProtocole = new NetworkProtocol();
		nwProtocole.setName(device.getNwProtocol());
		deviceSaffron.setNetworkProtocol(nwProtocole);

		String tagsString = device.getTags();
		if (isNotEmpty(tagsString)) {
			String[] tagsArray = tagsString.split(",");
			List<DeviceTag> tags = new ArrayList<DeviceTag>();
			for (String tagName : tagsArray) {
				if (isNotBlank(tagName)) {
					DeviceTag tag = new DeviceTag();
					tag.setName(tagName.trim());
					tags.add(tag);
				}
			}
			deviceSaffron.setTags(tags);
		}
		return deviceSaffron;
	}

	private EntityTemplateDTO getDeviceTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(DEVICE_TEMPLATE);
		return entityTemplateDTO;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GensetAlarmHistoryDTO> getLiveAlarms(String domainName) {

		// If domain is blank, get the domain from logged in user
		if (StringUtils.isBlank(domainName)) {
			domainName = getContext().getSubscription().getEndUserDomain();
		}

		EntityDTO entityDTO = setEntityDto(domainName);

		List<EntityDTO> pointEntityList = platformClient.post(
		        listMarkersPlatformEndpoint, getJwtToken(), entityDTO,
		        List.class, EntityDTO.class);

		Map<String, List<String>> equipMap = new HashMap<String, List<String>>();
		// HashMap<String, DeviceLocationParam> deviceParamMap = new
		// HashMap<String, DeviceLocationParam>();
		HashMap<String, List<DeviceLocationParam>> deviceParamMap2 = new HashMap<String, List<DeviceLocationParam>>();

		for (EntityDTO pointEntity : pointEntityList) {
			List<FieldMapDTO> dataprovider = pointEntity.getDataprovider();
			FieldMapDTO dataSourceFieldMap = new FieldMapDTO();
			FieldMapDTO displayNameFieldMap = new FieldMapDTO();
			FieldMapDTO sourceId = null;
			FieldMapDTO pointId = null;

			dataSourceFieldMap.setKey(WebConstants.DATASOURCE_NAME_FIELD);
			displayNameFieldMap.setKey(WebConstants.DISPLAY_NAME);

			// Extract the equip name
			FieldMapDTO equipNameMap = new FieldMapDTO();
			equipNameMap.setKey(EQUIP_NAME);
			equipNameMap = fetchField(dataprovider, equipNameMap);

			// Extract the equip template
			FieldMapDTO equipTemplateMap = new FieldMapDTO();
			equipTemplateMap.setKey(EQUIP_TEMPLATE);
			equipTemplateMap = fetchField(dataprovider, equipTemplateMap);

			// Extract the equip Id
			FieldMapDTO equipIdMap = new FieldMapDTO();
			equipIdMap.setKey(EQUIP_IDENTIFIER);
			equipIdMap = fetchField(dataprovider, equipIdMap);

			List<String> pointList = null;

			sourceId = fetchField(dataprovider, dataSourceFieldMap);
			pointId = fetchField(dataprovider, displayNameFieldMap);
			if (isBlank(sourceId.getValue()) || isBlank(pointId.getValue())) {
				continue;
			}

			if (isNotBlank(sourceId.getValue())
			        && equipMap.containsKey(sourceId.getValue())) {
				pointList = new ArrayList<String>();
				pointList = equipMap.get(sourceId.getValue());
				pointList.add(pointId.getValue());

				equipMap.put(sourceId.getValue(), pointList);
			} else {

				pointList = new ArrayList<String>();
				pointList.add(pointId.getValue());

				// add new sourceId, then add new pointId
				equipMap.put(sourceId.getValue(), pointList);
			}
			// Construct deviceparam payload
			// @SuppressWarnings("unused") DeviceLocationParam deviceParam =
			// constructDeviceParamPayload(
			// deviceParamMap, sourceId, equipNameMap, equipTemplateMap,
			// equipIdMap);
			@SuppressWarnings("unused") DeviceLocationParam deviceParam2 = constructDeviceParamPayload2(
			        deviceParamMap2, sourceId, equipNameMap, equipTemplateMap,
			        equipIdMap, pointId);
		}

		if (equipMap.isEmpty()) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}

		List<SearchDTO> alarmSearchDTOs = new ArrayList<>();

		for (String sourceId : equipMap.keySet()) {
			SearchDTO alarmSearchDTO = new SearchDTO();
			alarmSearchDTO.setSourceId(sourceId);
			alarmSearchDTO.setDisplayNames(equipMap.get(sourceId));
			alarmSearchDTOs.add(alarmSearchDTO);
		}

		// call saffron alarm history service
		List<AlarmDataResponse> alarmLatestResponse = saffronClient.post(
		        alarmsLiveDatasourceEndpoint, getJwtToken(), alarmSearchDTOs,
		        List.class, AlarmDataResponse.class);

		// List<GensetAlarmHistoryDTO> gensetLatestAlarmDTOs = getGensetAlarms(
		// deviceParamMap, alarmLatestResponse);
		List<GensetAlarmHistoryDTO> gensetLatestAlarmDTOs = getGensetAlarms2(
		        deviceParamMap2, alarmLatestResponse);
		return gensetLatestAlarmDTOs;
	}

	private DeviceLocationParam constructDeviceParamPayload(
	        HashMap<String, DeviceLocationParam> deviceParamMap,
	        FieldMapDTO sourceId, FieldMapDTO equipNameMap,
	        FieldMapDTO equipTemplateMap, FieldMapDTO equipIdMap) {
		DeviceLocationParam deviceParam = new DeviceLocationParam();
		deviceParam.setDatasourceName(sourceId.getValue());
		deviceParam.setEquipIdentifier(equipIdMap);
		deviceParam.setEquipName(equipNameMap.getValue());
		deviceParam.setEquipTemplate(equipTemplateMap.getValue());
		deviceParamMap.put(sourceId.getValue(), deviceParam);
		return deviceParam;
	}

	private DeviceLocationParam constructDeviceParamPayload2(
	        HashMap<String, List<DeviceLocationParam>> deviceParamMap,
	        FieldMapDTO sourceId, FieldMapDTO equipNameMap,
	        FieldMapDTO equipTemplateMap, FieldMapDTO equipIdMap,
	        FieldMapDTO pointId) {

		// for each source id : [ pointId with equipName and equipId]
		DeviceLocationParam deviceParam = new DeviceLocationParam();
		deviceParam.setDatasourceName(sourceId.getValue());
		deviceParam.setEquipIdentifier(equipIdMap);
		deviceParam.setEquipName(equipNameMap.getValue());
		deviceParam.setEquipTemplate(equipTemplateMap.getValue());
		deviceParam.setPointId(pointId.getValue());

		if (null != deviceParamMap.get(sourceId.getValue())) {
			List<DeviceLocationParam> list = deviceParamMap.get(sourceId
			        .getValue());
			list.add(deviceParam);
			deviceParamMap.put(sourceId.getValue(), list);
		} else {
			List<DeviceLocationParam> list = new ArrayList<DeviceLocationParam>();
			list.add(deviceParam);
			deviceParamMap.put(sourceId.getValue(), list);
		}
		return deviceParam;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GensetAlarmHistoryDTO> getAlarmHistory(String domainName,
	        Long startDate, Long endDate) {

		// If domain is blank, get the domain from logged in user
		if (StringUtils.isBlank(domainName)) {
			domainName = getContext().getSubscription().getEndUserDomain();
		}

		EntityDTO entityDTO = setEntityDto(domainName);

		List<EntityDTO> pointEntityList = platformClient.post(
		        listMarkersPlatformEndpoint, getJwtToken(), entityDTO,
		        List.class, EntityDTO.class);

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		HashMap<String, DeviceLocationParam> deviceParamMap = new HashMap<String, DeviceLocationParam>();

		HashMap<String, List<DeviceLocationParam>> deviceParamMap2 = new HashMap<String, List<DeviceLocationParam>>();

		for (EntityDTO pointEntity : pointEntityList) {
			List<FieldMapDTO> dataprovider = pointEntity.getDataprovider();
			FieldMapDTO dataSourceFieldMap = new FieldMapDTO();
			FieldMapDTO displayNameFieldMap = new FieldMapDTO();
			FieldMapDTO sourceId = null;
			FieldMapDTO pointId = null;

			dataSourceFieldMap.setKey(WebConstants.DATASOURCE_NAME_FIELD);
			displayNameFieldMap.setKey(WebConstants.DISPLAY_NAME);

			// Extract the equip name
			FieldMapDTO equipNameMap = new FieldMapDTO();
			equipNameMap.setKey(EQUIP_NAME);
			equipNameMap = fetchField(dataprovider, equipNameMap);

			// Extract the equip template
			FieldMapDTO equipTemplateMap = new FieldMapDTO();
			equipTemplateMap.setKey(EQUIP_TEMPLATE);
			equipTemplateMap = fetchField(dataprovider, equipTemplateMap);

			// Extract the equip Id
			FieldMapDTO equipIdMap = new FieldMapDTO();
			equipIdMap.setKey(EQUIP_IDENTIFIER);
			equipIdMap = fetchField(dataprovider, equipIdMap);

			List<String> pointList = null;

			sourceId = fetchField(dataprovider, dataSourceFieldMap);
			pointId = fetchField(dataprovider, displayNameFieldMap);
			if (isBlank(sourceId.getValue()) || isBlank(pointId.getValue())) {
				continue;
			}

			if (isNotBlank(sourceId.getValue())
			        && map.containsKey(sourceId.getValue())) {
				pointList = new ArrayList<String>();
				pointList = map.get(sourceId.getValue());
				pointList.add(pointId.getValue());

				map.put(sourceId.getValue(), pointList);
			} else {

				pointList = new ArrayList<String>();
				pointList.add(pointId.getValue());

				// add new sourceId, then add new pointId
				map.put(sourceId.getValue(), pointList);
			}
			// @SuppressWarnings("unused") DeviceLocationParam deviceParam =
			// constructDeviceParamPayload(
			// deviceParamMap, sourceId, equipNameMap, equipTemplateMap,
			// equipIdMap);

			@SuppressWarnings("unused") DeviceLocationParam deviceParam2 = constructDeviceParamPayload2(
			        deviceParamMap2, sourceId, equipNameMap, equipTemplateMap,
			        equipIdMap, pointId);
		}
		if (map.isEmpty()) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<SearchDTO> alarmSearchDTOs = new ArrayList<>();

		for (String sourceId : map.keySet()) {
			SearchDTO alarmSearchDTO = new SearchDTO();
			alarmSearchDTO.setSourceId(sourceId);
			alarmSearchDTO.setDisplayNames(map.get(sourceId));
			alarmSearchDTO.setEndDate(endDate);
			alarmSearchDTO.setStartDate(startDate);

			alarmSearchDTOs.add(alarmSearchDTO);
		}

		// call saffron alarm history service
		List<AlarmDataResponse> alarmHistoryResponse = saffronClient.post(
		        alarmsHistoryDatasourceEndpoint, getJwtToken(),
		        alarmSearchDTOs, List.class, AlarmDataResponse.class);

		List<GensetAlarmHistoryDTO> gensetAlarmHistoryDTOs = getGensetAlarms2(
		        deviceParamMap2, alarmHistoryResponse);
		return gensetAlarmHistoryDTOs;
	}

	private EntityDTO setEntityDto(String domainName) {
		EntityDTO entityDTO = new EntityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		entityDTO.setDomain(domain);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName(WebConstants.POINT_TEMPLATE);

		entityDTO.setDomain(domain);
		entityDTO.setEntityTemplate(entityTemplate);
		return entityDTO;
	}

	private List<GensetAlarmHistoryDTO> getGensetAlarms(
	        HashMap<String, DeviceLocationParam> deviceParamMap,
	        List<AlarmDataResponse> alarmHistoryResponse) {

		List<GensetAlarmHistoryDTO> gensetAlarmHistoryDTOs = new ArrayList<GensetAlarmHistoryDTO>();
		for (AlarmDataResponse alarmDataResponse : alarmHistoryResponse) {
			if (deviceParamMap.containsKey(alarmDataResponse.getSourceId())) {
				DeviceLocationParam deviceLocationParams = deviceParamMap
				        .get(alarmDataResponse.getSourceId());

				GensetAlarmHistoryDTO gensetAlarmHistoryDTO = new GensetAlarmHistoryDTO();
				gensetAlarmHistoryDTO.setSourceId(deviceLocationParams
				        .getDatasourceName());
				gensetAlarmHistoryDTO.setAlarmMessages(alarmDataResponse
				        .getAlarmMessages());
				gensetAlarmHistoryDTO.setEquipIdentifier(deviceLocationParams
				        .getEquipIdentifier());
				gensetAlarmHistoryDTO.setEquipName(deviceLocationParams
				        .getEquipName());
				gensetAlarmHistoryDTO.setEquipTemplate(deviceLocationParams
				        .getEquipTemplate());
				gensetAlarmHistoryDTOs.add(gensetAlarmHistoryDTO);
			}

		}

		return gensetAlarmHistoryDTOs;
	}

	private List<GensetAlarmHistoryDTO> getGensetAlarms2(
	        HashMap<String, List<DeviceLocationParam>> deviceParamMap,
	        List<AlarmDataResponse> alarmHistoryResponse) {

		List<GensetAlarmHistoryDTO> gensetAlarmHistoryDTOs = new ArrayList<GensetAlarmHistoryDTO>();

		for (AlarmDataResponse alarmDataResponse : alarmHistoryResponse) {
			if (deviceParamMap.containsKey(alarmDataResponse.getSourceId())) {

				List<AlarmMessage> alarmMessages = alarmDataResponse
				        .getAlarmMessages();

				Map<String, List<AlarmMessage>> alarmMessagesByDisplayNames = new HashMap<String, List<AlarmMessage>>();

				for (AlarmMessage alarmMessage : alarmMessages) {
					String displayName = alarmMessage.getDisplayName();

					if (null != alarmMessagesByDisplayNames.get(displayName)) {
						List<AlarmMessage> messages = alarmMessagesByDisplayNames
						        .get(displayName);
						messages.add(alarmMessage);
						alarmMessagesByDisplayNames.put(displayName, messages);
					} else {
						List<AlarmMessage> messages = new ArrayList<AlarmMessage>();
						messages.add(alarmMessage);
						alarmMessagesByDisplayNames.put(displayName, messages);
					}
				}

				List<DeviceLocationParam> deviceLocationParams = deviceParamMap
				        .get(alarmDataResponse.getSourceId());
				for (String displayName : alarmMessagesByDisplayNames.keySet()) {

					for (DeviceLocationParam deviceLocationParam : deviceLocationParams) {
						if (deviceLocationParam.getPointId()
						        .equals(displayName)) {

							GensetAlarmHistoryDTO gensetAlarmHistoryDTO = new GensetAlarmHistoryDTO();
							gensetAlarmHistoryDTO
							        .setSourceId(deviceLocationParam
							                .getDatasourceName());
							gensetAlarmHistoryDTO
							        .setAlarmMessages(alarmMessagesByDisplayNames
							                .get(displayName));
							gensetAlarmHistoryDTO
							        .setEquipIdentifier(deviceLocationParam
							                .getEquipIdentifier());
							gensetAlarmHistoryDTO
							        .setEquipName(deviceLocationParam
							                .getEquipName());
							gensetAlarmHistoryDTO
							        .setEquipTemplate(deviceLocationParam
							                .getEquipTemplate());
							gensetAlarmHistoryDTOs.add(gensetAlarmHistoryDTO);
							break;
						}
					}
				}

			}

		}

		return gensetAlarmHistoryDTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDTO> getAllOwnedDeviceByDomain(IdentityDTO identityDTO) {
		EntityAssignDTO entityAssignDTO = new EntityAssignDTO();
		validateMandatoryFields(identityDTO, DOMAIN, ENTITY_TEMPLATE,
		        PLATFORM_ENTITY, DeviceDataFields.IDENTIFIER);
		entityAssignDTO.setMarkerTemplateName(DEVICE_TEMPLATE);
		entityAssignDTO.setActor(identityDTO);
		return platformClient.post(hierarchyOwnedDevicesEndpoint,
		        getJwtToken(), entityAssignDTO, List.class, EntityDTO.class);
	}

	private List<DeviceLocationDTO> getLocation(String domainName, String mode) {
		// Validate get location
		validateGetLocation(domainName, mode);

		// Get points having dataType as GEOPOINT
		List<ReturnFieldsDTO> locationPoints = getAllLocationPoints(domainName);

		// Construct hashmap, with source id as key
		HashMap<String, DeviceLocationDTO> deviceParamMap = new HashMap<String, DeviceLocationDTO>();

		List<LatestDataSearchDTO> latestDataSearchList = new ArrayList<LatestDataSearchDTO>();

		for (ReturnFieldsDTO returnFieldsDTO : locationPoints) {

			// Extract the datasource name
			FieldMapDTO dataSourceNameMap = new FieldMapDTO();
			dataSourceNameMap.setKey(DATASOURCE_NAME_FIELD);
			String datasourceName = fetchField(
			        returnFieldsDTO.getReturnFields(), dataSourceNameMap)
			        .getValue();

			// Check if map contains the source id
			if (!deviceParamMap.containsKey(datasourceName)) {
				DeviceLocationDTO deviceLocation = constructDeviceLocation(
				        returnFieldsDTO.getReturnFields(), domainName);
				deviceParamMap.put(datasourceName, deviceLocation);
			}
			// TODO added temporarily for cummins
			LatestDataSearchDTO latestDataSearchDTO = new LatestDataSearchDTO();
			latestDataSearchDTO.setSourceId(datasourceName);
			List<String> points = new ArrayList<String>();
			points.add(SAF_LOCATION);
			latestDataSearchDTO.setPoints(points);
			latestDataSearchList.add(latestDataSearchDTO);

		}
		// Get latest saffron data
		getLatestLocTempFromSaffron(latestDataSearchList, deviceParamMap);

		List<DeviceLocationDTO> assetsWithoutDevice = new ArrayList<DeviceLocationDTO>();

		// If Asset Mode get all assets
		if (isNotBlank(mode) && mode.equalsIgnoreCase(ASSET_MODE)) {
			assetsWithoutDevice = getAllAssets(domainName, deviceParamMap,
			        assetsWithoutDevice);
		}
		// Construct the response payload
		List<DeviceLocationDTO> deviceLocList = constructDeviceLoc(
		        deviceParamMap, assetsWithoutDevice);

		// Add the list which does not have devices mapped
		deviceLocList.addAll(assetsWithoutDevice);

		// Validate the result
		validateResult(deviceLocList);
		return deviceLocList;

	}

	private void validateGetLocation(String domainName, String mode) {
		// If domain is blank, get the domain from logged in user
		if (StringUtils.isBlank(domainName)) {
			domainName = getContext().getSubscription().getEndUserDomain();
		}
		// Validate the mode
		if (isNotBlank(mode)
		        && (!(mode.equalsIgnoreCase(ASSET_MODE)) && !(mode
		                .equalsIgnoreCase(ASSET_LOC_MODE)))) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        MODE.getDescription());
		}
	}

	private List<ReturnFieldsDTO> getAllLocationPoints(String domainName) {
		// Get points having data type as Geo point
		List<ReturnFieldsDTO> locationPoints = getPointSearchFields(domainName,
		        GEO_POINT_FIELD);
		return locationPoints;
	}

	private DeviceLocationDTO constructDeviceLocation(List<FieldMapDTO> fields,
	        String domain) {
		DeviceLocationDTO deviceLocationParam = new DeviceLocationDTO();

		// Extract asset name
		FieldMapDTO assetNameMap = new FieldMapDTO();
		assetNameMap.setKey(EQUIP_NAME);
		assetNameMap = fetchField(fields, assetNameMap);
		deviceLocationParam.setAssetName(assetNameMap.getValue());

		// Extract asset identifier
		FieldMapDTO assetIdMap = new FieldMapDTO();
		assetIdMap.setKey(EQUIP_IDENTIFIER);
		assetIdMap = fetchField(fields, assetIdMap);

		// Extract asset template
		FieldMapDTO assetTemplateMap = new FieldMapDTO();
		assetTemplateMap.setKey(EQUIP_TEMPLATE);
		assetTemplateMap = fetchField(fields, assetTemplateMap);

		// Extract display name
		FieldMapDTO displayNameMap = new FieldMapDTO();
		displayNameMap.setKey(DISPLAY_NAME);
		displayNameMap = fetchField(fields, displayNameMap);

		// Extract data type
		FieldMapDTO dataTypeMap = new FieldMapDTO();
		dataTypeMap.setKey(DATA_TYPE);
		dataTypeMap = fetchField(fields, dataTypeMap);

		// Set the asset identifier
		FieldMapDTO assetIdentityMap = new FieldMapDTO();
		assetIdentityMap.setKey(IDENTIFIER.getFieldName());
		assetIdentityMap.setValue(assetIdMap.getValue());
		deviceLocationParam.setAssetIdentifier(assetIdentityMap);

		// Get device details for template
		EntityDTO assetEntity = getAssetDetails(domain, assetIdMap,
		        assetTemplateMap);

		// Check if asset template is ASSET
		FieldMapDTO assetTypeMap = new FieldMapDTO();
		if (isNotBlank(assetTemplateMap.getValue())
		        && assetTemplateMap.getValue().equalsIgnoreCase(ASSET_TEMPLATE)) {

			// Get asset type , this is to be used as asset template
			assetTypeMap.setKey(ASSET_TYPE);
			assetTypeMap = fetchField(assetEntity.getFieldValues(),
			        assetTypeMap);
		}
		// Set the deviceLocationParam

		if (isBlank(assetTypeMap.getValue())) {
			deviceLocationParam.setAssetTemplate(assetTemplateMap.getValue());
		} else {
			// Set asset template as asset type
			deviceLocationParam.setAssetTemplate(assetTypeMap.getValue());
		}
		deviceLocationParam.setTemplateName(assetTemplateMap.getValue());

		// if (dataTypeMap.getValue().equalsIgnoreCase(LATITUDE_FIELD)) {
		// deviceLocationParam.setLatitudeDisplayName(displayNameMap
		// .getValue());
		// } else if (dataTypeMap.getValue().equalsIgnoreCase(LONGITUDE_FIELD))
		// {
		// // Add the longitude name
		// deviceLocationParam.setLongitudeDisplayName(displayNameMap
		// .getValue());
		// }
		// Get latitude and longitude
		// try {
		// getLatLng(domain, assetEntity, deviceLocationParam);
		// } catch (GalaxyRestException galaxyException) {
		// if (!(galaxyException.getCode()
		// .equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()))) {
		// throw galaxyException;
		// }
		// LOGGER.debug("exception", galaxyException);
		// }
		return deviceLocationParam;
	}

	private EntityDTO getAssetDetails(String domain, FieldMapDTO assetIdMap,
	        FieldMapDTO assetTemplateMap) {
		// Get asset type info from asset
		DomainDTO assetDomain = new DomainDTO();
		assetDomain.setDomainName(domain);

		// Set entity template
		EntityTemplateDTO assetTemplate = new EntityTemplateDTO();
		assetTemplate.setEntityTemplateName(assetTemplateMap.getValue());

		// Set identifier
		FieldMapDTO assetIdentifierMap = new FieldMapDTO();
		assetIdentifierMap.setKey(IDENTIFIER.getFieldName());
		assetIdentifierMap.setValue(assetIdMap.getValue());

		// Construct identityDTO and get the asset
		IdentityDTO assetIdentity = new IdentityDTO();
		assetIdentity.setEntityTemplate(assetTemplate);
		assetIdentity.setDomain(assetDomain);
		assetIdentity.setIdentifier(assetIdentifierMap);

		return platformClient.post(findMarkerPlatformEndpoint, getJwtToken(),
		        assetIdentity, EntityDTO.class);
	}

	private List<DeviceLocationDTO> constructDeviceLoc(
	        HashMap<String, DeviceLocationDTO> deviceParamMap,
	        List<DeviceLocationDTO> allAssets) {
		// Construct the response payload
		List<DeviceLocationDTO> deviceLocs = new ArrayList<DeviceLocationDTO>();
		for (Map.Entry<String, DeviceLocationDTO> entry : deviceParamMap
		        .entrySet()) {
			DeviceLocationDTO deviceLoc = entry.getValue();
			deviceLoc.setDatasourceName(entry.getKey());
			deviceLocs.add(deviceLoc);
		}
		return deviceLocs;
	}

	private List<DeviceLocationDTO> getAllAssets(String domainName,
	        HashMap<String, DeviceLocationDTO> deviceParamMap,
	        List<DeviceLocationDTO> assetsWithoutDevice) {

		// Get all assetTypes
		List<EntityDTO> assets = getAssets(domainName, ASSET_TEMPLATE);

		// Create a list of both asset types
		HashMap<String, DeviceLocationDTO> allAssets = new HashMap<>();
		List<DeviceLocationDTO> retainThese = new ArrayList<DeviceLocationDTO>();

		for (EntityDTO asset : assets) {

			// Construct device location
			DeviceLocationDTO deviceLocation = constructDeviceLocAllAssets(asset);

			if (isNotBlank(deviceLocation.getDatasourceName())
			        && !deviceParamMap.containsKey(deviceLocation
			                .getDatasourceName())) {
				// Source id not present in the map, add it
				deviceParamMap.put(deviceLocation.getDatasourceName(),
				        deviceLocation);
			}
			if (isBlank(deviceLocation.getDatasourceName())) {
				for (Map.Entry<String, DeviceLocationDTO> entry : deviceParamMap
				        .entrySet()) {
					DeviceLocationDTO deviceLoc = entry.getValue();
					if (!(deviceLoc.getAssetIdentifier().getValue()
					        .equalsIgnoreCase(deviceLocation
					                .getAssetIdentifier().getValue()))) {
						allAssets.put(deviceLocation.getAssetIdentifier()
						        .getValue(), deviceLocation);
					} else {
						retainThese.add(deviceLocation);
					}
				}
				if (deviceParamMap.isEmpty()) {
					allAssets.put(deviceLocation.getAssetIdentifier()
					        .getValue(), deviceLocation);
				}
			}
		}
		assetsWithoutDevice = new ArrayList<DeviceLocationDTO>(
		        allAssets.values());
		// Latest data exists, hence remove these assets from the list
		assetsWithoutDevice.removeAll(retainThese);
		return assetsWithoutDevice;
	}

	private DeviceLocationDTO constructDeviceLocAllAssets(EntityDTO asset) {
		DeviceLocationDTO deviceLocation = new DeviceLocationDTO();

		// Extract asset name
		FieldMapDTO assetNameMap = new FieldMapDTO();
		assetNameMap.setKey(ASSET_NAME);
		assetNameMap = fetchField(asset.getDataprovider(), assetNameMap);
		deviceLocation.setAssetName(assetNameMap.getValue());

		// Set the asset template
		deviceLocation.setTemplateName(asset.getEntityTemplate()
		        .getEntityTemplateName());

		// If asset template then, fetch assetType and set it as assetTemplate
		if (asset.getEntityTemplate().getEntityTemplateName()
		        .equalsIgnoreCase(ASSET_TEMPLATE)) {
			FieldMapDTO assetTypeMap = new FieldMapDTO();
			assetTypeMap.setKey(ASSET_TYPE);
			assetTypeMap = fetchField(asset.getDataprovider(), assetTypeMap);
			deviceLocation.setAssetTemplate(assetTypeMap.getValue());
		} else {
			deviceLocation.setAssetTemplate(asset.getEntityTemplate()
			        .getEntityTemplateName());
		}
		// Set the asset identifier
		deviceLocation.setAssetIdentifier(asset.getIdentifier());
		return deviceLocation;
	}

	@SuppressWarnings("unchecked")
	private void getLatestLocTempFromSaffron(
	        List<LatestDataSearchDTO> latestData,
	        HashMap<String, DeviceLocationDTO> deviceParamMap) {
		// invoke saffron API here
		List<LatestDataResultDTO> latestLoc = new ArrayList<LatestDataResultDTO>();
		try {
			latestLoc = saffronClient.post(latestLocationEndpoint,
			        getJwtToken(), latestData, List.class,
			        LatestDataResultDTO.class);
		} catch (GalaxyRestException galaxyException) {
			if (!(galaxyException.getCode()
			        .equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()))) {
				throw galaxyException;
			}
			LOGGER.debug("exception", galaxyException);
		}
		// Extract the display name's and compare it with that saved in hashmap,
		for (LatestDataResultDTO latestDataResultDTO : latestLoc) {
			if (CollectionUtils.isNotEmpty(latestDataResultDTO.getData())
			        && deviceParamMap.containsKey(latestDataResultDTO
			                .getSourceId())) {
				DeviceLocationDTO deviceLocationParam = deviceParamMap
				        .get(latestDataResultDTO.getSourceId());
				Gson gson = new Gson();
				if (CollectionUtils.isNotEmpty(latestDataResultDTO.getData())) {
					com.pcs.avocado.commons.dto.CoordinatesDTO coordinates = gson
					        .fromJson(
					                latestDataResultDTO.getData().get(0)
					                        .getData(),
					                com.pcs.avocado.commons.dto.CoordinatesDTO.class);
					deviceLocationParam.setLocation(coordinates);
				}

				// Append the latest location info to the map
				deviceParamMap.put(latestDataResultDTO.getSourceId(),
				        deviceLocationParam);
			}

		}
	}

	@Override
	public List<ReturnFieldsDTO> getDevices(List<String> datasources) {
		// Validate the input
		validateCollection(DATA_SOURCES_LIST, datasources);

		List<ReturnFieldsDTO> device = new ArrayList<ReturnFieldsDTO>();

		Set<String> uniqueDatasourcesMap = new HashSet<String>(datasources);

		// Validate if empty string is passed
		String emptyString = "";
		if (uniqueDatasourcesMap.contains(emptyString)) {
			uniqueDatasourcesMap.remove(emptyString);
		}
		if (uniqueDatasourcesMap.isEmpty()) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        DATA_SOURCES_LIST.getDescription());
		}

		for (String datasource : uniqueDatasourcesMap) {
			if (isNotBlank(datasource)) {
				List<ReturnFieldsDTO> deviceList = getDeviceFromDs(datasource);
				if (CollectionUtils.isNotEmpty(deviceList)) {
					device.addAll(deviceList);
				}
			}
		}
		validateResult(device);
		return device;
	}

	@SuppressWarnings("unchecked")
	private List<ReturnFieldsDTO> getDeviceFromDs(String datasource) {
		SearchFieldsDTO searchFieldsDTO = new SearchFieldsDTO();
		// Set point template
		EntityTemplateDTO searchTemplate = new EntityTemplateDTO();
		searchTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
		searchFieldsDTO.setEntityTemplate(searchTemplate);

		// Set search fields, datasource
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey(DATASOURCE_NAME);
		fieldMapDTO.setValue(datasource);

		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		searchFields.add(fieldMapDTO);
		searchFieldsDTO.setSearchFields(searchFields);

		// Get the datasource name and equip fields
		List<ReturnFieldsDTO> deviceList = null;

		try {
			// Get points having data type as latitude
			deviceList = platformClient.post(
			        markerPlatformSearchFieldsEndpoint, getJwtToken(),
			        searchFieldsDTO, List.class, ReturnFieldsDTO.class);
		} catch (GalaxyRestException galaxyException) {
			if (!(galaxyException.getCode()
			        .equals(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode()))
			        && !(galaxyException.getCode()
			                .equals(GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED
			                        .getCode()))) {
				LOGGER.debug("exception", galaxyException.getMessage());
				throw galaxyException;
			}
		}
		return deviceList;
	}

	@Override
	public StatusMessageDTO deleteDevice(IdentityDTO deviceIdentifier,
	        String datasourceName) {

		// Only superAdmin has provision to delete a device
		if (!isSuperAdmin()) {
			throw new GalaxyException(GalaxyCommonErrorCodes.NO_ACCESS);
		}

		// Validate the input
		ValidationUtils.validateMandatoryFields(deviceIdentifier, IDENTIFIER);
		ValidationUtils.validateMandatoryFields(
		        deviceIdentifier.getIdentifier(), IDENTIFIER_KEY,
		        IDENTIFIER_VALUE);
		ValidationUtils.validateMandatoryField(DATASOURCE_NAME, datasourceName);

		ESBDeleteDevice deviceEsbPayload = new ESBDeleteDevice();
		deviceEsbPayload.setDatasourceName(datasourceName);
		deviceEsbPayload.setDomain(deviceIdentifier.getDomain());

		// Set template name
		EntityTemplateDTO deviceTemplate = new EntityTemplateDTO();
		deviceTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
		deviceEsbPayload.setEntityTemplate(deviceTemplate);

		// Set platform entity type
		PlatformEntityDTO markerType = new PlatformEntityDTO();
		markerType.setPlatformEntityType(MARKER);
		deviceEsbPayload.setPlatformEntity(markerType);
		deviceEsbPayload.setIdentifier(deviceIdentifier.getIdentifier());

		StatusMessageErrorDTO status = esbClient.post(deleteDeviceESBEndpoint,
		        getJwtToken(), deviceEsbPayload, StatusMessageErrorDTO.class);

		if (isNotBlank(status.getErrorCode())
		        && status.getErrorCode().equalsIgnoreCase(
		                GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED.getCode()
		                        .toString())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        status.getField());
		}
		StatusMessageDTO statusMessage = new StatusMessageDTO();
		statusMessage.setStatus(status.getStatus());
		return statusMessage;

	}
}
