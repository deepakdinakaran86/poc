$(document).ready(function(){
	FnInitializeComponentList()
})

$(window).load(function(){
	FnGetServiceComponentList();
});


function FnInitializeComponentList(){
	var ObjPageAccess = {'add':'1','list':'1','view':'1','edit':'1','sendemail':'1'};
	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewtenant' style='text-transform: capitalize;' data-toggle='tooltip' title='View #=serviceComponentName# details'  href='Javascript:void(0)'>#=serviceComponentName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=serviceComponentName#</a>";
	var ArrColumns = [{field: "serviceComponentName",title: "Component Name",template: ViewLink },
	                  {field: "serviceItem",title: "Service Item"},
	                  {field: "description",title: "Description"}];

	var ObjGridConfig = {
			"scrollable" : false,
			"filterable" : { mode : "row" },
			"sortable" : true,
			"height" : 320,
			"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
			"selectable" : true
	};
	FnDrawGridView('#component-list',[],[],ObjGridConfig);
} 


function FnGetDeviceTreeList(){
	console.log('Users:FnGetUsersList');
}

function FnGetServiceComponentList(){
	$("#GBL_loading").show();
	var VarUrl = "ajax/"+listServiceComponentUrl;
	VarUrl = VarUrl.replace("{domain}",UserInfo.getCurrentDomain());
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResServiceComponentList);
}

function FnResServiceComponentList(response){

	var ArrResponse = response.entity;
	var VarResLength = response.entity.length;
	var ArrResponseData = [];


	if(VarResLength > 0){
		$("#GBL_loading").hide();
		$(ArrResponse).each(function(){
			var element = {};
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
	//TODO change this
	var ObjPageAccess = {'add':'1','list':'1','view':'1','edit':'1','sendemail':'1'};

	var ViewLink =  (UserInfo.hasPermission('service_components','view')) ? "<a class='grid-viewtenant' style='text-transform: capitalize;' data-toggle='tooltip' title='View #=serviceComponentName# details'  href='Javascript:void(0)'>#=serviceComponentName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=serviceComponentName#</a>";
	var ArrColumns = [{field: "serviceComponentName",title: "Component Name",template: ViewLink },
	                  {field: "serviceItem",title: "Service Item"},
	                  {field: "description",title: "Description"}];
	var ObjGridConfig = {
			"scrollable" : false,
			"filterable" : { mode : "row" },
			"sortable" : true,
			"resizable":true,
			"height" : 460,
			"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
			"selectable" : true
	};

	var objDatasource = {};
	objDatasource['data']=ArrResponseData;
	objDatasource['sort']={field: "serviceComponentName", dir: "asc"}
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#component-list',objDatasource,ArrColumns,ObjGridConfig);

	$("#component-list").data("kendoGrid").tbody.on("click", ".grid-viewtenant", function(e) {

		var tr = $(this).closest("tr");
		var data = $("#component-list").data('kendoGrid').dataItem(tr);
		$('#selected_component').val(JSON.stringify(data));
		console.log("view click"+JSON.stringify(data));
		$("#component_view").submit();
	});

	$("#component-list").data("kendoGrid").tbody.on("click", ".grid-viewtenanthome", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#component-list").data('kendoGrid').dataItem(tr);
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

function FnGetSelectedIdentifier(){
	var componentlist = $("#component-list").data("kendoGrid");
	var VarSelectedItem = componentlist.dataItem(componentlist.select());
	return VarSelectedItem;
}


