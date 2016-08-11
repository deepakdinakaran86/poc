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
package com.pcs.fms.web.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.pcs.fms.web.client.FMSResponse;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.EntityDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;
import com.pcs.fms.web.manager.dto.UserPermissionsDTO;
import com.pcs.fms.web.model.UserCredentials;
import com.pcs.galaxy.rest.client.BaseClient;

/**
 * @author PCSEG191 Daniela
 * @date JULY 2016
 * @since FMS1.0.0
 * 
 */
@Service
public class AuthService extends BaseService {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(AuthService.class);

	@Value("${apimanager.clientid}")
	private String apimClientId;

	@Value("${apimanager.clientSecret}")
	private String apimClientSecret;

	@Value("${fms.auth.services.apim}")
	private String apimAuthURL;

	@Value("${fms.platform.services.apim}")
	private String apimLogoutURL;

	@Value("${fms.services.user.current}")
	private String apimUserCurrentURL;

	@Value("${fms.services.tenant.domaininfo}")
	private String apimDomainInfoURL;

	private static final String AUTHORIZATION = "Authorization";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_VALUE = "application/x-www-form-urlencoded";

	/**
	 * Method to get all tags of an entity
	 * 
	 * @param
	 * @return
	 */
	public Token authenticate(HttpSession httpSession) {

		FMSTokenHolder.setBearer(httpSession.getId());

		BaseClient client = null;
		client = new BaseClient(apimAuthURL);
		client.intialize();

		Map<String, String> header = new HashMap<String, String>();
		header.put(AUTHORIZATION, getAuthorizationCode());
		header.put(CONTENT_TYPE, CONTENT_VALUE);
		Token token = client.post("token?grant_type=client_credentials",
		        header, null, Token.class);
		TokenManager.setToken(token, httpSession.getId());
		return token;
	}

	/**
	 * Method to get all tags of an entity
	 * 
	 * @param
	 * @return
	 */
	public Token authenticate(UserCredentials credentials,
	        HttpSession httpSession) {

		FMSTokenHolder.setBearer(httpSession.getId());

		BaseClient client = null;
		client = new BaseClient(apimAuthURL);
		client.intialize();

		Map<String, String> header = new HashMap<String, String>();
		header.put(AUTHORIZATION, getAuthorizationCode());
		header.put(CONTENT_TYPE, CONTENT_VALUE);
		Token token = client.post(
		        "token?grant_type=password&username="
		                + credentials.getUserName() + "&password="
		                + credentials.getPassword(), header, null, Token.class);
		TokenManager.setToken(token, httpSession.getId());
		return token;
	}

	/**
	 * Method to get all tags of an entity
	 * 
	 * @param
	 * @return
	 */
	public UserPermissionsDTO getUserInfo() {
		String findDeviceDetailsServiceURI = getServiceURI(apimUserCurrentURL);

		FMSResponse<UserPermissionsDTO> user = getPlatformClient().getResource(
		        findDeviceDetailsServiceURI, UserPermissionsDTO.class);

		return user.getEntity();
	}

	/**
	 * Method to get all tags of an entity
	 * 
	 * @param
	 * @return
	 */
	public EntityDTO getTenantInfo() {
		String findDeviceDetailsServiceURI = getServiceURI(apimDomainInfoURL);

		FMSResponse<EntityDTO> domainEntityDTO = getPlatformClient()
		        .getResource(findDeviceDetailsServiceURI, EntityDTO.class);
		return domainEntityDTO.getEntity();
	}

	private String getAuthorizationCode() {
		String authToken = apimClientId + ":" + apimClientSecret;
		BASE64Encoder base64Encoder = new BASE64Encoder();
		authToken = "Basic "
		        + base64Encoder.encode(authToken.getBytes()).trim();
		return authToken;
	}

	/**
	 * Method to get all tags of an entity
	 * 
	 * @param
	 * @return
	 */
	public void logout(String token) {

		BaseClient client = new BaseClient(apimLogoutURL);
		client.intialize();

		Map<String, String> header = new HashMap<String, String>();
		header.put(AUTHORIZATION, getAuthorizationCode());
		header.put(CONTENT_TYPE, CONTENT_VALUE);

		client.post("revoke?token=" + token, header, null, null);

		System.out.println("success");
	}

}
