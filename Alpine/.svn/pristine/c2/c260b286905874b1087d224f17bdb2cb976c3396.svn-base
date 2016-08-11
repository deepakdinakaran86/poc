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
package com.pcs.web.services;

import static com.pcs.web.constants.WebConstants.CONFIGURATION;
import static com.pcs.web.constants.WebConstants.DATASOURCE_NAME;
import static com.pcs.web.constants.WebConstants.DEVICE;
import static com.pcs.web.constants.WebConstants.DEVICE_IP;
import static com.pcs.web.constants.WebConstants.DEVICE_PORT;
import static com.pcs.web.constants.WebConstants.DEVICE_TEMPLATE;
import static com.pcs.web.constants.WebConstants.DEVICE_TYPE;
import static com.pcs.web.constants.WebConstants.ENTITY_NAME;
import static com.pcs.web.constants.WebConstants.NETWORK_PROTOCOL;
import static com.pcs.web.constants.WebConstants.PROTOCOL_TYPE;
import static com.pcs.web.constants.WebConstants.PUBLISH;
import static com.pcs.web.constants.WebConstants.SOURCE_ID;
import static com.pcs.web.constants.WebConstants.TAGS;
import static com.pcs.web.constants.WebConstants.WRITEBACK_PORT;
import static java.lang.String.valueOf;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.ConfigurationSearch;
import com.pcs.web.dto.Device;
import com.pcs.web.dto.DeviceTag;
import com.pcs.web.dto.EntityDTO;
import com.pcs.web.dto.EntityTemplateDTO;
import com.pcs.web.dto.ErrorDTO;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.IdentityDTO;
import com.pcs.web.dto.MarkerSearchDTO;
import com.pcs.web.dto.Status;
import com.pcs.web.dto.StatusMessageDTO;

/**
 * AssetService
 * 
 * @author Twinkle
 * @date October 2015
 * @since galaxy-1.0.0
 * 
 */
@Service
public class AssetService extends BaseService {

	@Autowired
	private DeviceService deviceService;

	@Value("${cummins.services.createMarker}")
	private String createMarkerEndpointUri;

	@Value("${cummins.services.updateMarker}")
	private String updateMarkerEndpointUri;

	@Value("${cummins.services.deleteMarker}")
	private String deleteMarkerEndpointUri;

	@Value("${cummins.services.findMarker}")
	private String findMarkerEndpointUri;

	@Value("${cummins.services.listFilterMarker}")
	private String listFilterMarkerEndpointUri;

	@Value("${cummins.services.getDeviceDetails}")
	private String getDeviceDetailsEndpointUri;

	@Value("${cummins.services.claimDevice}")
	private String claimDeviceEndpointUri;
	
	@Value("${cummins.services.validate}")
	private String getEntityValidationEndpointUri;

	/**
	 * Method to list Assets
	 */
	public CumminsResponse<List<EntityDTO>> viewAllMarkers(
			IdentityDTO identityDto) {
		String viewAllMarkersServiceURI = getServiceURI(listFilterMarkerEndpointUri);

		Type entityListType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().postResource(viewAllMarkersServiceURI,
				identityDto, entityListType);
	}

	/**
	 * Method to view Asset details
	 */
	public CumminsResponse<EntityDTO> viewMarkerDetails(IdentityDTO identityDto) {
		String viewMarkerDetailsServiceURI = getServiceURI(findMarkerEndpointUri);
		return getPlatformClient().postResource(viewMarkerDetailsServiceURI,
				identityDto, EntityDTO.class);
	}

