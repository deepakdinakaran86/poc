package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.G2021.command.SyncCommandDispatcher;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.extension.alarm.state.AlarmState;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.device.gateway.G2021.utils.SupportedDevices;
import com.pcs.device.gateway.G2021.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.data.message.AlarmMessage;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.extension.AlarmExtension;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class AlarmProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmProcessor.class);

	private DefaultConfiguration configuration;

	public AlarmProcessor(DefaultConfiguration configuration) throws MessageProcessException{
		if(configuration == null){
			throw new MessageProcessException("Configuration cannot be null !!!");
		}
		this.configuration = configuration;
	}

	public AlarmProcessor(String uuid){

		if(configuration == null){
			this.configuration = (DefaultConfiguration) DeviceManagerUtil.getG2021DeviceManager().getConfiguration(uuid,SupportedDevices.getGateway(Devices.EDCP));
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
			message.setReceivedTime(Calendar.getInstance().getTime());
			message.setTime(snapshotTime*1000l);

			Point point = null ;
			if(configuration.getPoint(pid.toString()) == null){
				SyncCommandDispatcher.releaseSyncCommand(configuration);
				throw new MessageProcessException("Invalid point id : "+pid+" Command to SYNC has been queued!!");
			}else{
				point = configuration.getPoint(pid.toString());
				message.addPoint(point);
			}

			List<AlarmExtension> alarms = point.getAlarmExtensions();


			for (AlarmExtension alarmExtension : alarms) {// expecting only one alarm as per the protocol definition.

				if(alarmExtension != null){

					AlarmState alarmState = AlarmState.valueOfType(alarmStateFlag);

					try {
						switch (dataType) {

						case BOOLEAN:
							point.setData(alarmData.readUnsignedByte() ==0 ? false:true);
							
							message.setStatus(alarmState.getStatus());
							break;

						case SHORT:
							point.setData(alarmData.readShort());
							
							message.setStatus(alarmState.getStatus());
							break;

						case INT:
							point.setData(alarmData.readInt());
							
							message.setStatus(alarmState.getStatus());
							break;

						case LONG:
							point.setData(alarmData.readLong());
							
							message.setStatus(alarmState.getStatus());
							break;

						case FLOAT:
							point.setData(alarmData.readFloat());
							
							message.setStatus(alarmState.getStatus());
							break;

						case TIMESTAMP:
							Long timeStamp = alarmData.readInt()*1000l;
							point.setData(timeStamp);
							
							message.setStatus(alarmState.getStatus());
							break;

						case STRING:
							int stringLength = alarmData.readUnsignedByte();
							char[] stringValue = new char[stringLength];

							for (int j = 0; j < stringLength; j++) {
								stringValue[j] = (char) alarmData.readUnsignedByte();
							}
							point.setData(new String(stringValue));
							
							message.setStatus(alarmState.getStatus());
							break;

						case TEXT:
							int textLength = alarmData.readUnsignedByte();
							byte[] textValue = new byte[textLength];
							for (int j = 0; j < textLength; j++) {
								textValue[j] = (byte) alarmData.readUnsignedByte();
							}
							point.setData(new String(textValue,"UTF-8"));
							
							message.setStatus(alarmState.getStatus());

							break;

						default:
							break;
						}
					} catch (Exception e) {
						throw new MessageProcessException("Invalid text data",e);
					}



					message.setAlarmName(alarmExtension.getExtensionName());

					if( !message.getStatus()){
						switch (alarmState) {
						case LOWER_THRESHOLD_ALARM:
							message.setAlarmMessage(alarmExtension.getLowerThresholdNormalMessage());
							break;

						case UPPER_THRESHOLD_ALARM:
							message.setAlarmMessage(alarmExtension.getUpperThresholdNormalMessage());
							break;

						default:
							message.setAlarmMessage(alarmExtension.getNormalMessage());
							break;
						}
					}else{
						switch (alarmState) {
						case LOWER_THRESHOLD_ALARM:
							message.setAlarmMessage(alarmExtension.getLowerThresholdAlarmMessage());
							break;

						case UPPER_THRESHOLD_ALARM:
							message.setAlarmMessage(alarmExtension.getUpperThresholdAlarmMessage());
							break;

						default:
							message.setAlarmMessage(alarmExtension.getAlarmMessage());
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
			return message;
		}else{
			LOGGER.error("Invalid data type received !!!");
		}


		return null;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}

}
