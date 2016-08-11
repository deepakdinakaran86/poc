package com.pcs.device.web.client.security;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * Custom HTTP Client Request factory to help injecting {@link HostnameVerifier}
 * 
 * @author pcseg296 RIYAS PH
 * @date 28 July 2015
 * @since galaxy-1.0.0
 */
public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {

	private HostnameVerifier verifier;

	public HttpsClientRequestFactory(HostnameVerifier verifier) {
		this.verifier = verifier;
	}

	@Override
	protected void prepareConnection(HttpURLConnection connection,
	        String httpMethod) throws IOException {

		if (connection instanceof HttpsURLConnection) {
			((HttpsURLConnection)connection).setHostnameVerifier(verifier);
		}

		super.prepareConnection(connection, httpMethod);
	}
}
