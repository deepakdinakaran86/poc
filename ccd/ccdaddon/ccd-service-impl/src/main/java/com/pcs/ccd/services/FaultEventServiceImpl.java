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
import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.PERSISTENCE_EXCEPTION;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.ccd.constants.Constants.IDENTIFIER;
import static com.pcs.ccd.constants.Constants.MARKER;
import static com.pcs.ccd.enums.EquipmentFields.ENGINE_MODEL;
import static com.pcs.ccd.enums.EquipmentFields.EQUIPMENT_ID;
import static com.pcs.ccd.enums.FaultDataFields.ACTIVE;
import static com.pcs.ccd.enums.FaultDataFields.ALTITUDE;
import static com.pcs.ccd.enums.FaultDataFields.ASSET_NAME;
import static com.pcs.ccd.enums.FaultDataFields.DATALINK_BUS;
import static com.pcs.ccd.enums.FaultDataFields.DEVICE_ID;
import static com.pcs.ccd.enums.FaultDataFields.DIRECTION;
import static com.pcs.ccd.enums.FaultDataFields.ENTITY_STATUS;
import static com.pcs.ccd.enums.FaultDataFields.ESN;
import static com.pcs.ccd.enums.FaultDataFields.EVENT_SEND_STATUS;
import static com.pcs.ccd.enums.FaultDataFields.EVENT_STATUS;
import static com.pcs.ccd.enums.FaultDataFields.EVENT_TIMESTAMP;
import static com.pcs.ccd.enums.FaultDataFields.FMI;
import static com.pcs.ccd.enums.FaultDataFields.LATITUDE;
import static com.pcs.ccd.enums.FaultDataFields.LONGITUDE;
import static com.pcs.ccd.enums.FaultDataFields.MAKE;
import static com.pcs.ccd.enums.FaultDataFields.MESSAGE_TYPE;
import static com.pcs.ccd.enums.FaultDataFields.MODEL;
import static com.pcs.ccd.enums.FaultDataFields.NOTIFICATION_VERSION;
import static com.pcs.ccd.enums.FaultDataFields.OC;
import static com.pcs.ccd.enums.FaultDataFields.OCC_DATETIME;
import static com.pcs.ccd.enums.FaultDataFields.OC_CYCLE;
import static com.pcs.ccd.enums.FaultDataFields.RAW_IDENTIFIER;
import static com.pcs.ccd.enums.FaultDataFields.RESP_RECEIVE_STATUS;
import static com.pcs.ccd.enums.FaultDataFields.SNAPSHOTS;
import static com.pcs.ccd.enums.FaultDataFields.SNAPSHOT_DATETIME;
import static com.pcs.ccd.enums.FaultDataFields.SNAPSHOT_PARAMETERS;
import static com.pcs.ccd.enums.FaultDataFields.SNAPSHOT_PARAMETER_NAME;
import static com.pcs.ccd.enums.FaultDataFields.SNAPSHOT_PARAMETER_VALUE;
import static com.pcs.ccd.enums.FaultDataFields.SOURCE_ADDRESS;
import static com.pcs.ccd.enums.FaultDataFields.SOURCE_ID;
import static com.pcs.ccd.enums.FaultDataFields.SPEED;
import static com.pcs.ccd.enums.FaultDataFields.SPN;
import static com.pcs.ccd.enums.FaultDataFields.UNIT_NUMBER;
import static com.pcs.ccd.enums.FaultDataFields.VIN;
import static com.pcs.ccd.enums.FaultInfoReqFields.KEY_VALUES;
import static com.pcs.ccd.enums.TenantFields.TENANT;
import static com.pcs.ccd.enums.TenantFields.TENANT_ID;

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
import com.pcs.avocado.commons.dto.ReturnFieldsDTO;
import com.pcs.avocado.commons.dto.SearchFieldsDTO;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.ccd.bean.DomainObj;
import com.pcs.ccd.bean.EntityTemplateObj;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.FaultData;
import com.pcs.ccd.bean.FaultDataWrapper;
import com.pcs.ccd.bean.FaultEventData;
import com.pcs.ccd.bean.FaultEventInfoReq;
import com.pcs.ccd.bean.FaultParameter;
import com.pcs.ccd.bean.FaultSnapshot;
import com.pcs.ccd.bean.MarkerObject;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.enums.EventSendStatus;
import com.pcs.ccd.enums.EventStatus;
import com.pcs.ccd.enums.Integraters;
import com.pcs.ccd.enums.RespReceiveStatus;
import com.pcs.ccd.token.TokenProvider;

