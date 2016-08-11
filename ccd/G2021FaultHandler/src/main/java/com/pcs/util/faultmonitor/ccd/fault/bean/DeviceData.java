package com.pcs.util.faultmonitor.ccd.fault.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"notificationVersion","messageType","Telematics_Box_ID",
	"serialNumber","vidNumber","occuranceDateTime","sentDateTime","active",
	"datalinkBus","sourceAddress","SPN","FMI","OC","latitude","longitude","altitude",
	"direction","speed","snapshots"})

public class DeviceData implements Serializable{

    private static final long serialVersionUID = -563008541076703183L;
    
	private boolean realtimeData;
	@JsonProperty("Notification_Version")
	private String notificationVersion;	
	@JsonProperty("Message_Type")
	private String messageType;	
	@JsonProperty("Telematics_Box_ID")
	private String deviceId;	
	private String make;
	private String model;	
	@JsonProperty("Engine_Serial_Number")
	private String serialNumber;		
	private String unitNumber;	
	@JsonProperty("VIN")
	private String vidNumber;	
	@JsonProperty("Occurrence_Date_Time")
	private String occuranceDateTime;	
	@JsonProperty("Sent_Date_Time")
	private String sentDateTime;	
	@JsonProperty("Active")
	private String active;	
	@JsonProperty("Datalink_Bus")
	private String datalinkBus;	
	@JsonProperty("Source_Address")
	private String sourceAddress;	
	@JsonProperty("Latitude")
	private String latitude;	
	@JsonProperty("Longitude")
	private String longitude;	
	@JsonProperty("Altitude")
	private String altitude;	
	@JsonProperty("Direction_Heading")
	private String direction;	
	@JsonProperty("GPS_Vehicle_Speed")
	private String speed;	
	@JsonIgnore
	private List<String> parameterNames;
	private List<Snapshot> snapshots;
	private List<FaultData> faultData;
	

	public boolean isRealtimeData() {
		return realtimeData;
	}

	public void setRealtimeData(boolean realtimeData) {
		this.realtimeData = realtimeData;
	}

	public String getNotificationVersion() {
		return notificationVersion;
	}

	public void setNotificationVersion(String notificationVersion) {
		this.notificationVersion = notificationVersion;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getVidNumber() {
		return vidNumber;
	}

	public void setVidNumber(String vidNumber) {
		this.vidNumber = vidNumber;
	}

	public String getOccuranceDateTime() {
		return occuranceDateTime;
	}

	public void setOccuranceDateTime(String occuranceDateTime) {
		this.occuranceDateTime = occuranceDateTime;
	}

	public String getSentDateTime() {
		return sentDateTime;
	}

	public void setSentDateTime(String sentDateTime) {
		this.sentDateTime = sentDateTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDatalinkBus() {
		return datalinkBus;
	}

	public void setDatalinkBus(String datalinkBus) {
		this.datalinkBus = datalinkBus;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
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

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public List<String> getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(List<String> parameterNames) {
		this.parameterNames = parameterNames;
	}

	public void addParameterNames(String parameterName) {
		if(this.parameterNames == null){
			this.parameterNames = new ArrayList<String>();
		}
		this.parameterNames.add(parameterName);
	}
	
	public List<Snapshot> getSnapshots() {
		return snapshots;
	}
	public void setSnapshots(List<Snapshot> snapshots) {
		this.snapshots = snapshots;
	}
	
	public void addSnapshot(Snapshot snapshot) {
		if(snapshots == null){
			snapshots = new ArrayList<Snapshot>();
		}
		snapshots.add(snapshot);
	}

	public List<FaultData> getFaultData() {
		return faultData;
	}

	public void setFaultData(List<FaultData> faultData) {
		this.faultData = faultData;
	}
	
	public void addFaultData(FaultData faultData) {
		if(this.faultData == null){
			this.faultData = new ArrayList<FaultData>();
		}
		this.faultData.add(faultData);
	}
	
}
