package com.pcs.gateway.teltonika.message.analyzer;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import com.pcs.saffron.manager.bean.ConfigPoint;

public class DeviceConfigurationComparator implements Comparator<ConfigPoint> {

	public int compare(ConfigPoint configPoint1, ConfigPoint configPoint2) {
		if (configPoint1 != null && configPoint2 != null
				&& StringUtils.isNotBlank(configPoint1.getPrecedence())
				&& StringUtils.isNotBlank(configPoint2.getPrecedence())) {
			return Integer.parseInt(configPoint1.getPrecedence()) - Integer
					.parseInt(configPoint2.getPrecedence()) ;
		}
		return 0;
	}

}
