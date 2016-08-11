package com.pcs.alpine.serviceImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.junit.After;
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
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.commons.dto.StatusMessageErrorDTO;
import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.model.HierarchyEntity;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.serviceImpl.repository.EntityRepository;
import com.pcs.alpine.serviceImpl.repository.StatusRepository;
import com.pcs.alpine.services.CoreEntityService;
import com.pcs.alpine.services.GlobalEntityService;
import com.pcs.alpine.services.GlobalEntityTemplateService;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityAssignDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityRangeDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.GlobalEntityDTO;
import com.pcs.alpine.services.dto.GlobalEntityTemplateDTO;
import com.pcs.alpine.services.dto.HierarchyDTO;
import com.pcs.alpine.services.dto.HierarchyReturnDTO;
import com.pcs.alpine.services.dto.HierarchySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.builder.EntityAssignDTOBuilder;
import com.pcs.alpine.services.dto.builder.EntityDTOBuilder;
import com.pcs.alpine.services.dto.builder.EntityRangeDTOBuilder;
import com.pcs.alpine.services.dto.builder.HierarchyDTOBuilder;
import com.pcs.alpine.services.dto.builder.HierarchySearchDTOBuilder;
import com.pcs.alpine.services.repository.HierarchyRepository;

/**
 * 
 * This class is responsible for GeofenceServiceImplTest
 * 
 * @author Suraj Sugathan (PCSEG362)
 * @date 13 Apr 2016
 * @since alpine-1.0.0
 */
