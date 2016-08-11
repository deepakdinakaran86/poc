package com.pcs.alpine.commons.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HierarchyTagCountDTO {

	private String tag;

	private Integer count;

	public String getTag() {
	    return tag;
    }

	public void setTag(String tag) {
	    this.tag = tag;
    }

	public Integer getCount() {
	    return count;
    }

	public void setCount(Integer count) {
	    this.count = count;
    }

}
