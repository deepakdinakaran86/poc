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
package com.pcs.ccd.services;

import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.ccd.constants.Constants.IDENTIFIER;
import static com.pcs.ccd.constants.Constants.MARKER;
import static com.pcs.ccd.enums.EquipmentFields.ASSET_NAME;
import static com.pcs.ccd.enums.FCResponseFields.ACTIVE;
import static com.pcs.ccd.enums.FCResponseFields.ADD_DIAGC_INFO;
import static com.pcs.ccd.enums.FCResponseFields.ADD_INFO_TO_OPR;
import static com.pcs.ccd.enums.FCResponseFields.ALTITUDE;
import static com.pcs.ccd.enums.FCResponseFields.CUMMINS_NAME;
import static com.pcs.ccd.enums.FCResponseFields.CUST_REF;
import static com.pcs.ccd.enums.FCResponseFields.DATA_LINK_BUS;
import static com.pcs.ccd.enums.FCResponseFields.DERATE_FLAG;
import static com.pcs.ccd.enums.FCResponseFields.DERATE_VALUE_1;
import static com.pcs.ccd.enums.FCResponseFields.DERATE_VALUE_2;
import static com.pcs.ccd.enums.FCResponseFields.DERATE_VALUE_3;
import static com.pcs.ccd.enums.FCResponseFields.DIR_HEADING;
import static com.pcs.ccd.enums.FCResponseFields.ENG_SERIAL_NUMBER;
import static com.pcs.ccd.enums.FCResponseFields.EQUIP_ID;
import static com.pcs.ccd.enums.FCResponseFields.EQUIP_SUB_APP;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_CODE;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_CODE_CAT;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_CODE_DESC;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_LIKE_1;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_LIKE_2;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_LIKE_3;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_LIKE_4;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_ROOT_CAUSE_1;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_ROOT_CAUSE_2;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_ROOT_CAUSE_3;
import static com.pcs.ccd.enums.FCResponseFields.FAULT_ROOT_CAUSE_4;
import static com.pcs.ccd.enums.FCResponseFields.FMI;
import static com.pcs.ccd.enums.FCResponseFields.GEN_ASSIST_PHONE;
import static com.pcs.ccd.enums.FCResponseFields.GPS_VEH_SPEED;
import static com.pcs.ccd.enums.FCResponseFields.INST_TO_FLEET_MGR;
import static com.pcs.ccd.enums.FCResponseFields.INST_TO_OPR;
import static com.pcs.ccd.enums.FCResponseFields.LAMP_COLOR;
import static com.pcs.ccd.enums.FCResponseFields.LATITUDE;
import static com.pcs.ccd.enums.FCResponseFields.LOC_TEXT_DESC;
import static com.pcs.ccd.enums.FCResponseFields.LONGITUDE;
import static com.pcs.ccd.enums.FCResponseFields.MSG_TYPE;
import static com.pcs.ccd.enums.FCResponseFields.NOTIFICATION_ID;
import static com.pcs.ccd.enums.FCResponseFields.OCC_COUNT;
import static com.pcs.ccd.enums.FCResponseFields.OCC_DATE_TIME;
import static com.pcs.ccd.enums.FCResponseFields.OEM;
import static com.pcs.ccd.enums.FCResponseFields.PRIMARY_FAULT_CODE;
import static com.pcs.ccd.enums.FCResponseFields.PRIMARY_FAULT_CODE_DES;
import static com.pcs.ccd.enums.FCResponseFields.PRIMARY_FMI;
import static com.pcs.ccd.enums.FCResponseFields.PRIMARY_OCC_DATETIME;
import static com.pcs.ccd.enums.FCResponseFields.PRIMARY_SPN;
import static com.pcs.ccd.enums.FCResponseFields.PRIORITY;
import static com.pcs.ccd.enums.FCResponseFields.REPORT_TYPE;
import static com.pcs.ccd.enums.FCResponseFields.RESP_VERSION;
import static com.pcs.ccd.enums.FCResponseFields.SHUTDOWN_DESC;
import static com.pcs.ccd.enums.FCResponseFields.SHUTDOWN_FLAG;
import static com.pcs.ccd.enums.FCResponseFields.SPN;
import static com.pcs.ccd.enums.FCResponseFields.SRC_ADDRESS;
import static com.pcs.ccd.enums.FCResponseFields.SRV_LOCATOR_LINK;
import static com.pcs.ccd.enums.FCResponseFields.SRV_LOCATOR_MARKET_APP;
import static com.pcs.ccd.enums.FCResponseFields.SRV_MODEL_NAME;
import static com.pcs.ccd.enums.FCResponseFields.TELE_BOX_ID;
import static com.pcs.ccd.enums.FCResponseFields.TELE_PARTNER_MSG_ID;
import static com.pcs.ccd.enums.FCResponseFields.TELE_PARTNER_NAME;
import static com.pcs.ccd.enums.FCResponseFields.URL2;
import static com.pcs.ccd.enums.FCResponseFields.URL3;
import static com.pcs.ccd.enums.FCResponseFields.URL4;
import static com.pcs.ccd.enums.FCResponseFields.URL_CUMMINS_GEN;
import static com.pcs.ccd.enums.FCResponseFields.VEH_DIST;
import static com.pcs.ccd.enums.FCResponseFields.VIN;
import static com.pcs.ccd.enums.FaultDataFields.EVENT_STATUS;
import static com.pcs.ccd.enums.FaultDataFields.OC;
import static com.pcs.ccd.enums.FaultDataFields.RAW_IDENTIFIER;
import static com.pcs.ccd.enums.FaultDataFields.RESP_RECEIVE_STATUS;
import static com.pcs.ccd.enums.FaultDataFields.SOURCE_ID;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.HierarchyDTO;
import com.pcs.avocado.commons.dto.HierarchySearchDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.PlatformEntityDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.util.ObjectBuilderUtil;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.ccd.bean.DomainObj;
import com.pcs.ccd.bean.EntityTemplateObj;
import com.pcs.ccd.bean.FaultData;
import com.pcs.ccd.bean.FaultEventInfoReq;
import com.pcs.ccd.bean.FaultResponse;
import com.pcs.ccd.bean.MarkerObject;
import com.pcs.ccd.enums.EventStatus;
import com.pcs.ccd.enums.FCResponseFields;
import com.pcs.ccd.enums.Integraters;
import com.pcs.ccd.enums.MessageType;
import com.pcs.ccd.enums.RespReceiveStatus;
import com.pcs.ccd.publisher.MessagePublisher;
import com.pcs.ccd.token.TokenProvider;

