/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alpine.model;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Domain entity
 * 
 * @author pcseg292
 */

@Table(name = "domain")
public class Domain {

	private static final long serialVersionUID = 1L;

	public Domain() {

	}

	public Domain(String name, Integer statusKey, Integer maxConcurrentUsers,
	        Integer maxUsers, String domainUrl) {
		super();
		this.name = name;
		this.statusKey = statusKey;
		this.maxConcurrentUsers = maxConcurrentUsers;
		this.maxUsers = maxUsers;
		this.domainUrl = domainUrl;
	}

	@PartitionKey
	@Column(name = "name")
	private String name;

	@ClusteringColumn(value = 0)
	@JsonProperty("status_key")
	@Column(name = "status_key")
	private Integer statusKey;

	@Column(name = "max_concurrent_users")
	@JsonProperty("max_concurrent_users")
	private Integer maxConcurrentUsers;

	@Column(name = "max_users")
	@JsonProperty("max_users")
	private Integer maxUsers;

	@Column(name = "domain_url")
	@JsonProperty("domain_url")
	private String domainUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(Integer statusKey) {
		this.statusKey = statusKey;
	}

	public Integer getMaxConcurrentUsers() {
		return maxConcurrentUsers;
	}

	public void setMaxConcurrentUsers(Integer maxConcurrentUsers) {
		this.maxConcurrentUsers = maxConcurrentUsers;
	}

	public Integer getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

}
