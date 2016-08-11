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
package com.pcs.datasource.repository.titan;

import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_TAG;
import static com.pcs.datasource.repository.utils.GremlinConstants.SUBSCRIPTION;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResults;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceTag;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceTagRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Repository("deviceTagTitan")
public class DeviceTagTitanRepositoryImpl implements DeviceTagRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	private static final String getAllTagsQuery = "g.V().hasLabel(vertexLabel).has('subId',subId).out(hasTag).order().by('name',incr).valueMap()";

	private static final String mergeTags = "deviceId=g.V().has('DEVICE','sourceId',sourceId).next().id(); "
			+ "subId=g.V().has('SUBSCRIPTION','subId',subId).next().id(); "
			+ "tags.each{ tagName -> tag =g.V(deviceId).out('isTaggedWith').has('DEVICE_TAG','name',tagName); "
			+ "if(tag.hasNext()){ return tag.next() } else{ tag=g.V(subId).out('hasTag').has('DEVICE_TAG','name',tagName); "
			+ "if(tag.hasNext()){ g.V(deviceId).next().addEdge('isTaggedWith',tag.next()) } "
			+ "else{ tagId =graph.addVertex(label,'DEVICE_TAG','name',tagName).id(); "
			+ "g.V(subId).next().addEdge('hasTag',g.V(tagId).next()); "
			+ "g.V(deviceId).next().addEdge('isTaggedWith',g.V(tagId).next()); } } };";

	private static final String removeTagsFromDevice = "g.V().has('DEVICE','sourceId',sourceId).out('isTaggedWith').has('name',without(tags)).inE('isTaggedWith').drop(); ";
	private static final String removeTagsFromSub = "g.V().has('SUBSCRIPTION','subId',subId).out('hasTag').hasLabel('DEVICE_TAG').where(__.not(__.in('isTaggedWith'))).drop()";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.DeviceTagRepository#getAllTagsOfSubscription
	 * ()
	 */
	@Override
	public List<DeviceTag> getAllTagsOfSubscription(Subscription subscription) {

		List<DeviceTag> destList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vertexLabel", SUBSCRIPTION);
		params.put("subId", subscription.getSubId());
		params.put("hasTag", HAS_TAG);

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
					getAllTagsQuery, params);
			List<Result> list = resultSet.all().get();
			destList = fromResults(list, DeviceTag.class);
		} catch (NoResultException nre) {
			return destList;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return destList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.DeviceTagRepository#updateDeviceTag(com
	 * .pcs.datasource.dto.dc.Device)
	 */
	@Override
	public void updateDeviceRelation(Device device) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subId", device.getSubscription().getSubId());
		params.put("sourceId", device.getSourceId());

		List<DeviceTag> tags = device.getTags();
		String tagsList = "tags = [];";
		if (isNotEmpty(tags)) {
			tagsList = getTagList(tags);
			try {
				titanSessionManager.getClient().submit(
						tagsList.concat(mergeTags), params);
			} catch (NoResultException nre) {
			} catch (Exception e) {
				throw new DeviceCloudException(e);
			}
		}
		try {
			titanSessionManager.getClient().submit(
					tagsList.concat(removeTagsFromDevice), params);
			titanSessionManager.getClient().submit(removeTagsFromSub, params);
		} catch (NoResultException nre) {
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
	}

	private String getTagList(List<DeviceTag> tags) {
		String tagsList = "tags = [";

		int size = tags.size();

		for (int i = 0; i < size - 1; i++) {
			tagsList += "'" + tags.get(i).getName() + "',";
		}
		tagsList += "'" + tags.get(size - 1).getName() + "'];";
		return tagsList;
	}
}