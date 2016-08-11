package com.pcs.saffron.notification.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public final class ResourceMapper {
	private static final String CLASSPATH_PREFIX = "classpath:";

	
	
	public static URL locateConfig(final String path) {
        if (path.isEmpty()) {
            return null;
        }
        URL url = asFile(path);
        if (url == null) {
            url = asURL(path);
        }
        if (url == null) {
            url = asResource(path);
        }
        if (url == null) {
            String extractedPath = extractPathOrNull(path);
            if (extractedPath == null) {
                return null;
            }
            url = asResource(extractedPath);
        }
        return url;
    }
	
	private static String extractPathOrNull(String path) {
        if (path.startsWith(CLASSPATH_PREFIX)) {
            return path.substring(CLASSPATH_PREFIX.length());
        }
        return null;
    }

    private static URL asFile(final String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException ignored) {
                EmptyStatement.ignore(ignored);
            }
        }
        return null;
    }

    private static URL asURL(final String path) {
        try {
            return new URL(path);
        } catch (MalformedURLException ignored) {
            EmptyStatement.ignore(ignored);
        }
        return null;
    }

    private static URL asResource(final String path) {
        URL url = null;
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            url = contextClassLoader.getResource(path);
        }
        if (url == null) {
            url = ResourceMapper.class.getClassLoader().getResource(path);
        }
        if (url == null) {
            url = ClassLoader.getSystemClassLoader().getResource(path);
        }
        return url;
    }
}
