
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
package com.pcs.cache.test;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.pcs.datasource.model.Unit;

/**
 * This class is responsible for ..(Short Description)
 *
 * @author pcseg199
 * @date Apr 14, 2015
 * @since galaxy-1.0.0
 */
public class Test {

	public static void main(String[] args) {
		// PQStore pqStore = new PQStore();
		// List<String> emptyList = Collections.EMPTY_LIST;
		// pqStore.loadAll(emptyList);
		// System.out.println(load);

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getGroupConfig().setName("test-mapstore")
		        .setPassword("test-mapstore");
		clientConfig.getNetworkConfig().addAddress("10.234.60.27:5701");
		HazelcastInstance newHazelcastClient = HazelcastClient
		        .newHazelcastClient(clientConfig);

		IMap<String, String> map = newHazelcastClient
		        .getMap("physical_quantity");
		System.out.println("map Size" + map.size());
		long start = System.nanoTime();

		IMap<String, Unit> unitMap = newHazelcastClient.getMap("units");

		// Unit key = new Unit();
		// key.setUnit("chummaUnit");
		// PhyQuantity value = new PhyQuantity();
		// value.setDataStore("chummaStore");
		// unitMap.put(key, value);

		System.out.println("unitMap Size" + unitMap.size());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("unitMap Size" + unitMap.size());
		//

		start = System.nanoTime();

		Unit unit = unitMap.get("Seconds");
		System.out.println("timeTaken :" + (System.nanoTime() - start));
		System.out.println(unit);

		Unit unit1 = unitMap.get("celcius");
		System.out.println("timeTaken :" + (System.nanoTime() - start));
		System.out.println(unit1);

		for (int i = 0; i < 50; i++) {
			unitMap.get("celcius");
		}

		// PQMapStore pqMapStore = new PQMapStore();
		//
		// pqMapStore.loadAll(pqMapStore.loadAllKeys());
	}
}
