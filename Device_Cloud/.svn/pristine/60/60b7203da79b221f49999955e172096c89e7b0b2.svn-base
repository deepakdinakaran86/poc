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
package com.pcs.alarm.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.alarm.beans.AlarmBean;
import com.pcs.alarm.beans.AlarmDefinition;
import com.pcs.alarm.enums.AlarmStatus;
import com.pcs.alarm.util.CacheUtil;
import com.pcs.analytics.flinktests.beans.DeviceSnapshot;
import com.pcs.analytics.flinktests.beans.Parameter;
import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.point.Point;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Aug 1, 2016
 */
public class AlarmProcessHelper {

	private static final Logger logger = LoggerFactory
	        .getLogger(AlarmProcessHelper.class);

	static final String ALARM_CACHE = "alarm.cache";

	private AlarmBean alarmBean;

	public AlarmStatus getAlarmNextStatus(AlarmBean alarmBean,
	        AlarmStatus currentStatus) {
		String str = alarmBean.getAlarmStatus() + "->" + currentStatus;
		logger.info("transition : " + str);
		AlarmStatus alarmStatus = com.pcs.alarm.constants.AlarmTransitions.ALARM_STATUS_TRANSITIONS
		        .get(str);
		logger.error("New Status :" + alarmStatus);
		return alarmStatus;
	}

	public final AlarmBean getAlarmObject(String sourceId,
	        String alarmDefName) {
		alarmBean = new AlarmBean();
		alarmBean.setAlarmDefinitionName(alarmDefName);
		alarmBean.setSourceId(sourceId);

		Cache alarmCache = CacheUtil.getCacheProvider().getCache(ALARM_CACHE);
		List<AlarmBean> alarmBeans = alarmCache.get(alarmDefName, List.class);
		if (alarmBeans != null && !alarmBeans.isEmpty()) {
			int index = alarmBeans.indexOf(alarmBean);
			if (index >= 0) {
				return alarmBeans.get(index);
			}
		}else{
			alarmBean = ProcessHelper.getAlarmObjectFromStore(sourceId,alarmDefName);
			if(alarmBean!=null){
				alarmBeans = new ArrayList<AlarmBean>();
				alarmBeans.add(alarmBean);
				alarmCache.put(alarmDefName, alarmBeans);
			}
		}
		return alarmBean;
	}
	
	public final AlarmBean getAlarmObjectFromStore(String sourceId,
	        String alarmDefName) {
		alarmBean = new AlarmBean();
		
		return alarmBean;
	}
	
	public final  void updateAlarmObject(AlarmBean alarmBean){
		Cache alarmCache = CacheUtil.getCacheProvider().getCache(ALARM_CACHE);
		List<AlarmBean> alarmBeans = alarmCache.get(alarmBean.getAlarmDefinitionName(),List.class);
		if(alarmBeans!=null && !alarmBeans.isEmpty()){
			int index = alarmBeans.indexOf(alarmBean);
			if(index>=0){
				alarmBeans.remove(index);
				alarmBeans.add(alarmBean);
			} else {
				alarmBeans.add(alarmBean);
			}
			alarmCache.put(alarmBean.getAlarmDefinitionName(), alarmBeans);
		}else {
			alarmBeans = new ArrayList<AlarmBean>();
			alarmBeans.add(alarmBean);
			alarmCache.put(alarmBean.getAlarmDefinitionName(), alarmBeans);
		}
	}

	public AlarmBean buildAlarmObject(AlarmBean alertBean, String message,
	        String currentValue, AlarmStatus curStatus) {
		alertBean.setSourceId(alertBean.getSourceId());
		alertBean.setAlarmMessage(message);
		alertBean.setAlarmData(currentValue);
		alertBean.setAlarmStatus(curStatus);
		return alertBean;
	}

	public String buildAlarmValue(List<Parameter> alertPoints) {
		String alertCurrentValue = null;
		for (Parameter pointBean : alertPoints) {
			if (alertCurrentValue == null) {
				alertCurrentValue = pointBean.getDisplayName() + " : "
				        + pointBean.getValue().toString();
			} else {
				alertCurrentValue += ", " + pointBean.getDisplayName() + " : "
				        + pointBean.getValue().toString();
			}
		}
		return alertCurrentValue;
	}

	public AlarmMessage setAlarmValues(AlarmBean alarmBean,
	        DeviceSnapshot deviceSnapshot, AlarmDefinition alarmDefinition,
	        List<Parameter> alertPoints,boolean status) {
		AlarmMessage alarmMessage = new AlarmMessage();
		alarmMessage.setAlarmName(alarmDefinition.getName());
		alarmMessage.setAlarmMessage(alarmBean.getAlarmMessage());
		alarmMessage.setDisplayName(alarmDefinition.getAlarmDefinitionPoints()
		        .get(0).getDisplayName());
		alarmMessage.setData(alarmBean.getAlarmData());
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		alarmMessage.setReceivedTime(new Date(deviceSnapshot.getDeviceTime()));
		alarmMessage.setSourceId(deviceSnapshot.getSourceId());
		alarmMessage.setTime(deviceSnapshot.getDeviceTime());
		alarmMessage.setStatus(status);
		List<Point> points = new ArrayList<Point>();
		for (Parameter parameter : alertPoints) {
			Point point = new Point();
			try {
				point.setData(parameter.getValue());
				point.setPointName(parameter.getDisplayName());
				point.setPointId(parameter.getDisplayName());
				point.setType("INTEGER");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			point.setDisplayName(parameter.getDisplayName());
			points.add(point);
		}
		alarmMessage.setPoints(points);
		return alarmMessage;
	}
	
	public static void main(String[] args) {
		Cache alarmCache = CacheUtil.getCacheProvider().getCache(ALARM_CACHE);
		alarmCache.clear();
		//new AlarmProcessHelper().getAlarmObject("352848020700924","Visible Satellite Alarm");
    }

}
