/**
 * 
 */
package com.pcs.saffron.manager.authentication;

import com.pcs.saffron.manager.config.ManagerConfiguration;


/**
 * @author pcseg310
 *
 */
public abstract class BaseRequest {

	private String hostIp;
	private Integer port;
	private String scheme;

	protected BaseRequest() {
		setHostIp(ManagerConfiguration
				.getProperty(ManagerConfiguration.REMOTE_PLATFORM_IP));
		setPort(Integer.valueOf(ManagerConfiguration
				.getProperty(ManagerConfiguration.REMOTE_PLATFORM_PORT)));
		setScheme(ManagerConfiguration
				.getProperty(ManagerConfiguration.REMOTE_PLATFORM_SCHEME));
	}

	protected BaseRequest(Integer mode){
		switch (mode) {
		case 0:
			setHostIp(ManagerConfiguration
					.getProperty(ManagerConfiguration.REMOTE_PLATFORM_IP));
			setPort(Integer.valueOf(ManagerConfiguration
					.getProperty(ManagerConfiguration.REMOTE_PLATFORM_PORT)));
			setScheme(ManagerConfiguration
					.getProperty(ManagerConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 1:
			setHostIp(ManagerConfiguration
					.getProperty(ManagerConfiguration.DATASOURCE_PLATFORM_IP));
			setPort(Integer.valueOf(ManagerConfiguration
					.getProperty(ManagerConfiguration.DATASOURCE_PLATFORM_PORT)));
			setScheme(ManagerConfiguration
					.getProperty(ManagerConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 2:
			setHostIp(ManagerConfiguration
					.getProperty(ManagerConfiguration.ENTITY_PLATFORM_IP));
			setPort(Integer.valueOf(ManagerConfiguration
					.getProperty(ManagerConfiguration.ENTITY_PLATFORM_PORT)));
			setScheme(ManagerConfiguration
					.getProperty(ManagerConfiguration.REMOTE_PLATFORM_SCHEME));
			break;
		case 3:
			setHostIp(ManagerConfiguration
					.getProperty(ManagerConfiguration.DEVICE_WRITEBACK_RESPONSE_IP));
			setPort(Integer.valueOf(ManagerConfiguration
					.getProperty(ManagerConfiguration.DEVICE_WRITEBACK_RESPONSE_PORT)));
			setScheme(ManagerConfiguration
					.getProperty(ManagerConfiguration.REMOTE_PLATFORM_SCHEME));
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
