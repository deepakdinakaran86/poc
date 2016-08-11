package com.pcs.device.gateway.enevo.onecollect.osgi.utils;

import com.pcs.saffron.scheduler.util.SchedulerUtil;

public final class EnevoSchedulerUtil {


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
		if(EnevoSchedulerUtil.schedulerUtil == null)
			EnevoSchedulerUtil.schedulerUtil = schedulerUtil;
	}

	/**
	 * @param cacheProvider the cacheProvider to set
	 */
	public static void resetSchedulerUtil(SchedulerUtil cacheProvider) {
		if(cacheProvider == null)
			EnevoSchedulerUtil.schedulerUtil = null;
	}

}
