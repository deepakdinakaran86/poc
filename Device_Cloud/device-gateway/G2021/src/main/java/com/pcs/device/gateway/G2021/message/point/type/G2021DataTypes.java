package com.pcs.device.gateway.G2021.message.point.type;


public enum G2021DataTypes {

	BOOLEAN("0000",1),
	SHORT("0001",2),
	INT("0010",4),
	LONG("0011",8),//------|reverse for local
	FLOAT("0100",4),//<----|
	TIMESTAMP("0101",4),
	STRING("0110"),
	TEXT("0111");

	private String type;
	private Integer length;
	private static final G2021DataTypes[] values = values();
	
	G2021DataTypes(String type){
		this.type = type;
	}
	
	G2021DataTypes(String type,Integer length){
		this.type = type;
		this.length = length;
	}
	
	public static G2021DataTypes valueOfType(String type) {
		if (type == null) {
			return null;
		}
		for (G2021DataTypes dataType : values) {
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
