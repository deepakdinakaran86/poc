"use strict";

$(document).ready(function(){
	
  FnIntiateAccordions();// Accordions initiation
  FnInitializeGrid(); // Vehicle info
  
  // Alert details info
  FnAlertsDetailsGridAndChart(); 
  
  //Documents info
  FnDocumentDetails(); 
  mileageList();
  FuelList();
  summaryList();

})

$(window).load(function(){
  FnGetClientsList();
});

function FnAlertsDetailsGridAndChart(){
	
	FnAlertsChart();
	FnAlertsGrid();
	
}
function FnAlertsGrid(){
	//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewtenant' style='text-transform: capitalize;'>#=tenantName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=tenantName#</a>";
	var ViewLink ="<a class='grid-viewtenant' style='text-transform: capitalize;'>alertName1</a>";
	var ArrColumns = [
		{field: "alertName",title: "Alert Name",template: ViewLink },
		{field: "time",title: "Time"},
		{field: "message",title: "Message"}
	];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[{ alertName: "alertName1", time: "06/06/2016 12:34:00",message: "Over speed" },
					  { alertName: "alertName2", time: "28/07/2016 12:34:00",message: "Engine off" }];
	
	FnDrawGridView('#alerts-list',ArrGridData,ArrColumns,ObjGridConfig);
	
}

function FnAlertsChart(){

var ArrAlertChart = [{
                    type: "donut",
                    data: [{
                        category: "High",
                        value: 35
                    }, {
                        category: "Medium",
                        value: 25
                    }, {
                        category: "Low",
                        value: 20
                    }]
                }];
	$("#alert-chart").kendoChart({
                title: {
                    text: ""
                },
				chartArea: {
				 background: "transparent",
				 width: 300,				
				 height: 270
			  },
                legend: {
                   position: "bottom"
                },
                seriesDefaults: {
                    labels: {
                        template: "#= category # - #= kendo.format('{0:P}', percentage)#",
                       // position: "outsideEnd",
                        visible: true,
                        background: "transparent"
                    }
                },
                series:ArrAlertChart,
                tooltip: {
                    visible: true,
                    template: "#= category # - #= kendo.format('{0:P}', percentage) #"
                }
            });
	
	
}

function FnDocumentDetails(){
	
	//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewtenant' style='text-transform: capitalize;'>#=tenantName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=tenantName#</a>";
	var ViewLink ="<a class='grid-viewtenant' style='text-transform: capitalize;'>alertName1</a>";
	var ArrColumns = [
		{field: "docName",title: "Doc Name",template: ViewLink },
		{field: "docHolderName",title: "Holder Name"},
		{field: "activatedDate",title: "Activated Date"},
		{field: "expiryDate",title: "Expiry Date"},
		{field: "attachments",title: "Attachments"}
		
	];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[
	{ docName: "Driver List", docHolderName:"Sam", activatedDate:"01/02/2016 12:34:00",expiryDate:"06/06/2016 3:34:00",attachments: "PDF" },
	{ docName: "Vehicle List", docHolderName:"John", activatedDate:"01/02/2016 12:34:00",expiryDate:"06/06/2016 3:34:00",attachments: "PDF, DOC" }
					];
	
	FnDrawGridView('#documents-list',ArrGridData,ArrColumns,ObjGridConfig);
	
	
}

function FnClickedSlide(){} 	

function FnInitializeGrid(){
	//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewtenant' style='text-transform: capitalize;'>#=tenantName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=tenantName#</a>";
//	var ViewLink ="<a class='grid-viewtenant' style='text-transform: capitalize;'>"+pointName+"</a>";
	var ArrColumns = [
					//{field: "pointName",title: "Point Name",template: ViewLink },
					{field: "pointName",title: "Point Name" },
					{field: "value",title: "Value"},
					{field: "time",title: "Time"},
					{field: "unit",title: "Unit"}
					];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 320,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	var ArrGridData =[
        { pointName: "Speed", value: 10, time: "23:45", unit: "unitless"  },
		{ pointName: "Width", value: 67, time: "23:45", unit: "unitless"  },
		{ pointName: "height", value: 10, time: "23:45", unit: "unitless"  },
		{ pointName: "Temperature", value: 10, time: "23:45", unit: "unitless" },
		{ pointName: "Speed", value: 45, time: "23:45", unit: "unitless"  },
		{ pointName: "Width", value: 10, time: "23:45", unit: "unitless"  },
		{ pointName: "height", value: 34, time: "23:45", unit: "unitless"  },
		{ pointName: "Temperature", value: 78, time: "23:45", unit: "unitless" }
         
    ]
	FnDrawGridView('.vehicles-list',ArrGridData, ArrColumns,ObjGridConfig);
}

function FnGetClientsList(){
  console.log('Clients:FnGetClientsList');
}

function FnCreateClient(){
console.log('Clients:FnCreateClient');

}

function FnCreateClientAdmin(){
  console.log('Clients:FnCreateClientAdmin');
}


 function FnIntiateAccordions(){
	  $('.accordionContainer').uberAccordion({
			headerClass: 'title',
			contentClass: 'content' ,
			orientation: "vertical" ,// "vertical"
			animationSpeed: 1000,
			slideEvent       : 'click'            // Open slide event
			
	
  });  
	  
  }
  
  function mileageList(){
            $("#mileage-list").kendoChart({
                title: {
                    text: ""
                },
                legend: {
                    visible: true,
					 position: "bottom"
                },
				chartArea: {
                    background: "",
					height:300
                },
                seriesDefaults: {
                    type: "bar"
                },
                series: [{
                    name: "Mileage",
                    data: [300,400,200,450,360,500]
                }],
                valueAxis: {
                    max: 600,
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    },
                    labels: {
                        rotation: "auto"
                    },
					title: {
                    text: "Mileage"
                }
                },
                categoryAxis: {
                    categories: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
                    majorGridLines: {
                        visible: false
                    },
					title: {
                    text: "Days"
                }
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });
  }

  
   function FuelList(){
            $("#fuel-list").kendoChart({
                title: {
                    text: ""
                },
                legend: {
                    visible: true,
					 position: "bottom"
                },
				chartArea: {
                    background: "",
					height:300
                },
                seriesDefaults: {
                    type: "column"
                },
                series: [{
                    name: "Mileage",
                    data: [10,24,35,22,50,48],
					 color: "#cacc26"
                }],
                valueAxis: {
                    max: 60,
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    },
                    labels: {
                        rotation: "auto"
                    },
					title: {
                    text: "In Hours"
                }
                },
                categoryAxis: {
                    categories: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
                    majorGridLines: {
                        visible: false
                    },
					title: {
                    text: "Days"
                }
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });
  }
  
  
  function summaryList() {
            $("#summary-list").kendoChart({
                title: {
                    position: "bottom",
                    text: ""
                },
                legend: {
                    visible: true
                },
                chartArea: {
                    background: "",
					height:250
                },
                seriesDefaults: {
                    type: "donut",
                    startAngle: 150
                },
                series: [{
                    name: "Summary",
                    data: [{
                        category: "Run Hours",
                        value: 323,
                        color: "#90cc38"
                    },{
                        category: "Idle Hours",
                        value: 48,
                        color: "#068c35"
                    },{
                        category: "Stop Hours",
                        value: 139,
                        color: "#006634"
                    }]
                }],
                tooltip: {
                    visible: true,
                    template: "#= category # (#= series.name #): #= value #%"
                }
            });
        }









