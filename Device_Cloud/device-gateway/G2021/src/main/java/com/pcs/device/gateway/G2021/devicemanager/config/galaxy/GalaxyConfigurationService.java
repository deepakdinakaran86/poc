/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.config.galaxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.devicemanager.config.ConfigurationRequest;
import com.pcs.device.gateway.G2021.devicemanager.config.ConfigurationService;
import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;

/**
 * @author pcseg310
 *
 */
public class GalaxyConfigurationService implements ConfigurationService {

	private static final Logger logger = LoggerFactory.getLogger(GalaxyConfigurationService.class);

	@Override
	public void updateConfiguration(G2021DeviceConfiguration configuration) {
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
			status = client.post(pathUrl, null, configuration.getPointConfigurations(), JsonNode.class);
			logger.info("Update configuration status :(Not significant) "+status);
		} catch (ClientException e) {
			logger.error("Exception occured while updating the configuration for device id [" + sourceId + "] in platform",
			        e);
		}
	}
}
