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

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pcs.alpine.commons.util.ObjectBuilderUtil;
import com.pcs.alpine.services.dto.EntityDTO;
import com.pcs.alpine.services.dto.HierarchyRelationDTO;

/**
 * 
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg296
 */
public class HierarchyTranslator
        implements
            JsonDeserializer<HierarchyRelationDTO>,
            JsonSerializer<HierarchyRelationDTO> {

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	/*
	 * (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(HierarchyRelationDTO hierarchyRelation,
	        Type typeOfSrc, JsonSerializationContext context) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public HierarchyRelationDTO deserialize(JsonElement json, Type typeOfT,
	        JsonDeserializationContext context) throws JsonParseException {
		ObjectBuilderUtil objectBuilderUtil = new ObjectBuilderUtil();

		JsonObject jObj = json.getAsJsonObject();
		HierarchyRelationDTO hierarchyRelationDTO = new HierarchyRelationDTO();

		// Set Identifier Key
		if (jObj.get("parent") != null && !jObj.get("parent").isJsonNull()) {
			String identifierKey = jObj.get("parent").toString();

			GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
			builder.registerTypeAdapter(EntityDTO.class, new EntityTranslator());

			Gson gson = builder.create();
			EntityDTO parent = gson.fromJson(identifierKey, EntityDTO.class);

			hierarchyRelationDTO.setParent(parent);
		}
		if (jObj.get("child") != null && !jObj.get("child").isJsonNull()) {
			String identifierKey = jObj.get("child").toString();

			GsonBuilder builder = objectBuilderUtil.getLowercasegsonbuilder();
			builder.registerTypeAdapter(EntityDTO.class, new EntityTranslator());

			Gson gson = builder.create();
			EntityDTO child = gson.fromJson(identifierKey, EntityDTO.class);

			hierarchyRelationDTO.setChild(child);

		}

		return hierarchyRelationDTO;
	}
}