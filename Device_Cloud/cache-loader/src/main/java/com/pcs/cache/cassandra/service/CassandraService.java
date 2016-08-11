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
package com.pcs.cache.cassandra.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;

/**
 * Utility for connecting to Cassandra KeySpace based on configuration
 *
 * @author pcseg199(Javid Ahammed)
 * @date Apr 12, 2015
 * @since galaxy-1.0.0
 */
public class CassandraService {

	private static Session session;

	private static final String filePath = System.getProperty("user.dir")
	        + File.separator + "cassandra.yaml";

	/**
     *
     */
	public static Session getSession() {

		if (session == null) {
			try {
				Yaml yaml = new Yaml();
				@SuppressWarnings("unchecked") Map<String, Object> load = (HashMap<String, Object>)yaml
				        .load(new FileInputStream(filePath));
				Cluster cluster = Cluster
				        .builder()
				        .addContactPoints(
				                load.get("cassandra.keyspace.ip").toString())
				        .withProtocolVersion(ProtocolVersion.V3).build();
				session = cluster.connect(load.get("cassandra.keyspace.name")
				        .toString());
			} catch (Exception e) {
				throw new RuntimeException("Cassandra configuration not found",
				        e);
			}

		}
		return session;

	}
}
