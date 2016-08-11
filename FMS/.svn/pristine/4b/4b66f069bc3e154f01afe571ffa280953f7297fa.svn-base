$(document).ready(function(){
FnInitializeRouteScheduleList()
})


function FnInitializeRouteScheduleList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "routeSchedule",title: "Route Schedule",width: 130 },{field: "routeName",title: "Route Name",width: 130},{field: "action",title: "Action",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[
        { routeSchedule: "Route1", routeName: "35km"},
		{ routeSchedule: "Route2", routeName: "28km"},
		{ routeSchedule: "Route3", routeName: "32km"},
		{ routeSchedule: "Route4", routeName: "38km"}
         
    ]
	FnDrawGridView('#schedule-list',ArrGridData,ArrColumns,ObjGridConfig);
} 
 
