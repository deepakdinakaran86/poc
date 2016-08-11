package com.pcs.avocado.isadapter.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*
 * @author RIYAS PH (pcseg296)
 * @date September 2015
 * @since alpine-1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "uri", "value"})
@XmlRootElement(name = "claim")
public class UserClaim {
	@XmlElement(required = true)
	private String uri;
	@XmlElement(required = true)
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}