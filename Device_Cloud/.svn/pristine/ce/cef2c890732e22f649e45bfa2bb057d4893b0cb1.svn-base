
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
package com.pcs.analytics.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.analytics.handlers.PointHandler;
import com.pcs.analytics.util.AnalyticsUtil;
import com.pcs.analytics.util.PointUtility;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.DerivedPoint;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;

/**
 * This class is responsible for analyzing each device point
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 29 2015
 */
public class PointIdentifier {
	private static final Logger logger = LoggerFactory.getLogger(PointIdentifier.class);

	Object sourceId = null;
	Message message = null;

	public PointIdentifier(){
	}

	public PointIdentifier(Object sourceId,Message message){
		this.sourceId = sourceId;
		this.message = message;
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

	public Message identifyPoints() throws Exception{
		List<Point> analyzedPoints = null;

		List<Point> devicePoints = null;
		Map<Object, Point> pointMapping = null;
		HashMap<Object, DerivedPoint> derivedPointMapping = null;
		try{
			analyzedPoints = new ArrayList<Point>();
			devicePoints = message.getPoints();

			DeviceConfiguration deviceConfiguration = AnalyticsUtil.getDeviceConfiguration(sourceId);
			if(deviceConfiguration!=null){
				pointMapping = deviceConfiguration.getPointMapping();
				derivedPointMapping = deviceConfiguration.getDerivedPointMapping();

				for(Point point : pointMapping.values()){
					Point configPoint = PointUtility.getPoint(point.getPointId(), devicePoints);
					if(configPoint!=null){
						point.setData(configPoint.getData());
						PointHandler handler = new PointHandler();
						handler.setConfiguredPoint(point);
						point = handler.execute();
						analyzedPoints.add(point);
					}
				}

				//for derived points
				for(DerivedPoint derivedPoint : derivedPointMapping.values()){
					PointHandler handler = new PointHandler();
					handler.setConfiguredPoint(derivedPoint);
					handler.setPoints(analyzedPoints);
					handler.setDependencyPointIds(derivedPoint.getDependencyPointIds());
					Point point = handler.execute();
					analyzedPoints.add(point);
				}
				printPoints(analyzedPoints);

				//updating raw point collection with analyzed point collection
				message.setPoints(analyzedPoints);
			}else{
				logger.error("No device configuration available , unable to analyze the message for {}",sourceId);
				throw new Exception("Device Configuration not available");
			}

			deviceConfiguration = null;
			pointMapping = null;
			derivedPointMapping = null;
		}catch(Exception ex){
			logger.error("Error occurred while analyzing data from device {}",sourceId);
			throw new Exception();
		}finally{
			sourceId = null;
			devicePoints = null;
		}
		return message;
	}

	private static void printPoints(List<Point> analyzedPoints ){
		for(Point point : analyzedPoints){
			logger.info("Ponit Name : " + point.getPointName());
			logger.info("Custom Tag : " + point.getCustomTag());
			logger.info("Value : " + point.getData());
			logger.info("Unit : " + point.getUnit());
			logger.info("Point Id : " + point.getPointId());
			logger.info("Class Name : " + point.getClassName());
			logger.info("-------------------");
		}
	}


	public static void main(String[] args) throws Exception {
		PointIdentifier executor = new PointIdentifier();
		executor.setSourceId("541ca270-d791-11e4-8830-0800200c9a666");
		Message deviceMessage = new Message();
		List<Point> points = new ArrayList<Point>();
		Point point = new Point();
		point.setPointId("1");
		point.setData("2535");
		points.add(point);

		point = new Point();
		point.setPointId("5");
		point.setData("125");
		points.add(point);

		point = new Point();
		point.setPointId("15");
		point.setData("1");
		points.add(point);

		deviceMessage.setPoints(points);
		//deviceMessage.s;

		executor.setMessage(deviceMessage);
		
		
		try {
			executor.identifyPoints();
			System.out.println("Data analyzed for "+ executor.getSourceId());
		} catch (Exception e) {
			System.out.println("Error in point extraction for "+ executor.getSourceId());
		}
		
		
	}

}
