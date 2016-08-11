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
package com.pcs.web.controller;

import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.web.client.DataSourceResponse;
import com.pcs.web.services.GenericService;

/**
 * Ajax Controller handles all the ajax calls acts as a proxy to the ESB. <br>
 * ESB endpoint to be invoked is given as the url following the ajax resource
 * path.<br>
 * For eg., <br>
 * {@code /ajax/a/b/c} invokes {@code $ESBBaseURI/a/b/c} where
 * {@code $ESBBaseURI} is the platform base uri defined in
 * platform-api.properties
 * 
 * @author PCSEG296 RIYAS PH
 * @date October 2015
 * @since Alpine-1.0.0
 * 
 */
@RestController
@RequestMapping("ajax/**")
public class AjaxController extends BaseController {

	@Autowired
	private GenericService genericService;

	/**
	 * response to ajax requests according to the http methods
	 * 
	 * @param payload
	 * @param httpRequest
	 * @param httpResponse
	 * @return GalaxyPlatformResponse<JsonNode>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = {
	        GET, POST, PUT, DELETE})
	@ResponseBody
	public DataSourceResponse<JsonNode> invoke(
	        @RequestBody(required = false) Object payload,
	        HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

		HttpMethod httpMethod = HttpMethod.valueOf(httpRequest.getMethod());
		String platformUrl = buildPlatformUrl(httpRequest.getParameterMap(),
		        httpRequest.getRequestURI());
		DataSourceResponse<JsonNode> response = null;

		switch (httpMethod) {
			case DELETE :
				response = genericService.deleteObject(platformUrl);
				break;
			case GET :
				response = genericService.getObject(platformUrl);
				break;
			case POST :
				response = genericService.postObject(platformUrl, payload);
				break;
			case PUT :
				response = genericService.putObject(platformUrl, payload);
				break;
			default:
				LOGGER.warn("{} is not supported", httpMethod);
				break;
		}
		httpResponse.setStatus(response.getStatus().value());
		return response;
	}

	/**
	 * Method to build platformurl wchich support both queryparam and pathparm
	 * 
	 * @param requestParameterMap
	 * @param requestURI
	 * @return String platformUrl
	 * 
	 * @author pcseg323 Greeshma
	 */
	private String buildPlatformUrl(Map<String, Object[]> requestParameterMap,
	        String requestURI) {
		String platformUrl = substringAfter(requestURI, "ajax");
		Map<String, Object[]> parameterMap = requestParameterMap;
		if (MapUtils.isNotEmpty(parameterMap)) {
			UriComponentsBuilder builder = UriComponentsBuilder
			        .fromUriString(platformUrl);
			for (String key : parameterMap.keySet()) {
				builder.queryParam(key, parameterMap.get(key));
			}
			platformUrl = builder.build().toUriString();
		}
		return platformUrl;

	}
}
