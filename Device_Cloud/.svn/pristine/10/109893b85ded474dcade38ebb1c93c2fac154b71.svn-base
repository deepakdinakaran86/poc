/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.datasource.repository.utils;

import static com.pcs.datasource.enums.DeviceConfigFields.POINT_ALARM_EXT;
import static com.pcs.datasource.enums.DeviceConfigFields.POINT_DATA_TYPE;
import static com.pcs.datasource.enums.DeviceConfigFields.POINT_EXT;
import static com.pcs.datasource.enums.DeviceConfigFields.POINT_EXPRESSION;
import static java.net.URLDecoder.decode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pcs.datasource.dto.AlarmExtension;
import com.pcs.datasource.dto.ConfigPoint;
import com.pcs.datasource.model.udt.DeviceFieldMap;
import com.pcs.devicecloud.commons.exception.DeviceCloudErrorCodes;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for serializing and deserializing ConfigPoint
 * extensions
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 28 Sep 2015
 */
public class PointExtTrnaslator implements JsonDeserializer<ConfigPoint>,
		JsonSerializer<ConfigPoint> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(ConfigPoint configPoint, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jobj = new JsonObject();
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();
		String encodedExtension = "";
		try {
			@SuppressWarnings("serial")
			Type type = new TypeToken<List<DeviceFieldMap>>() {
			}.getType();
			encodedExtension = URLEncoder.encode(objectBuilderUtil.getGson()
					.toJson(configPoint.getExtensions(), type), "UTF-8");
			encodedExtension = encodedExtension.replaceAll("\"", "\\\\\"");
			jobj.addProperty("extensions", encodedExtension);
		} catch (Exception e) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.SERIALIZATION_FAILED,
					POINT_EXT.getDescription());
		}
		
		try {
			@SuppressWarnings("serial") Type type = new TypeToken<List<AlarmExtension>>() {
			}.getType();
			encodedExtension = URLEncoder.encode(objectBuilderUtil.getGson()
			        .toJson(configPoint.getAlarmExtensions(), type), "UTF-8");
			encodedExtension = encodedExtension.replaceAll("\"", "\\\\\"");
			jobj.addProperty("alarmExtensions", encodedExtension);
		} catch (Exception e) {
			throw new DeviceCloudException(
					DeviceCloudErrorCodes.SERIALIZATION_FAILED,
					POINT_ALARM_EXT.getDescription());
		}
		jobj.addProperty("point_id", configPoint.getPointId());
		jobj.addProperty("point_name", configPoint.getPointName());
		jobj.addProperty("display_name", configPoint.getDisplayName());
		jobj.addProperty("parameter", configPoint.getParameter());
		jobj.addProperty("physical_quantity", configPoint.getPhysicalQuantity());
		jobj.addProperty("type", configPoint.getType());
		jobj.addProperty("unit", configPoint.getUnit());
		jobj.addProperty("system_tag", configPoint.getSystemTag());
		jobj.addProperty("point_access_type", configPoint.getPointAccessType());
		jobj.addProperty("acquisition", configPoint.getAcquisition());
		jobj.addProperty("precedence", configPoint.getPrecedence());
		jobj.addProperty("expression", configPoint.getExpression());

		return jobj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public ConfigPoint deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();

		JsonObject jobj = json.getAsJsonObject();
		ConfigPoint configPoint = new ConfigPoint();

		if (jobj.get("point_id") != null && !jobj.get("point_id").isJsonNull()) {
			configPoint.setPointId(jobj.get("point_id").getAsString());
		}
		if (jobj.get("point_name") != null
				&& !jobj.get("point_name").isJsonNull()) {
			configPoint.setPointName(jobj.get("point_name").getAsString());
		}
		if (jobj.get("display_name") != null
				&& !jobj.get("display_name").isJsonNull()) {
			configPoint.setDisplayName(jobj.get("display_name")
					.getAsString());
		}
		if (jobj.get("parameter") != null
				&& !jobj.get("parameter").isJsonNull()) {
			configPoint.setParameter(jobj.get("parameter")
					.getAsString());
		}
		if (jobj.get("physical_quantity") != null
				&& !jobj.get("physical_quantity").isJsonNull()) {
			configPoint.setPhysicalQuantity(jobj.get("physical_quantity").getAsString());
		}
		if (jobj.get("type") != null
				&& !jobj.get("type").isJsonNull()) {
			try {
				//configPoint.setType(jobj.get("type").getAsString());
				configPoint.setDataType(jobj.get("type").getAsString());
			} catch (Exception e) {
				throw new DeviceCloudException(
						DeviceCloudErrorCodes.DESERIALIZATION_FAILED,
						POINT_DATA_TYPE.getDescription());
			}
		}
		if (jobj.get("unit") != null && !jobj.get("unit").isJsonNull()) {
			configPoint.setUnit(jobj.get("unit").getAsString());
		}
		if (jobj.get("system_tag") != null
				&& !jobj.get("system_tag").isJsonNull()) {
			configPoint.setSystemTag(jobj.get("system_tag").getAsString());
		}
		if (jobj.get("access") != null && !jobj.get("access").isJsonNull()) {
			configPoint.setPointAccessType(jobj.get("access").getAsString());
		}
		if (jobj.get("acquisition") != null
				&& !jobj.get("acquisition").isJsonNull()) {
			configPoint.setAcquisition(jobj.get("acquisition").getAsString());
		}
		if (jobj.get("precedence") != null && !jobj.get("precedence").isJsonNull()) {
			configPoint.setPrecedence(jobj.get("precedence").getAsString());
		}
		if (jobj.get("point_access_type") != null && !jobj.get("point_access_type").isJsonNull()) {
			configPoint.setPointAccessType(jobj.get("point_access_type").getAsString());
		}
		if (jobj.get("expression") != null
				&& !jobj.get("expression").isJsonNull()) {
			try {
				configPoint.setExpression(URLDecoder.decode(jobj.get("expression").getAsString(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new DeviceCloudException(
						DeviceCloudErrorCodes.DESERIALIZATION_FAILED,
						POINT_EXPRESSION.getDescription());
			}
		}

		if (jobj.get("extensions") != null
				&& !jobj.get("extensions").isJsonNull()) {
			String ext = jobj.get("extensions").getAsString();
			if (StringUtils.isNotEmpty(ext)) {
				try {
					@SuppressWarnings("serial")
					Type type = new TypeToken<List<DeviceFieldMap>>() {
					}.getType();
					List<DeviceFieldMap> extensions = objectBuilderUtil
							.getGson().fromJson(
									decode(ext, "UTF-8"), type);
					configPoint.setExtensions(extensions);
				} catch (Exception e) {
					throw new DeviceCloudException(
							DeviceCloudErrorCodes.DESERIALIZATION_FAILED,
							POINT_EXT.getDescription());
				}
			}
		}
		
		if (jobj.get("alarmExtensions") != null
				&& !jobj.get("alarmExtensions").isJsonNull()) {
			String ext = jobj.get("alarmExtensions").getAsString();
			if (StringUtils.isNotEmpty(ext)) {
				try {
					@SuppressWarnings("serial")
					Type type = new TypeToken<List<AlarmExtension>>() {
					}.getType();
					List<AlarmExtension> alarmExtensions = objectBuilderUtil
							.getGson().fromJson(
									URLDecoder.decode(ext,"UTF-8"), type);
					configPoint.setAlarmExtensions(alarmExtensions);
				} catch (Exception e) {
					throw new DeviceCloudException(
							DeviceCloudErrorCodes.DESERIALIZATION_FAILED,
							POINT_EXT.getDescription());
				}
			}
		}

		return configPoint;
	}

}
