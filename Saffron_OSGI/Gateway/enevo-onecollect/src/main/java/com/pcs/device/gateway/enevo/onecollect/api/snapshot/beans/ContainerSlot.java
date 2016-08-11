package com.pcs.device.gateway.enevo.onecollect.api.snapshot.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class ContainerSlot {

	public ContainerSlot() {
		// TODO Auto-generated constructor stub
	}

	private long id;
	private String name;
	private int contentType;
	private int containerType;
	private long siteContentType;
	private long site;
	private int fillLevel;
	private String dateWhenFull;
	private boolean photo;
	private String lastCollection;
	private String lastModified;
	private Container container;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getContainerType() {
		return containerType;
	}

	public void setContainerType(int containerType) {
		this.containerType = containerType;
	}

	public long getSiteContentType() {
		return siteContentType;
	}

	public void setSiteContentType(long siteContentType) {
		this.siteContentType = siteContentType;
	}

	public long getSite() {
		return site;
	}

	public void setSite(long site) {
		this.site = site;
	}

	public int getFillLevel() {
		return fillLevel;
	}

	public void setFillLevel(int fillLevel) {
		this.fillLevel = fillLevel;
	}

	public String getDateWhenFull() {
		return dateWhenFull;
	}

	public void setDateWhenFull(String dateWhenFull) {
		this.dateWhenFull = dateWhenFull;
	}

	public boolean isPhoto() {
		return photo;
	}

	public void setPhoto(boolean photo) {
		this.photo = photo;
	}

	public String getLastCollection() {
		return lastCollection;
	}

	public void setLastCollection(String lastCollection) {
		this.lastCollection = lastCollection;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

}
