
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
package com.pcs.iodecoder.teltonika;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.analytics.core.extractor.IOExtractor;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.iodecoder.constants.Constants;
import com.pcs.iodecoder.util.PointUtility;

/**
 * This class is responsible for extracting asset motion status
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 31 2015
 */
public class Motion_Status_Extractor extends IOExtractor {

	private static final Logger logger = LoggerFactory.getLogger(Motion_Status_Extractor.class);

	private static final String OFF = "Off";
	private static final String ON = "On";
	private static final String IDLE = "Idle";

	private static Motion_Status_Extractor extractor = null;

	private Motion_Status_Extractor() {

	}

	public static Motion_Status_Extractor getInstance() {
		if (extractor == null) {
			extractor = new Motion_Status_Extractor();
		}
		return extractor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException(
				"This class is meant to be a singleton");
	}

	/* (non-Javadoc)
	 * @see com.pcs.analytics.core.extractor.IOExtractor#getSpecificData(com.pcs.deviceframework.decoder.data.point.Point)
	 */
	@Override
	public Point getSpecificData(Point point) throws Exception {
		if(getDependencyPointIds()!=null && getPoints()!=null){
			List<Point> parameterPoints = null;
			Point refPoint = null;
			String engStat = null;
			String speedData = null;
			Float speed = null;
			Integer index = null;
			try {
				parameterPoints = PointUtility.getParameterPoints(getDependencyPointIds(),getPoints());

				refPoint = new Point();
				refPoint.setPointName("Engine Status");
				index = parameterPoints.indexOf(refPoint);
				if(index>=0){
					engStat = parameterPoints.get(index).getData().toString();
					
					refPoint.setPointName("Speed");
					index = parameterPoints.indexOf(refPoint);
					if(index>=0){
						speedData = parameterPoints.get(index).getData().toString();
						if(speedData!=null){
							speed = Float.parseFloat(speedData);
							if( engStat!=null && ! engStat.equals("")  ){
								if(engStat.equalsIgnoreCase(ON) && speed > 3 ){
									point.setData(ON);
								}
								else if(engStat.equalsIgnoreCase(ON) &&  speed < 4){
									point.setData(IDLE);
								}
								else{
									point.setData(OFF);
								}
							}else{
								logger.error("Error finding  engine status paramete value for device {}" + point.getPointId());
								point.setData(Constants.NOT_APPLICABLE);
							}
						}else{
							logger.error("Error finding speed parameter value for device {}" + point.getPointId());
							point.setData(Constants.NOT_APPLICABLE);
						}
					}else{
						logger.error("Error finding speed parameter for device {}" + point.getPointId());
						point.setData(Constants.NOT_APPLICABLE);
					}
				}else{
					logger.error("Error finding engine status parameter for device {}" + point.getPointId());
					point.setData(Constants.NOT_APPLICABLE);
				}

			} catch (Exception e) {
				logger.error("Error finding motion status for device {}" + point.getPointId(),e);
			}finally{
				parameterPoints = null;
				refPoint = null;
				engStat = null;
				speedData = null;
				speed = null;
			}
		}
		return point;
	}

}
