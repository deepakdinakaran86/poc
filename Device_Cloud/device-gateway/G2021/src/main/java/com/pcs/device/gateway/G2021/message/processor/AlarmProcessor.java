package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.command.SyncCommandDispatcher;
import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.AlarmMessage;
import com.pcs.device.gateway.G2021.message.extension.alarm.state.AlarmState;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.extension.AlarmExtension;

public class AlarmProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmProcessor.class);

	private G2021DeviceConfiguration configuration;

	public AlarmProcessor(G2021DeviceConfiguration configuration) throws MessageProcessException{
		if(configuration == null)
			throw new MessageProcessException("Configuration cannot be null !!!");
		this.configuration = configuration;
	}
	
	public AlarmProcessor(String uuid){

		if(configuration == null){
			this.configuration = (G2021DeviceConfiguration) G2021DeviceManager.instance().getConfiguration(uuid);
		}
	
	}

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException{

		ByteBuf alarmData = (ByteBuf) G2021Data;
		Short pid = alarmData.readShort();
		Integer snapshotTime = alarmData.readInt();

		String flag = Integer.toHexString(alarmData.readUnsignedByte());
		flag = ConversionUtils.convertToBinaryExt(flag, 8).toString();
		G2021DataTypes dataType = G2021DataTypes.valueOfType(flag.substring(0,4));
		String alarmStateFlag = flag.substring(4);

		if(dataType != null){
				AlarmMessage message = new AlarmMessage();
				message.setTime(snapshotTime*1000l);
				message.setSourceId(configuration.getDevice().getDeviceId());
				
				Point point = null ;
				if(configuration.getPoint(pid.toString()) == null){
					SyncCommandDispatcher.releaseSyncCommand(configuration);
					throw new MessageProcessException("Invalid point id : "+pid+" Command to SYNC has be queued!!");
				}else{
					point = configuration.getPoint(pid.toString());
					message.setCustomTag(point.getCustomTag());
				}

				List<AlarmExtension> alarms = point.getAlarmExtensions();
				point = null;
				
				for (AlarmExtension alarmExtension : alarms) {// expecting only one alarm as per the protocol definition.
					
					if(alarmExtension != null){
						
						AlarmState alarmState = AlarmState.valueOfType(alarmStateFlag);
						
						switch (dataType) {
						
						case BOOLEAN:
							message.setData(alarmData.readUnsignedByte() ==0 ? false:true);
							alarmExtension.setState(alarmState.name());
							break;
							
						case SHORT:
							message.setData(alarmData.readShort());
							alarmExtension.setState(alarmState.name());
							break;
							
						case INT:
							message.setData(alarmData.readInt());
							alarmExtension.setState(alarmState.name());
							break;
							
						case LONG:
							message.setData(alarmData.readLong());
							alarmExtension.setState(alarmState.name());
							break;
							
						case FLOAT:
							message.setData(alarmData.readFloat());
							alarmExtension.setState(alarmState.name());
							break;
							
						case TIMESTAMP:
							Long timeStamp = alarmData.readInt()*1000l;
							message.setData(timeStamp);
							alarmExtension.setState(alarmState.name());
							break;
							
						case STRING:
							int stringLength = alarmData.readUnsignedByte();
							char[] stringValue = new char[stringLength];
							
							for (int j = 0; j < stringLength; j++) {
								stringValue[j] = (char) alarmData.readUnsignedByte();
							}
							message.setData(new String(stringValue));
							alarmExtension.setState(alarmState.name());
							break;
							
						case TEXT:
							int textLength = alarmData.readUnsignedByte();
							byte[] textValue = new byte[textLength];
							for (int j = 0; j < textLength; j++) {
								textValue[j] = (byte) alarmData.readUnsignedByte();
							}
							try {
								message.setData(new String(textValue,"UTF-8"));
							} catch (UnsupportedEncodingException e) {
								throw new MessageProcessException("Invalid text data",e);
							}
							alarmExtension.setState(alarmState.name());
							
							break;
							
						default:
							break;
						}
						
						
						message.setAlarmType(alarmExtension.getExtensionName());
						
						if(AlarmState.valueOf(alarmExtension.getState()) == AlarmState.NORMALIZED){
							message.setStatus(false);
							switch (alarmState) {
							case LOWER_THRESHOLD_ALARM:
								message.setStatusMessage(alarmExtension.getLowerThresholdNormalMessage());
								break;
								
							case UPPER_THRESHOLD_ALARM:
								message.setStatusMessage(alarmExtension.getUpperThresholdNormalMessage());
								break;

							default:
								message.setStatusMessage(alarmExtension.getNormalMessage());
								break;
							}
						}else{
							message.setStatus(true);
							switch (alarmState) {
							case LOWER_THRESHOLD_ALARM:
								message.setStatusMessage(alarmExtension.getLowerThresholdAlarmMessage());
								break;
								
							case UPPER_THRESHOLD_ALARM:
								message.setStatusMessage(alarmExtension.getUpperThresholdAlarmMessage());
								break;

							default:
								message.setStatusMessage(alarmExtension.getAlarmMessage());
								break;
							}
						}
						alarmExtension = null;
					}else{
						LOGGER.error("No Alarm Extension Defined For The Point!!");
					}
					break;
				}
				alarms = null;
				alarmData.release();
				return message;
		}else{
			LOGGER.error("Invalid data type received !!!");
		}

		alarmData.release();
		return null;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		ByteBuffer ackResponse = ByteBuffer.allocate(3);
		ackResponse.put(PacketType.ANONYMOUS_ACK.getType().byteValue());
		ackResponse.put(header.getMessageType().getType().byteValue());
		ackResponse.put(header.getSeqNumber().byteValue());
		return ackResponse.array();
	}
	
	public static void main(String[] args) throws Exception {
		byte[] hexStringToByteArray = (byte[]) ConversionUtils.hexStringToByteArray("00195538C6A70400");
		ByteBuf buf = Unpooled.wrappedBuffer(hexStringToByteArray);
		new AlarmProcessor("36fba767-4a5b-47bc-9432-889c033464d8").processG2021Message(buf);
	}

}
