package com.pcs.device.gateway.meitrack.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.connection.utils.ConversionUtils;

public abstract class ChecksumUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChecksumUtil.class);
	
	public static final Boolean verifyCheckSum(String data,String receivedChecksum){
		String dataForCheckSum = data.substring(0,data.length()-8);
		String[] splitAsBytes = (String[]) ConversionUtils.splitAsBytes(dataForCheckSum);
		Long calculatedChecksum = 0l;
		for (int i = 0; i < splitAsBytes.length; i++) {
			calculatedChecksum += Long.parseLong(ConversionUtils.convertToLong(splitAsBytes[i]).toString());
		}
		String calculatedChecksumHexa = Long.toHexString(calculatedChecksum).toUpperCase().substring(2);
		if(receivedChecksum.toUpperCase().equalsIgnoreCase(calculatedChecksumHexa)){
			LOGGER.info("{}(received) = {}(calculated)",receivedChecksum.toUpperCase(),calculatedChecksumHexa);
			return true;
		}else{
			LOGGER.info("{}(received) != {}(calculated)",receivedChecksum.toUpperCase(),calculatedChecksumHexa);
			return false;
		}
		
	}
}
