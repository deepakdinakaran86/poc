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

import static com.pcs.datasource.repository.utils.CypherConstants.MAKE;
import static com.pcs.datasource.repository.utils.CypherConstants.MODEL;
import static com.pcs.datasource.repository.utils.CypherConstants.PROTOCOL;
import static com.pcs.datasource.repository.utils.CypherConstants.TYPE;
import static com.pcs.datasource.repository.utils.CypherConstants.VERSION;
import static com.pcs.datasource.repository.utils.GremlinConstants.GET_ALL_VERTICES_OF_TYPE;
import static com.pcs.datasource.repository.utils.GremlinConstants.VERTEX_LABEL;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResults;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.DeviceProtocol;
import com.pcs.datasource.dto.DeviceType;
import com.pcs.datasource.dto.Model;
import com.pcs.datasource.dto.Point;
import com.pcs.datasource.dto.ProtocolVersion;
import com.pcs.datasource.dto.SystemTag;
import com.pcs.datasource.repository.SystemRepository;
import com.pcs.datasource.repository.utils.TitanSessionManager;
import com.pcs.devicecloud.commons.exception.DeviceCloudException;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author pcseg199
 * @date May 6, 2015
 * @since galaxy-1.0.0
 */
@Repository("systemRepoTitan")
public class SystemTitanRepositoryImpl implements SystemRepository {

	@Autowired
	private TitanSessionManager titanSessionManager;

	private final static String getAllSystemTags = "g.V().hasLabel('SYSTEM_TAG').match(__.as('sysTags').out('isOfType').hasLabel('PHYSICAL_QUANTITY')"
	        + "{optionalFilter}.values('name').as('pqName'),__.as('sysTags').values('name').as('name')).select('pqName', 'name')";

	private static final String getDeviceTypesOfMake = "g.V().hasLabel('MAKE').has('name','{make}').out('hasType').valueMap()";

	private static final String getDeviceModels = "g.V().hasLabel('MAKE').has('name','{make}').out('hasType').has('name','{type}').out('hasModel').valueMap()";

	private static final String getDeviceProtocols = "g.V().hasLabel('MAKE').has('name','{make}').out('hasType').has('name','{type}').out('hasModel').has('name','{model}').out().valueMap()";

	private static final String getProtoclVersions = "g.V().hasLabel('MAKE').has('name','{make}').out('hasType').has('name','{type}').out('hasModel').has('name','{model}').out().has('name','{protocol}').out().hasLabel('PROTOCOL_VERSION').valueMap()";

