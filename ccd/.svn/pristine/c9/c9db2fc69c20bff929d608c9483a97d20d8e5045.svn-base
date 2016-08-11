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
package com.pcs.ccd.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.pcs.ccd.enums.MessageType;

/**
 * This class is responsible for holding fault response from Cummins
 * 
 * @author pcseg129(Seena Jyothish) Jan 24, 2016
 */
public class FaultResponse implements Serializable {

	private static final long serialVersionUID = 5005669554670138464L;

	@JsonProperty("Response_Version")
	private String responseVersion;

	@JsonProperty("Message_Type")
	private MessageType messageType;

	@JsonProperty("Notification_ID")
	private String notificationId;

	@JsonProperty("Telematics_Box_ID")
	private String telematicsBoxId;

	@JsonProperty("Telematics_Partner_Message_ID")
	private String telematicsPartnerMessageId;

	@JsonProperty("Telematics_Partner_Name")
	private String telematicsPartnerName;

	@JsonProperty("Customer_Reference")
	private String customerReference;

	@JsonProperty("Equipment_ID")
	private String equipmentId;

	@JsonProperty("Engine_Serial_Number")
	private String engineSerialNumber;

	@JsonProperty("VIN")
	private String VIN;

	@JsonProperty("Occurrence_Date_Time")
	private String occurrenceDateTime;

	@JsonProperty("Active")
	private String active;

	@JsonProperty("Datalink_Bus")
	private String datalinkBus;

	@JsonProperty("Source_Address")
	private String sourceAddress;

	@JsonProperty("SPN")
	private String SPN;

	@JsonProperty("FMI")
	private String FMI;

	@JsonProperty("Occurrence_Count")
	private Integer occurrenceCount;

	@JsonProperty("Latitude")
	private String latitude;

	@JsonProperty("Longitude")
	private String longitude;

	@JsonProperty("Altitude")
	private String altitude;

	@JsonProperty("Direction_Heading")
	private String directionHeading;

	@JsonProperty("Vehicle_Distance")
	private String vehicleDistance;

	@JsonProperty("Location_Text_Description")
	private String locationTextDescription;

	@JsonProperty("GPS_Vehicle_Speed")
	private String gpsVehicleSpeed;

	@JsonProperty("Fault_Code")
	private String faultCode;

	@JsonProperty("Fault_Code_Description")
	private String faultCodeDescription;

	@JsonProperty("Instruction_To_Fleet_Mgr")
	private String instructionToFleetMgr;

	@JsonProperty("Instruction_To_Operator")
	private String instructionToOperator;

	@JsonProperty("Additional_Info_To_Operator")
	private String additionalInfoToOperator;

	@JsonProperty("Derate_Flag")
	private String derateFlag;

	@JsonProperty("Derate_Value1")
	private String derateValue1;

	@JsonProperty("Derate_Value2")
	private String derateValue2;

	@JsonProperty("Derate_Value3")
	private String derateValue3;

	@JsonProperty("Lamp_Color")
	private String lampColor;

	@JsonProperty("Report_Type")
	private String reportType;

	@JsonProperty("Fault_Root_Cause1")
	private String faultRootCause1;

	@JsonProperty("Fault_Likelihood1")
	private String faultLikelihood1;

	@JsonProperty("Fault_Root_Cause2")
	private String faultRootCause2;

	@JsonProperty("Fault_Likelihood2")
	private String faultLikelihood2;

	@JsonProperty("Fault_Root_Cause3")
	private String faultRootCause3;

	@JsonProperty("Fault_Likelihood3")
	private String faultLikelihood3;

	@JsonProperty("Fault_Root_Cause4")
	private String faultRootCause4;

	@JsonProperty("Fault_Likelihood4")
	private String faultLikelihood4;

	@JsonProperty("Shutdown_Flag")
	private String shutdownFlag;

	@JsonProperty("Shutdown_Description")
	private String shutdownDescription;

	@JsonProperty("Priority")
	private String priority;

	@JsonProperty("Fault_Code_Category")
	private String faultCodeCategory;

	@JsonProperty("Additional_Diagnostic_Info")
	private String additionalDiagnosticInfo;

	@JsonProperty("Primary_Fault_Code")
	private String primaryFaultCode;

	@JsonProperty("Primary_SPN")
	private String primarySPN;

	@JsonProperty("Primary_FMI")
	private String primaryFMI;

	@JsonProperty("Primary_Occurrence_Date_Time")
	private String primaryOccurrenceDateTime;

	@JsonProperty("Primary_Fault_Code_Description")
	private String primaryFaultCodeDescription;

	@JsonProperty("Service_Locator_Link")
	private String serviceLocatorLink;

	@JsonProperty("Service_Model_Name")
	private String serviceModelName;

	@JsonProperty("General_Assistance_PhoneNumber")
	private String generalAssistancePhoneNumber;

	@JsonProperty("URL_Cummins_General")
	private String URLCumminsGeneral;

	@JsonProperty("URL_2")
	private String url2;

	@JsonProperty("URL_3")
	private String url3;

