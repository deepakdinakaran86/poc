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
package com.pcs.alpine.commons.serviceImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.dto.SubscriptionContextHolder;
import com.pcs.alpine.commons.service.SubscriptionProfileService;

/**
 * SubscriptionProfileServiceImpl provides implementation of
 * SubscriptionProfileService contract.
 * 
 * @author Twinkle(PCSEG297)
 * @date September 2015
 * @since galaxy-1.0.0
 */
@Service
public class SubscriptionProfileServiceImpl
        implements
            SubscriptionProfileService {

	/**
	 * This services will be invoked to fetch endUserDomain from
	 * SubscriptionContext.
	 * 
	 * @return <String> endUserDomain
	 */
	@Override
	public String getEndUserDomain() {
		return SubscriptionContextHolder.getContext().getEndUserDomain();
	}

	/**
	 * This services will be invoked to fetch SubscriptionContext.
	 * 
	 * @return <Subscription> subscription
	 */
	@Override
	public Subscription getSubscription() {
		return SubscriptionContextHolder.getContext().getSubscription();
	}

	@Override
	public String getEndUserName() {
		// TODO to null check
		return SubscriptionContextHolder.getContext().getSubscription()
		        .getEndUserName();
	}

	@Override
	public Map<String, String> getJwtToken() {
		return SubscriptionContextHolder.getJwtToken();
	}
}
