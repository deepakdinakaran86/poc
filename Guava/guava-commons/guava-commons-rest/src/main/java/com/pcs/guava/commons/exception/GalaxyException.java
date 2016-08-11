package com.pcs.guava.commons.exception;

import static org.apache.commons.lang.StringUtils.isBlank;

import com.pcs.guava.commons.rest.dto.ErrorMessageDTO;
import com.pcs.guava.constant.CommonConstants;

/**
 * Main Business exception for Galaxy core API services
 *
 * @author pcseg199 (Javid Ahammed)
 * @date September 21 2014
 * @see GalaxyCommonErrorCodes
 */
public class GalaxyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Integer code;
	private final String message;
	private final Throwable throwable;
	private final String fieldName;

	public GalaxyException() {
		this(GalaxyCommonErrorCodes.GENERAL_EXCEPTION);
	}

	public GalaxyException(Throwable t) {
		this(GalaxyCommonErrorCodes.GENERAL_EXCEPTION, t);
	}

	public GalaxyException(GalaxyErrorCode errorCode, Throwable t) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.throwable = t;
		this.fieldName = null;
	}

	public GalaxyException(GalaxyErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.throwable = null;
		this.fieldName = null;
	}

	public GalaxyException(GalaxyErrorCode errorCode, String missingField) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.throwable = null;
		this.fieldName = missingField;
	}

	public ErrorMessageDTO getErrorMessageDTO() {
		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
		errorMessageDTO.setErrorCode(getCode().toString());
		errorMessageDTO.setErrorMessage(getErrorMessage());
		return errorMessageDTO;
	}

	public GalaxyException(String customError, GalaxyErrorCode errorCode) {
		this.code = errorCode.getCode();
		String errorMessage = String.format(CommonConstants.EXCEPTION_CONTENT,
		        errorCode.getMessage(), customError);
		this.message = errorMessage;
		this.throwable = null;
		this.fieldName = null;
	}

	protected String getErrorMessage() {
		if (isBlank(fieldName)) {
			return getMessage();
		}
		String errorMessage = String.format(CommonConstants.EXCEPTION_CONTENT,
		        fieldName, getMessage());
		return errorMessage;
	}

	@Override
	public String toString() {
		return "GalaxyException [errorCode=" + code + ",errorMessage="
		        + getErrorMessage() + "]";
	}

	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public String getFieldName() {
		return fieldName;
	}

}
