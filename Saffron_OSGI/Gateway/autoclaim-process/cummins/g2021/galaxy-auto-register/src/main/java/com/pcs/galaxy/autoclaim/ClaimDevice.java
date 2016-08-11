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

import static com.pcs.galaxy.constants.FieldValueConstants.ALLOCATED;
import static com.pcs.galaxy.constants.FieldValueConstants.DATASOURCE_NAME;
import static com.pcs.galaxy.constants.FieldValueConstants.DEVICENAME;
import static com.pcs.galaxy.constants.FieldValueConstants.DEVICE_IP;
import static com.pcs.galaxy.constants.FieldValueConstants.DEVICE_PORT;
import static com.pcs.galaxy.constants.FieldValueConstants.DEVICE_TEMPLATE;
import static com.pcs.galaxy.constants.FieldValueConstants.DEVICE_TYPE;
import static com.pcs.galaxy.constants.FieldValueConstants.ENTITY_NAME;
import static com.pcs.galaxy.constants.FieldValueConstants.GMTOFFSET;
import static com.pcs.galaxy.constants.FieldValueConstants.LATITUDE;
import static com.pcs.galaxy.constants.FieldValueConstants.LONGITUDE;
import static com.pcs.galaxy.constants.FieldValueConstants.MAKE;
import static com.pcs.galaxy.constants.FieldValueConstants.MODEL;
import static com.pcs.galaxy.constants.FieldValueConstants.NETWORK_PROTOCOL;
import static com.pcs.galaxy.constants.FieldValueConstants.PROTOCOL;
import static com.pcs.galaxy.constants.FieldValueConstants.PUBLISH;
import static com.pcs.galaxy.constants.FieldValueConstants.TAGS;
import static com.pcs.galaxy.constants.FieldValueConstants.TIMEZONE;
import static com.pcs.galaxy.constants.FieldValueConstants.URL;
import static com.pcs.galaxy.constants.FieldValueConstants.VERSION;
import static com.pcs.galaxy.constants.FieldValueConstants.WRITEBACK_PORT;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.galaxy.dto.Device;
import com.pcs.galaxy.dto.EntityDTO;
import com.pcs.galaxy.dto.EntityTemplateDTO;
import com.pcs.galaxy.dto.FieldMapDTO;
import com.pcs.galaxy.dto.IdentityDTO;
import com.pcs.galaxy.dto.StatusMessageDTO;
import com.pcs.galaxy.dto.Tag;
import com.pcs.galaxy.token.TokenProvider;

/**
 * ClaimDevice
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class ClaimDevice {
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
	@Value("${autoclaim.saffron.device.claim}")
	private String claimURL;
	@Value("${autoclaim.alpine.device.create}")
	private String createDeviceURL;

	@Autowired
	@Qualifier("alpineClient")
	private BaseClient alpineClient;

	@Autowired
	@Qualifier("saffronClient")
	private BaseClient saffronClient;

	private static final String SOURCE_ID = "{source_id}";

	public IdentityDTO claimDevice(Device device) {

		IdentityDTO identity = null;
		if (claimDeviceSaffron(device.getSourceId())) {

			EntityDTO entityDTO = createEntityDto(device);

			EntityDTO entity = createAlpineDevice(entityDTO);

			identity = identityFromEntity(entity);

		}
		return identity;
	}

	public Boolean claimDeviceSaffron(String sourceId) {
		LOGGER.info("Enter into claimDeviceSaffron");
		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		String claimDevice = claimURL.replace(SOURCE_ID, sourceId);

		Boolean status = Boolean.TRUE;
		try {
			saffronClient
			        .put(claimDevice, header, null, StatusMessageDTO.class);
			LOGGER.info("** Claim Device Saffron Successfully **");
		} catch (Exception e) {
			LOGGER.info(e.getMessage() + " : " + sourceId);
			status = Boolean.FALSE;
		}
		return status;
	}

	private EntityDTO createAlpineDevice(EntityDTO device) {
		LOGGER.info("Enter into createAlpineDevice");

		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		EntityDTO entity = null;
		try {
			entity = alpineClient.post(createDeviceURL, header, device,
			        EntityDTO.class);
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

		return entity;
	}

	private EntityDTO createEntityDto(Device device) {
		EntityDTO deviceAlpine = new EntityDTO();
		List<FieldMapDTO> fiedValues = createFieldMaps(device);
		deviceAlpine.setFieldValues(fiedValues);
		deviceAlpine.setEntityTemplate(getDeviceTemplate());
		return deviceAlpine;
	}

	private List<FieldMapDTO> createFieldMaps(Device device) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(ENTITY_NAME, device.getSourceId()));
		if (device.getTags() != null && !device.getTags().isEmpty()) {
			StringBuffer tags = new StringBuffer();
			for (Tag tag : device.getTags()) {
				if (tags.length() > 0) {
					tags.append(",");
				}
				if (tag != null) {
					if (tag.getName() != null && !tag.getName().isEmpty()) {
						tags.append(tag.getName());
					}
				}
			}
			fieldMaps.add(createFieldMap(TAGS, tags.toString()));
		}
		fieldMaps.add(createFieldMap(MAKE, device.getVersion().getMake()));
		fieldMaps.add(createFieldMap(DEVICE_TYPE, device.getVersion()
		        .getDeviceType()));
		fieldMaps.add(createFieldMap(MODEL, device.getVersion().getModel()));
		fieldMaps.add(createFieldMap(PROTOCOL, device.getVersion()
		        .getProtocol()));
		fieldMaps
		        .add(createFieldMap(VERSION, device.getVersion().getVersion()));
		fieldMaps.add(createFieldMap(NETWORK_PROTOCOL, device
		        .getNetworkProtocol().getName()));
		fieldMaps.add(createFieldMap(DEVICE_IP, device.getIp()));
		if (device.getConnectedPort() != null) {
			fieldMaps.add(createFieldMap(DEVICE_PORT, device.getConnectedPort()
			        .toString()));
		}

		if (device.getWriteBackPort() != null) {
			fieldMaps.add(createFieldMap(WRITEBACK_PORT, device
			        .getWriteBackPort().toString()));
		}
		fieldMaps.add(createFieldMap(DATASOURCE_NAME,
		        device.getDatasourceName()));
		fieldMaps.add(createFieldMap(TIMEZONE, device.getTimezone()));

		if (device.getLongitude() != null) {
			fieldMaps.add(createFieldMap(LONGITUDE, device.getLongitude()
			        .toString()));
		}

		if (device.getLatitude() != null) {
			fieldMaps.add(createFieldMap(LATITUDE, device.getLatitude()
			        .toString()));
		}
		fieldMaps.add(createFieldMap(URL, device.getURL()));
		if (device.getGmtOffset() != null) {
			fieldMaps.add(createFieldMap(GMTOFFSET, device.getGmtOffset()
			        .toString()));
		}

		fieldMaps.add(createFieldMap(DEVICENAME, device.getDeviceName()));

		fieldMaps.add(createFieldMap(ALLOCATED, FALSE.toString()));
		fieldMaps.add(createFieldMap(PUBLISH, String.valueOf(TRUE.toString())));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	private EntityTemplateDTO getDeviceTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(DEVICE_TEMPLATE);
		return entityTemplateDTO;
	}

	private IdentityDTO identityFromEntity(EntityDTO entity) {
		IdentityDTO identity = new IdentityDTO();
		identity.setIdentifier(entity.getIdentifier());
		identity.setEntityTemplate(entity.getEntityTemplate());
		identity.setPlatformEntity(entity.getPlatformEntity());
		identity.setDomain(entity.getDomain());
		return identity;
	}

}
