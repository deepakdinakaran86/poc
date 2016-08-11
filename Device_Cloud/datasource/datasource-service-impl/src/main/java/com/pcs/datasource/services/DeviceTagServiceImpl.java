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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceTagRepository;
import com.pcs.devicecloud.commons.validation.ValidationUtils;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Service
public class DeviceTagServiceImpl implements DeviceTagService {

	@Autowired
	@Qualifier("deviceTagNeo")
	private DeviceTagRepository deviceTagRepository;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceTagService#getAllTagsOfSubscription
	 * (java.lang.Integer)
	 */
	@Override
	public List<DeviceTag> getAllTagsOfSubscription(Subscription subscription) {
		List<DeviceTag> tags = deviceTagRepository
		        .getAllTagsOfSubscription(subscription);
		ValidationUtils.validateResult(tags);
		return tags;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.services.DeviceTagService#updateDeviceRelation(com
	 * .pcs.datasource.dto.dc.Device)
	 */
	@Override
	public void updateDeviceRelation(Device device) {
		deviceTagRepository.updateDeviceRelation(device);
	}
}
