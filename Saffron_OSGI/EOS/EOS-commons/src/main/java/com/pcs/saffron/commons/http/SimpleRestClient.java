
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
package com.pcs.saffron.commons.http;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.saffron.commons.apache.ApacheHTTPClient;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * Jan 23, 2016
 */
public class SimpleRestClient {
	
	private static final long serialVersionUID = 5640506519742534581L;
	
	private static final ObjectMapper mapper = new ObjectMapper();

	private final ApacheHTTPClient httpClient = ApacheHTTPClient.getInstance();

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRestClient.class);

	public static RequestInfo requestInfo = new RequestInfo();

	public SimpleRestClient(){
		
	}
	protected SimpleRestClient(RequestInfo host) {
		this.setRequestInfo(host);
	}

	private RequestInfo getRequestInfo() {
		return requestInfo;
	}

	private void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}


	/**
	 * Method responsible for post requests
	 *
	 * @param url
	 * @param httpHeaders
	 * @param payload
	 * @param responseClazz
	 * @return
	 */
	public <T, R> String post(String pathURL, Map<String, String> httpHeaders, T payload) throws ClientException {
		URI uri = extractURI(pathURL);
		HttpPost post = new HttpPost(uri);
		post.setEntity(marshall(payload));
		return handleResponse(httpClient.execute(post, httpHeaders));
	}

	/**
	 * Private method for converting the response to the specified class object
	 *
	 * @param httpResponse
	 * @param responseClazz
	 * @return
	 */
	private String handleResponse(HttpResponse httpResponse) {
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		try {
			if ((statusCode >= 200 && statusCode <= 300 && statusCode != 204)) {
			}
			EntityUtils.consume(httpResponse.getEntity());
		} catch (Exception e) {
			LOGGER.error("Error in ApacheHTTPClient handleResponse", e);
		}
		LOGGER.debug("statusCode in ApacheHTTPClient", statusCode);
		return String.valueOf(statusCode);
	}

	/**
	 * @param beanObject
	 * @return
	 */
	private <T> BasicHttpEntity marshall(final T beanObject) {
		BasicHttpEntity entity = new BasicHttpEntity();
		String value = null;
		try {
			value = mapper.writeValueAsString(beanObject);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error in processing the payload in",e);
		}
		try {
			entity.setContent(new ByteArrayInputStream(value.getBytes("UTF-8")));
			entity.setContentType("application/json");
		} catch (UnsupportedEncodingException e) {
		}
		return entity;

	}

	private URI extractURI(String pathURL) throws ClientException {
		URI uri = null;
		if (requestInfo != null && requestInfo.getHostIp() != null) {
			uri = this.getRequestInfo().buildUri(pathURL);
		} else {
			try {
				uri = new URI(pathURL);
			} catch (URISyntaxException e) {
				throw new ClientException("Error in URI", e);
			}
		}
		return uri;
	}
}

	

