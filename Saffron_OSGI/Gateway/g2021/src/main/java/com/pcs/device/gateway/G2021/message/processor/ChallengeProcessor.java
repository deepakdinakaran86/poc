package com.pcs.device.gateway.G2021.message.processor;

import java.nio.ByteBuffer;

import com.pcs.device.gateway.G2021.message.ChallengeMessage;
import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.device.gateway.G2021.packet.type.PacketType;

public class ChallengeProcessor  {
	
	private MessageType messageType = MessageType.CHALLENGE;
	private PacketType packetType = PacketType.ANONYMOUS;

	public byte[] getChallengeMessage(ChallengeMessage challengeMessage){
		Integer subscriptionKeyLength = challengeMessage.getSubscriptionKey()!=null?challengeMessage.getSubscriptionKey().length():1;
		int payloadLength = subscriptionKeyLength+15;
		ByteBuffer challengeBuffer = ByteBuffer.allocate(payloadLength);
		challengeBuffer.put(packetType.getType().byteValue());
		challengeBuffer.put(messageType.getType().byteValue());
		challengeBuffer.putInt(challengeMessage.getSessionId());
		challengeBuffer.putInt(challengeMessage.getUnitId());
		challengeBuffer.putInt(challengeMessage.getSessionTimeout());
		challengeBuffer.put(subscriptionKeyLength.byteValue());
		for (int i = 0; i < challengeMessage.getSubscriptionKey().getBytes().length; i++) {
			challengeBuffer.put(challengeMessage.getSubscriptionKey().getBytes()[i]);
		}
		return challengeBuffer.array();
	}
	
	public static void main(String[] args) {
		ChallengeProcessor challengeProcessor = new ChallengeProcessor();
		ChallengeMessage challengeMessage = new ChallengeMessage();
		challengeMessage.setSessionId(1);
		challengeMessage.setUnitId(1);
		challengeMessage.setSubscriptionKey("ABC");
		challengeMessage.setSessionTimeout(1000);
		byte[] byteMessage = challengeProcessor.getChallengeMessage(challengeMessage);
		System.out.println(byteMessage.length);
	}
}
