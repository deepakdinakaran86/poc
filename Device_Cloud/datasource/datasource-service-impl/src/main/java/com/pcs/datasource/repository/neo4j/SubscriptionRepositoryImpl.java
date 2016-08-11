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
package com.pcs.datasource.repository.neo4j;

import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.repository.utils.CypherConstants.CREATE_NODE_QUERY;
import static com.pcs.datasource.repository.utils.CypherConstants.NODE_LABEL;
import static com.pcs.datasource.repository.utils.CypherConstants.SUB_ID;
import static com.pcs.datasource.repository.utils.DBConstansts.SUBSCRIPTION;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;

import java.io.IOException;

import javax.persistence.PersistenceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.SubscriptionRepository;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg296(Riyas PH)
 * @date 7 july 2015
 */

@Repository("subscriptionNeo")
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String getSubsciprion = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}) RETURN sub;";

	/**
	 * method to validate that the subscription is existing in database
	 * 
	 * @param subId
	 * @return flag
	 */
	@Override
	public boolean isSubscriptionIdExist(String subId) {
		String query = getSubsciprion.replace(SUB_ID, subId);
		JSONArray subJsonArray = null;
		try {
			subJsonArray = exexcuteQuery(neo4jURI, query, null, ROW.toString());
		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in fetching parameter details", e);
		}
		if (subJsonArray == null) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SubscriptionRepository#createSubscription
	 * (com.pcs.datasource.dto.Subscription)
	 */
	@Override
	public void createSubscription(Subscription subscription) {
		String query = CREATE_NODE_QUERY.replace(NODE_LABEL, SUBSCRIPTION);
		JSONArray subJsonArray = null;

		try {
			String json = objectBuilderUtil.getLowerCaseUnderScoreGson()
			        .toJson(subscription);
			subJsonArray = exexcuteQuery(neo4jURI, query, json, ROW.toString());
		} catch (Exception e) {
			new PersistenceException("Error in creating subscription", e);
		}

		if (subJsonArray == null) {
			new PersistenceException(
			        "Error in creating subscription,Response is null");
		}

	}
}
