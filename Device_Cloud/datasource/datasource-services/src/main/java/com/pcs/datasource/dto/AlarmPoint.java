/**
 * 
 */
package com.pcs.datasource.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author pcseg129(Seena Jyothish)
 * Mar 02, 2016
 *
 */
public class AlarmPoint implements Serializable{
	
	private static final long serialVersionUID = -9190432576857970340L;
	
	private String pointId;
	private String pointName;
	private String displayName;
	private String unit;
	private String data;
	private String type;
	private String status;
	
	private List<AlarmExtension> extensions;

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<AlarmExtension> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<AlarmExtension> extensions) {
		this.extensions = extensions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
