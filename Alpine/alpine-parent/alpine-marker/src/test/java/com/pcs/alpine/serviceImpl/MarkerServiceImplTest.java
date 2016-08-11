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
package com.pcs.alpine.serviceImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.rest.beans.StandardResponse;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.serviceImpl.validation.CommonEntityValidations;
import com.pcs.alpine.serviceImpl.validation.EntityValidation;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.GlobalEntityService;
import com.pcs.alpine.services.GlobalEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.dto.builder.EntityDTOBuilder;
import com.pcs.alpine.services.dto.builder.EntityTemplateDTOBuilder;
import com.pcs.alpine.services.dto.builder.IdentityDTOBuilder;
import com.pcs.alpine.services.enums.EMDataFields;
import com.pcs.alpine.services.enums.Status;

/**
 * This class is responsible for unit testing ClientAdapterServiceImpl
 * 
 * @author Bhupendra(PCSEG329)
 */
@ContextConfiguration("classpath:adapter-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MarkerServiceImplTest {

	@InjectMocks
	private MarkerServiceImpl MarkerServiceImpl;

	@Mock
	private GlobalEntityService globalEntityService;

	@Mock
	private EntityValidation entityTemplateValidation;

	@Mock
	private GlobalEntityTemplateService globalEntityTemplateService;

	@Mock
	private EntityTemplateService templateService;

	@Mock
	private CommonEntityValidations commonEntityValidations;

	@Mock
	CoreEntityService coreEntityService;

	@Mock
	private ObjectBuilderUtil objectBuilderUtil;

	@Mock
	private SubscriptionProfileService subscriptionProfileService;

	@Mock
	private BaseClient baseClient;

	@Mock
	private StatusService statusService;

	private EntityDTO MarkerEntity;

	private IdentityDTO identityDTO;

	private StatusMessageDTO statusMessageDTO;

	List<EntityDTO> MarkerList;

	private FieldMapDTO uniqueUserId;

	private FieldMapDTO loggedInUserDomain;

	private Subscription subscription;

	private String endUserDomain;

	private EntityDTO domainEntity;

	private Map<String, String> defaultBasicAuthHeader = ImmutableMap
	        .<String, String> builder()
	        .put("x-jwt-assertion",
	                "ll.eyJpc3MiOiJ3c28yLm9yZy9wcm9kdWN0cy9hbSIsImV4cCI6MTQ0MzUwNTQ4OTQzOSwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9zdWJzY3JpYmVyIjoiYXZvY2FkbyIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb25pZCI6IjI3IiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbm5hbWUiOiJhdm9jYWRvIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2FwaWNvbnRleHQiOiIvYWxwaW5ldXNlciIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdmVyc2lvbiI6IjEuMC4wIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy90aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9lbmR1c2VyIjoicGNzYWRtaW5AcGNzLmNvbSIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW5kdXNlclRlbmFudElkIjoiMSIsInN1YnNjcmliZXJEb21haW4iOiJhbHBpbmUuY29tIiwiZW5kVXNlckRvbWFpbiI6InBjcy5hbHBpbmUuY29tIiwic3Vic2NyaWJlck5hbWUiOiJhdm9jYWRvIiwiZW5kVXNlck5hbWUiOiJwY3NhZG1pbiIsIkNvbnN1bWVyS2V5IjoiNkt5djExRkV6a09SM0Y1ejQwcUEzelVCUWJRYSIsInN1YnNjcmliZXJBcHAiOiJhdm9jYWRvIn0=.ll")
	        .build();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		statusMessageDTO = new com.pcs.alpine.services.dto.StatusMessageDTO();
		statusMessageDTO.setStatus(Status.ACTIVE);
		MarkerList = new ArrayList<EntityDTO>();
		uniqueUserId = new FieldMapDTO();
		uniqueUserId.setKey(EMDataFields.USER_NAME.getVariableName());
		uniqueUserId.setValue("galaxyadmin");
		loggedInUserDomain = new FieldMapDTO();
		loggedInUserDomain.setKey(EMDataFields.DOMAIN.getVariableName());
		loggedInUserDomain.setValue("galaxy.com");

		endUserDomain = "pcs.galaxy";

		subscription = new Subscription();
		subscription.setEndUserDomain(endUserDomain);

		domainEntity = new EntityDTO();
		domainEntity.setEntityId("e2684346-cfbc-4056-a330-c71188adf473");
	}

	/**
	 * Test Case: Successful get MARKER Entity
	 */
	@Test
	public void testGetMarkerEntitySuccess() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER",
		        "jll.galaxy", "JLLMarkerTemplate", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		EntityDTO entity = MarkerServiceImpl.getMarker(identityDTO);

		Mockito.when(
		        baseClient.post("", defaultBasicAuthHeader, identityDTO,
		                StandardResponse.class)).thenReturn(null);
		Assert.assertTrue(entity != null);
		assertEquals(MarkerEntity.getEntityStatus().getStatusName(), entity
		        .getEntityStatus().getStatusName());
		assertEquals(MarkerEntity.getIdentifier(), entity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        entity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        entity.getEntityTemplate().getEntityTemplateName());
	}

	/**
	 * Test Case: Throws GalaxyException if global entity is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetMarkerEntityWithBlankGlobalEntity() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("", "jll.galaxy",
		        "JLLMarkerTemplate", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		EntityDTO entity = MarkerServiceImpl.getMarker(identityDTO);

		Assert.assertTrue(entity != null);
		assertEquals(MarkerEntity.getEntityStatus().getStatusName(), entity
		        .getEntityStatus().getStatusName());
		assertEquals(MarkerEntity.getIdentifier(), entity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        entity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        entity.getEntityTemplate().getEntityTemplateName());

	}

	/**
	 * Test Case: Throws GalaxyException if domain name is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetMarkerEntityWithBlankDomain() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER", "",
		        "JLLMarkerTemplate", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.when(subscriptionProfileService.getSubscription()).thenReturn(
		        subscription);
		EntityDTO entity = MarkerServiceImpl.getMarker(identityDTO);
		Assert.assertTrue(entity != null);
		assertEquals(MarkerEntity.getEntityStatus().getStatusName(), entity
		        .getEntityStatus().getStatusName());
		assertEquals(MarkerEntity.getIdentifier(), entity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        entity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        entity.getEntityTemplate().getEntityTemplateName());
	}

	/**
	 * Test Case: Throws GalaxyException if EntityTemplate is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetMarkerEntityWithBlankEntityTemplate() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER",
		        "jll.galaxy", "", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		EntityDTO entity = MarkerServiceImpl.getMarker(identityDTO);
		Assert.assertTrue(entity != null);
		assertEquals(MarkerEntity.getEntityStatus().getStatusName(), entity
		        .getEntityStatus().getStatusName());
		assertEquals(MarkerEntity.getIdentifier(), entity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        entity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        entity.getEntityTemplate().getEntityTemplateName());
	}

	/**
	 * Test Case: Throws GalaxyException if identifier is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetMarkerEntityWithBlankIdentifier() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER",
		        "jll.galaxy", "JLLMarkerTemplate", "identifier", "").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		EntityDTO entity = MarkerServiceImpl.getMarker(identityDTO);
		Assert.assertTrue(entity != null);
		assertEquals(MarkerEntity.getEntityStatus().getStatusName(), entity
		        .getEntityStatus().getStatusName());
		assertEquals(MarkerEntity.getIdentifier(), entity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        entity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        entity.getEntityTemplate().getEntityTemplateName());
	}

	/**
	 * Test Case: Successful delete a MARKER Entity
	 */
	@Test
	public void testDeleteMarkerEntitySuccess() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER",
		        "jll.galaxy", "JLLMarkerTemplate", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.when(coreEntityService.deleteEntity(MarkerEntity.getEntityId()))
		        .thenReturn(statusMessageDTO);
		StatusMessageDTO smDTO = MarkerServiceImpl.deleteMarker(identityDTO);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());

	}

	/**
	 * Test Case: Throws GalaxyException if global entity is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testDeleteMarkerEntityWithBlankGlobalEntity() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("", "jll.galaxy",
		        "JLLMarkerTemplate", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.when(coreEntityService.deleteEntity(MarkerEntity.getEntityId()))
		        .thenReturn(statusMessageDTO);
		StatusMessageDTO smDTO = MarkerServiceImpl.deleteMarker(identityDTO);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Throws GalaxyException if domain name is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testDeleteMarkerEntityWithBlankDomain() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER", "",
		        "JLLMarkerTemplate", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.when(coreEntityService.deleteEntity(MarkerEntity.getEntityId()))
		        .thenReturn(statusMessageDTO);
		StatusMessageDTO smDTO = MarkerServiceImpl.deleteMarker(identityDTO);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Throws GalaxyException if EntityTemplate is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testDeleteMarkerEntityWithBlankEntityTemplate()
	        throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER",
		        "jll.galaxy", "", "identifier",
		        "25e6a96a-edc0-43aa-9bdf-f614a3c8c833").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.when(coreEntityService.deleteEntity(MarkerEntity.getEntityId()))
		        .thenReturn(statusMessageDTO);
		StatusMessageDTO smDTO = MarkerServiceImpl.deleteMarker(identityDTO);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Throws GalaxyException if identifier is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testDeleteMarkerEntityWithBlankIdentifier() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("MARKER",
		        "jll.galaxy", "JLLMarkerTemplate", "identifier", "").build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.when(coreEntityService.deleteEntity(MarkerEntity.getEntityId()))
		        .thenReturn(statusMessageDTO);
		StatusMessageDTO smDTO = MarkerServiceImpl.deleteMarker(identityDTO);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Successful get MARKER Entity By Domain and uniqueUserId
	 */
	@Test
	public void testGetEntityByDomainAndUniqueUserIdSuccess() throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO(
		        EMDataFields.ORGANIZATION.getFieldName(), "pcs.com",
		        "PcsOrgTemplate", "orgName", "JLL").build();
		EntityDTO entity1 = new EntityDTOBuilder().anEntityDTO("TestMarker1")
		        .build();
		EntityDTO entity2 = new EntityDTOBuilder().anEntityDTO("TestMarker2")
		        .build();
		MarkerList.add(entity1);
		MarkerList.add(entity2);
		Mockito.when(
		        coreEntityService.getEntitiesByDomain((EntitySearchDTO)Mockito
		                .anyObject())).thenReturn(MarkerList);
		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());
		Mockito.when(subscriptionProfileService.getSubscription()).thenReturn(
		        subscription);

		List<EntityDTO> MarkerEntityList = MarkerServiceImpl
		        .getMarkersByDomain(identityDTO);

		for (EntityDTO MarkerDTO : MarkerList) {
			for (EntityDTO MarkerDTOTest : MarkerEntityList) {
				assertEquals(MarkerDTO.getEntityStatus().getStatusName(),
				        MarkerDTOTest.getEntityStatus().getStatusName());
			}
		}

	}

	/**
	 * Test Case: Throws GalaxyException if global entity is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetEntityByDomainAndUniqueUserWithBlankGlobalEntity()
	        throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO("", "pcs.com",
		        "PcsOrgTemplate", "orgName", "JLL").build();
		EntityDTO entity1 = new EntityDTOBuilder().anEntityDTO("TestMarker1")
		        .build();
		EntityDTO entity2 = new EntityDTOBuilder().anEntityDTO("TestMarker2")
		        .build();
		MarkerList.add(entity1);
		MarkerList.add(entity2);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());
		List<EntityDTO> MarkerEntityList = MarkerServiceImpl
		        .getMarkersByDomain(identityDTO);

		for (EntityDTO MarkerDTO : MarkerList) {
			for (EntityDTO MarkerDTOTest : MarkerEntityList) {
				assertEquals(MarkerDTO.getEntityStatus().getStatusName(),
				        MarkerDTOTest.getEntityStatus().getStatusName());
			}
		}
	}

	/**
	 * Test Case: Throws GalaxyException if domain name is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetEntityByDomainAndUniqueUserWithBlankDomain()
	        throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();

		identityDTO = new IdentityDTOBuilder().anIdentityDTO(
		        EMDataFields.ORGANIZATION.getFieldName(), "", "PcsOrgTemplate",
		        "orgName", "JLL").build();
		EntityDTO entity1 = new EntityDTOBuilder().anEntityDTO("TestMarker1")
		        .build();
		EntityDTO entity2 = new EntityDTOBuilder().anEntityDTO("TestMarker2")
		        .build();
		MarkerList.add(entity1);
		MarkerList.add(entity2);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		List<EntityDTO> MarkerEntityList = MarkerServiceImpl
		        .getMarkersByDomain(identityDTO);

		for (EntityDTO MarkerDTO : MarkerList) {
			for (EntityDTO MarkerDTOTest : MarkerEntityList) {
				assertEquals(MarkerDTO.getEntityStatus().getStatusName(),
				        MarkerDTOTest.getEntityStatus().getStatusName());
			}
		}
	}

	/**
	 * Test Case: Throws GalaxyException if EntityTemplate is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetEntityByDomainAndUniqueUserWithBlankEntityTemplate()
	        throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO(
		        EMDataFields.ORGANIZATION.getFieldName(), "pcs.com", "",
		        "orgName", "JLL").build();
		EntityDTO entity1 = new EntityDTOBuilder().anEntityDTO("TestMarker1")
		        .build();
		EntityDTO entity2 = new EntityDTOBuilder().anEntityDTO("TestMarker2")
		        .build();
		MarkerList.add(entity1);
		MarkerList.add(entity2);
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());
		List<EntityDTO> MarkerEntityList = MarkerServiceImpl
		        .getMarkersByDomain(identityDTO);

		for (EntityDTO MarkerDTO : MarkerList) {
			for (EntityDTO MarkerDTOTest : MarkerEntityList) {
				assertEquals(MarkerDTO.getEntityStatus().getStatusName(),
				        MarkerDTOTest.getEntityStatus().getStatusName());
			}
		}
	}

	/**
	 * Test Case: Throws GalaxyException if identifier is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetEntityByDomainAndUniqueUserWithBlankIdentifier()
	        throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO(
		        EMDataFields.ORGANIZATION.getFieldName(), "pcs.com", "",
		        "orgName", "").build();
		EntityDTO entity1 = new EntityDTOBuilder().anEntityDTO("TestMarker1")
		        .build();
		EntityDTO entity2 = new EntityDTOBuilder().anEntityDTO("TestMarker2")
		        .build();
		MarkerList.add(entity1);
		MarkerList.add(entity2);
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());
		List<EntityDTO> MarkerEntityList = MarkerServiceImpl
		        .getMarkersByDomain(identityDTO);

		for (EntityDTO MarkerDTO : MarkerList) {
			for (EntityDTO MarkerDTOTest : MarkerEntityList) {
				assertEquals(MarkerDTO.getEntityStatus().getStatusName(),
				        MarkerDTOTest.getEntityStatus().getStatusName());
			}
		}
	}

	/**
	 * Test Case: Throws GalaxyException if UniqueUserId is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testGetEntityByDomainAndUniqueUserWithBlankUniqueUserId()
	        throws Exception {
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker").build();
		identityDTO = new IdentityDTOBuilder().anIdentityDTO(
		        EMDataFields.ORGANIZATION.getFieldName(), "pcs.com", "",
		        "orgName", "").build();
		EntityDTO entity1 = new EntityDTOBuilder().anEntityDTO("TestMarker1")
		        .build();
		EntityDTO entity2 = new EntityDTOBuilder().anEntityDTO("TestMarker2")
		        .build();
		MarkerList.add(entity1);
		MarkerList.add(entity2);
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateIdentifierFields(identityDTO);
		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());
		List<EntityDTO> MarkerEntityList = MarkerServiceImpl
		        .getMarkersByDomain(identityDTO);

		for (EntityDTO MarkerDTO : MarkerList) {
			for (EntityDTO MarkerDTOTest : MarkerEntityList) {
				assertEquals(MarkerDTO.getEntityStatus().getStatusName(),
				        MarkerDTOTest.getEntityStatus().getStatusName());
			}
		}
	}

	/**
	 * Test Case: To Create MARKER entity with valid data
	 */

	@Test
	public void testSaveMarkerEntitySuccess() throws Exception {

		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker")
		        .withHierarchyDTO().build();
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);
		Mockito.when(
		        entityTemplateValidation.validateTemplate(MarkerEntity, false))
		        .thenReturn(MarkerEntity);
		Mockito.when(subscriptionProfileService.getSubscription()).thenReturn(
		        subscription);

		Mockito.when(coreEntityService.saveEntity(MarkerEntity)).thenReturn(
		        MarkerEntity);

		Mockito.when(coreEntityService.getDomainEntity(Mockito.anyString()))
		        .thenReturn(domainEntity);

		EntityDTO savedMarkerEntity = MarkerServiceImpl
		        .saveMarker(MarkerEntity);
		Assert.assertTrue(savedMarkerEntity != null);

		assertEquals(MarkerEntity.getIdentifier(),
		        savedMarkerEntity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        savedMarkerEntity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        savedMarkerEntity.getEntityTemplate().getEntityTemplateName());

	}

	/**
	 * Test Case: Throws GalaxyException if global entity is not specified
	 */
	@Test
	public void testSaveMarkerEntityWithBlankGlobalEntity() throws Exception {

		MarkerEntity = new EntityDTOBuilder()
		        .withParametersEntityDTO("", "pcs.com", "ACTIVE",
		                "PCSMarkerTemplate", "entityName", "CANADA")
		        .withHierarchyDTO().build();

		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntity(MarkerEntity);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(coreEntityService.saveEntity(MarkerEntity)).thenReturn(
		        MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);
		Mockito.when(
		        entityTemplateValidation.validateTemplate(MarkerEntity, false))
		        .thenReturn(MarkerEntity);
		Mockito.when(subscriptionProfileService.getSubscription()).thenReturn(
		        subscription);
		Mockito.when(coreEntityService.getDomainEntity(Mockito.anyString()))
		        .thenReturn(domainEntity);

		EntityDTO savedMarkerEntity = MarkerServiceImpl
		        .saveMarker(MarkerEntity);

		Assert.assertTrue(savedMarkerEntity != null);

		assertEquals(MarkerEntity.getIdentifier(),
		        savedMarkerEntity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        savedMarkerEntity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        savedMarkerEntity.getEntityTemplate().getEntityTemplateName());

	}

	/**
	 * Test Case: Throws GalaxyException if domain is not specified
	 */
	@Test
	public void testSaveMarkerEntityWithBlankDomain() {

		MarkerEntity = new EntityDTOBuilder()
		        .withParametersEntityDTO("MARKER", "", "ACTIVE",
		                "PCSMarkerTemplate", "entityName", "CANADA")
		        .withHierarchyDTO().build();

		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntity(MarkerEntity);
		Mockito.when(coreEntityService.saveEntity(MarkerEntity)).thenReturn(
		        MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);
		Mockito.when(
		        entityTemplateValidation.validateTemplate(MarkerEntity, false))
		        .thenReturn(MarkerEntity);

		Mockito.when(subscriptionProfileService.getSubscription()).thenReturn(
		        subscription);

		Mockito.when(statusService.getStatus(Mockito.anyString()))
		        .thenReturn(0);

		Mockito.when(coreEntityService.getDomainEntity(Mockito.anyString()))
		        .thenReturn(domainEntity);

		EntityDTO savedMarkerEntity = MarkerServiceImpl
		        .saveMarker(MarkerEntity);

		Assert.assertTrue(savedMarkerEntity != null);

		assertEquals(MarkerEntity.getIdentifier(),
		        savedMarkerEntity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        savedMarkerEntity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        savedMarkerEntity.getEntityTemplate().getEntityTemplateName());

	}

	/**
	 * Test Case: Throws GalaxyException if entity template is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testSaveMarkerEntityWithBlankEntityTemplate() {

		MarkerEntity = new EntityDTOBuilder()
		        .withParametersEntityDTO("MARKER", "pcs.com", "ACTIVE", "",
		                "entityName", "CANADA").withHierarchyDTO().build();

		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntity(MarkerEntity);
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(coreEntityService.saveEntity(MarkerEntity)).thenReturn(
		        MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);
		EntityDTO savedMarkerEntity = MarkerServiceImpl
		        .saveMarker(MarkerEntity);

		Assert.assertTrue(savedMarkerEntity != null);
		assertEquals(MarkerEntity.getEntityStatus().getStatusName(),
		        savedMarkerEntity.getEntityStatus().getStatusName());
		assertEquals(MarkerEntity.getIdentifier(),
		        savedMarkerEntity.getIdentifier());
		assertEquals(MarkerEntity.getGlobalEntity().getGlobalEntityType(),
		        savedMarkerEntity.getGlobalEntity().getGlobalEntityType());
		assertEquals(MarkerEntity.getEntityTemplate().getEntityTemplateName(),
		        savedMarkerEntity.getEntityTemplate().getEntityTemplateName());

	}

	/**
	 * Test Case: Successful update MARKER Entity
	 */

	@Test
	public void testUpdateMarkerEntitySuccess() {
		statusMessageDTO.setStatus(Status.SUCCESS);
		MarkerEntity = new EntityDTOBuilder().anEntityDTO("TestMarker")
		        .withHierarchyDTO().build();
		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(coreEntityService.getEntity(Mockito.anyString()))
		        .thenReturn(MarkerEntity);
		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Mockito.when(
		        coreEntityService.updateEntity((EntityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);
		Mockito.when(
		        entityTemplateValidation.validateTemplate(MarkerEntity, true))
		        .thenReturn(MarkerEntity);

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);

		StatusMessageDTO smDTO = MarkerServiceImpl.updateMarker(MarkerEntity);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());

	}

	/**
	 * Test Case: Throws GalaxyException if global entity is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateMarkerEntityWithBlankGlobalEntity() {
		MarkerEntity = new EntityDTOBuilder()
		        .withParametersEntityDTO("", "jll.galaxy", "ACTIVE",
		                "PCSMarkerTemplate", "identifier",
		                "6b876cfa-2a11-41cb-8247-c9c9a6cb6f54")
		        .withHierarchyDTO().build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntityUpdate(MarkerEntity);

		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Mockito.when(
		        coreEntityService.updateEntity((EntityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);

		StatusMessageDTO smDTO = MarkerServiceImpl.updateMarker(MarkerEntity);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Throws GalaxyException if domain name is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateMarkerEntityWithBlankDomainName() {
		MarkerEntity = new EntityDTOBuilder()
		        .withParametersEntityDTO("MARKER", "", "ACTIVE",
		                "PCSMarkerTemplate", "identifier",
		                "6b876cfa-2a11-41cb-8247-c9c9a6cb6f54")
		        .withHierarchyDTO().build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntityUpdate(MarkerEntity);

		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Mockito.when(
		        coreEntityService.updateEntity((EntityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);

		StatusMessageDTO smDTO = MarkerServiceImpl.updateMarker(MarkerEntity);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Throws GalaxyException if entity template is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateMarkerEntityWithBlankEntityTempalate() {
		MarkerEntity = new EntityDTOBuilder()
		        .withParametersEntityDTO("MARKER", "pcs.com", "ACTIVE", "",
		                "identifier", "6b876cfa-2a11-41cb-8247-c9c9a6cb6f54")
		        .withHierarchyDTO().build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntityUpdate(MarkerEntity);

		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Mockito.when(
		        coreEntityService.updateEntity((EntityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);

		StatusMessageDTO smDTO = MarkerServiceImpl.updateMarker(MarkerEntity);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Throws GalaxyException if entity status is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateMarkerEntityWithBlankStatus() {
		MarkerEntity = new EntityDTOBuilder()
		        .withParametersEntityDTO("MARKER", "pcs.com", "",
		                "PCSMarkerTemplate", "identifier",
		                "6b876cfa-2a11-41cb-8247-c9c9a6cb6f54")
		        .withHierarchyDTO().build();
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntityUpdate(MarkerEntity);

		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Mockito.when(
		        coreEntityService.updateEntity((EntityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);

		StatusMessageDTO smDTO = MarkerServiceImpl.updateMarker(MarkerEntity);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

	/**
	 * Test Case: Throws GalaxyException if entity identifier is not specified
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateMarkerEntityWithBlankIdentifier() {
		MarkerEntity = new EntityDTOBuilder()

		        .withParametersEntityDTO("MARKER", "pcs.com", "ACTIVE",
		                "PCSMarkerTemplate", "identifier", "")
		        .withHierarchyDTO().build();
		statusMessageDTO.setStatus(Status.SUCCESS);
		Mockito.doThrow(new GalaxyException()).when(commonEntityValidations)
		        .validateMandatoryForEntityUpdate(MarkerEntity);

		Mockito.when(
		        coreEntityService.getEntity((IdentityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(Mockito.anyString()))
		        .thenReturn(MarkerEntity.getGlobalEntity());

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        new EntityTemplateDTOBuilder().anEntityTemplateDTO().build());

		Mockito.when(
		        coreEntityService.updateEntity((EntityDTO)Mockito.anyObject()))
		        .thenReturn(MarkerEntity);

		Gson gson = new Gson();
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(gson);

		StatusMessageDTO smDTO = MarkerServiceImpl.updateMarker(MarkerEntity);
		Assert.assertTrue(smDTO != null);
		assertEquals(statusMessageDTO.getStatus(), smDTO.getStatus());
	}

}
