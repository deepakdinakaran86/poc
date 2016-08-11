$(document).ready(function(){
FnInitializeRouteScheduleList()
})


function FnInitializeRouteScheduleList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "title",title: "Title",width: 30 },{field: "address",title: "Address",width: 180},{field: "arrivalTime",title: "Arrival Time",width: 130},{field: "depatureTime",title: "Depature Time",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[
        { title: "A", address: "Churchill Tower", arrivalTime: "10:00AM", depatureTime: "10:02AM"},
		{ title: "B", address: "Bay Square 10", arrivalTime: "10:16AM", depatureTime: "10:18AM"},
		{ title: "C", address: "Zabeel Palace", arrivalTime: "10:33AM", depatureTime: "10:36AM"},
		{ title: "D", address: "Silicon Oasis", arrivalTime: "10:42AM", depatureTime: "10:44AM"},
		{ title: "E", address: "Palemera 4", arrivalTime: "10:50AM", depatureTime: "10:52AM"}
         
    ]
	FnDrawGridView('#schedule-list',ArrGridData,ArrColumns,ObjGridConfig);
} 
 
