package com.pcs.saffron.expressions.engine.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.expressions.engine.ExpressionEngineUtil;

public class ExpressionActivator implements BundleActivator {

	private static final Logger LOGGER  = LoggerFactory.getLogger(ExpressionActivator.class);
	ServiceRegistration expressionEngine = null;
	
	public void start(BundleContext context) throws Exception {
		LOGGER.info("Registering Expression Engine...");
		ExpressionEngineUtil expressionEngineUtil = new ExpressionEngineUtil();
		expressionEngine = context.registerService(ExpressionEngineUtil.class.getName(), expressionEngineUtil, null);
		LOGGER.info("Expression Engine Registered Successfully");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Shutting down Expression Engine...");
		expressionEngine.unregister();
		LOGGER.info("Expression Engine is terminated");
	}

}
