package com.pcs.datasource.enums;

public enum CommandType {

	REBOOT("Reboot", 1), SYNC("Sync", 2), APP_SAVE("App Save", 3);

	private String commandType;
	private int commandId;

	public int getCommandId() {
		return commandId;
	}

	private CommandType(String commandType, Integer commandId) {
		this.commandType = commandType;
		this.commandId = commandId;
	}

	private String getCommandType() {
		return commandType;
	}

	public static CommandType getEnum(String value) {

		for (CommandType v : values()) {
			if (v.getCommandType().equalsIgnoreCase(value)) {
				return v;
			}
		}
		throw new IllegalArgumentException();
	}
}
