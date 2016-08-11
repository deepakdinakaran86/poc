package com.pcs.util.faultmonitor.ccd.fault.handler;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import kafka.producer.KeyedMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.message.status.MessageCompletion;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.cipher.data.point.utils.PointComparator;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;
import com.pcs.saffron.manager.bean.Tag;
import com.pcs.saffron.notification.broker.publisher.CorePublisher;
import com.pcs.saffron.notification.enums.DistributorMode;
import com.pcs.util.faultmonitor.ccd.fault.bean.DeviceData;
import com.pcs.util.faultmonitor.ccd.fault.bean.Parameter;
import com.pcs.util.faultmonitor.ccd.fault.bean.Snapshot;
import com.pcs.util.faultmonitor.ccd.fault.decoder.FaultDecoderV2;
import com.pcs.util.faultmonitor.ccd.fault.decoder.FaultDecoderV3;
import com.pcs.util.faultmonitor.configuration.FaultMonitorConfiguration;
import com.pcs.util.faultmonitor.osgi.util.DeviceManagerUtil;
import com.pcs.util.faultmonitor.osgi.util.MessageUtil;
import com.pcs.util.faultmonitor.util.SupportedDevices;
import com.pcs.util.faultmonitor.util.SupportedDevices.Devices;

/**
 * @author pcseg171
 *
 *Handler class that would check for snapshots from G2021 devices representing fault messages from the equipment.
 */
