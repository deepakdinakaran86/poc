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
package com.pcs.fms.web.filter;

import static com.pcs.fms.web.constants.FMSWebConstants.MYOWN_HOME_PATH_NAME;

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

import com.pcs.fms.web.client.FMSAccessManager;
import com.pcs.fms.web.client.FMSTokenHolder;
import com.pcs.fms.web.dto.ErrorDTO;
import com.pcs.fms.web.manager.TokenManager;
import com.pcs.fms.web.manager.dto.Token;

/**
 * @author PCSEG296 RIYAS PH
 * @date JUNE 2016
 * @since FMS1.0.0
 * 
 */
public class FMSTokenFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(FMSTokenFilter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/*
		 * Check if the session id has a valid token.
		 */
		try {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if (httpServletRequest.getRequestURI().equals("/FMS/authenticate")
					|| httpServletRequest.getRequestURI().startsWith(
							"/FMS/resources")
					|| httpServletRequest.getRequestURI().startsWith(
							"/FMS/webjars")
					|| httpServletRequest.getRequestURI().startsWith(
							"/FMS/set_password")
					|| httpServletRequest.getRequestURI().startsWith(
							"/FMS/setpwd")
					|| httpServletRequest.getRequestURI().startsWith(
							"/FMS/clientadmin")
					|| httpServletRequest.getRequestURI().startsWith(
							"/FMS/set_user_password")
					|| httpServletRequest.getRequestURI().startsWith(
							"/FMS/forgot_password")) {
				chain.doFilter(request, response);
			} else if (httpServletRequest.getRequestURI().equals("/FMS/")
					|| httpServletRequest.getRequestURI().equals("/FMS")) {
				Token token = TokenManager.getToken(httpServletRequest
						.getSession().getId());
				if (token == null) {
					request.getRequestDispatcher("login").forward(request,
							response);

				} else {
					request.getRequestDispatcher("home").forward(request,
							response);
				}
			} else {
				Token token = TokenManager.getToken(httpServletRequest
						.getSession().getId());
				if (token == null) {
					logger.error("UnAuthorized transaction attempt !!!! Redirecting to safety..Google");
					ErrorDTO error = new ErrorDTO();
					if (!httpServletRequest.getRequestURI()
							.equals("/FMS/login")) {
						error.setErrorMessage("Please Login first.");
						request.setAttribute("error", error);
					}
					request.getRequestDispatcher("login").forward(request,
							response);
				} else {
					if (!FMSAccessManager.hasUrlAccess(httpServletRequest
							.getRequestURI())) {
						request.getRequestDispatcher(MYOWN_HOME_PATH_NAME)
								.forward(request, response);
					}
					FMSTokenHolder.setBearer(httpServletRequest.getSession()
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
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
