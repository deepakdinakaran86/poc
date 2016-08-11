package com.pcs.device.gateway.meitrack.devicemanager.device.api;

import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;
import com.pcs.device.gateway.meitrack.devicemanager.bean.Point;
import com.pcs.device.gateway.meitrack.devicemanager.bean.ProtocolPoints;

/**
 * Authentication service implementation.
 *
 * @author pcseg310
 *
 */
public class DeviceTypeService{


	private static final Logger logger = LoggerFactory.getLogger(DeviceTypeService.class);
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Point> getProtocolPoints(String device, String protocol){

		DeviceProtocolPointRequest request = new DeviceProtocolPointRequest(device,protocol);
		String pathUrl = request.buildConfigurationUrl();
		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		try {
			ProtocolPoints protocolPoints =  client.get(pathUrl, null, ProtocolPoints.class);
			HashMap<String, Point> pointMap = new HashMap<String, Point>();
			
			for (Iterator<Point> iterator = protocolPoints.getPoints().iterator(); iterator.hasNext();) {
				Point point = (Point) iterator.next();
				pointMap.put(point.getPointId(), point);
			}
			return pointMap;
		} catch (ClientException e) {
			logger.error("Exception occured while getting points for {} in {}!!",device,protocol,e);
			return null;
		}finally{
			client = null;
		}
	}

}
