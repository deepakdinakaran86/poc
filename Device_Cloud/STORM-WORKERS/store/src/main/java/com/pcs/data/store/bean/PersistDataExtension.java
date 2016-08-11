package com.pcs.data.store.bean;

import java.io.Serializable;

public class PersistDataExtension implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1450983772127541615L;
	
	private String extensionName;
	
	private String extensionType;
	
	public String getExtensionName() {
		return extensionName;
	}
	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}
	public String getExtensionType() {
		return extensionType;
	}
	public void setExtensionType(String extensionType) {
		this.extensionType = extensionType;
	}
}
