package com.pcs.saffron.manager.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.pcs.saffron.cipher.data.generic.message.GenericMessage;
import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.manager.api.datastore.DatastoreService;
import com.pcs.saffron.manager.bean.DataIngestionBean;
import com.pcs.saffron.manager.bean.DefaultConfiguration;
import com.pcs.saffron.manager.bean.GenericIngestionType;
import com.pcs.saffron.manager.bundle.util.CacheUtil;

public class GenericMessageIngestionUtil {


	private static final Logger LOGGER = LoggerFactory.getLogger(GenericMessageIngestionUtil.class);
	private static final String DATASTORE_CACHE = "data.analytics.physicalquantity.cache";

	public static GenericIngestionType prepareIngestionMessage(DefaultConfiguration deviceConfiguration, GenericMessage genericMessage,Boolean isLatest){
		GenericIngestionType genericIngestionType = null;
		DateTime deviceDate = new DateTime(genericMessage.getTimestamp()).toDateTime(DateTimeZone.UTC);
		long deviceDateStartOfDay = deviceDate.withTimeAtStartOfDay().getMillis();
		DateTime receivedTime = new DateTime(System.currentTimeMillis()).toDateTime(DateTimeZone.UTC);
		long insertTimeMs = receivedTime.getMillis();

		Point point = getPointByDisplayName(deviceConfiguration,genericMessage.getParameterName());

		if(point != null){

			try {
				genericIngestionType = new GenericIngestionType();
				DataIngestionBean ingestionData = new DataIngestionBean();
				String datastore = getDatastore(point.getPhysicalQuantity());

				if(datastore == null){
					LOGGER.info("No datastore found for point {}",genericMessage.getParameterName());
					return null;
				}

				Message message = new Message();
				message.setReceivedTime(receivedTime.toDate());
				message.setSourceId(genericMessage.getSourceId());
				message.setTime(deviceDate.getMillis());
				message.addPoint(point);
				point.setData(genericMessage.getValue());

				genericIngestionType.setMessage(message);

				ingestionData.setDeviceId(deviceConfiguration.getDevice().getDeviceId());
				ingestionData.setData(getData(point.getType(),genericMessage.getValue()));
				ingestionData.setDatastore(datastore);
				ingestionData.setDataType(point.getType());
				ingestionData.setDisplayName(point.getDisplayName());
				ingestionData.setExtensions(point.getExtensions());
				ingestionData.setSystemTag("");
				ingestionData.setDeviceTime(deviceDate.getMillis());
				ingestionData.setDeviceDate(deviceDateStartOfDay);
				ingestionData.setInsertTime(insertTimeMs);
				ingestionData.setIsLatest(isLatest);
				genericIngestionType.setIngestionBean(ingestionData);
			} catch (Exception e) {
				LOGGER.error("Error processing generic message",e);
			}

		}else{
			LOGGER.error("No parameter found with name {}",genericMessage.getParameterName());
			return null;
		}
		return genericIngestionType;
	}



	public static GenericIngestionType prepareIngestionMessage(DefaultConfiguration deviceConfiguration, List<GenericMessage> genericMessages,Boolean isLatest){

		DateTime receivedTime = new DateTime(System.currentTimeMillis()).toDateTime(DateTimeZone.UTC);
		long insertTimeMs = receivedTime.getMillis();
		GenericIngestionType genericDataBean = new GenericIngestionType();

		if(CollectionUtils.isEmpty(genericMessages)){
			return genericDataBean;
		}

		DateTime deviceDate = new DateTime(genericMessages.get(0).getTimestamp()).toDateTime(DateTimeZone.UTC);
		long deviceDateStartOfDay = deviceDate.withTimeAtStartOfDay().getMillis();
		Message message = new Message();
		message.setReceivedTime(receivedTime.toDate());
		message.setTime(deviceDate.getMillis());
		message.setSourceId(genericMessages.get(0).getSourceId());

		for (GenericMessage genericMessage : genericMessages) {
			deviceDate = new DateTime(genericMessage.getTimestamp()).toDateTime(DateTimeZone.UTC);
			deviceDateStartOfDay = deviceDate.withTimeAtStartOfDay().getMillis();

			Point point = getPointByDisplayName(deviceConfiguration,genericMessage.getParameterName());

			if(point != null){
				try {
					DataIngestionBean ingestionBean = new DataIngestionBean();
					String datastore = getDatastore(point.getPhysicalQuantity());

					if(datastore != null){

						message.addPoint(point);
						point.setData(genericMessage.getValue());
						if(genericDataBean.getMessage() == null)
							genericDataBean.setMessage(message);

						ingestionBean.setDeviceId(deviceConfiguration.getDevice().getDeviceId());
						ingestionBean.setData(getData(point.getType(),genericMessage.getValue()));
						ingestionBean.setDatastore(datastore);
						ingestionBean.setDataType(point.getType());
						ingestionBean.setDisplayName(point.getDisplayName());
						ingestionBean.setExtensions(point.getExtensions());
						ingestionBean.setSystemTag("");
						ingestionBean.setDeviceTime(deviceDate.getMillis());
						ingestionBean.setDeviceDate(deviceDateStartOfDay);
						ingestionBean.setInsertTime(insertTimeMs);
						ingestionBean.setIsLatest(isLatest);

						genericDataBean.addIngestionData(ingestionBean);
					}else{
						LOGGER.info("No datastore info for point {}, not added for persistence",genericMessage.getParameterName());
					}
				} catch (Exception e) {
					LOGGER.error("Error processing generic messages",e);
				}


			}else{
				LOGGER.error("No parameter found with name {}",genericMessage.getParameterName());
				return null;
			}
		}

		return genericDataBean;
	}


	private static String getDatastore(String physicalQuantity){
		String datastore = CacheUtil.getCacheProvider().getCache(DATASTORE_CACHE).get(physicalQuantity,String.class);
		if(StringUtils.isEmpty(datastore)){
			DatastoreService datastoreService = new DatastoreService();
			datastore = datastoreService.getDatasouce(physicalQuantity);
		}
		LOGGER.info("Datastore is {}",datastore);
		return datastore;
	}

	private static String getData(String dataType,Object data){

		switch (PointDataTypes.valueOf(dataType)) {
		case STRING:
		case BOOLEAN:
			return data.toString();
		case SHORT:
		case INTEGER:
		case LONG:
		case DOUBLE:
			LOGGER.info("Is double");
			return (data != null && data.toString().trim().length() > 0) ? data.toString() : "0";
		case FLOAT:
			return (data != null && data.toString().trim().length() > 0) ? data.toString() : "0";
		case LATITUDE:
			LOGGER.info("Is latitude");
			return (data != null && data.toString().trim().length() > 0) ? data.toString() : "0";
		case LONGITUDE:
			LOGGER.info("Is latitude");
			return (data != null && data.toString().trim().length() > 0) ? data.toString() : "0";
		default:
			return data!=null?data.toString():"0";
		}
	}


	private static  Point getPointByDisplayName(DefaultConfiguration configuration , String displayName){
		for (Point point : configuration.getConfigPoints()) {
			if(point.getDisplayName().equalsIgnoreCase(displayName)){
				LOGGER.info("Parameter found");
				return point;
			}
		}
		LOGGER.info("Parameter not found");
		return null;
	}
}
