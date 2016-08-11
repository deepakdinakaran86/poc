package com.pcs.fms.web.controller;

import static com.pcs.fms.web.constants.FMSWebConstants.ASSET_ENTITY_TEMP_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.EDIT;
import static com.pcs.fms.web.constants.FMSWebConstants.ENTITY;
import static com.pcs.fms.web.constants.FMSWebConstants.FILE_IMAGE_TYPE;
import static com.pcs.fms.web.constants.FMSWebConstants.MARKER;
import static com.pcs.fms.web.constants.FMSWebConstants.POINT_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.RESPONSE;
import static com.pcs.fms.web.constants.FMSWebConstants.TEMPLATE;
import static com.pcs.fms.web.constants.FMSWebConstants.UPDATE;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_CANCEL;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_DASHBOARD;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_HISTORY;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_HOME_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_MANAGEMENT_LIST;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_MANAGEMENT_LIST_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TEMPLATE;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_CREATE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_HOME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_ICON_FOLDER_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_SAVE_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_VIEW;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TYPE_VIEW_PATH_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_VIEW;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.Actor;
import com.pcs.fms.web.dto.AssetDTO;
import com.pcs.fms.web.dto.AssetResponseDTO;
import com.pcs.fms.web.dto.AssetTypeTemplateDTO;
import com.pcs.fms.web.dto.AttachHierarchySearchDTO;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.GeotagDTO;
import com.pcs.fms.web.dto.Image;
import com.pcs.fms.web.dto.PointListDTO;
import com.pcs.fms.web.dto.Status;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.dto.Tag;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.model.FileUploadForm;
import com.pcs.fms.web.model.VehicleModelDTO;
import com.pcs.fms.web.model.VehicleType;
import com.pcs.fms.web.services.FileService;
import com.pcs.fms.web.services.TagService;
import com.pcs.fms.web.services.VehicleService;

@Controller
public class VehicleContoller {

	@Autowired
	VehicleService vehicleService;

	@Autowired
	TagService tagService;

	@Autowired
	private FileService fileServer;

	@Value("${vehicle.type.create.success}")
	private String createVehicleTypeSuccess;

	@Value("${vehicle.type.create.failure}")
	private String createVehicleTypeFailure;

	@Value("${vehicle.create.success}")
	private String createVehicleSuccess;

	@Value("${vehicle.create.failure}")
	private String createVehicleFailure;

	@Value("${vehicle.update.success}")
	private String updateVehicleSuccess;

	@Value("${vehicle.update.failure}")
	private String updateVehicleFailure;

	@RequestMapping(value = VEHICLE_HOME,method = RequestMethod.GET)
	public ModelAndView viewHome() {
		// Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String domainName = FMSAccessManager.getCurrentDomain();
		FMSResponse<List<AssetTypeTemplateDTO>> vehicleType = vehicleService
		        .listVehicleType(domainName);
		AttachHierarchySearchDTO attachHierarchySearchDTO = getAllTagPayload(domainName);
		FMSResponse<List<Tag>> tags = tagService
		        .getTagsForEntities(attachHierarchySearchDTO);
		ModelAndView mv = new ModelAndView(VEHICLE_HOME_VIEW);
		Gson gson = new Gson();
		if (null == vehicleType.getErrorMessage()) {
			mv.addObject(VEHICLE_HOME_VIEW, new VehicleModelDTO());
			mv.addObject("domainName", domainName);
			mv.addObject("vehicleType", gson.toJson(vehicleType.getEntity()));
			mv.addObject("vehicleEdit", "");
		}
		if (null != tags.getEntity()) {
			mv.addObject("tags", gson.toJson(tags.getEntity()));

		}
		return mv;
	}

