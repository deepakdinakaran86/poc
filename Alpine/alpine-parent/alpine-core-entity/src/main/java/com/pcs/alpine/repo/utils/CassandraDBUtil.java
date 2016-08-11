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
package com.pcs.alpine.repo.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.pcs.alpine.model.udt.FieldMap;

/**
 * CassandraDBUtils
 * 
 * @description Utility Class for cassandra connection
 * @author Riyas (PCSEG296)
 * @date 10 November 2014
 * @since galaxy-1.0.0
 */

@Component
public class CassandraDBUtil {

	@Value("${cassandra.contact-point}")
	public String contactPoints;

	@Value("${cassandra.keyspace-name}")
	public String keySpace;

	private Session session;

	private Cluster cluster;

	private MappingManager mappingManager;

	public Session getSession() {
		return this.session;
	}

	public Cluster getCluster() {
		return this.cluster;
	}

	@PostConstruct
	public void setup() {
		cluster = Cluster.builder().addContactPoints(contactPoints)
		        .withProtocolVersion(ProtocolVersion.V3).build();
		session = cluster.connect(keySpace);
		mappingManager = new MappingManager(session);
		mappingManager.udtCodec(FieldMap.class);
	}

	public MappingManager getMappingManager() {
		return mappingManager;
	}

	public void forTest() {
		cluster = Cluster.builder().addContactPoints("10.234.31.170")
		        .withProtocolVersion(ProtocolVersion.V3).build();
		session = cluster.connect("galaxy_master");
		mappingManager = new MappingManager(session);
		mappingManager.udtCodec(FieldMap.class);
	}

}