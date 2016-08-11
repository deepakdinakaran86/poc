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

import static com.pcs.web.constants.WebConstants.ASSET_TYPE;
import static com.pcs.web.constants.WebConstants.COMMUTER_CREATE;
import static com.pcs.web.constants.WebConstants.COMMUTER_FORM;
import static com.pcs.web.constants.WebConstants.COMMUTER_HOME;
import static com.pcs.web.constants.WebConstants.COMMUTER_TEMPLATE;
import static com.pcs.web.constants.WebConstants.COMMUTER_UPDATE;
import static com.pcs.web.constants.WebConstants.COMMUTER_VIEW;
import static com.pcs.web.constants.WebConstants.DEVICE;
import static com.pcs.web.constants.WebConstants.ENGINE_MODEL;
import static com.pcs.web.constants.WebConstants.ENTITY_NAME;
import static com.pcs.web.constants.WebConstants.ESN;
import static com.pcs.web.constants.WebConstants.HOME_PATH_NAME;
import static com.pcs.web.constants.WebConstants.IDENTIFIER;
import static com.pcs.web.constants.WebConstants.NO_ACCESS;
import static org.springframework.http.HttpStatus.OK;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pcs.web.client.AccessManager;
import com.pcs.web.client.CumminsResponse;
import com.pcs.web.dto.EntityDTO;
import com.pcs.web.dto.EntityTemplateDTO;
import com.pcs.web.dto.FieldMapDTO;
import com.pcs.web.dto.IdentityDTO;
import com.pcs.web.dto.StatusDTO;
import com.pcs.web.model.Commuter;
import com.pcs.web.services.AssetService;

/**
 * @author pcseg199
 * 
 */
@Controller
public class CommuterController {

	private static final Logger logger = LoggerFactory
	        .getLogger(CommuterController.class);

	private static final String PERMISSION_COMMUTER_GRID = "commuter/grid";
	private static final String PERMISSION_COMMUTER_CREATE = "commuter/create";
	private static final String PERMISSION_COMMUTER_UPDATE = "commuter/update";
	private static final String PERMISSION_COMMUTER_VIEW = "commuter/view";

	@Autowired
	AssetService assetService;

	@RequestMapping(value = COMMUTER_HOME,method = RequestMethod.GET)
	public ModelAndView getCommuterHome() {
		logger.debug("Inside the commuter controller {}");
		if (AccessManager.hasNoAccess(PERMISSION_COMMUTER_GRID)) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		return new ModelAndView(COMMUTER_HOME);
	}

	@RequestMapping(value = COMMUTER_HOME,method = RequestMethod.POST)
	public ResponseEntity<String> getAllCommuters() {

		logger.debug("Inside commuter create ");
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(COMMUTER_TEMPLATE);
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setEntityTemplate(entityTemplateDTO);
		CumminsResponse<List<EntityDTO>> res = assetService
		        .viewAllMarkers(identityDto);
		Gson gson = new Gson();
		String json = gson.toJson(res);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<String>(json, responseHeaders,
		        HttpStatus.CREATED);

	}

	@RequestMapping(value = COMMUTER_CREATE,method = RequestMethod.GET)
	public ModelAndView getCommuterCreate() {
		if (AccessManager.hasNoAccess(PERMISSION_COMMUTER_CREATE)) {
			ModelAndView mv = new ModelAndView(COMMUTER_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mav = new ModelAndView(COMMUTER_FORM);
		mav.addObject(COMMUTER_FORM, new Commuter());
		return mav;
	}

	@RequestMapping(value = COMMUTER_CREATE,method = RequestMethod.POST)
	public ModelAndView insertCommuter(@ModelAttribute Commuter commuter) {

		ModelAndView mav = new ModelAndView(COMMUTER_FORM);
		// create entityDto from commuter
		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setFieldValues(createFieldMapsFromCommuter(commuter));

		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName("ACTIVE");
		entityDTO.setEntityStatus(entityStatus);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(COMMUTER_TEMPLATE);
		entityDTO.setEntityTemplate(entityTemplateDTO);
		@SuppressWarnings("rawtypes") CumminsResponse res = null;
		if (StringUtils.isBlank(commuter.getIdentifier())) {
			res = assetService.create(entityDTO);
		} else {
			entityDTO.setIdentifier(createFieldMap(IDENTIFIER,
			        commuter.getIdentifier()));
			entityDTO.getFieldValues().add(entityDTO.getIdentifier());
			res = assetService.update(entityDTO);
		}

		HttpStatus status = res.getStatus();
		if (status == OK) {
			mav.addObject("response", "Commuter Saved Successfully");
			mav.addObject(COMMUTER_FORM, new Commuter());
		} else {
			mav.addObject("response", res.getErrorMessage().getErrorMessage());
			mav.addObject(COMMUTER_FORM, commuter);
		}
		return mav;
	}

	@RequestMapping(value = COMMUTER_VIEW,method = RequestMethod.POST)
	public ModelAndView getCommuterView(FieldMapDTO field) {
		logger.debug("Inside commuterView");
		if (AccessManager.hasNoAccess(PERMISSION_COMMUTER_VIEW)) {
			ModelAndView mv = new ModelAndView(COMMUTER_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(COMMUTER_FORM);
		mv.addObject("action", "View");
		field.setKey("identifier");
		Commuter commuter = getCommuter(field);
		mv.addObject(COMMUTER_FORM, commuter);
		return mv;
	}

	@RequestMapping(value = COMMUTER_UPDATE,method = RequestMethod.POST)
	public ModelAndView getCommuterUpdate(FieldMapDTO field) {
		logger.debug("Inside commuterUpdate");
		if (AccessManager.hasNoAccess(PERMISSION_COMMUTER_UPDATE)) {
			ModelAndView mv = new ModelAndView(COMMUTER_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(COMMUTER_FORM);
		mv.addObject("action", "Update");
		field.setKey(IDENTIFIER);
		Commuter commuter = getCommuter(field);
		mv.addObject(COMMUTER_FORM, commuter);
		return mv;
	}

	private List<FieldMapDTO> createFieldMapsFromCommuter(Commuter commuter) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(ENTITY_NAME, commuter.getEntityName()));
		fieldMaps.add(createFieldMap(DEVICE, commuter.getDevice()));
		fieldMaps.add(createFieldMap(ENGINE_MODEL, commuter.getEngineModel()));
		fieldMaps.add(createFieldMap(ESN, commuter.getEsn()));
		fieldMaps.add(createFieldMap(ASSET_TYPE, commuter.getAssetType()));
		// fieldMaps.add(createFieldMap(IDENTIFIER, commuter.getIdentifier()));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	private Commuter getCommuter(FieldMapDTO field) {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(COMMUTER_TEMPLATE);
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setEntityTemplate(entityTemplateDTO);
		identityDto.setIdentifier(field);

		CumminsResponse<EntityDTO> res = assetService
		        .viewMarkerDetails(identityDto);

		Commuter commuter = new Commuter();
		if (res != null) {
			EntityDTO entity = res.getEntity();
			if (entity != null) {
				List<FieldMapDTO> fieldValues = entity.getFieldValues();
				for (FieldMapDTO fieldMapDTO : fieldValues) {
					if (fieldMapDTO.getKey().equals(ASSET_TYPE))
						commuter.setAssetType(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ENGINE_MODEL))
						commuter.setEngineModel(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ENTITY_NAME))
						commuter.setEntityName(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ESN))
						commuter.setEsn(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(DEVICE))
						commuter.setDevice(fieldMapDTO.getValue());

				}
			}
		}
		commuter.setIdentifier(field.getValue());
		return commuter;
	}
}
