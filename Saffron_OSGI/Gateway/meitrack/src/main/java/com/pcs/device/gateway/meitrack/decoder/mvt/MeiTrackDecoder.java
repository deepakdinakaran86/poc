package com.pcs.device.gateway.meitrack.decoder.mvt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.meitrack.bundle.utils.DeviceManagerUtil;
import com.pcs.device.gateway.meitrack.constant.mvt.ProtocolConstant;
import com.pcs.device.gateway.meitrack.message.MeitrackMessage;
import com.pcs.device.gateway.meitrack.utils.ChecksumUtil;
import com.pcs.device.gateway.meitrack.utils.SupportedDevices;
import com.pcs.device.gateway.meitrack.utils.SupportedDevices.Devices;
import com.pcs.saffron.cipher.adapter.DecoderAdapter;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.cipher.exception.MessageDecodeException;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.Device;

public class MeiTrackDecoder extends DecoderAdapter {

	private String hexaData = null;
	Date recTime = null;
	String imei = null;
	String deviceIp = null;
	private String packetHeader = null;
	public static Logger logger = LoggerFactory.getLogger(MeiTrackDecoder.class);
	private List<Message> readMessage = null;
	private String[] data = null;
	private String asciiMsg = null;

	public MeiTrackDecoder(String deviceip, Date recTime) {
		this.deviceIp = deviceip;
		this.recTime = recTime;
	}

	public List<Message> decodeMessage(String deviceIp, String receivedTime,
			ArrayList<String> configuredDevices) throws MessageDecodeException {
		return readMessage();
	}

	
	private void extractIdentification(){
		asciiMsg = hexaData;
		
		logger.error("asciiMsg...{}" , asciiMsg);
		data = asciiMsg.split(",");

		logger.error("packetHeader...{}" , packetHeader);
		packetHeader = data[0];
		
		imei = data[1];
		
	}
	
	public String getImei() {
		return imei;
	}

