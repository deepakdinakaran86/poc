/**
 * 
 */
package com.pcs.device.gateway.teltonika.devicemanager.auth;

import com.pcs.device.gateway.teltonika.config.TeltonikaGatewayConfiguration;

/**
 * @author pcseg310
 *
 */
public abstract class BaseRequest {

	private String hostIp;
	private Integer port;
	private String scheme;

	protected BaseRequest() {
		setHostIp(TeltonikaGatewayConfiguration
		        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_IP));
		setPort(Integer.valueOf(TeltonikaGatewayConfiguration
		        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_PORT)));
		setScheme(TeltonikaGatewayConfiguration
		        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
	}
	
	protected BaseRequest(Integer mode){
		switch (mode) {
		case 0:
			setHostIp(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_IP));
			setPort(Integer.valueOf(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_PORT)));
			setScheme(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 1:
			setHostIp(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.DATASOURCE_PLATFORM_IP));
			setPort(Integer.valueOf(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.DATASOURCE_PLATFORM_PORT)));
			setScheme(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 2:
			setHostIp(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.ENTITY_PLATFORM_IP));
			setPort(Integer.valueOf(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.ENTITY_PLATFORM_PORT)));
			setScheme(TeltonikaGatewayConfiguration
			        .getProperty(TeltonikaGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		default:
			break;
		}
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
