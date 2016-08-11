package com.pcs.saffron.g2021.SimulatorEngine.CS.message;

import java.sql.Timestamp;

public class PointDiscoveryAckMessage extends ServerMessage {	
	
	private static final long serialVersionUID = 1L;
	private Byte pdRecordCount;
	private Short leaseTime;
	private Timestamp timestamp;
	private String pId;
	
	public Byte getPdRecordCount() {
		return pdRecordCount;
	}
	public void setPdRecordCount(Byte pdRecordCount) {
		this.pdRecordCount = pdRecordCount;
	}
	public Short getLeaseTime() {
		return leaseTime;
	}
	public void setLeaseTime(Short leaseTime) {
		this.leaseTime = leaseTime;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
