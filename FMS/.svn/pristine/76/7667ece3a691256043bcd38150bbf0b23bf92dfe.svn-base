function FnGetLatestAlarm(GblAssetName) {
	//Not used
	var VarUrl = '/FMS/ajax/galaxy-am/1.0.0/assets/alarms/{asset_name}/{domain_name}';
	VarUrl = VarUrl.replace("{asset_name}", GblAssetName);
	VarUrl = VarUrl.replace("{domain_name}", payload.currentDomain);
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json', 'json',
			FnResLatestAlarm);
}

function FnResLatestAlarm(response) {
	// Not used
	response = response.entity;
	var ArrResponse = response;
	if ($.isArray(ArrResponse)) {
		$(ArrResponse)
				.each(
						function(key, ObjEquipment) {
							if (ObjEquipment.alarmMessages != undefined
									&& (ObjEquipment.alarmMessages).length > 0) {
								$
										.each(
												ObjEquipment.alarmMessages,
												function(key1, ObjAlaram) {
													var VarDSName = FnGetDatasourceName(ObjEquipment.equipName);
													var message = {
														"body" : [ {
															"datasourceName" : VarDSName,
															"messageType" : "ALARM",
															"status" : (ObjAlaram.status)
																	.toUpperCase(),
															"statusMessage" : ObjAlaram.alarmMessage,
															"parameters" : [ {
																"name" : ObjAlaram.displayName,
																"time" : ObjAlaram.time,
																"value" : ObjAlaram.data,
																"alertName" : ObjAlaram.alarmName
															} ]
														} ]
													}
													FnHandleLatestData(message);
												});
							}
						});
	}
}

function FnAlertsGrid() {
	var alertData = new kendo.data.DataSource({
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

	var grid = $("#alerts-list").kendoGrid({
		autoBind : true,
		dataSource : alertData,
		rowTemplate : kendo.template($("#rowTemplate").html()),
		pageable : {
			refresh : false,
			pageSizes : true,
			previousNext : true
		},
		height : 300,
		columns : [ {
			field : "alertName",
			title : "Alert Name"
		}, {
			field : "message",
			title : "Alert Message"
		}, {
			field : "assetInfo",
			title : "Asset Name"
		}, {
			field : "starttime",
			template : "#: FnCheckTimeEmpty(time)#",
			title : "Alert Generated Time"
		}, {
			field : "time",
			template : "#: FnCheckTimeEmpty(time)#",
			title : "Alert Updated Time"
		}, {
			field : "value",
			title : "Current Value"
		} ]
	});
}

function FnAlertsChart() {
	var ArrAlertChart = [ {
		type : "donut",
		data : [ {
			category : "High",
			value : 35
		}, {
			category : "Medium",
			value : 25
		}, {
			category : "Low",
			value : 20
		} ]
	} ];
	$("#alert-chart")
			.kendoChart(
					{
						title : {
							text : ""
						},
						chartArea : {
							background : "transparent",
							width : 300,
							height : 270
						},
						legend : {
							position : "bottom"
						},
						seriesDefaults : {
							labels : {
								template : "#= category # - #= kendo.format('{0:P}', percentage)#",
								// position: "outsideEnd",
								visible : true,
								background : "transparent"
							}
						},
						series : ArrAlertChart,
						tooltip : {
							visible : true,
							template : "#= category # - #= kendo.format('{0:P}', percentage) #"
						}
					});

}

function FnSetAlertGridUI(data,isInitialData){
	console.log("ObjData ALARMS : "+JSON.stringify(data))
	var grid = $("#alerts-list").data("kendoGrid");
	
	var VarDSName = data.datasourceName;
	var VarAssetName = GblVehicleInfoObj.assetName;
	var VarAlarmStatus = '';
	if(data.status==='TRUE'){
		VarAlarmStatus = 'Alarm';
	} else if(data.status==='FALSE'){
		VarAlarmStatus = 'Normal';
	}
	var VarAlarmMsg = data.statusMessage;
	var ArrParameters = data.parameters;
	
	var len = ArrParameters.length;
	for (var i = 0; i < len; i++) {
		
		var VarAlertName = ArrParameters[i].name;
		var VarId = VarDSName.replace(/ /g, '_') +"_"+ VarAlertName.replace(/ /g, '_');
		
		var isGExist = false;
		
		for (var k = 0; k < grid.dataSource.data().length; k++) {
			var VarChkId = grid.dataSource.data()[k].rowid;
			if (VarId === VarChkId) {
				
				if(data.status === 'FALSE'){
					setTimeout(FnClearAlarmConsole(VarId), 120000);
					return false;
				}
				
				isGExist = true;
				var item = grid.dataSource.data()[k];
				item.set('status', VarAlarmStatus);
				item.set('message', VarAlarmMsg);
				item.set('time', ArrParameters[i].time);
				item.set('value', ArrParameters[i].value);
				
				
				
			}
		}
		
		if (!isGExist) {
			if(data.status === 'FALSE'){
//				setTimeout(FnClearAlarmConsole(VarId), 120000);
				return false;
			}
			grid.dataSource.add({
							alertName : VarAlertName,
							status : VarAlarmStatus,
							message : VarAlarmMsg,
							assetInfo : VarAssetName,
							datasourceName : VarDSName,
							time : ArrParameters[i].time,
							rowid : VarId,
							starttime : ArrParameters[i].time,
							value : ArrParameters[i].value
							
			});
		}
		grid.dataSource.sync();
	}
}

function FnClearAlarmConsole(VarId){
	var grid = $("#alerts-list").data("kendoGrid");
	var dataItem = grid.dataItem($('#'+VarId));
	if(dataItem != undefined){
		console.log(dataItem);
		grid.dataSource.remove(dataItem);
		grid.dataSource.sync();
	}
}