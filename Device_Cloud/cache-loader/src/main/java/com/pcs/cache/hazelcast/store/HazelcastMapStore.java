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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.hazelcast.core.MapStore;

/**
 * Default implementation of MapStore
 *
 * This class defines what all the Custom MapStore should implement to meet with
 * the cache design
 *
 * @author pcseg199 (Javid Ahammed)
 * @date Apr 13, 2015
 * @since galaxy-1.0.0
 */
public abstract class HazelcastMapStore<K, V> implements MapStore<K, V> {

	/*
	 * (non-Javadoc)
	 * @see com.hazelcast.core.MapLoader#loadAll(java.util.Collection)
	 */
	@Override
	public Map<K, V> loadAll(Collection<K> keys) {
		Map<K, V> dataStores = new HashMap<K, V>();
		for (K key : keys) {
			V value = load(key);
			dataStores.put(key, value);
		}
		return dataStores;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hazelcast.core.MapStore#store(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public final void store(K key, V value) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.hazelcast.core.MapStore#storeAll(java.util.Map)
	 */
	@Override
	public final void storeAll(Map<K, V> map) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.hazelcast.core.MapStore#delete(java.lang.Object)
	 */
	@Override
	public final void delete(K key) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.hazelcast.core.MapStore#deleteAll(java.util.Collection)
	 */
	@Override
	public final void deleteAll(Collection<K> keys) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.hazelcast.core.MapLoader#load(java.lang.Object)
	 */
	@Override
	public abstract V load(K key);

	/*
	 * (non-Javadoc)
	 * @see com.hazelcast.core.MapLoader#loadAllKeys()
	 */
	@Override
	public abstract Set<K> loadAllKeys();

}
