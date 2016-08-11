/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.auth.api;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.device.gateway.G2021.devicemanager.auth.RegistrationRequest;
import com.pcs.device.gateway.G2021.devicemanager.auth.RegistrationService;
import com.pcs.device.gateway.G2021.devicemanager.bean.Device;
import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;

/**
 * @author pcseg310
 *
 */
public class DeviceRegistrationService implements RegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceRegistrationService.class);

	@Override
	public boolean register(Device device) {
		boolean registered = false;
		if (device == null || StringUtils.isBlank(device.getSourceId())) {
			logger.warn("Could not register device!!Device or device source id must not be null!!");
			return registered;
		}

		String sourceId = device.getSourceId();
		RegistrationRequest request = new RegistrationRequest();
		String pathUrl = request.buildRegistrationUrl(sourceId);

		Client client = ApacheRestClient.builder().host(request.getHostIp()).port(request.getPort())
				.scheme(request.getScheme()).build();
		JsonNode status = null;
		try {
			status = client.post(pathUrl, null, device, JsonNode.class);
			logger.info("Device {} registration status : {}", sourceId, status);
			if (status.get("status").asText().equalsIgnoreCase("SUCCESS")) {
				registered = true;
			}
		} catch (ClientException e) {
			logger.error("Exception occured while registring a new device [" + sourceId + "]");
			registered = false;
		}
		return registered;
	}

}
