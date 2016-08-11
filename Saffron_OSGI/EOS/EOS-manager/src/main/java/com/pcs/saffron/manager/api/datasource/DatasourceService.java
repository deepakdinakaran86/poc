/**
 * 
 */
package com.pcs.saffron.manager.api.datasource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.saffron.commons.http.ApacheRestClient;
import com.pcs.saffron.commons.http.Client;
import com.pcs.saffron.commons.http.ClientException;
import com.pcs.saffron.manager.api.datasource.bean.DatasourceDTO;
import com.pcs.saffron.manager.api.datasource.bean.EntityField;
import com.pcs.saffron.manager.bean.Identifier;

/**
 * @author pcseg171
 *
 */
public class DatasourceService {

	private static final String DATASOURCE_NAME = "datasourceName";
	private static final Logger logger = LoggerFactory.getLogger(DatasourceService.class);
	
	public static final Integer REGISTER = 1;
	public static final Integer UPDATE = 2;

	/**
	 * @param datasourceDTO
	 * @return
	 */
	public JsonNode register(DatasourceDTO datasourceDTO){

		if (datasourceDTO == null) {
			return null;
		}

		DatasourceRegisterRequest request = new DatasourceRegisterRequest();
		String pathUrl = request.buildConfigurationUrl();

		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		JsonNode status = null;
		try {
			status = client.post(pathUrl, null, datasourceDTO, JsonNode.class);
			return status;
		} catch (ClientException e) {
			logger.error("Exception occured while registering datasource  for datasource name [" + datasourceDTO.getDatasourceName() + "] in platform",
					e);
			return null;
		}finally{
			client = null;
		}
	}

	
	/**
	 * @param datasourceDTO
	 * @return
	 */
	public JsonNode update(DatasourceDTO datasourceDTO){

		if (datasourceDTO == null) {
			return null;
		}

		DatasourceUpdateRequest request = new DatasourceUpdateRequest();
		request.setDatasourceName(datasourceDTO.getDatasourceName());
		String pathUrl = request.buildConfigurationUrl();

		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		JsonNode status = null;
		try {
			logger.info("Datasource update url {}",pathUrl);
			status = client.put(pathUrl, null, datasourceDTO, JsonNode.class);
			return status;
		} catch (ClientException e) {
			logger.error("Exception occured while registering datasource  for datasource name [" + datasourceDTO.getDatasourceName() + "] in platform",
					e);
			return null;
		}finally{
			client = null;
		}
	}

	/**
	 * @param datasourceDTO
	 */
	public void publish(DatasourceDTO datasourceDTO){

		if (datasourceDTO == null) {
			return;
		}

		DatasourcePublishRequest request = new DatasourcePublishRequest();
		String pathUrl = request.buildConfigurationUrl(datasourceDTO.getDatasourceName());

		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		JsonNode status = null;
		try {
			status = client.post(pathUrl, null, datasourceDTO, JsonNode.class);
			logger.info("Publish status : "+status);
		} catch (ClientException e) {
			logger.error("Exception occured while registering datasource  for datasource name [" + datasourceDTO.getDatasourceName() + "] in platform",
					e);
		}
	}
	
	
	public JsonNode updateDeviceDatasource(Identifier identifier, String datasourceName){

		if (identifier == null) {
			return null;
		}

		DeviceDatasourceUpdateRequest request = new DeviceDatasourceUpdateRequest();
		String pathUrl = request.buildConfigurationUrl();
		List<EntityField> fields = new ArrayList<EntityField>();
		fields.add(new EntityField(identifier.getKey(), identifier.getValue()));
		fields.add(new EntityField(DATASOURCE_NAME,datasourceName));
		Client client = ApacheRestClient.builder().host(request.getHostIp())
				.port(request.getPort())
				.scheme(request.getScheme())
				.build();
		JsonNode status = null;
		try {
			status = client.put(pathUrl, null, fields, JsonNode.class);
			return status;
		} catch (ClientException e) {
			logger.error("Exception occured while updating device datasource  for datasource name [" + datasourceName + "] in platform",
					e);
			return null;
		}finally{
			client = null;
		}
	}
}
