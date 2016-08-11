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
package com.pcs.fms.web.services;

import static com.pcs.fms.web.constants.FMSWebConstants.IDENTIFIER;
import static com.pcs.fms.web.constants.FMSWebConstants.UPDATE;

import java.util.List;

import java.lang.reflect.Type;
import com.google.common.reflect.TypeToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.dto.DomainDTO;
import com.pcs.fms.web.dto.FieldMapDTO;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.ServiceScheduleDTO;
import com.pcs.fms.web.dto.StatusDTO;
import com.pcs.fms.web.dto.StatusMessageDTO;
import com.pcs.fms.web.model.ServiceSchedule;

/**
 * @author PCSEG191 Daniela
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class ServiceScheduleService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TenantService.class);

	@Value("${fms.services.findServiceSchedule}")
	private String findServiceScheduleEndpointUri;

	@Value("${fms.services.createServiceSchedule}")
	private String createServiceScheduleEndpointUri;

	@Value("${fms.services.updateServiceSchedule}")
	private String updateServiceScheduleEndpointUri;

	@Value("${fms.services.listServiceSchedule}")
	private String listServiceScheduleEndpointUri;

	public FMSResponse<StatusMessageDTO> updateServiceSchedule(
	        ServiceSchedule serviceSchedule,
	        List<IdentityDTO> serviceComponentIdentifiers) {
		String updateScheduleURI = getServiceURI(updateServiceScheduleEndpointUri);
		// Construct ServiceComponentDTO
		ServiceScheduleDTO serviceScheduleDTO = constructeScheduleDTO(
		        serviceSchedule, serviceComponentIdentifiers);

		// Invoke update endpoint
		return getPlatformClient().putResource(updateScheduleURI,
		        serviceScheduleDTO, StatusMessageDTO.class);

	}

	public FMSResponse<StatusMessageDTO> createServiceSchedule(
	        ServiceSchedule serviceSchedule,
	        List<IdentityDTO> serviceComponentIdentifiers) {
		String createScheduleURI = getServiceURI(createServiceScheduleEndpointUri);

		// Construct ServiceComponentDTO
		ServiceScheduleDTO serviceComponentDTO = constructeScheduleDTO(
		        serviceSchedule, serviceComponentIdentifiers);

		// Invoke update endpoint
		return getPlatformClient().postResource(createScheduleURI,
		        serviceComponentDTO, StatusMessageDTO.class);

	}
	
	/**
	 * Method to list all Service Schedules
	 * 
	 * @param (query) domainName
	 * @return List<EntityDTO>
	 */
	public FMSResponse<List<EntityDTO>> listServiceSchedules(String currDomain) {
		String listServiceSchedulesServiceURI = getServiceURI(
				listServiceScheduleEndpointUri).replace("{domain}", currDomain);

		Type serviceSchedules = new TypeToken<List<EntityDTO>>() {
			private static final long serialVersionUID = 5936335989523954928L;
		}.getType();

		return getPlatformClient().getResource(listServiceSchedulesServiceURI,
				serviceSchedules);
	}

	public ServiceSchedule findServiceSchedule(IdentityDTO schedule) {
		String findServiceScheduleURI = getServiceURI(findServiceScheduleEndpointUri);

		ServiceSchedule serviceSchedule = new ServiceSchedule();
		// Invoke find endpoint
		FMSResponse<ServiceScheduleDTO> scheduleDetail = getPlatformClient()
		        .postResource(findServiceScheduleURI, schedule,
		                ServiceScheduleDTO.class);
		if (scheduleDetail.getErrorMessage() == null) {
			serviceSchedule = constructScheduleModel(scheduleDetail.getEntity());
		}
		return serviceSchedule;
	}

	private ServiceScheduleDTO constructeScheduleDTO(
	        ServiceSchedule serviceSchedule,
	        List<IdentityDTO> serviceComponentIdentifiers) {
		ServiceScheduleDTO scheduleDTO = new ServiceScheduleDTO();
		// Set domain
		DomainDTO scheduleDomain = new DomainDTO();
		scheduleDomain.setDomainName(FMSAccessManager.getCurrentDomain());
		scheduleDTO.setDomain(scheduleDomain);

		scheduleDTO.setServiceScheduleName(serviceSchedule
		        .getServiceScheduleName());
		scheduleDTO.setDescription(serviceSchedule.getDescription());
		scheduleDTO.setOccuranceType(serviceSchedule.getOccurenceType());
		StatusDTO status = new StatusDTO();
		status.setStatusName("ACTIVE");
		scheduleDTO.setServiceScheduleStatus(status);
		scheduleDTO.setServiceComponentIdentifiers(serviceComponentIdentifiers);

		if (serviceSchedule.getAction() != null
		        && serviceSchedule.getAction().equalsIgnoreCase(UPDATE)) {
			FieldMapDTO identifier = new FieldMapDTO();
			identifier.setKey(IDENTIFIER);
			identifier.setValue(serviceSchedule.getIdentifier());
			scheduleDTO.setServiceScheduleIdentifier(identifier);
		}

		return scheduleDTO;

	}

	private ServiceSchedule constructScheduleModel(
	        ServiceScheduleDTO scheduleDTO) {
		ServiceSchedule schedule = new ServiceSchedule();
		schedule.setDescription(scheduleDTO.getDescription());
		schedule.setDomain(scheduleDTO.getDomain().getDomainName());
		schedule.setIdentifier(scheduleDTO.getServiceScheduleIdentifier()
		        .getValue());
		schedule.setOccurenceType(scheduleDTO.getOccuranceType());
		schedule.setServiceScheduleName(scheduleDTO.getServiceScheduleName());
		schedule.setServiceScheduleNameEdit(scheduleDTO
		        .getServiceScheduleName());
		schedule.setSelectedList(scheduleDTO.getServiceComponentNames());
		return schedule;
	}

}
