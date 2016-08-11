/**
 * 
 */
package com.pcs.device.gateway.jace.writeback.api.request;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.jace.config.JaceConfiguration;

/**
 * @author pcseg310
 *
 */
public class JaceWriteRequest {

	private static final Logger LOGGER = LoggerFactory.getLogger(JaceWriteRequest.class);
	
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

	public String buildConfigurationUrl(String deviceURL){
		try {
			URL url = new URL(deviceURL);
			LOGGER.info("Request URL : {}, resolved as : {}",deviceURL,url);
			LOGGER.info("HOST {}",url.getHost());
			hostIp = url.getHost();
			LOGGER.info("Request hostIp : {}",hostIp);
			port = url.getPort();
			LOGGER.info("Request port : {}",port);
			scheme = "http";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceURL+getDatapollUrl();
	}
	
	public String getDatapollUrl() {
		return JaceConfiguration.getProperty(JaceConfiguration.DATAPOLL_WRITEBACK_URL);
	}
	
	public static void main(String[] args) {
		new JaceWriteRequest().getDatapollUrl();
	}

}
