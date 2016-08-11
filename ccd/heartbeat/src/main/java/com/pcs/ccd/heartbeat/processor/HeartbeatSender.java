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
package com.pcs.ccd.heartbeat.processor;

import static com.pcs.ccd.heartbeat.constants.Constants.DATASOURCE_NAME;
import static com.pcs.ccd.heartbeat.constants.Constants.EQUIP_NAME;
import static com.pcs.ccd.heartbeat.constants.Constants.HEART_BEAT_MSG_TYPE;
import static com.pcs.ccd.heartbeat.constants.Constants.NOTIFICATION_VERSION;
import static com.pcs.ccd.heartbeat.token.TokenProvider.getAuthToken;
import static com.pcs.ccd.heartbeat.utils.HeartbeatUtil.getServicesConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.ccd.heartbeat.beans.Data;
import com.pcs.ccd.heartbeat.beans.DomainDTO;
import com.pcs.ccd.heartbeat.beans.EntityTemplateDTO;
import com.pcs.ccd.heartbeat.beans.Equipment;
import com.pcs.ccd.heartbeat.beans.FieldMapDTO;
import com.pcs.ccd.heartbeat.beans.Heartbeat;
import com.pcs.ccd.heartbeat.beans.Parameter;
import com.pcs.ccd.heartbeat.beans.ReturnFieldsDTO;
import com.pcs.ccd.heartbeat.beans.SearchFieldsDTO;
import com.pcs.ccd.heartbeat.beans.Snapshot;
import com.pcs.ccd.heartbeat.beans.Tenant;
import com.pcs.ccd.heartbeat.enums.Integraters;
import com.pcs.ccd.heartbeat.utils.HeartbeatUtil;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Mar 31, 2016
 */
public class HeartbeatSender {
	private static final String GALAXY = ".galaxy";
	private static final Logger logger = LoggerFactory
	        .getLogger(HeartbeatSender.class);