@ContextConfiguration("classpath:alpine-hierarchy-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class HierarchyServiceImplTest {

	@InjectMocks
	private HierarchyServiceImpl hierarchyServiceImpl;

	@Mock
	private BaseClient platformClient;

	@Mock
	private HierarchyRepository hierarchyRepository;

	@Mock
	private GlobalEntityService globalEntityService;

	@Mock
	private GlobalEntityTemplateService globalEntityTemplateService;

	@Mock
	private CoreEntityService coreEntityService;

	@Mock
	private StatusRepository statusRepository;

	@Mock
	private EntityRepository entityRepository;

	@Mock
	private StatusService statusService;

	private Subscription subscription;

	@Mock
	private SubscriptionProfileService subscriptionProfileService;

	private HierarchyDTOBuilder hierarchyDTOBuilder;

	private HierarchySearchDTOBuilder hierarchySearchDTOBuilder;

	private EntityAssignDTOBuilder entityAssignDTOBuilder;

	private EntityRangeDTOBuilder entityRangeDTOBuilder;

	private EntityDTOBuilder entityDTOBuilder;

	private StatusMessageDTO failureMessageDTO;

	private StatusDTO entityStatus;

	private StatusMessageDTO statusMessageDTO;

	private StatusMessageErrorDTO statusMessageErrorSuccess;

	private StatusMessageErrorDTO statusMessageUpdateError;

	private EntityDTO actor;

	private EntityDTO entityDTO;

	private IdentityDTO searchActor;

	private IdentityDTO parentIdentity;

	private IdentityDTO startEntity;

	private IdentityDTO endEntity;

	private List<EntityDTO> subjects;

	private EntityTemplateDTO entityTemplateDTO;

	private String entityTemplateName;

	private String globalEntityType;

	private GlobalEntityDTO globalEntityDTO;

	private GlobalEntityTemplateDTO globalEntityTemplateDTO;

	private FieldMapDTO identityDTO;

	private FieldMapDTO fieldMapDTO;

	private List<FieldMapDTO> entityFieldValues;

	private List<FieldMapDTO> entityDataprovider;

	private DomainDTO domainDTO;

	private DomainDTO endUserDomainDTO;

	private String statusName;

	private Map<String, String> token;

	private String endUserDomain;

	private String searchTemplateName;

	private String markerTemplateName;

	private String searchEntityType;

	private List<HierarchyEntity> hierarchies;

	private List<HierarchyEntity> hierarchiesStartEntity;

	private HierarchyDTO hierarchyDTO;

	private HierarchySearchDTO hierarchySearchDTO;

	private HierarchyEntity hierarchyEntity;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);

		statusMessageUpdateError = new StatusMessageErrorDTO();
		statusMessageUpdateError.setField("actor");
		statusMessageUpdateError.setErrorCode("800");

		failureMessageDTO = new StatusMessageDTO();
		failureMessageDTO.setStatus(Status.FAILURE);

		statusMessageErrorSuccess = new StatusMessageErrorDTO();
		statusMessageErrorSuccess.setStatus(Status.SUCCESS);

		entityStatus = new StatusDTO();
		entityStatus.setStatusName("INACTIVE");

		endUserDomain = "pcs.galaxy";

		endUserDomainDTO = new DomainDTO();
		endUserDomainDTO.setDomainName(endUserDomain);

		domainDTO = new DomainDTO();
		domainDTO.setDomainName("alpine.com");

		setEntityTemplateDTO();
		setGlobalEntityDTO();
		setIdentityDTO();
		setGlobalEntityTemplateDTO();
		statusName = Status.ACTIVE.getStatus();

		searchTemplateName = "AvocadoPointTemplate";
		markerTemplateName = "SaffronDeviceTemplate";
		searchEntityType = globalEntityType;

		setActor();
		setSubjects();
		setHierarchyDTO();

		setSearchActor();
		setParentIdentity();
		setHierarchySearchDTO();

		setFieldMapDTO();
		entityFieldValues = new ArrayList<FieldMapDTO>();
		entityDataprovider = new ArrayList<FieldMapDTO>();
		entityFieldValues.add(fieldMapDTO);
		entityDataprovider.add(fieldMapDTO);

		setEntityDTO();
		setHierarchyEntity();
		hierarchies = new ArrayList<HierarchyEntity>();
		hierarchies.add(hierarchyEntity);

		setTenantRangeEntities();

		setHierarchiesStartEntity();

		subscription = new Subscription();
		subscription.setEndUserDomain(endUserDomain);

		token = ImmutableMap
				.<String, String> builder()
				.put("x-jwt-assertion",
						"ll.eyJpc3MiOiJ3c28yLm9yZy9wcm9kdWN0cy9hbSIsImV4cCI6MTQ0MzUwNTQ4OTQzOSwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9zdWJzY3JpYmVyIjoiYXZvY2FkbyIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb25pZCI6IjI3IiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbm5hbWUiOiJhdm9jYWRvIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2FwaWNvbnRleHQiOiIvYWxwaW5ldXNlciIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdmVyc2lvbiI6IjEuMC4wIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy90aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9lbmR1c2VyIjoicGNzYWRtaW5AcGNzLmNvbSIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW5kdXNlclRlbmFudElkIjoiMSIsInN1YnNjcmliZXJEb21haW4iOiJhbHBpbmUuY29tIiwiZW5kVXNlckRvbWFpbiI6InBjcy5hbHBpbmUuY29tIiwic3Vic2NyaWJlck5hbWUiOiJhdm9jYWRvIiwiZW5kVXNlck5hbWUiOiJwY3NhZG1pbiIsIkNvbnN1bWVyS2V5IjoiNkt5djExRkV6a09SM0Y1ejQwcUEzelVCUWJRYSIsInN1YnNjcmliZXJBcHAiOiJhdm9jYWRvIn0=.ll")
				.build();

		hierarchyDTOBuilder = new HierarchyDTOBuilder();
		hierarchySearchDTOBuilder = new HierarchySearchDTOBuilder();
		entityAssignDTOBuilder = new EntityAssignDTOBuilder();
		entityRangeDTOBuilder = new EntityRangeDTOBuilder();
		entityDTOBuilder = new EntityDTOBuilder();
	}

	private void setParentIdentity() {
		parentIdentity = new IdentityDTO();
		parentIdentity.setDomain(domainDTO);
		parentIdentity.setEntityTemplate(entityTemplateDTO);
		parentIdentity.setGlobalEntity(globalEntityDTO);
		parentIdentity.setIdentifier(identityDTO);
	}

	private void setGlobalEntityTemplateDTO() {
		globalEntityTemplateDTO = new GlobalEntityTemplateDTO();
		globalEntityTemplateDTO.setGlobalEntityTemplateName("DefaultTenant");
	}

	private void setIdentityDTO() {
		identityDTO = new FieldMapDTO();
		identityDTO.setKey("tenantId");
		identityDTO.setValue("Chocos05");
	}

	private void setGlobalEntityDTO() {
		globalEntityDTO = new GlobalEntityDTO();
		globalEntityType = "TENANT";
		globalEntityDTO.setGlobalEntityType(globalEntityType);
		globalEntityDTO.setIsDefault(true);
	}

	private void setEntityTemplateDTO() {
		entityTemplateDTO = new EntityTemplateDTO();
		entityTemplateName = "DefaultTenant";
		entityTemplateDTO.setEntityTemplateName(entityTemplateName);
	}

	private void setTenantRangeEntities() {
		startEntity = new IdentityDTO();
		endEntity = new IdentityDTO();
		FieldMapDTO endEntityIdentifier = new FieldMapDTO();
		FieldMapDTO startEntityIdentifier = new FieldMapDTO();
		startEntityIdentifier.setKey("tenantId");
		startEntityIdentifier.setValue("srsinfotech");
		startEntity.setDomain(endUserDomainDTO);
		startEntity.setEntityTemplate(entityTemplateDTO);
		startEntity.setGlobalEntity(globalEntityDTO);
		startEntity.setIdentifier(startEntityIdentifier);
		endEntity.setDomain(endUserDomainDTO);
		endEntity.setEntityTemplate(entityTemplateDTO);
		endEntity.setGlobalEntity(globalEntityDTO);
		endEntityIdentifier.setKey("tenantId");
		endEntityIdentifier.setValue("srsclient01");
		endEntity.setIdentifier(endEntityIdentifier);
	}

	private void setHierarchiesStartEntity() {
		hierarchiesStartEntity = new ArrayList<HierarchyEntity>();
		HierarchyEntity hierarchyEntity = new HierarchyEntity();
		hierarchyEntity.setDomain(startEntity.getDomain().getDomainName());
		hierarchyEntity.setEntityType(startEntity.getGlobalEntity()
				.getGlobalEntityType());
		hierarchyEntity.setIdentifierKey(startEntity.getIdentifier().getKey());
		hierarchyEntity.setIdentifierValue(startEntity.getIdentifier()
				.getValue());
		hierarchyEntity.setTemplateName(startEntity.getEntityTemplate()
				.getEntityTemplateName());
		hierarchiesStartEntity.add(hierarchyEntity);
	}

	private void setFieldMapDTO() {
		fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey("dataType");
		fieldMapDTO.setValue("DOUBLE");
	}

	private void setHierarchyEntity() {
		hierarchyEntity = new HierarchyEntity();
		hierarchyEntity.setIdentifierKey(identityDTO.getKey());
		hierarchyEntity.setIdentifierValue(identityDTO.getValue());
		hierarchyEntity.setDomain(endUserDomain);
		hierarchyEntity.setEntityType(globalEntityType);
		hierarchyEntity.setTemplateName(entityTemplateName);
	}

	private void setHierarchyDTO() {
		hierarchyDTO = new HierarchyDTO();
		hierarchyDTO.setActor(actor);
		hierarchyDTO.setSubjects(subjects);
	}

	private void setSubjects() {
		subjects = new ArrayList<EntityDTO>();
		subjects.add(actor);
		subjects.add(actor);
	}

	private void setActor() {
		actor = new EntityDTO();
		actor.setDomain(domainDTO);
		actor.setEntityTemplate(entityTemplateDTO);
		actor.setGlobalEntity(globalEntityDTO);
		actor.setIdentifier(identityDTO);
	}

	private void setEntityDTO() {
		entityDTO = new EntityDTO();
		entityDTO.setDomain(domainDTO);
		entityDTO.setEntityTemplate(entityTemplateDTO);
		entityDTO.setGlobalEntity(globalEntityDTO);
		entityDTO.setIdentifier(identityDTO);
		entityDTO.setDataprovider(entityDataprovider);
		entityDTO.setFieldValues(entityFieldValues);
		entityDTO.setEntityStatus(entityStatus);
	}

	private void setHierarchySearchDTO() {
		hierarchySearchDTO = new HierarchySearchDTO();
		hierarchySearchDTO.setActor(searchActor);
		hierarchySearchDTO.setParentIdentity(parentIdentity);
		hierarchySearchDTO.setSearchEntityType(globalEntityType);
		hierarchySearchDTO.setSearchTemplateName(entityTemplateName);
		hierarchySearchDTO.setStatusName(statusName);
	}

	private void setSearchActor() {
		searchActor = new IdentityDTO();
		searchActor.setDomain(domainDTO);
		searchActor.setEntityTemplate(entityTemplateDTO);
		searchActor.setGlobalEntity(globalEntityDTO);
		searchActor.setIdentifier(identityDTO);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachChildrenActorNull() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(null, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachChildren(input);
		hierarchyServiceImpl.attachChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachChildrenActorEmpty() {
		EntityDTO actor = new EntityDTO();
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachChildren(input);
		hierarchyServiceImpl.attachChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachChildrenActorIdentifierKeyEmpty() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue("Chocos05");
		actor.setIdentifier(identifierKeyEmpty);
		HierarchyDTO input1 = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachChildren(input1);
		hierarchyServiceImpl.attachChildren(input1);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachChildrenActorIdentifierValueEmpty() {
		FieldMapDTO identifierValueEmpty = new FieldMapDTO();
		identifierValueEmpty.setKey("tenantId");
		actor.setIdentifier(identifierValueEmpty);
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachChildren(input);
		hierarchyServiceImpl.attachChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachChildrenSubjectsNull() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, null);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachChildren(input);
		hierarchyServiceImpl.attachChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachChildrenSubjectsEmpty() {
		List<EntityDTO> subjects = new ArrayList<EntityDTO>();
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachChildren(input);
		hierarchyServiceImpl.attachChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachChildrenFailure() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(null, null);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachChildren(input);
		hierarchyServiceImpl.attachChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachChildren(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test
	public void testAttachChildrenSuccess() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.when(hierarchyRepository.isTenantExist(Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(
				(hierarchyRepository).getParents(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		Mockito.when(
				(hierarchyRepository).getTenantDomainName(Mockito
						.any(EntityDTO.class))).thenReturn(endUserDomain);
		HashSet<String> childrenSet = new HashSet<String>();
		childrenSet.add("test");
		Mockito.when(
				(hierarchyRepository).getChildrenOfEntity(Mockito.anyString(),
						Mockito.any(HashSet.class))).thenReturn(childrenSet);

		assertEquals(Status.SUCCESS, hierarchyServiceImpl.attachChildren(input)
				.getStatus());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachParentActorNull() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(null, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachParents(input);
		hierarchyServiceImpl.attachParents(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachParentActorEmpty() {
		EntityDTO actor = new EntityDTO();
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachParents(input);
		hierarchyServiceImpl.attachParents(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachParentActorIdentifierKeyEmpty() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue("Chocos05");
		actor.setIdentifier(identifierKeyEmpty);
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachParents(input);
		hierarchyServiceImpl.attachParents(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachParentActorIdentifierValueEmpty() {
		FieldMapDTO identifierValueEmpty = new FieldMapDTO();
		identifierValueEmpty.setKey("tenantId");
		actor.setIdentifier(identifierValueEmpty);
		HierarchyDTO input1 = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachParents(input1);
		hierarchyServiceImpl.attachParents(input1);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachParentSubjectsNull() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, null);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachParents(input);
		hierarchyServiceImpl.attachParents(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachParentSubjectsEmpty() {
		List<EntityDTO> subjects = new ArrayList<EntityDTO>();
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachParents(input);
		hierarchyServiceImpl.attachParents(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testAttachParentFailure() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(null, null);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.attachParents(input);
		hierarchyServiceImpl.attachParents(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#attachParent(com.pcs.alpine.dto.HierarchyDTO)}
	 * .
	 */
	@Test
	public void testAttachParentSuccess() {
		HierarchyDTO input = hierarchyDTOBuilder.aHierachyDTO(actor, subjects);
		Mockito.when(hierarchyRepository.isTenantExist(Mockito.anyString()))
				.thenReturn(true);

		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<String> tenantDomains = new ArrayList<String>();
		tenantDomains.add("test");
		tenantDomains.add("test");
		Mockito.when(
				(hierarchyRepository).getTenantsDomainNames(Mockito
						.anyListOf(EntityDTO.class))).thenReturn(tenantDomains);
		HashSet<String> childrenSet = new HashSet<String>();
		childrenSet.add("test");
		Mockito.when(
				(hierarchyRepository).getParentsOfEntity(Mockito.anyString(),
						Mockito.any(HashSet.class))).thenReturn(childrenSet);

		assertEquals(Status.SUCCESS, hierarchyServiceImpl.attachParents(input)
				.getStatus());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchEntityTemplateNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, null, globalEntityType,
						statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchEntityTemplateEmpty() {
		String entityTemplateName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.doThrow(new GalaxyException())
				.when(globalEntityTemplateService)
				.getGlobalEntityTemplate(Mockito.anyString());
		Mockito.when((statusRepository).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchGlobalEntityTypeNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName, null,
						statusName);
		Mockito.doThrow(new GalaxyException()).when(globalEntityService)
				.getGlobalEntityWithName(Mockito.anyString());
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchGlobalEntityTypeEmpty() {
		String globalEntityType = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.doThrow(new GalaxyException()).when(globalEntityService)
				.getGlobalEntityWithName(Mockito.anyString());
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentStatusNameNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, null);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.doThrow(new GalaxyException()).when(statusService)
				.getStatus(Mockito.anyString());
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentStatusNameEmpty() {
		String statusName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.doThrow(new GalaxyException()).when(statusService)
				.getStatus(Mockito.anyString());
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchActorNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(null, entityTemplateName, globalEntityType,
						statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchActorEmpty() {
		IdentityDTO searchActor = new IdentityDTO();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchActorIdentifierKeyEmpty() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue("Chocos05");
		searchActor.setIdentifier(identifierKeyEmpty);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateParentSearchActorIdentifierValueEmpty() {
		FieldMapDTO identifierValueEmpty = new FieldMapDTO();
		identifierValueEmpty.setKey("tenantId");
		searchActor.setIdentifier(identifierValueEmpty);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateParents(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateParent(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetImmediateParentFailure() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getImmediateParents(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getImmediateParent(input);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(null);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateParent(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */

	@Test
	public void testGetImmediateParentSuccess() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(entityDTO);
		Mockito.when(
				(hierarchyRepository).getImmediateParents(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(entityDTO);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getImmediateParent(input);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchEntityTemplateNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, null, globalEntityType,
						statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchEntityTemplateEmpty() {
		String entityTemplateName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.doThrow(new GalaxyException())
				.when(globalEntityTemplateService)
				.getGlobalEntityTemplate(Mockito.anyString());
		Mockito.when((statusRepository).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchGlobalEntityTypeNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName, null,
						statusName);
		Mockito.doThrow(new GalaxyException()).when(globalEntityService)
				.getGlobalEntityWithName(Mockito.anyString());
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchGlobalEntityTypeEmpty() {
		String globalEntityType = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.doThrow(new GalaxyException()).when(globalEntityService)
				.getGlobalEntityWithName(Mockito.anyString());
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenStatusNameNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, null);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.doThrow(new GalaxyException()).when(statusService)
				.getStatus(Mockito.anyString());
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenStatusNameEmpty() {
		String statusName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.doThrow(new GalaxyException()).when(statusService)
				.getStatus(Mockito.anyString());
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchActorNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(null, entityTemplateName, globalEntityType,
						statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchActorEmpty() {
		IdentityDTO searchActor = new IdentityDTO();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchActorIdentifierKeyEmpty() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue("Chocos05");
		searchActor.setIdentifier(identifierKeyEmpty);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetImmediateChildrenSearchActorIdentifierValueEmpty() {
		FieldMapDTO identifierValueEmpty = new FieldMapDTO();
		identifierValueEmpty.setKey("tenantId");
		searchActor.setIdentifier(identifierValueEmpty);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getImmediateChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getImmediateChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetImmediateChildrenFailure() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getImmediateChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getImmediateChildren(input);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(null);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getImmediateChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */

	@Test
	public void testGetImmediateChildrenSuccess() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.aHierachySearchDTO(searchActor, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(entityDTO);
		Mockito.when(
				(hierarchyRepository).getImmediateChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(entityDTO);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getImmediateChildren(input);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainMarkerTemplateNameNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				searchActor, null, searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainMarkerTemplateNameEmpty() {
		String markerTemplateName = new String();
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainSearchTemplateNameNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				searchActor, markerTemplateName, null, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainSearchTemplateNameEmpty() {
		String searchTemplateName = new String();
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainStatusNameNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				searchActor, markerTemplateName, searchTemplateName, null);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainActorNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(null,
				markerTemplateName, searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainActorEmpty() {
		IdentityDTO identifier = new IdentityDTO();
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				identifier, markerTemplateName, searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainActorIdentifierKeyEmpty() {
		IdentityDTO identifierKeyEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setValue("sub001sanityclient002");
		identifierKeyEmpty.setIdentifier(fieldMapDTO);
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				identifierKeyEmpty, markerTemplateName, searchTemplateName,
				statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	@Test(expected = GalaxyException.class)
	public void testGetAllOwnedMarkersByDomainActorIdentifierValueEmpty() {
		IdentityDTO identifierValueEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey("tenantId");
		identifierValueEmpty.setIdentifier(fieldMapDTO);
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				identifierValueEmpty, markerTemplateName, searchTemplateName,
				statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAllOwnedMarkersByDomain(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAllOwnedMarkersByDomain(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test
	public void testGetAllOwnedMarkersByDomainFailure() {
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getAllOwnedMarkersByDomain(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getAllOwnedMarkersByDomain(input);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(null);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test
	public void testGetAllOwnedMarkersByDomainSuccess() {
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(3);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(entityDTO);
		Mockito.when(
				(hierarchyRepository).getAllOwnedMarkersByDomain(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(entityDTO);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getAllOwnedMarkersByDomain(input);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeStartEntityEmpty() {
		IdentityDTO startEntityEmpty = new IdentityDTO();
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startEntityEmpty, endEntity);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeEndEntityNull() {
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startEntity, null);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeEndEntityEmpty() {
		IdentityDTO endEntityEmpty = new IdentityDTO();
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startEntity, endEntityEmpty);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeStartIdentifierKeyEmpty() {
		IdentityDTO startIdentifierKeyEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setValue("srsinfotech");
		startIdentifierKeyEmpty.setIdentifier(fieldMapDTO);
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startIdentifierKeyEmpty, endEntity);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeStartIdentifierValueEmpty() {
		IdentityDTO startIdentifierValueEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey("tenantId");
		startIdentifierValueEmpty.setIdentifier(fieldMapDTO);
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startIdentifierValueEmpty, endEntity);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeEndIdentifierKeyEmpty() {
		IdentityDTO endIdentifierKeyEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setValue("srsinfotech");
		endIdentifierKeyEmpty.setIdentifier(fieldMapDTO);
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startEntity, endIdentifierKeyEmpty);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeEndIdentifierValueEmpty() {
		IdentityDTO endIdentifierValueEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey("tenantId");
		endIdentifierValueEmpty.setIdentifier(fieldMapDTO);
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				endIdentifierValueEmpty, endEntity);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeFailure() {
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startEntity, endEntity);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		Mockito.doThrow(new NoResultException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		hierarchyServiceImpl.getTenantsWithinRange(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getTenantsWithinRange(com.pcs.alpine.dto.EntityRangeDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetTenantsWithinRangeSuccess() {
		EntityRangeDTO input = entityRangeDTOBuilder.aEntityRangeDTO(
				startEntity, endEntity);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when((hierarchyRepository).getTenantNode(Mockito.anyString()))
				.thenReturn(hierarchyEntity);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchiesStartEntity);
		// Mockito.when(
		// (hierarchyRepository).getTenantsWithinRange(
		// Mockito.any(HierarchyEntity.class),
		// Mockito.any(HierarchyEntity.class))).thenReturn(
		// hierarchies);
		Mockito.doThrow(new NoResultException())
				.when(hierarchyRepository)
				.getTenantsWithinRange(Mockito.any(HierarchyEntity.class),
						Mockito.any(HierarchyEntity.class));
		List<IdentityDTO> actualResult = hierarchyServiceImpl
				.getTenantsWithinRange(input);
		// List<IdentityDTO> expectedResult = new ArrayList<IdentityDTO>();
		// searchActor.setDomain(endUserDomainDTO);
		// GlobalEntityDTO globalEntityDTO = new GlobalEntityDTO();
		// globalEntityDTO.setGlobalEntityType("TENANT");
		// globalEntityDTO.setIsDefault(null);
		// searchActor.setGlobalEntity(globalEntityDTO);
		// expectedResult.add(searchActor);
		// assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetCountByStatusDomainNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(null, entityTemplateName, globalEntityType,
						statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getCountByStatus(Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getCountByStatus(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetCountByStatusDomainEmpty() {
		String endUserDomain = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when(
				(hierarchyRepository).getCountByStatus(Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString())).thenReturn(1);
		HierarchyReturnDTO actualResult = hierarchyServiceImpl
				.getCountByStatus(input);
		HierarchyReturnDTO expectedResult = new HierarchyReturnDTO();
		expectedResult.setCount(1);
		assertEquals(expectedResult.getCount(), actualResult.getCount());

	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetCountByStatusEntityTemplateNameNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, null, globalEntityType,
						statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getCountByStatus(Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getCountByStatus(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetCountByStatusEntityTemplateNameEmpty() {
		String entityTemplateName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getCountByStatus(Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getCountByStatus(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetCountByStatusGlobalEntityTypeNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName, null,
						statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getCountByStatus(Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getCountByStatus(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetCountByStatusGlobalEntityTypeEmpty() {
		String globalEntityType = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getCountByStatus(Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getCountByStatus(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetCountByStatusStatusNameNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName,
						globalEntityType, null);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getCountByStatus(Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getCountByStatus(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetCountByStatusStatusNameEmpty() {
		String statusName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getCountByStatus(Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getCountByStatus(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetCountByStatusFailure() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when(
				(hierarchyRepository).getCountByStatus(Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString())).thenReturn(null);
		HierarchyReturnDTO actualHierarchyReturnDTO = hierarchyServiceImpl
				.getCountByStatus(input);
		HierarchyReturnDTO expectedHierarchyReturnDTO = new HierarchyReturnDTO();
		Integer count = null;
		expectedHierarchyReturnDTO.setCount(count);
		assertEquals(expectedHierarchyReturnDTO.getCount(),
				actualHierarchyReturnDTO.getCount());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getCountByStatus(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetCountByStatusSuccess() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.bHierachySearchDTO(endUserDomain, entityTemplateName,
						globalEntityType, statusName);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);
		Mockito.when(
				(hierarchyRepository).getCountByStatus(Mockito.anyString(),
						Mockito.anyString(), Mockito.anyString(),
						Mockito.anyString())).thenReturn(2);
		HierarchyReturnDTO actualHierarchyReturnDTO = hierarchyServiceImpl
				.getCountByStatus(input);
		HierarchyReturnDTO expectedHierarchyReturnDTO = new HierarchyReturnDTO();
		Integer count = 2;
		expectedHierarchyReturnDTO.setCount(count);
		assertEquals(expectedHierarchyReturnDTO.getCount(),
				actualHierarchyReturnDTO.getCount());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAssignableMarkers(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersMarkerTemplateNameNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				searchActor, null, searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAssignableMarkers(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersMarkerTemplateNameEmpty() {
		String markerTemplateName = new String();
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersSearchTemplateNameNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				searchActor, markerTemplateName, null, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersSearchTemplateNameEmpty() {
		String searchTemplateName = new String();
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersStatusNameNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				searchActor, markerTemplateName, searchTemplateName, null);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAllOwnedMarkersByDomain(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersActorNull() {
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(null,
				markerTemplateName, searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersActorEmpty() {
		IdentityDTO identifier = new IdentityDTO();
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				identifier, markerTemplateName, searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersActorIdentifierKeyEmpty() {
		IdentityDTO identifierKeyEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setValue("sub001sanityclient002");
		identifierKeyEmpty.setIdentifier(fieldMapDTO);
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				identifierKeyEmpty, markerTemplateName, searchTemplateName,
				statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	@Test(expected = GalaxyException.class)
	public void testGetAssignableMarkersActorIdentifierValueEmpty() {
		IdentityDTO identifierValueEmpty = new IdentityDTO();
		FieldMapDTO fieldMapDTO = new FieldMapDTO();
		fieldMapDTO.setKey("tenantId");
		identifierValueEmpty.setIdentifier(fieldMapDTO);
		EntityAssignDTO input = entityAssignDTOBuilder.aEntityAssignDTO(
				identifierValueEmpty, markerTemplateName, searchTemplateName,
				statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(actor);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getAssignableMarkers(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getAssignableMarkers(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAssignableMarkers(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test
	public void testGetAssignableMarkersFailure() {
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getAssignableMarkers(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getAssignableMarkers(input);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(null);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getAssignableMarkers(com.pcs.alpine.dto.EntityAssignDTO)}
	 * .
	 */
	@Test
	public void testGetAssignableMarkersSuccess() {
		EntityAssignDTO input = entityAssignDTOBuilder
				.aEntityAssignDTO(searchActor, markerTemplateName,
						searchTemplateName, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(3);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(entityDTO);
		Mockito.when(
				(hierarchyRepository).getAssignableMarkers(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(entityDTO);
		List<EntityDTO> actualResult = hierarchyServiceImpl
				.getAssignableMarkers(input);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityTemplateDTONull() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO, null,
				globalEntityDTO, identityDTO, entityDataprovider,
				entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityTemplateDTOEmpty() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeGlobalEntityDTONull() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, null, identityDTO, entityDataprovider,
				entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeGlobalEntityDTOEmpty() {
		GlobalEntityDTO globalEntityDTO = new GlobalEntityDTO();
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeIdentityDTONull() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, null, entityDataprovider,
				entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeIdentityDTOKeyEmpty() {
		FieldMapDTO identityDTOKeyEmpty = new FieldMapDTO();
		identityDTOKeyEmpty.setValue("Chocos05");
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeIdentityDTOValueEmpty() {
		FieldMapDTO identityDTOValueEmpty = new FieldMapDTO();
		identityDTOValueEmpty.setKey("tenantId");
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTOValueEmpty,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityDataproviderNull() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO, null,
				entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityDataproviderEmpty() {
		List<FieldMapDTO> entityDataprovider = new ArrayList<FieldMapDTO>();
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityFieldValuesNull() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, null, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityFieldValuesEmpty() {
		List<FieldMapDTO> entityFieldValues = new ArrayList<FieldMapDTO>();
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityStatusNull() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, null);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityStatusEmpty() {
		StatusDTO entityStatus = new StatusDTO();
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateNodeEntityStatusInvalid() {
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusName("INVALID");
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.doThrow(new GalaxyException()).when(hierarchyRepository)
				.updateNode(Mockito.any(EntityDTO.class));
		hierarchyServiceImpl.updateNode(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test
	public void testUpdateNodeFailure() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.when(
				(hierarchyRepository).updateNode(Mockito.any(EntityDTO.class)))
				.thenReturn(null);
		EntityDTO actualEntityDTO = hierarchyServiceImpl.updateNode(input);
		assertEquals(null, actualEntityDTO);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#updateNode(com.pcs.alpine.dto.EntityDTO)}
	 * .
	 */
	@Test
	public void testUpdateNodeSuccess() {
		EntityDTO input = entityDTOBuilder.aEntityDTO(domainDTO,
				entityTemplateDTO, globalEntityDTO, identityDTO,
				entityDataprovider, entityFieldValues, entityStatus);
		Mockito.when(
				(hierarchyRepository).updateNode(Mockito.any(EntityDTO.class)))
				.thenReturn(entityDTO);
		EntityDTO actualEntityDTO = hierarchyServiceImpl.updateNode(input);
		assertEquals(entityDTO, actualEntityDTO);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenParentIdentityNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(null, searchTemplateName, searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenParentIdentityIdKeyNull() {
		identityDTO.setKey(null);
		parentIdentity.setIdentifier(identityDTO);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenParentIdentityIdValueNull() {
		identityDTO.setValue(null);
		parentIdentity.setIdentifier(identityDTO);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenParentIdentityIdKeyEmpty() {
		identityDTO.setKey(new String());
		parentIdentity.setIdentifier(identityDTO);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenParentIdentityIdValueEmpty() {
		identityDTO.setValue(new String());
		parentIdentity.setIdentifier(identityDTO);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenSearchTemplateNameNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, null, searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenSearchTemplateNameEmpty() {
		searchTemplateName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenSearchEntityTypeNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName, null);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildrenSearchEntityTypeEmpty() {
		searchEntityType = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildren(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildren(input);
		hierarchyServiceImpl.getChildren(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetChildrenFailure() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(null);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(null);
		List<EntityDTO> actualResult = hierarchyServiceImpl.getChildren(input);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildren(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetChildrenSuccess() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.cHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(coreEntityService).getEntityDataProvider(Mockito
						.any(IdentityDTO.class))).thenReturn(entityDTO);
		Mockito.when(
				(hierarchyRepository).getChildren(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				hierarchies);
		List<EntityDTO> expectedResult = new ArrayList<EntityDTO>();
		expectedResult.add(entityDTO);
		List<EntityDTO> actualResult = hierarchyServiceImpl.getChildren(input);
		assertEquals(expectedResult, actualResult);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountParentIdentityNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(null, searchTemplateName, searchEntityType,
						statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getChildrenCount(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString()))
				.thenReturn(2);
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountParentIdentityIdKeyEmpty() {
		identityDTO.setKey(new String());
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getChildrenCount(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString()))
				.thenReturn(2);
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountParentIdentityIdKeyNull() {
		identityDTO.setKey(null);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getChildrenCount(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString()))
				.thenReturn(2);
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountParentIdentityIdValueEmpty() {
		identityDTO.setValue(new String());
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getChildrenCount(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString()))
				.thenReturn(2);
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountParentIdentityIdValueNull() {
		identityDTO.setValue(null);
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getChildrenCount(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString()))
				.thenReturn(2);
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountSearchTemplateNameNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, null, searchEntityType,
						statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildrenCount(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountSearchTemplateNameEmpty() {
		searchTemplateName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildrenCount(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountSearchEntityTypeNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName, null,
						statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildrenCount(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountSearchEntityTypeEmpty() {
		searchEntityType = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildrenCount(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountStatusNameEmpty() {
		statusName = new String();
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildrenCount(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testGetChildEntityTypeCountStatusNameNull() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, null);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.doThrow(new GalaxyException())
				.when(hierarchyRepository)
				.getChildrenCount(Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString());
		hierarchyServiceImpl.getChildEntityTypeCount(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetChildEntityTypeCountFailure() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getChildrenCount(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				null);
		HierarchyReturnDTO actualResult = hierarchyServiceImpl
				.getChildEntityTypeCount(input);
		HierarchyReturnDTO expectedResult = new HierarchyReturnDTO();
		expectedResult.setCount(null);
		assertEquals(expectedResult.getCount(), actualResult.getCount());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceImpl.HierarchyServiceImpl#getChildEntityTypeCount(com.pcs.alpine.dto.HierarchySearchDTO)}
	 * .
	 */
	@Test
	public void testGetChildEntityTypeCountSuccess() {
		HierarchySearchDTO input = hierarchySearchDTOBuilder
				.dHierachySearchDTO(parentIdentity, searchTemplateName,
						searchEntityType, statusName);
		Mockito.when(
				(globalEntityService).getGlobalEntityWithName(Mockito
						.anyString())).thenReturn(globalEntityDTO);
		Mockito.when(
				(globalEntityTemplateService).getGlobalEntityTemplate(Mockito
						.anyString())).thenReturn(globalEntityTemplateDTO);
		Mockito.when((statusService).getStatus(Mockito.anyString()))
				.thenReturn(0);
		Mockito.when(
				(hierarchyRepository).getChildrenCount(
						Mockito.any(HierarchyEntity.class),
						Mockito.anyString(), Mockito.anyString()))
				.thenReturn(2);
		HierarchyReturnDTO actualResult = hierarchyServiceImpl
				.getChildEntityTypeCount(input);
		HierarchyReturnDTO expectedResult = new HierarchyReturnDTO();
		expectedResult.setCount(2);
		assertEquals(expectedResult.getCount(), actualResult.getCount());
	}

}
