package com.pcs.avocado.commons.dto;

/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
*
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/


import java.io.Serializable;

/**
 * SubscriptionContext provides abstraction for SubscriptionProfileService
 * 
 * @author Twinkle(PCSEG297)
 * @date September 2015
 * @since galaxy-1.0.0
 */
public final class SubscriptionContext implements Serializable {

	private static final long serialVersionUID = 6397515461742804605L;
	
	private Subscription subscription;
	
	public SubscriptionContext() {
	}
	
	public SubscriptionContext(Subscription subscription) {
		this.subscription = subscription;
	}
	
	public Subscription getSubscription(){
		return subscription;
	}
	
	/**
	 * This services can be used to fetch endUserDomain from
	 * SubscriptionContext.
	 * 
	 * @return <String> endUserDomain
	 */
	public String getEndUserDomain() {
		return subscription.getEndUserDomain();
	}
	
}
