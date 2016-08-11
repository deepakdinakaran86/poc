/**
 * 
 */
package com.pcs.alpine.serviceImpl;

import static com.pcs.alpine.services.enums.EMDataFields.STATUS_NAME;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.validation.ValidationUtils;
import com.pcs.alpine.model.Status;
import com.pcs.alpine.serviceImpl.repository.StatusRepository;
import com.pcs.alpine.services.StatusService;
import com.pcs.alpine.services.dto.StatusDTO;

/**
 * StatusServiceImpl
 * 
 * @description Responsible for providing the service implementations for a
 *              status
 * 
 * @author Daniela(PCSEG191)
 * @date 25 Aug 2014
 * @since galaxy-1.0.0
 */
@Service
public class StatusServiceImpl implements StatusService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(StatusServiceImpl.class);

	@Autowired
	private StatusRepository statusRepository;

	/**
	 * @Description Responsible for fetching status key for status name
	 * @param statusName
	 * @return statusKey
	 */
	@Override
	public Integer getStatus(String statusName) {
		LOGGER.debug(">>>> Entry getStatus with statusName:{}", statusName);
		ValidationUtils.validateMandatoryField(STATUS_NAME, statusName);
		Integer statusKey = statusRepository.getStatus(statusName);

		if (statusKey == null || statusKey < 0) {
			throw new GalaxyException(
			        GalaxyCommonErrorCodes.INVALID_DATA_SPECIFIED,
			        STATUS_NAME.getDescription());
		}
		return statusKey;
	}

	@Override
	public List<StatusDTO> getAllStatus() {
		List<Status> statusList = statusRepository.getAllStatus();
		if (CollectionUtils.isEmpty(statusList)) {
			LOGGER.debug("<<-- getAllStatus, no data-->>");
			throw new GalaxyException(GalaxyCommonErrorCodes.DATA_NOT_AVAILABLE);
		}
		List<StatusDTO> statusDTOs = new ArrayList<StatusDTO>();
		for (Status status : statusList) {
			statusDTOs.add(getStatusDTO(status));
		}
		return statusDTOs;
	}

	private StatusDTO getStatusDTO(Status status) {
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setStatusKey(status.getStatusKey());
		statusDTO.setStatusName(status.getStatusName());
		return statusDTO;
	}

}