	private static final String getPointsOfAProtVersion = "g.V().hasLabel('MAKE').has('name','{make}').out('hasType').has('name','{type}').out('hasModel')."
	        + "has('name','{model}').out().has('name','{protocol}').out().hasLabel('PROTOCOL_VERSION').has('name','{version}').out('hasPoint').hasLabel('POINT').valueMap()";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SystemDataRepository#getAllOfAType(java
	 * .lang.String, java.lang.Class)
	 */
	@Override
	public <T> List<T> getAllOfAType(String vertexLabel, Class<T> type) {
		String getAllOfAType = GET_ALL_VERTICES_OF_TYPE.replace(VERTEX_LABEL,
		        vertexLabel);
		List<T> destList = null;
		try {
			Client client = titanSessionManager.getClient();
			ResultSet resultSet = client.submit(getAllOfAType);
			List<Result> list = resultSet.all().get();
			destList = fromResults(list, type);
		} catch (NoResultException nre) {
			return destList;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return destList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SystemDataRepository#getAllSystemTags(java
	 * .lang.String)
	 */
	@Override
	public List<SystemTag> getAllSystemTags(String physicalQty) {
		String query = null;
		if (StringUtils.isEmpty(physicalQty)) {
			query = getAllSystemTags.replace("{optionalFilter}", "");
		} else {
			query = getAllSystemTags.replace("{optionalFilter}",
			        ".has('name','" + physicalQty + "')");
		}
		List<SystemTag> systemTags = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query);
			if (resultSet == null) {
				return systemTags;
			}
			List<Result> list = null;
			systemTags = fromResults(list, SystemTag.class);
		} catch (NoResultException nre) {
			return systemTags;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return systemTags;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SystemRepository#getDeviceTypes(java.lang
	 * .String)
	 */
	public List<DeviceType> getDeviceTypes(String makeName) {
		List<DeviceType> deviceTypes = null;
		String query = getDeviceTypesOfMake.replace(MAKE, makeName);
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query);
			if (resultSet == null) {
				return deviceTypes;
			}
			deviceTypes = fromResults(resultSet.all().get(), DeviceType.class);
		} catch (NoResultException nre) {
			return deviceTypes;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return deviceTypes;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SystemRepository#getDeviceModels(com.pcs
	 * .datasource.dto.ConfigurationSearch)
	 */
	public List<Model> getDeviceModels(ConfigurationSearch configuration) {
		List<Model> models = null;
		String query = getDeviceModels.replace(MAKE, configuration.getMake())
		        .replace(TYPE, configuration.getDeviceType());
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query);
			if (resultSet == null) {
				return models;
			}
			models = fromResults(resultSet.all().get(), Model.class);
		} catch (NoResultException nre) {
			return models;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return models;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SystemRepository#getDeviceProtocols(com
	 * .pcs.datasource.dto.ConfigurationSearch)
	 */
	public List<DeviceProtocol> getDeviceProtocols(
	        ConfigurationSearch configuration) {
		List<DeviceProtocol> protocols = null;
		String query = getDeviceProtocols
		        .replace(MAKE, configuration.getMake());
		query = query.replace(TYPE, configuration.getDeviceType());
		query = query.replace(MODEL, configuration.getModel());
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query);
			if (resultSet == null) {
				return protocols;
			}
			protocols = fromResults(resultSet.all().get(), DeviceProtocol.class);
		} catch (NoResultException nre) {
			return protocols;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return protocols;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SystemRepository#getProtocolVersions(com
	 * .pcs.datasource.dto.ConfigurationSearch)
	 */
	public List<ProtocolVersion> getProtocolVersions(
	        ConfigurationSearch configuration) {
		List<ProtocolVersion> versions = null;
		String query = getProtoclVersions
		        .replace(MAKE, configuration.getMake());
		query = query.replace(TYPE, configuration.getDeviceType());
		query = query.replace(MODEL, configuration.getModel());
		query = query.replace(PROTOCOL, configuration.getProtocol());
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query);
			if (resultSet == null) {
				return versions;
			}
			versions = fromResults(resultSet.all().get(), ProtocolVersion.class);
		} catch (NoResultException nre) {
			return versions;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return versions;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.pcs.datasource.repository.SystemRepository#getDevicePoints(com.pcs
	 * .datasource.dto.ConfigurationSearch)
	 */
	@Override
	public List<Point> getProtocolVersionPoint(ConfigurationSearch configuration) {
		List<Point> points = null;
		String query = getPointsOfAProtVersion.replace(MAKE,
		        configuration.getMake());
		query = query.replace(TYPE, configuration.getDeviceType());
		query = query.replace(MODEL, configuration.getModel());
		query = query.replace(PROTOCOL, configuration.getProtocol());
		query = query.replace(VERSION, configuration.getVersion());
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query);
			if (resultSet == null) {
				return points;
			}
			points = getPoints(resultSet.all().get());
		} catch (NoResultException nre) {
			return points;
		} catch (Exception e) {
			throw new DeviceCloudException(e);
		}
		return points;
	}

	@SuppressWarnings({
	        "rawtypes", "unchecked"})
	private List<Point> getPoints(List<Result> resultList) {

		if (resultList == null || CollectionUtils.isEmpty(resultList)) {
			throw new NoResultException("Result Object is null or empty");
		}
		List<Point> points = new ArrayList<Point>();
		for (Result result : resultList) {
			LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>)result
			        .getObject();

			Point point = new Point();
			if (object.get("pointId") != null
			        && !object.get("pointId").equals("")) {
				point.setPointId(((List)object.get("pointId")).get(0)
				        .toString());
			}
			if (object.get("pointName") != null
			        && !object.get("pointName").equals("")) {
				point.setPointName(((List)object.get("pointName")).get(0)
				        .toString());
			}
			if (object.get("displayName") != null
			        && !object.get("displayName").equals("")) {
				point.setDisplayName(((List)object.get("displayName")).get(0)
				        .toString());
			}

			if (object.get("dataType") != null
			        && !object.get("dataType").equals(""))
				try {
					{

						point.setType(((List)object.get("dataType")).get(0)
						        .toString());
					}

				} catch (Exception e) {
					throw new DeviceCloudException(e);
				}

			if (object.get("accessType") != null
			        && !object.get("accessType").equals("")) {
				point.setPointAccessType(((List)object.get("accessType"))
				        .get(0).toString());
			}
			if (object.get("description") != null
			        && !object.get("description").equals("")) {
				point.setPointAccessType(((List)object.get("description")).get(
				        0).toString());
			}

			points.add(point);
		}
		return points;

	}

	@Override
	public boolean isDeviceMakeExist(String makeName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createDeviceMake(ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeviceMake(String uniqueId, ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDeviceTypeExist(String typeName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createDeviceType(ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeviceType(String uniqueId, ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDeviceModelExist(String modelName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createDeviceModel(ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeviceModel(String uniqueId, ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDeviceProtocolExist(ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createDeviceProtocol(ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeviceProtocol(String uniqueId,
			ConfigurationSearch configuration) {
		// TODO Auto-generated method stub
		
	}
}
