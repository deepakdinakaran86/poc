/**
 * Copyright 2016 Pacific Controls Software Services LLC (PCSS). All Rights
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
package com.pcs.alpine.serviceImpl.repository.utils;

import static com.pcs.alpine.services.enums.HMDataFields.DATAPROVIDER;
import static com.pcs.alpine.services.enums.HMDataFields.TREE;
import static java.net.URLDecoder.decode;

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
import com.pcs.alpine.services.dto.DomainDTO;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.EntityTemplateDTO;
import com.pcs.alpine.services.dto.FieldMapDTO;
import com.pcs.alpine.services.dto.StatusDTO;
import com.pcs.alpine.commons.exception.GalaxyCommonErrorCodes;
import com.pcs.alpine.commons.exception.GalaxyException;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for serializing and deserializing EntityDTO
 * 
 * @author pcseg362(Suraj Sugathan)
 * @date 11 June 2016
 */
public class EntityTranslator implements JsonDeserializer<EntityDTO>,
		JsonSerializer<EntityDTO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(EntityDTO entity, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jObj = new JsonObject();
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();
		String encodedExtension = "";
		try {
			@SuppressWarnings("serial")
			Type type = new TypeToken<List<FieldMapDTO>>() {
			}.getType();
			encodedExtension = URLEncoder.encode(objectBuilderUtil.getGson()
					.toJson(entity.getDataprovider(), type), "UTF-8");
			encodedExtension = encodedExtension.replaceAll("\"", "\\\\\"");
			jObj.addProperty("dataprovider", encodedExtension);
		} catch (Exception e) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.SERIALIZATION_FAILED,
					DATAPROVIDER.getDescription());
		}
		
		try {
			@SuppressWarnings("serial")
			Type type = new TypeToken<FieldMapDTO>() {
			}.getType();
			encodedExtension = URLEncoder.encode(objectBuilderUtil.getGson()
					.toJson(entity.getTree(), type), "UTF-8");
			encodedExtension = encodedExtension.replaceAll("\"", "\\\\\"");
			jObj.addProperty("tree", encodedExtension);
		} catch (Exception e) {
			throw new GalaxyException(
					GalaxyCommonErrorCodes.SERIALIZATION_FAILED,
					TREE.getDescription());
		}
		
		jObj.addProperty("identifierKey", entity.getIdentifier().getKey());
		jObj.addProperty("identifierValue", entity.getIdentifier().getValue());
		jObj.addProperty("domain", entity.getDomain().getDomainName());
		jObj.addProperty("templateName", entity.getEntityTemplate().getEntityTemplateName());
		jObj.addProperty("status", entity.getEntityStatus().getStatusName());

		return jObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public EntityDTO deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();

		JsonObject jObj = json.getAsJsonObject();
		EntityDTO entity = new EntityDTO();
		FieldMapDTO identifier = new FieldMapDTO();

		//Set Identifier Key
		if (jObj.get("identifierKey") != null && !jObj.get("identifierKey").isJsonNull()) {
			String identifierKey = jObj.get("identifierKey").getAsString();
			identifier.setKey(identifierKey);
		}
		//Set Identifier Value
		if (jObj.get("identifierValue") != null
				&& !jObj.get("identifierValue").isJsonNull()) {
			String identifierValue = jObj.get("identifierValue").getAsString();
			identifier.setValue(identifierValue);
		}
		//Set Identifier in EntityDTO
		entity.setIdentifier(identifier);
		
		//Set Domain
		if (jObj.get("domain") != null
				&& !jObj.get("domain").isJsonNull()) {
			DomainDTO domain = new DomainDTO();
			domain.setDomainName(jObj.get("domain").getAsString());
			entity.setDomain(domain);
		}
		
		//Set Status
		if (jObj.get("status") != null
				&& !jObj.get("status").isJsonNull()) {
			StatusDTO status = new StatusDTO();
			status.setStatusName(jObj.get("status").getAsString());
			entity.setEntityStatus(status);
		}
		
		//Set Entity Template 
		if (jObj.get("templateName") != null
				&& !jObj.get("templateName").isJsonNull()) {
			EntityTemplateDTO entityTemplate = new EntityTemplateDTO();
			entityTemplate.setEntityTemplateName(jObj.get("templateName").getAsString());
			entity.setEntityTemplate(entityTemplate);
		}
		
		if (jObj.get("dataprovider") != null
				&& !jObj.get("dataprovider").isJsonNull()) {
			String ext = jObj.get("dataprovider").getAsString();
			if (StringUtils.isNotEmpty(ext)) {
				try {
					@SuppressWarnings("serial")
					Type type = new TypeToken<List<FieldMapDTO>>() {
					}.getType();
					List<FieldMapDTO> dataprovider = objectBuilderUtil
							.getGson().fromJson(
									decode(ext, "UTF-8"), type);
					entity.setDataprovider(dataprovider);
				} catch (Exception e) {
					throw new GalaxyException(
							GalaxyCommonErrorCodes.DESERIALIZATION_FAILED,
							DATAPROVIDER.getDescription());
				}
			}
		}
		
		if (jObj.get("tree") != null
				&& !jObj.get("tree").isJsonNull()) {
			String ext = jObj.get("tree").getAsString();
			if (StringUtils.isNotEmpty(ext)) {
				try {
					@SuppressWarnings("serial")
					Type type = new TypeToken<FieldMapDTO>() {
					}.getType();
					FieldMapDTO tree = objectBuilderUtil
							.getGson().fromJson(
									URLDecoder.decode(ext,"UTF-8"), type);
					entity.setTree(tree);
				} catch (Exception e) {
					throw new GalaxyException(
							GalaxyCommonErrorCodes.DESERIALIZATION_FAILED,
							TREE.getDescription());
				}
			}
		}

		return entity;
	}

}
