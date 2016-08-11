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

import static com.pcs.datasource.enums.DeviceDataFields.SUBSCRIBER;
import static com.pcs.datasource.enums.DeviceDataFields.SUB_ID;
import static com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes.FIELD_IS_NOT_UNIQUE;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.devicecloud.enums.Status.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.SubscriptionRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	@Qualifier("subscriptionNeo")
	private SubscriptionRepository subscriptionRepository;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.SubscriptionService#isSubscriptionIdExist
	 * (java.lang.String)
	 */
	@Override
	public boolean isSubscriptionIdExist(String subId) {
		validateMandatoryField(SUB_ID, subId);
		return subscriptionRepository.isSubscriptionIdExist(subId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.SubscriptionService#createSubscription(com
	 * .pcs.datasource.dto.Subscription)
	 */
	@Override
	public StatusMessageDTO createSubscription(Subscription subscription) {
		validateMandatoryFields(subscription, SUB_ID, SUBSCRIBER);
		// Checking if the subId is unique
		if (isSubscriptionIdExist(subscription.getSubId())) {
			throw new DeviceCloudException(FIELD_IS_NOT_UNIQUE,
			        SUB_ID.getDescription());
		}
		subscriptionRepository.createSubscription(subscription);
		StatusMessageDTO statusMessageDTO = new StatusMessageDTO();
		statusMessageDTO.setStatus(SUCCESS);
		return statusMessageDTO;

	}

}
