/**
 * 
 */
package com.pcs.data.analyzer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.data.analyzer.executor.PointIdentifier;
import com.pcs.saffron.cipher.data.message.Message;

/**
 * @author pcseg129
 *
 */
public class DataAnalyzerUtil {
	
	private static Logger LOGGER = LoggerFactory
	        .getLogger(DataAnalyzerUtil.class);
	

	
	public static Message analyzeMessage(Message message,Object sourceId) throws Exception{
		PointIdentifier identifier = new PointIdentifier(sourceId, message);
		try {
			message = identifier.identifyPoints();
			LOGGER.info("---------Analyzed message {}",message.toString());
		} catch (Exception e) {
			LOGGER.error("Error occurred in message analysing");
			throw new Exception("Device Configuration not available",e);
		}
		return message;
	}
	
}
