package com.pcs.device.gateway.enevo.onecollect.api.snapshot.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class Container {
	public Container() {
		// TODO Auto-generated constructor stub
	}

	private long id;
	private int type;
	private String serial;
	private long containerSlot;
	private long siteContentType;
	private long site;
	private List<Device> devices;
	private String lastModified;

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public long getContainerSlot() {
		return containerSlot;
	}

	public void setContainerSlot(long containerSlot) {
		this.containerSlot = containerSlot;
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

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
}
