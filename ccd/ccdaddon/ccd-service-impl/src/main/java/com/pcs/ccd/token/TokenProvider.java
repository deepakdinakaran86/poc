
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
package com.pcs.ccd.token;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pcs.avocado.token.TokenInfoDTO;
import com.pcs.avocado.token.TokenManager;
import com.pcs.ccd.enums.Integraters;

/**
 * This class is responsible for providing different token for accessing different resources
 * 
 * @author pcseg129(Seena Jyothish)
 * Mar 22, 2016
 */

@Component
public class TokenProvider {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TokenProvider.class);
	
	@Value("${ccd.user.password}")
	private String ccdPassword;
	
	@Value("${ccd.user.name}")
	private String ccdUserName;
	
	@Value("${galaxy.user.name}")
	private String gxyUserName;
	
	@Value("${galaxy.user.password}")
	private String gxyPassword;
	
	@Value("${apiMgrClientId}")
	private String apiMgrConsumerKey;
	
	@Value("${apiMgrClientSecret}")
	private String apiMgrClientSecret;
	
	public TokenProvider(){
		
	}
	
	public Map<String, String> getAuthToken(Integraters integrater){
		switch (integrater) {
			case CCD :
				return getHeader(getCcdToken());
				
			case GALAXY:
				return getHeader(getGalaxyToken());

			default:
				break;
		}
		return null;
	}
	
	private Map<String, String> getHeader(String token) {
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		return httpHeaders;
	}
	
	private String getCcdToken() {
		LOGGER.error("Generating Token apiMgrConsumerKey:{} ,apiMgrClientSecret:{} ",apiMgrConsumerKey,apiMgrClientSecret );
		TokenInfoDTO token = TokenManager.getToken(apiMgrConsumerKey, apiMgrClientSecret, ccdUserName, ccdPassword, "ccduser");
		LOGGER.error("Token : " + token.getAccessToken());
		return token.getAccessToken();
	}
	
	private String getGalaxyToken() {
		LOGGER.error("Generating Token apiMgrConsumerKey:{} ,apiMgrClientSecret:{} ",apiMgrConsumerKey,apiMgrClientSecret );
		TokenInfoDTO token = TokenManager.getToken(apiMgrConsumerKey, apiMgrClientSecret, gxyUserName, gxyPassword, "galaxytoken");
		LOGGER.error("Token : " + token.getAccessToken());
		return token.getAccessToken();
	}
}