	@JsonProperty("URL_4")
	private String url4;

	@JsonProperty("Cummins_Name")
	private String cumminsName;

	@JsonProperty("OEM")
	private String oem;

	@JsonProperty("Service_Locator_Market_Application")
	private String serviceLocatorMarketApplication;

	@JsonProperty("Equipment_SubApplication")
	private String equipmentSubApplication;

	@JsonProperty("Related")
	List<RelatedFaultObject> relatedObjects;

	public String getResponseVersion() {
		return responseVersion;
	}

	public void setResponseVersion(String responseVersion) {
		this.responseVersion = responseVersion;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getTelematicsBoxId() {
		return telematicsBoxId;
	}

	public void setTelematicsBoxId(String telematicsBoxId) {
		this.telematicsBoxId = telematicsBoxId;
	}

	public String getTelematicsPartnerMessageId() {
		return telematicsPartnerMessageId;
	}

	public void setTelematicsPartnerMessageId(String telematicsPartnerMessageId) {
		this.telematicsPartnerMessageId = telematicsPartnerMessageId;
	}

	public String getTelematicsPartnerName() {
		return telematicsPartnerName;
	}

	public void setTelematicsPartnerName(String telematicsPartnerName) {
		this.telematicsPartnerName = telematicsPartnerName;
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

	public String getEngineSerialNumber() {
		return engineSerialNumber;
	}

	public void setEngineSerialNumber(String engineSerialNumber) {
		this.engineSerialNumber = engineSerialNumber;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getOccurrenceDateTime() {
		return occurrenceDateTime;
	}

	public void setOccurrenceDateTime(String occurrenceDateTime) {
		this.occurrenceDateTime = occurrenceDateTime;
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

	public String getSPN() {
		return SPN;
	}

	@JsonSetter("SPN")
	public void setSPN(String sPN) {
		SPN = sPN;
	}

	public String getFMI() {
		return FMI;
	}

	public void setFMI(String fMI) {
		FMI = fMI;
	}

	public Integer getOccurrenceCount() {
		return occurrenceCount;
	}

	public void setOccurrenceCount(Integer occurrenceCount) {
		this.occurrenceCount = occurrenceCount;
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

	public String getDirectionHeading() {
		return directionHeading;
	}

	public void setDirectionHeading(String directionHeading) {
		this.directionHeading = directionHeading;
	}

	public String getVehicleDistance() {
		return vehicleDistance;
	}

	public void setVehicleDistance(String vehicleDistance) {
		this.vehicleDistance = vehicleDistance;
	}

	public String getLocationTextDescription() {
		return locationTextDescription;
	}

	public void setLocationTextDescription(String locationTextDescription) {
		this.locationTextDescription = locationTextDescription;
	}

	public String getGpsVehicleSpeed() {
		return gpsVehicleSpeed;
	}

	public void setGpsVehicleSpeed(String gpsVehicleSpeed) {
		this.gpsVehicleSpeed = gpsVehicleSpeed;
	}

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public String getFaultCodeDescription() {
		return faultCodeDescription;
	}

	public void setFaultCodeDescription(String faultCodeDescription) {
		this.faultCodeDescription = faultCodeDescription;
	}

	public String getInstructionToFleetMgr() {
		return instructionToFleetMgr;
	}

	public void setInstructionToFleetMgr(String instructionToFleetMgr) {
		this.instructionToFleetMgr = instructionToFleetMgr;
	}

	public String getInstructionToOperator() {
		return instructionToOperator;
	}

	public void setInstructionToOperator(String instructionToOperator) {
		this.instructionToOperator = instructionToOperator;
	}

	public String getAdditionalInfoToOperator() {
		return additionalInfoToOperator;
	}

	public void setAdditionalInfoToOperator(String additionalInfoToOperator) {
		this.additionalInfoToOperator = additionalInfoToOperator;
	}

	public String getDerateFlag() {
		return derateFlag;
	}

	public void setDerateFlag(String derateFlag) {
		this.derateFlag = derateFlag;
	}

	public String getDerateValue1() {
		return derateValue1;
	}

	public void setDerateValue1(String derateValue1) {
		this.derateValue1 = derateValue1;
	}

	public String getDerateValue2() {
		return derateValue2;
	}

	public void setDerateValue2(String derateValue2) {
		this.derateValue2 = derateValue2;
	}

	public String getDerateValue3() {
		return derateValue3;
	}

	public void setDerateValue3(String derateValue3) {
		this.derateValue3 = derateValue3;
	}

	public String getLampColor() {
		return lampColor;
	}

	public void setLampColor(String lampColor) {
		this.lampColor = lampColor;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getFaultRootCause1() {
		return faultRootCause1;
	}

	public void setFaultRootCause1(String faultRootCause1) {
		this.faultRootCause1 = faultRootCause1;
	}

	public String getFaultLikelihood1() {
		return faultLikelihood1;
	}

	public void setFaultLikelihood1(String faultLikelihood1) {
		this.faultLikelihood1 = faultLikelihood1;
	}

	public String getFaultRootCause2() {
		return faultRootCause2;
	}

	public void setFaultRootCause2(String faultRootCause2) {
		this.faultRootCause2 = faultRootCause2;
	}

	public String getFaultLikelihood2() {
		return faultLikelihood2;
	}

	public void setFaultLikelihood2(String faultLikelihood2) {
		this.faultLikelihood2 = faultLikelihood2;
	}

	public String getFaultRootCause3() {
		return faultRootCause3;
	}

	public void setFaultRootCause3(String faultRootCause3) {
		this.faultRootCause3 = faultRootCause3;
	}

	public String getFaultLikelihood3() {
		return faultLikelihood3;
	}

	public void setFaultLikelihood3(String faultLikelihood3) {
		this.faultLikelihood3 = faultLikelihood3;
	}

	public String getFaultRootCause4() {
		return faultRootCause4;
	}

	public void setFaultRootCause4(String faultRootCause4) {
		this.faultRootCause4 = faultRootCause4;
	}

	public String getFaultLikelihood4() {
		return faultLikelihood4;
	}

	public void setFaultLikelihood4(String faultLikelihood4) {
		this.faultLikelihood4 = faultLikelihood4;
	}

	public String getShutdownFlag() {
		return shutdownFlag;
	}

	public void setShutdownFlag(String shutdownFlag) {
		this.shutdownFlag = shutdownFlag;
	}

	public String getShutdownDescription() {
		return shutdownDescription;
	}

	public void setShutdownDescription(String shutdownDescription) {
		this.shutdownDescription = shutdownDescription;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getFaultCodeCategory() {
		return faultCodeCategory;
	}

	public void setFaultCodeCategory(String faultCodeCategory) {
		this.faultCodeCategory = faultCodeCategory;
	}

	public String getAdditionalDiagnosticInfo() {
		return additionalDiagnosticInfo;
	}

	public void setAdditionalDiagnosticInfo(String additionalDiagnosticInfo) {
		this.additionalDiagnosticInfo = additionalDiagnosticInfo;
	}

	public String getPrimaryFaultCode() {
		return primaryFaultCode;
	}

	public void setPrimaryFaultCode(String primaryFaultCode) {
		this.primaryFaultCode = primaryFaultCode;
	}

	public String getPrimarySPN() {
		return primarySPN;
	}

	public void setPrimarySPN(String primarySPN) {
		this.primarySPN = primarySPN;
	}

	public String getPrimaryFMI() {
		return primaryFMI;
	}

	public void setPrimaryFMI(String primaryFMI) {
		this.primaryFMI = primaryFMI;
	}

	public String getPrimaryOccurrenceDateTime() {
		return primaryOccurrenceDateTime;
	}

	public void setPrimaryOccurrenceDateTime(String primaryOccurrenceDateTime) {
		this.primaryOccurrenceDateTime = primaryOccurrenceDateTime;
	}

	public String getPrimaryFaultCodeDescription() {
		return primaryFaultCodeDescription;
	}

	public void setPrimaryFaultCodeDescription(String primaryFaultCodeDescription) {
		this.primaryFaultCodeDescription = primaryFaultCodeDescription;
	}

	public String getServiceLocatorLink() {
		return serviceLocatorLink;
	}

	public void setServiceLocatorLink(String serviceLocatorLink) {
		this.serviceLocatorLink = serviceLocatorLink;
	}

	public String getServiceModelName() {
		return serviceModelName;
	}

	public void setServiceModelName(String serviceModelName) {
		this.serviceModelName = serviceModelName;
	}

	public String getGeneralAssistancePhoneNumber() {
		return generalAssistancePhoneNumber;
	}

	public void setGeneralAssistancePhoneNumber(String generalAssistancePhoneNumber) {
		this.generalAssistancePhoneNumber = generalAssistancePhoneNumber;
	}

	public String getURLCumminsGeneral() {
		return URLCumminsGeneral;
	}

	public void setURLCumminsGeneral(String uRLCumminsGeneral) {
		URLCumminsGeneral = uRLCumminsGeneral;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getUrl3() {
		return url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	public String getUrl4() {
		return url4;
	}

	public void setUrl4(String url4) {
		this.url4 = url4;
	}

	public String getCumminsName() {
		return cumminsName;
	}

	public void setCumminsName(String cumminsName) {
		this.cumminsName = cumminsName;
	}

	public String getOem() {
		return oem;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	public String getServiceLocatorMarketApplication() {
		return serviceLocatorMarketApplication;
	}

	public void setServiceLocatorMarketApplication(
	        String serviceLocatorMarketApplication) {
		this.serviceLocatorMarketApplication = serviceLocatorMarketApplication;
	}

	public String getEquipmentSubApplication() {
		return equipmentSubApplication;
	}

	public void setEquipmentSubApplication(String equipmentSubApplication) {
		this.equipmentSubApplication = equipmentSubApplication;
	}

	public List<RelatedFaultObject> getRelatedObjects() {
		return relatedObjects;
	}

	public void setRelatedObjects(List<RelatedFaultObject> relatedObjects) {
		this.relatedObjects = relatedObjects;
	}
	

}
