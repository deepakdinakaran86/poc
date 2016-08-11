/**
 * 
 */
package com.pcs.device.gateway.teltonika.config;

/**
 * @author pcseg310
 *
 */
public interface PropertyKeys {

	static final String CACHE_PROVIDER = "teltonika.devicemanager.cacheprovider";
	static final String CACHE_PROVIDER_CONFIG_PATH = "teltonika.devicemanager.cacheprovider.config.path";
	static final String DEVICE_COMMAND_CACHE = "teltonika.command.cache";
	
	static final String DEVICE_SESSION_CACHE = "teltonika.devicemanager.cache.session";
	static final String DEVICE_SESSION_CACHE_TIMEOUT = "teltonika.devicemanager.cache.session.timeout";
	static final String DEVICE_CONFIGURATION_CACHE = "teltonika.devicemanager.cache.configuration";
	static final String DEVICE_KEYS_CACHE = "teltonika.devicemanager.cache.keys";
	static final String DEVICE_POINTS_PROTOCOL_CACHE = "teltonika.devicemanager.cache.points.protocol";
	
	static final String REMOTE_PLATFORM_IP = "teltonika.devicemanager.remote.platform.hostIp";
	static final String REMOTE_PLATFORM_PORT = "teltonika.devicemanager.remote.platform.port";
	static final String REMOTE_PLATFORM_SCHEME = "teltonika.devicemanager.remote.platform.scheme";
	static final String AUTHENTICATION_URL = "teltonika.devicemanager.remote.authentication.url";
	static final String CONFIGURATION_URL = "teltonika.devicemanager.remote.configuration.url";
	static final String DATASOURCE_REGISTER_URL = "teltonika.devicemanager.datasource.register.url";
	static final String DATASOURCE_UPDATE_URL = "teltonika.devicemanager.datasource.update.url";
	static final String DATASOURCE_PUBLISH_URL = "teltonika.devicemanager.datasource.publish.url";
	static final String DATASOURCE_PLATFORM_IP ="teltonika.devicemanager.datasource.platform.hostIp";
	static final String DATASOURCE_PLATFORM_PORT="teltonika.devicemanager.datasource.platform.port";
	static final String ENTITY_PLATFORM_IP ="teltonika.devicemanager.entity.platform.hostIp";
	static final String ENTITY_PLATFORM_PORT="teltonika.devicemanager.entity.platform.port";
	static final String DEVICE_DATASOURCE_UPDATE_URL = "teltonika.devicemanager.device.datasource.update.url";
	static final String DATADISTRIBUTOR_IP="teltonika.devicemanager.datadistributor.ip";
	static final String DATADISTRIBUTOR_REGISTRY_NAME = "teltonika.devicemanager.datadistributor.registryname";
	static final String ANALYZED_MESSAGE_STREAM= "teltonika.devicemanager.datadistributor.analyzedmessagestream";
	static final String DECODED_MESSAGE_STREAM = "teltonika.devicemanager.datadistributor.decodedmessagestream";
	static final String DATADISTRIBUTOR_PORT = "teltonika.devicemanager.datadistributor.port";
	static final String DEVICE_PROTOCOL_POINTS_URL = "teltonika.devicemanager.datasource.device.points";
	//Gateway configurations
	static final String START_MODE = "teltonika.devicegateway.startmode";
	static final String START_WITH_DELAY = "teltonika.devicegateway.startwithdelay";
	
	static final String TCP_DATA_SERVER_DOMAIN = "teltonika.devicegateway.tcp.dataserverdomain";
	static final String TCP_DATA_SERVER_IP = "teltonika.devicegateway.tcp.dataserverip";
	static final String TCP_DATA_SERVER_PORT = "teltonika.devicegateway.tcp.dataserverport";
	static final String TCP_CONTROL_SERVER_PORT = "teltonika.devicegateway.tcp.controlserverport";
	static final String TCP_DATA_COMMAND_PORT = "teltonika.devicegateway.tcp.commandport";
	
	static final String UDP_DATA_SERVER_DOMAIN = "teltonika.devicegateway.udp.dataserverdomain";
	static final String UDP_DATA_SERVER_IP = "teltonika.devicegateway.udp.dataserverip";
	static final String UDP_DATA_SERVER_PORT = "teltonika.devicegateway.udp.dataserverport";
	static final String UDP_CONTROL_SERVER_PORT = "teltonika.devicegateway.udp.controlserverport";
	static final String UDP_DATA_COMMAND_PORT = "teltonika.devicegateway.udp.commandport";
	static final String DATA_DISTRIBUTOR_IP = "teltonika.devicegateway.datadistributor.ip";
	static final String DATA_DISTRIBUTOR_PORT = "teltonika.devicegateway.datadistributor.port";
	static final String REALTIME_DATA_PERSIST_TOPIC ="teltonika.devicegateway.realtime.persist.topic"; 

	static final String COMMAND_REGISTER_URL = "teltonika.devicegateway.command.register.url";
	static final String COMMAND_REGISTER = "teltonika.devicegateway.command.register";
	
}
