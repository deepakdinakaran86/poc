package com.pcs.saffron.connectivity.mqtt.impl;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.connectivity.mqtt.MQTTMessage;
import com.pcs.saffron.connectivity.mqtt.MQTTMessageTranslator;
import com.pcs.saffron.connectivity.mqtt.RemoteMessage;
import com.pcs.saffron.connectivity.mqtt.RemoteMessageHandler;

/**
 * Service to maintain a connection to a MQTT Server and provides a means to subscribe
 * to a topic which a listener is interested in receiving messages.
 * 
 * For QOS -1 and QOS - 2 , clean Session should be false.
 * When a client connects to a MQTT broker, it needs to create subscriptions for all topics that it is interested
 * in in order to receive messages from the broker. On a reconnect these topics are lost and the client needs to subscribe again. 
 * This is the normal behavior with no persistent session. But for constrained clients with limited resources it would be a burden 
 * to subscribe again each time they lose the connection. So a persistent session saves all information relevant for the client on the broker. 
 * 
 * The session is identified by the clientId provided by the client on connection establishment.
 * 
 * A retained message is a normal MQTT message with the retained flag set to true. The broker will store the last retained message and the
 * corresponding QoS for that topic Each client that subscribes to a topic pattern, which matches the topic of the retained message, will 
 * receive the message immediately after subscribing. For each topic only one retained message will be stored by the broker.
 * 
 *  @author renukaradhya
 * 
 */
public class MQTTService implements MqttCallback {

	private final Logger LOGGER = LoggerFactory.getLogger(MQTTService.class);

	private static final String CLASS_TAG = "[MQTTService]";

	private static final long DELAY_30_SECS = 30 * 1000L;

	private final Object clientLock = new Object();

	private Timer timer = new Timer();

	private boolean reConnectIfConnectionLost = true;

	//private final Map<String, Subscriber> subscribers;

	private final String brokerURI;

	private final String clientID;

	private final MqttConnectOptions mqttConnectOptions;

	private final MQTTServiceListener listener;

	private final boolean cleanSession;
	private Subscriber subscriber;

	private MqttClient client;

	/**
	 * Represents a listener to the MQTTService which is interested in 
	 * Receiving information about connection state changes
	 */
	public interface MQTTServiceListener{    	
		public void connectionLost();
	}

	/**
	 * Represents a subscriber that has registered as being interested in receiving messages
	 * that are relevant to a specific topic.
	 */
	private static class Subscriber {
		private final RemoteMessageHandler handler;
		private final MQTTMessageTranslator translator;
		private final String topic;

		/**
		 * Constructor
		 * 
		 * @param handler to be notified be a message related to the topic is received from the MQTT Server
		 * @param translator to translate messages which are received into MQTTRemoteMesssage objects
		 * @param topic of the messages the listener is interested in.
		 */
		private Subscriber(final RemoteMessageHandler handler, final MQTTMessageTranslator translator, final String topic) {
			this.handler =  handler;
			this.translator = translator;
			this.topic = topic;
		}
	}

	/**
	 * Constructor.
	 * Clean session is true  by default here.
	 */
	public MQTTService(final MQTTServiceListener listener, final String brokerURI, final String clientID, 
			final String userName, final String password) {
		//subscribers = new HashMap<String, Subscriber>();
		this.brokerURI = brokerURI;
		this.clientID = clientID;
		this.listener = listener;
		this.cleanSession = true;
		this.mqttConnectOptions = new MqttConnectOptions();
		this.mqttConnectOptions.setUserName(userName);
		this.mqttConnectOptions.setPassword(password.toCharArray());
		this.mqttConnectOptions.setCleanSession(true);

	}

	/**
	 * Constructor.
	 */
	public MQTTService(final MQTTServiceListener listener, final String brokerURI, final String clientID, 
			final String userName, final String password, boolean cleanSession) {
		//subscribers = new HashMap<String, Subscriber>();
		this.brokerURI = brokerURI;
		this.clientID = clientID;
		this.listener = listener;
		this.cleanSession = cleanSession;
		this.mqttConnectOptions = new MqttConnectOptions();
		this.mqttConnectOptions.setUserName(userName);
		this.mqttConnectOptions.setPassword(password.toCharArray());
		this.mqttConnectOptions.setCleanSession(cleanSession);
	}


