package com.pcs.galaxy.services;

import java.util.List;

import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.galaxy.dto.GeofenceAlarmConfigDTO;

/**
 * DeviceThresholdService
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */

public interface GeofenceAlarmConfigService {

	public GeofenceAlarmConfigDTO saveGeoFenceConf(
	        GeofenceAlarmConfigDTO geofence);

	public StatusMessageDTO updteGeoFenceConf(GeofenceAlarmConfigDTO geofence);

	public StatusMessageDTO deleteGeoFenceConf(String configId);

	public StatusMessageDTO deleteGeoFenceOfDevice(String sourceId);

	public StatusMessageDTO deleteGeoFenceOfAsset(String assetName);

	public List<GeofenceAlarmConfigDTO> getGeoFenceConfForDevice(String sourceId);

	public List<GeofenceAlarmConfigDTO> getGeoFenceConfForAsset(String assetName);
}
