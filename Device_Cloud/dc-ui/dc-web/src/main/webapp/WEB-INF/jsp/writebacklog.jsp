<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:eval var="getAllLogs"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getAllLogs')" />

<style>
.k-datepicker{
	width: 250px !important;
}
</style>	

	
<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>

  <div>
        
		
					<div id="writeBackLabel" style=" padding-bottom: 20px; padding-top: 20px;">
                      <label >From</label>
                      <input id="fromCalendar" style="width:250px"/>
                      <label>To</label>
                      <input id="toCalendar" style="width:250px"/>
                      <button id="btnView" style="width:150px;padding: 3px;">View</button>
                  </div>
		
	
       
            
                <div id="writebackloggrid" style="width:100%"></div>
            
	
    </div>
 
    <script type="text/x-kendo-template" id="writeBackLogSearchtemplate">
        <div class="toolbar" >
            <label class="category-label" for="category">Search</label>
            <input type="text" id="searchIdWriteBackLog" class"k-textbox" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>
              
        </div>
    </script>	
    <script>    
                $(document).ready(function () {
                	
                	
                	$(document).on('click', '#writebackloggrid .k-i-refresh' , function() {					    
					    $("#writebackloggrid").data('kendoGrid').dataSource.data([]);         	
	         			getAllLogData();
					});
                
                    //initialized date picker.    
                    $("#fromCalendar").kendoDatePicker();
                    $("#toCalendar").kendoDatePicker();
                    $("button").kendoButton();
                    
                    loadDatePicker();
                    
                    var grid = $("#writebackloggrid").kendoGrid({
                   		dataSource: {
                   		 pageSize: 20
                   		},
                   		toolbar: kendo.template($("#writeBackLogSearchtemplate").html()),
                    	height: 400,
                        sortable: true,
                         pageable: {
					        refresh: true,
					        pageSizes: true,
					        previousNext: true
				    	},
                        scrollable: true,
                        columns: [
                            { field: "parameter", title: "Parameter", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Parameter<strong>" },
                            { field: "assetName", title: "Source Id", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Source Id<strong>" },
                            { field: "priority", title: "Priority", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Priority<strong>" },
                            { field: "status", title: "Status", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Status<strong>" },
                            { field: "requestedTime", title: "Requested Time", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Requested Time<strong>" },                            
                       		{ field: "errorMsg", title: "Error Message", width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Error Message<strong>" }     
                       ]
                    	});
                  
				$("#btnView").click(function(){
				var getStartDate = $("#fromCalendar").data("kendoDatePicker").value();
				var getEndDate = $("#toCalendar").data("kendoDatePicker").value();
				
		            if (getStartDate==null || getEndDate==null){
		            	staticNotification.show({
					               message: "Please enter valid date."
					           }, "error");
					    $("#writebackloggrid").data('kendoGrid').dataSource.data([]);
		            }else{
		            	getAllLogData();
		            }
				
				
				});


				$("#searchIdWriteBackLog").keyup(function () {                   
	                var val = $("#searchIdWriteBackLog").val();
	                $("#writebackloggrid").data("kendoGrid").dataSource.filter({
	                    logic: "or",
	                    filters: [
	                        {
	                            field: "parameter",
	                            operator: "contains",
	                            value: val
	                        },
	                        {
	                            field: "assetName",
	                            operator: "contains",
	                            value: val
	                        },
	                        {
	                            field: "priority",
	                            operator: "contains",
	                            value:val
	                        },
	                        {
	                            field: "errorMsg",
	                            operator: "contains",
	                            value:val
	                        },
	                        {
	                            field: "status",
	                            operator: "contains",
	                            value:val
	                        },
	                        {
	                            field: "requestedTime",
	                            operator: "contains",
	                            value:val
	                        },
	                        {
	                            field: "errorMsg",
	                            operator: "contains",
	                            value:val
	                        }	                       
	                    ]
	                });
 
 
            	});


            });
            
            // function to load initial date selection (based on current date - 2 days)
            function loadDatePicker(){    
            	var today = new Date();
            	var previousDay = new Date(new Date().setDate(new Date().getDate()-3));//make it 3 in the mean time      
            	var endDate = kendo.toString(kendo.parseDate(today), 'MM/dd/yyyy');
  				$("#toCalendar").data("kendoDatePicker").value(endDate);
  				
  				var startDate = kendo.toString(kendo.parseDate(previousDay), 'MM/dd/yyyy');
  				$("#fromCalendar").data("kendoDatePicker").value(startDate);
  				
  				
  				
            }
            
            // convert local time to UTC and get in milliseconds         
	         function convertTimeToUtc (localDate){
	         
	          var dateToConvert = new Date(localDate);
	                   
	          var month = dateToConvert.getMonth(); //months from 1-12
			  var day = dateToConvert.getDate();
			  var year = dateToConvert.getFullYear();
			  		 
			  return Date.UTC(year,month,day)
			  
	         }
            
            // get logs data
            function getAllLogData(){
            
            var getStartDate = kendo.toString($("#fromCalendar").data("kendoDatePicker").value(), "MM/dd/yyyy");
         	var getEndDate = kendo.toString($("#toCalendar").data("kendoDatePicker").value(), "MM/dd/yyyy");
            
//             var startTime = "1433404090044";
//             var endTime = "1433404090244";
            
	            
	          	var startTime = convertTimeToUtc(getStartDate);
	           var endTime = convertTimeToUtc(getEndDate);
	           
	           var targetUrl = "${getAllLogs}";
				targetUrl = "ajax" + targetUrl.replace("{sub_id}","1");
				targetUrl = targetUrl.replace("{start_time}",startTime);
				targetUrl = targetUrl.replace("{end_time}",endTime);
							
				var data;
				$.ajax({
					url : targetUrl,
					dataType : 'json',
					type : "GET",
					success : function(response) {
					console.log(">>JSON response: " + JSON.stringify(response));					
					createDS(response);
	
					},
					error : function(xhr, status, error) {
					var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
						staticNotification.show({
		               message: errorMessage
		           }, "error");
						$("#writebackloggrid").data('kendoGrid').dataSource.data([]);
					}
				});	
	            
	            
            
			}
			
			var dataSrc = [];	
			// create data for grid
			function createDS(response){
						
			var parameter = "";
			var assetName= "";
			var deviceName="";
			var priority = "";
			var status="";
			var requestedTime = "";
			var errorMsg = "";
			
			
			$.each(response.entity,function (){
			
			updatedTime = this.command.requestedAt;			
			requestedTime = dt = kendo.toString(new Date(this.command.requestedAt), "G");
			status = this.command.status;
			priority = JSON.parse(this.command.customSpecsJSON).priority;
						
			parameter = this.command.customTag;
			assetName = this.device.sourceId;
			
				dataSrc.push({
				"parameter" : parameter,
				"assetName" : assetName,
				"deviceName" : deviceName,					
				"priority" : priority,
				"status" :	status,
				"requestedTime" : requestedTime,										
				"errorMsg": errorMsg				
				});
				
			});
			
			loadGrid(dataSrc);
			
			}
			
			function loadGrid(ds){
				dataSrc =[];
			
				//clear data
				$("#writebackloggrid").data('kendoGrid').dataSource.data([]);
				
				// refresh dataSource
				$("#writebackloggrid").data('kendoGrid').dataSource.data(ds);				
				$('#writebackloggrid').data('kendoGrid').refresh();
				
			}	
            
            
    </script>
	
</body>
</html>