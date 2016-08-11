package com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.HeaderUtil;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.UtilConstants;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.DataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.DTO.RealTimeDataPoints;
import com.pcs.saffron.g2021.SimulatorEngine.DS.enums.PDATAType;

public class RealTimeDataHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloHandler.class);

	public static byte[] getData(RealTimeDataPoints dataPoints) throws Exception {
		Header dataHeader = HeaderUtil.getDataHeader();
		return constructDataByteMsg(dataHeader, dataPoints);
	}

	private static byte[] constructDataByteMsg(Header header, RealTimeDataPoints message) throws Exception {
		int headerLength = 11;
		Integer bufferLength = headerLength + 1;
		bufferLength = getLength(message, bufferLength);

		ByteBuffer buffer = ByteBuffer.allocate(bufferLength + 2);
		try {

			// LOGGER.info("Buffer allocated size : {}", bufferLength);
			buffer.putShort(bufferLength.shortValue());
			buffer.put(header.getPacketType().getType().byteValue());
			buffer.put(header.getMessageType().getType().byteValue());
			buffer.put(header.getSeqNumber().byteValue());
			buffer.putInt(header.getSessionId());
			buffer.putInt(header.getUnitId());
			// inserting the payload
			Integer dataLength = message.getDataPoints().size();
			buffer.put(dataLength.byteValue());

			if (message != null && message.getDataPoints() != null && !message.getDataPoints().isEmpty()) {

				for (DataPoints dataPoint : message.getDataPoints()) {
					buffer.putShort(dataPoint.getPoints().getpID());
					Long time = System.currentTimeMillis();
					buffer.putInt(time.intValue());

					String flagData;
					byte b;

					switch (PDATAType.getDataType(dataPoint.getPoints().getpDataType())) {

					case Boolean:
						Integer value = 0;
						flagData = PDATAType.Boolean.getBitData() + UtilConstants.NORMAL_STATE;
						b = Byte.parseByte(flagData, 2);
						buffer.put(b);
						if ((Boolean) dataPoint.getGeneratedValue()) {
							value = 0;
						} else {
							value = 1;
						}
						buffer.put(value.byteValue());
						break;

					case Short:
						flagData = PDATAType.Short.getBitData() + UtilConstants.NORMAL_STATE;
						b = Byte.parseByte(flagData, 2);
						buffer.put(b);
						Short value1 = (Short) dataPoint.getGeneratedValue();
						buffer.putShort(value1);
						break;

					case Integer:
						flagData = PDATAType.Integer.getBitData() + UtilConstants.NORMAL_STATE;
						b = Byte.parseByte(flagData, 2);
						buffer.put(b);
						Short s =  (Short) dataPoint.getGeneratedValue();
						Integer intVal = s.intValue();
						buffer.putInt(intVal);
						break;

					case Float:
						flagData = PDATAType.Float.getBitData() + UtilConstants.NORMAL_STATE;
						b = Byte.parseByte(flagData, 2);
						buffer.put(b);
						Float value3 = (Float) dataPoint.getGeneratedValue();
						buffer.putFloat(value3);
						break;
						
					case Long:
						flagData = PDATAType.Long.getBitData() + UtilConstants.NORMAL_STATE;
						b = Byte.parseByte(flagData, 2);
						buffer.put(b);
						Short s1 =  (Short) dataPoint.getGeneratedValue();
						Integer intVal1 = s1.intValue();
						buffer.putInt(intVal1);
						break;

					case String:
						flagData = PDATAType.String.getBitData() + UtilConstants.NORMAL_STATE;
						b = Byte.parseByte(flagData, 2);
						buffer.put(b);
						String data = (String) dataPoint.getGeneratedValue();

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

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		LOGGER.info("Processed real time  Message!!!\n REALTIME Message : " + ConversionUtils.getHex(buffer.array()));
		return buffer.array();
	}

	private static int getLength(RealTimeDataPoints message, Integer bufferLength) {
		try {
			if (message != null && message.getDataPoints() != null && !message.getDataPoints().isEmpty()) {
				for (DataPoints dPoint : message.getDataPoints()) {
					// pID -- short
					bufferLength = bufferLength + 2;

					// TimeStamp -- 4 bytes
					bufferLength = bufferLength + 4;

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
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bufferLength;
	}

}
