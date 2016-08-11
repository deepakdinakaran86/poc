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
package com.pcs.avocado.dto;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is responsible for defining the device point configuration
 * template and its properties
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 07 Jul 2015
 */
public class DeviceConfigTemplate implements Serializable {

	private static final long serialVersionUID = 2113068444110384105L;
	private String name;
	private String deviceMake;
	private String deviceType;
	private String deviceModel;
	private String deviceProtocol;
	private String deviceProtocolVersion;
	private String selectedPointId;
	private String configuredPoints;
	private String selectedPhysicalQuantity;
	private String selectedUnit;
	private String selectedDataType;
	private List<ConfigPoint> configPoints;
	private Boolean isDeleted;
	private String subId;
	
	private String selectedPoints;

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

	public String getSelectedPhysicalQuantity() {
		return selectedPhysicalQuantity;
	}

	public String getConfiguredPoints() {
		return configuredPoints;
	}

	public void setConfiguredPoints(String configuredPoints) {
		this.configuredPoints = configuredPoints;
	}

	public void setSelectedPhysicalQuantity(String selectedPhysicalQuantity) {
		this.selectedPhysicalQuantity = selectedPhysicalQuantity;
	}

	public String getSelectedUnit() {
		return selectedUnit;
	}

	public void setSelectedUnit(String selectedUnit) {
		this.selectedUnit = selectedUnit;
	}

	public String getSelectedDataType() {
		return selectedDataType;
	}

	public void setSelectedDataType(String selectedDataType) {
		this.selectedDataType = selectedDataType;
	}

	public String getSelectedPointId() {
		return selectedPointId;
	}

	public void setSelectedPointId(String selectedPointId) {
		this.selectedPointId = selectedPointId;
	}

	public String getSelectedPoints() {
		return selectedPoints;
	}

	public void setSelectedPoints(String selectedPoints) {
		this.selectedPoints = selectedPoints;
	}
	
	public void prepareConfigPoints(){
		List<ConfigPoint> points = new ArrayList<ConfigPoint>();
		try {
			points = new ObjectMapper().readValue(selectedPoints, new TypeReference<List<ConfigPoint>>() { });
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(ConfigPoint configPoint : points){
			try {
				configPoint.setExpression( URLEncoder.encode(configPoint.getExpression(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				//logger.error("Error occurred in encoding expression ");
			}
		}
		this.configPoints = points;
	}

}
