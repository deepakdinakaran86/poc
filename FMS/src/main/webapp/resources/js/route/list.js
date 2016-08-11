$(document).ready(function(){
FnInitializeRouteList()
})


function FnInitializeRouteList(){
	FnResRouteList(routelist);
} 

function FnNavigateCreateRoute(){
	$("#route_map").submit();
}



//fms-route-view
 
function FnResRouteList(routeList){
	var ArrResponse = routeList;
	var VarResLength = ArrResponse.length;
	var ArrResponseData = [];
	if(VarResLength > 0){
		$(ArrResponse).each(function(){
			var element = {};
			element["routeName"] = this.routeName;
			element["totalDistance"] = this.totalDistance;
			element["totalDuration"] = this.totalDuration;
			element["status"] = this.status;
			ArrResponseData.push(element);
		});
		
		var ViewLink = "<a href='Javascript:void(0)' class='grid-viewRoutes' style='text-transform: capitalize; cursor: pointer; cursor: hand;'>#=routeName#</a>";	
		var ArrColumns = [{field: "routeName",title: "Route Name",template: ViewLink,width: 130 },{field: "totalDistance",title: "Total Distance",width: 130},{field: "totalDuration",title: "Total Duration",width: 130},{field: "status",title: "Status",width: 130}];
		var ObjGridConfig = {
			"scrollable" : false,
			"filterable" : { mode : "row" },
			"sortable" : true,
			"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
			"selectable" : true
		};
		var objDatasource = {};
		objDatasource['data']=ArrResponseData;
		objDatasource['sort']={field: "routeName", dir: "asc"};
		
		$(".k-grid-content").mCustomScrollbar('destroy');
		FnDrawGridView('#route-list',objDatasource,ArrColumns,ObjGridConfig);
		
		
		$("#route-list").data("kendoGrid").tbody.on("click", ".grid-viewRoutes", function(e) {
			var tr = $(this).closest("tr");
			var data = $("#route-list").data('kendoGrid').dataItem(tr);
			var VarIdentifier = data.routeName;
			$("#route_name").val(VarIdentifier);
			$("#fms-route-view").submit();
		});
}
}