
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.ccd.heartbeat.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is responsible for defining properties of heart beat
 * 
 * @author pcseg129(Seena Jyothish)
 * Mar 31, 2016
 */
public class Heartbeat {
	
	@JsonProperty("Notification_Version")
	private String notificationVersion;
	
	@JsonProperty("Message_Type")
	private String messageType;
	
	@JsonProperty("Telematics_Box_ID")
	private String deviceId;
	
	@JsonProperty("Telematics_Partner_Message_ID")
	private String messageId;
	
	@JsonProperty("Telematics_Partner_Name")
	private String partnerName;
	
	@JsonProperty("Customer_Reference")
	private String customerReference;
	
	@JsonProperty("Equipment_ID")
	private String equipmentId;
	
	@JsonProperty("Engine_Serial_Number")
	private String serialNumber;
	
	@JsonProperty("VIN")
	private String vidNumber;
	
	@JsonProperty("Occurrence_Date_Time")
	private String occuranceDateTime;
	
	@JsonProperty("Sent_Date_Time")
	private String sentDateTime;
	
	@JsonProperty("Latitude")
	private String latitude;
	
	@JsonProperty("Longitude")
	private String longitude;
	
	@JsonProperty("Altitude")
	private String altitude;
	
	@JsonProperty("Direction_Heading")
	private String direction;
	
	@JsonProperty("Vehicle_Distance")
	private String distance;
	
	@JsonProperty("Location_Text_Description")
	private String location;
	
	@JsonProperty("GPS_Vehicle_Speed")
	private String speed;
	
	@JsonProperty("Snapshots")
	private List<Snapshot> snapshots;

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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public List<Snapshot> getSnapshots() {
		return snapshots;
	}

	public void setSnapshots(List<Snapshot> snapshots) {
		this.snapshots = snapshots;
	}
	
	
}
