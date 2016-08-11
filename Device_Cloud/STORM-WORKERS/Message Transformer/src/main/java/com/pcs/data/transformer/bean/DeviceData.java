/**
 * 
 */
package com.pcs.data.transformer.bean;

import java.io.Serializable;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) May 29, 2016
 */
public class DeviceData implements Serializable {
	
	private static final long serialVersionUID = 5672721347424643980L;
	
	String sourceId;
	String parameterName;
	String value;
	long timestamp;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
