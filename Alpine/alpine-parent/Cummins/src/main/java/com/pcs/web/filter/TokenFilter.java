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
package com.pcs.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.alpine.token.Token;
import com.pcs.alpine.token.TokenManager;
import com.pcs.web.client.TokenHolder;
import com.pcs.web.dto.ErrorDTO;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg296
 */
public class TokenFilter implements Filter {

	private static final Logger logger = LoggerFactory
	        .getLogger(TokenFilter.class);

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
	        FilterChain chain) throws IOException, ServletException {
		/*
		 * Check if the session id has a valid token.
		 */
		try {

			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			if (httpServletRequest.getRequestURI().equals("/Cummins/login")
			        || httpServletRequest.getRequestURI().equals(
			                "/Cummins/authenticate")
			        || httpServletRequest.getRequestURI().equals("/Cummins/")
			        || httpServletRequest.getRequestURI().startsWith(
			                "/Cummins/resources")
			        || httpServletRequest.getRequestURI().startsWith(
			                "/Cummins/webjars")) {
				chain.doFilter(request, response);
			} else {
				Token token = TokenManager.getToken(httpServletRequest
				        .getSession().getId());
				if (token == null) {
					logger.error("UnAuthorized transaction attempt !!!! Redirecting to safety..Google");
					ErrorDTO error = new ErrorDTO();
					error.setErrorMessage("Please Login first.");
					request.setAttribute("error", error);
					request.getRequestDispatcher("login").forward(request,
					        response);
				} else {
					TokenHolder.setBearer(httpServletRequest.getSession()
					        .getId());
					chain.doFilter(request, response);
				}
			}
		} catch (Exception e) {
			logger.error("Error during request chaining", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
