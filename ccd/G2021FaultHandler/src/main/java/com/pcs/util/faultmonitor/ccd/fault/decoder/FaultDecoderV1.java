/**
 * 
 */
package com.pcs.util.faultmonitor.ccd.fault.decoder;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.util.faultmonitor.ccd.fault.bean.DeviceData;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultData;
import com.pcs.util.faultmonitor.ccd.fault.bean.Parameter;
import com.pcs.util.faultmonitor.ccd.fault.bean.Snapshot;
import com.pcs.util.faultmonitor.ccd.fault.decoder.enums.DecoderState;
import com.pcs.util.faultmonitor.ccd.fault.decoder.enums.FaultDataIdentificationState;

/**
 * @author pcseg171
 *
 */
public class FaultDecoderV1 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FaultDecoderV1.class);
	
	/*
	 * Line based separator
	 * line number details as below
	 * 
	 * 1: Notification version
	 * 2: Message_Type
	 * 3: Device ID
	 * 4: Make
	 * 5: Model
	 * 6: Serial Number
	 * 7: Unit Number
	 * 8: VID Number
	 * 9: Occurance Date Time
	 * 10: Active
	 * 11: Datalink_Bus
	 * 12: Source_Address
	 * 13: Activefault_Data
	 * 14: LatitudeString
	 * 15: LongitudeString
	 * 16: AltitudeString
	 * 17: Direction_Heading
	 * 18: GPS_Vehicle_Speed
	 * 19:Snapshot 
	 * 
	 */
	private static final String colonDelimiter = ":",commaDelimiter=",",equalDelimiter="=",openBraceDelimiter="{",closeBraceDelimiter="}";
	
	public static DeviceData decode(File faultFile) throws Exception{
		List<String> readLines = FileUtils.readLines(faultFile);
		if(!readLines.isEmpty()){
			
			DecoderState state = DecoderState.INIT;
			DeviceData deviceData = new DeviceData();
			deviceData.setRealtimeData(false);
			for (String line : readLines) {
				LOGGER.info("Read Line: {}",line);
				switch (state) {
				case INIT:
					extractNotification(line, deviceData);
					LOGGER.info("Notification versions: {}",deviceData.getNotificationVersion());
					state = DecoderState.NOTIFICATION;
					break;
				case NOTIFICATION:
					state = DecoderState.MESSAGETYPE;
					extractMessageType(line, deviceData);
					break;
				case MESSAGETYPE:
					state = DecoderState.DEVICEID;
					extractDeviceId(line, deviceData);
					break;
				case DEVICEID:
					state = DecoderState.MAKE;
					extractMake(line, deviceData);
					break;
				case MAKE:
					state = DecoderState.MODEL;
					extractModel(line, deviceData);
					break;
				case MODEL:
					state = DecoderState.SERIALNUMBER;
					extractSerialNumber(line, deviceData);
					break;
				case SERIALNUMBER:
					state = DecoderState.UNITNUMBER;
					extractUnitNumber(line, deviceData);
					break;
				case UNITNUMBER:
					state = DecoderState.VIDNUMBER;
					extractVidNumber(line, deviceData);
					break;
				case VIDNUMBER:
					state = DecoderState.OCCURANCEDATE;
					extractOccuranceDate(line, deviceData);
					break;
				case OCCURANCEDATE:
					state = DecoderState.ACTIVE;
					extractStatus(line, deviceData);
					break;
				case ACTIVE:
					state = DecoderState.DATALINKBUS;
					extractDataLink(line, deviceData);
					break;
				case DATALINKBUS:
					state = DecoderState.SOURCEADDRESS;
					extractSourceAddress(line, deviceData);
					break;
				case SOURCEADDRESS:
					state = DecoderState.ACTIVEFAULT;
					extractFaultData(line, deviceData);
					break;
				case ACTIVEFAULT:
					state = DecoderState.LATITUDE;
					extractLatitude(line, deviceData);
					break;
				case LATITUDE:
					state = DecoderState.LONGITUDE;
					extractLongitude(line, deviceData);
					break;
				case LONGITUDE:
					state = DecoderState.ALTITUDE;
					extractAltitude(line, deviceData);
					break;
				case ALTITUDE:
					state = DecoderState.DIRECTION;
					extractDirection(line, deviceData);
					break;
				case DIRECTION:
					state = DecoderState.ESCAPE;
					extractSpeed(line, deviceData);
					break;
				case ESCAPE:
					state = DecoderState.SPEED;
					break;
				case SPEED:
					state = DecoderState.SPEED;
					extractTimestamp(line, deviceData);
					break;

				default:
					break;
				}
			}
			return deviceData;	
		}
		return null;
	}
	
	private static void extractAltitude(String data,DeviceData deviceData) {
		StringTokenizer altitudeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(altitudeToken, 2))
			return;
		altitudeToken.nextElement();///skip key
		deviceData.setAltitude(altitudeToken.nextToken());
	}

	private static void extractDataLink(String data,DeviceData deviceData) {
		StringTokenizer dataLinkToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(dataLinkToken, 2))
			return;
		dataLinkToken.nextElement();///skip key
		deviceData.setDatalinkBus(dataLinkToken.nextToken());
		
	}

	private static void extractDeviceId(String data,DeviceData deviceData) {
		StringTokenizer deviceIdToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(deviceIdToken, 2))
			return;
		deviceIdToken.nextElement();///skip key
		deviceData.setDeviceId(deviceIdToken.nextToken());
	}

	private static void extractDirection(String data,DeviceData deviceData) {
		StringTokenizer directionToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(directionToken, 2))
			return;
		directionToken.nextElement();///skip key
		deviceData.setDirection(directionToken.nextToken());
	}

	private static void extractFaultData(String data,DeviceData deviceData) throws Exception {
		
		StringTokenizer faultDataToken = new StringTokenizer(data,colonDelimiter);
		faultDataToken.nextToken();//skip key
		
		String LSInfo = faultDataToken.nextToken();
		
		
		FaultDataIdentificationState identificationState = FaultDataIdentificationState.LS;
		boolean cycleComplete = false;
		
		FaultData faultData = null;
		do {
			switch (identificationState) {
			
			case LS:
				faultData = new FaultData();
				extractLS(faultData, LSInfo);
				identificationState = FaultDataIdentificationState.SPN;
				break;
			case SPN:
				String SPNInfo = faultDataToken.nextToken();
				extractSPN(faultData, SPNInfo);
				identificationState = FaultDataIdentificationState.FMI;
				break;
				
			case FMI:
				String  FMIInfo = faultDataToken.nextToken();
				extractFMI(faultData, FMIInfo);
				identificationState = FaultDataIdentificationState.OC;
				break;
				
			case OC:
				String OCinfo = faultDataToken.nextToken();
				extractOC(faultData, OCinfo);
				cycleComplete = true;
				deviceData.addFaultData(faultData);
				identificationState = FaultDataIdentificationState.LS;
				break;
				
			default:
				break;
			}
			
			if(!faultDataToken.hasMoreElements()){
				if(!cycleComplete)
					throw new Exception("Fault Extraction Not Complete, Currupt Data");
				identificationState = FaultDataIdentificationState.COMPLETE;
			}
		} while (identificationState != FaultDataIdentificationState.COMPLETE);
		
	}

	private static void extractLatitude(String data,DeviceData deviceData) {
		StringTokenizer latitudeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(latitudeToken, 2))
			return;
		latitudeToken.nextElement();///skip key
		deviceData.setLatitude(latitudeToken.nextToken());
		
	}

	private static void extractLongitude(String data,DeviceData deviceData) {
		StringTokenizer longitudeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(longitudeToken, 2))
			return;
		longitudeToken.nextElement();///skip key
		deviceData.setLongitude(longitudeToken.nextToken());
		
	}

	private static void extractMake(String data, DeviceData deviceData) {
		StringTokenizer makeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(makeToken, 2))
			return;
		makeToken.nextElement();///skip key
		deviceData.setMake(makeToken.nextToken());
		
	}

	private static void extractMessageType(String data,DeviceData deviceData) {
		StringTokenizer messageTypeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(messageTypeToken, 2))
			return;
		messageTypeToken.nextElement();///skip key
		deviceData.setMessageType(messageTypeToken.nextToken());
	}

	private static void extractModel(String data, DeviceData deviceData) {
		StringTokenizer modelToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(modelToken, 2))
			return;
		modelToken.nextElement();///skip key
		deviceData.setModel(modelToken.nextToken());
	}

	private static void extractNotification(String data,DeviceData deviceData){
		StringTokenizer notificationToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(notificationToken, 2))
			return;
		notificationToken.nextElement();///skip key
		deviceData.setNotificationVersion(notificationToken.nextToken());
	}

	private static void extractOccuranceDate(String data,DeviceData deviceData) {
		StringTokenizer ocDateTimeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(ocDateTimeToken, 4))
			return;
		ocDateTimeToken.nextElement();///skip key
		StringBuffer timeBuffer = new StringBuffer();  
		timeBuffer.append(ocDateTimeToken.nextToken()).append(colonDelimiter).
							append(ocDateTimeToken.nextToken()).append(colonDelimiter).
								append(ocDateTimeToken.nextToken());
		
		deviceData.setOccuranceDateTime(timeBuffer.toString());
	}

	private static void extractSerialNumber(String data,DeviceData deviceData) {
		StringTokenizer serialNumberToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(serialNumberToken, 2))
			return;
		serialNumberToken.nextElement();///skip key
		deviceData.setSerialNumber(serialNumberToken.nextToken());
	}

	private static void extractSourceAddress(String data,DeviceData deviceData) {
		StringTokenizer sourceAddressToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(sourceAddressToken, 2))
			return;
		sourceAddressToken.nextElement();///skip key
		deviceData.setSourceAddress(sourceAddressToken.nextToken());
	}

	private static void extractSpeed(String data, DeviceData deviceData) {
		StringTokenizer speedToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(speedToken, 2))
			return;
		speedToken.nextElement();///skip key
		deviceData.setSpeed(speedToken.nextToken());
	}

	private static void extractStatus(String data, DeviceData deviceData) {
		StringTokenizer statusToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(statusToken, 2))
			return;
		statusToken.nextElement();///skip key
		deviceData.setActive(statusToken.nextToken());
	}

	private static void extractTimestamp(String data,DeviceData deviceData) {
		Snapshot snapshot = new Snapshot();
		StringTokenizer snapshotToken = new StringTokenizer(data,openBraceDelimiter);
		
		if(!isValidToken(snapshotToken, 2))
			return;
		
		String timeInfo = snapshotToken.nextToken();
		StringTokenizer timeToken = new StringTokenizer(timeInfo,colonDelimiter);
		if(!isValidToken(timeToken, 4))
			return;
		timeToken.nextElement();//skip key
		
		StringBuffer timeBuffer = new StringBuffer();  
		timeBuffer.append(timeToken.nextToken()).append(colonDelimiter).
							append(timeToken.nextToken()).append(colonDelimiter).
								append(timeToken.nextToken());
		snapshot.setTimestamp(timeBuffer.toString());
		
		StringTokenizer parameterToken = new StringTokenizer(snapshotToken.nextToken(),closeBraceDelimiter);
		StringTokenizer parameterRefinedToken = new StringTokenizer(parameterToken.nextToken(),commaDelimiter);
		while (parameterRefinedToken.hasMoreElements()) {
			String parameterInfo =  parameterRefinedToken.nextToken();
			StringTokenizer parameterDataToken = new StringTokenizer(parameterInfo,equalDelimiter);
			snapshot.addParameter(new Parameter(parameterDataToken.nextToken(),parameterDataToken.nextToken()));
		}
		deviceData.addSnapshot(snapshot);
	}

	private static void extractUnitNumber(String data,DeviceData deviceData) {
		StringTokenizer unitNumberToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(unitNumberToken, 2))
			return;
		unitNumberToken.nextElement();///skip key
		deviceData.setUnitNumber(unitNumberToken.nextToken());
	}

	private static void extractVidNumber(String data,DeviceData deviceData) {
		StringTokenizer vidNumberToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(vidNumberToken, 2))
			return;
		vidNumberToken.nextElement();///skip key
		deviceData.setVidNumber(vidNumberToken.nextToken());
	}
	
	private static void extractOC(FaultData faultData, String OCinfo) {
		StringTokenizer OCToken = new StringTokenizer(OCinfo,equalDelimiter);
		if(!isValidToken(OCToken, 2))
			return;
		OCToken.nextElement();//skip key
		faultData.setOC(OCToken.nextToken());
	}

	private static void extractFMI(FaultData faultData, String FMIInfo) {
		StringTokenizer FMIToken = new StringTokenizer(FMIInfo,equalDelimiter);
		if(!isValidToken(FMIToken, 2))
			return;
		FMIToken.nextElement();//skip key
		faultData.setFMI(FMIToken.nextToken());
	}

	private static void extractSPN(FaultData faultData, String SPNInfo) {
		StringTokenizer SPNToken = new StringTokenizer(SPNInfo,equalDelimiter);
		if(!isValidToken(SPNToken, 2))
			return;
		SPNToken.nextElement();//skip key
		faultData.setSPN(SPNToken.nextToken());
	}

	private static void extractLS(FaultData faultData, String LSInfo) {
		StringTokenizer LSToken = new StringTokenizer(LSInfo,equalDelimiter);
		if(!isValidToken(LSToken, 2))
			return;
		LSToken.nextElement();//skip key
		//faultData.setLS(LSToken.nextToken());
	}

	
	private static boolean isValidToken(StringTokenizer tokenizer, Integer expectedCount){
		return tokenizer.countTokens()==expectedCount;
	}
	
}
