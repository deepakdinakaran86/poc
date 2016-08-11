package com.pcs.alpine.serviceImpl;

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

import static com.pcs.alpine.services.enums.EMDataFields.USER;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

import com.pcs.alpine.commons.dto.HierarchyDTO;
import com.pcs.alpine.model.Entity;
import com.pcs.alpine.model.EntityTemplate;
import com.pcs.alpine.model.keys.EntityKey;
import com.pcs.alpine.model.keys.EntityTemplateKey;
import com.pcs.alpine.model.udt.FieldMap;
import com.pcs.alpine.serviceImpl.repository.EntityRepository;
import com.pcs.alpine.serviceImpl.repository.EntityTemplateRepository;
import com.pcs.alpine.services.EntityTemplateService;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.PlatformEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.PlatformEntityTemplateDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.enums.Status;
import com.pcs.alpine.services.repository.HierarchyRepository;

/**
 * CoreEntityServiceImplTest
 * 
 * @description Test Class for CoreEntityServiceImplTest
 * @author Daniela (PCSEG191)
 * @date 30 Nov 2014
 * @since galaxy-1.0.0
 */

@ContextConfiguration("classpath:em-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CoreEntityServiceImplTest {

	@InjectMocks
	CoreEntityServiceImpl sut;

	@Mock
	private EntityRepository entityRepository;

	@Mock
	private PlatformEntityTemplateService globalEntityTemplateService;

	@Mock
	private StatusService statusService;

	@Mock
	private PlatformEntityService globalEntityService;

	@Mock
	private EntityTemplateService templateService;

	@Mock
	EntityTemplateRepository templateRepository;

	@Mock
	HierarchyRepository hierachyRepository;

	private EntitySearchDTO entitySearchDTO;

	private DomainDTO domainDTO;

	private PlatformEntityTemplateDTO userTemplate;

	private Entity entity;

	private EntityKey entityKey;

	private List<Entity> entities;

	private FieldMap fieldMap;

	private FieldMap userTypeMap;

	private FieldMap accessMap;

	private List<String> domainEntities;

	private EntityDTO entityDTO;

	private EntityTemplateDTO entityTemplateDTO;

	private StatusDTO entityStatus;

	private List<FieldMap> fieldValues;

	private List<FieldMapDTO> fieldMapList;

	private FieldMapDTO fieldMapDTO;

	private FieldMapDTO userType;

	private FieldMapDTO access;

	private PlatformEntityDTO globalEntity;

	private IdentityDTO identity;

	StatusDTO statusDTO;

	private PlatformEntityDTO globalEntityDTO;

	private HierarchyDTO hierarchyEntityDTO;

	private StatusMessageDTO statusMessageDTO;

	private Entity entityData;

	EntityTemplate entityTemplate;

	EntityTemplateKey entityTemplateKey;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	public static void main(String[] args) {
		EntityRepository entityRepository = new EntityRepository();
		entityRepository.getAllEntities("pcs.galaxy", "Marker", "Device", 3);
	}

	/**
	 * Test method for getEntitiesByDomain() ,valid
	 */
	@Test
	public void testGetEntitiesByDomain() {
		entities = new ArrayList<Entity>();
		Mockito.when(
		        entityRepository.getAllEntities("pcs.galaxy", "Marker",
		                "Device", 3)).thenReturn(entities);
		/*
		 * Mockito.when(
		 * entityRepository.getAllEntityIds(domainDTO.getDomainName(),
		 * entity.getPlatformEntityType(), 3)).thenReturn( domainEntities);
		 * Mockito.when(
		 * entityRepository.getEntitiesWithEntityIdsString(domainEntities))
		 * .thenReturn(entities);
		 */
		Mockito.when(statusService.getStatus(Status.ACTIVE.name())).thenReturn(
		        0);
		Mockito.when(
		        globalEntityService.getGlobalEntityWithName(USER.getFieldName()))
		        .thenReturn(globalEntity);

		Mockito.when(
		        templateService.getTemplate(Mockito
		                .any(EntityTemplateDTO.class))).thenReturn(
		        entityTemplateDTO);

		List<EntityDTO> actualEntity = sut.getEntitiesByDomain(entitySearchDTO);

		assertEquals(entityDTO.getEntityTemplate().getEntityTemplateName(),
		        actualEntity.get(0).getEntityTemplate().getEntityTemplateName());
		assertEquals(entityDTO.getDomain().getDomainName(), actualEntity.get(0)
		        .getDomain().getDomainName());
		assertEquals(entityDTO.getPlatformEntity().getPlatformEntityType(),
		        actualEntity.get(0).getPlatformEntity().getPlatformEntityType());
	}

}
