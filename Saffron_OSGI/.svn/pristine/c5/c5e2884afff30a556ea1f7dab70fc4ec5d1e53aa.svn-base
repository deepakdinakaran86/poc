/**
 * 
 */
package com.pcs.saffron.manager.api.writeback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.api.configuration.bean.StatusMessage;
import com.pcs.saffron.manager.bean.WritebackConfiguration;
import com.pcs.saffron.manager.bean.WritebackResponse;

/**
 * @author pcseg171
 *
 */
public class WritebackService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WritebackService.class);
	
	
	public JsonNode updateWritebackResponse(WritebackResponse writebackResponse){

		if (writebackResponse == null) {
			return null;
		}

		WritebackStatusUpdateRequest request = new WritebackStatusUpdateRequest();
		request.setSourceId(writebackResponse.getSourceId());
		String pathUrl = request.buildConfigurationUrl();

		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		JsonNode status = null;
		try {
			status = client.put(pathUrl, null, writebackResponse, JsonNode.class);
			LOGGER.info("Updating WB Response to DB, response is {} ",new ObjectMapper().writeValueAsString(writebackResponse));
			return status;
		} catch (ClientException e) {
			LOGGER.error("Exception updating writeback response for  [" + writebackResponse.getSourceId() + "] to the platform",
					e);
			return null;
		} catch (JsonProcessingException e) {
			LOGGER.error("Exception Converting response  [" + writebackResponse.getSourceId() + "] to the platform",
					e);
			return null;
		}finally{
			client = null;
		}
	}
	
	
	public StatusMessage updateConfiguration(WritebackConfiguration writebackConfiguration){

		if (writebackConfiguration == null) {
			return null;
		}

		WritebackConfigUpdateRequest request = new WritebackConfigUpdateRequest();
		request.setSourceId(writebackConfiguration.getSourceId());
		String pathUrl = request.buildConfigurationUrl();

		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		StatusMessage status = null;
		try {
			status = client.put(pathUrl, null, writebackConfiguration, StatusMessage.class);
			return status;
		} catch (ClientException e) {
			LOGGER.error("Exception updating writeback response for  [" + writebackConfiguration.getSourceId() + "] to the platform",
					e);
			return null;
		}finally{
			client = null;
		}
	}

}
