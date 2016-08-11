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
		var grid = $("#rolegrid").data("kendoGrid");
		var toolbar = $("#createactionsbar").data("kendoToolBar");
		if (grid.select().length == 1) {
			var data = getSelectedIdentifier().roleName;
			if(data === "admin"){
				toolbar.enable("#gx-btn-view", false);
				toolbar.enable("#gx-btn-update", false);
			}
			else{
				toolbar.enable("#gx-btn-view", true);
				toolbar.enable("#gx-btn-update", true);
			}
		} else {
			toolbar.enable("#gx-btn-view", false);
			toolbar.enable("#gx-btn-update", false);
		}
	}

	$(document)
			.ready(
					function() {

						payload = {};
						var entityTemplatesChecker = {};
						var entityTemplates = [];

						var grid = $("#rolegrid")
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
														url : "role_home",
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
															console.log(response.entity);
															return response.entity;
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
															roleName : {
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
												field : "roleName",
												title : "Role Name",
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
														click : FnRoleCreate
													},
													{
														id : "gx-btn-view",
														type : "button",
														text : "View",
														enable : false,
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnRoleView
													},
													{
														id : "gx-btn-update",
														type : "button",
														text : "Update",
														enable : false,
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnRoleUpdate
													},
													{
														id : "gx-btn-back",
														type : "button",
														text : "Back",
														attributes : {
															"class" : "button text-shadow bg-blue large-button fg-white"
														},
														click : FnUsers
													}  ]
										});

					});

	function FnRoleUpdate() {
		var identifier = getSelectedIdentifier();
		if (identifier == null || identifier === "undefined") {
			return;
		}
		var data = identifier.roleName;
		$("#update_value").val(data);
		$("#roleupdate").submit();
	}

	function FnRoleCreate() {
		$("#rolecreate").submit();

	}

	function FnRoleView() {
		var identifier = getSelectedIdentifier();
		if (identifier == null || identifier === "undefined") {
			return;
		}
		var data = identifier.roleName;
		$("#view_value").val(data);
		$("#roleview").submit();
	}

	function getSelectedIdentifier() {
		var grid = $("#rolegrid").data("kendoGrid");
		var selectedItem = grid.dataItem(grid.select());
		if (selectedItem == null || selectedItem === "undefined") {
			alert("Please select a row");
			return;
		}
		return selectedItem;
	}

	function FnRoleDelete() {
		var identifier = getSelectedIdentifier();
		if (identifier == null || identifier === "undefined") {
			return;
		}
		var data = identifier.roleName;
		$("#delete_value").val(data);
		$("#roledelete").submit();
	}
	function FnUsers() {
		$("#users").submit();
	}

	
</script>
<div id="createactionsbar" class="gx-toolbar-action"></div>
<div id="rolegrid"></div>

<form id="roleview" role="form" action="role_view" name="roleview"
	method="post">
	<input class="form-control" type="hidden" id="view_value" name="value" />
</form>
<form id="roledelete" role="form" action="role_delete" name="roledelete"
	method="post">
	<input class="form-control" type="hidden" id="delete_value" name="value" />
</form>
<form id="roleupdate" role="form" action="role_update"
	name="roleupdate" method="post">
	<input class="form-control" type="hidden" id="update_value"
		name="value" />
</form>
<form id="rolecreate" role="form" action="role_create" name="create"
	method="get"></form>
<form id="users" role="form" action="user_home" name="users" method="GET">
</form>