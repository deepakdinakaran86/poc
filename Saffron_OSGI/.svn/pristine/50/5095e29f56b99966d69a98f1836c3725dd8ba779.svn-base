package com.pcs.saffron.manager.api.devicetype;

import com.pcs.saffron.manager.api.configuration.bean.ConfigurationSearch;
import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

/**
 * @author pcseg310
 *
 */
public class DeviceProtocolPointRequest extends BaseRequest {

	private String device;
	private String protocol;
	private ConfigurationSearch configurationSearch;

	
	/**
	 * @return the configurationSearch
	 */
	public ConfigurationSearch getConfigurationSearch() {
		return configurationSearch;
	}

	/**
	 * @param configurationSearch the configurationSearch to set
	 */
	public void setConfigurationSearch(ConfigurationSearch configurationSearch) {
		this.configurationSearch = configurationSearch;
	}

	public DeviceProtocolPointRequest() {
		super();
	}
	
	public DeviceProtocolPointRequest(String device, String protocol){
		this.device = device;
		this.protocol = protocol;
	}
	
	public DeviceProtocolPointRequest(ConfigurationSearch configurationSearch) {
		this.configurationSearch = configurationSearch;
	}

	public String getDevice() {
		return device;
	}


	public void setDevice(String device) {
		this.device = device;
	}

	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		return ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_PROTOCOL_POINTS_URL);
	}
}
