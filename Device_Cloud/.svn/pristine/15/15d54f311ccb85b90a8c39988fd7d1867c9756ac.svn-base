
/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
 *
 * This software is the property of Pacific Controls  Software  Services LLC  and  its
 * suppliers. The intellectual and technical concepts contained herein are proprietary 
 * to PCSS. Dissemination of this information or  reproduction  of  this  material  is
 * strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
 * Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
 * MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.pcs.data.analyzer.bootstrap;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.data.analyzer.bean.ConfigBean;
import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cache.hazelcast.HazelCast;

/**
 * This class is responsible for initializing application
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 30 2015
 */
public class ApplicationConfig {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	private static CacheProvider cacheProvider;

	private static ConfigBean configBean;

	private static boolean initzd = false;


	public static ConfigBean getConfigBean() {
		init();
		return configBean;
	}

	public static void setConfigBean(ConfigBean configBean) {
		ApplicationConfig.configBean = configBean;
	}

	public static CacheProvider getCacheProvider() {
		init();
		return cacheProvider;
	}

	public static void init(){
		if(!initzd){
			readConfig();
			setCacheProvider();
			initzd = true;
		}
	}
	private static void setCacheProvider() {
		if(cacheProvider == null)
			cacheProvider = new HazelCast();
	}


	private static void readConfig(){
		FileReader fileReader = null;
		try{
			logger.info("System.getProperty(user.dir)  {}",System.getProperty("user.dir"));
			File file = new File (System.getProperty("user.dir") + File.separator+"Config" + File.separator + "config.properties");
			//URL url =  ClassLoader.getSystemResource("config.properties");
			Properties prop = new Properties();
			fileReader = new FileReader(file);
			prop.load(fileReader);
			configBean = new ConfigBean();
			configBean.setCacheprovider(prop.getProperty("cacheprovider"));
			configBean.setCacheproviderConfigPath(prop.getProperty("cacheproviderConfigPath"));
			configBean.setPlatformHostIp(prop.getProperty("remote.platform.hostIp"));
			configBean.setPlatformPort(prop.getProperty("remote.platform.port"));
			configBean.setPlatformScheme(prop.getProperty("remote.platform.scheme"));
			configBean.setDeviceUrl(prop.getProperty("remote.getdevice.url"));
			configBean.setDeviceConfigUrl(prop.getProperty("remote.getdevice.config.url"));
			configBean.setContextUrl(prop.getProperty("remote.getcontext.url"));
			configBean.setPhyQtyUrl(prop.getProperty("remote.getphyqty.url"));
			configBean.setAlpineHostIp(prop.getProperty("alpineHostIp"));
			configBean.setAlpinePort(prop.getProperty("alpinePort"));
			configBean.setAlpineScheme(prop.getProperty("alpineScheme"));
			configBean.setApiMgrClientId(prop.getProperty("apiMgrClientId"));
			configBean.setApiMgrClientSecret(prop.getProperty("apiMgrClientSecret"));
			configBean.setHeirarchyChildrenUrl(prop.getProperty("heirarchyChildrenUrl"));
		}catch(Exception ex){
			logger.error("Error while reading application configuration",ex);
		}finally{
			try {
				fileReader.close();
			} catch (IOException e) {
				logger.error("Error while reading the configuration file",e);
			}
		}
	}

	public static void main(String[] args) {
		init();
	}

}
