package com.pcs.fms.web.services;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.constants.FMSWebConstants;
import com.pcs.fms.web.dto.DeviceLocationDTO;

@Service
public class TrackingVehicleDetailsService extends BaseService {

	@Value("${fms.device.location}")
	private String listVehicleWithLocationEPUri;

	public FMSResponse<List<DeviceLocationDTO>> listVehicles() {

		Type vehicleLocationList = new TypeToken<List<DeviceLocationDTO>>() {
			private static final long serialVersionUID = 3117155356363701471L;
		}.getType();

		String currentDomain = FMSAccessManager.getCurrentDomain();
		String mode = FMSWebConstants.ASSET_ENTITY_TEMP_NAME;
		String listVehicleUri = getServiceURI(listVehicleWithLocationEPUri);
		listVehicleUri = listVehicleUri.replace("{domain_name}", currentDomain);
		listVehicleUri = listVehicleUri.replace("{mode}", mode);

		return getPlatformClient().getResource(listVehicleUri,
		        vehicleLocationList);
	}

}
