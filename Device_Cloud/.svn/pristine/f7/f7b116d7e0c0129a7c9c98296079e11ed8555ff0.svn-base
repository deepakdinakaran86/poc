/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.services;

import static com.pcs.datasource.enums.AggregationFunctionConstants.AVG;
import static com.pcs.datasource.enums.AggregationFunctionConstants.COUNT;
import static com.pcs.datasource.enums.AggregationFunctionConstants.MAX;
import static com.pcs.datasource.enums.AggregationFunctionConstants.MIN;
import static com.pcs.datasource.enums.AggregationFunctionConstants.RANGE;
import static com.pcs.datasource.enums.AggregationFunctionConstants.SUM;
import static com.pcs.datasource.enums.DeviceDataFields.CONFIGURATION;
import static com.pcs.datasource.enums.DeviceDataFields.DATE_RANGE;
import static com.pcs.datasource.enums.DeviceDataFields.DISPLAY_NAMES;
import static com.pcs.datasource.enums.DeviceDataFields.END_DATE;
import static com.pcs.datasource.enums.DeviceDataFields.PHYSICAL_QUANTIY_NAME;
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.START_DATE;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DeviceMessageAggregatedResponse;
import com.pcs.datasource.dto.DeviceMessageResponse;
import com.pcs.datasource.dto.TimeSeriesData;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.LatestDataResultDTO;
import com.pcs.datasource.dto.LatestDataSearchDTO;
import com.pcs.datasource.dto.LatestDeviceData;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.SearchDTO;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.repository.DataRepo;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.validation.ValidationUtils;
import com.pcs.devicecloud.enums.Status;

