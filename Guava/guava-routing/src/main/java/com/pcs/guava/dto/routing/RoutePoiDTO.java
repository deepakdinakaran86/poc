package com.pcs.guava.dto.routing;

import java.util.List;

import com.pcs.guava.commons.dto.FieldMapDTO;


public class RoutePoiDTO extends RoutePoiTypeDTO  {
	
	private String domainName;
	private String latitude;
	private String longitude;
	private String radius;
	private String poiType;
	private String name;
	private String poiName;
	private String status;
	private List<FieldMapDTO>poiTypeValues;


	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FieldMapDTO> getPoiTypeValues() {
		return poiTypeValues;
	}

	public void setPoiTypeValues(List<FieldMapDTO> poiTypeValues) {
		this.poiTypeValues = poiTypeValues;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPoiName() {
		return poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}
	
	
}
