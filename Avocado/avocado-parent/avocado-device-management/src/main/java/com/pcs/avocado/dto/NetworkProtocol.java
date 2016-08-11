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
package com.pcs.avocado.dto;

import java.io.Serializable;

/**
 * 
 * @author PCSEG296 RIYAS PH
 * @date November 2015
 * @since Alpine-1.0.0
 */
public class NetworkProtocol implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8454930049123575178L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
