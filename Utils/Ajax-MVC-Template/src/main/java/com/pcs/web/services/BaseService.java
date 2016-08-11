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
package com.pcs.web.services;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.web.client.DataSourceClient;
import com.pcs.web.client.DataSourceClientFactory;

/**
 * Base service provides all the utilities needed for service implementation
 * 
 * @author PCSEG296 RIYAS PH
 * @date October 2015
 * @since Alpine-1.0.0
 * 
 */
@Service
public abstract class BaseService {

	@Autowired
	private DataSourceClientFactory restTemplateFactory;

	protected Logger logger = LoggerFactory.getLogger(this.getClass()
	        .getSimpleName());

	protected final DataSourceClientFactory getRestTemplateFactory() {
		return restTemplateFactory;
	}

	protected final void setRestTemplateFactory(
	        DataSourceClientFactory restTemplateFactory) {
		this.restTemplateFactory = restTemplateFactory;
	}

	protected final DataSourceClient getPlatformClient() {
		return getRestTemplateFactory().getDataClient();
	}

	protected final String getPlatformBaseURI() {
		return getRestTemplateFactory().getPlatformBaseURI();
	}

	protected final String getServiceURI(String endpointUri) {
		String serviceUri = getPlatformBaseURI();
		if (isNotBlank(endpointUri)) {
			serviceUri = serviceUri.concat(endpointUri);
		}
		return serviceUri;
	}

}
