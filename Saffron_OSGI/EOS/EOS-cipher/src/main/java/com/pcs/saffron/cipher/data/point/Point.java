/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.extension.AlarmExtension;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.metrics.utilities.PhysicalQuantityResolver;

/**
 * Base interface for all types of points.
 * @author PCSEG171
 *
 */
public class Point extends DataPoint{


	public static final String UNITLESS = "unitless";
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

	public void setDisplayName(String customTag) {
		this.displayName = customTag;
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

	private final void setPhysicalQuantity(String physicalQuantity) {
		if(unit == null)
			return;
		this.physicalQuantity = physicalQuantity;
	}


	public void setUnit(String unit){
		if(unit != null && physicalQuantity == null){
			this.unit = unit;
			resolvePhysicalQuantity(unit);
		}else{
			this.unit = PhysicalQuantityResolver.UNITLESS;
		}
	}


	private void resolvePhysicalQuantity(String unit) {
		this.unit = PhysicalQuantityResolver.resolveUnit(unit);
		String physicalQuantity = PhysicalQuantityResolver.resolve(unit);

		if(physicalQuantity.equalsIgnoreCase(PhysicalQuantityResolver.GENERIC_QUANTITY) && type != null){
			this.unit = UNITLESS;
			PointDataTypes dataTypes = PointDataTypes.valueOfType(type);
			switch (dataTypes) {
			case BOOLEAN:
				physicalQuantity = "status boolean";					
				break;
			case STRING:
				physicalQuantity = "status string";
				break;
			case TEXT:
				physicalQuantity = "status string";
				break;
			case INTEGER:
				physicalQuantity = "status integer";
				break;
			case DOUBLE:
				physicalQuantity = "status double";
				break;
			case LONG:
				physicalQuantity = "status long";
				break;
			case FLOAT:
				physicalQuantity = "status float";
				break;
			case SHORT:
				physicalQuantity = "status short";
				break;
			case NUMERIC:
				physicalQuantity = "status numeric";
				break;
			case LATITUDE:
				physicalQuantity = "latitude";
				break;
			case LONGITUDE:
				physicalQuantity = "longitude";
				break;
			case GEOPOINT:
				physicalQuantity = "location";
				break;
			default:
				physicalQuantity = "status integer";
				break;
			}
		}
		setPhysicalQuantity(physicalQuantity);
	}

	public String getUnit(){
		return unit;
	}


	@Override
	public void setType(String type) {
		this.type = PointDataTypes.valueOf(type).getType(); 
		if(unit !=null){
			resolvePhysicalQuantity(unit);
		}
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
			return new StringText();
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
		case LATITUDE:
			return new Latitude();
		case LONGITUDE:
			return new Longitude();
		case LONG:
			return new NumericLong();
		case GEOPOINT:
			return new Geopoint();
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
		Point point = new Point();
		point.setType("STRING");
		System.err.println(point.getType());
		point.setType(point.getType());
		System.err.println(point.getType());
	}
}
