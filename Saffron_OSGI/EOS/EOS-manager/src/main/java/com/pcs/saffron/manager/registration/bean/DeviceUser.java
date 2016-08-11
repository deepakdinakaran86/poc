package com.pcs.saffron.manager.registration.bean;

public class DeviceUser {

	private String username;
	private String lastname;
	private String email;
	private String contactNumber;
	private boolean userExisting; 
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	public boolean isUserExisting() {
		return userExisting;
	}
	public void setUserExisting(boolean userExisting) {
		this.userExisting = userExisting;
	}
	
}
