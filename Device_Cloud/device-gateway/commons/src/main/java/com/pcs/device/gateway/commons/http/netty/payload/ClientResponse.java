package com.pcs.device.gateway.commons.http.netty.payload;

/**
 * Wrapper interface for client response.
 * 
 * @author pcseg310
 *
 * @param <T>
 */
public interface ClientResponse<T> {

	void setReadyState(boolean status);

	boolean readyState();

	T getResponse();

	void setResponse(T response);

	Class<T> getResponseType();

	void setResponseType(Class<T> clazz);
}
