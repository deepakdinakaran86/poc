"use strict";

$(document).ready(function() {
	$(".k-grid-content").css("overflow", "scroll").css("overflow-x", "auto").scroll(function() {
		var left = $(this).scrollLeft();
		var wrap = $(".k-grid-header-wrap");
		if (wrap.scrollLeft() != left) wrap.scrollLeft(left);
	});
});

$(window).load(function() {
	FnInitializeTagList();
	//FnGetTagTypes();
	FnResTagTypesList(tagTypesListResp);
	//FnGetTagList();
});


function FnInitializeTagList() {
	// var ViewLink = (ObjPageAccess['view']=='1') ? "<a
	// class='grid-tag-type-view capitalize'>#=entityTemplateName#</a>" : "<a
	// href='Javascript:void(0)' class='capitalize'>#=entityTemplateName#</a>";
	var ViewLink = "<a class='grid-tag-type-view capitalize'>#=entityTemplateName#</a>";
	var ArrGridColumns = FnGetGridColumns();

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
	FnDrawGridView('#tag-list', [], ArrGridColumns, ObjGridConfig);
}

function FnGetGridColumns(){
	
	var ViewLink = (UserInfo.hasPermission('tag','view')) ? "<a href='Javascript:void(0)' class='grid-tag-view capitalize' data-toggle='tooltip' title='View #=name# details'>#=name#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=name#</a>";
	var ArrColumns = [
						{
							field: "name",
							title: "Tag Name",
							template: ViewLink
						}
					];
					
	return ArrColumns;
}

//function FnGetTagTypes(){
//	$("#GBL_loading").show();
//	var VarUrl = "ajax/galaxy-tags/1.0.0/tagTypes?domain_name="+UserInfo.getCurrentDomain();
//	//VarUrl = VarUrl.replace("{domain}",UserInfo.getCurrentDomain());
//	//alert("in fngettagTypeslist");
//	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResTagTypesList);
//}

function FnResTagTypesList(response){
	$("#GBL_loading").hide();
	var ArrResponse = response;
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		var VarTagTypesTxt = '<option value=""> Select Tag Type </option>';
		$.each(ArrResponse,function(key,val){
			VarTagTypesTxt += '<option value="'+val.entityTemplateName+'">'+val.entityTemplateName+'</option>';
		});
		$('#tagCategory').html(VarTagTypesTxt);
	}
	
}

function FnGetTagList(VarTagType){
	//alert(VarTagType);
	$("#GBL_loading").show();
	if(VarTagType != undefined){
		if(VarTagType != ''){
			var VarUrl = 'ajax/' + GetTagListURL + '/' + VarTagType + '?domain_name=' + UserInfo.getCurrentDomain();
		}
	}
	//console.log('>> '+VarUrl);
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResTagList);
}

function FnResTagList(response){
	$("#GBL_loading").hide();
	var ArrResponse = response.entity;
	var ArrGridColumns = FnGetGridColumns();
	if($.isArray(ArrResponse)){
		var ArrResponseData = [];
		if($('#tagCategory').val() != ''){
			var i=0;
			var column_count = 0;
		
			$.each(ArrResponse,function(){
				var element = {};
				
				//alert('Tag Name : ' + this.name);
				//alert('Tag Type : ' + this.tagType);
				//alert('Domain Name : ' + this.domain.domainName);
				
				element['name'] = this.name;
				element['tagType'] = this.tagType;
				element['domain'] = this.domain.domainName;

				column_count++;
				ArrResponseData.push(element);
			});
			
			//console.log(ArrGridColumns);
			//console.log(ArrResponseData);
		} 
	} else {
		var ArrResponseData = [];
	}
	
	var ObjGridConfig = {
		"scrollable" : true,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var objDatasource = {};
	objDatasource['data']=ArrResponseData;
	objDatasource['sort']={field: "name", dir: "asc"}
	
	var grid = $("#gapp-tag-list").data("kendoGrid");
	if(grid != undefined){ grid.destroy(); $('#gapp-tag-list').html(''); }
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#gapp-tag-list',objDatasource,ArrGridColumns,ObjGridConfig);
		
	$("#gapp-tag-list").data("kendoGrid").tbody.on("click", ".grid-tag-view", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#gapp-tag-list").data('kendoGrid').dataItem(tr);
		
		$("#gapp-tag-view #tagName").val(data.name);
		$("#gapp-tag-view #tagType").val(data.tagType);
		$("#gapp-tag-view").submit();
	});
	
}

function FnCreateTag() {
	window.location.href = "tag_create";
}