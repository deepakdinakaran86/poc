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

import static java.lang.Integer.parseInt;
import static org.apache.tinkerpop.gremlin.driver.Cluster.build;

import javax.annotation.PostConstruct;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Cluster.Builder;
import org.apache.tinkerpop.gremlin.driver.ser.Serializers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TitanSessionManager
 * 
 * @description Utility Class for Gremlin Driver Client
 * @author Javid Ahammed (PCSEG199)
 * @date Oct 2015
 * @since alpine-1.0.0
 */

@Component
public class TitanSessionManager {

	@Value("${gremlin.server.ip}")
	public String gremlinAddress;

	@Value("${gremlin.server.port}")
	public String gremlinPort;

	Client client = null;

	public Client getClient() {
		if (client == null) {
			setup();
		}
		return client;
	}

	@PostConstruct
	public void setup() {
		Builder build = build(gremlinAddress).port(
				parseInt(gremlinPort));
		build.serializer(Serializers.GRAPHSON);
		Cluster cluster = build.create();
		client = cluster.connect();
	}

}