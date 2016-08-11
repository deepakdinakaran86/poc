<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script src="resources/themes/default/js/jquery.min.js"></script>

<link rel="stylesheet"
	href="resources/kendo/css/kendo.common-material.min.css" />
<link rel="stylesheet" href="resources/kendo/css/kendo.material.min.css" />

<script src="resources/themes/default/js/jquery.min.js"></script>
<script src="resources/themes/default/js/kendo.all.min.js"></script>

<script src="resources/js/websocket/WebSocket.js"></script>
<script src="resources/js/websocket/JmsClient.js"></script>
<script src="resources/js/websocket/webSocketConnection.js"></script>

<script>

	var assetsJson = ${assets}; 
	
	var validAssets = assetsJson.validAssets;
	
	var inValidAssets = assetsJson.invalidAssets;
	//inValidAssets = ["asset 1","asset 2"];
	
	$(document).ready(function() {

		var webSocketUrl = assetsJson.webSocketUrl;
		var datasourceName = assetsJson.destination;
		
		 $("#panelbar").kendoPanelBar();
   
 
	var cList = $('ul.mylist');
				if (typeof inValidAssets != 'undefined') {
					$.each(inValidAssets,
							function(i) {
								var li = $('<li/>').appendTo(cList);
								var aaa = $('<a/>').text(inValidAssets[i])
										.appendTo(li);
							});
				}

				if (typeof webSocketUrl != 'undefined'
						&& typeof datasourceName != 'undefined') {

					handleConnect(webSocketUrl, datasourceName);
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
					height : 620,
					pageable : {
						refresh : false,
						pageSizes : true,
						previousNext : true
					},
					columns : [ {
						field : "assetName",
						title : "Asset Name",
						width : 80,
						editable : false
					}, {
						field : "parameterName",
						title : "Parameter",
						width : 80,
						editable : false
					}, {
						field : "time",
						title : "Last Updated",
						width : 80,
						editable : false,
						template : "#: toDateFormat(time, 'F')#"
					}, {
						field : "value",
						title : "Value",
						width : 80,
						editable : false
					} ]
				});
				deviceData.read();

			});

	var handleMessage = function(destination, destinationId, message) {
		console.log("handling data from live  - : " + destination);
		if (message instanceof TextMessage) {
			var grid = $("#grid").data("kendoGrid");

			content = message.getText();
			var data = $.parseJSON(content);
			var dest = data.datasourceName;
			for (var i = 0; i < data.parameters.length; i++) {
				console.log("Parameter Name : " + data.parameters[i].name
						+ " value : " + data.parameters[i].value
						+ " datatype : " + data.parameters[i].dataType
						+ " time : " + data.parameters[i].time);
				var paramName = data.parameters[i].name;
				paramName = paramName.replace(/ /g, '_');
				/* updateChart(paramName, data.parameters[i].value,
						data.parameters[i].dataType, data.parameters[i].time); */

				var paramName = data.parameters[i].name;
				var assetName = '';
				for (var j = 0; j < validAssets.length; j++) {
					if (validAssets[j].datasourceName == dest) {
						assetName = validAssets[j].entityName;
						break;
					}
				}
				if (assetName == '') {
					continue;
				}

				var isExist = false;
				for (var k = 0; k < grid.dataSource.data().length; k++) {
					var dsParamName = grid.dataSource.data()[k].parameterName;
					var dsAssetName = grid.dataSource.data()[k].assetName;
					if (paramName == dsParamName && assetName == dsAssetName) {
						isExist = true;
						var item = grid.dataSource.data()[k];
						item.set('value', data.parameters[i].value);
						item.set('time', data.parameters[i].time);
					}
				}
				if (!isExist) {
					grid.dataSource.add({
						parameterName : paramName,
						value : data.parameters[i].value,
						unit : 'u',
						time : data.parameters[i].time,
						assetName : assetName
					});
				}

				grid.dataSource.sync();

			}
		}
	}

	function toDateFormat(time, format) {

		var date = new Date(Number(time));
		console.log(date.toUTCString());
		return date.toUTCString();
	}
</script>





<body>
	<div >
		
	<ul id="panelbar">
    <li id="lst1" ><h4>Connection Info</h4>
        <ul class="mylist" >
        </ul>
    </li>
	</ul>
	</div>
	<div id="grid"></div>


</body>