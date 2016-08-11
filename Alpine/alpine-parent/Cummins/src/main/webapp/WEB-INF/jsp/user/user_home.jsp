<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.form-group {
	display: inline-block;
}
</style>
<!-- <script src="resources/js/jquery.mask.min.js"></script> -->
<style>
html {
	font-size: 14px;
	font-family: Arial, Helvetica, sans-serif;
}
</style>
<title></title>
<link rel="stylesheet"
	href="resources/kendo/css/kendo.common-material.min.css" />
<link rel="stylesheet"
	href="resources/kendo/css/kendo.material.min.css" />

<script src="resources/themes/default/js/jquery.min.js"></script>
<script src="resources/themes/default/js/kendo.all.min.js"></script>

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
	var grid = $("#usergrid").data("kendoGrid");
	var toolbar = $("#createactionsbar").data("kendoToolBar");
	if(grid.select().length == 1){
		toolbar.enable("#gx-btn-view",true);
		toolbar.enable("#gx-btn-update",true);
	}
	else{
		toolbar.enable("#gx-btn-view",false);
		toolbar.enable("#gx-btn-update",false);
		}
}
	$(document)
			.ready(
					function() {

						payload = {};
						var entityTemplatesChecker = {};
						var entityTemplates = [];

						var grid = $("#usergrid")
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
														url : "user_home",
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
															JSON
															.stringify(response.entity);
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
														}else {

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
															userName : {
																type : "string"
															},
															firstName : {
																type : "string"
															},
															lastName : {
																type : "string"
															},
															contactNumber : {
																type : "number"
															},
															emailId : {
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
											selectable : "single",
											height : 620,
											change: onChange,
											filterable: {
					                            mode: "row"
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
												field : "userName",
												title : "User Name",
											}, {
												field : "firstName",
												title : "First Name",
											}, {
												field : "lastName",
												title : "Last Name",
											}, {
												field : "contactNumber",
												title : "Contact Number",
											}, {
												field : "emailId",
												title : "Email ID",
											} ]
										}).data("kendoGrid");
						 $("#createactionsbar").kendoToolBar({
					         resizable:false,
					             items: [
					             {
					                 id: "gx-btn-create",
					                 type: "button",
					                 text: "Create",
					                 attributes: {
					                     "class": "button text-shadow bg-blue large-button fg-white"
					                 },
					                 click: FnEntityCreate
					             },{
					                 id: "gx-btn-view",
					                 type: "button",
					                 text: "View",
					                 enable: false,
					                 attributes: {
					                     "class": "button text-shadow bg-blue large-button fg-white"
					                 },    
					                 click: FnEntityView
					             },{
					                 id: "gx-btn-update",
					                 type: "button",
					                 text: "Update",
					                 enable: false,
					                 attributes: {
					                     "class": "button text-shadow bg-blue large-button fg-white"
					                 },    
					                 click: FnEntityUpdate
					             },
					             {
					                 type: "separator"
					             },
					             {
					                 type: "separator"
					             },
					              {
					                 id: "gx-btn-permission",
					                 type: "button",
					                 text: "Resources",
					                 attributes: {
					                     "class": "button text-shadow bg-blue large-button fg-white"
					                 },
					                 click: FnResources
					             },{
					                 id: "gx-btn-role",
					                 type: "button",
					                 text: "Role",
					                 attributes: {
					                     "class": "button text-shadow bg-blue large-button fg-white"
					                 },    
					                 click: FnRoles
					             }]
					         });
					});

	

     function FnEntityUpdate(){
         var identifier = getSelectedIdentifier();
         if(identifier == null || identifier === "undefined" ){
 			return;
         }
         var data = identifier.identifier;
         $( "#update_value").val(data);
         $("#userupdate").submit();
      }

     function FnEntityCreate(){
    	 $("#usercreate").submit();
    	
     }

     function FnEntityView(){
    	 var identifier = getSelectedIdentifier();
    	 if(identifier == null || identifier === "undefined" ){
  			return;
          }
    	 var data = identifier.identifier;
         $( "#view_value").val(data);
    	 $("#userview").submit();
     }

     function getSelectedIdentifier(){
    	 var grid = $("#usergrid").data("kendoGrid");
    	 var selectedItem = grid.dataItem(grid.select());
    	 if(selectedItem == null || selectedItem === "undefined" ){
			alert("Please select a row");
			return;
        }
    	 return selectedItem;
     }
     function FnResources(){
         $("#resources").submit();
      }
     function FnRoles(){
         $("#roles").submit();
      }

</script>
<div id="createactionsbar" class="gx-toolbar-action"></div>
<div id="usergrid"></div>

<form id="userview" role="form" action="user_view" name="userview" method="GET">
<input class="form-control" type="hidden" id="view_value" name="value" />
</form>
<form id="userupdate" role="form" action="user_update" name="userupdate" method="GET">
<input class="form-control" type="hidden" id="update_value" name="value" />
</form>
<form id="usercreate" role="form" action="user_create" name="usercreate" method="GET">
</form>
<form id="resources" role="form" action="resource_home" name="resources" method="GET">
</form>
<form id="roles" role="form" action="role_home" name="roles" method="GET">
</form>
<body>


</body>