package com.pcs.data.analyzer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.message.Message;
import com.pcs.saffron.cipher.data.point.Point;
import com.pcs.saffron.cipher.data.point.types.PointDataTypes;
import com.pcs.saffron.manager.bean.DataIngestionBean;
import com.pcs.saffron.manager.bean.GeoPoint;
import com.pcs.saffron.manager.bundle.util.CacheUtil;

public class MessageIngestionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageIngestionUtil.class);
	private static final String DATASTORE_CACHE = "data.analytics.physicalquantity.cache";

	public static List<DataIngestionBean> prepareIngestionMessage(String deviceId, Message message,Boolean isLatest){
		List<DataIngestionBean> ingestionData = new ArrayList<DataIngestionBean>();
		DateTime deviceDate = new DateTime(message.getTime()).toDateTime(DateTimeZone.UTC);
		long deviceDateStartOfDay = deviceDate.withTimeAtStartOfDay().getMillis();
		long insertTimeMs = new DateTime(System.currentTimeMillis()).toDateTime(DateTimeZone.UTC).getMillis();
		for (Point point : message.getPoints()) {
			DataIngestionBean ingestionBean = new DataIngestionBean();
			String datastore = getDatastore(point.getPhysicalQuantity());
			ingestionBean.setSourceId(message.getSourceId().toString());
			ingestionBean.setUnit(point.getUnit());
			ingestionBean.setDeviceId(deviceId);
			ingestionBean.setData(getData(point.getType(),point.getData()));
			ingestionBean.setDatastore(datastore);
			ingestionBean.setDataType(point.getType());
			ingestionBean.setDisplayName(point.getDisplayName());
			ingestionBean.setExtensions(point.getExtensions());
			ingestionBean.setSystemTag("");
			ingestionBean.setDeviceTime(deviceDate.getMillis());
			ingestionBean.setDeviceDate(deviceDateStartOfDay);
			ingestionBean.setInsertTime(insertTimeMs);
			if(datastore != null){
				ingestionData.add(ingestionBean);
			}else{
				LOGGER.info("No datastore info point not added for persistence");
			}
		}
		return ingestionData;
	}

	private static String getDatastore(String physicalQuantity){
		String datastore = CacheUtil.getCacheProvider().getCache(DATASTORE_CACHE).get(physicalQuantity,String.class);
		if(StringUtils.isEmpty(datastore)){
			datastore = AnalyticsUtil.getDataStoreFromCache(physicalQuantity);
		}
		LOGGER.info("Datastore is {}",datastore);
		return datastore;
	}

	private static Object getData(String dataType,Object data){

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
		case GEOPOINT:
			LOGGER.info("Is Geopoint");
			Float[] location = {Float.NaN,Float.NaN};
			GeoPoint geoPoint = new GeoPoint();
			if(data!=null){
				location = (Float[]) data;
				if(location.length==2){
					geoPoint.setLatitude(location[0]);
					geoPoint.setLongitude(location[1]);
				}else{
					geoPoint.setLatitude(location[0]);
					geoPoint.setLongitude(location[1]);
				}
			}else{
				geoPoint.setLatitude(Float.NaN);
				geoPoint.setLongitude(Float.NaN);
			}
			
			return geoPoint;
			
		default:
			return data!=null?data.toString():"0";
		}
	}

}
