package com.pcs.device.gateway.meitrack.decoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.meitrack.constant.ProtocolConstant;
import com.pcs.device.gateway.meitrack.message.MeitrackMessage;
import com.pcs.device.gateway.meitrack.util.ChecksumUtil;
import com.pcs.deviceframework.connection.utils.ConversionUtils;
import com.pcs.deviceframework.decoder.adapter.DecoderAdapter;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.types.PointDataTypes;
import com.pcs.deviceframework.decoder.exception.MessageDecodeException;

public class MeiTrackDecoder extends DecoderAdapter {
	// Logger logger=Logger.getLogger(MeiTrackDecoder.class);

	private static final String NOTAVAILABLE = "Not Available";
	private String hexaData = null;
	Date recTime = null;
	String imei = null;
	String deviceIp = null;
	private String packetHeader = null;
	public static Logger logger = LoggerFactory.getLogger(MeiTrackDecoder.class);
	private List<Message> readMessage = null;

	public MeiTrackDecoder(String deviceip, Date recTime, String packetHeader) {
		this.deviceIp = deviceip;
		this.recTime = recTime;
		this.packetHeader = packetHeader;
	}

	public List<Message> decodeMessage(String deviceIp, String receivedTime,
			ArrayList<String> configuredDevices) throws MessageDecodeException {
		return readMessage();
	}

