/**
 * 
 */
package com.pcs.saffron.manager.api.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

/**
 * @author pcseg310
 *
 */
public class ConfigurationService {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

	public void updateConfiguration(DefaultConfiguration configuration) {
		if (configuration == null || configuration.getDevice()==null) {
			return;
		}

		String sourceId = configuration.getDevice().getSourceId();
		ConfigurationRequest request = new ConfigurationRequest();
		String pathUrl = request.buildConfigurationUrl(sourceId);

		
		Client client = ApacheRestClient.builder().host(request.getHostIp())
												  .port(request.getPort())
												  .scheme(request.getScheme())
												  .build();
		JsonNode status = null;
		try {
			logger.info("Data {}",new ObjectMapper().writeValueAsString(configuration.getConfigPoints()));
			status = client.put(pathUrl, null, configuration.getConfigPoints(), JsonNode.class);
			logger.info("Update configuration status :(Not significant) {}",status!=null?status.textValue():"N/A");
		} catch (ClientException | JsonProcessingException e) {
			logger.error("Exception occured while updating the configuration for device id [" + sourceId + "] in platform",
			        e);
		}
	}
}
