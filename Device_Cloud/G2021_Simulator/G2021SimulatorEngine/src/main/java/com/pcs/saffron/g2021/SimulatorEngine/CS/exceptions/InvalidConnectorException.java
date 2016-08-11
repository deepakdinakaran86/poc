/**
 * 
 */
package com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions;

/**
 * @author Aneesh
 *
 */
public class InvalidConnectorException extends RuntimeException {

	private static final long serialVersionUID = 1434118053590098417L;
	
	public InvalidConnectorException() {
		super();
	}

	public InvalidConnectorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidConnectorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidConnectorException(String message) {
		super(message);
	}

	public InvalidConnectorException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		return super.getCause();
	}

}
