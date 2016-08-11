
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
package com.pcs.iodecoder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.decoder.data.point.Point;

/**
 * This class is responsible for providing utilities for Point
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 30 2015
 */
public class PointUtility {
	private static final Logger logger = LoggerFactory.getLogger(PointUtility.class);

	public static List<Point> getParameterPoints(Set<String> pointIds,List<Point> points)  {
		if(pointIds!=null && points!=null){
			List<Point> reqParams=new ArrayList<Point>();
			for (String pointId : pointIds ) {
				try{
					Point point = new Point();
					point.setPointId(pointId);
					int index=points.indexOf(point);
					if(index>=0){
						reqParams.add(points.get(index));
					}else{
						logger.error("Invalid parameter id " + pointId);
						//throw new Exception("Invalid parameter name " + parameters[i]);
					}
				}catch(Exception ex){
					logger.error("Error while finding point {}",pointId,ex);
				}
			}
			return reqParams;
		}else{
			logger.error("Empty paramter list or empty list");
			//throw new IllegalArgumentException("Empty paramter list or empty list");
			return new ArrayList<Point>();
		}
	}

	public static Point getPoint(String pointId,List<Point> points){
		Point point = new Point();
		if(pointId!=null && points!=null){
			try{
				point.setPointId(pointId);
				int index = points.indexOf(point);
				if(index >= 0){
					point = points.get(index);
				}else{
					logger.error("Invalid parameter id " + pointId);
				}
			}catch(Exception ex){
				logger.error("Error while finding point {}",pointId,ex);
			}
		}else{
			logger.error("Empty paramter id or empty list");
		}
		return point;
	}

}
