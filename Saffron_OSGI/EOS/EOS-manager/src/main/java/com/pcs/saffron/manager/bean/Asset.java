package com.pcs.saffron.manager.bean;

import java.io.Serializable;
import java.util.List;

public class Asset implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7108310975199861521L;

	private String assetName;
	private String assetId;
	private String serialNumber;
	private List<String>tags;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAssetName() {
		return assetName;
	}
	public String getAssetId() {
		return assetId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	
}
