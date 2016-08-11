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
package com.pcs.deviceframework.datadist.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.pcs.deviceframework.datadist.beans.DistributionConfig;
import com.pcs.deviceframework.datadist.registry.DistributionRegistry;

/**
 * This class is responsible for setting the application configuration
 * 
 * @author pcseg129 (Seena Jyothish)
 * @date March 22 2015
 */
public class ApplicationConfiguration {

	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationConfiguration.class);
	public static DistributionConfig distributionConfig;

	private static boolean initzd = false;

	public static void init() {
		if (!initzd) {
			System.out.println("Application starting");
			distributionConfig = readDistributionConfig();
			initzd = true;
			DistributionRegistry.createRegistry();
		}

	}

	private static DistributionConfig readDistributionConfig() {
		Yaml yaml = null;
		FileReader fileReader = null;
		DistributionConfig config = null;
		try {
			yaml = new Yaml();
			File file = new File(System.getProperty("user.dir")
					+ File.separator + "Configuration" + File.separator
					+ "config.yml");
			fileReader = new FileReader(file);
			config = yaml.loadAs(fileReader, DistributionConfig.class);
		} catch (Exception ex) {
			System.out.println("Error reading config file --------");
			logger.error("Error while reading application configuration", ex);
			ex.printStackTrace();
		} finally {
			yaml = null;
			try {
				fileReader.close();
			} catch (IOException e) {
			}
		}
		return config;
	}

	public static void setDistributionConfig(
			DistributionConfig distributionConfig) {
		ApplicationConfiguration.distributionConfig = distributionConfig;
	}

	public static DistributionConfig getDistributionConfig() {
		init();
		return distributionConfig;
	}

	public static void main(String[] args) {
		init();
	}

}
