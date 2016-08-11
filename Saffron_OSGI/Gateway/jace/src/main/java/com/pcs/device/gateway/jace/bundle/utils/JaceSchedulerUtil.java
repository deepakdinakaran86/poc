package com.pcs.device.gateway.jace.bundle.utils;

import com.pcs.saffron.scheduler.util.SchedulerUtil;

public final class JaceSchedulerUtil {


	private static SchedulerUtil schedulerUtil = null;

	/**
	 * @return the cacheProvider
	 */
	public static SchedulerUtil getSchedulerUtil() {
		return schedulerUtil;
	}

	/**
	 * @param schedulerUtil the cacheProvider to set
	 */
	public static void setSchedulerUtil(SchedulerUtil schedulerUtil) {
		if(JaceSchedulerUtil.schedulerUtil == null)
			JaceSchedulerUtil.schedulerUtil = schedulerUtil;
	}

	/**
	 * @param cacheProvider the cacheProvider to set
	 */
	public static void resetSchedulerUtil(SchedulerUtil cacheProvider) {
		if(cacheProvider == null)
			JaceSchedulerUtil.schedulerUtil = null;
	}

}
