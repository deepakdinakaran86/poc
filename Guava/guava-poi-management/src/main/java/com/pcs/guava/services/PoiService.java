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
package com.pcs.guava.services;

import java.util.List;

import com.pcs.guava.commons.dto.IdentityDTO;
import com.pcs.guava.commons.dto.StatusMessageDTO;
import com.pcs.guava.dto.PoiDTO;

/**
 * Service Interface for POI
 * 
 * @author Greeshma (PCSEG323)
 * @date April 2016
 * @since Guava-1.0.0
 */
public interface PoiService {

	public StatusMessageDTO createPoi(PoiDTO poiDTO);
	
	public List<IdentityDTO> createPois(List<PoiDTO> poiDTOs);

	public StatusMessageDTO updatePoi(String poiName, PoiDTO poiDTO);
	
	public StatusMessageDTO updatePoi1(PoiDTO poiDTO);

	public List<PoiDTO> getAllPois(String domain, String poiType);

	public PoiDTO getPoiDetails(IdentityDTO poi);
	
	public PoiDTO getPoiDetails1(IdentityDTO poi);

	public List<PoiDTO> getPois(List<String> poiTypes, String domain);

	public StatusMessageDTO deletePoi(IdentityDTO poi);

}
