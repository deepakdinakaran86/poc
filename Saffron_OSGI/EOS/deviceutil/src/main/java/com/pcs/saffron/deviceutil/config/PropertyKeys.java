/**
 * 
 */
package com.pcs.saffron.deviceutil.config;

/**
 * @author pcseg310
 *
 */
public interface PropertyKeys {

	static final String CACHE_PROVIDER = "EOS.devicemanager.cacheprovider";
	static final String CACHE_PROVIDER_CONFIG_PATH = "EOS.devicemanager.cacheprovider.config.path";
	static final String DEVICE_COMMAND_CACHE = "EOS.command.cache";
	static final String DEVICE_POINTS_PROTOCOL_CACHE = "EOS.devicemanager.cache.points.protocol";
	
	static final String DEVICE_SESSION_CACHE = "EOS.devicemanager.cache.session";
	static final String DEVICE_SESSION_CACHE_TIMEOUT = "EOS.devicemanager.cache.session.timeout";
	static final String DEVICE_CONFIGURATION_CACHE = "EOS.devicemanager.cache.configuration";
	static final String DEVICE_KEYS_CACHE = "EOS.devicemanager.cache.keys";
	
	static final String REMOTE_PLATFORM_IP = "EOS.devicemanager.remote.platform.hostIp";
	static final String REMOTE_PLATFORM_PORT = "EOS.devicemanager.remote.platform.port";
	static final String REMOTE_PLATFORM_SCHEME = "EOS.devicemanager.remote.platform.scheme";
	static final String AUTHENTICATION_URL = "EOS.devicemanager.remote.authentication.url";
	static final String CONFIGURATION_URL = "EOS.devicemanager.remote.configuration.url";
	
	static final String DATASOURCE_REGISTER_URL = "EOS.devicemanager.datasource.register.url";
	static final String DATASOURCE_UPDATE_URL = "EOS.devicemanager.datasource.update.url";
	static final String DATASOURCE_PUBLISH_URL = "EOS.devicemanager.datasource.publish.url";
	static final String DATASOURCE_PLATFORM_IP ="EOS.devicemanager.datasource.platform.hostIp";
	static final String DATASOURCE_PLATFORM_PORT="EOS.devicemanager.datasource.platform.port";
	
	static final String ENTITY_PLATFORM_IP ="EOS.devicemanager.entity.platform.hostIp";
	static final String ENTITY_PLATFORM_PORT="EOS.devicemanager.entity.platform.port";
	static final String DEVICE_DATASOURCE_UPDATE_URL = "EOS.devicemanager.device.datasource.update.url";

	static final String DEVICE_WRITEBACK_RESPONSE_IP = "EOS.devicemanager.writeback.response.hostIp";
	static final String DEVICE_WRITEBACK_RESPONSE_PORT = "EOS.devicemanager.writeback.response.port";
	static final String DEVICE_WRITEBACK_RESPONSE_URL = "EOS.devicemanager.datasource.writeback.statusupdate.url";
	
	static final String DEVICE_WRITEBACK_CONFIG_IP = "EOS.devicemanager.writeback.config.update.hostIp";
	static final String DEVICE_WRITEBACK_CONFIG_PORT = "EOS.devicemanager.writeback.config.update.port";
	static final String DEVICE_WRITEBACK_CONFIG_URL = "EOS.devicemanager.datasource.writeback.configupdate.url";
	
	static final String DEVICE_REGISTRATION_URL = "EOS.devicemanager.device.registration.url";
	static final String DEVICE_TYPE_MODEL = "EOS.devicegateway.type.model";
	
	
	static final String DATADISTRIBUTOR_IP="EOS.devicemanager.datadistributor.ip";
	static final String DATADISTRIBUTOR_REGISTRY_NAME = "EOS.devicemanager.datadistributor.registryname";
	static final String ANALYZED_MESSAGE_STREAM= "EOS.devicemanager.datadistributor.analyzedmessagestream";
	static final String DECODED_MESSAGE_STREAM = "EOS.devicemanager.datadistributor.decodedmessagestream";
	static final String DECODED_MESSAGE_STREAM_PUBLISH = "EOS.devicemanager.datadistributor.decodedmessagestream.publish";
	static final String LIVE_MESSAGE_STREAM = "EOS.devicemanager.datadistributor.livemessagestream";
	static final String ALARM_STREAM = "EOS.devicemanager.datadistributor.alarmstream";
	static final String DEVICE_REGISTRATION_STREAM = "EOS.devicemanager.datadistributor.device.registry";
	
	static final String DATADISTRIBUTOR_PORT = "EOS.devicemanager.datadistributor.port";
	
	//Gateway configurations
	static final String START_MODE = "EOS.devicegateway.startmode";
	static final String START_WITH_DELAY = "EOS.devicegateway.startwithdelay";
	
	static final String TCP_DATA_SERVER_DOMAIN = "EOS.devicegateway.tcp.dataserverdomain";
	static final String TCP_DATA_SERVER_IP = "EOS.devicegateway.tcp.dataserverip";
	static final String TCP_DATA_SERVER_PORT = "EOS.devicegateway.tcp.dataserverport";
	static final String TCP_CONTROL_SERVER_PORT = "EOS.devicegateway.tcp.controlserverport";
	static final String TCP_DATA_COMMAND_PORT = "EOS.devicegateway.tcp.commandport";
	
	static final String UDP_DATA_SERVER_DOMAIN = "EOS.devicegateway.udp.dataserverdomain";
	static final String UDP_DATA_SERVER_IP = "EOS.devicegateway.udp.dataserverip";
	static final String UDP_DATA_SERVER_PORT = "EOS.devicegateway.udp.dataserverport";
	static final String UDP_CONTROL_SERVER_PORT = "EOS.devicegateway.udp.controlserverport";
	static final String UDP_DATA_COMMAND_PORT = "EOS.devicegateway.udp.commandport";
	static final String DATA_DISTRIBUTOR_IP = "EOS.devicegateway.datadistributor.ip";
	static final String DATA_DISTRIBUTOR_PORT = "EOS.devicegateway.datadistributor.port";
	static final String REALTIME_DATA_PERSIST_TOPIC ="EOS.devicegateway.realtime.persist.topic"; 

	static final String COMMAND_REGISTER_URL = "EOS.devicegateway.command.register.url";
	static final String COMMAND_REGISTER = "EOS.devicegateway.command.register";
			
	static final String RABBIT_MQ_LIVE_QUEUE = "EOS.devicegateway.rabbitmq.livequeue";
	static final String RABBIT_MQ_HOST = "EOS.devicegateway.rabbitmq.host";
	static final String RABBIT_MQ_PORT = "EOS.devicegateway.rabbitmq.port";
	static final String RABBIT_MQ_USERNAME = "EOS.devicegateway.rabbitmq.username";
	static final String RABBIT_MQ_PASSWORD = "EOS.devicegateway.rabbitmq.password";
	static final String REALTIME_DATA_QUEUE = "EOS.devicegateway.livedata";
	
	static final String TEST_MESSAGE_STREAM = "TestStream";
	static final String DEVICE_PROTOCOL_POINTS_URL = "EOS.devicemanager.datasource.device.points";
	//Gateway configurations
	
	
	static final String SOURCESTORE = "device.sourceid.path";
	static final String  DEVICE_HISTORY_URL = "EOS.devicemanager.device.history.url";
	
	static final String DEVICE_TYPE_VENDOR = "";
}
