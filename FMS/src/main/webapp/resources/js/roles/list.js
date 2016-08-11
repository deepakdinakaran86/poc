"use strict";

$(document).ready(function(){
	$('body').tooltip({
		selector: '.grid-viewrole'
	});
	//FnInitializeRoleGrid();
});

$(window).load(function(){
	console.log('Roles: List');
	FnGetRoleList();
});

function FnInitializeRoleGrid(){
	var ObjPageAccess = {'view':'1'};
	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewrole'>#=roleName#</a>" : "<a href='Javascript:void(0)'>#=roleName#</a>";
	var ViewLink ="";
	var ArrColumns = [{field: "roleName",title: "Role Name",template: ViewLink },{field: "description",title: "Description"}];
	
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	FnDrawGridView('#roles-list',[], ArrColumns, ObjGridConfig);
}

function FnGetRoleList(){
	$("#GBL_loading").show();
	var VarUrl = 'ajax/galaxy-um/1.0.0/roles?domain_name=' +UserInfo.getCurrentDomain();
	console.log(VarUrl); 
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResRoleList);
}

function FnResRoleList(response){	
	$("#GBL_loading").hide();
	var ArrResponse = response;
	//console.log(JSON.stringify(ArrResponse));
	
	var ObjPageAccess = {'view':'1'};
	var ViewLink = (UserInfo.hasPermission('role_management','view')) ? "<a href='Javascript:void(0)' class='grid-viewrole' data-toggle='tooltip' title='View #=roleName# details'>#=roleName#</a>" : "<a href='Javascript:void(0)'>#=roleName#</a>";
	var ArrColumns = [{field: "roleName",title: "Role Name",template: ViewLink,width: 330 },{field: "description",title: "Description"}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var objDatasource = {};
	objDatasource['data']=ArrResponse.entity;
	objDatasource['sort']={field: "roleName", dir: "asc"}
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#roles-list',objDatasource,ArrColumns,ObjGridConfig);
	
	$("#roles-list").data("kendoGrid").tbody.on("click", ".grid-viewrole", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#roles-list").data('kendoGrid').dataItem(tr);
		
		data = data.roleName;
		$( "#role_id").val(data);
		$("#gapp-role-view").submit();
	});
}

function FnCreateRole(){
	$('#gapp-role-create').submit();
}