$(document).ready(function(){
FnInitializeDeviceTree();
FnInitializeDeviceList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializeDeviceTree(){
 $("#treeview").kendoTreeView();
 }


function FnInitializeDeviceList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "sourceId",title: "Source Id",width: 130,template: ViewLink },{field: "deviceName",title: "Device Name",width: 130},{field: "tags",title: "Tags",width: 130},{field: "make",title: "Make",width: 130},{field: "type",title: "Type",width: 130},{field: "model",title: "Model",width: 130},{field: "protocol",title: "Protocol",width: 130},{field: "version",title: "Version",width: 130},{field: "networkProtocol",title: "Network Protocol",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#device-list',[],ArrColumns,ObjGridConfig);
} 
 

function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
