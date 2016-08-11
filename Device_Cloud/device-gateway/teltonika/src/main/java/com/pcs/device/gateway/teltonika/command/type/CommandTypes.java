package com.pcs.device.gateway.teltonika.command.type;

public enum CommandTypes {
	GETVERSION("234745542056455253494FE"),
	GETNETWORK("23474554204E4554574F524B"),
	DOREPORT("23444F205245504F5254"),
	DORESET("23444F2052455345543D475053"),
	GETOUT("23444F204F5554"),
	CRLF("0D0A");
	
	private String hexa;

	public String getHexa() {
		return hexa;
	}

	public void setHexa(String hexa) {
		this.hexa = hexa;
	}

	CommandTypes(String hexa){
		this.hexa = hexa;
	}

}
