<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.form-group {
	display: inline-block;
}
</style>
<!-- <script src="resources/js/jquery.mask.min.js"></script> -->
<style>
</style>
<title></title>
<link rel="stylesheet"
	href="resources/kendo/css/kendo.common-material.min.css" />
<link rel="stylesheet"
	href="resources/kendo/css/kendo.material.min.css" />

<script src="resources/themes/default/js/jquery.min.js"></script>
   <script src="resources/kendo/scripts/kendo.all.min.js"></script> 

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
	var grid = $("#devicegrid").data("kendoGrid");
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

						var grid = $("#devicegrid")
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
														url : "device_home",
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
															datasourceName : {
																type : "string"
															},
															deviceType : {
																type : "string"
															},
															entityName : {
																type : "string"
															},
															networkProtocol : {
																type : "string"
															},
															protocolType : {
																type : "string"
															},
															sourceId : {
																type : "string"
															},
															tags : {
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
												field : "datasourceName",
												title : "Datasource Name",
											}, {
												field : "deviceType",
												title : "Device Type",
											}, {
												field : "entityName",
												title : "Entity Name",
											}, {
												field : "networkProtocol",
												title : "Network Protocol",
											},{
												field : "protocolType",
												title : "Protocol Type",
											},{
												field : "sourceId",
												title : "Source Id",
											},{
												field : "tags",
												title : "Tags",
											}]
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
         $("#deviceupdate").submit();
      }

     function FnEntityCreate(){
    	 $("#devicecreate").submit();
    	
     }

     function FnEntityView(){
    	 var identifier = getSelectedIdentifier();
    	 if(identifier == null || identifier === "undefined" ){
  			return;
          }
    	 var data = identifier.identifier;
         $( "#view_value").val(data);
    	 $("#deviceview").submit();
     }

     function getSelectedIdentifier(){
    	 var grid = $("#devicegrid").data("kendoGrid");
    	 var selectedItem = grid.dataItem(grid.select());
    	 if(selectedItem == null || selectedItem === "undefined" ){
			alert("Please select a row");
			return;
        }
    	 return selectedItem;
     }

</script>
<div id="createactionsbar" class="gx-toolbar-action"></div>
<div id="devicegrid"></div>


<form id="deviceview" role="form" action="device_view" name="deviceview" method="post">
<input class="form-control" type="hidden" id="view_value" name="value" />
</form>
<form id="deviceupdate" role="form" action="device_update" name="deviceupdate" method="get">
<input class="form-control" type="hidden" id="update_value" name="value" />
</form>
<form id="devicecreate" role="form" action="device_create" name="create" method="get">
</form>
<body>


</body>