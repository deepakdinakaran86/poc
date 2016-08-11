/**
 * 
 */
package com.pcs.datasource.services;

import static com.pcs.datasource.enums.DeviceActivityUpdateFields.DEVICE_STATUS_LIST;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_IDS;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateCollection;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.pcs.datasource.dto.DeviceLatestUpdate;
import com.pcs.datasource.dto.DeviceStatus;
import com.pcs.datasource.dto.DeviceStatusNew;
import com.pcs.datasource.dto.DeviceStatusResponse;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.repository.DeviceActivityRepository;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.devicecloud.commons.dto.StatusMessageDTO;

/**
 * @author pcseg129(Seena Jyothish)
 * @date 18 Apr 2016
 *
 */
@Service
public class DeviceActivityServiceImpl implements DeviceActivityService {

	@Autowired
	private DeviceActivityRepository deviceActivityRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private DeviceService deviceService;

	@Override
	public List<DeviceLatestUpdate> getAllActiveDevices() {

		List<DeviceLatestUpdate> deviceLatestUpdates = new ArrayList<DeviceLatestUpdate>();

		deviceLatestUpdates = deviceActivityRepository.getAllActiveDevices();
		validateResult(deviceLatestUpdates);

		return deviceLatestUpdates;
	}

	@Override
	public DeviceStatusResponse getDeviceStatus(Set<String> sourceIds) {
		validateCollection(SOURCE_IDS, sourceIds);
		DeviceStatusResponse response = new DeviceStatusResponse();
		HashMap<String, String> devReference = deviceRepository
				.getDeviceReference(sourceIds);

		List<GeneralResponse> generalResponses = new ArrayList<>();
		/*
		 * HashMap<String, String> deviceIds = validateDevice(sourceIds,
		 * generalResponses);
		 */
		Set<String> invalidDevices = null;
		if (devReference != null && !devReference.isEmpty()) {
			invalidDevices = findInvalidDevices(sourceIds,
					devReference.keySet());
			Map<String, String> mapInversed = devReference
					.entrySet()
					.stream()
					.collect(
							Collectors.toMap(Map.Entry::getValue,
									Map.Entry::getKey));

			List<DeviceStatus> deviceStatus = deviceActivityRepository
					.getDeviceStatus(mapInversed);
			validateResult(deviceStatus);
			response.setDeviceStatus(deviceStatus);
		}else {
			invalidDevices = sourceIds;
		}
		if(CollectionUtils.isNotEmpty(invalidDevices)){
			addToResponse(invalidDevices,generalResponses);
			response.setResponses(generalResponses);
		}
		return response;
	}

	@Override
	public StatusMessageDTO persistDeviceTransition(
			List<DeviceStatusNew> deviceStatusList) {
		validateCollection(DEVICE_STATUS_LIST, deviceStatusList);
		// List<GeneralResponse> generalResponses = new ArrayList<>();
		// List<DeviceStatus> validDeviceStatusList = new
		// ArrayList<DeviceStatus>();
		/*
		 * for (DeviceStatus deviceStatus : deviceStatusList) { if
		 * (isValidDevice(deviceStatus.getDeviceId())) {
		 * validDeviceStatusList.add(deviceStatus); } else {
		 * generalResponses.add(addRemark(deviceStatus.getDeviceId(),
		 * "Device source id is invalid")); } }
		 */
		return deviceActivityRepository
				.persistDeviceTransition(deviceStatusList);
	}

	private Set<String> findInvalidDevices(Set<String> sourceIds,
			Set<String> sourceIds1) {
		return Sets.difference(sourceIds, sourceIds1);
	}

	private void addToResponse(Set<String> sourceIds,
			List<GeneralResponse> generalResponses) {
		for (String sourceId : sourceIds) {
			generalResponses.add(addRemark(sourceId,
					"Device source id is invalid"));
		}
	}

	private GeneralResponse addRemark(String reference, String remark) {
		GeneralResponse generalResponse = new GeneralResponse();
		generalResponse.setReference(reference);
		generalResponse.setRemarks(remark);
		return generalResponse;
	}

}
