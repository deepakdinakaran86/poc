package com.pcs.data.analyzer.storm.model;

import java.io.Serializable;


public class CEPAlarmEventMessage implements Serializable {

	private CEPMetaData metaData;

	public CEPAlarmEventMessage() {
		super();
	}

	public CEPAlarmEventMessage(CEPMetaData metaData) {
		super();
		this.metaData = metaData;
	}

	public CEPMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(CEPMetaData metaData) {
		this.metaData = metaData;
	}

	@Override
	public String toString() {
		return "CEPAlarmEventMessage [metaData=" + metaData + "]";
	}
	
	
}
