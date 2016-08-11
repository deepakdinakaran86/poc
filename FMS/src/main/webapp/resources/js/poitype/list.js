$(document).ready(function() {
	//FnInitializePOITypeList();
});

$(window).load(function() {
	FnGetPOITypesList();
});


function FnInitializePOITypeList() {
	var ViewLink = "<a class='grid-tag-type-view capitalize'>#=poiType#</a>";
	var ArrColumns = [ {
		field : "poiType",
		title : "POI Type",
	    template: ViewLink
	} ];

	var ObjGridConfig = {
		"scrollable" : true,
		"filterable" : {
			mode : "row"
		},
		"sortable" : true,
		"height":420,
		"pageable" : {
			pageSize : 10,
			numeric : true,
			pageSizes : true,
			refresh : false
		},
		"selectable" : true
	};
	FnDrawGridView('#poi-type-list', [], ArrColumns, ObjGridConfig);
}

function FnGetPOITypesList(){
	$("#GBL_loading").show();
	var VarUrl = "ajax" + getPOITypeList + "?domain_name=" + UserInfo.getCurrentDomain();
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResPOITypesList);
}

function FnResPOITypesList(response){
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
	var ViewLink = (UserInfo.hasPermission('poi_types','view')) ? "<a class='grid-tag-type-view capitalize'>#=poiType#</a>":"<a class='' >#=poiType#</a>";
	var ArrColumns = [
						{
							field: "poiType",
							title: "POI Type",
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
	objDatasource['sort']={field: "poiType", dir: "asc"}
	
	//$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#poi-type-list',objDatasource,ArrColumns,ObjGridConfig);
	
	$("#poi-type-list").data("kendoGrid").tbody.on("click", ".grid-tag-type-view", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#poi-type-list").data('kendoGrid').dataItem(tr);
		data = data.poiType;
		$("#poi_type_name").val(data);
		$("#poi_type_view").submit();
	});
}

function FnCreatePOIType() {
	$("#poi_type_create").submit();
}