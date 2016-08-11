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

import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED;
import static com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.ccd.constants.Constants.IDENTIFIER;
import static com.pcs.ccd.constants.Constants.MARKER;
import static com.pcs.ccd.enums.ContactFields.CONTACT;
import static com.pcs.ccd.enums.ContactFields.CONTACT_ID;
import static com.pcs.ccd.enums.ContactFields.CONTACT_NUMBER;
import static com.pcs.ccd.enums.ContactFields.EMAIL;
import static com.pcs.ccd.enums.ContactFields.NAME;
import static com.pcs.ccd.enums.EquipmentFields.ASSET_ID;
import static com.pcs.ccd.enums.EquipmentFields.ASSET_NAME;
import static com.pcs.ccd.enums.EquipmentFields.ASSET_SERIAL_NO;
import static com.pcs.ccd.enums.EquipmentFields.ASSET_TYPE;
import static com.pcs.ccd.enums.EquipmentFields.ENGINE_MAKE;
import static com.pcs.ccd.enums.EquipmentFields.ENGINE_MODEL;
import static com.pcs.ccd.enums.EquipmentFields.EQUIPMENTS;
import static com.pcs.ccd.enums.EquipmentFields.EQUIPMENT_ID;
import static com.pcs.ccd.enums.EquipmentFields.EQUIPMENT_IDS;
import static com.pcs.ccd.enums.TenantFields.TENANT;
import static com.pcs.ccd.enums.TenantFields.TENANT_CONTACT_FNAME;
import static com.pcs.ccd.enums.TenantFields.TENANT_CONTACT_LNAME;
import static com.pcs.ccd.enums.TenantFields.TENANT_DOMAIN_NAME;
import static com.pcs.ccd.enums.TenantFields.TENANT_ID;
import static com.pcs.ccd.enums.TenantFields.TENANT_NAME;

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
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.service.SubscriptionProfileService;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.ccd.bean.AttachEquipment;
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.DomainObj;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.EquipmentPersist;
import com.pcs.ccd.bean.HeirarchyObj;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.bean.TenantEquipment;
import com.pcs.ccd.enums.Integraters;
import com.pcs.ccd.token.TokenProvider;

/**
 * This class is responsible for implementing all services related to equipment
 * 
 * @author pcseg129(Seena Jyothish) Feb 7, 2016
 */

