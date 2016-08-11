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

import static com.pcs.devicecloud.enums.Status.ACTIVE;
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

import com.pcs.datasource.model.PhysicalQuantity;
import com.pcs.datasource.model.Unit;
import com.pcs.datasource.repository.UnitRepo;
import com.pcs.datasource.repository.utils.StatusUtil;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * Test class for unit related services
 *
 * @description Test Class for UnitServiceImpl
 * @author Greeshma (pcseg323)
 * @date April 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitServiceImplTest {

	@InjectMocks
	private UnitServiceImpl unitServiceImpl;

	@Mock
	private PhyQuantityService phyQuantityService;

	@Mock
	private UnitRepo unitRepo;

	@Mock
	private StatusUtil statusUtil;

	@Mock
	private UnitService unitService;

	Unit unit;
	String physicalQuantityName;
	PhysicalQuantity physicalQuantity;
	List<Unit> units;
	StatusMessageDTO statusMessageDTO;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		physicalQuantityName = "Temperature";
		statusMessageDTO = new StatusMessageDTO();

	}

	@Test
	public void testCreateUnit() {

		unit = new Unit();
		physicalQuantity = new PhysicalQuantity();
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatusKey(0);
		unit.setStatus(ACTIVE.name());
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
		        .fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		physicalQuantity.setUnits(units);
		
		Mockito.doNothing().when(unitRepo).persistUnit(unit);
		
		Mockito.when(
		        phyQuantityService.getPhyQuantity(physicalQuantityName))
		        .thenReturn(physicalQuantity);
		statusMessageDTO = unitServiceImpl.createUnit(unit);
		assertEquals(Status.SUCCESS, statusMessageDTO.getStatus());

	}

	@Test(expected = DeviceCloudException.class)
	public void testCreateUnitInvalidPQ() {

		Mockito.doNothing().when(unitRepo).persistUnit(unit);
		Mockito.when(
		        phyQuantityService.getPhyQuantity(physicalQuantityName))
		        .thenReturn(null);
		unitServiceImpl.createUnit(unit);

	}

	@Test
	public void testGetUnit() {

		unit = new Unit();
		physicalQuantity = new PhysicalQuantity();
		units = new ArrayList<Unit>();
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatusKey(0);
		unit.setStatus(ACTIVE.name());
		units.add(unit);
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
		        .fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		physicalQuantity.setUnits(units);
		Mockito.when(unitRepo.getActiveUnits(physicalQuantityName, 0))
		        .thenReturn(units);
		Mockito.when(unitService.getUnitDeatils(physicalQuantityName))
		        .thenReturn(units);

		assertEquals(units.size(),
		        unitServiceImpl.getUnitDeatils(physicalQuantityName).size());

	}

	@Test(expected = DeviceCloudException.class)
	public void testGetUnitNullPQ() {

		physicalQuantityName = null;
		Mockito.when(unitRepo.getActiveUnits(physicalQuantityName, 0))
		        .thenReturn(units);
		Mockito.when(unitService.getUnitDeatils(physicalQuantityName))
		        .thenReturn(units);

		unitServiceImpl.getUnitDeatils(physicalQuantityName);

	}

	@Test(expected = DeviceCloudException.class)
	public void testGetUnitNoResult() {

		Mockito.when(unitRepo.getActiveUnits(physicalQuantityName, 0))
		        .thenReturn(null);
		Mockito.when(unitService.getUnitDeatils(physicalQuantityName))
		        .thenReturn(null);

		unitServiceImpl.getUnitDeatils(physicalQuantityName);

	}

	@Test
	public void testDeleteUnit() {

		unit = new Unit();
		physicalQuantity = new PhysicalQuantity();
		unit.setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setIsSI(true);
		unit.setConversion("temperature");
		unit.setName("Kelvin");
		unit.setpName(physicalQuantityName);
		unit.setpUuid(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		unit.setStatusKey(0);
		unit.setStatus(ACTIVE.name());
		physicalQuantity.setDataStore("temperature");
		physicalQuantity.setName(physicalQuantityName);
		physicalQuantity.setStatus(ACTIVE.name());
		physicalQuantity.setStatusKey(0);
		physicalQuantity.setId(UUID
		        .fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		physicalQuantity.setUnits(units);
		// Mockito.doNothing().when(unitRepo)
		// .deleteUnit("Kelvin", physicalQuantity);
		Mockito.when(unitRepo.getUnitDetails("Kelvin")).thenReturn(unit);
		// Mockito.when(statusUtil.getStatus(UN_SUPPORTED.name())).thenReturn(2);
		Mockito.when(
		        phyQuantityService.getPhyQuantity(physicalQuantityName))
		        .thenReturn(physicalQuantity);
		// Mockito.when(unitService.deleteUnit("Kelvin")).thenReturn(
		// statusMessageDTO);
		// statusMessageDTO = unitServiceImpl.deleteUnit("Kelvin");
		assertEquals(Status.SUCCESS, statusMessageDTO.getStatus());

	}

	@Test(expected = DeviceCloudException.class)
	public void testDeleteUnitNullName() {

		Mockito.doNothing().when(unitRepo).deleteUnit(null, physicalQuantity);
		Mockito.when(unitRepo.getUnitDetails(null)).thenReturn(unit);
		// Mockito.when(statusUtil.getStatus(UN_SUPPORTED.name())).thenReturn(2);
		Mockito.when(
		        phyQuantityService.getPhyQuantity(physicalQuantityName))
		        .thenReturn(physicalQuantity);
		// Mockito.when(unitService.deleteUnit(null)).thenReturn(statusMessageDTO);

		// unitServiceImpl.deleteUnit(null);

	}
}
