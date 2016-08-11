package com.pcs.custom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.mediators.AbstractMediator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class StringifyMediator extends AbstractMediator implements ManagedLifecycle{


	private static final Log log = LogFactory.getLog(StringifyMediator.class);

	public StringifyMediator(){}


	@Override
	public boolean mediate(MessageContext mc) {
		//Get input payload from message context
		String payload = (String)mc.getProperty("payloadIn"); 
		payload = payload.replaceAll("\\r|\\n", "");
		ObjectMapper mapper = new ObjectMapper();

		String escapedString = "";
		try {
			String addEsp = mapper.writeValueAsString(payload);
			//Remove 1st and last quote character
			escapedString = addEsp.substring(1, addEsp.length()-1);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mc.setProperty("escapedPayload",escapedString);
		return true;
	}

	public String getType() {
		return null;
	}

	public void setTraceState(int traceState) {
		traceState = 0;
	}

	public int getTraceState() {
		return 0;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("in destry");

	}

	@Override
	public void init(SynapseEnvironment arg0) {

	}


}