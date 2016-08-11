/**
 * 
 */
package com.pcs.gateway.teltonika.handler.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.bundle.utils.DeviceManagerUtil;
import com.pcs.gateway.teltonika.constant.ProtocolConstants;
import com.pcs.gateway.teltonika.constant.codec.Codec;
import com.pcs.gateway.teltonika.decoder.FMXXXDecoder;
import com.pcs.gateway.teltonika.decoder.FMXXXDecoderCodec12;
import com.pcs.gateway.teltonika.event.notifier.MessageNotifier;
import com.pcs.gateway.teltonika.state.TeltonikaStates;
import com.pcs.gateway.teltonika.utils.CRC16;
import com.pcs.gateway.teltonika.utils.SupportedDevices;
import com.pcs.gateway.teltonika.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.exception.InvalidVersion;
import com.pcs.saffron.cipher.exception.MessageDecodeException;
import com.pcs.saffron.cipher.identity.bean.Gateway;
import com.pcs.saffron.connectivity.Connector;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.DeviceManager;
import com.pcs.saffron.manager.authentication.AuthenticationResponse;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;

/**
 * @author pcseg171
 *
 */
public class TeltonikaTCPHandler extends ReplayingDecoder<TeltonikaStates> {


	private static final Logger LOGGER = LoggerFactory.getLogger(TeltonikaTCPHandler.class);

