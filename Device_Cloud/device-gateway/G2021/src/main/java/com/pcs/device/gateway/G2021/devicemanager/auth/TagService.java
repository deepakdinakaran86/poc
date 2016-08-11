package com.pcs.device.gateway.G2021.devicemanager.auth;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.device.gateway.G2021.devicemanager.bean.Tag;
import com.pcs.device.gateway.commons.http.ApacheRestClient;
import com.pcs.device.gateway.commons.http.Client;
import com.pcs.device.gateway.commons.http.ClientException;

/**
 * @author pcseg310
 *
 */
public class TagService {

	private static final Logger logger = LoggerFactory.getLogger(TagService.class);
	
	public JsonNode updateTags(List<Tag> tags, String sourceId) {
		if (CollectionUtils.isEmpty(tags)) {
			return null;
		}

		TagRequest request = new TagRequest();
		String pathUrl = request.buildConfigurationUrl(sourceId);

		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		JsonNode status = null;
		try {
			status = client.put(pathUrl, null, tags, JsonNode.class);
			return status;
		} catch (ClientException e) {
			logger.error("Exception occured while creating/updating tags [" + tags.toString() + "] in platform",
					e);
			return null;
		}finally{
			client = null;
		}
	}
}
