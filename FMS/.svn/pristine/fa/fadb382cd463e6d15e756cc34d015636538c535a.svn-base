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
package com.pcs.fms.web.controller;

import static com.pcs.fms.web.constants.FMSWebConstants.ALLOCATED;
import static com.pcs.fms.web.constants.FMSWebConstants.ASSIGN_DEVICE_PATH;
import static com.pcs.fms.web.constants.FMSWebConstants.ASSIGN_DEVICE_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.CONFIGURATION;
import static com.pcs.fms.web.constants.FMSWebConstants.DEVICES_HOME_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.DEVICE_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.DEVICE_IP;
import static com.pcs.fms.web.constants.FMSWebConstants.DEVICE_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.DEVICE_PORT;
import static com.pcs.fms.web.constants.FMSWebConstants.DEVICE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.ENTITY_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.LATITUDE;
import static com.pcs.fms.web.constants.FMSWebConstants.LONGITUDE;
import static com.pcs.fms.web.constants.FMSWebConstants.MAKE;
import static com.pcs.fms.web.constants.FMSWebConstants.MODEL;
import static com.pcs.fms.web.constants.FMSWebConstants.NETWORK_PROTOCOL;
import static com.pcs.fms.web.constants.FMSWebConstants.PROTOCOL;
import static com.pcs.fms.web.constants.FMSWebConstants.PUBLISH;
import static com.pcs.fms.web.constants.FMSWebConstants.TAGS;
import static com.pcs.fms.web.constants.FMSWebConstants.VERSION;
import static com.pcs.fms.web.constants.FMSWebConstants.VIEW_DEVICE_PATH;
import static com.pcs.fms.web.constants.FMSWebConstants.VIEW_DEVICE_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.WRITEBACK_PORT;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.PlatformEntityDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Tenant;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.model.Device;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.DeviceService;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class DeviceController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(DeviceController.class);

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = DEVICES_HOME_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		logger.debug("Inside the device controller {}");
		ModelAndView mv = new ModelAndView(DEVICE_HOME_VIEW);
		mv.addObject("payload", getPayloadForGrid());
		mv.addObject("identifier", getIdentifier());
		return mv;
	}

	@RequestMapping(value = ASSIGN_DEVICE_PATH,method = RequestMethod.POST)
	public ModelAndView assign(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(ASSIGN_DEVICE_VIEW);
		mv.addObject("payload", getPayloadForGrid());
		mv.addObject("identifier", getIdentifier());
		return mv;
	}

	@RequestMapping(value = VIEW_DEVICE_PATH,method = RequestMethod.POST)
	public ModelAndView view(HttpServletRequest request,
	        @ModelAttribute FieldMapDTO field) {

		logger.debug("Inside device View");
		ModelAndView mv = new ModelAndView(VIEW_DEVICE_VIEW);
		mv.addObject("action", "View");

		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setIdentifier(field);

		FMSResponse<EntityDTO> res = deviceService.getDeviceDetails(field
		        .getValue());
		Device device = getDevice(res.getEntity());

		device.setOldSourceId(device.getSourceId());
		mv.addObject(VIEW_DEVICE_VIEW, device);
		return mv;
	}

	private Device getDevice(EntityDTO entity) {

		Device device = new Device();
		if (entity != null) {
			List<FieldMapDTO> fieldValues = entity.getFieldValues();
			for (FieldMapDTO fieldMapDTO : fieldValues) {
				if (fieldMapDTO.getKey().equals(ENTITY_NAME)) {
					device.setSourceId(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(TAGS)) {
					device.setTags(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(MAKE)) {
					device.setMake(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(DEVICE_TYPE)) {
					device.setDeviceType(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(MODEL)) {
					device.setModel(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(PROTOCOL)) {
					device.setProtocol(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(VERSION)) {
					device.setVersion(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(DEVICE_TYPE)) {
					device.setDeviceType(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(NETWORK_PROTOCOL)) {
					device.setNwProtocol(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(DEVICE_IP)) {
					device.setDeviceIp(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(DEVICE_PORT)) {
					device.setDevicePort(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(WRITEBACK_PORT)) {
					device.setWbPort(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(DEVICE_NAME)) {
					device.setDeviceName(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(CONFIGURATION)) {
					device.setConfiguration(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(PUBLISH)) {
					device.setIsPublish(fieldMapDTO.getValue()
					        .equalsIgnoreCase(Boolean.TRUE.toString()));
				} else if (fieldMapDTO.getKey().equals(ALLOCATED)) {
					device.setAllocated(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(LATITUDE)) {
					device.setLatitude(fieldMapDTO.getValue());
				} else if (fieldMapDTO.getKey().equals(LONGITUDE)) {
					device.setLongitude(fieldMapDTO.getValue());
				}
			}
			FieldMapDTO identity = entity.getIdentifier();
			if (identity != null) {
				if (StringUtils.isNotBlank(identity.getValue()))
					device.setIdentifier(identity.getValue());
			}
			if (entity.getEntityStatus() != null) {
				if (StringUtils.isNotBlank(entity.getEntityStatus()
				        .getStatusName())) {
					device.setStatus(entity.getEntityStatus().getStatusName());
				}
			}
		}
		return device;
	}

	private String getPayloadForGrid() {
		Gson gson = new Gson();
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		Tenant tenant = null;
		if (token.getIsSubClientSelected()) {
			tenant = token.getCurrentTenant();
		} else {
			tenant = token.getTenant();
		}
		return gson.toJson(tenant);
	}

	private String getIdentifier() {
		Gson gson = new Gson();
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		Tenant tenant = token.getTenant();
		if (!FMSAccessManager.isSuperAdmin()) {
			IdentityDTO identity = new IdentityDTO();

			DomainDTO domain = new DomainDTO();
			domain.setDomainName(tenant.getDomainName());
			EntityTemplateDTO entity = new EntityTemplateDTO();
			entity.setEntityTemplateName("DefaultTenant");
			PlatformEntityDTO platform = new PlatformEntityDTO();
			platform.setPlatformEntityType("TENANT");
			FieldMapDTO fieldMapDTO = new FieldMapDTO();
			fieldMapDTO.setKey("tenantId");
			fieldMapDTO.setValue(tenant.getTenantId());

			identity.setDomain(domain);
			identity.setEntityTemplate(entity);
			identity.setPlatformEntity(platform);
			identity.setIdentifier(fieldMapDTO);

			return gson.toJson(identity);
		}
		return gson.toJson(new JsonObject());
	}
}
