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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.jace.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.jace.datapoll.api.request.HistoryPollRequest;
import com.pcs.device.gateway.jace.message.beans.PointDetail;
import com.pcs.device.gateway.jace.utils.SupportedDevices;
import com.pcs.device.gateway.jace.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.data.generic.message.TimeseriesData;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;
import com.pcs.saffron.commons.apache.ApacheHTTPClient;
import com.pcs.saffron.connectivity.utils.Base64;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

/**
 * @author pcseg310
 *
 */
public class HistoryPollService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HistoryPollService.class);
	private static final DeviceManager jaceDeviceManager = DeviceManagerUtil.getJaceDeviceManager();

	public void pollData(DefaultConfiguration configuration) {

		if (configuration == null || configuration.getDevice()==null) {
			return;
		}

		String deviceURL = configuration.getDevice().getURL();
		String userName = "admin";//configuration.getDevice().getUserName();
		String password = "Pcs*12345678";//configuration.getDevice().getPassword();

		HistoryPollRequest request = new HistoryPollRequest();
		String pathUrl = request.buildConfigurationUrl(deviceURL);

		HistoryData historyData = null;
		try {

			//DateTime dateTime = new DateTime(DateTimeZone.forID(configuration.getDevice().getTimezone()!=null?configuration.getDevice().getTimezone():configuration.getDevice().getTimeZone()));
			DateTime dateTime = new DateTime(DateTimeZone.UTC);
			DateTime minusMinutes = dateTime.minusMinutes(2);
			
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			List<ConfigPoint> configPoints = configuration.getConfigPoints();
			ObjectMapper objectMapper = new ObjectMapper();
			
			for (ConfigPoint configPoint : configPoints) {
				if(!(configPoint.getPointName().equalsIgnoreCase("latitude") ||configPoint.getPointName().equalsIgnoreCase("longitude"))){
					PointExtension extensionByType = configPoint.getExtensionByType(PointDetail.HISTORY_PATH);
					if(extensionByType.getExtensionName() != null && extensionByType.getExtensionName().trim().length()>0){
						
						StringBuffer payload = new StringBuffer("query={\"hisid\":\"");
						payload.append(extensionByType.getExtensionName()).append("\",");
						payload.append("\"timest\":\"").append(fmt.print(minusMinutes.toDateTime(DateTimeZone.UTC)))
								.append("\",").append("\"timeend\":\"").append(fmt.print(dateTime.toDateTime(DateTimeZone.UTC))).append("\"}");
						
						HashMap<String, String> headers = new HashMap<String,String>();
						String payloadContent = payload.toString();
						LOGGER.info("Data {}",payloadContent);

						HttpPost post = new HttpPost(pathUrl);
						StringEntity entity = new StringEntity(payloadContent);
						entity.setContentType("application/x-www-form-urlencoded");
						post.setEntity( entity );

						post.addHeader("Authorization", "Basic "+Base64.encode(setAuthorization(userName,password).getBytes()));

						ApacheHTTPClient httpClient = ApacheHTTPClient.getInstance();
						HttpResponse response = httpClient.execute(post, headers);
						InputStream stream = response.getEntity().getContent();
						StringBuilder historyResponse = new StringBuilder();
						
						int r = 0;
						while ((r=stream.read()) > -1)
						{
							historyResponse.append((char)r);
						}
						String data = historyResponse.toString();
						LOGGER.info("Response {}",data);
						data = data.replaceAll("[\u0000-\u001f]", "");
						try {
							historyData = objectMapper.readValue(data, HistoryData.class);
							LOGGER.info("Response status {}",historyData.getSchema());
							for (String dataMsg : historyData.getData()) {
								
								String[] dataFragments = dataMsg.split(",");								
								TimeseriesData deviceData = new TimeseriesData();
								deviceData.setSourceId(configuration.getDevice().getSourceId());
								
								deviceData.setParameterName(configPoint.getDisplayName());
								deviceData.setTimestamp(Long.parseLong(dataFragments[0]));
								deviceData.setValue(dataFragments[1]);
								
								jaceDeviceManager.notifyGenericMessage(deviceData,SupportedDevices.getGateway(Devices.JACE_CONNECTOR));
							}
						} catch (Exception e) {
							LOGGER.error("Response not parsable as history data {}",data);
						}
					}
					
				}
			}
			
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
		DateTime dateTime = new DateTime(DateTimeZone.UTC);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		fmt.withZone(DateTimeZone.UTC);
		System.out.println(fmt.print(dateTime.toDateTime(DateTimeZone.UTC)));
	}
}
