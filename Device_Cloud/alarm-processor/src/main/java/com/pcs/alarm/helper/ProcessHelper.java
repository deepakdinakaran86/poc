package com.pcs.alarm.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.alarm.beans.AlarmBean;
import com.pcs.alarm.beans.AlarmDefinition;
import com.pcs.alarm.beans.AlarmInfo;
import com.pcs.alarm.core.ApplicationStart;
import com.pcs.alarm.util.CacheUtil;
import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 *
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Jul 28, 2016
 */
public class ProcessHelper {

	private static final Logger logger = LoggerFactory
	        .getLogger(ProcessHelper.class);

	static final String DEVICE_ALARM_DEF_CACHE = "alarm.processor.device.alarm.definition.cache";

	private static Client buildRestClient() {
		Client httpClient;
		httpClient = ApacheRestClient
		        .builder()
		        .host(ApplicationStart.getConfigBean().getPlatformHostIp())
		        .port(Integer.parseInt(ApplicationStart.getConfigBean()
		                .getPlatformPort()))
		        .scheme(ApplicationStart.getConfigBean().getPlatformScheme())
		        .build();
		return httpClient;
	}

	public static List<AlarmDefinition> getDeviceAlarmDefinitions(
	        String sourceId) {
		Cache alarmDefCache = CacheUtil.getCacheProvider().getCache(
		        DEVICE_ALARM_DEF_CACHE);
		List<AlarmDefinition> alarmDefinitions = alarmDefCache.get(sourceId,
		        List.class);
		if (alarmDefinitions != null && !alarmDefinitions.isEmpty()) {
			return alarmDefinitions;
		} else {
			alarmDefinitions = getDeviceAlarmDefinitionsFromStore(sourceId);
			if (alarmDefinitions != null && !alarmDefinitions.isEmpty()) {
				alarmDefCache.put(sourceId, alarmDefinitions);
			}
		}
		return alarmDefinitions;
	}

	private static List<AlarmDefinition> getDeviceAlarmDefinitionsFromStore(
	        String sourceId) {
		List<AlarmDefinition> alarmDefinitions = null;
		if (!StringUtils.isBlank(sourceId)) {
			Client httpClient = buildRestClient();

			try {
				String contextUrl = ApplicationStart.getConfigBean()
				        .getDeviceAlarmDefUrl()
				        .replace("{source_id}", sourceId);
				alarmDefinitions = httpClient.get(contextUrl, null, List.class,
				        AlarmDefinition.class);
			} catch (ClientException e) {
				logger.error(
				        "Error in getting alarm definitions for device {} ",
				        sourceId);
			}

		}
		return alarmDefinitions;
	}

	public static AlarmBean getAlarmObjectFromStore(String sourceId,
	        String alarmDefinitionName) {
		AlarmInfo alarmInfo = null;
		AlarmBean alarmBean = null;
		if (!StringUtils.isBlank(sourceId)) {
			Client httpClient = buildRestClient();
			try {
				String contextUrl = ApplicationStart
				        .getConfigBean()
				        .getDeviceAlarmInfoUrl()
				        .replace("{source_id}", sourceId)
				        .replace("{alarm_definition_name}",  URLEncoder.encode(alarmDefinitionName, "UTF-8"));
				alarmInfo = httpClient.get(contextUrl, null, AlarmInfo.class);
				if(alarmInfo!=null){
					alarmBean = new AlarmBean();
					alarmBean.setAlarmDefinitionName(alarmInfo.getAlarmName());
					if (alarmInfo.getStatus().equals("true")) {
						alarmBean.setAlarmStatus(com.pcs.alarm.enums.AlarmStatus.ACTIVE);
                    }
					alarmBean.setSourceId(sourceId);
					alarmBean.setAlarmData(alarmInfo.getData());
				}
				
			} catch (ClientException e) {
				logger.error(
				        "Error in getting alarm info for device {} for alarm definition {}",
				        sourceId, alarmDefinitionName);
			} catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
            }

		}
		return alarmBean;
	}

	public static void main(String[] args) {
		Cache alarmDefCache = CacheUtil.getCacheProvider().getCache(
		        DEVICE_ALARM_DEF_CACHE);
		alarmDefCache.evict("352848020700924");
		//ProcessHelper.getAlarmObjectFromStore("352848020700924","Visible Satellite Alarm");
	}
}