/**
 * This class is responsible for implementing all services related to fault
 * event
 * 
 * @author pcseg129(Seena Jyothish) Jan 31, 2016
 */
@Service
public class FaultEventServiceImpl implements FaultEventService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(FaultEventServiceImpl.class);

	@Autowired
	@Qualifier("apiMgrClient")
	private BaseClient apiMgrClient;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Value("${ccd.fault.event.template}")
	private String faultEvtTemp;

	@Value("${domain.name}")
	private String domainName;

	@Value("${ccd.tenant.template}")
	private String tenantTemp;

	@Value("${ccd.equipment.template}")
	private String equipmentTemp;
	
	@Value("${api.mgr.marker.find}")
	private String findMarkerEP;
	
	@Value("${api.mgr.marker}")
	private String markerPlatformEndpoint;

	@Value("${api.mgr.marker.search}")
	private String searchMarkerEP;

	@Value("${api.mgr.hierarchy.attach}")
	private String heirarchyAttachEP;

	@Value("${api.mgr.hierarchy.children}")
	private String heirarchyChildrenEP;

	@Override
	public FaultData getLatestFaultEventIfExists(
	        FaultEventInfoReq eventInfoReq) {
		SearchFieldsDTO search = new SearchFieldsDTO();
		search.setSearchFields(eventInfoReq.getKeyValues());
		search.setStatus(eventInfoReq.getEntityStatus());
		EntityTemplateDTO entity = new EntityTemplateDTO();
		entity.setEntityTemplateName(faultEvtTemp);
		search.setEntityTemplate(entity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);

		ReturnFieldsDTO[] searchResult = apiMgrClient.post(searchMarkerEP,
		        tokenProvider.getAuthToken(Integraters.CCD), search,
		        ReturnFieldsDTO[].class);
		return convertToFaultData1(searchResult[0]);
	}

	@Override
	public FaultData getFaultEvent(FaultEventInfoReq eventInfoReq) {
		SearchFieldsDTO search = new SearchFieldsDTO();
		search.setSearchFields(eventInfoReq.getKeyValues());
		search.setStatus(eventInfoReq.getEntityStatus());
		EntityTemplateDTO entity = new EntityTemplateDTO();
		entity.setEntityTemplateName(faultEvtTemp);
		search.setEntityTemplate(entity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);

		try {
			ReturnFieldsDTO[] searchResult = apiMgrClient.post(
			        searchMarkerEP,tokenProvider.getAuthToken(Integraters.CCD),
			        search, ReturnFieldsDTO[].class);
			return convertToFaultData1(searchResult[0]);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public StatusMessageDTO updateFaultEvent(FaultData faultData) {
		StatusMessageDTO status = apiMgrClient.put(markerPlatformEndpoint,
		        tokenProvider.getAuthToken(Integraters.CCD),
		        createUpdateFaultEventObj(faultData), StatusMessageDTO.class);
		status.setStatus(Status.SUCCESS);
		return status;
	}

	@Override
	public List<FaultEventData> findAllFaultEvent(FaultEventInfoReq faultInfoReq) {
		validateCollection(KEY_VALUES, faultInfoReq.getKeyValues());
		SearchFieldsDTO search = new SearchFieldsDTO();
		search.setSearchFields(faultInfoReq.getKeyValues());
		EntityTemplateDTO entity = new EntityTemplateDTO();
		entity.setEntityTemplateName(faultEvtTemp);
		search.setEntityTemplate(entity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);
		ReturnFieldsDTO[] searchResult = apiMgrClient.post(searchMarkerEP,
		        tokenProvider.getAuthToken(Integraters.CCD), search,
		        ReturnFieldsDTO[].class);
		return convertToFaultEventData(searchResult);
	}
	
	@Override
    public boolean isFaultEventExists(String faultEventIdentifier) {
		validateMandatoryField(RAW_IDENTIFIER.getDescription(), faultEventIdentifier);
			return checkFaultEventExists(faultEventIdentifier);
    }
	
	private boolean checkFaultEventExists(String faultEventIdentifier) {
		boolean isExist = false;
		try {
			EntityDTO faultEventEntity = apiMgrClient.post(findMarkerEP,
			        tokenProvider.getAuthToken(Integraters.CCD),
			        createFaultEventFindPayload(faultEventIdentifier), EntityDTO.class);
			isExist = true;
		} catch (Exception e) {
			LOGGER.error("Error returned from the remote service, could be requested data not found",e);
		}
		return isExist;
	}
	
	private IdentityDTO createFaultEventFindPayload(String faultEventIdentifier) {
		IdentityDTO search = new IdentityDTO();

		FieldMapDTO identifier = new FieldMapDTO(RAW_IDENTIFIER
		        .getFieldName(), faultEventIdentifier);
		search.setIdentifier(identifier);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(faultEvtTemp);
		search.setEntityTemplate(entityTemp);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);

		return search;
	}
	
	private MarkerObject createUpdateFaultEventObj(FaultData faultData) {
		MarkerObject markerObject = new MarkerObject();
		List<FieldMapDTO> fieldValues = createFaulEventKeyValues(faultData);
		markerObject.setFieldValues(fieldValues);

		if (faultData.getReadOnlyRawIdentifier() != null) {
			markerObject.setIdentifier(new FieldMapDTO(RAW_IDENTIFIER
			        .getFieldName(), faultData.getReadOnlyRawIdentifier()));
		}
		if (faultData.getEntityStatus() != null) {
			StatusDTO entityStats = new StatusDTO();
			entityStats.setStatusName(faultData.getEntityStatus().toString());
			markerObject.setEntityStatus(entityStats);
		}

		EntityTemplateObj entityTemplate = new EntityTemplateObj();
		entityTemplate.setEntityTemplateName(faultEvtTemp);
		markerObject.setEntityTemplate(entityTemplate);

		DomainObj domain = new DomainObj();
		domain.setDomainName(domainName);
		markerObject.setDomain(domain);

		return markerObject;
	}

	private List<FaultEventData> convertToFaultEventData(
	        List<EntityDTO> searchResults) {
		List<FaultEventData> faultEventDatas = new ArrayList<FaultEventData>();
		for (EntityDTO entityDTO : searchResults) {
			faultEventDatas.add(convertToFaultEventData(
			        entityDTO.getDataprovider(), entityDTO.getIdentifier()));
		}
		return faultEventDatas;
	}

	private List<FaultEventData> convertToFaultEventData(
	        ReturnFieldsDTO[] searchResults) {
		List<FaultEventData> eventDatas = new ArrayList<FaultEventData>();
		for (ReturnFieldsDTO returnField : searchResults) {
			eventDatas
			        .add(convertToFaultEventData(returnField.getDataprovider(),
			                returnField.getIdentifier()));
		}
		return eventDatas;
	}

	private FaultEventData convertToFaultEventData(
	        List<FieldMapDTO> fieldMapDTOs, FieldMapDTO identifier) {
		FaultEventData eventData = new FaultEventData();
		eventData.setSourceId(fetchFieldValue(fieldMapDTOs,
		        SOURCE_ID.getFieldName()));
		eventData.setAssetName(fetchFieldValue(fieldMapDTOs,
		        ASSET_NAME.getFieldName()));
		eventData.setFMI(fetchFieldValue(fieldMapDTOs, FMI.getFieldName()));
		eventData.setSPN(fetchFieldValue(fieldMapDTOs, SPN.getFieldName()));
		eventData.setOC(Short.parseShort(fetchFieldValue(fieldMapDTOs,
		        OC.getFieldName())));
		eventData.setEventStatus(EventStatus.valueOf(fetchFieldValue(
		        fieldMapDTOs, EVENT_STATUS.getFieldName())));
		eventData.setEventTimestamp(Long.parseLong(fetchFieldValue(
		        fieldMapDTOs, EVENT_TIMESTAMP.getFieldName())));
		eventData.setEntityStatus(fetchFieldValue(fieldMapDTOs,
		        ENTITY_STATUS.getFieldName()));
		eventData.setEventSendStatus(EventSendStatus.valueOf(fetchFieldValue(
		        fieldMapDTOs, EVENT_SEND_STATUS.getFieldName())));
		eventData.setRespReceiveStatus(RespReceiveStatus
		        .valueOf(fetchFieldValue(fieldMapDTOs,
		                RESP_RECEIVE_STATUS.getFieldName())));
		eventData.setOcCylcle(Integer.parseInt(fetchFieldValue(fieldMapDTOs,
		        OC_CYCLE.getFieldName())));
		eventData.setIdentifier(identifier.getValue());
		return eventData;
	}

	private FaultData convertToFaultData1(ReturnFieldsDTO returnField) {
		FaultData eventData = new FaultData();
		FaultDataWrapper faultDataInfo = new FaultDataWrapper();

		eventData.setAssetName(fetchFieldValue(returnField.getDataprovider(),
		        ASSET_NAME.getFieldName()));
		eventData.setFMI(fetchFieldValue(returnField.getDataprovider(),
		        FMI.getFieldName()));
		eventData.setSPN(fetchFieldValue(returnField.getDataprovider(),
		        SPN.getFieldName()));
		eventData.setOC(fetchFieldValue(returnField.getDataprovider(),
		        OC.getFieldName()));
		eventData.setOcCycle(Integer.valueOf(fetchFieldValue(
		        returnField.getDataprovider(), OC_CYCLE.getFieldName())));
		eventData
		        .setEventSendStatus(EventSendStatus.valueOf(fetchFieldValue(
		                returnField.getDataprovider(),
		                EVENT_SEND_STATUS.getFieldName())));
		eventData.setRespReceiveStatus(RespReceiveStatus
		        .valueOf(fetchFieldValue(returnField.getDataprovider(),
		                RESP_RECEIVE_STATUS.getFieldName())));
		eventData.setEntityStatus(Status.valueOf(returnField.getEntityStatus()
		        .getStatusName()));
		eventData.setReadOnlyRawIdentifier(returnField.getIdentifier()
		        .getValue());
		faultDataInfo.setDeviceId(fetchFieldValue(
		        returnField.getDataprovider(), SOURCE_ID.getFieldName()));
		faultDataInfo.setNotificationVersion(fetchFieldValue(
		        returnField.getDataprovider(),
		        NOTIFICATION_VERSION.getFieldName()));
		faultDataInfo.setMessageType(fetchFieldValue(
		        returnField.getDataprovider(), MESSAGE_TYPE.getFieldName()));
		faultDataInfo.setMake(fetchFieldValue(returnField.getDataprovider(),
		        MAKE.getFieldName()));
		faultDataInfo.setModel(fetchFieldValue(returnField.getDataprovider(),
		        ENGINE_MODEL.getFieldName()));
		faultDataInfo.setSerialNumber(fetchFieldValue(
		        returnField.getDataprovider(), ESN.getFieldName()));
		faultDataInfo.setUnitNumber(fetchFieldValue(
		        returnField.getDataprovider(), UNIT_NUMBER.getFieldName()));
		faultDataInfo.setVidNumber(fetchFieldValue(
		        returnField.getDataprovider(), VIN.getFieldName()));
		faultDataInfo.setOccuranceDateTime(fetchFieldValue(
		        returnField.getDataprovider(), OCC_DATETIME.getFieldName()));
		faultDataInfo.setDatalinkBus(fetchFieldValue(
		        returnField.getDataprovider(), DATALINK_BUS.getFieldName()));
		faultDataInfo.setSourceAddress(fetchFieldValue(
		        returnField.getDataprovider(), SOURCE_ADDRESS.getFieldName()));
		faultDataInfo.setLatitude(fetchFieldValue(
		        returnField.getDataprovider(), LATITUDE.getFieldName()));
		faultDataInfo.setLongitude(fetchFieldValue(
		        returnField.getDataprovider(), LONGITUDE.getFieldName()));
		faultDataInfo.setAltitude(fetchFieldValue(
		        returnField.getDataprovider(), ALTITUDE.getFieldName()));
		faultDataInfo.setDirection(fetchFieldValue(
		        returnField.getDataprovider(), DIRECTION.getFieldName()));
		faultDataInfo.setSpeed(fetchFieldValue(returnField.getDataprovider(),
		        SPEED.getFieldName()));
		faultDataInfo.setActive(fetchFieldValue(returnField.getDataprovider(),
		        EVENT_STATUS.getFieldName()));
		faultDataInfo.setOccuranceDateTime(fetchFieldValue(
		        returnField.getDataprovider(), EVENT_TIMESTAMP.getFieldName()));
		eventData.setFaultDataInfo(faultDataInfo);

		return eventData;
	}

	@Override
	public StatusMessageDTO persistFaultEvent(FaultData faultData) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		String faultRowIdentifier = saveFault(faultData);
		String equipmentRowIdentifier = getEquipmentRowIdentifier(faultData
		        .getAssetName());
		if (!StringUtils.isBlank(faultRowIdentifier)
		        && !StringUtils.isBlank(equipmentRowIdentifier)) {
			attachFaultToEquipment(equipmentRowIdentifier, faultRowIdentifier);
			status.setStatus(Status.SUCCESS);
		} else {
			LOGGER.error("Error in attach fault event to equipment, either faultRowIdentifier or equipmentRowIdentifier is blank");
		}
		return status;
	}

	@Override
	public List<FaultEventData> getAllFaultEvent(String tenantId) {
		validateMandatoryField(TENANT_ID, tenantId);
		Tenant tenantBySearch = tenantService.getTenant(tenantId);
		if (tenantBySearch != null) {
			return findAllFaultEvents(tenantBySearch.getReadOnlyRowIdentifier());
		} else {
			throw new GalaxyException(INVALID_DATA_SPECIFIED,
			        TENANT.getDescription() + " " + tenantId);
		}
	}

	private List<FaultEventData> findAllFaultEvents(String tenantRowId) {
		List<EntityDTO> faultEventEntities = null;
		faultEventEntities = apiMgrClient.post(heirarchyChildrenEP,
		        tokenProvider.getAuthToken(Integraters.CCD),
		        createFindAllFaultPayload(tenantRowId), List.class,
		        EntityDTO.class);
		return convertToFaultEventData(faultEventEntities);
	}

	private HierarchySearchDTO createFindAllFaultPayload(String tenantRowId) {
		HierarchySearchDTO hs = new HierarchySearchDTO();

		IdentityDTO parentIdentity = new IdentityDTO();
		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		parentIdentity.setPlatformEntity(platformEntity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		parentIdentity.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(tenantTemp);
		parentIdentity.setEntityTemplate(entityTemp);

		FieldMapDTO identifier = new FieldMapDTO(IDENTIFIER, tenantRowId);
		parentIdentity.setIdentifier(identifier);
		hs.setParentIdentity(parentIdentity);

		hs.setSearchTemplateName(faultEvtTemp);
		hs.setSearchEntityType(MARKER);

		return hs;
	}

	private String saveFault(FaultData faultData) {
		validateMandatoryFields(faultData, ASSET_NAME, SPN, FMI, OC);
		validateMandatoryFields(faultData.getFaultDataInfo(),
		        NOTIFICATION_VERSION, MESSAGE_TYPE, DEVICE_ID, VIN,
		        OCC_DATETIME, ACTIVE, DATALINK_BUS, SOURCE_ADDRESS, LATITUDE,
		        LONGITUDE, ALTITUDE, SPEED, SNAPSHOTS);
		for (FaultSnapshot snapshot : faultData.getFaultDataInfo()
		        .getSnapshots()) {
			validateMandatoryFields(snapshot, SNAPSHOT_DATETIME,
			        SNAPSHOT_PARAMETERS);
			for (FaultParameter param : snapshot.getParameters()) {
				validateMandatoryFields(param, SNAPSHOT_PARAMETER_NAME,
				        SNAPSHOT_PARAMETER_VALUE);
			}
		}

		List<FieldMapDTO> fields = createFaulEventKeyValues(faultData);
		MarkerObject markerObject = new MarkerObject();
		markerObject.setFieldValues(fields);

		EntityTemplateObj entityTemplate = new EntityTemplateObj();
		entityTemplate.setEntityTemplateName(faultEvtTemp);
		markerObject.setEntityTemplate(entityTemplate);

		DomainObj domain = new DomainObj();
		domain.setDomainName(domainName);
		markerObject.setDomain(domain);

		try {
			IdentityDTO identityDTO = apiMgrClient.post(
			        markerPlatformEndpoint,
			        tokenProvider.getAuthToken(Integraters.CCD),
			        markerObject, IdentityDTO.class);
			return getFaultRowIdentifier(identityDTO);
		} catch (Exception e) {
			throw new GalaxyException(PERSISTENCE_EXCEPTION,
			        EQUIPMENT_ID.getDescription() + " "
			                + faultData.getAssetName());
		}
	}

	private String getFaultRowIdentifier(IdentityDTO identityDTO) {
		return identityDTO.getIdentifier().getValue();
	}

	private String getEquipmentRowIdentifier(String equipmentId) {
		Equipment equipment = equipmentService
		        .getEquipmentBySearch(equipmentId);
		if (equipment == null)
			throw new GalaxyException(INVALID_DATA_SPECIFIED,
			        EQUIPMENT_ID.getDescription() + " " + equipmentId);
		return equipment.getRowIdentifier();
	}

	private boolean attachFaultToEquipment(String equipRowIdentifier,
	        String faultRowIdentifier) {
		StatusMessageDTO status = new StatusMessageDTO();
		try {
			status = apiMgrClient.post(
			        heirarchyAttachEP,
			        tokenProvider.getAuthToken(Integraters.CCD),
			        createAttachFaultPayload(equipRowIdentifier,
			                faultRowIdentifier), StatusMessageDTO.class);
		} catch (GalaxyException e) {
			if (status == null || status.getStatus().equals(Status.FAILURE)) {
				LOGGER.error(
				        "Error in attach fault event to equipment for equip {} ",
				        equipRowIdentifier);
				throw new GalaxyException(e);
			}
		}
		return true;
	}

	private HierarchyDTO createAttachFaultPayload(String equipRowIdentifier,
	        String faultRowIdentifier) {

		HierarchyDTO hierarchy = new HierarchyDTO();
		EntityDTO actor = new EntityDTO();

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		actor.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(equipmentTemp);
		actor.setEntityTemplate(entityTemp);

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		actor.setPlatformEntity(platformEntity);

		FieldMapDTO identifier = new FieldMapDTO();
		identifier.setKey(IDENTIFIER);
		identifier.setValue(equipRowIdentifier);
		actor.setIdentifier(identifier);

		hierarchy.setActor(actor);

		List<EntityDTO> subjects = new ArrayList<EntityDTO>();
		EntityDTO sub = new EntityDTO();

		DomainDTO domainSub = new DomainDTO();
		domainSub.setDomainName(domainName);
		sub.setDomain(domainSub);

		EntityTemplateDTO entityTempSub = new EntityTemplateDTO();
		entityTempSub.setEntityTemplateName(faultEvtTemp);
		sub.setEntityTemplate(entityTempSub);

		PlatformEntityDTO platformEntitySub = new PlatformEntityDTO();
		platformEntitySub.setPlatformEntityType(MARKER);
		sub.setPlatformEntity(platformEntitySub);

		FieldMapDTO identifierSub = new FieldMapDTO();
		identifierSub.setKey(IDENTIFIER);
		identifierSub.setValue(faultRowIdentifier);
		sub.setIdentifier(identifierSub);

		subjects.add(sub);
		hierarchy.setSubjects(subjects);

		return hierarchy;
	}
	
	private List<FieldMapDTO> createFaulEventKeyValues(FaultData faultData) {
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		fields.add(new FieldMapDTO(SOURCE_ID.getFieldName(), faultData
		        .getFaultDataInfo().getDeviceId()));
		fields.add(new FieldMapDTO(SPN.getFieldName(), faultData.getSPN()));
		fields.add(new FieldMapDTO(FMI.getFieldName(), faultData.getFMI()));
		fields.add(new FieldMapDTO(OC.getFieldName(), faultData.getOC()));
		fields.add(new FieldMapDTO(NOTIFICATION_VERSION.getFieldName(),
		        faultData.getFaultDataInfo().getNotificationVersion()));
		fields.add(new FieldMapDTO(MESSAGE_TYPE.getFieldName(), faultData
		        .getFaultDataInfo().getMessageType()));
		fields.add(new FieldMapDTO(MAKE.getFieldName(), faultData
		        .getFaultDataInfo().getMake()));
		fields.add(new FieldMapDTO(MODEL.getFieldName(), faultData
		        .getFaultDataInfo().getModel()));
		fields.add(new FieldMapDTO(ASSET_NAME.getFieldName(), faultData
		        .getAssetName()));
		fields.add(new FieldMapDTO(UNIT_NUMBER.getFieldName(), faultData
		        .getFaultDataInfo().getUnitNumber()));
		fields.add(new FieldMapDTO(VIN.getFieldName(), faultData
		        .getFaultDataInfo().getVidNumber()));
		fields.add(new FieldMapDTO(OCC_DATETIME.getFieldName(), faultData
		        .getFaultDataInfo().getOccuranceDateTime()));
		fields.add(new FieldMapDTO(DATALINK_BUS.getFieldName(), faultData
		        .getFaultDataInfo().getDatalinkBus()));
		fields.add(new FieldMapDTO(SOURCE_ADDRESS.getFieldName(), faultData
		        .getFaultDataInfo().getSourceAddress()));
		fields.add(new FieldMapDTO(LATITUDE.getFieldName(), faultData
		        .getFaultDataInfo().getLatitude()));
		fields.add(new FieldMapDTO(LONGITUDE.getFieldName(), faultData
		        .getFaultDataInfo().getLongitude()));
		fields.add(new FieldMapDTO(ALTITUDE.getFieldName(), faultData
		        .getFaultDataInfo().getAltitude()));
		fields.add(new FieldMapDTO(DIRECTION.getFieldName(), faultData
		        .getFaultDataInfo().getDirection()));
		fields.add(new FieldMapDTO(SPEED.getFieldName(), faultData
		        .getFaultDataInfo().getSpeed()));
		fields.add(new FieldMapDTO(OC_CYCLE.getFieldName(), String
		        .valueOf(faultData.getOcCycle())));
		fields.add(new FieldMapDTO(EVENT_STATUS.getFieldName(), faultData
		        .getFaultDataInfo().getActive()));
		fields.add(new FieldMapDTO(EVENT_TIMESTAMP.getFieldName(), faultData
		        .getFaultDataInfo().getOccuranceDateTime()));
		fields.add(new FieldMapDTO(EVENT_SEND_STATUS.getFieldName(), faultData
		        .getEventSendStatus().toString()));
		fields.add(new FieldMapDTO(RESP_RECEIVE_STATUS.getFieldName(),
		        faultData.getRespReceiveStatus().toString()));
		return fields;
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

}
