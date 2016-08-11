
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
package com.pcs.datasource.dto;

import java.io.Serializable;

/**
 * This class is responsible for defining properties for Device authentication response
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 10 Oct 2015
 */
public class DeviceAuthentication implements Serializable{

    private static final long serialVersionUID = -6883251638141897090L;
    
    private GeneralResponse generalResponse;
    
    private Device device;

	public GeneralResponse getGeneralResponse() {
		return generalResponse;
	}

	public void setGeneralResponse(GeneralResponse generalResponse) {
		this.generalResponse = generalResponse;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
    
}
