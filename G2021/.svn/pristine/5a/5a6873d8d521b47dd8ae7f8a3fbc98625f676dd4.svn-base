$(document).ready(function(){
FnInitializeRouteList()
})


function FnInitializeRouteList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "routeName",title: "Route Name",width: 130 },{field: "totalDistance",title: "Total Distance",width: 130},{field: "totalDuration",title: "Total Duration",width: 130},{field: "action",title: "Action",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[
        { routeName: "Route1", totalDistance: "35km", totalTime: "45min"},
		{ routeName: "Route2", totalDistance: "28km", totalTime: "30min"},
		{ routeName: "Route3", totalDistance: "32km", totalTime: "40min"},
		{ routeName: "Route4", totalDistance: "38km", totalTime: "55min"}
         
    ]
	FnDrawGridView('#route-list',ArrGridData,ArrColumns,ObjGridConfig);
} 
 
