package com.pcs.device.gateway.ruptela.bundle.utils;

import com.pcs.saffron.notification.handler.NotificationHandler;

/**
 * @author Aneesh
 *
 */
public final class MessageUtil {

	private static NotificationHandler notificationHandler;
	private static NotificationHandler diagNotificationHandler;

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

	public static NotificationHandler getDiagNotificationHandler() {
		return diagNotificationHandler;
	}

	public static void setDiagNotificationHandler(
			NotificationHandler diagNotificationHandler) {
		MessageUtil.diagNotificationHandler = diagNotificationHandler;
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
	public static void resetDiagNotificationHandler(NotificationHandler handler) {
		if(handler == null)
			diagNotificationHandler = null;
	}
}
