package com.pcs.saffron.g2021.SimulatorEngine.DS.eventQueue;

public class EventQueueData {
	
	private byte[] dataBuffer;
	private int retrialCount;	
	
	public byte[] getDataBuffer() {
		return dataBuffer;
	}
	public void setDataBuffer(byte[] dataBuffer) {
		this.dataBuffer = dataBuffer;
	}
	public int getRetrialCount() {
		return retrialCount;
	}
	public void setRetrialCount(int retrialCount) {
		this.retrialCount = retrialCount;
	}
	
}
