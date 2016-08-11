
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
package com.pcs.analytics.handlers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.analytics.core.extractor.IOExtractor;
import com.pcs.deviceframework.decoder.data.point.Point;


/**
 * This class is responsible for finding the point value by executing the configured class
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 30 2015
 */
public class PointHandler {

	private static final Logger logger = LoggerFactory.getLogger(PointHandler.class);

	List<Point>points;
	Set<String> dependencyPointIds;
	Point configuredPoint;

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Set<String> getDependencyPointIds() {
		return dependencyPointIds;
	}

	public void setDependencyPointIds(Set<String> pointIds) {
		this.dependencyPointIds = pointIds;
	}

	public Point getConfiguredPoint() {
		return configuredPoint;
	}

	public void setConfiguredPoint(Point configuredPoint) {
		this.configuredPoint = configuredPoint;
	}

	public Point execute(){
		IOExtractor ioExtractor = null;
		Object extractorInstance = null;
		Point resultPoint = null;
		String className = configuredPoint.getClassName();
		if(className!= null && className.trim().length()>0){
			try{
				Method declaredMethod = Class.forName(className).getDeclaredMethod("getInstance", new Class[] {});
				extractorInstance = declaredMethod.invoke(null, new Object[]{});
				if (!(extractorInstance instanceof IOExtractor)) {
					logger.error("Error in casting to IOExtractor class ");
				}else{
					ioExtractor = (IOExtractor)extractorInstance;
					ioExtractor.setPoints(points);
					ioExtractor.setDependencyPointIds(dependencyPointIds);
					resultPoint = ioExtractor.getSpecificData(configuredPoint);
				}
			}catch(Exception ex){
				logger.error("Error while extracting point");
				return null;
			}
		}
		ioExtractor = null;
		extractorInstance = null;
		className = null;
		
		return resultPoint;
	}

}
