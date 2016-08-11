
/**
* Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights Reserved.
*
* This software is the property of Pacific Controls  Software  Services LLC  and  its
* suppliers. The intellectual and technical concepts contained herein are proprietary 
* to PCSS. Dissemination of this information or  reproduction  of  this  material  is
* strictly forbidden  unless  prior  written  permission  is  obtained  from  Pacific 
* Controls Software Services.
* 
* PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY  OF  THE  SOFTWARE,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE  IMPLIED  WARRANTIES  OF
* MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE,  OR  NON-INFRINGMENT.  PCSS SHALL
* NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF  USING,  MODIFYING
* OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
*/
package com.pcs.fault.monitor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 30, 2016
 */
public class MarkerObject implements Serializable{

    private static final long serialVersionUID = 7938711603086538964L;
	
    private List<KeyValueObject> fieldValues;
	private EntityTemplateObj entityTemplate;
	private DomainObj domain;
	
	public List<KeyValueObject> getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(List<KeyValueObject> fieldValues) {
		this.fieldValues = fieldValues;
	}
	public EntityTemplateObj getEntityTemplate() {
		return entityTemplate;
	}
	public void setEntityTemplate(EntityTemplateObj entityTemplate) {
		this.entityTemplate = entityTemplate;
	}
	public DomainObj getDomain() {
		return domain;
	}
	public void setDomain(DomainObj domain) {
		this.domain = domain;
	}
	
}
