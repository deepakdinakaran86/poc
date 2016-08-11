
//console.log("////---> " + sessionStorage.sub_id);

function findTab(tabs, tabName) {
	var i = 0;
	var foundTab = false;
	while (true) {

		var next = tabs.tabGroup.children("li").eq(i);
		if (next && next.length > 0) {
			var tabText = next[0].innerText;
			if (tabText.trim() == tabName) {
				// found
				foundTab = next;
				break;
			}
		}
		if (!next || (next.length == 0)) {
			break;
		}
		i++;
	}
	return foundTab;
}

function actionControl(action, selectedItem) {
	switch (action) {
	case "MANAGE":
		manageDevice(action, selectedItem);
		break;
	case "HISTORY":
		historyDevice(action, selectedItem);
		break;
	case "LIVE":
		liveDevice(action, selectedItem);
		break;
	case "WRITE_BACK":
		writeBackDevice(action, selectedItem);
		break;
	}
}

var editParameterOfThisDevice;
var mapParametersToThisDevice;

function loadDeviceTree(deviceUrl) {
	//console.log(">>deviceUrl okis: "+deviceUrl);
	//Kendogrid for device list
			
	var grid = $("#mainpage_grid").kendoGrid({
		height: 380,
		resizable: true,
		dataSource: {
	        type: "json",
	        transport: {
	            read: "ajax" + deviceUrl
	        },
//	        data: da,
					 schema: {
				            data: function(response) {
				            	
				            	//console.log("DATA: " + JSON.stringify(response));
	
				               if (response.entity != null) {
				            	   var responseData = response.entity;				            	   				            	 				            	   
				                    return responseData;
				                }
				               
				            },				            
				            total: function(response) {
				                return $(response.entity).length;
				            },
				            model: {
				                fields: {
				                	sourceId: {
				                        type: "string"
				                    },
				                    datasourceName: {
				                        type: "string"
				                    }
				                    ,
				                    tags: {
				                        type: "json"
				                    }
				                    ,
				                    type: {
				                        type: "json"
				                    }
				                    ,
				                    networkProtocol: {
				                        type: "json"
				                    },
				                    protocol: {
				                        type: "json"
				                    }
				                    ,
				                    make: {
				                        type: "json"
				                    }
				                    ,
				                    model: {
				                        type: "json"
				                    }
				                    ,
				                    version: {
				                        type: "json"
				                    }
				                    
				                }
				            }
				        },
				        pageSize: 20,
				        page: 1,
				        serverPaging: false,
				        serverFiltering: false,
				        serverSorting: false,
				        sort: { field: "sourceId", dir: "asc" }
				    },
				    selectable: "row",
				    sortable: {
				        mode: "single",
				        allowUnsort: true
				    },
			        detailInit: getConfigurations,
				    pageable: {
				        refresh: true,
				        pageSizes: true,
				        previousNext: true
				    },
				    columnMenu: false,
				    columns: [
					     {
					         field: "sourceId",
					         title: "Source Id",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Source Id<strong>"
					     },  {
					         field: "datasourceName",
					         title: "Datasource Name",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Datasource Name<strong>"
					     },  {
					         field: "tags",
					         title: "Tags",
					         template: function(dataItem) {
					        	 var tagsInComma = "";
					        	 var tags = dataItem.tags;
					        	 if(tags !=undefined && tags.length !=undefined){
					        		 $(tags).each(function(index) {
										tagsInComma += tags[index].name +",";
						        	 });
					        		 return kendo.htmlEncode(tagsInComma.substring(0, tagsInComma.length-1)) ;
					        	 }
					           return "";
					         },
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Tags<strong>"
					     }
					     , {
					         field: "version.make",
					         title: "Make",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Make<strong>"
					     },{
					         field: "version.deviceType",
					         title: "Type",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Type<strong>"
					     }
					     , {
					         field: "version.model",
					         title: "Model",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Model<strong>"
					     }, {
					         field: "version.protocol",
					         title: "Protocol",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Protocol<strong>"
					     }, {
					         field: "version.version",
					         title: "Version",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Version<strong>"
					     },  {
					         field: "networkProtocol.name",
					         title: "Network Protocol",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Network Protocol<strong>"
					     }
				     ]
				    ,change : function(e){
				    	grid = e.sender;
				        var currentDataItem = grid.dataItem(this.select());
				        
				        editParameterOfThisDevice = currentDataItem.sourceId;
				        mapParametersToThisDevice = currentDataItem.sourceId;
				        
				    }
		
	}).data("kendoGrid");
	configureTagFilter();

}
function getConfigurations(e) {
	var url = getPointConfigurations.replace("{source_id}",e.data.sourceId);
	$("<div/>").appendTo(e.detailCell).kendoGrid({
		resizable: true,
		dataSource: {
	        type: "json",
	        transport: {
	            read: "ajax" + url
	        },
	       schema: {
				            data: function(response) {
				            	if (response.entity != null) {
				            	   var responseData = response.entity.configPoints;				            	   				            	 				            	   
				                    return responseData;
				                }
				               
				            },				            
				            total: function(response) {
				                return $(response.entity).length;
				            },
				            model: {
				                fields: {
				                	pointId: {
				                        type: "string"
				                    },
				                    pointName: {
				                        type: "string"
				                    }
				                    ,
				                    type: {
				                        type: "json"
				                    }
				                    ,
				                    displayName: {
				                        type: "json"
				                    }
				                    ,
				                    physicalQuantity: {
				                        type: "json"
				                    },
				                    systemTag: {
				                        type: "json"
				                    }
				                    ,
				                    precedence: {
				                        type: "json"
				                    }				                    
				                }
				            }
				        },
				        pageSize: 10,
				        page: 1,
				        serverPaging: false,
				        serverFiltering: false,
				        serverSorting: false,
				        sort: { field: "pointName", dir: "asc" }
				    },
				    selectable: "row",
				    sortable: {
				        mode: "single",
				        allowUnsort: true
				    },
				    pageable: {
				        refresh: true,
				        pageSizes: true,
				        previousNext: true
				    },
				    columnMenu: false,
				    columns: [
					     {
					         field: "pointId",
					         title: "Point Id",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Point Id<strong>"
					     },  {
					         field: "pointName",
					         title: "Point Name",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Point Name<strong>"
					     }, {
					         field: "type",
					         title: "Data Type",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Data Type<strong>"
					     }
					     , {
					         field: "displayName",
					         title: "Display Name",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Display Name<strong>"
					     },{
					         field: "physicalQuantity",
					         title: "Physical Quantity",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Physical Quantity<strong>"
					     }
					     , {
					         field: "systemTag",
					         title: "System Tag",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >System Tag<strong>"
					     }, {
					         field: "precedence",
					         title: "Precedence",
					         filterable: false,
					         headerTemplate :"<strong style='color:black ;' >Precedence<strong>"
					     }
				     ]
		
	});

}

