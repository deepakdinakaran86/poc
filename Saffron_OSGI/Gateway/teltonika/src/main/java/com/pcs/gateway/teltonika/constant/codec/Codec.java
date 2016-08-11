package com.pcs.gateway.teltonika.constant.codec;


public enum Codec {

	CODEC12("OC"),
	CODEC08("08"),
	CODEC00("00");
	
	private String hexaValue;
	private static final Codec values[] = values();
	
	Codec(){
		
	}
	
	Codec(String hexaValue){
		this.hexaValue = hexaValue;
	}

	public String getHexaValue() {
		return hexaValue;
	}

	public void setHexaValue(String hexaValue) {
		this.hexaValue = hexaValue;
	}
	
	
	public static Codec valueOfType(String hexaValue) {
		if (hexaValue == null) {
			return null;
		}
		for (Codec codec : values) {
			if (hexaValue.equals(codec.getHexaValue())) {
				return codec;
			}
		}
		return null;
	}
}
