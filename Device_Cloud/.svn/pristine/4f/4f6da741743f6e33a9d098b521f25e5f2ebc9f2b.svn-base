/**
 * 
 */
package com.pcs.datasource.dto;

import java.util.List;

import com.pcs.datasource.dto.writeback.GeoData;
import com.pcs.datasource.model.udt.DeviceFieldMap;

/**
 * This class is responsible for device data(for mapping from cassandra db)
 * 
 * @author pcseg129(Seena Jyothish) May 21, 2016
 */
public class DeviceGeoData extends TimeSeries {

	/**
	 *
	 */
	private static final long serialVersionUID = 3078198646611708058L;

	GeoData data;
	private List<DeviceFieldMap> extensions;

	public GeoData getData() {
		return data;
	}

	public void setData(GeoData data) {
		this.data = data;
		super.setData(data);
	}

	public List<DeviceFieldMap> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<DeviceFieldMap> extensions) {
		this.extensions = extensions;
	}

}
