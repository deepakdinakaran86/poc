<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval var="dsUrl"
	expression="@propertyConfigurer.getProperty('datasource.history.getallparams')" />
<spring:eval var="historyUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.history')" />
	
<script src="resources/js/history/historydevice.js"></script>

<head>
<script type="text/javascript">window.paramUrl = "${dsUrl}"</script>
<script type="text/javascript">window.historyUrl = "${historyUrl}"</script>
</head>

<body>
	<div id="deviceDetails">
		<table style="width: 100%">
			<tr>
				<td><label id="sourceid">Selected Device :</label>${sourceId}</td>
				<td><label id="parameters">Parameters :</label> <input id="parameterHist" /></td>
				<td><label id="from">From : </label> <input id="fromDatePicker" /></td>
				<td><label id="to">To : </label> <input id="toDatePicker" /></td>
				<td><button onclick="getHistoryData();" id="historysubmit"
						class="button text-shadow large-button fg-white bg-blue"
						style="height: 40px; width: 80px;" >Search
						</button></td>
			</tr><td></br></td>
			<tr>
			</tr>
		</table>
		<input type="hidden" id="source-identifier" value=${sourceId} />
	</div>
	<div id="historydatediv"><label id="ondate">On : </label><input id="historydate" style="width: 250px"/></div>
	<div id = "historyGrid"></div>
	<div id="extwindow"><div id='extgrid'></div></div>
</body>

<script>
	$(document).ready(function() {
		initNotifications();
		$('#historysubmit').attr('disabled','disabled');
		$("#historydatediv").hide();
		var datasourceName = window.histDSName;
		getDataSorceParameters('${sourceId}');
		$("#fromDatePicker").kendoDatePicker(getTodayDate());
		$("#toDatePicker").kendoDatePicker(getTomorrowDate());
		loadHistoryGrid();
	});

	function getHistoryData(){
	    var startDate = kendo.toString($("#fromDatePicker").data("kendoDatePicker").value(), "MM/dd/yyyy");
	    var endDate = kendo.toString($("#toDatePicker").data("kendoDatePicker").value(), "MM/dd/yyyy");
	    var pq = physicalQuantity[$("#parameterHist").data("kendoDropDownList").value()];
	    var sourceID = $('#source-identifier').val();
	    var param = $("#parameterHist").data("kendoDropDownList").text();
	   	fetchHistoryData(startDate,endDate,param,sourceID,pq);
		}
</script>
