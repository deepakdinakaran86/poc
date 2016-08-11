/**
 * 
 */
package com.pcs.fms.web.model;

import java.io.Serializable;
import java.util.List;

import com.pcs.fms.web.dto.EntityTemplateDTO;
import com.pcs.fms.web.dto.IdentityDTO;
import com.pcs.fms.web.dto.Tag;

/**
 * @author PCSEG362
 * 
 */
public class MapTagToTemplateModel implements Serializable {

	private static final long serialVersionUID = 2375352466454520359L;

	private Tag tag;

	private List<EntityTemplateDTO> templates;

	public List<EntityTemplateDTO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<EntityTemplateDTO> templates) {
		this.templates = templates;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
