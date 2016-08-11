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
package com.pcs.datasource.enums;

/**
 * AggregationFunctionConstants
 * 
 * @description holds different Aggregation function constants
 * 
 * @author PCSEG297(Twinkle)
 * @date April 2016
 */
public enum AggregationFunctionConstants {

	MAX("max"), MIN("min"), AVG("avg"), SUM("sum"), COUNT("count"), RANGE("range"), ALL("all");
	
	private String funcName;
	
	private AggregationFunctionConstants(String funcName){
		this.funcName = funcName;
	}
	
	public static Boolean hasEnum(String name) {
		Boolean flag = false;
		for (AggregationFunctionConstants v : values()) {
			if (v.name().equalsIgnoreCase(name)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public String getFuncName() {
	    return funcName;
    }

	public void setFuncName(String funcName) {
	    this.funcName = funcName;
    }
}
