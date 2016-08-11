/**
 * 
 */
package com.pcs.datasource.enums;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish) Jun 7, 2016
 */
public enum WebsocketClient {
	WEBORB("Weborb"), MQTT("mqtt");

	private String wsClient;

	public String getWsClient() {
		return wsClient;
	}

	private WebsocketClient(String wsClient) {
		this.wsClient = wsClient;
	}

	public static WebsocketClient getWebsocketClient(String value) {
		for (WebsocketClient wsClient : values()) {
			if (wsClient.getWsClient().equalsIgnoreCase(value)) {
				return wsClient;
			}
		}
		return null;
	}
}
