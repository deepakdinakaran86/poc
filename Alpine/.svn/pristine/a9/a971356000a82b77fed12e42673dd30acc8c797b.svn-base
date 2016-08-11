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
package com.pcs.alpine.serviceimpl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.HttpMethod;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.dto.ApplicationAuditDTO;
import com.pcs.alpine.dto.AuditDTO;
import com.pcs.alpine.service.AuditLogService;
import com.pcs.alpine.services.AuditService;
import com.pcs.alpine.constants.NameConstants;

/**
 * 
 * This class is responsible for Audit Log Implementation
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date July 2016
 * @since alpine-1.0.0
 */

@Service
public class AuditLogServiceImpl implements AuditLogService {

	@Autowired
	private AuditService auditService;

	private static final String DATEFORMAT = "MM/dd/yyyy HH:mm:ss";
	private static final String TIMEZONE = "UTC";

	@Override
	public List<AuditDTO> getAuditLog() {
		List<com.pcs.alpine.services.dto.AuditDTO> auditDTOs = null;
		try {
			auditDTOs = auditService.getAllAudits();
		} catch (GalaxyException e) {
			if (GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode().equals(
					e.getCode())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
						CommonConstants.USER);
			} else {
				throw e;
			}
		}
		return setAuditLogDTO(auditDTOs);
	}

	private List<AuditDTO> setAuditLogDTO(
			List<com.pcs.alpine.services.dto.AuditDTO> auditDTOs) {
		List<AuditDTO> auditLogDTOs = new ArrayList<AuditDTO>();

		for (com.pcs.alpine.services.dto.AuditDTO auditDTO : auditDTOs) {
			auditLogDTOs.add(getAuditDTO(auditDTO));
		}

		return auditLogDTOs;
	}

	@Override
	public List<ApplicationAuditDTO> getAuditLogForApp() {
		List<com.pcs.alpine.services.dto.AuditDTO> auditDTOs = null;
		try {
			auditDTOs = auditService.getAllAudits();
		} catch (GalaxyException e) {
			if (GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE.getCode().equals(
					e.getCode())) {
				throw new GalaxyException(
						GalaxyCommonErrorCodes.SPECIFIC_DATA_NOT_AVAILABLE,
						CommonConstants.USER);
			} else {
				throw e;
			}
		}
		return setAuditLogDTOForApp(auditDTOs);
	}

	private List<ApplicationAuditDTO> setAuditLogDTOForApp(
			List<com.pcs.alpine.services.dto.AuditDTO> auditDTOs) {
		List<ApplicationAuditDTO> auditLogDTOs = new ArrayList<ApplicationAuditDTO>();

		for (com.pcs.alpine.services.dto.AuditDTO auditDTO : auditDTOs) {
			if(processSummary(auditDTO) != null){
				auditLogDTOs.add(getAuditDTOForApp(auditDTO));
			}
		}

		return auditLogDTOs;
	}

	private AuditDTO getAuditDTO(com.pcs.alpine.services.dto.AuditDTO auditDTO) {
		AuditDTO auditLogDTO = new AuditDTO();
		auditLogDTO.setUserName(auditDTO.getUserName());
		auditLogDTO.setStartTime(auditDTO.getStartTime());
		auditLogDTO.setResourceUrl(auditDTO.getResourceUrl());
		auditLogDTO.setAction(auditDTO.getAction());
		auditLogDTO.setActorIdentity(auditDTO.getActorIdentity());
		auditLogDTO.setEndTime(auditDTO.getEndTime());
		auditLogDTO.setIpAddress(auditDTO.getIpAddress());
		auditLogDTO.setRemarks(auditDTO.getRemarks());
		auditLogDTO.setTarget(auditDTO.getTarget());
		auditLogDTO.setTargetIdentity(auditDTO.getTargetIdentity());
		auditLogDTO.setTotalTime(auditDTO.getTotalTime());

		return auditLogDTO;
	}

	private ApplicationAuditDTO getAuditDTOForApp(
			com.pcs.alpine.services.dto.AuditDTO auditDTO) {
		ApplicationAuditDTO applicationAuditDTO = new ApplicationAuditDTO();
		applicationAuditDTO.setUserName(auditDTO.getUserName());
		applicationAuditDTO.setActivityTimeStamp(convertToStartTime(auditDTO
				.getStartTime()));
		applicationAuditDTO.setAffectedModule(auditDTO.getTarget());
		applicationAuditDTO.setIpAddress(auditDTO.getIpAddress());
		applicationAuditDTO.setEventDomain(getDomain(auditDTO.getUserName()));
		// applicationAuditDTO.setEventLocale(getEventLocale(auditDTO.getIpAddress()));
		applicationAuditDTO.setEventLocale("nil");
		applicationAuditDTO.setAuditSummary(processSummary(auditDTO));

		return applicationAuditDTO;
	}

	private String processSummary(com.pcs.alpine.services.dto.AuditDTO auditDTO) {
		String response = null;
		if (auditDTO.getResourceUrl().equalsIgnoreCase(
				NameConstants.USER_RESOURCE)
				&& auditDTO.getAction().equalsIgnoreCase(HttpMethod.POST)) {
			response = NameConstants.USER_CREATED;
		}
		return response;
	}

	private String getDomain(String userName) {
		String domain = userName.substring(userName.indexOf('@') + 1,
				userName.indexOf('.'));
		return domain;
	}

	private java.lang.String getEventLocale(java.lang.String ipAddress) {
		// TODO
		URL url;
		try {
			url = new URL(
					"https://context.skyhookwireless.com/accelerator/ip?version=2.0&ip="
							+ ipAddress
							+ "&prettyPrint=true&key=eJwVwUEOABAMBMCzxzTR0urVbviU-LuY0aL18xFZTmwwaSGchGh4SusLAnS16bCxeR8XXQtf&user=eval");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * Convert long to start time
	 * 
	 * @param startTime
	 * @return
	 */
	private String convertToStartTime(String startTime) {
		String timeStamp = null;
		try {
			if (StringUtils.isNumericSpace(startTime)) {
				Date timeStampDate = new Date(Long.parseLong(startTime)); // if
																			// you
																			// really
																			// have
																			// long
				SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
				sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
				timeStamp = sdf.format(timeStampDate.getTime());
			} else {
				timeStamp = startTime;
			}
		} catch (Exception e) {
			timeStamp = startTime;
		}
		return timeStamp;
	}
}
