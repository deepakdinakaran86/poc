/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.alpine.dto;

import java.io.Serializable;

import com.pcs.alpine.commons.dto.EntityDTO;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author Riyas (PCSEG296)
 * @date 12 June 2016
 * @since alpine-1.0.0
 */

public class DocumentDTO implements Serializable {

	private static final long serialVersionUID = -775482194445563861L;

	private EntityDTO document;
	private EntityDTO documentType;

	public EntityDTO getDocument() {
		return document;
	}

	public void setDocument(EntityDTO document) {
		this.document = document;
	}

	public EntityDTO getDocumentType() {
		return documentType;
	}

	public void setDocumentType(EntityDTO documentType) {
		this.documentType = documentType;
	}

}
