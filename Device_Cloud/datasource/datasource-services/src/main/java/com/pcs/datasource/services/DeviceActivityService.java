/**
 * 
 */
package com.pcs.datasource.services;

import java.util.List;
import java.util.Set;

import com.pcs.datasource.dto.DeviceLatestUpdate;
import com.pcs.datasource.dto.DeviceStatusNew;
import com.pcs.datasource.dto.DeviceStatusResponse;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * @author pcseg129(Seena Jyothish)
 * @date 18 Apr 2016
 *
 */
public interface DeviceActivityService {

	public List<DeviceLatestUpdate> getAllActiveDevices();
	
	public DeviceStatusResponse getDeviceStatus(Set<String> sourceIds);
	
	public StatusMessageDTO persistDeviceTransition(List<DeviceStatusNew> deviceStatusList);
}
