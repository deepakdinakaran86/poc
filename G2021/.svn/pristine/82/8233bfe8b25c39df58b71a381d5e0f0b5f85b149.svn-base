$(document).ready(function(){
FnInitializeScheduleList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializeScheduleList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "alarmName",title: "Alarm Name",width: 130,template: ViewLink },{field: "alarmMessage",title: "Alarm Message",width: 130},{field: "assetName",title: "Asset Name",width: 130},{field: "alarmTime",title: "Alarm Time",width: 130},{field: "currentValue",title: "Current Value",width: 130},{field: "status",title: "Status",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#history-list',[],ArrColumns,ObjGridConfig);
} 
 

function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
