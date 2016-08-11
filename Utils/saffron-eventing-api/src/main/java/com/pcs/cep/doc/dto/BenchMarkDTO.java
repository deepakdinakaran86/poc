package com.pcs.cep.doc.dto;

import java.io.Serializable;

/**
 * @author pcseg199
 *
 */
public class BenchMarkDTO implements Serializable {
	
	private static final long serialVersionUID = -214803026006686092L;

	private String sourceId;
	
	private String parameter;

	private Float maxValue;
	
	private Float minValue;
	
	private String compareValue;
	
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Float maxValue) {
		this.maxValue = maxValue;
	}

	public Float getMinValue() {
		return minValue;
	}

	public void setMinValue(Float minValue) {
		this.minValue = minValue;
	}

	public String getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(String compareValue) {
		this.compareValue = compareValue;
	}
	
	
}
