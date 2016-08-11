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
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.fault.monitor.bean.FaultData1;
import com.pcs.fault.monitor.enums.EntityStatus;
import com.pcs.fault.monitor.enums.FaultMsgType;
import com.pcs.fault.monitor.util.FaultMonitorUtil;

/**
 * This class is responsible for analyze the fault event, check fault already
 * exists or not etc
 * 
 * @author pcseg129(Seena Jyothish) Feb 2, 2016
 */
public class FaultAnalyzeBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 948166302814397901L;

	private static Logger LOGGER = LoggerFactory
	        .getLogger(FaultAnalyzeBolt.class);

	public static final String SOURCE_ID_KEY = "deviceId";
	public static final String FAULT_EVENT_MESSAGE_KEY = "faultEvent";
	public static final String FAULT_EVENT_STRING_MESSAGE_KEY = "faultEventsString";
	public static final String ENTITY_STATUS_KEY = "entityStatus";
	public static final String ROW_IDENTIFIER_KEY = "identifier";
	public static final String PREV_EVENT_KEY = "prevEvent";
	public static final String FAULT_MSG_TYPE = "faultMsgType";

	FaultData1 faultData1;
	FaultData1 eventData;
	EntityStatus entityStatus = EntityStatus.EXISTING_ENTITY;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		Object sourceId = input.getString(0);
		faultData1 = (FaultData1)input.getValue(1);
		String jsonValue = input.getString(2);

		ObjectMapper mapper = new ObjectMapper();
		try {
			faultData1 = mapper.readValue(jsonValue, FaultData1.class);

			LOGGER.info(
			        "faultData1 deviceId = {}, FMI = {},SPN = {},OC = {} ,event sttaus = {} ",
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

		LOGGER.info("faultData1.getFaultDataInfo().getDeviceId() "
		        + faultData1.getFaultDataInfo().getDeviceId());
		if (checkFaultIsNew(faultData1)) {
			LOGGER.info("fault is new , emitting ");
			String eventDataString = null;
			if (eventData != null) {
				try {
					eventDataString = mapper.writeValueAsString(eventData);
				} catch (JsonProcessingException e) {
					LOGGER.error("Error converting existing fault data to json string");
				}
			}

			collector.emit(new Values(sourceId, jsonValue, entityStatus
			        .toString(), eventDataString));
			collector.emit("FaultNotificationStream", new Values(
			        FaultMsgType.FAULT_MSG, faultData1, jsonValue));
		}else{
			LOGGER.info(" ***NOT emitting,fault is duplicate");
		}
		LOGGER.info("fault is {}", entityStatus.toString());
	}

	public void checkFautlt(FaultData1 faultData1, String msg)
	        throws JsonParseException, JsonMappingException, IOException {
		boolean checkFaultIsNew = checkFaultIsNew(faultData1);

		if (checkFaultIsNew) {
			// sendNotification(faultData1);
		}
		FaultEventPersistBolt evtPersist = new FaultEventPersistBolt();
		evtPersist.persist(faultData1, entityStatus, eventData);
	}

	private boolean checkFaultIsNew(FaultData1 faultData1) {
		eventData = FaultMonitorUtil.getLatestFaultEvt(faultData1);

		if (eventData != null) {
			int ocChange = compareOC(Short.parseShort(faultData1.getOC()),
			        Short.parseShort(eventData.getOC()));
			if (ocChange == 0) {
				entityStatus = EntityStatus.DUPLICATE_ENTITY;
				return false;
			}
			if (ocChange < 0) {
				faultData1.setOcCycle(eventData.getOcCycle() + 1);
				LOGGER.info(
				        "OC decreased for device {},indicating OC cycle change",
				        faultData1.getFaultDataInfo().getDeviceId());
				entityStatus = EntityStatus.EXISTING_ENTITY;
				return true;
			}
			if (ocChange > 0) {
				LOGGER.info("OC increased for device {}", faultData1
				        .getFaultDataInfo().getDeviceId());
				entityStatus = EntityStatus.EXISTING_ENTITY;
				return true;
			}
		}
		entityStatus = EntityStatus.NEW_ENTITY;
		return true;
	}

	private int compareOC(short newOC, short oldOC) {
		return Short.compare(newOC, oldOC);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(SOURCE_ID_KEY,
		        FAULT_EVENT_STRING_MESSAGE_KEY, ENTITY_STATUS_KEY,
		        PREV_EVENT_KEY));
		declarer.declareStream("FaultNotificationStream", new Fields(
		        FAULT_MSG_TYPE, FAULT_EVENT_MESSAGE_KEY,
		        FAULT_EVENT_STRING_MESSAGE_KEY));
	}

}
