package com.pcs.alpine.services.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author pcseg369
 *
 */
public class TemplateListDTO {

	private List<Template> templates = new ArrayList<Template>();

	public TemplateListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TemplateListDTO(List<Template> templates) {
		super();
		this.templates = templates;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	
}