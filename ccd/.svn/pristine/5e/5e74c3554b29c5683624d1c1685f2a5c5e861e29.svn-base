/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 *
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.fault.monitor.storm.bolts;

import java.util.Calendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.pcs.fault.monitor.util.FaultMonitorUtil;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * This class is responsible for sending fault notification request to engine
 * analytic platform
 * 
 * @author pcseg129(Seena Jyothish) Jan 20, 2016
 */
public class FaultSendBolt extends BaseBasicBolt {

	private static final long serialVersionUID = -9107506812753737988L;

	private static Logger LOGGER = LoggerFactory.getLogger(FaultSendBolt.class);

	public void execute(Tuple input, BasicOutputCollector collector) {
		Object sourceId = null;
		try {
			sourceId = input.getString(0);
			String faultData = input.getString(1);
			sendRequest(faultData);
		} catch (Exception e) {
			LOGGER.error("Error in request build bolt execution for device {}",
			        e, sourceId);
		}
	}

	// making public for testing , should be changed back to private
	public void sendRequest(String faultData) {
		
		Client httpClient = FaultMonitorUtil.buildRestClient();
		try {
			String statusCode = httpClient.post(
			        FaultMonitorUtil.getExtPlatformInfo().getPlatformUrl()
			                .replace("{token}", getToken()), null, faultData,null);
			LOGGER.info("status recived is {}", statusCode);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		
	}

	private String getToken() {
		String token;
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE) + 1;
		int year = cal.get(Calendar.YEAR);
		token = ""
		        + (FaultMonitorUtil.getExtPlatformInfo().getSeed() * (month + day + year));
		return FaultMonitorUtil.getExtPlatformInfo().getProviderKey() + token;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