	public String getBrokerURI() {
		return brokerURI;
	}

	/**
	 * Starts the MQTT Service. This will connect to MQTT Server.
	 * 
	 * If a connection fails or a successful connection is lost, reattempt to
	 * connect will not be made by this method.
	 */
	public boolean start(boolean reconnecting) {
		LOGGER.debug(CLASS_TAG + " start() Broker URI: " + brokerURI + ", client ID: " + clientID);

		synchronized(clientLock) {
			if(client == null) {
				try {
					client = new MqttClient(brokerURI, clientID);
					client.setCallback(this);
					client.connect(mqttConnectOptions);
					if(cleanSession || !reconnecting ) {
						subscribeAllToServer();
					}
					return true;
				} catch (MqttException e) {
					String msg = "Exception when trying to start the MQTT Client Service";
					LOGGER.error(CLASS_TAG + msg, e);
					client = null;
				}
			}
			LOGGER.debug(CLASS_TAG + " start() DONE client = " + client);

		}
		return false;
	}

	/**
	 * Un-subscribes all topics from the MQTT Server and disconnects the client.
	 * Disconnects the MQTT Service from the MQTT Server. 
	 * 
	 * Previously subscribed topics do not need to be re-subscribed,  they will be
	 * re-registered with the MQTT Server when the MQTTCommandService is started again.
	 */
	public void stop() {
		synchronized(clientLock) {
			if(client != null){
				LOGGER.trace(CLASS_TAG + " stop() Broker URI: " + brokerURI + ", client ID: " + clientID) ;
				try {
					unSubscribeAllFromServer();
					client.disconnect();
				} catch (MqttException e) {
					LOGGER.error(CLASS_TAG + "stop() Exception when stopping the service", e);
				} finally {
					client = null;
				}
			}
		}
	}

	/**
	 * Subscribe a topic with the MQTT Server.  Once this subscription is made with the MQTTCommandSevice
	 * the topic will always be subscribed with the MQTT Server.
	 * 
	 * When messages are delivered for the subscribed topic they will be parsed and delivered to the provided 
	 * listener.
	 * 
	 * Only one listener can be registered per topic.  If a listener was previously registered for the given 
	 * topic it will be replaced by the new listener and no longer receive message notifications.
	 * 
	 * @param topic To be subscribed with the MQTT Server
	 * @param handler to be notified when messages for the provided topic are delivered by the MQTT Server.
	 * @param translator that will translate the messages from the MQTT Server into the MQTTRemoteMessage that will be
	 * delivered to the provided listener.
	 */
	public void subscribe(final String topic, final RemoteMessageHandler handler, MQTTMessageTranslator translator ){		
		LOGGER.debug(CLASS_TAG + " subscribe() topic = '" + topic+ "'");
		subscriber = new Subscriber(handler, translator, topic);
		boolean serverSyncNeeded = true;
		/*synchronized(subscribers){
			serverSyncNeeded = !subscribers.containsKey(topic);
			subscribers.put(topic, subscriber);
		}*/
		if(serverSyncNeeded){
			subscribeToServer(topic);
		}
	}


	public void subscribe(final String topic, final RemoteMessageHandler handler, MQTTMessageTranslator translator, int qos) {		
		LOGGER.debug(CLASS_TAG + " subscribe() topic = '" + topic+ "'" + "qos = "+qos);
		subscriber = new Subscriber(handler, translator, topic);
		boolean serverSyncNeeded = true;
		/*synchronized(subscribers){
			serverSyncNeeded = !subscribers.containsKey(topic);
			subscribers.put(topic, subscriber);
		}*/
		if(serverSyncNeeded){
			subscribeToServer(topic, qos);
		}
	}

