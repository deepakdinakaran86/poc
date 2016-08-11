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
package com.pcs.subscription.web.client;

import static org.apache.commons.lang.StringUtils.defaultString;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * GalaxyPlatformClientFactory provides {@link DataSourceClient} to the Services
 * to communicate with platform apis in HTTP
 * 
 * @author pcseg296 RIYAS PH
 * @date October 2015
 * @since alpine-1.0.0
 * 
 */
public class DataSourceClientFactory implements FactoryBean<DataSourceClient> {

	private static Logger logger = LoggerFactory
	        .getLogger(DataSourceClientFactory.class);

	private DataSourceClient dataClient;

	private String datasourceServicesHost;

	private Integer datasourceServicesPort;

	private String datasourceServicesScheme;

	private String datasourceServicesContextPath;

	private String clientId;

	private String clientSecret;

	public DataSourceClient getObject() {
		return dataClient;
	}

	public Class<DataSourceClient> getObjectType() {
		return DataSourceClient.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public DataSourceClient getDataClient() {
		return dataClient;
	}

	public void setDataClient(DataSourceClient dataClient) {
		this.dataClient = dataClient;
	}

	public String getDatasourceServicesHost() {
		return datasourceServicesHost;
	}

	public void setDatasourceServicesHost(String datasourceServicesHost) {
		this.datasourceServicesHost = datasourceServicesHost;
	}

	public Integer getDatasourceServicesPort() {
		return datasourceServicesPort;
	}

	public void setDatasourceServicesPort(Integer datasourceServicesPort) {
		this.datasourceServicesPort = datasourceServicesPort;
	}

	public String getDatasourceServicesScheme() {
		return datasourceServicesScheme;
	}

	public void setDatasourceServicesScheme(String datasourceServicesScheme) {
		this.datasourceServicesScheme = datasourceServicesScheme;
	}

	public String getDatasourceServicesContextPath() {
		return datasourceServicesContextPath;
	}

	public void setDatasourceServicesContextPath(
	        String datasourceServicesContextPath) {
		this.datasourceServicesContextPath = datasourceServicesContextPath;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getPlatformBaseURI() {

		String platformBaseUri = null;

		try {
			URIBuilder uriBuilder = new URIBuilder()
			        .setScheme(getDatasourceServicesScheme())
			        .setHost(getDatasourceServicesHost())
			        .setPath(defaultString(getDatasourceServicesContextPath()));
			if (getDatasourceServicesPort() != null) {
				uriBuilder.setPort(getDatasourceServicesPort());
			}

			platformBaseUri = uriBuilder.build().toString();
		} catch (URISyntaxException e) {
			logger.error("Error occurred while building the platform base uri",
			        e);
		}

		return platformBaseUri;
	}
}