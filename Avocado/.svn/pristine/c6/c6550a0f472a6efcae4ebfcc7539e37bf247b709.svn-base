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
package com.pcs.avocado.commons.filters;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.ext.ResponseHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;

/**
 * This Class is not required as Response Handling is done through ESB
 * CXFRequestResponseFilter
 * 
 * @description This class is Responsible for updating CXF request or response 
 * @author Javid Ahammed(PCSEG199)
 * @since galaxy-1.0.0
 * @deprecated as not in use
 */

@Deprecated
public class CXFRequestResponseFilter
        implements
            RequestHandler,
            ResponseHandler {

	@Override
	public Response handleRequest(Message message,
	        ClassResourceInfo resourceClass) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response handleResponse(Message message, OperationResourceInfo ori,
	        Response response) {
		Map<String, List<String>> headers = (Map<String, List<String>>)message
		        .get(Message.PROTOCOL_HEADERS);
		if (headers == null) {
			headers = new TreeMap<String, List<String>>(
			        String.CASE_INSENSITIVE_ORDER);
			message.put(Message.PROTOCOL_HEADERS, headers);
		}
		headers.put("Access-Control-Allow-Origin", Arrays.asList("*"));
		headers.put("Access-Control-Allow-Methods",
		        Arrays.asList("PUT, GET, POST, DELETE"));
		headers.put("Access-Control-Allow-Headers",
		        Arrays.asList("Content-Type"));
		message.put(Message.PROTOCOL_HEADERS, headers);
		return response;
	}
}
