package com.pcs.saffron.g2021.SimulatorEngine.CS.util;
/**
 * 
 * @author Santhosh
 *
 */

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.type.MessageType;
import com.pcs.saffron.g2021.SimulatorEngine.CS.packet.type.PacketType;
import com.pcs.saffron.g2021.SimulatorEngine.CS.session.SessionInfo;

public class HeaderUtil {
	
	private static SessionInfo session = SessionInfo.getInstance();
	
	public static Header getHelloHeader(){
		Header header = new Header();
		header.setMessageType(MessageType.HELLO);
		header.setPacketType(PacketType.ANONYMOUS_WITH_ASSURED_DELIVERY);
		header.setSeqNumber(SequenceNoGenerator.sequenceNoIncreamentor());
		header.setSessionId(UtilConstants.SESSION_ID);
		header.setUnitId(UtilConstants.UNIT_ID);		
		return header;		
	}
	
	public static Header getAuthenticateHeader(){
		Header header = new Header();
		header.setMessageType(MessageType.AUTHENTICATE);
		header.setPacketType(PacketType.ANONYMOUS_WITH_ASSURED_DELIVERY);
		header.setSeqNumber(SequenceNoGenerator.sequenceNoIncreamentor());		
		if(session.getSessionId() != null && session.getUnitId() != null){			
			header.setSessionId(session.getSessionId());
			header.setUnitId(session.getUnitId());
		}else{
			return null;
		}			
		return header;		
	}

	public static Header getPointDiscoveryResponseHeader() {
		Header header = new Header();
		header.setMessageType(MessageType.POINTDISCOVERYRESPONSE);
		header.setPacketType(PacketType.IDENTIFIED_WITH_ASSURED_DELIVERY);
		header.setSeqNumber(SequenceNoGenerator.sequenceNoIncreamentor());
		if (session.getSessionId() != null && session.getUnitId() != null) {
			header.setSessionId(session.getSessionId());
			header.setUnitId(session.getUnitId());
		} else {
			return null;
		}
		return header;
	}
	
	public static Header getPointScordCardHeader() {
		Header header = new Header();
		header.setMessageType(MessageType.POINTDISCOVERYSCORECARD);
		header.setPacketType(PacketType.IDENTIFIED_WITH_ASSURED_DELIVERY);
		header.setSeqNumber(SequenceNoGenerator.sequenceNoIncreamentor());
		if (session.getSessionId() != null && session.getUnitId() != null) {
			header.setSessionId(session.getSessionId());
			header.setUnitId(session.getUnitId());
		} else {
			return null;
		}
		return header;
	}
	
	public static Header getDataHeader(){
		Header header = new Header();
		header.setMessageType(MessageType.POINTREALTIMEDATA);
		header.setPacketType(PacketType.IDENTIFIED_WITH_ASSURED_DELIVERY);
		header.setSeqNumber(SequenceNoGenerator.sequenceNoIncreamentor());	
		//session.setSessionId(1434747860);
		//session.setUnitId(2);
		if (session.getSessionId() != null && session.getUnitId() != null) {
			header.setSessionId(session.getSessionId());
			header.setUnitId(session.getUnitId());
		} else {
			return null;
		}
		return header;		
	}
	
	public static Header getPointAlarmHeader(){
		Header header = new Header();
		header.setMessageType(MessageType.POINTALARM);
		header.setPacketType(PacketType.IDENTIFIED_WITH_ASSURED_DELIVERY);
		header.setSeqNumber(SequenceNoGenerator.sequenceNoIncreamentor());	
		//session.setSessionId(1434747860);
		//session.setUnitId(2);
		if (session.getSessionId() != null && session.getUnitId() != null) {
			header.setSessionId(session.getSessionId());
			header.setUnitId(session.getUnitId());
		} else {
			return null;
		}
		return header;		
	}
}
