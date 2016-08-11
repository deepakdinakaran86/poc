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

import static com.pcs.galaxy.constants.FieldValueConstants.ASSET_ID;
import static com.pcs.galaxy.constants.FieldValueConstants.ASSET_MAKE;
import static com.pcs.galaxy.constants.FieldValueConstants.ASSET_MODEL;
import static com.pcs.galaxy.constants.FieldValueConstants.ASSET_NAME;
import static com.pcs.galaxy.constants.FieldValueConstants.ASSET_TYPE;
import static com.pcs.galaxy.constants.FieldValueConstants.DESCRIPTION;
import static com.pcs.galaxy.constants.FieldValueConstants.DEVICE_DOMAIN;
import static com.pcs.galaxy.constants.FieldValueConstants.ENTITY_TEMPLATE;
import static com.pcs.galaxy.constants.FieldValueConstants.IDENTIFIER_KEY;
import static com.pcs.galaxy.constants.FieldValueConstants.IDENTIFIER_VALUE;
import static com.pcs.galaxy.constants.FieldValueConstants.OWNER;
import static com.pcs.galaxy.constants.FieldValueConstants.PARENT;
import static com.pcs.galaxy.constants.FieldValueConstants.PLATFORM_ENTITY;
import static com.pcs.galaxy.constants.FieldValueConstants.SERIAL_NUMBER;
import static com.pcs.galaxy.constants.FieldValueConstants.SOURCE_ID;
import static com.pcs.galaxy.constants.FieldValueConstants.SUPER_TENANT;
import static com.pcs.galaxy.constants.FieldValueConstants.UN_RECOGNIZED_DEVICE;

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
import com.pcs.galaxy.dto.DeviceManageDTO;
import com.pcs.galaxy.dto.DomainDTO;
import com.pcs.galaxy.dto.EntityDTO;
import com.pcs.galaxy.dto.EntityTemplateDTO;
import com.pcs.galaxy.dto.FieldMapDTO;
import com.pcs.galaxy.dto.IdentityDTO;
import com.pcs.galaxy.token.TokenProvider;

/**
 * MapPoints
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class UnRecognizedDevices {

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
	@Value("${autoclaim.alpine.device.create}")
	private String createDeviceURL;

	@Autowired
	@Qualifier("alpineClient")
	private BaseClient alpineClient;

	public EntityDTO createUnRecDevice(EntityDTO device) {

		Map<String, String> header = TokenProvider.getHeader(clientId, secret,
		        userName, password);

		EntityDTO entity = null;
		try {
			entity = alpineClient.post(createDeviceURL, header, device,
			        EntityDTO.class);
			LOGGER.info("*** Un Recognized Device Created successfully ***");
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

		return entity;
	}

	public EntityDTO prepareUnRecEntity(DeviceManageDTO parentDetail,
	        IdentityDTO device) {
		LOGGER.info("Create Un Recognized Device : {}",
		        parentDetail.getDescription());

		EntityDTO entity = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(parentDetail.getDomainName());

		entity.setDomain(domain);

		EntityTemplateDTO template = new EntityTemplateDTO();
		template.setEntityTemplateName(UN_RECOGNIZED_DEVICE);
		entity.setEntityTemplate(template);

		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(SOURCE_ID, parentDetail.getSourceId()));
		fieldMaps.add(createFieldMap(IDENTIFIER_VALUE, device.getIdentifier()
		        .getValue()));
		fieldMaps.add(createFieldMap(IDENTIFIER_KEY, device.getIdentifier()
		        .getKey()));
		fieldMaps.add(createFieldMap(ENTITY_TEMPLATE, device
		        .getEntityTemplate().getEntityTemplateName()));
		fieldMaps.add(createFieldMap(DEVICE_DOMAIN, device.getDomain()
		        .getDomainName()));
		fieldMaps.add(createFieldMap(PLATFORM_ENTITY, device
		        .getPlatformEntity().getPlatformEntityType()));
		fieldMaps.add(createFieldMap(SUPER_TENANT,
		        parentDetail.getSuperTenant()));
		fieldMaps.add(createFieldMap(PARENT, parentDetail.getParentTenant()));
		fieldMaps.add(createFieldMap(OWNER, parentDetail.getOwnerTenant()));
		fieldMaps
		        .add(createFieldMap(DESCRIPTION, parentDetail.getDescription()));
		fieldMaps.add(createFieldMap(ASSET_ID, parentDetail.getAssetId()));
		fieldMaps.add(createFieldMap(ASSET_NAME, parentDetail.getAssetName()));
		fieldMaps.add(createFieldMap(ASSET_TYPE, parentDetail.getAssetType()));
		fieldMaps.add(createFieldMap(SERIAL_NUMBER,
		        parentDetail.getSerialNumber()));

		fieldMaps.add(createFieldMap(ASSET_MAKE, parentDetail.getMake()));
		fieldMaps.add(createFieldMap(ASSET_MODEL, parentDetail.getModel()));

		entity.setFieldValues(fieldMaps);
		return entity;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}
}
