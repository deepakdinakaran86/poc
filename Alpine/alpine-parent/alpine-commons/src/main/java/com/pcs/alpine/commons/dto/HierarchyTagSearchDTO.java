package com.pcs.alpine.commons.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HierarchyTagSearchDTO {

	private String tagType;

	private String domainName;

	private String endEntityTemplate;

	private List<String> intermediateTemplates;

	private Boolean isParentDomain;

	public String getTagType() {
	    return tagType;
    }

	public void setTagType(String tagType) {
	    this.tagType = tagType;
    }

	public String getDomainName() {
	    return domainName;
    }

	public void setDomainName(String domainName) {
	    this.domainName = domainName;
    }

	public String getEndEntityTemplate() {
	    return endEntityTemplate;
    }

	public void setEndEntityTemplate(String endEntityTemplate) {
	    this.endEntityTemplate = endEntityTemplate;
    }

	public List<String> getIntermediateTemplates() {
	    return intermediateTemplates;
    }

	public void setIntermediateTemplates(List<String> intermediateTemplates) {
	    this.intermediateTemplates = intermediateTemplates;
    }

	public Boolean getIsParentDomain() {
	    return isParentDomain;
    }

	public void setIsParentDomain(Boolean isParentDomain) {
	    this.isParentDomain = isParentDomain;
    }

}
