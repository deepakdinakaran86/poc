/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.device.gateway.G2021.devicemanager.device.bean.WritebackConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.device.bean.WritebackResponse;
import com.pcs.device.gateway.G2021.devicemanager.device.config.WritebackConfigUpdateRequest;
import com.pcs.device.gateway.G2021.devicemanager.device.config.WritebackStatusUpdateRequest;
import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;

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
			return status;
		} catch (ClientException e) {
			LOGGER.error("Exception updating writeback response for  [" + writebackResponse.getSourceId() + "] to the platform",
					e);
			return null;
		}finally{
			client = null;
		}
	}
	
	
	public JsonNode updateConfiguration(WritebackConfiguration writebackConfiguration){

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
		JsonNode status = null;
		try {
			status = client.put(pathUrl, null, writebackConfiguration, JsonNode.class);
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
