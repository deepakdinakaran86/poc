<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.form-group {
	display: inline-block;
}
</style>

<script type="text/javascript">

function onChange(arg) {
	var grid = $("#commutergrid").data("kendoGrid");
	var toolbar = $("#createactionsbar").data("kendoToolBar");
	if(grid.select().length == 1){
		toolbar.enable("#gx-btn-view",true);
		toolbar.enable("#gx-btn-update",true);
		toolbar.enable("#gx-btn-history",true);
		toolbar.enable("#gx-btn-live",true);
	}
	else{
	    toolbar.enable("#gx-btn-view",false);
	    toolbar.enable("#gx-btn-update",false);
	    toolbar.enable("#gx-btn-history",false);
	    toolbar.enable("#gx-btn-live",true);
	}
}

	$(document)
			.ready(
					function() {

						payload = {};
						var entityTemplatesChecker = {};
						var entityTemplates = [];

						var grid = $("#commutergrid")
								.kendoGrid(
										{
											resizable : true,
											dataSource : {
												type : "json",
												transport : {
													read : {
														type : "POST",
														dataType : "json",
														contentType : "application/json",
														url : "commuter_home",
														data : payload
													},
													parameterMap : function(
															options, operation) {
														var data = JSON
																.stringify(options);
														return data;
													}
												},
												// dataBound: onDataBound,
												error : function(e) {

												},
												schema : {

													data : function(response) {

														if (response.entity != null) {
															var responseData = [];
															$(response.entity)
																	.each(
																			function(
																					index) {
																				var element = {};
																				$(
																						this.dataprovider)
																						.each(
																								function() {
																									var key = this.key;
																									element[key] = this.value;
																								});
																				element["identifier"] = this.identifier.value;
																				responseData
																						.push(element);
																			});

															return responseData;
														} else {

															$('#errorContainer')
																	.css(
																			{
																				"color" : "#FF0000",
																				"background-color" : "#FFEBE6",
																				"border-color" : "#FF8566"
																			});
														}
													},
													errors : function(response) {
														return response.error;
													},
													total : function(response) {
														return $(response.entity).length;
													},
													model : {
														fields : {
															assetType : {
																type : "string"
															},
															engineModel : {
																type : "string"
															},
															entityName : {
																type : "string"
															},
															esn : {
																type : "string"
															},
															device : {
																type : "string"
															}
														}
													}
												},
												pageSize : 10,
												page : 1,
												serverPaging : false,
												serverFiltering : false,
												serverSorting : false
											},
											selectable : "multiple",
											height : 620,
											change: onChange,
											filterable : {
												mode : "row"
											},
											sortable : {
												mode : "single",
												allowUnsort : true
											},
											pageable : {
												refresh : true,
												pageSizes : true,
												previousNext : true
											},
											columnMenu : false,

											columns : [ {
												field : "entityName",
												title : "Entity Name",
											}, {
												field : "assetType",
												title : "Asset Type",
											}, {
												field : "engineModel",
												title : "Engine Model",
											}, {
												field : "esn",
												title : "ESN",
											}, {
												field : "device",
												title : "Device",
											} ]
										}).data("kendoGrid");

						$("#createactionsbar")
								.kendoToolBar(
										{
											resizable : false,
											items : [
													{
														id : "gx-btn-create",
														type : "button",
														text : "Create",
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnEntityCreate
													},
													{
														id : "gx-btn-view",
														type : "button",
														text : "View",
														enable: false,
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnEntityView
													},
													{
														id : "gx-btn-update",
														type : "button",
														text : "Update",
														enable: false,
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnEntityUpdate
													},
													{
										                 id: "gx-btn-live",
										                 type: "button",
										                 text: "Live",
										                 enable: false,
										                 attributes: {
										                     "class": "button text-shadow bg-blue large-button fg-white"
										                 },    
										                 click: FnLiveView
										            },
													{
														id : "gx-btn-history",
														type : "button",
														text : "History",
														enable: false,
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnHistoryView
													} ]
										});

					});

	function FnEntityUpdate() {
		var identifier = getSelectedIdentifier();
		if (identifier == null || identifier === "undefined") {
			return;
		}
		var data = identifier.identifier;
		$("#update_value").val(data);
		$("#commuterupdate").submit();
	}

	function FnEntityCreate() {
		$("#commutercreate").submit();

	}

	function FnEntityView() {
		var identifier = getSelectedIdentifier();
		if (identifier == null || identifier === "undefined") {
			return;
		}
		var data = identifier.identifier;
		$("#view_value").val(data);
		$("#commuterview").submit();
	}

	function getSelectedIdentifier() {
		var grid = $("#commutergrid").data("kendoGrid");
		var selectedItem = grid.dataItem(grid.select());
		if (selectedItem == null || selectedItem === "undefined") {
			alert("Please select a row");
			return;
		}
		return selectedItem;
	}

	function FnHistoryView() {
		var selectedItem = getSelectedIdentifier();
		if (selectedItem == null || selectedItem === "undefined") {
			return;
		}
		var data = selectedItem.device;
		if (data == null || data === undefined || data === '') {
			initNotifications();
		 staticNotification.show({
			 message:"Device is not attached"
		 }, "warning");
			return;
		}
		$("#device_value").val(data);
		$("#historyTracking").submit();

	}

	function FnLiveView(){
   		var grid = $("#commutergrid").data("kendoGrid");
   		var selectedItems = grid.dataItems(grid.select());
		var assetList = [];
		var rows = grid.select();
		var record;
			rows.each(function() {
		record = grid.dataItem($(this));
		var selectedItem = {
			asset : record.entityName,
			sourceId : record.device
		};
	});
	assetList.push({'sourceId': record.device, 'asset': record.entityName});
	$("#live_value").val(JSON.stringify(assetList));
	$("#liveForm").submit();
}
initNotifications();
var response = '${response}';
if(response != ''){
var type = "success";
	if(response != "SUCCESS"){
		type = "error";
	}
	 staticNotification.show({
		 message:response
	 }, type);
}	
</script>
<div id="createactionsbar" class="gx-toolbar-action"></div>
<div id="commutergrid"></div>

<form id="commuterview" role="form" action="commuter_view"
	name="commuterview" method="post">
	<input class="form-control" type="hidden" id="view_value" name="value" />
</form>
<form id="commuterupdate" role="form" action="commuter_update"
	name="commuterupdate" method="post">
	<input class="form-control" type="hidden" id="update_value"
		name="value" />
</form>
<form id="commutercreate" role="form" action="commuter_create"
	name="create" method="get"></form>
<form id="historyTracking" role="form" action="historytracking"
	name="history" method="get">
	<input class="form-control" type="hidden" id="device_value"
		name="value" />
		<input class="form-control" type="hidden" id="live_value" name="key" value="commuter_home" />
</form>

<form id="liveForm" role="form" action="live_tracking" name="liveForm"
	method="get">
	<input class="form-control" type="hidden" id="live_value" name="value" />
	<input class="form-control" type="hidden" id="live_value" name="key" value="commuter_home" />
</form>