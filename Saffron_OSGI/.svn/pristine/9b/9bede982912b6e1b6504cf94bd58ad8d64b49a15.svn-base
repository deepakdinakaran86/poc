package com.pcs.device.gateway.G2021.message.processor;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.message.WelcomeMessage;
import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.saffron.connectivity.utils.ConversionUtils;

public class WelcomeProcessor {

	private PacketType packetType = PacketType.ANONYMOUS;
	private MessageType messageType = MessageType.WELCOME;
	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeProcessor.class);
	
	public byte[] getWelcomeMessage(WelcomeMessage welcomeMessage){
		Integer domainNameLength = welcomeMessage.getDataserverDomainName()!=null?welcomeMessage.getDataserverDomainName().length():0;
		
		ByteBuffer welcomeBuffer = ByteBuffer.allocate(domainNameLength+18);
		welcomeBuffer.put(packetType.getType().byteValue());
		welcomeBuffer.put(messageType.getType().byteValue());
		welcomeBuffer.putInt(welcomeMessage.getServerTimestamp()/1000);
		welcomeBuffer.putShort(welcomeMessage.getDataserverPort());
		welcomeBuffer.put(welcomeMessage.getDataserverType());
		if(welcomeMessage.getDataserverIP() != null && welcomeMessage.getDataserverIP().split("\\.").length==4){
			for (int i = 0; i < welcomeMessage.getDataserverIP().split("\\.").length; i++) {
				Integer ipAddressSplit = Integer.parseInt(welcomeMessage.getDataserverIP().split("\\.")[i]);
				welcomeBuffer.put(ipAddressSplit.byteValue());
			}
		}else{
			LOGGER.error("Invalid data server IP");
		}
		if(domainNameLength != 0){
			welcomeBuffer.put(domainNameLength.byteValue());
			for (int i = 0; i < welcomeMessage.getDataserverDomainName().toCharArray().length; i++) {
				Integer asciiInt = (int)welcomeMessage.getDataserverDomainName().toCharArray()[i];
				welcomeBuffer.put(asciiInt.byteValue());
			}
		}
		
		
		byte[] array = welcomeBuffer.array();
		LOGGER.info("Processed Welcome Message!!!\n Welcome Message : "+ConversionUtils.getHex(array));
		return array; 
	}
}
