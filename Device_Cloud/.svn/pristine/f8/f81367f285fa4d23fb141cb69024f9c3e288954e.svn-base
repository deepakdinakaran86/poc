/**
 * 
 */
package com.pcs.device.gateway.G2021.devicemanager.auth.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pcs.device.gateway.G2021.devicemanager.auth.RemoteAuthenticationResponse;
import com.pcs.device.gateway.G2021.devicemanager.bean.Device;

/**
 * @author pcseg310
 *
 */
@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class DeviceAuthenticationResponse implements RemoteAuthenticationResponse {

	private static final long serialVersionUID = -582418127976941747L;

	private GeneralResponse generalResponse;

	private Device device;

	public GeneralResponse getGeneralResponse() {
		return generalResponse;
	}

	public void setGeneralResponse(GeneralResponse generalResponse) {
		this.generalResponse = generalResponse;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