	private String sourceId = null;
	private byte[] data = null;
	private String hexaData = null;
	private InetSocketAddress remoteAddress = null;
	private Codec dataCodec = null;
	public TeltonikaTCPHandler() {
		super(TeltonikaStates.INTIATED);
	}
	DeviceManager deviceManager = DeviceManagerUtil.getTeltonikaDeviceManager();
	Gateway gateway = SupportedDevices.getGateway(Devices.FMXXX);
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,List<Object> out) throws Exception {
		short bytesSend = 0;
		int dataBytes = 0;
		remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		gateway.setDeviceIP(remoteAddress.getHostName());
		gateway.setConnectedPort(remoteAddress.getPort());
		gateway.setWritebackPort(remoteAddress.getPort());
		gateway.setCommunicationProtocol(Connector.CONNECTOR_PROTOCOL_TCP);
		
		switch (state()) {

		case INTIATED:
			bytesSend = buf.readShort();
			/*
			 * read the first 15 bytes which represents the imei of a device.
			 */
			if(bytesSend > 0) {
				try {
					if(buf.readableBytes() >= bytesSend) {
						sourceId = new String(buf.readBytes(bytesSend).array());
						LOGGER.info("Imei: {}",sourceId);
						
						Device device = new Device();
						device.setSourceId(sourceId);
						device.setDatasourceName(sourceId);
						device.setIsPublishing(true);
						device.setDeviceName(sourceId);
						device.setLatitude(0.0F);
						device.setLongitude(0.0F);
						AuthenticationResponse authenticate = DeviceManagerUtil.getTeltonikaDeviceManager().authenticate(device,gateway);
						device = null;
						byte[] ackState = null;
						if(authenticate.isAuthenticated()){
							LOGGER.info("Device authorized sending positive response");
							ackState = new byte[] { 0x01 };
						}else{
							LOGGER.info("Device not authorized blocking the device");
							ackState = new byte[] { 0x00 };
						}
						ctx.writeAndFlush(Unpooled.wrappedBuffer(ackState));
						checkpoint(TeltonikaStates.READ_DATA);
					}else{
						return;
					}
					
				} catch (Exception e) {
					LOGGER.error("Error reading IMEI",e);
				}
			}else {
				LOGGER.error("Zero bytes send from the device, Resetting buffers!!");
				return;
			}
			break;

		case READ_DATA:
			try {
				dataBytes = buf.readInt();
				LOGGER.error("Read Data "+dataBytes);
				if(dataBytes >0 ) { //databytes >0 ensures that the start of message 00000000 is discarded.The decoder functions only after receiving the actual message.
					data = buf.readBytes(dataBytes).array();
					checkpoint(TeltonikaStates.DATA_RECEIVED);
				}else {
					return;
				}
			} catch (Exception e) {
				LOGGER.error("Error reading data",e);
			}
			break;

		case DATA_RECEIVED:
			LOGGER.error("DATA_RECEIVED");
			try {
				if(buf.readableBytes() >= 4){
					String CRC = ConversionUtils.getHex(buf.readBytes(4).array()).toString();
					int dataCRC = CRC16.crc_16_rec(data, ProtocolConstants.CRC_POLYNOMIAL);
					int receivedCRC = Integer.parseInt(ConversionUtils.convertToLong(CRC).toString());

					if(dataCRC == receivedCRC){
						LOGGER.error("CRC Matched...!!! {}=={}",dataCRC,receivedCRC);
						hexaData = ConversionUtils.getHex(data).toString();//new String(dataBytes);//ConversionUtils.getHex(data).toString()
						//String dataReceived = ConversionUtils.getHex(dataBytes);//
						LOGGER.info("Raw Data From Device {} = {}",sourceId,hexaData);
						byte[] packetCount = new byte[] { 0X00, 0X00, 0X00,(byte) (getDataPacketCount(hexaData)) };
						ctx.writeAndFlush(Unpooled.wrappedBuffer(packetCount));
						if(dataCodec != null)
							extractDeviceData(dataCodec);
					}else{
						byte[] packetCount = new byte[] { 0X00, 0X00, 0X00,0X00 };//invalid received packet count,forces devices to resend data.
						ctx.writeAndFlush(Unpooled.wrappedBuffer(packetCount));
						LOGGER.error("CRC Failed...!!! "+dataCRC +"!="+ receivedCRC);
					}
					checkpoint(TeltonikaStates.READ_DATA);
					break;
				}else{
					LOGGER.error("Error reading data !! CRC Not Send");
					return;
				}

			} catch (Exception e) {
				LOGGER.error("Exception in Data Reception",e);
			}
			
		default:
			LOGGER.error("In Default state::{}",state());
			break;
		}
		return;
	}


	private void extractDeviceData(Codec codec) throws MessageDecodeException {

		switch (codec) {

		case CODEC00:
			LOGGER.info("{} received ping from device {}",hexaData,sourceId);
			break;

		case CODEC08:
			FMXXXDecoder decoder = new FMXXXDecoder(remoteAddress.getHostString(), sourceId, Calendar.getInstance().getTime());
			decoder.setData(hexaData);
			MessageNotifier.getInstance().notifyMessageReception(decoder.readMessage(),(DefaultConfiguration)deviceManager.getConfiguration(sourceId, gateway),gateway);
			break;

		case CODEC12:
			FMXXXDecoderCodec12 codec12Decoder = new FMXXXDecoderCodec12(remoteAddress.getHostString(), sourceId, Calendar.getInstance().getTime());
			codec12Decoder.setData(hexaData);
			MessageNotifier.getInstance().notifyMessageReception(codec12Decoder.readMessage(),(DefaultConfiguration)deviceManager.getConfiguration(sourceId, gateway),gateway);
			break;
		default:
			break;
		}
	}

	private int getDataPacketCount(String data) throws MessageDecodeException {
		int start = 0;
		if (data.length() > 2) {
			dataCodec = extractCodec(data.substring(start, start += 2));

			try {
				String noOfData = data.substring(start, start += 2);// no of data packets
				Long packetCount = Long.parseLong(ConversionUtils.convertToLong(noOfData).toString());
				LOGGER.info("{} new packets received from device {}",packetCount.intValue(),sourceId);
				return packetCount.intValue();
			} catch (Exception ex) {
				throw new MessageDecodeException("Exception In Getting Packet Count: ", ex);
			}
		} else {
			return 0;
		}

	}

	private Codec extractCodec(String codecFromMsg) throws InvalidVersion {

		Codec codec = Codec.valueOfType(codecFromMsg);

		switch (codec) {

		case CODEC00:
			LOGGER.info("{} received from device {}",Codec.CODEC00,sourceId);
			return Codec.CODEC00;
		case CODEC08:
			LOGGER.info("{} received from device {}",Codec.CODEC08,sourceId);
			return Codec.CODEC08;
		case CODEC12:
			LOGGER.info("{} received from device {}",Codec.CODEC12,sourceId);
			return Codec.CODEC12;

		default:
			throw new InvalidVersion("Not Valid Data Package " + sourceId + " " + codecFromMsg);
		}
	}

	public static void main(String[] args) {
		//String a = "0801000001537FD0AA90000003EF6400086F1A00D800E309007400160901010201030116034703F0011505C800CCFF0BCB035209000F0A34F70B000313045043241B440000B5000AB600064236A118000002F10000A5A246000001EE0001";
		
	}
	
	
}
