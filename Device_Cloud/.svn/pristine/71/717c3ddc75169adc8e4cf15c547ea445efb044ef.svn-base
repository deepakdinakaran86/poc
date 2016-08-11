/**
 * parameterview.js 7/13/2015 - PCSEG339 Kenny Chavez
 */

$(document).on('click', '#viewParameterGrid .k-i-refresh', function() {
	$("#viewParameterGrid").data('kendoGrid').dataSource.data([]);
	$("#viewParameterGrid").data('kendoGrid').dataSource.data(data);
	// getAllLogData();
});

var data = [ {
	parameter : "Param1",
	physicalQuantity : "PhysicalQ1",
	dataType : "String",
	action : ""
}, {
	parameter : "Param2",
	physicalQuantity : "PhysicalQ2",
	dataType : "Boolean",
	action : ""
} ];

var grid = $("#viewParameterGrid")
		.kendoGrid(
				{
					dataSource : {
						pageSize : 20,
						 transport: {
	                		 read: function(options) { 
	                			 
	                			 var targetUrl = "ajax" + getAllParamsUrl;
	                			 targetUrl = targetUrl.replace("{sub_id}",1); // change this to token in the future
	                			 
	                			 
	                			 $
	                				.ajax({
	                					url : targetUrl,
	                					type : 'GET',
	                					dataType : 'JSON',
	                					success : function(response) {
	                						//console.log(response.entity);
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
					 schema: {
						 model: {
							 id: "name",
								 fields: {
									 name: { editable: true, nullable: false },
									 physicalQuantity: { editable: true, nullable: false },
									 dataType: { editable: true, nullable: false }
							 }
						 }
					 }
					},
					toolbar : kendo.template($("#configSearchTemplate").html()),
					//toolbar: [{name: "create", text: "Create New Parameter"}],
					height : 500,
					sortable : true,
					pageable : {
						refresh : true,
						pageSizes : true,
						previousNext : true
					},
					editable : {
						mode: "popup"
					},
					scrollable : true,
					columns : [
							{
								field : "name",
								title : "Parameter",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Parameter<strong>"
							},
							{
								field : "physicalQuantity",
								title : "Physical Quantity",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Physical Quantity<strong>"
							},
							{
								field : "dataType",
								title : "Data Type",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Data Type<strong>"
							},
							{
								command : [ {
									name : "edit-params",
									text : " ",
									width: 200,
									click : editParameters,
									imageClass : "k-icon k-edit"
								}, {
									name : "destroy-params",
									text : " ",
									click : deleteParameters,
									imageClass : "k-icon k-delete"
								} ],
								title : "Error Message",
								width : 50,								
								headerTemplate : "<strong style='color:black ;' >Action<strong>"
							} ]
				});

$("#searchTemplate").keyup(function() {
	var val = $("#searchTemplate").val();
	$("#viewParameterGrid").data("kendoGrid").dataSource.filter({
		logic : "or",
		filters : [ {
			field : "name",
			operator : "contains",
			value : val
		}, {
			field : "physicalQuantity",
			operator : "contains",
			value : val
		}, {
			field : "dataType",
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

$(".createParameter").on('click', function() {
	 
	 var kendoWindow = $("<div />").kendoWindow({
		    title: "Add Parameter",
		    resizable: false,
		    modal: true,
		    deactivate: function() {
		        this.destroy();                                           
		    }
		});
	 
	 kendoWindow.data("kendoWindow")
	    .content($("#createParameterForm").html())
	    .center().open(); 
	 
	 var targetUrl = "ajax" + getAllDataTypesUrl;

	 
	 $("#dataType").kendoComboBox({
		 dataSource: {
        	 type: "json",
        	 transport: {
    		    read: {
    		      url: targetUrl	    		      
    		    } 
        	 },
        	 schema : {
					data : function(response) {
						if (response.entity != null) {								
							var finalResponse = response.entity;
							finalResponse.unshift({"name":""});							
							return finalResponse;
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
     index: 0
     });
	 
	// for dialog add confirmation
	$("#createParameter").click(function(){
	
		var paramName = $("#paramName").val();
		var phy_quantity = $("#phy_quantity").val();
		var dataType = $("#dataType").val();
		
		validateInputs(paramName,dataType,phy_quantity,kendoWindow);
		
	});

	$("#cancelCreateParameter").click(function(){
		
		var hasValues = doInputsHaveValues();
		
		if (hasValues){
			
			 var kendoWindowConfirm = $("<div />").kendoWindow({
				    title: "Confirm",
				    resizable: false,
				    modal: true,
				    deactivate: function() {
				        this.destroy();                                           
				    }
				});
			 
			 kendoWindowConfirm.data("kendoWindow")
			    .content($("#parameterConfirmClose").html())
			    .center().open(); 
			 
			// for dialog delete confirmation
			$("#yesButton").click(function(){
				kendoWindow.data("kendoWindow").close();					                        	
				kendoWindowConfirm.data("kendoWindow").close();
			});

			$("#noButton").click(function(){
				kendoWindowConfirm.data("kendoWindow").close();
			});
			
		}else{
			kendoWindow.data("kendoWindow").close();			
		}
		
		
	});	
	

	$('#paramName').keypress(function (e) {
		alphanumericValidator(e,"paramName");
	});
	
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

function editParameters(){
	//next phase
	alert("Edit Parameter");
}

function deleteParameters(){
	//next phase
	alert("Delete Parameter");
}




//function to save valid parameter
function saveParameter(dataObj,kendoWindow){
	
	var url = "ajax" + saveParams;
	//var url = "/datasources/parameters";						
	
	var ds = {
			  "name": dataObj.paramName,
			  "dataType": dataObj.dataType,
			  "physicalQuantity": dataObj.phy_quantity
			};
	
	$.ajax({
		url : url,
		type : 'POST',
		dataType : 'json',
		contentType : "application/json",
		data : JSON.stringify(ds),
		success : function(response) {
			//console.log(JSON.stringify(response));
			kendoWindow.data("kendoWindow").close();
			
			staticNotification.show({
                message:"Parameter has been successfully added."
            }, "success");
			
			$('#viewParameterGrid').data('kendoGrid').dataSource.read();
			
		},
		error : function(xhr, status, error) {
			var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
			$("#errorMsg").text("*"+errorMessage);
		}
	});	
	
	
}

//function to check parameter name uniqueness
function checkParameterUniqueness(dataObj,kendoWindow){
	
	var url = "ajax" + paramUniquenessUrl;
	//var url = "/datasources/parameters/{param_name}/{sub_id}/isexist";						
	url = url.replace("{param_name}",dataObj.paramName);
	url = url.replace("{sub_id}",1);
	
	$.ajax({
		url : url,
		type : 'GET',
		dataType : 'json',
		success : function(response) {
			//console.log(JSON.stringify(response));
			
			if (response.entity.status =="SUCCESS"){
				$("#errorMsg").text("*Parameter already exists");
				return false;
			}else{ 
				// if unique, save to db
				saveParameter(dataObj,kendoWindow);
			}
			
		},
		error : function(xhr, status, error) {
			var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
			$("#errorMsg").text("*"+errorMessage);
		}
	});	
	
	
}

// function to validate inputs in create parameters
function validateInputs(paramName,dataType,phy_quantity,kendoWindow){
	var dataObj;
	
	clearInputs();	
	
	if ((!paramName=="" || !paramName==" ") && 
			 (!phy_quantity=="" || !phy_quantity==" ") && 
			 (!dataType=="" || !dataType==" ")){
		

	dataObj = {"paramName": paramName,
				   "phy_quantity": phy_quantity,
				   "dataType":dataType};
	
	// check if parameter name exists			
	checkParameterUniqueness(dataObj,kendoWindow);	
			
	}else{

		if ((paramName=="" || paramName==" ")){
			$("#errorMsgParamName").text("*Please enter Parameter Name");		
		}
		
		if ((phy_quantity=="" || phy_quantity==" ")){
			$("#errorMsgPhyQuantity").text("*Please select a Physical Quantity");				
		}
		
		if((dataType=="" || dataType==" ")){
			$("#errorMsgDataType").text("*Please select a Data Type");				
		}
		
	}
	
}

// function to clear all input fields
function clearInputs(){
	$("#errorMsgParamName").text("");
	$("#errorMsgPhyQuantity").text("");
	$("#errorMsgDataType").text("");
	$("#errorMsg").text("");
}

// function to tell whether the form has values or not
function doInputsHaveValues(){
	var hasValues = false;
	
	if ($("#paramName").val()!=="" && 
		$("#paramName").val()!==" "){

		hasValues = true;

	}else if ($("#dataType").val()!=="" && 
		$("#dataType").val()!==" "){
		hasValues = true;

	}else if ($("#phy_quantity").val()!=="" && 
		$("#phy_quantity").val()!==" "){
		hasValues = true;

	}
	
	return hasValues;
}