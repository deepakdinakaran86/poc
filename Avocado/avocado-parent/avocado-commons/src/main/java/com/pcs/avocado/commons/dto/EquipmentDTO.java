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
import java.util.Map;

import com.pcs.avocado.commons.dto.EntityDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.IdentityDTO;

/**
 * DTO for Equipment
 * 
 * @author pcseg199 (Javid Ahammed)
 * @date January 2016
 * @since Avocado-1.0.0
 */
public class EquipmentDTO implements Serializable {

	private static final long serialVersionUID = -775482194445563861L;
	private IdentityDTO equipment;
	private IdentityDTO facility;
	private List<EntityDTO> points;
	private List<FieldMapDTO> fieldValues;
	private Map<String, Object> skySparkEquipmentTags;
	private List<SkySparkPoint> skySparkPointTags;
	private FieldMapDTO tree;
	
	
	public IdentityDTO getEquipment() {
		return equipment;
	}
	public void setEquipment(IdentityDTO equipment) {
		this.equipment = equipment;
	}
	public IdentityDTO getFacility() {
		return facility;
	}
	public void setFacility(IdentityDTO facility) {
		this.facility = facility;
	}
	public List<EntityDTO> getPoints() {
		return points;
	}
	public void setPoints(List<EntityDTO> points) {
		this.points = points;
	}
	public List<FieldMapDTO> getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(List<FieldMapDTO> fieldValues) {
		this.fieldValues = fieldValues;
	}
	public Map<String, Object> getSkySparkEquipmentTags() {
		return skySparkEquipmentTags;
	}
	public void setSkySparkEquipmentTags(Map<String, Object> skySparkEquipmentTags) {
		this.skySparkEquipmentTags = skySparkEquipmentTags;
	}
	public List<SkySparkPoint> getSkySparkPointTags() {
		return skySparkPointTags;
	}
	public void setSkySparkPointTags(List<SkySparkPoint> skySparkPointTags) {
		this.skySparkPointTags = skySparkPointTags;
	}
	public FieldMapDTO getTree() {
	    return tree;
    }
	public void setTree(FieldMapDTO tree) {
	    this.tree = tree;
    }
	
}
