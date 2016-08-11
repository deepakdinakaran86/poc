/**
 * 
 */
package com.pcs.guava.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.pcs.guava.rest.exception.GalaxyRestErrorCodes;
import com.pcs.guava.rest.exception.GalaxyRestException;

/**
 * A simple rest client. usage : RestClient.invoke(ResponseDTO.class,
 * PayloadDto, "http:localhost:8080/services/um", "/users",RestOperations.GET)
 * 
 * @author Rakesh
 * @author Dinesh
 * @deprecated as not used
 * 
 */
@Deprecated
public class RestClient {

	public RestClient() {

	}

	/**
	 * A set of operations supported by {@link RestClient}
	 * 
	 * @author rakesh
	 * 
	 */
	public static enum RestOperations {
		PUT("PUT"), GET("GET"), POST("POST"), DELETE("DELETE");
		String value;

		private RestOperations(String val) {
			value = val;
		}

		String getValue() {
			return value;
		}
	}

	/**
	 * @param responseClazz
	 *            The response DTO (inbound)
	 * @param payload
	 *            Outbound DTO
	 * @param targetServiceURI
	 *            target URI of the service
	 * @param targetResourcePath
	 *            resource path
	 * @param operation
	 *            {@link RestOperations}
	 * @return
	 * @throws RestClientException
	 */
	public static <P extends Object, R extends Object> R invoke(
	        Class<R> responseClazz, P payload, String targetServiceURI,
	        String targetResourcePath, RestOperations operation,
	        Map<String, String> queryMap) throws GalaxyRestException {

		List<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient restClient = WebClient.create(targetServiceURI, providers);

		restClient.path(targetResourcePath);

		// adding query params
		if (queryMap != null) {

			Set<String> keySet = queryMap.keySet();
			for (String key : keySet) {
				restClient.query(key, queryMap.get(key));
			}
		}
		restClient.accept(MediaType.APPLICATION_JSON_TYPE);
		restClient.type(MediaType.APPLICATION_JSON_TYPE);
		Response response = restClient.invoke(operation.getValue(), payload);
		R responseDTO = null;
		// ErrorMessageDTO errorDto = null;

		if (response.getStatus() < 400) {
			// success
			UnMarshallHelper<R> helper = new UnMarshallHelper<R>();
			try {
				responseDTO = helper.unmarshall(responseClazz,
				        (InputStream)response.getEntity());
			} catch (IllegalStateException | IOException e) {
				throw new GalaxyRestException(e);
			}
		} else {
			// throw a new RestClientException based on response
			throw new GalaxyRestException(
			        GalaxyRestErrorCodes.GENERAL_EXCEPTION);
		}
		return responseDTO;
	}

	public static <P extends Object, R extends Object> R invoke(
	        Class<R> responseClazz, P payload, String targetServiceURI,
	        String targetResourcePath, RestOperations operation)
	        throws GalaxyRestException {
		return invoke(responseClazz, payload, targetServiceURI,
		        targetResourcePath, operation, null);

	}

}
