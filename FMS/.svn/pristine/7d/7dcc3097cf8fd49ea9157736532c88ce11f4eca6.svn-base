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
package com.pcs.fms.web.services;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.fms.web.client.FMSClient;
import com.pcs.fms.web.client.FMSClientFactory;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public abstract class BaseService {

	@Autowired
	private FMSClientFactory restTemplateFactory;

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass()
	        .getSimpleName());

	protected final FMSClientFactory getRestTemplateFactory() {
		return restTemplateFactory;
	}

	protected final void setRestTemplateFactory(
	        FMSClientFactory restTemplateFactory) {
		this.restTemplateFactory = restTemplateFactory;
	}

	protected final FMSClient getPlatformClient() {
		return getRestTemplateFactory().getDataClient();
	}

	protected final String getPlatformBaseURI() {
		return getRestTemplateFactory().getPlatformBaseURI();
	}

	protected final String getAuthBaseURI() {
		return getRestTemplateFactory().getAuthURI();
	}

	protected final String getServiceURI(String endpointUri) {
		String serviceUri = getPlatformBaseURI();
		if (isNotBlank(endpointUri)) {
			serviceUri = serviceUri.concat(endpointUri);
		}
		return serviceUri;
	}

	protected final String getAuthURI(String endpointUri) {
		String serviceUri = getAuthBaseURI();
		if (isNotBlank(endpointUri)) {
			serviceUri = serviceUri.concat(endpointUri);
		}
		return serviceUri;
	}

}
