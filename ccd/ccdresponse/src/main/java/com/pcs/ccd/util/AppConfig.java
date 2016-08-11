
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
package com.pcs.ccd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.ccd.beans.RegistryConfig;
import com.pcs.ccd.utils.YamlUtils;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Apr 17, 2016
 */
public class AppConfig {
	private static final Logger logger = LoggerFactory
	        .getLogger(AppConfig.class);
	
	private static RegistryConfig regConfig = null;
	
	public static void init(){
		readConfig();
		try {
        } catch (Exception e) {
	        e.printStackTrace();
        }
	}
	
	private static void readConfig(){
		String filePath = "/config.yaml";
		try {
			regConfig = YamlUtils.copyYamlFromClassPath(RegistryConfig.class,
					filePath);
        } catch (Exception e) {
        	logger.error("Error reading the config file",e);
        }
	}

	public static RegistryConfig getRegConfig() {
		return regConfig;
	}

}
