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
import com.pcs.saffron.manager.authentication.RegistrationService;
import com.pcs.saffron.manager.registration.bean.DeviceRegistrationData;

/**
 * @author pcseg296
 * 
 */
public class DeviceAutoClaimService implements RegistrationService {

	private static final Logger logger = LoggerFactory
	        .getLogger(DeviceAutoClaimService.class);

	public boolean register(DeviceRegistrationData deviceRegistrationData) {
		boolean registered = false;
		if (deviceRegistrationData == null
		        || deviceRegistrationData.getDevice() == null
		        || StringUtils.isBlank(deviceRegistrationData.getDevice()
		                .getSourceId())) {
			logger.warn("Could not register device!!Device or device source id must not be null!!");
			return registered;
		}

		CumminsRegistrationRequest request = new CumminsRegistrationRequest();
		String pathUrl = request.buildRegistrationUrl();
		logger.info("Registration request recieved {}", pathUrl);
		Client client = ApacheRestClient.builder().host(request.getHostIp())
		        .port(request.getPort()).scheme(request.getScheme()).build();
		logger.info("Registration request after client recieved {}", pathUrl);
		JsonNode status = null;
		try {
			status = client.post(pathUrl, null,
			        deviceRegistrationData.getDevice(), JsonNode.class);
			logger.info("Device {} registration status : {}",
			        deviceRegistrationData.getDevice().getSourceId(), status);
			if (status.get("status").asText().equalsIgnoreCase("SUCCESS")) {
				registered = true;
			}
		} catch (Exception e) {
			logger.error("Exception occured while auto claiming a new device ["
			        + deviceRegistrationData.getDevice().getSourceId() + "]");
			logger.error("Exception Message : {}", e.getMessage());
			registered = false;
		}
		return registered;
	}

}
