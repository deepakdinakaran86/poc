/**
 * 
 */
package com.pcs.saffron.deviceutil.bean;

import java.util.List;


/**
 * @author pcseg310
 *
 */
public class ConfigPoint {

	private String precedence;
	private String expression;
	private String dataType;
	private String pointId;
	private String pointName;
	private String type;
	private String unit;
	private String displayName;
	private String physicalQuantity;
	private List<Extension> extensions;
	private List<Extension> alarmExtensions;
	private String pointAccessType;
	
	
	public String getPointAccessType() {
		return pointAccessType;
	}
	public void setPointAccessType(String pointAccessType) {
		this.pointAccessType = pointAccessType;
	}
	public String getPhysicalQuantity() {
		return physicalQuantity;
	}
	public void setPhysicalQuantity(String physicalQuantity) {
		this.physicalQuantity = physicalQuantity;
	}
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public List<Extension> getExtensions() {
		return extensions;
	}
	public void setExtensions(List<Extension> extensions) {
		this.extensions = extensions;
	}
	public List<Extension> getAlarmExtensions() {
		return alarmExtensions;
	}
	public void setAlarmExtensions(List<Extension> alarmExtensions) {
		this.alarmExtensions = alarmExtensions;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getPrecedence() {
		return precedence;
	}
	public void setPrecedence(String precedence) {
		this.precedence = precedence;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	

}
