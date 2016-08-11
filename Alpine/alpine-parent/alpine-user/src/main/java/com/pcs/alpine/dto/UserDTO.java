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
package com.pcs.alpine.dto;


/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author Daniela (pcseg191)
 * @date November 2015
 * @since alpine-1.0.0
 */
public class UserDTO {
	
	private String userName;
	
	private String password;
	
	private String domain;
	
	private Boolean isParentDomain;
	
	private String emailId;
	
	private String newPassword;
	
	private String emailLink;
	
	private ESBUserDTO esbUserDTO;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Boolean getIsParentDomain() {
		return isParentDomain;
	}
	public void setIsParentDomain(Boolean isParentDomain) {
		this.isParentDomain = isParentDomain;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getEmailLink() {
		return emailLink;
	}
	public void setEmailLink(String emailLink) {
		this.emailLink = emailLink;
	}
	public ESBUserDTO getEsbUserDTO() {
		return esbUserDTO;
	}
	public void setEsbUserDTO(ESBUserDTO esbUserDTO) {
		this.esbUserDTO = esbUserDTO;
	}

}
