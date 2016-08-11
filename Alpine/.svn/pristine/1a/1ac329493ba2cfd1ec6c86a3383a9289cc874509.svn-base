var historyGridData = {};
var physicalQuantity = {};

function historyDevice(button, selectedItem) {
	datasourceName = selectedItem.datasourceName;
	$("#deviceName").text(datasourceName);
}

function getTodayDate() {
	var today = {
		value : new Date(),
		min : new Date(1950, 0, 1),
		max : new Date(2049, 11, 31)
	};
	return today;
}

function getTomorrowDate() {
	var date = new Date();
	date.setDate(date.getDate() + 1);
	var tomorrow = {
		value : date,
		min : new Date(1950, 0, 1),
		max : date,
		footer : "Today - #=kendo.toString(data, 'd') #"
	};
	return tomorrow;
}

function fetchHistoryData(startDate, endDate,param,sourceID,pq) {
	var startMillis = convertTimeToUtc(startDate);
	var endMillis = convertTimeToUtc(endDate);

	var payload = buildJSONDevHistory(startMillis,endMillis,param,sourceID);
	var url = "ajax/" + window.historyUrl.replace("{pq}", pq);
	
	 var progressBar = $("#deviceDetails");
	  kendo.ui.progress(progressBar, true);
	$
	.ajax({
		url : url,
		data : JSON.stringify(payload),
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		type : "POST",
		success : function(response) {
			if (response.entity != null) {
				createHistGrid(response.entity);
			}
			  kendo.ui.progress(progressBar, false);
		},
		error : function(xhr, status, error) {
			staticNotification.show({
                message:jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage
            }, "error");
			  kendo.ui.progress(progressBar, false);
		},
		
	});
	
	
}

function buildJSONDevHistory(startMillis,endMillis,param,sourceID) {
	return {
		"sourceId" : sourceID,
		"startDate" : startMillis,
		"endDate" : endMillis,
		"customTags" : [  param ]
	};
}

function convertTimeToUtc(localDate) {
	var dateToConvert = new Date(localDate);
	var month = dateToConvert.getMonth();
	var day = dateToConvert.getDate();
	var year = dateToConvert.getFullYear();
	return Date.UTC(year, month, day);
}

function getDataSorceParameters(sourceID) {
	
	 $("#parameterHist").kendoDropDownList({
		  dataTextField: "pointName",
		  dataValueField: "pointName",
		  dataSource: {
		    data: ""
		  }
	 });
	 
	var url = "ajax/" + window.paramUrl.replace("{source_id}", sourceID);
	if (sourceID) {
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'GET',
			contentType : "application/json",
			success : function(response) {
				parameterArray = response.entity;
				param = parameterArray.configPoints;
				$("#parameterHist").data("kendoDropDownList").dataSource.data(param); 
				for (var i = 0; i < param.length; i++) {
					var obj = param[i];
					physicalQuantity[obj.pointName] = obj.phyQtyName;
				}
				$('#historysubmit').removeAttr('disabled');
			},
			error : function(xhr, status, error) {
				staticNotification.show({
	                message: "No Parameters found."
	            }, "error");
			}
		});
	}
}

function loadHistoryGrid() {

	$("#historyGrid")
			.kendoGrid(
					{
						dataSource : {
							type : "json",
							transport : {
								read : {
								}
							},
							pageSize : 10,
							page : 1,
							serverPaging : false,
							serverFiltering : false,
							serverSorting : false,
							sort : {
								field : "deviceTime",
								dir : "asc"
							}
						},
						selectable : "single",
						height : 540,
						sortable : {
							mode : "single",
							allowUnsort : true
						},
						pageable : {
							refresh : false,
							pageSizes : true,
							previousNext : true
						},
						columnMenu : false,

						columns : [
								{
									field : "deviceTime",
									title : "Time",
									template : "#: toDateFormat(deviceTime, 'F')#",
							        headerTemplate :"<strong style='color:black ;' >Time<strong>"
								}, {
									field : "data",
									title : "Data",
									template : "#: data # ",
							        headerTemplate :"<strong style='color:black ;' >Data<strong>"
								}, {
									field : "extensions",
									title : "Extensions",
									template :'<u><div style="cursor:pointer" onclick="histLabelClick(#:kendo.stringify(extensions)#)" title="#: JSON.stringify(extensions) #">' +
										'#: extensions[0].extensionType # : #: extensions[0].extensionName #...</div></u>',
								    headerTemplate :"<strong style='color:black ;' >Extensions<strong>"
								} ]
					}).data("kendoGrid");

}
function toDateFormat(time, format) {
	
	var date = new Date(Number(time));
	console.log(date.toUTCString());
	return date.toUTCString();
}

function refreshHistGrid(value){
	var gridData = historyGridData[value];
	$("#historyGrid").data("kendoGrid").dataSource.data(gridData); 
	$("#historyGrid").data("kendoGrid").dataSource.page(1);
}

var onWindoClose = function() {
	//$("#extwindow").hide();
};

function histLabelClick(extension){
	
	if (!$("#extwindow").data("kendoWindow")) {
		
		$("#extwindow").kendoWindow({
			width: "600px",
			position: {
			    top: "20%", // or "100px"
			    left: "25%"
			  },
			  draggable: false,
			  modal: true,
			  resizable: false,
			title: "Extensions",
			actions: [
			          "Close"
			          ],
                      close: onWindoClose
		});
	}
	else{
		$("#extwindow").data("kendoWindow").open();
	}
	
	 $("#extgrid").kendoGrid({
         dataSource: {
             data: extension,
             schema: {
                 model: {
                     fields: {
                    	 extensionType: { type: "string" },
                    	 extensionName: { type: "string" }
                     }
                 }
             },
             pageSize: 20
         },
         height: 500,
         scrollable: true,
         sortable: true,
         filterable: false,
	     columns : [ {
			field : "extensionType",
			title : "Extension Type",
		    headerTemplate :"<strong style='color:black ;' >Extension Type<strong>"
		 }, {
			field : "extensionName",
			title : "Extension Name",
		    headerTemplate :"<strong style='color:black ;' >Extension Name<strong>"
		 } ]
     });
}

function createHistGrid(arg) {
	$("#historydate").html('');
	$("#historydatediv").show();
	var dataArray = [];
	for (var i = 0; i < arg.customTags[0].dates.length; i++) {
		var obj = arg.customTags[0].dates[i];
		historyGridData[obj.date] = obj.values;
		dataArray.push({"date":obj.date,"textdate":toDateFormat(obj.date,"D")});
	}
	$("#historydate").kendoDropDownList({
		  dataTextField: "textdate",
		  dataValueField: "date",
		  dataSource: {
		    data: dataArray
		  },
		  change: function (e) {
			  refreshHistGrid(this.value());
		  }
		});
	$("#historyGrid").data("kendoGrid").dataSource.data(arg.customTags[0].dates[0].values); 
	$("#historyGrid").data("kendoGrid").dataSource.page(1);
}