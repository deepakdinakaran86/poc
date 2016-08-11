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
import static com.pcs.ccd.enums.ContactFields.CONTACTS;
import static com.pcs.ccd.enums.ContactFields.CONTACTS_IDS;
import static com.pcs.ccd.enums.ContactFields.CONTACT_ID;
import static com.pcs.ccd.enums.ContactFields.CONTACT_NAME;
import static com.pcs.ccd.enums.ContactFields.CONTACT_NUMBER;
import static com.pcs.ccd.enums.ContactFields.CONTACT_TYPE;
import static com.pcs.ccd.enums.ContactFields.EMAIL;
import static com.pcs.ccd.enums.ContactFields.NAME;
import static com.pcs.ccd.enums.ContactFields.ROW_IDENTIFIER;
import static com.pcs.ccd.enums.EquipmentFields.ASSET_NAME;
import static com.pcs.ccd.enums.EquipmentFields.EQUIPMENT;
import static com.pcs.ccd.enums.EquipmentFields.EQUIPMENT_ID;
import static com.pcs.ccd.enums.TenantFields.TENANT;
import static com.pcs.ccd.enums.TenantFields.TENANT_CONTACT_FNAME;
import static com.pcs.ccd.enums.TenantFields.TENANT_CONTACT_LNAME;
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
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.commons.service.SubscriptionProfileService;
import com.pcs.avocado.enums.Status;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.avocado.rest.exception.GalaxyRestException;
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.ContactDTO;
import com.pcs.ccd.bean.DomainObj;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.EquipmentContacts;
import com.pcs.ccd.bean.HeirarchyObj;
import com.pcs.ccd.bean.SearchContact;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.bean.TenantContact;
import com.pcs.ccd.enums.ContactType;
import com.pcs.ccd.enums.Integraters;
import com.pcs.ccd.token.TokenProvider;

/**
 * This class is responsible for implementing all services related to contacts
 * 
 * @author pcseg129(Seena Jyothish) Jan 21, 2016
 */
