
function createLineChart(VarChartId,ds) {
	var chartTitle = VarChartId.replace(/_/g,' ');
	$("#chart_"+VarChartId).kendoChart({
		dataSource: ds,
		title: {
			text: chartTitle,
			font: "bold 16px Arial,Helvetica,sans-serif",
			color: "#000000",
			align: "left"
		},
		series: [
		         {"name": chartTitle, type: "line", field: "value" }
		         ],
		         transitions: false,
		         seriesDefaults: {
		        	 type: "line",
		        	 style: "smooth"
		         },
		         chartArea: {
		        	 background: ""
		         },
		         valueAxis: {
		        	 line: {
		        		 visible: false
		        	 },
		        	 minorGridLines: {
		        		 visible: true
		        	 }
		         },
		         categoryAxis: {
		        	 field: "time",
		        	 majorGridLines: {
		        		 visible: false
		        	 }
		         },
		         tooltip: {
		        	 visible: true,
		        	 format: "{0}",
		        	 template: "#= series.name #: #= value # at #=category#<br /> "
		         },
		         legend: {
		        	 visible: true,
		        	 position: "bottom",
		         }
	});	
}