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
package com.pcs.galaxy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pcs.galaxy.repository.model.GeofenceAlarmConfig;

/**
 * GeofenceAlarmConfigRepository
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Repository
public class GeofenceAlarmConfigRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeofenceAlarmConfigRepository.class);

	private static final String ASSET_NAME = "assetName";
	private static final String SOURCE_ID = "sourceId";
	private static final String CONFIG_ID = "configId";

	private static final String FETCH_GEOCONF_FOR_ASSET = "SELECT geofenceConfig FROM GeofenceAlarmConfig geofenceConfig where geofenceConfig.assetName = :assetName";
	private static final String FETCH_GEOCONF_FOR_DEVICE = "SELECT geofenceConfig FROM GeofenceAlarmConfig geofenceConfig where geofenceConfig.sourceId = :sourceId";
	private static final String DELETE_GEOCONF_BY_CONFIG_ID = "DELETE FROM GeofenceAlarmConfig geofenceConfig where geofenceConfig.configId = :configId";
	private static final String DELETE_GEOCONF_BY_SOURCE_ID = "DELETE FROM GeofenceAlarmConfig geofenceConfig where geofenceConfig.sourceId = :sourceId";
	private static final String DELETE_GEOCONF_BY_ASSET_NAME = "DELETE FROM GeofenceAlarmConfig geofenceConfig where geofenceConfig.assetName = :assetName";

	@PersistenceContext
	private EntityManager em;

	public GeofenceAlarmConfig save(GeofenceAlarmConfig geofence) {
		try {
			em.persist(geofence);
		} catch (Exception e) {
			LOGGER.error("Exception in Save : " + e.getMessage());
		}
		return geofence;
	}

	public Boolean delete(Long configId) {
		GeofenceAlarmConfig model = null;
		try {
			Query deleteQuery = em.createQuery(DELETE_GEOCONF_BY_CONFIG_ID);
			deleteQuery.setParameter(CONFIG_ID, configId);
			int executeUpdate = deleteQuery.executeUpdate();
			if (executeUpdate > 0) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in Get : " + e.getMessage());
		}
		return false;
	}

	public Boolean deleteForDevice(String sourceId) {
		GeofenceAlarmConfig model = null;
		try {
			Query deleteQuery = em.createQuery(DELETE_GEOCONF_BY_SOURCE_ID);
			deleteQuery.setParameter(SOURCE_ID, sourceId);
			int executeUpdate = deleteQuery.executeUpdate();
			if (executeUpdate > 0) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in Get : " + e.getMessage());
		}
		return false;
	}

	public Boolean deleteForAsset(String assetName) {
		GeofenceAlarmConfig model = null;
		try {
			Query deleteQuery = em.createQuery(DELETE_GEOCONF_BY_ASSET_NAME);
			deleteQuery.setParameter(ASSET_NAME, assetName);
			int executeUpdate = deleteQuery.executeUpdate();
			if (executeUpdate > 0) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in Get : " + e.getMessage());
		}
		return false;
	}

	public GeofenceAlarmConfig getGeoconfig(Long configid) {
		GeofenceAlarmConfig model = null;
		try {
			model = em.find(GeofenceAlarmConfig.class, configid);
		} catch (Exception e) {
			LOGGER.error("Exception in Get : " + e.getMessage());
		}
		return model;
	}

	public List<GeofenceAlarmConfig> getForDevice(String sourceId) {
		List<GeofenceAlarmConfig> modelList = null;
		try {
			Query geoConfQuery = em.createQuery(FETCH_GEOCONF_FOR_DEVICE);
			geoConfQuery.setParameter(SOURCE_ID, sourceId);
			modelList = geoConfQuery.getResultList();
		} catch (Exception e) {
			LOGGER.error("Exception in Get : " + e.getMessage());
		}
		return modelList;
	}

	public List<GeofenceAlarmConfig> getForAsset(String assetName) {
		List<GeofenceAlarmConfig> modelList = null;
		try {
			Query geoConfQuery = em.createQuery(FETCH_GEOCONF_FOR_ASSET);
			geoConfQuery.setParameter(ASSET_NAME, assetName);
			modelList = geoConfQuery.getResultList();
		} catch (Exception e) {
			LOGGER.error("Exception in Get : " + e.getMessage());
		}
		return modelList;
	}
}
