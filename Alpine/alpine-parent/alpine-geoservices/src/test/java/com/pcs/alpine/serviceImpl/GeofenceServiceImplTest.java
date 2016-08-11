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

import static com.pcs.alpine.enums.GeoDataFields.CO_ORDINATES;
import static com.pcs.alpine.enums.GeoDataFields.GEOFENCE_NAME;
import static com.pcs.alpine.services.enums.EMDataFields.IDENTIFIER;
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
import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.service.SubscriptionProfileService;
import com.pcs.alpine.dto.GeofenceDTO;
import com.pcs.alpine.enums.Status;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.service.impl.GeofenceServiceImpl;
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.PlatformEntityDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.services.dto.builder.GeofenceDTOBuilder;
import com.pcs.alpine.services.dto.builder.IdentityDTOBuilder;

/**
 * 
 * This class is responsible for GeofenceServiceImplTest
 * 
 * @author Daniela PCSEG191)
 * @author Suraj Sugathan (PCSEG362)
 * @date 11 Apr 2016
 * @since alpine-1.0.0
 */
@ContextConfiguration("classpath:alpine-geo-services-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GeofenceServiceImplTest {

	@InjectMocks
	private GeofenceServiceImpl sut;

	@Mock
	private BaseClient platformClient;

	private Subscription subscription;

	@Mock
	private SubscriptionProfileService subscriptionProfileService;

	private GeofenceDTOBuilder geofenceDTOBuilder;

	private IdentityDTOBuilder identityDTOBuilder;

	private String geofenceName;

	private String desc;

	private List<FieldMapDTO> geofenceFields;

	private String type;

	private String status;

	private StatusMessageDTO statusMessageDTO;

	private String endUserDomain;

	private Map<String, String> token;

	private EntityDTO geofenceEntity;

	private IdentityDTO geofenceIdentity;

	private FieldMapDTO identityMap;

	private StatusMessageDTO failureMessageDTO;

	private StatusMessageErrorDTO statusMessageErrorSuccess;

	private StatusMessageErrorDTO statusMessageUpdateError;

	private DomainDTO domainDTO;

	private GeofenceDTO geofenceDto;

	private List<EntityDTO> findAllGeofenceDto;

	private List<FieldMapDTO> geofenceFields1;

	private EntityTemplateDTO entityTemplate;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		geofenceDTOBuilder = new GeofenceDTOBuilder();

		geofenceName = "Gardens";
		desc = "This is the geofence for discrovery gardens";
		type = "Polygon";
		status = "ACTIVE";

		statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(Status.SUCCESS);

		geofenceFields = geofenceDTOBuilder
		        .constructPolygonFields("[{\"latitude\":\"25.15740470846001\",\"longitude\":\"55.25093078613281\"},{\"latitude\":\"25.16828069419319\",\"longitude\":\"55.2890396\"},{\"latitude\":\"25.147460101488445\",\"longitude\":\"55.27153015\"}]");

		endUserDomain = "pcs.galaxy";

		token = ImmutableMap
		        .<String, String> builder()
		        .put("x-jwt-assertion",
		                "ll.eyJpc3MiOiJ3c28yLm9yZy9wcm9kdWN0cy9hbSIsImV4cCI6MTQ0MzUwNTQ4OTQzOSwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9zdWJzY3JpYmVyIjoiYXZvY2FkbyIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvYXBwbGljYXRpb25pZCI6IjI3IiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbm5hbWUiOiJhdm9jYWRvIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOi8vd3NvMi5vcmcvY2xhaW1zL2FwaWNvbnRleHQiOiIvYWxwaW5ldXNlciIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdmVyc2lvbiI6IjEuMC4wIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy90aWVyIjoiVW5saW1pdGVkIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDovL3dzbzIub3JnL2NsYWltcy9lbmR1c2VyIjoicGNzYWRtaW5AcGNzLmNvbSIsImh0dHA6Ly93c28yLm9yZy9jbGFpbXMvZW5kdXNlclRlbmFudElkIjoiMSIsInN1YnNjcmliZXJEb21haW4iOiJhbHBpbmUuY29tIiwiZW5kVXNlckRvbWFpbiI6InBjcy5hbHBpbmUuY29tIiwic3Vic2NyaWJlck5hbWUiOiJhdm9jYWRvIiwiZW5kVXNlck5hbWUiOiJwY3NhZG1pbiIsIkNvbnN1bWVyS2V5IjoiNkt5djExRkV6a09SM0Y1ejQwcUEzelVCUWJRYSIsInN1YnNjcmliZXJBcHAiOiJhdm9jYWRvIn0=.ll")
		        .build();
		geofenceEntity = new EntityDTO();
		identityMap = new FieldMapDTO();
		identityMap.setKey("identifier");
		identityMap.setValue("e2684346-cfbc-4056-a330-c71188adf473");

		geofenceIdentity = new IdentityDTO();
		geofenceIdentity.setIdentifier(identityMap);
		geofenceEntity.setIdentifier(identityMap);

		failureMessageDTO = new StatusMessageDTO();
		failureMessageDTO.setStatus(Status.FAILURE);

		statusMessageErrorSuccess = new StatusMessageErrorDTO();
		statusMessageErrorSuccess.setStatus(Status.SUCCESS);

		domainDTO = new DomainDTO();
		domainDTO.setDomainName(endUserDomain);

		statusMessageUpdateError = new StatusMessageErrorDTO();
		statusMessageUpdateError.setField("geofenceName");
		statusMessageUpdateError.setErrorCode("526");

		subscription = new Subscription();
		subscription.setEndUserDomain(endUserDomain);

		identityDTOBuilder = new IdentityDTOBuilder();

		// find geofence build

		geofenceDto = new GeofenceDTO();
		StatusDTO geofenceStatus = new StatusDTO();
		geofenceStatus.setStatusName(Status.ACTIVE.name());
		geofenceDto.setDesc("desc for village route");

		geofenceFields1 = constructRouteFieldsResponse(
		        "[{\\\"latitude\\\":\\\"25.15740470846001\\\",\\\"longitude\\\":\\\"55.25093078613281\\\",\\\"order\\\":\\\"0\\\"},{\\\"latitude\\\":\\\"25.16828069419319\\\",\\\"longitude\\\":\\\"55.2890396\\\",\\\"order\\\":\\\"1\\\"},{\\\"latitude\\\":\\\"25.147460101488445\\\",\\\"longitude\\\":\\\"55.27153015\\\",\\\"order\\\":\\\"2\\\"}]\"}]",
		        "d7e487d2-8713-47d2-ab28-2cca724fe36d", "55.23");
		geofenceDto.setGeofenceFields(geofenceFields1);
		geofenceDto.setGeofenceName("DgStreet1");
		geofenceDto.setGeofenceStatus(geofenceStatus);
		geofenceDto.setIdentifier(identityMap);
		geofenceDto.setType("Route");

		findAllGeofenceDto = constructFindAllGeofence();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#createGeofence(com.pcs.alpine.dto.GeofenceDTO)}
	 * .
	 */

	@Test(expected = GalaxyException.class)
	public void testCreateGeofenceEmptyGeofenceName() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(null, desc,
		        geofenceFields, type, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		sut.createGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testCreateGeofenceEmptyGeofenceType() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, null, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		sut.createGeofence(input);
	}

	@Test
	public void testCreateGeofenceEmptyGeofenceTypeSuccess() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, type, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);

		StatusMessageDTO actualResponse = sut.createGeofence(input);
		assertEquals(statusMessageDTO.getStatus(), actualResponse.getStatus());
	}

	@Test(expected = GalaxyException.class)
	public void testCreateGeofenceEmptyGeofenceFieldsNull() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        null, type, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		sut.createGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testCreateGeofenceInvalidType() {
		String invalidType = "Square";
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, invalidType, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		sut.createGeofence(input);
	}

	@Test
	public void testCreateGeofenceEmptyGeofenceTypeFailure() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, type, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(null);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);

		StatusMessageDTO actualResponse = sut.createGeofence(input);
		assertEquals(failureMessageDTO.getStatus(), actualResponse.getStatus());
	}

	@Test(expected = GalaxyException.class)
	public void testCreateGeofenceEmptyListGeofenceFields() {
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        fields, type, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		sut.createGeofence(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#updateGeofence(com.pcs.alpine.dto.GeofenceDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testUpdateGeofenceEmptyGeofenceName() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(null, desc,
		        geofenceFields, type, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageErrorSuccess);

		sut.updateGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testUpdateGeofenceEmptyGeofenceType() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, null, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageErrorSuccess);

		sut.updateGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testUpdateGeofenceInvalidType() {
		String invalidType = "Square";
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, invalidType, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageErrorSuccess);

		sut.updateGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testUpdateGeofenceEmptyIdentifierKey() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue(identityMap.getValue());
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, type, status, domainDTO, identifierKeyEmpty);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageErrorSuccess);

		sut.updateGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testUpdateGeofenceEmptyIdentifierValue() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue(identityMap.getKey());
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, type, status, domainDTO, identifierKeyEmpty);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageErrorSuccess);

		sut.updateGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testUpdateGeofenceUpdateError() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setKey(identityMap.getKey());
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, type, status, domainDTO, identifierKeyEmpty);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageUpdateError);

		sut.updateGeofence(input);
	}

	@Test
	public void testUpdateGeofenceUpdateSuccess() {
		GeofenceDTO input = geofenceDTOBuilder.aGeofence(geofenceName, desc,
		        geofenceFields, type, status, domainDTO, identityMap);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageErrorSuccess);

		StatusMessageDTO actualResponse = sut.updateGeofence(input);
		assertEquals(statusMessageErrorSuccess.getStatus(),
		        actualResponse.getStatus());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#deleteGeofence(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testDeleteGeofenceEmptyIdentifierKey() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setKey(identityMap.getKey());
		IdentityDTO input = identityDTOBuilder.aIdentity(identifierKeyEmpty,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageUpdateError);

		sut.deleteGeofence(input);
	}

	@Test(expected = GalaxyException.class)
	public void testDeleteGeofenceEmptyIdentifierValue() {
		FieldMapDTO identifierValueEmpty = new FieldMapDTO();
		identifierValueEmpty.setValue(identityMap.getValue());
		IdentityDTO input = identityDTOBuilder.aIdentity(identifierValueEmpty,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.put(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<StatusMessageErrorDTO>)Mockito.any()))
		        .thenReturn(statusMessageUpdateError);

		sut.deleteGeofence(input);
	}

	@Test
	public void testDeleteGeofenceGeofenceFailure() {
		IdentityDTO input = identityDTOBuilder.aIdentity(identityMap,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(null);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);

		StatusMessageDTO actualResponse = sut.deleteGeofence(input);
		assertEquals(failureMessageDTO.getStatus(), actualResponse.getStatus());
	}

	@Test
	public void testDeleteGeofenceGeofenceSuccess() {
		IdentityDTO input = identityDTOBuilder.aIdentity(identityMap,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);

		StatusMessageDTO actualResponse = sut.deleteGeofence(input);
		assertEquals(statusMessageDTO.getStatus(), actualResponse.getStatus());
	}

	@Test
	public void testDeleteGeofenceWithSubscriberDomainSuccess() {
		DomainDTO emptyDomian = new DomainDTO();
		IdentityDTO input = identityDTOBuilder.aIdentity(identityMap,
		        emptyDomian, entityTemplate);

		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        geofenceEntity);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);

		Mockito.when(subscriptionProfileService.getSubscription()).thenReturn(
		        subscription);

		StatusMessageDTO actualResponse = sut.deleteGeofence(input);
		assertEquals(statusMessageDTO.getStatus(), actualResponse.getStatus());
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#findGeofence(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindGeofenceEmptyIdentifierKey() {
		FieldMapDTO identifierKeyEmpty = new FieldMapDTO();
		identifierKeyEmpty.setValue(identityMap.getKey());
		IdentityDTO input = identityDTOBuilder.aIdentity(identifierKeyEmpty,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<GeofenceDTO>)Mockito.any())).thenReturn(
		        geofenceDto);

		sut.findGeofence(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#findGeofence(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindGeofenceEmptyIdentifierValue() {
		FieldMapDTO identifierKeyValue = new FieldMapDTO();
		identifierKeyValue.setKey(identityMap.getValue());
		IdentityDTO input = identityDTOBuilder.aIdentity(identifierKeyValue,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<GeofenceDTO>)Mockito.any())).thenReturn(
		        geofenceDto);
		sut.findGeofence(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#findGeofence(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test(expected = GalaxyException.class)
	public void testFindGeofenceFailure() {
		FieldMapDTO identifierKeyValue = new FieldMapDTO();
		identifierKeyValue.setKey(identityMap.getKey());
		IdentityDTO input = identityDTOBuilder.aIdentity(identifierKeyValue,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<GeofenceDTO>)Mockito.any())).thenReturn(
		        geofenceDto);
		sut.findGeofence(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#findGeofence(com.pcs.alpine.services.dto.IdentityDTO)}
	 * .
	 */
	@Test
	public void testFindGeofenceSuccess() {
		IdentityDTO input = identityDTOBuilder.aIdentity(identityMap,
		        domainDTO, entityTemplate);
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<GeofenceDTO>)Mockito.any())).thenReturn(
		        geofenceDto);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);

		GeofenceDTO actualGeofenceDto = sut.findGeofence(input);
		assertEquals(geofenceDto, actualGeofenceDto);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#findAllGeofences(java.lang.String)}
	 * .
	 */
	@Test
	public void testFindAllGeofencesFailure() {
		String input = " ";
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<List<EntityDTO>>)Mockito.any(),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        findAllGeofenceDto);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);
		Mockito.when(subscriptionProfileService.getSubscription()).thenReturn(
		        subscription);
		List<EntityDTO> actualEntityDto = sut.findAllGeofences(input);
	}

	/**
	 * Test method for
	 * {@link com.pcs.alpine.serviceimpl.GeofenceServiceImpl#findAllGeofences(java.lang.String)}
	 * .
	 */
	@Test
	public void testFindAllGeofencesSuccess() {
		String input = endUserDomain;
		Mockito.when(
		        platformClient.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(IdentityDTO.class),
		                (Class<List<EntityDTO>>)Mockito.any(),
		                (Class<EntityDTO>)Mockito.any())).thenReturn(
		        findAllGeofenceDto);

		Mockito.when(subscriptionProfileService.getJwtToken())
		        .thenReturn(token);

		List<EntityDTO> actualFindAllGeofenceDto = sut.findAllGeofences(input);
		assertEquals(findAllGeofenceDto, actualFindAllGeofenceDto);
	}

	private List<FieldMapDTO> constructRouteFieldsResponse(String coordinates,
	        String identifier, String width) {
		List<FieldMapDTO> fields = new ArrayList<FieldMapDTO>();

		FieldMapDTO coordinateMap = new FieldMapDTO();
		coordinateMap.setKey(CO_ORDINATES.getFieldName());
		coordinateMap.setValue(coordinates);

		FieldMapDTO widthMap = new FieldMapDTO();
		widthMap.setKey("width");
		widthMap.setValue(width);

		FieldMapDTO identifierMap = new FieldMapDTO();
		identifierMap.setKey(IDENTIFIER.getFieldName());
		identifierMap.setValue(identifier);

		fields.add(coordinateMap);
		fields.add(identifierMap);
		fields.add(identifierMap);
		return fields;
	}

	private List<EntityDTO> constructFindAllGeofence() {
		// TODO Auto-generated method stub
		List<EntityDTO> geofenceEntities = new ArrayList<EntityDTO>();
		EntityDTO geofenceEntity = new EntityDTO();

		PlatformEntityDTO globalEntity = new PlatformEntityDTO();
		// set Global Entity Type as MARKER
		globalEntity.setPlatformEntityType(MARKER.getFieldName());
		geofenceEntity.setGlobalEntity(globalEntity);

		// set domain as pcs.galaxy
		geofenceEntity.setDomain(domainDTO);

		// set entityStatus as ACTIVE
		StatusDTO entityStatus = new StatusDTO();
		entityStatus.setStatusKey(0);
		entityStatus.setStatusName(Status.ACTIVE.toString());
		geofenceEntity.setEntityStatus(entityStatus);

		// setting dataprovider
		List<FieldMapDTO> dataprovider = new ArrayList<FieldMapDTO>();
		FieldMapDTO fieldMap1 = new FieldMapDTO();
		fieldMap1.setKey(GEOFENCE_NAME.getFieldName());
		fieldMap1.setValue("Barsha1");
		dataprovider.add(fieldMap1);
		geofenceEntity.setDataprovider(dataprovider);

		// setting entity template name as Geofence
		EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
		entityTemplate.setEntityTemplateName("Geofence");
		geofenceEntity.setEntityTemplate(entityTemplate);

		FieldMapDTO geofenceIdentifier = new FieldMapDTO();
		geofenceIdentifier.setKey(IDENTIFIER.getFieldName());
		geofenceIdentifier.setValue("da924e60-e284-456b-95f1-fd032ebd6e5b");
		geofenceEntity.setIdentifier(geofenceIdentifier);

		// add the geofenceentity to the List of geofencesEntities
		geofenceEntities.add(geofenceEntity);

		// adding second element only changes in dataprovider and identifer
		dataprovider = new ArrayList<FieldMapDTO>();
		FieldMapDTO fieldMap2 = new FieldMapDTO();
		fieldMap2.setKey(GEOFENCE_NAME.getFieldName());
		fieldMap2.setValue("jafza");
		dataprovider.add(fieldMap1);

		geofenceIdentifier.setValue("d18e895a-e09b-4c64-8954-0a188dec5492");
		geofenceEntity.setIdentifier(geofenceIdentifier);
		geofenceEntities.add(geofenceEntity);

		return geofenceEntities;
	}
}
