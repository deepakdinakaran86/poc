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
package com.pcs.datasource.services;

import static com.pcs.datasource.enums.PQDataFields.WARNING;
import static com.pcs.devicecloud.enums.Status.ACTIVE;
import static com.pcs.devicecloud.enums.Status.FAILURE;
import static com.pcs.devicecloud.enums.Status.SUCCESS;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pcs.datasource.dto.PhyQuantityResponse;
import com.pcs.datasource.model.PhysicalQuantity;
import com.pcs.datasource.model.Unit;
import com.pcs.datasource.repository.PhyQuantityRepo;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.datasource.services.utils.CacheUtility;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * Test class for physical quantity related services
 *
 * @description Test Class for PhysicalQuantityServiceImpl
 * @author Greeshma (pcseg323)
 * @date April 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PhyQuantityServiceImplTest {

	@InjectMocks
	private PhyQuantityServiceImpl phyQuantityServiceImpl;

	@Mock
	private PhyQuantityService phyQuantityService;

	@Mock
	private PhyQuantityRepo phyQuantityRepo;

	@Mock
	private StatusUtil statusUtil;

	@Mock
	private UnitService unitService;

	@Mock
	private CacheUtility cacheUtility;

	@Mock
	private Cache cache;

	@Mock
	private CacheProvider cacheProvider;

	String physicalQuantityName;
	PhysicalQuantity physicalQuantity;
	Unit unit;
	List<Unit> units;
	StatusMessageDTO statusMessageDTO;
	PhyQuantityResponse phyQuantityResponse;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		physicalQuantityName = "Temperature";
		phyQuantityResponse = new PhyQuantityResponse();
		statusMessageDTO = new StatusMessageDTO();

	}

	/**
	 * Test method CreatePhyQuantity
	 */
	@Test
	public void testCreatePhyQuantity() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);

		Mockito.doNothing().when(phyQuantityRepo)
				.savePhyQuantity(physicalQuantity);
		Mockito.when(unitService.getUnitDetails("Kelvin", physicalQuantityName))
				.thenReturn(null);
		phyQuantityResponse = phyQuantityServiceImpl
				.createPhyQuantity(physicalQuantity);
		assertEquals(SUCCESS.name(), phyQuantityResponse.getStatus());

	}

	/**
	 * Test method CreatePhyQuantity with no SI Unit
	 */
	@Test
	public void testCreatePQWithNoSIUnit() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(false);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);
		Mockito.doNothing().when(phyQuantityRepo)
				.savePhyQuantity(physicalQuantity);
		Mockito.when(unitService.getUnitDetails("Kelvin", physicalQuantityName))
				.thenReturn(null);
		phyQuantityResponse = phyQuantityServiceImpl
				.createPhyQuantity(physicalQuantity);
		assertEquals(WARNING.name(), phyQuantityResponse.getStatus());

	}

	/**
	 * Test method CreatePhyQuantity with duplicate units
	 */

	@Test
	public void testCreatePQWithDuplicateUnits() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);
		Mockito.doNothing().when(phyQuantityRepo)
				.savePhyQuantity(physicalQuantity);
		Mockito.when(unitService.getUnitDetails("Kelvin", physicalQuantityName))
				.thenReturn(null);
		phyQuantityResponse = phyQuantityServiceImpl
				.createPhyQuantity(physicalQuantity);
		assertEquals(FAILURE.name(), phyQuantityResponse.getStatus());

	}

	/**
	 * Test method DeletePhyQuantity
	 */

	@Test
	public void testDeletePhyQuantity() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);
		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName))
				.thenReturn(physicalQuantity);
		statusMessageDTO = phyQuantityServiceImpl
				.deletePhyQuantity(physicalQuantityName);
		assertEquals(Status.SUCCESS, statusMessageDTO.getStatus());

	}

	/**
	 * Test method DeletePhyQuantity with NonExisting physical quantity
	 */

	@Test(expected = DeviceCloudException.class)
	public void testDeletePQWithNonExistinPQ() {

		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName))
				.thenReturn(null);
		phyQuantityServiceImpl.deletePhyQuantity(physicalQuantityName);

	}

	/**
	 * Test method GetPhyQuantityFromCache
	 */
	@Test
	public void testGetPhyQuantityFromCache() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);
		Mockito.when(
				phyQuantityService
						.getPhyQuantityByName(physicalQuantityName))
				.thenReturn(physicalQuantityName);

		Mockito.when(statusUtil.getStatus(ACTIVE.name())).thenReturn(0);
		Mockito.when(cacheUtility.getCacheProvider()).thenReturn(cacheProvider);
		Mockito.when(cacheProvider.getCache(Mockito.anyString())).thenReturn(
				cache);
		Mockito.when(cache.get(physicalQuantityName, String.class)).thenReturn(
				physicalQuantityName);
		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName,
						ACTIVE)).thenReturn(physicalQuantity);
		assertEquals(physicalQuantityName,
				phyQuantityServiceImpl
						.getPhyQuantityByName(physicalQuantityName));

	}

	/**
	 * Test method GetPhysicalQuantityDetails
	 */
	@Test
	public void testGetPhysicalQuantityDetails() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);

		Mockito.when(statusUtil.getStatus(ACTIVE.name())).thenReturn(0);

		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName,
						ACTIVE)).thenReturn(physicalQuantity);
		assertEquals(physicalQuantityName, phyQuantityServiceImpl
				.getActivePhyQuantity(physicalQuantityName).getName());

	}

	/**
	 * Test method GetPhysicalQuantityDetails with null physicalQuantityName
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetPhysicalQuantityDetailsWithNullPQName() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);

		Mockito.when(statusUtil.getStatus(ACTIVE.name())).thenReturn(0);

		Mockito.when(phyQuantityRepo.getPhyQuantityDetails(null, ACTIVE))
				.thenReturn(null);
		assertEquals(physicalQuantityName, phyQuantityServiceImpl
				.getActivePhyQuantity(physicalQuantityName).getName());

	}

	/**
	 * Test method GetPhysicalQuantityDetails with No data
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetPhysicalQuantityDetailsWithNoData() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);

		Mockito.when(statusUtil.getStatus(ACTIVE.name())).thenReturn(0);

		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName,
						ACTIVE)).thenReturn(null);
		assertEquals(physicalQuantityName, phyQuantityServiceImpl
				.getActivePhyQuantity(physicalQuantityName).getName());

	}

	/**
	 * Test method updatePhyQuantity
	 */
	@Test
	public void testUpdatePhyQuantity() {

		physicalQuantity = new PhysicalQuantity();
		PhysicalQuantity physicalQuantity1 = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		physicalQuantity1.setDataStore("length");
		physicalQuantity1.setName("Length");
		physicalQuantity1.setStatus(ACTIVE.name());
		physicalQuantity1.setStatusKey(0);

		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);
		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName))
				.thenReturn(physicalQuantity);
		Mockito.when(unitService.getUnitDeatils(physicalQuantityName))
				.thenReturn(units);

		Mockito.doNothing().when(phyQuantityRepo)
				.updatePhyQuantity(physicalQuantityName, physicalQuantity);

		assertEquals(
				SUCCESS,
				phyQuantityServiceImpl.updatePhyQuantity(physicalQuantityName,
						physicalQuantity1).getStatus());

	}

	/**
	 * Test method updatePhyQuantity with duplicate physical quantity
	 */
	@Test(expected = DeviceCloudException.class)
	public void testUpdatePhyQuantityWithDuplicatePQ() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));

		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);
		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName))
				.thenReturn(physicalQuantity);
		Mockito.when(unitService.getUnitDeatils(physicalQuantityName))
				.thenReturn(units);

		Mockito.doNothing().when(phyQuantityRepo)
				.updatePhyQuantity(physicalQuantityName, physicalQuantity);

		assertEquals(
				SUCCESS,
				phyQuantityServiceImpl.updatePhyQuantity(physicalQuantityName,
						physicalQuantity).getStatus());

	}

	/**
	 * Test method updatePhyQuantity with invalid physical quantity
	 */
	@Test(expected = DeviceCloudException.class)
	public void testUpdatePhyQuantityWithInvalidPQ() {

		physicalQuantity = new PhysicalQuantity();
		unit = new Unit();
		units = new ArrayList<Unit>();
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
				.fromString("e2684346-cfbc-4056-a330-c71188adf473"));

		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatus(ACTIVE.name());
		unit.setStatusKey(0);
		units.add(unit);
		physicalQuantity.setUnits(units);
		Mockito.when(
				phyQuantityRepo.getPhyQuantityDetails(physicalQuantityName))
				.thenReturn(null);
		Mockito.when(unitService.getUnitDeatils(physicalQuantityName))
				.thenReturn(units);

		Mockito.doNothing().when(phyQuantityRepo)
				.updatePhyQuantity(physicalQuantityName, physicalQuantity);

		assertEquals(
				SUCCESS,
				phyQuantityServiceImpl.updatePhyQuantity(physicalQuantityName,
						physicalQuantity).getStatus());

	}
}
