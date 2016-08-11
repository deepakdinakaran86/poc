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

import static com.pcs.datasource.enums.AggregationFields.AGGREGATION_FUNCTIONS;
import static com.pcs.datasource.enums.AggregationFields.DEVICE_POINTS_MAP;
import static com.pcs.datasource.enums.AggregationFunctionConstants.ALL;
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
import static com.pcs.datasource.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.datasource.enums.DeviceDataFields.START_DATE;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.pcs.datasource.dto.Aggregation;
import com.pcs.datasource.dto.AggregationResponse;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DevicePointsMap;
import com.pcs.datasource.dto.DisplayNameAggregatedData;
import com.pcs.datasource.dto.GeneralResponse;
import com.pcs.datasource.dto.PhysicalQuantity;
import com.pcs.datasource.dto.PointAggregationMap;
import com.pcs.datasource.enums.AggregationFunctionConstants;
import com.pcs.datasource.enums.DataTypes;
import com.pcs.datasource.repository.DataRepo;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.enums.Status;

/**
 * This service implementation class is responsible for defining all the
 * services related to analytics on data of a Device communicating to the
 * system.
 * 
 * @author pcseg297 (Twinkle)
 * @date April 2016
 * @since galaxy-1.0.0
 */
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	@Autowired
	private DataRepo dataRepo;

	@Autowired
	private PhyQuantityService phyQuantityService;

	@Autowired
	DeviceService deviceService;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AnalyticsServiceImpl.class);

	List<GeneralResponse> generalResponses = null;

	@Override
	public AggregationResponse getAggregatedData(Aggregation aggregation) {
		generalResponses = new ArrayList<GeneralResponse>();
		validateAggregationPayload(aggregation);

		AggregationResponse aggregationResponse = new AggregationResponse();
		aggregationResponse.setData(getData(aggregation));

		if (CollectionUtils.isNotEmpty(generalResponses)) {
			aggregationResponse.setGeneralResponses(generalResponses);
		}
		return aggregationResponse;
	}

	private List<DisplayNameAggregatedData> getData(Aggregation aggregation) {

		List<DisplayNameAggregatedData> data = new ArrayList<DisplayNameAggregatedData>();
		for (DevicePointsMap devicePointsMap : aggregation.getDevicePointsMap()) {
			DisplayNameAggregatedData displayNameAggregatedData = new DisplayNameAggregatedData();

			UUID uuid = getDeviceUUId(devicePointsMap.getSourceId());
			if (uuid == null)
				continue;

			List<ConfigPoint> fetchPoints = fetchPointsConfigurations(
			        devicePointsMap.getSourceId(),
			        devicePointsMap.getDisplayNames());
			DateTime startDateValue = new DateTime(aggregation.getStartDate())
			        .toDateTime(DateTimeZone.UTC);
			DateTime endDateValue = new DateTime(aggregation.getEndDate())
			        .toDateTime(DateTimeZone.UTC);
			List<String> aggregationFunctions = aggregation
			        .getAggregationFunctions();

			List<PointAggregationMap> rows = new ArrayList<PointAggregationMap>();
			for (ConfigPoint configPoint : fetchPoints) {
				Map<String, String> aggregatedValues = null;
				try {
					aggregatedValues = dataRepo.getAggregatedData(uuid,
					        startDateValue, endDateValue, configPoint,
					        aggregationFunctions);
				} catch (InvalidQueryException e) {
					setGeneralResponse(configPoint.getDisplayName(),
					        "DisplayName " + configPoint.getDisplayName()
					                + "configuration is invalid");
					// do not throw exception, else continue
					continue;
					// throw new DeviceCloudException(
					// DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
					// PHYSICAL_QUANTIY_NAME.getDescription());
				}
				if (aggregatedValues == null) {
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.CUSTOM_ERROR,
					        DISPLAY_NAMES.getDescription()
					                + "configuration of Source Id "
					                + devicePointsMap.getSourceId()
					                + " is invalid");
				}

				String countValue = aggregatedValues.get(COUNT.getFuncName());
				if (countValue.equals("0")) {
					throw new DeviceCloudException(
					        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE);
				}

				// if contains MAX and MIN both, findout RANGE
				String range = getRange(aggregatedValues);
				if (StringUtils.isNotEmpty(range)) {
					aggregatedValues.put(RANGE.getFuncName(), range);
				}

				Map<String, Map<String, String>> pointMap = new HashMap<String, Map<String, String>>();
				pointMap.put(configPoint.getDisplayName(), aggregatedValues);

				// Map<String, Map<String, Map<String, String>>> map = new
				// HashMap<String, Map<String, Map<String, String>>>();
				// map.put(devicePointsMap.getSourceId(), pointMap);

				PointAggregationMap pointAggregationMap = new PointAggregationMap();
				pointAggregationMap.setAggregatedData(aggregatedValues);
				pointAggregationMap
				        .setDisplayName(configPoint.getDisplayName());
				rows.add(pointAggregationMap);
				displayNameAggregatedData.setRows(rows);
				displayNameAggregatedData.setSourceId(devicePointsMap
				        .getSourceId());
			}
			data.add(displayNameAggregatedData);
		}
		return data;
	}

	private List<ConfigPoint> fetchPointsConfigurations(String sourceId,
	        List<String> displayNames) {
		DeviceConfigTemplate configurations = null;
		try {
			configurations = deviceService.getConfigurations(sourceId);
		} catch (DeviceCloudException dce) {
			if (dce.getMessage().equals(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				setGeneralResponse(sourceId, "Device for Source Id " + sourceId
				        + ", doesnot exist");
				LOGGER.error("Device for Source Id " + sourceId
				        + ", doesnot exist", dce);
			}
		}

		if (configurations == null
		        || CollectionUtils.isEmpty(configurations.getConfigPoints())) {
			setGeneralResponse(sourceId, CONFIGURATION.getDescription()
			        + " for Source Id " + sourceId + ", doesnot exist");
		}

		// remove duplicate displayName
		Set<String> setDisplayNames = new HashSet<String>();
		setDisplayNames.addAll(displayNames);
		displayNames.clear();
		displayNames.addAll(setDisplayNames);

		List<ConfigPoint> fetchPoints = new ArrayList<ConfigPoint>();
		for (String displayName : displayNames) {

			Boolean configContainsDisplayNameFlag = false;
			Boolean dataTypeNotSupportFlag = false;
			for (ConfigPoint configPoint : configurations.getConfigPoints()) {
				if (displayName.equalsIgnoreCase(configPoint.getDisplayName())) {

					if (configPoint.getDataType().name()
					        .equals(DataTypes.STRING.name())
					        || configPoint.getDataType().name()
					                .equals(DataTypes.BOOLEAN.name())) {
						setGeneralResponse(
						        sourceId,
						        "DisplayName "
						                + displayName
						                + " for Source Id "
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
						dataTypeNotSupportFlag = true;
						break;
					}
					PhysicalQuantity phyQuantityByName = phyQuantityService
					        .getPhyQuantityByName(configPoint
					                .getPhysicalQuantity());

					try {
						if (phyQuantityByName != null) {
							String dataStore = phyQuantityByName.getDataStore();
							configPoint.setPhysicalQuantity(dataStore);
							configPoint.setDataType(phyQuantityByName
							        .getDataType().name());
							fetchPoints.add(configPoint);
							configContainsDisplayNameFlag = true;
						}
					} catch (Exception e) {

						LOGGER.error(
						        "Physical quantity could not be resolved {} : {}",
						        sourceId, e);

					}

					break;
				}/*
				 * else{ setGeneralResponse(sourceId,
				 * CONFIGURATION.getDescription() + " for DisplayName : " +
				 * displayName + " of Source Id " + sourceId +
				 * ", doesnot exist"); }
				 */
			}
			if (!configContainsDisplayNameFlag) {
				if (!dataTypeNotSupportFlag) {
					setGeneralResponse(sourceId, CONFIGURATION.getDescription()
					        + " for DisplayName : " + displayName
					        + " of Source Id " + sourceId + ", doesnot exist");
				}
			}
		}
		if (CollectionUtils.isEmpty(fetchPoints)) {
			setGeneralResponse(sourceId, DISPLAY_NAMES.getDescription()
			        + " for Source Id " + sourceId + ", are invalid" + sourceId);
		}
		return fetchPoints;
	}

	private UUID getDeviceUUId(String sourceId) {
		UUID uuid = null;
		try {
			Device device = deviceService.getDevice(sourceId);
			if (device == null) {
				setGeneralResponse(sourceId, "Source ID : " + sourceId
				        + " is invalid");
				return uuid;
			}
			uuid = UUID.fromString(device.getDeviceId());
		} catch (DeviceCloudException dce) {
			if (dce.getMessage().equals(
			        DeviceCloudErrorCodes.DATA_NOT_AVAILABLE.getMessage())) {
				setGeneralResponse(sourceId, "Source ID : " + sourceId
				        + " is invalid");
			}
		}
		return uuid;
	}

	/**
	 * validate aggregation input
	 * 
	 * @param physicalQuantityName
	 * @param searchDTO
	 */
	private void validateAggregationPayload(Aggregation aggregation) {
		validateMandatoryFields(aggregation, START_DATE, END_DATE,
		        DEVICE_POINTS_MAP, AGGREGATION_FUNCTIONS);

		if (CollectionUtils.isEmpty(aggregation.getDevicePointsMap())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        DEVICE_POINTS_MAP.getDescription());
		}
		if (CollectionUtils.isEmpty(aggregation.getAggregationFunctions())) {
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
			        AGGREGATION_FUNCTIONS.getDescription());
		}

		// validate startDate and EndDate
		DateTime startDateValue = new DateTime(aggregation.getStartDate())
		        .toDateTime(DateTimeZone.UTC);
		DateTime endDateValue = new DateTime(aggregation.getEndDate())
		        .toDateTime(DateTimeZone.UTC);
		validateDateRange(startDateValue, endDateValue);

		validateAggregationFunctions(aggregation);

		List<String> aggregationFunctions = null;
		if (CollectionUtils.isEmpty(aggregation.getAggregationFunctions())) {
			// if no aggregationFunstion specified, return all the aggregation
			// Function data TODO is it correct
			aggregationFunctions = setAllAggregationFunctions();
			aggregation.setAggregationFunctions(aggregationFunctions);
		} else if (aggregation.getAggregationFunctions().size() == 1) {
			String aggregationFunc = aggregation.getAggregationFunctions()
			        .get(0).toUpperCase();
			if (AggregationFunctionConstants.hasEnum(aggregationFunc)
			        && aggregationFunc.equalsIgnoreCase(ALL.getFuncName())) {
				aggregationFunctions = setAllAggregationFunctions();
				aggregation.setAggregationFunctions(aggregationFunctions);
			}
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

	private List<String> setAllAggregationFunctions() {
		List<String> aggregationFunctions = new ArrayList<String>();
		aggregationFunctions.add(MAX.getFuncName());
		aggregationFunctions.add(MIN.getFuncName());
		aggregationFunctions.add(AVG.getFuncName());
		aggregationFunctions.add(COUNT.getFuncName());
		aggregationFunctions.add(SUM.getFuncName());

		return aggregationFunctions;
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

	private void validateAggregationFunctions(Aggregation aggregation) {
		List<String> validAggregationFuncs = new ArrayList<String>();

		List<GeneralResponse> genResponses = new ArrayList<GeneralResponse>();
		GeneralResponse generalResponse = new GeneralResponse();

		for (DevicePointsMap devicePointsMap : aggregation.getDevicePointsMap()) {
			if (StringUtils.isEmpty(devicePointsMap.getSourceId())) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
				        SOURCE_ID.getDescription());
			} else if (devicePointsMap.getDisplayNames() == null
			        && CollectionUtils.isEmpty(devicePointsMap
			                .getDisplayNames())) {
				throw new DeviceCloudException(
				        DeviceCloudErrorCodes.FIELD_DATA_NOT_SPECIFIED,
				        DISPLAY_NAMES.getDescription());
			}
		}

		for (String aggregationFunc : aggregation.getAggregationFunctions()) {
			if (AggregationFunctionConstants.hasEnum(aggregationFunc)) {
				if (aggregationFunc.equalsIgnoreCase(ALL.getFuncName())) {
					validAggregationFuncs.clear();
					validAggregationFuncs = setAllAggregationFunctions();
					aggregation.setAggregationFunctions(validAggregationFuncs);
					break;
				}
				validAggregationFuncs.add(aggregationFunc);
			} else {
				generalResponse.setReference(AGGREGATION_FUNCTIONS
				        .getDescription());
				generalResponse.setRemarks(aggregationFunc + " is invalid");
				generalResponse.setStatus(Status.FAILURE);
				genResponses.add(generalResponse);
			}
		}

		if (CollectionUtils.isEmpty(validAggregationFuncs)) {
			// setGeneralResponse(AGGREGATION_FUNCTIONS.getDescription(),
			// AGGREGATION_FUNCTIONS.getDescription() + " are invalid");
			// no valid aggregation function specified
			throw new DeviceCloudException(
			        DeviceCloudErrorCodes.INVALID_DATA_SPECIFIED,
			        AGGREGATION_FUNCTIONS.getDescription());
		} else {
			// add only invalid aggregation functions
			generalResponses.addAll(genResponses);
		}
		aggregation.setAggregationFunctions(validAggregationFuncs);
	}
}
