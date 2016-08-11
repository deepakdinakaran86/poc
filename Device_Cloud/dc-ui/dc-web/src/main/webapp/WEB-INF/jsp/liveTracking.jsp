<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval var="dsUrl" expression="@propertyConfigurer.getProperty('datasource.getallparams')" />
<spring:eval var="wsUrl" expression="@propertyConfigurer.getProperty('datasource.getWsConn')" />

<!-- bxSlider Javascript file -->
<script src="resources/js/jquery.bxslider.min.js"></script>
 <!-- bxSlider CSS file -->
<link href="resources/css/jquery.bxslider.css" rel="stylesheet" />


</head>
<!-- Required for IE6/IE7 cross-origin support -->
<meta name="kaazing:postMessageBridgeURL"
	content="PostMessageBridge.html">



<!-- kendo CSS for chart -->
<!-- <link rel="stylesheet"
	href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.dataviz.min.css" />
<link rel="stylesheet"
	href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.dataviz.material.min.css" />
 -->

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<script type="text/javascript">window.deviceUrl1 = "${dsUrl}";window.wsUrl1 = "${wsUrl}";</script>

<script>
var handleMessage = function(destination, destinationId, message) {
	console.log("handling data from live  - : " + destination);
	if (message instanceof TextMessage) {
		content = message.getText();
		var data = $.parseJSON(content);
		for(var i=0;i<data.parameters.length;i++){
			console.log("Parameter : " + data.parameters[i].name + " value : " + data.parameters[i].value + " datatype : " + data.parameters[i].dataType);
			var paramName = data.parameters[i].name;
			paramName = paramName.replace(/ /g,'_');
			updateChart(paramName,data.parameters[i].value,data.parameters[i].dataType,data.parameters[i].time);
		}
		FnReloadSlider();
	}	
};

</script>
<style>.bx-wrapper .bx-viewport { height:420px !important}</style>
</head>

<body>
<div style="">
<div class="container">

		<div class="row"> 
				<div class="col-md-12"> 				
						<div id="deviceDetails">
							<label> Selected Device : </label> <%= request.getParameter("datasourceName") %><br/>
							<div hidden><select id="parameter" name="parameter"></select></div>
							<br/>
						</div>
				</div> 
		</div> <!--  row02 -->

<div class="row">
			<div class="col-md-12"> 	
					<div class="slider-wrapper"></div>
			</div>
	</div> <!--  row03 -->


</div><!--  container -->


	</div>	
	
	
	<script>
	var hv = $('#datasourceName').text();
		liveDevice('<%= request.getParameter("datasourceName") %>');
	</script>
</body>
</html>