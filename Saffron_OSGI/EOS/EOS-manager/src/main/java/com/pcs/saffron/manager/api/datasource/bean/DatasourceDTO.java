/**
 * 
 */
package com.pcs.saffron.manager.api.datasource.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pcs.saffron.manager.api.configuration.bean.Status;
import com.pcs.saffron.manager.enums.MessageType;

/**
 * @author pcseg171
 * 
 */
public class DatasourceDTO {

	private String datasourceName;
	private List<Parameter> parameters = new ArrayList<Parameter>();
	private MessageType messageType;
	private String statusMessage;
	private Status status;
	private Date receivedTime;
	private Long time;

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(Parameter parameter){
		this.parameters.add(parameter);
	}
	
	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
