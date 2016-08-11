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
package com.pcs.guava.token;

import java.io.Serializable;
import java.util.UUID;

/**
 * GlobalEntityDTO
 *
 * @description DTO for Status
 * @author Daniela (PCSEG191)
 * @date 25 August 2014
 * @since galaxy-1.0.0
 */
public class StatusDTO implements Serializable {

	private static final long serialVersionUID = -5464747354737214005L;
	private UUID uuid;
	private Integer statusKey;
	private String statusName;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Integer getStatusKey() {
	    return statusKey;
    }

	public void setStatusKey(Integer statusKey) {
	    this.statusKey = statusKey;
    }

	public String getStatusName() {
	    return statusName;
    }

	public void setStatusName(String statusName) {
	    this.statusName = statusName;
    }



}
