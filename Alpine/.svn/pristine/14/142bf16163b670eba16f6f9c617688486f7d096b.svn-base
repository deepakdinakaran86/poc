/**
 * 
 */
package com.pcs.alpine.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Status
 * 
 * @description POJO for audit
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 09 Jul 2016
 * @since alpine-1.0.0
 */

@Table(name = "platform_audit_log")
public class Audit {

	@PartitionKey
	@JsonProperty("user_name")
	@Column(name = "user_name")
	private String userName;

	@JsonProperty("start_time")
	@Column(name = "start_time")
	private String startTime;

	@JsonProperty("resource_url")
	@Column(name = "resource_url")
	private String resourceUrl;
	
	@JsonProperty("action")
	@Column(name = "action")
	private String action;
	
	@JsonProperty("actor")
	@Column(name = "actor")
	private String actor;

	@JsonProperty("actor_identity")
	@Column(name = "actor_identity")
	private String actorIdentity;
	
	@JsonProperty("end_time")
	@Column(name = "end_time")
	private String endTime;
	
	@JsonProperty("ip_address")
	@Column(name = "ip_address")
	private String ipAddress;

	@JsonProperty("remarks")
	@Column(name = "remarks")
	private String remarks;
	
	@JsonProperty("target")
	@Column(name = "target")
	private String target;
	
	@JsonProperty("target_identity")
	@Column(name = "target_identity")
	private String targetIdentity;

	@JsonProperty("total_time")
	@Column(name = "total_time")
	private String totalTime;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getActorIdentity() {
		return actorIdentity;
	}

	public void setActorIdentity(String actorIdentity) {
		this.actorIdentity = actorIdentity;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetIdentity() {
		return targetIdentity;
	}

	public void setTargetIdentity(String targetIdentity) {
		this.targetIdentity = targetIdentity;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	
}
