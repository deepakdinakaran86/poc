package com.pcs.alpine.service;

import java.util.List;

import com.pcs.alpine.commons.dto.IdentityDTO;
import com.pcs.alpine.commons.dto.StatusMessageDTO;
import com.pcs.alpine.dto.DocumentDTO;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author Riyas (PCSEG296)
 * @date 12 June 2016
 * @since alpine-1.0.0
 */
public interface DocumentService {
	/***
	 * @Description This method is responsible to create a geotag
	 * 
	 * @param GeotagDTO
	 * @return StatusMessageDTO
	 */
	public DocumentDTO createDocument(DocumentDTO document);

	/***
	 * @Description This method is responsible to update a geotag
	 * 
	 * @param GeotagDTO
	 * @return StatusMessageDTO
	 */
	public StatusMessageDTO updateDocument(DocumentDTO document);

	/***
	 * @Description This method is responsible to retrieve a specific geotag
	 * 
	 * @param geotag
	 *            identifier fields
	 * @return GeotagDTO
	 */
	public DocumentDTO findDocument(IdentityDTO document);

	/***
	 * @Description This method is responsible to retrieve all geotag's of a
	 *              domain
	 * 
	 * @param domainName
	 *            ,entityTemplateName
	 * 
	 * @return List<GeoTaggedEntitiesDTO>
	 */
	public List<DocumentDTO> listDocuments(String domain, String assetType);

}
