$(document).ready(function(){
FnInitializeComponentList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializeComponentList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "componentName",title: "Component Name",width: 130,template: ViewLink },{field: "poiType",title: "POI Type",width: 130},{field: "serviceItem",title: "Service Item",width: 130},{field: "frequency",title: "Frequency",width: 130},{field: "notifyBefore",title: "Notify Before",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#component-list',[],ArrColumns,ObjGridConfig);
} 
 

function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
