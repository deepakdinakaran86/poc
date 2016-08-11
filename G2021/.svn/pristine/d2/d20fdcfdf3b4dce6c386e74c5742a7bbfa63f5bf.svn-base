$(document).ready(function(){
FnInitializeServiceList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializeServiceList(){
//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "userName",title: "User Name",width: 130 },{field: "activityTimeStamp",title: "Activity TimeStamp",width: 130},{field: "eventDomain",title: "Event Domain",width: 130},{field: "affectedModule",title: "Affected Module",width: 130},{field: "auditSummary",title: "Audit Summary",width: 130},{field: "ipAddress",title: "IP Address",width: 130},{field: "eventLocale",title: "Event Locale",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[
        { userName: "alex@pcs.galaxy", activityTimeStamp: "22/06/2016 10:35:20", eventDomain: "pcs", affectedModule:"User Management", auditSummary: "User Creates" ,ipAddress: "10.137.43.104",eventLocale: "Dubai" },
		{ userName: "steve@tesla.galaxy", activityTimeStamp: "12/06/2016 09:15:40", eventDomain: "tesla", affectedModule:"Roles Management", auditSummary: "Roles updated" ,ipAddress: "10.137.43.204",eventLocale: "Dubai" },
		{ userName: "morrish@cola.galaxy", activityTimeStamp: "18/06/2016 06:30:23", eventDomain: "cola", affectedModule:"vehicles Namagement", auditSummary: "Vehicles added" ,ipAddress: "10.137.33.245",eventLocale: "AbuDhabi" },
		{ userName: "jobi@apple.galaxy", activityTimeStamp: "23/06/2016 10:35:20", eventDomain: "apple", affectedModule:"Configuration Template", auditSummary: "Template created" ,ipAddress: "10.137.11.138",eventLocale: "Dubai" },
		{ userName: "govind@samsung.galaxy", activityTimeStamp: "24/06/2016 10:35:20", eventDomain: "samsung", affectedModule:"Service item", auditSummary: "Items creates" ,ipAddress: "10.137.23.167",eventLocale: "Dubai" }
         
    ]
	FnDrawGridView('#audit-list',ArrGridData,ArrColumns,ObjGridConfig);
} 
 

function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
