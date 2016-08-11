$(document).ready(function(){
FnInitializeTagList();
});


function FnInitializeTagList(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
	var ArrColumns = [{field: "tagName",title: "Tag Name",width: 130 }];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#tag-list',[],ArrColumns,ObjGridConfig);
} 
