
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
import com.pcs.iodecoder.constants.Constants;

/**
 * This class is responsible for Altitude extraction
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 31 2015
 */
public class Altitude_Signed_Extractor extends IOExtractor {
	
	private static Altitude_Signed_Extractor extractor = null;

	private Altitude_Signed_Extractor(){

	}

	public static Altitude_Signed_Extractor getInstance(){
		if(extractor == null){
			extractor = new Altitude_Signed_Extractor();
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
	
	//Altitude = MOD(HEX2DEC(Byte2&Byte1)+2^15; 2^16)-2^15. 
	public Point getSpecificData(Point point) throws Exception {
		String pointValue = point.getData().toString();
		DecimalFormat decimalFormat = new DecimalFormat(Constants.TWO_POINT_DECIMAL);
		Double rawData = Double.parseDouble((String)ConversionUtils.convertToDecimal(pointValue));
		Double value2 = (rawData + Math.pow(2, 15)) % Math.pow(2, 16);
		Double value3 = value2 - Math.pow(2, 15);
		point.setData(decimalFormat.format(value3));
		
		pointValue = null;
		decimalFormat = null;
		rawData = null;
		value2 = null;
		value3 = null;
		
		return point;
	}
}
