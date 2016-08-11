package com.pcs.saffron.manager.bean;

import java.util.ArrayList;
import java.util.List;

import com.pcs.saffron.cipher.data.message.Message;

public class GenericIngestionType {

	private DataIngestionBean ingestionBean;
	private List<DataIngestionBean> ingestionData;
	private Message message;
	
	public DataIngestionBean getIngestionBean() {
		return ingestionBean;
	}
	public Message getMessage() {
		return message;
	}
	public List<DataIngestionBean> getIngestionData() {
		return ingestionData;
	}
	public void setIngestionData(List<DataIngestionBean> ingestionData) {
		this.ingestionData = ingestionData;
	}
	public void setIngestionBean(DataIngestionBean ingestionBean) {
		this.ingestionBean = ingestionBean;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	public void addIngestionData(DataIngestionBean dataIngestionBean){
		if(this.ingestionData == null){
			this.ingestionData = new ArrayList<DataIngestionBean>();
		}
		this.ingestionData.add(dataIngestionBean);
	}
	
	
}
