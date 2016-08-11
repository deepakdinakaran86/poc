function FnserviceSchedulingGrid() {
	var ssData = new kendo.data.DataSource({
		data : [],
		pageSize : 10,
		page : 1,
		serverPaging : false,
		serverFiltering : false,
		serverSorting : false,
		sort : {
			field : "serviceScheduleName",
			dir : "desc"
		}
	});

	var grid = $("#serviceScheduling-grid").kendoGrid({
		autoBind : true,
		dataSource : ssData,
		pageable : {
			refresh : false,
			pageSizes : true,
			previousNext : true
		},
		height : 300,
		columns : [ {
			field : "serviceScheduleName",
			title : "Schedule Name"
		}, {
			field : "occuranceType",
			title : "Occurrance Type"
		}, {
			field : "description",
			title : "Description"
		}]
	});
}

/*function FnGetServiceScheduleList() {
	//Not used
	var VarUrl = '/FMS/ajax/galaxy-service-scheduling/1.0.0/schedule/list?domain_name={domain_name}';
	VarUrl = VarUrl.replace("{domain_name}", payload.currentDomain);
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json', 'json',FnResGetServiceScheduleList);
}

function FnResGetServiceScheduleList(response) {
	response = response.entity;
	var ArrResponse = response;
	if ($.isArray(ArrResponse)) {
		var grid = $("#serviceScheduling-grid").data("kendoGrid");
		$(ArrResponse).each(function(key, ssObj) {
			var element = {};
			
			$.each(ssObj.dataprovider,function(key,val){ 
				console.log("key="+val.key+" Value="+val.value);
				element[val.key] = val.value;
			});
			element["identifier"] = ssObj.identifier.value;
			element["domain"] = ssObj.domain.domainName;
			
			grid.dataSource.add(element);
			grid.dataSource.sync();
		});
	}
}*/