/**
 * 
 */
package com.pcs.device.gateway.teltonika.decoder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.teltonika.constant.ProtocolConstants;
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
public class FMXXXDecoderCodec12 extends DecoderAdapter {


	private static final Logger LOGGER = LoggerFactory.getLogger(FMXXXDecoderCodec12.class);

	private String hexaData;
	private String imei;
	private String deviceIP;
	private Date recTime;

	public FMXXXDecoderCodec12(String deviceip, String imei, Date recTime) {
		this.deviceIP = deviceip;
		this.imei = imei;
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
		int start = 0;
		int lastPosition = 0;
		List<Message> messages = new ArrayList<Message>();
		if (!(hexaData != null && hexaData.length() >= 2)) {
			LOGGER.error("Not Valid teltonika Package.." + hexaData);
			throw new InvalidVersion("Not Valid teltonika Package");
		}
		String codec = hexaData.substring(start, start += 2);// one byte
		if (!codec.equals(ProtocolConstants.PROTOCOL_VERSION_12))// for codecId=12
		{
			LOGGER.error("Not Valid teltonika codec");
			throw new InvalidVersion("Not Valid teltonika codec");
		}
		try {
			//TODO decoder logic here
		} catch (Exception ex) {
			LOGGER.error("exception teltonika decoding....", ex);
			LOGGER.error("Error decoding : " + hexaData);
			throw new MessageDecodeException("Exception In Decoding The Message", ex);
		}
		LOGGER.info("Size of message points : " + messages.size());
		return messages;

	}



}
