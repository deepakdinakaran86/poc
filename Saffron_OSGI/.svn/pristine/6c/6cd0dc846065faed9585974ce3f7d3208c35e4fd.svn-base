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
package com.pcs.saffron.commons.http;

import java.util.Map;

/**
 * Interface for HTTP Clients
 *
 * @author pcseg310
 * @see {@link ClientBuilder}
 *
 */
public interface Client {

	/**
	 * @param pathUrl
	 * @param responseClass
	 * @return
	 * @throws ClientException
	 */
	<T> T get(String pathUrl, Map<String, String> httpHeaders,
	        Class<T> responseClass) throws ClientException;
	
	<T> T get(String pathUrl, Map<String, String> httpHeaders,
	        Class<T> responseClass,Class typeClass) throws ClientException;

	/**
	 * @param pathURL
	 * @param httpHeaders
	 * @param payload
	 * @param responseClazz
	 * @return
	 */
	<T, R> R post(String pathURL, Map<String, String> httpHeaders, T payload,
	        Class<R> responseClazz) throws ClientException;

	<T, R> R post(String pathURL, Map<String, String> httpHeaders, T payload,
	        Class<R> responseClazz, Class typeClass) throws ClientException;

	/**
	 * @param pathURL
	 * @param httpHeaders
	 * @param payload
	 * @param responseClazz
	 * @return
	 * @throws ClientException
	 */
	<T, R> R put(String pathURL, Map<String, String> httpHeaders, T payload,
	        Class<R> responseClazz) throws ClientException;

	/**
	 * @param pathUrl
	 * @param httpHeaders
	 * @param responseClass
	 * @return
	 * @throws ClientException
	 */
	<T> T delete(String pathUrl, Map<String, String> httpHeaders,
	        Class<T> responseClass) throws ClientException;
}
