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
package com.pcs.avocado.commons.dto;

import java.io.Serializable;
import java.util.List;

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg191
 * @date Jan 21, 2015
 * @since galaxy-1.0.0
 */
public class PointRelationship implements Serializable {

	private static final long serialVersionUID = 6566986422578998677L;

	private IdentityDTO equipment;
	
	private String equipmentId;
	
	private String facilityName;
	
	private String tenantName;
	
	private IdentityDTO device;
	
	private List<EntityDTO> points;
	
	private Boolean pointsExist;
	
	private SkySparkPayload skysparkPayload;
	
	private String pointTemplate;
	
	private List<SearchFieldsDTO> pointValidation;

	public IdentityDTO getEquipment() {
		return equipment;
	}

	public void setEquipment(IdentityDTO equipment) {
		this.equipment = equipment;
	}

	public IdentityDTO getDevice() {
		return device;
	}

	public void setDevice(IdentityDTO device) {
		this.device = device;
	}

	public List<EntityDTO> getPoints() {
		return points;
	}

	public void setPoints(List<EntityDTO> points) {
		this.points = points;
	}

	

	public SkySparkPayload getSkysparkPayload() {
		return skysparkPayload;
	}

	public void setSkysparkPayload(SkySparkPayload skysparkPayload) {
		this.skysparkPayload = skysparkPayload;
	}

	public String getPointTemplate() {
		return pointTemplate;
	}

	public void setPointTemplate(String pointTemplate) {
		this.pointTemplate = pointTemplate;
	}

	public Boolean getPointsExist() {
	    return pointsExist;
    }

	public void setPointsExist(Boolean pointsExist) {
	    this.pointsExist = pointsExist;
    }

	public String getEquipmentId() {
	    return equipmentId;
    }

	public void setEquipmentId(String equipmentId) {
	    this.equipmentId = equipmentId;
    }

	public String getFacilityName() {
	    return facilityName;
    }

	public void setFacilityName(String facilityName) {
	    this.facilityName = facilityName;
    }

	public String getTenantName() {
	    return tenantName;
    }

	public void setTenantName(String tenantName) {
	    this.tenantName = tenantName;
    }

	public List<SearchFieldsDTO> getPointValidation() {
	    return pointValidation;
    }

	public void setPointValidation(List<SearchFieldsDTO> pointValidation) {
	    this.pointValidation = pointValidation;
    }

}
