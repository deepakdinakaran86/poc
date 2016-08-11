
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
package com.pcs.alarm.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.alarm.beans.ConfigBean;

/**
 * This class is responsible for initializing application
 * 
 * @author pcseg129(Seena Jyothish)
 * Jul 31, 2016
 */
public class ApplicationStart {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationStart.class);
	
	private static ConfigBean configBean;
	
	private static boolean initzd = false;
	
	private static void init(){
		if(!initzd){
			readConfig();
			initzd = true;
		}
	}
	
	private static void readConfig(){
		FileReader fileReader = null;
		try{
			logger.info("System.getProperty(user.dir)  {}",System.getProperty("user.dir"));
			File file = new File (System.getProperty("user.dir") + File.separator+"Config" + File.separator + "config.properties");
			Properties prop = new Properties();
			fileReader = new FileReader(file);
			prop.load(fileReader);
			configBean = new ConfigBean();
			configBean.setCacheprovider(prop.getProperty("cacheprovider"));
			configBean.setCacheproviderConfigPath(prop.getProperty("cacheproviderConfigPath"));
			configBean.setPlatformHostIp(prop.getProperty("remote.platform.hostIp"));
			configBean.setPlatformPort(prop.getProperty("remote.platform.port"));
			configBean.setPlatformScheme(prop.getProperty("remote.platform.scheme"));
			configBean.setPlatformScheme(prop.getProperty("remote.platform.scheme"));
			configBean.setDeviceAlarmDefUrl(prop.getProperty("device.alarm.def.url"));
			configBean.setDeviceAlarmInfoUrl(prop.getProperty("device.alarm.info.url"));
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
	
	public static ConfigBean getConfigBean() {
		init();
		return configBean;
	}
	
	public static void main(String[] args) {
		init();
	}

}
