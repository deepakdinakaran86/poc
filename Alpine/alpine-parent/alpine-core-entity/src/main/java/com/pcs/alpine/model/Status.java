/**
 * 
 */
package com.pcs.alpine.model;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Status
 * 
 * @description POJO for status
 * @author Daniela (pcseg191)
 * @date 11 Aug 2014
 * @since galaxy-1.0.0
 */

@Table(name = "status")
public class Status {

	@PartitionKey
	private UUID uuid;

	@JsonProperty("status_key")
	@Column(name = "status_key")
	private Integer statusKey;

	@JsonProperty("status_name")
	@Column(name = "status_name")
	private String statusName;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Integer getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(Integer statusKey) {
		this.statusKey = statusKey;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
