/**
 * 
 */
package com.pcs.saffron.manager.registration.api;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.authentication.RegistrationService;
import com.pcs.saffron.manager.registration.bean.DeviceRegistrationData;

/**
 * @author pcseg310
 * 
 */
public class DeviceRegistrationService implements RegistrationService {

	private static final Logger logger = LoggerFactory
	        .getLogger(DeviceRegistrationService.class);

	public boolean register(DeviceRegistrationData deviceRegistrationData) {
		boolean registered = false;
		if (deviceRegistrationData == null
		        || deviceRegistrationData.getDevice() == null
		        || StringUtils.isBlank(deviceRegistrationData.getDevice()
		                .getSourceId())) {
			logger.warn("Could not register device!!Device or device source id must not be null!!");
			return registered;
		}

		String sourceId = deviceRegistrationData.getDevice().getSourceId();
		RegistrationRequest request = new RegistrationRequest();
		String pathUrl = request.buildRegistrationUrl(sourceId);
		logger.info("Registration request recieved {}", pathUrl);
		Client client = ApacheRestClient.builder().host(request.getHostIp())
		        .port(request.getPort()).scheme(request.getScheme()).build();
		logger.info("Registration request after client recieved {}", pathUrl);
		JsonNode status = null;
		try {
			status = client.post(pathUrl, null,
			        deviceRegistrationData.getDevice(), JsonNode.class);
			logger.info("Device {} registration status : {}", sourceId, status);
			if (status.get("status").asText().equalsIgnoreCase("SUCCESS")) {
				registered = true;
			}
		} catch (ClientException e) {
			logger.error("Exception occured while registring a new device ["
			        + sourceId + "]");
			registered = false;
		}
		return registered;
	}

}
