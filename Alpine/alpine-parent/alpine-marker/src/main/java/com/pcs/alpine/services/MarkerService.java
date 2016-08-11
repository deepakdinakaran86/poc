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
package com.pcs.alpine.services;

import java.util.List;

import com.pcs.alpine.dto.MarkerBatchDTO;
import com.pcs.alpine.services.dto.EntityCountDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntitySearchDTO;
import com.pcs.alpine.services.dto.IdentityDTO;
import com.pcs.alpine.services.dto.ReturnFieldsDTO;
import com.pcs.alpine.services.dto.SearchFieldsDTO;
import com.pcs.alpine.services.dto.StatusMessageDTO;

/**
 * @description This class is responsible for the Custom EntityService interface
 * 
 * @author bhupendra (pcseg329)
 * @date 14 Jan 2015
 */
public interface MarkerService {

	/**
	 * 
	 * @Description Fetch details of an entity based on unique identifiers
	 *              fields in EntityDTO
	 * @param IdentityDTO
	 * @return EntityDTO
	 */

	public EntityDTO getMarker(IdentityDTO identityDTO);

	/**
	 * 
	 * @Description Fetch list of an entities based on domain and uniqueUserId
	 * @param IdentityDTO
	 * @return List<EntityDTO>
	 */

	public List<EntityDTO> getMarkersByDomain(IdentityDTO identity);

	/**
	 * 
	 * @Description Create a custom entity in Database
	 * @param MarkerBatchDTO
	 * @return EntityDTO
	 */

	public EntityDTO saveMarker(EntityDTO entityDTO);

	/**
	 * 
	 * @Description Update a custom entity in Database
	 * @param MarkerBatchDTO
	 * @return StatusMessageDTO
	 */

	public StatusMessageDTO updateMarker(EntityDTO entityDTO);

	/**
	 * 
	 * @Description Delete an entity based on unique identifier fields
	 * @param IdentityDTO
	 * @return StatusMessageDTO
	 */

	public StatusMessageDTO deleteMarker(IdentityDTO identityDTO);

	/**
	 * 
	 * @Description Fetch list of an entities based on domain and template name
	 * @param IdentityDTO
	 * @return List<EntityDTO>
	 */

	public List<EntityDTO> getMarkers(IdentityDTO identity);

	/**
	 * 
	 * @Description Service used to validate a marker entity
	 * @param EntitySearchDTO
	 * @return StatusMessageDTO
	 */

	public StatusMessageDTO validateUniqueness(EntitySearchDTO identity);

	/**
	 * 
	 * @Description Service to search for specific fields in an entity
	 * @param SearchFieldsDTO
	 * @return ReturnFieldsListDTO
	 */
	public List<ReturnFieldsDTO> searchFields(SearchFieldsDTO searchFieldsDTO);

	/***
	 * @Description This method is responsible to get the marker count
	 * 
	 * @param EntitySearchDTO
	 * 
	 * @return EntityCountDTO
	 */
	public EntityCountDTO getMarkerCount(EntitySearchDTO markerSearch);

	/**
	 * 
	 * @Description Service to get the count for specific fields in an entity
	 * @param SearchFieldsDTO
	 * @return EntityCountDTO
	 */
	public EntityCountDTO getSearchFieldsCount(SearchFieldsDTO searchFieldsDTO);

	/**
	 * 
	 * @Description Create a list of custom entities in Database
	 * @param List
	 *            <IdentityDTO>
	 * @return List<EntityDTO>
	 */

	public List<IdentityDTO> saveMarkers(MarkerBatchDTO markers);

	/**
	 * 
	 * @Description update a list of custom entities in Database
	 * @param List
	 *            <IdentityDTO>
	 * @return List<EntityDTO>
	 */

	public StatusMessageDTO updateMarkersStatus(List<IdentityDTO> markers,
	        String statusName);

	/**
	 * 
	 * @Description get a list of entities in Database
	 * @param List
	 *            <IdentityDTO>
	 * @return List<EntityDTO>
	 */
	public List<EntityDTO> getMarkers(List<IdentityDTO> markers);

}
