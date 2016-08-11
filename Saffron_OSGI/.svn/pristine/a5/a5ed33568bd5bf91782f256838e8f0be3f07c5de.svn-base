package com.pcs.device.gateway.jace.message.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcs.device.gateway.jace.message.type.PointAccess;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

public class PointDetail {
	
	public static final String FOLDER_PATH = "folderPath";

	public static final String ACQUISITION_TYPE = "acquisitionType";

	public static final String HISTORY_PATH = "historyPath";

	public static final String REALTIME_PATH = "realtimePath";

	public static final String FACET = "facet";
	
	@JsonProperty("Point_Name")
	private String pointName;
	@JsonProperty("Handle_ID")
	private String pointId;
	@JsonProperty("Reading_Type")
	private String dataType;
	@JsonProperty("Real_Time_Path")
	private String realtimePath;
	@JsonProperty("History_Path")
	private String historyPath;
	@JsonProperty("History_Data_Type")
	private String acquisitionType;
	@JsonProperty("Alarm_Source_Path")
	private String alarmSourcePath;
	@JsonProperty("Folder_Path")
	private String folderPath;
	@JsonProperty("Facet(Format:-Seperated_by_Semicolon)")
	private String facet;
	@JsonProperty("Point_Status")
	private String status;
	@JsonProperty("Point_Access")
	private String accessType;
	@JsonProperty("Point_Value")
	private Object value;
	
	private List<PointExtension> extensions = new ArrayList<PointExtension>();
	
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		if(dataType.equalsIgnoreCase("Boolean")){
			this.dataType = PointDataTypes.BOOLEAN.name();
		}else if(dataType.equalsIgnoreCase("Real")){
			this.dataType = PointDataTypes.DOUBLE.name();
		}else{
			this.dataType = PointDataTypes.STRING.name();
		}
	}
	public String getRealtimePath() {
		return realtimePath;
	}
	public void setRealtimePath(String realtimePath) {
		PointExtension extension = new PointExtension();
		extension.setExtensionName(realtimePath);
		extension.setExtensionType(REALTIME_PATH);
		extensions.add(extension);
		this.realtimePath = realtimePath;
	}
	public String getHistoryPath() {
		return historyPath;
	}
	public void setHistoryPath(String historyPath) {
		PointExtension extension = new PointExtension();
		extension.setExtensionName(historyPath);
		extension.setExtensionType(HISTORY_PATH);
		extensions.add(extension);
		this.historyPath = historyPath;
	}
	public String getAcquisitionType() {
		return acquisitionType;
	}
	public void setAcquisitionType(String acquisitionType) {
		PointExtension extension = new PointExtension();
		extension.setExtensionName(acquisitionType);
		extension.setExtensionType(ACQUISITION_TYPE);
		extensions.add(extension);
		this.acquisitionType = acquisitionType;
	}
	public String getAlarmSourcePath() {
		return alarmSourcePath;
	}
	public void setAlarmSourcePath(String alarmSourcePath) {
		this.alarmSourcePath = alarmSourcePath;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		PointExtension extension = new PointExtension();
		extension.setExtensionName(folderPath);
		extension.setExtensionType(FOLDER_PATH);
		extensions.add(extension);
		this.folderPath = folderPath;
	}
	public String getFacet() {
		return facet;
	}
	public void setFacet(String facet) {
		PointExtension extension = new PointExtension();
		extension.setExtensionName(facet);
		extension.setExtensionType(FACET);
		extensions.add(extension);
		this.facet = facet;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		if(accessType.equalsIgnoreCase("r")){
			this.accessType = PointAccess.READONLY.name();
		}else if(accessType.equalsIgnoreCase("rw")){
			this.accessType = PointAccess.WRITEABLE.name();
		}
	}
	public List<PointExtension> getExtensions() {
		return extensions;
	}
	
	
	
	

}