	/**
	 * Publish a RemoteMessage to a the given MQTT topic.  
	 * 
	 * Publishing is done on a best effort basis if an errors occur the publish will
	 * fail silently.
	 * 
	 * @param topic to publish the message too
	 * @param message the message to publish to the given topic.
	 * @param translator to translate the given message into a a format appropriate to be sent to 
	 * a mqtt server.
	 */
	public void publish(final String topic, final RemoteMessage message, MQTTMessageTranslator translator, int qos, boolean retained) {
		MqttMessage toSend = translator.translate(message);
		synchronized(clientLock){
			if(client != null){
				try {
					client.getTopic(topic).publish(toSend.getPayload(), qos, retained);
				} catch (MqttException e) {
					LOGGER.error(CLASS_TAG + " publish() MqttException : ", e);
				}
			}
		}
	}

	/**
	 * Publish a RemoteMessage to a the given MQTT topic.  
	 * 
	 * Publishing is done on a best effort basis if an errors occur the publish will
	 * fail silently.
	 * 
	 * @param topic to publish the message too
	 * @param message the message to publish to the given topic.
	 * @param translator to translate the given message into a a format appropriate to be sent to 
	 * a mqtt server.
	 */
	public void publish(final String topic, final RemoteMessage message, MQTTMessageTranslator translator) {
		MqttMessage toSend = translator.translate(message);
		synchronized(clientLock){
			if(client != null){
				try {
					client.getTopic(topic).publish(toSend);
				} catch (MqttException e) {
					LOGGER.error(CLASS_TAG + " publish() MqttException : ", e);
				}
			}
		}
	}

	/**
	 * Subscribe a topic with the MQTT Server.
	 * 
	 * @param topic to be subscribed to the MQTT Server
	 */
	private void subscribeToServer(final String topic){		
		LOGGER.debug(CLASS_TAG + " subscribeToServer() topic = " + topic);
		synchronized(clientLock){
			if(client != null){
				try {
					client.subscribe(topic);
				} catch (MqttSecurityException e) {
					LOGGER.error(CLASS_TAG + "MqttSecurityException when trying to subscribe to server", e);
				} catch (MqttException e) {
					LOGGER.error(CLASS_TAG + "MqttException when trying to subscribe to server", e);
				}				
			}
		}
	}

	/**
	 * Subscribe a topic with the MQTT Server.
	 * 
	 * @param topic to be subscribed to the MQTT Server
	 */
	private void subscribeToServer(final String topic, int qos){		
		LOGGER.debug(CLASS_TAG + " subscribeToServer() topic = " + topic);
		synchronized(clientLock){
			if(client != null){
				try {
					client.subscribe(topic, qos);
				} catch (MqttSecurityException e) {
					LOGGER.error(CLASS_TAG + "MqttSecurityException when trying to subscribe to server", e);
				} catch (MqttException e) {
					LOGGER.error(CLASS_TAG + "MqttException when trying to subscribe to server", e);
				}				
			}
		}
	}

	/**
	 * Subscribe the topic of all currently subscribed with the MQTT Server.
	 */
	private void subscribeAllToServer(){
		/*synchronized(subscribers){
			Iterator<Subscriber> iter = subscribers.values().iterator();
			while(iter.hasNext()){
				subscribeToServer(iter.next().topic);
			}
		}*/
	}

	/**
	 * Un-subscribe a listener so that it not longer receives any messages from the MQTT Server
	 * for any topics which it subscribed to.
	 * 
	 * This will also cause the subscribed topics to be un-subscribed with the MQTT Server.
	 * 
	 * @param listener to be un-subscribed.
	 */
	public void unSubscribe(final RemoteMessageHandler listener){
		//synchronized(subscribers){
			//List<Subscriber> values = new ArrayList<Subscriber>(subscribers.values());
			//for(int i=0;i!= values.size();++i){
				//Subscriber subscriber = values.get(i);
				if(subscriber.handler == listener){
					unSubscribeFromServer(subscriber.topic);
					//subscribers.remove(subscriber.topic);
				}
			//}
		//}
	}

