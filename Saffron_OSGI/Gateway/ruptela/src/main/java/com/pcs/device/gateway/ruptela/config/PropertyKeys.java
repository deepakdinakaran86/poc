/**
 * 
 */
package com.pcs.device.gateway.ruptela.config;

/**
 * @author pcseg310
 *
 */
public interface PropertyKeys {

	static final String CACHE_PROVIDER = "ruptela.devicemanager.cacheprovider";
	static final String CACHE_PROVIDER_CONFIG_PATH = "ruptela.devicemanager.cacheprovider.config.path";
	static final String DEVICE_COMMAND_CACHE = "ruptela.command.cache";
	
	static final String DEVICE_SESSION_CACHE = "ruptela.devicemanager.cache.session";
	static final String DEVICE_SESSION_CACHE_TIMEOUT = "ruptela.devicemanager.cache.session.timeout";
	static final String DEVICE_CONFIGURATION_CACHE = "ruptela.devicemanager.cache.configuration";
	static final String DEVICE_KEYS_CACHE = "ruptela.devicemanager.cache.keys";
	static final String DEVICE_POINTS_PROTOCOL_CACHE = "ruptela.devicemanager.cache.points.protocol";
	
	static final String REMOTE_PLATFORM_IP = "ruptela.devicemanager.remote.platform.hostIp";
	static final String REMOTE_PLATFORM_PORT = "ruptela.devicemanager.remote.platform.port";
	static final String REMOTE_PLATFORM_SCHEME = "ruptela.devicemanager.remote.platform.scheme";
	static final String AUTHENTICATION_URL = "ruptela.devicemanager.remote.authentication.url";
	static final String CONFIGURATION_URL = "ruptela.devicemanager.remote.configuration.url";
	static final String DATASOURCE_REGISTER_URL = "ruptela.devicemanager.datasource.register.url";
	static final String DATASOURCE_UPDATE_URL = "ruptela.devicemanager.datasource.update.url";
	static final String DATASOURCE_PUBLISH_URL = "ruptela.devicemanager.datasource.publish.url";
	static final String DATASOURCE_PLATFORM_IP ="ruptela.devicemanager.datasource.platform.hostIp";
	static final String DATASOURCE_PLATFORM_PORT="ruptela.devicemanager.datasource.platform.port";
	static final String ENTITY_PLATFORM_IP ="ruptela.devicemanager.entity.platform.hostIp";
	static final String ENTITY_PLATFORM_PORT="ruptela.devicemanager.entity.platform.port";
	static final String DEVICE_DATASOURCE_UPDATE_URL = "ruptela.devicemanager.device.datasource.update.url";
	static final String DATADISTRIBUTOR_IP="ruptela.devicemanager.datadistributor.ip";
	static final String DATADISTRIBUTOR_REGISTRY_NAME = "ruptela.devicemanager.datadistributor.registryname";
	static final String ANALYZED_MESSAGE_STREAM= "ruptela.devicemanager.datadistributor.analyzedmessagestream";
	static final String DECODED_MESSAGE_STREAM = "ruptela.devicemanager.datadistributor.decodedmessagestream";
	static final String DATADISTRIBUTOR_PORT = "ruptela.devicemanager.datadistributor.port";
	static final String DEVICE_PROTOCOL_POINTS_URL = "ruptela.devicemanager.datasource.device.points";
	//Gateway configurations
	static final String START_MODE = "ruptela.devicegateway.startmode";
	static final String START_WITH_DELAY = "ruptela.devicegateway.startwithdelay";
	
	static final String TCP_DATA_SERVER_DOMAIN = "ruptela.devicegateway.tcp.dataserverdomain";
	static final String TCP_DATA_SERVER_IP = "ruptela.devicegateway.tcp.dataserverip";
	static final String TCP_DATA_SERVER_PORT = "ruptela.devicegateway.tcp.dataserverport";
	static final String TCP_CONTROL_SERVER_PORT = "ruptela.devicegateway.tcp.controlserverport";
	static final String TCP_DATA_COMMAND_PORT = "ruptela.devicegateway.tcp.commandport";
	
	static final String UDP_DATA_SERVER_DOMAIN = "ruptela.devicegateway.udp.dataserverdomain";
	static final String UDP_DATA_SERVER_IP = "ruptela.devicegateway.udp.dataserverip";
	static final String UDP_DATA_SERVER_PORT = "ruptela.devicegateway.udp.dataserverport";
	static final String UDP_CONTROL_SERVER_PORT = "ruptela.devicegateway.udp.controlserverport";
	static final String UDP_DATA_COMMAND_PORT = "ruptela.devicegateway.udp.commandport";
	static final String DATA_DISTRIBUTOR_IP = "ruptela.devicegateway.datadistributor.ip";
	static final String DATA_DISTRIBUTOR_PORT = "ruptela.devicegateway.datadistributor.port";
	static final String REALTIME_DATA_PERSIST_TOPIC ="ruptela.devicegateway.realtime.persist.topic"; 

	static final String COMMAND_REGISTER_URL = "ruptela.devicegateway.command.register.url";
	static final String COMMAND_REGISTER = "ruptela.devicegateway.command.register";
	
	static final String FMXXX_MODEL = "FMS";
	static final String FMXXX_PROTOCOL = "FMPRO";
	static final String FMXXX_TYPE = "Telematics";
	static final String FMXXX_VENDOR = "Ruptela";
	static final String FMXXX_VERSION = "1.02";
	
	static final String DIAG_ENABLE="diag.enable";
}
