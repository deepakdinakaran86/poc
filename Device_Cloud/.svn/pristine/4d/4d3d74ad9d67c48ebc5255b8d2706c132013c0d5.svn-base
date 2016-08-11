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

import io.netty.handler.codec.http.HttpMethod;

import java.util.Map;

import org.apache.http.MethodNotSupportedException;

import com.pcs.device.gateway.commons.http.netty.HttpClient;
import com.pcs.device.gateway.commons.http.netty.payload.ClientRequest;
import com.pcs.device.gateway.commons.http.netty.payload.ClientResponse;
import com.pcs.device.gateway.commons.http.netty.payload.JsonResponse;

/**
 *  Rest client based on netty.
 *
 * @author pcseg310
 *
 */
public final class NettyRestClient implements Client {

	private RequestInfo host;

	private NettyRestClient(RequestInfo host) {
		this.setHost(host);
	}


	public static final ClientBuilder builder() throws MethodNotSupportedException {
		throw new MethodNotSupportedException("Implementation in progress");
		//return new NettyRestClientBuilder();
	}

	@Override
	public <T> T get(String pathUrl, Map<String, String> httpHeaders,
	        Class<T> responseClass) throws ClientException {

		ClientRequest<Object> request = buildClientRequest(Object.class, pathUrl, HttpMethod.GET);
		ClientResponse<T> response = new JsonResponse<T>(responseClass);

		T resp = execute(request, response);

		return resp;
	}

	private <T> T execute(ClientRequest<Object> request,
			ClientResponse<T> response) throws ClientException {
		try {
			HttpClient.execute(request, response);
		} catch (Exception e) {
			throw new ClientException(e);
		}

		T resp = null;
		while (!response.readyState()) {
			resp = response.getResponse();
			break;
		}
		return resp;
	}

	private <T> ClientRequest<T> buildClientRequest(Class<T> t, String pathUrl, HttpMethod method) {
		ClientRequest<T> request = new ClientRequest<T>();
		request.setHost(this.getHost().getHostIp());
		request.setPath(pathUrl);
		request.setHttpMethod(method);
		request.setScheme(this.getHost().getScheme());
		request.setPort(this.getHost().getPort());
		return request;
	}

	private RequestInfo getHost() {
		return host;
	}

	private void setHost(RequestInfo host) {
		this.host = host;
	}

	public static class NettyRestClientBuilder implements ClientBuilder {
		private final RequestInfo host;

		public NettyRestClientBuilder() {
			host = new RequestInfo();
		}

		@Override
        public NettyRestClientBuilder host(String hostIp) {
			host.setHostIp(hostIp);
			return this;
		}

		@Override
        public NettyRestClientBuilder port(Integer port) {
			host.setPort(port);
			return this;
		}

		@Override
        public NettyRestClientBuilder scheme(String scheme) {
			host.setScheme(scheme);
			return this;
		}

		@Override
        public Client build() {
			return new NettyRestClient(host);
		}
	}

	public static void main(String...strings) {
		/*Client rc = NettyRestClient.builder().host("10.234.60.30")
											.port(9763)
											.scheme("http")
											.build();
		JsonNode node = null;
		long startTime = System.nanoTime();
		try {
			node = rc.get("galaxy-registration-api-1.0.0/services/registration/countries", JsonNode.class);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		System.out.println(node.toString());
		System.out.print("Netty client::Total time elapsed:" + (endTime - startTime));*/
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.device.gateway.commons.http.Client#post(java.lang.String,
	 * java.util.Map, java.lang.Object, java.lang.Class)
	 */
	@Override
	public <T, R> R post(String pathURL, Map<String, String> httpHeaders,
	        T payload, Class<R> responseClazz) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.device.gateway.commons.http.Client#put(java.lang.String,
	 * java.util.Map, java.lang.Object, java.lang.Class)
	 */
	@Override
	public <T, R> R put(String pathURL, Map<String, String> httpHeaders,
	        T payload, Class<R> responseClazz) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.pcs.device.gateway.commons.http.Client#delete(java.lang.String,
	 * java.util.Map, java.lang.Class)
	 */
	@Override
	public <T> T delete(String pathUrl, Map<String, String> httpHeaders,
	        Class<T> responseClass) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

}