/**
 * This class is responsible for implementing all services related to receiving
 * fault code response
 * 
 * @author pcseg129(Seena Jyothish) Jan 24, 2016
 */
@Service
public class FCResponseServiceImpl implements FCResponseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(FCResponseServiceImpl.class);

	@Autowired
	@Qualifier("apiMgrClient")
	private BaseClient apiMgrClient;

	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	@Autowired
	FaultEventService faultEventService;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Value("${ccd.fault.response.template}")
	private String faultRespTemp;

	@Value("${domain.name}")
	private String domainName;

	@Value("${ccd.equipment.template}")
	private String equipmentTemp;

	@Value("${ccd.fault.event.template}")
	private String faultEvtTemp;
	
	@Value("${ccd.fault.response.template}")
	private String responseTemplate;

	@Value("${api.mgr.marker}")
	private String markerPlatformEndpoint;

	@Value("${api.mgr.hierarchy.attach}")
	private String heirarchyAttachEP;
	
	@Value("${api.mgr.hierarchy.children}")
	private String heirarchyChildrenEP;

	@Override
	public StatusMessageDTO publishResponse(FaultResponse faultResponse) {
		StatusMessageDTO status = new StatusMessageDTO();
		try {
			status = messagePublisher.publishToKafkaTopic(
			        "fault_response_stream", objectBuilderUtil.getGson()
			                .toJson(faultResponse));
			StatusMessageDTO status1 = persistResponse(faultResponse);
			if (status1.getStatus().equals(Status.SUCCESS)) {
				LOGGER.info("response persisted for notifiecation {}",
				        faultResponse.getNotificationId());
			}
		} catch (RemoteException e) {
			status.setStatus(Status.FAILURE);
			LOGGER.error(
			        "Error publishing response to kafka for response notifiecation {}",
			        faultResponse.getNotificationId());
		}
		return status;
	}

	@Override
	public StatusMessageDTO persistResponse(FaultResponse faultResponse) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		String responseRowIdentifier = persistFaultResponse(faultResponse);

		FaultData faultData = getFaultEvent(faultResponse);
		if (faultData != null) {
			String faultDataRowIdentifier = getFaultDataRowIdentifier(faultData);

			faultData.setRespReceiveStatus(RespReceiveStatus.RECEIVED);
			status = faultEventService.updateFaultEvent(faultData);
			if (status.getStatus().equals(Status.SUCCESS)) {
				LOGGER.info("updated response received status for device {}",
				        faultResponse.getTelematicsBoxId());
			} else {
				LOGGER.info("unable to find a fault event of device {}",
				        faultResponse.getTelematicsBoxId());
			}
			if (!StringUtils.isBlank(responseRowIdentifier)
			        && !StringUtils.isBlank(faultDataRowIdentifier)) {
				attachFaultToEquipment(faultDataRowIdentifier,
				        responseRowIdentifier);
				status.setStatus(Status.SUCCESS);
			} else {
				LOGGER.error("Error in attach fault event to equipment, either faultRowIdentifier or equipmentRowIdentifier is blank");
			}
		}
		status.setStatus(Status.SUCCESS);
		return status;
	}

	@Override
    public FaultResponse getFaultEventResponse(String faultRowIdentifier) {
		validateMandatoryField(RAW_IDENTIFIER.getDescription(), faultRowIdentifier);
		if(!faultEventService.isFaultEventExists(faultRowIdentifier)){
			throw new GalaxyException(INVALID_DATA_SPECIFIED,RAW_IDENTIFIER.getDescription()+" "+
			        faultRowIdentifier );
		}
			return getAttachedFaultResponse(faultRowIdentifier);
    }
	
	private FaultResponse getAttachedFaultResponse(String faultEventIdentifier) {
		List<EntityDTO> faultResponse = null;
		faultResponse = apiMgrClient.post(heirarchyChildrenEP,
				tokenProvider.getAuthToken(Integraters.CCD),
		        getAttachedResponsePayload(faultEventIdentifier), List.class,
		        EntityDTO.class);
		return convertToFaultResponse(faultResponse.get(0).getDataprovider(),faultResponse.get(0).getIdentifier());
	}
	
	/*private List<FaultResponse> convertToFaultResponse(List<EntityDTO> entityDTOs){
		List<FaultResponse> faultResponses = new ArrayList<FaultResponse>();
		for(EntityDTO entityDTO : entityDTOs){
			FaultResponse faultResponse = convertToFaultResponse(entityDTO.getDataprovider(),entityDTO.getIdentifier());
			faultResponses.add(faultResponse);
		}
		return faultResponses;
	}*/
	
	private FaultResponse convertToFaultResponse(List<FieldMapDTO> dataProvider,FieldMapDTO identifier){
		FaultResponse faultResponse  = new FaultResponse();
		faultResponse.setResponseVersion(fetchFieldValue(dataProvider,RESP_VERSION.getFieldName()));
		faultResponse.setMessageType(MessageType.valueOf(fetchFieldValue(dataProvider,MSG_TYPE.getFieldName())));
		faultResponse.setNotificationId(fetchFieldValue(dataProvider,NOTIFICATION_ID.getFieldName()));
		faultResponse.setTelematicsBoxId(fetchFieldValue(dataProvider,TELE_BOX_ID.getFieldName()));
		faultResponse.setTelematicsPartnerMessageId(fetchFieldValue(dataProvider,TELE_PARTNER_MSG_ID.getFieldName()));
		faultResponse.setTelematicsPartnerName(fetchFieldValue(dataProvider,TELE_PARTNER_NAME.getFieldName()));
		faultResponse.setCustomerReference(fetchFieldValue(dataProvider,CUST_REF.getFieldName()));
		faultResponse.setEquipmentId(fetchFieldValue(dataProvider,EQUIP_ID.getFieldName()));
		faultResponse.setEngineSerialNumber(fetchFieldValue(dataProvider,ENG_SERIAL_NUMBER.getFieldName()));
		faultResponse.setVIN(fetchFieldValue(dataProvider,VIN.getFieldName()));
		faultResponse.setOccurrenceDateTime(fetchFieldValue(dataProvider,OCC_DATE_TIME.getFieldName()));
		faultResponse.setActive(fetchFieldValue(dataProvider,ACTIVE.getFieldName()));
		faultResponse.setDatalinkBus(fetchFieldValue(dataProvider,DATA_LINK_BUS.getFieldName()));
		faultResponse.setSourceAddress(fetchFieldValue(dataProvider,SRC_ADDRESS.getFieldName()));
		faultResponse.setSPN(fetchFieldValue(dataProvider,SPN.getFieldName()));
		faultResponse.setFMI(fetchFieldValue(dataProvider,FMI.getFieldName()));
		faultResponse.setOccurrenceCount(Integer.parseInt(fetchFieldValue(dataProvider,OCC_COUNT.getFieldName())));
		faultResponse.setLatitude(fetchFieldValue(dataProvider,LATITUDE.getFieldName()));
		faultResponse.setLongitude(fetchFieldValue(dataProvider,LONGITUDE.getFieldName()));
		faultResponse.setAltitude(fetchFieldValue(dataProvider,ALTITUDE.getFieldName()));
		faultResponse.setDirectionHeading(fetchFieldValue(dataProvider,DIR_HEADING.getFieldName()));
		faultResponse.setVehicleDistance(fetchFieldValue(dataProvider,VEH_DIST.getFieldName()));
		faultResponse.setLocationTextDescription(fetchFieldValue(dataProvider,LOC_TEXT_DESC.getFieldName()));
		faultResponse.setGpsVehicleSpeed(fetchFieldValue(dataProvider,GPS_VEH_SPEED.getFieldName()));
		faultResponse.setFaultCode(fetchFieldValue(dataProvider,FAULT_CODE.getFieldName()));
		faultResponse.setFaultCodeDescription(fetchFieldValue(dataProvider,FAULT_CODE_DESC.getFieldName()));
		faultResponse.setInstructionToFleetMgr(fetchFieldValue(dataProvider,INST_TO_FLEET_MGR.getFieldName()));
		faultResponse.setInstructionToOperator(fetchFieldValue(dataProvider,INST_TO_OPR.getFieldName()));
		faultResponse.setAdditionalInfoToOperator(fetchFieldValue(dataProvider,ADD_INFO_TO_OPR.getFieldName()));
		faultResponse.setDerateFlag(fetchFieldValue(dataProvider,DERATE_FLAG.getFieldName()));
		faultResponse.setDerateValue1(fetchFieldValue(dataProvider,DERATE_VALUE_1.getFieldName()));
		faultResponse.setDerateValue2(fetchFieldValue(dataProvider,DERATE_VALUE_2.getFieldName()));
		faultResponse.setDerateValue3(fetchFieldValue(dataProvider,DERATE_VALUE_3.getFieldName()));
		faultResponse.setLampColor(fetchFieldValue(dataProvider,LAMP_COLOR.getFieldName()));
		faultResponse.setReportType(fetchFieldValue(dataProvider,REPORT_TYPE.getFieldName()));
		faultResponse.setFaultRootCause1(fetchFieldValue(dataProvider,FAULT_ROOT_CAUSE_1.getFieldName()));
		faultResponse.setFaultRootCause2(fetchFieldValue(dataProvider,FAULT_ROOT_CAUSE_2.getFieldName()));
		faultResponse.setFaultRootCause3(fetchFieldValue(dataProvider,FAULT_ROOT_CAUSE_3.getFieldName()));
		faultResponse.setFaultRootCause4(fetchFieldValue(dataProvider,FAULT_ROOT_CAUSE_4.getFieldName()));
		faultResponse.setFaultLikelihood1(fetchFieldValue(dataProvider,FAULT_LIKE_1.getFieldName()));
		faultResponse.setFaultLikelihood2(fetchFieldValue(dataProvider,FAULT_LIKE_2.getFieldName()));
		faultResponse.setFaultLikelihood3(fetchFieldValue(dataProvider,FAULT_LIKE_3.getFieldName()));
		faultResponse.setFaultLikelihood4(fetchFieldValue(dataProvider,FAULT_LIKE_4.getFieldName()));
		faultResponse.setShutdownFlag(fetchFieldValue(dataProvider,SHUTDOWN_FLAG.getFieldName()));
		faultResponse.setShutdownDescription(fetchFieldValue(dataProvider,SHUTDOWN_DESC.getFieldName()));
		faultResponse.setPriority(fetchFieldValue(dataProvider,PRIORITY.getFieldName()));
		faultResponse.setFaultCodeCategory(fetchFieldValue(dataProvider,FAULT_CODE_CAT.getFieldName()));
		faultResponse.setAdditionalDiagnosticInfo(fetchFieldValue(dataProvider,ADD_DIAGC_INFO.getFieldName()));
		faultResponse.setPrimaryFaultCode(fetchFieldValue(dataProvider,PRIMARY_FAULT_CODE.getFieldName()));
		faultResponse.setPrimarySPN(fetchFieldValue(dataProvider,PRIMARY_SPN.getFieldName()));
		faultResponse.setPrimaryFMI(fetchFieldValue(dataProvider,PRIMARY_FMI.getFieldName()));
		faultResponse.setPrimaryOccurrenceDateTime(fetchFieldValue(dataProvider,PRIMARY_OCC_DATETIME.getFieldName()));
		faultResponse.setPrimaryFaultCodeDescription(fetchFieldValue(dataProvider,PRIMARY_FAULT_CODE_DES.getFieldName()));
		faultResponse.setServiceLocatorLink(fetchFieldValue(dataProvider,SRV_LOCATOR_LINK.getFieldName()));
		faultResponse.setServiceModelName(fetchFieldValue(dataProvider,SRV_MODEL_NAME.getFieldName()));
		faultResponse.setGeneralAssistancePhoneNumber(fetchFieldValue(dataProvider,GEN_ASSIST_PHONE.getFieldName()));
		faultResponse.setURLCumminsGeneral(fetchFieldValue(dataProvider,URL_CUMMINS_GEN.getFieldName()));
		faultResponse.setUrl2(fetchFieldValue(dataProvider,URL2.getFieldName()));
		faultResponse.setUrl3(fetchFieldValue(dataProvider,URL3.getFieldName()));
		faultResponse.setUrl4(fetchFieldValue(dataProvider,URL4.getFieldName()));
		faultResponse.setCumminsName(fetchFieldValue(dataProvider,CUMMINS_NAME.getFieldName()));
		faultResponse.setOem(fetchFieldValue(dataProvider,OEM.getFieldName()));
		faultResponse.setServiceLocatorMarketApplication(fetchFieldValue(dataProvider,SRV_LOCATOR_MARKET_APP.getFieldName()));
		faultResponse.setEquipmentSubApplication(fetchFieldValue(dataProvider,EQUIP_SUB_APP.getFieldName()));
		faultResponse.setShutdownFlag(fetchFieldValue(dataProvider,SHUTDOWN_FLAG.getFieldName()));
		
		return faultResponse;
	}
	
	private String fetchFieldValue(List<FieldMapDTO> keyValueObjects, String key) {
		FieldMapDTO keyValueObject = new FieldMapDTO();
		keyValueObject.setKey(key);
		FieldMapDTO field = new FieldMapDTO();
		field.setKey(keyValueObject.getKey());
		int fieldIndex = keyValueObjects.indexOf(field);
		String value = fieldIndex != -1 ? keyValueObjects.get(fieldIndex)
		        .getValue() : null;
		return value;
	}
	
	private boolean attachFaultToEquipment(String faultDataRowIdentifier,
	        String responseRowIdentifier) {
		StatusMessageDTO status = new StatusMessageDTO();
		try {
			status = apiMgrClient.post(
			        heirarchyAttachEP,
			        tokenProvider.getAuthToken(Integraters.CCD),
			        createAttachResponsePayload(faultDataRowIdentifier,
			                responseRowIdentifier), StatusMessageDTO.class);
		} catch (GalaxyException e) {
			if (status == null || status.getStatus().equals(Status.FAILURE)) {
				LOGGER.error(
				        "Error in attach fault response to fault eventt for  {} ",
				        faultDataRowIdentifier);
				throw new GalaxyException(e);
			}
		}
		return true;
	}

	private String persistFaultResponse(FaultResponse faultResponse) {
		IdentityDTO identityDTO = apiMgrClient.post(markerPlatformEndpoint,
		        tokenProvider.getAuthToken(Integraters.CCD),
		        createFaultRespObj(faultResponse), IdentityDTO.class);
		return getFaultRowIdentifier(identityDTO);
	}

	private String getFaultRowIdentifier(IdentityDTO identityDTO) {
		return identityDTO.getIdentifier().getValue();
	}

	private FaultData getFaultEvent(FaultResponse faultResponse) {
		FaultEventInfoReq eventInfoReq = createFaultEvtReq(faultResponse);
		FaultData faultData = faultEventService.getFaultEvent(eventInfoReq);
		return faultData;
	}

	private String getFaultDataRowIdentifier(FaultData faultData) {
		return faultData.getReadOnlyRawIdentifier();
	}

	private MarkerObject createFaultRespObj(FaultResponse fcResp) {
		MarkerObject markerObject = new MarkerObject();
		List<FieldMapDTO> fieldValues = new ArrayList<FieldMapDTO>();

		fieldValues.add(createKeyValueObj(
		        FCResponseFields.RESP_VERSION.getFieldName(),
		        fcResp.getResponseVersion()));
		fieldValues.add(createKeyValueObj(FCResponseFields.MSG_TYPE
		        .getFieldName(), fcResp.getMessageType().toString()));

		fieldValues.add(createKeyValueObj(
		        FCResponseFields.NOTIFICATION_ID.getFieldName(),
		        fcResp.getNotificationId()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.TELE_BOX_ID.getFieldName(),
		        fcResp.getTelematicsBoxId()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.TELE_PARTNER_MSG_ID.getFieldName(),
		        fcResp.getTelematicsPartnerMessageId()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.TELE_PARTNER_NAME.getFieldName(),
		        fcResp.getTelematicsPartnerName()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.CUST_REF.getFieldName(),
		        fcResp.getCustomerReference()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.EQUIP_ID.getFieldName(),
		        fcResp.getEquipmentId()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.ENG_SERIAL_NUMBER.getFieldName(),
		        fcResp.getEngineSerialNumber()));
		fieldValues.add(createKeyValueObj(FCResponseFields.VIN.getFieldName(),
		        fcResp.getVIN()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.OCC_DATE_TIME.getFieldName(),
		        fcResp.getOccurrenceDateTime()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.ACTIVE.getFieldName(), fcResp.getActive()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.DATA_LINK_BUS.getFieldName(),
		        fcResp.getDatalinkBus()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.SRC_ADDRESS.getFieldName(),
		        fcResp.getSourceAddress()));
		fieldValues.add(createKeyValueObj(FCResponseFields.SPN.getFieldName(),
		        fcResp.getSPN()));
		fieldValues.add(createKeyValueObj(FCResponseFields.FMI.getFieldName(),
		        fcResp.getFMI()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.OCC_COUNT.getFieldName(),
		        String.valueOf(fcResp.getOccurrenceCount())));
		fieldValues
		        .add(createKeyValueObj(
		                FCResponseFields.LATITUDE.getFieldName(),
		                fcResp.getLatitude()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.LONGITUDE.getFieldName(),
		        fcResp.getLongitude()));
		fieldValues
		        .add(createKeyValueObj(
		                FCResponseFields.ALTITUDE.getFieldName(),
		                fcResp.getAltitude()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.DIR_HEADING.getFieldName(),
		        fcResp.getDirectionHeading()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.VEH_DIST.getFieldName(),
		        fcResp.getVehicleDistance()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.LOC_TEXT_DESC.getFieldName(),
		        fcResp.getLocationTextDescription()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.GPS_VEH_SPEED.getFieldName(),
		        fcResp.getGpsVehicleSpeed()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_CODE.getFieldName(),
		        fcResp.getFaultCode()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_CODE_DESC.getFieldName(),
		        fcResp.getFaultCodeDescription()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.INST_TO_FLEET_MGR.getFieldName(),
		        fcResp.getInstructionToFleetMgr()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.INST_TO_OPR.getFieldName(),
		        fcResp.getInstructionToOperator()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.ADD_INFO_TO_OPR.getFieldName(),
		        fcResp.getAdditionalInfoToOperator()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.DERATE_FLAG.getFieldName(),
		        fcResp.getDerateFlag()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.DERATE_VALUE_1.getFieldName(),
		        fcResp.getDerateValue1()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.DERATE_VALUE_2.getFieldName(),
		        fcResp.getDerateValue2()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.DERATE_VALUE_3.getFieldName(),
		        fcResp.getDerateValue3()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.LAMP_COLOR.getFieldName(),
		        fcResp.getLampColor()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.REPORT_TYPE.getFieldName(),
		        fcResp.getReportType()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_ROOT_CAUSE_1.getFieldName(),
		        fcResp.getFaultRootCause1()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_LIKE_1.getFieldName(),
		        fcResp.getFaultLikelihood1()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_ROOT_CAUSE_2.getFieldName(),
		        fcResp.getFaultRootCause2()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_LIKE_2.getFieldName(),
		        fcResp.getFaultLikelihood2()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_ROOT_CAUSE_3.getFieldName(),
		        fcResp.getFaultRootCause3()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_LIKE_3.getFieldName(),
		        fcResp.getFaultLikelihood3()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_ROOT_CAUSE_4.getFieldName(),
		        fcResp.getFaultRootCause4()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_LIKE_4.getFieldName(),
		        fcResp.getFaultLikelihood4()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.SHUTDOWN_FLAG.getFieldName(),
		        fcResp.getShutdownFlag()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.SHUTDOWN_DESC.getFieldName(),
		        fcResp.getShutdownDescription()));
		fieldValues
		        .add(createKeyValueObj(
		                FCResponseFields.PRIORITY.getFieldName(),
		                fcResp.getPriority()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.FAULT_CODE_CAT.getFieldName(),
		        fcResp.getFaultCodeCategory()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.ADD_DIAGC_INFO.getFieldName(),
		        fcResp.getAdditionalDiagnosticInfo()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.PRIMARY_FAULT_CODE.getFieldName(),
		        fcResp.getPrimaryFaultCode()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.PRIMARY_SPN.getFieldName(),
		        fcResp.getPrimarySPN()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.PRIMARY_FMI.getFieldName(),
		        fcResp.getPrimaryFMI()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.PRIMARY_OCC_DATETIME.getFieldName(),
		        fcResp.getPrimaryOccurrenceDateTime()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.PRIMARY_FAULT_CODE_DES.getFieldName(),
		        fcResp.getPrimaryFaultCodeDescription()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.SRV_LOCATOR_LINK.getFieldName(),
		        fcResp.getServiceLocatorLink()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.SRV_MODEL_NAME.getFieldName(),
		        fcResp.getServiceModelName()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.GEN_ASSIST_PHONE.getFieldName(),
		        fcResp.getGeneralAssistancePhoneNumber()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.URL_CUMMINS_GEN.getFieldName(),
		        fcResp.getURLCumminsGeneral()));
		fieldValues.add(createKeyValueObj(FCResponseFields.URL2.getFieldName(),
		        fcResp.getUrl2()));
		fieldValues.add(createKeyValueObj(FCResponseFields.URL3.getFieldName(),
		        fcResp.getUrl3()));
		fieldValues.add(createKeyValueObj(FCResponseFields.URL4.getFieldName(),
		        fcResp.getUrl4()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.CUMMINS_NAME.getFieldName(),
		        fcResp.getCumminsName()));
		fieldValues.add(createKeyValueObj(FCResponseFields.OEM.getFieldName(),
		        fcResp.getOem()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.SRV_LOCATOR_MARKET_APP.getFieldName(),
		        fcResp.getServiceLocatorMarketApplication()));
		fieldValues.add(createKeyValueObj(
		        FCResponseFields.EQUIP_SUB_APP.getFieldName(),
		        fcResp.getEquipmentSubApplication()));

		markerObject.setFieldValues(fieldValues);

		EntityTemplateObj entityTemplate = new EntityTemplateObj();
		entityTemplate.setEntityTemplateName(faultRespTemp);
		markerObject.setEntityTemplate(entityTemplate);

		DomainObj domain = new DomainObj();
		domain.setDomainName(domainName);
		markerObject.setDomain(domain);

		return markerObject;
	}

	private HierarchyDTO createAttachResponsePayload(
	        String faultDataRowIdentifier, String responseRowIdentifier) {

		HierarchyDTO hierarchy = new HierarchyDTO();
		EntityDTO actor = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		actor.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(faultEvtTemp);
		actor.setEntityTemplate(entityTemp);

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		actor.setPlatformEntity(platformEntity);

		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(IDENTIFIER);
		identifier.setValue(faultDataRowIdentifier);
		actor.setIdentifier(identifier);

		hierarchy.setActor(actor);

		List<EntityDTO> subjects = new ArrayList<EntityDTO>();
		EntityDTO sub = new EntityDTO();

		DomainDTO domainSub = new DomainDTO();
		domainSub.setDomainName(domainName);
		sub.setDomain(domainSub);

		EntityTemplateDTO entityTempSub = new EntityTemplateDTO();
		entityTempSub.setEntityTemplateName(faultRespTemp);
		sub.setEntityTemplate(entityTempSub);

		PlatformEntityDTO platformEntitySub = new PlatformEntityDTO();
		platformEntitySub.setPlatformEntityType(MARKER);
		sub.setPlatformEntity(platformEntitySub);

		FieldMapDTO identifierSub = new FieldMapDTO();
		identifierSub.setKey(IDENTIFIER);
		identifierSub.setValue(responseRowIdentifier);
		sub.setIdentifier(identifierSub);

		subjects.add(sub);
		hierarchy.setSubjects(subjects);

		return hierarchy;
	}
	
	private HierarchySearchDTO getAttachedResponsePayload(String faultEventIdentifier) {
		HierarchySearchDTO hs = new HierarchySearchDTO();

		IdentityDTO parentIdentity = new IdentityDTO();

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		parentIdentity.setPlatformEntity(platformEntity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		parentIdentity.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(faultEvtTemp);
		parentIdentity.setEntityTemplate(entityTemp);

		FieldMapDTO identifier = new FieldMapDTO(IDENTIFIER, faultEventIdentifier);
		parentIdentity.setIdentifier(identifier);
		hs.setParentIdentity(parentIdentity);

		hs.setSearchTemplateName(responseTemplate);
		hs.setSearchEntityType(MARKER);

		return hs;
	}

	private FaultEventInfoReq createFaultEvtReq(FaultResponse faultResponse) {
		FaultEventInfoReq eventInfoReq = new FaultEventInfoReq();
		List<FieldMapDTO> fieldValues = new ArrayList<FieldMapDTO>();
		fieldValues.add(createKeyValueObj(SOURCE_ID.getFieldName(),
		        faultResponse.getTelematicsBoxId()));
		fieldValues.add(createKeyValueObj(ASSET_NAME.getFieldName(),
		        faultResponse.getEngineSerialNumber()));
		fieldValues.add(createKeyValueObj(SPN.getFieldName(),
		        faultResponse.getSPN()));
		fieldValues.add(createKeyValueObj(FMI.getFieldName(),
		        faultResponse.getFMI()));
		fieldValues.add(createKeyValueObj(OC.getFieldName(),
		        String.valueOf(faultResponse.getOccurrenceCount())));
		fieldValues
		        .add(createKeyValueObj(
		                EVENT_STATUS.getFieldName(),
		                EventStatus.valueOfType(
		                        Integer.parseInt(faultResponse.getActive()))
		                        .toString()));
		fieldValues.add(createKeyValueObj(RESP_RECEIVE_STATUS.getFieldName(),
		        RespReceiveStatus.NOT_RECEIVED.toString()));
		eventInfoReq.setKeyValues(fieldValues);
		return eventInfoReq;
	}

	private FieldMapDTO createKeyValueObj(String key, String value) {
		return new FieldMapDTO(key, value);
	}

}
