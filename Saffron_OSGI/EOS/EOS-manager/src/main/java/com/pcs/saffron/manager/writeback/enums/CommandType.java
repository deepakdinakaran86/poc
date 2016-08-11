package com.pcs.saffron.manager.writeback.enums;


public enum CommandType {


	WRITE_COMMAND (0x00,"Point Write Command","Sent by hobbies devices for registration"),
	DATA_REQUEST(0x03,"Data request command"),
	SYSTEM_COMMAND (0x01,"System Command","Sent by hobbies devices for updating data");
	
	private Integer type;
	private String desc;
	private String indicator;
	private static final CommandType[] values = values();

	CommandType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	CommandType(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static CommandType valueOfType(Integer type) {
		if (type == null) {return null;}
		for (CommandType commandType : values) {
			if (type.equals(commandType.getType()))
			{
				return commandType;
			}
		}
		return null;
	}
	
	public static CommandType valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (CommandType commandType : values) {
			if (indicator.equalsIgnoreCase(commandType.getIndicator()))
			{
				return commandType;
			}
		}
		return null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
