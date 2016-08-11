package com.pcs.device.gateway.commons.http.netty.payload;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Generic JSON client response wrapper.
 * 
 * @author pcseg310
 *
 * @param <T> Response Type
 */
public class JsonResponse<T> implements ClientResponse<T> {

	private T responseType;
	private AtomicBoolean state;
	private Class<T> responseClazz;

	public JsonResponse(Class<T> clazz) {
		this.state = new AtomicBoolean();
		this.responseClazz = clazz;
	}

	@Override
	public T getResponse() {
		return responseType;
	}

	@Override
	public void setResponse(T responseType) {
		this.responseType = responseType;
	}

	@Override
	public boolean readyState() {
		return state.get();
	}

	@Override
	public void setReadyState(boolean status) {
		this.state.set(status);
	}

	@Override
	public Class<T> getResponseType() {
		return responseClazz;
	}

	@Override
	public void setResponseType(Class<T> clazz) {
		this.responseClazz = clazz;
	}

}