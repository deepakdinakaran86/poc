/**
 *  mapparameterconfig.js
 *  7/13/2015 - PCSEG339 Kenny Chavez
 */


    function insertItemToMapParameterGrid(){
	    var grid = $("#mapParameterGrid").data("kendoGrid");
	    grid.addRow();
	    $(".k-grid-edit-row").appendTo("#mapParameterGrid tbody");
	   
	}
	
	// function to open window for assigning map to other devices
	function openAssignMapWindow(){
		
	    var window = $("#window").kendoWindow({
	                width: "400px",
	                height: "600px",
	                title: "Assign to Device",
	                modal: true,
//				    deactivate: function() { // FIX ME
//				        //this.destroy();    
//				        $("#window").empty();
//					       
//				    },
	                actions: [
	                    "Pin",
	                    "Minimize",
	                    "Maximize",
	                    "Close"
	                ]
	            });
	     
	        window.data("kendoWindow").center().open();	       
     
	}

	// filter through list of devices
	function filterDevice(){
		var val = $("#filterDeviceGrid").val();		
		$("#assignToDeviceGrid").data("kendoGrid").dataSource.filter({
			logic : "or",
			filters : [ {
				field : "sourceId",
				operator : "contains",
				value : val
			} ]
		});
	}
	
	
	// function to load device list to datagrid
	function loadDeviceList(response){
		 $("#filterDeviceGrid").val("");
		var grid = $("#assignToDeviceGrid").kendoGrid({
       		dataSource: {
       		 pageSize: 20,
       		 data: response.entity,
       		 schema: {
					model: {
						id: "unitId",
						fields: {
							sourceId: { editable: false, nullable: false }
						}
					}
				}
       		},
       		toolbar: kendo.template($("#filterDevice").html()),
        	height: 500,
            sortable: true,
            pageable: true,
			navigatable: true,			
            scrollable: true,                        
            columns: [
                
           		{
					headerTemplate :"<strong style='color:black ;' >Select<strong>",
					width: 20,
					template: "<input type='checkbox' onclick='addToDeviceList(this);'/>"
           		},
           		{ field: "sourceId",
                    title: "Device Name", 
                    width: 80, 
                    headerTemplate :"<strong style='color:black ;' >Device Name<strong>",
                    filterable: true
                  }                       	                			
           		]

        	}).data("kendoGrid");
		
		$("#assignToDeviceBtns").css("display","");
	}
	
var assignToDeviceList=[];

