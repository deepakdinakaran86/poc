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

import static com.pcs.datasource.enums.DeviceDataFields.SUB_ID;
import static com.pcs.devicecloud.commons.validation.ValidationUtils.validateMandatoryField;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.datasource.dto.PointTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.PointTagRepository;

/**
 * This class is responsible for defining all services related to point custom
 * tag
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 08 Jul 2015
 */
@Service
public class PointTagServiceImpl implements PointTagService {

	@Autowired
	PointTagRepository pointTagRepository;

	@Override
	public List<PointTag> getAllTags(Subscription subscription) {
		validateMandatoryField(SUB_ID.getDescription(), subscription.getSubId());
		return pointTagRepository.getAllTags(subscription.getSubId());
	}

}
