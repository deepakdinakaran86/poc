package com.pcs.device.gateway.G2021.message.extension.point.status.type;


public enum StatusType{

	HEALTHY("0000"),
	OVERRIDEN_IN1("0001"),
	OVERRIDEN_IN2("0010"),
	OVERRIDEN_IN3("0011"),
	OVERRIDEN_IN4("0100"),
	OVERRIDEN_IN5("0101"),
	OVERRIDEN_IN6("0110"),
	OVERRIDEN_IN7("0111"),
	OVERRIDEN_IN8("1000"),
	OVERRIDEN_IN9("1001"),
	OVERRIDEN_IN10("1010"),
	OVERRIDEN_IN11("1011"),
	OVERRIDEN_IN12("1100"),
	OVERRIDEN_FALLBACK("1101"),
	FAULTY("1110"),
	ALARM("1111");

	private String type;
	private static final StatusType[] values = values();
	
	StatusType(String type){
		this.type = type;
	}
	
	public static StatusType valueOfType(String type) {
		if (type == null) {
			return null;
		}
		for (StatusType dataType : values) {
			if (type.equals(dataType.getType())) {
				return dataType;
			}
		}

		return null;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
