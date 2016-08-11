package com.pcs.fms.web.services;

import static com.pcs.fms.web.constants.FMSWebConstants.ASSET_ENTITY_TEMP_NAME;
import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;
import static com.pcs.fms.web.constants.FMSWebConstants.VEHICLE_TEMPLATE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.AssetDTO;
import com.pcs.fms.web.dto.AssetResponseDTO;
import com.pcs.fms.web.dto.AssetTypeTemplateDTO;
import com.pcs.fms.web.dto.AttachTags;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.GeotagDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.VehicleModelDTO;

@Service
public class VehicleService extends BaseService {

	@Value("${fms.services.listvehicle}")
	private String listVehicleEndpointUri;

	@Value("${fms.services.listvehicleType}")
	private String listVehicleTypeEndpointUri;

	@Value("${fms.services.vehicle}")
	private String createVehicleEndpointUri;

	@Value("${fms.services.vehicleFind}")
	private String viewVehicleEndpointUri;

	@Value("${fms.services.updateVehicle}")
	private String updateVehicleEndpointUri;

	@Autowired
	TagService tagService;

	@Autowired
	GeotagService geoTagService;

	@Value("${fms.vehicleType.get}")
	private String findAssetTypeEndpointUri;

	@Value("${fms.vehicleType.save}")
	private String saveVehicleTypeEndpointUri;

	public List<AssetDTO> listVehicles(String currentDomain,
			List<AssetTypeTemplateDTO> list) {
		Type listVehicle = new TypeToken<List<AssetDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		List<AssetDTO> totalVehicles = new ArrayList<AssetDTO>();

		if (!list.isEmpty()) {
			for (AssetTypeTemplateDTO type : list) {
				String listVehicleUri = getServiceURI(listVehicleEndpointUri);
				listVehicleUri = listVehicleUri.replace("{domain}",
						currentDomain);
				listVehicleUri = listVehicleUri.replace("{assetType}",
						type.getAssetType());
				FMSResponse<List<AssetDTO>> vehicles = getPlatformClient()
						.getResource(listVehicleUri, listVehicle);
				if (null != vehicles.getEntity()) {
					totalVehicles.addAll(vehicles.getEntity());
				}

			}
		}
		return totalVehicles;

	}

	public FMSResponse<List<AssetTypeTemplateDTO>> listVehicleType(
			String currentDomain) {
		// AssetTypeTemplateDTO
		Type listVehicleType = new TypeToken<List<AssetTypeTemplateDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		String listVehicleTypeUri = getServiceURI(listVehicleTypeEndpointUri);
		listVehicleTypeUri = listVehicleTypeUri.replace("{domain}",
				currentDomain);
		listVehicleTypeUri = listVehicleTypeUri.replace("{parent_type_name}",
				VEHICLE_TEMPLATE);

		return getPlatformClient().getResource(listVehicleTypeUri,
				listVehicleType);
	}

	public FMSResponse<EntityDTO> createVehicle(VehicleModelDTO asset,
			String domainName) {

		AssetDTO createAsset = new AssetDTO();
		Type fieldAsset = new TypeToken<List<FieldMapDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		Gson gson = new Gson();
		List<FieldMapDTO> assetFields = gson.fromJson(
				asset.getAssetTypeValues(), fieldAsset);
		createAsset.setDomainName(domainName);
		createAsset.setSerialNumber(asset.getSerialNumber());
		createAsset.setDescription(asset.getDescription());
		createAsset.setAssetId(asset.getAssetId());
		createAsset.setParentType(VEHICLE_TEMPLATE);
		createAsset.setAssetType(asset.getAssetType());
		createAsset.setAssetName(asset.getAssetName());
		createAsset.setAssetTypeValues(assetFields);
		String createVehicleUri = getServiceURI(createVehicleEndpointUri);
		return getPlatformClient().postResource(createVehicleUri, createAsset,
				EntityDTO.class);
	}

	public FMSResponse<StatusMessageDTO> assignTags(String tagDetails,
			FieldMapDTO identifier) {

		Type tagConfig = new TypeToken<AttachTags>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		Gson gson = new Gson();
		AttachTags tagConfiguration = gson.fromJson(tagDetails, tagConfig);
		IdentityDTO identity = new IdentityDTO();
		identity = tagConfiguration.getEntity();
		identity.setIdentifier(identifier);
		tagConfiguration.setEntity(identity);

		return tagService.attachTagsToEntity(tagConfiguration);
	}

	public FMSResponse<StatusMessageDTO> assignGeoTags(String geoDetails,
			FieldMapDTO identifier) {

		Type tagConfig = new TypeToken<GeotagDTO>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		Gson gson = new Gson();
		GeotagDTO tagConfiguration = gson.fromJson(geoDetails, tagConfig);
		IdentityDTO identity = new IdentityDTO();
		identity = tagConfiguration.getEntity();
		identity.setIdentifier(identifier);
		tagConfiguration.setEntity(identity);
		return geoTagService.createGeotag(tagConfiguration);
	}

