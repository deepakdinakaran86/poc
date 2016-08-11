package com.pcs.ccd.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaultDataWrapper implements Serializable{
    
	private static final long serialVersionUID = 7279807615556676243L;

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
	
	@JsonProperty("Snapshots")
	private List<FaultSnapshot> snapshots;
	
	
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
	public List<FaultSnapshot> getSnapshots() {
		return snapshots;
	}
	public void setSnapshots(List<FaultSnapshot> snapshots) {
		this.snapshots = snapshots;
	}
	
	public void addSnapshot(FaultSnapshot snapshot) {
		if(snapshots == null){
			snapshots = new ArrayList<FaultSnapshot>();
		}
		snapshots.add(snapshot);
	}

}
