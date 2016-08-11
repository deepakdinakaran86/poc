$(document).ready(function(){
FnInitializeServiceList()
})

$(window).load(function(){
	FnGetServiceItemsList(serviceItemsListResp);
});


function FnInitializeServiceList(){
	//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
	var ViewLink = (UserInfo.hasPermission('service_items','list')) ? "<a href='Javascript:void(0)' class='grid-view-item capitalize' data-toggle='tooltip' title='View #=serviceItemName# details'>#=serviceItemName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=serviceItemName#</a>";
	//var ViewLink = (1) ? "<a href='Javascript:void(0)' class='grid-view-item capitalize' data-toggle='tooltip' title='View #=serviceItemName# details'>#=serviceItemName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=serviceItemName#</a>";
	var ArrColumns = [{field: "serviceItemName",title: "Service Item",width: 130,template: ViewLink },/*{field: "tags",title: "Tags",width: 130},*/{field: "description",title: "Description",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#serviceitem-list',[],ArrColumns,ObjGridConfig);
}

function FnGetGridColumns(){
	
	var ViewLink = (UserInfo.hasPermission('service_items','view')) ? "<a href='Javascript:void(0)' class='grid-view-item capitalize' data-toggle='tooltip' title='View #=serviceItemName# details'>#=serviceItemName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=serviceItemName#</a>";
	//var ViewLink = (1) ? "<a href='Javascript:void(0)' class='grid-view-item capitalize' data-toggle='tooltip' title='View #=serviceItemName# details'>#=serviceItemName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=serviceItemName#</a>";
	
	var ArrColumns = [
	                  {field: "serviceItemName",title: "Service Item",width: 130,template: ViewLink },
	                  /*{field: "tags",title: "Tags",width: 130},*/
	                  {field: "description",title: "Description",width: 130}
	                 ];
	
	return ArrColumns;
} 

function FnGetServiceItemsList(response){
	$("#GBL_loading").hide();
	var ArrResponse = response;
	var ArrGridColumns = FnGetGridColumns();
	if($.isArray(ArrResponse)){
		var ArrResponseData = [];

		$.each(ArrResponse,function(key,val){
			var element = {};
			$.each(val.dataprovider,function(key,val){ 
				console.log("key="+val.key+" Value="+val.value);
				element[val.key] = val.value;
			});
			
			element["identifier"] = val.identifier.value;
			element["domain"] = val.domain.domainName;
			ArrResponseData.push(element);
			
		});

		console.log(ArrResponseData);
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
	objDatasource['sort']={field: "serviceItemName", dir: "asc"}
	
	var grid = $("#serviceitem-list").data("kendoGrid");
	if(grid != undefined){ grid.destroy(); $('#serviceitem-list').html(''); }
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#serviceitem-list',objDatasource,ArrGridColumns,ObjGridConfig);
		
	$("#serviceitem-list").data("kendoGrid").tbody.on("click", ".grid-view-item", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#serviceitem-list").data('kendoGrid').dataItem(tr);
		
		$("#gapp-item-view #itemDomain").val(data.domain);
		$("#gapp-item-view #itemIdentifier").val(data.identifier);
		$("#gapp-item-view").submit();
	});
}
 
