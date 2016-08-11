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

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.dto.writeback.BatchCommand;
import com.pcs.datasource.dto.writeback.Command;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.repository.WriteBackRepository;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * Test class for write back related services
 * 
 * @description Test Class for WriteBackLogServiceImpl
 * @author Greeshma (pcseg323)
 * @date May 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WriteBackLogServiceImplTest {

	@InjectMocks
	private WriteBackLogServiceImpl writeBackLogServiceImpl;

	@Mock
	private WriteBackRepository writeBackRepository;

	String subId;
	Long startTime;
	Long endTime;
	WriteBackLog writeBackLog;
	List<WriteBackLog> writeBackLogs;
	BatchCommand batchCommand;
	Command command;
	Device device;
	NetworkProtocol networkProtocol;
	DeviceProtocol deviceProtocol;
	DeviceType deviceType;
	JSONArray jsonArray;
	JSONObject jsonObject;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		subId = "1";
		startTime = 1432684800000L;
		endTime = 1432857600000L;
		writeBackLog = new WriteBackLog();
		writeBackLogs = new ArrayList<WriteBackLog>();
		batchCommand = new BatchCommand();
		command = new Command();
		device = new Device();
		networkProtocol = new NetworkProtocol();
		deviceProtocol = new DeviceProtocol();
		deviceType = new DeviceType();
		jsonArray = new JSONArray();
		jsonObject = new JSONObject(
		        "{\"graph\":\"nodes\",\"properties\":\"request_id\"}");

		networkProtocol.setName("TCP");
		deviceProtocol.setName("EDCP");
		deviceProtocol.setDeviceType(deviceType);
		deviceProtocol.setVersion("EDCP");

		batchCommand.setBatchId(1);
		batchCommand.setRequestedAt(startTime);
		batchCommand.setStatus("QUEUED");

		command.setCommand("Server Command");
		command.setCustomTag("customTag");
		command.setDataType("BOOLEAN");
		command.setPointId((short)18);
		command.setRequestedAt(startTime);
		command.setRequestId((short)2);
		command.setStatus("QUEUED");
		command.setValue("true");

		device.setDatasourceName("datasourceName");
		device.setDeviceId("025bdbc8-dfde-4090-9ea5-9431c591655f");
		device.setIsPublishing(true);
		device.setNetworkProtocol(networkProtocol);
		//device.setProtocol(deviceProtocol);
		device.setUnitId(1);

		writeBackLog.setBatch(batchCommand);
		writeBackLog.setCommand(command);
		writeBackLog.setDevice(device);

		writeBackLogs.add(writeBackLog);

		jsonArray.put(jsonObject);

	}

	/**
	 * Test method GetAllLogs (fetch data specific to Grid)
	 */
	@Test
	public void testGetAllLogs() {

		Mockito.when(writeBackRepository.getAllLogs(subId, startTime, endTime))
		        .thenReturn(writeBackLogs);
		assertEquals(writeBackLogs.size(),
		        writeBackLogServiceImpl.getAllLogs(subId, startTime, endTime)
		                .size());

	}

	/**
	 * Test method GetAllLogs (fetch data with relationship)
	 */
	@Test
	public void testGetAllLogsWithRelation() {

		Mockito.when(
		        writeBackRepository.getAllLogsWithRelation(subId, startTime,
		                endTime)).thenReturn(jsonArray);
		assertEquals(jsonArray.length(), writeBackLogServiceImpl
		        .getAllLogsWithRelation(subId, startTime, endTime).length());

	}

	/**
	 * Test method GetAllLogs with Null subscription id (fetch data specific to
	 * Grid)
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetAllLogsWithNullSubId() {

		subId = null;
		Mockito.when(writeBackRepository.getAllLogs(subId, startTime, endTime))
		        .thenReturn(null);
		assertEquals(writeBackLogs.size(),
		        writeBackLogServiceImpl.getAllLogs(subId, startTime, endTime)
		                .size());

	}

	/**
	 * Test method GetAllLogs with Null End time (fetch data specific to Grid)
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetAllLogsWithNullEndTime() {

		endTime = null;
		Mockito.when(writeBackRepository.getAllLogs(subId, startTime, endTime))
		        .thenReturn(null);
		assertEquals(writeBackLogs.size(),
		        writeBackLogServiceImpl.getAllLogs(subId, startTime, endTime)
		                .size());

	}

	/**
	 * Test method GetAllLogs with Null startTime(fetch data specific to Grid)
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetAllLogsWithNullStartTime() {

		startTime = null;
		Mockito.when(writeBackRepository.getAllLogs(subId, startTime, endTime))
		        .thenReturn(null);
		assertEquals(writeBackLogs.size(),
		        writeBackLogServiceImpl.getAllLogs(subId, startTime, endTime)
		                .size());

	}

	/**
	 * Test method GetAllLogs with startTime > endTime (fetch data specific to
	 * Grid)
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetAllLogsWithInvalidTime() {

		Mockito.when(writeBackRepository.getAllLogs(subId, endTime, startTime))
		        .thenReturn(null);
		assertEquals(writeBackLogs.size(),
		        writeBackLogServiceImpl.getAllLogs(subId, startTime, endTime)
		                .size());

	}

	/**
	 * Test method GetAllLogs with No data available(fetch data specific to
	 * Graph)
	 */
	@Test(expected = DeviceCloudException.class)
	public void testGetAllLogsWithNoData() {

		Mockito.when(writeBackRepository.getAllLogs(subId, startTime, endTime))
		        .thenReturn(null);
		assertEquals(writeBackLogs.size(),
		        writeBackLogServiceImpl.getAllLogs(subId, startTime, endTime)
		                .size());

	}
}
