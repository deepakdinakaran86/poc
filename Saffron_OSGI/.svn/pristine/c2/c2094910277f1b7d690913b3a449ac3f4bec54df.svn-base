package com.pcs.saffron.manager.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author pcseg310
 *
 */
@JsonAutoDetect
public class Identifier implements Serializable {

    private static final long serialVersionUID = -2518458179555234663L;
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Identifier other = (Identifier) obj;
		if (key == null && other.key != null) {
			return false;
		} else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}
}
