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

import com.pcs.alpine.commons.dto.EntityTemplateDTO;

/**
 * TagRangePayload
 * 
 * @author Twinkle (pcseg297)
 * @date June 2016
 * @since alpine-1.0.0
 */
public class TagRangePayload implements Serializable {

	private static final long serialVersionUID = -1483084020597151074L;

	private String startNodeType;
	private String endNodeType;
	private StartNode startNode;
	private EndNode endNode;
	private List<EntityTemplateDTO> tagTypes;

	public String getStartNodeType() {
		return startNodeType;
	}

	public void setStartNodeType(String startNodeType) {
		this.startNodeType = startNodeType;
	}

	public String getEndNodeType() {
		return endNodeType;
	}

	public void setEndNodeType(String endNodeType) {
		this.endNodeType = endNodeType;
	}

	public StartNode getStartNode() {
		return startNode;
	}

	public void setStartNode(StartNode startNode) {
		this.startNode = startNode;
	}

	public EndNode getEndNode() {
		return endNode;
	}

	public void setEndNode(EndNode endNode) {
		this.endNode = endNode;
	}

	public List<EntityTemplateDTO> getTagTypes() {
		return tagTypes;
	}

	public void setTagTypes(List<EntityTemplateDTO> tagTypes) {
		this.tagTypes = tagTypes;
	}

}
