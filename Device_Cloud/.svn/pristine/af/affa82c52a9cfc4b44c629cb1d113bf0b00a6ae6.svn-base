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

import static com.pcs.devicecloud.enums.Status.FAILURE;
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

import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.ParameterRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */
@ContextConfiguration("classpath:datasource-app-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParameterServiceImplTest {

	@InjectMocks
	private ParameterServiceImpl paramSerice;

	@Mock
	private ParameterRepository paramRepository;

	@Mock
	private PhyQuantityService phyQuantityService;

	@Mock
	private SubscriptionService subscriptionService;

	private String subId;

	private ParameterDTO parameter;

	private Subscription subscription;

	private List<ParameterDTO> parameters;

	private StatusMessageDTO status;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		subId = "1";
		parameter = new ParameterDTO();
		parameter.setName("parameterName");
		parameter.setPhysicalQuantity("PQ1");
		parameter.setDataType("String");

		subscription = new Subscription();
		subscription.setSubId(subId);
		parameter.setSubscription(subscription);

		parameters = new ArrayList<ParameterDTO>();
		parameters.add(parameter);

		status = new StatusMessageDTO();

	}

	@Test
	public void testGetParametersSuccess() {
		Mockito.when(paramRepository.getParameters(subId)).thenReturn(
		        parameters);
		assertEquals(parameters.size(), paramSerice.getParameters(subscription)
		        .size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetParametersFailureNullParam() {
		Mockito.when(paramRepository.getParameters(subId)).thenReturn(
		        parameters);
		assertEquals(parameters.size(), paramSerice.getParameters(null).size());
	}

	@Test(expected = DeviceCloudException.class)
	public void testGetParametersFailureNullReturn() {
		Mockito.when(paramRepository.getParameters(subId)).thenReturn(null);
		assertEquals(parameters.size(), paramSerice.getParameters(subscription)
		        .size());
	}

	@Test
	public void testIsParameterExistSuccess() {
		status.setStatus(SUCCESS);
		Mockito.when(paramRepository.getParameter(parameter.getName(), subId))
		        .thenReturn(parameter);
		assertEquals(status.getStatus(),
		        paramSerice.isParameterExist(parameter.getName(), subscription)
		                .getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testIsParameterExistFailureNullParam() {
		Mockito.when(paramRepository.getParameter(parameter.getName(), subId))
		        .thenReturn(parameter);
		assertEquals(parameter,
		        paramSerice.isParameterExist(parameter.getName(), null));
	}

	@Test
	public void testIsParameterExistFailureNullReturn() {
		status.setStatus(FAILURE);
		Mockito.when(paramRepository.getParameter(parameter.getName(), subId))
		        .thenReturn(null);
		assertEquals(status.getStatus(),
		        paramSerice.isParameterExist(parameter.getName(), subscription)
		                .getStatus());
	}

	@Test
	public void testSaveParameterSuccess() {
		status.setStatus(SUCCESS);
		Mockito.doNothing().when(paramRepository).saveParameter(parameter);
		Mockito.when(paramRepository.isDataTypeExist(parameter.getDataType()))
		        .thenReturn(true);
		Mockito.when(
		        subscriptionService.isSubscriptionIdExist(parameter
		                .getSubscription().getSubId().toString())).thenReturn(
		        true);
		Mockito.when(
		        paramRepository.getParameter(parameter.getName(), parameter
		                .getSubscription().getSubId().toString())).thenReturn(
		        null);
		assertEquals(status.getStatus(), paramSerice.saveParameter(parameter)
		        .getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testSaveParameterFailureSubExists() {
		status.setStatus(SUCCESS);
		Mockito.doNothing().when(paramRepository).saveParameter(parameter);
		Mockito.when(paramRepository.isDataTypeExist(parameter.getDataType()))
		        .thenReturn(false);
		Mockito.when(
		        subscriptionService.isSubscriptionIdExist(parameter
		                .getSubscription().getSubId().toString())).thenReturn(
		        true);
		Mockito.when(
		        paramRepository.getParameter(parameter.getName(), parameter
		                .getSubscription().getSubId().toString())).thenReturn(
		        null);
		assertEquals(status.getStatus(), paramSerice.saveParameter(parameter)
		        .getStatus());
	}

	@Test(expected = DeviceCloudException.class)
	public void testSaveParameterFailureParamExist() {
		status.setStatus(SUCCESS);
		Mockito.doNothing().when(paramRepository).saveParameter(parameter);
		Mockito.when(paramRepository.isDataTypeExist(parameter.getDataType()))
		        .thenReturn(false);
		Mockito.when(
		        subscriptionService.isSubscriptionIdExist(parameter
		                .getSubscription().getSubId().toString())).thenReturn(
		        false);
		Mockito.when(
		        paramRepository.getParameter(parameter.getName(), parameter
		                .getSubscription().getSubId().toString())).thenReturn(
		        parameter);
		assertEquals(status.getStatus(), paramSerice.saveParameter(parameter)
		        .getStatus());
	}

}
