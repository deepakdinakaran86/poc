/**
 * 
 */
package com.pcs.saffron.manager.autoclaim.api.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.manager.config.ManagerConfiguration;

/**
 * @author pcseg310
 *
 */
public class AutoClaimRequest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoClaimRequest.class);
	
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

	public String buildConfigurationUrl(String pathParam){
		return getAutoclaimUrl().replace("{id}", pathParam);
	}
	
	public String getAutoclaimUrl() {
		LOGGER.info("Configurations : {},{},{}",ManagerConfiguration.getProperty(ManagerConfiguration.AUTOCLAIM_IP),ManagerConfiguration.getProperty(ManagerConfiguration.AUTOCLAIM_PORT)
				,ManagerConfiguration.getProperty(ManagerConfiguration.AUTOCLAIM_SCHEME));
		this.hostIp = ManagerConfiguration.getProperty(ManagerConfiguration.AUTOCLAIM_IP);
		this.port = Integer.parseInt(ManagerConfiguration.getProperty(ManagerConfiguration.AUTOCLAIM_PORT));
		this.scheme = ManagerConfiguration.getProperty(ManagerConfiguration.AUTOCLAIM_SCHEME);
		return ManagerConfiguration.getProperty(ManagerConfiguration.AUTOCLAIM_URL);
	}
	
	public static void main(String[] args) {
		new AutoClaimRequest().getAutoclaimUrl();
	}

}
