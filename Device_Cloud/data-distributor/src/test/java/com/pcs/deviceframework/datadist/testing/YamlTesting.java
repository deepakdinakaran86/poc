
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
package com.pcs.deviceframework.datadist.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.yaml.snakeyaml.Yaml;

import com.pcs.deviceframework.datadist.beans.DistributionConfig;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 */
public class YamlTesting {
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		YamlTesting yamlTesting = new YamlTesting();
		yamlTesting.test();
	}

	private void test(){
		Yaml yaml = new Yaml();
		try {
			//File file = new File (System.getProperty("user.dir") + File.separator+"Configuration" + File.separator + "config.yml");
			File file = new File ("D:\\Seena\\config.yml");
			FileReader fileReader = new FileReader(file);
			// Object object = yaml.load(fileReader);
			DistributionConfig config = yaml.loadAs(fileReader, DistributionConfig.class);
			System.out.println("jms url : " + config.getJmsHostUrl());
			System.out.println("KafkaBrokers : " + config.getKafkaBrokers());
			System.out.println("Zookeeper : " + config.getZookeeper());
			System.out.println("zookeeperSessionTimeout : " + config.getZookeeperSessionTimeout());
			System.out.println("zookeeperSyncTime : " + config.getZookeeperSyncTime());
			System.out.println("autoCommitInterval : " + config.getKafkaAutoCommitInterval());
			System.out.println("autooffsetReset : " + config.getKafkaAutooffsetReset());
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
