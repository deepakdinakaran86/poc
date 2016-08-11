/**
 * 
 */
package com.pcs.saffron.deviceregistry.configuration;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pcseg310
 *
 */
public class RegistryConfiguration implements PropertyKeys {

	private static Properties props;
	private static final String configFileName = "registry.properties";
	private static final Logger logger = LoggerFactory.getLogger(RegistryConfiguration.class);

	static {
		initialize();
	}

	private RegistryConfiguration() {
	}

	private static void initialize() {
		props = new Properties();
		URL url = resolveURL(configFileName);
		loadProperties(url);
	}

	private static void loadProperties(URL url) {
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (IOException e1) {
			logger.error("Property file name is not correct", e1);
		}
		try {
			props.load(is);
		} catch (IOException e) {
			logger.error("Could not load the properties from the file", e);
		} finally {
			closeResource(is);
		}
	}

	private static void closeResource(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
			// closed quietly
		}
	}

	private static URL resolveURL(String path) {
		URL url = null;
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		if (contextClassLoader != null) {
			url = contextClassLoader.getResource(path);
		}
		if (url == null) {
			url = RegistryConfiguration.class.getClassLoader().getResource(path);
		}
		if (url == null) {
			url = ClassLoader.getSystemClassLoader().getResource(path);
		}
		return url;
	}

	public static String getProperty(String key) {
		if (key == null || key.isEmpty()) {
			return null;
		}
		return getProperty(key, null);
	}

	public static String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
	
}