	/**
	 * Method to view vehicle type details
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<AssetTypeTemplateDTO> getAssetTypeDetails(
			String assetType) {
		String url = findAssetTypeEndpointUri.replace("{asset_type_name}",
				assetType);
		String findAssetDetailsServiceURI = getServiceURI(url);
		return getPlatformClient().getResource(
				findAssetDetailsServiceURI + "?domain_name="
						+ FMSAccessManager.getCurrentDomain() + "&parent_type="
						+ VEHICLE_TEMPLATE, AssetTypeTemplateDTO.class);
	}

	/**
	 * Method to save vehicle type
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> saveVehicleType(
			AssetTypeTemplateDTO assetType) {
		String findDeviceDetailsServiceURI = getServiceURI(saveVehicleTypeEndpointUri);
		return getPlatformClient().postResource(
				findDeviceDetailsServiceURI + "?parent_type="
						+ VEHICLE_TEMPLATE, assetType, StatusMessageDTO.class);
	}

	public FMSResponse<AssetResponseDTO> viewVehicle(String vehicle_id,
			String currDomain) {
		// fms.services.vehicleFind
		IdentityDTO vehcileIdentity = new IdentityDTO();
		EntityTemplateDTO assetTemplate = new EntityTemplateDTO();
		assetTemplate.setEntityTemplateName(ASSET_ENTITY_TEMP_NAME);
		vehcileIdentity.setEntityTemplate(assetTemplate);
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(currDomain);
		vehcileIdentity.setDomain(domain);
		FieldMapDTO vehicleIdentifier = new FieldMapDTO();
		vehicleIdentifier.setKey(IDENTIFIER);
		vehicleIdentifier.setValue(vehicle_id);
		vehcileIdentity.setIdentifier(vehicleIdentifier);
		String geoFenceFindEndpointUri = getServiceURI(viewVehicleEndpointUri);
		return getPlatformClient().postResource(geoFenceFindEndpointUri,
				vehcileIdentity, AssetResponseDTO.class);
	}

	public FMSResponse<GeotagDTO> geoTag(FMSResponse<AssetResponseDTO> vehicle,
			String domainName) {
		IdentityDTO geoIdentity = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		geoIdentity.setDomain(domain);
		FieldMapDTO vehicleIdentifier = new FieldMapDTO();
		vehicleIdentifier = vehicle.getEntity().getAsset().getIdentifier();
		geoIdentity.setIdentifier(vehicleIdentifier);
		geoIdentity.setEntityTemplate(vehicle.getEntity().getAsset()
				.getEntityTemplate());
		return geoTagService.findGeotag(geoIdentity);
	}

	public FMSResponse<StatusMessageDTO> updateVehicle(VehicleModelDTO asset,
			String domainName) {
		// updateVehicleEndpointUri
		AssetDTO updateAsset = new AssetDTO();
		Type fieldAsset = new TypeToken<List<FieldMapDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		Gson gson = new Gson();
		List<FieldMapDTO> assetFields = gson.fromJson(
				asset.getAssetTypeValues(), fieldAsset);
		updateAsset.setDomainName(domainName);
		updateAsset.setSerialNumber(asset.getSerialNumber());
		updateAsset.setDescription(asset.getDescription());
		updateAsset.setAssetId(asset.getAssetId());
		updateAsset.setParentType(VEHICLE_TEMPLATE);
		updateAsset.setAssetType(asset.getVehicle_type());
		updateAsset.setAssetName(asset.getVehicle_name());
		updateAsset.setAssetTypeValues(assetFields);
		updateAsset.setAssetIdentifier(createFieldMap(asset.getVehicle_id()));
		updateAsset.setAssetTypeIdentifier(createFieldMap(asset
				.getGblAssetTypeId()));
		String vehicleUpdatetUri = getServiceURI(updateVehicleEndpointUri);
		return getPlatformClient().putResource(vehicleUpdatetUri, updateAsset,
				StatusMessageDTO.class);
	}

	public FMSResponse<StatusMessageDTO> updateGeoTags(VehicleModelDTO asset) {
		FMSResponse<StatusMessageDTO> status = null;
		if (asset.getTag_update_flag().equals("true")) {
			Type tagConfig = new TypeToken<GeotagDTO>() {
				private static final long serialVersionUID = 5936335989523954928L;
			}.getType();

			Gson gson = new Gson();
			GeotagDTO tagConfiguration = gson.fromJson(asset.getGeoDetails(),
					tagConfig);
			status = geoTagService.updateGeotag(tagConfiguration);
		} else {
			FieldMapDTO identifier = new FieldMapDTO();
			identifier = createFieldMap(asset.getVehicle_id());
			status = assignGeoTags(asset.getGeoDetails(), identifier);
		}
		return status;
	}

	private FieldMapDTO createFieldMap(String name) {

		FieldMapDTO field = new FieldMapDTO();
		field.setKey(IDENTIFIER);
		field.setValue(name);
		return field;
	}

	public FMSResponse<StatusMessageDTO> updateTags(VehicleModelDTO asset) {
		FieldMapDTO identifier = new FieldMapDTO();
		identifier = createFieldMap(asset.getVehicle_id());
		FMSResponse<StatusMessageDTO> status = assignTags(
				asset.getTagDetails(), identifier);
		return status;
	}

}
