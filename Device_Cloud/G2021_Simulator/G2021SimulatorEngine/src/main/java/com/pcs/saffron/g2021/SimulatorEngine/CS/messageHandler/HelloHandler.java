package com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.config.SimulatorConfiguration;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.HelloMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.AppTranistion;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.HeaderUtil;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.UtilConstants;

/**
 * 
 * @author Santhosh
 *
 */
public class HelloHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloHandler.class);

	public static byte[] getHello(SimulatorConfiguration config) throws Exception {
		Header helloHeader = HeaderUtil.getHelloHeader();
		HelloMessage helloMsg = constructHelloMsg(config);
		helloMsg.setCcKey(UtilConstants.CCKEY);
		helloMsg.setSimICCID(UtilConstants.SIM_ICCID);
		return constructHelloByteMsg(helloHeader, helloMsg);
	}

	private static byte[] constructHelloByteMsg(Header header, HelloMessage message) throws Exception {
		int headerLength = 11;
		Integer bufferLength = headerLength + 25;
		bufferLength = getLength(message, bufferLength);

		ByteBuffer buffer = ByteBuffer.allocate(bufferLength + 2);
		//LOGGER.info("Buffer allocated size : {}", bufferLength);
		buffer.putShort(bufferLength.shortValue());
		buffer.put(header.getPacketType().getType().byteValue());
		buffer.put(header.getMessageType().getType().byteValue());
		buffer.put(header.getSeqNumber().byteValue());
		buffer.putInt(header.getSessionId());
		buffer.putInt(header.getUnitId());
		// inserting the payload
		buffer.putLong(message.getMacId());
		buffer.putShort(message.getAssetId());
		buffer.put(message.getVersionId().byteValue());
		buffer.put(message.getCause().getType().byteValue());
		buffer.put(message.getCellularRSSi().byteValue());
		buffer.putFloat(message.getLatitude());
		buffer.putFloat(message.getLongitude());
		buffer.putInt(240);

		if (message.getCcKey() != null) {
			Integer cckeyLength = message.getCcKey().length();
			if (cckeyLength != 0) {
				buffer.put(cckeyLength.byteValue());
				for (int i = 0; i < cckeyLength; i++) {
					Integer acsii = (int) message.getCcKey().toCharArray()[i];
					buffer.put(acsii.byteValue());
				}
			}
		} else {
			Integer cckeyLength = 0;
			buffer.put(cckeyLength.byteValue());
		}

		if (message.getSimICCID() != null) {
			Integer simCCIDLength = message.getSimICCID().length();
			Integer length = simCCIDLength;
			if (simCCIDLength != 0) {
				buffer.put(length.byteValue());
				for (int i = 0; i < simCCIDLength; i++) {
					Integer acsii = (int) message.getSimICCID().toCharArray()[i];
					buffer.put(acsii.byteValue());
				}
			}
		} else {
			Integer simCCIDLength = 0;
			buffer.put(simCCIDLength.byteValue());
		}

		if (message.getClientInfo() != null) {
			for (String client : message.getClientInfo()) {
				if (client == null) {
					Integer clientInfo = 0;
					buffer.put(clientInfo.byteValue());
				} else {
					Integer byteLength = client.length();
					if (byteLength != 0) {
						buffer.put(byteLength.byteValue());
						for (int i = 0; i < byteLength; i++) {
							Integer acsii = (int) client.toCharArray()[i];
							buffer.put(acsii.byteValue());
						}
					}
				}

			}
		} else {
			Integer clientInfo = 0;
			buffer.put(clientInfo.byteValue());
		}
		LOGGER.info("Processed Hello Message!!!\n Hello Message : " + ConversionUtils.getHex(buffer.array()));
		return buffer.array();
	}

	private static int getLength(HelloMessage message, int length) {

		if (message.getCcKey() == null)
			length++;
		else
			length = length + message.getCcKey().toCharArray().length + 1;

		if (message.getSimICCID() == null)
			length++;
		else
			length = length + message.getSimICCID().toCharArray().length + 1;

		if (message.getClientInfo() != null) {
			// length = length + message.getClientInfo().size()+1;
			for (String client : message.getClientInfo()) {
				if (client == null) {
					length++;
				} else {
					length += client.toCharArray().length + 1;
				}
			}

			/*
			 * for(int k=0;k<message.getClientInfo().size();k++){ if }
			 */
		}

		return length;
	}

	private static HelloMessage constructHelloMsg(SimulatorConfiguration config) throws Exception {
		HelloMessage msg = new HelloMessage();
		msg.setMacId(config.getMacId());

		if (config.getSessionId() != null) {
			msg.setSessionId(config.getSessionId());
		} else {
			msg.setSessionId(UtilConstants.SESSION_ID);
		}

		if (config.getUnitId() != null) {
			msg.setUnitId(config.getUnitId());
		} else {
			msg.setUnitId(UtilConstants.UNIT_ID);
		}

		if (config.getAssetId() != null) {
			msg.setAssetId(config.getAssetId());
		} else {
			msg.setAssetId(UtilConstants.ASSET_ID);
		}

		if (config.getpVer() != null) {
			msg.setVersionId(config.getpVer());
		} else {
			msg.setVersionId(UtilConstants.P_VER);
		}

		if (config.getLatitude() != null) {
			msg.setLatitude(config.getLatitude());
		} else {
			msg.setLatitude(UtilConstants.LATTITUDE);
		}

		if (config.getLongitude() != null) {
			msg.setLongitude(config.getLongitude());
		} else {
			msg.setLongitude(UtilConstants.LONGITUDE);
		}

		if (config.getCellularRSSI() != null) {
			msg.setCellularRSSi(config.getCellularRSSI());
		} else {
			msg.setCellularRSSi(UtilConstants.CELLULAR_RSSI);
		}

		msg.setClientInfo(config.getClientInfo());
		msg.identifyReason(AppTranistion.getReason().getKey());
		return msg;
	}

}
