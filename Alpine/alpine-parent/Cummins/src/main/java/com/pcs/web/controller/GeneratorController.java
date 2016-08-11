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
import static com.pcs.web.constants.WebConstants.GENERATOR_CREATE;
import static com.pcs.web.constants.WebConstants.GENERATOR_FORM;
import static com.pcs.web.constants.WebConstants.GENERATOR_HOME;
import static com.pcs.web.constants.WebConstants.GENERATOR_TEMPLATE;
import static com.pcs.web.constants.WebConstants.GENERATOR_UPDATE;
import static com.pcs.web.constants.WebConstants.GENERATOR_VIEW;
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
import com.pcs.web.model.Generator;
import com.pcs.web.services.AssetService;

/**
 * @author pcseg199
 * 
 */
@Controller
public class GeneratorController {

	private static final Logger logger = LoggerFactory
	        .getLogger(GeneratorController.class);

	private static final String PERMISSION_GENERATOR_GRID = "generator/grid";
	private static final String PERMISSION_GENERATOR_CREATE = "generator/create";
	private static final String PERMISSION_GENERATOR_UPDATE = "generator/update";
	private static final String PERMISSION_GENERATOR_VIEW = "generator/view";

	@Autowired
	AssetService assetService;

	@RequestMapping(value = GENERATOR_HOME,method = RequestMethod.GET)
	public ModelAndView getGeneratorHome() {
		logger.debug("Inside the generator controller {}");
		if (AccessManager.hasNoAccess(PERMISSION_GENERATOR_GRID)) {
			ModelAndView mv = new ModelAndView(HOME_PATH_NAME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		return new ModelAndView(GENERATOR_HOME);
	}

	@RequestMapping(value = GENERATOR_HOME,method = RequestMethod.POST)
	public ResponseEntity<String> getAllGenerators() {

		logger.debug("Inside generator create ");
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(GENERATOR_TEMPLATE);
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

	@RequestMapping(value = GENERATOR_CREATE,method = RequestMethod.GET)
	public ModelAndView getGeneratorCreate() {
		if (AccessManager.hasNoAccess(PERMISSION_GENERATOR_CREATE)) {
			ModelAndView mv = new ModelAndView(GENERATOR_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mav = new ModelAndView(GENERATOR_FORM);
		mav.addObject(GENERATOR_FORM, new Generator());
		return mav;
	}

	@RequestMapping(value = GENERATOR_CREATE,method = RequestMethod.POST)
	public ModelAndView insertGenerator(@ModelAttribute Generator generator) {

		ModelAndView mav = new ModelAndView(GENERATOR_FORM);
		// create entityDto from generator
		EntityDTO entityDTO = new EntityDTO();
		entityDTO.setFieldValues(createFieldMapsFromGenerator(generator));

		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName("ACTIVE");
		entityDTO.setEntityStatus(entityStatus);

		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(GENERATOR_TEMPLATE);
		entityDTO.setEntityTemplate(entityTemplateDTO);
		@SuppressWarnings("rawtypes") CumminsResponse res = null;
		if (StringUtils.isBlank(generator.getIdentifier())) {
			res = assetService.create(entityDTO);
		} else {
			entityDTO.setIdentifier(createFieldMap(IDENTIFIER,
			        generator.getIdentifier()));
			entityDTO.getFieldValues().add(entityDTO.getIdentifier());
			res = assetService.update(entityDTO);
		}

		HttpStatus status = res.getStatus();
		if (status == OK) {
			mav.addObject("response", "Generator Saved Successfully");
			mav.addObject(GENERATOR_FORM, new Generator());
		} else {
			mav.addObject("response", res.getErrorMessage().getErrorMessage());
			mav.addObject(GENERATOR_FORM, generator);
		}
		return mav;
	}

	@RequestMapping(value = GENERATOR_VIEW,method = RequestMethod.POST)
	public ModelAndView getGeneratorView(FieldMapDTO field) {
		logger.debug("Inside generatorView");
		if (AccessManager.hasNoAccess(PERMISSION_GENERATOR_VIEW)) {
			ModelAndView mv = new ModelAndView(GENERATOR_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(GENERATOR_FORM);
		mv.addObject("action", "View");
		field.setKey("identifier");
		Generator generator = getGenerator(field);
		mv.addObject(GENERATOR_FORM, generator);
		return mv;
	}

	@RequestMapping(value = GENERATOR_UPDATE,method = RequestMethod.POST)
	public ModelAndView getGeneratorUpdate(FieldMapDTO field) {
		logger.debug("Inside generatorUpdate");
		if (AccessManager.hasNoAccess(PERMISSION_GENERATOR_UPDATE)) {
			ModelAndView mv = new ModelAndView(GENERATOR_HOME);
			mv.addObject("response", NO_ACCESS);
			return mv;
		}
		ModelAndView mv = new ModelAndView(GENERATOR_FORM);
		mv.addObject("action", "Update");
		field.setKey(IDENTIFIER);
		Generator generator = getGenerator(field);
		mv.addObject(GENERATOR_FORM, generator);
		return mv;
	}

	private List<FieldMapDTO> createFieldMapsFromGenerator(Generator generator) {
		List<FieldMapDTO> fieldMaps = new ArrayList<FieldMapDTO>();
		fieldMaps.add(createFieldMap(ENTITY_NAME, generator.getEntityName()));
		fieldMaps.add(createFieldMap(DEVICE, generator.getDevice()));
		fieldMaps.add(createFieldMap(ENGINE_MODEL, generator.getEngineModel()));
		fieldMaps.add(createFieldMap(ESN, generator.getEsn()));
		fieldMaps.add(createFieldMap(ASSET_TYPE, generator.getAssetType()));
		// fieldMaps.add(createFieldMap(IDENTIFIER, generator.getIdentifier()));
		return fieldMaps;
	}

	private FieldMapDTO createFieldMap(String key, String value) {
		return new FieldMapDTO(key, value);
	}

	private Generator getGenerator(FieldMapDTO field) {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateDTO.setEntityTemplateName(GENERATOR_TEMPLATE);
		IdentityDTO identityDto = new IdentityDTO();
		identityDto.setEntityTemplate(entityTemplateDTO);
		identityDto.setIdentifier(field);

		CumminsResponse<EntityDTO> res = assetService
		        .viewMarkerDetails(identityDto);

		Generator generator = new Generator();
		if (res != null) {
			EntityDTO entity = res.getEntity();
			if (entity != null) {
				List<FieldMapDTO> fieldValues = entity.getFieldValues();
				for (FieldMapDTO fieldMapDTO : fieldValues) {
					if (fieldMapDTO.getKey().equals(ASSET_TYPE))
						generator.setAssetType(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ENGINE_MODEL))
						generator.setEngineModel(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ENTITY_NAME))
						generator.setEntityName(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(ESN))
						generator.setEsn(fieldMapDTO.getValue());
					else if (fieldMapDTO.getKey().equals(DEVICE))
						generator.setDevice(fieldMapDTO.getValue());

				}
			}
		}
		generator.setIdentifier(field.getValue());
		return generator;
	}

}
