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
package com.pcs.alpine.services.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GeoTaggedEntitiesDTO
 *
 * @description DTO for tags
 * @author Daniela (PCSEG191)
 * @date 20 Apr 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class GeoTaggedEntitiesDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String entityTemplateName;

	private List<GeoTaggedEntityDTO> entities;

	public String getEntityTemplateName() {
		return entityTemplateName;
	}

	public void setEntityTemplateName(String entityTemplateName) {
		this.entityTemplateName = entityTemplateName;
	}

	public List<GeoTaggedEntityDTO> getEntities() {
		return entities;
	}

	public void setEntities(List<GeoTaggedEntityDTO> entities) {
		this.entities = entities;
	}

}
