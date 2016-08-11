package com.pcs.device.gateway.commons.http;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author pcseg310
 *
 */
public class RequestInfo {

	private String hostIp;
	private Integer port;
	private String scheme;
	
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	
	public URI buildUri(String path) {
		StringBuilder url = new StringBuilder();
		url.append(this.scheme)
			.append("://")
			.append(this.hostIp)
			.append(":")
			.append(this.port)
			.append("/")
			.append(path);
		URI uri = null;
		try {
			uri = new URI(url.toString());
		} catch (URISyntaxException e) {
			// TODO 
		}
		return uri;
	}
}
