package com.pcs.saffron.cipher.data.generic.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GenericMessage {

	private String sourceId;
	private String parameterName;
	private Object value;
	private long timestamp;
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public Object getValue() {
		return value;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
