package com.pcs.fms.web.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author PCSEG191 Daniela PH
 * @date JULY 2016
 * @since FMS1.0.0
 * 
 */
@JsonSerialize
public class ServiceSchedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7006870922680644081L;

	private String identifier;

	private String description;

	private String serviceScheduleName;

	private String status;

	private String domain;

	private String action;

	private String serviceComponentList;

	private String serviceScheduleNameEdit;

	private String selectedComponents;

	private String occurenceType;

	private List<String> components;

	private List<String> selectedList;

	private List<String> selectedListOnEdit;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getServiceComponentList() {
		return serviceComponentList;
	}

	public void setServiceComponentList(String serviceComponentList) {
		this.serviceComponentList = serviceComponentList;
	}

	public String getServiceScheduleName() {
		return serviceScheduleName;
	}

	public void setServiceScheduleName(String serviceScheduleName) {
		this.serviceScheduleName = serviceScheduleName;
	}

	public String getServiceScheduleNameEdit() {
		return serviceScheduleNameEdit;
	}

	public void setServiceScheduleNameEdit(String serviceScheduleNameEdit) {
		this.serviceScheduleNameEdit = serviceScheduleNameEdit;
	}

	public String getSelectedComponents() {
		return selectedComponents;
	}

	public void setSelectedComponents(String selectedComponents) {
		this.selectedComponents = selectedComponents;
	}

	public String getOccurenceType() {
		return occurenceType;
	}

	public void setOccurenceType(String occurenceType) {
		this.occurenceType = occurenceType;
	}

	public List<String> getComponents() {
		return components;
	}

	public void setComponents(List<String> components) {
		this.components = components;
	}

	public List<String> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<String> selectedList) {
		this.selectedList = selectedList;
	}

	public List<String> getSelectedListOnEdit() {
	    return selectedListOnEdit;
    }

	public void setSelectedListOnEdit(List<String> selectedListOnEdit) {
	    this.selectedListOnEdit = selectedListOnEdit;
    }

}