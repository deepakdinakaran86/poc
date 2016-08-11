package com.pcs.saffron.g2021.SimulatorEngine.DS.enums;

public enum PDATAType {
	Boolean(0,"0000"),
	Short(1,"0001"),
	Integer(2,"0010"),
	Long(3,"0011"),
	Float(4,"0100"),
	String(5,"0101");
	
	private int value;
	private String bitData;
	
	PDATAType(int value, String bitData) {
	this.value = value;
	this.bitData = bitData;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}	
	
	public String getBitData() {
		return bitData;
	}

	public void setBitData(String bitData) {
		this.bitData = bitData;
	}

	public static PDATAType getDataType(Integer value) {
		if (value == null) {
			return null;
		}
		for (PDATAType packetType : values()) {
			if (value.equals(packetType.getValue())) {
				return packetType;
			}
		}

		return null;
	}
}
