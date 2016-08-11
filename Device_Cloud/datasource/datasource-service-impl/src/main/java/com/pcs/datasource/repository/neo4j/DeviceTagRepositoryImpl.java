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
package com.pcs.datasource.repository.neo4j;

import static com.pcs.datasource.enums.DeviceDataFields.ROW;
import static com.pcs.datasource.repository.utils.CypherConstants.NAME;
import static com.pcs.datasource.repository.utils.CypherConstants.SOURCE_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.SUB_ID;
import static com.pcs.datasource.repository.utils.CypherConstants.TAGS;
import static com.pcs.datasource.repository.utils.Neo4jExecuter.exexcuteQuery;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceTagRepository;
import com.pcs.devicecloud.commons.util.ObjectBuilderUtil;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Repository("deviceTagNeo")
public class DeviceTagRepositoryImpl implements DeviceTagRepository {

	@Value("${neo4j.rest.2.1.4.uri}")
	private String neo4jURI;

	@Autowired
	private ObjectBuilderUtil objectBuilderUtil;

	private static final String getAllTagsQuery = "MATCH (sub:SUBSCRIPTION {sub_id:'{sub_id}'}), sub-[r:isTaggedWith]->tag return tag ORDER by tag.name;";

	private static final String findDeviceAndSubcription = "MATCH (device:DEVICE {source_id:'{source_id}'}), "
	        + "(sub:SUBSCRIPTION {sub_id:'{sub_id}'}) {mergeDeviceTagQuery}  return device;";

	private static final String mergeDevicetag = " MERGE (tag#:DEVICE_TAG {name:'{name}'})<-[:isTaggedWith]-sub MERGE device-[:isTaggedWith]->tag#";

	private static final String removeUnMappedTag = "MATCH (device:DEVICE {source_id:'{source_id}'}), device-[r:isTaggedWith]->tag WHERE NOT tag.name IN [{tags}] DELETE r;";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.DeviceTagRepository#getAllTagsOfSubscription
	 * ()
	 */
	@Override
	public List<DeviceTag> getAllTagsOfSubscription(Subscription subscription) {
		List<DeviceTag> deviceTags = new ArrayList<DeviceTag>();
		String query = getAllTagsQuery.replace(SUB_ID, subscription.getSubId()
		        .toString());
		try {
			JSONArray tagsArray = exexcuteQuery(neo4jURI, query, null,
			        ROW.getFieldName());
			if (tagsArray == null) {
				return Collections.emptyList();
			}
			for (int i = 0; i < tagsArray.length(); i++) {
				deviceTags.add(convertToDeviceTag(tagsArray.getJSONObject(i)));
			}
		} catch (JSONException | IOException e) {
			throw new PersistenceException(
			        "Error in fetching all tags of a subscription", e);
		}
		return deviceTags;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.DeviceTagRepository#updateDeviceTag(com
	 * .pcs.datasource.dto.dc.Device)
	 */
	@Override
	public void updateDeviceRelation(Device device) {
		String sourceId = device.getSourceId();
		String query = findDeviceAndSubcription
		        .replace(SOURCE_ID, sourceId)
		        .replace(SUB_ID, device.getSubscription().getSubId().toString());
		List<DeviceTag> tags = device.getTags();
		List<String> tagsList = new ArrayList<>();

		if (isNotEmpty(tags)) {
			String mergeQueries = "";
			for (int i = 0; i < tags.size(); i++) {
				String name = tags.get(i).getName();
				String tagQuery = mergeDevicetag
				        .replace("#", String.valueOf(i)).replace(NAME, name);
				mergeQueries += tagQuery;
				tagsList.add("'" + name + "'");
			}
			query = query.replace("{mergeDeviceTagQuery}", mergeQueries);

			try {
				exexcuteQuery(neo4jURI, query, null, ROW.getFieldName());
			} catch (JSONException | IOException e) {
				throw new PersistenceException(
				        "Error in Updating Device's Tags", e);
			}
		}
		String tagsJoined = StringUtils.join(tagsList, ',');
		try {
			// Remove UnMapped Tags
			String removeTagsQuery = removeUnMappedTag.replace(SOURCE_ID,
			        sourceId).replace(TAGS, tagsJoined);
			exexcuteQuery(neo4jURI, removeTagsQuery, null, ROW.getFieldName());

		} catch (JSONException | IOException e) {
			throw new PersistenceException("Error in Updating Device's Tags", e);
		}

	}

	/**
	 * @param deviceJson
	 * @return
	 */
	private DeviceTag convertToDeviceTag(JSONObject deviceJson) {
		DeviceTag deviceTag = objectBuilderUtil.getLowerCaseUnderScoreGson()
		        .fromJson(
		                deviceJson.getJSONArray(ROW.getFieldName())
		                        .getJSONObject(0).toString(), DeviceTag.class);
		return deviceTag;
	}

	/*
	 * public static void main(String[] args) { // DeviceTagRepositoryImpl
	 * deviceTagRepositoryImpl = new // DeviceTagRepositoryImpl(); //
	 * deviceTagRepositoryImpl.neo4jURI = "http://10.234.31.233:7475"; //
	 * deviceTagRepositoryImpl.objectBuilderUtil = new ObjectBuilderUtil(); //
	 * // Subscription subscription = new Subscription(); //
	 * subscription.setSubId(1); //
	 * deviceTagRepositoryImpl.getAllTagsOfSubscription(subscription); String
	 * removeTagsQuery = removeUnMappedTag.replace("{source_id}",
	 * "000001").replace(TAGS, "'tag1','tag2'");
	 * System.out.println(removeTagsQuery); }
	 */

}