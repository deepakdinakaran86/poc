package com.pcs.avocado.commons.dto;

import java.util.List;

public class ValidationJsonStringDTO {

	private List<String> client;
	private List<String> server;
	private Boolean showOnGrid;
	private Boolean showOnTree;

	
	public List<String> getClient() {
		return client;
	}
	public void setClient(List<String> client) {
		this.client = client;
	}
	public List<String> getServer() {
		return server;
	}
	public void setServer(List<String> server) {
		this.server = server;
	}
	public Boolean getShowOnGrid() {
	    return showOnGrid;
    }
	public void setShowOnGrid(Boolean showOnGrid) {
	    this.showOnGrid = showOnGrid;
    }
	public Boolean getShowOnTree() {
	    return showOnTree;
    }
	public void setShowOnTree(Boolean showOnTree) {
	    this.showOnTree = showOnTree;
    }

	
}
