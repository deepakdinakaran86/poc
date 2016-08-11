/**
 * 
 */
package com.pcs.device.gateway.enevo.onecollect.api.bean;



/**
 * @author pcseg310
 *
 */
public abstract class BaseRequest {

	private String hostIp;
	private Integer port;
	private String scheme;

	protected BaseRequest() {
		
	}


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

}