@Service
public class EquipmentServiceImpl implements EquipmentService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EquipmentServiceImpl.class);

	@Autowired
	@Qualifier("ccdEsbClient")
	private BaseClient ccdEsbClient;
	
	@Autowired
	@Qualifier("apiMgrClient")
	private BaseClient apiMgrClient;

	@Autowired
	private ContactsService contactsService;
	
	@Autowired
	private SubscriptionProfileService subProfileService;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Value("${ccd.default.tenant.template}")
	private String defTenantTemp;

	@Value("${ccd.tenant.template}")
	private String tenantTemp;

	@Value("${ccd.equipment.template}")
	private String equipmentTemp;

	@Value("${ccd.contacts.template}")
	private String contactsTemp;
	
	@Value("${ccd.parent.equip.template}")
	private String parentEquipTemp;

	@Value("${parent.domain.name}")
	private String parentDomainName;
	
	@Value("${alpine.domain.name}")
	private String alpineDomainName;

	@Value("${tenant.name}")
	private String tenantName;

	@Value("${domain.name}")
	private String domainName;

	@Value("${ccd.esb.create.equipment}")
	private String createEquipmentEP;

	@Value("${api.mgr.marker.find}")
	private String findMarkerEP;
	
	@Value("${api.mgr.marker.search}")
	private String searchdMarkerEP;
	
	@Value("${api.mgr.marker.list.filter}")
	private String listFilterMarkerEP;

	@Value("${api.mgr.hierarchy.attach}")
	private String heirarchyAttachEP;
	
	@Value("${api.mgr.hierarchy.children}")
	private String heirarchyChildrenEP;
	
	@Value("${api.mgr.hierarchy.children.immediate}")
	private String heirarchyImmChildrenEP;
	
	@Autowired
	private TenantService tenantService; 

	@Override
    public StatusMessageDTO persistEquipment(TenantEquipment tenantEquip) {
		validateMandatoryFields(tenantEquip, TENANT);
		validateMandatoryFields(tenantEquip.getTenant(), TENANT_NAME,TENANT_ID,TENANT_CONTACT_FNAME,TENANT_CONTACT_LNAME);
		validateCollection(EQUIPMENTS, tenantEquip.getEquipments());
		for(Equipment equipment : tenantEquip.getEquipments()){
			validateMandatoryFields(equipment, ASSET_NAME);
		}
		
		EquipmentPersist equipPersist = new EquipmentPersist();
		equipPersist.setEquipments(tenantEquip.getEquipments());
		equipPersist.setTenant(tenantEquip.getTenant());
		
		equipPersist.setDefaultTenantTemplate(defTenantTemp);
		equipPersist.setTenantMarkerTemplate(tenantTemp);
		equipPersist.setEquipMarkerTemplate(equipmentTemp);
		
		HeirarchyObj heirarchyObj = new HeirarchyObj();
		heirarchyObj.setDomainName(alpineDomainName);
		heirarchyObj.setTenantName(tenantName);
		equipPersist.setParent(heirarchyObj);
		
		DomainObj myDomain = new DomainObj();
		myDomain.setDomainName(domainName);
		equipPersist.setMydomain(myDomain);
		
		StatusMessageDTO status = ccdEsbClient.post(createEquipmentEP,
				subProfileService.getSubscription().getJwtToken(),
		        equipPersist, StatusMessageDTO.class);
		return status;
    }

	@Override
	public boolean isEquipmentExists(String equipId) {
		if (StringUtils.isNotEmpty(equipId)) {
			return checkIsEquipmentExist(equipId);
		} else {
			throw new GalaxyException(FIELD_DATA_NOT_SPECIFIED,
					EQUIPMENT_ID.getDescription());
		}
	}

	@Override
    public List<Equipment> getAllEquipments(Tenant tenant) {
		validateMandatoryFields(tenant, TENANT_ID);
		Tenant tenantBySearch = tenantService.getTenant(tenant.getTenantId());
		if(tenantBySearch !=null){
			return findAllEquipments(tenantBySearch.getReadOnlyRowIdentifier());
		} else {
			throw new GalaxyException(INVALID_DATA_SPECIFIED,TENANT.getDescription() + " " + tenant.getTenantId());
		}
    }
	
	@Override
    public List<Equipment> getEquipmentsOfAContact(String contactId) {
		validateMandatoryField(contactId, CONTACT_ID.getDescription());
		if(contactsService.isContactExists(contactId)){
			return getAttachedEquipments(contactId);
		}else{
			throw new GalaxyException(INVALID_DATA_SPECIFIED,CONTACT.getDescription());
		}
    }
	
	@Override
    public StatusMessageDTO attachEquipments(AttachEquipment attachEquipment) {
		validateMandatoryFields(attachEquipment, CONTACT_ID);
		validateMandatoryFields(attachEquipment, TENANT_ID);
		validateCollection(EQUIPMENT_IDS, attachEquipment.getEquipmentIds());
		for(String equipmentId:attachEquipment.getEquipmentIds()){
			validateMandatoryField(EQUIPMENT_ID, equipmentId);	
		}
		if (!contactsService.isContactExists(attachEquipment.getContactId())) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED,
					CONTACT.getDescription()+ " " +attachEquipment.getContactId());
		}
		checkIsValidEquipments(attachEquipment.getEquipmentIds());
		return attachEquipmentToContact(attachEquipment);
    }
	
	@Override
	public Equipment getEquipmentBySearch(String equipId) {
		List<ReturnFieldsDTO> equipEntities = null;
		try {
			equipEntities = apiMgrClient.post(searchdMarkerEP,
			        tokenProvider.getAuthToken(Integraters.CCD),
			        createEquipmentSearchPayload(equipId),List.class,ReturnFieldsDTO.class);
			return convertToEquipment(equipEntities.get(0).getDataprovider(),equipEntities.get(0).getIdentifier());
		} catch (Exception e) {
			LOGGER.error("Error returned from the remote service, could be requested data not found");
		}
		return null;
	}
	
	@Override
    public List<Equipment> getAllTenantEquipments(String tenantDomainName) {
		validateMandatoryField(tenantDomainName, TENANT_DOMAIN_NAME.getDescription());
		List<EntityDTO> equipEntities = null;
		try {
			equipEntities = apiMgrClient.post(listFilterMarkerEP,
					tokenProvider.getAuthToken(Integraters.GALAXY),
					tenantAllEquipSearchPayload(tenantDomainName),List.class,EntityDTO.class);
			return convertToEquipment(equipEntities);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error returned from the remote service, could be requested data not found");
		}
		return null;
    }
	
	private void checkIsValidEquipments(List<String> equipmentIds) {
		for (String equipmentId : equipmentIds) {
			if(StringUtils.isNotBlank(equipmentId)){
				if (!isEquipmentExists(equipmentId)) {
					throw new GalaxyException(INVALID_DATA_SPECIFIED,EQUIPMENT_ID.getDescription()+" "+
					        equipmentId);
				}
			} else {
				throw new GalaxyException(INVALID_DATA_SPECIFIED,EQUIPMENT_ID.getDescription());
			}
		}
	}
	
	private List<Equipment> getAttachedEquipments(String contactId) {
		List<EntityDTO> contacts = null;
		contacts = apiMgrClient.post(heirarchyChildrenEP,
				tokenProvider.getAuthToken(Integraters.CCD),
		        getAttachedEquipmentsPayload(contactId), List.class,
		        EntityDTO.class);
		return convertToEquipment(contacts);
	}
	
	private HierarchySearchDTO getAttachedEquipmentsPayload(String contactId) {
		HierarchySearchDTO hs = new HierarchySearchDTO();

		IdentityDTO parentIdentity = new IdentityDTO();

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		parentIdentity.setPlatformEntity(platformEntity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		parentIdentity.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(contactsTemp);
		parentIdentity.setEntityTemplate(entityTemp);

		FieldMapDTO identifier = new FieldMapDTO(IDENTIFIER, contactId);
		parentIdentity.setIdentifier(identifier);
		hs.setParentIdentity(parentIdentity);

		hs.setSearchTemplateName(equipmentTemp);
		hs.setSearchEntityType(MARKER);

		return hs;
	}
	
	private StatusMessageDTO attachEquipmentToContact(
			AttachEquipment attachEquipment) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		for (String equipId : attachEquipment.getEquipmentIds()) {

			HierarchyDTO hierarchy = new HierarchyDTO();
			EntityDTO actor = new EntityDTO();

			DomainDTO domain = new DomainDTO();
			domain.setDomainName(domainName);
			actor.setDomain(domain);

			EntityTemplateDTO entityTemp = new EntityTemplateDTO();
			entityTemp.setEntityTemplateName(contactsTemp);
			actor.setEntityTemplate(entityTemp);

			PlatformEntityDTO platformEntity = new PlatformEntityDTO();
			platformEntity.setPlatformEntityType(MARKER);
			actor.setPlatformEntity(platformEntity);

			FieldMapDTO identifier = new FieldMapDTO();
			identifier.setKey(IDENTIFIER);
			identifier
			        .setValue(attachEquipment.getContactId());
			actor.setIdentifier(identifier);

			hierarchy.setActor(actor);

			List<EntityDTO> subjects = new ArrayList<EntityDTO>();
			EntityDTO sub = new EntityDTO();

			DomainDTO domainSub = new DomainDTO();
			domainSub.setDomainName(domainName);
			sub.setDomain(domainSub);

			EntityTemplateDTO entityTempSub = new EntityTemplateDTO();
			entityTempSub.setEntityTemplateName(equipmentTemp);
			sub.setEntityTemplate(entityTempSub);

			PlatformEntityDTO platformEntitySub = new PlatformEntityDTO();
			platformEntitySub.setPlatformEntityType(MARKER);
			sub.setPlatformEntity(platformEntitySub);

			FieldMapDTO identifierSub = new FieldMapDTO();
			identifierSub.setKey(IDENTIFIER);
			identifierSub.setValue(equipId);
			sub.setIdentifier(identifierSub);

			subjects.add(sub);
			hierarchy.setSubjects(subjects);

			try {
				status = apiMgrClient.post(heirarchyAttachEP,
				        tokenProvider.getAuthToken(Integraters.CCD),
				        hierarchy, StatusMessageDTO.class);
			} catch (GalaxyException e) {
				if (status == null || status.getStatus().equals(Status.FAILURE)) {
					LOGGER.error(
					        "Error in attach equipment to contact for equip {} for contact {}",
					        equipId,attachEquipment.getContactId());
					throw new GalaxyException(e);
				}
				status.setStatus(Status.SUCCESS);
			}
		}
		return status;
	}

	private List<Contact> getAttachedContcts(String equipId) {
		List<EntityDTO> contacts = null;
		contacts = apiMgrClient.post(heirarchyChildrenEP,
				tokenProvider.getAuthToken(Integraters.CCD),
		        getEquipContactsPayload(equipId), List.class,
		        EntityDTO.class);
		return convertToContact(contacts);
		//return contacts;
	}

	private boolean checkIsEquipmentExist(String equipId) {
		boolean isExist = false;
		try {
			EntityDTO equipEntity = apiMgrClient.post(findMarkerEP,
			        tokenProvider.getAuthToken(Integraters.CCD),
			        equipSearchPayload(equipId), EntityDTO.class);
			isExist = true;
		} catch (Exception e) {
			LOGGER.error("Error returned from the remote service, could be requested data not found");
		}
		return isExist;
	}
	
	private HierarchySearchDTO getEquipContactsPayload(String equipId) {
		HierarchySearchDTO hs = new HierarchySearchDTO();

		IdentityDTO parentIdentity = new IdentityDTO();

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		parentIdentity.setPlatformEntity(platformEntity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		parentIdentity.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(equipmentTemp);
		parentIdentity.setEntityTemplate(entityTemp);

		FieldMapDTO identifier = new FieldMapDTO(IDENTIFIER, equipId);
		parentIdentity.setIdentifier(identifier);
		hs.setParentIdentity(parentIdentity);

		hs.setSearchTemplateName(contactsTemp);
		hs.setSearchEntityType(MARKER);

		return hs;
	}
	
	private HierarchySearchDTO getAllEquipPayload(String tenantRowId) {
		HierarchySearchDTO hs = new HierarchySearchDTO();

		IdentityDTO actor = new IdentityDTO();
		PlatformEntityDTO platformEntityDTO = new PlatformEntityDTO();
		platformEntityDTO.setPlatformEntityType(MARKER);
		actor.setPlatformEntity(platformEntityDTO);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		actor.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(tenantTemp);
		actor.setEntityTemplate(entityTemp);

		FieldMapDTO identifier = new FieldMapDTO(IDENTIFIER, tenantRowId);
		actor.setIdentifier(identifier);
		hs.setActor(actor);

		hs.setSearchTemplateName(equipmentTemp);
		hs.setSearchEntityType(MARKER);

		return hs;
	}

	private List<Contact> convertToContact(List<EntityDTO> entityDTOs){
		List<Contact> contacts = new ArrayList<Contact>();
		for(EntityDTO entityDTO : entityDTOs){
			Contact contact = new Contact();
			contact.setName(fetchFieldValue(entityDTO.getDataprovider(),NAME.getFieldName()));
			contact.setEmail(fetchFieldValue(entityDTO.getDataprovider(),EMAIL.getFieldName()));
			contact.setContactNumber(fetchFieldValue(entityDTO.getDataprovider(),CONTACT_NUMBER.getFieldName()));
			contact.setRowIdentifier(entityDTO.getIdentifier().getValue());
			contacts.add(contact);
		}
		return contacts;
	}
	
	private List<Equipment> convertToEquipment(List<EntityDTO> entityDTOs){
		List<Equipment> equipments = new ArrayList<Equipment>();
		for(EntityDTO entityDTO : entityDTOs){
			Equipment equipment = convertToEquipment(entityDTO.getDataprovider(),entityDTO.getIdentifier());
			equipments.add(equipment);
		}
		return equipments;
	}
	
	private Equipment convertToEquipment(List<FieldMapDTO> dataProvider,FieldMapDTO identifier){
		Equipment equipment = new Equipment();
		equipment.setAssetName(fetchFieldValue(dataProvider,ASSET_NAME.getFieldName()));
		equipment.setAssetType(fetchFieldValue(dataProvider,ASSET_TYPE.getFieldName()));
		equipment.setEngineMake(fetchFieldValue(dataProvider,ENGINE_MAKE.getFieldName()));
		equipment.setEngineModel(fetchFieldValue(dataProvider,ENGINE_MODEL.getFieldName()));
		equipment.setAssetId(fetchFieldValue(dataProvider,ASSET_ID.getFieldName()));
		equipment.setSerialNumber(fetchFieldValue(dataProvider,ASSET_SERIAL_NO.getFieldName()));
		equipment.setRowIdentifier(identifier.getValue());
		return equipment;
	}
	
	private SearchFieldsDTO createEquipmentSearchPayload(String srcId){
		SearchFieldsDTO search = new SearchFieldsDTO();
		
		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO searchField = new FieldMapDTO();
		searchField.setKey(ASSET_NAME.getFieldName());
		searchField.setValue(srcId);
		searchFields.add(searchField);
		search.setSearchFields(searchFields);
		
		EntityTemplateDTO entity = new EntityTemplateDTO();
		entity.setEntityTemplateName(equipmentTemp);
		search.setEntityTemplate(entity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);
		
		return search;
	}
	
	private IdentityDTO equipSearchPayload(String equipRowId){
		
		IdentityDTO search = new IdentityDTO();

		FieldMapDTO identifier = new FieldMapDTO(IDENTIFIER,equipRowId);
		search.setIdentifier(identifier);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(equipmentTemp);
		search.setEntityTemplate(entityTemp);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);

		return search;
	}
	
	private IdentityDTO tenantAllEquipSearchPayload(String tenantDomainName){
		IdentityDTO search = new IdentityDTO();
		
		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(parentEquipTemp);
		search.setEntityTemplate(entityTemp);
		
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(tenantDomainName);
		search.setDomain(domain);
		
		return search;
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

	private List<Equipment> findAllEquipments(String tenantRowId) {
		List<EntityDTO> equipEntities = null;
		equipEntities = apiMgrClient.post(heirarchyImmChildrenEP,
				tokenProvider.getAuthToken(Integraters.CCD),
		        getAllEquipPayload(tenantRowId), List.class,
		        EntityDTO.class);
		return convertToEquipment(equipEntities);
	}
	
}
