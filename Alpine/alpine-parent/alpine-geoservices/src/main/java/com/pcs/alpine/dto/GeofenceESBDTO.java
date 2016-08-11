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

import com.pcs.alpine.services.dto.EntityDTO;

/**
 * GeofenceESBDTO
 *
 * @description DTO for geofence esb
 * @author Daniela (PCSEG191)
 * @date 12 Mar 2016
 * @since galaxy-1.0.0
 */
@XmlRootElement
public class GeofenceESBDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityDTO geofenceEntity;

	private EntityDTO geofenceTypeEntity;

	private String geofenceName;

	private String type;

	public EntityDTO getGeofenceEntity() {
		return geofenceEntity;
	}

	public void setGeofenceEntity(EntityDTO geofenceEntity) {
		this.geofenceEntity = geofenceEntity;
	}

	public EntityDTO getGeofenceTypeEntity() {
		return geofenceTypeEntity;
	}

	public void setGeofenceTypeEntity(EntityDTO geofenceTypeEntity) {
		this.geofenceTypeEntity = geofenceTypeEntity;
	}

	public String getGeofenceName() {
		return geofenceName;
	}

	public void setGeofenceName(String geofenceName) {
		this.geofenceName = geofenceName;
	}

	public String getType() {
	    return type;
    }

	public void setType(String type) {
	    this.type = type;
    }

}
