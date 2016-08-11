"use strict";

$(document).ready(function(){	
	console.log('Dashboard: ready');
	FnMileageVsFuelConsumptionChart();	
	FnAssetCountBasedOnType();
	FnAlertCountBasedOnCriticality();
});

$(window).load(function() {
	
});

$(window).resize(function () {	
		FnReSizeKendoChart("chart");
		FnReSizeKendoChart("asset-count-chart");
		FnReSizeKendoChart("alert-count-chart");
		
	});


function FnMileageVsFuelConsumptionChart(){
	console.log('Dashboard: FnMileageVsFuelConsumptionChart');
	$(document).ajaxStart(function() { Pace.restart();
console.log();
	}); 
	/*
	 $(".export-pdf").click(function() {
            var chart = $("#chart").getKendoChart();
            chart.exportPDF({ paperSize: "auto", margin: { left: "1cm", top: "1cm", right: "1cm", bottom: "1cm" } }).done(function(data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.pdf",
                    proxyURL: "//demos.telerik.com/kendo-ui/service/export"
                });
            });
        });

        $(".export-img").click(function() {
            var chart = $("#chart").getKendoChart();
            chart.exportImage().done(function(data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.png",
                    proxyURL: "//demos.telerik.com/kendo-ui/service/export"
                });
            });
        });

        $(".export-svg").click(function() {
            var chart = $("#chart").getKendoChart();
            chart.exportSVG().done(function(data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.svg",
                    proxyURL: "//demos.telerik.com/kendo-ui/service/export"
                });
            });
        });
*/
      
            $("#chart").kendoChart({
                title: {
                    text: "",
                    font: "bold 16px 'DejaVu Sans'"
                },
                legend: {
                    position: "bottom",
                    labels: {
                        font: "12px 'DejaVu Sans'"
                    }
                },
                series: [{
                    type: "column",
                    data: [200, 400, 450, 300, 500,450],
                    stack: true,
                    name: "Mileage",
                    color: "#cc6e38"
                },
			/*	{
                    type: "column",
                    data: [20, 30, 35, 35, 40,60],
                    stack: true,
                    name: "Fuel",
                    color: "#ef955f"
                },
			*/	{
                    type: "line",
                    data: [30, 38, 40, 32, 42,60],
                    name: "Liner(Fuel)",
                    color: "#3c8dbc",
                    axis: "mpg"
                },
			/*	{
                    type: "line",
                    data: [7.8, 6.2, 5.9, 7.4, 5.6,5.8],
                    name: "Liner(Mileage)",
                    color: "#4e4141",
                    axis: "l100km"
                }
				*/
				],
                valueAxes: [{
                    title: { text: "miles" },
                    min: 100,
                    max: 500
                }, {
                    name: "km",
                    title: { text: "km" },
                    min: 100,
                    max: 200,
                    majorUnit: 32
                }, {
                    name: "mpg",
                    title: { text: "" },
                    color: "#ec5e0a"
                }, {
                    name: "l100km",
                    title: { text: "liters per 100km" },
                    color: "#4e4141"
                }],
                categoryAxis: {
                    categories: ["Sun","Mon", "Tue", "Wed", "Thu", "Fri"],
                    // Align the first two value axes to the left
                    // and the last two to the right.
                    //
                    // Right alignment is done by specifying a
                    // crossing value greater than or equal to
                    // the number of categories.
                    axisCrossingValues: [0, 0, 10, 50]
                },
				tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });
       
	   //FnReSizeChart("#chart",500);

    //    $(document).ready(createChart);
       // $(document).bind("kendo:skinChange", createChart);
	
}



function FnAssetCountBasedOnType(response){
	console.log('Dashboard: FnAssetCountBasedOnType');
	var ArrAssetCount=response;
		 var ArrAssetCount = [
            {
                "source": "Car",
                "percentage": 22,
                "explode": true
            },
            {
                "source": "Truck",
                "percentage": 2
            },
            {
                "source": "Bus",
                "percentage": 49
            },
            {
                "source": "Others",
                "percentage": 27
            }
        ];
	
	  $("#asset-count-chart").kendoChart({
                title: {
                    text: ""
                },
				chartArea: {
				 background: "transparent",
				 width: 400,				
				height: 270
			  },
			  plotArea: {
					background: "transparent",
					 width: 500,				
					height: 270
				   },
							  
                legend: {
                    position: "bottom"
                },
                dataSource: {
                    data: ArrAssetCount
                },
                series: [{
                    type: "pie",
                    field: "percentage",
                    categoryField: "source",
                    explodeField: "explode"
                }],
                seriesColors: ["#03a9f4", "#ff9800", "#fad84a", "#dd4b39 "],
                tooltip: {
                    visible: true,
                    template: "${ category } - ${ value }%"
                }
            });

}


function FnAlertCountBasedOnCriticality(){
		console.log('Dashboard: FnAlertCountBasedOnCriticality');
		kendo.ui.progress($("#alert-count-chart"), true);

		var ArrSeries=[{
                    name: "High",
                    data: [40, 32, 34, 36, 45, 33 ],
                    color: "#f3ac32"
                }, {
                    name: "Medium",
                    data: [19, 25, 21, 26, 28, 31],
                    color: "#b8b8b8"
                }, {
                    name: "Low",
                    data: [17, 17, 16, 28, 34, 30],
                    color: "#bb6e36"
                }];
		var ArrCategories=["Sun","Mon", "Tue", "Wed", "Thu", "Fri"];


		 $("#alert-count-chart").kendoChart({
                title: {
                    text: ""
                },
				chartArea: {
				 background: "transparent",								
				height: 266
				 
			  },
                legend: {
                    visible: true,
					position: "bottom"
                },
                seriesDefaults: {
                    type: "bar",
                    stack: {
                       // type: "100%"
                    }
                },
                series: ArrSeries,
                valueAxis: {
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    }
                },
                categoryAxis: {
                    categories: ArrCategories,
                    majorGridLines: {
                        visible: false
                    }
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });
        }






