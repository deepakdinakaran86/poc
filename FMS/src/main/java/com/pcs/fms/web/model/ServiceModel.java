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
package com.pcs.fms.web.model;

import java.io.Serializable;
import java.util.List;

import com.pcs.fms.web.dto.Tag;

/**
 * DTO for Tag
 * 
 * @author Twinkle (PCSEG297)
 * @date May 2016
 * @since galaxy-1.0.0
 */
public class ServiceModel implements Serializable {

	private static final long serialVersionUID = -6764529651188105693L;

	private String itemDomain;
	private String itemIdentifier;
	private String itemName;
	private String itemDescription;
	private List<String> itemValues;
	private List<Tag> itemTags;

	public String getItemDomain() {
		return itemDomain;
	}

	public void setItemDomain(String itemDomain) {
		this.itemDomain = itemDomain;
	}

	public String getItemIdentifier() {
		return itemIdentifier;
	}

	public void setItemIdentifier(String itemIdentifier) {
		this.itemIdentifier = itemIdentifier;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public List<String> getItemValues() {
		return itemValues;
	}

	public void setItemValues(List<String> itemValues) {
		this.itemValues = itemValues;
	}

	public void setItemTags(List<Tag> itemTags) {
		this.itemTags = itemTags;
	}

	public List<Tag> getItemTags() {
		return itemTags;
	}

}
