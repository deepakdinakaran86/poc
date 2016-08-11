"use strict";

$(function() {

	$(window).on("resize", function() {
		kendo.resize($(".chart-wrapper"));
	});
	
	$( window ).load(function() {	
		var VarStartDate = FnDateProcess('prev',6);
		var VarEndDate = FnDateProcess('next',1);
		FnGetAlarmHistoryList(FnConvertLocalToUTC(VarStartDate),FnConvertLocalToUTC(VarEndDate));	
		$("#fromdate").data("kendoDatePicker").value(new Date(VarStartDate));
		$("#todate").data("kendoDatePicker").value(new Date());
	});
	
	// create DatePicker from input HTML element
    $("#fromdate, #todate").kendoDatePicker({format: "MM/dd/yyyy"});	

});

function FnGetAlarmHistoryList(VarStartDate,VarEndDate){
	
	var VarUrl = '/FMS/ajax/galaxy-dm/1.0.0/devices/alarms/history/{domain_name}/{start_date}/{end_date}';
	VarUrl = VarUrl.replace("{domain_name}",payload.currentDomain);
	VarUrl = VarUrl.replace("{start_date}",VarStartDate);
	VarUrl = VarUrl.replace("{end_date}",VarEndDate);	
	FnMakeAjaxRequest(VarUrl, 'GET','', 'application/json', 'json', FnResAlarmHistoryList);

}

function FnResAlarmHistoryList(response){
	response = response.entity;
	var ArrResponse = response;
	var ArrResponseData = [];
	if($.isArray(ArrResponse)){
		$(ArrResponse).each(function(key,ObjEquipment){
			if(ObjEquipment.alarmMessages != undefined && (ObjEquipment.alarmMessages).length>0){
				$.each(ObjEquipment.alarmMessages,function(key1,ObjAlaram){ 
					var VarAlarmStatus = (ObjAlaram.status ==='true') ? 'ALARM' : 'NORMAL';
					ArrResponseData.push({'assetname':ObjEquipment.equipName,'alarmname':ObjAlaram.alarmName,'status':VarAlarmStatus,'alarmtime':(ObjAlaram.time).toString(),'alarmvalue':ObjAlaram.data,'alarmmsg':ObjAlaram.alarmMessage});
				});
			}			
		});
	} else {
		if(ArrResponse.errorCode != undefined){
			FnShowNotificationMessage(ArrResponse);
		}
	}
	
	var grid = $("#gapp-alarmhistory-list").data("kendoGrid");
	if (grid != null) {
		$("#gapp-alarmhistory-list").data("kendoGrid").dataSource
				.data(ArrResponseData);
	} else {
		var ArrColumns = [{field: "alarmname",title: "Alert Name"},{field: "alarmmsg",title: "Alert Information"},{field: "assetname",title: "Asset Name"},{field: "alarmtime",title: "Alarm Time",template : "#: toDateFormat(alarmtime, 'F')#"},{field: "alarmvalue",title: "Current Value"},{field: "status",title: "Status"}];
		var ObjGridConfig = {
			"scrollable" : false,
			"filterable" : { mode : "row" },
			"sortable" : true,
			"height" : 0,
			"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
			"selectable" : true
		};
		$(".k-grid-content").mCustomScrollbar('destroy');
		
		FnDrawGridView('#gapp-alarmhistory-list',ArrResponseData,ArrColumns,ObjGridConfig);
	}

}

function FnSearchAlarmHistory(){
	var VarStartDate = $('#fromdate').val();
	var VarEndDate = $('#todate').val();
	if(VarStartDate !='' && VarEndDate != ''){
		var VarEndDate1 = (FnConvertLocalToUTC(VarEndDate) + (24*60*60*1000));
		FnGetAlarmHistoryList(FnConvertLocalToUTC(VarStartDate),VarEndDate1);		
	}

}

