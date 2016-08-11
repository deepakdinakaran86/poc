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
import static java.util.Collections.EMPTY_LIST;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pcs.datasource.dto.AccessType;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.ConfigureDevice;
import com.pcs.datasource.dto.DataType;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.GeneralBatchResponse;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.Make;
import com.pcs.datasource.dto.Model;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.dto.SystemTag;
import com.pcs.datasource.repository.SystemRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * Test class for SystemServiceImpl
 * 
 * @description Test Class for SystemServiceImpl
 * @author Javid Ahammed (pcseg199)
 * @date Sep 2015
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemServiceImplTest {

	@InjectMocks
	SystemServiceImpl sut;

	@Mock
	@Qualifier("neo4j")
	SystemRepository systemRepository;

	@Mock
	DeviceConfigService deviceConfigService;

	ConfigurationSearch conSearch;

	List<DataType> dataTypes;

	List<SystemTag> allSystemTags;

	private ConfigureDevice configureDevice;

	private Subscription subscription;

	private DeviceConfigTemplate confTemplate;

	private List<String> sourceIds;

	private GeneralBatchResponse batchResponse;

	private StatusMessageDTO confTempExists;

	private Make make;

	@SuppressWarnings("rawtypes")
	private List makes;

	private DeviceType deviceType;

	private List<DeviceType> deviceTypes;

	private Model model;

	private List<Model> models;

	private DeviceProtocol deviceProtocol;

	private List<DeviceProtocol> deviceProtocols;

	private ProtocolVersion protocolVersion;

	private List<ProtocolVersion> protocolVersions;

	private ConfigurationSearch configurtionSearch;

	private List<Point> points;

	private Point point;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		dataTypes = new ArrayList<DataType>();
		DataType dataType1 = new DataType();
		dataTypes.add(dataType1);

		allSystemTags = new ArrayList<SystemTag>();

		SystemTag systemTag1 = new SystemTag();
		allSystemTags.add(systemTag1);

		configureDevice = new ConfigureDevice();

		confTemplate = new DeviceConfigTemplate();
		confTemplate.setName("template Name");
		List<ConfigPoint> configPoints = new ArrayList<ConfigPoint>();
		confTemplate.setConfigPoints(configPoints);

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

		make = new Make();
		make.setName("Make_name");
		makes = new ArrayList<Make>();
		makes.add(make);

		deviceType = new DeviceType();
		deviceType.setName("Type_name");
		deviceTypes = new ArrayList<DeviceType>();
		deviceTypes.add(deviceType);

		model = new Model();
		model.setName("Modele Name");
		models = new ArrayList<Model>();
		models.add(model);

		deviceProtocol = new DeviceProtocol();
		deviceProtocol.setName("protocol");
		deviceProtocols = new ArrayList<DeviceProtocol>();
		deviceProtocols.add(deviceProtocol);

		protocolVersion = new ProtocolVersion();
		protocolVersion.setName("version");
		protocolVersions = new ArrayList<ProtocolVersion>();
		protocolVersions.add(protocolVersion);

		points = new ArrayList<Point>();
		points.add(point);
	}

	@Test
	public void testGetAllDataTypes() {

		List<DataType> dataTypes = new ArrayList<DataType>();
		DataType dataType1 = new DataType();
		dataTypes.add(dataType1);
		Mockito.when(
				systemRepository.getAllOfAType(Mockito.anyString(),
						DataType.class)).thenReturn(dataTypes);

		assertEquals(dataTypes.size(), sut.getAllDataTypes().size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetAllDataTypesNoResult() {

		Mockito.when(
				systemRepository.getAllOfAType(Mockito.anyString(),
						DataType.class)).thenReturn(null);

		assertEquals(dataTypes.size(), sut.getAllDataTypes().size());
	}

	@Test
	public void testGetAllSystemTags() {

		Mockito.when(systemRepository.getAllSystemTags(Mockito.anyString()))
				.thenReturn(allSystemTags);

		assertEquals(allSystemTags.size(), sut.getAllSystemTags("Temperature")
				.size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetAllSystemTagsNoResult() {

		Mockito.when(systemRepository.getAllSystemTags(Mockito.anyString()))
				.thenReturn(null);

		assertEquals(allSystemTags.size(), sut.getAllSystemTags("Temperature")
				.size());
	}

	@Test
	public void testGetAllAccesstypes() {
		List<AccessType> accessTypes = new ArrayList<AccessType>();
		AccessType accessType = new AccessType();
		accessTypes.add(accessType);
		Mockito.when(
				systemRepository.getAllOfAType(Mockito.anyString(),
						AccessType.class)).thenReturn(accessTypes);

		assertEquals(allSystemTags.size(), sut.getAllAccessTypes().size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetAllAccesstypesNoResult() {

		Mockito.when(
				systemRepository.getAllOfAType(Mockito.anyString(),
						AccessType.class)).thenReturn(null);

		assertEquals(allSystemTags.size(), sut.getAllAccessTypes().size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDeviceMakesSuccess() {
		Mockito.when(
				systemRepository.getAllOfAType(Mockito.anyString(),
						Make.class)).thenReturn(makes);
		assertEquals(makes.size(), sut.getDeviceMakes().size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceMakesFailureNull() {
		Mockito.when(
				systemRepository.getAllOfAType(Mockito.anyString(),
						Make.class)).thenReturn(null);
		assertEquals(makes.size(), sut.getDeviceMakes().size());
	}

	@SuppressWarnings("unchecked")
	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceMakesFailureEmpty() {
		Mockito.when(
				systemRepository.getAllOfAType(Mockito.anyString(),
						Make.class)).thenReturn(EMPTY_LIST);
		assertEquals(makes.size(), sut.getDeviceMakes().size());
	}

	@Test
	public void testGetDeviceTypesSuccess() {
		Mockito.when(systemRepository.getDeviceTypes(make.getName()))
				.thenReturn(deviceTypes);
		assertEquals(deviceTypes.size(), sut.getDeviceTypes(make.getName())
				.size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceTypesFailureParamNull() {
		Mockito.when(systemRepository.getDeviceTypes(make.getName()))
				.thenReturn(deviceTypes);
		assertEquals(deviceTypes.size(), sut.getDeviceTypes(null).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceTypesFailureReturnNull() {
		Mockito.when(systemRepository.getDeviceTypes(make.getName()))
				.thenReturn(null);
		assertEquals(deviceTypes.size(), sut.getDeviceTypes(make.getName())
				.size());
	}

	@Test
	public void testGetDeviceModelsSuccess() {
		Mockito.when(systemRepository.getDeviceModels(configurtionSearch))
				.thenReturn(models);
		assertEquals(models.size(), sut.getDeviceModels(configurtionSearch)
				.size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceModelsFailureParamNull() {
		Mockito.when(systemRepository.getDeviceModels(configurtionSearch))
				.thenReturn(models);
		assertEquals(models.size(), sut.getDeviceModels(null).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceModelsFailureReturnNull() {
		Mockito.when(systemRepository.getDeviceModels(configurtionSearch))
				.thenReturn(null);
		assertEquals(models.size(), sut.getDeviceModels(configurtionSearch)
				.size());
	}

	@Test
	public void testGetDeviceProtocolsSuccess() {
		Mockito.when(systemRepository.getDeviceProtocols(configurtionSearch))
				.thenReturn(deviceProtocols);
		assertEquals(deviceProtocols.size(),
				sut.getDeviceProtocols(configurtionSearch).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceProtocolsFailureParamNull() {
		Mockito.when(systemRepository.getDeviceProtocols(configurtionSearch))
				.thenReturn(deviceProtocols);
		assertEquals(deviceProtocols.size(), sut.getDeviceProtocols(null)
				.size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDeviceProtocolsFailureReturnNull() {
		Mockito.when(systemRepository.getDeviceProtocols(configurtionSearch))
				.thenReturn(null);
		assertEquals(deviceProtocols.size(),
				sut.getDeviceProtocols(configurtionSearch).size());
	}

	@Test
	public void testGetProtocolVersionsSuccess() {
		Mockito.when(systemRepository.getProtocolVersions(configurtionSearch))
				.thenReturn(protocolVersions);
		assertEquals(protocolVersions.size(),
				sut.getProtocolVersions(configurtionSearch).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetProtocolVersionsFailureParamNull() {
		Mockito.when(systemRepository.getProtocolVersions(configurtionSearch))
				.thenReturn(protocolVersions);
		assertEquals(protocolVersions.size(), sut.getProtocolVersions(null)
				.size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetProtocolVersionsFailureReturnNull() {
		Mockito.when(systemRepository.getProtocolVersions(configurtionSearch))
				.thenReturn(null);
		assertEquals(protocolVersions.size(),
				sut.getProtocolVersions(configurtionSearch).size());
	}

	@Test
	public void testGetDevicePointsSuccess() {
		Mockito.when(
				systemRepository.getProtocolVersionPoint(configurtionSearch))
				.thenReturn(points);
		assertEquals(points.size(),
				sut.getProtocolVersionPoint(configurtionSearch).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDevicePointsFailureParamNull() {
		Mockito.when(
				systemRepository.getProtocolVersionPoint(configurtionSearch))
				.thenReturn(points);
		assertEquals(points.size(), sut.getProtocolVersionPoint(null).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetDevicePointsFailureReturnNull() {
		Mockito.when(
				systemRepository.getProtocolVersionPoint(configurtionSearch))
				.thenReturn(null);
		assertEquals(points.size(),
				sut.getProtocolVersionPoint(configurtionSearch).size());
	}
}
