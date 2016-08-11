package com.pcs.devicecloud.commons.exception;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.StringJoiner;

import com.pcs.devicecloud.commons.rest.dto.ErrorMessageDTO;
import com.pcs.devicecloud.constant.CommonConstants;

/**
 * Main Business exception for Galaxy core API services
 * 
 * @author RIYAS PH (pcseg296)
 */
public class DeviceCloudException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Integer code;
	private final String message;
	private final Throwable throwable;
	private final String fieldName;
	private String fieldValue;

	public enum ErrorAppendMode {
		PREFIX, SUFFFIX, IGNORE
	};

	public DeviceCloudException() {
		this(DeviceCloudErrorCodes.GENERAL_EXCEPTION);
	}

	public DeviceCloudException(Throwable t) {
		this(DeviceCloudErrorCodes.GENERAL_EXCEPTION, t);
	}

	public DeviceCloudException(DeviceCloudErrorCode errorCode, Throwable t) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.throwable = t;
		this.fieldName = null;
	}

	public DeviceCloudException(DeviceCloudErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.throwable = null;
		this.fieldName = null;
	}

	public DeviceCloudException(DeviceCloudErrorCode errorCode,
			String missingField) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.throwable = null;
		this.fieldName = missingField;
	}

	public DeviceCloudException(DeviceCloudErrorCode errorCode,
			String errorField, String errorValue) {
		this.code = errorCode.getCode();
		this.throwable = null;
		this.fieldName = null;
		this.fieldValue = errorValue;
		this.message = buildErrorMessage(errorField, fieldValue, errorCode
				.getMessage().trim());
	}

	public ErrorMessageDTO getErrorMessageDTO() {
		ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
		errorMessageDTO.setErrorCode(getCode().toString());
		errorMessageDTO.setErrorMessage(getErrorMessage());
		return errorMessageDTO;
	}

	public DeviceCloudException(String customError,
			DeviceCloudErrorCode errorCode) {
		this.code = errorCode.getCode();
		String errorMessage = String.format(CommonConstants.EXCEPTION_CONTENT,
				errorCode.getMessage(), customError);
		this.message = errorMessage;
		this.throwable = null;
		this.fieldName = null;
	}

	public DeviceCloudException(String customMsg,
			DeviceCloudErrorCode errorCode, ErrorAppendMode errorAppendMode) {
		this.code = errorCode.getCode();
		this.message = buildErrorMessage(customMsg,errorCode.getMessage().trim(),errorAppendMode);
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
		return "DeviceCloudException [errorCode=" + code + ",errorMessage="
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

	private String buildErrorMessage(String field1, String field2, String field3) {
		return String.format("%1$s %2$s %3$s", field1, field2, field3);
	}

	private String buildErrorMessage(String customMsg,String errorMsg, ErrorAppendMode errorAppendMode) {
		StringJoiner joiner = new StringJoiner(" ");
		switch (errorAppendMode) {
		case PREFIX:
			joiner.add(errorMsg).add(customMsg);
			return joiner.toString();

		case SUFFFIX:
			joiner.add(customMsg).add(errorMsg);
			return joiner.toString();
		
		case IGNORE:
			return customMsg;

		default:
			joiner.add(errorMsg).add(customMsg);
			return joiner.toString();
		}
		
	}

}
