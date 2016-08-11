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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pcs.alpine.dto.PermissionGroupsDTO;
import com.pcs.alpine.isadapter.dto.PermissionGroup;
import com.pcs.alpine.isadapter.dto.Subscription;
import com.pcs.alpine.rest.beans.StandardResponse;
import com.pcs.alpine.rest.client.BaseClient;
import com.pcs.alpine.serviceimpl.PermissionGroupServiceImpl;
import com.pcs.alpine.services.PlatformEntityService;
import com.pcs.alpine.services.dto.StatusMessageDTO;
import com.pcs.alpine.services.dto.builder.PermissionGroupsDTOBuilder;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author Daniela PCSEG191)
 * @date 28 Oct 2015
 * @since alpine-1.0.0
 */
@ContextConfiguration("classpath:adapter-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionGroupServiceImplTest {

	@InjectMocks
	private PermissionGroupServiceImpl sut;

	@Mock
	private PlatformEntityService globalEntityService;

	@Mock
	private BaseClient client;

	private PermissionGroupsDTO permissionGroupsDTO;

	private String resourceName;

	private String domain;

	private List<String> permissions;

	private PermissionGroup permissionGroup;

	private StandardResponse response;

	private Subscription subscription;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		resourceName = "FMS";
		permissions = new ArrayList<String>();
		permissions.add("View");
		permissions.add("Delete");
		domain = "cummins.alpine.com";

		permissionGroup = new PermissionGroup();
		permissionGroup.setId(resourceName);
		permissionGroup.setPermissions(permissions);

		response = new StandardResponse();
		response.setStatus("Success");

		subscription = new Subscription();
		subscription.setAction("subscribe");
		subscription.setPermissionGroupIDs(permissions);
		subscription.setTenantDomain(domain);
	}

	/**
	 * Test Case: Successful save PG
	 */
	@Test
	public void testSavePermissionSuccess() throws Exception {
		permissionGroupsDTO = new PermissionGroupsDTOBuilder()
		        .aPermissionGroup(resourceName, permissions, domain).build();
		permissionGroup.setPermissions(new ArrayList<String>());
		Mockito.when(
		        client.get(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                (Class<PermissionGroup>)Mockito.any())).thenReturn(
		        permissionGroup);
		Mockito.when(
		        client.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(PermissionGroup.class),
		                (Class<StandardResponse>)Mockito.any())).thenReturn(
		        response);

		Mockito.when(
		        client.post(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                Mockito.any(Subscription.class),
		                (Class<StandardResponse>)Mockito.any())).thenReturn(
		        response);
		StatusMessageDTO statusMessageDTO = sut
		        .createPermissionGroup(permissionGroupsDTO);
		assertEquals("success", statusMessageDTO.getStatus().getStatus());
	}

	@Test
	public void testGetPermissionSuccess() throws Exception {
		permissionGroupsDTO = new PermissionGroupsDTOBuilder()
		        .aPermissionGroup(resourceName, permissions, domain).build();
		permissionGroup.setPermissions(permissions);
		Mockito.when(
		        client.get(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                (Class<PermissionGroup>)Mockito.any())).thenReturn(
		        permissionGroup);
		Boolean isParentDomain = false;
		PermissionGroupsDTO permissionGroupsDTO = sut.getPermissionGroup(
		        resourceName, domain, isParentDomain);
		assertEquals(resourceName, permissionGroupsDTO.getResourceName());
	}

	@Test
	public void testGetResourcesSuccess() throws Exception {
		permissionGroupsDTO = new PermissionGroupsDTOBuilder()
		        .aPermissionGroup(resourceName, permissions, domain).build();
		Mockito.when(
		        client.get(Mockito.any(String.class),
		                Mockito.anyMapOf(String.class, String.class),
		                (Class<Subscription>)Mockito.any())).thenReturn(
		        subscription);
		Boolean isParentDomain = false;
		PermissionGroupsDTO permissionGroupsDTO = sut.getResources(
		        resourceName, isParentDomain);
		assertEquals(domain, permissionGroupsDTO.getDomain().getDomainName());
	}

}
