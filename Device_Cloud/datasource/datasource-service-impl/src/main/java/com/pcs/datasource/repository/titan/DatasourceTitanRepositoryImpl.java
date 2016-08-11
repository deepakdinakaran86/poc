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

import static com.pcs.datasource.repository.utils.VertexMapper.fromResult;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.utils.UUIDs;
import com.pcs.datasource.dto.DatasourceDTO;
import com.pcs.datasource.dto.ParameterDTO;
import com.pcs.datasource.model.DatasourceRegistration;
import com.pcs.datasource.model.SubscriptionContext;
import com.pcs.datasource.repository.DatasourceRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg129(Seena Jyothish)
 * @date 26 May 2015
 */

@Repository("dsTitan")
public class DatasourceTitanRepositoryImpl implements DatasourceRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	public static final String registerDatasource = "graph.addVertex(label,'DATA_SOURCE','datasourceName', name,'isActive', isActive)";

	private static final String getAllDatasource = "g.V().hasLabel('DATA_SOURCE').has('isActive',true).valueMap()";

	public static final String registerParameter = "ds = g.V().hasLabel('DATA_SOURCE').has('datasourceName', dsName).next(); dsparam = graph.addVertex(label,'DATA_SOURCE_PARAMETERS','name',dsPName,'dataType',dataType); ds.addEdge('hasParameter',dsparam)";

	private static final String deleteDsRelations = "g.V().hasLabel('DATA_SOURCE').has('datasourceName', name).has('isActive',true).out('hasParameter').drop();";

	private static final String deleteDsCtxRelations = "g.V().hasLabel('DATA_SOURCE').has('datasourceName', name).has('isActive',true).outE('hasParameter').drop();";

	private static final String getAllParameters = "g.V().hasLabel('DATA_SOURCE').has('datasourceName', name).has('isActive',true).out('hasParameter').valueMap();";

	private static final String getDatasource = "g.V().hasLabel('DATA_SOURCE').has('datasourceName', name).has('isActive',true).valueMap();";

	private static final String createContext = "graph.addVertex(label,'DATA_SOURCE_CONTEXT','contextName',name)";

	private static final String updateDatasource = "g.V().hasLabel('DATA_SOURCE').has('datasourceName', name).has('isActive',true).property('isActive',false);";

	private static final String createContextRelations = "sc = g.V().hasLabel('DATA_SOURCE_CONTEXT').has('contextName',scName).next(); ds = g.V().hasLabel('DATA_SOURCE').has('datasourceName', dsName).next(); ds.addEdge('subscribedTo',sc);";

	private static final String getContexts = "g.V().hasLabel('DATA_SOURCE').has('datasourceName', name).out('subscribedTo').valueMap();";

	private static final String getContext = "scName=null;g.V().hasLabel('DATA_SOURCE_CONTEXT').as('sc','ds').select('sc','ds').by(valueMap()).by(__.in('subscribedTo').hasLabel('DATA_SOURCE').values('datasourceName').fold()).each{dsNamesFromSub = it.get('ds');if(dsNames.size() == dsNamesFromSub.size()){string1 = dsNamesFromSub.sort().toString().toLowerCase();string2 = dsNames.sort().toString().toLowerCase();if(string1 == string2){scName = it.get('sc').get('contextName').get(0);return;}}};scName;";

	@Override
	public void saveDatasource(DatasourceRegistration datasourceRegistration,
	        List<ParameterDTO> datasourceParameters) {
		saveDatasource(datasourceRegistration);
		saveParameters(datasourceRegistration.getDatasourceName(),
		        datasourceParameters);
	}

	private void saveDatasource(DatasourceRegistration datasourceRegistration) {
		datasourceRegistration.setIsActive(Boolean.TRUE);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", datasourceRegistration.getDatasourceName());
		params.put("isActive", datasourceRegistration.getIsActive());
		try {
			titanSessionManager.getClient().submit(registerDatasource, params);
		} catch (Exception e) {
			throw new PersistenceException("Error while creating ds ", e);
		}

	}

	private void saveParameters(String dsName,
	        List<ParameterDTO> datasourceParameters) {
		if (CollectionUtils.isNotEmpty(datasourceParameters)) {
			for (ParameterDTO datasourceParameter : datasourceParameters) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("dsName", dsName);
				params.put("dsPName", datasourceParameter.getName());
				params.put("dataType", datasourceParameter.getDataType());

				try {
					titanSessionManager.getClient().submit(registerParameter,
					        params);
				} catch (Exception e) {
					throw new PersistenceException(
					        "Error while creating parameters ", e);
				}
			}
		}
	}

	@Override
	public List<ParameterDTO> getParameters(String datasourceName) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", datasourceName);

		List<ParameterDTO> datasourceParameters = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getAllParameters, params);
			if (resultSet == null) {
				return datasourceParameters;
			}
			datasourceParameters = fromResults(resultSet.all().get(),
			        ParameterDTO.class);
		} catch (NoResultException nre) {
			return Collections.emptyList();
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return datasourceParameters;
	}

	@Override
	public void updateDatasource(DatasourceDTO datasourceDTO) {
		try {
			removeDsRelations(datasourceDTO.getDatasourceName());
			saveParameters(datasourceDTO.getDatasourceName(),
			        datasourceDTO.getParameters());
		} catch (Exception e) {
			throw new PersistenceException("Error in Updating device", e);
		}
	}

	private void removeDsRelations(String datasourceName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", datasourceName);
		try {
			titanSessionManager.getClient().submit(deleteDsRelations, params);
		} catch (Exception e) {
			throw new PersistenceException("Error while delete points ", e);
		}
	}

	private void removeDsCtxRelations(String datasourceName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", datasourceName);
		try {
			titanSessionManager.getClient()
			        .submit(deleteDsCtxRelations, params);
		} catch (Exception e) {
			throw new PersistenceException("Error while points relations ", e);
		}
	}

	@Override
	public List<DatasourceDTO> getAllDatasource() {

		List<DatasourceDTO> datasourceDTOs = null;

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getAllDatasource);
			if (resultSet == null) {
				return datasourceDTOs;
			}
			datasourceDTOs = fromResults(resultSet.all().get(),
			        DatasourceDTO.class);
		} catch (NoResultException nre) {
			return Collections.emptyList();
		} catch (Exception e) {
			throw new PersistenceException("Error in fetching datasources", e);
		}
		return datasourceDTOs;
	}

	@Override
	public boolean isDatasourceExist(String datasourceName) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", datasourceName);
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getDatasource, params);
			if (resultSet == null) {
				return Boolean.FALSE;
			}
			fromResult(resultSet.one(), DatasourceDTO.class);
		} catch (NoResultException nre) {
			return Boolean.FALSE;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return Boolean.TRUE;
	}

	@Override
	public void deregisterDatasource(String datasourceName) {
		removeDsRelations(datasourceName);
		removeDsCtxRelations(datasourceName);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", datasourceName);
		try {
			titanSessionManager.getClient().submit(updateDatasource, params);
		} catch (Exception e) {
			throw new PersistenceException("Error in deregistering datasource",
			        e);
		}
	}

	@Override
	public String createContext(SortedSet<String> datasourceNames) {
		String contextName = getContext(datasourceNames);
		if (StringUtils.isNotBlank(contextName)) {
			return contextName;
		}
		UUID timeBased = UUIDs.timeBased();
		saveContext(timeBased.toString());
		createCtxRelations(timeBased.toString(), datasourceNames);
		return timeBased.toString();
	}

	private String getContext(SortedSet<String> datasourceNames) {
		String joinedDsNames = StringUtils.join(datasourceNames, "','");
		joinedDsNames = "dsNames=['" + joinedDsNames + "'];";
		String query = joinedDsNames.concat(getContext);
		String contextName = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query);
			if (resultSet != null) {
				Result result = resultSet.one();
				if (result != null && !result.isNull()) {
					contextName = result.getString();
				}
			}
		} catch (Exception e) {
			throw new PersistenceException("Error while creating parameters ", e);
		}
		return contextName;
	}

	private void saveContext(String ctxName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", ctxName);
		try {
			titanSessionManager.getClient().submit(createContext, params);
		} catch (Exception e) {
			throw new PersistenceException(
			        "Error in creating subscription context : ", e);
		}
	}

	private void createCtxRelations(String contextName,
	        SortedSet<String> datasourceNames) {
		for (String datasourceName : datasourceNames) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("scName", contextName);
			params.put("dsName", datasourceName);
			try {
				titanSessionManager.getClient().submit(createContextRelations,
				        params);
			} catch (Exception e) {
				throw new PersistenceException("Error while creating parameters ", e);
			}
		}
	}

	@Override
	public List<String> getSubscribedContexts(String datasourceName) {
		List<String> contextNames = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", datasourceName);

		List<SubscriptionContext> ctxs = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
			        getContexts, params);
			if (resultSet == null) {
				return contextNames;
			}
			ctxs = fromResults(resultSet.all().get(), SubscriptionContext.class);
		} catch (NoResultException nre) {
			return Collections.emptyList();
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}

		for (SubscriptionContext ctx : ctxs) {
			contextNames.add(ctx.getContextName());
		}
		return contextNames;
	}

}
