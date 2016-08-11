/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This class is responsible for defining the device point configuration
 * template and its properties
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 07 Jul 2015
 */
public class DeviceConfigTemplate implements Serializable{
	
    private static final long serialVersionUID = 2113068444110384105L;
   
	//@SerializedName("name")
	private String name;
	//@SerializedName("deviceMake")
	private String deviceMake;
	//@SerializedName("deviceType")
	private String deviceType;
	//@SerializedName("deviceModel")
	private String deviceModel;
	//@SerializedName("deviceProtocol")
	private String deviceProtocol;
	//@SerializedName("deviceProtocolVersion")
	private String deviceProtocolVersion;
	//@SerializedName("configPoints")
	private List<ConfigPoint> configPoints;
	private Boolean isDeleted;
	private String subId;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDeviceMake() {
		return deviceMake;
	}

	public void setDeviceMake(String deviceMake) {
		this.deviceMake = deviceMake;
	}

	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceProtocol() {
		return deviceProtocol;
	}

	public void setDeviceProtocol(String deviceProtocol) {
		this.deviceProtocol = deviceProtocol;
	}

	public String getDeviceProtocolVersion() {
		return deviceProtocolVersion;
	}

	public void setDeviceProtocolVersion(String deviceProtocolVersion) {
		this.deviceProtocolVersion = deviceProtocolVersion;
	}

	public List<ConfigPoint> getConfigPoints() {
		return configPoints;
	}
	
	public void setConfigPoints(List<ConfigPoint> configPoints) {
		this.configPoints = configPoints;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