function configureTagFilter() {
	
	$("#select_tag").kendoMultiSelect({
        change: function(e) {
            var filter = {
                logic: "and",
                filters: []
            };
            // Get the selected Values
            var values = this.value();
            $.each(values, function(i, v) {
                filter.filters.push({
                    field: "tags",
                    operator: function(item){
                    	if(item.length>0 ){
                    		for (var i=0;i<item.length;i++){
                    			if(item[i].name==v){
                    				return true;
                    			}
                    		}
                    	}
                        return false;
                    },
                    value: v.toString()
                });
            });
            var dataSource = $('#mainpage_grid').data(
                'kendoGrid').dataSource;
            dataSource.filter(filter);
            //dataSource.read();
        },
        enable: true,
        dataSource : {
			schema : {
				total : function(response) {
					return $(response).length;
				},
				model : {
					fields : {
						name : {
							type : "string",
						}
					}
				},
				errors : function(response) {
					return response.error;
				}
			}
		},
		dataTextField: "name",
		dataValueField: "name",
		animation: false
    });
	

	$.ajax({
		url : 'ajax' + getAllDeviceTags,
		dataType : 'json',
		type : "GET",
		success : function(response) {
			$("#select_tag").data("kendoMultiSelect").dataSource.data(response.entity);
		},
		error : function(xhr, status, error) {
			console.log('eeee');
		}
	});
}

function initNotifications(){
staticNotification = $("#staticNotification").kendoNotification({
                        position: {
                            pinned: true,
                             top: 300,
                          left:630
                        },
                        allowHideAfter: 1000,
                        //stacking: "down",                      
                        templates: [ {
                            type: "error",
                            template: $("#errorTemplate").html()
                        }, {
                            type: "success",
                            template: $("#successTemplate").html()
                        }]

                    }).data("kendoNotification");

}

function refreshManageGrid(){
	
	$('#mainpage_grid').data('kendoGrid').dataSource.read();
	$('#select_tag').data('kendoMultiSelect').dataSource.read();
	
}

var contextObj = {};

