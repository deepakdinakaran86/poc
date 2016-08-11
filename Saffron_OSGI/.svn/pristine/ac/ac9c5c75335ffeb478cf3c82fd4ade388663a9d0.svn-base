package com.pcs.device.gateway.hobbies.message.processor;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.pcs.device.gateway.hobbies.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.hobbies.message.HobbiesConfigurationParameter;
import com.pcs.device.gateway.hobbies.message.HobbiesMessage;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.message.status.MessageCompletion;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class DataMessageProcessor implements HobbiesMessageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataMessageProcessor.class);

	@Override
	public void processMessage(HobbiesMessage hobbiesMessage, Gateway gateway) {
		DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getHobbiesDeviceManager().getConfiguration(hobbiesMessage.getDevice().getDeviceId(), gateway);
		try {
			if(configuration != null && !CollectionUtils.isEmpty(configuration.getConfigPoints())){
				Message message = new Message();
				List<Point> dataPoints = new ArrayList<Point>();
				message.setSourceId(configuration.getDevice().getSourceId());
				message.setPoints(dataPoints);
				message.setReceivedTime(DateTime.now(DateTimeZone.UTC).toDate());
				message.setTime(hobbiesMessage.getTimestamp());
				List<HobbiesConfigurationParameter> parameterData = hobbiesMessage.getData();
				for (HobbiesConfigurationParameter parameter : parameterData) {
					Point point = configuration.getPoint(parameter.getParameterId());
					if(point != null){
						point.setData(parameter.getParameterValue());
						dataPoints.add(point);
						LOGGER.info("Point identified {}, value {}",point.getPointName(),point.getData().toString());
					}else{
						LOGGER.error("Invalid point {} with id {}",parameter.getParameterName(),parameter.getParameterId());
					}
				}
				DeviceManagerUtil.getHobbiesDeviceManager().notifyMessage(message, configuration, MessageCompletion.COMPLETE, gateway);
			}else{
				LOGGER.error("Invalid device found");
			}
		} catch (Exception e) {
			LOGGER.error("Could not process data",e);
		}

	}

	@Override
	public Object getServerMesssage() {

		return null;
	}

}
