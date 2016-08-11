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
package com.pcs.alpine.enums;

/**
 * UserProfileDataFields
 * 
 * @description This class defines enums being used for attributes used in UserProfile.
 * @author Twinkle (pcseg297)
 * @date April 2015
 * @since galaxy-1.0.0
 */
public enum UserProfileDataFields {

	ACCESS_TOKEN("accessToken"),
	REFRESH_TOKEN("refreshToken"),
	CLIENT_ID("clientId"),
	CLIENT_SECRET("clientSecret"),
	GRANT_TYPE("grantType"),
	SCOPE("scope"),
	PASSWORD("password"),
	USER_NAME("userName"),
	ROLE("role"),
	EMAIL("email"),
	NAME("name"),
	DOMAIN_NAME("domain_name"),
	PREFERRED_USER_NAME("preferredUsername"),
	ROLES("roles"),
	LAST_ACCESSED_TIME("lastAccessedTime"),
	USER_TYPE("userType"),
	GLOBAL_ENTITY_TYPE("globalEntityType"),
	IS_SUPER_TENANT("isSuperTenant"),
	FAMILY_NAME("familyName")
	;

	private String fieldName;

	private UserProfileDataFields(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return this.fieldName;
	}

	public String getName() {
		return fieldName;
	}

}
