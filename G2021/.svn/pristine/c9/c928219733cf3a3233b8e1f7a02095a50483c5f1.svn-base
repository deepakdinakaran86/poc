"use strict";

$(document).ready(function(){
  FnInitializeRoleGrid();
})

$(window).load(function(){
  FnGetClientsList();
});


function FnInitializeRoleGrid(){
	//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewrole'>#=roleName#</a>" : "<a href='Javascript:void(0)'>#=roleName#</a>";
	var ViewLink ="<a class='grid-viewrole'>#=roleName#</a>";
	var ArrColumns = [{field: "roleName",title: "Role Name",width: 130,template: ViewLink },{field: "description",title: "Description",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#roles-list',[],ArrColumns,ObjGridConfig);
}

function FnCreateRole(){
	 console.log('Roles:FnCreateRole');
	
}


