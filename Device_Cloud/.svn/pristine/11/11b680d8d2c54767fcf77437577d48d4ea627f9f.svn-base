package com.pcs.iodecoder.teltonika;


import com.pcs.analytics.core.extractor.IOExtractor;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.iodecoder.constants.Constants;


public class DIN_On_Off_Extractor extends IOExtractor{

	
	private static DIN_On_Off_Extractor extractor = null;

	private DIN_On_Off_Extractor(){

	}

	public static DIN_On_Off_Extractor getInstance(){
		if(extractor == null){
			extractor = new DIN_On_Off_Extractor();
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
	
	public Point getSpecificData(Point point)throws Exception {
		String pointData=(String)ConversionUtils.convertToLong(point.getData().toString());
		if(Integer.parseInt(pointData)==1){
			point.setData(Constants.STATUS_ON);
		} else {
			point.setData(Constants.STATUS_OFF);
		}
		return point;
	}
}

