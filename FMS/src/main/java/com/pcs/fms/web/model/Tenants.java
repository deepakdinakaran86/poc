package com.pcs.fms.web.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@JsonSerialize
public class Tenants extends FileUploadForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7006870922680644081L;

	private String tenantName;

	private String contactEmail;

	private String firstName;

	private String lastName;

	private String domain;

	private String status;

	private String action;

	private String identifier;

	private String domainNameOnEdit;

	private String currentDomain;

	private String tenantId;

	private List<String> features;

	private List<String> availableFeatures;

	private String tags;

	private String latitude;

	private String longitude;

	private String parentDomain;

	private String location;

	private Boolean geotagPresent;

	private String selectedTags;

	private List<String> tenantFeatures;

	private String subTenant;

	private String type;

	private String zoom;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDomainNameOnEdit() {
		return domainNameOnEdit;
	}

	public void setDomainNameOnEdit(String domainNameOnEdit) {
		this.domainNameOnEdit = domainNameOnEdit;
	}

	public String getCurrentDomain() {
		return currentDomain;
	}

	public void setCurrentDomain(String currentDomain) {
		this.currentDomain = currentDomain;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

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

	public String getParentDomain() {
		return parentDomain;
	}

	public void setParentDomain(String parentDomain) {
		this.parentDomain = parentDomain;
	}

	public List<String> getAvailableFeatures() {
		return availableFeatures;
	}

	public void setAvailableFeatures(List<String> availableFeatures) {
		this.availableFeatures = availableFeatures;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getGeotagPresent() {
		return geotagPresent;
	}

	public void setGeotagPresent(Boolean geotagPresent) {
		this.geotagPresent = geotagPresent;
	}

	public String getSelectedTags() {
		return selectedTags;
	}

	public void setSelectedTags(String selectedTags) {
		this.selectedTags = selectedTags;
	}

	public List<String> getTenantFeatures() {
		return tenantFeatures;
	}

	public void setTenantFeatures(List<String> tenantFeatures) {
		this.tenantFeatures = tenantFeatures;
	}

	public String getSubTenant() {
		return subTenant;
	}

	public void setSubTenant(String subTenant) {
		this.subTenant = subTenant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getZoom() {
		return zoom;
	}

	public void setZoom(String zoom) {
		this.zoom = zoom;
	}

}