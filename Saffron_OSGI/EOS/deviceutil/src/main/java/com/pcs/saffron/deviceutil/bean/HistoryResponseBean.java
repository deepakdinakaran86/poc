package com.pcs.saffron.deviceutil.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoryResponseBean {

	private String sourceId;
	@JsonProperty("displayNames")
	private List<PointDataBean> customTags;
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public List<PointDataBean> getCustomTags() {
		return customTags;
	}
	public void setCustomTags(List<PointDataBean> customTags) {
		this.customTags = customTags;
	}
}
