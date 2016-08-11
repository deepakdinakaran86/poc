/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.extension.AlarmExtension;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

/**
 * Base interface for all types of points.
 * @author PCSEG171
 *
 */
 class DataPoint implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1226370321559235636L;
	private static Logger logger = LoggerFactory.getLogger(Point.class);
	
	private String pointName;
	protected Integer type;
	private Object data;
	protected String unit;
	private String pointId;
	private String status = "healthy";
	private String pointAccessType;
	public List<PointExtension> extensions = new ArrayList<PointExtension>();
	public List<AlarmExtension> alarmExtensions = new ArrayList<AlarmExtension>();;

	protected DataPoint(){
	}

	protected DataPoint(String name){
		this.pointName = name;
	}

	protected DataPoint(String name, Object value){
		this.pointName = name;
		this.data = value;
	}

	/**
	 * Gets the type of the point. 
	 * @return type
	 */
	public String getType(){
		return PointDataTypes.valueOfType(type)!=null?PointDataTypes.valueOfType(type).name():"Inidentified Point type::"+type;
	}

	public void setType(String type) {
		this.type = PointDataTypes.valueOf(type).getType();
	}

	/**
	 * Sets the type of the point. 
	 * @return value of the point
	 */
	public final Object getData(){
		return data;
	}
	
	/**
	 * @param value sets the value of the point with the specifed value.
	 * @throws Exception 
	 */
	 public void setData(Object value) throws Exception{
		if(!(value.equals(null))){
			this.data = value;
		}
		else{
			logger.trace("Point value is null");
			throw new Exception();
		}
	}
	
	/**
	 * @param formula the formula to be applied on the original value of the point.
	 * @return returns the value of the point after applying the formula for calculation.
	 * @throws Exception 
	 */
	protected Object getCalculatedValue(String formula) throws Exception{
		if(!(formula.equals(null)&& formula.equalsIgnoreCase(""))){
			return null;
		}
		else{
			logger.trace("Formula value is null");
			throw new Exception();
		}
	}
	
	/**
	 * Sets the name of the point.The name of a point should be unique for a device.
	 * @param name 
	 * @throws Exception 
	 */
	public final void setPointName(String name) throws Exception{
		if(!(name.equals(null)&& name.equalsIgnoreCase(""))){
			this.pointName = name;
		}
		else{
			logger.trace("Point Name is null");
			throw new Exception();
		}	
	}
	
	/**
	 * gets the name of the point.
	 * @return
	 */
	public final String getPointName(){
		return pointName;
	}


	public String getPointId() {
		return pointId;
	}

	public void setPointId(String id) {
		this.pointId = id;
	}

	public List<PointExtension> getExtensions() {
		return extensions;
	}

	public void addExtension(PointExtension extension){
		if(extensions == null){
			extensions = new ArrayList<PointExtension>();
		}
		extensions.add(extension);
	}

	public void addExtensions(List<PointExtension> extensions){
		if(extensions == null)
			return;

		if(this.extensions == null){
			this.extensions = new ArrayList<PointExtension>();
		}
		this.extensions.removeAll(extensions);
		this.extensions.addAll(extensions);
	}

	public String getStatus() {
		return status;
	}

	protected void setStatus(String status) {
		this.status = status;
	}

	public String getPointAccessType() {
		return pointAccessType;
	}

	protected void setPointAccessType(String pointAccessType) {
		this.pointAccessType = pointAccessType;
	}

	public List<AlarmExtension> getAlarmExtensions() {
		return alarmExtensions;
	}

	public void setAlarmExtensions(List<AlarmExtension> alarmExtensions) {
		this.alarmExtensions = alarmExtensions;
	}

	public void addAlarmExtension(AlarmExtension alarmExt){
		if(alarmExtensions == null)
			alarmExtensions = new ArrayList<AlarmExtension>();
		alarmExtensions.add(alarmExt);
	}

	public void addAlarmExtensions(List<AlarmExtension> alarmExtensions){
		if(extensions == null)
			return;

		if(this.alarmExtensions == null){
			this.alarmExtensions = new ArrayList<AlarmExtension>();
		}
		this.alarmExtensions.removeAll(alarmExtensions);
		this.alarmExtensions.addAll(alarmExtensions);
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null)
			return false;
		else {
			Point newObj = (Point) obj;
			if (newObj.getPointId() == null) {
				logger.error("point id is empty ");
				return false;
			} else {
				if ((newObj.getPointId().equalsIgnoreCase(pointId)))
					return true;
				else
					return false;
			}
		}
	}

	@Override
	public final int hashCode() {
		Double hashCode = (Math.random()*pointName.length()*pointId.length());
		return hashCode.intValue();
	}
}
