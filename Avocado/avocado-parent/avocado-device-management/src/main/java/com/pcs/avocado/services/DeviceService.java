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
package com.pcs.avocado.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pcs.avocado.commons.dto.DeviceLocationDTO;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.HierarchyDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.ReturnFieldsDTO;
import com.pcs.avocado.commons.dto.StatusMessageDTO;
import com.pcs.avocado.dto.ClaimDeviceDTO;
import com.pcs.avocado.dto.Device;
import com.pcs.avocado.dto.GeneralBatchResponse;
import com.pcs.avocado.dto.GensetAlarmHistoryDTO;
import com.pcs.avocado.dto.OwnerDeviceDTO;

/**
 * Service Interface for Device
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date January 2016
 * @since Avocado-1.0.0
 */
@Service
public interface DeviceService {

	/**
	 * Service to create create a new device
	 * 
	 * This service will create a specific payload and invoke the corresponding
	 * ESB endpoint
	 * 
	 * @param device
	 * @return
	 */
	public StatusMessageDTO createDevice(Device device);

	/**
	 * Service to update an existing device
	 * 
	 * This service will create a specific payload and invoke the corresponding
	 * ESB endpoint
	 * 
	 * @param device
	 * @return
	 */
	public StatusMessageDTO updateDevice(String sourceId, Device device);

	/**
	 * Service to claim a device,update in Saffron and Create in Alpine
	 * 
	 * @param device
	 * @return
	 */
	public StatusMessageDTO claimDevice(String sourceId, Device device);

	/**
	 * Service to claim a device,update in Saffron and Create in Alpine
	 * 
	 * @param device
	 * @return
	 */
	public StatusMessageDTO claimDevices(ClaimDeviceDTO claimDevice);

	/**
	 * Service to fetch details of the device
	 * 
	 * @param identifier
	 * @return
	 */
	public EntityDTO getDeviceDetails(String identifier);

	/**
	 * Service to assign a bulk devices to multiple clients
	 * 
	 * @param hierarchy
	 * @return
	 */
	public StatusMessageDTO assignBulkDevice(OwnerDeviceDTO bulkDevices);

	/**
	 * Service to assign a device to a tenant
	 * 
	 * @param hierarchy
	 * @return
	 */
	public GeneralBatchResponse assignDevice(HierarchyDTO hierarchy);

	/**
	 * Service to fetch all the devices of the logged in user's domain with an
	 * optional filtered with specified tenant
	 * 
	 * @param hierarchySearchDTO
	 * @return
	 */
	public List<EntityDTO> getAllDevices(IdentityDTO tenantIdentity);

	/**
	 * Service to fetch all the Live alarms(Active) from an Asset
	 * 
	 * @param domainName
	 * @return
	 * 
	 */
	public List<GensetAlarmHistoryDTO> getLiveAlarms(String domainName);

	/**
	 * Service to fetch all the device location i.e latitude and longitude for
	 * the devices of a domain
	 * 
	 * @param domainName
	 * @return DeviceLocationDTO
	 */
	public List<DeviceLocationDTO> getDevicesLocation(String domainName,
	        String locationMode);

	/**
	 * Service to get the Alarm History of an Asset
	 * 
	 * @param domainName
	 * @return
	 */
	public List<GensetAlarmHistoryDTO> getAlarmHistory(String domainName,
	        Long startDate, Long endDate);

	public List<EntityDTO> getAllOwnedDeviceByDomain(IdentityDTO identityDTO);

	public List<ReturnFieldsDTO> getDevices(List<String> datasources);

	public StatusMessageDTO deleteDevice(IdentityDTO deviceIdentifier,
	        String datasourceName);
}
