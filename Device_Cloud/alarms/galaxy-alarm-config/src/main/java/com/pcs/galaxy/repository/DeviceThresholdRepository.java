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

import com.pcs.galaxy.repository.model.ConfigurationId;
import com.pcs.galaxy.repository.model.DeviceThreshold;

/**
 * DeviceThresholdRepository
 * 
 * @author PCSEG296 (RIYAS PH)
 * @date APRIL 2016
 * @since GALAXY-1.0.0
 */
@Repository
public class DeviceThresholdRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(DeviceThresholdRepository.class);

	private static final String FETCH_ALL_THRESHOLD_FOR_DEVICE = "SELECT deviceThreshold FROM DeviceThreshold deviceThreshold where deviceThreshold.assetName = :assetName";

	@PersistenceContext
	private EntityManager em;

	public DeviceThreshold save(DeviceThreshold customer) {
		try {
			em.persist(customer);
		} catch (Exception e) {
			LOGGER.error("Exception in Save : " + e.getMessage());
		}
		return customer;
	}

	public DeviceThreshold update(DeviceThreshold customer) {

		DeviceThreshold merge = null;
		try {
			merge = em.merge(customer);
		} catch (Exception e) {
			LOGGER.error("Exception in Update : " + e.getMessage());
		}
		return merge;

	}

	public DeviceThreshold delete(DeviceThreshold customer) {
		DeviceThreshold threshold = getThreshold(customer.getId());
		try {
			em.remove(threshold);
		} catch (Exception e) {
			LOGGER.error("Exception in Delete : " + e.getMessage());
		}
		return customer;
	}

	public List<DeviceThreshold> getForDevice(String assetName) {
		List<DeviceThreshold> thresholds = null;
		try {

			Query deviceThreshold = em
			        .createQuery(FETCH_ALL_THRESHOLD_FOR_DEVICE);
			deviceThreshold.setParameter("assetName", assetName);
			thresholds = deviceThreshold.getResultList();
		} catch (Exception e) {
			LOGGER.error("Exception in Get all for a device : "
			        + e.getMessage());
		}
		return thresholds;
	}

	public DeviceThreshold getThreshold(ConfigurationId id) {
		DeviceThreshold threshold = null;
		try {
			threshold = em.find(DeviceThreshold.class, id);
		} catch (Exception e) {
			LOGGER.error("Exception in Get : " + e.getMessage());
		}
		return threshold;
	}
}
