/**
 * 
 */
package com.pcs.device.gateway.G2021.command;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.device.gateway.G2021.message.type.MessageType;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.devicemanager.bean.DeviceConfiguration;

/**
 * @author pcseg171
 *
 */
public class PointWrite  {
	
	public static final PacketType PACKET_TYPE = PacketType.ANONYMOUS;
	public static final MessageType MESSAGE_TYPE = MessageType.POINTWRITECOMMAND;
	private static final Logger LOGGER = LoggerFactory.getLogger(PointWrite.class);
			

	public static byte[] getServerMessage(G2021Command g2021Command) throws Exception {

		Integer messageLength = 15;
		boolean isText = false;
		byte  flag;
		Integer textLength = 0;
		G2021DeviceConfiguration configuration = (G2021DeviceConfiguration) G2021DeviceManager.instance().getConfiguration(g2021Command.getSourceId());
		if(configuration == null){
			return null;
		}
		Point point = configuration.getPoint(g2021Command.getPointId().toString());
		G2021DataTypes pointType = G2021DataTypes.valueOf(point.getDataType());
		g2021Command.setDataType(point.getDataType());
		
		if(pointType == G2021DataTypes.STRING || pointType == G2021DataTypes.TEXT){
			textLength = (g2021Command.getData() == null?0:g2021Command.getData().toString().length());
			messageLength += textLength+1;
			isText = true;
		}else{
			messageLength += g2021Command.getDataType().getLength();
		}

		pointType = null;
		point = null;
		

		ByteBuffer pointData = ByteBuffer.allocate(messageLength);
		pointData.put(PACKET_TYPE.getType().byteValue());
		pointData.put(MESSAGE_TYPE.getType().byteValue());
		pointData.putInt(configuration.getSessionId());
		pointData.putInt(configuration.getUnitId());
		String flagBinary = g2021Command.getDataType().getType();
		
		flagBinary += ConversionUtils.convertToBinaryExt(Integer.toHexString(g2021Command.getPriority()),4);
		flag = ((Integer) ConversionUtils.convertBinaryToInt(flagBinary)).byteValue();
		
		pointData.put(flag);
		pointData.putShort(g2021Command.getPointId().shortValue());
		pointData.putShort(g2021Command.getRequestId());
		
		configuration = null;
		
		if(isText){
			pointData.put(textLength.byteValue());
			String text = g2021Command.getData().toString();
			char[] chars = text.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				pointData.put((byte)chars[i]);
			}
		}
		try {

			switch (g2021Command.getDataType()) {
			
			case BOOLEAN:
				Boolean statusData = new Boolean(g2021Command.getData().toString());
				pointData.put((byte)(statusData==true?1:0));
				break;

			case FLOAT:
				pointData.putFloat(Float.parseFloat(g2021Command.getData().toString()));
				break;

			case INT:
				pointData.putInt(Integer.parseInt(g2021Command.getData().toString()));
				break;

			case LONG:
				pointData.putLong(Long.parseLong(g2021Command.getData().toString()));
				break;

			case SHORT:
				pointData.putShort(Short.parseShort(g2021Command.getData().toString()));
				break;

			case TIMESTAMP:
				pointData.putInt(Integer.parseInt(g2021Command.getData().toString()));
				break;

			default:
				LOGGER.error("Illegal datatype received!!! cannot proceed!!");
				break;
			}

		} catch (Exception e) {
			throw new Exception("Invalid command, expected parameter "+g2021Command.getDataType()+" but found "+g2021Command.getData().getClass(),e);
		}
		
		return pointData.array();
	}
	
	
	public static void main(String[] args) throws Exception {
		G2021Command command = new G2021Command();
		command.setSourceId("412526757178");
		command.setData(1f);
		command.setPointId((short)22);
		command.setPriority((short)8);
		command.setRequestId((short)2);
		command.setSessionId(1);
		command.setUnitId(27);
		getServerMessage(command);
	}
}
