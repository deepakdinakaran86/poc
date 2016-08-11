$(document).ready(function(){
FnInitializePOIList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializePOIList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "poiName",title: "POI Name",width: 130,template: ViewLink },{field: "poiType",title: "POI Type",width: 130},{field: "description",title: "Description",width: 130},{field: "latitude",title: "Latitude",width: 130},{field: "longitude",title: "Longitude",width: 130},{field: "radius",title: "Radius",width: 130}];
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
