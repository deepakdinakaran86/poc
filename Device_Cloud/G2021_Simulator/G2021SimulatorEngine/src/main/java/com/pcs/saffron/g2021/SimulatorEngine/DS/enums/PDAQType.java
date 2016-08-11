package com.pcs.saffron.g2021.SimulatorEngine.DS.enums;

public enum PDAQType {
	NOLOG(0),
	TIMEBASED(1),
	STATECHANGE(2),
	COV(3);
	
	private Integer value;
	private static final PDAQType[] values = values();
	
	private PDAQType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public static PDAQType valueOfType(Integer type) {
		if (type == null) {
			return null;
		}
		for (PDAQType packetType : values) {
			if (type.equals(packetType.getValue())) {
				return packetType;
			}
		}

		return null;
	}

}
