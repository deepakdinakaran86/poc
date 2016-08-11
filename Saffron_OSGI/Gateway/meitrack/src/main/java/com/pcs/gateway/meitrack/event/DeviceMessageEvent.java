/**
 * 
 */
package com.pcs.gateway.meitrack.event;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import com.pcs.saffron.cipher.data.message.Message;


/**
 * @author PCSEG171
 *
 */
public class DeviceMessageEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6679029674643098828L;
	
	private List<Message> messages = new ArrayList<Message>();
	private Message newMessage = null;
	private Message historyMessage = null;

	public DeviceMessageEvent(Object source) {
		super(source);
	}

	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	/**
	 * @return the newMessage
	 */
	public Message getNewMessage() {
		return newMessage;
	}

	/**
	 * @param newMessage the newMessage to set
	 */
	public void setNewMessage(Message newMessage) {
		this.newMessage = newMessage;
	}

	/**
	 * @return the historyMessage
	 */
	public Message getHistoryMessage() {
		return historyMessage;
	}

	/**
	 * @param historyMessage the historyMessage to set
	 */
	public void setHistoryMessage(Message historyMessage) {
		this.historyMessage = historyMessage;
	}

	
}