	/**
	 * Method to create asset
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> create(EntityDTO entityDTO) {

		String createMarkerServiceURI = getServiceURI(createMarkerEndpointUri);
		EntityDTO entity = manageDeviceSelectionInsert(entityDTO);
		// 5. create asset with or without device
		return getPlatformClient().postResource(createMarkerServiceURI, entity,
				EntityDTO.class);
	}

	/**
	 * Method to update asset
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<StatusMessageDTO> update(EntityDTO entityDTO) {
		String updateMarkerServiceURI = getServiceURI(updateMarkerEndpointUri);
		EntityDTO entity = manageDeviceSelectionUpdate(entityDTO);
		return getPlatformClient().putResource(updateMarkerServiceURI, entity,
				StatusMessageDTO.class);
	}

	/**
	 * Method to delete asset
	 * TODO : not complete
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> delete(EntityDTO entityDTO) {
		String deleteMarkerServiceURI = getServiceURI(deleteMarkerEndpointUri);
		return getPlatformClient().deleteResource(deleteMarkerServiceURI,
				EntityDTO.class, entityDTO);
	}

	/**
	 * Method to find asset
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> find(EntityDTO entityDTO) {
		String findMarkerServiceURI = getServiceURI(findMarkerEndpointUri);
		return getPlatformClient().postResource(findMarkerServiceURI,
				entityDTO, EntityDTO.class);
	}

	/**
	 * Method to list assets
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> list(EntityDTO entityDTO) {
		String listMarkerServiceURI = getServiceURI(listFilterMarkerEndpointUri);
		return getPlatformClient().postResource(listMarkerServiceURI,
				entityDTO, EntityDTO.class);
	}

	/**
	 * Method to update 
	 * 
	 * @param
	 * @return
	 */
	public CumminsResponse<EntityDTO> listFilter(EntityDTO entityDTO) {
		String listFilterMarkerServiceURI = getServiceURI(listFilterMarkerEndpointUri);
		return getPlatformClient().postResource(listFilterMarkerServiceURI,
				entityDTO, EntityDTO.class);
	}

	private FieldMapDTO fetchField(List<FieldMapDTO> fieldMapDTOs,
			FieldMapDTO fieldMapDTO) {
		FieldMapDTO field = new FieldMapDTO();
		// populate field<FieldMapDTO> from input EntityDto
		field.setKey(fieldMapDTO.getKey());
		int fieldIndex = fieldMapDTOs.indexOf(field);
		field = fieldIndex != -1 ? fieldMapDTOs.get(fieldIndex) : field;
		return field;
	}

	private List<FieldMapDTO> createFieldMapsForDevice(Device device) {
		Gson gson = new Gson();
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(ENTITY_NAME, device.getSourceId()));
		fieldMaps.add(createFieldMap(SOURCE_ID, device.getSourceId()));
		fieldMaps.add(createFieldMap(CONFIGURATION,
				gson.toJson(device.getConfigurations())));

		List<DeviceTag> tags = device.getTags();
		if (CollectionUtils.isNotEmpty(tags)) {
			fieldMaps.add(createFieldMap(TAGS, gson.toJson(tags)));
		}
		ConfigurationSearch version = device.getVersion();
		fieldMaps.add(createFieldMap(DEVICE_TYPE, version.getDeviceType()));
		fieldMaps.add(createFieldMap(PROTOCOL_TYPE, version.getProtocol()));

		fieldMaps.add(createFieldMap(NETWORK_PROTOCOL, device
				.getNetworkProtocol().getName()));
		fieldMaps.add(createFieldMap(DEVICE_IP, device.getIp()));
		fieldMaps.add(createFieldMap(DEVICE_PORT,
				valueOf(device.getConnectedPort())));
		fieldMaps.add(createFieldMap(WRITEBACK_PORT,
				valueOf(device.getWriteBackPort())));
		fieldMaps.add(createFieldMap(DATASOURCE_NAME,
				device.getDatasourceName()));
		fieldMaps.add(createFieldMap(PUBLISH,
				String.valueOf(device.getIsPublishing())));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	private EntityDTO manageDeviceSelectionInsert(EntityDTO entityDTO) {
		List<FieldMapDTO> fields = entityDTO.getFieldValues();

		// 1. get deviceSourceId from request
		FieldMapDTO fieldMapInput = new FieldMapDTO(DEVICE);
		fieldMapInput = fetchField(fields, fieldMapInput);

		@SuppressWarnings("rawtypes")
		CumminsResponse cumminsResponse = null;
		// fetch device from saffron
		if (StringUtils.isNotEmpty(fieldMapInput.getValue())) {
			cumminsResponse = fetchDeviceDetails(fieldMapInput);
		}
		if (cumminsResponse == null
				|| cumminsResponse.getEntity() instanceof ErrorDTO) {

		} else {
			// claim device in saffron and create it
			@SuppressWarnings("unchecked")
			StatusMessageDTO statusMessageDTO = claimDevice(cumminsResponse,
					fieldMapInput);

			if (statusMessageDTO.getStatus().equals(Status.FAILURE)) {
				// if failure from safron, don't set the device source id
				fieldMapInput.setValue(null);
			}
		}
		return entityDTO;
	}