function addToDeviceList(e){
	
		// Every user clicks on the checkbox, the corresponding device / source id will be temporarily stored
		// To be used in building the sourcIds
	
		 var tr = $(e).closest("tr"); //get the row for deletion
		 //console.log("//JSON tr>>>>: " + JSON.stringify(tr));
		 
		 var grid = $("#assignToDeviceGrid").data("kendoGrid");
		 
		 var data = grid.dataItem(tr); //get the row data so it can be referred later
		 //console.log("//JSON>>>>: " + JSON.stringify(data));
	
		if($(e).prop('checked')){

			 assignToDeviceList.push(data.sourceId);
		}else{
			var index = assignToDeviceList.indexOf(data.sourceId);
			if (index > -1) {
				assignToDeviceList.splice(index, 1);
			}
		}
		
}
	
	// create grid
	function mapParameterGrid(){
		
		templateGridName = $("#templateContainer").val();
		getSelectedDeviceDetails();
		var grid = $("#mapParameterGrid")
		.kendoGrid(
		{
			dataSource : {
				pageSize : 20,
//				schema : {
//					model : {
//						id : "deviceIO",
//						fields : {
//							deviceIO : {
//								editable : false,
//								nullable : false
//							},
//							pointName : {
//								editable : false,
//								nullable : false
//							},
//							parameterName : {
//								editable : true,
//								nullable : true,
//								type : 'string',
//								defaultValue: ""
//							},
//							systemTag : {
//								editable : true,
//								nullable : true,
//								type : 'string',
//								defaultValue: ""
//							},
//							access : {
//								editable : true,
//								nullable : true,
//								type : 'string',
//								defaultValue: ""
//							},
//							dataType : {
//								editable : true,
//								nullable : true,
//								type : 'string',
//								defaultValue: ""
//							},
//							customTag : {
//								editable : true,
//								nullable : false,
//								type : 'json'
//							},
//							unitOfMeasurement : {
//								editable : true,
//								nullable : true,
//								type : 'string',
//								defaultValue: ""
//							}
//						}
//					}
//				}
			},
			toolbar : kendo.template($("#filterTemplate").html()),
			// toolbar: ["create"],
			height : 500,
			sortable : true,
			pageable : true,
			// filterable: {
			// extra: false

			// },
			// pageable: {
			// refresh: true,
			// pageSizes: true,
			// previousNext: true
			// },
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
						//,editor : deviceIoDropDownEditor
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
						editor : customTagDropDownEditor,
						template : function(dataItem) {
							var tagsInComma = "";
							var tags = dataItem.customTag;
							if (tags != undefined
									&& tags.length != undefined) {
								$(tags)
										.each(
												function(index) {
													tagsInComma += tags[index].name
															+ ",";
												});
								return kendo
										.htmlEncode(tagsInComma
												.substring(
														0,
														tagsInComma.length - 1));
							}
							return "";
						}

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
							
							if (dataItem.hasOwnProperty("unitOfMeasurement")){
								return kendo.htmlEncode(dataItem.unitOfMeasurement.name);	
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
//					,
//					{
//						command : [
//								{
//									name: "insert",
//									text : " ",
//									click : insertItemToMapParameterGrid,
//									imageClass : "k-icon k-add"
//								}, {
//									name : "destroy",
//									text : " "
//								} ],
//						headerTemplate : "<strong style='color:black ;' >Action<strong>",
//						width : 70
//					}

			],
			remove : function(e) {
				var grid = $("#mapParameterGrid").data(
						"kendoGrid");
				var dataSource = grid.dataSource;

				// records on current view / page
				var recordsOnCurrentView = dataSource.view().length;
				if (recordsOnCurrentView == 1) {
					insertItemToCreateTemplateConfig();
				}
			}

		}).data("kendoGrid");
		
		
	}
	
	mapParameterGrid();
    
    // function to toggle configuration template
    function toggleTemplate(){
    
    var linkText = $("#selectAddTemplateLink").text(); 
    var dropdownlist = $("#mapParamConfigurationTemplate").data("kendoDropDownList");
    
	    if (linkText == 'Select Template'){
	       $("#selectAddTemplateLink").text("Remove Template");	       
	       dropdownlist.enable(true);
	    }else{
	    	

			 var kendoWindowConfirm = $("<div />").kendoWindow({
				    title: "Select Template Confirm",
				    resizable: false,
				    modal: true,
				    deactivate: function() {
				        this.destroy();                                           
				    }
				});
			 
			 kendoWindowConfirm.data("kendoWindow")
			    .content($("#removeTemplateSelectedConfirm").html())
			    .center().open(); 
			 
			// for dialog remove template confirmation
			$("#yesButton").click(function(){
		    	
		    	dropdownlist.value("Select");
		    	getSelectedDeviceDetails();
		    	$("#selectAddTemplateLink").text("Select Template");
		    	dropdownlist.enable(false);
		    	kendoWindowConfirm.data("kendoWindow").close();
			});

			$("#noButton").click(function(){
				kendoWindowConfirm.data("kendoWindow").close();
			});
			
	    }
    
    }	
    	
    	
    	     


                $(document).ready(function() {

	$("#assignParamMap").prop("disabled", true).addClass("k-state-disabled");

	$("#filterTemplateGrid").keyup(function() {
		var val = $("#filterTemplateGrid").val();

		$("#mapParameterGrid").data("kendoGrid").dataSource.filter({
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


});

var selectedTemplateName;            
function updateLabelFormMapParameter(response){
	// alert("updateLabelForm");

	// //console.log("//RESPONSE: " + JSON.stringify(response));
	
	$("#mapParamConfigDeviceName").text(mapParametersToThisDevice);
	$("#mapParamConfigMake").text(response.version.make);
	$("#mapParamConfigDeviceType").text(response.version.deviceType);
	$("#mapParamConfigModel").text(response.version.model);
	$("#mapParamConfigProtocol").text(response.version.protocol);
	$("#mapParamConfigProtocolVersion").text(response.version.version);	
	
	// check if G2021 and hide template configuration
	if (response.version.deviceMake == "G2021"){
		$(".confTemplateSelection").hide();
	}
	

	  // drop down for Configuration Template
	$("#mapParamConfigurationTemplate").kendoDropDownList({
		dataSource: {
	       	 type: "json",
		       	transport: {
          		 read: function(options) {
          			 
          			 var targetUrl = mapParameterEditUrl;
          			 targetUrl = targetUrl.replace("{sub_id}",1);
          			 // FIX ME
          			 
           			var payload = 
           					{   "make": $("#mapParamConfigMake").text(),
	             				    "deviceType": $("#mapParamConfigDeviceType").text(),
	             				    "model": $("#mapParamConfigModel").text(),
	             				    "protocol": $("#mapParamConfigProtocol").text(),
	             				    "version": $("#mapParamConfigProtocolVersion").text()
           					};           			            			
          			 $.ajax({
          					url :"ajax" + targetUrl,
          					type : 'POST',
          					dataType : 'JSON',
          					contentType : "application/json; charset=utf-8",	                					
          					data : JSON.stringify(payload), // FIX ME		                					
          					success : function(response) {

          					// build list of template names
          					var templateList = [];
          					$.each(response.entity, function (){
          						var name = this.name;
          						templateList.push({"name":name});			            						
          					});
          					
          					return options.success(templateList);	

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
						total : function(response) {
							return $(response.entity).length;
						},
						model : {
							fields : {
								name : {
									type : "string",
								}
							}
						}
					}
	        },
	        dataTextField: "name",
	        dataValueField: "name",
	        filter: "contains",
	        suggest: true,
	        index: 3,
	        change : function (e){
	        	// load datagrid with selected template				        	
	        	loadSelectedTemplate(this.text());
	        	selectedTemplateName = this.text();
	        }
      });
	// Load configured points (if available) to grid	
	//loadPointsToGrid(response);
	
}


// function to get device details
function getSelectedDeviceDetails(){
	//alert(mapParametersToThisDevice);
	//get device details
	var targetUrl = deviceDetails;
	 targetUrl = targetUrl.replace("{source_id}",mapParametersToThisDevice);

	 $.ajax({
			url :"ajax" + targetUrl,
			type : 'GET',
			dataType : 'JSON',
			contentType : "application/json; charset=utf-8",	                					
			//data : JSON.stringify(payload), // FIX ME		                					
			success : function(response) {
				//alert(JSON.stringify(response.entity));
				////console.log("//deviceDetails: " + JSON.stringify(response));
				updateLabelFormMapParameter(response.entity);					
				
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});	
}
//function to load initial data to grid based from selected device's protocol version

function loadPointsToGrid(response){
	
	////console.log("//////console---> " + JSON.stringify(response));
	
	// get all points from response and add them to grid
	
	var grid = $("#mapParameterGrid").data('kendoGrid');
	grid.dataSource.data([]);
	
	if (jQuery.isEmptyObject(response.configPoints)){		
		insertItemToMapParameterGrid();		
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

//function to load points to grid, based from selected template
function loadSelectedTemplate(templateName){
	
	//get device details
	var targetUrl = mapParameterEditUrl;
	 targetUrl = targetUrl.replace("all",templateName);
	          			            			
	 $.ajax({
			url :"ajax" + targetUrl,
			type : 'GET',
			dataType : 'JSON',
			contentType : "application/json; charset=utf-8",	                					             					
			success : function(response) {

				////console.log("//loadSelectedTemplate: " + JSON.stringify(response));
				
				processPoints(response.entity.configPoints);
									
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});	
	
	
}

// function to process points of a template and load to grid
function processPoints(response){

	// remove existing grid data and load new points from selected template 
	var grid = $("#mapParameterGrid").data('kendoGrid');
	grid.dataSource.data([]);
	
	$.each(response, function (){
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

// build payload for assigning configured points to device
function buildPayloadAssignPoints(){
	var grid = $("#mapParameterGrid").data('kendoGrid');
	var configPoints = grid.dataSource.view();
	var deviceName = $("#mapParamConfigDeviceName").text();
	
	var payload = 
		{ "sourceIds": [ deviceName ],
			 "confTemplate": {
			        "name": selectedTemplateName,
			        "configPoints": configPoints
		
			 }
		};
	
	
	return payload;
}

// Save Parameter Mapping
$("#saveParamMap").on('click', function() {

	var targetUrl = assignPointsToDevice;
	var payload = buildPayloadAssignPoints();
	
	 $.ajax({
			url :"ajax" + targetUrl,
			type : 'PUT',
			dataType : 'JSON',
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(payload),
			success : function(response) {
				
				staticNotification.show({
					message: payload.sourceIds + " parameters mapped successfully."
				}, "success");
				
				// if success, enable the "Assign To" Button, disable save all
				$("#assignParamMap").prop("disabled", false).removeClass("k-state-disabled");
				$("#saveParamMap").prop("disabled", true).addClass("k-state-disabled");
				
				
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});	
});

// cancel parameter mapping

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

//assign parameter to current device
$("#assignParamMap").on('click',function () {										
	openAssignMapWindow();	
	getAllSimilarDevices();
});

// cancel assign parameter to other device
$("#cancelAssign").on('click',function () {										

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
	kendoWindowConfirm.data("kendoWindow").close();
	 $("#window").kendoWindow("close");
	 assignToDeviceList=[];
	});

	$("#noButton").click(function(){
	kendoWindowConfirm.data("kendoWindow").close();
	});

});


//assign parameter to other devices
$("#assignParametersToDevice").on('click',function () {										
	
	// Build payload
	var grid = $("#mapParameterGrid").data('kendoGrid');
	var configPoints = grid.dataSource.view();
	var deviceName = assignToDeviceList;
	var targetUrl = assignPointsToDevice;
	
	// check for selected entries
	if(deviceName.length>0){
		var payload = 
		{ "sourceIds": deviceName,
			 "confTemplate": {
			        "name": selectedTemplateName,
			        "configPoints": configPoints
		
			 }
		};
	
	// Run service for assigning configured points
	 $.ajax({
			url :"ajax" + targetUrl,
			type : 'PUT',
			dataType : 'JSON',
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(payload),
			success : function(response) {

				// close assign window
				$("#window").kendoWindow("close");
				assignToDeviceList=[];
				 
				// show message
				staticNotification.show({
					message: payload.sourceIds + " parameters mapped successfully."
				}, "success");
				
				// if success, disable the "Assign To" Button, enable save all
				$("#assignParamMap").prop("disabled", true).addClass("k-state-disabled");
				$("#saveParamMap").prop("disabled", false).removeClass("k-state-disabled");
				
			},
			error : function(xhr, status, error) {
				
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});	
	 
	}else{ //show message if no devices are selected
			// close assign window
			$("#window").kendoWindow("close");
			assignToDeviceList=[];
			
			staticNotification.show({
				message: "No Selected Devices. Please select one device to assign points to."
			}, "error");
				
	}
	
	
});

// function to get all similar devices with the same make , model , protocol, and protocol version
function getAllSimilarDevices(){
	
	var targetUrl = getAllDevices;
	
	 $.ajax({
			url :"ajax" + targetUrl,
			type : 'GET',
			dataType : 'JSON',
			contentType : "application/json; charset=utf-8",
			success : function(response) {
			
			//console.log("response: " + JSON.stringify(response));
			filterDevices(response);
			 
			
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});
	
}

// filter devices with the same make, model, protocol and protocol version and return as list
function filterDevices(response){
		
	var tempDevices = [];
	
	$.each(response.entity,function (){
		var make = this.version.make;
		var model = this.version.model;
		var protocol = this.version.protocol;
		var protocolVersion = this.version.version;
		
		if (make == $("#mapParamConfigMake").text() && 
			model == $("#mapParamConfigModel").text() &&
			protocol == $("#mapParamConfigProtocol").text() && 
			protocolVersion == $("#mapParamConfigProtocolVersion").text()){
			
			tempDevices.push(this);
		}
		
	});
	
	var finalResponse = {"entity": tempDevices};
	//console.log("finalDevices: " + JSON.stringify(finalResponse));
		
	loadDeviceList(finalResponse);
}
