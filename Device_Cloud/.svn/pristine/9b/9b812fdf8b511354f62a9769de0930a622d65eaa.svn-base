/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.model;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.pcs.datasource.model.udt.DeviceFieldMap;

/**
 * Model Class added to represent device data
 * 
 * @author pcseg199 (Javid Ahammed)
 * @author pcseg323 (Greeshma)
 * @date March 2015
 * @since galaxy-1.0.0
 */
public class Data {

	private UUID sourceId;
	private Long date;
	private Long deviceTime;
	private String data;
	private List<DeviceFieldMap> extensions;
	private String customTag;
	private String systemTag;
	private Long insertTime;
	private String unit;

	public UUID getSourceId() {
		return sourceId;
	}

	public void setSourceId(UUID sourceId) {
		this.sourceId = sourceId;
	}

	public Long getDate() {
		return date;
	}

	// this public setter method set date and insertTime
	public void setDate() {
		if (this.deviceTime != null) {
			DateTime deviceDate = new DateTime(this.deviceTime)
			        .toDateTime(DateTimeZone.UTC);
			this.date = deviceDate.withTimeAtStartOfDay().getMillis();
		}
		if (this.insertTime == null) {
			DateTime insertDate = new DateTime(DateTimeZone.UTC);
			setInsertTime(insertDate.getMillis());
		}

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<DeviceFieldMap> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<DeviceFieldMap> extensions) {
		this.extensions = extensions;
	}

	public Long getDeviceTime() {
		return deviceTime;
	}

	public void setDeviceTime(Long deviceTime) {
		this.deviceTime = deviceTime;
	}

	public String getCustomTag() {
		return customTag;
	}

	public void setCustomTag(String customTag) {
		this.customTag = customTag;
	}

	public String getSystemTag() {
		return systemTag;
	}

	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}

	public Long getInsertTime() {
		return insertTime;
	}

	// setter method is private. this field is set in setDate()
	private void setInsertTime(Long insertTime) {
		this.insertTime = insertTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
