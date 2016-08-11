package com.pcs.device.gateway.jace.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.jace.message.beans.PointDetail;
import com.pcs.saffron.manager.bean.ConfigPoint;

public class PointConfigurationUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PointConfigurationUtil.class);
	
	public static List<ConfigPoint> extractPointConfigurations(List<PointDetail> pointDetails){
		List<ConfigPoint> configPoints = new ArrayList<ConfigPoint>();
		try {
			for (PointDetail pointDetail : pointDetails) {
				ConfigPoint configPoint = new ConfigPoint();
				configPoints.add(configPoint);
				configPoint.setPointName(pointDetail.getPointName());
				configPoint.setPointId(pointDetail.getPointId());
				configPoint.setDisplayName(pointDetail.getPointName());
				configPoint.setPointAccessType(pointDetail.getAccessType());
				configPoint.setPrecedence("1");
				configPoint.setUnit(null);
				configPoint.setType(pointDetail.getDataType());
				configPoint.setDataType(pointDetail.getDataType());
				configPoint.addExtensions(pointDetail.getExtensions());
			}
		} catch (Exception e) {
			LOGGER.error("Error extracting point configuration",e);
		}
		return configPoints;
	}
	
}
