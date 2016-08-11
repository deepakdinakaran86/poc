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

import static com.pcs.fms.web.constants.FMSWebConstants.REDIRECT;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_CREATE;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_CREATE_SERVICE;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_TYPES_CREATE;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_TYPES_CREATE_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_TYPES_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_TYPE_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.TAG_VIEW;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.constants.FMSWebConstants;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.Tag;
import com.pcs.fms.web.dto.TagTypeTemplate;
import com.pcs.fms.web.model.MapTagToTemplateModel;
import com.pcs.fms.web.model.TagModel;
import com.pcs.fms.web.model.TagTypeTemplateModel;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.TagService;
import com.pcs.fms.web.services.TagTypeService;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */

@Controller
public class TagController extends BaseService {

	private static final Logger logger = LoggerFactory
			.getLogger(TagController.class);

	@Autowired
	private TagTypeService tagTypeService;

	@Autowired
	private TagService tagService;

	@Value("${tagtype.create.success}")
	private String tagTypeCreateSuccess;

	@Value("${tagtype.create.failure}")
	private String tagTypeCreateFailure;

	@Value("${tag.create.success}")
	private String tagCreateSuccess;

	@Value("${tag.create.failure}")
	private String tagCreateFailure;

	@Value("${tag.update.success}")
	private String tagUpdateSuccess;

	@Value("${tag.update.failure}")
	private String tagUpdateFailure;

	@RequestMapping(value = TAG_TYPE_HOME, method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView(TAG_TYPE_HOME);
	}

	@RequestMapping(value = TAG_TYPES_CREATE, method = RequestMethod.GET)
	public ModelAndView create(HttpServletRequest request,
			RedirectAttributes redirect) {
		return new ModelAndView(TAG_TYPES_CREATE_VIEW);
	}

	@RequestMapping(value = FMSWebConstants.TAG_TYPES_CREATE_SERVICE, method = RequestMethod.POST)
	public ModelAndView createTagTypeService(
			@ModelAttribute TagTypeTemplateModel tagTypeTemplateModel,
			RedirectAttributes redirect) {
		String currDomain = FMSAccessManager.getCurrentDomain();
		FMSResponse<StatusMessageDTO> createTagTypeResp = tagTypeService
				.createTagType(tagTypeTemplateModel, currDomain);
		Gson gson = new Gson();
		String json = null;
		String response = null;
		RedirectView redirectView = null;
		if (createTagTypeResp.getEntity() != null) {
			response = FMSResponseManager.success(tagTypeCreateSuccess);
			json = gson.toJson(createTagTypeResp.getEntity());
		} else {
			response = FMSResponseManager.error(createTagTypeResp
					.getErrorMessage().getErrorMessage());
			redirectView = new RedirectView(TAG_TYPES_CREATE_VIEW);
			ModelAndView mv = new ModelAndView(redirectView);
			redirect.addFlashAttribute(RESPONSE, response);
			redirect.addFlashAttribute("createTagTypeFields", gson.toJson(tagTypeTemplateModel));
			return mv;
		}
		redirectView = new RedirectView(TAG_TYPE_HOME);
		ModelAndView mv = new ModelAndView(redirectView);
		redirect.addFlashAttribute("createTagTypeResponse", json);
		redirect.addFlashAttribute("response", response);

		return mv;

	}

	@RequestMapping(value = TAG_TYPES_VIEW, method = RequestMethod.POST)
	public ModelAndView viewTagType(@RequestParam("tag_type_name") String tag_type_name) {
		ModelAndView mv = new ModelAndView(TAG_TYPES_CREATE);
		String currDomain = FMSAccessManager.getCurrentDomain();
		FMSResponse<TagTypeTemplate> viewTagTypeResp = tagTypeService
				.viewTagType(tag_type_name, currDomain);
		Gson gson = new Gson();
		String json = gson.toJson(viewTagTypeResp.getEntity());
		mv.addObject("viewTagTypeResponse", json);
		return mv;

	}

