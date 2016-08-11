$(document).ready(function() {
	FnInitializeVehicleTypeList();
});

$(window).load(function() {
	FnGetVehicleTypesList();
});


function FnInitializeVehicleTypeList() {
	var ViewLink = "<a class='grid-tag-type-view capitalize'>#=vehicleType#</a>";
	var ArrColumns = [ {
		field : "assetType",
		title : "Vehicle Type",
	    template: ViewLink
	} ];

	var ObjGridConfig = {
		"scrollable" : true,
		"filterable" : {
			mode : "row"
		},
		"sortable" : true,
		"pageable" : {
			pageSize : 10,
			numeric : true,
			pageSizes : true,
			refresh : false
		},
		"selectable" : true
	};
	FnDrawGridView('#vehicle-type-list', [], ArrColumns, ObjGridConfig);
}

function FnGetVehicleTypesList(){
	$("#GBL_loading").show();
	var VarUrl = "ajax" + getVehicleTypeList + "?domain_name=" +  UserInfo.getCurrentDomain()+"&parent_type="+vehicleTemplate;
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResVehicleTypesList);
}

function FnResVehicleTypesList(response){
	$("#GBL_loading").hide();
	var ArrResponse = response.entity;
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		var ArrResponseData = [];
		if(VarResLength >0){
			ArrResponseData = ArrResponse;
		}
	} else {
		var ArrResponseData = [];
	}		
	var ViewLink = (UserInfo.hasPermission('vehicle_types','view')) ?"<a class='grid-tag-type-view capitalize'>#=assetType#</a>" : "<a>#=assetType#</a>";
	var ArrColumns = [
						{
							field: "assetType",
							title: "Vehicle Type",
							template: ViewLink
						}
					];
		
	var ObjGridConfig = {
		"scrollable" : true,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var objDatasource = {};
	objDatasource['data']=ArrResponseData;
	objDatasource['sort']={field: "assetType", dir: "asc"}
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#vehicle-type-list',objDatasource,ArrColumns,ObjGridConfig);
	
	$("#vehicle-type-list").data("kendoGrid").tbody.on("click", ".grid-tag-type-view", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#vehicle-type-list").data('kendoGrid').dataItem(tr);
		data = data.assetType;
		$("#vehicle_type_name").val(data);
		$("#vehicle_type_view").submit();
	});
}

function FnCreateVehicleType() {
	$("#vehicle_type_create").submit();
}