package com.pcs.alpine.token.config;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author pcseg310
 * 
 */
public class TokenManagerConfiguration {

	private static Properties props;
	private static final String configFileName = "tokenmgr.properties";

	public static final String API_MANAGER_GET_TOKEN_URL = "token.manager.apimanager.url";
	public static final String API_PLATFORM_USER_URL = "token.manager.user.permission.url";
	public static final String API_PLATFORM_TENANT_URL = "token.manager.tenant.url";

	static {
		initialize();
	}

	private TokenManagerConfiguration() {
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

		}
		try {
			props.load(is);
		} catch (IOException e) {

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
		ClassLoader contextClassLoader = Thread.currentThread()
		        .getContextClassLoader();
		if (contextClassLoader != null) {
			url = contextClassLoader.getResource(path);
		}
		if (url == null) {
			url = TokenManagerConfiguration.class.getClassLoader().getResource(
			        path);
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
