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
package com.pcs.datasource.repository.titan;

import static com.pcs.datasource.repository.utils.GremlinConstants.SUBSCRIPTION;

import java.util.HashMap;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.SubscriptionRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg323(Greeshma)
 * @date Dec 2015
 */

@Repository("subscriptionTitan")
public class SubscriptionTitanRepositoryImpl implements SubscriptionRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	private static final String getSubsciprion = "g.V().hasLabel(vertexLabel).has('subId',subId).valueMap()";

	private static final String createSubsciprion = "graph.addVertex(label,vertexLabel,'subId',subId,'subscriber',subscriber)";

	/**
	 * method to validate that the subscription is existing in database
	 * 
	 * @param subId
	 * @return {@link Boolean}
	 */
	public boolean isSubscriptionIdExist(String subId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vertexLabel", SUBSCRIPTION);
		params.put("subId", subId);
		Client client = titanSessionManager.getClient();
		try {
			ResultSet resultSet = client.submit(getSubsciprion, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return false;
			}
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return true;
	}

	/**
	 * method to create subscription
	 *
	 * @param {@link Subscription}
	 */
	@Override
	public void createSubscription(Subscription subscription) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("vertexLabel", SUBSCRIPTION);
		params.put("subId", subscription.getSubId());
		params.put("subscriber", subscription.getsubscriber());

		try {
			Client client = titanSessionManager.getClient();
			client.submit(createSubsciprion, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

}
