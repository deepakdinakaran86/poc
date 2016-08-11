package com.pcs.deviceframework.decoder.data.point.utils;

import java.util.Comparator;

import com.pcs.deviceframework.decoder.data.point.Point;

public class PointComparator implements Comparator<Point> {

	@Override
	public int compare(Point point1, Point point2) {
		return point1.getPointId().compareToIgnoreCase(point2.getPointId());
	}

}
