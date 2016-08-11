
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

import org.apache.log4j.Logger;

import com.pcs.analytics.core.extractor.IOExtractor;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.iodecoder.util.PointUtility;

/**
 * This class is responsible for finding the Direction
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 31 2015
 */
public class Direction_Extractor extends IOExtractor {

	private static Logger logger = Logger.getLogger(Direction_Extractor.class);
	private static final String N_A = "N/A";

	private static Direction_Extractor extractor = null;

	private Direction_Extractor(){
	}

	public static Direction_Extractor getInstance(){
		if(extractor == null){
			extractor = new Direction_Extractor();
		}
		return extractor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("This class is meant to be a singleton");
	}

	public Point getSpecificData(Point point) throws Exception {
		if(getDependencyPointIds()!=null && getPoints()!=null){
			try{
				List<Point> parameterPoints = PointUtility.getParameterPoints(getDependencyPointIds(),getPoints());

				Point point1=new Point();
				point1.setPointName("Angle");
				String angleData=parameterPoints.get(parameterPoints.indexOf(point1)).getData().toString();
				Float angle=null;
				if(angleData!=null){
					angle=Float.parseFloat(angleData);
				}

				String direction=N_A;

				if((angle>=337.5)||(angle < 22.5))
					direction="N";

				else if((angle>=22.5) &&(angle<67.5))
					direction="NE";

				else if((angle>=67.5) &&(angle<112.5)) 
					direction="E";

				else if((angle>=112.5)&&(angle<157.5)) 
					direction="SE";    

				else if((angle>=157.5)&&(angle<202.5)) 
					direction="S"; 

				else if((angle>=202.5)&&(angle<247.5))  
					direction="SW";  

				else if((angle>=247.5)&&(angle<292.5))  
					direction="W";

				else if((angle>=292.5)&&(angle<337.5))
					direction="NW";

				point.setData(direction);

			} catch (Exception e) {
				logger.error("Error in getting parameter points",e);
			}
		}
		return point;
	}

}
