
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
package com.pcs.ccd.heartbeat.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.ccd.heartbeat.scheduler.SchedulerUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Apr 4, 2016
 */
public class HeartbeatMsgActivator implements BundleActivator{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatMsgActivator.class);
	
	public void start(BundleContext arg0) throws Exception {
		LOGGER.info("Starting Heartbeat message sender");
		SchedulerUtil.init();
	}
	
	public void stop(BundleContext arg0) throws Exception {
		SchedulerUtil.shutdown();
		LOGGER.info("Stopping Heartbeat message sender");
	}

	public static void main(String[] args) throws Exception {
		SchedulerUtil.init();
    }
	

}