/**
 * This service implementation class is responsible for defining all the
 * services related to data of a Device communicating to the system. This class
 * is responsible for persisting (single persist , batch persist) and fetching
 * the device data details from the communicating devices.
 * 
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private DataRepo dataRepo;

	@Autowired
	private PhyQuantityService phyQuantityService;

	@Autowired
	DeviceService deviceService;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DataServiceImpl.class);

	List<GeneralResponse> generalResponses = null;

	/**
	 * Method to fetch details of the device data communicating to the system
	 * 
	 * @param {@link SearchDTO}
	 * @return List<PhysicalQuantity>
	 */

	@Override
	public DeviceMessageResponse getDeviceData(SearchDTO searchDTO) {
		UUID uid = null;
		validateSearchDTO(searchDTO);

		DeviceConfigTemplate configurations = null;
		try {
			Device device = deviceService.getDevice(searchDTO.getSourceId());
			uid = UUID.fromString(device.getDeviceId());
			configurations = deviceService.getConfigurations(searchDTO
			        .getSourceId());
		} catch (
		        IllegalArgumentException | DeviceCloudException
		        | NullPointerException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		// remove duplicate elements from list of displayName tags
		Set<String> displayNameSet = new HashSet<String>();
		displayNameSet.addAll(searchDTO.getDisplayNames());
		searchDTO.getDisplayNames().clear();
		searchDTO.getDisplayNames().addAll(displayNameSet);

		List<ConfigPoint> fetchPoints = new ArrayList<ConfigPoint>();
		if (configurations == null
		        || CollectionUtils.isEmpty(configurations.getConfigPoints())) {
			throw new DeviceCloudException(CONFIGURATION.getDescription()
			        + " for the device is not available",
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		for (String displayName : searchDTO.getDisplayNames()) {
			for (ConfigPoint configPoint : configurations.getConfigPoints()) {
				if (displayName.equalsIgnoreCase(configPoint.getDisplayName())) {
					PhysicalQuantity physicalQuantity = phyQuantityService
					        .getPhyQuantityByName(configPoint
					                .getPhysicalQuantity());
					try {
						if (physicalQuantity != null) {
							configPoint.setPhysicalQuantity(physicalQuantity
							        .getDataStore());
							configPoint.setDataType(physicalQuantity
							        .getDataType().name());
							fetchPoints.add(configPoint);
						}
					} catch (Exception e) {
						LOGGER.error("Error identifying physical quantity", e);
					}

					break;
				}
			}
		}
		if (CollectionUtils.isEmpty(fetchPoints)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        DISPLAY_NAMES.getDescription());
		}
		DateTime startDateValue = new DateTime(searchDTO.getStartDate())
		        .toDateTime(DateTimeZone.UTC);
		DateTime endDateValue = new DateTime(searchDTO.getEndDate())
		        .toDateTime(DateTimeZone.UTC);
		validateDateRange(startDateValue, endDateValue);

		DeviceMessageResponse deviceMessageResponse = null;
		List<TimeSeriesData> displayNamesList = new ArrayList<TimeSeriesData>();
		try {
			displayNamesList = dataRepo.getDeviceData(uid, startDateValue,
			        endDateValue, fetchPoints);
		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        PHYSICAL_QUANTIY_NAME.getDescription());
		}
		if (CollectionUtils.isNotEmpty(displayNamesList)) {
			deviceMessageResponse = new DeviceMessageResponse();
			deviceMessageResponse.setSourceId(uid);
			deviceMessageResponse.setDisplayNames(displayNamesList);
			return deviceMessageResponse;
		} else {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
	}

	/**
	 * validate search conditions provided in the input
	 * 
	 * @param physicalQuantityName
	 * @param searchDTO
	 */
	private void validateSearchDTO(SearchDTO searchDTO) {
		validateMandatoryFields(searchDTO, SOURCE_ID, START_DATE, END_DATE,
		        DISPLAY_NAMES);
		if (CollectionUtils.isEmpty(searchDTO.getDisplayNames())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        DISPLAY_NAMES.getDescription());
		}
	}

	/**
	 * validate the date range input from the input
	 * 
	 * @param startDateValue
	 * @param endDateValue
	 */
	private void validateDateRange(DateTime startDateValue,
	        DateTime endDateValue) {
		if (Days.daysBetween(startDateValue, endDateValue).getDays() < 0) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        DATE_RANGE.getDescription());
		}
	}

	public static void main(String[] args) {
		Device deviceA = new Device();
		Subscription subscription = new Subscription();
		subscription.setSubId("1");
		deviceA.setSubscription(subscription);
		Device deviceB = deviceA;
		deviceB.setSubscription(null);

	}

	@Override
	public List<LatestDeviceData> getDeviceLatestData(String sourceId) {

		validateMandatoryField(SOURCE_ID, sourceId);

		Device device = null;

		try {
			device = deviceService.getDevice(sourceId);
		} catch (DeviceCloudException e) {
			LOGGER.debug("No Device Found for sourceId:{}", sourceId, e);
		}

		if (device == null) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}

		DeviceConfigTemplate configurations = deviceService.getConfigurations(
		        sourceId, device.getSubscription());

		List<ConfigPoint> points = null;
		if (configurations != null) {
			points = configurations.getConfigPoints();
		}

		if (CollectionUtils.isEmpty(points)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
			        CONFIGURATION.getDescription());
		}

		List<LatestDeviceData> data = new ArrayList<LatestDeviceData>();
		for (ConfigPoint configPoint : points) {
			String pQName = configPoint.getPhysicalQuantity();
			PhysicalQuantity physicalQuantity = phyQuantityService
			        .getPhyQuantityByName(pQName);
			if (physicalQuantity != null) {
				String displayName = configPoint.getDisplayName();
				LatestDeviceData latestData = dataRepo.getLatestData(
				        device.getDeviceId(), physicalQuantity.getDataStore(),
				        displayName);
				if (latestData != null) {
					latestData.setDisplayName(displayName);
					latestData.setUnit(configPoint.getUnit());
					data.add(latestData);
				}
			}
		}
		ValidationUtils.validateResult(data);
		return data;
	}

	@Override
	public List<LatestDataResultDTO> getDevicesLatestData(
	        List<LatestDataSearchDTO> LatestDataSearch) {

		List<LatestDataResultDTO> latestDataResults = null;

		if (CollectionUtils.isNotEmpty(LatestDataSearch)) {
			latestDataResults = new ArrayList<LatestDataResultDTO>();
			for (LatestDataSearchDTO latestDataSearchDTO : LatestDataSearch) {
				LatestDataResultDTO latestDataResult = null;
				if (latestDataSearchDTO != null) {

					if (StringUtils.isNotEmpty(latestDataSearchDTO
					        .getSourceId())
					        && CollectionUtils.isNotEmpty(latestDataSearchDTO
					                .getPoints())) {

						String sourceId = latestDataSearchDTO.getSourceId();
						Device device = null;
						try {
							device = deviceService.getDevice(sourceId);
						} catch (Exception e) {
							LOGGER.error(
							        "No Device Found for sourceId:{} for fetching latest data",
							        sourceId);
							continue;
						}

						if (device != null) {

							List<String> requestPoints = latestDataSearchDTO
							        .getPoints();

							DeviceConfigTemplate configurations = null;
							try {
								configurations = deviceService
								        .getConfigurations(sourceId,
								                device.getSubscription());
							} catch (Exception e) {
								LOGGER.error(
								        "No point configurtions found for sourceId:{} for fetching latest data",
								        sourceId);
								continue;
							}

							List<ConfigPoint> points = null;
							if (configurations != null) {
								points = configurations.getConfigPoints();
							}
							latestDataResult = new LatestDataResultDTO();
							latestDataResult.setSourceId(sourceId);
							List<LatestDeviceData> data = new ArrayList<LatestDeviceData>();
							if (CollectionUtils.isNotEmpty(points)) {

								for (ConfigPoint configPoint : points) {
									if (requestPoints.contains(configPoint
									        .getDisplayName())) {
										String pqName = configPoint
										        .getPhysicalQuantity();
										PhysicalQuantity physicalQuantity = phyQuantityService
										        .getPhyQuantityByName(pqName);
										if (physicalQuantity != null) {
											String displayName = configPoint
											        .getDisplayName();
											LatestDeviceData latestData = dataRepo
											        .getLatestData(
											                device.getDeviceId(),
											                physicalQuantity
											                        .getDataStore(),
											                displayName);
											if (latestData != null) {
												latestData
												        .setDisplayName(displayName);
												latestData.setUnit(configPoint
												        .getUnit());
												data.add(latestData);
											}
										}
									}
								}
							}
							latestDataResult.setData(data);
						}
					}
				}
				if (latestDataResult != null) {
					latestDataResults.add(latestDataResult);
				}
			}
		}
		ValidationUtils.validateResult(latestDataResults);
		return latestDataResults;
	}

	@Override
	public DeviceMessageAggregatedResponse getDeviceAndAggregatedData(
	        SearchDTO searchDTO) {

		generalResponses = new ArrayList<GeneralResponse>();
		UUID uid = null;
		validateSearchDTO(searchDTO);

		DeviceConfigTemplate configurations = null;
		try {
			Device device = deviceService.getDevice(searchDTO.getSourceId());
			uid = UUID.fromString(device.getDeviceId());
			configurations = deviceService.getConfigurations(searchDTO
			        .getSourceId());
		} catch (
		        IllegalArgumentException | DeviceCloudException
		        | NullPointerException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        SOURCE_ID.getDescription());
		}
		// remove duplicate elements from list of displayName tags
		Set<String> displayNameSet = new HashSet<String>();
		displayNameSet.addAll(searchDTO.getDisplayNames());
		searchDTO.getDisplayNames().clear();
		searchDTO.getDisplayNames().addAll(displayNameSet);

		List<ConfigPoint> fetchPoints = new ArrayList<ConfigPoint>();
		if (configurations == null
		        || CollectionUtils.isEmpty(configurations.getConfigPoints())) {
			throw new DeviceCloudException(CONFIGURATION.getDescription()
			        + " for the device is not available",
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED);
		}
		for (String displayName : searchDTO.getDisplayNames()) {
			for (ConfigPoint configPoint : configurations.getConfigPoints()) {
				if (displayName.equalsIgnoreCase(configPoint.getDisplayName())) {
					PhysicalQuantity physicalQuantity = phyQuantityService
					        .getPhyQuantityByName(configPoint
					                .getPhysicalQuantity());
					try {
						if (physicalQuantity != null) {
							configPoint.setPhysicalQuantity(physicalQuantity
							        .getDataStore());
							configPoint.setDataType(physicalQuantity
							        .getDataType().name());
							fetchPoints.add(configPoint);
						}
					} catch (Exception e) {
						LOGGER.error("Error resolving Physical Quantity : ", e);
					}
					break;
				}
			}
		}
		if (CollectionUtils.isEmpty(fetchPoints)) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        DISPLAY_NAMES.getDescription());
		}
		DateTime startDateValue = new DateTime(searchDTO.getStartDate())
		        .toDateTime(DateTimeZone.UTC);
		DateTime endDateValue = new DateTime(searchDTO.getEndDate())
		        .toDateTime(DateTimeZone.UTC);
		validateDateRange(startDateValue, endDateValue);

		List<TimeSeriesData> displayNamesList = new ArrayList<TimeSeriesData>();;
		DeviceMessageAggregatedResponse deviceMessageAggregatedResponse = null;
		try {
			for (ConfigPoint configPoint : fetchPoints) {

				TimeSeriesData displayNameData = null;
				if (configPoint.getDataType().name()
				        .equals(DataTypes.STRING.name())
				        || configPoint.getDataType().name()
				                .equals(DataTypes.BOOLEAN.name())) {
					String sourceId = searchDTO.getSourceId();
					String displayName = configPoint.getDisplayName();

					setGeneralResponse(
					        searchDTO.getSourceId(),
					        "DisplayName '"
					                + displayName
					                + "' for Source Id "
					                + sourceId
					                + ", is of "
					                + configPoint.getDataType()
					                + " DataType. Aggregated Data cannot be generated for this DataType.");
					LOGGER.error("DisplayName :"
					        + displayName
					        + " for Source Id "
					        + sourceId
					        + ", is of "
					        + configPoint.getDataType()
					        + " DataType. Aggregated Data cannot be generated for this DataType.");
					displayNameData = dataRepo.getDeviceAndAggregatedData(uid,
					        startDateValue, endDateValue, configPoint, null);
					if (displayNameData == null) {
						// data doesnot exist for this configPoint
						continue;
					}
				} else {
					displayNameData = dataRepo.getDeviceAndAggregatedData(uid,
					        startDateValue, endDateValue, configPoint,
					        setAllAggregationFunctions());

					if (displayNameData == null) {
						// data does not exists for this configPoint
						continue;
					}
					if (CollectionUtils.isNotEmpty(displayNameData.getValues())) {
						Map<String, String> aggregatedValues = displayNameData
						        .getAggregatedData();
						String range = getRange(aggregatedValues);
						if (StringUtils.isNotEmpty(range)) {
							aggregatedValues.put(RANGE.getFuncName(), range);
						}
					} else {
						// values doesnot present, data doesnot exist skip this
						// entry
						continue;
					}
				}
				displayNamesList.add(displayNameData);
			}

		} catch (InvalidQueryException e) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        PHYSICAL_QUANTIY_NAME.getDescription());
		}
		if (CollectionUtils.isNotEmpty(displayNamesList)) {
			deviceMessageAggregatedResponse = new DeviceMessageAggregatedResponse();
			deviceMessageAggregatedResponse.setSourceId(uid);
			deviceMessageAggregatedResponse.setDisplayNames(displayNamesList);
			if (CollectionUtils.isNotEmpty(generalResponses)) {
				deviceMessageAggregatedResponse
				        .setGeneralResponses(generalResponses);
			}
			return deviceMessageAggregatedResponse;
		} else {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
		}
	}

	private List<String> setAllAggregationFunctions() {
		List<String> aggregationFunctions = new ArrayList<String>();
		aggregationFunctions.add(MAX.getFuncName());
		aggregationFunctions.add(MIN.getFuncName());
		aggregationFunctions.add(AVG.getFuncName());
		aggregationFunctions.add(COUNT.getFuncName());
		aggregationFunctions.add(SUM.getFuncName());

		return aggregationFunctions;
	}

	private String getRange(Map<String, String> aggregatedValues) {
		String range = null;
		if (aggregatedValues.containsKey(MAX.getFuncName())
		        && aggregatedValues.containsKey(MIN.getFuncName())) {

			String maxValue = aggregatedValues.get(MAX.getFuncName());
			Double firstOperand = new Double(maxValue);

			String minValue = aggregatedValues.get(MIN.getFuncName());
			Double secondOperand = new Double(minValue);

			Double rangeResult = firstOperand - secondOperand;
			range = rangeResult.toString();
			range = range.replace("-", "");
		}
		return range;
	}

	private GeneralResponse setGeneralResponse(String reference, String remark) {
		GeneralResponse generalResponse = new GeneralResponse();
		generalResponse.setStatus(Status.FAILURE);
		generalResponse.setRemarks(remark);
		generalResponse.setReference(reference);
		generalResponses.add(generalResponse);
		LOGGER.error(remark);
		return generalResponse;
	}
}
