<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="getProtocolPoints"
	expression="@propertyConfigurer.getProperty('mc.device.getAllPointsByProtocolVersion')" />
<spring:eval var="getSourceParams"
	expression="@propertyConfigurer.getProperty('mc.protocol.getSourceParams')" />
<spring:eval var="getQueuedCommands"
	expression="@propertyConfigurer.getProperty('mc.protocol.getQueuedCommands')" />
<spring:eval var="executeCommands"
	expression="@propertyConfigurer.getProperty('mc.protocol.executeCommands')" />
<spring:eval var="getLatestData"
	expression="@propertyConfigurer.getProperty('mc.data.getLatestData')" />
<spring:eval var="wsUrl"
	expression="@propertyConfigurer.getProperty('mc.websocket.connection')" />

<script src="resources/javascript/livedeviceupdate.js"></script>
<script src="resources/javascript/websocket/WebORB.js"></script>

<!-- Required for IE6/IE7 cross-origin support -->
<meta name="kaazing:postMessageBridgeURL"
	content="PostMessageBridge.html">

<meta name="kaazing:postMessageBridgeURL"
	content="PostMessageBridge.html">
	
	<div class="container2 theme-showcase dc_main" role="main" style="margin: 0px 10px;">
		<section class="dc_maincontainer">
			<div class="page-header">
				<h1>Write Back</h1>
			</div>
			<form role="form" action="device_home" id="device_home" method="get"></form>
			<span id="writebackErrorNotification"></span>
			<span id="writebackSuccessNotification"></span>
			<input type="text" value="${sourceId}" id="wbSrcId" hidden="true" />
			
			<div>
		<div class="gx-toolbar-action">
		
				<section class="text-left pull-left" style="padding-top:12px;"><label>Selected Device:</label>${sourceId}</section>
	
				<!-- 			<label>Device:</label> -->
				<%-- 			<%=request.getParameter("device")%> --%>
			
				<aside class="text-right pull-right">
					<button id="rebootButton" name="Reboot" class="k-button btn dc_btn_secondary glyphicon glyphicon-refresh"  onclick="promptReboot()">Reboot</button>
				</aside>
				<aside class="text-right pull-right">
					<button id="writeToDevice" class="k-button btn dc_btn_secondary glyphicon glyphicon-saved">Write to Device</button>
				</aside>
		</div>
		<div class="row">
			<div class="col-md-12">
			<div id="writebackgrid" ></div>
			</div>

		</div>
		<div class="row">
			<section class="col-md-12 text-right dc_groupbtn">
				<!--<button id="writeToDevice" class="btn btn-primary">Write to Device</button>-->
			<button id="cancelBatchOperation" class="btn btn-default">Back</button>
			</section>
		</div>
		<div>
			
		</div>
	</div>
		</section>
	</div>

<script type="text/x-kendo-template" id="writeBackSearchTemplate">
        <div class="toolbar" >
            <label class="category-label" for="category">Search</label>
            <input type="text" id="searchIdWriteBack" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>              
        </div>
    </script>

<script type="text/x-kendo-template" id="setPointTemplate">		

		#type = type.toUpperCase();#

		# if(status == "QUEUED" || pointAccessType=="READONLY"){#		

		# if (type == "LONG" ||type == "FLOAT" || type == "NUMBER" || type=="INTEGER"){	#
			<input id="numVal_#=pointId #" type="number" data-role="numerictextbox" disabled="true" class="numericTextBox" style="width: 100px"/>		
		# }else if (type == "BOOLEAN"){ #
			<label>True: </label><input type='radio' class="boolVal" name="booleanVal_#=pointId #" id="booleanVal_#=pointId #" disabled="true" value="true"> <label>False: </label><input type='radio' name="booleanVal_#=pointId #" id="booleanVal_#=pointId #" disabled="true" value="false">
		#}else if (type == "STRING"){#
			<input id="stringVal_#=pointId #" class="strVal" type="text" disabled="true" style="width: 100px"/>
		#}#

		#}else{#
	
		# if (type == "LONG" ||type == "FLOAT" || type == "NUMBER" || type=="INTEGER"){	#
			<input id="numVal_#=pointId #" type="number" data-role="numerictextbox" class="numericTextBox" style="width: 100px"/>		
		# }else if (type == "BOOLEAN"){ #
			<label>True: </label><input type='radio' class="boolVal" name="booleanVal_#=pointId #" id="booleanVal_#=pointId #" value="true"> <label>False: </label><input type='radio' name="booleanVal_#=pointId #" id="booleanVal_#=pointId #" value="false">
		#}else if (type == "STRING"){#
			<input id="stringVal_#=pointId #" class="strVal" type="text" style="width: 100px"/>
		#}#

		#}#

    </script>

