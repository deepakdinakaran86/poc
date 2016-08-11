/**
 * 
 */
package com.pcs.deviceframework.devicemanager.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pcs.deviceframework.decoder.data.point.Point;


/**
 * @author pcseg310
 *
 */
public class DeviceConfiguration implements Serializable {

	private static final long serialVersionUID = -8771992434686224196L;
	
	private Map<Object, Point> pointMapping = new HashMap<Object, Point>();
	List<ConfigPoint> configPoints = new ArrayList<ConfigPoint>();
	private Boolean changed = false;
	private String deviceIP;
	private Integer deviceConnectedPort;
	private Integer deviceWritebackPort;
	
	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public Integer getDeviceConnectedPort() {
		return deviceConnectedPort;
	}

	public void setDeviceConnectedPort(Integer deviceConnectedPort) {
		this.deviceConnectedPort = deviceConnectedPort;
	}

	public Integer getDeviceWritebackPort() {
		return deviceWritebackPort;
	}

	public void setDeviceWritebackPort(Integer deviceWritebackPort) {
		this.deviceWritebackPort = deviceWritebackPort;
	}

	public Map<Object, Point> getPointMapping() {
		return pointMapping;
	}

	public void setPointMapping(Map<Object, Point> pointMapping) {
		this.pointMapping = pointMapping;
	}
	
	public final void addPointMapping(Object pointId,Point point){
		if(point != null){
			this.pointMapping.put(pointId, point);
		}else{
		}
	}
	
	public final void addPointMappings(List<Point> points){
		if(points != null){
			for (Point point : points) {
				addPointMapping(point.getPointId(),point);
			}
		}
	}
	
	public final void addConfigPoints(ConfigPoint configPoint){
		if(configPoint!=null){
			this.configPoints.add(configPoint);
		}
	}
	
	public Point getPoint(Object pointId){
		if(pointId != null){
			return this.pointMapping.get(pointId.toString());
		}else{
			return null;
		}
	}

	public List<ConfigPoint> getConfigPoints() {
		return configPoints;
	}

	public void setConfigPoints(
			List<ConfigPoint> configPointMapping) {
		this.configPoints = configPointMapping;
	}

	public Boolean hasChanged() {
		return changed;
	}

	public Boolean getChanged() {
		return changed;
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
	
}
