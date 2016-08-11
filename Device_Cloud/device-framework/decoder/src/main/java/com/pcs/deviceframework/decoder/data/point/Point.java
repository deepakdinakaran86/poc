/**
 * 
 */
package com.pcs.deviceframework.decoder.data.point;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.deviceframework.decoder.data.point.extension.AlarmExtension;
import com.pcs.deviceframework.decoder.data.point.extension.PointExtension;
import com.pcs.deviceframework.decoder.data.point.types.PointDataTypes;

/**
 * Base interface for all types of points.
 * @author PCSEG171
 *
 */
public class Point extends DataPoint{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3854621679790316040L;
	private static final String STATUS = "STATUS";
	public static final String GENERIC_QUANTITY = "generic_quantity";
	private static final String POINT_ACCESS_TYPE = "ACCESS TYPE";
	
	private static Logger logger = LoggerFactory.getLogger(Point.class);

	private String displayName;
	private String systemTag;
	private String className;
	private String physicalQuantity;

	

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSystemTag() {
		return systemTag;
	}

	public void setSystemTag(String systemTag) {
		this.systemTag = systemTag;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public String getPhysicalQuantity() {
		if(physicalQuantity == null)
			physicalQuantity = GENERIC_QUANTITY;
		return physicalQuantity;
	}

	public void setPhysicalQuantity(String physicalQuantity) {
		this.physicalQuantity = physicalQuantity;
	}
	

	@Override
	public void setType(String type) {
		this.type = PointDataTypes.valueOf(type).getType(); 
	}

	public List<PointExtension> getExtensions() {
		return extensions;
	}
	
	

	@Override
	public void setStatus(String status) {
		addExtension(new PointExtension(status, STATUS));
		super.setStatus(status);
	}
	
	
	@Override
	public void setPointAccessType(String pointAccessType) {
		addExtension(new PointExtension(pointAccessType, POINT_ACCESS_TYPE));
		super.setPointAccessType(pointAccessType);
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
		if(alarmExtensions == null)
			return;

		if(this.alarmExtensions == null){
			this.alarmExtensions = new ArrayList<AlarmExtension>();
		}
		this.alarmExtensions.removeAll(alarmExtensions);
		this.alarmExtensions.addAll(alarmExtensions);
	}

	public static final Point getPoint(Integer type){

		PointDataTypes choice = PointDataTypes.valueOfType(type);

		switch (choice) {
		case NUMERIC:
			return new Numeric();
		case BOOLEAN:
			return new Boolean();
		case DATE:
			return new Date();
		case BASE:
			return new Point();
		case INTEGER:
			return new NumericInteger();
		case FLOAT:
			return new NumericFloat();
		case SHORT:
			return new NumericShort();
		case DOUBLE:
			return new NumericLong();
		case STRING:
			return new StringText();

		default:
			return null;
		}
	}
	
	public PointExtension getExtensionByType(String extensionType){
		if(extensions != null & !extensions.isEmpty()){
			for (PointExtension extension : extensions) {
				if(extension.getExtensionType().equalsIgnoreCase(extensionType))
					return extension;
			}
			return null;
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		new Point().setType("6");
	}
}
