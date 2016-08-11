package com.pcs.alpine.service;

import java.util.List;

import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.dto.GeoTaggedEntitiesDTO;
import com.pcs.alpine.dto.GeotagDTO;
import com.pcs.alpine.services.dto.IdentityDTO;

/**
 * @description This class is responsible for the GeotagServiceImpl
 * @author Suraj Sugathan (PCSEG362)
 * @date 20 Apr 2016
 */
public interface GeotagService {
	/***
	 * @Description This method is responsible to create a geotag
	 *
	 * @param GeotagDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO createGeotag(GeotagDTO geotag);

	/***
	 * @Description This method is responsible to update a geotag
	 *
	 * @param GeotagDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateGeotag(GeotagDTO geotag);

	/***
	 * @Description This method is responsible to retrieve a specific geotag
	 *
	 * @param geotag
	 *            identifier fields
	 * @return GeotagDTO
	 */
	public GeotagDTO findGeotag(IdentityDTO geotag);

	/***
	 * @Description This method is responsible to retrieve all geotag's of a
	 *              domain
	 *
	 * @param domainName
	 *            ,entityTemplateName
	 * 
	 * @return List<GeoTaggedEntitiesDTO>
	 */
	public List<GeoTaggedEntitiesDTO> findAllGeotags(String domainName,
	        String entityTemplateName);

	/***
	 * @Description This method is responsible to delete a geotag
	 *
	 * @param geotag
	 *            identifier fields
	 * @return Status
	 */
	public StatusMessageDTO deleteGeotag(IdentityDTO geotag);
	
	/***
	 * @Description This method is responsible to fetch template names of Geotagged entities
	 *
	 * @param IdentityDTO
	 *            identifier fields
	 * @return List<String>
	 */
	public List<String> getAttachedGeotaggedTemplates(String domainName);
}
