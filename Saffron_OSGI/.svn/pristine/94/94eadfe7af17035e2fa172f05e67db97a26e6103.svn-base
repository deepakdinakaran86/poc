package com.pcs.saffron.manager.api.devicetype;

import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.api.configuration.bean.ConfigurationSearch;
import com.pcs.saffron.manager.bean.ProtocolPoints;

/**
 * Authentication service implementation.
 *
 * @author pcseg310
 *
 */
public class DeviceTypeService{


	private static final Logger logger = LoggerFactory.getLogger(DeviceTypeService.class);
	
	
	public HashMap<String, Point> getProtocolPoints(ConfigurationSearch configurationSearch){

		DeviceProtocolPointRequest request = new DeviceProtocolPointRequest(configurationSearch);
		String pathUrl = request.buildConfigurationUrl();
		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		try {
			ProtocolPoints protocolPoints =  client.post(pathUrl, null,configurationSearch, ProtocolPoints.class);
			HashMap<String, Point> pointMap = new HashMap<String, Point>();
			
			for (Iterator<Point> iterator = protocolPoints.getPoints().iterator(); iterator.hasNext();) {
				Point point = (Point) iterator.next();
				pointMap.put(point.getPointId(), point);
			}
			return pointMap;
		} catch (ClientException e) {
			logger.error("Exception occured while getting points for {} in {}!!",configurationSearch.getDeviceType(),configurationSearch.getProtocol(),e);
			return null;
		}finally{
			client = null;
		}
	}

}
