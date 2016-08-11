/**
 * 
 */
package com.pcs.device.gateway.teltonika.decoder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.teltonika.constant.ProtocolConstants;
import com.pcs.device.gateway.teltonika.devicemanager.TeltonikaDeviceManager;
import com.pcs.device.gateway.teltonika.devicemanager.bean.Device;
import com.pcs.device.gateway.teltonika.devicemanager.bean.TeltonikaDeviceConfiguration;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.adapter.DecoderAdapter;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.types.PointDataTypes;
import com.pcs.deviceframework.decoder.exception.InvalidVersion;
import com.pcs.deviceframework.decoder.exception.MessageDecodeException;

/**
 * @author pcseg171
 *
 */
public class FMXXXDecoder extends DecoderAdapter {


	private static final Logger LOGGER = LoggerFactory.getLogger(FMXXXDecoder.class);
	private static final TeltonikaDeviceManager TeltonikaManager = TeltonikaDeviceManager.instance();
	
	private String hexaData;
	private String sourceId;
	private String deviceIP;
	private Date recTime;
	

	public FMXXXDecoder(String deviceip, String sourceId, Date recTime) {
		this.deviceIP = deviceip;
		this.sourceId = sourceId;
		this.recTime = recTime;
	}

	@Override
	public void setData(String hexaData) {
		this.hexaData = hexaData;
	}

	@Override
	public String getData() {
		return this.hexaData;
	}

	@Override
	public List<List<Point>> getPoints() throws MessageDecodeException {
		return super.getPoints();
	}

