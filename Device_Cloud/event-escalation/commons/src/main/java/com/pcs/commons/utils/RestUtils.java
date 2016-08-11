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
package com.pcs.commons.utils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.commons.beans.RestMessage;
import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;

/**
 * Utility Class for Rest Based Requests
 * 
 * @author pcseg199
 * @date Mar 25, 2015
 * @since galaxy-1.0.0
 */
public class RestUtils {

	static Client client = ApacheRestClient.builder().build();

	public static void sendGETRequest(RestMessage restDTO) throws Exception {

		if (restDTO != null && isNotBlank(restDTO.getEndPoint())) {
			client.get(restDTO.getEndPoint(), null, JsonNode.class);
		} else {
			throw new Exception("Invalid Request");
		}

	}

	public static void sendPOSTRequest(RestMessage restDTO) throws Exception {

		if (restDTO != null && isNotBlank(restDTO.getEndPoint())
		        && isNotBlank(restDTO.getContent())) {
			client.post(restDTO.getEndPoint(), null, restDTO.getContent(),
			        JsonNode.class);
		} else {
			throw new Exception("Invalid Request");
		}

	}

	public static void sendPUTRequest(RestMessage restDTO) throws Exception {

		if (restDTO != null && isNotBlank(restDTO.getEndPoint())
		        && isNotBlank(restDTO.getContent())) {
			client.put(restDTO.getEndPoint(), null, restDTO.getContent(),
			        JsonNode.class);
		} else {
			throw new Exception("Invalid Request");
		}

	}

	public static void sendDELETERequest(RestMessage restDTO) throws Exception {

		if (restDTO != null && isNotBlank(restDTO.getEndPoint())) {
			client.delete(restDTO.getEndPoint(), null, JsonNode.class);
		} else {
			throw new Exception("Invalid Request");
		}

	}

}
