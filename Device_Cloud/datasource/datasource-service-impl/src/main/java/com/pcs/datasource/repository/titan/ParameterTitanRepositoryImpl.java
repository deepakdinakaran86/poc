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

import static com.pcs.datasource.repository.utils.GremlinConstants.CONTAINED_IN;
import static com.pcs.datasource.repository.utils.GremlinConstants.DATA_TYPE;
import static com.pcs.datasource.repository.utils.GremlinConstants.HAS_PARAMETER;
import static com.pcs.datasource.repository.utils.GremlinConstants.IS_OF_TYPE;
import static com.pcs.datasource.repository.utils.GremlinConstants.PARAMETER;
import static com.pcs.datasource.repository.utils.GremlinConstants.PHYSICAL_QUANTITY;
import static com.pcs.datasource.repository.utils.GremlinConstants.SUBSCRIPTION;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResult;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.repository.ParameterRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for managing all parameters of system
 * 
 * @author pcseg323(Greeshma)
 * @date December 2015
 */

@Repository("paramTitan")
public class ParameterTitanRepositoryImpl implements ParameterRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	private static final String saveParameter = "param =graph.addVertex(label,vertexLabel,'name',parameter);sub = g.V().hasLabel(subscriptionLabel).has('subId',subId).next();"
	        + "pq = g.V().hasLabel(pqLabel).has('name',physicalQuantity).next();dt = g.V().hasLabel(dataTypeLabel).has('name',dataType).next();"
	        + "sub.addEdge(hasParameter,param);param.addEdge(containedIn,pq);param.addEdge(isOfType,dt)";

	private static final String getParameters = "g.V().hasLabel(vertexLabel).has('subId',subId).out('hasParameter')"
	        + ".as('name','physicalQuantity','dataType') .select('name','physicalQuantity','dataType').by('name').by(out(containedIn).values('name')).by(out(isOfType).values('name'))";

	private static final String getParameter = "g.V().hasLabel(vertexLabel).has('subId',subId).out('hasParameter').has('name',parameter)"
	        + ".as('name','physicalQuantity','dataType') .select('name','physicalQuantity','dataType').by('name').by(out(containedIn).values('name')).by(out(isOfType).values('name'))";

	private static final String getDataType = "g.V().hasLabel(vertexLabel).has('name',dataType).valueMap()";

	/**
	 * method to create a parameter to the system under a subscription
	 * 
	 * @param parameterDTO
	 *
	 */

	@Override
	public void saveParameter(ParameterDTO parameterDTO) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("parameter", parameterDTO.getName());
		params.put("subId", parameterDTO.getSubscription().getSubId());
		params.put("physicalQuantity", parameterDTO.getPhysicalQuantity());
		params.put("dataType", parameterDTO.getDataType());

		params.put("vertexLabel", PARAMETER);
		params.put("subscriptionLabel", SUBSCRIPTION);
		params.put("pqLabel", PHYSICAL_QUANTITY);
		params.put("dataTypeLabel", DATA_TYPE);

		params.put("hasParameter", HAS_PARAMETER);
		params.put("containedIn", CONTAINED_IN);
		params.put("isOfType", IS_OF_TYPE);

		try {
			Client client = titanSessionManager.getClient();
			client.submit(saveParameter, params);
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

	}

	/**
	 * method to create a parameter to the system under a subscription
	 * 
	 * @param parameterDTO
	 * @return StatusMessageDTO
	 */
	@Override
	public List<ParameterDTO> getParameters(String subId) {

		Map<String, Object> params = new HashMap<String, Object>();
		List<ParameterDTO> destList = null;

		params.put("vertexLabel", SUBSCRIPTION);

		params.put("subId", subId);
		params.put("hasParameter", HAS_PARAMETER);
		params.put("containedIn", CONTAINED_IN);
		params.put("isOfType", IS_OF_TYPE);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getParameters, params);
			List<Result> list = resultSet.all().get();
			destList = fromResults(list, ParameterDTO.class);
		} catch (NoResultException nre) {
			return destList;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return destList;
	}

	/**
	 * method is to get parameter from db for a subscription
	 * 
	 * @param paramName
	 * @param subId
	 * @return ParameterDTO
	 */
	@Override
	public ParameterDTO getParameter(String paramName, String subId) {

		Map<String, Object> params = new HashMap<String, Object>();
		ParameterDTO parameterDTO = null;

		params.put("vertexLabel", SUBSCRIPTION);

		params.put("subId", subId);
		params.put("parameter", paramName);
		params.put("hasParameter", HAS_PARAMETER);
		params.put("containedIn", CONTAINED_IN);
		params.put("isOfType", IS_OF_TYPE);

		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getParameter, params);
			Result result = resultSet.one();
			parameterDTO = fromResult(result, ParameterDTO.class);
		} catch (NoResultException nre) {
			return parameterDTO;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return parameterDTO;
	}

	/**
	 * method is to check whether datatype is exist
	 * 
	 * @param dataType
	 * @return {@link Boolean}
	 */
	@Override
	public boolean isDataTypeExist(String dataType) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vertexLabel", DATA_TYPE);
		params.put("dataType", dataType);
		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getDataType, params);
			Result result = resultSet.one();
			if (result == null || result.getObject() == null) {
				return false;
			}
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return true;
	}

	public static void main(String[] args) {

		// Builder build = Cluster.build("10.234.31.163").port(
		// Integer.parseInt("8182"));
		// build.serializer(Serializers.GRAPHSON);
		// Cluster cluster = build.create();
		// Client client = cluster.connect();
		//
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("parameter", "sampleParam_1");
		// params.put("subId", "6Kyv11FEzkOR3F5z40qA3zUBQbQa");
		// params.put("physicalQuantity", "Temperature");
		// params.put("dataType", "Double");
		//
		// params.put("hasParameter", HAS_PARAMETER);
		// params.put("containedIn", CONTAINED_IN);
		// params.put("isOfType", IS_OF_TYPE);
		//
		// try {
		// ResultSet submit = client
		// .submit("g.V().hasLabel('PHYSICAL_QUANTITY').has('name', 'temperature').valueMap(true)");
		//
		// List<Result> list = submit.all().get();
		//
		// System.out.println(list.get(0));
		// } catch (Exception e) {
		// throw new DeviceCloudException(e);
		// }

	}
}
