/**
 * 
 */
package com.pcs.saffron.deviceutil.api.history.request;

import com.pcs.saffron.deviceutil.config.ManagerConfiguration;
import com.pcs.saffron.deviceutil.util.BaseRequest;

/**
 * @author pcseg310
 *
 */
public class DeviceHistoryRequest extends BaseRequest {

	public DeviceHistoryRequest() {
		super(0);
	}
	
	public String buildRegistrationUrl(String sourceId){
		return getRegistrationUrl();
	}
	
	public String getRegistrationUrl() {
		return ManagerConfiguration.getProperty(ManagerConfiguration.DEVICE_HISTORY_URL);
	}
}
