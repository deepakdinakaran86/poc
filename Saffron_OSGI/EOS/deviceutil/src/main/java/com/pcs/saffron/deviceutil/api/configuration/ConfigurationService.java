/**
 * 
 */
package com.pcs.saffron.deviceutil.api.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.deviceutil.api.configuration.request.ConfigurationRequest;
import com.pcs.saffron.deviceutil.bean.ConfigurationBean;

/**
 * @author pcseg310
 *
 */
public class ConfigurationService {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

	public ConfigurationBean getConfiguration(String sourceId) {
		if (sourceId == null ) {
			return null;
		}else{

			ConfigurationRequest request = new ConfigurationRequest();
			String pathUrl = request.buildConfigurationUrl(sourceId);

			logger.info("Path URL {}",pathUrl);
			Client client = ApacheRestClient.builder().host(request.getHostIp())
					.port(request.getPort())
					.scheme(request.getScheme())
					.build();
			ConfigurationBean confguration = null;
			try {

				confguration = client.get(pathUrl, null, ConfigurationBean.class);
				return confguration;
			} catch (ClientException e) {
				logger.error("Exception occured while updating the configuration for device id [" + sourceId + "] in platform",
						e);
				return null;
			}
		}
	}
}
