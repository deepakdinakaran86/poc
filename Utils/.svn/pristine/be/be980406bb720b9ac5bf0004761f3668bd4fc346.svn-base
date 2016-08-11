package com.pcs.alpine.token;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fieldMap")
public class FieldMapDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6549593710348606191L;
	private String key;
	private String value;

	public FieldMapDTO() {

	}

	public FieldMapDTO(String key) {
		this.key = key;
	}

	public FieldMapDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}

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

	// FieldMapDto objects are UNIQUE based on Key alone.
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

		FieldMapDTO other = (FieldMapDTO) obj;
		if (key == null && other.key != null) {
			return false;
		} else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}
}
