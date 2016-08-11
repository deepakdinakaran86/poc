package com.pcs.saffron.deviceutil.bean;

import java.util.List;

public class HistoryRequestBean {
	
	private String sourceId;
	private long startDate;
	private long endDate;
	private List<String> customTags;
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startTime) {
		this.startDate = startTime;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endTime) {
		this.endDate = endTime;
	}
	public List<String> getCustomTags() {
		return customTags;
	}
	public void setCustomTags(List<String> customTags) {
		this.customTags = customTags;
	}

}
