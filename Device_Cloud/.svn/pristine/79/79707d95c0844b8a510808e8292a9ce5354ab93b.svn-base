/**
 * 
 */
package com.pcs.device.activity.processor;

import static com.pcs.device.activity.utils.ActivityUpdaterUtil.getServicesConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.activity.beans.Device;
import com.pcs.device.activity.beans.DeviceLatestUpdate;
import com.pcs.device.activity.beans.DeviceTransition;
import com.pcs.device.activity.beans.StatusMessageDTO;
import com.pcs.device.activity.enums.DeviceCommStatus;
import com.pcs.device.activity.utils.ActivityUpdaterUtil;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Apr 18, 2016
 */
public class DeviceTransitionUpdater {

	private static final Logger logger = LoggerFactory
			.getLogger(DeviceTransitionUpdater.class);
	private static long schedLastRunTime;

	public static void start() {
		schedLastRunTime = new Date().getTime();
		List<DeviceLatestUpdate> deviceLatestUpdates = getAllActiveDevices();
		List<Device> devices = getAllDevices();
		List<DeviceTransition> transitionList = new ArrayList<DeviceTransition>();

		if (deviceLatestUpdates != null && !deviceLatestUpdates.isEmpty()) {
			onlineTransitionPersist(deviceLatestUpdates,transitionList);
			logger.info("{} online devices",transitionList.size());
		}else{
			logger.info("No online devices found");
		}

		if (devices != null && !devices.isEmpty()) {
			if (deviceLatestUpdates != null && !deviceLatestUpdates.isEmpty()) {
				List<Device> offlineDevices = findOfflineDevices1(
						deviceLatestUpdates, devices);
				if (!offlineDevices.isEmpty()) {
					logger.info("{} offline devices",offlineDevices.size());
					offlineTransitionPersist(offlineDevices,transitionList);
				}
			} else {
				logger.info("{} offline devices",devices.size());
				offlineTransitionPersist(devices,transitionList);
			}
		}else{
			logger.info("No devices (all devices) found");
		}
		
		if(!transitionList.isEmpty()){
			persistTransition(transitionList);
		}
	}

	private static List<DeviceLatestUpdate> getAllActiveDevices() {
		Client httpClient = ActivityUpdaterUtil.buildRestClient();

		List<DeviceLatestUpdate> deviceLatestUpdates = null;
		try {
			deviceLatestUpdates = httpClient.get(getServicesConfig()
					.getGetAllActiveDeviceApi(), null, List.class,
					DeviceLatestUpdate.class);
			return deviceLatestUpdates;
		} catch (ClientException e) {
			logger.error("Error in getting all active devices");
		}
		return null;
	}

	private static List<Device> getAllDevices() {
		Client httpClient = ActivityUpdaterUtil.buildRestClient();

		List<Device> devices = null;
		try {
			devices = httpClient.get(getServicesConfig()
					.getGetAllDeviceApi(), null, List.class, Device.class);
			return devices;
		} catch (ClientException e) {
			logger.error("Error in getting all devices");
		}
		return null;
	}

	private static List<Device> findOfflineDevices1(
			List<DeviceLatestUpdate> deviceLatestUpdates, List<Device> devices) {
		List<Device> offlineDevices = new ArrayList<Device>();
		for (Device device : devices) {
			boolean flag = false;
			for (DeviceLatestUpdate deviceLatestUpdate : deviceLatestUpdates) {
				if (device.getDeviceId().equals(
						deviceLatestUpdate.getDeviceId())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				offlineDevices.add(device);
			}
		}
		return offlineDevices;
	}

	private static DeviceTransition createDeviceOfflineMessage(Device device) {
		DeviceTransition deviceCurrState = new DeviceTransition();
		deviceCurrState.setDeviceId(device.getDeviceId());
		deviceCurrState.setStatus(DeviceCommStatus.OFFLINE);
		deviceCurrState.setLastOfflineTime(schedLastRunTime);
		return deviceCurrState;
	}

	private static DeviceTransition createDeviceOnlineMessage(
			DeviceLatestUpdate device) {
		DeviceTransition deviceCurrState = new DeviceTransition();
		deviceCurrState.setDeviceId(device.getDeviceId());
		deviceCurrState.setStatus(DeviceCommStatus.ONLINE);
		deviceCurrState.setLastOnlineTime(device.getLastUpdatedTime());
		return deviceCurrState;
	}


	private static void offlineTransitionPersist(List<Device> offlineDevices,List<DeviceTransition> transitionList) {
		for (Device device : offlineDevices) {
			transitionList.add(createDeviceOfflineMessage(device));
		}
	}

	private static void onlineTransitionPersist(
			List<DeviceLatestUpdate> deviceLatestUpdates,List<DeviceTransition> transitionList) {
		for (DeviceLatestUpdate device : deviceLatestUpdates) {
			transitionList.add(createDeviceOnlineMessage(device));
		}
	}
	
	private static void persistTransition(List<DeviceTransition> transitionList){
		logger.info("persisting {} transitions",transitionList.size());
		Client httpClient = ActivityUpdaterUtil.buildRestClient();

		try {
			 httpClient.post(getServicesConfig()
					.getPersistTransitionApi(), null,transitionList, StatusMessageDTO.class);
			 logger.error("transitions persisted");
		} catch (ClientException e) {
			logger.error("Error in getting all devices");
		}
	}
	

	public static void main(String[] args) {
		DeviceTransitionUpdater.start();
	}

}
