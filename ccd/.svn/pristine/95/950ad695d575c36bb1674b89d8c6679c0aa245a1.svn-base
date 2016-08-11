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
 * This class is responsible for maintaining external response receive status
 * 
 * @author pcseg129(Seena Jyothish) 
 * Jan 31, 2016
 */
public enum RespReceiveStatus {
	RECEIVED(1,"received"), NOT_RECEIVED(0,"not received");
	
	private int status;
	private String desc;
	
	private static final RespReceiveStatus[] values = values();
	
	RespReceiveStatus(int status,String desc){
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
	
	public static RespReceiveStatus valueOfType(int status) {
		for (RespReceiveStatus respStatus : values) {
			if (status==respStatus.getStatus()) {
				return respStatus;
			}
		}
		return null;
	}
}
