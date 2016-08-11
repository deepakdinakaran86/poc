package com.pcs.device.gateway.jace.writeback.api.enums;


public enum JaceCommandMode {

	ACTION(2,"Action invocation"),
	PROPERTY(1,"Property write");
	
	private Integer type;
	private String desc;
	private String indicator;
	private static final JaceCommandMode[] values = values();

	JaceCommandMode(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	JaceCommandMode(Integer type, String indicator, String desc) {
		this.type = type;
		this.desc = desc;
		this.indicator = indicator;
	}
	
	public static JaceCommandMode valueOfType(Integer type) {
		if (type == null) {return null;}
		for (JaceCommandMode commandMode : values) {
			if (type.equals(commandMode.getType()))
			{
				return commandMode;
			}
		}
		return null;
	}
	
	public static JaceCommandMode valueOfIndicator(String indicator) {
		if (indicator == null) {return null;}
		for (JaceCommandMode commandMode : values) {
			if (indicator.equalsIgnoreCase(commandMode.getIndicator()))
			{
				return commandMode;
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
