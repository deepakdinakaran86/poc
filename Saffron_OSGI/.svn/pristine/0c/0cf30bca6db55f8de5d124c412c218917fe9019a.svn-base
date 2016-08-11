package com.pcs.device.gateway.G2021.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.packet.exception.type.ExceptionType;
import com.pcs.saffron.cipher.data.message.Message;

public class ExceptionMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1973443803630314545L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMessage.class);
	
	
	private ExceptionType exceptionType;
	
	
	public ExceptionType getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(ExceptionType exceptionType) {
		LOGGER.info("Exception Message Found "+exceptionType);
		this.exceptionType = exceptionType;
	}
}
