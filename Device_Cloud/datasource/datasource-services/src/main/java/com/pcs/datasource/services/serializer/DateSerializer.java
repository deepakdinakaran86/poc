/**
 * 
 */
package com.pcs.datasource.services.serializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * May 24, 2016
 */
public class DateSerializer extends JsonDeserializer<Long>{

	private SimpleDateFormat formatter = 
		      new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");
		 
		    @Override
		    public Long deserialize(JsonParser jsonparser, DeserializationContext context)
		      throws IOException, JsonProcessingException {
		        String date = jsonparser.getText();
		        if(StringUtils.isNotBlank(date)){
		        	try {
		        		return formatter.parse(date).getTime();
		        	} catch (ParseException e) {
		        		throw new RuntimeException(e);
		        	}
		        }else{
		        	return null;
		        }
		    }
}
