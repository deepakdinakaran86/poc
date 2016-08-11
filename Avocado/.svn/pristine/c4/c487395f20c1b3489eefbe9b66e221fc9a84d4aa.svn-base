package com.pcs.galaxy.rest.exception;

/**
 * Main Business exception for Galaxy core API services
 * 
 * @author rakesh
 * @see GalaxyRestErrorCodes
 */
public class GalaxyRestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private GalaxyRestErrorCodes errorCodes;
	private Throwable throwable;

	public GalaxyRestException() {
		this(GalaxyRestErrorCodes.GENERAL_EXCEPTION);
	}

	public GalaxyRestException(Throwable t) {
		this(GalaxyRestErrorCodes.GENERAL_EXCEPTION, t);
	}

	public GalaxyRestException(GalaxyRestErrorCodes errorCode, Throwable t) {
		this.setErrorCodes(errorCode);
		this.throwable = t;
	}

	public GalaxyRestException(GalaxyRestErrorCodes errorCode) {
		this.setErrorCodes(errorCode);
	}

	public GalaxyRestException(GalaxyRestErrorCodes errorCode,
	        Exception unhandled) {
		this.setErrorCodes(errorCode);
	}

	public GalaxyRestErrorCodes getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(GalaxyRestErrorCodes errorCodes) {
		this.errorCodes = errorCodes;
	}

	public Integer getCode() {
		return this.getErrorCodes().getErrorCode();
	}

	@Override
	public String getMessage() {
		return this.getErrorCodes().getMessage();
	}

	public String getDescription() {
		return this.getErrorCodes().getDescription();
	}

	public Throwable getRootException() {
		return this.throwable;
	}

	@Override
	public String toString() {
		return "GalaxyCoreException [errorCodes=" + errorCodes + "]";
	}
}
