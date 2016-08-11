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
package com.pcs.fms.web.controller;

import static com.pcs.fms.web.constants.FMSWebConstants.POINTS_MAPPING;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pcs.fms.web.dto.Equipment;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.services.BaseService;
import com.pcs.fms.web.services.PointMappingService;

/**
 * @author PCSEG191 Daniela
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Controller
public class PointsMappingController extends BaseService {

	private static final Logger logger = LoggerFactory
	        .getLogger(PointsMappingController.class);

	@Autowired
	PointMappingService pointMapping;

	@RequestMapping(value = POINTS_MAPPING,method = RequestMethod.POST)
	public ModelAndView viewPointsMapping(FieldMapDTO field) {
		ModelAndView mv = new ModelAndView(POINTS_MAPPING);
		mv.addObject("action", "View");

		// Get the equipment payload
		Gson gson = new Gson();

		Equipment equipment = gson.fromJson(field.getValue(), Equipment.class);
		String equipJson = gson.toJson(equipment);
		mv.addObject("equipName", equipment.getEquipName());
		mv.addObject("equipment", equipJson);
		// Fetch the owned devices
		return mv;
	}

}
