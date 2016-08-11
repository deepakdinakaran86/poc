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
import com.pcs.guava.dto.routing.PoiDeSerializeDTO;

/**
 * This class is responsible for serializing and deserializing ConfigPoint
 * extensions
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 28 Sep 2015
 */
public class PointExtTrnaslator implements JsonDeserializer<PoiDeSerializeDTO>,
		JsonSerializer<PoiDeSerializeDTO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(PoiDeSerializeDTO configPoint, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jobj = new JsonObject();
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();
		String encodedExtension = "";
		try {
			@SuppressWarnings("serial")
			Type type = new TypeToken<List<FieldMapDTO>>() {
			}.getType();
			encodedExtension = URLEncoder.encode(objectBuilderUtil.getGson()
					.toJson(configPoint.getPoiData(), type), "UTF-8");
			encodedExtension = encodedExtension.replaceAll("\"", "\\\\\"");
			jobj.addProperty("poiData", encodedExtension);
		} catch (Exception e) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.CUSTOM_ERROR);
		}
		
		try {
			@SuppressWarnings("serial") Type type = new TypeToken<List<FieldMapDTO>>() {
			}.getType();
			
			encodedExtension = URLEncoder.encode(objectBuilderUtil.getGson()
					.toJson(configPoint.getPoiTypeData(), type), "UTF-8");
			encodedExtension = encodedExtension.replaceAll("\"", "\\\\\"");
			jobj.addProperty("poiTypeData", encodedExtension);
		} catch (Exception e) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.CUSTOM_ERROR);
		}
//		jobj.addProperty("point_id", configPoint.getPointId());
//		jobj.addProperty("point_name", configPoint.getPointName());
//		jobj.addProperty("display_name", configPoint.getDisplayName());
//		jobj.addProperty("parameter", configPoint.getParameter());
//		jobj.addProperty("physical_quantity", configPoint.getPhysicalQuantity());
//		jobj.addProperty("type", configPoint.getType());
//		jobj.addProperty("unit", configPoint.getUnit());
//		jobj.addProperty("system_tag", configPoint.getSystemTag());
//		jobj.addProperty("point_access_type", configPoint.getPointAccessType());
//		jobj.addProperty("acquisition", configPoint.getAcquisition());
//		jobj.addProperty("precedence", configPoint.getPrecedence());
//		jobj.addProperty("expression", configPoint.getExpression());

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
	public PoiDeSerializeDTO deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();

		JsonObject jobj = json.getAsJsonObject();
		PoiDeSerializeDTO configPoint = new PoiDeSerializeDTO();
		if (jobj.get("poiData") != null
				&& !jobj.get("poiData").isJsonNull() && jobj.get("poiTypeData") != null
						&& !jobj.get("poiTypeData").isJsonNull() ) {
			String ext = jobj.get("poiData").getAsString();
			String ext1 = jobj.get("poiTypeData").getAsString();
			if (StringUtils.isNotEmpty(ext) && StringUtils.isNotEmpty(ext1)) {
				try {
					@SuppressWarnings("serial")
					Type type = new TypeToken<List<FieldMapDTO>>() {
					}.getType();
					List<FieldMapDTO> extensions = objectBuilderUtil
							.getGson().fromJson(
									decode(ext, "UTF-8"), type);
					
					List<FieldMapDTO> extensions1 = objectBuilderUtil
							.getGson().fromJson(
									decode(ext1, "UTF-8"), type);
					
					configPoint.setPoiData(extensions);
					configPoint.setPoiTypeData(extensions1);
					
				} catch (Exception e) {
					throw new GalaxyException(
							GalaxyCommonErrorCodes.CUSTOM_ERROR);
				}
			}
		}
		
		return configPoint;
	}


}
