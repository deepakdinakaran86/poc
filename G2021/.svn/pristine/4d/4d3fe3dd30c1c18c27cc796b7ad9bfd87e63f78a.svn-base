$(document).ready(function(){
FnInitializePOITypeList()
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializePOITypeList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "tagType",title: "Tag Type",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#tagtype-list',[],ArrColumns,ObjGridConfig);
} 
 

function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
