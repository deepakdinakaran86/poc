package com.pcs.device.gateway.jace.writeback.api.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcs.device.gateway.jace.writeback.api.enums.JaceCommandMode;

public class JaceCommand {

	private String sourceId;
	@JsonProperty("mode")
	private Integer commandType;
	private JaceCommandMode mode;
	@JsonProperty("val")
	private List<PointCommand> commands;
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getCommandType() {
		return commandType;
	}
	public void setCommandType(Integer commandType) {
		this.commandType = commandType;
	}
	public JaceCommandMode getMode() {
		return mode;
	}
	public void setMode(JaceCommandMode mode) {
		this.mode = mode;
	}
	public List<PointCommand> getCommands() {
		return commands;
	}
	public void setCommands(List<PointCommand> commands) {
		this.commands = commands;
	}
	
	public void addCommand(PointCommand pointCommand){
		if(CollectionUtils.isEmpty(commands)){
			commands = new ArrayList<PointCommand>();
		}
		commands.add(pointCommand);
	}
	
	
}
