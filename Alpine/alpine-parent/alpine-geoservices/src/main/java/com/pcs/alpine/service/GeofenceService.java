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
package com.pcs.alpine.service;

import java.util.List;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.dto.GeofenceDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.IdentityDTO;

/**
 * 
 * @description This class is responsible for the GeoService Interface
 * @date 10 Mar 2016
 * @since galaxy-1.0.0
 */
public interface GeofenceService {

	/***
	 * @Description This method is responsible to create a geofence
	 *
	 * @param GeofenceDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO createGeofence(GeofenceDTO geofence);

	/***
	 * @Description This method is responsible to update a geofence
	 *
	 * @param GeofenceDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateGeofence(GeofenceDTO geofence);

	/***
	 * @Description This method is responsible to retrieve a specific geofence
	 *
	 * @param geofence
	 *            identifier fields
	 * @return GeofenceDTO
	 */
	public GeofenceDTO findGeofence(IdentityDTO geofence);

	/***
	 * @Description This method is responsible to retrieve all geofence's of a
	 *              domain
	 *
	 * @param geofence
	 *            identifier fields
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> findAllGeofences(String domainName);

	/***
	 * @Description This method is responsible to delete a tenant
	 *
	 * @param geofence
	 *            identifier fields
	 * @return Status
	 */
	public StatusMessageDTO deleteGeofence(IdentityDTO geofence);

}
