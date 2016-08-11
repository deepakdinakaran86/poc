/**
 * 
 */
package com.pcs.device.gateway.jace.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcs.device.gateway.jace.message.type.DeviceAuthenticationStatus;

/**
 * @author pcseg171
 *
 */
@JsonInclude(Include.NON_NULL)
public class JaceMessage {

	@JsonProperty("hid")
	private String hostId;
	@JsonProperty("sts")
	private DeviceAuthenticationStatus status;
	@JsonProperty("rem")
	private String remarks;
	@JsonProperty("csid")
	private Integer unitId;
	@JsonProperty("tz")
	private String timezone;
	private Integer timeOffset;
	
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public final Integer getStatus() {
		return status.getType();
	}
	public final void setStatus(DeviceAuthenticationStatus status) {
		this.status = status;
	}
	public final String getRemarks() {
		return remarks;
	}
	public final void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public final Integer getUnitId() {
		return unitId;
	}
	public final void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public Integer getTimeOffset() {
		return timeOffset;
	}
	public void setTimeOffset(Integer timeOffset) {
		this.timeOffset = timeOffset;
	}
	
	
	
}
