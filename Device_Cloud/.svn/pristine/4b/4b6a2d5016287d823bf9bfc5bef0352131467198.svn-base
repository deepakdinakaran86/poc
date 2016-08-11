/**
 * 
 */
package com.pcs.device.gateway.teltonika.devicemanager.auth.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pcs.device.gateway.teltonika.devicemanager.auth.RemoteAuthenticationResponse;
import com.pcs.device.gateway.teltonika.devicemanager.bean.Device;
import com.pcs.deviceframework.decoder.data.point.Point;

/**
 * @author pcseg310
 *
 */
@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
public class GalaxyAuthenticationResponse implements RemoteAuthenticationResponse {

	private static final long serialVersionUID = -2112798487265775372L;
	private Device entity;
	private Integer unitId;
	private String datasourceName;
	private List<Point> pointConfigurations;

	public Device getEntity() {
		if(this.entity != null){
			this.entity.setDatasourceName(datasourceName);
		}
		return entity;
	}

	public void setEntity(Device entity) {
		this.entity = entity;
	}

	public List<Point> getPointConfigurations() {
		return pointConfigurations;
	}

	public void setPointConfigurations(List<Point> pointConfigurations) {
		this.pointConfigurations = pointConfigurations;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

}
