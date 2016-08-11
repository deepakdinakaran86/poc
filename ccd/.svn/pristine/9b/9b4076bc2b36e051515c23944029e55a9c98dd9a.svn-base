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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.fault.monitor.bean.FaultData1;
import com.pcs.fault.monitor.bean.FaultEventData;
import com.pcs.fault.monitor.enums.EntityStatus;
import com.pcs.fault.monitor.util.FaultMonitorUtil;

/**
 * This class is responsible for persisting fault event
 * 
 * @author pcseg129(Seena Jyothish) Jan 30, 2016
 */
public class FaultEventPersistBolt extends BaseBasicBolt {

	private static Logger LOGGER = LoggerFactory
	        .getLogger(FaultEventPersistBolt.class);

	private static final long serialVersionUID = -9094858351839122496L;
	FaultData1 faultData1;
	FaultData1 faultDataUpdate;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonValue = input.getString(1);
		String existingFaultDataJson = input.getString(3);
		try {
			faultData1 = mapper.readValue(jsonValue, FaultData1.class);

			if (existingFaultDataJson != null) {
				faultDataUpdate = mapper.readValue(existingFaultDataJson,
				        FaultData1.class);
				LOGGER.info("--Existing faultData with deviceId = {}, FMI = {},SPN = {},OC = {} ,event sttaus = {} ",
						faultDataUpdate.getFaultDataInfo().getDeviceId(), faultDataUpdate
		                .getFMI(), faultDataUpdate.getSPN(), faultDataUpdate.getOC(),
		                faultDataUpdate.getFaultDataInfo().getActive());
			}

			LOGGER.info(
			        "persisting faultData with deviceId = {}, FMI = {},SPN = {},OC = {} ,event sttaus = {} ",
			        faultData1.getFaultDataInfo().getDeviceId(), faultData1
			                .getFMI(), faultData1.getSPN(), faultData1.getOC(),
			        faultData1.getFaultDataInfo().getActive());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		EntityStatus entityStatus = EntityStatus.valueOf(input.getString(2));
		LOGGER.info("persisting " + faultData1.getFMI());
		persist(faultData1, entityStatus,faultDataUpdate);
	}

	// changing access to public only for testing
	// should be chnaged back to private after testing
	public void persist(FaultData1 faultData1, EntityStatus entityStatus,
	        FaultData1 faultDataUpdate) {
		LOGGER.info("persisting entity with entityStatus {} ",
		        entityStatus.toString());
		if (entityStatus.equals(EntityStatus.NEW_ENTITY)) {
			FaultMonitorUtil.persistFaultEvt(faultData1);
		} else if (entityStatus.equals(EntityStatus.EXISTING_ENTITY)) {
			FaultMonitorUtil
			        .updateExistingFaultEvt(faultDataUpdate);
			FaultMonitorUtil.persistFaultEvt(faultData1);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

}