public class FaultDataHandler extends FileAlterationListenerAdaptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(FaultDataHandler.class);
	private static final Gateway gateway = SupportedDevices.getGateway(Devices.EDCP);
	/*
	 * Checks for a new directory creation, would have to authorize the file and register.
	 */
	@Override
	public void onDirectoryCreate(File directory) {
		LOGGER.info("New directory created {}",directory);
	}

	@Override
	public void onFileCreate(File file) {
		try {
			Thread.sleep(2000);//delay for file creation completion
			boolean configuredDevice = false;
			DeviceData deviceData = null;
			String absolutePath = file.getAbsolutePath();
			String[] fileRoot = absolutePath.split(Pattern.quote(String.valueOf(File.separatorChar)));
			String assetName = null;
			if(absolutePath.contains("RealTimeData")){
				deviceData = FaultDecoderV3.decode(file);
				if(fileRoot.length <3){
					LOGGER.error("Invalid file root {}",absolutePath);
				}else{
					assetName = fileRoot[(fileRoot.length-3)];
				}
			}else{
				deviceData = FaultDecoderV2.decode(file);
				if(fileRoot.length <2){
					LOGGER.error("Invalid file root {}",absolutePath);
				}else{
					assetName = fileRoot[(fileRoot.length-2)];
				}
			}
			LOGGER.error("assetName = {}",assetName);
			if(assetName != null){
				Device device = new Device();
				device.setDatasourceName(assetName);
				device.setSourceId(assetName);
				device.setDeviceName(assetName);
				device.setIsPublishing(true);
				device.setLatitude(Float.parseFloat(deviceData.getLatitude()));
				device.setLongitude(Float.parseFloat(deviceData.getLongitude()));
				ArrayList<Tag> tags = new ArrayList<Tag>();
				tags.add(new Tag("cummins"));
				tags.add(null);
				tags.add(null);
				device.setTags(tags);


				gateway.setCommunicationProtocol("FTP");
				AuthenticationResponse authenticate = DeviceManagerUtil.getG2021DeviceManager().authenticate(device, gateway);

				DefaultConfiguration configuration = null;
				if(authenticate.isAuthenticated()){
					LOGGER.info("Device authenticated for communication");
					List<String> parameterNames = deviceData.getParameterNames();
					List<ConfigPoint> configurationPoints = new ArrayList<ConfigPoint>();
					for (String parameterName : parameterNames) {
						ConfigPoint configPoint = new ConfigPoint();
						configPoint.setPointAccessType("READONLY");
						configPoint.setUnit("unitless");
						configPoint.setType("FLOAT");
						configPoint.setDataType("FLOAT");
						configPoint.setPointId(parameterName);
						configPoint.setPointName(parameterName);
						configPoint.setDisplayName(parameterName);
						configPoint.setPrecedence("1");
						configPoint.setType(PointDataTypes.DOUBLE.name());
						configurationPoints.add(configPoint);
					}
					configuration = (DefaultConfiguration) DeviceManagerUtil.getG2021DeviceManager().getConfiguration(assetName, gateway);
					if(configuration.getConfigPoints() == null || configuration.getConfigPoints().isEmpty()){
						configuration.addPointMappings(configurationPoints);
						configuration.setConfigPoints(configurationPoints);
						configuredDevice = true;
						configuration.setConfigured(true);
						DeviceManagerUtil.getG2021DeviceManager().setDeviceConfiguration(configuration, gateway);
					}else{
						PointComparator pointComparator = new PointComparator();
						Collections.sort(configuration.getConfigPoints(),pointComparator);
						Collections.sort(configurationPoints,pointComparator);
						pointComparator = null;
						configuredDevice = true;
						if(!configuration.getConfigPoints().equals(configurationPoints)){
							configuration.addPointMappings(configurationPoints);
							configuration.setConfigPoints(configurationPoints);
							configuration.setConfigured(true);
							DeviceManagerUtil.getG2021DeviceManager().setDeviceConfiguration(configuration, gateway);
						}
					}

				}else{
					LOGGER.info("New Device found");
				}
			}
			List<Serializable> deviceDataCollection = new ArrayList<Serializable>();
			KeyedMessage<Object, Object> publishMessage = null;
			if(deviceData == null){
				LOGGER.error("Data could not be parsed");
			}else{
				ObjectMapper mapper = new ObjectMapper();
				CorePublisher publisher = MessageUtil.getNotificationHandler().getPublisher(DistributorMode.KAFKA);
				List<Snapshot> availableSnaps = new ArrayList<Snapshot>(deviceData.getSnapshots());
				for (Snapshot snapshot : availableSnaps) {
					DeviceData data = new DeviceData();
					BeanUtils.copyProperties(data, deviceData);
					data.setSnapshots(null);
					List<Snapshot> eachSnapshot = new ArrayList<Snapshot>();
					eachSnapshot.add(snapshot);
					data.setSnapshots(eachSnapshot);
					String deviceDataJSON = mapper.writeValueAsString(data);
					publishMessage = new KeyedMessage<Object, Object>(FaultMonitorConfiguration.getProperty(FaultMonitorConfiguration.FAULT_MESSAGE_STREAM),
							deviceDataJSON);
					deviceDataCollection.add(publishMessage);
				}

				if(MessageUtil.getNotificationHandler() != null && deviceDataCollection !=null && !deviceDataCollection.isEmpty() ){
					publisher.publish(deviceDataCollection);
				}
				deviceDataCollection = null;
				availableSnaps = null;
				
				DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getG2021DeviceManager().getConfiguration(assetName, gateway);

				if(configuredDevice){
					DateTime dateTime = new DateTime(DateTimeZone.UTC);
					DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MMM-YYYY HH:mm:ss");
					fmt.withZone(DateTimeZone.UTC);
					deviceDataCollection = new ArrayList<Serializable>();
					List<Message>messages = new ArrayList<Message>();
					for (Snapshot snapshot : deviceData.getSnapshots()) {
						try {
							DateTime snapshotTime = fmt.parseDateTime(snapshot.getTimestamp().toUpperCase());
							Message message = new Message();
							message.setSourceId(deviceData.getDeviceId());
							message.setTime(snapshotTime.getMillis());
							message.setReceivedTime(dateTime.toDateTime(DateTimeZone.UTC).toDate());

							for (Parameter parameter : snapshot.getParameters()) {
								Point point = configuration.getPoint(parameter.getName());
								if(point != null){
									if(parameter.getValue().equalsIgnoreCase("nan")){
										point.setData(0D);
										PointExtension extension = new PointExtension("NA","STATUS");
										point.getExtensions().remove(extension);//removing existing status extensions
										point.setStatus("NA");
									}else{
										try {
											point.setData(Double.parseDouble(parameter.getValue()));
										} catch (Exception e) {
											LOGGER.error("Parameter value cannot be casted as Double {}",parameter.getValue());
										}
										
									}
									message.addPoint(point);
								}else{
									LOGGER.error("Mismatch in point configuration {}",parameter.getName());
								}
							}
							Point latitude = configuration.getPoint("Latitude");
							Point longitude =  configuration.getPoint("Longitude");;
							if(latitude != null && longitude !=null){
								latitude.setData(Double.parseDouble(deviceData.getLatitude()));
								longitude.setData(Double.parseDouble(deviceData.getLongitude()));
								message.addPoint(latitude);
								message.addPoint(longitude);
							}
							if(!CollectionUtils.isEmpty(message.getPoints())){
								messages.add(message);
								if(messages.size()>=10){
									DeviceManagerUtil.getG2021DeviceManager().notifyMessages(messages, configuration, MessageCompletion.COMPLETE, gateway);
									messages.clear();
								}
							}else{
								LOGGER.info("No points found on message {}",message.getPoints().size());
							}
						} catch (Exception e) {
							LOGGER.error("Error processing ",e);
						}
					}
					if(!CollectionUtils.isEmpty(messages)){
						DeviceManagerUtil.getG2021DeviceManager().notifyMessages(messages, configuration, MessageCompletion.COMPLETE, gateway);
						messages.clear();
					}
					messages = null;
					LOGGER.info("Published all messages ({})",deviceData.getSnapshots().size());
				}else{
					LOGGER.info("Message discarded for persistence as not configured yet.");
				}
			}

		} catch (Exception e) {
			LOGGER.error("Error processing fault file",e);
		}
	}
}
