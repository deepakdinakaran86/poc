/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.auth;

import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;

/**
 * @author pcseg310
 *
 */
public abstract class BaseRequest {

	private String hostIp;
	private Integer port;
	private String scheme;

	protected BaseRequest() {
		setHostIp(G2021GatewayConfiguration
				.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_IP));
		setPort(Integer.valueOf(G2021GatewayConfiguration
				.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_PORT)));
		setScheme(G2021GatewayConfiguration
				.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_SCHEME));
	}

	protected BaseRequest(Integer mode){
		switch (mode) {
		case 0:
			setHostIp(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_IP));
			setPort(Integer.valueOf(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_PORT)));
			setScheme(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 1:
			setHostIp(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.DATASOURCE_PLATFORM_IP));
			setPort(Integer.valueOf(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.DATASOURCE_PLATFORM_PORT)));
			setScheme(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 2:
			setHostIp(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.ENTITY_PLATFORM_IP));
			setPort(Integer.valueOf(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.ENTITY_PLATFORM_PORT)));
			setScheme(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 3:
			setHostIp(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.DEVICE_WRITEBACK_RESPONSE_IP));
			setPort(Integer.valueOf(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.DEVICE_WRITEBACK_RESPONSE_PORT)));
			setScheme(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_SCHEME));
			break;

		case 4:
			setHostIp(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.DEVICE_WRITEBACK_CONFIG_IP));
			setPort(Integer.valueOf(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.DEVICE_WRITEBACK_CONFIG_PORT)));
			setScheme(G2021GatewayConfiguration
					.getProperty(G2021GatewayConfiguration.REMOTE_PLATFORM_SCHEME));
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
