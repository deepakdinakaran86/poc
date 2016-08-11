package com.pcs.device.gateway.hobbies.message.data.type;


public enum HobbiesDataType {


	BOOLEAN("BOOLEAN",1),
	SHORT("SHORT",2),
	INT("INT",4),
	LONG("LONG",8),
	FLOAT("FLOAT",4),
	TIMESTAMP("TIMESTAMP",4),
	STRING("STRING"),
	DOUBLE("DOUBLE"),
	TEXT("TEXT");

	private String type;
	private Integer length;
	private static final HobbiesDataType[] values = values();
	
	HobbiesDataType(String type){
		this.type = type;
	}
	
	HobbiesDataType(String type,Integer length){
		this.type = type;
		this.length = length;
	}
	
	public static HobbiesDataType valueOfType(String type) {
		if (type == null) {
			return null;
		}
		for (HobbiesDataType dataType : values) {
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
	
	public Integer getLength(){
		return length;
				
	}


}
