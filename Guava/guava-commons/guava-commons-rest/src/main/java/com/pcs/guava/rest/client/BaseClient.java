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
package com.pcs.guava.rest.client;

import static com.pcs.guava.rest.exception.GalaxyRestErrorCodes.UN_MARSHAL_ERROR;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.pcs.guava.commons.rest.dto.ErrorMessageDTO;
import com.pcs.guava.rest.exception.GalaxyRestErrorCodes;
import com.pcs.guava.rest.exception.GalaxyRestException;
import com.pcs.guava.rest.mapper.GalaxyObjectMapper;

/**
 * 
 * This class is responsible HTTP actions such as {@link GET},{@link POST},
 * {@link PUT},{@link DELETE}
 * 
 * Users should import META-INF/rest/rest-conf.xml in order to Autowire the same
 * and the property (is.endpoint.uri) should be available
 * 
 * @author Javid Ahammed (PCSEG199)
 * @date Nov 16, 2014
 * @since galaxy-1.0.0
 */
public class BaseClient {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseClient.class);

	private ConnectionManager connectionManager;

	private static final GalaxyObjectMapper mapper = new GalaxyObjectMapper();

	private String serviceEndpoint;

	public BaseClient(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}

	@PostConstruct
	public void intialize() {
		if (!serviceEndpoint.endsWith("/")) {
			serviceEndpoint = serviceEndpoint + "/";
		}
		connectionManager = new ConnectionManager(serviceEndpoint);
	}

	public <T, R> R post(String serviceContext,
			Map<String, String> httpHeaders, T payload, Class<R> responseClazz) {
		HttpPost post = new HttpPost();
		post.setEntity(marshall(payload));
		return execute(post, serviceContext, httpHeaders, responseClazz);
	}

	public <T, R> R post(String serviceContext,
			Map<String, String> httpHeaders, T payload, Class<R> responseClazz,
			Class typeClass) {
		HttpPost post = new HttpPost();
		post.setEntity(marshall(payload));
		return execute(post, serviceContext, httpHeaders, responseClazz,
				typeClass);
	}

	public <T, R> R put(String serviceContext, Map<String, String> httpHeaders,
			T payload, Class<R> responseClazz) {
		HttpPut put = new HttpPut();
		put.setEntity(marshall(payload));
		return execute(put, serviceContext, httpHeaders, responseClazz);
	}

	public <R> R delete(String serviceContext, Map<String, String> httpHeaders,
			Class<R> responseClazz) {
		HttpDelete delete = new HttpDelete();
		try {
			return execute(delete, serviceContext, httpHeaders, responseClazz);
		} catch (Exception e) {
			if (e instanceof GalaxyRestException) {
				throw e;
			}
			throw new GalaxyRestException(e);
		}

	}

	public <R> R get(String serviceContext, Map<String, String> httpHeaders,
			Class<R> responseClazz) {
		HttpGet get = new HttpGet();
		try {
			return execute(get, serviceContext, httpHeaders, responseClazz);
		} catch (Exception e) {
			if (e instanceof GalaxyRestException) {
				throw e;
			}
			throw new GalaxyRestException(e);
		}
	}

	public <R> R get(String serviceContext, Map<String, String> httpHeaders,
			Class<R> responseClazz, Class typeClass) {
		HttpGet get = new HttpGet();
		try {
			return execute(get, serviceContext, httpHeaders, responseClazz,
					typeClass);
		} catch (Exception e) {
			if (e instanceof GalaxyRestException) {
				throw e;
			}
			throw new GalaxyRestException(e);
		}
	}

	private <R> R execute(HttpRequestBase method, String serviceContext,
			Map<String, String> httpHeaders, Class<R> responseClazz) {
		setHeaders(httpHeaders, method);
		HttpResponse httpResponse = connectionManager.execute(method,
				serviceContext);
		return handleResponse(httpResponse, responseClazz, null);

	}

	private <R> R execute(HttpRequestBase method, String serviceContext,
			Map<String, String> httpHeaders, Class<R> responseClazz,
			Class typeClass) {
		setHeaders(httpHeaders, method);
		HttpResponse httpResponse = connectionManager.execute(method,
				serviceContext);
		return handleResponse(httpResponse, responseClazz, typeClass);

	}

	private <T> BasicHttpEntity marshall(final T beanObject) {
		BasicHttpEntity entity = new BasicHttpEntity();
		AnnotationIntrospector annotationIntrospector = new JaxbAnnotationIntrospector(
				mapper.getTypeFactory());
		mapper.setAnnotationIntrospector(annotationIntrospector);
		String value = null;
		try {
			if (beanObject instanceof String) {
				value = beanObject.toString();
			} else {
				value = mapper.writeValueAsString(beanObject);
			}
		} catch (JsonProcessingException e) {
			throw new GalaxyRestException(e);
		}
		LOGGER.debug("Marshalled payload : " + value);
		try {
			entity.setContent(new ByteArrayInputStream(value.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new GalaxyRestException(e);
		}
		return entity;

	}

	public <T> T unmarshall(Class<T> cls, InputStream inputStream) {
		AnnotationIntrospector annotationIntrospector = new JaxbAnnotationIntrospector(
				mapper.getTypeFactory());
		mapper.setAnnotationIntrospector(annotationIntrospector);
		T readValue = null;
		try {
			readValue = mapper.readValue(inputStream, cls);
		} catch (IOException e) {
			throw new GalaxyRestException(UN_MARSHAL_ERROR, e);
		}
		return readValue;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T unmarshall(Class<T> cls, InputStream inputStream,
			Class collectionClass, Class typeClass) {
		AnnotationIntrospector annotationIntrospector = new JaxbAnnotationIntrospector(
				mapper.getTypeFactory());
		mapper.setAnnotationIntrospector(annotationIntrospector);
		T readValue = null;
		try {
			readValue = mapper.readValue(inputStream, mapper.getTypeFactory()
					.constructCollectionType(collectionClass, typeClass));
		} catch (IOException e) {
			throw new GalaxyRestException(e);
		}
		return readValue;
	}

	public void setHeaders(Map<String, String> httpHeaders,
			HttpRequestBase requestBase) {
		// setting application/json as the default content-type
		requestBase.setHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON));
		for (String header : httpHeaders.keySet()) {
			requestBase.setHeader(new BasicHeader(header, httpHeaders
					.get(header)));
		}
	}

	public <R> R handleResponse(HttpResponse httpResponse,
			Class<R> responseClazz, Class typeClass) {
		try {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			LOGGER.trace("Handling response - " + statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				R response = null;
				if (typeClass != null) {
					response = unmarshall(responseClazz, httpResponse
							.getEntity().getContent(), responseClazz, typeClass);
				} else {
					response = unmarshall(responseClazz, httpResponse
							.getEntity().getContent());
				}
				return response;
			} else if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
				throw new GalaxyRestException(
						GalaxyRestErrorCodes.AUTHENTICATION_FAILED);

			} else if (statusCode == HttpStatus.SC_FORBIDDEN) {
				throw new GalaxyRestException(
						GalaxyRestErrorCodes.FORBIDDEN_ACCESS);
			} else if (statusCode == HttpStatus.SC_BAD_REQUEST) {
				try {
					ErrorMessageDTO errorMessageDTO = unmarshall(
							ErrorMessageDTO.class, httpResponse.getEntity()
									.getContent());
					throw new GalaxyRestException(errorMessageDTO);
				} catch (GalaxyRestException gre) {
					throw gre;
				} catch (Exception e) {

					throw new GalaxyRestException(
							GalaxyRestErrorCodes.INVALID_REQUEST);
				}
			} else if (statusCode == 520) {
				throw new GalaxyRestException(
						GalaxyRestErrorCodes.DATA_NOT_AVAILABLE);
			} else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				try {
					ErrorMessageDTO errorStatus = mapper.readValue(httpResponse
							.getEntity().getContent(), ErrorMessageDTO.class);
					throw new GalaxyRestException(errorStatus);
				} catch (GalaxyRestException gre) {
					throw gre;
				} catch (Exception e) {
					throw new GalaxyRestException(
							GalaxyRestErrorCodes.GENERAL_EXCEPTION);
				}
			} else {
				LOGGER.debug("Response error message code : "
						+ statusCode
						+ "\n"
						+ "message : "
						+ convertToString(httpResponse.getEntity().getContent()));
				throw new GalaxyRestException(
						GalaxyRestErrorCodes.DATA_NOT_AVAILABLE);
			}
		} catch (IOException e) {
			LOGGER.error("Error while processiong http response", e);
			throw new GalaxyRestException(
					GalaxyRestErrorCodes.DATA_FETCHING_ERROR, e);
		} finally {
			try {
				EntityUtils.consume(httpResponse.getEntity());
			} catch (IOException e) {
				LOGGER.error("Error while cleaning http response", e);
			}
		}
	}

	// convert InputStream to String
	private String convertToString(InputStream is) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		if (is != null) {
			String line;
			try {
				br = new BufferedReader(new InputStreamReader(is,
						StandardCharsets.UTF_8));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return sb.toString();
	}

}