	public static void start() {
		List<Tenant> tenants = getAllTenants();
		if (!tenants.isEmpty()) {
			for (Tenant tenant : tenants) {
				List<Equipment> equipments = getAllAssets(tenant
				        .getTenantName());
				if(equipments!=null){
					for (Equipment equipment : equipments) {
						String sourceId = equipment.getDatasourceName();
						if(StringUtils.isEmpty(sourceId)){
							sourceId = getPointDatasource(equipment,
									equipment.getAssetName(), tenant.getTenantName()
									+ GALAXY);
						}
						if (StringUtils.isNotEmpty(sourceId)) {
							equipment.setDatasourceName(sourceId);
							Heartbeat heartbeat = getLatestData(equipment,sourceId);
							if (heartbeat != null) {
								ObjectMapper mapper = new ObjectMapper();
								try {
									logger.info("******************* Heartbeat msg of {} is {} "
											, equipment.getAssetName(),mapper.writeValueAsString(heartbeat));
								} catch (JsonProcessingException e) {
									e.printStackTrace();
								}
							}else {
								logger.info("heart beat msg is null for asset {}",
										equipment.getAssetName());
							}
						} else {
							logger.info("datasource name is empty for asset {}",
									equipment.getAssetName());
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static List<Tenant> getAllTenants() {
		logger.info("Get all tenants");
		Client httpClient = HeartbeatUtil.buildRestClient();
		List<Tenant> equipTenants = null;
		try {
			equipTenants = httpClient.get(getServicesConfig()
			        .getGetAllCcdTenantsApi(), getAuthToken(Integraters.CCD),
			        List.class, Tenant.class);
			return equipTenants;
		} catch (ClientException e) {
			logger.error("Error in getting all tenants");
		}
		return null;
	}

	private static List<Equipment> getAllAssets(String tenantName) {
		logger.info("Get all assets of {}", tenantName);
		Client httpClient = HeartbeatUtil.buildRestClient();
		List<Equipment> equipEntities = null;
		try {
			equipEntities = httpClient.post(getServicesConfig()
			        .getGetCcdAssetsApi(), getAuthToken(Integraters.CCD),
			        payloadForAllAssets(tenantName), List.class,
			        Equipment.class);
			return equipEntities;
		} catch (ClientException e) {
			logger.error("Error in getting all assets of tenant {} ",
			        tenantName);
		}
		return null;
	}

	private static Tenant payloadForAllAssets(String tenantName) {
		Tenant tenant = new Tenant();
		tenant.setTenantName(tenantName);
		return tenant;
	}

	@SuppressWarnings("unchecked")
	public static String getPointDatasource(Equipment equipment,String assetName, String tenantDomain) {
		logger.info("Get points for asset {} for {}", assetName, tenantDomain);
		Client httpClient = HeartbeatUtil.buildRestClient();
		List<ReturnFieldsDTO> pointEntities = null;
		try {
			pointEntities = httpClient.post(getServicesConfig()
			        .getGetAssetsApi(), getAuthToken(Integraters.GALAXY),
			        payloadForPoints(assetName, tenantDomain), List.class,
			        ReturnFieldsDTO.class);
			if(pointEntities!=null && !pointEntities.isEmpty()){
				return getDatasourceName(pointEntities.get(0));
			}
		} catch (ClientException e) {
			logger.error("Error in get points for asset {} ", assetName);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static Heartbeat getLatestData(Equipment equipment,String sourceId) {
		logger.info("Get latest data for asset source id {} ", sourceId);
		Client httpClient = HeartbeatUtil.buildRestClient();
		List<Data> latestData = null;
		try {
			latestData = httpClient.get(getServicesConfig()
			        .getGetLatestDataApi().replace("{source_id}", sourceId),
			        getAuthToken(Integraters.GALAXY), List.class, Data.class);
			return convertToHeartbeatMsg(equipment,sourceId, latestData);
		} catch (ClientException e) {
			logger.error("Error in getting latestdata for source id {} ",
			        sourceId);
		}
		return null;
	}

	private static Heartbeat convertToHeartbeatMsg(Equipment equipment,String sourceId,
	        List<Data> latestData) {
		Heartbeat heartbeat = new Heartbeat();
		Snapshot snapshot = new Snapshot();
		List<Snapshot> snapshots = new ArrayList<Snapshot>();
		List<Parameter> parameters = new ArrayList<Parameter>();
		for (Data data : latestData) {
			Parameter p = new Parameter();
			p.setName(data.getCustomTag());
			p.setValue(data.getData());
			parameters.add(p);
		}
		snapshot.setParameters(parameters);
		snapshots.add(snapshot);
		heartbeat.setSnapshots(snapshots);

		heartbeat.setNotificationVersion(NOTIFICATION_VERSION);
		heartbeat.setMessageType(HEART_BEAT_MSG_TYPE);
		heartbeat.setDeviceId(sourceId);
		heartbeat.setSentDateTime(setSentDateTime());
		heartbeat.setEquipmentId(equipment.getAssetId());
		heartbeat.setSerialNumber(equipment.getSerialNumber());
		heartbeat.setVidNumber(equipment.getAssetName());
		return heartbeat;
	}

	private static String setSentDateTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date d = Calendar.getInstance().getTime();
		return df.format(d);
	}

	private static String getDatasourceName(ReturnFieldsDTO returnFieldsDTO) {
		logger.info("Get datasourcename");
		return fetchFieldValue(returnFieldsDTO.getDataprovider(),
		        DATASOURCE_NAME);
	}

	private static SearchFieldsDTO payloadForPoints(String assetName,
	        String tenantDomain) {
		SearchFieldsDTO search = new SearchFieldsDTO();

		List<FieldMapDTO> searchFields = new ArrayList<FieldMapDTO>();
		FieldMapDTO searchField = new FieldMapDTO();
		searchField.setKey(EQUIP_NAME);
		searchField.setValue(assetName);
		searchFields.add(searchField);
		search.setSearchFields(searchFields);

		EntityTemplateDTO entity = new EntityTemplateDTO();
		entity.setEntityTemplateName(HeartbeatUtil.getServicesConfig()
		        .getPointTemplate());
		search.setEntityTemplate(entity);

		DomainDTO domain = new DomainDTO();
		domain.setDomainName(tenantDomain);
		search.setDomain(domain);

		return search;
	}

	private static String fetchFieldValue(List<FieldMapDTO> keyValueObjects, String key) {
		FieldMapDTO keyValueObject = new FieldMapDTO();
		keyValueObject.setKey(key);
		FieldMapDTO field = new FieldMapDTO();
		field.setKey(keyValueObject.getKey());
		int fieldIndex = keyValueObjects.indexOf(field);
		String value = fieldIndex != -1 ? keyValueObjects.get(fieldIndex)
		        .getValue() : null;
		return value;
	}

	public static void main(String[] args) {
		HeartbeatSender hb = new HeartbeatSender();
		ObjectMapper mapper = new ObjectMapper();
		/*
		 * String dsName = hb.getPointDatasource("InternalGensetDemo1",
		 * "gbots.galaxy"); System.out.println("Datasource name : " + dsName);
		 * Heartbeat heartbeat = hb.getLatestData(dsName); try {
		 * System.out.println("Heartbeat msg" +
		 * mapper.writeValueAsString(heartbeat)); } catch
		 * (JsonProcessingException e) { e.printStackTrace(); }
		 */

		/*List<Equipment> allAssets = hb.getAllAssets("gbots");
		try {
			System.out.println("all assets "
			        + mapper.writeValueAsString(allAssets));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}*/
		
		HeartbeatSender.start();
	}
}
