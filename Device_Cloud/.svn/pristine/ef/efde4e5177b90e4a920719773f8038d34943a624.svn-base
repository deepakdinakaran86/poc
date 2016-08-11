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
package com.pcs.galaxy.serviceimpl;

import static com.pcs.avocado.commons.validation.ValidationUtils.validateMandatoryFields;
import static com.pcs.galaxy.enums.DeviceDataFields.ALARM_TYPE;
import static com.pcs.galaxy.enums.DeviceDataFields.ASSET_ID;
import static com.pcs.galaxy.enums.DeviceDataFields.CONFIG_ID;
import static com.pcs.galaxy.enums.DeviceDataFields.COORDINATES;
import static com.pcs.galaxy.enums.DeviceDataFields.GEOCOORDINATES;
import static com.pcs.galaxy.enums.DeviceDataFields.RADIUS;
import static com.pcs.galaxy.enums.DeviceDataFields.SOURCE_ID;
import static com.pcs.galaxy.enums.DeviceDataFields.TYPE;
import static com.pcs.galaxy.enums.GeoType.CIRCLE;
import static com.pcs.galaxy.enums.GeoType.POLYGON;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.avocado.commons.exception.GalaxyException;
import com.pcs.avocado.enums.Status;
import com.pcs.galaxy.dto.GeocoordinatesDTO;
import com.pcs.galaxy.dto.GeofenceAlarmConfigDTO;
import com.pcs.galaxy.enums.AlarmType;
import com.pcs.galaxy.enums.GeoType;
import com.pcs.galaxy.repository.GeofenceAlarmConfigRepository;
import com.pcs.galaxy.repository.model.CircleCoordinates;
import com.pcs.galaxy.repository.model.GeofenceAlarmConfig;
import com.pcs.galaxy.repository.model.PolygonCoordinates;
import com.pcs.galaxy.services.GeofenceAlarmConfigService;

