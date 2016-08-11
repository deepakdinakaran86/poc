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

import static com.pcs.alpine.enums.GeoDataFields.LATITUDE;
import static com.pcs.alpine.enums.GeoDataFields.LONGITUDE;
import static com.pcs.alpine.services.enums.EMDataFields.MARKER;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.dto.CoordinatesDTO;
import com.pcs.alpine.dto.GeoTagSearchDTO;
import com.pcs.alpine.dto.GeoTaggedEntitiesDTO;
import com.pcs.alpine.dto.GeotagDTO;
import com.pcs.alpine.dto.GeotagESBDTO;
import com.pcs.alpine.dto.TagInfoESBDTO;
import com.pcs.alpine.dto.TagsESBDTO;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.repo.utils.CoreEntityUtil;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.service.impl.GeotagServiceImpl;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.HierarchySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.builder.GeotagDTOBuilder;
import com.pcs.alpine.services.dto.builder.IdentityDTOBuilder;

/**
 * 
 * This class is responsible for GeofenceServiceImplTest
 * 
 * @author Daniela PCSEG191)
 * @date 11 Apr 2016
 * @since alpine-1.0.0
 */
@ContextConfiguration("classpath:alpine-geo-services-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GeotagServiceImplTest {

	@InjectMocks
	private GeotagServiceImpl sut;

	@Mock
	private BaseClient platformClient;

	@Mock
	private SubscriptionProfileService subscriptionProfileService;

	@Mock
	CoreEntityUtil coreEntityUtil;

	private IdentityDTOBuilder identityDTOBuilder;

	private GeotagDTOBuilder geotagDTOBuilder;

	private FieldMapDTO identityMap;

	private DomainDTO domainDTO;

	private EntityTemplateDTO entityTemplate;

	private PlatformEntityDTO globalEntity;

	private List<EntityDTO> entities;

	private EntityDTO geoTag = new EntityDTO();

	private List<FieldMapDTO> geoFields = new ArrayList<FieldMapDTO>();

	private List<FieldMapDTO> taggedEntityFields = new ArrayList<FieldMapDTO>();

	private FieldMapDTO latitudeMap = new FieldMapDTO();

	private FieldMapDTO longitudeMap = new FieldMapDTO();

	private Map<String, String> token;

	private TagsESBDTO geoTagsESBDTO;

	private TagInfoESBDTO tagInfoESBDTO;

	private List<TagInfoESBDTO> tagInfoList;

	private EntityDTO entity;

	private FieldMapDTO assetId;

	private CoordinatesDTO geotagCoordinates;

	private StatusMessageErrorDTO statusMessageError;

	private String latitude;

	private String longitude;

	private String errorCode;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		identityDTOBuilder = new IdentityDTOBuilder();
		identityMap = new FieldMapDTO();

		identityMap = new FieldMapDTO();
		identityMap.setKey("identifier");
		identityMap.setValue("e2684346-cfbc-4056-a330-c71188adf473");

		domainDTO = new DomainDTO();
		domainDTO.setDomainName("pcs.galaxy");

		entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName("PUMP");

		globalEntity = new PlatformEntityDTO();
		globalEntity.setPlatformEntityType(MARKER.getFieldName());

		geoTag.setDomain(domainDTO);

		latitudeMap.setKey(LATITUDE.getFieldName());
		latitudeMap.setValue("22.55");

		longitudeMap.setKey(LONGITUDE.getFieldName());
		longitudeMap.setValue("55.63");

		geoFields.add(latitudeMap);
		geoFields.add(longitudeMap);
		geoTag.setDataprovider(geoFields);

		entities = new ArrayList<EntityDTO>();
		entities.add(geoTag);

		token = ImmutableMap
				.<String, String> builder()
				.put("x-jwt-assertion",
						"ll.eyJpc3MiOiJ3c28yLm9yZy9wcm9kdWN0cy9hbSIsImV4cCI6MTQ0MzUwNTQ4OTQzOSwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9zdWJzY3JpYmVyIjoiYXZvY2FkbyIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb25pZCI6IjI3IiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbm5hbWUiOiJhdm9jYWRvIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2FwaWNvbnRleHQiOiIvYWxwaW5ldXNlciIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdmVyc2lvbiI6IjEuMC4wIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy90aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9lbmR1c2VyIjoicGNzYWRtaW5AcGNzLmNvbSIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW5kdXNlclRlbmFudElkIjoiMSIsInN1YnNjcmliZXJEb21haW4iOiJhbHBpbmUuY29tIiwiZW5kVXNlckRvbWFpbiI6InBjcy5hbHBpbmUuY29tIiwic3Vic2NyaWJlck5hbWUiOiJhdm9jYWRvIiwiZW5kVXNlck5hbWUiOiJwY3NhZG1pbiIsIkNvbnN1bWVyS2V5IjoiNkt5djExRkV6a09SM0Y1ejQwcUEzelVCUWJRYSIsInN1YnNjcmliZXJBcHAiOiJhdm9jYWRvIn0=.ll")
				.build();

		taggedEntityFields = new ArrayList<FieldMapDTO>();

		entity = new EntityDTO();
		entity.setDomain(domainDTO);

		assetId = new FieldMapDTO();
		assetId.setKey("assetId");
		assetId.setValue("K890");
		taggedEntityFields.add(assetId);

		entity.setDataprovider(taggedEntityFields);
		entity.setEntityTemplate(entityTemplate);

		tagInfoESBDTO = new TagInfoESBDTO();
		tagInfoESBDTO.setEntity(entity);
		tagInfoESBDTO.setGeoTag(geoTag);

		geoTagsESBDTO = new TagsESBDTO();
		tagInfoList = new ArrayList<TagInfoESBDTO>();
		tagInfoList.add(tagInfoESBDTO);
		geoTagsESBDTO.setTagInfo(tagInfoList);

		latitude = latitudeMap.getValue();
		longitude = longitudeMap.getValue();

		geotagDTOBuilder = new GeotagDTOBuilder();

		geotagCoordinates = new CoordinatesDTO();
		geotagCoordinates.setLatitude(latitude);
		geotagCoordinates.setLongitude(longitude);

		errorCode = "802";
		statusMessageError = new StatusMessageErrorDTO();
		statusMessageError.setErrorCode(errorCode);
		statusMessageError.setStatus(Status.SUCCESS);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#findGeotag(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindGeotagEmptyIdentifierKey() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue(identityMap.getKey());
		IdentityDTO input = identityDTOBuilder.aIdentity(identifierKeyEmpty,
				domainDTO, entityTemplate);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(HierarchySearchDTO.class),
						(Class<List<EntityDTO>>) Mockito.any(),
						(Class<EntityDTO>) Mockito.any());

		sut.findGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#findGeotag(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindGeotagEmptyIdentifierValue() {
		FieldMapDTO identifierKeyValue = new FieldMapDTO();
		identifierKeyValue.setKey(identityMap.getValue());
		IdentityDTO input = identityDTOBuilder.aIdentity(identifierKeyValue,
				domainDTO, entityTemplate);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(HierarchySearchDTO.class),
						(Class<List<EntityDTO>>) Mockito.any(),
						(Class<EntityDTO>) Mockito.any());
		sut.findGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#findGeotag(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindGeotagEmptyTemplate() {
		EntityTemplateDTO entityTemplateDTO = new EntityTemplateDTO();
		IdentityDTO input = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplateDTO);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		sut.findGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#findGeotag(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindGeotagNoData() {
		IdentityDTO input = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);

		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(HierarchySearchDTO.class),
						(Class<List<EntityDTO>>) Mockito.any(),
						(Class<EntityDTO>) Mockito.any());

		sut.findGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#findGeotag(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test
	public void testFindGeotagsSuccess() {
		IdentityDTO input = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);

		Mockito.when(
				platformClient.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(HierarchySearchDTO.class),
						(Class<List<EntityDTO>>) Mockito.any(),
						(Class<EntityDTO>) Mockito.any())).thenReturn(entities);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);

		GeotagDTO geotag = sut.findGeotag(input);
		assertEquals(geotag.getGeotag().getLatitude(), latitudeMap.getValue());
		assertEquals(geotag.getGeotag().getLongitude(), longitudeMap.getValue());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#findAllGeotags(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test
	public void testFindAllGeotagsSuccess() {
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);

		Mockito.when(
				platformClient.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeoTagSearchDTO.class),
						(Class<TagsESBDTO>) Mockito.any())).thenReturn(
				geoTagsESBDTO);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);

		List<GeoTaggedEntitiesDTO> geotag = sut.findAllGeotags(
				domainDTO.getDomainName(),
				entityTemplate.getEntityTemplateName());
		assertEquals(geotag.get(0).getEntityTemplateName(), geoTagsESBDTO
				.getTagInfo().get(0).getEntity().getEntityTemplate()
				.getEntityTemplateName());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#findAllGeotags(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindAllGeotagsFailure() {
		Mockito.when(subscriptionProfileService.getJwtToken())
				.thenReturn(token);

		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeoTagSearchDTO.class),
						(Class<TagsESBDTO>) Mockito.any());

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);

		List<GeoTaggedEntitiesDTO> geotag = sut.findAllGeotags(
				domainDTO.getDomainName(),
				entityTemplate.getEntityTemplateName());
		assertEquals(geotag.get(0).getEntityTemplateName(), geoTagsESBDTO
				.getTagInfo().get(0).getEntity().getEntityTemplate()
				.getEntityTemplateName());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testCreateGeotagEmptyIdentifierKey() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue(identityMap.getKey());
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identifierKeyEmpty,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageErrorDTO>) Mockito.any());

		sut.createGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testCreateGeotagEmptyIdentifierValue() {
		FieldMapDTO identifierValueEmpty = new FieldMapDTO();
		identifierValueEmpty.setKey(identityMap.getValue());
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identifierValueEmpty,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageErrorDTO>) Mockito.any());

		sut.createGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testCreateGeotagEntityTemplateNull() {
		EntityTemplateDTO entityTemplate = null;
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.doThrow(new GalaxyException())
				.when(coreEntityUtil)
				.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class));
		sut.createGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testCreateGeotagEmptyEntityTemplateName() {
		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName("");
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.doThrow(new GalaxyException())
				.when(coreEntityUtil)
				.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class));
		sut.createGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testCreateGeotagEmptyCoordinates() {
		CoordinatesDTO geotagCoordinates = new CoordinatesDTO();
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		sut.createGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testCreateGeotagEmptyLatitudeCoordinates() {
		CoordinatesDTO geotagCoordinates = new CoordinatesDTO();
		geotagCoordinates.setLongitude(longitude);
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		sut.createGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testCreateGeotagEmptyLongitudeCoordinates() {
		CoordinatesDTO geotagCoordinates = new CoordinatesDTO();
		geotagCoordinates.setLatitude(latitude);
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		sut.createGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test
	public void testCreateGeotagFailure() {
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		statusMessageError.setStatus(Status.FAILURE);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.when(
				(platformClient).post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageErrorDTO>) Mockito.any()))
				.thenReturn(statusMessageError);
		StatusMessageDTO statusMessageDTO = sut.createGeotag(input);
		assertEquals(statusMessageError.getStatus(),
				statusMessageDTO.getStatus());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#createGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test
	public void testCreateGeotagSuccess() {
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.when(
				(platformClient).post(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageErrorDTO>) Mockito.any()))
				.thenReturn(statusMessageError);
		StatusMessageDTO statusMessageDTO = sut.createGeotag(input);
		assertEquals(statusMessageError.getStatus(),
				statusMessageDTO.getStatus());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#updateGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateGeotagEmptyIdentifierKey() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue(identityMap.getKey());
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identifierKeyEmpty,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.put(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageErrorDTO>) Mockito.any());

		sut.updateGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#updateGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateGeotagEmptyIdentifierValue() {
		FieldMapDTO identifierValueEmpty = new FieldMapDTO();
		identifierValueEmpty.setKey(identityMap.getValue());
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identifierValueEmpty,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.doThrow(new GalaxyException())
				.when(platformClient)
				.put(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageErrorDTO>) Mockito.any());

		sut.updateGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#updateGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateGeotagEmptyCoordinates() {
		CoordinatesDTO geotagCoordinates = new CoordinatesDTO();
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);

		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		sut.updateGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#updateGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateGeotagEmptyLatitudeCoordinates() {
		CoordinatesDTO geotagCoordinates = new CoordinatesDTO();
		geotagCoordinates.setLongitude(longitude);
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		sut.updateGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#updateGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateGeotagEmptyLongitudeCoordinates() {
		CoordinatesDTO geotagCoordinates = new CoordinatesDTO();
		geotagCoordinates.setLatitude(latitude);
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		sut.updateGeotag(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#updateGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test
	public void testUpdateGeotagFailure() {
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		StatusMessageDTO statusMessageFail = new StatusMessageDTO();
		statusMessageFail.setStatus(Status.FAILURE);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.when(
				(platformClient).put(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageDTO>) Mockito.any())).thenReturn(
				statusMessageFail);
		StatusMessageDTO status = sut.updateGeotag(input);
		assertEquals(statusMessageFail.getStatus(), status.getStatus());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeotagServiceImpl#updateGeotag(com.pcs.alpine.dto.GeotagDTO)}
	 * .
	 */
	@Test
	public void testUpdateGeotagSuccess() {
		IdentityDTO idMap = identityDTOBuilder.aIdentity(identityMap,
				domainDTO, entityTemplate);
		GeotagDTO input = geotagDTOBuilder.aGeotag(idMap, geotagCoordinates);
		StatusMessageDTO statusMessageSuccess = new StatusMessageDTO();
		statusMessageSuccess.setStatus(Status.SUCCESS);
		Mockito.when(
				coreEntityUtil.getGlobalEntityType(Mockito.any(String.class),
						Mockito.any(String.class))).thenReturn(globalEntity);
		Mockito.when(
				(platformClient).put(Mockito.any(String.class),
						Mockito.anyMapOf(String.class, String.class),
						Mockito.any(GeotagESBDTO.class),
						(Class<StatusMessageDTO>) Mockito.any())).thenReturn(
				statusMessageSuccess);
		StatusMessageDTO status = sut.updateGeotag(input);
		assertEquals(statusMessageSuccess.getStatus(), status.getStatus());
	}

}
