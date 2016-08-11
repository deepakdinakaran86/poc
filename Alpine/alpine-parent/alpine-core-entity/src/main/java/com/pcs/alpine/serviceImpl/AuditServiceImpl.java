/**
 * 
 */
package com.pcs.alpine.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.alpine.model.Audit;
import com.pcs.alpine.serviceImpl.repository.AuditRepository;
import com.pcs.alpine.services.AuditService;
import com.pcs.alpine.services.dto.AuditDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.validation.ValidationUtils;

/**
 * AuditServiceImpl
 * 
 * @description Responsible for providing the service implementations for a
 *              Audit
 * 
 * @author DEEPAK DINAKARAN (PCSEG288)
 * @date 09 Jul 2016
 * @since alpine-1.0.0
 */
@Service
public class AuditServiceImpl implements AuditService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AuditServiceImpl.class);

	@Autowired
	private AuditRepository auditRepository;


	@Override
	public List<AuditDTO> getAllAudits() {
		List<Audit> auditList = auditRepository.getAllAudit();
		if (CollectionUtils.isEmpty(auditList)) {
			LOGGER.debug("<<-- getAllStatus, no data-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<AuditDTO> auditDTOs = new ArrayList<AuditDTO>();
		for (Audit audit : auditList) {
			auditDTOs.add(getAuditDTO(audit));
		}
		return auditDTOs;
	}

	private AuditDTO getAuditDTO(Audit audit) {
		AuditDTO auditDTO = new AuditDTO();
		auditDTO.setUserName(audit.getUserName());
		auditDTO.setStartTime(audit.getStartTime());
		auditDTO.setResourceUrl(audit.getResourceUrl());
		auditDTO.setAction(audit.getAction());
		auditDTO.setActorIdentity(audit.getActorIdentity());
		auditDTO.setEndTime(audit.getEndTime());
		auditDTO.setIpAddress(audit.getIpAddress());
		auditDTO.setRemarks(audit.getRemarks());
		auditDTO.setTarget(audit.getTarget());
		auditDTO.setTargetIdentity(audit.getTargetIdentity());
		auditDTO.setTotalTime(audit.getTotalTime());
		
		return auditDTO;
	}

}
