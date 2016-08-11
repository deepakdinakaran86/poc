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
 * GeoTaggedEntityDTO
 * 
 * @description DTO for tags
 * @author Daniela (PCSEG191)
 * @date 21 Apr 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class GeoTaggedEntityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private FieldMapDTO identifier;

	private List<FieldMapDTO> dataprovider;

	private DomainDTO domain;

	private CoordinatesDTO geoTag;

	private FieldMapDTO tree;

	public FieldMapDTO getIdentifier() {
		return identifier;
	}

	public void setIdentifier(FieldMapDTO identifier) {
		this.identifier = identifier;
	}

	public List<FieldMapDTO> getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(List<FieldMapDTO> dataprovider) {
		this.dataprovider = dataprovider;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public CoordinatesDTO getGeoTag() {
		return geoTag;
	}

	public void setGeoTag(CoordinatesDTO geoTag) {
		this.geoTag = geoTag;
	}

	public FieldMapDTO getTree() {
		return tree;
	}

	public void setTree(FieldMapDTO tree) {
		this.tree = tree;
	}

}