	private VehicleModelDTO getVehicleFromDto(AssetResponseDTO assetResponseDTO) {
		VehicleModelDTO assetModel = null;
		if (assetResponseDTO != null) {
			assetModel = new VehicleModelDTO();
			EntityDTO asset = assetResponseDTO.getAsset();
			if (asset != null) {
				List<FieldMapDTO> fieldValues = asset.getFieldValues();
				if (fieldValues != null) {
					for (FieldMapDTO fieldMapDTO : fieldValues) {
						if (fieldMapDTO.getKey().equals("assetName")) {
							assetModel.setAssetName(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey().equals("assetId")) {
							assetModel.setAssetId(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey().equals("description")) {
							assetModel.setDescription(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey().equals("assetType")) {
							assetModel.setAssetType(fieldMapDTO.getValue());
						}
						if (fieldMapDTO.getKey().equals("serialNumber")) {
							assetModel.setSerialNumber(fieldMapDTO.getValue());
						}
						assetModel.setDomain(FMSAccessManager
						        .getCurrentDomain());
					}
				}
			}
			EntityDTO assetType = assetResponseDTO.getAssetType();
			if (assetType != null) {
				List<FieldMapDTO> assetTypeValues = assetType.getFieldValues();
				if (assetTypeValues != null) {
					Gson gson = new Gson();
					assetModel.setAssetTypeValues(gson.toJson(assetTypeValues));
				}
			}

		}
		return assetModel;
	}

	@RequestMapping(value = VEHICLE_HOME,method = RequestMethod.POST)
	public ModelAndView createVehicle(@ModelAttribute VehicleModelDTO asset,
	        HttpSession session, RedirectAttributes redirectAttributes) {
		// Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String domainName = FMSAccessManager.getCurrentDomain();
		ModelAndView mv = new ModelAndView();
		if (!isBlank(asset.getVehicle_id())
		        && asset.getVehicle_mode().equals(EDIT)) {
			FMSResponse<AssetResponseDTO> vehicle = vehicleService.viewVehicle(
			        asset.getVehicle_id(), domainName);
			FMSResponse<List<Tag>> tagResponse = null;
			FMSResponse<GeotagDTO> geoDetails = null;
			Gson gson = new Gson();
			if (null != vehicle.getEntity()) {
				geoDetails = vehicleService.geoTag(vehicle, domainName);
				AttachHierarchySearchDTO attachHierarchySearchDTO = getTagEntityPayload(
				        domainName, vehicle.getEntity().getAsset());
				tagResponse = tagService
				        .getTagsForEntities(attachHierarchySearchDTO);
				if (CollectionUtils.size(vehicle.getEntity().getPoints()) > 0) {
					mv.addObject("pointList",
					        gson.toJson(vehicle.getEntity().getPoints()));
				} else {
					mv.addObject("pointList", "");
				}
			}
			AttachHierarchySearchDTO getAllTags = getAllTagPayload(domainName);
			FMSResponse<List<Tag>> allTags = tagService
			        .getTagsForEntities(getAllTags);
			mv.setViewName(VEHICLE_HOME_VIEW);
			VehicleModelDTO vehicleFrom = getVehicleFromDto(vehicle.getEntity());
			getVImage(vehicleFrom);
			mv.addObject(VEHICLE_HOME_VIEW, vehicleFrom);
			mv.addObject("vehicleEdit", "1");
			mv.addObject("vehicleId", asset.getVehicle_id().toString());
			if (null != allTags.getEntity()) {
				mv.addObject("tags", gson.toJson(allTags.getEntity()));
			} else {
				mv.addObject("tags", "");
			}
			if (null != tagResponse.getEntity()) {
				mv.addObject("tagResponse",
				        gson.toJson(tagResponse.getEntity()));
			} else {
				mv.addObject("tagResponse", "");
			}
			if (null != geoDetails.getEntity()) {
				mv.addObject("geoDetails", gson.toJson(geoDetails.getEntity()));
			} else {
				mv.addObject("geoDetails", "");
			}
			FMSResponse<List<AssetTypeTemplateDTO>> vehicleType = vehicleService
			        .listVehicleType(domainName);
			mv.addObject("vehicleType", gson.toJson(vehicleType.getEntity()));

		} else if (!isBlank(asset.getVehicle_id())
		        && asset.getVehicle_mode().equals(UPDATE)) {
			asset.setAssetName(asset.getVehicle_name());
			FMSResponse<StatusMessageDTO> vehicleUpdate = vehicleService
			        .updateVehicle(asset, domainName);
			FMSResponse<StatusMessageDTO> tags = null;
			FMSResponse<StatusMessageDTO> geoTags = null;
			String response = null;
			if (null == vehicleUpdate.getErrorMessage()) {
				// updateGeoTag
				asset.setDomain(FMSAccessManager.getCurrentDomain());
				saveVImage(asset);
				if (!isBlank(asset.getGeoDetails())) {
					geoTags = vehicleService.updateGeoTags(asset);
				}
				if (!isBlank(asset.getTagDetails())) {
					tags = vehicleService.updateTags(asset);
				}
				mv = new ModelAndView("redirect:" + VEHICLE_MANAGEMENT_LIST);

				// Add success response
				if (vehicleUpdate.getEntity() != null
				        && (tags == null || tags.getErrorMessage() == null)
				        && (geoTags == null || geoTags.getErrorMessage() == null)) {
					response = FMSResponseManager.success(updateVehicleSuccess);
				} else {
					response = FMSResponseManager.error(updateVehicleFailure);
					return viewComponentOnError(asset, response);
				}
				redirectAttributes.addFlashAttribute(RESPONSE, response);
				redirectAttributes.addFlashAttribute("vehicleResponse",
				        vehicleUpdate);
				redirectAttributes.addFlashAttribute("tagResponse", tags);
				redirectAttributes
				        .addFlashAttribute("geoTagsResponse", geoTags);
			} else {
				response = FMSResponseManager.error(vehicleUpdate
				        .getErrorMessage().getErrorMessage());
				return viewComponentOnError(asset, response);
			}

		} else {
			String response = null;
			FMSResponse<EntityDTO> vehicle = vehicleService.createVehicle(
			        asset, domainName);
			mv = new ModelAndView("redirect:" + VEHICLE_MANAGEMENT_LIST);
			FMSResponse<StatusMessageDTO> tags = null;
			FMSResponse<StatusMessageDTO> geoTags = null;
			if (null != vehicle.getEntity()) {
				asset.setDomain(FMSAccessManager.getCurrentDomain());
				saveVImage(asset);
				if (!isBlank(asset.getTagDetails())) {
					tags = vehicleService.assignTags(asset.getTagDetails(),
					        vehicle.getEntity().getIdentifier());
				}
				if (!isBlank(asset.getGeoDetails())) {
					geoTags = vehicleService.assignGeoTags(asset
					        .getGeoDetails(), vehicle.getEntity()
					        .getIdentifier());
				}
				// Add success response
				if (vehicle.getEntity() != null
				        && (tags == null || tags.getErrorMessage() == null)
				        && (geoTags == null || geoTags.getErrorMessage() == null)) {
					response = FMSResponseManager.success(createVehicleSuccess);
				} else {
					response = FMSResponseManager.error(createVehicleFailure);
					return viewComponentOnError(asset, response);
				}
				redirectAttributes.addFlashAttribute(RESPONSE, response);
				redirectAttributes
				        .addFlashAttribute("vehicleResponse", vehicle);
				redirectAttributes.addFlashAttribute("tagResponse", tags);
				redirectAttributes
				        .addFlashAttribute("geoTagsResponse", geoTags);

			} else {
				response = FMSResponseManager.error(vehicle.getErrorMessage()
				        .getErrorMessage());
				return viewComponentOnError(asset, response);
			}
		}

		return mv;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = VEHICLE_MANAGEMENT_LIST,method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		String domainName = FMSAccessManager.getCurrentDomain();
		List<AssetDTO> vehicle = null;
		FMSResponse<List<AssetDTO>> vehicles = new FMSResponse<List<AssetDTO>>();
		ModelAndView mv = new ModelAndView();
		Gson gson = new Gson();
		FMSResponse<List<AssetTypeTemplateDTO>> vehicleType = vehicleService
		        .listVehicleType(domainName);
		if (!vehicleType.getEntity().isEmpty()) {
			vehicle = vehicleService.listVehicles(domainName,
			        vehicleType.getEntity());
			if (!vehicle.isEmpty()) {
				vehicles.setEntity(vehicle);
				mv.addObject("domainName", domainName);
				mv.addObject("vehicle", gson.toJson(vehicles));
				mv.addObject("vehicleType",
				        gson.toJson(vehicleType.getEntity()));
			} else {
				mv.addObject("domainName", domainName);
				mv.addObject("vehicle", null);
				mv.addObject("vehicleType",
				        gson.toJson(vehicleType.getEntity()));
			}

		} else {
			mv.addObject("domainName", domainName);
			mv.addObject("vehicle", null);
			mv.addObject("vehicleType", null);
		}
		mv.setViewName(VEHICLE_MANAGEMENT_LIST_VIEW);
		mv.addObject("domainName", domainName);

		return mv;
	}

	@RequestMapping(value = VEHICLE_TYPE_VIEW,method = RequestMethod.GET)
	public ModelAndView VehicleTypeView(HttpServletRequest request) {
		return new ModelAndView(VEHICLE_TYPE_VIEW);
	}

	@RequestMapping(value = VEHICLE_DASHBOARD,method = RequestMethod.GET)
	public ModelAndView VehicleDashboard(HttpServletRequest request) {
		return new ModelAndView(VEHICLE_DASHBOARD);
	}

	@RequestMapping(value = VEHICLE_TYPE_HOME,method = RequestMethod.GET)
	public ModelAndView listVehiclType(HttpServletRequest request,
	        RedirectAttributes redirect) {
		ModelAndView mv = new ModelAndView(VEHICLE_TYPE_HOME);
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		mv.addObject("domain", token.getTenant().getCurrentDomain());
		mv.addObject("vehicleTemplate", VEHICLE_TEMPLATE);
		return mv;
	}

	@RequestMapping(value = VEHICLE_TYPE_VIEW,method = RequestMethod.POST)
	public ModelAndView getVehicleTypeDetails(@ModelAttribute FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(VEHICLE_TYPE_VIEW);
		FMSResponse<AssetTypeTemplateDTO> vehicleDetails = vehicleService
		        .getAssetTypeDetails(field.getValue());
		VehicleType vehicleType = getVehicleTypeFromDto(vehicleDetails
		        .getEntity());
		if (vehicleDetails.getEntity() != null) {
			getVTImage(vehicleType);
		}
		mv.addObject(VEHICLE_TYPE_VIEW, vehicleType);
		mv.addObject("action", "view");
		return mv;
	}

	private VehicleType getVehicleTypeFromDto(
	        AssetTypeTemplateDTO vehicleTypeDto) {
		VehicleType vehicleType = new VehicleType();
		if (vehicleTypeDto != null) {
			if (StringUtils.isEmpty(vehicleTypeDto.getDomainName())) {
				vehicleTypeDto.setDomainName(FMSAccessManager
				        .getCurrentDomain());
			}
			vehicleType.setDomainName(vehicleTypeDto.getDomainName());
			vehicleType.setVehicleType(vehicleTypeDto.getAssetType());
			if (StringUtils.isEmpty(vehicleTypeDto.getStatus())) {
				vehicleTypeDto.setStatus("ACTIVE");
			}
			vehicleType.setStatus(vehicleTypeDto.getStatus());
			Gson gson = new Gson();
			vehicleType.setVehicleTypeValues(gson.toJson(vehicleTypeDto
			        .getAssetTypeValues()));
		}
		return vehicleType;
	}

	@RequestMapping(value = VEHICLE_TYPE_CREATE_PATH_NAME,
	        method = RequestMethod.POST)
	public ModelAndView createPage() {
		ModelAndView mv = new ModelAndView(VEHICLE_TYPE_VIEW_PATH_NAME);
		VehicleType vehicleType = new VehicleType();
		vehicleType.setStatus("ACTIVE");
		mv.addObject(VEHICLE_TYPE_VIEW, vehicleType);
		mv.addObject("action", "create");
		return mv;
	}

	@RequestMapping(value = VEHICLE_TYPE_SAVE_PATH_NAME,
	        method = RequestMethod.POST)
	public ModelAndView saveVehicleType(HttpServletRequest request,
	        @ModelAttribute VehicleType vehicleType, RedirectAttributes redirect) {
		AssetTypeTemplateDTO assetTypeDto = new AssetTypeTemplateDTO();
		assetTypeDto.setAssetType(vehicleType.getVehicleType());
		assetTypeDto.setAssetTypeValues(assetTypeValues(vehicleType
		        .getVehicleTypeValues()));
		assetTypeDto.setStatus(vehicleType.getStatus());
		vehicleType.setDomainName(FMSAccessManager.getCurrentDomain());
		assetTypeDto.setDomainName(vehicleType.getDomainName());
		FMSResponse<StatusMessageDTO> saveStatus = vehicleService
		        .saveVehicleType(assetTypeDto);
		String response = null;
		RedirectView redirectView = null;
		ModelAndView mv = null;
		if (saveStatus.getEntity() != null
		        && saveStatus.getEntity().getStatus().getStatus()
		                .equalsIgnoreCase(Status.SUCCESS.toString())) {
			response = FMSResponseManager.success(createVehicleTypeSuccess);
			redirectView = new RedirectView(VEHICLE_TYPE_HOME);
			mv = new ModelAndView(redirectView);
		} else {
			response = FMSResponseManager.error(saveStatus.getErrorMessage()
			        .getErrorMessage());
			redirectView = new RedirectView(VEHICLE_TYPE_VIEW_PATH_NAME);

			if (vehicleType != null) {
				getVTImage(vehicleType);
			}
			redirect.addFlashAttribute(RESPONSE, response);
			return viewVehicleOnError(vehicleType, redirect, redirectView,
			        response);
		}
		StatusMessageDTO status = saveStatus.getEntity();
		if (status != null) {
			saveVTImage(vehicleType);
		}
		redirect.addFlashAttribute(RESPONSE, response);
		Token token = TokenManager.getToken(FMSTokenHolder.getBearer());
		mv.addObject("domain", token.getTenant().getCurrentDomain());
		return mv;
	}

	private void saveVTImage(VehicleType vehicleType) {

		FileUploadForm vtImage = new FileUploadForm();
		vtImage.setFileDatas(vehicleType.getFileDatas());

		Image imageDetails = new Image();
		imageDetails.setDomain(vehicleType.getDomainName());
		imageDetails.setModule(VEHICLE_TYPE_FOLDER_NAME);
		imageDetails
		        .setFilename(vehicleType.getVehicleType() + FILE_IMAGE_TYPE);

		Image iconDetails = new Image();
		iconDetails.setDomain(vehicleType.getDomainName());
		iconDetails.setModule(VEHICLE_TYPE_ICON_FOLDER_NAME);
		iconDetails.setFilename(vehicleType.getVehicleType() + FILE_IMAGE_TYPE);

		CommonsMultipartFile[] fileDatas = vtImage.getFileDatas();
		if (fileDatas.length > 0) {
			fileServer.doUpload(fileDatas[0], imageDetails);
		}

		if (StringUtils.isNotEmpty(vehicleType.getIcon())) {
			fileServer.doUpload(vehicleType.getIcon(), iconDetails);
		}

	}

	private void getVTImage(VehicleType vehicleType) {

		Image image = new Image();
		image.setDomain(vehicleType.getDomainName());
		image.setModule(VEHICLE_TYPE_FOLDER_NAME);
		image.setFilename(vehicleType.getVehicleType() + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowload(image);
		vehicleType.setImage(userImage);
	}

	private void saveVImage(VehicleModelDTO vehicle) {

		FileUploadForm vImage = new FileUploadForm();
		vImage.setFileDatas(vehicle.getFileDatas());

		Image image = new Image();
		image.setDomain(vehicle.getDomain());
		image.setModule(VEHICLE_FOLDER_NAME);
		image.setFilename(vehicle.getAssetId() + FILE_IMAGE_TYPE);
		CommonsMultipartFile[] fileDatas = vImage.getFileDatas();
		if (fileDatas.length > 0) {
			fileServer.doUpload(fileDatas[0], image);
		}
	}

	private void getVImage(VehicleModelDTO vehicle) {

		Image image = new Image();
		image.setDomain(vehicle.getDomain());
		image.setModule(VEHICLE_FOLDER_NAME);
		image.setFilename(vehicle.getAssetId() + FILE_IMAGE_TYPE);
		String userImage = fileServer.doDowload(image);
		vehicle.setImage(userImage);

	}

	private List<String> assetTypeValues(String values) {
		List<String> arraylist = null;
		if (StringUtils.isNotEmpty(values)) {
			String[] split = values.split(",");
			if (split.length > 0) {
				arraylist = Arrays.asList(split);
			}
		}
		return arraylist;
	}

	private AttachHierarchySearchDTO getAllTagPayload(String domain) {
		AttachHierarchySearchDTO attachHierarchySearchDTO = new AttachHierarchySearchDTO();
		attachHierarchySearchDTO.setActorType(TEMPLATE);
		Actor actor = new Actor();
		EntityTemplateDTO template = new EntityTemplateDTO();
		DomainDTO domainDto = new DomainDTO();
		domainDto.setDomainName(domain);
		template.setDomain(domainDto);
		template.setEntityTemplateName(ASSET_ENTITY_TEMP_NAME);
		template.setPlatformEntityType(MARKER);
		actor.setTemplate(template);
		attachHierarchySearchDTO.setActor(actor);
		return attachHierarchySearchDTO;

	}

	private AttachHierarchySearchDTO getTagEntityPayload(String domainName,
	        EntityDTO entityDTO) {
		AttachHierarchySearchDTO attachHierarchySearchDTO = new AttachHierarchySearchDTO();
		attachHierarchySearchDTO.setActorType(ENTITY);
		Actor actor = new Actor();
		actor.setEntity(entityDTO);
		attachHierarchySearchDTO.setActor(actor);
		return attachHierarchySearchDTO;
	}

	@RequestMapping(value = VEHICLE_CANCEL,method = RequestMethod.GET)
	public ModelAndView cancelPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:" + VEHICLE_MANAGEMENT_LIST);
		return mv;
	}

	@RequestMapping(value = VEHICLE_HISTORY,method = RequestMethod.GET)
	public ModelAndView listVehiclesInHistory(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(VEHICLE_HISTORY);
		String currDomain = FMSAccessManager.getCurrentDomain();

		FMSResponse<List<AssetTypeTemplateDTO>> vehicleType = vehicleService
		        .listVehicleType(currDomain);

		List<AssetDTO> vehicleList = vehicleService.listVehicles(currDomain,
		        vehicleType.getEntity());
		Gson gson = new Gson();
		// if (null != vehicleList.getEntity()) {
		mv.addObject("vehicleListResp", gson.toJson(vehicleList));
		// }
		return mv;
	}

	@RequestMapping(value = POINT_LIST,method = RequestMethod.POST)
	public ModelAndView getPointsDetails(@ModelAttribute FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(POINT_LIST);
		Gson gson = new Gson();
		PointListDTO vehicleModel = gson.fromJson(field.getValue(),
		        PointListDTO.class);
		mv.addObject("pointList", gson.toJson(vehicleModel.getSelectedPoints()));
		mv.addObject("assetId", vehicleModel.getAssetId());
		return mv;
	}

	@RequestMapping(value = VEHICLE_VIEW,method = RequestMethod.POST)
	public ModelAndView cancelViewPoints(@ModelAttribute FieldMapDTO field,
	        HttpSession session, RedirectAttributes redirectAttributes) {
		VehicleModelDTO vehicleModelDTO = new VehicleModelDTO();
		vehicleModelDTO.setVehicle_id(field.getValue());
		vehicleModelDTO.setVehicle_mode(EDIT);
		return createVehicle(vehicleModelDTO, session, redirectAttributes);
	}

	public ModelAndView viewVehicleOnError(VehicleType vehicleType,
	        RedirectAttributes redirectAttributes, RedirectView redirectView,
	        String response) {
		List<String> list = assetTypeValues(vehicleType.getVehicleTypeValues());
		Gson gson = new Gson();
		vehicleType.setVehicleTypeValues(gson.toJson(list));
		ModelAndView mv = new ModelAndView(VEHICLE_TYPE_VIEW_PATH_NAME);
		mv.addObject(VEHICLE_TYPE_VIEW, vehicleType);
		mv.addObject("action", "view");
		mv.addObject(RESPONSE, response);
		return mv;
	}

	@SuppressWarnings("serial")
	private ModelAndView viewComponentOnError(VehicleModelDTO asset,
	        String response) {
		ModelAndView mv = new ModelAndView(VEHICLE_HOME);

		String domainName = FMSAccessManager.getCurrentDomain();
		AttachHierarchySearchDTO attachHierarchySearchDTO = getAllTagPayload(domainName);
		FMSResponse<List<Tag>> tags = tagService
		        .getTagsForEntities(attachHierarchySearchDTO);

		Gson gson = new Gson();

		mv.addObject("onerrorTag", asset.getTagArray());
		mv.addObject("onerrorLat", asset.getLatitude());
		mv.addObject("onerrorLng", asset.getLongitude());
		mv.addObject(RESPONSE, response);
		mv.addObject("error", "1");
		mv.addObject(RESPONSE, response);
		mv.addObject(VEHICLE_HOME, asset);

		FMSResponse<List<AssetTypeTemplateDTO>> vehicleType = vehicleService
		        .listVehicleType(domainName);
		mv.addObject("vehicleType", gson.toJson(vehicleType.getEntity()));

		if (null != tags.getEntity()) {
			mv.addObject("tags", gson.toJson(tags.getEntity()));

		}
		return mv;
	}

}
