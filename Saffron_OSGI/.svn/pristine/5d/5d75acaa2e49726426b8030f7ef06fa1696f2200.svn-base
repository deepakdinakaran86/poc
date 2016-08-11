/**
 * 
 */
package com.pcs.saffron.manager.autoclaim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.autoclaim.api.request.AutoClaimRequest;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

/**
 * @author pcseg310
 *
 */
public class AutoClaimService {

	private static final Logger logger = LoggerFactory.getLogger(AutoClaimService.class);

	public void claimAsset(DefaultConfiguration configuration) {
		if (configuration == null || configuration.getDevice()==null) {
			return;
		}

		String sourceId = configuration.getDevice().getSourceId();
		AutoClaimRequest request = new AutoClaimRequest();
		String pathUrl = request.buildConfigurationUrl(sourceId);

		logger.info("Path URL {}",pathUrl);
		Client client = ApacheRestClient.builder().host(request.getHostIp())
												  .port(request.getPort())
												  .scheme(request.getScheme())
												  .build();
		JsonNode status = null;
		try {
			status = client.post(pathUrl, null, configuration.getConfigPoints(), JsonNode.class);
			logger.info("Autoclaim configuration status :(Not significant) {}",status!=null?status.textValue():"N/A");
		} catch (ClientException  e) {
			logger.error("Exception occured while updating the Autoclaim configuration for device id [" + sourceId + "] in platform",
			        e);
		}
	}
}
