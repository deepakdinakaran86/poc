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
package com.pcs.device.gateway.commons.http;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.device.gateway.commons.apache.ApacheHTTPClient;

/**
 * Rest client based on apache - httpcomponents.
 *
 * @author pcseg310
 *
 */
public class ApacheRestClient implements Client {

	private static final ObjectMapper mapper = new ObjectMapper();

	private final ApacheHTTPClient httpClient = ApacheHTTPClient.getInstance();

	private static final Logger LOGGER = LoggerFactory.getLogger(ApacheRestClient.class);

	private RequestInfo requestInfo;

	protected ApacheRestClient(RequestInfo host) {
		this.setRequestInfo(host);
	}

	private RequestInfo getRequestInfo() {
		return requestInfo;
	}

	private void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public static final ClientBuilder builder() {
		return new ApacheRestClientBuilder();
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
	@Override
	public <T, R> R post(String pathURL, Map<String, String> httpHeaders, T payload, Class<R> responseClazz)
	                                                                                                throws ClientException {
		URI uri = extractURI(pathURL);
		HttpPost post = new HttpPost(uri);
		post.setEntity(marshall(payload));
		return handleResponse(httpClient.execute(post, httpHeaders), responseClazz);
	}

	/**
	 * Method responsible for put requests
	 *
	 * @param url
	 * @param httpHeaders
	 * @param payload
	 * @param responseClazz
	 * @return
	 */
	@Override
	public <T, R> R put(String pathURL, Map<String, String> httpHeaders, T payload, Class<R> responseClazz)
	                                                                                               throws ClientException {
		URI uri = extractURI(pathURL);
		HttpPut put = new HttpPut(uri);
		put.setEntity(marshall(payload));
		return handleResponse(httpClient.execute(put, httpHeaders), responseClazz);
	}

	/**
	 * Method responsible for delete requests
	 *
	 * @param url
	 * @param httpHeaders
	 * @param responseClazz
	 * @return
	 * @throws Exception
	 */
	@Override
	public <R> R delete(String pathURL, Map<String, String> httpHeaders, Class<R> responseClazz) throws ClientException {
		URI uri = extractURI(pathURL);
		HttpDelete delete = new HttpDelete(uri);
		return handleResponse(httpClient.execute(delete, httpHeaders), responseClazz);

	}

	/**
	 * Method responsible for get requests
	 *
	 * @param url
	 * @param httpHeaders
	 * @param responseClazz
	 * @return
	 * @throws Exception
	 */
	@Override
	public <R> R get(String pathURL, Map<String, String> httpHeaders, Class<R> responseClazz) throws ClientException {
		URI uri = extractURI(pathURL);
		HttpGet get = new HttpGet(uri);
		return handleResponse(httpClient.execute(get, httpHeaders), responseClazz);
	}

	/**
	 * Private method for converting the response to the specified class object
	 *
	 * @param httpResponse
	 * @param responseClazz
	 * @return
	 */
	private <R> R handleResponse(HttpResponse httpResponse, Class<R> responseClazz) {
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		R response = null;
		try {
			if ((statusCode >= 200 && statusCode <= 300 && statusCode != 204)) {
				response = mapper.readValue(httpResponse.getEntity().getContent(), responseClazz);
			}
			EntityUtils.consume(httpResponse.getEntity());
		} catch (Exception e) {
			LOGGER.error("Error in ApacheHTTPClient handleResponse", e);
		}
		LOGGER.debug("Response in ApacheHTTPClient", response);
		return response;
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

class ApacheRestClientBuilder implements ClientBuilder {
	private final RequestInfo host;

	public ApacheRestClientBuilder() {
		host = new RequestInfo();
	}

	@Override
	public ApacheRestClientBuilder host(String hostIp) {
		host.setHostIp(hostIp);
		return this;
	}

	@Override
	public ApacheRestClientBuilder port(Integer port) {
		host.setPort(port);
		return this;
	}

	@Override
	public ApacheRestClientBuilder scheme(String scheme) {
		host.setScheme(scheme);
		return this;
	}

	@Override
	public Client build() {
		return new ApacheRestClient(host);
	}
}