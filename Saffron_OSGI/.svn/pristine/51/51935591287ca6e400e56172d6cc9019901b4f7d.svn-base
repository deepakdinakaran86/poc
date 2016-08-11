package com.pcs.saffron.manager.api.datastore;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;

public class DatastoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatastoreService.class);
	
	public String getDatasouce(String physicalQuantity){
		String datastore = null;
		if (StringUtils.isEmpty(physicalQuantity)) {
			return null;
		}

		DatastoreRequest request = new DatastoreRequest();
		String pathUrl = request.buildConfigurationUrl(physicalQuantity);

		LOGGER.info("Path URL {}",pathUrl);
		Client client = ApacheRestClient.builder().host(request.getHostIp())
												  .port(request.getPort())
												  .scheme(request.getScheme())
												  .build();
		JsonNode jsonNode = null;
		try {
			
			jsonNode = client.get(pathUrl, null, JsonNode.class);
			if (jsonNode == null) {
				LOGGER.info("Error in get physical quantity api, response is null");
			} else if (StringUtils.isNotBlank(jsonNode.get("status")
					.asText())
					&& jsonNode.get("status").asText()
							.equalsIgnoreCase("active")) {
				datastore =  jsonNode.get("dataStore").asText();
			}

		} catch (ClientException e) {
			LOGGER.error("Exception occured while fetching datastore fpr [" + physicalQuantity + "] in platform",
			        e);
		}
		return datastore;
	}

	

}
