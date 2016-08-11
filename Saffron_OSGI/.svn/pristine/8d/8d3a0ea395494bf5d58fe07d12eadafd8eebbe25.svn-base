package com.pcs.custom;

import java.util.ArrayList;
import java.util.List;

import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.mediators.AbstractMediator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pcs.custom.dto.ConfigureDevice;
import com.pcs.custom.dto.Device;
import com.pcs.custom.dto.DeviceConfigTemplate;

public class SkysparkDevicePointMediator  extends AbstractMediator implements ManagedLifecycle{

	@Override
	public boolean mediate(MessageContext mc) {
		//Get input payload from message context
		String payload = (String)mc.getProperty("payloadIn"); 

		//construct the device payload
		Device device = (Device) new Gson()
		.fromJson( payload , Device.class);

		//construct the point payload
		String pointPayload = null;
		Gson gson = new Gson();
		if(device.getConfigurations()!= null && device.getConfigurations().getConfigPoints()!=null){
			ConfigureDevice configureDevice = contructPointsPayload(device);
			//construct point escaped payload
			pointPayload = escapePayload(gson.toJson(configureDevice));
		}

		//construct device escaped payload
		String devicePayload = escapePayload(gson.toJson(device));
		mc.setProperty("pointPayload",pointPayload);
		mc.setProperty("devicePayload",devicePayload);
		return true;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(SynapseEnvironment arg0) {
		// TODO Auto-generated method stub

	}

	private ConfigureDevice contructPointsPayload(Device device){
		ConfigureDevice configureDevice = new ConfigureDevice();
		List<String> sourceIds = new ArrayList<String>();
		sourceIds.add(device.getSourceId());
		configureDevice.setSourceIds(sourceIds);

		//set points, do not add the expression
		DeviceConfigTemplate deviceConfigTemplate = new DeviceConfigTemplate();

		//set the point details
		deviceConfigTemplate.setConfigPoints(device.getConfigurations().getConfigPoints());
		configureDevice.setConfTemplate(deviceConfigTemplate);

		return configureDevice;
	}

	private String escapePayload(String payload){
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
		return escapedString;
	}
}
