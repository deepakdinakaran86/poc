/**
 * 
 */
package com.pcs.data.store.utils;



/**
 * @author PCSEG171
 *
 */
public enum 
PointDataTypes {
	
	BASE(0x00,"Base"),
	BOOLEAN(0x01,"Boolean"),
	NUMERIC(0x02,"Numeric"),
	INTEGER(0x04,"Integer"),
	FLOAT(0x05,"Float"),
	DOUBLE(0x06,"Double"),
	SHORT(0x07,"Short"),
	LONG(0x08,"Long"),
	DATE(0x09,"Date"),
	STRING(0x10,"String"),
	CAN(0x11,"CAN"), 
	TEXT(0x12,"TEXT"),
	LATITUDE(0x14,"LATITUDE"),
	LONGITUDE(0x15,"LONGITUDE");
	
	
	private Integer type;
	private String desc;
	private static final PointDataTypes[] values = values();

	PointDataTypes(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static PointDataTypes valueOfType(Integer type) {
		if (type == null) {
			return null;
		}
		for (PointDataTypes packetType : values) {
			if (type.equals(packetType.getType())) {
				return packetType;
			}
		}

		return null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
