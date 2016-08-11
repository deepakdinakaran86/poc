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

import com.pcs.avocado.commons.dto.Device;
import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.EquipmentDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;
import com.pcs.avocado.commons.dto.LatestDataResultDTO;
import com.pcs.avocado.commons.dto.PointRelationship;
import com.pcs.avocado.commons.dto.StatusMessageDTO;

/**
 * Service Interface for Air Handlers
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date January 2016
 * @since Avocado-1.0.0
 */
@Service
public interface EquipmentService {

	/**
	 * Service to create create a new equipment
	 * 
	 * This service will create a specific payload and invoke the corresponding
	 * ESB endpoint
	 * 
	 * @param equipment
	 * @return
	 */
	public EntityDTO createEquipment(EquipmentDTO equipment);

	/**
	 * Service to update an existing equipment
	 * 
	 * This service will create a specific payload and invoke the corresponding
	 * ESB endpoint
	 * 
	 * @param equipment
	 * @return
	 */
	public StatusMessageDTO updateEquipment(EquipmentDTO equipment);

	/**
	 * Service to fetch details of the equipment and associated points
	 * 
	 * @param identifier
	 * @return
	 */
	public EquipmentDTO getEquipmentDetails(IdentityDTO identityDTO);

	/**
	 * Service to fetch all points of a device with status If device not present
	 * in alpine then get from Saffron
	 * 
	 * @param device
	 * @return
	 */
	public List<EntityDTO> getPointsOfADevice(Device device);

	/**
	 * Service to create a list of points and attach relationships with a device
	 * and equipment, a point can be created if a device has no other points
	 * attached to it, hence once points are associated only updates on those
	 * points will be allowed, all other sync operations will happen when the
	 * point configuration is updated
	 * 
	 * @param device
	 * @return
	 */
	public StatusMessageDTO createPointsAndRelationship(
	        PointRelationship pointRelationship);

	/**
	 * Service to fetch details of the equipment and associated points
	 * 
	 * @param identifier
	 * @return
	 */
	public List<LatestDataResultDTO> getGensetLatestData(IdentityDTO identityDTO);

}