<script type="text/x-kendo-template" id="priorityTemplate">	
		#status = status.toUpperCase();#
		# if(status == "QUEUED" || pointAccessType=="READONLY"){#
			<input id="priorityVal_#=pointId #" type="number" class="numericTextBox" data-role="numerictextbox" style="width: 100px" disabled="true"/>
		#}else{#
			<input id="priorityVal_#=pointId #" type="number" class="numericTextBox" data-role="numerictextbox" style="width: 100px"/>
		#}#
					 				
    </script>

<script id="write-confirmation" type="text/x-kendo-template">
    <p class="delete-message">Are you sure?</p>

    <button class="write-confirm k-button" style="width: 80px;">Yes</button>
    <button class="write-cancel k-button" style="width: 80px;">No</a>
	</script>

<script id="errorWriteBack" type="text/x-kendo-template">
                <div class="errorWriteBackMessage">
                  <!-- <img src="resources/themes/gx/images/icons/w/error-icon.png" /> -->            
                    <p>#= message #</p>
                </div>
          </script>

<script id="successWriteBack" type="text/x-kendo-template">
                <div class="successWriteBackMessage">
                   <!-- <img src="resources/themes/gx/images/icons/w/success-icon.png" /> -->
                    <p>#= message #</p>
                </div>
            </script>


