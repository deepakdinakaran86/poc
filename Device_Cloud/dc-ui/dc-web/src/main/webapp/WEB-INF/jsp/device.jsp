
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="resources/js/JMSClientJS/WebSocket.js"></script>
<script src="resources/js/JMSClientJS/JmsClient.js"></script>
<script src="resources/js/webSocketConnection.js"></script>

<script src="resources/javascript/managedevice.js"></script>
<script src="resources/javascript/historydevice.js"></script>
<script src="resources/javascript/livedevice.js"></script>
<script src="resources/javascript/writebackdevice.js"></script>
<script src="resources/javascript/device.js"></script>

<script src="resources/js/chart/createLineChart.js"></script>
<script src="resources/js/chart/createBooleanChart.js"></script>


<style>
.bx-wrapper,.bx-viewport {
	height: auto !important;
	min-height: 300px;
}


</style>
<body>
<!-- 	<div id="tabStrip"></div> -->
	
<!--   <ol class="breadcrumb" style=" font-size: small;background-color: transparent;"> -->
<!-- 	  <li><a href="#" onclick="loadPage('devicetab')">Device Home</a></li> -->
<!--   </ol>		 -->
	<div class="panel" style="border-color: border-color: rgba(221, 221, 221, 0)">
		  
		  <header class="gx-page-title"><h2 id="panelHeaderTitle">Home</h2> <img src="resources/themes/gx/images/icons/w/favorites-add.png" class="gx-favorites" style="display: none;"></header>
		  
		  <div class="panel-body">
		    <div id="contentModule"></div>
		  </div>
		</div>
	
	<span id="staticNotification"></span>
</body>
<script>
	var handleMessage = function(destination, destinationId, message) {
		console.log("handling data from  device : " + destination);

	};
</script>
<!--script: to show notifications messages start-->

<script id="errorTemplate" type="text/x-kendo-template">
                <div class="errorTemp">
                   <img src="resources/themes/gx/images/icons/w/error-icon.png" />            
                    <h3>#= message #</h3>
                </div>
          </script>

<script id="successTemplate" type="text/x-kendo-template">
                <div class="successTemp">
                    <img src="resources/themes/gx/images/icons/w/success-icon.png" />
                    <h3>#= message #</h3>
                </div>
            </script>
<!--script: to show notifications messages end-->
<script>

var staticNotification;
initNotifications();
	$("#tabStrip").kendoTabStrip({
		animation : false,
		dataTextField : "text",
		dataContentUrlField : "contentUrl"
	});

	function removeTab(varThis,nodeText) {
		var VarConId = $(varThis).closest('li').attr('aria-controls');

		var tabCount = $(varThis).closest('li').index();
		if (tabCount == tabStrip.select().index()) {
			tabStrip.select(0);
		}
		if(nodeText=="LIVE"){
			handleDisconnect();
		}
		tabStrip.remove(tabCount);
		//$(varThis).closest('li').remove();
		//$('#'+VarConId).remove();
		handleDisconnect();

	}

	var tabStrip = $("#tabStrip").data("kendoTabStrip");	
	loadPage("devicetab");
	function addNewTab(button) {

		var selectedNodeText = button.text;
		var selectedNodeValue = button.id;
		var contentUrl = button.url;

		if ((selectedNodeText != "DEVICE")&&(selectedNodeText != "WRITE BACK LOG")&&(selectedNodeText != "CONFIGURATION")) {
			selectedNodeText = selectedNodeText
					+ "<span class='k-icon k-i-close' style='margin-left:4px;' onclick='removeTab(this,selectedNodeText)'>";
			selectedNodeText = selectedNodeText.replace("selectedNodeText",'"'+button.text+'"');
					
		}
		var data = tabStrip.element.data("my-data") || [];
		if (findTab(tabStrip, button.text)) {
			if(button.text=="LIVE"){
				handleDisconnect();
			}
			var index = tabStrip.tabGroup.find("li:textEquals(" + button.text + ")").prev().index();
			tabStrip.remove("li:textEquals(" + button.text + ")");
			tabStrip.insertAfter({
				text : selectedNodeText,
				encoded : false,
				contentUrl : contentUrl
			}, tabStrip.tabGroup.children("li").eq(index));
		}
		else{
			tabStrip.append({
				text : selectedNodeText,
				encoded : false,
				contentUrl : contentUrl
			});
			}
		tabStrip.select("li:textEquals(" + button.text + ")");
	}

	$.expr[':'].textEquals = $.expr.createPseudo(function(arg) {
	    return function( elem ) {
	        return $(elem).text().match("^" + arg + "$");
	    };
	});

// 	var button = {
// 		"text" : "DEVICE",
// 		"id" : "device",
// 		"url" : "devicetab"
// 	};
// 	addNewTab(button);
	
	
// 	var button = {
// 		"text" : "WRITE BACK LOG",
// 		"id" : "writebacklog",
// 		"url" : "writebacklog"
// 	};
// 	addNewTab(button);
	
// 	var button = {
// 		"text" : "CONFIGURATION",
// 		"id" : "configTemplate",
// 		"url" : "configTemplate"
// 	};
// 	addNewTab(button);
	
// 	tabStrip.select(0);
</script>