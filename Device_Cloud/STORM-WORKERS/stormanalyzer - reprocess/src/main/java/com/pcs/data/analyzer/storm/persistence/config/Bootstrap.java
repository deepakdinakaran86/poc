
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
package com.pcs.data.analyzer.storm.persistence.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.pcs.data.analyzer.storm.persistence.DataBatchWriter;
import com.pcs.data.analyzer.storm.persistence.beans.PersistConfig;

/**
 * This class is responsible for reading jms configuration from file
 * 
 * @author pcseg129
 */
public class Bootstrap {
	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	public void init(){
		PersistConfig config = readConfig();
		DataBatchWriter dataBatchWriter = new DataBatchWriter();
		dataBatchWriter.init(config);
	}

	private static PersistConfig readConfig(){
		Yaml yaml = null;
		FileReader fileReader = null;
		PersistConfig config = null;
		try{
			yaml = new Yaml();
			File file = new File (System.getProperty("user.dir") + File.separator+"Configuration" + File.separator + "persist-config.yml");
			fileReader = new FileReader(file);
			config = yaml.loadAs(fileReader, PersistConfig.class);
		}catch(Exception ex){
			logger.error("Error while reading JMS configuration",ex);
			ex.printStackTrace();
		}finally{
			yaml = null;
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Error while reading configuration",e);
			}
		}
		return config;
	}
	
	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.init();
    }
	
}
