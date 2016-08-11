/**
 * 
 */
package com.pcs.device.gateway.commons.http.netty.payload.marshal;

import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author pcseg310
 *
 */
public class JsonUnmarshaller<T> implements Unmarshaller<T> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public T unmarshall(HttpContent content, Class<T> clazz)
			throws UnmarshallerException {
		T ret = null;
		if (content == null) {
			// happens??
			return ret;
		}
		try {
			ret = mapper.readValue(content.content()
					.toString(CharsetUtil.UTF_8), clazz);
		} catch (JsonParseException e2) {
			throw new UnmarshallerException("Parsing exception", e2);
		} catch (JsonMappingException e) {
			throw new UnmarshallerException("Property mapping exception", e);
		} catch (IOException e) {
			throw new UnmarshallerException("Generic I/O exception", e);
		}

		return ret;
	}

}