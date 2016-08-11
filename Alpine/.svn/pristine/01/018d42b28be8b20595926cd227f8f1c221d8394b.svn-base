package com.pcs.alpine.services.dto.builder;

import static com.pcs.alpine.enums.GeoDataFields.CO_ORDINATES;

import java.util.ArrayList;
import java.util.List;

import com.pcs.alpine.dto.GeofenceDTO;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.StatusDTO;

public class GeofenceDTOBuilder {

	private GeofenceDTO geofenceDTO;

	public GeofenceDTO aGeofence(String geofenceName, String desc,
	        List<FieldMapDTO> geofenceFields, String type,
	        String geofenceStatus, DomainDTO domainDto,
	        FieldMapDTO identifierMap) {
		if (geofenceDTO == null) {
			geofenceDTO = new GeofenceDTO();
			geofenceDTO.setGeofenceName(geofenceName);
			geofenceDTO.setDesc(desc);
			geofenceDTO.setType(type);
			geofenceDTO.setGeofenceFields(geofenceFields);

			// Set the status
			StatusDTO geofenceStatusDTO = new StatusDTO();
			geofenceStatusDTO.setStatusName(geofenceStatus);
			geofenceDTO.setGeofenceStatus(geofenceStatusDTO);

			// Set domain
			geofenceDTO.setDomain(domainDto);
			geofenceDTO.setIdentifier(identifierMap);
		}
		return geofenceDTO;
	}

	public GeofenceDTO build() {
		return geofenceDTO;
	}

	public List<FieldMapDTO> constructRouteFields(String coordinates,
	        String width) {
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();

		FieldMapDTO coordinateMap = new FieldMapDTO();
		coordinateMap.setKey(CO_ORDINATES.getFieldName());
		coordinateMap.setValue(coordinates);

		FieldMapDTO widthMap = new FieldMapDTO();
		widthMap.setKey("width");
		widthMap.setValue(width);

		fields.add(coordinateMap);
		fields.add(widthMap);
		return fields;
	}

	public List<FieldMapDTO> constructCircleFields(String latitude,
	        String longitude, String radius) {
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();

		FieldMapDTO latitudeMap = new FieldMapDTO();
		latitudeMap.setKey("latitude");
		latitudeMap.setValue(latitude);

		FieldMapDTO longitudeMap = new FieldMapDTO();
		longitudeMap.setKey("longitude");
		longitudeMap.setValue(longitude);

		FieldMapDTO radiusMap = new FieldMapDTO();
		radiusMap.setKey("radius");
		radiusMap.setValue(radius);

		fields.add(latitudeMap);
		fields.add(longitudeMap);
		fields.add(radiusMap);
		return fields;
	}

	public List<FieldMapDTO> constructPolygonFields(String coordinates) {
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();

		FieldMapDTO coordinateMap = new FieldMapDTO();
		coordinateMap.setKey(CO_ORDINATES.getFieldName());
		coordinateMap.setValue(coordinates);

		fields.add(coordinateMap);
		return fields;
	}

}
