package com.pcs.device.gateway.G2021.message;

import java.util.ArrayList;
import java.util.List;

import com.pcs.device.gateway.G2021.message.type.Reason;
import com.pcs.saffron.manager.bean.Tag;

public class HelloMessage extends G2021Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982350269706353800L;

	private Reason cause;
	private Integer cellularRSSi;
	private String ccKey;
	private String simICCID;
	private List<Tag> clientInfo;

	public Integer getCellularRSSi() {
		return cellularRSSi;
	}

	public void setCellularRSSi(Integer cellularRSSi) {
		this.cellularRSSi = cellularRSSi;
	}

	public String getCcKey() {
		return ccKey;
	}

	public void setCcKey(String ccKey) {
		this.ccKey = ccKey;
	}

	public String getSimICCID() {
		return simICCID;
	}

	public void setSimICCID(String simICCID) {
		this.simICCID = simICCID;
	}

	public void identifyReason(Integer reasonIndicator) {
		this.cause = Reason.valueOfType(reasonIndicator);
		setReason(this.cause.name());
	}

	public Reason getCause() {
		return cause;
	}

	public List<Tag> getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(List<Tag> clientInfo) {
		this.clientInfo = clientInfo;
	}

	public void addClientInfo(String clientInfo) {
		if (this.clientInfo == null)
			this.clientInfo = new ArrayList<Tag>();
		Tag tag = new Tag();
		tag.setName(clientInfo);
		this.clientInfo.add(tag);
	}
}
