/**
 *  createconfigtemplate.js
 *  7/12/2015 - PCSEG339 Kenny Chavez
 */

// Validations for form input for config template
// accepts alphanumeric, space, '-', '_'

$('#configTemplateName').keypress(function (e) {
	alphanumericValidator(e,"configTemplateName");
});


// method to check configuration template name if unique 
// across the entire templates

function checkTemplateConfigUniqueness(configTemplateName){
	
	// if unique, proceed. Otherwise, show message
	//
	// if (configTemplate isUnique)
	//	call loadMake();
	// else
	//  show message(Config Template is not unique);
	//
}

// method to load make dropdown

function loadMake(){
	
	// based from config template,
	// do an ajax call to API to get
	// make list then populate
	// to drop down.
	
	// on change of drop down,
	// it will call loadDevice();
	

	 $("#configTemplateMake").kendoDropDownList({
        dataSource: {
       	 type: "json",
       	 transport: {
   		    read: {
   		      url: "ajax" + getAllMake	    		      
   		    } 
       	 },
       	 schema : {
					data : function(response) {
						if (response.entity != null) {
							return response.entity;
						} 

					},
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
        change: function(e){   
        	
        	clearText("configTemplateDevice");
        	clearText("configTemplateModel");
        	clearText("configTemplateProtocol");
        	clearText("configTemplateProtocolVersion");
        	
        	$("#configTemplateDevice").prop('disabled','disabled');
        	$("#configTemplateModel").prop('disabled','disabled');
        	$("#configTemplateProtocol").prop('disabled','disabled');
        	$("#configTemplateProtocolVersion").prop('disabled','disabled');
        	
        	
        	var make = this.text(); //selected make
        	enableNextDropDown("configTemplateDevice");
        	loadDevice(make);
        	
        }
    });
	
	
}

loadMake();


// method to load device dropdown

function loadDevice(make){
	
	// based from device selection,
	// do an ajax call to API to get
	// device list then populate
	// to drop down.
	
	// on change of drop down,
	// it will call loadModel();
	var url = "ajax"+ getAllDeviceTypes;
	url = url.replace("{make_name}",make);
	
	 $("#configTemplateDevice").kendoDropDownList({
	        dataSource: {
	       	 type: "json",
	       	 transport: {
	   		    read: {
	   		      url: url	    		      
	   		    } 
	       	 },
	       	 schema : {
						data : function(response) {
							if (response.entity != null) {
								return response.entity;
							} 

						},
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
	        change: function(e){   
	        	
	        	clearText("configTemplateModel");
	        	clearText("configTemplateProtocol");
	        	clearText("configTemplateProtocolVersion");

	        	$("#configTemplateModel").prop('disabled','disabled');
	        	$("#configTemplateProtocol").prop('disabled','disabled');
	        	$("#configTemplateProtocolVersion").prop('disabled','disabled');
	        	
	        	
	        	var device = this.text(); //selected make
	        	enableNextDropDown("configTemplateModel");
	        	loadModel(device);
	        	
	        }
	    });
	
}

//method to load model dropdown

function loadModel(device){
	
	// based from device selection,
	// do an ajax call to API to get
	// model list then populate
	// to drop down.
	
	// on change of drop down,
	// it will call loadProtocol();
	
	// loadProtocol(Device,Make);

	var make = $("#configTemplateMake").data("kendoDropDownList").text();
		
	var payload = 
			{
			  "make": make,
			  "deviceType": device
			
			}; 
	
	$("#configTemplateModel").kendoDropDownList({
        dataSource: {
       	 type: "json",
       	 transport: {
       		read: function(options) {
   			 
   			 $.ajax({
   					url : "ajax" + getAllModels,
   					type : 'POST',
   					dataType : 'JSON',
   					contentType : "application/json; charset=utf-8",
   					data : JSON.stringify(payload),
   					success : function(response) {
   						//console.log(response.entity);
   						return options.success(response.entity);
   					
   					},
   					error : function(xhr, status, error) {
//   						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
//   						staticNotification.show({
//   							message: errorMessage
//   						}, "error");
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
        change: function(e){   


        	clearText("configTemplateProtocol");
        	clearText("configTemplateProtocolVersion");

        	$("#configTemplateProtocol").prop('disabled','disabled');
        	$("#configTemplateProtocolVersion").prop('disabled','disabled');
        	
        	
        	
        	var device = this.text(); //selected make
        	enableNextDropDown("configTemplateProtocol");
        	loadProtocol(device);
        	
        }
    });
	
}

//method to loadProtocol dropdown

function loadProtocol(){
	
	// based from device/model,
	// do an ajax call to API to get
	// protocol version list then populate
	// to drop down.
	
	// parameter mapping grid will be enabled.
	
	var make = $("#configTemplateMake").data("kendoDropDownList").text();
	var device = $("#configTemplateDevice").data("kendoDropDownList").text();
	var model = $("#configTemplateModel").data("kendoDropDownList").text();
	
	var payload = 
	{
	  "make": make,
	  "deviceType": device,
	  "model": model
	
	};
	
	$("#configTemplateProtocol").kendoDropDownList({
        dataSource: {
       	 type: "json",
       	 transport: {
       		read: function(options) {
   			 
   			 $.ajax({
   					url : "ajax" + getAllProtocols,
   					type : 'POST',
   					dataType : 'JSON',
   					contentType : "application/json; charset=utf-8",
   					data : JSON.stringify(payload),
   					success : function(response) {
   						//console.log(response.entity);
   						return options.success(response.entity);
   					
   					},
   					error : function(xhr, status, error) {
//   						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
//   						staticNotification.show({
//   							message: errorMessage
//   						}, "error");
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
        change: function(e){   

        	clearText("configTemplateProtocolVersion");
        	
        	$("#configTemplateProtocolVersion").prop('disabled','disabled');
        	
        	var device = this.text(); //selected make
        	enableNextDropDown("configTemplateProtocolVersion");
        	loadProtocolVersion();
        	
        }
    });
	
	
}

//method to loadProtocol Version dropdown

function loadProtocolVersion(){
	
	// based from device/model,
	// do an ajax call to API to get
	// protocol version list then populate
	// to drop down.
	
	// parameter mapping grid will be enabled.
	
	var make = $("#configTemplateMake").data("kendoDropDownList").text();
	var device = $("#configTemplateDevice").data("kendoDropDownList").text();
	var model = $("#configTemplateModel").data("kendoDropDownList").text();
	var protocol = $("#configTemplateProtocol").data("kendoDropDownList").text();
	
	
	var payload = 
	{
	  "make": make,
	  "deviceType": device,
	  "model": model,
	  "protocol": protocol
	};
	
	$("#configTemplateProtocolVersion").kendoDropDownList({
        dataSource: {
       	 type: "json",
       	 transport: {
       		read: function(options) {
   			 
   			 $.ajax({
   					url : "ajax" + getAllProtocolVersions,
   					type : 'POST',
   					dataType : 'JSON',
   					contentType : "application/json; charset=utf-8",
   					data : JSON.stringify(payload),
   					success : function(response) {
   						//console.log(response.entity);   						
   						return options.success(response.entity);
   					
   					},
   					error : function(xhr, status, error) {
   						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
   						staticNotification.show({
   							message: errorMessage
   						}, "error");
//   						var response = {"name":"1.0.0"};
//   						return options.success(response);
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
        change: function(e){   

        	var protocolVersion = this.text();
        	
        	// load grid based from items selected protocol version
        	loadPointsToGrid(protocolVersion);
       	    
        }
    });
	
	
}

// function to disable all element

function disableFormElements(){
	
	$("#configTemplateDevice").prop('disabled','disabled');
	$("#configTemplateModel").prop('disabled','disabled');
	$("#configTemplateProtocol").prop('disabled','disabled');
	$("#configTemplateProtocolVersion").prop('disabled','disabled');
	
}
disableFormElements();

//method to enable next element

function enableNextDropDown(elementName){
	$("#"+elementName).prop("disabled","");
}

//method to clear text element

function clearText(elementName){
	
	var ddl = $("#"+elementName).kendoDropDownList({
		   dataTextField: "text",
		   dataValueField: "value"
		 }).data().kendoDropDownList;
	ddl.dataSource.data([]);
	ddl.text(""); // clears visible text
	ddl.value(""); // clears invisible value
}


function insertItemToCreateTemplateConfig(){
    var grid = $("#createConfigTemplateGrid").data("kendoGrid");
    grid.addRow();
    $(".k-grid-edit-row").appendTo("#createConfigTemplateGrid tbody");
//    $(".k-grid-delete").css("display","");
}

function returnData(data){
	alert(data);
    return "-Select-";
}

// create grid
function createConfigTemplateGrid() {
	templateGridName = $("#templateContainer").val();
	var grid = $("#createConfigTemplateGrid")
	.kendoGrid(
	{
		dataSource : {
			pageSize : 20,
			schema : {
				model : {
					id : "deviceIO",
					fields : {
						deviceIO : {
							editable : false,
							nullable : false
						},
						parameterName : {
							editable : false,
							nullable : false,
							type : 'string'
							//defaultValue: { "parameterName.name" : "Seat Switch"} 
						},
						systemTag : {
							editable : true,
							nullable : false,
							type : 'string'
						},
						physicalQuantity : {
							editable : false,
							nullable : false,
							type : 'string'
						},
						access : {
							editable : true,
							nullable : false,
							type : 'string'
						},
						dataType : {
							editable : true,
							nullable : false,
							type : 'string'
						},
						customTag : {
							editable : true,
							nullable : false,
							type : 'json'
						},
						unitOfMeasurement : {
							editable : true,
							nullable : false,
							type : 'string'
						},
						acquisition : {
							editable : true,
							nullable : false,
							type : 'string'
						}
					}
				}
			}
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
//					, 
//					editor : deviceIoDropDownEditor
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
					editor : parameterNameDropDownEditor,
					template : function(dataItem) {
						
						if (dataItem.hasOwnProperty("parameterName")){
							return kendo.htmlEncode(dataItem.parameterName.name);	
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
							return kendo.htmlEncode(dataItem.systemTag.name);	
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
							return kendo.htmlEncode(dataItem.access.name);	
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
//				,
//				{
//					command : [
//							{
//								name: "insert",
//								text : " ",
//								click : insertItemToCreateTemplateConfig,
//								imageClass : "k-icon k-add"
//							}, {
//								name : "destroy",
//								text : " "
//							} ],
//					headerTemplate : "<strong style='color:black ;' >Action<strong>",
//					width : 70
//				}

		],
		remove : function(e) {
			var grid = $("#createConfigTemplateGrid").data(
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

createConfigTemplateGrid();   

//insertItemToCreateTemplateConfig(); 

$("#filterTemplateGrid").keyup(function() {
	var val = $("#filterTemplateGrid").val();

	$("#createConfigTemplateGrid").data("kendoGrid").dataSource.filter({
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


//function to load points based from selected item's protocol version
function loadPointsToGrid(protocolVersion){
	
	var make = $("#configTemplateMake").val();
	var device = $("#configTemplateDevice").val();
	var model = $("#configTemplateModel").val();
	var protocol = $("#configTemplateProtocol").val();	
	
	var payload = {
			  "make": make,
			  "deviceType": device,
			  "model": model,
			  "protocol": protocol,
			  "version": protocolVersion
			};
	
	// get all points and load them to grid
	var url = "ajax" + getAllPointsByProtocolVersion;
	
//	var samplepayload = {
//			  "make": "Teltonica",
//			  "deviceType": "3G Routers",
//			  "model": "FM1010",
//			  "protocol": "EDCP",
//			  "version": "EDCP-1.0.0"
//			};
console.log('pint fetch');
	var dataSource = new kendo.data.DataSource({
       	 type: "json",
	       	 transport: {
	       		read: function(options) { 
       			        			 
       			 url = url.replace("{sub_id}",1); // change this to token in the future
       			        			 
       			 $.ajax({
       					url : url,
       					type : 'POST',
       					dataType : 'JSON',
       					contentType : "application/json; charset=utf-8",
       					data: JSON.stringify(payload),
       					success : function(response) {
       						//console.log(">>loadPointsToGrid!: --> "+JSON.stringify(response.entity)); // FIX ME
       						
       						var tempVar = [];
       						
       						$.each(response.entity.points, function (){       							
       							var pointId = this.pointId;       							
								if (!pointId==""){
	       							var pointName = this.pointName;       							
	       							tempVar.push({
									"deviceIO": pointId ,
									"pointName":pointName
									});
	   							}
   							
       						});
       						
       						tempVar = {"entity":tempVar};
       						
       						return options.success(tempVar.entity);
       						  
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

    });

    dataSource.read();
	
    // update grid datasource    
    var grid = $("#createConfigTemplateGrid").data("kendoGrid");
    grid.setDataSource(dataSource);
    grid.dataSource.read();
    
}


// function save template
function saveTemplate(){
	// URL saveTemplate
	var payload = buildTemplatePayload();
	
	if (payload.hasOwnProperty('statusError')){
		staticNotification.show({
            message: "No Valid Configuration. Please modify at least one config point."
        }, "error");
		return false;
	}
	
	var url = "ajax" + saveTemplateUrl;

	// testing ; delete me
	//url = "http://10.234.60.12:9763/datasource-api-1.0.0/services/datasources/device_config";
	
	
	// build header needed from subscription token
	//FIX ME
	$.ajax({
		url : url,
		type : 'POST',
		dataType : 'json',
		contentType : "application/json; charset=utf-8",		
		data : JSON.stringify(payload),
		success : function(response) {
			//console.log(JSON.stringify(response));
			
			staticNotification.show({
                message: payload.name+ " Template added successfully."
            }, "success");
		
		},
		error : function(xhr, status, error) {
			var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
			staticNotification.show({
                message: errorMessage
            }, "error");
		}
	});	
	
	
	
}

// build template to use in saving
function buildTemplatePayload(){
	
	var templateName = $("#configTemplateName").val();
	var templateMake = $("#configTemplateMake").val();
	var templateDevice = $("#configTemplateDevice").val();
	var templateModel = $("#configTemplateModel").val();
	var templateProtocol = $("#configTemplateProtocol").val();
	var templateProtocolVersion = $("#configTemplateProtocolVersion").val();
	
	var grid = $("#createConfigTemplateGrid").data("kendoGrid");
	var gridData = grid.dataSource.view();
	
	//console.log(JSON.stringify("//--> gridData: " + JSON.stringify(gridData)));
	
	
	var configPoints = [];
	
	$.each(gridData, function (){
		var pointId = this.deviceIO;
		var pointName = this.pointName;
		var configuredName = this.configuredName;
		
		if (this.hasOwnProperty("parameterName")){
			var parameterName = this.parameterName.name;		
			var phyQtyName = this.physicalQuantity;
			var dataType = this.dataType;
			var unit = this.unitOfMeasurement.name;
			var systemTag = this.systemTag.name;
			var customTags = this.customTag;
			var acquisition = this.acquisition;
			var access = this.access.name;
			
			var configPointData = {
		            "pointId": pointId,
		            "pointName": pointName,
		            "configuredName": configuredName,
		            "parameterName": parameterName,
		            "phyQtyName": phyQtyName,
		            "dataType": dataType,
		            "unitOfMeasurement": unit,
		            "systemTag": systemTag,
		            "customTags": [],//change me
		            "acquisition" : acquisition,
		            "access" : access
		        };
			
			//console.log(JSON.stringify(parameterName));
			if (parameterName != undefined){
				configPoints.push(configPointData);	
			}
		}
		
		
	});
	
	if (configPoints.length>0){
		var payload = {
			    "name": templateName,
			    "deviceMake": templateMake,
			    "deviceType": templateDevice,
			    "deviceModel": templateModel,
			    "deviceProtocol": templateProtocol,
			    "deviceProtocolVersion": templateProtocolVersion,
			    "configPoints": configPoints
			};

		
		return payload;	
	}else{
		
		return {"statusError":"1"};
	}
	
}

//cancel Create Template

$("#cancelSaveTemplate").on('click', function() {
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

