package com.pcs.event.scanner.client;

import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.event.scanner.beans.EventfulParameter;
import com.pcs.event.scanner.beans.EventfulParameters;
import com.pcs.event.scanner.config.ScannerConfiguration;
import com.pcs.event.scanner.publisher.FormattedDataPublisher;
import com.pcs.jms.jmshelper.publishers.BasePublisher;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;

/**
 * @author pcseg310
 *
 */
public class EventHubPublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventHubPublisher.class);
	private static BasePublisher basePublisher = null;

	public static void publishMessage(Message message) throws NamingException, JMSException, IOException,
	InterruptedException {
		if(basePublisher == null){
			LOGGER.info("No producer found, creating new one");
			basePublisher = new FormattedDataPublisher(ScannerConfiguration.getProperty(ScannerConfiguration.EVENT_SCAN_SUSPECTS),
					ScannerConfiguration.getProperty(ScannerConfiguration.REALTIME_DATA_URL));
		}

		try {

			LOGGER.info("=======Device message: {}=========", message);

			List<Point> points = message.getPoints();
			EventfulParameters eventParameters = new EventfulParameters(message.getSourceId().toString(),message.getTime());
			for (Point point : points) {
				eventParameters.addParameter(new EventfulParameter(point.getDisplayName(), point.getData(), null));
			}
			LOGGER.info("*******Sent message: {}*********",eventParameters.getJSON());
			basePublisher.publish(eventParameters.getJSON());
			eventParameters = null;
			Thread.sleep(100);
		} catch (Exception e) {
			LOGGER.error("Error occured while preparing/sending message to service bus", e);
		} finally {

		}
	}


	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String test = "{\"sourceId\":\"464ff738-7ddc-4814-a4cd-6d1c5bd6f61a\",\"time\":\"1451214441000\",\"receivedTime\":\"1451214445081\",\"points\":[{\"pointId\":\"1\",\"pointName\":\"DIN1\",\"displayName\":\"Engine Status\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status string\",\"unit\":\"unitless\",\"data\":\"OFF\",\"status\":\"HEALTHY\",\"type\":\"STRING\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"speed\",\"pointName\":\"speed\",\"displayName\":\"GPS speed\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status double\",\"unit\":\"unitless\",\"data\":\"0\",\"status\":\"HEALTHY\",\"type\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"1\",\"pointName\":\"DIN1\",\"displayName\":\"Asset Status\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status string\",\"unit\":\"unitless\",\"data\":\"OFF\",\"status\":\"HEALTHY\",\"type\":\"STRING\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"Angle\",\"pointName\":\"Angle\",\"displayName\":\"Angle\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status double\",\"unit\":\"unitless\",\"data\":\"0\",\"status\":\"HEALTHY\",\"type\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"Angle\",\"pointName\":\"Angle\",\"displayName\":\"Direction\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status string\",\"unit\":\"unitless\",\"data\":\"N\",\"status\":\"HEALTHY\",\"type\":\"STRING\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"66\",\"pointName\":\"External Power Voltage\",\"displayName\":\"Vehicle Battery\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status double\",\"unit\":\"unitless\",\"data\":\"12.44\",\"status\":\"HEALTHY\",\"type\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"67\",\"pointName\":\"Internal Battery Voltage\",\"displayName\":\"Device Battery\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status double\",\"unit\":\"unitless\",\"data\":\"9.6\",\"status\":\"HEALTHY\",\"type\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"Longitude\",\"pointName\":\"Longitude\",\"displayName\":\"Longitude\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status double\",\"unit\":\"unitless\",\"data\":\"55.051452\",\"status\":\"HEALTHY\",\"type\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"Latitude\",\"pointName\":\"Latitude\",\"displayName\":\"Latitude\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status double\",\"unit\":\"unitless\",\"data\":\"24.9388878\",\"status\":\"HEALTHY\",\"type\":\"DOUBLE\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]},{\"pointId\":\"Visible Satellites\",\"pointName\":\"Visible Satellites\",\"displayName\":\"Visible Satellites\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"status integer\",\"unit\":\"unitless\",\"data\":\"20\",\"status\":\"HEALTHY\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"},{\"extensionName\":\"HEALTHY\",\"extensionType\":\"STATUS\"}],\"alarmExtensions\":[]}]}";
		Message message = mapper.readValue(test, Message.class);
		publishMessage(message);
	}

}
