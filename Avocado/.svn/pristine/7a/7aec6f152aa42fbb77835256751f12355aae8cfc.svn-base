package com.pcs.galaxy.rest.client;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
/**
 * 
 * @author pcseg311(Dinesh)
 * @deprecated as not used
 * @param <T>
 */
@Deprecated
public class UnMarshallHelper<T> {
	
	private static final ObjectMapper mapper =  new ObjectMapper();

	public T unmarshall(Class<T> cls, InputStream inputStream)
			throws IOException {
		//mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		AnnotationIntrospector annotationIntrospector = new JaxbAnnotationIntrospector(
				mapper.getTypeFactory());
		mapper.setAnnotationIntrospector(annotationIntrospector);
		return mapper.readValue(inputStream, cls);
	}
	
}
