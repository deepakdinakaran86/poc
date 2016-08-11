/**
 *  mapparameteredit.js
 *  7/13/2015 - PCSEG339 Kenny Chavez
 */

function insertItemToMapParameterEditTemplateGrid() {
	var grid = $("#mapParameterEditTemplateGrid").data("kendoGrid");
	grid.addRow();
	$(".k-grid-edit-row").appendTo("#mapParameterEditTemplateGrid tbody");

}

// create grid
function mapParameterEditTemplateGrid() {	
	templateGridName = $("#templateContainer").val();
	
	// change me
	var mapParamEditUrl = "/datasources/device_config/{conf_template}";
	mapParamEditUrl = mapParamEditUrl.replace("{conf_template}",mapParameterEdit);
	
			
	var grid = $("#mapParameterEditTemplateGrid").kendoGrid(
					{
						dataSource : {
							pageSize : 20,
							transport: {
		                		 read: function(options) {		                			 
		                			 $
		                				.ajax({
		                					url :"ajax" + mapParamEditUrl,
		                					type : 'GET',
		                					dataType : 'JSON',
		                					//contentType : "application/json; charset=utf-8",		                					
		                					success : function(response) {
		                						//alert(JSON.stringify(response.entity));
		                						//console.log(">>>>>>>>"+JSON.stringify(response));
		                						updateLabelForm(response.entity);
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
		                	 }
					
//							,
//							schema : {
//								model : {
//									id : "deviceIO",
//									fields : {
//										deviceIO : {
//											editable : false,
//											nullable : false
//										},
//										parameterName : {
//											editable : false,
//											nullable : false,
//											type : 'string'
//											//defaultValue: { "parameterName.name" : "Seat Switch"} 
//										},
//										systemTag : {
//											editable : true,
//											nullable : false,
//											type : 'string'
//										},
//										physicalQuantity : {
//											editable : false,
//											nullable : false,
//											type : 'string'
//										},
//										access : {
//											editable : true,
//											nullable : false,
//											type : 'string'
//										},
//										dataType : {
//											editable : true,
//											nullable : false,
//											type : 'string'
//										},
//										customTag : {
//											editable : true,
//											nullable : false,
//											type : 'json'
//										},
//										unitOfMeasurement : {
//											editable : true,
//											nullable : false,
//											type : 'string'
//										},
//										acquisition : {
//											editable : true,
//											nullable : false,
//											type : 'string'
//										}
//									}
//								}
//							}
						},
						toolbar : kendo.template($("#filterTemplate").html()),
						//toolbar: ["create"],
						height : 500,
						sortable : true,
						pageable : true,
						//                         filterable: {
						//                             extra: false

						//                         },
						//                          pageable: {
						// 					        refresh: true,
						// 					        pageSizes: true,
						// 					        previousNext: true
						// 				    	},
						navigatable : true,
						editable : {
							confirmation : false,
							createAt : "bottom"
						},
						scrollable : true,
						columns : [
								{
									field : "deviceIO",
									title : "Device I/O",
									width : 50,
									headerTemplate : "<strong style='color:black ;' >Device I/O<strong>",
									filterable : true
								},
								{
									field : "pointName",
									title : "Point Name",
									width : 50,
									editable : false,
									headerTemplate : "<strong style='color:black ;' >Point Name<strong>",
								},
								{
									field : "parameterName",
									title : "Parameter Name",
									width : 50,

									headerTemplate : "<strong style='color:black ;' >Parameter Name<strong>",
									editor : parameterNameDropDownEditorEditTemplate,
									template : function(dataItem) {
										//console.log("//PARAM ITEM: " + JSON.stringify(dataItem));
										if (dataItem.hasOwnProperty("parameterName")){
											return kendo.htmlEncode(dataItem.parameterName);	
										}
									}
								},
								{
									field : "customTag",
									title : "Custom Tag",
									width : 50,
									headerTemplate : "<strong style='color:black ;' >Custom Tag<strong>",
									editor : customTagDropDownEditor
								//                               ,
								//                               template: "#= customTag.join(', ') #"
								},
								{
									field : "systemTag",
									title : "System Tag",
									width : 50,
									headerTemplate : "<strong style='color:black ;' >System Tag<strong>",
									editor : systemTagDropDownEditor,
									template : function(dataItem) {
										
										if (dataItem.hasOwnProperty("systemTag")){
											return kendo.htmlEncode(dataItem.systemTag);	
										}
										
									}
								},
								{
									field : "physicalQuantity",
									title : "Physical Quantity",
									width : 50,
									editable : false,
									headerTemplate : "<strong style='color:black ;' >Physical Quantity<strong>"
								},
								{
									field : "unitOfMeasurement",
									title : "Unit",
									width : 50,
									headerTemplate : "<strong style='color:black ;' >Unit<strong>",
									editor : unitDropDownEditor,
									template : function(dataItem) {
										//console.log("dataItem unit: " + JSON.stringify(dataItem));
										if (dataItem.hasOwnProperty("unitOfMeasurement")){
											return kendo.htmlEncode(dataItem.unitOfMeasurement);	
										}
										
									}
								},
								{
									field : "dataType",
									title : "Data Type",
									width : 50,
									headerTemplate : "<strong style='color:black ;' >Data Type<strong>",
									editor : dataTypeDropDownEditor
								},
								{
									field : "access",
									title : "Access",
									width : 50,
									headerTemplate : "<strong style='color:black ;' >Access<strong>",
									editor : accessDropDownEditor,
									template : function(dataItem) {
										
										if (dataItem.hasOwnProperty("access")){
											return kendo.htmlEncode(dataItem.access);	
										}
										
									}
								},
								{
									field : "acquisition",
									title : "Acquisition",
									width : 50,
									editable : false,
									headerTemplate : "<strong style='color:black ;' >Acquisition<strong>"
								}
//								,
//								{
//									command : [
//											{
//												text : " ",
//												click : insertItemToMapParameterEditTemplateGrid,
//												imageClass : "k-icon k-add"
//											}, {
//												name : "destroy",
//												text : " "
//											} ],
//									headerTemplate : "<strong style='color:black ;' >Action<strong>",
//									width : 70
//								}

						],
						remove : function(e) {
							var grid = $("#mapParameterEditTemplateGrid").data(
									"kendoGrid");
							var dataSource = grid.dataSource;

							//records on current view / page   
							var recordsOnCurrentView = dataSource.view().length;
							if (recordsOnCurrentView == 1) {
								insertItemToMapParameterEditTemplateGrid();
							}
						}

					}).data("kendoGrid");

}

mapParameterEditTemplateGrid();

$(document).ready(
		function() {
			//insertItemToMapParameterEditTemplateGrid();

			$("#filterTemplateGrid")
					.keyup(
							function() {
								var val = $("#filterTemplateGrid").val();

								$("#mapParameterEditTemplateGrid").data(
										"kendoGrid").dataSource.filter({
									logic : "or",
									filters : [ {
										field : "deviceIO",
										operator : "contains",
										value : val
									}, {
										field : "pointName",
										operator : "contains",
										value : val
									}, {
										field : "parameterName",
										operator : "contains",
										value : val
									}, {
										field : "customTag",
										operator : "contains",
										value : val
									}, {
										field : "systemTag",
										operator : "contains",
										value : val
									}, {
										field : "physicalQuantity",
										operator : "contains",
										value : val
									}, {
										field : "unitOfMeasurement",
										operator : "contains",
										value : val
									}, {
										field : "dataType",
										operator : "contains",
										value : val
									}, {
										field : "access",
										operator : "contains",
										value : val
									}, {
										field : "acquisition",
										operator : "contains",
										value : val
									} ]
								});

							});

			$("#saveParamMap").on('click', function() {
				updateTemplate();
			});

		});

// function to update template based from grid data
function updateTemplate(){

	// prepare payload
	var updatepayload = buildTemplatePayload();
	
	// update config
	
	var url = "ajax" + saveTemplateUrl;
	 
		$.ajax({
			url : url,
			type : 'PUT',
			contentType : "application/json",	                					
			data : JSON.stringify(updatepayload), 
			success : function(response) {
				
				staticNotification.show({
					message: "'"+updatepayload.name+"' updated successfully."
				}, "success");
				
			//	kendoWindow.data("kendoWindow").close();
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});
}


function updateLabelForm(response){
	
	$("#viewEditTemplateName").text(mapParameterEdit);
	$("#viewEditTemplateMake").text(response.deviceMake);
	$("#viewEditTemplateDeviceType").text(response.deviceType);
	$("#viewEditTemplateModel").text(response.deviceModel);
	$("#viewEditTemplateProtocol").text(response.deviceProtocol);
	$("#viewEditTemplateProtocolVersion").text(response.deviceProtocolVersion);	
	
	// Load configured points (if available) to grid	
	loadPointsToGrid(response);
	
}

//function to load initial data to grid based from selected template

function loadPointsToGrid(response){
	
	//console.log("////consoleEdit---> " + JSON.stringify(response));
	
	// get all points from response and add them to grid
	
	var grid = $("#mapParameterEditTemplateGrid").data('kendoGrid');
	grid.dataSource.data([]);
	
	if (jQuery.isEmptyObject(response.configPoints)){		
		//insertItemToMapParameterGrid();		
	}else{

		$.each(response.configPoints, function (){
			var pointId = this.pointId;
			var pointName = this.pointName;
			var configuredName = this.configuredName;
			var parameterName = this.parameterName;
			var systemTag = this.systemTag;
			var physicalQuantity = this.phyQtyName;
			var unit = this.unitOfMeasurement;
			var dataType = this.dataType;
			var access = this.access;
			var acquisition = this.acquisition;

			grid.dataSource.add({
								"deviceIO": pointId,
								"pointName":pointName,
								"parameterName": parameterName,
				                "physicalQuantity": physicalQuantity,
				                "dataType": dataType,
				                "systemTag": systemTag,
				                "access": access,
				                "acquisition": acquisition,
				                "customTags": []
								});			
		});
	}
	
	
}


//build template to use in update
function buildTemplatePayload(){
	
	var templateName = mapParameterEdit;
	var templateMake = $("#viewEditTemplateMake").text(); // change me
	var templateDevice = $("#viewEditTemplateDeviceType").text();
	var templateModel = $("#viewEditTemplateModel").text();
	var templateProtocol = $("#viewEditTemplateProtocol").text();
	var templateProtocolVersion = $("#viewEditTemplateProtocolVersion").text();
	
	var grid = $("#mapParameterEditTemplateGrid").data("kendoGrid");
	var gridData = grid.dataSource.view();
	
	//console.log("//--> gridDataEdit: " + JSON.stringify(gridData));
	
	
	var configPoints = [];
	
	$.each(gridData, function (){
		var pointId = this.deviceIO;
		var pointName = this.pointName;
		var configuredName = this.configuredName;
		
		if (this.hasOwnProperty("parameterName")){
			var parameterName = this.parameterName;		
			var phyQtyName = this.physicalQuantity;
			var dataType = this.dataType;
			var unit = this.unit;
			var systemTag = this.systemTag;
			var customTags = this.customTag;
			var acquisition = this.acquisition;
			var access = this.access;
			
			
			var configPointData = {
		            "pointId": pointId,
		            "pointName": pointName,
		            "configuredName": configuredName,
		            "parameterName": parameterName,
		            "phyQtyName": phyQtyName,
		            "dataType": dataType,
		            "unit": unit,
		            "systemTag": systemTag,
		            "customTags": [],//change me
		            "acquisition" : acquisition,
		            "access" : access
		        };
			
			
			if (parameterName != undefined){
				configPoints.push(configPointData);	
			}
		}
		
		
	});
	
	
	
	var payload = {
		    "name": templateName,
		    "deviceMake": templateMake,
		    "deviceType": templateDevice,
		    "deviceModel": templateModel,
		    "deviceProtocol": templateProtocol,
		    "deviceProtocolVersion": templateProtocolVersion,
		    "configPoints": configPoints
		};
	
	//console.log("//finalSavePayload: " + JSON.stringify(payload));
	
	return payload;	
}

//cancel Edit Mapped parameters

$("#cancelParamMap").on('click', function() {
	 var kendoWindowConfirm = $("<div />").kendoWindow({
		    title: "Confirm",
		    resizable: false,
		    modal: true,
		    deactivate: function() {
		        this.destroy();                                           
		    }
		});
	 
	 kendoWindowConfirm.data("kendoWindow")
	    .content($("#confirmClose").html())
	    .center().open(); 
	 
	// for dialog delete confirmation
	$("#yesButton").click(function(){
		loadPage("devicetab");					                        	
		kendoWindowConfirm.data("kendoWindow").close();
	});

	$("#noButton").click(function(){
		kendoWindowConfirm.data("kendoWindow").close();
	});
	
});

