$(document).ready(function(){
FnInitializeClientList();
FnInitializeAssignDeviceList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializeClientList(){
 $("#treeview").kendoTreeView();
 }


function FnInitializeAssignDeviceList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "sourceId",title: "Source Id",width: 130,template: ViewLink },{field: "deviceName",title: "Device Name",width: 130},{field: "clientId",title: "Client Id",width: 130}];
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
