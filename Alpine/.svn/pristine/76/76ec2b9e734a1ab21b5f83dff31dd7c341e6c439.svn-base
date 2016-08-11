package com.pcs.alpine.commons.serviceImpl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pcs.alpine.commons.dto.Subscription;
import com.pcs.alpine.commons.dto.SubscriptionContext;
import com.pcs.alpine.commons.dto.SubscriptionContextHolder;
import com.pcs.alpine.constant.CommonConstants;
import com.pcs.alpine.util.SubscriptionUtility;

@Provider
public class SubscriptionProfileFilter implements RequestHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Context
	private HttpHeaders headers;

	@Autowired
	public SubscriptionUtility subscriptionUtility;

	@Override
	public Response handleRequest(Message m, ClassResourceInfo resourceClass) {
		List<String> headersList = headers.getRequestHeader("x-jwt-assertion");
		// logger.info(headersList.toString());

		String urlPattern = m.get(Message.REQUEST_URI).toString();
		Pattern swaggerApiPattern = Pattern.compile("/swagger(/|$)");
		Pattern swaggerApiJsonPattern = Pattern.compile("/swagger.json(/|$)");
		Matcher swaggerApiPatternMatcher = swaggerApiPattern
		        .matcher(urlPattern);
		Matcher swaggerApiJsonPatternMatcher = swaggerApiJsonPattern
		        .matcher(urlPattern);

		if (swaggerApiPatternMatcher.find()
		        || swaggerApiJsonPatternMatcher.find()) {
			/**
			 * if url is /login, /tokens, /validations, /users/set_password or
			 * notifications don't do anything.
			 */
			return null;
		}

		if (headersList.size() != 1) {
			return createFaultResponse();
		}

		Subscription subscription = null;

		String jwtObject = headersList.get(0);
		if (StringUtils.isNotEmpty(jwtObject)) {
			subscription = subscriptionUtility.getSubscription(jwtObject);
		} else {
			return createFaultResponse();
		}

		subscription = validateSubscriptionHeader(subscription);
		SubscriptionContext subscriptionContext = new SubscriptionContext(
		        subscription);
		// logger.debug("Setting userProfile in securityContext : "
		// + subscriptionContext);
		SubscriptionContextHolder.setContext(subscriptionContext);

		return null;
	}

	private Response createFaultResponse() {
		return Response.status(401).header("Illegal", "Illegal access").build();
	}

	private Subscription validateSubscriptionHeader(Subscription subscription) {

		// TODO fix is required, to manage carbon.super endUserDomain
		if (StringUtils.isBlank(subscription.getEndUserDomain())
		        || subscription.getEndUserDomain().equalsIgnoreCase("null")
		        || subscription.getEndUserDomain().equalsIgnoreCase(
		                "carbon.super")) {
			subscription.setEndUserDomain(CommonConstants.ALPINE_DOTE_GALAXY);
		}
		if (StringUtils.isBlank(subscription.getSubscriberDomain())
		        || subscription.getSubscriberDomain().equalsIgnoreCase("null")
		        || subscription.getSubscriberDomain().equalsIgnoreCase(
		                "carbon.super")) {
			subscription
			        .setSubscriberDomain(CommonConstants.ALPINE_DOTE_GALAXY);
		}

		// logger.info("Subscriber Domain : " +
		// subscription.getSubscriberDomain());
		// logger.info("Subscriber Name : " + subscription.getSubscriberName());
		// logger.info("End User Name : " + subscription.getEndUserName());
		// logger.info("End User Domain : " + subscription.getEndUserDomain());
		// logger.info("Subscriber App : " + subscription.getSubscriberApp());

		/*
		 * else { logger.info("Subscription endUserDomain :" +
		 * subscription.getEndUserDomain() + "------------------------");
		 * subscription.setEndUserDomain("alpine.com"); }
		 */
		return subscription;
	}
}
