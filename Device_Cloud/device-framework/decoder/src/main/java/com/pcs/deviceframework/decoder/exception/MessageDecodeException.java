/**
 * 
 */
package com.pcs.deviceframework.decoder.exception;

/**
 * @author aneeshp
 *
 */
public class MessageDecodeException extends Exception {


	/** 
	 * 
	 */
	private static final long serialVersionUID = -3178605752240101685L;
	
	String strValue="";
	String key;

	public MessageDecodeException() {
		super();
	}

	public MessageDecodeException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public MessageDecodeException(String arg0) {
		super(arg0);
	}

	public MessageDecodeException(Throwable arg0) {
		super(arg0);
	}

	public MessageDecodeException(String message, String key) {
		super(message);
		this.key=key;
	}
}
