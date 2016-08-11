/**
 * 
 */
package com.pcs.util.faultmonitor.configuration;

/**
 * @author pcseg310
 *
 */
public interface PropertyKeys {
	static final String SCAN_DIRECTORY = "scan.directory";
	static final String SCAN_FREQUENCY = "scan.interval";
	static final String FAULT_MESSAGE_STREAM = "fault.message.stream";
	static final String ANALYZED_MESSAGE_STREAM="fault.datadistributor.analyzedmessagestream";
	
	static final String EDCP_MODEL = "EDCP";
	static final String EDCP_PROTOCOL = "EDCP";
	static final String EDCP_TYPE = "Edge Device";
	static final String EDCP_VENDOR = "G2021";
	static final String EDCP_VERSION = "0.9.4";
}
