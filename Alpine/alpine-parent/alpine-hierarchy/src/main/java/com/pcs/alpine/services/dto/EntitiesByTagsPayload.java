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

import com.pcs.alpine.commons.dto.EntityDTO;

/**
 * EntitiesByTagsPayload
 * 
 * @author Twinkle (pcseg297)
 * @date June 2016
 * @since alpine-1.0.0
 */
public class EntitiesByTagsPayload implements Serializable {

	private static final long serialVersionUID = -1483084020597151074L;

	private List<String> platformEntityTypes;
	private List<String> identifierKeys;
	private List<String> identifierValues;
	private List<String> domains;
	private List<String> templateNames;
	
	private List<EntityDTO> startNodes;
	private EntitiesByTagsFilter filter;
	private String match;

	public List<String> getIdentifierValues() {
		return identifierValues;
	}

	public void setIdentifierValues(List<String> identifierValues) {
		this.identifierValues = identifierValues;
	}

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public List<String> getTemplateNames() {
		return templateNames;
	}

	public void setTemplateNames(List<String> templateNames) {
		this.templateNames = templateNames;
	}

	public EntitiesByTagsFilter getFilter() {
		return filter;
	}

	public void setFilter(EntitiesByTagsFilter filter) {
		this.filter = filter;
	}

	public List<String> getPlatformEntityTypes() {
		return platformEntityTypes;
	}

	public void setPlatformEntityTypes(List<String> platformEntityTypes) {
		this.platformEntityTypes = platformEntityTypes;
	}

	public List<String> getIdentifierKeys() {
		return identifierKeys;
	}

	public void setIdentifierKeys(List<String> identifierKeys) {
		this.identifierKeys = identifierKeys;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public List<EntityDTO> getStartNodes() {
		return startNodes;
	}

	public void setStartNodes(List<EntityDTO> startNodes) {
		this.startNodes = startNodes;
	}

}
