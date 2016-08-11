package com.pcs.device.gateway.G2021.bundle.utils;

import com.pcs.saffron.notification.handler.NotificationHandler;

/**
 * @author Aneesh
 *
 */
public final class MessageUtil {

	private static NotificationHandler notificationHandler;
	private static NotificationHandler diagnosisNotificationHandler;

	/**
	 * @return the notificationHandler
	 */
	public static NotificationHandler getNotificationHandler() {
		return notificationHandler;
	}

	/**
	 * @param handler the notificationHandler to set
	 */
	public static void setNotificationHandler(NotificationHandler handler) {
		if(notificationHandler == null)
			notificationHandler = handler;
	}

	public static NotificationHandler getDiagnosisNotificationHandler() {
		return diagnosisNotificationHandler;
	}

	public static void setDiagnosisNotificationHandler(
			NotificationHandler diagnosisNotificationHandler) {
		MessageUtil.diagnosisNotificationHandler = diagnosisNotificationHandler;
	}

	/**
	 * @param handler the notificationHandler to set
	 */
	public static void resetNotificationHandler(NotificationHandler handler) {
		if(handler == null)
			notificationHandler = null;
	}
	
	/**
	 * @param handler the notificationHandler to set
	 */
	public static void resetDiagnosisNotificationHandler(NotificationHandler handler) {
		if(handler == null)
			diagnosisNotificationHandler = null;
	}
}
