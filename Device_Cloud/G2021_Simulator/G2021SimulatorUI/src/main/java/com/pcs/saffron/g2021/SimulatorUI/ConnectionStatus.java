package com.pcs.saffron.g2021.SimulatorUI;

public enum ConnectionStatus {
	CS_DISCONNECTED(0),
	CS_CONNECTED(1),
	CS_CONNECTING(2),
	DS_DISCONNECTED(0),
	DS_CONNECTED(1);
	
	private int value;
	
	private ConnectionStatus(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
	
