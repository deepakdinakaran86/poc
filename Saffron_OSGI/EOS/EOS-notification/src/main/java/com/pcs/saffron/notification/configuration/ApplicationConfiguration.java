
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
package com.pcs.saffron.notification.configuration;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.pcs.saffron.notification.beans.DistributionConfig;

/**
 * This class is responsible for setting the application configuration
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 22 2015
 */
public final class ApplicationConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
	public static DistributionConfig distributionConfig; 


	public static final void init(String configPath){
		if(distributionConfig == null)
			distributionConfig = readDistributionConfig(configPath);
	}

	private static DistributionConfig readDistributionConfig(String configPath){
		Yaml yaml = null;
		DistributionConfig config = new DistributionConfig();
		try{
			URL locateConfig = ResourceMapper.locateConfig(configPath);
			logger.info("Yaml found at {}",locateConfig);
			yaml = new Yaml();
			config = yaml.loadAs(locateConfig.openStream(), DistributionConfig.class);
			logger.info("Zookeeper server {}",config.getZookeeper());
			logger.info("File created at {}",config);
		}catch(Exception ex){
			logger.error("Error while reading application configuration",ex);
		}finally{
			yaml = null;
		}
		return config;
	}

	public static final void setDistributionConfig(DistributionConfig distributionConfig) {
		ApplicationConfiguration.distributionConfig = distributionConfig;
	}

	public static final DistributionConfig getDistributionConfig() {
		return distributionConfig;
	}

}
