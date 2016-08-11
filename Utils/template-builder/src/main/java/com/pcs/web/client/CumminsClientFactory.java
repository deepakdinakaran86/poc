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
package com.pcs.web.client;

import static org.apache.commons.lang.StringUtils.defaultString;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * CumminsClientFactory provides {@link CumminsClient} to the Services to
 * communicate with platform apis in HTTP
 * 
 * @author PCSEG296 RIYAS PH
 * @date October 2015
 * @since Alpine-1.0.0
 */
public class CumminsClientFactory implements FactoryBean<CumminsClient> {

	private static Logger logger = LoggerFactory
	        .getLogger(CumminsClientFactory.class);

	private CumminsClient dataClient;

	private String cumminsServicesHost;

	private Integer cumminsServicesPort;

	private String cumminsServicesScheme;

	private String cumminsServicesContextPath;

	public CumminsClient getObject() {
		return dataClient;
	}

	public Class<CumminsClient> getObjectType() {
		return CumminsClient.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public CumminsClient getDataClient() {
		return dataClient;
	}

	public void setDataClient(CumminsClient dataClient) {
		this.dataClient = dataClient;
	}

	public String getCumminsServicesHost() {
		return cumminsServicesHost;
	}

	public void setCumminsServicesHost(String cumminsServicesHost) {
		this.cumminsServicesHost = cumminsServicesHost;
	}

	public Integer getCumminsServicesPort() {
		return cumminsServicesPort;
	}

	public void setCumminsServicesPort(Integer cumminsServicesPort) {
		this.cumminsServicesPort = cumminsServicesPort;
	}

	public String getCumminsServicesScheme() {
		return cumminsServicesScheme;
	}

	public void setCumminsServicesScheme(String cumminsServicesScheme) {
		this.cumminsServicesScheme = cumminsServicesScheme;
	}

	public String getCumminsServicesContextPath() {
		return cumminsServicesContextPath;
	}

	public void setCumminsServicesContextPath(String cumminsServicesContextPath) {
		this.cumminsServicesContextPath = cumminsServicesContextPath;
	}

	public String getPlatformBaseURI() {

		String platformBaseUri = null;

		try {
			URIBuilder uriBuilder = new URIBuilder()
			        .setScheme(getCumminsServicesScheme())
			        .setHost(getCumminsServicesHost())
			        .setPath(defaultString(getCumminsServicesContextPath()));
			if (getCumminsServicesPort() != null) {
				uriBuilder.setPort(getCumminsServicesPort());
			}

			platformBaseUri = uriBuilder.build().toString();
		} catch (URISyntaxException e) {
			logger.error("Error occurred while building the platform base uri",
			        e);
		}

		return platformBaseUri;
	}
}