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
package com.pcs.datasource.repository;

import com.pcs.datasource.dto.Subscription;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */
public interface SubscriptionRepository {

	/**
	 * Repository method to validate that the subscription is existing in
	 * database
	 * 
	 * @param subId
	 * @return flag
	 */
	public boolean isSubscriptionIdExist(String subId);

	/**
	 * Repository method to create a new subscription entry in the system
	 * 
	 * @param subscription
	 */
	public void createSubscription(Subscription subscription);

}
