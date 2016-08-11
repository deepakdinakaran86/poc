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

import java.util.Set;

/**
 * Interface for defining methods needed in any MapStoreRepo
 *
 * @author pcseg199(Javid Ahammed)
 * @date Apr 13, 2015
 * @since galaxy-1.0.0
 */
public interface MapStoreRepoI<K, V> {

	/**
	 * To return all the keys of the specified from database
	 *
	 * @return
	 */
	public Set<K> getAllKeys();

	/**
	 * To return value of the specified key
	 *
	 * @param key
	 * @return
	 */
	public V getValueOfkey(K key);
}
