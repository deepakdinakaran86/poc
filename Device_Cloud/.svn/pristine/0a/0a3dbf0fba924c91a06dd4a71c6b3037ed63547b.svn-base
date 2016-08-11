/**
 * 
 */
package com.pcs.device.gateway.commons.http.netty.payload.marshal;

import io.netty.handler.codec.http.HttpContent;

/**
 * @author pcseg310
 *
 */
public interface Unmarshaller<T> {

	T unmarshall(HttpContent content, Class<T> clazz) throws UnmarshallerException;
	
}
