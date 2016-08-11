package com.pcs.ccd.heartbeat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.ccd.heartbeat.beans.ServicesConfig;
import com.pcs.saffron.commons.http.ApacheRestClient;

public class HeartbeatUtil {
	private static final Logger logger = LoggerFactory
	        .getLogger(HeartbeatUtil.class);
	
	private static boolean initzd = false;
	private static ServicesConfig servicesConfig;
	
	public static void readConfig() {
		try {

			String filePath = "/services.yaml";
			servicesConfig = YamlUtils.copyYamlFromClassPath(
			        ServicesConfig.class, filePath);
			initzd = true;
		} catch (Exception e) {
			logger.error("Error while reading services config yaml file", e);
		}
	}
	
	public static com.pcs.ccd.heartbeat.beans.ServicesConfig getServicesConfig() {
		if (!initzd) {
			readConfig();
		}
		return servicesConfig;
	}
	
	public static ApacheRestClient buildRestClient() {
		ApacheRestClient httpClient;
		httpClient = (ApacheRestClient)ApacheRestClient.builder()
		        .host(HeartbeatUtil.getServicesConfig().getHost())
		        .port(Integer.parseInt(HeartbeatUtil.getServicesConfig().getPort()))
		        .scheme(HeartbeatUtil.getServicesConfig().getScheme()).build();
		return httpClient;
	}

}
