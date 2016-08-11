/**
 * 
 */
package com.pcs.saffron.manager.api.datasource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

/**
 * @author pcseg310
 *
 */
public class DatasourceUpdateRequest extends BaseRequest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceService.class);
	
	private String datasourceName;
	
	public DatasourceUpdateRequest() {
		super(1);
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		String pathURL = null;
		try {
			pathURL =  ManagerConfiguration.getProperty(ManagerConfiguration.DATASOURCE_UPDATE_URL).replace("{datasource_name}", URLEncoder.encode(this.datasourceName,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Invalid configuration URL",e);
		}
		return pathURL;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

}
