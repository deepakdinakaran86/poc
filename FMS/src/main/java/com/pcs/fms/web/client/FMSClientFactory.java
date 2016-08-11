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
package com.pcs.fms.web.client;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
public class FMSClientFactory implements FactoryBean<FMSClient> {

	private static Logger logger = LoggerFactory
	        .getLogger(FMSClientFactory.class);

	private FMSClient dataClient;

	private String fmsPlatformServicesApim;
	private String fmsAuthServicesApim;

	public FMSClient getObject() {
		return dataClient;
	}

	public Class<FMSClient> getObjectType() {
		return FMSClient.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public FMSClient getDataClient() {
		return dataClient;
	}

	public void setDataClient(FMSClient dataClient) {
		this.dataClient = dataClient;
	}

	public String getFmsPlatformServicesApim() {
		return fmsPlatformServicesApim;
	}

	public void setFmsPlatformServicesApim(String fmsPlatformServicesApim) {
		this.fmsPlatformServicesApim = fmsPlatformServicesApim;
	}

	public String getFmsAuthServicesApim() {
		return fmsAuthServicesApim;
	}

	public void setFmsAuthServicesApim(String fmsAuthServicesApim) {
		this.fmsAuthServicesApim = fmsAuthServicesApim;
	}

	public String getPlatformBaseURI() {

		String platformBaseUri = null;

		try {
			URIBuilder uriBuilder = new URIBuilder()
			        .setPath(getFmsPlatformServicesApim());

			platformBaseUri = uriBuilder.build().toString();
		} catch (URISyntaxException e) {
			logger.error("Error occurred while building the platform base uri",
			        e);
		}

		return platformBaseUri;
	}

	public String getAuthURI() {

		String platformBaseUri = null;

		try {
			URIBuilder uriBuilder = new URIBuilder()
			        .setPath(getFmsAuthServicesApim());

			platformBaseUri = uriBuilder.build().toString();
		} catch (URISyntaxException e) {
			logger.error("Error occurred while building the platform base uri",
			        e);
		}

		return platformBaseUri;
	}
}