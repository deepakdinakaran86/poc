"use strict";

$(document).ready(function(){
  FnInitializeClientsGrid();
})

$(window).load(function(){
  FnGetClientsList();
});


function FnInitializeClientsGrid(){
	//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewtenant' style='text-transform: capitalize;'>#=tenantName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=tenantName#</a>";
	var ViewLink ="<a class='grid-viewtenant' style='text-transform: capitalize;'>tenantName</a>";
	var ArrColumns = [{field: "tenantName",title: "Client Name",template: ViewLink },{field: "tenantId",title: "Client Id"},{field: "firstName",title: "First Name"},{field: "lastName",title: "Last Name"},{field: "contactEmail",title: "Email Id"},{field: "status",title: "Status"},{title: "Action",template: "<a class='grid-viewtenanthome' title='#=tenantName#'><i class='smicon sm-homeicon '></i></a>",width: 70}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 320,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#clients-list',[],ArrColumns,ObjGridConfig);
}

function FnGetClientsList(){
  console.log('Clients:FnGetClientsList');
}

function FnCreateClient(){
console.log('Clients:FnCreateClient');

}

function FnCreateClientAdmin(){
  console.log('Clients:FnCreateClientAdmin');
}