	/**
	 * Un-subscribe a listener so that it not longer receives any messages from the MQTT Server
	 * for any topics which it subscribed to.
	 * 
	 * This will also cause the subscribed topics to be un-subscribed with the MQTT Server.
	 * 
	 * @param topic that the listener was subscribed with to be removed
	 */
	public void unSubscribe(final String topic){
		//synchronized(subscribers){
			//Subscriber subscriber = subscribers.get(topic);
			if(subscriber != null){
				unSubscribeFromServer(subscriber.topic);
			//	subscribers.remove(subscriber.topic);
			}
		//}
	}

	/**
	 * UnSubscribe a topic with the MQTT Server.
	 * 
	 * @param topic to be un-subscribed with the MQTT Server
	 */
	private void unSubscribeFromServer(final String topic){
		synchronized(clientLock){
			if( client != null){
				try {
					client.unsubscribe(topic);
				} catch (MqttException e) {	
					LOGGER.error(CLASS_TAG + " unSubscribeFromServer Exception: ", e);
				}
			}
		}
	}

	/**
	 * Subscribe the topic of all currently subscribed with the MQTT Server.
	 */
	private void unSubscribeAllFromServer() {
		/*synchronized(subscribers){
			Iterator<Subscriber> iter = subscribers.values().iterator();
			while(iter.hasNext()){
				unSubscribeFromServer(iter.next().topic);
			}
		}*/
	}

	/**
	 * Notification from the MQTTClient that the connection to the MQTT Server was lost.	 * 
	 * As a result an attempt to re-connect with the MQTT Server will be scheduled.
	 */
	public void connectionLost(Throwable cause) {
		String msg = CLASS_TAG + "Connection Lost with the MQTT Server!";
		LOGGER.error(msg);
		synchronized(clientLock) {
			client = null;
		}
		this.listener.connectionLost();

		// schedule a timer for reconnection.
		if(reConnectIfConnectionLost && !isConnected()) {
			try {
				client.close();
			} catch (Exception e) {
				LOGGER.error("MQTTService", "connectionLost", "Exception = "+e.getMessage());
			}

			client = null;
			timer.schedule(new ReconnectTask(), DELAY_30_SECS );
		}
	}

	/**
	 * Notification from the MQTTClient that a message was received for a topic which
	 * a subscription has been made.
	 * 
	 * The Message payload will be parsed and delivered to the relevant listener which
	 * made the subscription.
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {	
		LOGGER.trace(CLASS_TAG + "messageArrived: topic: " + topic + " message: " + message);

		/*Subscriber subscriber = null;
		synchronized(subscribers) {
			subscriber = (Subscriber) subscribers.values().toArray()[0];
		}*/
		if (subscriber != null) {
			final MQTTMessage msg = subscriber.translator.translate(message,topic);
			if (msg == null) {
				LOGGER.error(CLASS_TAG + "messageArrived: parsing error");
				return;
			}
			subscriber.handler.notify(msg,topic);
		} else {
			LOGGER.error(CLASS_TAG + "Unable to find any subscriber for topic : '" + topic + "'");
		}
	}	
	/**
	 * Determine if the MQTTService is currently connected to the MQTTBroker.
	 * 
	 * @return TRUE if a connection to the broker is currently established, otherwise FALSE.
	 */
	public boolean isConnected() {
		synchronized(clientLock) {
			return client != null && client.isConnected();
		}
	}


	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}

	/**
	 * This class is a timer task used to try reconnecting after some delay 
	 * if the client gets disconnected automatically.
	 * 
	 * @author Renukaradhya
	 *
	 */
	class ReconnectTask extends TimerTask {

		/**
		 * 
		 */
		public void run() {
			// try connecting again.
			LOGGER.info("MQTTService", "run", "Task Running. Client ="+client);

			start(true);

			// If not started re-schedule a timer to start after 30 seconds again.
			if(client == null) {
				timer.schedule( new ReconnectTask(), DELAY_30_SECS );
			} 

			if(client != null) {
				LOGGER.info("MQTTService", "run", "connection re-established.");
			}
		}
	}
}
