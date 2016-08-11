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

package com.pcs.galaxy.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.FileReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.is.client.dto.DomainDTO;
import com.pcs.alpine.serviceImpl.validation.CommonEntityValidations;
import com.pcs.alpine.serviceImpl.validation.EntityValidation;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.DomainService;
import com.pcs.alpine.services.GlobalEntityService;
import com.pcs.alpine.services.GlobalEntityTemplateService;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.GlobalEntityDTO;
import com.pcs.alpine.services.dto.GlobalEntityTemplateDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.builder.EntityDTOBuilder;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.impl.TenantServiceImpl;

@ContextConfiguration("classpath:app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TenantServiceImplTest {

	@InjectMocks
	private TenantServiceImpl sut;

	@Mock
	private SubscriptionProfileService subscriptionProfileService;

	@Mock
	private GlobalEntityService globalEntityService;

	@Mock
	private CoreEntityService coreEntityService;

	@Mock
	private EntityValidation entityValidation;

	@Mock
	private DomainService domainService;

	@Mock
	private CommonEntityValidations commonEntityValidations;

	@Mock
	private GlobalEntityTemplateService globalEntityTemplateService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	Gson gson = null;
	GlobalEntityDTO globalEntityDTO = null;

	String type = EMDataFields.TENANT.getVariableName();
	String subcriptionEndUserDomain = null;
	String parentDomain = null;

	private GlobalEntityTemplateDTO globalEntityTemplate;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		gson = new Gson();

		globalEntityDTO = new GlobalEntityDTO();
		globalEntityDTO.setGlobalEntityType(type);
		globalEntityDTO.setIsDefault(Boolean.TRUE.booleanValue());

		subcriptionEndUserDomain = new String("pcs.galaxy");
		parentDomain = subcriptionEndUserDomain;
		globalEntityTemplate = new GlobalEntityTemplateDTO();

		globalEntityTemplate.setGlobalEntityTemplateName(EMDataFields.TENANT
		        .getFieldName());

	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * save tenant, valid case , creating tenant. ParentDomain must be taken
	 * from subscripttionProfile (endUserDomain : "alpine.com") own domain is
	 * created using tenantName domainEntity is existing in the system domain
	 * gets created in IS
	 */
	@Test
	public void createTenantTest() throws Exception {
		FileReader fileReader = new FileReader(
		        "src/test/resources/testJsonInput/createTenantRequest.json");
		EntityDTO tenantEntityInput = gson
		        .fromJson(fileReader, EntityDTO.class);
		fileReader.close();

		EntityDTO domainEntity = new EntityDTOBuilder().createDomainEntityDTO(
		        "pcs.galaxy", "7a963717-883f-4d6b-b796-82f97a92726c").build();

		fileReader = new FileReader(
		        "src/test/resources/testJsonInput/createTenantResponse.json");
		EntityDTO resultEntity = gson.fromJson(fileReader, EntityDTO.class);
		fileReader.close();

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName("");
		domainDTO.setEntity(tenantEntityInput);

		when(globalEntityService.getGlobalEntityWithName(type)).thenReturn(
		        globalEntityDTO);
		when(subscriptionProfileService.getEndUserDomain()).thenReturn(
		        subcriptionEndUserDomain);
		when(coreEntityService.getDomainEntity(parentDomain)).thenReturn(
		        domainEntity);
		when(entityValidation.validateTemplate(tenantEntityInput, false))
		        .thenReturn(tenantEntityInput);
		when(domainService.createDomain(domainDTO)).thenReturn(domainDTO);
		when(coreEntityService.saveEntity(tenantEntityInput)).thenReturn(
		        resultEntity);

		when(coreEntityService.getEntity(Mockito.any(IdentityDTO.class)))
		        .thenReturn(resultEntity);

		IdentityDTO entityDTO = sut.createTenant(tenantEntityInput);
		assertNull(entityDTO.getEntityTemplate().getDomain());
		assertNull(entityDTO.getEntityTemplate().getGlobalEntityType());
	}

	@Test(expected = GalaxyException.class)
	public void createTenantTestTenantIdEmpty() throws Exception {
		FileReader fileReader = new FileReader(
		        "src/test/resources/testJsonInput/createTenantNoTenantId.json");
		EntityDTO tenantEntityInput = gson
		        .fromJson(fileReader, EntityDTO.class);
		fileReader.close();

		EntityDTO domainEntity = new EntityDTOBuilder().createDomainEntityDTO(
		        "pcs.galaxy", "7a963717-883f-4d6b-b796-82f97a92726c").build();

		fileReader = new FileReader(
		        "src/test/resources/testJsonInput/createTenantResponse.json");
		EntityDTO resultEntity = gson.fromJson(fileReader, EntityDTO.class);
		fileReader.close();

		DomainDTO domainDTO = new DomainDTO();
		domainDTO.setDomainName("");
		domainDTO.setEntity(tenantEntityInput);

		when(globalEntityService.getGlobalEntityWithName(type)).thenReturn(
		        globalEntityDTO);
		when(subscriptionProfileService.getEndUserDomain()).thenReturn(
		        subcriptionEndUserDomain);
		when(coreEntityService.getDomainEntity(parentDomain)).thenReturn(
		        domainEntity);
		when(entityValidation.validateTemplate(tenantEntityInput, false))
		        .thenReturn(tenantEntityInput);
		when(domainService.createDomain(domainDTO)).thenReturn(domainDTO);
		when(coreEntityService.saveEntity(tenantEntityInput)).thenReturn(
		        resultEntity);

		new IdentityDTO(resultEntity);
		when(coreEntityService.getEntity(Mockito.any(IdentityDTO.class)))
		        .thenReturn(resultEntity);

		sut.createTenant(tenantEntityInput);
	}

	@Test
	public void findTenantSuccessTest() throws Exception {
		FileReader fileReader = new FileReader(
		        "src/test/resources/testJsonInput/findTenant.json");
		IdentityDTO tenantIdentityInput = gson.fromJson(fileReader,
		        IdentityDTO.class);
		fileReader.close();

		fileReader = new FileReader(
		        "src/test/resources/testJsonInput/createTenantResponse.json");
		EntityDTO resultEntity = gson.fromJson(fileReader, EntityDTO.class);
		fileReader.close();

		Mockito.doNothing().when(commonEntityValidations)
		        .validateIdentifierFields(Mockito.any(IdentityDTO.class));
		when(coreEntityService.getEntity(Mockito.any(IdentityDTO.class)))
		        .thenReturn(resultEntity);
		when(
		        globalEntityTemplateService.getGlobalEntityTemplate(Mockito
		                .anyString())).thenReturn(globalEntityTemplate);

		EntityDTO actualResponse = sut.findTenant(tenantIdentityInput);
		assertEquals(resultEntity.getDomain().getDomainName(), actualResponse
		        .getDomain().getDomainName());
		assertEquals(resultEntity.getIdentifier().getValue(), actualResponse
		        .getIdentifier().getValue());
	}

	@Test(expected = GalaxyException.class)
	public void findTenantWithNoAccessTest() throws Exception {
		FileReader fileReader = new FileReader(
		        "src/test/resources/testJsonInput/findTenant.json");
		IdentityDTO tenantIdentityInput = gson.fromJson(fileReader,
		        IdentityDTO.class);
		fileReader.close();
		tenantIdentityInput.setIsParentDomain(true);
		fileReader = new FileReader(
		        "src/test/resources/testJsonInput/createTenantResponse.json");
		EntityDTO resultEntity = gson.fromJson(fileReader, EntityDTO.class);
		fileReader.close();

		Mockito.doNothing().when(commonEntityValidations)
		        .validateIdentifierFields(Mockito.any(IdentityDTO.class));
		when(coreEntityService.getEntity(Mockito.any(IdentityDTO.class)))
		        .thenReturn(resultEntity);
		when(coreEntityService.getDomainEntity(Mockito.anyString()))
		        .thenReturn(resultEntity);
		when(
		        globalEntityTemplateService.getGlobalEntityTemplate(Mockito
		                .anyString())).thenReturn(globalEntityTemplate);
		when(subscriptionProfileService.getEndUserDomain()).thenReturn(
		        subcriptionEndUserDomain);

		sut.findTenant(tenantIdentityInput);
	}

	@Test
	public void findTenantNullDomainTest() throws Exception {
		FileReader fileReader = new FileReader(
		        "src/test/resources/testJsonInput/findTenant.json");
		IdentityDTO tenantIdentityInput = gson.fromJson(fileReader,
		        IdentityDTO.class);
		fileReader.close();

		tenantIdentityInput.setDomain(null);
		fileReader = new FileReader(
		        "src/test/resources/testJsonInput/createTenantResponse.json");
		EntityDTO resultEntity = gson.fromJson(fileReader, EntityDTO.class);
		fileReader.close();

		Mockito.doNothing().when(commonEntityValidations)
		        .validateIdentifierFields(Mockito.any(IdentityDTO.class));
		when(coreEntityService.getEntity(Mockito.any(IdentityDTO.class)))
		        .thenReturn(resultEntity);
		when(
		        globalEntityTemplateService.getGlobalEntityTemplate(Mockito
		                .anyString())).thenReturn(globalEntityTemplate);
		when(subscriptionProfileService.getEndUserDomain()).thenReturn(
		        subcriptionEndUserDomain);

		EntityDTO actualResponse = sut.findTenant(tenantIdentityInput);
		assertEquals(resultEntity.getDomain().getDomainName(), actualResponse
		        .getDomain().getDomainName());
		assertEquals(resultEntity.getIdentifier().getValue(), actualResponse
		        .getIdentifier().getValue());
	}
}