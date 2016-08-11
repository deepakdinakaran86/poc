package com.pcs.device.gateway.hobbies.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HobbiesCommand extends HobbiesConfigurationParameter {

	private String commandId;
	private Boolean writebackStatus;

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public Boolean getWritebackStatus() {
		return writebackStatus;
	}

	public void setWritebackStatus(Boolean writebackStatus) {
		this.writebackStatus = writebackStatus;
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		HobbiesCommand value = new HobbiesCommand();
		System.err.println(
				mapper.writeValueAsString(value));
	}
	
}
