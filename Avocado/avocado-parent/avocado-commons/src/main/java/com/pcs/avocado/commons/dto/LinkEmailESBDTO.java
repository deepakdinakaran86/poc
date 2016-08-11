package com.pcs.avocado.commons.dto;

import java.io.Serializable;
import java.util.List;

import com.pcs.avocado.commons.dto.DomainDTO;
import com.pcs.avocado.commons.dto.EntityTemplateDTO;
import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.StatusDTO;
import com.pcs.avocado.commons.email.dto.EmailDTO;

/**
 * LinkEmailESBDTO 
 * 
 * 
 * @author Javid Ahammed (PCSEG199)
 * @date January 2016
 * @since galaxy-1.0.0
 */
public class LinkEmailESBDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5825336844098857961L;

	private StatusDTO entityStatus;
	
	private List<FieldMapDTO> fieldValues;
	
	private DomainDTO domain;
	
	private EmailDTO email;

	private EntityTemplateDTO entityTemplate;

	public StatusDTO getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(StatusDTO entityStatus) {
		this.entityStatus = entityStatus;
	}

	public List<FieldMapDTO> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldMapDTO> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public DomainDTO getDomain() {
		return domain;
	}

	public void setDomain(DomainDTO domain) {
		this.domain = domain;
	}

	public EmailDTO getEmail() {
		return email;
	}

	public void setEmail(EmailDTO email) {
		this.email = email;
	}

	public EntityTemplateDTO getEntityTemplate() {
		return entityTemplate;
	}

	public void setEntityTemplate(EntityTemplateDTO entityTemplate) {
		this.entityTemplate = entityTemplate;
	}
	
}
