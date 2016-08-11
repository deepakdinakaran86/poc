package com.pcs.saffron.manager.bean;

import java.io.Serializable;
import java.util.List;

import com.pcs.saffron.cipher.data.point.Point;

public class ProtocolPoints implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2188338436149756704L;
	
	private List<Point> points;

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	

}
