
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
package com.pcs.ccd.services;

import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.ccd.enums.TenantFields.TENANT_CONTACT_FNAME;
import static com.pcs.ccd.enums.TenantFields.TENANT_CONTACT_LNAME;
import static com.pcs.ccd.enums.TenantFields.TENANT_ID;
import static com.pcs.ccd.enums.TenantFields.TENANT_NAME;

import java.util.ArrayList;
import java.util.List;

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
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.ReturnFieldsDTO;
import com.pcs.avocado.commons.dto.SearchFieldsDTO;
import com.pcs.avocado.rest.client.BaseClient;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.enums.Integraters;
import com.pcs.ccd.token.TokenProvider;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 18, 2016
 */
@Service
public class TenantServiceImpl implements TenantService{

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TenantServiceImpl.class);
	
	@Autowired
	@Qualifier("apiMgrClient")
	private BaseClient apiMgrClient;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Value("${ccd.tenant.template}")
	private String tenantTemp;
	
	@Value("${domain.name}")
	private String domainName;
	
	@Value("${api.mgr.marker.search}")
	private String searchdMarkerEP;
	
	@Value("${api.mgr.marker.list.filter}")
	private String listFilterMarkerEP;
	
	@Override
    public Tenant getTenant(String tenantName) {
		validateMandatoryField(tenantName, TENANT_NAME.getDescription());
	    return getTenantBySearch(tenantName);
    }
	
	@Override
    public List<Tenant> getAllTenants() {
		List<EntityDTO> tenantEntities = null;
		try {
			tenantEntities = apiMgrClient.post(listFilterMarkerEP,
					tokenProvider.getAuthToken(Integraters.CCD),
					getAllTenantSearchPayload(),List.class,EntityDTO.class);
			return convertToTenantList(tenantEntities);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error returned from the remote service, could be requested data not found");
		}
	    return null;
    }
	
	private IdentityDTO getAllTenantSearchPayload(){
		IdentityDTO search = new IdentityDTO();
		
		EntityTemplateDTO entityTemp = new EntityTemplateDTO();
		entityTemp.setEntityTemplateName(tenantTemp);
		search.setEntityTemplate(entityTemp);
		
		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);
		
		return search;
	}
	
	private Tenant getTenantBySearch(String tenantName) {
		List<ReturnFieldsDTO> equipEntities = null;
		try {
			equipEntities = apiMgrClient.post(searchdMarkerEP,
					tokenProvider.getAuthToken(Integraters.CCD),
			        tenantSearchPayload(tenantName),List.class,ReturnFieldsDTO.class);
			return convertToTenant(equipEntities.get(0));
		} catch (Exception e) {
			LOGGER.error("Error returned from the remote service, could be requested data not found");
		}
		return null;
	}
	
	private SearchFieldsDTO tenantSearchPayload(String tenantName){
		SearchFieldsDTO search = new SearchFieldsDTO();
		
		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO searchField = new FieldMapDTO();
		searchField.setKey(TENANT_ID.getFieldName());
		searchField.setValue(tenantName);
		searchFields.add(searchField);
		search.setSearchFields(searchFields);
		
		EntityTemplateDTO entity = new EntityTemplateDTO();
		entity.setEntityTemplateName(tenantTemp);
		search.setEntityTemplate(entity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(domainName);
		search.setDomain(domain);
		
		return search;
	}
	
	private List<Tenant> convertToTenantList(List<EntityDTO> entityDTOs){
		List<Tenant> tenants = new ArrayList<Tenant>();
		for(EntityDTO entityDTO : entityDTOs){
			Tenant tenant = convertToTenant(entityDTO.getDataprovider(),entityDTO.getIdentifier());
			tenants.add(tenant);
		}
		return tenants;
	}
	
	private Tenant convertToTenant(List<FieldMapDTO> fieldMapDTOs,FieldMapDTO identifier){
		Tenant tenant = new Tenant();
		tenant.setTenantName(fetchFieldValue(fieldMapDTOs,TENANT_NAME.getFieldName()));
		tenant.setContactFName(fetchFieldValue(fieldMapDTOs,TENANT_CONTACT_FNAME.getFieldName()));
		tenant.setContactLName(fetchFieldValue(fieldMapDTOs,TENANT_CONTACT_LNAME.getFieldName()));
		tenant.setReadOnlyRowIdentifier(identifier.getValue());
		return tenant;
	}
	
	private Tenant convertToTenant(ReturnFieldsDTO returnFields){
		Tenant tenant = new Tenant();
		tenant.setTenantName(fetchFieldValue(returnFields.getDataprovider(),TENANT_NAME.getFieldName()));
		tenant.setContactFName(fetchFieldValue(returnFields.getDataprovider(),TENANT_CONTACT_FNAME.getFieldName()));
		tenant.setContactLName(fetchFieldValue(returnFields.getDataprovider(),TENANT_CONTACT_LNAME.getFieldName()));
		tenant.setReadOnlyRowIdentifier(returnFields.getIdentifier().getValue());
		return tenant;
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
