/**
 * 
 */
package com.pcs.gateway.teltonika.decoder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.gateway.teltonika.bundle.utils.DeviceManagerUtil;
import com.pcs.gateway.teltonika.constant.ProtocolConstants;
import com.pcs.gateway.teltonika.utils.SupportedDevices;
import com.pcs.gateway.teltonika.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.adapter.DecoderAdapter;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.cipher.exception.InvalidVersion;
import com.pcs.saffron.cipher.exception.MessageDecodeException;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;

/**
 * @author pcseg171
 *
 */
public class FMXXXDecoder extends DecoderAdapter {


	private static final Logger LOGGER = LoggerFactory.getLogger(FMXXXDecoder.class);

	private String hexaData;
	private String sourceId;
	private String deviceIP;
	private Date recTime;


	public FMXXXDecoder(String deviceip, String sourceId, Date recTime) {
		this.deviceIP = deviceip;
		this.sourceId = sourceId;
		this.recTime = recTime;
	}


	public void setData(String hexaData) {
		this.hexaData = hexaData;
	}


	public String getData() {
		return this.hexaData;
	}


	public List<List<Point>> getPoints() throws MessageDecodeException {
		return super.getPoints();
	}


	public List<Message> readMessage() throws MessageDecodeException {

		LOGGER.info("Raw hexaData-> FMXXX from {} , data {}",deviceIP,hexaData);
		LOGGER.info("Trying to fetch configuration from cache...");
		DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getTeltonikaDeviceManager().getConfiguration(sourceId,SupportedDevices.getGateway(Devices.FMXXX));
		Device device = null;
		
		if(configuration != null) {
			device = configuration.getDevice();
		}
		
		HashMap<String, Point> pointMap = new HashMap<String, Point>();
		if(device != null){
			LOGGER.info("Configuration Fetch returned successfully....");
			pointMap = DeviceManagerUtil.getTeltonikaDeviceManager().getPointsByProtocolAndDeviceType(SupportedDevices.getGateway(Devices.FMXXX));
		}else{
			pointMap = DeviceManagerUtil.getTeltonikaDeviceManager().getPointsByProtocolAndDeviceType(SupportedDevices.getGateway(Devices.FMXXX));
		}

		int start = 0;
		int lastPosition = 0;
		List<Message> messages = new ArrayList<Message>();
		if (!(hexaData != null && hexaData.length() >= 2)) {
			LOGGER.error("Not Valid teltonika Package.." + hexaData);
			throw new InvalidVersion("Not Valid teltonika Package");
		}
		String codec = hexaData.substring(start, start += 2);// one byte
		if (!codec.equals(ProtocolConstants.PROTOCOL_VERSION_08))// for FM4100 and FM2100 codecId=08
		{
			LOGGER.error("Not Valid teltonika codec");
			throw new InvalidVersion("Not Valid teltonika codec");
		}
		try {
			String noOfData = hexaData.substring(start, start += 2);// no of
			Long dataLen = Long.parseLong(ConversionUtils.convertToLong(noOfData).toString());
			lastPosition = start;
			for (long ln = 0; ln < dataLen; ln++) {
				Message message = new Message();
				message.setRawData(hexaData);
				message.setSourceId(sourceId);
				List<Point> dataPoints = new ArrayList<Point>();
				message.setPoints(dataPoints);
				message.setReceivedTime(recTime);

				int pos = lastPosition;
				String timeStamp = hexaData.substring(pos, pos += 16);// 8 bytes
				Calendar gpsTime = Calendar.getInstance();
				long realTime = Long.parseLong(ConversionUtils.convertToLong(timeStamp).toString());
				gpsTime.setTimeInMillis(realTime);
				message.setTime(realTime);
				//message.setMessageTime(gpsTime.getTime());

				Point priorityPt = pointMap.get("Priority");
				priorityPt.setData(hexaData.substring(pos, pos += 2));
				dataPoints.add(priorityPt);

				Point lngPt = pointMap.get("Longitude");
				String longitude = hexaData.substring(pos, pos += 8);
				lngPt.setData(longitude);
				dataPoints.add(lngPt);
				// message.setLongitude(Double.parseDouble(ConversionUtils.convertToLong(longitude))/(Constants.MILLIONTH*10));
				longitude = null;

				Point latPt = pointMap.get("Latitude");
				String latitude = hexaData.substring(pos, pos += 8);
				latPt.setData(latitude);
				dataPoints.add(latPt);
				// message.setLatitude(Double.parseDouble(ConversionUtils.convertToLong(latitude))/(Constants.MILLIONTH*10));
				latitude = null;

				Point altitudePt = pointMap.get("Altitude");
				altitudePt.setData(hexaData.substring(pos, pos += 4));
				dataPoints.add(altitudePt);

				Point anglePt = pointMap.get("Angle");
				anglePt.setData(hexaData.substring(pos, pos += 4));
				dataPoints.add(anglePt);

				Point visibleSatPt = pointMap.get("Visible Satellites");
				visibleSatPt.setData(hexaData.substring(pos, pos += 2));
				dataPoints.add(visibleSatPt);

				Point speedPt = pointMap.get("Speed");
				speedPt.setData(hexaData.substring(pos, pos += 4));
				dataPoints.add(speedPt);

				String eventIOID = hexaData.substring(pos, pos += 2);
				message.setReason(eventIOID);
				eventIOID = null;

				String noOfTotalIO = hexaData.substring(pos, pos += 2);
				LOGGER.info("Total IO Count {}",noOfTotalIO);
				noOfTotalIO = null;

				String noOfOneByteIO = hexaData.substring(pos, pos += 2);
				long oneByte = Long.parseLong(noOfOneByteIO, 16);
				List<Point> oneBytePoints = new ArrayList<Point>();

				for (int i = 0; i < oneByte; i++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 2);
					Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
					if(point == null){
						LOGGER.error("Un identified point {}!! Check Configuration !!",ConversionUtils.convertToLong(iOId).toString());
					}else{
						Point oneBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getType().toUpperCase()).getType());
						oneBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
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

				String noOfTwoByteIO = hexaData.substring(pos, pos += 2);
				long twoByte = Long.parseLong(noOfTwoByteIO, 16);
				List<Point> twoBytePoints = new ArrayList<Point>();

				for (int j = 0; j < twoByte; j++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 4);
					Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
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

				String noOfFourByteIO = hexaData.substring(pos, pos += 2);
				long fourByte = Long.parseLong(noOfFourByteIO, 16);
				List<Point> fourBytePoints = new ArrayList<Point>();

				for (int k = 0; k < fourByte; k++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 8);
					Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
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

				String noOfEightByteIO = hexaData.substring(pos, pos += 2);
				long eightByte = Long.parseLong(noOfEightByteIO, 16);
				List<Point> eightBytePoints = new ArrayList<Point>();

				for (int l = 0; l < eightByte; l++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 16);
					Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
					
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
				lastPosition = pos;
				LOGGER.info("Message decoded is {}",message.toString());
				// display(messageParameters);353976013128388
			}// ends one hexaData packet decoding
		} catch (Exception ex) {
			LOGGER.error("exception teltonika decoding....", ex);
			LOGGER.error("Error decoding : {}",  hexaData);
			throw new MessageDecodeException("Exception In Decoding The Message", ex);
		}
		LOGGER.info("Size of message points : " + messages.size());
		return messages;

	}
	
	public static void main(String[] args) throws MessageDecodeException {
		FMXXXDecoder fmxxxDecoder = new FMXXXDecoder("", "", Calendar.getInstance().getTime());
		fmxxxDecoder.setData("08010000001521aa124b700003d9b9000087181000cd0070008500160901010201030116034703F0011505C800CCFF0BCB035209000F0A34F70B000313045043241B440000B5000AB600064236A118000002F10000A5A246000001EE0001");
		fmxxxDecoder.readMessage();
	}

}
