package com.pcs.saffron.cipher.data.point.utils;

import java.util.Comparator;

import com.pcs.saffron.cipher.data.point.Point;

public class PointComparator implements Comparator<Point> {

	public int compare(Point point1, Point point2) {
		return point1.getPointId().compareToIgnoreCase(point2.getPointId());
	}

}
