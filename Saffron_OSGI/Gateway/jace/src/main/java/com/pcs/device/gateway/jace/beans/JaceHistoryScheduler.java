package com.pcs.device.gateway.jace.beans;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.jace.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.jace.datapoll.api.HistoryPollService;
import com.pcs.device.gateway.jace.utils.SupportedDevices;
import com.pcs.device.gateway.jace.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class JaceHistoryScheduler implements Job {

	private static final Logger LOGGER = LoggerFactory.getLogger(JaceHistoryScheduler.class);
	private static final DeviceManager jaceDeviceManager = DeviceManagerUtil.getJaceDeviceManager();
	Gateway gateway = SupportedDevices.getGateway(Devices.JACE_CONNECTOR);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DefaultConfiguration configuration = (DefaultConfiguration) jaceDeviceManager.getConfiguration("Win-687E-06BE-E9C1-B372", gateway);
		LOGGER.info("In schedule job {}",configuration);
		
		HistoryPollService hispollService = new HistoryPollService();
		hispollService.pollData(configuration);
	}

}
