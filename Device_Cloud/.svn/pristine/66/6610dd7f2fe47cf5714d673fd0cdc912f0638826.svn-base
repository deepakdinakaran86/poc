
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

import java.text.DecimalFormat;

import com.pcs.analytics.core.extractor.IOExtractor;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.point.Point;

/**
 * This class is responsible for Vehicle Battery extraction
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 31 2015
 */
public class Vehicle_Battery_Extractor extends IOExtractor {
	
	private static Vehicle_Battery_Extractor extractor = null;

	private Vehicle_Battery_Extractor(){
	}

	public static Vehicle_Battery_Extractor getInstance(){
		if(extractor == null){
			extractor = new Vehicle_Battery_Extractor();
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
		DecimalFormat decFormat = new DecimalFormat("#0.0");
		String pointData=(String)ConversionUtils.convertToLong(point.getData().toString());
		Float vehBat = Float.parseFloat(pointData)/1000;
		point.setData(decFormat.format(vehBat));
		
		decFormat = null;
		pointData=null;
		vehBat = null;
		
		return point;
	}

}
