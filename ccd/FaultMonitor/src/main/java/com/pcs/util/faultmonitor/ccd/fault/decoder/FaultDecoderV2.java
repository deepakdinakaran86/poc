/**
 * 
 */
package com.pcs.util.faultmonitor.ccd.fault.decoder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.util.faultmonitor.ccd.fault.bean.FaultData;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultDataWrapper;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultParameter;
import com.pcs.util.faultmonitor.ccd.fault.bean.FaultSnapshot;
import com.pcs.util.faultmonitor.ccd.fault.decoder.enums.DecoderState;
import com.pcs.util.faultmonitor.ccd.fault.decoder.enums.FaultDataIdentificationState;

/**
 * @author pcseg171
 *
 */
public class FaultDecoderV2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FaultDecoderV2.class);
	
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
	
	static List<String> parameterNames = new ArrayList<String>();
	static HashMap<Integer, String> headers = new HashMap<Integer, String>();
	public static List<FaultData> decode(File faultFile) throws Exception{
		List<String> readLines = FileUtils.readLines(faultFile);
		if(!readLines.isEmpty()){
			
			DecoderState state = DecoderState.INIT;
			List<FaultData> faultDataIndex = new ArrayList<FaultData>();
			FaultDataWrapper faultDetails = new FaultDataWrapper();
			for (String line : readLines) {
				LOGGER.info("Read Line: {}",line);
				switch (state) {
				case INIT:
					extractNotification(line, faultDetails);
					LOGGER.info("Notification versions: {}",faultDetails.getNotificationVersion());
					state = DecoderState.NOTIFICATION;
					break;
				case NOTIFICATION:
					state = DecoderState.MESSAGETYPE;
					extractMessageType(line, faultDetails);
					break;
				case MESSAGETYPE:
					state = DecoderState.DEVICEID;
					extractDeviceId(line, faultDetails);
					break;
				case DEVICEID:
					state = DecoderState.MAKE;
					extractMake(line, faultDetails);
					break;
				case MAKE:
					state = DecoderState.MODEL;
					extractModel(line, faultDetails);
					break;
				case MODEL:
					state = DecoderState.SERIALNUMBER;
					extractSerialNumber(line, faultDetails);
					break;
				case SERIALNUMBER:
					state = DecoderState.UNITNUMBER;
					extractUnitNumber(line, faultDetails);
					break;
				case UNITNUMBER:
					state = DecoderState.VIDNUMBER;
					extractVidNumber(line, faultDetails);
					break;
				case VIDNUMBER:
					state = DecoderState.SOFTWAREID;
					extractSwId(line, faultDetails);
					break;
				case SOFTWAREID:
					state = DecoderState.CALIBRATIONID;
					extractCaliberationId(line, faultDetails);
					break;
				case CALIBRATIONID:
					state = DecoderState.OCCURANCEDATE;
					extractOccuranceDate(line, faultDetails);
					break;
				case OCCURANCEDATE:
					state = DecoderState.ACTIVE;
					extractStatus(line, faultDetails);
					break;
				case ACTIVE:
					state = DecoderState.DATALINKBUS;
					extractDataLink(line, faultDetails);
					break;
				case DATALINKBUS:
					state = DecoderState.SOURCEADDRESS;
					extractSourceAddress(line, faultDetails);
					break;
				case SOURCEADDRESS:
					state = DecoderState.ACTIVEFAULT;
					extractFaultData(line, faultDataIndex);
					break;
				case ACTIVEFAULT:
					state = DecoderState.LATITUDE;
					extractLatitude(line, faultDetails);
					break;
				case LATITUDE:
					state = DecoderState.LONGITUDE;
					extractLongitude(line, faultDetails);
					break;
				case LONGITUDE:
					state = DecoderState.ALTITUDE;
					extractAltitude(line, faultDetails);
					break;
				case ALTITUDE:
					state = DecoderState.DIRECTION;
					extractDirection(line, faultDetails);
					break;
				case DIRECTION:
					state = DecoderState.ESCAPE;
					extractSpeed(line, faultDetails);
					break;
				case ESCAPE:
					state = DecoderState.SPEED;
					break;
				case SPEED:
					state = DecoderState.SPN;
					extractColumnHeaders(line, faultDetails);
					break;
				case SPN:
					state = DecoderState.SPN;
					extractParameters(line, faultDetails);
					break;

				default:
					break;
				}
			}
			
			if(faultDataIndex.isEmpty()){
				throw new Exception("Corrupted file processed !!!,"+faultFile.getAbsolutePath());
			}else{
				for (FaultData faultData : faultDataIndex) {
					faultData.setFaultDataInfo(faultDetails);
				}
			}
			return faultDataIndex;	
		}
		return null;
	}
	
	private static void extractAltitude(String data,FaultDataWrapper faultDetails) {
		StringTokenizer altitudeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(altitudeToken, 2))
			return;
		altitudeToken.nextElement();///skip key
		faultDetails.setAltitude(altitudeToken.nextToken());
	}

	private static void extractDataLink(String data,FaultDataWrapper faultDetails) {
		StringTokenizer dataLinkToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(dataLinkToken, 2))
			return;
		dataLinkToken.nextElement();///skip key
		faultDetails.setDatalinkBus(dataLinkToken.nextToken());
		
	}

	private static void extractDeviceId(String data,FaultDataWrapper faultDetails) {
		StringTokenizer deviceIdToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(deviceIdToken, 2))
			return;
		deviceIdToken.nextElement();///skip key
		faultDetails.setDeviceId(deviceIdToken.nextToken());
	}

	private static void extractDirection(String data,FaultDataWrapper faultDetails) {
		StringTokenizer directionToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(directionToken, 2))
			return;
		directionToken.nextElement();///skip key
		faultDetails.setDirection(directionToken.nextToken());
	}

	private static void extractFaultData(String data,List<FaultData> faultDataIndex) throws Exception {
		
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
				faultDataIndex.add(faultData);
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

	private static void extractLatitude(String data,FaultDataWrapper faultDetails) {
		StringTokenizer latitudeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(latitudeToken, 2))
			return;
		latitudeToken.nextElement();///skip key
		faultDetails.setLatitude(latitudeToken.nextToken());
		
	}

	private static void extractLongitude(String data,FaultDataWrapper faultDetails) {
		StringTokenizer longitudeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(longitudeToken, 2))
			return;
		longitudeToken.nextElement();///skip key
		faultDetails.setLongitude(longitudeToken.nextToken());
		
	}

	private static void extractMake(String data, FaultDataWrapper faultDetails) {
		StringTokenizer makeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(makeToken, 2))
			return;
		makeToken.nextElement();///skip key
		faultDetails.setMake(makeToken.nextToken());
		
	}

	private static void extractMessageType(String data,FaultDataWrapper faultDetails) {
		StringTokenizer messageTypeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(messageTypeToken, 2))
			return;
		messageTypeToken.nextElement();///skip key
		faultDetails.setMessageType(messageTypeToken.nextToken());
	}

	private static void extractModel(String data, FaultDataWrapper faultDetails) {
		StringTokenizer modelToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(modelToken, 2))
			return;
		modelToken.nextElement();///skip key
		faultDetails.setModel(modelToken.nextToken());
	}

	private static void extractNotification(String data,FaultDataWrapper faultDetails){
		StringTokenizer notificationToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(notificationToken, 2))
			return;
		notificationToken.nextElement();///skip key
		faultDetails.setNotificationVersion(notificationToken.nextToken());
	}

	private static void extractOccuranceDate(String data,FaultDataWrapper faultDetails) {
		StringTokenizer ocDateTimeToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(ocDateTimeToken, 4))
			return;
		ocDateTimeToken.nextElement();///skip key
		StringBuffer timeBuffer = new StringBuffer();  
		timeBuffer.append(ocDateTimeToken.nextToken()).append(colonDelimiter).
							append(ocDateTimeToken.nextToken()).append(colonDelimiter).
								append(ocDateTimeToken.nextToken());
		
		faultDetails.setOccuranceDateTime(timeBuffer.toString());
	}

	private static void extractSerialNumber(String data,FaultDataWrapper faultDetails) {
		StringTokenizer serialNumberToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(serialNumberToken, 2))
			return;
		serialNumberToken.nextElement();///skip key
		faultDetails.setSerialNumber(serialNumberToken.nextToken());
	}

	private static void extractSourceAddress(String data,FaultDataWrapper faultDetails) {
		StringTokenizer sourceAddressToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(sourceAddressToken, 2))
			return;
		sourceAddressToken.nextElement();///skip key
		faultDetails.setSourceAddress(sourceAddressToken.nextToken());
	}

	private static void extractSpeed(String data, FaultDataWrapper faultDetails) {
		StringTokenizer speedToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(speedToken, 2))
			return;
		speedToken.nextElement();///skip key
		faultDetails.setSpeed(speedToken.nextToken());
	}

	private static void extractStatus(String data, FaultDataWrapper faultDetails) {
		StringTokenizer statusToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(statusToken, 2))
			return;
		statusToken.nextElement();///skip key
		faultDetails.setActive(statusToken.nextToken());
	}

	private static void extractColumnHeaders(String data,FaultDataWrapper faultDetails) {
		
		StringTokenizer snapshotToken = new StringTokenizer(data,commaDelimiter);
		
		if(!isValidToken1(snapshotToken, 2))
			return;
		
		int index = 0;
		while(snapshotToken.hasMoreTokens()){
			headers.put(index, snapshotToken.nextToken());
			index++;
		}
		
	}
	private static void extractParameters(String data,FaultDataWrapper faultDetails) {
		FaultSnapshot faultSnapshot = new FaultSnapshot();
		StringTokenizer snapshotToken = new StringTokenizer(data,commaDelimiter);
		
		String timeInfo = snapshotToken.nextToken();
		StringTokenizer timeToken = new StringTokenizer(timeInfo,colonDelimiter);
		
		if(!isValidToken(timeToken, 3))
			return;
		//timeToken.nextElement();//skip key
		
		StringBuffer timeBuffer = new StringBuffer();
		timeBuffer.append(timeToken.nextToken()).append(colonDelimiter).
		append(timeToken.nextToken()).append(colonDelimiter).
			append(timeToken.nextToken());
		faultSnapshot.setTimestamp(timeBuffer.toString());
		
		int index = 1;
		while (snapshotToken.hasMoreElements()) {
			String parameterData =  snapshotToken.nextToken();
			faultSnapshot.addParameter(new FaultParameter(headers.get(index),parameterData));
			index++;
		}
		faultDetails.addSnapshot(faultSnapshot);
	}

	private static void extractUnitNumber(String data,FaultDataWrapper faultDetails) {
		StringTokenizer unitNumberToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(unitNumberToken, 2))
			return;
		unitNumberToken.nextElement();///skip key
		faultDetails.setUnitNumber(unitNumberToken.nextToken());
	}

	private static void extractVidNumber(String data,FaultDataWrapper faultDetails) {
		StringTokenizer vidNumberToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(vidNumberToken, 2))
			return;
		vidNumberToken.nextElement();///skip key
		faultDetails.setVidNumber(vidNumberToken.nextToken());
	}
	
	private static void extractSwId(String data,FaultDataWrapper faultDetails) {
		StringTokenizer swIdToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(swIdToken, 2))
			return;
		swIdToken.nextElement();///skip key
		faultDetails.setVidNumber(swIdToken.nextToken());
	}
	
	private static void extractCaliberationId(String data,FaultDataWrapper faultDetails) {
		StringTokenizer caliberationIdToken = new StringTokenizer(data,colonDelimiter);
		if(!isValidToken(caliberationIdToken, 2))
			return;
		caliberationIdToken.nextElement();///skip key
		faultDetails.setVidNumber(caliberationIdToken.nextToken());
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
	
	private static boolean isValidToken1(StringTokenizer tokenizer, Integer expectedCount){
		return tokenizer.countTokens()>=expectedCount;
	}
	
	public static void main(String[] args) throws Exception {
		
		extractFaultData("Activefault_Data:LS=23:SPN=4096:FMI=31:OC=1:SPN=3667:FMI=2:OC=1:SPN=3703:FMI=31:OC=1", new ArrayList<FaultData>());
		/*String a = "TS:Oct/15/2015 18:06:25{SPN969=3.00,SPN91=0.00,SPN92=0.00,SPN974=nan,SPN190=0.00,SPN513=0.00,SPN512=0.00,SPN2659=nan,SPN132=0.00,SPN3216=0.00,SPN3226=0.00,SPN5313=51.36,SPN4765=0.00,SPN4360=0.00,SPN4363=0.00,SPN3700=0.00,SPN3610=0.00,SPN27=0.00,SPN641=78.00,SPN3251=6527.90,SPN3246=0.00,SPN3242=0.00,SPN1761=0.00,SPN3031=-40.00,SPN1209=-0.12,SPN1176=99.72,SPN1172=22.41,SPN411=nan,SPN412=-50.00,SPN103=0.00,SPN514=1.00,SPN985=1.00,SPN110=95.00,SPN174=nan,SPN175=150.00,SPN101=-1.76,SPN100=248.00,SPN111=100.00,SPN980=3.00,SPN979=3.00,SPN86=0.00,SPN84=0.00,SPN596=0.00,SPN70=0.00,SPN597=1.00,SPN598=1.00,SPN976=31.00,SPN183=0.00,SPN171=78.94,SPN108=99.50,SPN102=0.00,SPN105=16.00,SPN173=nan,SPN168=12.45,SPN96=0.00,SPN97=0.00,SPN5466=45.75,SPN3719=37.00,SPN1033=1628.35,SPN247=194.85,SPN2791=0.00,SPN2795=78.00,SPN5457=0.00,SPN250=14.00,SPN3301=0.00,SPN544=3085.00}";
		StringTokenizer stringTokenizer = new StringTokenizer(a,openBraceDelimiter);
		while (stringTokenizer.hasMoreElements()) {
			StringTokenizer setTokenizer = new StringTokenizer(stringTokenizer.nextToken(),closeBraceDelimiter);
			while (setTokenizer.hasMoreElements()) {
				System.err.println(setTokenizer.nextElement());
			}
		}*/
		
		File file = new File("C:\\Users\\pcseg129\\Desktop\\todelete\\FC latest\\snapshot245.txt");
		FaultDecoderV2.decode(file);
		
	}

}
