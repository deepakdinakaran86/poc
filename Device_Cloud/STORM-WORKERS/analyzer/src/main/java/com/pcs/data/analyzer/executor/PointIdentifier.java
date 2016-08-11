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
package com.pcs.data.analyzer.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.data.analyzer.bean.AnalyzedData;
import com.pcs.data.analyzer.bean.comparator.DeviceConfigurationComparator;
import com.pcs.data.analyzer.util.AnalyticsUtil;
import com.pcs.data.analyzer.util.MessageIngestionUtil;
import com.pcs.data.analyzer.util.PointUtility;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.expressions.engine.ExpressionEngineUtil;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DataIngestionBean;
import com.pcs.saffron.manager.bean.DeviceConfiguration;

/**
 * This class is responsible for analyzing each device point
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 29 2015
 */
public class PointIdentifier {
	private static final Logger logger = LoggerFactory
			.getLogger(PointIdentifier.class);

	private static final String SPACE = " ";
	private static final String ERROR = "Error";
	private static final String NA = "N/A";
	private static final String HEALTHY = "HEALTHY";
	private static final String READ_ONLY = "READONLY";
	private static final String FAULTY = "FAULTY";
	private static final int ZERO = 0;

	Object sourceId = null;
	Message message = null;
	String deviceId = null;
	public PointIdentifier() {
	}

	public PointIdentifier(Object sourceId, Message message) {
		this.sourceId = sourceId;
		this.message = message;
		this.deviceId = AnalyticsUtil.getDeviceUUID(sourceId);
	}

	public Object getSourceId() {
		return sourceId;
	}

	public void setSourceId(Object sourceId) {
		this.sourceId = sourceId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public AnalyzedData identifyPoints() throws Exception {
		List<Point> devicePoints = null;
		List<Point> identifiedPoints = new ArrayList<Point>();
		List<ConfigPoint> configPoints = null;
		DeviceConfiguration configuration = null;
		AnalyzedData analyzedData = null;
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		try {
			devicePoints = message.getPoints();

			for (Point devicePoint : devicePoints) {
				parameterMap.put(
						devicePoint.getDisplayName().replaceAll(SPACE, ""),
						devicePoint.getData());
			}

			configuration = AnalyticsUtil.getDeviceConfiguration(sourceId);
			if (configuration != null) {
				configPoints = configuration.getConfigPoints();
				Collections.sort(configPoints,new DeviceConfigurationComparator());

				for (ConfigPoint configPoint : configPoints) {
					logger.info("Config point Name: {}, PQ: {}, Unit: {}",configPoint.getPointName(),configPoint.getPhysicalQuantity(),configPoint.getUnit());
					Point devicePoint = PointUtility.getPoint(configPoint.getPointId(), devicePoints);
					if (devicePoint != null) {
						String expression = configPoint.getExpression()
								.replaceAll(System.lineSeparator(), "");
						expression = expression.replaceAll("[\t]+", "");
						expression = expression.replaceAll("[\n]+", "");
						try {
							setDefaultProps(configPoint);
							Object evaluatedValue = new ExpressionEngineUtil().evaluate(expression, parameterMap);
							if(isDataDirty(evaluatedValue)){
								setDirtyProps(configPoint,evaluatedValue);
							}else{
								setHealthyProps(configPoint);
								configPoint.setData(evaluatedValue.toString());
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("------error in expression evaluation {}",
									configPoint.getDisplayName());
							continue;
						}

					}else{
						setDirtyProps(configPoint,NA);
					}
					parameterMap.put(configPoint.getDisplayName()
							.replaceAll(SPACE, ""), configPoint.getData());
					identifiedPoints.add(configPoint);
				}
				message.setPoints(identifiedPoints);
			} else {
				logger.error(
						"No device configuration available , unable to analyze the message for {}",
						sourceId);
				throw new Exception("Device Configuration not available");
			}
			configuration = null;
		} catch (Exception ex) {
			logger.error("Error occurred while analyzing data from device {}",
					sourceId);
			throw new Exception();
		} finally {
			sourceId = null;
			configuration = null;
			identifiedPoints = null;
			configPoints = null;
			parameterMap = null;
		}
		if(message != null){
			analyzedData = new AnalyzedData();
			analyzedData.setMessage(message);
			List<DataIngestionBean> dataIngestionBean = MessageIngestionUtil.prepareIngestionMessage(deviceId, message, false);
			analyzedData.setDataIngestionBean(dataIngestionBean);
		}
		return analyzedData;
	}

	private void setDefaultProps(ConfigPoint configPoint) {
		configPoint.setPointAccessType(READ_ONLY);
	}
	
	private void setHealthyProps(ConfigPoint configPoint) {
		configPoint.setStatus(HEALTHY);
	}

	private boolean isDataDirty(Object evaluatedValue) {
		if (evaluatedValue==null || evaluatedValue.toString().equalsIgnoreCase(ERROR)
				|| evaluatedValue.toString().equalsIgnoreCase(NA)) {
			if(evaluatedValue == null)
				evaluatedValue = ERROR;
			return true;
		}
		return false;
	}

	private void setDirtyProps(ConfigPoint configPoint,Object evaluatedValue) {
		configPoint.setStatus(FAULTY);
		switch (PointDataTypes.valueOf(configPoint.getType())) {
		case NUMERIC:
		case INTEGER:
		case FLOAT:
		case DOUBLE:
		case SHORT:
		case LONG:
			try {
				configPoint.setData(Float.NaN);
			} catch (Exception e) {
				logger.error(
						"Error setting dirty data to config point {} of device {}",
						configPoint.getDisplayName(), sourceId);
			}
			break;
		case GEOPOINT:
			try {
				Float[] invalidLocation = {Float.NaN,Float.NaN};
				configPoint.setData(invalidLocation);
			} catch (Exception e) {
				logger.error(
						"Error setting dirty data to config point {} of device {}",
						configPoint.getDisplayName(), sourceId);
			}
			break;
		case STRING:
		case TEXT:
			try {
				configPoint.setData(evaluatedValue);
			} catch (Exception e) {
				logger.error(
						"Error setting dirty data to config point {} of device {}",
						configPoint.getDisplayName(), sourceId);
			}
			
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		PointIdentifier p = new PointIdentifier();
		p.isDataDirty(null);
	}

}