	@RequestMapping(value = TAG_CREATE, method = RequestMethod.GET)
	public ModelAndView createTag(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(TAG_CREATE);
		String currDomain = FMSAccessManager.getCurrentDomain();
		FMSResponse<List<EntityTemplateDTO>> listOfTagTypes = tagTypeService
				.getAllTagTypes(currDomain);
		// FMSResponse<List<EntityTemplateDTO>> listOfTemplates =
		// tagTypeService.getAllTemplates(currDomain);
		Gson gson = new Gson();
		String json1 = gson.toJson(listOfTagTypes.getEntity());
		// String json2 = gson.toJson(listOfTemplates.getEntity());
		mv.addObject("listOfTagTypesResp", json1);
		mv.addObject("tag_create_service", new TagModel());
		
		// mv.addObject("listOfTemplatesResp", json2);
		return mv;
	}

	@RequestMapping(value = TAG_CREATE_SERVICE, method = RequestMethod.POST)
	public ModelAndView createTagService(@ModelAttribute TagModel tagModel,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		Gson gson = new Gson();
		String currDomain = FMSAccessManager.getCurrentDomain();
		String mapTagToTemplatesResJson = null;
		String response = null;

		// Check TagId is present or not in the payload, To create a tag or
		// Update a tag
		if (isEmpty(tagModel.getTagId())) {
			FMSResponse<EntityDTO> createTagResponse = tagService.createTag(
					tagModel, currDomain);

			if (createTagResponse.getEntity() != null) {
				String createTagJson = gson.toJson(createTagResponse
						.getEntity());

				// Check if any templates are to be mapped
				if (!tagModel.getAssignToTemplates().isEmpty()) {
					MapTagToTemplateModel mapTagToTemplateModel = constructMapTagToTemplatesPayload(
							tagModel, currDomain);
					FMSResponse<StatusMessageDTO> mapTagToTemplatesResponse = tagService
							.mapTagToTemplates(mapTagToTemplateModel);

					if (mapTagToTemplatesResponse.getEntity() != null) {
						mapTagToTemplatesResJson = gson
								.toJson(mapTagToTemplatesResponse.getEntity());
						redirectAttributes.addFlashAttribute(
								"mapTagToTemplatesResponse",
								mapTagToTemplatesResJson);
						redirectAttributes.addFlashAttribute(
								"createTagResponse", createTagJson);
						response = FMSResponseManager.success(tagCreateSuccess);
						mv = new ModelAndView(REDIRECT + TAG_HOME);
					} else {
						if (mapTagToTemplatesResponse.getErrorMessage() != null) {
							response = FMSResponseManager
									.error(mapTagToTemplatesResponse
											.getErrorMessage()
											.getErrorMessage());
						}
					}
				} else {
					response = FMSResponseManager.success(tagCreateSuccess);
					mv = new ModelAndView(REDIRECT + TAG_HOME);

				}
			} else {
				response = FMSResponseManager.error(createTagResponse
						.getErrorMessage().getErrorMessage());
				mv = new ModelAndView(REDIRECT + TAG_CREATE);
				redirectAttributes.addFlashAttribute("createTagFields", gson.toJson(tagModel));
				redirectAttributes.addFlashAttribute(RESPONSE, response);
			}
		} else {

			// Update Templates to be mapped to tag
			if (tagModel.getAssignToTemplates() != null) {
				MapTagToTemplateModel mapTagToTemplateModel = constructMapTagToTemplatesPayload(
						tagModel, currDomain);

				FMSResponse<StatusMessageDTO> mapTagToTemplatesResponse = tagService
						.mapTagToTemplates(mapTagToTemplateModel);

				// mv.addObject("mapTagToTemplatesResponse", json2);

				// Response Messages
				if (mapTagToTemplatesResponse.getEntity() != null) {
					mapTagToTemplatesResJson = gson
							.toJson(mapTagToTemplatesResponse.getEntity());
					response = FMSResponseManager.success(tagUpdateSuccess);
				} else {
					if (mapTagToTemplatesResponse.getErrorMessage() != null) {
						response = FMSResponseManager
								.error(mapTagToTemplatesResponse
										.getErrorMessage().getErrorMessage());
					}
				}
			} else {
				response = FMSResponseManager.success(tagUpdateSuccess);
			}
			mv = new ModelAndView(REDIRECT + TAG_HOME);
			redirectAttributes.addFlashAttribute("updateTagResponse",
					mapTagToTemplatesResJson);
			redirectAttributes.addFlashAttribute("response", response);
		}

		return mv;
	}

