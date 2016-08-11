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
package com.pcs.web.controller;

import static com.pcs.web.constants.WebConstants.CONFIGURATION;
import static com.pcs.web.constants.WebConstants.DATASOURCE_NAME;
import static com.pcs.web.constants.WebConstants.DEVICE_CREATE;
import static com.pcs.web.constants.WebConstants.DEVICE_FORM;
import static com.pcs.web.constants.WebConstants.DEVICE_HOME;
import static com.pcs.web.constants.WebConstants.DEVICE_IP;
import static com.pcs.web.constants.WebConstants.DEVICE_PORT;
import static com.pcs.web.constants.WebConstants.DEVICE_TYPE;
import static com.pcs.web.constants.WebConstants.DEVICE_UPDATE;
import static com.pcs.web.constants.WebConstants.DEVICE_VIEW;
import static com.pcs.web.constants.WebConstants.ENTITY_NAME;
import static com.pcs.web.constants.WebConstants.HOME_PATH_NAME;
import static com.pcs.web.constants.WebConstants.IDENTIFIER;
import static com.pcs.web.constants.WebConstants.NETWORK_PROTOCOL;
import static com.pcs.web.constants.WebConstants.NO_ACCESS;
import static com.pcs.web.constants.WebConstants.PROTOCOL_TYPE;
import static com.pcs.web.constants.WebConstants.PUBLISH;
import static com.pcs.web.constants.WebConstants.SOURCE_ID;
import static com.pcs.web.constants.WebConstants.TAGS;
import static com.pcs.web.constants.WebConstants.TRUE;
import static com.pcs.web.constants.WebConstants.WRITEBACK_PORT;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pcs.web.client.AccessManager;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.EntityDTO;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.IdentityDTO;
import com.pcs.web.dto.StatusDTO;
import com.pcs.web.dto.StatusMessageDTO;
import com.pcs.web.model.Device;
import com.pcs.web.services.DeviceService;

/**
 * Controller class for Device Marker. deviceController class extends
 * BaseController .This GensetController provide access to the application
 * behavior which is typically defined by a service interface and interpret user
 * input and transform such input into a sensible model which will be
 * represented to the user by the view
 * 
 * @author PCSEG191 Daniela
 * @date October 2015
 * @since Alpine-1.0.0
 * 
 */
@Controller
public class DeviceController extends BaseController {

	private static final Logger logger = LoggerFactory
	        .getLogger(DeviceController.class);

