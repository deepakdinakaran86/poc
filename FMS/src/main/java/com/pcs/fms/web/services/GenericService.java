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

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.fms.web.client.FMSResponse;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class GenericService extends BaseService {

	public FMSResponse<JsonNode> getObject(String url) {
		String serviceUri = getServiceURI(url);
		return getPlatformClient().getResource(serviceUri, JsonNode.class);
	}

	public FMSResponse<JsonNode> postObject(String url, Object payload) {
		String serviceUri = getServiceURI(url);
		return getPlatformClient().postResource(serviceUri, payload,
		        JsonNode.class);
	}

	public FMSResponse<JsonNode> putObject(String url, Object payload) {
		String serviceUri = getServiceURI(url);
		return getPlatformClient().putResource(serviceUri, payload,
		        JsonNode.class);
	}

	public FMSResponse<JsonNode> deleteObject(String url) {
		String serviceUri = getServiceURI(url);
		return getPlatformClient().deleteResource(serviceUri, JsonNode.class);
	}
}
