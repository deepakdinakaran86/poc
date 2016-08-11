package com.pcs.device.gateway.meitrack.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ChecksumUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChecksumUtil.class);
	private static final String COMMA = ",";
	
	public static final Boolean verifyCheckSum(String data,String receivedChecksum){
	 //Implement checksum logic
		
		String[] seperatedData = data.split(COMMA);
		StringBuilder dataBuilder = new StringBuilder();
		for (int i = 3; i < seperatedData.length; i++) {
			dataBuilder.append(seperatedData[i]);
		}
		String dataString = dataBuilder.toString();
		LOGGER.info("Data part of the message is {}",dataString);
		Integer asciiDataSum = 0;
		for (int i = 0; i < dataString.length(); i++) {
			asciiDataSum += (int)dataString.charAt(i);
		}
		
		Integer.toHexString(asciiDataSum);
		return true;
		
	}
}
