
//var webSocketUrl = 'ws://10.234.60.42:8001/jms';
var webSocketUrl;

var webSocketConnect = function(){
	console.log("liveUpdateUrl: " + liveUpdateUrl);
		var ds = new Array();
		ds[0] =sourceId;
		$
		.ajax({
			url : "ajax" + liveUpdateUrl,
			type : 'POST',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(ds),
			success : function(response) {
				console.log(">>>response: " + JSON.stringify(response.entity));
				webSocketUrl = response.entity.webSocketUrl;
				destination = response.entity.destination;
				doConnect(webSocketUrl,sourceId);
			},
			error : function(xhr, status, error) {
//				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
//				staticNotification.show({
//					message: errorMessage
//				}, "error");
				console.log("No available data for this datasource.");
			}
		});
	
	
};


function doConnect(webSocketUrl,sourceId) {	
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
							
							handleSubscribe(deviceName); //commenting as subscription not required on connection

							connection.start(function() {
							});
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