	private EntityDTO manageDeviceSelectionUpdate(EntityDTO inputEntityDTO) {

		// fetch already existing entity
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setEntityTemplate(inputEntityDTO.getEntityTemplate());
		identityDto.setIdentifier(inputEntityDTO.getIdentifier());

		EntityDTO existingEntityDTO = viewMarkerDetails(identityDto)
				.getEntity();

		// check for device attached to existing entity
		List<FieldMapDTO> existingFields = existingEntityDTO.getFieldValues();
		FieldMapDTO fieldMapExisting = new FieldMapDTO(DEVICE);
		fieldMapExisting = fetchField(existingFields, fieldMapExisting);

		// get deviceSourceId from request
		List<FieldMapDTO> inputFields = inputEntityDTO.getFieldValues();
		FieldMapDTO fieldMapInput = new FieldMapDTO(DEVICE);
		fieldMapInput = fetchField(inputFields, fieldMapInput);

		String newDevice = fieldMapInput.getValue();
		@SuppressWarnings("rawtypes")
		CumminsResponse cumminsResponse = null;
		String oldDevice = fieldMapExisting.getValue();
		if (StringUtils.isNotBlank(oldDevice)) {
			// device already exist, do not update
			// remove device from inputEntityDTO

			if (!oldDevice.equalsIgnoreCase(newDevice)) {
				fieldMapInput.setValue(oldDevice);
			}
		} else {
			if (StringUtils.isNotBlank(newDevice)) {
				// entity does not contain device, add and claim it
				// fetch device from saffron
				cumminsResponse = fetchDeviceDetails(fieldMapInput);
				if (cumminsResponse == null || cumminsResponse.getEntity() instanceof ErrorDTO) {

				} else {
					// claim device in saffron
					@SuppressWarnings("unchecked")
					StatusMessageDTO statusMessageDTO = claimDevice(
							cumminsResponse, fieldMapInput);

					if (statusMessageDTO.getStatus().equals(Status.FAILURE)) {
						// if failure from safron, don't create the device
						fieldMapInput.setValue(null);
					}
				}

			}
		}
		inputEntityDTO.setFieldValues(inputFields);
		return inputEntityDTO;
	}

	@SuppressWarnings("rawtypes")
	private CumminsResponse fetchDeviceDetails(FieldMapDTO fieldMap) {
		String getDeviceDetailsUri = getServiceURI(getDeviceDetailsEndpointUri
				.replace("{source_id}", fieldMap.getValue()));
		return getPlatformClient().getResource(getDeviceDetailsUri,
				Device.class, fieldMap.getValue());
	}

	private StatusMessageDTO claimDevice(
			CumminsResponse<Device> cumminsResponse, FieldMapDTO fieldMapInput) {
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.FAILURE);
		Device device = null;
		if (cumminsResponse != null) {
			device = cumminsResponse.getEntity();
			if (device != null && StringUtils.isNotBlank(device.getSourceId())) {

				// 3. create device in alpine
				EntityDTO deviceEntity = new EntityDTO();
				deviceEntity.setFieldValues(createFieldMapsForDevice(device));

				EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
				entityTemplate.setEntityTemplateName(DEVICE_TEMPLATE);
				deviceEntity.setEntityTemplate(entityTemplate);

				CumminsResponse<EntityDTO> deviceCreationRes = deviceService
						.createDevice(deviceEntity);
				if (deviceCreationRes != null && deviceCreationRes.getEntity() instanceof EntityDTO) {
					// 4. claim device in Saffron
					String claimDeviceUri = getServiceURI(claimDeviceEndpointUri
							.replace("{source_id}", fieldMapInput.getValue()));
					CumminsResponse<Object> putResource = getPlatformClient()
							.putResource(claimDeviceUri, null, null);
					if (putResource == null || putResource.getEntity() instanceof ErrorDTO) {
						statusMessageDTO.setStatus(Status.FAILURE);
					} else {
						statusMessageDTO.setStatus(Status.SUCCESS);
					}
				} else {
					statusMessageDTO.setStatus(Status.FAILURE);
				}

			}
		}
		return statusMessageDTO;
	}
	
	public CumminsResponse<StatusMessageDTO> checkFieldAvailability(MarkerSearchDTO markerSearchDTO ){
		String entityUniqueEndpointUri = getServiceURI(getEntityValidationEndpointUri);
		CumminsResponse<StatusMessageDTO> postResource = getPlatformClient().postResource(entityUniqueEndpointUri,
				markerSearchDTO, StatusMessageDTO.class);
		return postResource;
	}

}