<script>
	var wbSuccessNotification;
	var wbErrorNotification;
	var editFlag=0;
	var liveUpdateUrl = "${wsUrl}";
	
	function initNotifications(){
	wbErrorNotification = $("#writebackErrorNotification").kendoNotification({
	                        position: {
	                            pinned: true,
// 	                             top: 300,
// 	                          	 left:630,
								 top: 300,
                            	 left: 65
	                        },
	                        allowHideAfter: 0,
	                        stacking: "down",
	                        templates: [ {
	                            type: "error",
	                            template: $("#errorWriteBack").html()
	                        }]
	
	                    }).data("kendoNotification");
	                    
	    wbSuccessNotification = $("#writebackSuccessNotification").kendoNotification({
	                        position: {
	                            pinned: true,
// 	                             top: 300,
// 	                          	 left:630
								 top: 300,
                            	 right: 65
	                        },
	                        allowHideAfter: 0,
	                        stacking: "down",
	                        templates: [ {
	                            type: "success",
	                            template: $("#successWriteBack").html()
	                        }]
	
	                    }).data("kendoNotification");                
	                    
	
	}
	initNotifications();
	var sourceId = '${sourceId}';
	
    $(document).ready(function () {
    			webSocketConnect();
 				$(document).on('click', '#writebackgrid' , function() {
				    highlightQueuedRows();
				});
				
				
                	$(document).on('keyup', '.numericTextBox', function(event) {
						   var v = this.value;
						   if($.isNumeric(v) === false) {
						        //chop off the last char entered
						        this.value = this.value.slice(0,-1);
						   }
						   editFlag++;
							
					});
						
						
						
                    //initialized date picker.  
                     
                    $("#fromCalendar").kendoDatePicker();
                    $("#toCalendar").kendoDatePicker();
                    $("button").kendoButton();
                    
                 
                          
             	  // call write to device
	             $("#writeToDevice").click(function(e){
	             	
	                e.preventDefault();
    
    				var payload =  writeToDevice();
			                   
                   if (payload.length!=0){             	
	             		   var kendoWindow = $("<div />").kendoWindow({
				            title: "Confirm",
				            height:100,
				            width: 190,
				            resizable: false,
				            modal: true
				        });
				        
				     kendoWindow.data("kendoWindow")
			        .content($("#write-confirmation").html())
			        .center().open();   
				    
				     kendoWindow
			        .find(".write-confirm,.write-cancel")
			            .click(function() {
			                if ($(this).hasClass("write-confirm")) {
			                   writeData(sourceId,payload);
			                   
			                }
			                
			                kendoWindow.data("kendoWindow").close();
			            })
			            .end()
				        
	             		
	             	}else{
	             		staticNotification.show({
			               message: "No Modified Set points."
			           }, "error");
	             	}
    
	             });
	             
	              // cancel
	             $("#cancelBatchOperation").click(function(e){
	             	editFlag=0;
	             	 e.preventDefault();
    
    				$("#writebackgrid .strVal, .numericTextBox").each(function() {
    				
					    if($(this).val()!=""){
					    	editFlag++;
					    }
					});
					
					$("#writebackgrid .boolVal").each(function() {

					    if($(this).is(':checked')){
					    	editFlag++;
					    }
					});
        				
    				if (editFlag>0){
    				
				    var kendoWindow = $("<div />").kendoWindow({
				            title: "Confirm",
				            height:100,
				            width: 190,
				            resizable: false,
				            modal: true
				        });
				        
				     kendoWindow.data("kendoWindow")
			        .content($("#write-confirmation").html())
			        .center().open();   
				    
				     kendoWindow
			        .find(".write-confirm,.write-cancel")
			            .click(function() {
			                if ($(this).hasClass("write-confirm")) {
			                	
			                	//loadPage("devicetab");	
			                	cancel();		                	
			                }
			                
			                kendoWindow.data("kendoWindow").close();
			            })
			            .end()
    				}else{
    					
    					//loadPage("devicetab");
    					cancel();
    				}
    
	             });

            });
            
            var queuedCmds = []; 
		    // function to load queued commands for specific id
			function loadQueuedCommands(){
			
				
				//var sourceId = "351756051523999";
				
				var targetUrl = "${getQueuedCommands}";
				targetUrl = "ajax/" + targetUrl.replace("{source_id}",sourceId);
							
				var data;
				$.ajax({
					url : targetUrl,
					dataType : 'json',
					type : "GET",
					async: false,
					success : function(response) {
						//console.log("//JSON loadQueued: " + JSON.stringify(response));
						updateQueuedCommands(response);
								
					},
					error : function(xhr, status, error) {
						//console.log("no configuration");						
						highlightQueuedRows();																					
					}
				});
				
				return data;
			}
			
					    
		    // function to load params for specific id
			function loadParams(){
			
			var setPointTemplate = kendo.template($("#setPointTemplate").html()); 
             	 var setPriorityTemplate = kendo.template($("#priorityTemplate").html());
				
				
				var targetUrl = "${getSourceParams}";
				targetUrl = "ajax/" + targetUrl.replace("{source_id}",sourceId);
				
				$("#writebackgrid").kendoGrid({
						templateSettings: {
					    	paramName : "gridData"
					 	},
						//toolbar: kendo.template($("#writeBackSearchTemplate").html()),
						height: 500,
						resizable: true,
						dataSource: {
					        type: "json",
					        transport: {
					            read: {
					            url: targetUrl,
					           	complete: function(){
					           			// disable write button if no data in grid
					           			var grid = $("#writebackgrid").data("kendoGrid");
								    	if (grid.dataSource.data().length === 0) {
								    		 $("#writeToDevice").kendoButton({
										        enable: false
										    }).data("kendoButton");
								    		 $("#rebootButton").prop('disabled', true);
								    	}
								    	else{
								    		loadQueuedCommands();
						  					loadLatestData();
									    }                                   

					           		}
					            } 					           
					        },
				            error : function(xhr, status, error) {
								staticNotification.show({message : "No Configurations found"}, "error");
							},
					        
									 schema: {
								            data: function(response) {
								            	//console.log("//response: " + JSON.stringify(response));
								            	
							            		if(response.entity == undefined){
													var errorMessage = response.errorMessage.errorMessage;
													staticNotification.show({message : errorMessage}, "error");
												}
							            		else if (response.entity.configPoints != undefined) {
								            	   return response.entity.configPoints;
								                } else {
								  					staticNotification.show({
								                             message:response.errorMessage.errorMessage
								                         }, "error");
								                }
								               
								            },
								            total: function(response) {
								                return $(response.entity.configPoints).length;
								            },
								            model: {
								                id : 'pointId'
								            }
								        },
								        pageSize: 20,
								        page: 1,
								        serverPaging: false,
								        serverFiltering: false,
								        serverSorting: false
// 								        ,
// 								        sort: { field: "sourceId", dir: "asc" }
								    },
								    //selectable: "row",
								    height: 480,
								    sortable: {
								        mode: "single",
								        allowUnsort: true
								    },
								    filterable : {
										mode : "row",
										extra: false
									},
								    pageable: {
								        refresh: true,
								        pageSizes: true,
								        previousNext: true
								    },
								    columnMenu: false,
								    columns: [
		                            { field: "pointName", title: "Parameter", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Parameter<strong>" },		                            
		                            { field: "type", title: "Type", width: 40, editable: false, headerTemplate :"<strong style='color:black ;' >Type<strong>" },
		                            { field: "requestId", title: "Type", width: 40, editable: false, headerTemplate :"<strong style='color:black ;' >Request Id<strong>" },
		                            { field: "pointAccessType", title: "Point Access Type", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Point Access Type<strong>"  },
		                            {
		                            	title: "Last Updated",
		                            	headerTemplate :"<strong style='color:black ;' >Last Updated<strong>",
		                            	columns : [
		                            		{ field: "currentVal", title: "Type", width: 60, editable: false, headerTemplate :"<strong style='color:black ;' >Current Value<strong>" },
		                            		{ field: "deviceTime", title: "Type", width: 60, editable: false, headerTemplate :"<strong style='color:black ;' >Last Updated<strong>" }	
		                            	]
		                            },
		                            
		                            {
		                            	title: "Last Executed",
		                            	headerTemplate :"<strong style='color:black ;' >Last Executed<strong>",
		                            	columns: [
		                            	{ field: "value", title: "Current Set Point", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Current Set Point<strong>"  },
			                            { field: "lastUpdated", title: "Requested Time", width: 60 , editable: false, headerTemplate :"<strong style='color:black ;' >Requested Time<strong>" },                          
			                            { field: "currentPriority", title: "Current Priority", width: 50, editable: false , headerTemplate :"<strong style='color:black ;' >Current Priority<strong>" }
		                            	]
		                            },
		                            
		                            {
		                            	title: "New Set Point",
		                            	headerTemplate :"<strong style='color:black ;' >New Set Point<strong>",
		                            	columns: [		                            	                          
				                            { 
				                              field: "newSetPoint",
				                              title: "New Set Point", width: 60,
				                              template: setPointTemplate,
				                              editor: setPointTemplate,
				                              editable: true,
				                              headerTemplate :"<strong style='color:black ;' >New Set Point<strong>"     
				                            },
				                            { field: "newPriority",
				                             title: "New Priority",
				                             width: 60,
				                             editable: true,
				                             template: setPriorityTemplate,
				                             editor: setPriorityTemplate,
				                             headerTemplate :"<strong style='color:black ;' >New Priority<strong>"     
				                              }
		                            	]
		                            }
		                            
                        			]
						
					}).data("kendoGrid");
				
					                                      
                   
                   $("#searchIdWriteBack").keyup(function () {                   
	                var val = $("#searchIdWriteBack").val();
	                $("#writebackgrid").data("kendoGrid").dataSource.filter({
	                    logic: "or",
	                    filters: [
	                    	{
	                            field: "pointName",
	                            operator: "contains",
	                            value: val
	                        },
	                        {
	                            field: "pointName",
	                            operator: "contains",
	                            value: val
	                        },
	                        {
	                            field: "type",
	                            operator: "contains",
	                            value: val
	                        },
	                        {
	                            field: "value",
	                            operator: "contains",
	                            value:val
	                        },
	                        {
	                            field: "currentPriority",
	                            operator: "contains",
	                            value:val
	                        },
	                        {
	                            field: "lastUpdated",
	                            operator: "contains",
	                            value:val
	                        },
	                        {
	                            field: "pointAccessType",
	                            operator: "contains",
	                            value:val
	                        }
	                    ]
	                });
 
 					highlightQueuedRows();
            	});
            	
                    highlightQueuedRows();		
				}            
             loadParams();
             
             
             // function write to device
             function writeToDevice(){
             	
             	//get elements from parameter until the last edited column
				var displayedData = $("#writebackgrid").data().kendoGrid.dataSource.view();
             	var displayedDataAsJSON = JSON.stringify(displayedData);
             	
             	//used to get the html element input type in each row
             	var grid = $("#writebackgrid").data("kendoGrid");
             	var count = grid.dataSource.total();
         
				var payload = [];
				
				// build payload	
				$.each(displayedData,function(){
				//console.log("//displayedData: " + JSON.stringify(displayedData));
				
					if (this.hasOwnProperty("pointAccessType")){
					
						if (this.pointAccessType=="WRITEABLE"){
												
							var parameter = this.displayName;
							var pointId = this.pointId;
							var type = this.type.toUpperCase();
							
							var paramVal = "";
							var priority = $('#writebackgrid').find('#priorityVal_'+pointId).val();
							
							if (type == 'BOOLEAN'){
								
								paramVal = $('input[name="booleanVal_'+pointId+'"]:checked').val();
														
								if (typeof paramVal =="undefined"){
									paramVal = "";
								}
							
							}else if (type == "LONG" ||type == "FLOAT" || type == "NUMBER" || type=="INTEGER"){
								paramVal = $('#writebackgrid').find('#numVal_'+pointId).val();
							
							}else if(type == "STRING"){
								paramVal = $('#writebackgrid').find('#stringVal_'+pointId).val();
							
							}
																				
							// if no value has been entered - exclude in the update
							if (paramVal != "" || priority != ""){										
								payload.push({
									"command" : "0x22",
									"pointId": pointId,
									"value": paramVal,
									"customSpecs" : {
										"priority": priority
									}					
								});					
							}
						
						}
						
					
					}
				
					
				}); // end of building payload
				
				return payload;
             }
             
             // function to take payload and src id and invoke api for writing
             function writeData(srcId,payload){
             
             var targetUrl = "${executeCommands}";
			 targetUrl = "ajax/" + targetUrl.replace("{source_id}",srcId);
             
             var data;
				$.ajax({
					url : targetUrl,
					dataType : 'json',
					contentType: "application/json; charset=utf-8",
					type : "POST",
					data: JSON.stringify(payload),
					success : function(response) {
					
					var responseJson = response.entity;
					returnInfoMessage(payload,responseJson);						
					},
					error : function(xhr, status, error) {
						
						
					}
				});
             
             }
         
         	// function to return response if success or not
         	function returnInfoMessage(payload,response){
         	        	
			var failedStatus = "<div style='display: inline-block;  font-size: 14px;'><table><tr><th colspan=3>Failure on the following Set Points: </th></tr><tr><th style='width: 120px'>Parameter</th><th style='width: 120px'>Point ID</th><th style='width: 140px'>Remarks</th></tr>"; 
			var successStatus = "<div style='display: inline-block;  font-size: 14px;'><table><tr><th colspan=3>Successfully queued the Set Points: </th></tr><tr><th style='width: 120px'>Parameter</th><th style='width: 120px'>Point ID</th><th style='width: 140px'>Remarks</th></tr>";
			
			var failFlag=0;
			var successFlag=0;
				
			var failedParams = [];
				
         	$.each(response.writeBackResponses,function(){
         		var status = this.status;
				var command = this.command;
				var remarks = this.remarks;
				var pointId = this.pointId;
				var displayedData = $("#writebackgrid").data().kendoGrid.dataSource.view();
				var dataItem = $("#writebackgrid").data("kendoGrid").dataSource.get(pointId);
								
				
				if (status == "FAILURE"){
					failFlag++;
					//failedStatus = failedStatus + "Failed on <label>Parameter: </label>" + parameter + ". <label>Remarks: </label> " + remarks + " <label>Command: </label> " + command + " </div>";
					failedStatus = failedStatus + "<tr><td>"+dataItem.pointName+"</td><td>"+pointId+"</td><td>"+remarks+"</td></tr>";
					
	  			}else if (status == "QUEUED"){
	  			
	  				updateSuccessValues(this,pointId);
	  				
					successFlag++;												
					//successStatus = "Successfully queued set point for <label>Parameter: </label> " + parameter + ". <label>Command: </label> " + command + "<br /></div>" ;
					successStatus = successStatus + "<tr><td>"+dataItem.pointName+"</td><td>"+pointId+"</td><td>"+status+"</td></tr>";
       			}
       						
         	});
         	
         	failedStatus = failedStatus + "</table></div>"; 
			successStatus = successStatus + "</table></div>"; 
         	
         	//failed parameters
         	if (successFlag>0){
         		 wbSuccessNotification.show({
		               message: successStatus
		           }, "success");
         	}
         	
         	//success parameters
         	if (failFlag>0){
         		wbErrorNotification.show({
		               message: failedStatus
		           }, "error");	
         	}
         	    
         	    
         	}
         
              // convert local time to UTC and get in milliseconds         
	         function convertTimeToUtc (localDate){
	         
		          var dateToConvert = new Date(localDate);
		                   
		          var month = dateToConvert.getMonth(); //months from 1-12
				  var day = dateToConvert.getDate();
				  var year = dateToConvert.getFullYear();
				  		 
				  return Date.UTC(year,month,day);
			  
	         }
	         
	         // give colors to queued commands
	         // *hint: row with disabled inputs are ones queued
	         function highlightQueuedRows(){
	         	$("#writebackgrid input:disabled").closest('tr').css("background-color","#B2FFB2");
	         	$("#writebackgrid").find("td:contains('READONLY')").closest('tr').css("background-color","rgb(195, 195, 195)");		 
	         }
	         
	         // function to update queued commands
	         function updateQueuedCommands(data){
	        
		         $.each(data.entity,function(){
		         	var pointId = this.pointId;
		         	var dataItem = $("#writebackgrid").data("kendoGrid").dataSource.get(pointId);
		         	var value = this.value;
		         	var requestedAt = kendo.toString(new Date(this.requestedAt), "G");
		         	var status = this.status;
		         	var customSpecs = this.customSpecsJSON;
		         	var reqId = this.requestId;
		         	var priority = JSON.parse(customSpecs).priority;
		         	
		         	if (typeof dataItem != "undefined"){
		         	dataItem.set("value",value);
					dataItem.set("lastUpdated",requestedAt);
					dataItem.set("currentPriority",priority);
					dataItem.set("status",status);
					dataItem.set("requestId",reqId);
		         	}
		         	
		         	
		         	
		         });
	         highlightQueuedRows();
	         
	         }
	         
	         // function update datagrid
	         function updateSuccessValues(response,pointId){
	         		 var dataItem = $("#writebackgrid").data("kendoGrid").dataSource.get(pointId);
	         		 					         
			         var type = this.type;
			         var requestedAt = kendo.toString(new Date(response.requestedAt), "G");	         	         
			         var paramVal = "";					 
					 if (typeof dataItem != "undefined"){	
		         		dataItem.set("value",response.value);
						dataItem.set("lastUpdated",requestedAt);
						dataItem.set("currentPriority",response.customSpecs.priority);
						dataItem.set("status",response.status);
						dataItem.set("requestId",response.requestId);
					 }
					
	         		highlightQueuedRows();
	         }
	         
	         // function to get latest data of a datasourceId
			function loadLatestData(){
				
				var targetUrl = "${getLatestData}";
				targetUrl = "ajax/" + targetUrl.replace("{source_id}",sourceId);
							
				var data;
				$.ajax({
					url : targetUrl,
					dataType : 'json',
					type : "GET",
					success : function(response) {
						
						updateLatestData(response);
								
					},
					error : function(xhr, status, error) {
						//console.log("no configuration");						
						highlightQueuedRows();																					
					}
				});
				
				return data;
			}
			
			// function to update latest data in grid
	         function updateLatestData(data){
	        	
	        	var gview = $("#writebackgrid").data("kendoGrid");		         	
		        var gridData = gview.dataSource.data();
		         $.each(data.entity,function(){		         	
					// Find those records that have requestId
					// and return them in `res` array
					var pointName = this.customTag;				
					var res = $.grep(gridData, function (d) {
					
					return d.displayName == pointName;
					  
					});
		         	
					//console.log(JSON.stringify(res));
					if(res !=undefined){
						var dataItem =gview.dataSource.get(res[0].id);
						dataItem.set("currentVal",this.data);
						dataItem.set("deviceTime",kendo.toString(new Date(this.deviceTime), "G"));
					}	
	         	
		         });
	         highlightQueuedRows();
	         
	         }
	         
	        // function to handle data change from websocket 
			var handleMessage = function(message) {
				console.log("handling write back response data  : ");
				content = message.body[0];
				var data = $.parseJSON(content);
				console.log("data received for processing...");
				updateGridStatusFromWS(data);			
			};
				
			// function to update data row in grid from websocket response after success/fail
			function updateGridStatusFromWS(data){
			var requestedTime = kendo.toString(new Date(data.time), "G");
			var status = data.status;
			var reqId = data.requestId;
			
			if (data.hasOwnProperty('remarks')){
			var remarks = data.remarks;
			}
						
			var grid =$("#writebackgrid").data("kendoGrid");
			var data = grid.dataSource.data();
			// Find those records that have requestId
			// and return them in `res` array
			var res = $.grep(data, function (d) {
			    return d.requestId == reqId;
			});
			
			if(res !=undefined){
				var dataItem =grid.dataSource.get(res[0].id);
				
				dataItem.set("lastUpdated",requestedTime);
				dataItem.set("status",status);
				
				if (status.toUpperCase() == "SUCCESS"){
				wbSuccessNotification.show({
		               message: "Parameter <strong>" + dataItem.pointName + "</strong> has been updated successfully."
		           }, "success");
				}else if (status.toUpperCase() == "FAILURE"){
				
				
				wbErrorNotification.show({
		               message: "Failed to update Parameter <strong>" + dataItem.pointName + "</strong>. " + remarks
		           }, "error");
				}
				
				
				
			}
			//console.log(test);
			highlightQueuedRows();
			}

			//function to reboot
			function promptReboot(){
				if (confirm('Device will get reboot. Do you wish to continue?')) {
					executeServerComman('Reboot');
				} else {
				    // Do nothing!
				}
			}
			

			// function to call Server Command Response
			function executeServerComman(commandRequest){
				
				var targetUrl = "${executeCommands}";
				targetUrl = "ajax/" + targetUrl.replace("{source_id}",sourceId);
				
			    var payload = [{
					"command" : "0x20",    		
					"customSpecs" : {
						"commandCode": commandRequest
					}
			    }];
			        
				$.ajax({
					url : targetUrl,
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
								var errorMessage =commandRequest+" Failed on Source Id " + response.entity.sourceId + ". " + remarks + ".";
								staticNotification.show({message : errorMessage}, "error");
								
							}else{
								var errorMessage =commandRequest+" on Source Id"+response.entity.sourceId+" is now successfully queued.";
								staticNotification.show({message : errorMessage}, "error");
							}
							
						});
												
					},
					error : function(xhr, status, error) {
						var errorMessage = commandRequest+" on Source Id"+response.entity.sourceId+" is now successfully queued."
						staticNotification.show({message : errorMessage}, "error");
					}
				});
				
			}
			function cancel() {
				$('#device_home').submit();
			}
	         
    </script>