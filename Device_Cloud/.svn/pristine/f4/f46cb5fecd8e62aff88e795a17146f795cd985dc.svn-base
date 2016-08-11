/**
 * configtemplate.js 7/13/2015 - PCSEG339 Kenny Chavez
 */
//setHeader();
$(document).on('click', '#configTemplateGrid .k-i-refresh', function() {
	$("#configTemplateGrid").data('kendoGrid').dataSource.data([]);
	$("#configTemplateGrid").data('kendoGrid').dataSource.data(data);
	// getAllLogData();
});


// global variables

var mapParameterEdit=""; 

var grid = $("#configTemplateGrid")
		.kendoGrid(
				{
					 dataSource: {
						 pageSize : 20,
	                	 type: "json",
	                	 transport: {
	                		 read: function(options) {
	                			
	                			 var targetUrl = getAllTemplatesUrl;
	                			 targetUrl = targetUrl.replace("{sub_id}",1);
	                			 //FIX ME
		                			var payload = 
		                					{  
//		                						"make": "Teltonica",
//											    "deviceType": "Fleet Management",
//											    "model": "FM5300",
//											    "protocol": "FM5300",
//											    "version": "1.0.0"
		                					}; 

		                			$.ajax({
	                					url : "ajax" + targetUrl,
	                					type : 'POST',
	                					dataType : 'JSON',
	                					contentType : "application/json; charset=utf-8",	                					
	                					data : JSON.stringify(payload), // FIX ME

	                					success : function(response) {
	                						//console.log(response.entity);
	                						mapParameterEdit = response.entity[0].name;
	                						
	                						return options.success(response.entity);
	                					
	                					},
	                					error : function(xhr, status, error) {
	                						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
	                						staticNotification.show({
	                							message: errorMessage
	                						}, "error");
	                					}
	                				});	                			 
	                			 
	                		    } 
	                	 },	                
	                	 schema : {
								model : {
								id : "name",
								fields : {
									name : {
										editable : false,
										nullable : false
									},
									deviceType : {
										editable : false,
										nullable : false
									},
//									make : {
//										editable : false,
//										nullable : false
//									},
									deviceModel : {
										editable : false,
										nullable : false
									},
									deviceProtocol : {
										editable : false,
										nullable : false
									},
									deviceProtocolVersion : {
										editable : false,
										nullable : false
									}
								}
							}
	    					}
	                 },
					toolbar : kendo.template($("#configSearchTemplate").html()),
					height : 500,
					sortable : true,
					pageable : {
						refresh : true,
						pageSizes : true,
						previousNext : true
					},
					editable : {
						confirmation : false,
						createAt : "bottom"
					},
					scrollable : true,
					columns : [
							{
								field : "name",
								title : "Template Name",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Configuration Template<strong>"
							},
							{
								field : "deviceType",
								title : "Device Type",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Device Type<strong>"
							},
//							{
//								field : "make",
//								title : "Priority",
//								width : 50,
//								editable : false,
//								headerTemplate : "<strong style='color:black ;' >Make<strong>"
//							},
							{
								field : "deviceModel",
								title : "Device Model",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Model<strong>"
							},
							{
								field : "deviceProtocol",
								title : "Device Protocol",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Protocol<strong>"
							},
							{
								field : "deviceProtocolVersion",
								title : "Protocol Version",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Protocol Version<strong>"
							},
							{
								command : [ {
									name : "edit-param",
									text : " ",
									//click : editParameters,
									click : function (e){
										
										var thisGrid = $("#configTemplateGrid").getKendoGrid();  
					                    var item = thisGrid.dataItem($(e.target).closest("tr"));
					                    
					                    mapParameterEdit = item.name;
					                    
										loadPage("mapParameterEdit");
										$("#panelHeaderTitle").text("Edit Parameter Mapping");
										
									},
									
									imageClass : "glyphicon glyphicon-th-list"
								}, {
									name : "Deactivate",
									text : "Deactivate",
									 click: function(e){  //add a click event listener on the delete button
										 
										 var tr = $(e.target).closest("tr"); //get the row for deletion
										 var data = this.dataItem(tr); //get the row data so it can be referred later
										 
										 var kendoWindow = $("<div />").kendoWindow({
											    title: "Confirm",
											    resizable: false,
											    modal: true,
											    deactivate: function() {
											        this.destroy();                                           
											    }
											});
										 
										 kendoWindow.data("kendoWindow")
										    .content($("#deleteTemplateConfirm").html())
										    .center().open(); 
										 
										// for dialog delete confirmation
										$("#yesButton").click(function(){
											 
				                			 var targetUrl = deactivateTemplateUrl;
				                			 targetUrl = targetUrl.replace("{sub_id}",1);				                			 
				                			 var payload="[\""+data.name+"\"]";
				                			 
											$.ajax({
			                					url : "ajax" + targetUrl,
			                					type : 'PUT',
			                					contentType : "application/json",	                					
			                					data : payload, 
			                					success : function(response) {
			                						
			                						staticNotification.show({
			                							message: "'"+data.name+"' deactivated successfully."
			                						}, "success");
			                						
			                						kendoWindow.data("kendoWindow").close();
			                					},
			                					error : function(xhr, status, error) {
			                						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
			                						staticNotification.show({
			                							message: errorMessage
			                						}, "error");
			                					}
			                				});
											
										});

										$("#noButton").click(function(){
											kendoWindow.data("kendoWindow").close();
										});	
					                    }
								} ],
								title : "Error Message",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Action<strong>"
							} ]
				});

$("#searchTemplate").keyup(function() {
	var val = $("#searchTemplate").val();
	$("#configTemplateGrid").data("kendoGrid").dataSource.filter({
		logic : "or",
		filters : [ {
			field : "name",
			operator : "contains",
			value : val
		}, {
			field : "deviceType",
			operator : "contains",
			value : val
		}, {
			field : "deviceMake",
			operator : "contains",
			value : val
		}, {
			field : "deviceModel",
			operator : "contains",
			value : val
		}, {
			field : "deviceProtocol",
			operator : "contains",
			value : val
		}, {
			field : "deviceProtocolVersion",
			operator : "contains",
			value : val
		}, {
			field : "action",
			operator : "contains",
			value : val
		} ]
	});
});

$("#createConfigTemplate").on('click', function() {
	// var button = {
	// "text" : "CREATE CONFIG TEMPLATE",
	// "id" : "createConfigTemplate",
	// "url" : "createConfigTemplate"
	// };
	// addNewTab(button);
	loadPage("createConfigTemplate");
	$("#panelHeaderTitle").text("Create New Template Configuration");
});

$("#createParamConfiguration").on('click', function() {
	// var button = {
	// "text" : "PARAMETER CONFIGURATION",
	// "id" : "parameterConfiguration",
	// "url" : "parameterConfiguration"
	// };
	// addNewTab(button);
	loadPage("parameterConfiguration");
	$("#panelHeaderTitle").text("Create New Parameter Configuration");
});

function editParameters() {
	// var button = {
	// "text" : "MAP PARAMETER EDIT",
	// "id" : "mapParameterEdit",
	// "url" : "mapParameterEdit"
	// };
	// addNewTab(button);
	
	//console.log("PARAMETER is "+mapParameterEdit);
	var dataItem = $("#writebackgrid"+sourceId).data("kendoGrid").dataSource.get(pointId);
	
	loadPage("mapParameterEdit");
	$("#panelHeaderTitle").text("Edit Parameter Mapping");

}

