package com.pcs.device.gateway.hobbies.message.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.hobbies.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.hobbies.message.HobbiesConfigurationParameter;
import com.pcs.device.gateway.hobbies.message.HobbiesMessage;
import com.pcs.saffron.cipher.data.point.utils.PointComparator;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class RegistrationMessageProcessor implements HobbiesMessageProcessor{
	
	
	private static final DeviceManager hobbiesDeviceManager = DeviceManagerUtil.getHobbiesDeviceManager();
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationMessageProcessor.class);
	

	@Override
	public Object getServerMesssage() {
		
		return null;
	}


	@Override
	public void processMessage(HobbiesMessage hobbiesMessage, Gateway gateway) {
		DefaultConfiguration defaultConfiguration = (DefaultConfiguration) hobbiesDeviceManager.getConfiguration(hobbiesMessage.getDevice().getDeviceId(), gateway);
		
		List<HobbiesConfigurationParameter> configurations = hobbiesMessage.getConfiguration();
		try {
			List<ConfigPoint> deviceConfiguration = new ArrayList<ConfigPoint>();
			
			for (HobbiesConfigurationParameter parameter : configurations) {
				
				if(!(parameter.getParameterName().equalsIgnoreCase("Latitude") || parameter.getParameterName().equalsIgnoreCase("Longitude"))){
					ConfigPoint configPoint = new ConfigPoint();
					configPoint.setPointId(parameter.getParameterId());
					configPoint.setPointName(parameter.getParameterName());
					configPoint.setDisplayName(parameter.getParameterName());
					configPoint.setPrecedence("1");
					configPoint.setType(parameter.getParameterDataType().name());
					configPoint.setDataType(parameter.getParameterDataType().name());
					configPoint.setUnit(parameter.getUnit());
					defaultConfiguration.addPointMapping(parameter.getParameterId(), configPoint);
					deviceConfiguration.add(configPoint);
				}else{
					LOGGER.info("Paramaeee {}",parameter.getParameterName());
				}
			}
			LOGGER.info("Point configuration in cache is {}",new ObjectMapper().writeValueAsString(defaultConfiguration.getConfigPoints()));
			LOGGER.info("Received Point configuration is {}",new ObjectMapper().writeValueAsString(deviceConfiguration));
			if(!defaultConfiguration.isConfigured()){
				LOGGER.info("Initial configuration received");
				defaultConfiguration.setConfigPoints(deviceConfiguration);
				defaultConfiguration.setConfigured(true);
				hobbiesDeviceManager.setDeviceConfiguration(defaultConfiguration, gateway);
			}else{
				PointComparator comparator = new PointComparator();
				Collections.sort(deviceConfiguration,comparator);
				Collections.sort(defaultConfiguration.getConfigPoints(),comparator);
				if(!defaultConfiguration.getConfigPoints().equals(deviceConfiguration)){
					defaultConfiguration.setConfigPoints(new ArrayList<ConfigPoint>());
					hobbiesDeviceManager.refreshDeviceConfiguration(defaultConfiguration, gateway);
					LOGGER.info("Configuration update detected ");
					defaultConfiguration.setConfigPoints(deviceConfiguration);
					hobbiesDeviceManager.setDeviceConfiguration(defaultConfiguration, gateway);
				}else{
					LOGGER.info("Same configuration");
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("Error identifying registration message",e);
		}
		
		
	}

}
