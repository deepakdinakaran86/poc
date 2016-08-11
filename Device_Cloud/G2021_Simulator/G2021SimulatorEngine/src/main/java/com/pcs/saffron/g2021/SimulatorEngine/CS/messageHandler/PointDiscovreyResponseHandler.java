package com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.HeaderUtil;

/**
 * 
 * @author Santhosh
 *
 */
public class PointDiscovreyResponseHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PointDiscovreyResponseHandler.class);

	public static byte[] getPointDiscoveryResponseMessage(Points[] object, int startPosition,
			int endPosition) {		
		Header responseHeader = HeaderUtil.getPointDiscoveryResponseHeader();		
		//LOGGER.info("getPointDiscoveryResponseMessage :::  header : " + responseHeader);
		if (responseHeader != null) {
			return constructPointDiscoveryResponseByteMsg(responseHeader, object, startPosition,
					endPosition);
		}
		return null;

	}

	private static byte[] constructPointDiscoveryResponseByteMsg(Header header, Points[] points, int startPosition,
			int endPosition) {
		Integer headerLength = 11;
		Integer pointsLength = endPosition;
		Integer bufferLength = headerLength; // 2 is the total message
													// length
		bufferLength = getBufferLength(points, startPosition, endPosition, bufferLength);
		bufferLength += 1;// 1 is the points length
		ByteBuffer buffer = ByteBuffer.allocate(bufferLength+2); 
		//LOGGER.info("Buffer allocated size : {}", bufferLength);
		buffer.putShort(bufferLength.shortValue());
		buffer.put(header.getPacketType().getType().byteValue());
		buffer.put(header.getMessageType().getType().byteValue());
		buffer.put(header.getSeqNumber().byteValue());
		buffer.putInt(header.getSessionId());
		buffer.putInt(header.getUnitId());
		
		//points data
		buffer.put(pointsLength.byteValue());

			for (int i = startPosition; i < endPosition; i++) {
	
				buffer.putShort(points[i].getpID());
	
				if (points[i].getpName() != null) {
					Integer pNameLength = points[i].getpName().length();
					if (pNameLength != 0) {
						buffer.put(pNameLength.byteValue());
						for (int i1 = 0; i1 < pNameLength; i1++) {
							Integer acsii = (int) points[i].getpName().toCharArray()[i1];
							buffer.put(acsii.byteValue());
						}
					}
				} else {
					Integer pNameLength = 0;
					buffer.put(pNameLength.byteValue());
				}
	
				if (points[i].getpUnit() != null) {
					Integer pUnitLength = points[i].getpUnit().length();
					if (pUnitLength != 0) {
						buffer.put(pUnitLength.byteValue());
						for (int i1 = 0; i1 < pUnitLength; i1++) {
							Integer acsii = (int) points[i].getpUnit().toCharArray()[i1];
							buffer.put(acsii.byteValue());
						}
					}
				} else {
					Integer pUnitLength = 0;
					buffer.put(pUnitLength.byteValue());
				}
	
				buffer.put(points[i].getpType().byteValue());
				buffer.put(points[i].getpDataType().byteValue());
				buffer.put(points[i].getpDAQType().byteValue());
				buffer.putShort(points[i].getpDAQTime().shortValue());
				buffer.putFloat(points[i].getpDAQCOV_TH());
				buffer.put(points[i].getpAlarmType().byteValue());
				buffer.put(points[i].getpAlarmCriticality().byteValue());
				buffer.putFloat(points[i].getpAlarm_LT());
				buffer.putFloat(points[i].getpAlarm_UT());
	
				if (points[i].getNormalTEXT() != null) {
					Integer textLength = points[i].getNormalTEXT().length();
					if (textLength != 0) {
						buffer.put(textLength.byteValue());
						for (int i1 = 0; i1 < textLength; i1++) {
							Integer acsii = (int) points[i].getNormalTEXT().toCharArray()[i1];
							buffer.put(acsii.byteValue());
						}
					}
				} else {
					Integer textLength = 0;
					buffer.put(textLength.byteValue());
				}
	
				if (points[i].getOffnormalTEXT() != null) {
					Integer textLength = points[i].getOffnormalTEXT().length();
					if (textLength != 0) {
						buffer.put(textLength.byteValue());
						for (int i1 = 0; i1 < textLength; i1++) {
							Integer acsii = (int) points[i].getOffnormalTEXT().toCharArray()[i1];
							buffer.put(acsii.byteValue());
						}
					}
				} else {
					Integer textLength = 0;
					buffer.put(textLength.byteValue());
				}
	
				if (points[i].getOffnormalTEXT_LT() != null) {
					Integer textLength = points[i].getOffnormalTEXT_LT().length();
					if (textLength != 0) {
						buffer.put(textLength.byteValue());
						for (int i1 = 0; i1 < textLength; i1++) {
							Integer acsii = (int) points[i].getOffnormalTEXT_LT().toCharArray()[i1];
							buffer.put(acsii.byteValue());
						}
					}
				} else {
					Integer textLength = 0;
					buffer.put(textLength.byteValue());
				}
	
				if (points[i].getOffnormalTEXT_UT() != null) {
					Integer textLength = points[i].getOffnormalTEXT_UT().length();
					if (textLength != 0) {
						buffer.put(textLength.byteValue());
						for (int i1 = 0; i1 < textLength; i1++) {
							Integer acsii = (int) points[i].getOffnormalTEXT_UT().toCharArray()[i1];
							buffer.put(acsii.byteValue());
						}
					}
				} else {
					Integer textLength = 0;
					buffer.put(textLength.byteValue());
				}
	
			}

			LOGGER.info("Processed PointDiscoveryResponse Message!!!\n PointDiscoveryResponse Message : "+ConversionUtils.getHex(buffer.array()));
			return buffer.array();
	}

	private static Integer getBufferLength(Points[] points, int startPosition, int endPosition, int bufferLength) {

		for (int i = startPosition; i < endPosition; i++) {

			// pId is short
			bufferLength += 2;

			// pName is String
			if (points[i].getpName() == null) {
				bufferLength++;
			} else {
				bufferLength += points[i].getpName().toCharArray().length + 1;
			}

			// pUnit is String
			if (points[i].getpUnit() == null) {
				bufferLength++;
			} else {
				bufferLength += points[i].getpUnit().toCharArray().length + 1;
			}

			// pType is Byte
			bufferLength++;
			// pDataType is Byte
			bufferLength++;
			// pDAQType is Byte
			bufferLength++;
			// pDAQTime is short
			bufferLength += 2;
			// pDAQCOV_TH is Float
			bufferLength += 4;
			// pAlarmType is Byte
			bufferLength++;
			// pAlarmCriticality is Byte
			bufferLength++;
			// pAlarm_LT is Float
			bufferLength += 4;
			// pAlarm_UT is Float
			bufferLength += 4;

			// normalText is String
			if (points[i].getNormalTEXT() == null) {
				bufferLength++;
			} else {
				bufferLength += points[i].getNormalTEXT().toCharArray().length + 1;
			}

			// offnormalTEXT is String
			if (points[i].getOffnormalTEXT() == null) {
				bufferLength++;
			} else {
				bufferLength += points[i].getOffnormalTEXT().toCharArray().length + 1;
			}

			// offnormalTEXT_LT is String
			if (points[i].getOffnormalTEXT_LT() == null) {
				bufferLength++;
			} else {
				bufferLength += points[i].getOffnormalTEXT_LT().toCharArray().length + 1;
			}

			// offnormalTEXT_UT is String
			if (points[i].getOffnormalTEXT_UT() == null) {
				bufferLength++;
			} else {
				bufferLength += points[i].getOffnormalTEXT_UT().toCharArray().length + 1;
			}

		}	
		return bufferLength;
	}

}
