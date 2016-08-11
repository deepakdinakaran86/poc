/**
 * 
 */
package com.pcs.device.gateway.jace.writeback.api;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.jace.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.jace.utils.SupportedDevices;
import com.pcs.device.gateway.jace.utils.SupportedDevices.Devices;
import com.pcs.device.gateway.jace.writeback.api.beans.JaceCommand;
import com.pcs.device.gateway.jace.writeback.api.beans.WritebackResponse;
import com.pcs.device.gateway.jace.writeback.api.request.JaceWriteRequest;
import com.pcs.saffron.commons.apache.ApacheHTTPClient;
import com.pcs.saffron.connectivity.utils.Base64;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

/**
 * @author pcseg310
 *
 */
public class WritebackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WritebackService.class);

	public void pollData(JaceCommand command) {
		DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getJaceDeviceManager().getConfiguration(command.getSourceId(), SupportedDevices.getGateway(Devices.JACE_CONNECTOR));

		if (configuration == null || configuration.getDevice()==null) {
			return;
		}

		String deviceURL = configuration.getDevice().getURL();
		String userName = "admin";//configuration.getDevice().getUserName();
		String password = "Pcs*12345678";//configuration.getDevice().getPassword();

		JaceWriteRequest request = new JaceWriteRequest();
		String pathUrl = request.buildConfigurationUrl(deviceURL);


		WritebackResponse status = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			

			StringBuffer payload = new StringBuffer("query=");
			
			payload.append(objectMapper.writeValueAsString(command));
			
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
			
			status = objectMapper.readValue(data, WritebackResponse.class);
			LOGGER.info("Response status {}",status.getResponse());
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

		System.err.println(Base64.encode(new WritebackService().setAuthorization("admin", "Pcs*12345678").getBytes()));
	}
}
