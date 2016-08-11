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
package com.pcs.device.gateway.commons.apache;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for Managing HTTP Connections .PoolingHttpClientConnectionManager is
 * used for configuring total number of connections ,Default maximum number of
 * connections per route,Keep Alive Strategy and IdleConnectionMonitorThread
 *
 * @see CloseableHttpClient
 * @see PoolingHttpClientConnectionManager
 *
 * @author pcseg199 (Javid Ahammed)
 * @date Mar 24, 2015
 * @since galaxy-1.0.0
 */
public class ApacheHTTPClient {

	private static ApacheHTTPClient apacheHTTPClient;

	private static CloseableHttpClient httpClient;

	private final PoolingHttpClientConnectionManager connManager;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(ApacheHTTPClient.class);

	/**
	 * Private Constructor
	 */
	private ApacheHTTPClient() {
		connManager = new PoolingHttpClientConnectionManager();
		// TODO to accept the values from a configuration
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(5);
		httpClient = HttpClients.custom().setConnectionManager(connManager)
		        .setKeepAliveStrategy(getKeepAliveStrategy()).build();
		IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(
		        connManager);
		staleMonitor.start();
		try {
			staleMonitor.join(1000);
		} catch (InterruptedException e) {
			LOGGER.error("Error in ApacheHTTPClient Initialization", e);
		}
	}

	/**
	 *
	 * Singleton Object Creator Method
	 */
	public static ApacheHTTPClient getInstance() {

		if (apacheHTTPClient == null) {
			apacheHTTPClient = new ApacheHTTPClient();
		}
		return apacheHTTPClient;
	}

	/**
	 * Private method for executing the @HttpUriRequest with @CloseableHttpClient
	 * 
	 * @param request
	 * @param httpHeaders
	 * @param responseClazz
	 * @return
	 */
	public HttpResponse execute(HttpUriRequest request,
	        Map<String, String> httpHeaders) {
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(request);
		} catch (Exception e) {
			LOGGER.error("Error in ApacheHTTPClient execute", e);
		}
		return httpResponse;

	}



	/**
	 * Method responsible for returning @ConnectionKeepAliveStrategy
	 *
	 * @return
	 */
	private ConnectionKeepAliveStrategy getKeepAliveStrategy() {
		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response,
			        HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(
				        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return 5 * 1000;
			}
		};
		return myStrategy;
	}

}
class IdleConnectionMonitorThread extends Thread {
	private final HttpClientConnectionManager connMgr;
	private volatile boolean shutdown;

	public IdleConnectionMonitorThread(
	        PoolingHttpClientConnectionManager connMgr) {
		super();
		this.connMgr = connMgr;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(1000);
					connMgr.closeExpiredConnections();
					connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException ex) {
			shutdown();
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}