	public List<Message> readMessage() throws MessageDecodeException {
		List<Message> allMsgDataPoints = new ArrayList<Message>();
		logger.error("dataMessage....>" + hexaData);
		String asciiMsg = ConversionUtils.convertToASCII(hexaData).toString();// used for
																	// production
																	// ConversionUtils.convertToASCII(hexaData);//ConversionUtils.convertToASCII(hexaData);//used
																	// for
																	// production
																	// ConversionUtils.convertToASCII(hexaData);//
																	// used for
																	// testing
																	// hexaData

		try {
			MeitrackMessage message = new MeitrackMessage();
			List<Point> dataPoints = new ArrayList<Point>();
			message.setPoints(dataPoints);
			String eventId = null;

			logger.error("asciiMsg..." + asciiMsg);
			String[] data = asciiMsg.split(",");

			logger.error("packetHeader..." + packetHeader);
			String packageFlag = data[0];
			message.setHeader(packageFlag.substring(0, 2));

			Point flagPt = Point.getPoint(PointDataTypes.BASE.getType());
			flagPt.setData(packageFlag.substring(2, 3));
			dataPoints.add(flagPt);
			message.setDataLength(Long.parseLong(packageFlag.substring(3, packageFlag.length())));

			String imei = data[1];
			message.setRawData(hexaData);
			message.setSourceId(imei);
			message.setReceivedTime(recTime);

			Point commandPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("command");
			commandPt.setData(data[2]);
			dataPoints.add(commandPt);

			eventId = data[3];
			String eventType = eventId;//DevicePointInitializer.getPointMap(deviceName, eventId);
			message.setReason(eventType);

			String latitude = data[4];
			Point latPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Latitude");
			latPt.setData(latitude);
			dataPoints.add(latPt);
			latitude = null;

			String longitude = data[5];
			Point lngPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Longitude");
			lngPt.setData(longitude);
			dataPoints.add(lngPt);
			longitude = null;

			String msgTime = data[6];
			DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
			Date gpsTime = dateFormat.parse(msgTime);
			Long timeInMilli = gpsTime.getTime();
			message.setTime(timeInMilli);

			String gpsValid = data[7];
			Point gpsvalidityPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("GPS validity");
			if (gpsValid.equals("A")) {
				gpsvalidityPt.setData(ProtocolConstant.VALID);
			} else {
				gpsvalidityPt.setData(ProtocolConstant.INVALID);
			}
			dataPoints.add(gpsvalidityPt);
			gpsValid = null;

			Point visibleSatPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Visible Satellites");
			visibleSatPt.setData(data[8]);
			dataPoints.add(visibleSatPt);

			Point gsmSignalPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Gsm Signal");
			gsmSignalPt.setData(data[9]);
			dataPoints.add(gsmSignalPt);

			Point speedPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Speed"); // ask speed
			speedPt.setData(data[10]);
			dataPoints.add(speedPt);

			Point anglePt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Angle"); // ask speed
			anglePt.setData(data[11]);
			dataPoints.add(anglePt);

			Point hdopPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("hdop");
			hdopPt.setData(data[12]);
			dataPoints.add(hdopPt);

			Point altitudePt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Gps Altitude");// ask altitute
			altitudePt.setData(data[13]);
			dataPoints.add(altitudePt);

			Point journeyPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Mileage");
			journeyPt.setData(data[14]);
			dataPoints.add(journeyPt);

			Point odometerPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Virtual Odometer");
			odometerPt.setData(data[14]);
			dataPoints.add(odometerPt);

			Point runTimePt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("RunTime");
			runTimePt.setData(data[15]);
			dataPoints.add(runTimePt);

			String baseId = data[16];
			String arr[] = baseId.split("\\|");

			if (arr.length == 4) {
				Point mccPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("MCC");
				Point mncPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("MNC");
				Point lacPt = Point.getPoint(PointDataTypes.BASE.getType());// new Point("LAC");
				Point ciPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("CI");
				mccPt.setData(arr[0]);
				mncPt.setData(arr[1]);
				lacPt.setData(arr[2]);
				ciPt.setData(arr[3]);
				dataPoints.add(mccPt);
				dataPoints.add(mncPt);
				dataPoints.add(ciPt);
			}
			arr = null;
			baseId = null;

			// state is represented as binary. This has to be converted in 16
			// bit format to identify the status of digital input and output
			// points.
			String state = data[17];
			String stateFormatedBinary = ConversionUtils.convertToBinaryExt(state,ConversionUtils.FORMAT_SIXTEEN_DIGIT).toString();// formating to
															// sixteen digits.
			char[] dinBits = stateFormatedBinary.toCharArray();
			for (int i = 0; i < dinBits.length; i++) {

				switch (i) {
					case 15:// DOP1
						Point dop1Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 1");
						dop1Pt.setData(dinBits[i]);
						dataPoints.add(dop1Pt);
						break;
					case 14:// DOP2
						Point dop2Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 2");
						dop2Pt.setData(dinBits[i]);
						dataPoints.add(dop2Pt);
						break;
					case 13:// DOP3
						Point dop3Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 3");
						dop3Pt.setData(dinBits[i]);
						dataPoints.add(dop3Pt);
						break;
					case 12:// DOP4
						Point dop4Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 4");
						dop4Pt.setData(dinBits[i]);
						dataPoints.add(dop4Pt);
						break;
					case 11:// DOP5
						Point dop5Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 5");
						dop5Pt.setData(dinBits[i]);
						dataPoints.add(dop5Pt);
						break;
					case 10:// DOP6
						Point dop6Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 6");
						dop6Pt.setData(dinBits[i]);
						dataPoints.add(dop6Pt);
						break;
					case 9:// DOP7
						Point dop7Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 7");
						dop7Pt.setData(dinBits[i]);
						dataPoints.add(dop7Pt);
						break;
					case 8:// DOP8
						Point dop8Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Output 8");
						dop8Pt.setData(dinBits[i]);
						dataPoints.add(dop8Pt);
						break;

					case 7:// DIN1
						Point din1Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 1");
						din1Pt.setData(dinBits[i]);
						dataPoints.add(din1Pt);
						break;
					case 6:// DIN2
						Point din2Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 2");
						din2Pt.setData(dinBits[i]);
						dataPoints.add(din2Pt);
						break;
					case 5:// DIN3
						Point din3Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 3");
						din3Pt.setData(dinBits[i]);
						dataPoints.add(din3Pt);
						break;
					case 4:// DIN4
						Point din4Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 4");
						din4Pt.setData(dinBits[i]);
						dataPoints.add(din4Pt);
						break;
					case 3:// DIN5-Engine Status configured
						Point din5Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 5");
						din5Pt.setData(dinBits[i]);
						dataPoints.add(din5Pt);
						break;
					case 2:// DIN6
						Point din6Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 6");
						din6Pt.setData(dinBits[i]);
						dataPoints.add(din6Pt);
						break;
					case 1:// DIN7
						Point din7Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 7");
						din7Pt.setData(dinBits[i]);
						dataPoints.add(din7Pt);
						break;
					case 0:// DIN8
						Point din8Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Digital Input 8");
						din8Pt.setData(dinBits[i]);
						dataPoints.add(din8Pt);
						break;

					default:
						break;
				}

			}
			state = null;

			String ad = data[18];
			String arr1[] = ad.split("\\|");
			if (arr1.length > 0) {
				Point ad1Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Analog Input 1");
				ad1Pt.setData(arr1[0]);
				dataPoints.add(ad1Pt);

				Point ad2Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Analog Input 2");
				ad2Pt.setData(arr1[1]);
				dataPoints.add(ad2Pt);

				Point ad3Pt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Analog Input 3");
				ad3Pt.setData(arr1[2]);
				dataPoints.add(ad3Pt);

				Point deviceBatteryPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Device Battery");
				deviceBatteryPt.setData(arr1[3]);
				dataPoints.add(deviceBatteryPt);

				Point vehicleBatteryPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("Vehicle Battery");
				vehicleBatteryPt.setData(arr1[4]);
				dataPoints.add(vehicleBatteryPt);
			}
			ad = null;
			arr = null;


			if (eventType.equals(ProtocolConstant.RFID)) {
				Point rfidPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point("RFID");
				rfidPt.setData(data[19]);
				dataPoints.add(rfidPt);
				
				String checksum = data[20];
				checksum = checksum.substring(1, 3);
				if(!ChecksumUtil.verifyCheckSum(hexaData,checksum)){
					throw new MessageDecodeException("Exception In Decoding The Message!! Invalid checksum");
				}
				checksum = null;
			}else{
				String checksum = data[19];
				checksum = checksum.substring(1, 3);
				if(!ChecksumUtil.verifyCheckSum(hexaData,checksum)){
					throw new MessageDecodeException("Exception In Decoding The Message!! Invalid checksum");
				}
				checksum = null;
			}
			eventId = null;
			Point loginPt = Point.getPoint(PointDataTypes.BASE.getType());//new Point();
			loginPt.setPointName("login status");
			loginPt.setData(NOTAVAILABLE);
			dataPoints.add(loginPt);
			allMsgDataPoints.add(message);
		} catch (Exception ex) {
			logger.error("Exception In Decoding The Message", ex);
			throw new MessageDecodeException("Exception In Decoding The Message", ex);
		}
		return allMsgDataPoints;
	}

	
	public static void main(String[] args) {
		MeiTrackDecoder meiTrackDecoder = new MeiTrackDecoder("", Calendar.getInstance().getTime(), null);
		try {
			meiTrackDecoder
					.setData("2424553134312C3836323137303031333639323630312C4141412C33352C32352E3232363233302C35352E3330363734302C3132313132363030303132302C412C31302C33312C302C3135332C302E392C31342C333736323433312C363535363430302C3432347C327C313233457C444538312C303030302C303346467C303346467C7C303244367C303130442C2A39420D0A");
			List<Message> createMessage = meiTrackDecoder.decodeMessage("", Calendar.getInstance()
					.getTime().toString(), null);
			for (Message message : createMessage) {
				System.out.println(message.getTime());
				System.out.println(message.getSourceId());
				System.out.println(message.getReceivedTime());
				System.out.println(message.getReason());
				List<Point> Points = message.getPoints();
				for (Point Point : Points) {
					System.out.println(Point.getPointName() + " " + Point.getData());
				}
			}

		} catch (MessageDecodeException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.driver.core.decoder.BaseDecoder#setData(java.lang.String)
	 */

	public void setData(String hexaData) {
		this.hexaData = hexaData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.driver.core.decoder.BaseDecoder#getData()
	 */

	public String getData() {
		return hexaData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.driver.core.decoder.BaseDecoder#getPoints()
	 */

	public List<List<Point>> getPoints() throws MessageDecodeException {
		List<List<Point>> Points = new ArrayList<List<Point>>();
		if (hexaData != null && readMessage == null) {
			readMessage = readMessage();
			for (Message message : readMessage) {
				Points.add(message.getPoints());
			}
		}
		return Points;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.driver.core.decoder.BaseDecoder#createMessage()
	 */

	public List<Message> createMessage() throws MessageDecodeException {
		if (hexaData != null) {
			try {
				return readMessage();
			} catch (MessageDecodeException e) {
				logger.error("Error Creating Messages", e);
				throw new MessageDecodeException("Error creating message", e);
			}
		} else {
			logger.error("Error Creating Messages!!! \t No Data Specified For Decoding");
			throw new MessageDecodeException(
					"Error creating message...No Data Specified For Decoding");
		}
	}

}
