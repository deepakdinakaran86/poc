package com.pcs.device.gateway.commons.http.netty.payload;

import io.netty.handler.codec.http.HttpMethod;

import java.net.URI;
import java.net.URISyntaxException;

import com.pcs.device.gateway.commons.http.netty.HttpClient;

/**
 * Client request wrapper for {@link HttpClient}}
 * 
 * @author pcseg310
 *
 * @param <T>
 */
public class ClientRequest<T> {

	private T payload;
	private HttpMethod httpMethod;
	private String host;
	private Integer port;
	private String path;
	private String scheme;
	
	public void setPayload(T payloadType) {
		this.payload = payloadType;
	}

	public T getPayload() {
		return payload;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public URI buildUri() {
		StringBuilder url = new StringBuilder();
		url.append(this.scheme)
			.append("://")
			.append(this.host)
			.append(":")
			.append(this.port)
			.append("/")
			.append(this.path);
		URI uri = null;
		try {
			uri = new URI(url.toString());
		} catch (URISyntaxException e) {
			// TODO 
		}
		return uri;
	}

}