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
package com.pcs.alpine.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.alpine.commons.rest.dto.ErrorMessageDTO;
import com.pcs.alpine.services.dto.EntityDTO;

/**
 * TagInfoESBDTO
 *
 * @description DTO for tags
 * @author Daniela (PCSEG191)
 * @date 24 Apr 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class TagInfoESBDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityDTO geoTag;

	private EntityDTO entity;

	private ErrorMessageDTO errorMessage;

	public EntityDTO getGeoTag() {
		return geoTag;
	}

	public void setGeoTag(EntityDTO geoTag) {
		this.geoTag = geoTag;
	}

	public EntityDTO getEntity() {
		return entity;
	}

	public void setEntity(EntityDTO entity) {
		this.entity = entity;
	}

	public ErrorMessageDTO getErrorMessage() {
	    return errorMessage;
    }

	public void setErrorMessage(ErrorMessageDTO errorMessage) {
	    this.errorMessage = errorMessage;
    }

}