	private static final String PERMISSION_DEVICE_GRID = "device/grid";
	private static final String PERMISSION_DEVICE_CREATE = "device/create";
	private static final String PERMISSION_DEVICE_UPDATE = "device/update";
	private static final String PERMISSION_DEVICE_VIEW = "device/view";

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = DEVICE_HOME,method = RequestMethod.GET)
	public ModelAndView getDeviceHome() {
		logger.debug("Inside the device controller {}");
		if (AccessManager.hasNoAccess(PERMISSION_DEVICE_GRID)) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		return new ModelAndView(DEVICE_HOME);
	}

	/**
	 * This controller will return view and List<entityDto> as response of
	 * viewAllDevices service
	 * 
	 * @param identityDto
	 * @return ModelAndView
	 */
	@RequestMapping(value = DEVICE_HOME,method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<String> viewAllDevices() {
		logger.debug("Inside view devices");
		IdentityDTO identityDto = new IdentityDTO();
		CumminsResponse<List<EntityDTO>> res = deviceService
		        .getDevices(identityDto);
		Gson gson = new Gson();
		String json = gson.toJson(res);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>(json, responseHeaders,
		        HttpStatus.CREATED);
	}

	@RequestMapping(value = DEVICE_CREATE,method = RequestMethod.GET)
	public ModelAndView getDeviceCreate() {
		if (AccessManager.hasNoAccess(PERMISSION_DEVICE_CREATE)) {
			ModelAndView mv = new ModelAndView(DEVICE_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mav = new ModelAndView(DEVICE_FORM);
		mav.addObject(DEVICE_FORM, new Device());
		return mav;
	}

	@RequestMapping(value = DEVICE_CREATE,method = RequestMethod.POST)
	public ModelAndView insertDevice(@ModelAttribute Device device) {
		String responseMessage = "";
		if (device.getAction() != null
		        && device.getAction().equalsIgnoreCase("Update")) {
			CumminsResponse<StatusMessageDTO> response = updateG2021Device(device);
			responseMessage = "Device updated successfully";
			if (response.getErrorMessage() != null
			        && isNotBlank(response.getErrorMessage().getErrorMessage())) {
				responseMessage = response.getErrorMessage().getErrorMessage();
			}
		} else {
			// In device create
			EntityDTO entityDTO = new EntityDTO();
			entityDTO.setFieldValues(createFieldMapsFromDevice(device));
			CumminsResponse<EntityDTO> response = deviceService
			        .createDevice(entityDTO);
			responseMessage = "Device saved successfully";
			if (response.getErrorMessage() != null
			        && isNotBlank(response.getErrorMessage().getErrorMessage())) {
				responseMessage = response.getErrorMessage().getErrorMessage();
			}
		}
		ModelAndView mav = new ModelAndView(DEVICE_FORM);
		mav.addObject("response", responseMessage);
		mav.addObject("action", "Create");
		mav.addObject(DEVICE_FORM, new Device());
		return mav;
	}

	@RequestMapping(value = DEVICE_UPDATE,method = RequestMethod.POST)
	public ModelAndView updateDevice(@ModelAttribute Device device) {
		ModelAndView mav = new ModelAndView(DEVICE_FORM);
		updateG2021Device(device);
		mav.addObject("response", "SUCCESS");
		mav.addObject("action", "Create");
		mav.addObject(DEVICE_FORM, new Device());
		return mav;
	}

	private CumminsResponse<StatusMessageDTO> updateG2021Device(Device device) {
		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setFieldValues(createFieldMapsFromDevice(device));

		// set the identifier
		FieldMapDTO identifierMap = new FieldMapDTO();
		identifierMap.setKey(IDENTIFIER);
		identifierMap.setValue(device.getIdentifier());
		entityDTO.setIdentifier(identifierMap);
		entityDTO.getFieldValues().add(identifierMap);

		CumminsResponse<StatusMessageDTO> status = deviceService
		        .update(entityDTO);
		return status;
	}

	private List<FieldMapDTO> createFieldMapsFromDevice(Device device) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(ENTITY_NAME, device.getEntityName()));
		fieldMaps.add(createFieldMap(SOURCE_ID, device.getSourceId()));
		fieldMaps.add(createFieldMap(CONFIGURATION, device.getConfiguration()));
		fieldMaps.add(createFieldMap(TAGS, device.getTags()));
		fieldMaps.add(createFieldMap(DEVICE_TYPE, device.getDeviceType()));
		fieldMaps.add(createFieldMap(PROTOCOL_TYPE, device.getProtocolType()));
		fieldMaps.add(createFieldMap(NETWORK_PROTOCOL,
		        device.getNetworkProtocol()));
		fieldMaps.add(createFieldMap(DEVICE_IP, device.getDeviceIp()));
		fieldMaps.add(createFieldMap(DEVICE_PORT, device.getDevicePort()));
		fieldMaps
		        .add(createFieldMap(WRITEBACK_PORT, device.getWritebackPort()));
		fieldMaps.add(createFieldMap(DATASOURCE_NAME,
		        device.getDatasourceName()));
		fieldMaps.add(createFieldMap(PUBLISH,
		        String.valueOf(device.getPublish())));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	// TODO use this for UI call
	@RequestMapping(value = DEVICE_VIEW,method = RequestMethod.POST)
	public ModelAndView getDeviceView(@ModelAttribute FieldMapDTO field) {
		logger.debug("Inside device View");
		if (AccessManager.hasNoAccess(PERMISSION_DEVICE_VIEW)) {
			ModelAndView mv = new ModelAndView(DEVICE_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(DEVICE_FORM);
		mv.addObject("action", "View");
		field.setKey(IDENTIFIER);
		Device device = getDevice(field);
		mv.addObject(DEVICE_FORM, device);
		return mv;
	}

	@RequestMapping(value = DEVICE_UPDATE,method = RequestMethod.GET)
	public ModelAndView getDeviceUpdate(FieldMapDTO field) {
		logger.debug("Inside gensetUpdate");
		if (AccessManager.hasNoAccess(PERMISSION_DEVICE_UPDATE)) {
			ModelAndView mv = new ModelAndView(DEVICE_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(DEVICE_FORM);
		mv.addObject("action", "Update");
		field.setKey("identifier");
		Device device = getDevice(field);
		device.setAction("Update");
		mv.addObject(DEVICE_FORM, device);
		return mv;
	}

	private Device getDevice(FieldMapDTO field) {
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setIdentifier(field);

		CumminsResponse<EntityDTO> res = deviceService
		        .getDeviceDetails(identityDto);

		Device device = new Device();
		if (res != null) {
			EntityDTO entity = res.getEntity();
			if (entity != null) {
				List<FieldMapDTO> fieldValues = entity.getFieldValues();
				for (FieldMapDTO fieldMapDTO : fieldValues) {
					if (fieldMapDTO.getKey().equals(ENTITY_NAME)) {
						device.setEntityName(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(SOURCE_ID)) {
						device.setSourceId(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(CONFIGURATION)) {
						device.setConfiguration(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(TAGS)) {
						device.setTags(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(DEVICE_TYPE)) {
						device.setDeviceType(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(PROTOCOL_TYPE)) {
						device.setProtocolType(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(NETWORK_PROTOCOL)) {
						device.setNetworkProtocol(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(DEVICE_IP)) {
						device.setDeviceIp(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(DEVICE_PORT)) {
						device.setDevicePort(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(WRITEBACK_PORT)) {
						device.setWritebackPort(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(DATASOURCE_NAME)) {
						device.setDatasourceName(fieldMapDTO.getValue());
					} else if (fieldMapDTO.getKey().equals(PUBLISH)) {
						// device.setPublish(publish);(fieldMapDTO.getValue());
						if (fieldMapDTO.getValue().equalsIgnoreCase(TRUE)) {
							device.setPublish(true);
						} else {
							device.setPublish(false);
						}
					}

				}
				StatusDTO status = entity.getEntityStatus();
				if (status != null) {
					if (StringUtils.isNotBlank(status.getStatusName()))
						device.setStatus(status.getStatusName());
				}

				FieldMapDTO identity = entity.getIdentifier();
				if (identity != null) {
					if (StringUtils.isNotBlank(identity.getValue()))
						device.setIdentifier(identity.getValue());
				}
			}
		}
		return device;
	}
}
