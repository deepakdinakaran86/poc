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

import static com.pcs.datasource.enums.DeviceDataFields.DATA_TYPE;
import static com.pcs.datasource.enums.DeviceDataFields.NAME;
import static com.pcs.datasource.enums.DeviceDataFields.PARAMETER_NAME;
import static com.pcs.datasource.enums.DeviceDataFields.PHYSICAL_QUANTIY;
import static com.pcs.datasource.enums.DeviceDataFields.SUBSCRIPTION;
import static com.pcs.datasource.enums.DeviceDataFields.SUB_ID;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryInnerFields;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateResult;
import static com.pcs.devicecloud.enums.Status.FAILURE;
import static com.pcs.devicecloud.enums.Status.SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.ParameterRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */
@Service
public class ParameterServiceImpl implements ParameterService {

	@Autowired
	@Qualifier("paramNeo")
	private ParameterRepository parameterRepository;
	@Autowired
	private PhyQuantityService phyQuantityService;
	@Autowired
	private SubscriptionService subscriptionService;

	/**
	 * method to create a parameter to the system under a subscription
	 * 
	 * @param parameterDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO saveParameter(ParameterDTO parameterDTO) {
		validateMandatoryFields(parameterDTO, NAME, PHYSICAL_QUANTIY,
		        DATA_TYPE, SUBSCRIPTION);
		validateMandatoryInnerFields(SUBSCRIPTION,
		        parameterDTO.getSubscription(), SUB_ID);
		try {
			phyQuantityService.getPhyQuantity(parameterDTO
			        .getPhysicalQuantity());
		} catch (DeviceCloudException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        PHYSICAL_QUANTIY.getDescription());
		}
		if (!parameterRepository.isDataTypeExist(parameterDTO.getDataType())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        DATA_TYPE.getDescription());
		}
		if (!subscriptionService.isSubscriptionIdExist(parameterDTO
		        .getSubscription().getSubId().toString())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SUB_ID.getDescription());
		}
		ParameterDTO parameter = parameterRepository.getParameter(
		        parameterDTO.getName(), parameterDTO.getSubscription()
		                .getSubId().toString());
		if (parameter != null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_IS_NOT_UNIQUE,
			        PARAMETER_NAME.getDescription());
		}
		parameterRepository.saveParameter(parameterDTO);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;
	}

	/**
	 * method to get all parameters of a subscription
	 * 
	 * @param subId
	 * @return List<ParameterDTO>
	 */
	@Override
	public List<ParameterDTO> getParameters(Subscription subscription) {
		validateMandatoryInnerFields(SUBSCRIPTION, subscription, SUB_ID);
		List<ParameterDTO> parameters = parameterRepository
		        .getParameters(subscription.getSubId());
		validateResult(parameters);
		return parameters;
	}

	/**
	 * method is responsible to confirm parameter is existing in database or
	 * note
	 * 
	 * @param paramName
	 * @param subId
	 * @return StatusMessageDTO
	 */
	@Override
	public StatusMessageDTO isParameterExist(String paramName,
	        Subscription subscription) {
		validateMandatoryField(NAME, paramName);
		validateMandatoryInnerFields(SUBSCRIPTION, subscription, SUB_ID);
		ParameterDTO parameter = parameterRepository.getParameter(paramName,
		        subscription.getSubId());
		StatusMessageDTO status = new StatusMessageDTO();
		status.setStatus(SUCCESS);
		if (parameter == null) {
			status.setStatus(FAILURE);
		}
		return status;
	}

}
