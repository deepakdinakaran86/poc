"use strict";

$(document).ready(function(){
  FnInitializeUsersGrid();
})

$(window).load(function(){
  FnGetUsersList();
});


function FnInitializeUsersGrid(){
//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "userName",title: "User Name",width: 130,template: ViewLink },{field: "firstName",title: "First Name",width: 130},{field: "lastName",title: "Last Name",width: 130},{field: "emailId",title: "Email Id",width: 130},{field: "roleName",title: "Role Name",width: 130},{field: "contactNumber",title: "Mobile Number",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 540,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#users-list',[],ArrColumns,ObjGridConfig);
}

function FnGetUsersList(){
  console.log('Users:FnGetUsersList');
}

function FnCancelUser(){
console.log('Users:FnCancelUser');

}

function FnSaveUser(){
  console.log('Users:FnSaveUser');
}

function FnShowTags(){
console.log('Users:FnShowTags');

}
