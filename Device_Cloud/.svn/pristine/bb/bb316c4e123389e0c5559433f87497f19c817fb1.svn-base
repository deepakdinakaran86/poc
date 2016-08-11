
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

import com.pcs.analytics.core.extractor.IOExtractor;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.point.Point;


/**
 * This class is responsible for Latitude extraction
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 31 2015
 */
public class Latitude_Extractor extends IOExtractor {

	private static Latitude_Extractor extractor = null;
	public static final long MILLIONTH = 1000000;
	
	private Latitude_Extractor(){
	}

	public static Latitude_Extractor getInstance(){
		if(extractor == null){
			extractor = new Latitude_Extractor();
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
		String pointData = point.getData().toString();
		String[] dataForVerification = (String[])ConversionUtils.splitAsBytes(pointData);
		String bitVarification = ConversionUtils.convertToBinary(dataForVerification[0]).toString();
		
		if(bitVarification.charAt(0)=='1'){
			String twosComplement = ConversionUtils.getTwosComplement(pointData).toString();
			Integer twosComplementData = Integer.parseInt(twosComplement);
			point.setData(twosComplementData.doubleValue()/(MILLIONTH*10));
		}else{
			String lng = ConversionUtils.convertToLong(pointData).toString();
			point.setData(Double.parseDouble(lng)/(MILLIONTH*10));
		}
		
		pointData = null;
		dataForVerification = null;
		bitVarification = null;
		
		return point;
	}
}
