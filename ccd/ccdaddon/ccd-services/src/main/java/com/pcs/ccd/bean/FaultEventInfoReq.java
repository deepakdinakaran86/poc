
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
package com.pcs.ccd.bean;

import java.io.Serializable;
import java.util.List;

import com.pcs.avocado.commons.dto.FieldMapDTO;
import com.pcs.avocado.commons.dto.StatusDTO;

/**
 * This class is responsible for holding fault event related information
 * 
 * @author pcseg129(Seena Jyothish)
 * Feb 1, 2016
 */
public class FaultEventInfoReq implements Serializable{
	
    private static final long serialVersionUID = -4866989344754295894L;
	
    private List<FieldMapDTO> keyValues;
    
    private StatusDTO entityStatus;
    
    private FieldMapDTO identifier;

	public List<FieldMapDTO> getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(List<FieldMapDTO> keyValues) {
		this.keyValues = keyValues;
	}

	public StatusDTO getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(StatusDTO entityStatus) {
		this.entityStatus = entityStatus;
	}

	public FieldMapDTO getIdentifier() {
		return identifier;
	}

	public void setIdentifier(FieldMapDTO identifier) {
		this.identifier = identifier;
	}
	

}