	@Override
	public List<Message> readMessage() throws MessageDecodeException {

		LOGGER.info("Raw hexaData-> FMXXX " + hexaData);
		TeltonikaDeviceConfiguration configuration = (TeltonikaDeviceConfiguration) TeltonikaManager.getConfiguration(sourceId);
		Device device = configuration.getDevice();
		
		HashMap<String, com.pcs.device.gateway.teltonika.devicemanager.bean.Point> pointMap = TeltonikaManager.getPointsByProtocolAndDeviceType(device.getDeviceType().getName(),
																								device.getDeviceProtocol().getName());
		
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

				Point priorityPt = Point.getPoint(PointDataTypes.BASE.getType());
				priorityPt.setPointName("Priority");
				priorityPt.setData(hexaData.substring(pos, pos += 2));
				dataPoints.add(priorityPt);

				Point lngPt = Point.getPoint(PointDataTypes.BASE.getType());
				lngPt.setPointName("Longitude");
				String longitude = hexaData.substring(pos, pos += 8);
				lngPt.setData(longitude);
				dataPoints.add(lngPt);
				// message.setLongitude(Double.parseDouble(ConversionUtils.convertToLong(longitude))/(Constants.MILLIONTH*10));
				longitude = null;

				Point latPt = Point.getPoint(PointDataTypes.BASE.getType());
				latPt.setPointName("Latitude");
				String latitude = hexaData.substring(pos, pos += 8);
				latPt.setData(latitude);
				dataPoints.add(latPt);
				// message.setLatitude(Double.parseDouble(ConversionUtils.convertToLong(latitude))/(Constants.MILLIONTH*10));
				latitude = null;

				Point altitudePt = Point.getPoint(PointDataTypes.BASE.getType());
				altitudePt.setPointName("Gps Altitude");
				altitudePt.setData(hexaData.substring(pos, pos += 4));
				dataPoints.add(altitudePt);

				Point anglePt = Point.getPoint(PointDataTypes.BASE.getType());
				anglePt.setPointName("Angle");
				anglePt.setData(hexaData.substring(pos, pos += 4));
				dataPoints.add(anglePt);

				Point visibleSatPt = Point.getPoint(PointDataTypes.BASE.getType());
				visibleSatPt.setPointName("Visible Satellites");
				visibleSatPt.setData(hexaData.substring(pos, pos += 2));
				dataPoints.add(visibleSatPt);

				Point speedPt = Point.getPoint(PointDataTypes.BASE.getType());
				speedPt.setPointName("Speed");
				speedPt.setData(hexaData.substring(pos, pos += 4));
				dataPoints.add(speedPt);

				String eventIOID = hexaData.substring(pos, pos += 2);
				message.setReason(eventIOID);
				eventIOID = null;

				String noOfTotalIO = hexaData.substring(pos, pos += 2);
				noOfTotalIO = null;

				String noOfOneByteIO = hexaData.substring(pos, pos += 2);
				long oneByte = Long.parseLong(noOfOneByteIO, 16);
				List<Point> oneBytePoints = new ArrayList<Point>();

				for (int i = 0; i < oneByte; i++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 2);
					com.pcs.device.gateway.teltonika.devicemanager.bean.Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
					Point oneBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getDataType().toUpperCase()).getType());
					oneBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
					oneBytePoint.setPointName(point.getPointName());
					oneBytePoint.setUnit(point.getUnit());
					oneBytePoint.setPointAccessType(point.getAccessType());
					oneBytePoint.setData(IOValue);
					oneBytePoints.add(oneBytePoint);
					oneBytePoint = null;
				}
				noOfOneByteIO = null;

				String noOfTwoByteIO = hexaData.substring(pos, pos += 2);
				long twoByte = Long.parseLong(noOfTwoByteIO, 16);
				List<Point> twoBytePoints = new ArrayList<Point>();

				for (int j = 0; j < twoByte; j++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 4);
					com.pcs.device.gateway.teltonika.devicemanager.bean.Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
					Point twoBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getDataType().toUpperCase()).getType());
					twoBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
					twoBytePoint.setPointName(point.getPointName());
					twoBytePoint.setUnit(point.getUnit());
					twoBytePoint.setPointAccessType(point.getAccessType());
					twoBytePoint.setData(IOValue);
					oneBytePoints.add(twoBytePoint);
					twoBytePoint = null;
				}
				noOfTwoByteIO = null;

				// TWO BYTE INFO EXTRACTED

				String noOfFourByteIO = hexaData.substring(pos, pos += 2);
				long fourByte = Long.parseLong(noOfFourByteIO, 16);
				List<Point> fourBytePoints = new ArrayList<Point>();

				for (int k = 0; k < fourByte; k++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 8);
					com.pcs.device.gateway.teltonika.devicemanager.bean.Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
					Point fourBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getDataType().toUpperCase()).getType());
					fourBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
					fourBytePoint.setPointName(point.getPointName());
					fourBytePoint.setUnit(point.getUnit());
					fourBytePoint.setPointAccessType(point.getAccessType());
					fourBytePoint.setData(IOValue);
					oneBytePoints.add(fourBytePoint);
					fourBytePoint = null;
				}
				noOfFourByteIO = null;
				// FOUR BYTE INFO EXTRACTED

				String noOfEightByteIO = hexaData.substring(pos, pos += 2);
				long eightByte = Long.parseLong(noOfEightByteIO, 16);
				List<Point> eightBytePoints = new ArrayList<Point>();

				for (int l = 0; l < eightByte; l++) {
					String iOId = hexaData.substring(pos, pos += 2);
					String IOValue = hexaData.substring(pos, pos += 16);
					com.pcs.device.gateway.teltonika.devicemanager.bean.Point point = pointMap.get(ConversionUtils.convertToLong(iOId).toString());
					Point eightBytePoint = Point.getPoint(PointDataTypes.valueOf(point.getDataType().toUpperCase()).getType());
					eightBytePoint.setPointId(ConversionUtils.convertToLong(iOId).toString());
					eightBytePoint.setPointName(point.getPointName());
					eightBytePoint.setUnit(point.getUnit());
					eightBytePoint.setPointAccessType(point.getAccessType());
					eightBytePoint.setData(IOValue);
					oneBytePoints.add(eightBytePoint);
					eightBytePoint = null;
				}
				noOfEightByteIO = null;
				// EIGHT BYTE INFO EXTRACTED
				dataPoints.addAll(eightBytePoints);
				dataPoints.addAll(fourBytePoints);
				dataPoints.addAll(twoBytePoints);
				dataPoints.addAll(oneBytePoints);

				messages.add(message);
				lastPosition = pos;
				// display(messageParameters);353976013128388
			}// ends one hexaData packet decoding
		} catch (Exception ex) {
			LOGGER.error("exception teltonika decoding....", ex);
			LOGGER.error("Error decoding : " + hexaData);
			throw new MessageDecodeException("Exception In Decoding The Message", ex);
		}
		LOGGER.info("Size of message points : " + messages.size());
		return messages;

	}



}
