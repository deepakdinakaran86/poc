package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.LocationMessage;
import com.pcs.device.gateway.G2021.message.extension.access.type.PointAccess;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

public class LocationProcessor extends G2021Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationProcessor.class); 
	
	
	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {

		ByteBuf locationData = (ByteBuf) G2021Data;
		Integer snapshotTime = locationData.readInt();
		LocationMessage message = new LocationMessage();
		try {
			message.setReceivedTime(Calendar.getInstance().getTime());
			message.setTime(snapshotTime*1000l);
			
			Point latitude = Point.getPoint(PointDataTypes.LATITUDE.getType());
			latitude.setPointId("Latitude");
			latitude.setPointName("Latitude");
			latitude.setPointAccessType(PointAccess.READONLY.name());
			latitude.setDisplayName("Latitude");
			latitude.setUnit(Point.UNITLESS);
			float latitudeData = locationData.readFloat();
			latitude.setData(latitudeData);
			message.addPoint(latitude);
			
			Point longitude = Point.getPoint(PointDataTypes.LONGITUDE.getType());
			longitude.setPointId("Longitude");
			longitude.setPointName("Longitude");
			longitude.setPointAccessType(PointAccess.READONLY.name());
			longitude.setDisplayName("Longitude");
			longitude.setUnit(Point.UNITLESS);
			float longitudeData = locationData.readFloat();
			longitude.setData(longitudeData);
			message.addPoint(longitude);
			
			Point geoPoint = Point.getPoint(PointDataTypes.GEOPOINT.getType());
			geoPoint.setPointId("Location");
			geoPoint.setPointName("Location");
			geoPoint.setPointAccessType(PointAccess.READONLY.name());
			geoPoint.setDisplayName("Location");
			geoPoint.setUnit(Point.UNITLESS);
			Float[] location = {latitudeData,longitudeData};
			geoPoint.setData(location);
			message.addPoint(geoPoint);
			
			int reason = locationData.readUnsignedByte();
			
			switch (reason) {
			case 0:
				message.setReason(LocationMessage.REASON_UNKNOWN);
				break;
			case 1:
				message.setReason(LocationMessage.MOVING);
				break;
			case 2:
				message.setReason(LocationMessage.SERVICE_FLAG_RESET);
				break;
			case 3:
				message.setReason(LocationMessage.GEOFENCE_ENTRANCE);
				break;
			case 4:
				message.setReason(LocationMessage.GEOFENCE_EXIT);
				break;

			default:
				message.setReason(LocationMessage.REASON_UNKNOWN);
				break;
			}
			message.setGeofenceId(locationData.readShort());
		} catch (Exception e) {
			LOGGER.error("Error Identifying Location",e);
		}
		
		return message;
	}

	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}

}
