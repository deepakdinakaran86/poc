package com.pcs.device.web.client;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;

import com.pcs.device.web.model.UserToken;

/**
 * Interceptor to add platform access token in all outbound requests
 * 
 * @author pcseg296 RIYAS PH
 * @date 28 July 2015
 * @since galaxy-1.0.0
 */
public class DataSourceTokenInterceptor implements ClientHttpRequestInterceptor {

	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
	        ClientHttpRequestExecution execution) throws IOException {
		UserToken userToken = (UserToken)RequestContextHolder
		        .getRequestAttributes().getAttribute("token", 1);

		HttpHeaders headers = request.getHeaders();
		headers.set("Authorization", "Bearer " + userToken.getAccessToken());

		return execution.execute(request, body);
	}

}
