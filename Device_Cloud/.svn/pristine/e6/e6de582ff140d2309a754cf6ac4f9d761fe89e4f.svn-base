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

import com.pcs.datasource.dto.CommandType;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.NetworkProtocol;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.repository.DeviceProtocolRepository;

/**
 * Test class for device protocol related services
 * 
 * @description Test Class for DeviceProtocolServiceImpl
 * @author Greeshma (pcseg323)
 * @date May 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceProtocolServiceImplTest {

	@InjectMocks
	private DeviceProtocolServiceImpl deviceProtocolServiceImpl;

	@Mock
	private DeviceProtocolRepository deviceProtocolRepository;

	private DeviceType deviceType;
	private DeviceProtocol deviceProtocol;
	private List<DeviceProtocol> deviceProtocols;
	private Device device;
	private NetworkProtocol networkProtocol;
	private CommandType commandType;
	private List<CommandType> commandTypes;
	private Point point;
	private List<Point> points;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		deviceType = new DeviceType();
		deviceProtocol = new DeviceProtocol();
		deviceProtocols = new ArrayList<DeviceProtocol>();
		device = new Device();
		networkProtocol = new NetworkProtocol();
		commandType = new CommandType();
		commandTypes = new ArrayList<CommandType>();
		point = new Point();
		points = new ArrayList<Point>();

		deviceProtocol.setName("EDCP");
		deviceProtocol.setDeviceType(deviceType);
		deviceProtocol.setVersion("EDCP");

		deviceProtocols.add(deviceProtocol);

		networkProtocol.setName("TCP");

		deviceType.setName("G2021");

		device.setDatasourceName("datasourceName");
		device.setDeviceId("025bdbc8-dfde-4090-9ea5-9431c591655f");
		device.setIsPublishing(true);
		device.setNetworkProtocol(networkProtocol);
		device.setUnitId(1);

		commandType.setCreatedAt("1432684800000");
		commandType.setName("Server Command");
		commandType.setSystemCmd(true);

		commandTypes.add(commandType);

		point.setCreatedAt(1432684800000L);
		point.setDataType("BOOLEAN");
		point.setPointId("1");
		point.setPointName("DO1");
		point.setUnit("Kelvin");

		points.add(point);
	}

	/**
	 * Test method updateDeviceRelation
	 */
	@Test
	public void testUpdateDeviceRelation() {

		Mockito.doNothing().when(deviceProtocolRepository)
				.updateDeviceRelation(device);
		;
		deviceProtocolServiceImpl.updateDeviceRelation(device);

	}
}