// function to get protocol commands through API
function loadProtocolCommands(deviceType,protocolName){

	var targetUrl = commandProtocol;
	//console.log(">>>the targetURL: " +targetUrl);
	targetUrl = targetUrl.replace("{device_type}",deviceType);
	targetUrl = targetUrl.replace("{protocol_name}",protocolName);	
	targetUrl = "ajax" + targetUrl;
	
	
	var cmds = contextObj[protocolName];
	
	if (typeof cmds != "undefined"){			
		createDataContextMenu(protocolName,cmds);	
	}else{
		
		$.ajax({
			url : targetUrl,
			dataType : 'json',
			type : "GET",
			success : function(response) {
				//console.log("loading commands...");
				createDataContextMenu(protocolName,response);
				contextObj[protocolName] = response;
			},
			error : function(xhr, status, error) {
				//console.log("no configuration");
								
				$("#mainpage_grid").on("contextmenu",function(){
				       return false;
				});
				$("#contextMenu").empty();
				var errorMessage = "No System Commands available for this source.";
				staticNotification.show({
	                message: errorMessage
	            }, "error");
				
			}
		});
	}
	
}

//create method to append ds for context menu
function addCommandDs(data){
	
	var device_type = data.version.make;
	var protocol_name = data.version.version;	
	
	loadProtocolCommands(device_type,protocol_name);
	
							
}

var ds = [];
// function to create context menu for each row data
function createDataContextMenu(protocolName,data){
	
	var tempData = [];	
	
	
	if (!jQuery.isEmptyObject(data))
    {
		
		$.each(data.entity, function(key,val){
			var cmdName = val.name;		
			tempData.push({text: cmdName});		
		});
		
		$("#contextMenu").empty();
				
		$("#contextMenu").kendoContextMenu({
		    target: "#mainpage_grid",
		    dataSource: tempData,
		    autoHide: true,
		    animation: {
	          open: {effects: "fadeIn"},
	          duration: 500
		    },
		    select: function(e) {
		    	
		    	var grid = $("#mainpage_grid").data("kendoGrid");
		    	var selectedItem = grid.dataItem(grid.select());
		    	
		    	
				var configSelection = $("li#contextMenu_mn_active").find("span").text();
				configSelection = configSelection.trim();
				
				if (configSelection == "Sync"){
					getResponse(configSelection,selectedItem.sourceId);
				}else if (configSelection == "Reboot"){
					getResponse(configSelection,selectedItem.sourceId);
				}else if (configSelection == "Point Write Request"){
				add("WRITE BACK");
				}else if (configSelection == "App Save"){
					getResponse(configSelection,selectedItem.sourceId);
				}
				
				  
	          }
		});
			
		loadGlyphicons();	
		
	}
	
	ds = tempData;
	
}

// function to call Server Command Response
function getResponse(commandRequest,sourceId){
	
    var targetUrl = getSysCmdsUrl;	
    ////console.log(">>>>>payload: " + JSON.stringify(payload));
    targetUrl = targetUrl.replace("{source_id}",sourceId);
    var payload = [{
		"command" : "0x20",    		
		"customSpecs" : {
			"commandCode": commandRequest
		}
    }];
        
		$.ajax({
			url : "ajax" + targetUrl,
			dataType : 'json',
			contentType: "application/json; charset=utf-8",
			type : "POST",
			data: JSON.stringify(payload),
			success : function(response) {
				//console.log(">>>>>response: " + JSON.stringify(response));
				$.each(response.entity.writeBackResponses, function (){
					var status = this.status;
					var remarks="";
					if (status == 'FAILURE'){
						remarks = this.remarks;
						staticNotification.show({
			                message: "Failed on Source ID " + response.entity.sourceId + ". " + remarks + "."
			            }, "error");		
						
					}else{
						staticNotification.show({
			                message: "Source ID "+response.entity.sourceId+" is now successfully queued."
			            }, "success");		
						
					}
					
					
				});
				
				
				
										
			},
			error : function(xhr, status, error) {
				//console.log("no configuration");
				
			}
		});
	
}