/**
 * GeofenceAlarmConfigServiceImpl
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Service
public class GeofenceAlarmConfigServiceImpl
        implements
            GeofenceAlarmConfigService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeofenceAlarmConfigServiceImpl.class);

	@Autowired
	GeofenceAlarmConfigRepository geofenceAlarmConfig;

	@Transactional
	public GeofenceAlarmConfigDTO saveGeoFenceConf(
	        GeofenceAlarmConfigDTO geofence) {
		validateMandatoryFields(geofence, SOURCE_ID, ASSET_ID, ALARM_TYPE,
		        GEOCOORDINATES);
		validateGeoCordintes(geofence.getGeocoordinates());
		GeofenceAlarmConfig model = dtoToModel(geofence);
		if (!AlarmType.contains(geofence.getAlarmType())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        ALARM_TYPE.getDescription());
		}
		geofenceAlarmConfig.save(model);
		return geofence;
	}

	@Transactional
	public StatusMessageDTO updteGeoFenceConf(GeofenceAlarmConfigDTO geofence) {
		StatusMessageDTO status = new StatusMessageDTO(Status.SUCCESS);
		validateMandatoryFields(geofence, CONFIG_ID, ASSET_ID, SOURCE_ID,
		        ALARM_TYPE, GEOCOORDINATES);
		if (!AlarmType.contains(geofence.getAlarmType())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        ALARM_TYPE.getDescription());
		}
		validateGeoCordintes(geofence.getGeocoordinates());
		GeofenceAlarmConfig model = geofenceAlarmConfig.getGeoconfig(geofence
		        .getConfigId());
		if (model == null) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		model.setSourceId(geofence.getSourceId());
		model.setAlarmmessage(geofence.getAlarmmessage());
		model.setAlarmName(geofence.getAlarmName());
		model.setAlarmType(geofence.getAlarmType());
		model.setAssetId(geofence.getAssetId());
		model.setAssetName(geofence.getAssetName());
		model.setDeviceId(geofence.getDeviceId());
		model.setEnabled(geofence.isEnabled());
		model.setGeocoordinates(getGeoCordintesForModel(geofence
		        .getGeocoordinates()));
		return status;
	}

	@Transactional
	public StatusMessageDTO deleteGeoFenceConf(String configId) {
		StatusMessageDTO status = new StatusMessageDTO();
		Long configIdLong = null;
		try {
			configIdLong = Long.parseLong(configId);
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage());
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        CONFIG_ID.getDescription());
		}
		if (configIdLong != null) {
			if (geofenceAlarmConfig.delete(configIdLong)) {
				status.setStatus(Status.SUCCESS);
			} else {
				throw new GalaxyException(
				        GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
			}
		}
		return status;
	}

	@Transactional
	public StatusMessageDTO deleteGeoFenceOfDevice(String sourceId) {
		StatusMessageDTO status = new StatusMessageDTO();
		if (geofenceAlarmConfig.deleteForDevice(sourceId)) {
			status.setStatus(Status.SUCCESS);
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return status;
	}

	@Transactional
	public StatusMessageDTO deleteGeoFenceOfAsset(String assetName) {
		StatusMessageDTO status = new StatusMessageDTO();
		if (geofenceAlarmConfig.deleteForAsset(assetName)) {
			status.setStatus(Status.SUCCESS);
		} else {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		return status;
	}

	public List<GeofenceAlarmConfigDTO> getGeoFenceConfForDevice(String sourceId) {
		List<GeofenceAlarmConfig> modelList = geofenceAlarmConfig
		        .getForDevice(sourceId);
		List<GeofenceAlarmConfigDTO> dtos = new ArrayList<GeofenceAlarmConfigDTO>();
		if (CollectionUtils.isEmpty(modelList)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		for (GeofenceAlarmConfig geofence : modelList) {
			GeofenceAlarmConfigDTO dto = modelToDto(geofence);
			dtos.add(dto);
		}
		return dtos;
	}

	public List<GeofenceAlarmConfigDTO> getGeoFenceConfForAsset(String assetName) {
		List<GeofenceAlarmConfig> modelList = geofenceAlarmConfig
		        .getForAsset(assetName);
		List<GeofenceAlarmConfigDTO> dtos = new ArrayList<GeofenceAlarmConfigDTO>();
		if (CollectionUtils.isEmpty(modelList)) {
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		for (GeofenceAlarmConfig geofence : modelList) {
			GeofenceAlarmConfigDTO dto = modelToDto(geofence);
			dtos.add(dto);
		}
		return dtos;
	}

	private GeofenceAlarmConfig dtoToModel(GeofenceAlarmConfigDTO dto) {
		GeofenceAlarmConfig model = null;
		if (dto != null) {
			model = new GeofenceAlarmConfig();

			model.setAlarmmessage(dto.getAlarmmessage());
			model.setAlarmName(dto.getAlarmName());
			model.setAlarmType(dto.getAlarmType());
			model.setAssetId(dto.getAssetId());
			model.setAssetName(dto.getAssetName());
			model.setDeviceId(dto.getDeviceId());
			model.setEnabled(dto.isEnabled());
			model.setGeocoordinates(getGeoCordintesForModel(dto
			        .getGeocoordinates()));
			model.setSourceId(dto.getSourceId());

		}
		return model;
	}

	private GeofenceAlarmConfigDTO modelToDto(GeofenceAlarmConfig model) {
		GeofenceAlarmConfigDTO dto = null;
		if (model != null) {
			dto = new GeofenceAlarmConfigDTO();
			dto.setConfigId(model.getConfigId());
			dto.setAlarmmessage(model.getAlarmmessage());
			dto.setAlarmName(model.getAlarmName());
			dto.setAlarmType(model.getAlarmType());
			dto.setAssetId(model.getAssetId());
			dto.setAssetName(model.getAssetName());
			dto.setDeviceId(model.getDeviceId());
			dto.setEnabled(model.isEnabled());
			dto.setGeocoordinates(getGeoCordintesForDto(model
			        .getGeocoordinates()));
			dto.setSourceId(model.getSourceId());

		}
		return dto;
	}

	private GeocoordinatesDTO getGeoCordintesForDto(String geoCoordString) {
		Gson gson = new Gson();
		GeocoordinatesDTO geoCoordinates = null;
		if (!StringUtils.isEmpty(geoCoordString)) {
			CircleCoordinates circle = null;
			if (geoCoordString.contains(CIRCLE.getType())) {
				try {
					circle = gson.fromJson(geoCoordString,
					        CircleCoordinates.class);
				} catch (JsonSyntaxException e) {
					LOGGER.error("Error while converting circle string to dto : "
					        + e.getMessage());
				}
				if (circle != null) {
					geoCoordinates = new GeocoordinatesDTO();
					geoCoordinates.setType(circle.getType());
					geoCoordinates.setRadius(circle.getRadius());
					List<List<Double>> coordinates = new ArrayList<List<Double>>();
					coordinates.add(circle.getCoordinates());
					geoCoordinates.setCoordinates(coordinates);
				}

			} else if (geoCoordString.contains(POLYGON.getType())) {
				PolygonCoordinates polygon = null;
				try {
					polygon = gson.fromJson(geoCoordString,
					        PolygonCoordinates.class);
				} catch (JsonSyntaxException e) {
					LOGGER.error("Error while converting polygon string to dto : "
					        + e.getMessage());
				}
				if (polygon != null) {
					geoCoordinates = new GeocoordinatesDTO();
					geoCoordinates.setType(polygon.getType());

					List<List<List<Double>>> coordinatesDb = polygon
					        .getCoordinates();
					if (!CollectionUtils.isEmpty(coordinatesDb)) {
						if (coordinatesDb.size() > 0) {
							if (!CollectionUtils.isEmpty(coordinatesDb.get(0))) {
								coordinatesDb.get(0).remove(
								        coordinatesDb.get(0).size() - 1);
							}
							geoCoordinates.setCoordinates(coordinatesDb.get(0));
						}
					}

				}
			}
		}
		return geoCoordinates;
	}

	private void validateGeoCordintes(GeocoordinatesDTO geocoordinates) {
		validateMandatoryFields(geocoordinates, TYPE, COORDINATES);
		if (!GeoType.contains(geocoordinates.getType())) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        TYPE.getDescription());
		}
		if (CIRCLE.getType().equals(geocoordinates.getType())) {
			validateMandatoryFields(geocoordinates, RADIUS);
		}
	}

	private String getGeoCordintesForModel(GeocoordinatesDTO geocoordinates) {
		PolygonCoordinates polygon = null;
		CircleCoordinates circle = null;
		String response = new String();

		Boolean flag = new Boolean(Boolean.FALSE);
		if (CIRCLE.getType().equals(geocoordinates.getType())) {
			circle = new CircleCoordinates();
			circle.setType(geocoordinates.getType());
			circle.setRadius(geocoordinates.getRadius());

			List<List<Double>> coordinates = geocoordinates.getCoordinates();
			if (!CollectionUtils.isEmpty(coordinates)) {
				List<Double> latLong = coordinates.get(0);
				if (!CollectionUtils.isEmpty(latLong)) {
					if (latLong.size() >= 2) {
						Double lat = latLong.get(0);
						Double lon = latLong.get(1);
						if (lat != null && !StringUtils.isEmpty(lat)
						        && lat != null && !StringUtils.isEmpty(lat)) {
							flag = Boolean.TRUE;
							ArrayList<Double> latLonCoord = new ArrayList<Double>();
							latLonCoord.add(lat);
							latLonCoord.add(lon);
							circle.setCoordinates(latLonCoord);
						}
					}
				}
			}
			Gson gson = new Gson();
			response = gson.toJson(circle);
		} else if (POLYGON.getType().equals(geocoordinates.getType())) {
			polygon = new PolygonCoordinates();
			polygon.setType(geocoordinates.getType());

			List<List<Double>> coordinates = geocoordinates.getCoordinates();
			ArrayList<List<Double>> coordinatesDb = null;
			if (!CollectionUtils.isEmpty(coordinates)) {
				if (coordinates.size() >= 3) {
					coordinatesDb = new ArrayList<List<Double>>();
					for (List<Double> latLong : coordinates) {
						ArrayList<Double> latLonCoord = null;
						if (!CollectionUtils.isEmpty(latLong)) {
							if (latLong.size() >= 2) {
								Double lat = latLong.get(0);
								Double lon = latLong.get(1);
								if (lat != null && !StringUtils.isEmpty(lat)
								        && lat != null
								        && !StringUtils.isEmpty(lat)) {
									latLonCoord = new ArrayList<Double>();
									latLonCoord.add(lat);
									latLonCoord.add(lon);
									coordinatesDb.add(latLonCoord);
								}
							}
						}
					}
				}
			}
			if (!CollectionUtils.isEmpty(coordinatesDb)) {
				if (coordinatesDb.size() >= 3) {
					flag = Boolean.TRUE;
					coordinatesDb.add(coordinatesDb.get(0));
					List<List<List<Double>>> coordinateList = new ArrayList<List<List<Double>>>();
					coordinateList.add(coordinatesDb);
					polygon.setCoordinates(coordinateList);
				}
			}

			Gson gson = new Gson();
			response = gson.toJson(polygon);
		}
		if (!flag) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        COORDINATES.getDescription());
		}
		return response;
	}

}
