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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.writeback.Command;
import com.pcs.datasource.dto.writeback.WriteBackLog;
import com.pcs.datasource.dto.writeback.WriteBackPointResponse;
import com.pcs.datasource.publisher.MessagePublisher;
import com.pcs.datasource.repository.utils.Neo4jExecuter;
import com.pcs.datasource.services.utils.CacheUtility;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;
import com.pcs.devicecloud.enums.Status;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.cache.CacheProvider;

/**
 * Test class for G2021 device command related services
 * 
 * @description Test Class for G2021CommandServiceImpl
 * @author Greeshma (pcseg323)
 * @date 17 Feb 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class G2021CommandServiceImplTest {

	@InjectMocks
	private G2021CommandServiceImpl sut;

	@Mock
	private CacheUtility cacheUtility;

	@Mock
	private ObjectBuilderUtil objectBuilderUtil;

	@Mock
	private MessagePublisher messagePublisher;

	@Mock
	private Cache cache;

	@Mock
	private CacheProvider cacheProvider;
	
	@Mock
	private WriteBackLogService writeBackLogService;

	private String sourceId;

	private List<Command> commands;

	private Command command;

	private Map<String, String> customSpecs;

	private Device device;

	private WriteBackPointResponse writeBackResponse;
	
	
	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		sourceId = "sourceId_020";
		commands = new ArrayList<Command>();
		command = new Command();
		commands.add(command);
		customSpecs = new HashMap<String, String>();
		command.setCustomSpecs(customSpecs);
		Mockito.when(cacheUtility.getCacheProvider()).thenReturn(cacheProvider);
		Mockito.when(cacheProvider.getCache(Mockito.anyString())).thenReturn(
				cache);
		device = new Device();
		device.setSourceId(sourceId);
		Mockito.when(cache.get(sourceId, Device.class)).thenReturn(device);
		Mockito.when(objectBuilderUtil.getGson()).thenReturn(new Gson());
		writeBackResponse = new WriteBackPointResponse();
		writeBackResponse.setRequestId((short) 12);
		writeBackResponse.setStatus(Status.QUEUED);
		Mockito.doNothing().when(messagePublisher).publishToKafkaTopic(Mockito.anyString(), Mockito.anyString());
		
	}

	@Test(expected = DeviceCloudException.class)
	public void testProcessCommandSourceIdNotSpecified() {
		customSpecs.put("commandCode", "App Save");
		sut.processCommands("", commands);
	}

	@Test(expected = DeviceCloudException.class)
	public void testProcessCommandCommandsNotSpecified() {
		customSpecs.put("commandCode", "App Save");
		sut.processCommands(sourceId, null);
	}

	@Test	
	public void testProcessCommandSuccess() {
		command.setCommand("Server Command");
		customSpecs.put("commandCode", "App Save");
		sut.processCommands(sourceId, commands);
		try {
			Mockito.when(
					Neo4jExecuter.exexcuteQuery(Mockito.anyString(),
							Mockito.anyString(), null, Mockito.anyString()))
					.thenReturn(new JSONArray());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		Mockito.when(writeBackLogService.insertLog(Mockito.any(WriteBackLog.class))).thenReturn(writeBackResponse);
	}

}
