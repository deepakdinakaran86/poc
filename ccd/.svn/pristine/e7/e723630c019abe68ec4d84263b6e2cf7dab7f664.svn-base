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
package com.pcs.ccd.heartbeat.token;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.avocado.token.TokenInfoDTO;
import com.pcs.avocado.token.TokenManager;
import com.pcs.ccd.heartbeat.enums.Integraters;
import static com.pcs.ccd.heartbeat.utils.HeartbeatUtil.getServicesConfig;

/**
 * This class is responsible for providing different token for accessing
 * different resources
 * 
 * @author pcseg129(Seena Jyothish) Mar 22, 2016
 */

public class TokenProvider {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TokenProvider.class);

	public TokenProvider() {

	}

	public static Map<String, String> getAuthToken(Integraters integrater) {
		switch (integrater) {
			case CCD :
				return getHeader(getCcdToken());

			case GALAXY :
				return getHeader(getGalaxyToken());

			default:
				break;
		}
		return null;
	}

	private static Map<String, String> getHeader(String token) {
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		return httpHeaders;
	}

	private static String getCcdToken() {
		TokenInfoDTO token = TokenManager.getToken(getServicesConfig()
		        .getApiMgrClientId(), getServicesConfig()
		        .getApiMgrClientSecret(), getServicesConfig().getCcdUser(),
		        getServicesConfig().getCcdUserPwd(), "hbccdtoken");
		return token.getAccessToken();
	}

	private static String getGalaxyToken() {
		TokenInfoDTO token = TokenManager.getToken(getServicesConfig().getGxyApiMgrClientId(),
				getServicesConfig().getGxyApiMgrClientSecret(), getServicesConfig().getGxyUser(), getServicesConfig().getGxyUserPwd(), "hbgalaxytoken");
		return token.getAccessToken();
	}
}
