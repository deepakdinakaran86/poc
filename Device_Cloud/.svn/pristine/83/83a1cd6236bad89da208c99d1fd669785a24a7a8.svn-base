package com.pcs.analytics.core.extractor;

import java.util.List;
import java.util.Set;

import com.pcs.deviceframework.decoder.data.point.Point;



/**
 * @author pcseg129 (Seena Jyothish)
 * @date March 30 2015
 */
public abstract class IOExtractor {

	public abstract Point getSpecificData(Point configPoint) throws Exception ;
	List<Point> points;
	Set<String> dependencyPointIds;
	
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public Set<String> getDependencyPointIds() {
		return dependencyPointIds;
	}
	public void setDependencyPointIds(Set<String> pointIds) {
		this.dependencyPointIds = pointIds;
	}
	public final void addParamater(Point point){
		
	}

}

