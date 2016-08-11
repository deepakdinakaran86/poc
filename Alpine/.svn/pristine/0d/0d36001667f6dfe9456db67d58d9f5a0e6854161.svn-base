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
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.StatusDTO;

/**
 * GeofenceDTO
 *
 * @description DTO for geofence
 * @author Daniela (PCSEG191)
 * @date 10 Mar 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class GeofenceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String geofenceName;

	private String desc;

	private String type;

	private EntityTemplateDTO template;

	private DomainDTO domain;

	private List<FieldMapDTO> geofenceFields;

	private FieldMapDTO identifier;

	private StatusDTO geofenceStatus;

	public String getGeofenceName() {
		return geofenceName;
	}

	public void setGeofenceName(String geofenceName) {
		this.geofenceName = geofenceName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FieldMapDTO> getGeofenceFields() {
		return geofenceFields;
	}

	public void setGeofenceFields(List<FieldMapDTO> geofenceFields) {
		this.geofenceFields = geofenceFields;
	}

	public EntityTemplateDTO getTemplate() {
		return template;
	}

	public void setTemplate(EntityTemplateDTO template) {
		this.template = template;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public FieldMapDTO getIdentifier() {
		return identifier;
	}

	public void setIdentifier(FieldMapDTO identifier) {
		this.identifier = identifier;
	}

	public StatusDTO getGeofenceStatus() {
	    return geofenceStatus;
    }

	public void setGeofenceStatus(StatusDTO geofenceStatus) {
	    this.geofenceStatus = geofenceStatus;
    }

}
