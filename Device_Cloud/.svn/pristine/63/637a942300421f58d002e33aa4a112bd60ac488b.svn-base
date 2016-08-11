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

import static com.pcs.datasource.repository.utils.GremlinConstants.DROP_VERTEX_WITH_ID;
import static com.pcs.datasource.repository.utils.VertexMapper.fromResult;
import static com.pcs.datasource.repository.utils.VertexMapper.getAddVertexQuery;
import static com.pcs.datasource.repository.utils.VertexMapper.getMap;
import static com.pcs.datasource.repository.utils.VertexMapper.nullAwareBeanCopy;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcs.datasource.dto.ConfigurationSearch;
import com.pcs.datasource.dto.Device;
import com.pcs.datasource.dto.DeviceConfigTemplate;
import com.pcs.datasource.dto.DevicePointData;
import com.pcs.datasource.dto.Subscription;
import com.pcs.datasource.repository.DeviceRepository;
import com.pcs.datasource.repository.utils.GremlinConstants;
import com.pcs.datasource.repository.utils.TitanSessionManager;

/**
 * This class is responsible for ..(Short Description)
 * 
 * @author RIYAS PH PCSEG296
 * @date December 23, 2015
 * @since galaxy-1.0.0
 */
@Repository("deviceRepoTitan")
public class DeviceTitanRepositoryImpl implements DeviceRepository {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DeviceTitanRepositoryImpl.class);

	@Autowired
	private TitanSessionManager titanSessionManager;

	private final int unitIdSeed = 1;

	private static final String updateDevice = " device = g.V().hasLabel('DEVICE').has('sourceId', sourceId).next(); data.each{key,value -> device.property(key,value)};";

	private static final String createDevice = "device = {addDevice} sub = g.V().hasLabel('SUBSCRIPTION').has('subId', subId).next(); "
			+ "version = g.V().hasLabel('MAKE').has('name', make).out('hasType').has('name', type).out('hasModel').has('name', model).out('talksIn').has('name', protocol).out('hasVersion').has('name', version).next(); "
			+ "nwProtocol = g.V().hasLabel('NW_PROTOCOL').has('name', nwProtocol).next(); sub.addEdge('owns', device); "
			+ "device.addEdge('talksIn', version); device.addEdge('transportsIn', nwProtocol);";

	private static final String registerDevice = "device = {addDevice} ; "
			+ "version = g.V().hasLabel('MAKE').has('name', make).out('hasType').has('name', type).out('hasModel').has('name', model).out('talksIn').has('name', protocol).out('hasVersion').has('name', version).next(); "
			+ "nwProtocol = g.V().hasLabel('NW_PROTOCOL').has('name', nwProtocol).next(); "
			+ "device.addEdge('talksIn', version); device.addEdge('transportsIn', nwProtocol);";

	private static final String getDataSourceName = "g.V().hasLabel('SUBSCRIPTION').has('subId', subId).out('owns').has('sourceId', sourceId).values('datasourceName');";

	private static final String claimDevice = "sub = g.V().hasLabel('SUBSCRIPTION').has('subId', subId).next(); "
			+ "device = g.V().hasLabel('DEVICE').has('sourceId', sourceId).next(); sub.addEdge('owns',device);";

	private static final String getDevice = " g.V().hasLabel('DEVICE').has('sourceId',sourceId).as('device','version','networkProtocol','tags','subscription')."
			+ "select('device','version','networkProtocol','tags','subscription')."
			+ "by(valueMap())."
			+ "by(out('talksIn').hasLabel('PROTOCOL_VERSION').as('version').in('hasVersion').hasLabel('DEVICE_PROTOCOL')."
			+ "as('protocol').in('talksIn').hasLabel('MODEL').as('model').in('hasModel').hasLabel('DEVICE_TYPE')."
			+ "as('deviceType').in('hasType').hasLabel('MAKE').as('make')."
			+ "select('version','protocol','model','deviceType','make').by(values('name')).by(values('name')))."
			+ "by(out('transportsIn').valueMap()).by(out('isTaggedWith').valueMap().fold())."
			+ "by(__.in('owns').hasLabel('SUBSCRIPTION').valueMap().fold())";

	private static final String getDeviceWithSub = " g.V().hasLabel('SUBSCRIPTION').has('subId',subId).out('owns')."
			+ "hasLabel('DEVICE').has('sourceId',sourceId).as('device','version','networkProtocol','tags','subscription')."
			+ "select('device','version','networkProtocol','tags','subscription')."
			+ "by(valueMap())."
			+ "by(out('talksIn').hasLabel('PROTOCOL_VERSION').as('version').in('hasVersion').hasLabel('DEVICE_PROTOCOL')."
			+ "as('protocol').in('talksIn').hasLabel('MODEL').as('model').in('hasModel').hasLabel('DEVICE_TYPE')."
			+ "as('deviceType').in('hasType').hasLabel('MAKE').as('make')."
			+ "select('version','protocol','model','deviceType','make').by(values('name')).by(values('name')))."
			+ "by(out('transportsIn').valueMap()).by(out('isTaggedWith').valueMap().fold())."
			+ "by(__.in('owns').hasLabel('SUBSCRIPTION').valueMap())";

	private static final String getAllDevicesOfASub = " g.V().hasLabel('SUBSCRIPTION').has('subId',subId).out('owns')."
			+ "hasLabel('DEVICE'){filter}.as('device','version','networkProtocol','tags','subscription')."
			+ "select('device','version','networkProtocol','tags','subscription')."
			+ "by(valueMap())."
			+ "by(out('talksIn').hasLabel('PROTOCOL_VERSION').as('version').in('hasVersion').hasLabel('DEVICE_PROTOCOL')."
			+ "as('protocol').in('talksIn').hasLabel('MODEL').as('model').in('hasModel').hasLabel('DEVICE_TYPE')."
			+ "as('deviceType').in('hasType').hasLabel('MAKE').as('make')."
			+ "select('version','protocol','model','deviceType','make').by(values('name')).by(values('name')))."
			+ "by(out('transportsIn').valueMap()).by(out('isTaggedWith').valueMap().fold())."
			+ "by(__.in('owns').hasLabel('SUBSCRIPTION').valueMap())";

	private static final String pvFilter = ".where(out('talksIn').has('PROTOCOL_VERSION','name',version).in('hasVersion').has('DEVICE_PROTOCOL','name',protocol).in('talksIn').has('MODEL','name',model).in('hasModel').has('DEVICE_TYPE','name',deviceType).in('hasType').has('MAKE','name',make))";

	private static final String getAllUnClaimed = "g.V().hasLabel('DEVICE').where(__.not(__.in('owns'))).as('device','version','networkProtocol').select('device','version','networkProtocol').by(valueMap()).by(out('talksIn').hasLabel('PROTOCOL_VERSION').as('version').in('hasVersion').hasLabel('DEVICE_PROTOCOL').as('protocol').in('talksIn').hasLabel('MODEL').as('model').in('hasModel').hasLabel('DEVICE_TYPE').as('deviceType').in('hasType').hasLabel('MAKE').as('make').select('version','protocol','model','deviceType','make').by(values('name')).by(values('name'))).by(out('transportsIn').valueMap())";

	private static final String getRecycle = "g.V().has('MAKE','name',make).out('hasType').has('DEVICE_TYPE','name',deviceType)."
			+ "out('hasModel').has('MODEL','name',model).out('talksIn').has('DEVICE_PROTOCOL','name',protocol)."
			+ "out('hasVersion').has('PROTOCOL_VERSION','name',version).out('hasUnitId').hasLabel('RECYCLE').order().by('unitId', decr).tail(1).valueMap(true)";

	private static final String getMaxUnitId = "g.V().has('MAKE','name',make).out('hasType').has('DEVICE_TYPE','name',deviceType)."
			+ "out('hasModel').has('MODEL','name',model).out('talksIn').has('DEVICE_PROTOCOL','name',protocol)."
			+ "out('hasVersion').has('PROTOCOL_VERSION','name',version)"
			+ ".in('talksIn').hasLabel('DEVICE').values('unitId').max()";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.DeviceRepository#getDevice(java.lang.String
	 * )
	 */
	@Override
	public Device getDevice(String sourceId) {

		Device device = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GremlinConstants.SOURCE_ID, sourceId);
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
					getDevice, param);
			Result deviceResult = resultSet.one();
			if (deviceResult != null) {
				device = convertToDevice(deviceResult);
			}
		} catch (Exception e) {
			LOGGER.error("Error in getDevice({}) with exception {}", sourceId,
					e);
		}
		return device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.DeviceRepository#getDevice(java.lang.String
	 * )
	 */
	@Override
	public Device getDevice(String sourceId, Subscription subscription) {
		Device device = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GremlinConstants.SOURCE_ID, sourceId);
		param.put(GremlinConstants.SUB_ID, subscription.getSubId());
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
					getDeviceWithSub, param);
			Result deviceResult = resultSet.one();
			if (deviceResult != null) {
				device = convertToDevice(deviceResult);
			}
		} catch (Exception e) {
			LOGGER.error("Error in getDevice({}) with exception {}", sourceId,
					e);
		}
		return device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.DeviceRepository#getAllDevices(java.lang
	 * .String, java.util.List)
	 */
	@Override
	public List<Device> getAllDevices(String subId, List<String> tagNames) {
		List<Device> devices = new ArrayList<Device>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GremlinConstants.SUB_ID, subId);
		String query = null;
		if (isEmpty(tagNames)) {
			query = getAllDevicesOfASub.replace("{filter}", "");
		} else {
			String tagQuery = ".where(out('isTaggedWith').has('name','"
					+ tagNames.get(0) + "')";
			for (int i = 1; i < tagNames.size(); i++) {
				tagQuery += ".and().out('isTaggedWith').has('name','"
						+ tagNames.get(i) + "')";
			}
			tagQuery += ")";
			query = getAllDevicesOfASub.replace("{filter}", tagQuery);
		}
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query,
					param);
			List<Result> resultList = resultSet.all().get();
			if (resultList != null && isNotEmpty(resultList)) {
				for (Result result : resultList) {
					Device device = convertToDevice(result);
					devices.add(device);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in getAllDevices({}) with exception {}", subId,
					e);
		}
		return devices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.DeviceRepository#getAllDeviceOfProtocol
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public List<Device> getAllDeviceOfProtocol(Subscription subscription,
			ConfigurationSearch searchDTO) {
		List<Device> devices = new ArrayList<Device>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GremlinConstants.SUB_ID, subscription.getSubId());
		param.put("make", searchDTO.getMake());
		param.put("deviceType", searchDTO.getDeviceType());
		param.put("model", searchDTO.getModel());
		param.put("protocol", searchDTO.getProtocol());
		param.put("version", searchDTO.getVersion());
		String query = getAllDevicesOfASub.replace("{filter}", pvFilter);

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(query,
					param);
			List<Result> resultList = resultSet.all().get();
			if (resultList != null && isNotEmpty(resultList)) {
				for (Result result : resultList) {
					Device device = convertToDevice(result);
					devices.add(device);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in getAllDeviceOfProtocol with exception ", e);
		}
		return devices;
	}

	@Override
	public List<Device> getAllUnSubscribed() {
		List<Device> devices = new ArrayList<Device>();
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
					getAllUnClaimed);
			List<Result> resultList = resultSet.all().get();
			if (resultList != null && isNotEmpty(resultList)) {
				for (Result result : resultList) {
					Device device = convertToDevice(result);
					devices.add(device);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in getAllDeviceOfProtocol with exception ", e);
		}
		return devices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.datasource.repository.DeviceRepository#createDevice(com.pcs.
	 * datasource.dto.dc.Device)
	 */

	@Override
	public void insertDevice(Device device) {

		Device clone = cloneDevice(device);
		String addDevice = getAddVertexQuery("DEVICE", clone);
		String createDeviceQuery = createDevice.replace("{addDevice}",
				addDevice);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subId", device.getSubscription().getSubId());

		params.put("make", device.getVersion().getMake());
		params.put("type", device.getVersion().getDeviceType());
		params.put("model", device.getVersion().getModel());
		params.put("protocol", device.getVersion().getProtocol());
		params.put("version", device.getVersion().getVersion());
		params.put("nwProtocol", device.getNetworkProtocol().getName());

		try {
			titanSessionManager.getClient().submit(createDeviceQuery, params);
		} catch (Exception e) {
			throw new PersistenceException("Error while fetching ds name : ", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.datasource.repository.DeviceRepository#updateDevice(com.pcs.
	 * datasource.dto.dc.Device)
	 */
	public void updateDevice(String sourceId, Device device) {

		Device clone = cloneDevice(device);
		clone.setSourceId(null);
		String updateDeviceMap = getMap("data", clone);
		String updateDeviceQuery = updateDeviceMap + updateDevice;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceId", sourceId);

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
					updateDeviceQuery, params);
			System.out.println(resultSet);
		} catch (Exception e) {
			throw new PersistenceException("Error while fetching ds name : ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.datasource.repository.DeviceRepository#registerDevice()
	 */
	@Override
	public void registerDevice(Device device) {
		Device clone = cloneDevice(device);
		clone.setSubscription(null);
		String addDevice = getAddVertexQuery("DEVICE", clone);
		String createDeviceQuery = registerDevice.replace("{addDevice}",
				addDevice);

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("make", device.getVersion().getMake());
		params.put("type", device.getVersion().getDeviceType());
		params.put("model", device.getVersion().getModel());
		params.put("protocol", device.getVersion().getProtocol());
		params.put("version", device.getVersion().getVersion());
		params.put("nwProtocol", device.getNetworkProtocol().getName());

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(createDeviceQuery, params);
			resultSet.all().get();
		} catch (Exception e) {
			throw new PersistenceException("Error in saving device : ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pcs.datasource.repository.DeviceRepository#getMaxUnitId()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Integer generateUnitId(ConfigurationSearch version) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("make", version.getMake());
		params.put("deviceType", version.getDeviceType());
		params.put("model", version.getModel());
		params.put("protocol", version.getProtocol());
		params.put("version", version.getVersion());

		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
					getRecycle, params);
			Result result = resultSet.one();

			if (result != null) {
				LinkedHashMap<String, Object> linkedHashMap = result
						.get(LinkedHashMap.class);
				List unitIdValue = (List) linkedHashMap.get("unitId");
				// DROP the Recycle
				titanSessionManager.getClient().submit(
						DROP_VERTEX_WITH_ID.replace("{id}",
								linkedHashMap.get("id").toString()));
				return Integer.parseInt(unitIdValue.get(0).toString());
			} else {
				ResultSet maxUnitResSet = titanSessionManager.getClient()
						.submit(getMaxUnitId, params);
				Result maxUnitRes = maxUnitResSet.one();
				if (maxUnitRes != null) {
					return maxUnitRes.getInt() + unitIdSeed;
				}

			}
		} catch (Exception e) {
			LOGGER.error("Error in getAllDeviceOfProtocol with exception ", e);
		}

		return unitIdSeed;
	}

	/**
	 * Service Method for update write back configuration of a device
	 * 
	 * @param sourceId
	 * @param device
	 */
	public void updateWritebackConf(String sourceId, Device device) {

		Device clone = cloneDeviceWB(device);
		String updateDeviceMap = getMap("data", clone);
		String updateDeviceQuery = updateDeviceMap + updateDevice;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceId", sourceId);

		try {
			titanSessionManager.getClient().submit(updateDeviceQuery, params);
		} catch (Exception e) {
			throw new PersistenceException("Error while fetching ds name : ", e);
		}
	}

	/**
	 * Service Method to get datasource name of a device
	 * 
	 * @param sourceId
	 * @return dataSourceName
	 */
	public String getDatasourceName(Subscription subscription, String sourceId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subId", subscription.getSubId());
		params.put("sourceId", sourceId);
		String datasourceName = null;
		try {
			ResultSet resultSet = titanSessionManager.getClient().submit(
					getDataSourceName, params);
			Result result = resultSet.one();
			if (result != null) {
				datasourceName = result.getString();
			}
		} catch (Exception e) {
			throw new PersistenceException("Error while fetching ds name : ", e);
		}
		return datasourceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pcs.datasource.repository.DeviceRepository#claimDevice(java.lang.
	 * String, com.pcs.datasource.dto.Subscription)
	 */
	@Override
	public void claimDevice(String sourceId, Subscription subscription) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subId", subscription.getSubId());
		params.put("sourceId", sourceId);

		try {
			titanSessionManager.getClient().submit(claimDevice, params);
		} catch (Exception e) {
			throw new PersistenceException("Error in claiming the device", e);
		}
	}

	private Device cloneDeviceWB(Device device) {
		Device clone = new Device();
		clone.setIp(device.getIp());
		clone.setConnectedPort(device.getConnectedPort());
		clone.setWriteBackPort(device.getWriteBackPort());
		return clone;
	}

	private Device cloneDevice(Device device) {
		Device clone = new Device();
		clone.setSourceId(device.getSourceId());
		clone.setDatasourceName(device.getDatasourceName());
		clone.setIsPublishing(device.getIsPublishing());
		clone.setTimeZone(device.getTimeZone());
		clone.setUnitId(device.getUnitId());
		clone.setDeviceId(device.getDeviceId());
		clone.setIp(device.getIp());
		clone.setConnectedPort(device.getConnectedPort());
		clone.setWriteBackPort(device.getWriteBackPort());
		return clone;
	}

	@SuppressWarnings("unchecked")
	private Device convertToDevice(Result result) {
		LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) result
				.getObject();
		Result proResult = new Result(object.remove("device"));
		Device device1 = fromResult(proResult, Device.class);
		Device device2 = fromResult(result, Device.class);
		try {
			nullAwareBeanCopy(device1, device2);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error("Error in nullAwareBeanCopy",e);
		}
		return device1;
	}

	@Override
	public void updateConfigurations(String sourceId,
			DeviceConfigTemplate deviceConfigTemplate) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DevicePointData> getDeviceConfig(String sourceId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		DeviceTitanRepositoryImpl repo = new DeviceTitanRepositoryImpl();

		repo.titanSessionManager = new TitanSessionManager();
		// repo.objectBuilderUtil = new ObjectBuilderUtil();
		repo.titanSessionManager.gremlinAddress = "10.234.31.163";
		repo.titanSessionManager.gremlinPort = "8182";
		
		testGetDataSourceName(repo);
	}
	
	private static void testGetDataSourceName(DeviceTitanRepositoryImpl repo) {
		Subscription subscription = new Subscription();
		subscription.setSubId("OJar1THxAgCZUk3Gf3GXlKTMHWgd");
		String datasourceName = repo.getDatasourceName(subscription,
				"SourceId_004_FromAPI");
		System.out.println(datasourceName);
	}

	@Override
	public List<Device> getAllDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDevice(Device device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> getDeviceReference(Set<String> sourceIds) {
		// TODO Auto-generated method stub
		return null;
	}

}