	public List<Message> readMessage() throws MessageDecodeException {
		List<Message> allMsgDataPoints = new ArrayList<Message>();
		logger.error("dataMessage....>{}" ,hexaData);
		

		try {
			
			
			DefaultConfiguration configuration = (DefaultConfiguration) DeviceManagerUtil.getMeitrackDeviceManager().getConfiguration(imei,SupportedDevices.getGateway(Devices.MVTXXX));
			Device device = null;
			
			if(configuration != null) {
				device = configuration.getDevice();
			}
			
			HashMap<String, Point> pointMap = new HashMap<String, Point>();
			if(device != null){
				pointMap = DeviceManagerUtil.getMeitrackDeviceManager().getPointsByProtocolAndDeviceType(SupportedDevices.getGateway(Devices.MVTXXX));
				logger.info("Configuration Fetch returned successfully with point map");
			}else{
				pointMap = DeviceManagerUtil.getMeitrackDeviceManager().getPointsByProtocolAndDeviceType(SupportedDevices.getGateway(Devices.MVTXXX));
			}
			
			MeitrackMessage message = new MeitrackMessage();
			List<Point> dataPoints = new ArrayList<Point>();
			message.setPoints(dataPoints);
			String eventId = null;
			message.setRawData(hexaData);
			message.setSourceId(imei);
			message.setReceivedTime(recTime);
			
			message.setHeader(packetHeader.substring(0, 2));

			Point flagPt = Point.getPoint(PointDataTypes.BASE.getType());
			flagPt.setPointName("Flag");
			flagPt.setDisplayName("Flag");
			flagPt.setPointId("Flag");
			flagPt.setData(packetHeader.substring(2, 3));
			dataPoints.add(flagPt);
			message.setDataLength(Long.parseLong(packetHeader.substring(3, packetHeader.length())));

			Point commandPt = pointMap.get("command");//new Point("command");
			commandPt.setData(data[2]);
			dataPoints.add(commandPt);

			eventId = data[3];
			Point eventType = pointMap.get(eventId);//DevicePointInitializer.getPointMap(deviceName, eventId);
			eventType.setData(eventType.getDisplayName());
			message.setReason(eventType.getDisplayName());

			String latitude = data[4];
			Point latPt = pointMap.get("Latitude");
			latPt.setData(latitude);
			dataPoints.add(latPt);
			latitude = null;

			String longitude = data[5];
			Point lngPt = pointMap.get("Longitude");
			lngPt.setData(longitude);
			dataPoints.add(lngPt);
			longitude = null;

			String msgTime = data[6];
			DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
			Date gpsTime = dateFormat.parse(msgTime);
			Long timeInMilli = gpsTime.getTime();
			message.setTime(timeInMilli);

			String gpsValid = data[7];
			Point gpsvalidityPt = pointMap.get("GPS Validity");//new Point("GPS validity");
			if (gpsValid.equals("A")) {
				gpsvalidityPt.setData(ProtocolConstant.VALID);
			} else {
				gpsvalidityPt.setData(ProtocolConstant.INVALID);
			}
			dataPoints.add(gpsvalidityPt);
			gpsValid = null;

			Point visibleSatPt = pointMap.get("Visible Satellites");//new Point("Visible Satellites");
			visibleSatPt.setData(data[8]);
			dataPoints.add(visibleSatPt);

			Point gsmSignalPt = pointMap.get("GSM Signal");//new Point("Gsm Signal");
			gsmSignalPt.setData(data[9]);
			dataPoints.add(gsmSignalPt);

			Point speedPt = pointMap.get("Speed");//new Point("Speed"); // ask speed
			speedPt.setData(data[10]);
			dataPoints.add(speedPt);

			Point anglePt = pointMap.get("Angle");//new Point("Angle"); // ask speed
			anglePt.setData(data[11]);
			dataPoints.add(anglePt);

			Point hdopPt = pointMap.get("HDOP");//new Point("hdop");
			hdopPt.setData(data[12]);
			dataPoints.add(hdopPt);

			Point altitudePt = pointMap.get("Altitude");//new Point("Gps Altitude");// ask altitute
			altitudePt.setData(data[13]);
			dataPoints.add(altitudePt);

			Point odometerPt = pointMap.get("Virtual Odometer");//new Point("Virtual Odometer");
			odometerPt.setData(data[14]);
			dataPoints.add(odometerPt);

			Point runTimePt = pointMap.get("Run Time");//new Point("RunTime");
			runTimePt.setData(data[15]);
			dataPoints.add(runTimePt);

			String baseId = data[16];
			String arr[] = baseId.split("\\|");

			if (arr.length == 4) {
				Point mccPt = pointMap.get("MCC");//new Point("MCC");
				Point mncPt = pointMap.get("MNC");//new Point("MNC");
				Point lacPt = pointMap.get("LAC");// new Point("LAC");
				Point ciPt = pointMap.get("CI");//new Point("CI");
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
			// bit format to identify the status of DIN and output
			// points.
			String state = data[17];
			String stateFormatedBinary = ConversionUtils.convertToBinaryExt(state,ConversionUtils.FORMAT_SIXTEEN_DIGIT).toString();// formating to
															// sixteen digits.
			char[] dinBits = stateFormatedBinary.toCharArray();
			for (int i = 0; i < dinBits.length; i++) {

				switch (i) {
					case 15:// DOP1
						Point dop1Pt = pointMap.get("DO 1");//new Point("DO 1");
						dop1Pt.setData(dinBits[i]);
						dataPoints.add(dop1Pt);
						break;
					case 14:// DOP2
						Point dop2Pt = pointMap.get("DO 2");//new Point("DO 2");
						dop2Pt.setData(dinBits[i]);
						dataPoints.add(dop2Pt);
						break;
					case 13:// DOP3
						Point dop3Pt = pointMap.get("DO 3");//new Point("DO 3");
						dop3Pt.setData(dinBits[i]);
						dataPoints.add(dop3Pt);
						break;
					case 12:// DOP4
						Point dop4Pt = pointMap.get("DO 4");//new Point("DO 4");
						dop4Pt.setData(dinBits[i]);
						dataPoints.add(dop4Pt);
						break;
					case 11:// DOP5
						Point dop5Pt = pointMap.get("DO 5");//new Point("DO 5");
						dop5Pt.setData(dinBits[i]);
						dataPoints.add(dop5Pt);
						break;
					case 10:// DOP6
						Point dop6Pt = pointMap.get("DO 6");//new Point("DO 6");
						dop6Pt.setData(dinBits[i]);
						dataPoints.add(dop6Pt);
						break;
					case 9:// DOP7
						Point dop7Pt = pointMap.get("DO 7");//new Point("DO 7");
						dop7Pt.setData(dinBits[i]);
						dataPoints.add(dop7Pt);
						break;
					case 8:// DOP8
						Point dop8Pt = pointMap.get("DO 8");//new Point("DO 8");
						dop8Pt.setData(dinBits[i]);
						dataPoints.add(dop8Pt);
						break;

					case 7:// DIN1
						Point din1Pt = pointMap.get("DIN 1");//new Point("DIN 1");
						din1Pt.setData(dinBits[i]);
						dataPoints.add(din1Pt);
						break;
					case 6:// DIN2
						Point din2Pt = pointMap.get("DIN 2");//new Point("DIN 2");
						din2Pt.setData(dinBits[i]);
						dataPoints.add(din2Pt);
						break;
					case 5:// DIN3
						Point din3Pt = pointMap.get("DIN 3");//new Point("DIN 3");
						din3Pt.setData(dinBits[i]);
						dataPoints.add(din3Pt);
						break;
					case 4:// DIN4
						Point din4Pt = pointMap.get("DIN 4");//new Point("DIN 4");
						din4Pt.setData(dinBits[i]);
						dataPoints.add(din4Pt);
						break;
					case 3:// DIN5-Engine Status configured
						Point din5Pt = pointMap.get("DIN 5");//new Point("DIN 5");
						din5Pt.setData(dinBits[i]);
						dataPoints.add(din5Pt);
						break;
					case 2:// DIN6
						Point din6Pt = pointMap.get("DIN 6");//new Point("DIN 6");
						din6Pt.setData(dinBits[i]);
						dataPoints.add(din6Pt);
						break;
					case 1:// DIN7
						Point din7Pt = pointMap.get("DIN 7");//new Point("DIN 7");
						din7Pt.setData(dinBits[i]);
						dataPoints.add(din7Pt);
						break;
					case 0:// DIN8
						Point din8Pt = pointMap.get("DIN 8");//new Point("DIN 8");
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
				Point ad1Pt = pointMap.get("AIN 1");//new Point("AIN 1");
				ad1Pt.setData(arr1[0]);
				dataPoints.add(ad1Pt);

				Point ad2Pt = pointMap.get("AIN 2");//new Point("AIN 2");
				ad2Pt.setData(arr1[1]);
				dataPoints.add(ad2Pt);

				Point ad3Pt = pointMap.get("AIN 3");//new Point("AIN 3");
				ad3Pt.setData(arr1[2]);
				dataPoints.add(ad3Pt);

				Point deviceBatteryPt = pointMap.get("Device Battery");//new Point("Device Battery");
				deviceBatteryPt.setData(arr1[3]);
				dataPoints.add(deviceBatteryPt);

				Point vehicleBatteryPt = pointMap.get("External Power");//new Point("Vehicle Battery");
				vehicleBatteryPt.setData(arr1[4]);
				dataPoints.add(vehicleBatteryPt);
			}
			ad = null;
			arr = null;


			if (eventType.getDisplayName().equals(ProtocolConstant.RFID)) {
				Point rfidPt = pointMap.get("RFID");//new Point("RFID");
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
			allMsgDataPoints.add(message);
		} catch (Exception ex) {
			logger.error("Exception In Decoding The Message", ex);
			throw new MessageDecodeException("Exception In Decoding The Message", ex);
		}
		return allMsgDataPoints;
	}

	
	public static void main(String[] args) {
		MeiTrackDecoder meiTrackDecoder = new MeiTrackDecoder("", Calendar.getInstance().getTime());
		//y135,862170013047731,AAA,35,24.938785,55.051318,151207155132,A,11,31,0,179,1,12,3923,100823,424|2|10A6|1EEF,0000,03FF|03FF||02D5|0106
		try {
			String a = "|";
			int d = a.charAt(0);
			System.err.println(d);
			ChecksumUtil.verifyCheckSum("$$y135,862170013047731,AAA,35,24.938785,55.051318,151207155132,A,11,31,0,179,1,12,3923,100823,424|2|10A6|1EEF,0000,03FF|03FF||02D5|0106,*B0", "176");
			//$$y135,862170013047731,AAA,35,24.938785,55.051318,151207155132,A,11,31,0,179,1,12,3923,100823,424|2|10A6|1EEF,0000,03FF|03FF||02D5|0106,*B0
			meiTrackDecoder
					.setData("$$y135,862170013047731,AAA,35,24.938785,55.051318,160102145810,A,11,31,0,179,1,12,3923,100823,424|2|10A6|1EEF,0000,03FF|03FF||02D5|0106,*B0");
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
		extractIdentification();
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
