package com.pcs.guava.rest.exception;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import com.pcs.guava.commons.rest.dto.ErrorMessageDTO;

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
	private Integer code;
	private String message;

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
			String errorMessage) {
		this.setErrorCodes(errorCode);
		this.code = errorCode.getErrorCode();
		this.message = errorMessage;
	}

	public GalaxyRestException(ErrorMessageDTO errorMessageDTO) {

		this.message = errorMessageDTO.getErrorMessage();
		try {
			this.code = parseInt(errorMessageDTO.getErrorCode());
		} catch (Exception e) {
		}

	}

	public GalaxyRestException(GalaxyRestErrorCodes errorCode,
			Exception unhandled) {
		this.setErrorCodes(errorCode);
		this.throwable = unhandled;
	}

	public GalaxyRestErrorCodes getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(GalaxyRestErrorCodes errorCodes) {
		this.errorCodes = errorCodes;
		this.code = errorCodes.getErrorCode();
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return isNotBlank(message) ? message : this.getErrorCodes()
				.getMessage();
	}

	public String getDescription() {
		return this.getErrorCodes().getDescription();
	}

	public Throwable getRootException() {
		return this.throwable;
	}

	@Override
	public String toString() {
		return "GalaxyRestException [errorCodes=" + errorCodes + "]";
	}
}