// function to load page to div element
function loadPage(controller){
	if (controller!="" || controller!=undefined){
		createBreadcrumbs(controller);
		switch (controller) {
		
		case "devicetab":
			$("#panelHeaderTitle").text("Device");
			$("#contentModule").load(controller);	
			break;
		case "configTemplate":
			$("#panelHeaderTitle").text("Configuration");
			$("#contentModule").load(controller);	
			break;
		case "writebacklog":
			$("#panelHeaderTitle").text("Write Back Log");
			$("#contentModule").load(controller);	
			break;
		case "DEVICE":
			$("#contentModule").load("devicetab");
			$("#panelHeaderTitle").text("Device");
			break;
		case "MANAGE":
			$("#contentModule").load( "managedevice?sourceId="+ selectedItem.sourceId);
			$("#panelHeaderTitle").text("Manage");
			break;
		case "LIVE":
			$("#contentModule").load( "livetracking?sourceId="
					+ selectedItem.sourceId + "&datasourceName="
					+ selectedItem.datasourceName);
			$("#panelHeaderTitle").text("Live Tracking");				
			break;
		case "HISTORY":			
			$("#contentModule").load( "historytracking?sourceId="
							+ selectedItem.sourceId + "&datasourceName="
							+ selectedItem.datasourceName);
			$("#panelHeaderTitle").text("History Tracking");						
			break;
		case "WRITE":
			$("#contentModule").load( "writeback?sourceId=" + selectedItem.sourceId
					+ "&datasourceName=" + selectedItem.datasourceName);								
			break;

		case "ADD":
			$("#contentModule").load("createdevice");
			$("#panelHeaderTitle").text("Add Device");
			break;
		case "createdevice":
			$("#contentModule").load("createdevice");
			$("#panelHeaderTitle").text("Add Device");
			break;	
			
		case "WRITE BACK":
			$("#contentModule").load("writebackbatch?device="+ selectedItem.deviceId
			+ "&sourceId=" + selectedItem.sourceId);
			$("#panelHeaderTitle").text("Device Write Back");
			break;
		
		case "MAP PARAMETER":
			$("#contentModule").load("mapParameterConfig");
			$("#panelHeaderTitle").text("Map Parameter");
			break;
		
		case "PARAMETER CONFIGURATION":
			$("#contentModule").load("parameterDeviceConfiguration");
			$("#panelHeaderTitle").text("Parameter Configuration");
			break;
			
		case "createConfigTemplate":
			$("#contentModule").load("createConfigTemplate");
			$("#panelHeaderTitle").text("Create New Template Configuration");
			break;
			
		case "parameterConfiguration":
			$("#contentModule").load("parameterConfiguration");
			$("#panelHeaderTitle").text("Create New Parameter Configuration");
			break;	
			
		case "mapParameterEdit":
			$("#contentModule").load("mapParameterEdit");
			$("#panelHeaderTitle").text("Edit Parameter Mapping");
			break;
			
		case "parameterView":
			$("#contentModule").load("parameterView");
			$("#panelHeaderTitle").text("Parameter View");
			break;
			
		};
		
	}	
}

// function to create breadcrumbs
function createBreadcrumbs(controller){
	// remove existing items inside breadcrumb
	$(".breadcrumb").empty();
	
	// create breadcrumb based from controller
	switch (controller){
	
	// main home breadcrumbs
	case "devicetab":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" class="active">Device Home</a></li>');
		break;
		
	case "configTemplate":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'configTemplate\')" class="active">Configuration Home</a></li>');
		break;
		
	case "writebacklog":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'writebacklog\')" class="active">Write Back Log</a></li>');
		break;	
	
	// device page breadcrumbs
	case "MANAGE":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Manage</li>');
		break;
	case "managedevice":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Manage</li>');
		break;
		
	case "LIVE":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Live Tracking</li>');
		break;
	case "livetracking":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Live Tracking</li>');
		break;	
		
		
	case "HISTORY":			
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">History Tracking</li>');
		
		break;
	case "historytracking":			
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">History Tracking</li>');
		
		break;
	case "WRITE BACK":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Write Back</li>');								
		break;
	case "writebackbatch":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Write Back</li>');								
		break;	

	case "ADD":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Add Device</li>');
		break;
		
	case "createdevice":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Add Device</li>');
		break;

	case "mapParameterConfig":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Map Parameter</li>');
		break;
	
	case "parameterDeviceConfiguration":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
		$(".breadcrumb").append('<li class="active">Parameter Configuration</li>');
		break;
		
	// configuration page breadcrumbs
	case "createConfigTemplate":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'configTemplate\')" >Configuration Home</a></li>');
		$(".breadcrumb").append('<li class="active">Create New Template Configuration</li>');
		break;
		
	case "parameterConfiguration":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'parameterView\')" >Parameter View Home</a></li>');
		$(".breadcrumb").append('<li class="active">Create New Parameter Configuration</li>');
		break;	
		
	case "mapParameterEdit":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'configTemplate\')" >Configuration Home</a></li>');
		$(".breadcrumb").append('<li class="active">Edit Mapped Parameters</li>');
		break;
		
	case "parameterView":
		$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'parameterView\')" >Parameter View Home</a></li>');
		break;
		
	};
	
	
}

