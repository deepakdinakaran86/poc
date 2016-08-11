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
package com.pcs.ccd.servlets;

import static com.pcs.ccd.util.AppConfig.getRegConfig;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.avocado.token.TokenInfoDTO;
import com.pcs.avocado.token.TokenManager;
import com.pcs.ccd.beans.FaultResponse;
import com.pcs.ccd.util.AppConfig;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;

/**
 * This class is responsible for receiving requests from third parties
 * 
 * @author pcseg129(Seena Jyothish) Apr 14, 2016
 */
public class ReceiveFaultEventResponse extends HttpServlet {

    private static final long serialVersionUID = -8149957564668000918L;

	private static final Logger logger = LoggerFactory
	        .getLogger(ReceiveFaultEventResponse.class);
	
	public ReceiveFaultEventResponse(){
		super();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		AppConfig.init();
		logger.info("Started...");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) { 
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			FaultResponse faultResponse = mapper.readValue(request.getReader(),
			        FaultResponse.class);
			Client httpClient = buildRestClient();
			httpClient.post(getRegConfig().getCcdresponseUrl()
			        , setHeader(), faultResponse,
			        FaultResponse.class);
		} catch (Exception e) {
			logger.error("Error in sending fault response to CCD api", e);
		}
	}
	
	private static ApacheRestClient buildRestClient() {
		ApacheRestClient httpClient;
		httpClient = (ApacheRestClient)ApacheRestClient.builder()
		        .host(getRegConfig().getHost())
		        .port(Integer.parseInt(getRegConfig().getPort()))
		        .scheme(getRegConfig().getScheme()).build();
		return httpClient;
	}
	
	public static Map<String, String> setHeader() {
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put(HttpHeaders.AUTHORIZATION, "Bearer " + getToken());
		return httpHeaders;
	}
	
	private static String getToken() {
		logger.info("Client id : " + getRegConfig().getApiMgrClientId());
		logger.info("Client secret : "
		        + getRegConfig().getApiMgrClientSecret());

		TokenInfoDTO token = TokenManager.getToken(getRegConfig()
		        .getApiMgrClientId(), getRegConfig()
		        .getApiMgrClientSecret(), getRegConfig().getCcdUser(),
		        getRegConfig().getCcdUserPwd(), "ccd-servlet");

		return token.getAccessToken();
	}

}
