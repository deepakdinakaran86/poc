package com.pcs.device.gateway.hobbies.message;

import java.util.ArrayList;
import java.util.List;

import com.pcs.saffron.cipher.data.generic.message.GenericDevice;

public class HobbiesMessage{
	
	private String manufacturer;
	private String userName;
	private String email;
	private String contactNumber;
	private boolean userExisting = false;
	private GenericDevice device;
	private List<HobbiesConfigurationParameter> configuration;
	private List<HobbiesConfigurationParameter> data;
	private List<HobbiesCommand> commands;
	
	private String messageType;
	private Long timestamp;
	
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public GenericDevice getDevice() {
		return device;
	}
	
	public void setDevice(GenericDevice device) {
		this.device = device;
	}
	
	
	public boolean isUserExisting() {
		return userExisting;
	}
	public void setUserExisting(boolean userExisting) {
		this.userExisting = userExisting;
	}
	
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public List<HobbiesConfigurationParameter> getConfiguration() {
		return configuration;
	}
	public void setConfiguration(List<HobbiesConfigurationParameter> configuration) {
		this.configuration = configuration;
	}
	
	public void addConfiguration(HobbiesConfigurationParameter parameter){
		if(configuration == null){
			configuration = new ArrayList<HobbiesConfigurationParameter>();
		}
		if(parameter != null){
			configuration.add(parameter);
		}
	}
	public List<HobbiesConfigurationParameter> getData() {
		return data;
	}
	public void setData(List<HobbiesConfigurationParameter> data) {
		this.data = data;
	}
	public List<HobbiesCommand> getCommands() {
		return commands;
	}
	public void setCommands(List<HobbiesCommand> commands) {
		this.commands = commands;
	}
	
}
