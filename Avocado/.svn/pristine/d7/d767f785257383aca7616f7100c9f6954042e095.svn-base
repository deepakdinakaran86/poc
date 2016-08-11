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
package com.pcs.avocado.commons.dto;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since avocado-1.0.0
 */
public class Subscription implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8111187766702590858L;

	@SerializedName("subscriberName")
	private String subscriberName;

	@SerializedName("subscriberDomain")
	private String subscriberDomain;

	@SerializedName("endUserName")
	private String endUserName;

	@SerializedName("endUserDomain")
	private String endUserDomain;

	@SerializedName("subscriberApp")
	private String subscriberApp;
	
	@SerializedName("x-jwt-assertion")
	private Map<String,String> jwtToken;
	
	@SerializedName("isSuperAdmin")
	private boolean isSuperAdmin;
	
	@SerializedName("tenantId")
	private String tenantId;
	
	@SerializedName("tenantName")
	private String tenantName;

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getSubscriberDomain() {
		return subscriberDomain;
	}

	public void setSubscriberDomain(String subscriberDomain) {
		this.subscriberDomain = subscriberDomain;
	}

	public String getEndUserName() {
		return endUserName;
	}

	public void setEndUserName(String endUserName) {
		this.endUserName = endUserName;
	}

	public String getEndUserDomain() {
		return endUserDomain;
	}

	public void setEndUserDomain(String endUserDomain) {
		this.endUserDomain = endUserDomain;
	}

	public String getSubscriberApp() {
		return subscriberApp;
	}

	public void setSubscriberApp(String subscriberApp) {
		this.subscriberApp = subscriberApp;
	}

	public Map<String,String> getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(Map<String,String> jwtToken) {
		this.jwtToken = jwtToken;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getTenantId() {
	    return tenantId;
    }

	public void setTenantId(String tenantId) {
	    this.tenantId = tenantId;
    }

	public String getTenantName() {
	    return tenantName;
    }

	public void setTenantName(String tenantName) {
	    this.tenantName = tenantName;
    }
	
}
