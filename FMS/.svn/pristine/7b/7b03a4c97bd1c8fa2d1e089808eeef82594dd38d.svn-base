$(document).ready(function() {
	FnInitializeTagTypeList();
});

$(window).load(function() {
	FnGetTagTypesList();
});


function FnInitializeTagTypeList() {
	//var ViewLink = (UserInfo.hasPermission('tag_type','list')) ? "<a class='grid-tag-type-view capitalize'>#=entityTemplateName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=entityTemplateName#</a>";
	var ViewLink = "<a class='grid-tag-type-view capitalize'>#=entityTemplateName#</a>";
	var ArrColumns = [ {
		field : "entityTemplateName",
		title : "Tag Type",
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
	FnDrawGridView('#tag-list', [], ArrColumns, ObjGridConfig);
}

function FnGetTagTypesList(){
	$("#GBL_loading").show();
	var VarUrl = "ajax/" + GetTagTypesListURL;
	VarUrl = VarUrl.replace("{domain}",UserInfo.getCurrentDomain());
	//alert("in fngettagTypeslist");
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResTagTypesList);
}

function FnResTagTypesList(response){
	$("#GBL_loading").hide();
	var ArrResponse = response.entity;
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		var ArrResponseData = [];
		if(VarResLength >0){
			ArrResponseData = ArrResponse;
			//alert("arr response:"+ ArrResponseData);
		}
	} else {
		var ArrResponseData = [];
	}
	var ViewLink = (UserInfo.hasPermission('tag_type','view')) ? "<a href='Javascript:void(0)' class='grid-tag-type-view capitalize' data-toggle='tooltip' title='View #=entityTemplateName# details'>#=entityTemplateName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=entityTemplateName#</a>";

	var ArrColumns = [
						{
							field: "entityTemplateName",
							title: "Tag Type",
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
	objDatasource['sort']={field: "entityTemplateName", dir: "asc"}
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#tag-list',objDatasource,ArrColumns,ObjGridConfig);
	
	$("#tag-list").data("kendoGrid").tbody.on("click", ".grid-tag-type-view", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#tag-list").data('kendoGrid').dataItem(tr);
		data = data.entityTemplateName;
		//alert(data);
		$("#tag_type_name").val(data);
		$("#tag_type_view").submit();
	});
}

function FnCreateTagType() {
	window.location.href = "tagtype_create";
}