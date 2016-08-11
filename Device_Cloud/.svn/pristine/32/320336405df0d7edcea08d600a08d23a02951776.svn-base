package com.pcs.device.gateway.G2021.message;

import java.util.ArrayList;
import java.util.List;

import com.pcs.deviceframework.decoder.data.message.Message;

public class PointDiscoveryMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2854515712555857826L;

	
	private List<Short> pids ;

	public List<Short> getPids() {
		return pids;
	}

	public void addPid(Short pid){
		if(pids == null){
			pids = new ArrayList<Short>();
		}
		pids.add(pid);
	}

}
