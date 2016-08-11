package com.pcs.device.activity.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.activity.beans.ServicesConfig;
import com.pcs.saffron.commons.http.ApacheRestClient;

public class ActivityUpdaterUtil {
	private static final Logger logger = LoggerFactory
	        .getLogger(ActivityUpdaterUtil.class);
	
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
	
	public static com.pcs.device.activity.beans.ServicesConfig getServicesConfig() {
		if (!initzd) {
			readConfig();
		}
		return servicesConfig;
	}
	
	public static ApacheRestClient buildRestClient() {
		ApacheRestClient httpClient;
		httpClient = (ApacheRestClient)ApacheRestClient.builder()
		        .host(getServicesConfig().getHost())
		        .port(Integer.parseInt(getServicesConfig().getPort()))
		        .scheme(getServicesConfig().getScheme()).build();
		return httpClient;
	}
	
	

}
