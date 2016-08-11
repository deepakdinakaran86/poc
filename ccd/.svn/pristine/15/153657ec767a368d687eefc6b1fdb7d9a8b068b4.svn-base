package com.pcs.util.faultmonitor.ccd.fault.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Snapshots {
	
	@JsonProperty("Snapshots")
	private List<Snapshot> snapshots;
	
	
	
	public List<Snapshot> getSnapshots() {
		return snapshots;
	}
	public void setSnapshots(List<Snapshot> snapshots) {
		this.snapshots = snapshots;
	}
	
	public void addSnapshot(Snapshot snapshot) {
		if(snapshots == null){
			snapshots = new ArrayList<Snapshot>();
		}
		snapshots.add(snapshot);
	}
	
}
