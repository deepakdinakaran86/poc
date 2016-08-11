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

import static com.pcs.devicecloud.enums.Status.SUCCESS;
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

import com.google.gson.Gson;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.ConfigureDevice;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.GeneralBatchResponse;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceConfigRepository;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * Test class for DeviceConfigServiceImpl
 * 
 * @description Test Class for UnitServiceImpl
 * @author Javid Ahammed (pcseg199)
 * @date Sep 2015
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceConfigServiceImplTest {

	@InjectMocks
	DeviceConfigServiceImpl sut;

	@Mock
	DeviceConfigRepository deviceConfigRepository;

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private ObjectBuilderUtil objectBuilderUtil;

	String subId;

	String templateName;

	DeviceConfigTemplate value;

	ConfigurationSearch conSearch;

	List<DeviceConfigTemplate> confTemplates;
	List<String> confTemplateNames;

	private List<String> sourceIds;

	private GeneralBatchResponse batchResponse;

	private StatusMessageDTO confTempExists;

	private ConfigureDevice configureDevice;

	private Subscription subscription;

	private DeviceConfigTemplate confTemplate;

	private String sourceId;

	private Device device;

	private List<ConfigPoint> configurations;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		subId = "1";
		templateName = "templateName";
		value = new DeviceConfigTemplate();
		value.setName(templateName);

		sourceId = "00000000012";
		device = new Device();
		device.setSourceId(sourceId);
		device.setUnitId(12);
		device.setIp("10.234.60.38");
		device.setConnectedPort(9443);
		device.setWriteBackPort(9444);

		conSearch = new ConfigurationSearch();
		conSearch.setDeviceType("device type");
		conSearch.setMake("make");
		conSearch.setModel("model");
		conSearch.setProtocol("protocol");
		conSearch.setVersion("1.0.0");
		confTemplates = new ArrayList<>();

		confTemplate = new DeviceConfigTemplate();
		confTemplate.setName("template Name");
		List<ConfigPoint> configPoints = new ArrayList<ConfigPoint>();
		confTemplate.setConfigPoints(configPoints);
		confTemplates.add(confTemplate);

		confTemplateNames = new ArrayList<String>();
		confTemplateNames.add(templateName);

		configureDevice.setConfTemplate(confTemplate);
		sourceIds = new ArrayList<String>();
		configureDevice.setSourceIds(sourceIds);
		sourceIds.add("sourceId 1");
		sourceIds.add("sourceId 2");

		batchResponse = new GeneralBatchResponse();
		List<GeneralResponse> responses = new ArrayList<GeneralResponse>();
		GeneralResponse generalResponse1 = new GeneralResponse();
		generalResponse1.setReference("1");
		generalResponse1.setStatus(SUCCESS);
		responses.add(generalResponse1);
		batchResponse.setResponses(responses);
		subscription = new Subscription();
		subscription.setSubId("1");
		confTempExists = new StatusMessageDTO();
		confTempExists.setStatus(SUCCESS);
	}

	@Test
	public void testGetAllConfTemplatesSuccess() {

		Mockito.when(
				deviceConfigRepository.getAllConfTemplates(Mockito.anyString(),
						Mockito.any(ConfigurationSearch.class))).thenReturn(
				confTemplates);

		assertEquals(confTemplates.size(),
				sut.getAllConfTemplates(subId, conSearch).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetAllConfTemplatesFailedValidation() {

		Mockito.when(
				deviceConfigRepository.getAllConfTemplates(Mockito.anyString(),
						Mockito.any(ConfigurationSearch.class))).thenReturn(
				confTemplates);
		conSearch.setDeviceType(null);
		assertEquals(confTemplates.size(),
				sut.getAllConfTemplates(subId, conSearch).size());
	}

	@Test
	public void testGetConfTemplate() {
		Mockito.when(
				deviceConfigRepository.getConfTemplate(Mockito.anyString(),
						Mockito.anyString())).thenReturn(value);
		DeviceConfigTemplate confTemplate = sut.getConfTemplate(subId,
				templateName);
		assertEquals(confTemplate.getName(), templateName);

	}

	@Test(expected = DeviceCloudException.class)
	public void testGetConfTemplateFailedNoResult() {

		Mockito.when(
				deviceConfigRepository.getConfTemplate(Mockito.anyString(),
						Mockito.anyString())).thenReturn(null);
		DeviceConfigTemplate confTemplate = sut.getConfTemplate(subId,
				templateName);
		assertEquals(confTemplate.getName(), templateName);

	}

	@Test
	public void testInActivateConfigTemplates() {
		Mockito.doNothing().when(deviceConfigRepository)
				.detachAllDevice(subId, confTemplateNames);
		assertEquals(SUCCESS,
				sut.inActivateConfigTemplates(subId, confTemplateNames)
						.getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testInActivateConfigTemplatesFailed() {
		Mockito.doThrow(DeviceCloudException.class)
				.when(deviceConfigRepository)
				.detachAllDevice(subId, confTemplateNames);
		assertEquals(SUCCESS,
				sut.inActivateConfigTemplates(subId, confTemplateNames)
						.getStatus());
	}

	@Test
	public void testAssignConfPointsToDevicesSuccess() {
		Mockito.when(
				sut.isDeviceConfigTempExist(Mockito.anyString(),
						Mockito.anyString())).thenReturn(confTempExists);

		Mockito.when(
				deviceConfigRepository.assignConfigPointToDevices(
						configureDevice.getConfTemplate(), sourceIds, true))
				.thenReturn(batchResponse);

		assertEquals(batchResponse.getResponses().size(), sut
				.assignConfPointsToDevices(configureDevice, subscription)
				.getResponses().size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testAssignConfPointsToDevicesFailedSourceIdsNull() {
		Mockito.when(
				sut.isDeviceConfigTempExist(Mockito.anyString(),
						Mockito.anyString())).thenReturn(confTempExists);

		Mockito.when(
				deviceConfigRepository.assignConfigPointToDevices(
						configureDevice.getConfTemplate(), sourceIds, true))
				.thenReturn(batchResponse);

		configureDevice.setSourceIds(null);
		assertEquals(batchResponse.getResponses().size(), sut
				.assignConfPointsToDevices(configureDevice, subscription)
				.getResponses().size());
	}

	@Test
	public void testupdateDeviceConfSuccess() {
		Mockito.when(
				deviceRepository.getDevice(Mockito.anyString(),
						Mockito.any(Subscription.class))).thenReturn(device);
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(new Gson());
		Mockito.doNothing()
				.when(deviceRepository)
				.updateConfigurations(Mockito.anyString(),
						Mockito.any(DeviceConfigTemplate.class));
		assertEquals(SUCCESS,
				sut.updateDeviceConfiguration(sourceId, configurations)
						.getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testupdateDeviceConfFailureOnInvalidSourceId() {
		Mockito.when(deviceRepository.getDevice(sourceId, subscription))
				.thenReturn(null);
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(new Gson());
		Mockito.doNothing()
				.when(deviceRepository)
				.updateConfigurations(Mockito.anyString(),
						Mockito.any(DeviceConfigTemplate.class));
		assertEquals(SUCCESS,
				sut.updateDeviceConfiguration(sourceId, configurations));
	}

}
