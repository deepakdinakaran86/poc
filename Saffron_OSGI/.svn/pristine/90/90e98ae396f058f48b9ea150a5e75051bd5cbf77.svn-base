/**
 * 
 */
package com.pcs.device.gateway.jace.datapoll.api;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.jace.datapoll.api.request.DatapollRequest;
import com.pcs.saffron.commons.apache.ApacheHTTPClient;
import com.pcs.saffron.connectivity.utils.Base64;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

/**
 * @author pcseg310
 *
 */
public class DatapollService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatapollService.class);

	public void pollData(DefaultConfiguration configuration) {

		if (configuration == null || configuration.getDevice()==null) {
			return;
		}

		String deviceURL = configuration.getDevice().getURL();
		String userName = "admin";//configuration.getDevice().getUserName();
		String password = "Pcs*12345678";//configuration.getDevice().getPassword();

		DatapollRequest request = new DatapollRequest();
		String pathUrl = request.buildConfigurationUrl(deviceURL);


		RealtimeData status = null;
		try {


			List<ConfigPoint> configPoints = configuration.getConfigPoints();
			StringBuffer payload = new StringBuffer("query={\"hid\":\"");
			for (ConfigPoint configPoint : configPoints) {
				if(!(configPoint.getPointName().equalsIgnoreCase("latitude") ||configPoint.getPointName().equalsIgnoreCase("longitude")))
					payload.append(configPoint.getPointId()).append(",");
			}
			payload.deleteCharAt(payload.length()-1).append("\"").append("}");
			HashMap<String, String> headers = new HashMap<String,String>();
			String payloadContent = payload.toString();
			LOGGER.info("Data {}",payloadContent);

			pathUrl = pathUrl.replace(":80", "");
			LOGGER.info("Path URL {}",pathUrl);

			HttpPost post = new HttpPost(pathUrl);
			StringEntity entity = new StringEntity(payloadContent);
			entity.setContentType("application/x-www-form-urlencoded");
			post.setEntity( entity );

			post.addHeader("Authorization", "Basic "+Base64.encode(setAuthorization(userName,password).getBytes()));

			ApacheHTTPClient httpClient = ApacheHTTPClient.getInstance();
			HttpResponse response = httpClient.execute(post, headers);
			InputStream stream = response.getEntity().getContent();
			StringBuilder strWriter = new StringBuilder();
			
			int r = 0;//in.read();
			while ((r=stream.read()) > -1)
			{
				// r = in.read();

				strWriter.append((char)r);
			}
			String data = strWriter.toString();
			LOGGER.info("Response {}",data);
			data = data.replaceAll("[\u0000-\u001f]", "");
			status = new ObjectMapper().readValue(data, RealtimeData.class);
			LOGGER.info("Response status {}",status.getSchema());
		} catch (Exception e) {
			LOGGER.error("Exception occured while updating the Autoclaim configuration for device id [" + deviceURL + "] in platform",
					e);
		}
	}


	public String setAuthorization(String user, String pass)
	{
		if (user == null)
		{
			return null;
		}
		String authorization = user + ":" + ((pass == null) ? "" : pass);
		return authorization;
	}

	public static void main(String[] args) {

		System.err.println(Base64.encode(new DatapollService().setAuthorization("admin", "Pcs*12345678").getBytes()));
	}
}
