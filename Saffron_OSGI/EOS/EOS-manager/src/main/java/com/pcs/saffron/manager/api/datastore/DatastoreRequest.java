package com.pcs.saffron.manager.api.datastore;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

public class DatastoreRequest extends BaseRequest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatastoreRequest.class);

	public DatastoreRequest() {
		super();
	}
	
	public String buildConfigurationUrl(String physicalQuantity){
		try {
			physicalQuantity = URLEncoder.encode(physicalQuantity,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Error encoding PQ",e);
		}
		return getConfigurationUrl().replace("{physical_quantity}", physicalQuantity);
	}
	
	public String getConfigurationUrl() {
		return ManagerConfiguration.getProperty(ManagerConfiguration.DATASTOREURL);
	}
	
}
