package com.pcs.device.gateway.enevo.onecollect.osgi.bundle.utils;

import com.pcs.saffron.notification.handler.NotificationHandler;

/**
 * @author Aneesh
 *
 */
public final class MessageUtil {

	private static NotificationHandler notificationHandler;

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

	/**
	 * @param handler the notificationHandler to set
	 */
	public static void resetNotificationHandler(NotificationHandler handler) {
		if(handler == null)
			notificationHandler = null;
	}
}
