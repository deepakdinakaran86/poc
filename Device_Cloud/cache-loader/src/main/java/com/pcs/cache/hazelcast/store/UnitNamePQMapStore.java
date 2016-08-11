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
package com.pcs.cache.hazelcast.store;

import java.util.Set;

import com.pcs.cache.neo4j.repository.UnitNamePQMapRepository;

/**
 * MapStore with unit name as the key and physical qty name as the value
 * 
 * This will be a Map<UnitName,PQ Name>
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date Apr 13, 2015
 * @since galaxy-1.0.0
 */
public class UnitNamePQMapStore extends HazelcastMapStore<String, String> {

	private UnitNamePQMapRepository unitNamePQMapRepository = new UnitNamePQMapRepository();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.deviceframework.cache.hazelcast.stores.HazelCastMapStore#load
	 * (java.lang.Object)
	 */
	@Override
	public String load(String key) {
		return unitNamePQMapRepository.getValueOfkey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.deviceframework.cache.hazelcast.stores.HazelCastMapStore#loadAllKeys
	 * ()
	 */
	@Override
	public Set<String> loadAllKeys() {
		return unitNamePQMapRepository.getAllKeys();
	}

}
