package com.pcs.saffron.g2021.SimulatorEngine.CS.serverMessageHandler;

import java.util.ArrayList;
import java.util.List;

import com.pcs.saffron.g2021.SimulatorEngine.CS.listener.ServerMessageListener;

/**
 * 
 * @author Santhosh
 *
 */
public class ServerMessageNotifier {

	private static ServerMessageNotifier notifier;

	private List<ServerMessageListener> listeners = new ArrayList<ServerMessageListener>();

	private ServerMessageNotifier() {

	}

	public static ServerMessageNotifier getInstance() {

		if (notifier == null)
			notifier = new ServerMessageNotifier();
		return notifier;
	}

	public void addListener(ServerMessageListener listener) {
		listeners.add(listener);
	}

	public void notifyAcknowledgements(String msg, byte[] byteArray) {
		for (ServerMessageListener listener : listeners) {
			listener.notifyAcknowledgement(msg, byteArray);
		}
	}
	
	public void notifyDataServerRequests(Integer seqNo , byte[] dsServerReq) {
		for (ServerMessageListener listener : listeners) {
			listener.notifyDataServerRequests(seqNo, dsServerReq);
		}
	}

}
