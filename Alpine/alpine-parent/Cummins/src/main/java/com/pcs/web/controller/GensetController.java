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
import static com.pcs.web.constants.WebConstants.DEVICE;
import static com.pcs.web.constants.WebConstants.ENGINE_MODEL;
import static com.pcs.web.constants.WebConstants.ENTITY_NAME;
import static com.pcs.web.constants.WebConstants.ESN;
import static com.pcs.web.constants.WebConstants.GENSET_CREATE;
import static com.pcs.web.constants.WebConstants.GENSET_FORM;
import static com.pcs.web.constants.WebConstants.GENSET_HOME;
import static com.pcs.web.constants.WebConstants.GENSET_TEMPLATE;
import static com.pcs.web.constants.WebConstants.GENSET_UPDATE;
import static com.pcs.web.constants.WebConstants.GENSET_VIEW;
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
import com.pcs.web.model.Genset;
import com.pcs.web.services.AssetService;

/**
 * @author pcseg199
 * 
 */
@Controller
public class GensetController {

	private static final Logger logger = LoggerFactory
	        .getLogger(GensetController.class);

	private static final String PERMISSION_GENSET_GRID = "genset/grid";
	private static final String PERMISSION_GENSET_CREATE = "genset/create";
	private static final String PERMISSION_GENSET_UPDATE = "genset/update";
	private static final String PERMISSION_GENSET_VIEW = "genset/view";

	@Autowired
	AssetService assetService;

	@RequestMapping(value = GENSET_HOME,method = RequestMethod.GET)
	public ModelAndView getGensetHome() {
		logger.debug("Inside the genset controller {}");
		if (AccessManager.hasNoAccess(PERMISSION_GENSET_GRID)) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		return new ModelAndView(GENSET_HOME);
	}

	@RequestMapping(value = GENSET_HOME,method = RequestMethod.POST)
	public ResponseEntity<String> getAllGensets() {

		logger.debug("Inside genset create ");
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(GENSET_TEMPLATE);
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

	@RequestMapping(value = GENSET_CREATE,method = RequestMethod.GET)
	public ModelAndView getGensetCreate() {
		if (AccessManager.hasNoAccess(PERMISSION_GENSET_CREATE)) {
			ModelAndView mv = new ModelAndView(GENSET_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mav = new ModelAndView(GENSET_FORM);
		mav.addObject(GENSET_FORM, new Genset());
		return mav;
	}

	@RequestMapping(value = GENSET_CREATE,method = RequestMethod.POST)
	public ModelAndView insertGenset(@ModelAttribute Genset genset) {

		ModelAndView mav = new ModelAndView(GENSET_FORM);
		// create entityDto from genset
		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setFieldValues(createFieldMapsFromGenset(genset));

		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName("ACTIVE");
		entityDTO.setEntityStatus(entityStatus);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(GENSET_TEMPLATE);
		entityDTO.setEntityTemplate(entityTemplateDTO);
		@SuppressWarnings("rawtypes") CumminsResponse res = null;
		if (StringUtils.isBlank(genset.getIdentifier())) {
			res = assetService.create(entityDTO);
		} else {
			entityDTO.setIdentifier(createFieldMap(IDENTIFIER,
			        genset.getIdentifier()));
			entityDTO.getFieldValues().add(entityDTO.getIdentifier());
			res = assetService.update(entityDTO);
		}

		HttpStatus status = res.getStatus();
		if (status == OK) {
			mav.addObject("response", "Genset Saved Successfully");
			mav.addObject(GENSET_FORM, new Genset());
		} else {
			mav.addObject("response", res.getErrorMessage().getErrorMessage());
			mav.addObject(GENSET_FORM, genset);
		}
		return mav;
	}

	@RequestMapping(value = GENSET_VIEW,method = RequestMethod.POST)
	public ModelAndView getGensetView(FieldMapDTO field) {
		logger.debug("Inside gensetView");
		if (AccessManager.hasNoAccess(PERMISSION_GENSET_VIEW)) {
			ModelAndView mv = new ModelAndView(GENSET_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(GENSET_FORM);
		mv.addObject("action", "View");
		field.setKey("identifier");
		Genset genset = getGenset(field);
		mv.addObject(GENSET_FORM, genset);
		return mv;
	}

	@RequestMapping(value = GENSET_UPDATE,method = RequestMethod.POST)
	public ModelAndView getGensetUpdate(FieldMapDTO field) {
		logger.debug("Inside gensetUpdate");
		if (AccessManager.hasNoAccess(PERMISSION_GENSET_UPDATE)) {
			ModelAndView mv = new ModelAndView(GENSET_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(GENSET_FORM);
		mv.addObject("action", "Update");
		field.setKey(IDENTIFIER);
		Genset genset = getGenset(field);
		mv.addObject(GENSET_FORM, genset);
		return mv;
	}

	private List<FieldMapDTO> createFieldMapsFromGenset(Genset genset) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(ENTITY_NAME, genset.getEntityName()));
		fieldMaps.add(createFieldMap(DEVICE, genset.getDevice()));
		fieldMaps.add(createFieldMap(ENGINE_MODEL, genset.getEngineModel()));
		fieldMaps.add(createFieldMap(ESN, genset.getEsn()));
		fieldMaps.add(createFieldMap(ASSET_TYPE, genset.getAssetType()));
		// fieldMaps.add(createFieldMap(IDENTIFIER, genset.getIdentifier()));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	private Genset getGenset(FieldMapDTO field) {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(GENSET_TEMPLATE);
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setEntityTemplate(entityTemplateDTO);
		identityDto.setIdentifier(field);

		CumminsResponse<EntityDTO> res = assetService
		        .viewMarkerDetails(identityDto);

		Genset genset = new Genset();
		if (res != null) {
			EntityDTO entity = res.getEntity();
			if (entity != null) {
				List<FieldMapDTO> fieldValues = entity.getFieldValues();
				for (FieldMapDTO fieldMapDTO : fieldValues) {
					if (fieldMapDTO.getKey().equals(ASSET_TYPE))
						genset.setAssetType(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ENGINE_MODEL))
						genset.setEngineModel(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ENTITY_NAME))
						genset.setEntityName(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ESN))
						genset.setEsn(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(DEVICE))
						genset.setDevice(fieldMapDTO.getValue());

				}
			}
		}
		genset.setIdentifier(field.getValue());
		return genset;
	}

}
