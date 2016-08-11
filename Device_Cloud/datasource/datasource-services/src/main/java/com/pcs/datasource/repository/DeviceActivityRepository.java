/**
 * 
 */
package com.pcs.datasource.repository;

import java.util.List;
import java.util.Map;

import com.pcs.datasource.dto.DeviceLatestUpdate;
import com.pcs.datasource.dto.DeviceStatus;
import com.pcs.datasource.dto.DeviceStatusNew;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * @author pcseg129(Seena Jyothish)
 * @date 18 Apr 2016
 *
 */
public interface DeviceActivityRepository {
	
	public List<DeviceLatestUpdate> getAllActiveDevices();
	
	public List<DeviceStatus> getDeviceStatus(Map<String,String> srcIds);
	
	public StatusMessageDTO persistDeviceTransition(List<DeviceStatusNew> deviceStatusList);
	
}
