package com.pcs.alpine.services.dto;

import javax.xml.bind.annotation.XmlRootElement;

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
