/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

/**
 * @author pcseg171
 *
 */
public class Geopoint extends Point {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3063048994864846470L;
	
	private PointDataTypes TYPE = PointDataTypes.GEOPOINT;
	private static final Logger LOGGER = LoggerFactory.getLogger(Geopoint.class);
	
	private Float latitude;
	private Float longitude;
	
	
	

	public Float getLatitude() {
		return latitude;
	}

	public Float getLongitude() {
		return longitude;
	}
	
	public void setLocation(Float latitude,Float longitude) throws Exception{
		if(latitude != null && longitude != null){
			this.latitude = latitude;
			this.longitude = longitude;
			Float[] location = {this.latitude,this.longitude};
			setData(location);
		}else{
			LOGGER.error("Invalid location details !! [Latitude : {}, Longitude : {}]",latitude,longitude);
		}
		
	}


	@Override
	public final void setData(Object value) throws Exception {
		super.setData(value);
	}


	/**
	 * Default constructor.
	 */
	protected Geopoint(){
		super.setType(TYPE.name());
	}
	
	/**
	 * Named constructor
	 * @param name name of the point
	 * @throws Exception
	 */
	protected Geopoint(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exe) {
			LOGGER.error("Error initalizing Analog Point under name : "+name,exe);
			throw new Exception("Error initalizing Analog Point under name : "+name,exe);
		}
	}
	
	
	
	
}
