/**
 * 
 */
package com.pcs.avocado.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author PCSEG296 RIYAS PH
 * @date November 2015
 * @since Alpine-1.0.0
 */
public class TemplateAssign implements Serializable {

	private static final long serialVersionUID = 328662116391405895L;
	String templateName;
	private List<String> sourceIds;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<String> getSourceIds() {
		return sourceIds;
	}

	public void setSourceIds(List<String> sourceIds) {
		this.sourceIds = sourceIds;
	}

}
