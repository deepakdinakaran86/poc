package com.pcs.device.gateway.jace.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;


public class ConfigurationPointHandle {

	
	private static final String COMMA = ",";
	private List<String> handles;
	private String handleHeader;
	private List<String>handleIds;
	private List<String> handleHeaders;
	private Integer handleIDIndex;
	private boolean handlesExtracted = false;

	public ConfigurationPointHandle(){

	}


	public List<String> getHandle() {
		return handles;
	}

	public String getHandleHeader() {
		return handleHeader;
	}

	public void setHandleHeader(String handleHeader) {
		if(handleHeader != null){
			String[] handleHeaders = handleHeader.split(COMMA);
			this.handleHeaders = Arrays.asList(handleHeaders);
		}
		handleIDIndex = handleHeaders.indexOf("Handle_ID");
		this.handleHeader = handleHeader;
		if(!handlesExtracted)
			extractHandles();
	}

	public void setHandles(List<String> handles) {
		this.handles = handles;
		if(handles != null && handleIDIndex != null){
			extractHandles();
		}
	}

	private void extractHandles() {
		if( !handlesExtracted && !CollectionUtils.isEmpty(handles)){
			handlesExtracted = true;
			for (int i = 0; i < handles.size(); i++) {
				String pointDetail = handles.get(i);
				String pointHandleId = pointDetail.split(COMMA)[handleIDIndex];
				if(CollectionUtils.isEmpty(this.handleIds)){
					this.handleIds = new ArrayList<String>();
				}
				this.handleIds.add(pointHandleId);
			}
		}
	}

	public List<String> getHandleIds() {
		return handleIds;
	}
	
	public List<String> getHandleHeaders() {
		return handleHeaders;
	}
	

}
