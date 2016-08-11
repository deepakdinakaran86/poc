package com.pcs.fms.web.services;


import java.lang.reflect.Type;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.dto.StatusDTO;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.GeofenceDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.GeofenceModelDTO;

import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;



@Service
public class GeoFenceService extends BaseService{
	
	@Value("${fms.services.createGeoFence}")
	private String createGeoFenceEndpointUri;
	
	@Value("${fms.services.findGeoFence}")
	private String findGeoFenceEndpointUri;
	
	@Value("${fms.services.listGeoFence}")
	private String listGeoFenceEndpointUri;
	
	@Value("${fms.services.updateGeoFence}")
	private String updateGeoFenceEndpointUri;
	
	@Value("${fms.services.deleteGeoFence}")
	private String geoFenceDeleteEndpointUri;
	
	
	
	
	
	/**
	 * Method to create user
	 * @param domainName 
	 * 
	 * @param
	 * @return
	 */
	public FMSResponse<StatusMessageDTO> createGeofence(GeofenceModelDTO geofenceModel, String domainName) {
		
		Type fieldGeoType = new TypeToken<List<FieldMapDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		Gson gson = new Gson();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		List<FieldMapDTO>geofenceFields=gson.fromJson(geofenceModel.getGeofenceFields(), fieldGeoType);
		GeofenceDTO geofence = new GeofenceDTO();
		StatusDTO geoStatus= new StatusDTO();
		if(!isBlank(geofenceModel.getGeofenceName())){
			geofence.setGeofenceName(geofenceModel.getGeofenceName());
		}else{
			geofence.setGeofenceName(geofenceModel.getFenceName());
		}
		geoStatus.setStatusName(geofenceModel.getGeofenceStatus());
		geofence.setGeofenceFields(geofenceFields);
		geofence.setDomain(domain);
		
		geofence.setType(geofenceModel.getGeofenceType());
		geofence.setGeofenceStatus(geoStatus);
		String createGeoFenceCreateURI = getServiceURI(createGeoFenceEndpointUri);
		return getPlatformClient().postResource(createGeoFenceCreateURI,
				geofence, StatusMessageDTO.class);
	}



	public FMSResponse<GeofenceDTO> viewGeofence(String geofence_id,
			String domainName) {
		
		IdentityDTO geoIdentity = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		geoIdentity.setDomain(domain);
		FieldMapDTO geoIdentifier= new FieldMapDTO();
		geoIdentifier.setKey(IDENTIFIER);
		geoIdentifier.setValue(geofence_id);
		geoIdentity.setIdentifier(geoIdentifier);
		String geoFenceFindEndpointUri = getServiceURI(findGeoFenceEndpointUri);
		return getPlatformClient().postResource(geoFenceFindEndpointUri,
				geoIdentity, GeofenceDTO.class);
	}

	public FMSResponse<List<EntityDTO>> listGeofence(String currentDomain) {
		
		Type listGeoType = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		
		String listGeofenceUri=getServiceURI(listGeoFenceEndpointUri);
		listGeofenceUri=listGeofenceUri.replace("{domain}", currentDomain);
		return getPlatformClient().getResource(listGeofenceUri, listGeoType);
	}



	public FMSResponse<StatusMessageDTO> updateGeofence(
			GeofenceModelDTO geofenceModel) {
		
		Type fieldGeoType = new TypeToken<List<FieldMapDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();
		Gson gson = new Gson();
		List<FieldMapDTO>geofenceFields=gson.fromJson(geofenceModel.getGeofenceFields(), fieldGeoType);
		GeofenceDTO geofence = new GeofenceDTO();
		StatusDTO geoStatus= new StatusDTO();
		if(!isBlank(geofenceModel.getGeofenceName())){
			geofence.setGeofenceName(geofenceModel.getGeofenceName());
		}else{
			geofence.setGeofenceName(geofenceModel.getFenceName());
		}
		geoStatus.setStatusName(geofenceModel.getGeofenceStatus());
		geofence.setGeofenceFields(geofenceFields);
		
		FieldMapDTO geoIdentifier= new FieldMapDTO();
		geoIdentifier.setKey(IDENTIFIER);
		geoIdentifier.setValue(geofenceModel.getGeofence_id());
		geofence.setIdentifier(geoIdentifier);
		geofence.setType(geofenceModel.getGeofenceType());
		geofence.setGeofenceStatus(geoStatus);
		
		String updateGeoFenceURI = getServiceURI(updateGeoFenceEndpointUri);
		return getPlatformClient().putResource(updateGeoFenceURI,
				geofence, StatusMessageDTO.class);
	}



	public FMSResponse<StatusMessageDTO> deleteGeofence(String currentDomain,
			String geofence_id) {
		IdentityDTO geoIdentity = new IdentityDTO();
		DomainDTO domain = new DomainDTO();
		String domainName= currentDomain;
		domain.setDomainName(domainName);
		geoIdentity.setDomain(domain);
		FieldMapDTO geoIdentifier= new FieldMapDTO();
		geoIdentifier.setKey(IDENTIFIER);
		geoIdentifier.setValue(geofence_id);
		geoIdentity.setIdentifier(geoIdentifier);
		String geoFenceDeleteUri = getServiceURI(geoFenceDeleteEndpointUri);
		return getPlatformClient().postResource(geoFenceDeleteUri,
				geoIdentity, StatusMessageDTO.class);
	}
	
	

}
