package com.pcs.saffron.metrics.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.metrics.bundle.util.CacheUtil;

public class MetricsActivator implements BundleActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetricsActivator.class);
	
	ServiceReference cacheService = null;
	public void start(BundleContext context) throws Exception {
		cacheService = context.getServiceReference(CacheProvider.class.getName());
		CacheProvider cacheProvider = (CacheProvider) context.getService(cacheService);
		CacheUtil.setCacheProvider(cacheProvider);
		LOGGER.info("Started  Metrics System.");
	}

	public void stop(BundleContext context) throws Exception {
		LOGGER.info("Goodbye  Metrics..!!");
		context.ungetService(cacheService);
	}

}
