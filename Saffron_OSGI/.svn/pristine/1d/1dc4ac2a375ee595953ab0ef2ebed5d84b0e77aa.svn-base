package com.pcs.saffron.manager.util;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;

/**
 * 
 * @author pcseg369
 * 
 */
public class CEPMessageConverter {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(CEPMessageConverter.class);

	private static HashMap<Object, Object> pointsConfiguration;

	/**
	 * 
	 * @param confFilePath
	 */
	public static void initialize(URL confFilePath) {
		try {

			pointsConfiguration = YamlUtils.copyYamlFromFile(HashMap.class,
			        confFilePath);
		} catch (Exception e) {
			LOGGER.error("CEPMessageConverter | initialize error {}", e);
		}
	}

	public static String getCEPInputMessage(Message message) {

		if (message == null) {
			return null;
		}


		String messageToReturn = null;
		try {

			ObjectMapper mapper = new ObjectMapper();
			ObjectNode objectNode1 = mapper.createObjectNode();
			objectNode1.put("sourceId", String.valueOf(message.getSourceId()));
			objectNode1
			        .put("receivedTime", message.getReceivedTime().getTime());
			objectNode1.put("time", message.getTime());

			List<Point> points = message.getPoints();
			for (int i = 0; i < points.size(); i++) {
				Point p = points.get(i);
				String key = (String)pointsConfiguration.get(new String(Base64
				        .encodeBase64(p.getDisplayName().getBytes())));
				if (key != null) {
					if (p.getData() instanceof Float) {
						objectNode1.put(key, (Float)p.getData());
					} else if (p.getData() instanceof String) {
						objectNode1.put(key, (String)p.getData());
					} else if (p.getData() instanceof Integer) {
						objectNode1.put(key, (Integer)p.getData());
					} else if (p.getData() instanceof Boolean) {
						objectNode1.put(key, (Boolean)p.getData());
					} else if (p.getData() instanceof Double) {
						objectNode1.put(key, (Double)p.getData());
					}
				}
			}

			Collection cepJsonkeys = pointsConfiguration.values();
			Iterator itr = cepJsonkeys.iterator();
			while (itr.hasNext()) {
				String jsonKey = (String)itr.next();
				if (!objectNode1.has(jsonKey)) {
					objectNode1.put(jsonKey, -999.99f);
				}

			}

			messageToReturn = objectNode1.toString();
			LOGGER.info("CEPMessageConverter | getCEPInputMessage {}",
			        messageToReturn);

		} catch (Exception e) {
			LOGGER.error("CEPMessageConverter | getCEPInputMessage {}", e);

		}
		return messageToReturn;
	}

}
