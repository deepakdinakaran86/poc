package com.pcs.alpine.services.dto.builder;

import com.pcs.alpine.dto.CoordinatesDTO;
import com.pcs.alpine.dto.GeotagDTO;
import com.pcs.alpine.services.dto.IdentityDTO;

public class GeotagDTOBuilder {

	private GeotagDTO geotagDTO;

	public GeotagDTO aGeotag(IdentityDTO entity, CoordinatesDTO geotag) {
		if (geotagDTO == null) {
			geotagDTO = new GeotagDTO();
			geotagDTO.setEntity(entity);
			;
			geotagDTO.setGeotag(geotag);
		}
		return geotagDTO;
	}

	public GeotagDTO build() {
		return geotagDTO;
	}
}