	private MapTagToTemplateModel constructMapTagToTemplatesPayload(
			TagModel tagModel, String currDomain) {
		Tag tag = new Tag();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(currDomain);
		tag.setDomain(domain);
		tag.setName(tagModel.getTagName());
		tag.setTagType(tagModel.getTagType());

		MapTagToTemplateModel mapTagToTemplateModel = new MapTagToTemplateModel();
		mapTagToTemplateModel.setTag(tag);
		mapTagToTemplateModel.setTemplates(constructAssignTemplates(
				tagModel.getAssignToTemplates(), tag));

		return mapTagToTemplateModel;
	}

	private List<EntityTemplateDTO> constructAssignTemplates(
			List<String> assignToTemplates, Tag tag) {
		List<EntityTemplateDTO> templates = new ArrayList<EntityTemplateDTO>();
		for (String template : assignToTemplates) {
			EntityTemplateDTO templateDTO = new EntityTemplateDTO();
			switch (template) {
			case "DefaultTenant":
				templateDTO.setDomain(tag.getDomain());
				templateDTO.setPlatformEntityType(FMSWebConstants.TENANT);
				templateDTO.setEntityTemplateName(template);
				break;

			case "DefaultUser":
				templateDTO.setDomain(tag.getDomain());
				templateDTO.setPlatformEntityType(FMSWebConstants.USER);
				templateDTO.setEntityTemplateName(template);
				break;

			case "Point":
				templateDTO.setDomain(tag.getDomain());
				templateDTO.setPlatformEntityType(FMSWebConstants.MARKER);
				templateDTO.setEntityTemplateName(template);
				break;

			case "Asset":
				templateDTO.setDomain(tag.getDomain());
				templateDTO.setPlatformEntityType(FMSWebConstants.MARKER);
				templateDTO.setEntityTemplateName(template);
				break;

			default:
				throw new IllegalArgumentException("Invalid template passed"
						+ template);
			}
			templates.add(templateDTO);
		}
		return templates;
	}

	@RequestMapping(value = TAG_HOME, method = RequestMethod.GET)
	public ModelAndView listTags(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(TAG_HOME);
		String currDomain = FMSAccessManager.getCurrentDomain();
		Gson gson = new Gson();
		FMSResponse<List<EntityTemplateDTO>> listOfTagTypes = tagTypeService
				.getAllTagTypes(currDomain);
		String listOfTagTypesJson = gson.toJson(listOfTagTypes.getEntity());
		mv.addObject("listOfTagTypesResp", listOfTagTypesJson);
		return mv;
	}

	@RequestMapping(value = TAG_VIEW, method = RequestMethod.POST)
	public ModelAndView viewTag(@ModelAttribute TagModel tagModel) {
		ModelAndView mv = new ModelAndView(TAG_VIEW);
		String currDomain = FMSAccessManager.getCurrentDomain();
		Gson gson = new Gson();
		FMSResponse<EntityDTO> tagDetailsResponse = tagService.viewTag(
				tagModel, currDomain);
		String tagDetailsResponseJson = gson.toJson(tagDetailsResponse
				.getEntity());

		if (tagDetailsResponse != null) {
			FMSResponse<List<EntityTemplateDTO>> taggedTemplatesResponse = tagService
					.viewTaggedTemplates(tagModel, currDomain);
			String taggedTemplatesResponseJson = gson
					.toJson(taggedTemplatesResponse.getEntity());
			if (taggedTemplatesResponseJson != null) {
				mv.addObject("mappedTemplatesResp", taggedTemplatesResponseJson);
			}
		}
		mv.addObject("viewTagResp", tagDetailsResponseJson);
		mv.addObject("tag_create_service", new TagModel());
		return mv;
	}

}
