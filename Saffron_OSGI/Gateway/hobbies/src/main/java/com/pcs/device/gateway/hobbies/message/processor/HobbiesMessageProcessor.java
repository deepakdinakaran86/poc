package com.pcs.device.gateway.hobbies.message.processor;

import com.pcs.device.gateway.hobbies.message.HobbiesMessage;
import com.pcs.saffron.cipher.identity.bean.Gateway;

public interface HobbiesMessageProcessor {

	public void processMessage(HobbiesMessage hobbiesMessage,Gateway gateway);
	public Object getServerMesssage();
}
