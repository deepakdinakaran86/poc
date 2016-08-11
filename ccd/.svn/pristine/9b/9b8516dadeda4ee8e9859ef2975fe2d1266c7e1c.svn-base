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
package com.pcs.fault.monitor.enums;

/**
 * This class is responsible for mentioning all possible entity status
 * 
 * @author pcseg129(Seena Jyothish) Feb 2, 2016
 */
public enum EntityStatus {

	ALLOCATED(2, "allocated"),
	ACTIVE(1, "active"),
	NEW_ENTITY(3,"new entity"),
	EXISTING_ENTITY(4, "existing entity"),
	DUPLICATE_ENTITY(4, "duplicate entity");

	private int status;
	private String desc;

	private static final EntityStatus[] values = values();

	EntityStatus(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static EntityStatus valueOfType(int status) {
		for (EntityStatus entityStatus : values) {
			if (status == entityStatus.getStatus()) {
				return entityStatus;
			}
		}
		return null;
	}

}
