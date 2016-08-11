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
package com.pcs.fms.web.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
@JsonSerialize
public class Subscription implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String prodKey;
	private String prodConsumerKey;
	private String prodConsumerSecret;
	private Long prodValidityTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProdKey() {
		return prodKey;
	}

	public void setProdKey(String prodKey) {
		this.prodKey = prodKey;
	}

	public String getProdConsumerKey() {
		return prodConsumerKey;
	}

	public void setProdConsumerKey(String prodConsumerKey) {
		this.prodConsumerKey = prodConsumerKey;
	}

	public String getProdConsumerSecret() {
		return prodConsumerSecret;
	}

	public void setProdConsumerSecret(String prodConsumerSecret) {
		this.prodConsumerSecret = prodConsumerSecret;
	}

	public Long getProdValidityTime() {
		return prodValidityTime;
	}

	public void setProdValidityTime(Long prodValidityTime) {
		this.prodValidityTime = prodValidityTime;
	}

}
