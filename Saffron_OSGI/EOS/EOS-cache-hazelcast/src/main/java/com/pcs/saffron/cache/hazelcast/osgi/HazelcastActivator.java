/**
 * 
 */
package com.pcs.saffron.cache.hazelcast.osgi;

import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.pcs.saffron.cache.base.CacheProvider;
import com.pcs.saffron.cache.hazelcast.HazelCast;

/**
 * @author Aneesh
 *
 */
public class HazelcastActivator implements BundleActivator {

	//private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastActivator.class);
	ServiceRegistration hazelcastCaheService = null;
	
	
	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		//LOGGER.info("Registering Hazelcast Cache Service...");
		System.err.println("Registering Hazelcast Cache Service...");
		URL entry = context.getBundle().getEntry("hazelcast-client.xml");
		CacheProvider hazelcastCache = new HazelCast(entry.toString());
		hazelcastCaheService = context.registerService(CacheProvider.class.getName(), hazelcastCache, null);
		//LOGGER.info("Hazelcast Cache Service Registered Successfully");
		System.err.println("Hazelcast Cache Service Registered Successfully");
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		//LOGGER.info("Shutting down Hazelcast Cache Service...");
		System.err.println("Shutting down Hazelcast Cache Service...");
		hazelcastCaheService.unregister();
		//LOGGER.info("Hazelcast Cache Service Is Terminated !!");
		System.err.println("Hazelcast Cache Service Is Terminated !!");
	}

}
