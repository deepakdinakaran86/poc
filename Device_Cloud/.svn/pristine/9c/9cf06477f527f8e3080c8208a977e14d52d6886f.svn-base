/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
 *
 * This software is the property of Pacific Controls  Software  Services LLC  and  its
 * suppliers. The intellectual and technical concepts contained herein are proprietary 
 * to PCSS. Dissemination of this information or  reproduction  of  this  material  is
 * strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
 * Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
 * MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.pcs.data.analyzer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.data.analyzer.bean.DeviceConfigTemplate;
import com.pcs.data.analyzer.bean.SubscriptionContext;
import com.pcs.data.analyzer.bean.Unit;
import com.pcs.data.analyzer.bootstrap.ApplicationConfig;
import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.api.configuration.bean.Status;
import com.pcs.saffron.manager.authentication.bean.DeviceAuthenticationResponse;
import com.pcs.saffron.manager.bean.DeviceConfiguration;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class AnalyticsUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnalyticsUtil.class);

	static final String DEVICE_CONFIGURATION_CACHE = "analyzer.devicemanager.cache.configuration";
	static final String DEVICE_DATASOURCE_CACHE = "data.analytics.device.datasource.cache";
	static final String PHYSICAL_QUANTITY_CACHE = "data.analytics.physicalquantity.cache";

	public static DeviceConfiguration getDeviceConfiguration(Object identifier) {
		validateSourceIdentifier(identifier);

		Cache deviceConfigurationCache = ApplicationConfig.getCacheProvider()
				.getCache(DEVICE_CONFIGURATION_CACHE);

		DeviceConfiguration configuration = deviceConfigurationCache.get(
				identifier, DeviceConfiguration.class);
		if (configuration == null) {
			LOGGER.error(
					"Unable to get device point configuration from cache for {}",
					identifier);
			configuration = getConfig(identifier.toString());
			putDeviceConfig(identifier.toString(),configuration);
		}
		return configuration;
	}

	private static void validateSourceIdentifier(Object sourceIdentifier) {
		if (sourceIdentifier == null || sourceIdentifier.toString().isEmpty()) {
			throw new IllegalArgumentException(
					"Source identifier cant't be NULL or empty");
		}
	}

	public static String getDeviceUUID(Object identifier) {
		Cache deviceCache = ApplicationConfig.getCacheProvider().getCache(
				DEVICE_DATASOURCE_CACHE);
		DeviceAuthenticationResponse authnResponse = deviceCache.get(
				identifier, DeviceAuthenticationResponse.class);
		if (authnResponse == null) {
			authnResponse = getDeviceFromStore(identifier);
			LOGGER.info("Status11 {}", authnResponse != null ? authnResponse
					.getGeneralResponse().getStatus().toString()
					: "authnResponse is null");
			deviceCache.put(identifier, authnResponse);
		}
		return authnResponse.getDevice().getDeviceId();
	}

	public static String getDatasourceName(Object identifier) {
		validateSourceIdentifier(identifier);
		Cache deviceCache = ApplicationConfig.getCacheProvider().getCache(
				DEVICE_DATASOURCE_CACHE);
		DeviceAuthenticationResponse authnResponse = deviceCache.get(
				identifier, DeviceAuthenticationResponse.class);

		LOGGER.info("Status {}", authnResponse != null ? authnResponse
				.getGeneralResponse().getStatus() : "authnResponse is null");
		if (authnResponse == null) {
			authnResponse = getDeviceFromStore(identifier);
			LOGGER.info("Status1 {}", authnResponse != null ? authnResponse
					.getGeneralResponse().getStatus().toString()
					: "authnResponse is null");
			if (!authnResponse.getGeneralResponse().getStatus().toString()
					.equals(Status.NOT_AVAIALABLE.toString())) {
				LOGGER.info("Status#### {}", authnResponse.getGeneralResponse()
						.getStatus());
				deviceCache.put(identifier, authnResponse);
				LOGGER.info("Auth Response name {} ", authnResponse);
				LOGGER.info("Device name {} ", authnResponse.getDevice());
				LOGGER.info("Datasource name {} ", authnResponse.getDevice()
						.getDatasourceName());
				return authnResponse.getDevice().getDatasourceName();
			}
		} else {
			return authnResponse.getDevice().getDatasourceName();
		}

		return null;
	}
	
	public static Long getDatasourcePublishTime(Object identifier) {
		validateSourceIdentifier(identifier);
		Cache deviceCache = ApplicationConfig.getCacheProvider().getCache(
				DEVICE_DATASOURCE_CACHE);
		DeviceAuthenticationResponse authnResponse = deviceCache.get(
				identifier, DeviceAuthenticationResponse.class);

		LOGGER.info("For last Publish time , device status {}", authnResponse != null ? authnResponse
				.getGeneralResponse().getStatus() : "authnResponse is null");
		if (authnResponse != null) {
			return authnResponse.getLastPublishTime();
		}
		return null;
	}
	
	public static boolean updateDatasourcePublishTime(Object identifier,long messageTime) {
		Cache deviceCache = ApplicationConfig.getCacheProvider().getCache(
				DEVICE_DATASOURCE_CACHE);
		DeviceAuthenticationResponse authnResponse = deviceCache.get(
				identifier, DeviceAuthenticationResponse.class);
		if (authnResponse != null) {
			authnResponse.setLastPublishTime(messageTime);
			deviceCache.put(identifier, authnResponse);
			LOGGER.info("updated last publish time for device {} to {}",identifier,messageTime);
			return true;
		}
		LOGGER.info("unable to update last publish time as device {} is not in the cache",identifier);
		return false;
	}

	/**
	 * This method calls a device api to get the device datasource name If it is
	 * available put it in the cache
	 * 
	 * @param sourceId
	 *            - unique id of the device
	 * @param deviceCache
	 *            - cache for device datasource names
	 * @return - datasource name
	 */

	private static DeviceAuthenticationResponse getDeviceFromStore(
			Object sourceId) {
		Client httpClient = buildRestClient();
		DeviceAuthenticationResponse authnResponse = null;
		try {
			String url = ApplicationConfig.getConfigBean().getDeviceUrl()
					.replace("{source_id}", sourceId.toString());
			authnResponse = httpClient.get(url, null,
					DeviceAuthenticationResponse.class);
			if (authnResponse == null) {
				LOGGER.info("Error in get device api, response is null");
			} else {
				LOGGER.info(
						"Received device object from api {},{}",
						authnResponse.getGeneralResponse().getStatus()
								.getStatus(),
						authnResponse.getDevice() != null ? authnResponse
								.getDevice().getSourceId() : "NA:"
								+ sourceId.toString());
			}
		} catch (ClientException e) {
			LOGGER.error("Error calling device api for source {}", sourceId);
		}
		return authnResponse;
	}

	/**
	 * This method calls a datasource api (getSubscriptionContexts) to get the
	 * list of subscription contexts this datasource belongs
	 * 
	 * @param sourceId
	 *            - datasource name
	 * @return - list of subscription context
	 */
	public static List<String> getDeviceContext(String calledBy,String sourceId) {
		LOGGER.info("In Analytics Util {} {} ", sourceId,calledBy);
		String datasourceName = getDatasourceName(sourceId);
		// keeping for a while for testing , has to be removed
		// ApplicationConfig.init();
		List<String> contexts = new ArrayList<String>();
		String dsName = "";
		if (!StringUtils.isBlank(datasourceName)) {

			try {
				dsName = URLEncoder.encode(datasourceName, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				LOGGER.error("Error in encoding to UTF-8", e1);
			}
			Client httpClient = buildRestClient();
			try {

				String contextUrl = ApplicationConfig.getConfigBean()
						.getContextUrl().replace("{datasource_name}", dsName);
				SubscriptionContext context = httpClient.get(contextUrl, null,
						SubscriptionContext.class);
				if (context == null) {
					LOGGER.info("Error in get datasource context api, response is null");
				} else if (context.getStatus().equals(Status.SUCCESS)) {
					contexts.add(datasourceName);
					contexts.addAll(context.getContexts());
				}
			} catch (Exception e) {
				LOGGER.error("Error in getting subscription context for {}",
						datasourceName, e);
			}
		} else {
			LOGGER.error(
					"No datasource name or device found for {} may be device not publishing data",
					datasourceName);
		}
		return contexts;
	}

	public static Unit getUnitDetails(String unit, String physicalQuantity) {
		List<String> contexts = new ArrayList<String>();
		if (!StringUtils.isBlank(physicalQuantity)) {
			Client httpClient = buildRestClient();
			try {

				String contextUrl = ApplicationConfig.getConfigBean()
						.getDeviceUrl()
						.replace("{datasource_name}", physicalQuantity);
				JsonNode jsonNode = httpClient.get(contextUrl, null,
						JsonNode.class);
				if (jsonNode == null) {
					LOGGER.info("Error in get datasource context api, response is null");
				} else if (jsonNode.get("status").asText()
						.equalsIgnoreCase("SUCCESS")) {
					JsonNode contextNodes = jsonNode.path("contexts");
					for (JsonNode context : contextNodes) {
						contexts.add(context.asText());
					}
				}
			} catch (Exception e) {
				LOGGER.error("Error in getting subscription context for {}",
						physicalQuantity, e);
			}
		} else {
			LOGGER.error("Unable to fetch context ,empty datasource name");
		}
		return null;
	}

	public static String getDatastore(String physicalQuantity) {
		if (!StringUtils.isBlank(physicalQuantity)) {
			Client httpClient = buildRestClient();
			try {
				physicalQuantity = URLEncoder.encode(physicalQuantity, "UTF-8");
				String contextUrl = ApplicationConfig.getConfigBean()
						.getPhyQtyUrl()
						.replace("{physical_quantity}", physicalQuantity);
				JsonNode jsonNode = httpClient.get(contextUrl, null,
						JsonNode.class);
				if (jsonNode == null) {
					LOGGER.info("Error in get physical quantity api, response is null");
				} else if (StringUtils.isNotBlank(jsonNode.get("status")
						.asText())
						&& jsonNode.get("status").asText()
								.equalsIgnoreCase("active")) {
					return jsonNode.get("dataStore").asText();
				}
			} catch (UnsupportedEncodingException e1) {
				LOGGER.error("Error in encoding physical quantity to UTF-8", e1);
			} catch (Exception e) {
				LOGGER.error("Error in getting physical quantity for {}",
						physicalQuantity, e);
			}
		} else {
			LOGGER.error("Physical quantity name is blank");
		}
		return null;
	}
	
	public static String getDataStoreFromCache(String physicalQuantity){
		Cache phyQtyCache = ApplicationConfig.getCacheProvider().getCache(PHYSICAL_QUANTITY_CACHE);
		String datastore = phyQtyCache.get(physicalQuantity,String.class);
		if(StringUtils.isEmpty(datastore)){
			datastore =  getDatastore(physicalQuantity);
			if(!StringUtils.isEmpty(datastore)){
				phyQtyCache.put(physicalQuantity, datastore);
			}
		}
		LOGGER.info("returning cached physical quantity {}",datastore);
		return datastore;
	}

	private static Client buildRestClient() {
		Client httpClient;
		httpClient = ApacheRestClient
				.builder()
				.host(ApplicationConfig.getConfigBean().getPlatformHostIp())
				.port(Integer.parseInt(ApplicationConfig.getConfigBean()
						.getPlatformPort()))
				.scheme(ApplicationConfig.getConfigBean().getPlatformScheme())
				.build();
		return httpClient;
	}

	private static void putDeviceConfig(String sourceId,DeviceConfiguration config) {
		Cache deviceConfigurationCache = ApplicationConfig.getCacheProvider()
				.getCache(DEVICE_CONFIGURATION_CACHE);
		LOGGER.info("device config {}", config != null ? config
				.getConfigPoints().size() : "NUll config");
		deviceConfigurationCache.put(sourceId, config);
		LOGGER.info("cached device config");
	}

	private static DeviceConfiguration getConfig(String sourceId) {
		DeviceConfiguration config = new DeviceConfiguration();
		Client httpClient = buildRestClient();
		try {
			DeviceConfigTemplate configTemplate = httpClient.get(
					ApplicationConfig.getConfigBean().getDeviceConfigUrl()
							.replace("{source_id}", sourceId), null,
					DeviceConfigTemplate.class);
			if (configTemplate == null) {
				LOGGER.info("Error in get device config , response is null");
				return null;
			}
			LOGGER.info("Template Name {} {}", configTemplate.getName(),
					configTemplate.getConfigPoints().size());
			config.setConfigPoints(configTemplate.getConfigPoints());
		} catch (ClientException e) {
			LOGGER.error("Error calling device api for source {}", sourceId);
		}
		return config;
	}

	public static void main(String[] args) {

/*
		Cache deviceCache = ApplicationConfig.getCacheProvider().getCache(
				DEVICE_DATASOURCE_CACHE);
		deviceCache.clear();
		Cache deviceCache1 = ApplicationConfig.getCacheProvider().getCache(
				DEVICE_CONFIGURATION_CACHE);
		deviceCache.clear();
		deviceCache1.clear();
		System.out.println("Cache cleared");*/
		//DeviceConfiguration config = AnalyticsUtil.getConfig("103000000000201");
		//System.out.println(config.getConfigPoints());
		
		getDataStoreFromCache("latitude");
	}
	
	public static void getAssetInfo(String dsName,String pointId){
		
	}
}
