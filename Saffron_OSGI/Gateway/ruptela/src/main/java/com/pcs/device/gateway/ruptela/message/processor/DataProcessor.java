/**
 * 
 */
package com.pcs.device.gateway.ruptela.message.processor;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.ruptela.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.ruptela.exception.MessageProcessException;
import com.pcs.device.gateway.ruptela.message.header.DeviceMessageHeader;
import com.pcs.device.gateway.ruptela.utils.CRC16;
import com.pcs.device.gateway.ruptela.utils.SupportedDevices;
import com.pcs.device.gateway.ruptela.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.connectivity.utils.ConversionUtils;

/**
 * @author pcseg171
 *
 */
public class DataProcessor extends RuptelaDeviceMessageProcessor {


	private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessor.class);
	private boolean validPacket = false;


	/* (non-Javadoc)
	 * @see com.pcs.device.gateway.ruptela.message.processor.RuptelaDeviceMessageProcessor#processG2021Message(io.netty.buffer.ByteBuf)
	 */
	@Override
	public List<Message> processRuptelaMessage(String ruptelaHexData, DeviceMessageHeader header) throws MessageProcessException {

		List<Message> messages = new ArrayList<Message>();
		try {
			HashMap<String, Point> pointMap = new HashMap<String, Point>();
			pointMap = DeviceManagerUtil.getRuptelaDeviceManager().getPointsByProtocolAndDeviceType(SupportedDevices.getGateway(Devices.FMXXX));

			LOGGER.info("Data message is {}",ruptelaHexData);
			
			LOGGER.info("Data received from device {} which has {} records left out of which {} are being send in this packet",
					header.getImei(),header.getNumberOfRecords(),header.getNumberOfRecords());
			
			
			int readPosition = 22;//excluding header length
			for (int recordCount = 0; recordCount < header.getNumberOfRecords(); recordCount++) {

				Message message = new Message();
				List<Point> dataPoints = new ArrayList<Point>();

				String timestampHexa = ruptelaHexData.substring(readPosition,readPosition += 8);
				long realTime = Long.parseLong(ConversionUtils.convertToLong(timestampHexa).toString());
				message.setTime(realTime);
				message.setReceivedTime(DateTime.now(DateTimeZone.UTC).toDate());
				message.setPoints(dataPoints);

				Integer timestampExtension = Integer.parseInt(ConversionUtils.convertToLong(ruptelaHexData.substring(readPosition,readPosition += 2)).toString());
				if(timestampExtension !=0){
					//TODO implement logic if required to split new and old messages.
					LOGGER.info("Indicates an older message");
				}
				
				LOGGER.info("Fetching Priority");
				Point priorityPt = pointMap.get("Priority");
				priorityPt.setData(ruptelaHexData.substring(readPosition, readPosition += 2));
				dataPoints.add(priorityPt);
				
				LOGGER.info("Fetching Longitude");
				Point lngPt = pointMap.get("Longitude");
				String longitude = ruptelaHexData.substring(readPosition, readPosition += 8);
				lngPt.setData(longitude);
				dataPoints.add(lngPt);
				// message.setLongitude(Double.parseDouble(ConversionUtils.convertToLong(longitude))/(Constants.MILLIONTH*10));
				longitude = null;

				LOGGER.info("Fetching Latitude");
				Point latPt = pointMap.get("Latitude");
				String latitude = ruptelaHexData.substring(readPosition, readPosition += 8);
				latPt.setData(latitude);
				dataPoints.add(latPt);
				// message.setLatitude(Double.parseDouble(ConversionUtils.convertToLong(latitude))/(Constants.MILLIONTH*10));
				latitude = null;

				LOGGER.info("Fetching Altitude");
				Point altitudePt = pointMap.get("Altitude");
				altitudePt.setData(ruptelaHexData.substring(readPosition, readPosition += 4));
				dataPoints.add(altitudePt);

				LOGGER.info("Fetching Angle");
				Point anglePt = pointMap.get("Angle");
				anglePt.setData(ruptelaHexData.substring(readPosition, readPosition += 4));
				dataPoints.add(anglePt);

				LOGGER.info("Fetching Visible Satellites");
				Point visibleSatPt = pointMap.get("Visible Satellites");
				visibleSatPt.setData(ruptelaHexData.substring(readPosition, readPosition += 2));
				dataPoints.add(visibleSatPt);

				LOGGER.info("Fetching Speed");
				Point speedPt = pointMap.get("Speed");
				speedPt.setData(ruptelaHexData.substring(readPosition, readPosition += 4));
				dataPoints.add(speedPt);

				LOGGER.info("Fetching HDOP");
				Point hdopPt = pointMap.get("HDOP");
				hdopPt.setData(ruptelaHexData.substring(readPosition, readPosition += 2));
				dataPoints.add(hdopPt);

				LOGGER.info("Fetching eventIOID");
				String eventIOID = ruptelaHexData.substring(readPosition, readPosition += 2);
				message.setReason(eventIOID);
				eventIOID = null;

				/*String noOfTotalIO = ruptelaHexData.substring(readPosition, readPosition += 2);
				LOGGER.info("Total IO Count {}",noOfTotalIO);
				noOfTotalIO = null;*/

				LOGGER.info("Fetching priority");
				String noOfOneByteIO = ruptelaHexData.substring(readPosition, readPosition += 2);
				long oneByte = Long.parseLong(noOfOneByteIO, 16);
				List<Point> oneBytePoints = new ArrayList<Point>();

				for (int i = 0; i < oneByte; i++) {
					String iOId = ruptelaHexData.substring(readPosition, readPosition += 2);
					String IOValue = ruptelaHexData.substring(readPosition, readPosition += 2);
					String eventId = ConversionUtils.convertToLong(iOId).toString();
					LOGGER.info("Fetching one byte {}",eventId);
					Point point = pointMap.get(eventId);
					if(point == null){
						LOGGER.error("Un identified point {}!! Check Configuration !!",eventId);
					}else{
						Point oneBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getType().toUpperCase()).getType());
						oneBytePoint.setPointId(eventId);
						oneBytePoint.setPointName(point.getPointName());
						oneBytePoint.setDisplayName(point.getPointName());
						oneBytePoint.setUnit(point.getUnit());
						oneBytePoint.setPointAccessType(point.getPointAccessType());
						oneBytePoint.setData(IOValue);
						oneBytePoints.add(oneBytePoint);
						oneBytePoint = null;
					}

				}
				noOfOneByteIO = null;

				String noOfTwoByteIO = ruptelaHexData.substring(readPosition, readPosition += 2);
				long twoByte = Long.parseLong(noOfTwoByteIO, 16);
				List<Point> twoBytePoints = new ArrayList<Point>();

				for (int j = 0; j < twoByte; j++) {
					String iOId = ruptelaHexData.substring(readPosition, readPosition += 2);
					String IOValue = ruptelaHexData.substring(readPosition, readPosition += 4);
					String eventId = ConversionUtils.convertToLong(iOId).toString();
					LOGGER.info("Fetching two byte {}",eventId);
					Point point = pointMap.get(eventId);
					if(point == null){
						LOGGER.error("Un identified point {}!! Check Configuration !!",ConversionUtils.convertToLong(iOId).toString());
					}else{
						Point twoBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getType().toUpperCase()).getType());
						twoBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
						twoBytePoint.setPointName(point.getPointName());
						twoBytePoint.setDisplayName(point.getPointName());
						twoBytePoint.setUnit(point.getUnit());
						twoBytePoint.setPointAccessType(point.getPointAccessType());
						twoBytePoint.setData(IOValue);
						oneBytePoints.add(twoBytePoint);
						twoBytePoint = null;
					}

				}
				noOfTwoByteIO = null;

				// TWO BYTE INFO EXTRACTED

				String noOfFourByteIO = ruptelaHexData.substring(readPosition, readPosition += 2);
				long fourByte = Long.parseLong(noOfFourByteIO, 16);
				List<Point> fourBytePoints = new ArrayList<Point>();

				for (int k = 0; k < fourByte; k++) {
					String iOId = ruptelaHexData.substring(readPosition, readPosition += 2);
					String IOValue = ruptelaHexData.substring(readPosition, readPosition += 8);
					String eventId = ConversionUtils.convertToLong(iOId).toString();
					LOGGER.info("Fetching four byte {}",eventId);
					Point point = pointMap.get(eventId);
					if(point == null){
						LOGGER.error("Un identified point {}!! Check Configuration !!",ConversionUtils.convertToLong(iOId).toString());
					}else{
						Point fourBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getType().toUpperCase()).getType());
						fourBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
						fourBytePoint.setPointName(point.getPointName());
						fourBytePoint.setDisplayName(point.getPointName());
						fourBytePoint.setUnit(point.getUnit());
						fourBytePoint.setPointAccessType(point.getPointAccessType());
						fourBytePoint.setData(IOValue);
						oneBytePoints.add(fourBytePoint);
						fourBytePoint = null;
					}

				}
				noOfFourByteIO = null;
				// FOUR BYTE INFO EXTRACTED

				String noOfEightByteIO = ruptelaHexData.substring(readPosition, readPosition += 2);
				long eightByte = Long.parseLong(noOfEightByteIO, 16);
				List<Point> eightBytePoints = new ArrayList<Point>();

				for (int l = 0; l < eightByte; l++) {
					String iOId = ruptelaHexData.substring(readPosition, readPosition += 2);
					String IOValue = ruptelaHexData.substring(readPosition, readPosition += 16);
					String eventId = ConversionUtils.convertToLong(iOId).toString();
					LOGGER.info("Fetching eight byte {}",eventId);
					Point point = pointMap.get(eventId);

					if(point == null){
						LOGGER.error("Un identified point {}!! Check Configuration !!",ConversionUtils.convertToLong(iOId).toString());
					}else{
						Point eightBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getType().toUpperCase()).getType());
						eightBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
						eightBytePoint.setPointName(point.getPointName());
						eightBytePoint.setDisplayName(point.getPointName());
						eightBytePoint.setUnit(point.getUnit());
						eightBytePoint.setPointAccessType(point.getPointAccessType());
						eightBytePoint.setData(IOValue);
						oneBytePoints.add(eightBytePoint);
						eightBytePoint = null;
					}

				}
				noOfEightByteIO = null;
				// EIGHT BYTE INFO EXTRACTED
				dataPoints.addAll(eightBytePoints);
				dataPoints.addAll(fourBytePoints);
				dataPoints.addAll(twoBytePoints);
				dataPoints.addAll(oneBytePoints);

				messages.add(message);
			} 
			String payload = ruptelaHexData.substring(0,readPosition);
			LOGGER.info("Actual payload {}",payload);
			String messageCRC = ruptelaHexData.substring(readPosition);
			LOGGER.info("Received CRC {}",messageCRC);
			int messageCRCValue = Integer.parseInt(ConversionUtils.convertToLong(messageCRC).toString());
			LOGGER.info("Actual CRC {}",messageCRCValue);
			int crc_16_rec = CRC16.crc_16_rec(ConversionUtils.hexStringToByteArray(payload));
			LOGGER.info("Calculated CRC {}",crc_16_rec);
			if(messageCRCValue == crc_16_rec)
				validPacket = true;

		}catch (Exception e) {
			LOGGER.error("Error decoding ruptela messages",e);
		}finally{
			ruptelaHexData = null;
		}

		return messages;
	}

	/* (non-Javadoc)
	 * @see com.pcs.device.gateway.ruptela.message.processor.RuptelaDeviceMessageProcessor#getServerMessage(com.pcs.saffron.cipher.data.message.Message, com.pcs.device.gateway.ruptela.message.header.DeviceMessageHeader)
	 */
	@Override
	public byte[] getServerMessage(Message message, DeviceMessageHeader header)
			throws MessageProcessException {
		ByteBuffer dataAckCRC = ByteBuffer.allocate(6);
		ByteBuffer dataAck = ByteBuffer.allocate(2);
		Integer dataAckSize = 2;		
		dataAckCRC.putShort(dataAckSize.shortValue());
		dataAck.put((byte)0X64);
		
		if(validPacket)
			dataAck.put((byte)1);
		else
			dataAck.put((byte)0);
		
		Integer crc16 = CRC16.crc_16_rec(dataAck.array());
		dataAckCRC.put(dataAck.array());
		dataAckCRC.putShort(crc16.shortValue());
		dataAck = null;
		dataAckSize = null;
		return dataAckCRC.array();
	}
	
	public static void main(String[] args) {
		String hex = "00000b1a4a5585c30100024e9c036900000f101733208ff45e07b31b570a001009090605011b1a020003001c01ad01021d338e16000002960000601a41014bc16d004e9c038400000f104fdf20900d20075103b00a001308090605011b1a020003001c01ad01021d33b116000002960000601a41014bc1ea00";
		byte[] hexStringToByteArray = ConversionUtils.hexStringToByteArray(hex);
		System.err.println(CRC16.crc_16_rec(hexStringToByteArray));
	}
}
