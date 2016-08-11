/**
 * 
 */
package com.pcs.device.gateway.hobbies.config;

/**
 * @author pcseg310
 *
 */
public interface PropertyKeys {

	static final String CACHE_PROVIDER = "hobbies.devicemanager.cacheprovider";
	static final String CACHE_PROVIDER_CONFIG_PATH = "hobbies.devicemanager.cacheprovider.config.path";
	static final String DEVICE_COMMAND_CACHE = "hobbies.command.cache";
	
	static final String DEVICE_SESSION_CACHE = "hobbies.devicemanager.cache.session";
	static final String DEVICE_SESSION_CACHE_TIMEOUT = "hobbies.devicemanager.cache.session.timeout";
	static final String DEVICE_CONFIGURATION_CACHE = "hobbies.devicemanager.cache.configuration";
	static final String DEVICE_KEYS_CACHE = "hobbies.devicemanager.cache.keys";
	static final String DEVICE_POINTS_PROTOCOL_CACHE = "hobbies.devicemanager.cache.points.protocol";
	
	static final String REMOTE_PLATFORM_IP = "hobbies.devicemanager.remote.platform.hostIp";
	static final String REMOTE_PLATFORM_PORT = "hobbies.devicemanager.remote.platform.port";
	static final String REMOTE_PLATFORM_SCHEME = "hobbies.devicemanager.remote.platform.scheme";
	static final String AUTHENTICATION_URL = "hobbies.devicemanager.remote.authentication.url";
	static final String CONFIGURATION_URL = "hobbies.devicemanager.remote.configuration.url";
	static final String DATASOURCE_REGISTER_URL = "hobbies.devicemanager.datasource.register.url";
	static final String DATASOURCE_UPDATE_URL = "hobbies.devicemanager.datasource.update.url";
	static final String DATASOURCE_PUBLISH_URL = "hobbies.devicemanager.datasource.publish.url";
	static final String DATASOURCE_PLATFORM_IP ="hobbies.devicemanager.datasource.platform.hostIp";
	static final String DATASOURCE_PLATFORM_PORT="hobbies.devicemanager.datasource.platform.port";
	static final String ENTITY_PLATFORM_IP ="hobbies.devicemanager.entity.platform.hostIp";
	static final String ENTITY_PLATFORM_PORT="hobbies.devicemanager.entity.platform.port";
	static final String DEVICE_DATASOURCE_UPDATE_URL = "hobbies.devicemanager.device.datasource.update.url";
	static final String DATADISTRIBUTOR_IP="hobbies.devicemanager.datadistributor.ip";
	static final String DATADISTRIBUTOR_REGISTRY_NAME = "hobbies.devicemanager.datadistributor.registryname";
	static final String ANALYZED_MESSAGE_STREAM= "hobbies.devicemanager.datadistributor.analyzedmessagestream";
	static final String DECODED_MESSAGE_STREAM = "hobbies.devicemanager.datadistributor.decodedmessagestream";
	static final String DATADISTRIBUTOR_PORT = "hobbies.devicemanager.datadistributor.port";
	static final String DEVICE_PROTOCOL_POINTS_URL = "hobbies.devicemanager.datasource.device.points";
	//Gateway configurations
	static final String START_MODE = "hobbies.devicegateway.startmode";
	static final String START_WITH_DELAY = "hobbies.devicegateway.startwithdelay";
	
	static final String TCP_DATA_SERVER_DOMAIN = "hobbies.devicegateway.tcp.dataserverdomain";
	static final String TCP_DATA_SERVER_IP = "hobbies.devicegateway.tcp.dataserverip";
	static final String TCP_DATA_SERVER_PORT = "hobbies.devicegateway.tcp.dataserverport";
	static final String TCP_CONTROL_SERVER_PORT = "hobbies.devicegateway.tcp.controlserverport";
	static final String TCP_DATA_COMMAND_PORT = "hobbies.devicegateway.tcp.commandport";
	
	static final String UDP_DATA_SERVER_DOMAIN = "hobbies.devicegateway.udp.dataserverdomain";
	static final String UDP_DATA_SERVER_IP = "hobbies.devicegateway.udp.dataserverip";
	static final String UDP_DATA_SERVER_PORT = "hobbies.devicegateway.udp.dataserverport";
	static final String UDP_CONTROL_SERVER_PORT = "hobbies.devicegateway.udp.controlserverport";
	static final String UDP_DATA_COMMAND_PORT = "hobbies.devicegateway.udp.commandport";
	static final String DATA_DISTRIBUTOR_IP = "hobbies.devicegateway.datadistributor.ip";
	static final String DATA_DISTRIBUTOR_PORT = "hobbies.devicegateway.datadistributor.port";
	static final String REALTIME_DATA_PERSIST_TOPIC ="hobbies.devicegateway.realtime.persist.topic"; 

	static final String COMMAND_REGISTER_URL = "hobbies.devicegateway.command.register.url";
	static final String COMMAND_REGISTER = "hobbies.devicegateway.command.register";
	
	static final String APPLE_DEVICE_MODEL = "Mobile";
	static final String APPLE_DEVICE_PROTOCOL = "ios";
	static final String APPLE_DEVICE_TYPE = "mobile";
	static final String APPLE_VENDOR = "Apple";
	static final String APPLE_OS_VERSION = "x.xx";
	
	
	static final String ANDROID_DEVICE_MODEL = "Mobile";
	static final String ANDROID_DEVICE_PROTOCOL = "Android";
	static final String ANDROID_DEVICE_TYPE = "mobile";
	static final String ANDROID_VENDOR = "Google";
	static final String ANDROID_OS_VERSION = "x.xx";
	
	
	static final String MQTT_BROKER_URL="hobbies.devicegateway.mqtt.broker.url";
	static final String MQTT_BROKER_USERNAME="hobbies.devicegateway.mqtt.broker.username";
	static final String MQTT_BROKER_PASSWORD="hobbies.devicegateway.mqtt.broker.password";
	static final String MQTT_DEVICE_FEED_TOPIC="hobbies.devicegateway.mqtt.broker.device.feed.topic.root";
	static final String MQTT_DEVICE_COMMAND_TOPIC="hobbies.devicegateway.mqtt.broker.server.command.topic.root";
	static final String MQTT_DEVICE_COMMAND_RESPONSE_TOPIC="hobbies.devicegateway.mqtt.broker.server.command.response.topic.root";
	static final String MQTT_DEVICE_EVENT_FEED_TOPIC="hobbies.devicegateway.mqtt.broker.device.event.topic.root";
	
	static final String MQTT_DEVICE_FEED_QOS="hobbies.devicegateway.mqtt.broker.device.feed.qos";
	
	
	static final String AUTOCLAIM_URL = "hobbies.autoclaim.url";
	static final String AUTOCLAIM_IP = "hobbies.autoclaim.api.ip";
	static final String AUTOCLAIM_PORT = "hobbies.autoclaim.api.port";
	static final String AUTOCLAIM_SCHEME = "hobbies.autoclaim.api.scheme";
	
	static final String DIAG_ENABLE="diag.enable";
}
