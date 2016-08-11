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
package com.pcs.avocado.rest.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.avocado.rest.exception.GalaxyRestErrorCodes;
import com.pcs.avocado.rest.exception.GalaxyRestException;

/**
 * 
 * This class is responsible HTTP Connections
 * 
 * @author Javid Ahammed (PCSEG199)
 * @date Nov 16, 2014
 * @since galaxy-1.0.0
 */

public class ConnectionManager {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(ConnectionManager.class);
	private HttpClient httpClient = null;
	private String serviceEndpoint = null;

	public ConnectionManager(String serviceEndpoint) {
		try {
			this.serviceEndpoint = serviceEndpoint;
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
			        .getSocketFactory()));
			schemeRegistry.register(new Scheme("https", 443,
			        buildSSLSocketFactory()));
			PoolingClientConnectionManager cm = new PoolingClientConnectionManager(
			        schemeRegistry);
			cm.setMaxTotal(400);
			cm.setDefaultMaxPerRoute(50);
			httpClient = new DefaultHttpClient(cm);
		} catch (SSLInitializationException e) {
			String msg = "Error while initializing client connection manager";
			LOGGER.error(msg, e);
			throw new GalaxyRestException(
			        GalaxyRestErrorCodes.GENERAL_EXCEPTION, e);
		}
	}

	/**
	 * This method is added for testing purpose, which currently skips ssl
	 * certificate verification. This has to be changed get the key-store and
	 * build the ssl socket factory properly
	 * 
	 */
	private SSLSocketFactory buildSSLSocketFactory() {
		TrustStrategy ts = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] x509Certificates,
			        String s) throws CertificateException {
				return true;
			}
		};
		SSLSocketFactory sf = null;
		try {
			sf = new SSLSocketFactory(ts,
			        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Failed to initialize SSL handling.", e);
		} catch (KeyManagementException e) {
			LOGGER.error("Failed to initialize SSL handling.", e);
		} catch (KeyStoreException e) {
			LOGGER.error("Failed to initialize SSL handling.", e);
		} catch (UnrecoverableKeyException e) {
			LOGGER.error("Failed to initialize SSL handling.", e);
		}
		return sf;
	}

	public HttpResponse execute(HttpRequestBase method, String serviceContext)
	        throws GalaxyRestException {
		HttpResponse response = null;
		try {
			method.setURI(new URI(serviceEndpoint + serviceContext.replaceAll(" ", "%20")));
			response = httpClient.execute(method);
		} catch (URISyntaxException | IOException e) {
			String msg = "Error while executing http client method : "
			        + method.getMethod();
			LOGGER.error(msg, e);
			throw new GalaxyRestException(e);
		}
		return response;
	}
}
