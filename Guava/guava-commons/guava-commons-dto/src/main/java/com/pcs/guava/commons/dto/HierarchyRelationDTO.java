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
package com.pcs.guava.commons.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.pcs.guava.commons.dto.EntityDTO;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author Riyas (PCSEG296)
 * @date 12 June 2016
 * @since alpine-1.0.0
 */

@XmlRootElement
public class HierarchyRelationDTO {

	private EntityDTO parent;
	private EntityDTO child;

	public EntityDTO getParent() {
		return parent;
	}

	public void setParent(EntityDTO parent) {
		this.parent = parent;
	}

	public EntityDTO getChild() {
		return child;
	}

	public void setChild(EntityDTO child) {
		this.child = child;
	}

}
