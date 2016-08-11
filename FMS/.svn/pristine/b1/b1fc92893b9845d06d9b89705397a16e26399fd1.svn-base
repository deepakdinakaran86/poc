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

import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_CREATE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_DEF_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_DEF_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_DELETE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_SAVE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_CREATE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_ICON_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_SAVE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_VIEW_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_TYPE_VIEW_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.POI_VIEW_HOME_VIEW;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSResponseManager;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.Image;
import com.pcs.fms.web.dto.PoiDTO;
import com.pcs.fms.web.dto.PoiTypeDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.FileUploadForm;
import com.pcs.fms.web.model.Poi;
import com.pcs.fms.web.model.PoiType;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.FileService;
import com.pcs.fms.web.services.POIService;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class POIController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(POIController.class);

	@Autowired
	private POIService poiService;

	@Autowired
	private FileService fileServer;

	@Value("${poitype.create.success}")
	private String poiTypeCreateSuccess;

	@Value("${poitype.create.failure}")
	private String poiTypeCreateFailure;

	@Value("${poi.create.success}")
	private String poiCreateSuccess;

	@Value("${poi.update.success}")
	private String poiUpdateSuccess;

	@Value("${poi.delete.success}")
	private String poiDeletedSuccess;

	@RequestMapping(value = POI_TYPE_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(POI_TYPE_HOME_VIEW);
		return mv;
	}

	@RequestMapping(value = POI_TYPE_VIEW_PATH_NAME,method = RequestMethod.POST)
	public ModelAndView poiTypeView(@ModelAttribute FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(POI_TYPE_VIEW_HOME_VIEW);
		FMSResponse<PoiTypeDTO> poiDetails = poiService.getPOITypeDetails(field
		        .getValue());
		PoiTypeDTO poiType = poiDetails.getEntity();
		PoiType poi = getPoiFromDto(poiType);
		poi.setDomainName(FMSAccessManager.getCurrentDomain());
		getPOITypeImage(poi);
		mv.addObject(POI_TYPE_VIEW_HOME_VIEW, poi);
		mv.addObject("action", "edit");
		return mv;
	}

	private PoiType getPoiFromDto(PoiTypeDTO poiTypeDto) {
		PoiType poiType = null;
		if (poiTypeDto != null) {
			poiType = new PoiType();
			poiType.setPoiType(poiTypeDto.getPoiType());
			poiType.setStatus("ACTIVE");
			Gson gson = new Gson();
			poiType.setPoiTypeValues(gson.toJson(poiTypeDto.getPoiTypeValues()));
		}
		return poiType;
	}

	@RequestMapping(value = POI_TYPE_CREATE_PATH_NAME,
	        method = RequestMethod.POST)
	public ModelAndView createPage() {
		ModelAndView mv = new ModelAndView(POI_TYPE_VIEW_HOME_VIEW);
		PoiType poiType = new PoiType();
		poiType.setStatus("ACTIVE");
		mv.addObject(POI_TYPE_VIEW_HOME_VIEW, poiType);
		mv.addObject("action", "create");
		return mv;
	}

	private List<String> poiTypeValues(String values) {
		List<String> arraylist = null;
		if (StringUtils.isNotEmpty(values)) {
			String[] split = values.split(",");
			if (split.length > 0) {
				arraylist = Arrays.asList(split);
			}
		}
		return arraylist;
	}

	private void getPOITypeImage(PoiType poiType) {

		Image image = new Image();
		image.setDomain(poiType.getDomainName());
		image.setModule(POI_TYPE_FOLDER_NAME);
		image.setFilename(poiType.getPoiType() + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowload(image);
		poiType.setImage(userImage);
	}

	private void getPOIImage(Poi poi) {

		Image image = new Image();
		image.setDomain(poi.getDomainName());
		image.setModule(POI_FOLDER_NAME);
		image.setFilename(poi.getPoiName() + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowload(image);
		poi.setImage(userImage);
	}

	private void savePOITypeImage(PoiType poiType) {

		FileUploadForm userImage = new FileUploadForm();
		userImage.setFileDatas(poiType.getFileDatas());

		Image image = new Image();
		image.setDomain(poiType.getDomainName());
		image.setModule(POI_TYPE_FOLDER_NAME);
		image.setFilename(poiType.getPoiType() + FILE_IMAGE_TYPE);
		CommonsMultipartFile[] fileDatas = userImage.getFileDatas();

		Image iconDetails = new Image();
		iconDetails.setDomain(poiType.getDomainName());
		iconDetails.setModule(POI_TYPE_ICON_FOLDER_NAME);
		iconDetails.setFilename(poiType.getPoiType() + FILE_IMAGE_TYPE);

		if (fileDatas.length > 0) {
			fileServer.doUpload(fileDatas[0], image);
		}

		if (StringUtils.isNotEmpty(poiType.getIcon())) {
			fileServer.doUpload(poiType.getIcon(), iconDetails);
		}

	}

	private void savePOIImage(Poi poi) {

		FileUploadForm userImage = new FileUploadForm();
		userImage.setFileDatas(poi.getFileDatas());

		Image image = new Image();
		image.setDomain(poi.getDomainName());
		image.setModule(POI_FOLDER_NAME);
		image.setFilename(poi.getPoiName() + FILE_IMAGE_TYPE);
		CommonsMultipartFile[] fileDatas = userImage.getFileDatas();
		if (fileDatas.length > 0) {
			fileServer.doUpload(fileDatas[0], image);
		}
	}

	private void deleteImage(String poiName, String domainName) {

		Image image = new Image();
		image.setDomain(domainName);
		image.setModule(POI_FOLDER_NAME);
		image.setFilename(poiName + FILE_IMAGE_TYPE);
		fileServer.doDelete(image);
	}

	@RequestMapping(value = POI_TYPE_SAVE_PATH_NAME,method = RequestMethod.POST)
	public ModelAndView poiTypeSave(@ModelAttribute PoiType poiType,
	        RedirectAttributes redirect) {
		String response = null;
		poiType.setDomainName(FMSAccessManager.getCurrentDomain());
		PoiTypeDTO poiTypeDto = new PoiTypeDTO();
		poiTypeDto.setPoiType(poiType.getPoiType());
		poiTypeDto.setPoiTypeValues(poiTypeValues(poiType.getPoiTypeValues()));
		poiTypeDto.setStatus(poiType.getStatus());
		poiTypeDto.setDomainName(poiType.getDomainName());
		FMSResponse<StatusMessageDTO> savePOI = poiService
		        .savePOIType(poiTypeDto);
		RedirectView redirectView = new RedirectView(POI_TYPE_HOME_VIEW);
		ModelAndView mv = new ModelAndView(redirectView);
		if (savePOI.getEntity() != null) {
			StatusMessageDTO status = savePOI.getEntity();
			if ("SUCCESS".equals(status.getStatus().name())) {
				savePOITypeImage(poiType);
				response = FMSResponseManager.success(poiTypeCreateSuccess);
				redirect.addFlashAttribute("response", response);
			} else {
				mv = new ModelAndView(POI_TYPE_VIEW_HOME_VIEW);
				response = FMSResponseManager.error(poiTypeCreateFailure);
				mv.addObject("response", response);
				mv.addObject(POI_TYPE_VIEW_HOME_VIEW, poiType);
				mv.addObject("action", "error");
			}
		} else {
			mv = new ModelAndView(POI_TYPE_VIEW_HOME_VIEW);
			if (savePOI.getErrorMessage() != null) {
				response = FMSResponseManager.error(savePOI.getErrorMessage()
				        .getErrorMessage());
				mv.addObject("response", response);
			}
			mv.addObject(POI_TYPE_VIEW_HOME_VIEW, poiType);
			mv.addObject("action", "error");
		}

		return mv;
	}

	@RequestMapping(value = POI_DEF_PATH_NAME,method = RequestMethod.GET)
	public ModelAndView poiHome(HttpServletRequest request) {
		return new ModelAndView(POI_DEF_HOME_VIEW);
	}

	@RequestMapping(value = POI_CREATE_PATH_NAME,method = RequestMethod.POST)
	public ModelAndView poiCreate() {
		ModelAndView mv = new ModelAndView(POI_VIEW_HOME_VIEW);
		Poi poi = new Poi();
		poi.setStatus("ACTIVE");
		poi.setAction("create");
		mv.addObject(POI_VIEW_HOME_VIEW, poi);
		mv.addObject("action", "create");
		return mv;
	}

	@RequestMapping(value = POI_VIEW_HOME_VIEW,method = RequestMethod.POST)
	public ModelAndView poiView(@ModelAttribute FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(POI_VIEW_HOME_VIEW);
		IdentityDTO identifier = new IdentityDTO();

		FieldMapDTO identity = new FieldMapDTO();
		identity.setKey("identifier");
		identity.setValue(field.getValue());
		identifier.setIdentifier(identity);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName("Poi");
		identifier.setEntityTemplate(entityTemplate);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(field.getKey());
		identifier.setDomain(domain);

		FMSResponse<PoiDTO> poiDetails = poiService.getPOIDetails(identifier);
		String error = "";
		Poi poi = convertDtoToModel(poiDetails.getEntity());
		if (poiDetails.getEntity() != null) {
			getPOIImage(poi);
			poi.setAction("edit");
		} else {
			if (poiDetails.getErrorMessage() != null) {
				error = poiDetails.getErrorMessage().getErrorMessage();
			}
		}
		mv.addObject(POI_VIEW_HOME_VIEW, poi);
		mv.addObject("action", "edit");
		mv.addObject("error", error);
		return mv;
	}

	private Poi convertDtoToModel(PoiDTO poiDto) {
		Poi poi = null;
		if (poiDto != null) {
			poi = new Poi();
			poi.setPoiName(poiDto.getPoiName());
			poi.setDescription(poiDto.getDescription());
			poi.setDomainName(poiDto.getDomainName());
			poi.setLatitude(poiDto.getLatitude());
			poi.setLongitude(poiDto.getLongitude());
			poi.setStatus(poiDto.getStatus());
			poi.setRadius(poiDto.getRadius());
			poi.setPoiType(poiDto.getPoiType());
			Gson gson = new Gson();
			poi.setPoiIdentifier(gson.toJson(poiDto.getPoiIdentifier()));
			poi.setPoiTypeValues(gson.toJson(poiDto.getPoiTypeValues()));
		}
		return poi;
	}

	private PoiDTO convertModelToDto(Poi poi) {
		PoiDTO poiDto = null;
		if (poi != null) {
			poiDto = new PoiDTO();
			if ("edit".equals(poi.getAction())) {
				poi.setPoiName(poi.getPoiNameSub());
			}
			poiDto.setPoiName(poi.getPoiName());
			poiDto.setDescription(poi.getDescription());
			poiDto.setDomainName(poi.getDomainName());
			poiDto.setLatitude(poi.getLatitude());
			poiDto.setLongitude(poi.getLongitude());
			poiDto.setStatus(poi.getStatus());
			poiDto.setRadius(poi.getRadius());
			poiDto.setPoiType(poi.getPoiType());
			Gson gson = new Gson();
			List<FieldMapDTO> fromJson = gson.fromJson(poi.getPoiTypeValues(),
			        List.class);
			FieldMapDTO identifier = gson.fromJson(poi.getPoiIdentifier(),
			        FieldMapDTO.class);
			poiDto.setPoiIdentifier(identifier);
			poiDto.setPoiTypeValues(fromJson);
		}
		return poiDto;
	}

	@RequestMapping(value = POI_SAVE_PATH_NAME,method = RequestMethod.POST)
	public ModelAndView poiSave(@ModelAttribute Poi poi,
	        RedirectAttributes redirect) {
		PoiDTO poiDto = convertModelToDto(poi);
		String response = null;
		RedirectView redirectView = new RedirectView(POI_DEF_HOME_VIEW);
		ModelAndView mv = new ModelAndView(redirectView);
		if ("create".equals(poi.getAction())) {
			FMSResponse<PoiDTO> savePoi = poiService.savePoi(poiDto);
			if (savePoi.getEntity() != null) {
				savePOIImage(poi);
				response = FMSResponseManager.success(poiCreateSuccess);
				redirect.addFlashAttribute("response", response);
			} else {
				if (savePoi.getErrorMessage() != null) {
					response = FMSResponseManager.error(savePoi
					        .getErrorMessage().getErrorMessage());
					mv = handleErrorPOI(poi, response, "createerror");
				}
			}
		} else if ("edit".equals(poi.getAction())) {
			FMSResponse<PoiDTO> updatePoi = poiService.updatePoi(poiDto);
			if (updatePoi.getEntity() != null) {
				savePOIImage(poi);
				response = FMSResponseManager.success(poiUpdateSuccess);
				redirect.addFlashAttribute("response", response);
			} else {
				if (updatePoi.getErrorMessage() != null) {
					response = FMSResponseManager.error(updatePoi
					        .getErrorMessage().getErrorMessage());
					mv = handleErrorPOI(poi, response, "editerror");
				}
			}
		}
		return mv;
	}

	private ModelAndView handleErrorPOI(Poi poi, String response, String action) {

		ModelAndView mv = new ModelAndView(POI_VIEW_HOME_VIEW);
		mv.addObject(POI_VIEW_HOME_VIEW, poi);
		mv.addObject("action", action);
		mv.addObject("response", response);
		return mv;
	}

	@RequestMapping(value = POI_DELETE_PATH_NAME,method = RequestMethod.POST)
	public ModelAndView poiDelete(@ModelAttribute Poi poi,
	        RedirectAttributes redirect) {
		IdentityDTO identifier = new IdentityDTO();

		Gson gson = new Gson();
		FieldMapDTO identity = gson.fromJson(poi.getPoiIdentifier(),
		        FieldMapDTO.class);
		identifier.setIdentifier(identity);

		com.pcs.fms.web.dto.PlatformEntityDTO platformEntityDTO = new com.pcs.fms.web.dto.PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType("MARKER");
		identifier.setPlatformEntity(platformEntityDTO);

		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName("Poi");
		identifier.setEntityTemplate(entityTemplate);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(poi.getDomainName());
		identifier.setDomain(domain);

		FMSResponse<StatusMessageDTO> deleteStatus = poiService
		        .deletePoi(identifier);
		StatusMessageDTO status = deleteStatus.getEntity();
		if (status != null) {
			if (status.getStatus().name().equals("SUCCESS")) {
				deleteImage(poi.getPoiName(), poi.getDomainName());
				redirect.addFlashAttribute("response",
				        FMSResponseManager.success(poiDeletedSuccess));
			}
		} else {
			if (deleteStatus.getErrorMessage() != null) {
				String response = FMSResponseManager.error(deleteStatus
				        .getErrorMessage().getErrorMessage());
				redirect.addFlashAttribute("response", response);
			}
		}
		RedirectView redirectView = new RedirectView(POI_DEF_HOME_VIEW);
		return new ModelAndView(redirectView);
	}
}
