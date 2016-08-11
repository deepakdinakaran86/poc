package com.pcs.device.gateway.jace.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurationFeedResponse extends JaceMessage {

	@JsonProperty("segCnt")
	private Integer segmentIndex;
	@JsonProperty("list")
	private List<String> handles;
	
	public Integer getSegmentIndex() {
		return segmentIndex;
	}
	public void setSegmentIndex(Integer segmentIndex) {
		this.segmentIndex = segmentIndex;
	}
	public List<String> getHandles() {
		return handles;
	}
	public void setHandles(List<String> handles) {
		this.handles = handles;
	}
	
	public void addList(String handleId){
		if(CollectionUtils.isEmpty(handles)){
			handles = new ArrayList<String>();
		}
		handles.add(handleId);
	}
	
	
}
