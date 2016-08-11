<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>




<script src="resources/javascript/websocket/WebORB.js"></script>

<script>
	var myConsumer ;
	var devices = ${assets};

	//var latest = '[{source_id:"123",latestData:[{"deviceTime": 1446448308000,"data": "0.0",  "customTag": "Altitude",    "systemTag": "Power"  },{ "deviceTime": 1446448308000,"data": "24.9755485","customTag": "Latitude","systemTag": "Power"}]},{source_id:"456",latestData:[{"deviceTime": 1446448308000,"data": "0.0","customTag": "CoolantTemperature","systemTag": "Power"}]}]';

	$(document).ready(
			function() {
				//webORB.defaultMessagingURL = "ws://localhost:8888";

				var webSocketUrl = devices.webSocketUrl;
				//webSocketUrl = 'ws://10.234.60.55:8888';
				var datasourceName = devices.destination;
				var latest = devices.latestData;
				var dest1=devices.destination;

				if (typeof webSocketUrl != 'undefined'
						&& typeof datasourceName != 'undefined') {
					subscribe(webSocketUrl, datasourceName);
				}

				var deviceData = new kendo.data.DataSource({
					data : [],
					pageSize : 10,
					page : 1,
					serverPaging : false,
					serverFiltering : false,
					serverSorting : false,
					sort : {
						field : "time",
						dir : "desc"
					}
				});
				$("#pager").kendoPager({
					dataSource : deviceData,
					pageSizes : [ 10, 25, 50 ]
				});

				var grid = $("#grid").kendoGrid({
					autoBind : true,
					dataSource : deviceData,
					height : 480,
					pageable : {
						refresh : false,
						pageSizes : true,
						previousNext : true
					},
					columns : [ /*{
						field : "datasourceName",
						title : "Datasource Name",
						width : 80,
						editable : false
					},*/ {
						field : "parameterName",
						title : "Parameter",
						width : 80,
						editable : false
					}, {
						field : "value",
						title : "Value",
						width : 80,
						editable : false
					},{
						field : "time",
						title : "Last updated time",
						width : 80,
						editable : false,
						template : "#: toDateFormat(time, 'F')#"
					}, {
						field : "unit",
						title : "Unit",
						width : 80,
						editable : false
					}]
				});
				deviceData.read();
				
				
	if (typeof latest != 'undefined') {
	var grid = $("#grid").data("kendoGrid");
					$.each(latest, function(i, item) {
						console.log(item.sourceId);
						$.each(item.latestData, function(j, item2) {
							grid.dataSource.add({
								datasourceName : dest1,
								parameterName : item2.customTag,
								value : item2.data,
								unit : item2.unit,
								time : item2.deviceTime
							});
						});
					});
				}

			});

	var subscribe = function(webSocketUrl, destination) {
		webORB.defaultMessagingURL = webSocketUrl;
		myConsumer = new Consumer(destination, new Async(handleMessage,
				handleFault));
		myConsumer.subscribe();
	}
	
	var unsubscribe = function(){
		myConsumer.unsubscribe();
		console.log("unsubscribed");
	}

	var handleMessage = function(message) {
		//console.log("handling data from live  - : " + destination);
		//if (message instanceof TextMessage) {
		var grid = $("#grid").data("kendoGrid");

		content = message.body[0];
		console.log("Message Content : " + content);
		var data = $.parseJSON(content);
		if(data.messageType!='MESSAGE'){
			return;
		}
		var dest = data.datasourceName;
		for (var i = 0; i < data.parameters.length; i++) {
			console.log("Datasource Name : " + dest + " Parameter Name : "
					+ data.parameters[i].name + " value : "
					+ data.parameters[i].value + " datatype : "
					+ data.parameters[i].dataType + " time : "
					+ data.parameters[i].time);
			var paramName = data.parameters[i].name;
			paramName = paramName.replace(/ /g, '_');

			var paramName = data.parameters[i].name;

			var isExist = false;
			for (var k = 0; k < grid.dataSource.data().length; k++) {
				var dsParamName = grid.dataSource.data()[k].parameterName;
				if (paramName == dsParamName) {
					isExist = true;
					var item = grid.dataSource.data()[k];
					item.set('value', data.parameters[i].value);
					item.set('time', data.parameters[i].time);
				}
			}
			if (!isExist) {
				grid.dataSource.add({
					datasourceName : dest,
					parameterName : paramName,
					value : data.parameters[i].value,
					unit : data.parameters[i].unit,
					time : data.parameters[i].time
				});
			}

			grid.dataSource.sync();
		}
		//}
	}

	function handleFault(fault) {
		console.log('fault');
	}

	function toDateFormat(time, format) {
		var date = new Date(Number(time));
		console.log(date.toUTCString());
		return date.toUTCString();
	}

	var latestData = function() {
	}
	
	function submitBackAction(){
		unsubscribe();
		$("#device_home").submit();
	}
</script>


<body>
		<div class="container2 theme-showcase dc_main" role="main" style="margin: 0px 10px;">
		<section class="dc_maincontainer animate-panel">
			<div class="page-header">
				<h1>Live Tracking</h1>
			</div>
			<div style="height: 1px;">
				<form action="device_home" id="device_home" method="get"></form>
			</div>
			<div class="col-md-12 rowextraspacing">
				<div class="source_id pull-left" style="margin-right:40px;">
				<label>Source Id:</label><span class="text-right sourceid"> ${sourceId} </span>
				</div>
				<div class="datasource_name">
				<label>Datasource Name:</label>
				<span class="text-right sourceid"> ${datasourceName} </span>
				</div>
			</div>
			<div class="col-md-12">
			<div id="grid"></div>
			</div>
			<div class="col-md-12">
			<section class="text-right dc_groupbtn">
				<button type="button" onClick="submitBackAction()"
					class="btn btn-default">Back</button>
			</section>
			</div>
		</section>
	</div>

</body>