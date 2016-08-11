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

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * HierarchyEntityDTO
 * 
 * @author RIYAS PH (pcseg296)
 * @date October 2015
 * @since alpine-1.0.0
 */
@XmlRootElement(name = "hierarchy")
public class HierarchyDTO {

	private EntityDTO actor;
	private List<EntityDTO> subjects;
	private EntityDTO tenant;

	public EntityDTO getActor() {
		return actor;
	}

	public void setActor(EntityDTO actor) {
		this.actor = actor;
	}

	public List<EntityDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<EntityDTO> subjects) {
		this.subjects = subjects;
	}

	public EntityDTO getTenant() {
		return tenant;
	}

	public void setTenant(EntityDTO tenant) {
		this.tenant = tenant;
	}

}
