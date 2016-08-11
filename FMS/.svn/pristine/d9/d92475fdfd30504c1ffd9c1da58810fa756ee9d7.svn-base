<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval var="dsUrl"
	expression="@propertyConfigurer.getProperty('datasource.getallparams')" />
<spring:eval var="historyUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.history')" />

<script src="resources/javascript/historydevice.js"></script>



<style>



</style>
<head>
<script type="text/javascript">
	window.paramUrl = "${dsUrl}"
</script>
<script type="text/javascript">
	window.historyUrl = "${historyUrl}"
</script>
<style>
				
			.histroy-wrapper{
					position:relative;
			    	
				    animation-delay: 0.1s;
				    margin: 0 auto;
				    text-align: center;			    
				    padding:6px 0 6px 0;
			}
			#historyGrid{
			margin:12px 0 6px 0;
			
			}
			
			.input-xs {
		    line-height: 1.5; 
		    border-radius: 3px;
		    }
		    
		    
		 .histroy-wrapper  label {
							    line-height: 2.6em;							    
							    height: 40px;
							}
							.sourceid { color:#0683b3}
</style>
</head>
<body>
<div class="container0 theme-showcase dc_main" role="main" style="margin:0 10px;">
	<section class="dc_maincontainer animate-panel">
		<div class="page-header">
			<h1>History Tracking</h1>
		</div>
		
		<div style="height: 1px;">
				<form action="device_home" id="device_home" method="get"></form>
			</div>
			
		
			<div class="histroy-wrapper" id="deviceDetails" >

			<div class="row">
					<div class="col-md-6 col-sm-12 col-xs-12 ">
							<div class="col-md-5" > 
							<label  class="col-md-12 col-sm-12 col-xs-12 text-left  " id="sourceid" >Selected Device : <span class="sourceid" >${sourceId} </span></label>
							</div>
							
							<div class="col-md-7">
							<label  class="col-md-5  col-sm-12 col-xs-6 text-left " id="parameters">Parameters :</label> 
							<input class="col-md-7  col-sm-12 col-xs-6 form-control " style=" width: 185px;" id="parameterHist" />
							</div>
					</div>
					
								
					
					
					<div class="col-md-5 ">
					
							<div class="col-md-6 col-sm-12 col-xs-12" >
								<label class="col-md-3 col-sm-2 col-xs-2 text-left"  style="padding:0; margin: 0 12px 0 0" id="from">From : </label>  
								<input class="col-md-9  col-sm-10 col-xs-10 form-control"	id="fromDatePicker" />							
							</div>
							
							<div class="col-md-6 col-sm-12 col-xs-12" >
								<label class="col-md-3  col-sm-2 col-xs-12 text-left" style="padding:0; margin: 0 12px 0 0" id="to">To : </label>  
								<input class="col-md-9  col-sm-10 col-xs-12 form-control " id="toDatePicker" />							
							</div>
					</div>
					
					
					
					
					<div class="col-md-1 col-sm-12 col-xs-12 ">
					<button onclick="getHistoryData();" id="historysubmit"
								class="button text-shadow large-button fg-white bg-blue btn btn-primary"
								style="height: 40px; width: 80px;">Search</button></div>	
								<input type="hidden" id="source-identifier" value=${sourceId } />
			</div>
			
			
		
			<div class="row">			
				<div class="col-md-12"> <div id="historyGrid" ></div></div>
			</div>
	
			
			</div><!-- history wrapper -->
			

		
			
			<div id="extwindow">
				<div id='extgrid'></div>
			</div>
			<section class="text-right dc_groupbtn">
				<button type="button" onClick="submitBackAction()"
					class="btn btn-default">Back</button>
			</section>
	</section>
</div>
</body>

<script>
	$(document).ready(function() {
		//initNotifications();
		$('#historysubmit').attr('disabled', 'disabled');
		$("#historydatediv").hide();
		var datasourceName = window.histDSName;
		getDataSorceParameters('${sourceId}');
		$("#fromDatePicker").kendoDatePicker(getTodayDate());
		$("#toDatePicker").kendoDatePicker(getTomorrowDate());
		loadHistoryGrid();
	});

	function getHistoryData() {
		var startDate = kendo.toString($("#fromDatePicker").data(
				"kendoDatePicker").value(), "MM/dd/yyyy");
		var endDate = kendo.toString($("#toDatePicker").data("kendoDatePicker")
				.value(), "MM/dd/yyyy");
		var pq = physicalQuantity[$("#parameterHist").data("kendoDropDownList")
				.value()];
		var sourceID = $('#source-identifier').val();
		var param = $("#parameterHist").data("kendoDropDownList").text();
		fetchHistoryData(startDate, endDate, param, sourceID, pq);
	}

	function submitBackAction() {
		$("#device_home").submit();
	}
</script>
