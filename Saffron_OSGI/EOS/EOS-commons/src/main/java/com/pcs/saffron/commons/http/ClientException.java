package com.pcs.saffron.commons.http;

/**
 * @author pcseg310
 *
 */
public class ClientException extends Exception {

	private static final long serialVersionUID = 6963848424238323740L;

	public ClientException() {
		super();
	}

	public ClientException(String message) {
		super(message);
	}
	
	public ClientException(Throwable cause) {
		super(cause);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
