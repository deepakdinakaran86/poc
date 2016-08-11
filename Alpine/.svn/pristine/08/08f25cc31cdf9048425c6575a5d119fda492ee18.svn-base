/**
 *
 */
package com.pcs.alpine.services.enums;

/**
 * Hierarchy Node Types enum supported by Hierarchy services
 * 
 * @author Twinkle (PCSEG297)
 * @date May 2016
 * @since alpine-1.0.0
 */
public enum HierarchyNodeTypes {

	ENTITY, TEMPLATE;

	public static HierarchyNodeTypes getEnum(String type) {
		for (HierarchyNodeTypes validType : values()) {
			if (validType.name().equalsIgnoreCase(type)) {
				return validType;
			}
		}
		throw new IllegalArgumentException();
	}

}