@Service
public class ContactsServiceImpl implements ContactsService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(ContactsServiceImpl.class);
	
	@Autowired
	@Qualifier("ccdEsbClient")
	private BaseClient ccdEsbClient;
	
	@Autowired
	@Qualifier("apiMgrClient")
	private BaseClient apiMgrClient;
	
	@Autowired
	private SubscriptionProfileService subProfileService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Value("${ccd.default.tenant.template}")
	private String defTenantTemp;
	
	@Value("${ccd.tenant.template}")
	private String tenantTemp;
	
	@Value("${ccd.contacts.template}")
	private String contactsTemp;
	
	@Value("${ccd.equipment.template}")
	private String equipmentTemp;

	@Value("${parent.domain.name}")
	private String parentDomainName;
	
	@Value("${alpine.domain.name}")
	private String alpineDomainName;
	
	@Value("${domain.name}")
	private String domainName;

	@Value("${tenant.name}")
	private String tenantName;
	
	@Value("${ccd.user.password}")
	private String ccdPassword;
	
	@Value("${ccd.user.name}")
	private String ccdUserName;
	
	@Value("${galaxy.user.name}")
	private String gxyUserName;
	
	@Value("${galaxy.user.password}")
	private String gxyPassword;
	
	@Value("${apiMgrClientId}")
	private String apiMgrConsumerKey;
	
	@Value("${apiMgrClientSecret}")
	private String apiMgrClientSecret;
	
	@Value("${api.mgr.marker.search}")
	private String apisearchdMarkerEP;
	
	@Value("${api.mgr.hierarchy.children}")
	private String apiheirarchyChildrenEP;

	@Value("${api.mgr.marker.find}")
	private String findMarkerEP;
	
	@Value("${ccd.esb.create.contact}")
	private String createContactEP;
	
	@Value("${api.mgr.hierarchy.parents.immediate}")
	private String heirarchyParentsEP;
	
	@Value("${api.mgr.hierarchy.attach}")
	private String heirarchyAttachEP;
	
	public StatusMessageDTO createContacts(TenantContact tenantContact) {
		validateMandatoryFields(tenantContact.getTenant(), TENANT_NAME,TENANT_ID,
		        TENANT_CONTACT_FNAME, TENANT_CONTACT_LNAME);
		validateCollection(CONTACTS, tenantContact.getContacts());
		for(Contact contact : tenantContact.getContacts()){
			validateMandatoryFields(contact, NAME,
					EMAIL, CONTACT_NUMBER,CONTACT_TYPE);
		}
		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setTenant(tenantContact.getTenant());
		contactDTO.setContacts(tenantContact.getContacts());
		
		DomainObj domainObj = new DomainObj();
		domainObj.setDomainName(domainName);
		contactDTO.setMydomain(domainObj);
		
		contactDTO.setTenantMarkerTemplate(tenantTemp);
		contactDTO.setDefaultTenantTemplate(defTenantTemp);
		contactDTO.setContactMarkerTemplate(contactsTemp);
		
		HeirarchyObj heirarchyObj = new HeirarchyObj();
		heirarchyObj.setDomainName(alpineDomainName);
		heirarchyObj.setTenantName(tenantName);
		contactDTO.setParent(heirarchyObj);
		
		
		StatusMessageDTO status = ccdEsbClient.post(createContactEP,
				subProfileService.getSubscription().getJwtToken(),
		        contactDTO, StatusMessageDTO.class);
		return status;
	}
	
	@Override
    public List<Contact> getEquipmentContacts(Equipment equipment) {
		validateMandatoryFields(equipment, ASSET_NAME);
		Equipment equipmentBySearch = equipmentService.getEquipmentBySearch(equipment.getAssetName());
		if(equipmentBySearch!=null){
			return getAttachedContcts(equipmentBySearch.getRowIdentifier());
		}else {
			throw new GalaxyException(INVALID_DATA_SPECIFIED,EQUIPMENT.getDescription());
		}
    }

	@Override
	public boolean isContactExists(String contactId) {
		if (StringUtils.isNotEmpty(contactId)) {
			return checkContactExists(contactId);
		} else {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.FIELD_DATA_NOT_SPECIFIED,ROW_IDENTIFIER.getDescription());
		}
	}

	private boolean checkContactExists(String contactId) {
		boolean isExist = false;
		try {
			EntityDTO equipEntity = apiMgrClient.post(findMarkerEP,
			        tokenProvider.getAuthToken(Integraters.CCD),
			        createContactFindPayload(contactId), EntityDTO.class);
			isExist = true;
		}catch (GalaxyRestException ge) {
			if (ge.getMessage().equals(
			        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);

			} 
		} 
		catch (Exception e) {
			LOGGER.error("Error returned from the remote service, could be requested data not found",e);
		}
		return isExist;
	}

	private IdentityDTO createContactFindPayload(String contactId) {
		IdentityDTO search = new IdentityDTO();

		FieldMapDTO identifier = new FieldMapDTO("identifier", contactId);
		search.setIdentifier(identifier);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(contactsTemp);
		search.setEntityTemplate(entityTemp);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);

		return search;
	}

	@Override
    public List<Contact> getAllContacts(Tenant tenant) {
		validateMandatoryFields(tenant, TENANT_ID);
		Tenant tenantBySearch = tenantService.getTenant(tenant.getTenantId());
		if(tenantBySearch != null){
			return getTenantContcts(tenantBySearch.getReadOnlyRowIdentifier());
		}else{
			throw new GalaxyException(INVALID_DATA_SPECIFIED,TENANT.getDescription() + " "+ tenant.getTenantId());
		}
    }
	
	@Override
    public Contact getContact(SearchContact searchContact) {
		validateMandatoryFields(searchContact.getTenantId(), TENANT_ID);
		validateMandatoryFields(searchContact.getContactName(), CONTACT_NAME);
		Tenant tenantBySearch = tenantService.getTenant(searchContact.getTenantId());
		if(tenantBySearch != null){
			
		}else{
			throw new GalaxyException(INVALID_DATA_SPECIFIED,TENANT.getDescription() + " "+ searchContact.getTenantId());
		}
	    return null;
    }
	
	@Override
	public StatusMessageDTO attachContacts(EquipmentContacts equipContacts) {
		validateMandatoryFields(equipContacts, EQUIPMENT_ID);
		validateMandatoryFields(equipContacts, TENANT_ID);
		validateCollection(CONTACTS_IDS, equipContacts.getContactIds());
		for(String contactId:equipContacts.getContactIds()){
			validateMandatoryField(CONTACT_ID, contactId);	
		}
		if (!equipmentService.isEquipmentExists(equipContacts.getEquipmentId())) {
			throw new GalaxyException(INVALID_DATA_SPECIFIED,
			        EQUIPMENT.getDescription());
		}
		checkIsValidContacts(equipContacts.getContactIds());
		return attachContactToEquip(equipContacts);
	}
	
	@Override
	public List<Contact> getEquipmentContacts(String equipId) {
		if (StringUtils.isNotBlank(equipId)) {
			if(equipmentService.isEquipmentExists(equipId)){
				return getAttachedContcts(equipId);
			}else{
				throw new GalaxyException(INVALID_DATA_SPECIFIED,EQUIPMENT.getDescription());
			}
		} else {
			throw new GalaxyException(FIELD_DATA_NOT_SPECIFIED,
					EQUIPMENT_ID.getDescription());
		}
	}
	
	private List<Contact> getAttachedContcts(String equipId) {
		List<EntityDTO> contacts = null;
		contacts = apiMgrClient.post(heirarchyParentsEP,
				tokenProvider.getAuthToken(Integraters.CCD),
		        getEquipContactsPayload1(equipId), List.class,
		        EntityDTO.class);
		return convertToContact(contacts);
	}
	
	private void checkIsValidContacts(List<String> contactIds) {
		for (String contactId : contactIds) {
			if(StringUtils.isNotBlank(contactId)){
				if (!isContactExists(contactId)) {
					throw new GalaxyException(INVALID_DATA_SPECIFIED,"Contact id "
					        + contactId );
				}
			} else {
				throw new GalaxyException(INVALID_DATA_SPECIFIED,CONTACT_ID.getDescription());
			}
		}
	}
	
	private HierarchySearchDTO getEquipContactsPayload1(String equipId) {
		HierarchySearchDTO hs = new HierarchySearchDTO();

		IdentityDTO actor = new IdentityDTO();

		PlatformEntityDTO platformEntity = new PlatformEntityDTO();
		platformEntity.setPlatformEntityType(MARKER);
		actor.setPlatformEntity(platformEntity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		actor.setDomain(domain);

		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(equipmentTemp);
		actor.setEntityTemplate(entityTemp);

		FieldMapDTO identifier = new FieldMapDTO(IDENTIFIER, equipId);
		actor.setIdentifier(identifier);
		hs.setActor(actor);

		hs.setSearchTemplateName(contactsTemp);
		hs.setSearchEntityType(MARKER);

		return hs;
	}

	
	private StatusMessageDTO attachContactToEquip(
	        EquipmentContacts equipContacts) {
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(Status.FAILURE);
		for (String contactId : equipContacts.getContactIds()) {

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
			identifier
			        .setValue(equipContacts.getEquipmentId());
			actor.setIdentifier(identifier);

			hierarchy.setActor(actor);

			List<EntityDTO> subjects = new ArrayList<EntityDTO>();
			EntityDTO sub = new EntityDTO();

			DomainDTO domainSub = new DomainDTO();
			domainSub.setDomainName(domainName);
			sub.setDomain(domainSub);

			EntityTemplateDTO entityTempSub = new EntityTemplateDTO();
			entityTempSub.setEntityTemplateName(contactsTemp);
			sub.setEntityTemplate(entityTempSub);

			PlatformEntityDTO platformEntitySub = new PlatformEntityDTO();
			platformEntitySub.setPlatformEntityType(MARKER);
			sub.setPlatformEntity(platformEntitySub);

			FieldMapDTO identifierSub = new FieldMapDTO();
			identifierSub.setKey(IDENTIFIER);
			identifierSub.setValue(contactId);
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
					        "Error in attach contact to equipment for equip {} for contact {}",
					        equipContacts.getEquipmentId(),
					        contactId);
					throw new GalaxyException(e);
				}
				status.setStatus(Status.SUCCESS);
			}
		}
		return status;
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
	
	private List<Contact> getTenantContcts(String tenantRowId) {
		List<EntityDTO> contacts = null;
		contacts = apiMgrClient.post(apiheirarchyChildrenEP, tokenProvider.getAuthToken(Integraters.CCD),
		        getAttachedContactsPayload(tenantRowId), List.class,
		        EntityDTO.class);
		return convertToContact(contacts);
	}
	
	private HierarchySearchDTO getAttachedContactsPayload(String tenantRowId) {
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

		hs.setSearchTemplateName(contactsTemp);
		hs.setSearchEntityType("MARKER");

		return hs;
	}
	
	private List<Contact> convertToContact(List<EntityDTO> entityDTOs){
		List<Contact> contacts = new ArrayList<Contact>();
		for(EntityDTO entityDTO : entityDTOs){
			Contact contact = new Contact();
			contact.setName(fetchFieldValue(entityDTO.getDataprovider(),NAME.getFieldName()));
			contact.setEmail(fetchFieldValue(entityDTO.getDataprovider(),EMAIL.getFieldName()));
			contact.setContactNumber(fetchFieldValue(entityDTO.getDataprovider(),CONTACT_NUMBER.getFieldName()));
			contact.setContactType(ContactType.valueOf(fetchFieldValue(entityDTO.getDataprovider(),CONTACT_TYPE.getFieldName())));
			contact.setRowIdentifier(entityDTO.getIdentifier().getValue());
			contacts.add(contact);
		}
		return contacts;
	}
	
}
