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
package com.pcs.fault.monitor.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.pcs.fault.monitor.enums.MessageType;

/**
 * This class is responsible for holding fault response from Cummins
 * 
 * @author pcseg129(Seena Jyothish) Jan 24, 2016
 */
public class FaultResponse implements Serializable {

	private static final long serialVersionUID = 5005669554670138464L;

	
	private String responseVersion;

	private MessageType messageType;

	private String notificationId;

	private String telematicsBoxId;

	private String telematicsPartnerMessageId;

	private String telematicsPartnerName;

	private String customerReference;

	private String equipmentId;

	private String engineSerialNumber;

	@JsonProperty("VIN")
	private String VIN;

	private String occurrenceDateTime;

	private String active;

	private String datalinkBus;

	private String sourceAddress;

	private String SPN;

	@JsonProperty("FMI")
	private String FMI;

	private Integer occurrenceCount;

	private String latitude;

	private String longitude;

	private String altitude;

	private String directionHeading;

	private String vehicleDistance;

	private String locationTextDescription;

	private String gpsVehicleSpeed;

	private String faultCode;

	private String faultCodeDescription;

	private String instructionToFleetMgr;

	private String instructionToOperator;

	private String additionalInfoToOperator;

	private String derateFlag;

	private String derateValue1;

	private String derateValue2;

	private String derateValue3;

	private String lampColor;

	private String reportType;

	private String faultRootCause1;

	private String faultLikelihood1;

	private String faultRootCause2;

	private String faultLikelihood2;

	private String faultRootCause3;

	private String faultLikelihood3;

	private String faultRootCause4;

	private String faultLikelihood4;

	private String shutdownFlag;

	private String shutdownDescription;

	private String priority;

	private String faultCodeCategory;

	private String additionalDiagnosticInfo;

	private String primaryFaultCode;

	private String primarySPN;

	private String primaryFMI;

	private String primaryOccurrenceDateTime;

	private String primaryFaultCodeDescription;

	private String serviceLocatorLink;

	private String serviceModelName;

	private String generalAssistancePhoneNumber;

	@JsonProperty("URLCumminsGeneral")
	private String URLCumminsGeneral;

	private String url2;

	private String url3;

	private String url4;

	private String cumminsName;

	private String oem;

	private String serviceLocatorMarketApplication;

	private String equipmentSubApplication;

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

	@JsonGetter("SPN")
	public String getSPN() {
		return SPN;
	}

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
