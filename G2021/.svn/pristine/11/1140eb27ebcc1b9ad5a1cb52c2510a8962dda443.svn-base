$(document).ready(function(){
FnInitializeScheduleList();
FnInitializeDeviceTree();
});

function FnInitializeDeviceTree(){
 $("#treeview").kendoTreeView();
 }


function FnInitializeScheduleList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "pointName",title: "Point Name",width: 130,template: ViewLink },{field: "alarmName",title: "Alarm Name",width: 130},{field: "alarmValueSettings",title: "Alarm Value Settings",width: 130},{field: "alarmRangeSettings",title: "Alarm Range Settings",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#definition-list',[],ArrColumns,ObjGridConfig);
} 
 

