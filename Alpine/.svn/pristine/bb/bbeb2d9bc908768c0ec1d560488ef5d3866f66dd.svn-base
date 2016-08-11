package com.pcs.galaxy.apim.handler;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;

import com.pcs.galaxy.esb.handler.AuditHandler;

public class APIMHandler extends AbstractHandler {

	private static final String REMOTE_ADDR = "REMOTE_ADDR";

	private static final Log logger = LogFactory.getLog(AuditHandler.class);

	public boolean handleRequest(MessageContext messageContext) {
		// TODO Auto-generated method stub
		Axis2MessageContext axis2smc = (Axis2MessageContext) messageContext;
		org.apache.axis2.context.MessageContext axis2MessageCtx = axis2smc
				.getAxis2MessageContext();
		Map headers = (Map) ((Axis2MessageContext) messageContext)
				.getAxis2MessageContext()
				.getProperty(
						org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
		TreeMap headersMap = new TreeMap<String, String>();

		if (headers != null && headers instanceof Map) {
			headersMap = (TreeMap) headers;
			headersMap.put("HTTP_CLIENT_IP",
					axis2MessageCtx.getProperty(REMOTE_ADDR));
		}

		headers.put("HTTP_CLIENT_IP", axis2MessageCtx.getProperty(REMOTE_ADDR));

		messageContext.setProperty(
				org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS,
				headersMap);

		/*
		 * System.out.println(axis2MessageCtx.getProperty(REMOTE_ADDR));
		 * System.out
		 * .println(axis2MessageCtx.getProperty("HTTP_X_FORWARDED_FOR"));
		 * System.out.println(axis2MessageCtx.getProperty("HTTP_CLIENT_IP"));
		 * System.out.println(axis2MessageCtx.getProperty("HTTP_X_FORWARDED"));
		 * System
		 * .out.println(axis2MessageCtx.getProperty("HTTP_X_CLUSTER_CLIENT_IP"
		 * ));
		 * System.out.println(axis2MessageCtx.getProperty("HTTP_FORWARDED_FOR"
		 * ));
		 * System.out.println(axis2MessageCtx.getProperty("HTTP_FORWARDED"));
		 * 
		 * 
		 * logger.error(axis2MessageCtx.getProperty(REMOTE_ADDR));
		 * logger.error(axis2MessageCtx.getProperty("HTTP_X_FORWARDED_FOR"));
		 * logger.error(axis2MessageCtx.getProperty("HTTP_CLIENT_IP"));
		 * logger.error(axis2MessageCtx.getProperty("HTTP_X_FORWARDED"));
		 * logger.
		 * error(axis2MessageCtx.getProperty("HTTP_X_CLUSTER_CLIENT_IP"));
		 * logger.error(axis2MessageCtx.getProperty("HTTP_FORWARDED_FOR"));
		 * logger.error(axis2MessageCtx.getProperty("HTTP_FORWARDED"));
		 */

		return true;
	}

	public boolean handleResponse(MessageContext arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}