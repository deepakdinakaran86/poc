$(document).ready(function(){
FnInitializeScheduleList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializeScheduleList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "scheduleName",title: "Schedule Name",width: 130,template: ViewLink },{field: "component",title: "Component",width: 130},{field: "occurrenceType",title: "Occurrence Type",width: 130},{field: "description",title: "Description",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#poi-list',[],ArrColumns,ObjGridConfig);
} 
 

function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
