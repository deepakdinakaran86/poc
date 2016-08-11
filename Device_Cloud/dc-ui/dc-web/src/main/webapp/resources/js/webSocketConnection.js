var destinationCounter = 1;
var destination;
function handleConnect(webSocketUrl,deviceName) {

	var jmsConnectionFactory = new JmsConnectionFactory(webSocketUrl);

	console.log("starting to connect......");
	
	
	
	
	// setup challenge handler
	//setupSSO(jmsConnectionFactory.getWebSocketFactory());
	try {
		var connectionFuture = jmsConnectionFactory.createConnection('', '',
				function() {
					if (!connectionFuture.exception) {
						try {
							connection = connectionFuture.getValue();
							connection.setExceptionListener(handleException);

							console.log("CONNECTED");

							session = connection.createSession(false,
									Session.AUTO_ACKNOWLEDGE);
							transactedSession = connection.createSession(true,
									Session.AUTO_ACKNOWLEDGE);

							connection.start(function() {
							});
							
							handleSubscribe(deviceName); //commenting as subscription not required on connection
						} catch (e) {
							handleException(e);
							console.log(e);
						}
					} else {
						handleException(connectionFuture.exception);
						console.log(e);
					}
				});
	} catch (e) {
		handleException(e);
		console.log(e);
	}
}

var handleException = function(e) {
	console.log(e);
	if (e.type == "ConnectionDisconnectedException") {
		//updateConnectionButtons(false);
	}
}

var handleDisconnect = function() {

	// Clear any subscriptions.

	console.log("Websocket connection closing.....");
	try {
		connection.close(function() {
			console.log("CONNECTION CLOSED");
			//updateConnectionButtons(false);
		});
	} catch (e) {
		handleException(e);
	}
}

function handleSubscribe(destination) {

//    var destinationId = destinationCounter++;
//    
//        console.log("SUBSCRIBE: " + destination + " <span class=\"subscriptionTag\">[#"+destinationId+"]</span>");
//
//    var dest = createDestination(destination, session);
//
//    var consumer;
//
//    if (document.getElementById("messageSelector").value.length > 0) {
//        consumer = session.createConsumer(dest, document.getElementById("messageSelector").value);
//    } else {
//        consumer = session.createConsumer(dest);
//    }
//
//    consumer.setMessageListener(function(message) {
//	
//        handleMessage(destination, destinationId, message);
//    });
	
	 var name = destination;		
	    var destinationId = destinationCounter++;				    
	    var dest = createDestination(name, session);		    		   		    	
	    var consumer;
	
	    consumer = session.createConsumer(dest);	
	    console.log("topic created..");
		 					
		                    	
	    consumer.setMessageListener(function(message) {
	        handleMessage(name, destinationId, message);
	    });
	
}




var setupSSO = function(webSocketFactory) {
	/* Respond to authentication challenges with popup login dialog */
	var basicHandler = new BasicChallengeHandler();
	basicHandler.loginHandler = function(callback) {
		popupLoginDialog(callback);
	}
	webSocketFactory.setChallengeHandler(basicHandler);
}

function createDestination(name, session,type) {
//    if (type == undefined || type.indexOf("topic") == 0) {
//        return session.createTopic("/topic/"+name);
//    }
//    else if (type.indexOf("queue") == 0) {
//        return session.createQueue("/queue/"+name);
//    }
//    else {
//        throw new Error("Destination must start with /topic/ or /queue/");
//    }
	name = "/topic/"+ name;	

	if (name != "") {
		console.log("connecting to " + name +"...");
        return session.createTopic(name);
    }else{
    	throw new Error("Destination must start with /topic/ or /queue/");
    }
	
}