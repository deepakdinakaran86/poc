package com.pcs.device.gateway.G2021.command;



public enum OperationCommandType {
	REBOOT((byte)01,"0x01",0x01),
	SYNC((byte)02,"0x02",0x01),
	APPSAVE((byte)03,"0x03",0x01);
	
	
	private byte command;
	private String indicator;
	private Integer type;
	private static final OperationCommandType[] values = values();
	
	public byte getCommand() {
		return command;
	}

	public void setCommand(byte command) {
		this.command = command;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	OperationCommandType(byte command) {
		this.command = command;
	}
	
	private OperationCommandType(byte command,String indicator,Integer type) {
		this.command = command;
		this.indicator = indicator;
		this.type = type;
	}
	
	public static OperationCommandType valueOfCommand(byte cmd) {
		for (OperationCommandType command : values) {
			if (cmd == command.getCommand())
			{
				return command;
			}
		}
		return null;
	}
	
	public static OperationCommandType valueOfType(Integer type) {
		for (OperationCommandType command : values) {
			if (type == command.getType())
			{
				return command;
			}
		}
		return null;
	}
	
	public static OperationCommandType valueOfIndicator(String indicator) {
		for (OperationCommandType command : values) {
			if (indicator.equalsIgnoreCase(command.getIndicator()))
			{
				return command;
			}
		}
		return null;
	}

}
