package com.pcs.saffron.teltonika.simulator.bean;

import org.kohsuke.args4j.Option;

public class SimulatorConfig {

	@Option(name = "-ip", usage = "server ip address")
	private String serverIP;

	@Option(name = "-p", usage = "server port")
	private Integer serverPort;

	@Option(name = "-imei", usage = "IMEI number")
	private String imei;

	@Option(name = "-d", usage = "delay")
	private Long delay;

	@Option(name = "-m", usage = "supported modes.1->Tamed mode (stick to file),2->Fire Monkey (Use this for simulating real devices) ")
	private Integer mode;
	
	@Option(name = "-fp", usage = "file path for -m 1")
	private String filePath;
	
	@Option(name = "-msg", usage = "refrence message for -m 2")
	private String referenceMsg;

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getReferenceMsg() {
		return referenceMsg;
	}

	public void setReferenceMsg(String referenceMsg) {
		this.referenceMsg = referenceMsg;
	}

}
