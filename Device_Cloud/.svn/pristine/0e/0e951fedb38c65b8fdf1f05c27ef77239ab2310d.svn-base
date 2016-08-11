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
package com.pcs.cache.neo4j.repository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pcs.cache.neo4j.service.Neo4jExecuter;

/**
 * Data store name stored against physical quantity name
 * 
 * @author pcseg199(Javid Ahammed)
 * @date Apr 13, 2015
 * @since galaxy-1.0.0
 */
public class PQDataStoreMapRepository implements MapStoreRepoI<String, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.deviceframework.cache.hazelcast.repository.MapStoreRepoI#getAllKeys
	 * ()
	 */

	String getAllPQQuery = "MATCH (n:PHYSICAL_QUANTITY) return {name:n.name}";
	String getPQQuery = "MATCH (n:PHYSICAL_QUANTITY {name:'{pqName}'}) return {data_store:n.data_store};";

	@Override
	public Set<String> getAllKeys() {

		JSONArray phyQuantities = null;
		try {
			phyQuantities = Neo4jExecuter.exexcuteQuery(getAllPQQuery, null,
					"row");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		Set<String> dataStores = new HashSet<String>();
		int noOfPQs = phyQuantities.length();
		if (noOfPQs > 0) {

			for (int i = 0; i < noOfPQs; i++) {
				JSONObject jsonObject = phyQuantities.getJSONObject(i);
				dataStores.add(jsonObject.getJSONArray("row").getJSONObject(0)
						.getString("name"));

			}
		}
		return dataStores;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.cache.cassandra.repository.MapStoreRepoI#getValueOfkey(java.lang
	 * .Object)
	 */
	@Override
	public String getValueOfkey(String key) {
		JSONArray phyQuantity = null;
		String dataStoreName = null;
		try {
			String query = getPQQuery.replace("{pqName}", key);
			phyQuantity = Neo4jExecuter.exexcuteQuery(query, null, "row");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		int noOfPQs = phyQuantity.length();
		if (noOfPQs > 0) {
			JSONObject jsonObject = phyQuantity.getJSONObject(0);
			dataStoreName = jsonObject.getJSONArray("row").getJSONObject(0)
					.getString("data_store");
		}
		return dataStoreName;
	}

}
