$(document).ready(function(){
//FnInitializeScheduleList()
})

$(window).load(function(){
	FnGetServiceScheduleList(serviceScheduleListResp);
});


function FnInitializeScheduleList(){
	//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
	//var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ViewLink = ('1') ? "<a href='Javascript:void(0)' class='grid-view-schedule capitalize' data-toggle='tooltip' title='View #=serviceScheduleName# details'>#=serviceScheduleName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=serviceScheduleName#</a>";
	var ArrColumns = [{field: "serviceScheduleName",title: "Schedule Name",width: 130,template: ViewLink },/*{field: "component",title: "Component",width: 130},*/{field: "occuranceType",title: "Occurrance Type",width: 130},{field: "description",title: "Description",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#schedule-list',[],ArrColumns,ObjGridConfig);
} 

function FnGetGridColumns(){
	
	var ViewLink = (UserInfo.hasPermission('service_scheduling','view')) ? "<a href='Javascript:void(0)' class='grid-view-schedule capitalize' data-toggle='tooltip' title='View #=serviceScheduleName# details'>#=serviceScheduleName#</a>" : "<a href='Javascript:void(0)' class='capitalize'>#=serviceScheduleName#</a>";
	
	var ArrColumns = [
	                  {field: "serviceScheduleName",title: "Schedule Name",template: ViewLink },
	                  /*{field: "component",title: "Component",width: 130},*/
	                  {field: "occuranceType",title: "Occurrance Type"},
	                  {field: "description",title: "Description"}
	                 ];
	
	return ArrColumns;
} 
 
function FnGetServiceScheduleList(response){
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
	objDatasource['sort']={field: "serviceScheduleName", dir: "asc"}
	
	var grid = $("#serviceitem-list").data("kendoGrid");
	if(grid != undefined){ grid.destroy(); $('#schedule-list').html(''); }
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#schedule-list',objDatasource,ArrGridColumns,ObjGridConfig);
		
	$("#schedule-list").data("kendoGrid").tbody.on("click", ".grid-view-schedule", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#schedule-list").data('kendoGrid').dataItem(tr);
		
		$('#selected_schedule').val(JSON.stringify(data));
		console.log("view click"+JSON.stringify(data));
		$("#schedule_view").submit();
	});
}


function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
