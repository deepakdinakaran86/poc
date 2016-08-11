package com.pcs.saffron.g2021.SimulatorEngine.CS.messageHandler;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.DeviceAuthenticateMessage;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.header.Header;
import com.pcs.saffron.g2021.SimulatorEngine.CS.session.SessionInfo;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.ConversionUtils;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.HeaderUtil;
import com.pcs.saffron.g2021.SimulatorEngine.CS.util.UtilConstants;

/**
 * 
 * @author Santhosh
 *
 */

public class AuthenticateHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateHandler.class);

	public static byte[] getAuthenticatedMsg() {
		Header authenticateHeader = HeaderUtil.getAuthenticateHeader();	
		DeviceAuthenticateMessage deviceMsg = prepareDeviceMsg();
		if (authenticateHeader != null) {
			return constructAuthenticateByteMsg(authenticateHeader, deviceMsg);
		}
		return null;

	}

	private static byte[] constructAuthenticateByteMsg(Header header, DeviceAuthenticateMessage device) {
		int headerLength = 11;
		Integer bufferLength = headerLength + 4;
		if (device.getCcKey() != null) {
			bufferLength += device.getCcKey().toCharArray().length+1;
		}else{
			bufferLength += 1;
		}
		ByteBuffer buffer = ByteBuffer.allocate(bufferLength + 2);
		//LOGGER.info("Buffer allocated size : " + bufferLength);
		buffer.putShort(bufferLength.shortValue());
		buffer.put(header.getPacketType().getType().byteValue());
		buffer.put(header.getMessageType().getType().byteValue());
		buffer.put(header.getSeqNumber().byteValue());
		buffer.putInt(header.getSessionId());
		buffer.putInt(header.getUnitId());		
		buffer.putInt(device.getDeviceTimeOut());
			if(device.getCcKey() != null){
				Integer length = device.getCcKey().length();
				buffer.put(length.byteValue());
				for(int i=0;i<length;i++){
					Integer acsii = (int) device.getCcKey().toCharArray()[i];
					buffer.put(acsii.byteValue());
				}
			}else{
				Integer ccKey = 0;
				buffer.put(ccKey.byteValue());
			}
		LOGGER.info("Processed Device Authenticate Message!!!\n Device Authenticate Message : "+ConversionUtils.getHex(buffer.array()));
		return buffer.array();
	}

	private static DeviceAuthenticateMessage prepareDeviceMsg() {
		DeviceAuthenticateMessage deviceMsg = new DeviceAuthenticateMessage();		
		deviceMsg.setDeviceTimeOut(UtilConstants.DEVICE_RECEIVE_TIMEOUT);
		deviceMsg.setCcKey(SessionInfo.getInstance().getSubscriptionKey());
		return deviceMsg;
	}

}
