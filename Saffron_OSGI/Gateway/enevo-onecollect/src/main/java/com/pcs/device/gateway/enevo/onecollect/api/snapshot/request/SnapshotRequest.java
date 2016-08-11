package com.pcs.device.gateway.enevo.onecollect.api.snapshot.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pcs.device.gateway.enevo.onecollect.api.bean.BaseRequest;
import com.pcs.device.gateway.enevo.onecollect.config.EnevoOnecollectGatewayConfiguration;

/**
 * 
 * @author PCSEG311
 * 
 */

@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class SnapshotRequest extends BaseRequest {
	
	
	private String snapshotURL = EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.SNAPSHOT_REQUEST_URL);
	private String accessToken = null;
	
	public SnapshotRequest() {
		super();
	}
	
	public SnapshotRequest(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	/**
	 * @return the snapshotURL
	 */
	public String getSnapshotURL() {
		return snapshotURL;
	}

	/**
	 * @param snapshotURL the snapshotURL to set
	 */
	public void setSnapshotURL(String snapshotURL) {
		this.snapshotURL = snapshotURL;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
