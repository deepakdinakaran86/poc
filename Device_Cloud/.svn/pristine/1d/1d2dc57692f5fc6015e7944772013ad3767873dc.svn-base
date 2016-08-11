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
package com.pcs.data.analyzer.bean;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * reference class for unit in physical quantity
 *
 * @author pcseg323(Greeshma)
 * @date April 2015
 * @since galaxy-1.0.0
 */

public class Unit implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8719664792831195191L;

	private UUID id;

	private String conversion;

	//@SerializedName("is_SI")
	private Boolean isSi;

	private String name;

	private String pName;

	private UUID pUuid;

	private Integer statusKey;

	private String status;
	
	private String desc;
	
	private String symbol;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getConversion() {
		return conversion;
	}

	public void setConversion(String conversion) {
		this.conversion = conversion;
	}

	public Boolean getIsSi() {
		return isSi;
	}

	public void setIsSi(Boolean isSI) {
		this.isSi = isSI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public UUID getpUuid() {
		return pUuid;
	}

	public void setpUuid(UUID pUuid) {
		this.pUuid = pUuid;
	}

	public Integer getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(Integer status) {
		this.statusKey = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String statusName) {
		this.status = statusName;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public int hashCode() {
		int result = ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			Unit unit = (Unit)obj;
			if ((unit.getName().equalsIgnoreCase(this.name))
			        && (StringUtils.isNotEmpty(unit.getName()))) {
				return true;
			} else {
				return false;
			}
		}
	}

}
