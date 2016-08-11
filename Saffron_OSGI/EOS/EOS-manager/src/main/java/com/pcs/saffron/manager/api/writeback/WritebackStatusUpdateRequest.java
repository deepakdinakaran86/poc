/**
 * 
 */
package com.pcs.saffron.manager.api.writeback;

import com.pcs.saffron.manager.authentication.BaseRequest;
import com.pcs.saffron.manager.config.ManagerConfiguration;

/**
 * @author pcseg310
 *
 */
public class WritebackStatusUpdateRequest extends BaseRequest {

	private String sourceId;
	
	public WritebackStatusUpdateRequest() {
		//TODO REVERT TO PLATFORM IP (1) AFTER TESTING WITH LOCAL DEPLOYMENT
		super(0);
	}
	
	public String buildConfigurationUrl(){
		return getConfigurationUrl();
	}
	
	public String getConfigurationUrl() {
		return ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_WRITEBACK_RESPONSE_URL).replace("{source_id}", this.sourceId);
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}


}
