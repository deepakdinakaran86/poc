package com.pcs.avocado.dto;

import java.io.Serializable;

import com.pcs.avocado.commons.dto.EntityDTO;

public class PayloadDTO implements Serializable {

	private static final long serialVersionUID = -2049990530877806985L;

	private SiteDTO skysparkPayload;
	private EntityDTO markerPayload;
	
	public SiteDTO getSkysparkPayload() {
	    return skysparkPayload;
    }
	public void setSkysparkPayload(SiteDTO skysparkPayload) {
	    this.skysparkPayload = skysparkPayload;
    }
	public EntityDTO getMarkerPayload() {
	    return markerPayload;
    }
	public void setMarkerPayload(EntityDTO markerPayload) {
	    this.markerPayload = markerPayload;
    }
}
