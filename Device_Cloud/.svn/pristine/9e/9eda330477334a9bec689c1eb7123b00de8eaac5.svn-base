
function createBooleanChart(VarChartId,ds) {
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
		        	 missingValues: "gap",

		        	 style: "step",
		        	 markers: {
		        		 visible: false
		        	 }
		         },
		         chartArea: {
		        	 background: ""
		         },
		         valueAxis:{
		        	 min:-1,
		        	 max:1,
		        	 majorUnit: 1
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
		        	 template: "#= series.name #: #= value #"
		         },
		         legend: {
		        	 visible: true,
		        	 position: "bottom",
		         }
	});
}