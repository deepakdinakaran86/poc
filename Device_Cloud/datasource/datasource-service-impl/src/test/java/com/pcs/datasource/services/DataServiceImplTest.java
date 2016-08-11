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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pcs.datasource.dto.BatchPersistResponse;
import com.pcs.datasource.dto.CustomTagData;
import com.pcs.datasource.dto.DateData;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceMessage;
import com.pcs.datasource.dto.DeviceMessageResponse;
import com.pcs.datasource.dto.DevicePointData;
import com.pcs.datasource.dto.SearchDTO;
import com.pcs.datasource.model.Data;
import com.pcs.datasource.model.PhysicalQuantity;
import com.pcs.datasource.model.udt.DeviceFieldMap;
import com.pcs.datasource.repository.DataRepo;
import com.pcs.datasource.repository.utils.ProcessDeviceMessage;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * Test class for device data related services
 * 
 * @description Test Class for DataServiceImpl
 * @author Greeshma (pcseg323)
 * @date 17 Feb 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DataServiceImplTest {

	@InjectMocks
	private DataServiceImpl dataServiceImpl;

	@Mock
	private DataRepo dataRepo;

	@Mock
	private ProcessDeviceMessage processDeviceMessage;

	@Mock
	private DataService dataService;

	@Mock
	private DeviceService deviceService;

	@Mock
	private PhyQuantityService dataStoreService;

	String physicalQuantityName;
	Data physicalQuantity;
	Long deviceTime;
	DeviceFieldMap deviceFieldMap;
	StatusMessageDTO statusMessageDTO;
	DeviceMessage deviceMessage;
	DevicePointData devicePointData;
	SearchDTO searchDTO;
	PhysicalQuantity dataStore;
	List<DeviceFieldMap> extensions;
	List<String> customTags;
	List<DevicePointData> devicePointDatas;
	List<DeviceMessage> deviceMessages;
	Long startDate;
	Long endDate;
	String systemTag;
	DateTime startDateValue;
	DateTime endDateValue;
	UUID uid;
	List<Data> physicalQuantities;
	DeviceMessageResponse deviceMessageResponse;
	CustomTagData customTagData;
	List<CustomTagData> customTagDatas;
	DateData dateData;
	List<DateData> dateDatas;
	Device device;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		physicalQuantityName = "temperature";
		deviceTime = 1426912126981L;
		startDate = 1426636800000L;
		endDate = 1426896000000L;
		startDateValue = new DateTime(startDate).toDateTime(DateTimeZone.UTC);
		endDateValue = new DateTime(endDate).toDateTime(DateTimeZone.UTC);
		physicalQuantity = new Data();
		statusMessageDTO = new StatusMessageDTO();
		searchDTO = new SearchDTO();
		dataStore = new PhysicalQuantity();
		device = new Device();
		deviceFieldMap = new DeviceFieldMap();
		extensions = new ArrayList<DeviceFieldMap>();
		customTags = new ArrayList<String>();
		deviceMessage = new DeviceMessage();
		devicePointData = new DevicePointData();
		customTagData = new CustomTagData();
		customTagDatas = new ArrayList<CustomTagData>();
		dateData = new DateData();
		dateDatas = new ArrayList<DateData>();
		deviceMessageResponse = new DeviceMessageResponse();
		systemTag = "Ground - Room Temperature 2";
		physicalQuantities = new ArrayList<Data>();
		devicePointDatas = new ArrayList<DevicePointData>();
		deviceMessages = new ArrayList<DeviceMessage>();
		uid = UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473");
		physicalQuantity.setSourceId(uid);
		physicalQuantity.setData("23");
		physicalQuantity.setDeviceTime(deviceTime);
		deviceFieldMap.setExtensionName("Point Data Acquisition type");
		deviceFieldMap.setExtensionType("Time Based");
		extensions.add(deviceFieldMap);
		physicalQuantity.setExtensions(extensions);
		physicalQuantity.setCustomTag("Room Temperature");
		physicalQuantity.setSystemTag(systemTag);
		physicalQuantities.add(physicalQuantity);
		customTags.add("Room Temperature");
		searchDTO.setSourceId("e2684346-cfbc-4056-a330-c71188adf473");
		searchDTO.setCustomTags(customTags);
		searchDTO.setSystemTag(systemTag);
		searchDTO.setStartDate(startDate);
		searchDTO.setEndDate(endDate);
		statusMessageDTO.setStatus(Status.SUCCESS);
		dataStore.setDataStore("temperature");
		dataStore.setStatus(Status.ACTIVE.name());
		dataStore
		        .setId(UUID.fromString("e2684346-cfbc-4056-a330-c71188adf473"));
		dataStore.setName("Temperature");
		deviceMessage.setSourceId("e2684346-cfbc-4056-a330-c71188adf473");
		deviceMessage.setTime(deviceTime);
		deviceMessage.setSourceId("123344");
		devicePointData.setCustomTag("Room Temperature");
		devicePointData.setData("23");
		devicePointData.setPhysicalQuantity(physicalQuantityName);
		devicePointData.setPointId("455");
		devicePointData.setSystemTag(systemTag);
		devicePointData.setUnit("kelvin");
		devicePointDatas.add(devicePointData);
		deviceMessage.setPoints(devicePointDatas);
		deviceMessages.add(deviceMessage);
		deviceMessageResponse.setSourceId(uid);
		customTagData.setCustomTag("Room Temperature");
		dateData.setDate(startDate);
		dateData.setValues(physicalQuantities);
		dateDatas.add(dateData);
		customTagData.setDates(dateDatas);
		customTagDatas.add(customTagData);
		deviceMessageResponse.setCustomTags(customTagDatas);

		device.setDatasourceName("datasourceName");
		device.setSourceId("e2684346-cfbc-4056-a330-c71188adf473");
		device.setDeviceId("e2684346-cfbc-4056-a330-c71188adf473");

	}

	/**
	 * Test method savePhysicalQuantity
	 */
	@Test
	public void testSavePhysicalQuantity() {
		Mockito.when(dataRepo.save(physicalQuantityName, physicalQuantity))
		        .thenReturn(physicalQuantity);
		Mockito.when(
		        dataStoreService.getPhyQuantityByName(physicalQuantityName))
		        .thenReturn(physicalQuantityName);
		statusMessageDTO = dataServiceImpl.createDeviceData(
		        dataStore.getDataStore(), physicalQuantity);
		assertEquals(Status.SUCCESS, statusMessageDTO.getStatus());

	}

	/**
	 * Test Case: physical quantity name is null in save
	 */
	@Test(expected = DeviceCloudException.class)
	public void testSavePhysicalQuantityNullPhyQuantity() {
		physicalQuantityName = null;
		Mockito.when(dataRepo.save(physicalQuantityName, physicalQuantity))
		        .thenReturn(null);
		dataServiceImpl
		        .createDeviceData(physicalQuantityName, physicalQuantity);
	}

	/**
	 * Test Case: device Id is not specified in save
	 */
	@Test(expected = DeviceCloudException.class)
	public void testSavePhysicalQuantityNullDeviceId() {
		physicalQuantity.setSourceId(null);
		Mockito.when(dataRepo.save(physicalQuantityName, physicalQuantity))
		        .thenReturn(null);
		dataServiceImpl
		        .createDeviceData(physicalQuantityName, physicalQuantity);
	}

	/**
	 * Test Case: device time is not specified in save
	 */
	@Test(expected = DeviceCloudException.class)
	public void testSavePhysicalQuantityNullDeviceTime() {
		physicalQuantity.setDeviceTime(null);
		Mockito.when(dataRepo.save(physicalQuantityName, physicalQuantity))
		        .thenReturn(null);
		dataServiceImpl
		        .createDeviceData(physicalQuantityName, physicalQuantity);
	}

	/**
	 * Test Case: device data is not specified in save
	 */
	@Test(expected = DeviceCloudException.class)
	public void testSavePhysicalQuantityNullDeviceData() {
		physicalQuantity.setData(null);
		Mockito.when(dataRepo.save(physicalQuantityName, physicalQuantity))
		        .thenReturn(null);
		dataServiceImpl
		        .createDeviceData(physicalQuantityName, physicalQuantity);
	}

	/**
	 * Test Case: custom tag is not specified in save
	 */
	@Test(expected = DeviceCloudException.class)
	public void testSavePhysicalQuantityNullCustomTag() {
		physicalQuantity.setCustomTag(null);;
		Mockito.when(dataRepo.save(physicalQuantityName, physicalQuantity))
		        .thenReturn(null);
		dataServiceImpl
		        .createDeviceData(physicalQuantityName, physicalQuantity);
	}

	/**
	 * Test Case: Invalid datastore specified in save
	 */
	@Test(expected = DeviceCloudException.class)
	public void testSavePhysicalQuantityInvalidDataStore() {
		Mockito.when(
		        dataStoreService.getPhyQuantityByName(physicalQuantityName))
		        .thenReturn(null);
		Mockito.when(dataRepo.save(dataStore.getDataStore(), physicalQuantity))
		        .thenReturn(null);
		dataServiceImpl
		        .createDeviceData(physicalQuantityName, physicalQuantity);
	}

	/**
	 * Test method getDeviceData
	 */
	@Test
	public void testGetDeviceData() {
		Mockito.when(
		        dataStoreService.getPhyQuantityByName(physicalQuantityName))
		        .thenReturn(physicalQuantityName);

		Mockito.when(deviceService.getDevice(searchDTO.getSourceId()))
		        .thenReturn(device);

		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(physicalQuantityName),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(
		        deviceMessageResponse);
		assertEquals(deviceMessageResponse.getSourceId(), dataServiceImpl
		        .getDeviceData(dataStore.getDataStore(), searchDTO)
		        .getSourceId());

	}

	/**
	 * Test method for getDeviceData with null device Id
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceDataNullDeviceId() {
		searchDTO.setSourceId(null);
		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(physicalQuantityName),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(null);
		dataServiceImpl.getDeviceData(physicalQuantityName, searchDTO);

	}

	/**
	 * Test method for empty custom tags in getDeviceData
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceDataEmptyCustomTags() {
		searchDTO.setCustomTags(null);
		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(physicalQuantityName),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(null);
		dataServiceImpl.getDeviceData(physicalQuantityName, searchDTO);

	}

	/**
	 * Test method for null startdate in getDeviceData
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceDataNullStartDate() {
		searchDTO.setStartDate(null);
		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(physicalQuantityName),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(null);
		dataServiceImpl.getDeviceData(physicalQuantityName, searchDTO);

	}

	/**
	 * Test method for null enddate in getDeviceData
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceDataNullEndDate() {
		searchDTO.setEndDate(null);
		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(physicalQuantityName),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(null);
		dataServiceImpl.getDeviceData(physicalQuantityName, searchDTO);

	}

	/**
	 * Test method for invalid date range in getDeviceData
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceDataInvalidDateRange() {
		searchDTO.setStartDate(endDate);
		searchDTO.setEndDate(startDate);
		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(physicalQuantityName),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(null);
		dataServiceImpl.getDeviceData(physicalQuantityName, searchDTO);

	}

	/**
	 * Test method for empty result in getDeviceData
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceEmptyResult() {
		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(physicalQuantityName),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(null);
		dataServiceImpl.getDeviceData(physicalQuantityName, searchDTO);

	}

	/**
	 * Test method for invalid physical quantity name result in getDeviceData
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceInvalidDataStore() {
		Mockito.when(
		        dataStoreService.getPhyQuantityByName(physicalQuantityName))
		        .thenReturn(null);
		Mockito.when(
		        dataRepo.getDeviceData(Mockito.eq(dataStore.getDataStore()),
		                Mockito.eq(uid), Mockito.eq(startDateValue),
		                Mockito.eq(endDateValue), Mockito.eq(systemTag),
		                Mockito.anyListOf(String.class))).thenReturn(null);
		dataServiceImpl.getDeviceData(physicalQuantityName, searchDTO);

	}

	/**
	 * Test method saveBatchPersist
	 */
	@Test
	public void testBatchPersist() {
		BatchPersistResponse batchPersistResponse = new BatchPersistResponse();
		batchPersistResponse.setFailedDeviceMsgs(deviceMessages);
		Mockito.when(dataRepo.batchPersist(deviceMessages)).thenReturn(
		        batchPersistResponse);
		batchPersistResponse = dataServiceImpl.createDeviceData(deviceMessages);
		assertEquals(Status.SUCCESS, statusMessageDTO.getStatus());

	}

	/**
	 * Test method saveDeviceBatchPersist
	 */
	@Test
	public void testDeviceBatchPersist() {
		BatchPersistResponse batchPersistResponse = new BatchPersistResponse();
		batchPersistResponse.setFailedDeviceMsgs(deviceMessages);
		Mockito.when(dataRepo.batchPersist(deviceMessage)).thenReturn(
		        batchPersistResponse);
		batchPersistResponse = dataServiceImpl.createDeviceData(deviceMessage);
		assertEquals(Status.SUCCESS, statusMessageDTO.getStatus());

	}
}
