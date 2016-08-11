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

package com.pcs.avocado.enums;

/**
 * GalaxyCoreStatus
 * 
 * @description status and the status codes
 * @author Daniela (pcseg191)
 * @date 16 July 2014
 * @since galaxy-1.0.0
 */

public enum GalaxyCoreStatus {
	
	FALSE (0),
	TRUE  (1),
	SUCCESS (1),
	FAILURE (0);
	
	private Integer statusCode;
	
	private GalaxyCoreStatus(Integer statusCode){
		this.statusCode = statusCode;
	}

	public Integer getstatusCode() {
		return statusCode;
	}
}
