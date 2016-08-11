/**
 * 
 */
package com.pcs.device.gateway.meitrack.devicemanager.auth;

import com.pcs.device.gateway.meitrack.config.MeitrackGatewayConfiguration;

/**
 * @author pcseg310
 *
 */
public abstract class BaseRequest {

	private String hostIp;
	private Integer port;
	private String scheme;

	protected BaseRequest() {
		setHostIp(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_IP));
		setPort(Integer.valueOf(MeitrackGatewayConfiguration
		        .getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_PORT)));
		setScheme(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
	}

	protected BaseRequest(Integer mode) {
		switch (mode) {
		case 0:
			setHostIp(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_IP));
			setPort(Integer.valueOf(MeitrackGatewayConfiguration
			        .getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_PORT)));
			setScheme(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 1:
			setHostIp(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.DATASOURCE_PLATFORM_IP));
			setPort(Integer.valueOf(MeitrackGatewayConfiguration
			        .getProperty(MeitrackGatewayConfiguration.DATASOURCE_PLATFORM_PORT)));
			setScheme(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 2:
			setHostIp(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.ENTITY_PLATFORM_IP));
			setPort(Integer.valueOf(MeitrackGatewayConfiguration
			        .getProperty(MeitrackGatewayConfiguration.ENTITY_PLATFORM_PORT)));
			setScheme(MeitrackGatewayConfiguration.getProperty(MeitrackGatewayConfiguration.REMOTE_PLATFORM_SCHEME));
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
