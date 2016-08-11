<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">


 	var response = '${response}';
	
  	if (response != undefined && response != '') {
  		staticNotification.show({message: response}, "success");
 	}
 	
	$(document)
			.ready(
					function() {

						payload = {};
						var grid = $("#templategrid")
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
														url : "device_config",
														data : payload
													},
													parameterMap : function(
															options, operation) {
														var data = JSON
																.stringify(options);
														return data;
													}
												},
												error : function(e) {												
												},
												schema : {
													data : function(response) {
														if (response.entity != null) {
															return response.entity;
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
															name : {
																type : "string"
															},
															deviceMake : {
																type : "string"
															},
															deviceType : {
																type : "string"
															},
															deviceModel : {
																type : "string"
															},
															deviceProtocol : {
																type : "string"
															},
															deviceProtocolVersion : {
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
											height : 500,
											sortable : {
												mode : "single",
												allowUnsort : true
											},
											filterable : {
												mode : "row",
												cell: {
        											inputWidth: "100%"
    											 }
											},
											pageable : {
												refresh : true,
												pageSizes : true,
												previousNext : true
											},
											columnMenu : false,

											columns : [ {
												field : "name",
												title : "Template Name",
												template: "<a class='grid-viewuser'>#=name#</a>",
												width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Template Name<strong>"
											}, {
												field : "deviceMake",
												title : "Device Make",
												width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Device Make<strong>"
											}, {
												field : "deviceType",
												title : "Device Type",
												width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Device Type<strong>"
											}, {
												field : "deviceModel",
												title : "Device Model",
												width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Device Model<strong>"
											},{
												field : "deviceProtocol",
												title : "Device Protocol",
												width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Device Protocol<strong>"
											},{
												field : "deviceProtocolVersion",
												title : "Device Protocol Version",
												width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Device Protocol Version<strong>"
											}]
										}).data("kendoGrid");

						 $("#createactionsbar").kendoToolBar({
					         resizable:false,
					             items: [
					            {
// 					                 id: "gx-btn-view",
// 					                 type: "button",
// 					                 text: "View",
// 					                 attributes: {
// 					                     "class": "btn dc_btn_secondary glyphicon glyphicon-list-alt"
// 					                 },    
// 					                 click: FnTemplateView
// 					             },{
					                 id: "gx-btn-create",
					                 type: "button",
					                 text: "Add Template",
					                 attributes: {
					                     "class": "btn dc_btn_primary glyphicon glyphicon-plus"
					                 },
					                 click: FnTemplateCreate
					             }]
					         });
					         
					       
							$("#templategrid").data("kendoGrid").tbody.on("click",".grid-viewuser", function(e) {
									var tr = $(this).closest("tr");
									var data = $("#templategrid").data('kendoGrid').dataItem(tr);
									data = data.name;
									$("#view_value").val(data);
									$("#configtempview").submit();
								});

					});

	function FnTemplateCreate() {
		$("#devicecreate").submit();
	}

	function FnTemplateView() {
		var identifier = getSelectedIdentifier();
		if (identifier == null || identifier === "undefined") {
			return;
		}
		var data = identifier.name;
		$("#view_value").val(data);
		$("#configtempview").submit();
	}

	function getSelectedIdentifier() {
		var grid = $("#templategrid").data("kendoGrid");
		var selectedItem = grid.dataItem(grid.select());
		if (selectedItem == null || selectedItem === "undefined") {
			alert("Please select a row");
			return;
		}
		return selectedItem;
	}
</script>

<body>

	<div class="container2 theme-showcase dc_main" role="main" style="margin: 0px 10px;">
		<section class="dc_maincontainer animate-panel">
			<div class="page-header">
				<h1>Configuration Template</h1>
			</div>
			<div id="createactionsbar" class="gx-toolbar-action text-right"></div>
			<div id="templategrid"></div>
		</section>
	</div>
	
	<form id="configtempview" role="form" action="config_template_view" name="configTempView" method="post">
	<input class="form-control" type="hidden" id="view_value" name="name" />
	</form>
	<form id="deviceupdate" role="form" action="device_update" name="deviceupdate" method="get">
	<input class="form-control" type="hidden" id="update_value" name="value" />
	</form>
	<form id="devicecreate" role="form" action="config_template_create" name="create" method="get">
	</form>

</body>