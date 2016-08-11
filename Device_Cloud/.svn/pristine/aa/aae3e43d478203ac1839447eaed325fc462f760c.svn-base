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

import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceConfigRepository;
import com.pcs.datasource.repository.DeviceProtocolRepository;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.datasource.repository.DeviceTagRepository;
import com.pcs.datasource.repository.NetworkProtocolRepository;
import com.pcs.datasource.services.utils.CacheUtility;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;

/**
 * Test class for device related services
 * 
 * @description Test Class for DeviceServiceImpl
 * @author Greeshma (pcseg323)
 * @date 17 Feb 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceServiceImplTest {

	@InjectMocks
	private DeviceServiceImpl sut;

	@Mock
	private CacheUtility cacheUtility;

	@Mock
	private ObjectBuilderUtil objectBuilderUtil;

	@Mock
	private Cache cache;

	@Mock
	private CacheProvider cacheProvider;

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private DeviceConfigRepository deviceConfigRepository;


	@Mock
	private DeviceProtocolRepository deviceProtocolRepository;

	@Mock
	private NetworkProtocolRepository nwProtocolRepository;

	@Mock
	private DeviceTagRepository deviceTagRepository;

	@Mock
	private NetworkProtocolService nwProtocolService;

	@Mock
	private DeviceProtocolService deviceProtocolService;

	@Mock
	private DeviceTypeService deviceTypeService;

	@Mock
	private DeviceTagService deviceTagService;

	@Mock
	private DatasourceRegistrationService datasourceRegistrationService;

	private DeviceConfigTemplate configurations;

	short pointId = 12;

	private String sourceId;

	private Device device;

	private List<Device> devices;

	private String subId;

	private Subscription subscription;

	private final String datasourceName = "dataSourceName";

	private NetworkProtocol networkProtocol;

	private final String nwProtocolName = "TCP";

	private DeviceProtocol protocol;

	private DeviceType type;

	private ConfigurationSearch configurtionSearch;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		subId = "1";
		sourceId = "00000000012";
		device = new Device();
		device.setSourceId(sourceId);
		device.setUnitId(12);
		device.setIp("10.234.60.38");
		device.setConnectedPort(9443);
		device.setWriteBackPort(9444);

		configurtionSearch = new ConfigurationSearch();
		configurtionSearch.setMake("makename");
		configurtionSearch.setDeviceType("deviceType");
		configurtionSearch.setModel("modelname");
		configurtionSearch.setProtocol("protocol");
		configurtionSearch.setVersion("version");

		device.setVersion(configurtionSearch);

		devices = new ArrayList<Device>();
		devices.add(device);

		List<ConfigPoint> configPoints = new ArrayList<ConfigPoint>();
		ConfigPoint configPoint = new ConfigPoint();
		configPoint.setPointId(String.valueOf(pointId));
		// configPoint.setType("1");
		configPoints.add(configPoint);
		configurations.setConfigPoints(configPoints);
		;
		device.setConfigurations(configurations);

		subscription = new Subscription();
		subscription.setSubId(subId);
		device.setSubscription(subscription);

		device.setDatasourceName(datasourceName);
		device.setIsPublishing(true);

		networkProtocol = new NetworkProtocol();
		networkProtocol.setName(nwProtocolName);
		device.setNetworkProtocol(networkProtocol);

		type = new DeviceType();
		type.setName("G2021");
		// device.setType(type);

		protocol = new DeviceProtocol();
		protocol.setName("EDCP 1.0");
		// device.setProtocol(protocol);
		protocol.setDeviceType(type);

	}

	@Test
	public void testgetDeviceSuccess() {
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(device);
		assertEquals(device.getUnitId(), sut.getDevice(sourceId).getUnitId());
	}

	@Test(expected = DeviceCloudException.class)
	public void testgetDeviceFailureSourceIdNull() {
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(null);
		assertEquals(device.getUnitId(), sut.getDevice(null).getUnitId());
	}

	@Test(expected = DeviceCloudException.class)
	public void testgetDeviceFailureNoResult() {
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(null);
		assertEquals(device.getUnitId(), sut.getDevice(sourceId).getUnitId());
	}

	@Test
	public void testgetConfigurationsSuccess() {
		Mockito.when(
				deviceRepository.getDevice(Mockito.anyString(),
						Mockito.any(Subscription.class))).thenReturn(device);
		DeviceConfigTemplate deviceConfigTemplate = new DeviceConfigTemplate();

		Mockito.when(
				deviceConfigRepository.getDeviceConfiguration(
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				deviceConfigTemplate);
		assertEquals(deviceConfigTemplate,
				sut.getConfigurations(sourceId, subscription));
	}

	@Test(expected = DeviceCloudException.class)
	public void testgetConfigurationsFailureDeviceNull() {
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(null);
		DeviceConfigTemplate deviceConfigTemplate = null;
		assertEquals(deviceConfigTemplate,
				sut.getConfigurations(sourceId, subscription));
	}

	@Test(expected = DeviceCloudException.class)
	public void testgetConfigurationsFailureConfigurationsNull() {
		device.setConfigurations(null);
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(device);
		DeviceConfigTemplate deviceConfigTemplate = null;
		assertEquals(deviceConfigTemplate,
				sut.getConfigurations(sourceId, subscription));
	}

	@Test
	public void testgetAllDevicesSuccess() {
		// Mockito.when(deviceRepository.getAllDevices(String.valueOf(subId)))
		// .thenReturn(devices);
		// assertEquals(devices.size(), sut.getAllDevices(String.valueOf(subId))
		// .size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testgetAllDevicesFailureOnEmptyResult() {
		Mockito.when(
				deviceRepository.getAllDevices(String.valueOf(subId), null))
				.thenReturn(null);
		assertEquals(devices.size(), sut.getAllDevices(subscription, null)
				.size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testgetAllDevicesFailureOnSubIdNull() {
		Mockito.when(deviceRepository.getAllDevices(null, null)).thenReturn(
				devices);
		assertEquals(devices.size(), sut.getAllDevices(subscription, null)
				.size());
	}

	@Test
	public void testinsertDeviceSuccess() {
		Mockito.when(
				deviceRepository.getDevice(Mockito.anyString(),
						Mockito.any(Subscription.class))).thenReturn(null);
		Mockito.when(
				nwProtocolService.getNetworkProtocol(device
						.getNetworkProtocol().getName())).thenReturn(
				device.getNetworkProtocol());

		ProtocolVersion protocolVersion = new ProtocolVersion();
		Mockito.when(
				deviceProtocolService.getDeviceProtocolVersion(device
						.getVersion())).thenReturn(protocolVersion);

		Mockito.when(
				deviceRepository.generateUnitId(Mockito
						.any(ConfigurationSearch.class))).thenReturn(1);
		Mockito.doNothing().when(deviceRepository).insertDevice(device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		assertEquals(SUCCESS, sut.insertDevice(sourceId, device).getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testinsertDeviceFailureSubIdNull() {
		device.getSubscription().setSubId(null);
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(null);
		Mockito.when(
				nwProtocolService.getNetworkProtocol(device
						.getNetworkProtocol().getName())).thenReturn(
				device.getNetworkProtocol());

		Mockito.when(
				deviceRepository.generateUnitId(Mockito
						.any(ConfigurationSearch.class))).thenReturn(1);
		Mockito.doNothing().when(deviceRepository).insertDevice(device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		assertEquals(SUCCESS, sut.insertDevice(sourceId, device).getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testinsertDeviceFailureSourceIdNotUnique() {
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(device);
		Mockito.when(
				nwProtocolService.getNetworkProtocol(device
						.getNetworkProtocol().getName())).thenReturn(
				device.getNetworkProtocol());

		Mockito.when(
				deviceRepository.generateUnitId(Mockito
						.any(ConfigurationSearch.class))).thenReturn(1);
		Mockito.doNothing().when(deviceRepository).insertDevice(device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		assertEquals(SUCCESS, sut.insertDevice(sourceId, device).getStatus());
	}

	@Test
	public void testupdateDeviceSuccess() {
		Mockito.when(
				deviceRepository.getDevice(Mockito.anyString(),
						Mockito.any(Subscription.class))).thenReturn(device);
		Mockito.when(
				nwProtocolService.getNetworkProtocol(device
						.getNetworkProtocol().getName())).thenReturn(
				device.getNetworkProtocol());

		Mockito.when(
				datasourceRegistrationService.isDatasourceExist(Mockito
						.anyString())).thenReturn(false);

		ProtocolVersion protocolVersion = new ProtocolVersion();
		Mockito.when(
				deviceProtocolService.getDeviceProtocolVersion(device
						.getVersion())).thenReturn(protocolVersion);

		Mockito.when(
				deviceRepository.generateUnitId(Mockito
						.any(ConfigurationSearch.class))).thenReturn(1);

		Mockito.doNothing().when(deviceRepository).insertDevice(device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		Mockito.doNothing().when(nwProtocolService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceProtocolService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceTypeService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceRepository)
				.updateDevice(sourceId, device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		assertEquals(SUCCESS, sut.updateDevice(sourceId, device).getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testupdateDeviceFailureOnNonExistingDevice() {
		Mockito.when(deviceRepository.getDevice(sourceId)).thenReturn(null);
		Mockito.when(
				nwProtocolService.getNetworkProtocol(device
						.getNetworkProtocol().getName())).thenReturn(
				device.getNetworkProtocol());

		Mockito.when(
				deviceRepository.generateUnitId(Mockito
						.any(ConfigurationSearch.class))).thenReturn(1);
		Mockito.doNothing().when(deviceRepository).insertDevice(device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		Mockito.doNothing().when(nwProtocolService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceProtocolService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceTypeService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceRepository)
				.updateDevice(sourceId, device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		assertEquals(SUCCESS, sut.updateDevice(sourceId, device).getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testupdateDeviceFailureOnNonExistingDeviceProtocol() {

		Mockito.when(
				deviceRepository.getDevice(Mockito.anyString(),
						Mockito.any(Subscription.class))).thenReturn(device);
		Mockito.when(
				nwProtocolService.getNetworkProtocol(device
						.getNetworkProtocol().getName())).thenReturn(
				device.getNetworkProtocol());

		Mockito.when(
				datasourceRegistrationService.isDatasourceExist(Mockito
						.anyString())).thenReturn(false);

		Mockito.when(
				deviceProtocolService.getDeviceProtocolVersion(device
						.getVersion())).thenReturn(null);

		Mockito.when(
				deviceRepository.generateUnitId(Mockito
						.any(ConfigurationSearch.class))).thenReturn(1);

		Mockito.doNothing().when(deviceRepository).insertDevice(device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		Mockito.doNothing().when(nwProtocolService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceProtocolService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceTypeService)
				.updateDeviceRelation(device);
		Mockito.doNothing().when(deviceRepository)
				.updateDevice(sourceId, device);
		Mockito.doNothing().when(deviceTagService).updateDeviceRelation(device);
		assertEquals(SUCCESS, sut.updateDevice(sourceId, device).getStatus());
	}

	@Test
	public void testUpdateWritebackConfSuccess() {
		Mockito.when(
				deviceRepository.getDevice(Mockito.anyString(),
						Mockito.any(Subscription.class))).thenReturn(device);
		assertEquals(SUCCESS, sut.updateWritebackConf(sourceId, device)
				.getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testUpdateWritebackConfFailureOnInvalidIP() {
		device.setIp("somestring");
		assertEquals(SUCCESS, sut.updateWritebackConf(sourceId, device)
				.getStatus());
	}

	@Test
	public void testgetDatasourceNameSuccess() {
		Mockito.when(deviceRepository.getDatasourceName(subscription, sourceId))
				.thenReturn(datasourceName);
		assertEquals(datasourceName,
				sut.getDatasourceName(subscription, sourceId));
	}

	@Test(expected = DeviceCloudException.class)
	public void testgetDatasourceNameFailure() {
		Mockito.when(deviceRepository.getDatasourceName(subscription, sourceId))
				.thenReturn(null);
		assertEquals(datasourceName,
				sut.getDatasourceName(subscription, sourceId));
	}

	@Test
	public void testGetDevicesOfProtocolVersion() {
		Mockito.when(
				deviceRepository.getAllDeviceOfProtocol(
						Mockito.any(Subscription.class),
						Mockito.any(ConfigurationSearch.class))).thenReturn(
				devices);
		assertEquals(
				devices.size(),
				sut.getDevicesOfProtocolVersion(subscription,
						configurtionSearch).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDevicesOfProtocolVersionNoResult() {
		Mockito.when(
				deviceRepository.getAllDeviceOfProtocol(
						Mockito.any(Subscription.class),
						Mockito.any(ConfigurationSearch.class))).thenReturn(
				null);
		assertEquals(
				devices.size(),
				sut.getDevicesOfProtocolVersion(subscription,
						configurtionSearch).size());
	}
}
