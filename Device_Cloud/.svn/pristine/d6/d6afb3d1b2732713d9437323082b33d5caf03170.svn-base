package com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.HeaderUtil;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PAlarmState;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PDATAType;

public class AlarmHandler {


	private static final Logger LOGGER = LoggerFactory.getLogger(HelloHandler.class);

	public static byte[] getData(DataPoints dataPoints) throws Exception {
		Header dataHeader = HeaderUtil.getPointAlarmHeader();		
		return constructDataByteMsg(dataHeader, dataPoints);
	}

	private static byte[] constructDataByteMsg(Header header, DataPoints dataPoint) throws Exception {
		int headerLength = 11;
		Integer bufferLength = headerLength;
		bufferLength = getLength(dataPoint, bufferLength);

		ByteBuffer buffer = ByteBuffer.allocate(bufferLength + 2);
		//LOGGER.info("Buffer allocated size : {}", bufferLength);
		buffer.putShort(bufferLength.shortValue());
		buffer.put(header.getPacketType().getType().byteValue());
		buffer.put(header.getMessageType().getType().byteValue());
		buffer.put(header.getSeqNumber().byteValue());
		buffer.putInt(header.getSessionId());
		buffer.putInt(header.getUnitId());
		// inserting the payload



		buffer.putShort(dataPoint.getPoints().getpID());
		Long time = System.currentTimeMillis();
		buffer.putInt(time.intValue());	

		String flagData;
		byte b;

		switch (PAlarmState.getDataType(dataPoint.getCurrentAlarmState().getValue())) {
		case ALARM_LT:			
			flagData = PDATAType.getDataType(dataPoint.getPoints().getpDataType()).getBitData() + PAlarmState.ALARM_LT.getValue();
			b = Byte.parseByte(flagData, 2);
			buffer.put(b);
			prepareAlarmData(dataPoint, buffer);
			break;

		case ALARM_UT:
			flagData = PDATAType.getDataType(dataPoint.getPoints().getpDataType()).getBitData() + PAlarmState.ALARM_UT.getValue();
			b = Byte.parseByte(flagData, 2);
			buffer.put(b);
			prepareAlarmData(dataPoint, buffer);			
			break;

		case NORMALIZED:	
			flagData = PDATAType.getDataType(dataPoint.getPoints().getpDataType()).getBitData() + PAlarmState.NORMALIZED.getValue();
			b = Byte.parseByte(flagData, 2);
			buffer.put(b);
			prepareAlarmData(dataPoint, buffer);			
			break;

		case STATECHANGE:
			flagData = PDATAType.getDataType(dataPoint.getPoints().getpDataType()).getBitData() + PAlarmState.STATECHANGE.getValue();
			b = Byte.parseByte(flagData, 2);
			buffer.put(b);
			prepareAlarmData(dataPoint, buffer);			
			break;

		case NA:
			flagData = PDATAType.getDataType(dataPoint.getPoints().getpDataType()).getBitData() + PAlarmState.NA.getValue();
			b = Byte.parseByte(flagData, 2);
			buffer.put(b);
			prepareAlarmData(dataPoint, buffer);			
			break;

		default:
			break;
		}

		LOGGER.info("Processed Alarm  Message!!!\n Alarm Message : " + ConversionUtils.getHex(buffer.array()));
		return buffer.array();
	}

	private static void prepareAlarmData(DataPoints dPoint,ByteBuffer buffer){		

		if(dPoint != null){
			switch (PDATAType.getDataType(dPoint.getPoints().getpDataType())) {
			case Boolean:	
				Integer boolVal = 0;
				if ((Boolean) dPoint.getGeneratedValue()) {
					boolVal = 0;
				} else {
					boolVal = 1;
				}
				buffer.put(boolVal.byteValue());
				break;

			case Short:
				Short shortVal = (Short) dPoint.getGeneratedValue();
				buffer.putShort(shortVal);
				break;

			case Integer:
				Short s =  (Short) dPoint.getGeneratedValue();
				Integer intVal = s.intValue();
				buffer.putInt(intVal);
				break;

			case Float:
				Float floatVal = (Float) dPoint.getGeneratedValue();
				buffer.putFloat(floatVal);
				break;

			case Long:
				Short s1 =  (Short) dPoint.getGeneratedValue();
				Long longVal = s1.longValue();
				buffer.putLong(longVal);
				break;

			case String:
				String data = (String) dPoint.getGeneratedValue();

				if (data != null) {
					Integer length = data.length();
					if (length != 0) {
						buffer.put(length.byteValue());
						for (int i = 0; i < length; i++) {
							Integer acsii = (int) data.toCharArray()[i];
							buffer.put(acsii.byteValue());
						}
					}
				} else {
					Integer length = 0;
					buffer.put(length.byteValue());
				}
				break;

			default:
				break;
			}
		}		
	}	

	private static int getLength(DataPoints dPoint,Integer bufferLength){

		// pID -- short
		bufferLength = bufferLength+2;

		// TimeStamp -- 4 bytes
		bufferLength = bufferLength+4;

		// flag -- 1 byte
		bufferLength++;

		switch (PDATAType.getDataType(dPoint.getPoints().getpDataType())) {
		case Boolean:	
			bufferLength++;
			break;

		case Short:
			bufferLength = bufferLength +2;
			break;

		case Integer:
			bufferLength = bufferLength + 4;
			break;

		case Float:
			bufferLength = bufferLength + 4;
			break;

		case Long:
			bufferLength = bufferLength + 8;
			break;

		case String:
			if(dPoint.getGeneratedValue() == null && String.valueOf(dPoint.getGeneratedValue()).isEmpty()){
				bufferLength++;
			}else{
				bufferLength += String.valueOf(dPoint.getGeneratedValue()).toCharArray().length+1;				
			}
			break;

		default:
			break;
		}

		return bufferLength;		
	}



}
