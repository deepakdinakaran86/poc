$(document).ready(function(){
FnInitializeAlarmList();
})



function FnInitializeAlarmList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "alarmName",title: "Alarm Name",width: 130 },{field: "alarmMessage",title: "Alarm Message",width: 130},{field: "assetName",title: "Asset Name",width: 130},{field: "alarmGeneratedTime",title: "Alarm Generated Time",width: 130},{field: "alarmUpdatedTime",title: "Alarm Updated Time",width: 130},{field: "currentValue",title: "Current Value",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[
        { alarmName: "Over Speeding", alarmMessage: "Vehicle is Overspeeding", assetName: "Car - AD45362", alarmGeneratedTime: "18/06/2016 10:32:45", alarmUpdatedTime: "18/06/2016 18:20:40", currentValue: "120"},
		{ alarmName: "Seat Belt", alarmMessage: "Seat belt not worn", assetName: "Car - ED2342", alarmGeneratedTime: "18/06/2016 10:32:45", alarmUpdatedTime: "18/06/2016 18:20:40", currentValue: "OFF"},
		{ alarmName: "Over Speeding", alarmMessage: "Vehicle is Overspeeding", assetName: "Truck - HD45444", alarmGeneratedTime: "19/06/2016 15:23:54", alarmUpdatedTime: "19/06/2016 17:20:40", currentValue: "110"},
		{ alarmName: "OverSpeeding", alarmMessage: "Vehicle is Overspeeding", assetName: "Car - AD45362", alarmGeneratedTime: "20/06/2016 13:43:23", alarmUpdatedTime: "20/06/2016 15:20:40", currentValue: "90"},
		{ alarmName: "Fuel Level", alarmMessage: "Fuel level is low", assetName: "Truck - AH67675", alarmGeneratedTime: "21/06/2016 17:34:43", alarmUpdatedTime: "21/06/2016 19:20:40", currentValue: "10"}
         
    ]
	FnDrawGridView('#console-list',ArrGridData,ArrColumns,ObjGridConfig);
} 
 
