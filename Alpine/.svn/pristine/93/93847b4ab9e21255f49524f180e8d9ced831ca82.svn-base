<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.form-group {
	display: inline-block;
}
</style>

<script type="text/javascript">
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
	function onChange(arg) {
		var grid = $("#resourcegrid").data("kendoGrid");
		var toolbar = $("#createactionsbar").data("kendoToolBar");
		if (grid.select().length == 1) {
			toolbar.enable("#gx-btn-view", true);
		} else {
			toolbar.enable("#gx-btn-view", false);
		}
	}

	$(document)
			.ready(
					function() {

						payload = {};
						var entityTemplatesChecker = {};
						var entityTemplates = [];

						var grid = $("#resourcegrid")
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
														url : "resource_home",
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
															$(response.entity.resources)
																	.each(
																			function(
																					index) {
																				var element = {};
																				element["resourceName"] = this;
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
															resourceName : {
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
											change : onChange,
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
												field : "resourceName",
												title : "Resouce Name",
											}]
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
														enable : false,
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnEntityView
													},
													{
														id : "gx-btn-back",
														type : "button",
														text : "Back",
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnUsers
													} ]
										});

					});

	function FnEntityCreate() {
		$("#resourcecreate").submit();

	}

	function FnEntityView() {
		var identifier = getSelectedIdentifier();
		console.log(identifier);
		console.log(identifier.resourceName);
		if (identifier == null || identifier === "undefined") {
			return;
		}
		var data = identifier.resourceName;
		$("#view_value").val(data);
		$("#resourceview").submit();
	}

	function getSelectedIdentifier() {
		var grid = $("#resourcegrid").data("kendoGrid");
		var selectedItem = grid.dataItem(grid.select());
		if (selectedItem == null || selectedItem === "undefined") {
			alert("Please select a row");
			return;
		}
		return selectedItem;
	}
	function FnUsers() {
		$("#users").submit();
	}

	
</script>
<div id="createactionsbar" class="gx-toolbar-action"></div>
<div id="resourcegrid"></div>

<form id="resourceview" role="form" action="resource_view" name="resourceview"
	method="post">
	<input class="form-control" type="hidden" id="view_value" name="value" />
</form>
<form id="resourceupdate" role="form" action="resource_update"
	name="resourceupdate" method="post">
	<input class="form-control" type="hidden" id="update_value"
		name="value" />
</form>
<form id="resourcecreate" role="form" action="resource_create" name="create"
	method="get"></form>
<form id="users" role="form" action="user_home" name="users" method="GET">
</form>