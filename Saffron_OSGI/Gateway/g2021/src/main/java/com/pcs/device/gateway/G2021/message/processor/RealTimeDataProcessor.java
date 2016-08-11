package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.bundle.utils.CacheUtil;
import com.pcs.device.gateway.G2021.command.SyncCommandDispatcher;
import com.pcs.device.gateway.G2021.config.G2021GatewayConfiguration;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.extension.point.status.type.StatusType;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.message.point.type.G2021DataTypes;
import com.pcs.saffron.cache.base.Cache;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.extension.PointExtension;
import com.pcs.saffron.connectivity.utils.ConversionUtils;
import com.pcs.saffron.manager.autoclaim.AutoClaimService;
import com.pcs.saffron.manager.bean.ConfigPoint;
import com.pcs.saffron.manager.bean.DefaultConfiguration;

public class RealTimeDataProcessor extends G2021Processor {

	private static final String VEHICLE_IDENTIFICATION_NUMBER_N0 = "Vehicle Identification Number:n0";

	private static final String UNIT_NUMBER_POWER_UNIT_N0 = "Unit Number(Power Unit):n0";

	private static final String MODEL_N0 = "Model:n0";

	private static final String MAKE_N0 = "Make:n0";

	private static final String SERIAL_NUMBER_N0 = "Serial Number:n0";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RealTimeDataProcessor.class);

	private static final String STATUS = "STATUS";

	private DefaultConfiguration configuration;

	public RealTimeDataProcessor() {

	}

	public RealTimeDataProcessor(DefaultConfiguration configuration)
			throws MessageProcessException {
		if (configuration == null) {
			throw new MessageProcessException("Configuration cannot be null !!");
		}
		this.configuration = configuration;
	}

	@Override
	public Message processG2021Message(Object G2021Data)
			throws MessageProcessException {

		ByteBuf realTimeDataBuf = (ByteBuf) G2021Data;
		Integer recordCount = (int) realTimeDataBuf.readUnsignedByte();

		Message message = new Message();
		message.setReceivedTime(Calendar.getInstance().getTime());
		for (int i = 0; i < recordCount; i++) {
			Point point = null;
			Integer pid = realTimeDataBuf.readUnsignedShort();
			point = configuration.getPoint(pid.toString());
			if (point == null) {
				SyncCommandDispatcher.releaseSyncCommand(configuration);
				throw new MessageProcessException("Invalid point id : " + pid
						+ ". Command to SYNC has been queued!!");
			}
			Integer snapshotTime = realTimeDataBuf.readInt();
			message.setTime(snapshotTime * 1000l);
			int flag = realTimeDataBuf.readUnsignedByte();
			String binaryRepresentation = ConversionUtils.convertToBinaryExt(
					Integer.toHexString(flag), 8).toString();
			String dataType = binaryRepresentation.substring(0, 4);
			boolean isFaultData = false;
			try {

				switch (G2021DataTypes.valueOfType(dataType)) {

				case BOOLEAN:
					point.setData(realTimeDataBuf.readUnsignedByte() == 0 ? false
							: true);
					message.addPoint(point);
					break;

				case SHORT:
					point.setData(realTimeDataBuf.readShort());
					message.addPoint(point);
					break;

				case INT:
					point.setData(realTimeDataBuf.readInt());
					message.addPoint(point);
					break;

				case LONG:
					point.setData(realTimeDataBuf.readLong());
					message.addPoint(point);
					break;

				case FLOAT:
					Float readFloat = realTimeDataBuf.readFloat();
					if (!Float.isNaN(readFloat)) {
						point.setData(readFloat);
					}else if(Float.isInfinite(readFloat)){
						isFaultData = true;
						point.setData(Float.POSITIVE_INFINITY);
					}else {
						isFaultData = true;
						point.setData(Float.NaN);
					}
					message.addPoint(point);
					break;

				case TIMESTAMP:
					Long timeStamp = realTimeDataBuf.readInt() * 1000l;
					point.setData(timeStamp);
					message.addPoint(point);
					break;

				case STRING:
					int stringLength = realTimeDataBuf.readUnsignedByte();
					char[] stringValue = new char[stringLength];

					for (int j = 0; j < stringLength; j++) {
						stringValue[j] = (char) realTimeDataBuf
								.readUnsignedByte();
					}
					String value = new String(stringValue);
					point.setData(value);
					message.addPoint(point);
					break;

				case TEXT:
					int textLength = realTimeDataBuf.readUnsignedByte();
					byte[] textValue = new byte[textLength];
					for (int j = 0; j < textLength; j++) {
						textValue[j] = (byte) realTimeDataBuf
								.readUnsignedByte();
					}
					point.setData(new String(textValue, "UTF-8"));
					message.addPoint(point);
					break;

				default:
					break;
				}
			} catch (Exception e) {
				LOGGER.error("Exception",e);
			}

			StatusType pointstatus = StatusType
					.valueOfType(binaryRepresentation.substring(4));
			if (pointstatus != null) {
				PointExtension statusExtension = point
						.getExtensionByType(STATUS);
				if (statusExtension != null) {
					point.getExtensions().remove(statusExtension);
				}
				if (isFaultData) {
					point.setStatus(StatusType.NOTAVAILABLE.name());
				} 
			} else {
				LOGGER.info("Invalid point type fetched :"
						+ binaryRepresentation.substring(4));
			}
		}

		// Required only when auto claim is enabled and the device sends demographic info as point
		/*Cache autoClaimCache = CacheUtil.getCacheProvider().getCache(G2021GatewayConfiguration.AUTOCLAIM_CACHE);
		LOGGER.info("Auto Claim Cache {}",autoClaimCache);
		Boolean claimed = autoClaimCache.get(configuration.getDevice().getSourceId(),Boolean.class);
		LOGGER.info("Auto Claim Status {}",claimed);
		if(claimed == null || !claimed)
			processRegistration(message);
		autoClaimCache = null;*/
		// Required only when auto claim is enabled and the device sends demographic info as point

		return message;
	}

	/**
	 * 
	 * Hard coded method to support device limitation to send demographic
	 * details of the asset. This method would help the auto claim process to
	 * identify the asset information to which the device is connected.
	 * 
	 * @param message
	 * 
	 */
	@Deprecated
	private void processRegistration(Message message) {
		List<ConfigPoint> assetDetails = new ArrayList<ConfigPoint>();
		LOGGER.info("Autoclaim process request received");
		try {
			for (Point point : message.getPoints()) {
				switch (point.getPointName()) {

				case MAKE_N0:
					ConfigPoint make = configuration.getConfigPoints().get(configuration.getConfigPoints().indexOf(point));
					make.setData(point.getData());
					assetDetails.add(make);
					break;

				case MODEL_N0:
					ConfigPoint model = configuration.getConfigPoints().get(configuration.getConfigPoints().indexOf(point));
					model.setData(point.getData());
					assetDetails.add(model);
					break;

				case SERIAL_NUMBER_N0:
					ConfigPoint serialNumber = configuration.getConfigPoints().get(configuration.getConfigPoints().indexOf(point));
					serialNumber.setData(point.getData());
					assetDetails.add(serialNumber);
					break;

				case UNIT_NUMBER_POWER_UNIT_N0:
					ConfigPoint unitNumber = configuration.getConfigPoints().get(configuration.getConfigPoints().indexOf(point));
					unitNumber.setData(point.getData());
					assetDetails.add(unitNumber);
					break;

				case VEHICLE_IDENTIFICATION_NUMBER_N0:
					ConfigPoint vinNumber = configuration.getConfigPoints().get(configuration.getConfigPoints().indexOf(point));
					vinNumber.setData(point.getData());
					assetDetails.add(vinNumber);
					break;

				default:
					break;
				}
			}
			if (!assetDetails.isEmpty()) {
				configuration.getConfigPoints().removeAll(assetDetails);
				configuration.getConfigPoints().addAll(assetDetails);
				AutoClaimService autoClaimService = new AutoClaimService();
				LOGGER.info("Autoclaim process request send");
				autoClaimService.claimAsset(configuration);

				Cache autoClaimCache = CacheUtil.getCacheProvider().getCache(G2021GatewayConfiguration.AUTOCLAIM_CACHE);
				autoClaimCache.put(configuration.getDevice().getSourceId(), true);
			}
		} catch (Exception e) {
			LOGGER.error("Error performing auto claim",e);
		}
	}

	@Override
	public byte[] getServerMessage(Message message, Header header)
			throws MessageProcessException {
		return G2021ACKUtil.getServerMessage(message, header);
	}

	public static void main(String[] args) throws Exception {
		List<ConfigPoint> test1 = new ArrayList<ConfigPoint>();
		ConfigPoint testPt1 = new ConfigPoint();
		testPt1.setPointName("teswt");
		testPt1.setPointId("1");
		testPt1.setData("teasda");
		test1.add(testPt1);

		List<Point> points = new ArrayList<Point>();
		Point ts = new Point();
		ts.setPointName("test");
		ts.setPointId("1");
		points.add(ts);

		ConfigPoint configPoint = test1.get(test1.indexOf(testPt1));
		System.err.println(configPoint.getData());
	}
}
