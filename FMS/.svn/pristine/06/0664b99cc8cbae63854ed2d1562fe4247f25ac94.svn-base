"use strict";

$(document).ready(function(){
		
	$('body').tooltip({
		selector: '.grid-viewtenant,.grid-viewtenanthome'
	});
	FnInitializeClientsGrid();
})

$(window).load(function(){
	FnGetClientsList();
});


function FnInitializeClientsGrid(){
	var ObjPageAccess = {'add':'1','list':'1','view':'1','edit':'1','sendemail':'1'};
	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewtenant' style='text-transform: capitalize;'>#=tenantName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=tenantName#</a>";
	//var ViewLink ="<a class='grid-viewtenant' style='text-transform: capitalize;'>tenantName</a>";
	var ArrColumns = [{field: "tenantName",title: "Client Name",template: ViewLink },
	                  {field: "tenantId",title: "Client Id"},
	                  {field: "firstName",title: "First Name"},
	                  {field: "lastName",title: "Last Name"},
	                  {field: "contactEmail",title: "Email Id"},
	                  {field: "status",title: "Status"},
	                  {title: "Action",template: "<a class='grid-viewtenanthome' title='#=tenantName#'><i class='smicon sm-homeicon '></i></a>",width: 70}];
	var ObjGridConfig = {
			"scrollable" : false,
			"filterable" : { mode : "row" },
			"sortable" : true,
			"height" : 320,
			"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
			"selectable" : true
	};

	FnDrawGridView('#clients-list',[],[],ObjGridConfig);
}

function FnGetClientsList(){
	$("#GBL_loading").show();
	var VarUrl = "ajax/"+listTenantsUrl;
	VarUrl = VarUrl.replace("{domain}",currentDomain);
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResClientsList);
}

function FnResClientsList(response){
	
	var ArrResponse = response.entity;
	var VarResLength = response.entity.length;
	var ArrResponseData = [];
	
	
	
	if(VarResLength > 0){
		$("#GBL_loading").hide();
		$(ArrResponse).each(function(){
			var element = {};
			element["parentDomain"] = this.domain.domainName;
			$(this.dataprovider).each(function() {
				var key = this.key;
				element[key] = this.value;
			});
			element["identifier"] = this.identifier.value;
			element["status"] = this.entityStatus.statusName;
			ArrResponseData.push(element);
		});
	} else 
		{		
			alert('No Data Available');
		}

	var ViewLink = (UserInfo.hasPermission('client_management','view')) ? "<a class='grid-viewtenant' style='text-transform: capitalize;' data-toggle='tooltip' title='View #=tenantName# details'  href='Javascript:void(0)'>#=tenantName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=tenantName#</a>";
	
	var ClientHome = (UserInfo.hasPermission('client_management','navigate')) ? "<a class='grid-viewtenanthome' title=''><i class='fa fa-home' aria-hidden='true'></i></a>" : "<a class='' title=''><i class='fa fa-home' aria-hidden='true'></i></a>";

	var ArrColumns = [{field: "tenantName",title: "Client Name",template: ViewLink },
	                  {field: "tenantId",title: "Client Id"},
	                  {field: "firstName",title: "First Name"},
	                  {field: "lastName",title: "Last Name"},
	                  {field: "contactEmail",title: "Email Id"},
	                  {field: "status",title: "Status"},
	                  {title: "Action",template: ClientHome,width: 70}];
	var ObjGridConfig = {
			"scrollable" : false,
			"filterable" : { mode : "row" },
			"sortable" : true,
			"height" : 320,
			"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
			"selectable" : true
	};

	var objDatasource = {};
	objDatasource['data']=ArrResponseData;
	objDatasource['sort']={field: "tenantName", dir: "asc"}
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#clients-list',objDatasource,ArrColumns,ObjGridConfig);


	var clientlist = $("#clients-list").data("kendoGrid");
	clientlist.bind("change", FnGridChangeEvent);

	$("#clients-list").data("kendoGrid").tbody.on("click", ".grid-viewtenant", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#clients-list").data('kendoGrid').dataItem(tr);	
		$('#tenant_name').val(JSON.stringify(data));
		var getClientId= $('#tenant_name').val(JSON.stringify(data));
		console.log(JSON.stringify(getClientId));
		$("#tenant_view").submit();
	});

	$("#clients-list").data("kendoGrid").tbody.on("click", ".grid-viewtenanthome", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#clients-list").data('kendoGrid').dataItem(tr);
		var ObjTenantInfo = {};
		ObjTenantInfo['tenantId'] = data.tenantId;
		ObjTenantInfo['currentDomain'] = data.domain;
		ObjTenantInfo['parentDomain'] = data.parentDomain;
		ObjTenantInfo['tenantName'] = data.tenantName;
		ObjTenantInfo['type'] = "verify";
		$('#subTenant').val(JSON.stringify(ObjTenantInfo));
		$("#sub_tenant_view").submit();

	});
}

function FnClientConfirmNo(){
	$("#gapp-tenant-info").val('');
	$("#clientConfirmation").modal('hide');
}

function FnGridChangeEvent(){
	var clientlist = $("#clients-list").data("kendoGrid");

	if(clientlist.select().length > 0){
		$('#createadmin-btn').attr('disabled',false);
	} else {
		$('#createadmin-btn').attr('disabled',true);
	}
}

function FnCreateClient(){
	$('#tenant_create').submit();

}

function FnCreateClientAdmin(){
	var ObjIdentifier = FnGetSelectedIdentifier();
	if(ObjIdentifier == null || ObjIdentifier === "undefined" ){
		return;
	}
	var data = ObjIdentifier;	
	$('#tenant_send_email').val(JSON.stringify(data));
	$("#tenant_send_email_view").submit();	
	console.log('Clients:FnCreateClientAdmin');
}

function FnGetSelectedIdentifier(){
	var clientlist = $("#clients-list").data("kendoGrid");
	var VarSelectedItem = clientlist.dataItem(clientlist.select());

	if(VarSelectedItem == null || VarSelectedItem === "undefined" ){
		$('#createAdminNotification').modal('show');
		return;
	}	
	return VarSelectedItem;
}

function FnClientConfirmNo(){
	$("#gapp-tenant-info").val('');
	$("#clientConfirmation").modal('hide');
}

function FnClientConfirmYes(){
	var data = FnGetSelectedIdentifier();
	var ObjTenantInfo = {};
	ObjTenantInfo['tenantId'] = data.tenantId;
	ObjTenantInfo['tenantDomain'] = data.domain;
	ObjTenantInfo['parentDomain'] = data.parentDomain;
	ObjTenantInfo['tenantName'] = data.tenantName;
	ObjTenantInfo['type'] = "add";
	//TODO show a popup here "moving to "tenant name""
	
	$('#subTenant').val(JSON.stringify(ObjTenantInfo));
	$("#sub_tenant_view").submit();
}