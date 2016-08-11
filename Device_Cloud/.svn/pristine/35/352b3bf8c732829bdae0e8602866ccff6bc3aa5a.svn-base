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
package com.pcs.datasource.repository.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

/**
 * CassandraDBUtils
 *
 * @description Utility Class for cassandra connection
 * @author Riyas (PCSEG296)
 * @date 10 November 2014
 * @since galaxy-1.0.0
 */

@Component
public class CassandraSessionManager {

	private Map<String, String> contactPoints;

	private Map<String, String> keySpaces;

	private final Map<String, Session> sessions = new HashMap<String, Session>();

	private final Map<String, MappingManager> mappingManagers = new HashMap<String, MappingManager>();

	@PostConstruct
	public void setup() {

		Set<String> keySet = contactPoints.keySet();
		for (Object element : keySet) {
			String key = (String)element;
			Cluster cluster = Cluster.builder()
			        .addContactPoints(contactPoints.get(key))
			        .withProtocolVersion(ProtocolVersion.V3).build();
			Session session = cluster.connect(keySpaces.get(key));
			sessions.put(key, session);
			MappingManager mappingManager = new MappingManager(session);
			mappingManagers.put(key, mappingManager);
		}
	}

	public Map<String, String> getContactPoints() {
		return contactPoints;
	}

	public void setContactPoints(Map<String, String> contactPoints) {
		this.contactPoints = contactPoints;
	}

	public Map<String, String> getKeySpaces() {
		return keySpaces;
	}

	public void setKeySpaces(Map<String, String> keySpaces) {
		this.keySpaces = keySpaces;
	}

	public Session getSession(String cassandraReference) {
		return this.sessions.get(cassandraReference);
	}

	public MappingManager getMappingManager(String cassandraReference) {
		return this.mappingManagers.get(cassandraReference);
	}
}