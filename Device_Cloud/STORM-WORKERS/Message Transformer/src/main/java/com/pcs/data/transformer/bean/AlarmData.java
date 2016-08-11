/**
 * 
 */
package com.pcs.data.transformer.bean;

import java.io.Serializable;

import com.pcs.saffron.cipher.data.point.enums.Criticality;

/**
 * This class is responsible for alarm data
 * 
 * @author pcseg129(Seena Jyothish) May 30, 2016
 */
public class AlarmData implements Serializable {
	
	private static final long serialVersionUID = -8168255279765024238L;
	
	String sourceId;
	String title;
	String name;
	String parameterName;
	String value;
	long timestamp;
	Criticality Criticality;
	String message;
	boolean status;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Criticality getCriticality() {
		return Criticality;
	}

	public void setCriticality(Criticality criticality) {
		Criticality = criticality;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
