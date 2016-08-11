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
import com.pcs.datasource.repository.NetworkProtocolRepository;

/**
 * Test class for NetworkProtocolServiceImpl
 * 
 * @description Test Class for NetworkProtocolServiceImpl
 * @author Greeshma (pcseg323)
 * @date 17 Feb 2015
 * @since galaxy-1.0.0
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NetworkProtocolServiceImplTest {

	@InjectMocks
	private NetworkProtocolServiceImpl networkProtocolServiceImpl;

	@Mock
	private NetworkProtocolRepository networkProtocolRepository;

	NetworkProtocol networkProtocol;
	List<NetworkProtocol> networkProtocols;
	Device device;
	DeviceProtocol deviceProtocol;
	DeviceType deviceType;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		networkProtocol = new NetworkProtocol();
		networkProtocols = new ArrayList<NetworkProtocol>();
		device = new Device();
		deviceProtocol = new DeviceProtocol();
		deviceType = new DeviceType();

		networkProtocol.setName("TCP");
		networkProtocols.add(networkProtocol);

		deviceType.setName("G2021");
		deviceProtocol.setName("EDCP");
		deviceProtocol.setDeviceType(deviceType);
		deviceProtocol.setVersion("EDCP");

		device.setDatasourceName("datasourceName");
		device.setDeviceId("025bdbc8-dfde-4090-9ea5-9431c591655f");
		device.setIsPublishing(true);
		device.setNetworkProtocol(networkProtocol);
		// device.setProtocol(deviceProtocol);
		device.setUnitId(1);
	}

	/**
	 * Test method updateDeviceRelation
	 */
	@Test
	public void testUpdateDeviceRelation() {

		Mockito.doNothing().when(networkProtocolRepository)
				.updateDeviceNwProtocol(device);
		;
		networkProtocolServiceImpl.updateDeviceRelation(device);

	}

	/**
	 * Test method GetNwProtocol
	 */
	@Test
	public void testGetNwProtocol() {

		Mockito.when(networkProtocolRepository.getNwProtocol("TCP"))
				.thenReturn(networkProtocol);
		assertEquals(networkProtocol.getName(), networkProtocolServiceImpl
				.getNetworkProtocol("TCP").getName());

	}

}
