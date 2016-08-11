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

import com.pcs.alpine.services.dto.IdentityDTO;

/**
 * GeotagDTO
 * 
 * @description DTO for Geotag
 * @author Suraj (PCSEG362)
 * @date 20 Apr 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class GeotagDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private IdentityDTO entity;

	private Tag tag;

	private CoordinatesDTO geotag;

	public IdentityDTO getEntity() {
		return entity;
	}

	public void setEntity(IdentityDTO entity) {
		this.entity = entity;
	}

	public CoordinatesDTO getGeotag() {
		return geotag;
	}

	public void setGeotag(CoordinatesDTO geotag) {
		this.geotag = geotag;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}