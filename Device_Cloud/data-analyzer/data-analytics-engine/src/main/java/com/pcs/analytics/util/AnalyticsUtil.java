
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
package com.pcs.analytics.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.analytics.core.ApplicationConfig;
import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.DerivedPoint;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.extension.AlarmExtension;
import com.pcs.deviceframework.decoder.data.point.extension.OutOfRangeAlarm;
import com.pcs.deviceframework.decoder.data.point.extension.PointExtension;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129 (Seena Jyothish)
 */
public class AnalyticsUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnalyticsUtil.class);

	static final String DEVICE_CONFIGURATION_CACHE = "g2021.devicemanager.cache.configuration";
	static final String DEVICE_DATASOURCE_CACHE = "data.analytics.device.datasource.cache";


	public static DeviceConfiguration getDeviceConfiguration(Object identifier){
		validateSourceIdentifier(identifier);

		Cache deviceConfigurationCache = ApplicationConfig.getCacheProvider().getCache(DEVICE_CONFIGURATION_CACHE);

		DeviceConfiguration config = deviceConfigurationCache.get(identifier, DeviceConfiguration.class);
		if(config == null){
			LOGGER.error("Unable to get device configuration from cache for {}",identifier);
		}
		return config;
	}

	private static void validateSourceIdentifier(Object sourceIdentifier) {
		if (sourceIdentifier == null || sourceIdentifier.toString().isEmpty()) {
			throw new IllegalArgumentException("Source identifier cant't be NULL or empty");
		}
	}

	/*public static String getDatasourceName(Object identifier){
		String datasourceName = null;
		validateSourceIdentifier(identifier);
		Cache deviceCache = ApplicationConfig.getCacheProvider().getCache(DEVICE_DATASOURCE_CACHE);
		datasourceName = deviceCache.get(identifier,String.class);
		if(datasourceName == null){
			datasourceName = getDatasourceFromStore(identifier);
			deviceCache.put(identifier, datasourceName);
		}
		return datasourceName;
	}*/

	/**
	 * This method calls a device api to get the device datasource name
	 * If it is available put it in the cache
	 * 
	 * @param sourceId - unique id of the device
	 * @param deviceCache - cache for device datasource names
	 * @return - datasource name
	 *//*
	private static String getDatasourceFromStore(Object sourceId){
		String datasourceName = null;
		Client httpClient = buildRestClient();
		try {
			JsonNode jsonNode = httpClient.get(ApplicationConfig.getConfigBean().getDeviceUrl(), null, JsonNode.class);
			if(jsonNode == null){
				LOGGER.info("Error in get device api, response is null");
			}else if (jsonNode.get("status").asText().equalsIgnoreCase("SUCCESS")) {
				if (jsonNode.get("datasourceName")!=null && !StringUtils.isEmpty(jsonNode.get("datasourceName").toString())){
					datasourceName = jsonNode.get("datasourceName").textValue();
				}
			}else{
				LOGGER.info("Unable to get the device details from device api");
			}
		} catch (ClientException e) {
			LOGGER.error("Error calling device api for source {}",sourceId);
		}
		return datasourceName;
	}*/
	
	
	/**
	 * This method calls a datasource api (getSubscriptionContexts) to get the list of subscription contexts
	 * this datasource belongs
	 * @param datasourceName - datasource name
	 * @return - list of subscription context
	 */
	public static List<String> getDeviceContext(String datasourceName){
		//keeping for a while for testing , has to be removed
		//ApplicationConfig.init();
		List<String> contexts = new ArrayList<String>();
		if(!StringUtils.isBlank(datasourceName)){
			Client httpClient = buildRestClient();
			try {
				
				String contextUrl = ApplicationConfig.getConfigBean().getDeviceUrl().replace("{datasource_name}", datasourceName);
				JsonNode jsonNode = httpClient.get(contextUrl, null, JsonNode.class);
				if(jsonNode == null){
					LOGGER.info("Error in get datasource context api, response is null");
				}else if(jsonNode.get("status").asText().equalsIgnoreCase("SUCCESS")) {
					JsonNode contextNodes = jsonNode.path("contexts");
					for(JsonNode context : contextNodes){
						contexts.add(context.asText());
					}
				}
	        } catch (Exception e) {
	        	LOGGER.error("Error in getting subscription context for {}",datasourceName,e);
	        }
		}else{
			LOGGER.error("Unable to fetch context ,empty datasource name");
		}
		return contexts;
	}

	private static Client buildRestClient(){
		Client httpClient;
		httpClient = ApacheRestClient.builder()
				.host(ApplicationConfig.getConfigBean().getPlatformHostIp())
				.port(Integer.parseInt(ApplicationConfig.getConfigBean().getPlatformPort()))
				.scheme(ApplicationConfig.getConfigBean().getPlatformScheme())
				.build();
		return httpClient;
	}

	public static void main(String[] args) {
		//ApplicationConfig.init();
		//AnalyticsUtil.getDeviceContext("40077f6a-00d5-4580-b95f-784a5065fe9b");
		getMessageForTesting();
	}

	private static void putDeviceConfig(){
		Cache deviceConfigurationCache = ApplicationConfig.getCacheProvider().getCache(DEVICE_CONFIGURATION_CACHE);
		DeviceConfiguration config1 =getConfig();
		deviceConfigurationCache.put("541ca270-d791-11e4-8830-0800200c9a66", config1);
	}

	private static DeviceConfiguration getConfig(){
		DeviceConfiguration config = new DeviceConfiguration();
		try{
			Map<Object, Point> pointMapping = new HashMap<Object, Point>();
			config = new DeviceConfiguration();
			HashMap<Object, DerivedPoint> derivedPointMapping = new HashMap<Object, DerivedPoint>();
			Point point = new Point();
			point.setPointId("1");
			point.setPointName("Vehicle Battery");
			point.setCustomTag("Vehicle Battery");
			point.setClassName("com.pcs.iodecoder.teltonika.Vehicle_Battery_Extractor");
			point.setUnit("V");
			pointMapping.put("1", point);
			point = null;

			point = new Point();
			point.setPointId("2");
			point.setPointName("Latitude");
			point.setCustomTag("Latitude");
			point.setClassName("com.pcs.iodecoder.teltonika.Latitude_Extractor");
			pointMapping.put("2", point);
			point = null;

			point = new Point();
			point.setPointId("3");
			point.setPointName("Longitude");
			point.setCustomTag("Longitude");
			point.setClassName("com.pcs.iodecoder.teltonika.Longitude_Extractor");
			pointMapping.put("3", point);
			point = null;

			point = new Point();
			point.setPointId("4");
			point.setPointName("Visible Satellites");
			point.setCustomTag("Visible Satellites");
			point.setClassName("com.pcs.iodecoder.teltonika.Visible_Satellites_Extractor");
			pointMapping.put("4", point);
			point = null;

			point = new Point();
			point.setPointId("5");
			point.setPointName("Speed");
			point.setCustomTag("Speed");
			point.setClassName("com.pcs.iodecoder.teltonika.Speed_Extractor");
			point.setUnit("km\\h");
			pointMapping.put("5", point);
			point = null;

			point = new Point();
			point.setPointId("6");
			point.setPointName("GSM Signal");
			point.setCustomTag("GSM Signal");
			point.setClassName("com.pcs.iodecoder.teltonika.Gsm_Signal_Strength_Extractor");
			pointMapping.put("6", point);
			point = null;

			point = new Point();
			point.setPointId("7");
			point.setPointName("Device Battery");
			point.setCustomTag("Device Battery");
			point.setClassName("com.pcs.iodecoder.teltonika.Device_Battery_Extractor");
			point.setUnit("V");
			pointMapping.put("7", point);
			point = null;

			point = new Point();
			point.setPointId("8");
			point.setPointName("Angle");
			point.setCustomTag("Angle");
			point.setClassName("com.pcs.iodecoder.teltonika.Angle_Extractor");
			pointMapping.put("8", point);
			point = null;

			point = new Point();
			point.setPointId("9");
			point.setPointName("Altitude");
			point.setCustomTag("Altitude");
			point.setClassName("com.pcs.iodecoder.teltonika.Altitude_Signed_Extractor");
			pointMapping.put("9", point);
			point = null;

			point = new Point();
			point.setPointId("15");
			point.setPointName("Engine Status");
			point.setCustomTag("Engine Status");
			point.setClassName("com.pcs.iodecoder.teltonika.DIN_On_Off_Extractor");
			pointMapping.put("15", point);
			point = null;

			config.setPointMapping(pointMapping);

			Set<String> dependencyPointIds = new HashSet<String>();
			DerivedPoint derivedPoint = new DerivedPoint();
			derivedPoint.setPointId("10");
			derivedPoint.setPointName("Direction");
			derivedPoint.setCustomTag("Direction");
			derivedPoint.setClassName("com.pcs.iodecoder.teltonika.Direction_Extractor");
			dependencyPointIds.add("8");
			derivedPoint.setDependencyPointIds(dependencyPointIds);
			derivedPointMapping.put("10", derivedPoint);
			derivedPoint = null;

			derivedPoint = new DerivedPoint();
			derivedPoint.setPointId("11");
			derivedPoint.setPointName("Machine Status");
			derivedPoint.setCustomTag("Machine Status");
			derivedPoint.addDependencyPoint("15");
			derivedPoint.addDependencyPoint("5");
			derivedPoint.setClassName("com.pcs.iodecoder.teltonika.Motion_Status_Extractor");

			derivedPointMapping.put("11", derivedPoint);
			derivedPoint = null;

			config.setDerivedPointMapping(derivedPointMapping);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return config;
	}
	
	public static Message getMessageForTesting(){
		Message message = new Message();
		try {
			message.setSourceId("40077f6a-00d5-4580-b95f-784a5065fe9b");
			Point point = new Point();
			point.setPointId("28");
			point.setPointName("AI2");
			point.setCustomTag("AI2");
			point.setSystemTag("AI2");
			point.setClassName("com.pcs.iodecoder.teltonika.Latitude_Extractor");
			point.setPhysicalQuantity("generic_quantity");
			point.setData("-4.99");
			message.setTime(1427951123000l);
			
			PointExtension extension = new PointExtension();
			extension.setExtensionName("DATA_TYPE");
			extension.setExtensionType("NUMERIC");
			point.addExtension(extension);
			
			OutOfRangeAlarm alarm = new OutOfRangeAlarm();
			alarm.setAlarmMessage("out of range");
			point.addAlarmExtension(alarm);
			
			message.addPoint(point);
			
			System.out.println("Message : " + message.toString());
			
        } catch (Exception e) {
	       e.printStackTrace();
        }
		return message;
	}
		
}
