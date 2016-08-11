/**
 * 
 */
package com.pcs.data.platform.bean;

/**
 * @author pcseg129
 *
 */
public class HierarchySearch {
	
	private IdentityEntity parentIdentity;
	private String searchTemplateName;
	private String searchEntityType;

	public IdentityEntity getParentIdentity() {
		return parentIdentity;
	}

	public void setParentIdentity(IdentityEntity parentIdentity) {
		this.parentIdentity = parentIdentity;
	}

	public String getSearchTemplateName() {
		return searchTemplateName;
	}

	public void setSearchTemplateName(String searchTemplateName) {
		this.searchTemplateName = searchTemplateName;
	}

	public String getSearchEntityType() {
		return searchEntityType;
	}

	public void setSearchEntityType(String searchEntityType) {
		this.searchEntityType = searchEntityType;
	}
	
	
}
