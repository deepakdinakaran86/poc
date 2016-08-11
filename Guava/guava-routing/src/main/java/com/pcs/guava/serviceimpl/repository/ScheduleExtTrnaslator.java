package com.pcs.guava.serviceimpl.repository;

import static java.net.URLDecoder.decode;

import java.lang.reflect.Type;
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
import com.pcs.guava.commons.dto.FieldMapDTO;
import com.pcs.guava.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.guava.commons.exception.GalaxyException;
import com.pcs.guava.commons.util.ObjectBuilderUtil;
import com.pcs.guava.dto.scheduling.ScheduleDeSerializeDTO;

public class ScheduleExtTrnaslator implements JsonDeserializer<ScheduleDeSerializeDTO>,
JsonSerializer<ScheduleDeSerializeDTO> {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(ScheduleDeSerializeDTO configPoint, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jobj = new JsonObject();
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();
		String encodedExtension = "";
		try {
			@SuppressWarnings("serial")
			Type type = new TypeToken<List<FieldMapDTO>>() {
			}.getType();
			encodedExtension = URLEncoder.encode(objectBuilderUtil.getGson()
					.toJson(configPoint.getScheduleDest(), type), "UTF-8");
			encodedExtension = encodedExtension.replaceAll("\"", "\\\\\"");
			jobj.addProperty("poiData", encodedExtension);
		} catch (Exception e) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.CUSTOM_ERROR);
		}
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
	public ScheduleDeSerializeDTO deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();

		JsonObject jobj = json.getAsJsonObject();
		ScheduleDeSerializeDTO configPoint = new ScheduleDeSerializeDTO();
		if (jobj.get("scheduleDest") != null
				&& !jobj.get("scheduleDest").isJsonNull()) {
			String ext = jobj.get("scheduleDest").getAsString();
			if (StringUtils.isNotEmpty(ext)) {
				try {
					@SuppressWarnings("serial")
					Type type = new TypeToken<List<FieldMapDTO>>() {
					}.getType();
					List<FieldMapDTO> extensions = objectBuilderUtil
							.getGson().fromJson(
									decode(ext, "UTF-8"), type);
					configPoint.setScheduleDest(extensions);
					
				} catch (Exception e) {
					throw new GalaxyException(
							GalaxyCommonErrorCodes.CUSTOM_ERROR);
				}
			}
		}
		
		return configPoint;
	}


}
