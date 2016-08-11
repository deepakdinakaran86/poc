$(document).ready(function(){
FnInitializeScheduleList();
FnInitializeAssetTree();
})

$(window).load(function(){
FnGetUsersList();
});


function FnInitializeScheduleList(){


//	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
//var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "timeStamp",title: "Time Stamp",width: 430},{field: "dataval",title: "Data",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrGridData =[
        { timeStamp: "15/06/2016 10:24", dataval: "38"},
		{ timeStamp: "18/06/2016 12:36", dataval: "32"},
		{ timeStamp: "20/06/2016 16:47", dataval: "40"},
		{ timeStamp: "22/06/2016 18:39", dataval: "41"}
         
    ]
	FnDrawGridView('#history-list',ArrGridData,ArrColumns,ObjGridConfig);
} 

function FnInitializeAssetTree(){
 $("#treeview").kendoTreeView();
 }

 
 function createChart() {
            $("#chart").kendoChart({
                title: {
                    text: ""
                },
                legend: {
                    position: "bottom"
                },
                chartArea: {
                    background: "",
					height:300
                },
                seriesDefaults: {
                    type: "line",
                    style: "smooth"
                },
                series: [{
                    name: "Fuel Level",
                    data: [38, 40, 42, 41, 32, 35, 44, 39, 37, 43]
                }],
                valueAxis: {
                    labels: {
                        format: "{0}"
                    },
                    line: {
                        visible: false
                    },
                    axisCrossingValue: -10
                },
                categoryAxis: {
                    categories: ["22/06/2016", "21/06/2016", "20/06/2016", "19/06/2016", "18/06/2016", "17/06/2016", "16/06/2016","15/06/2016"],
                    majorGridLines: {
                        visible: false
                    },
                    labels: {
                        rotation: "auto"
                    }
                },
                tooltip: {
                    visible: true,
                    format: "{0}%",
                    template: "#= series.name #: #= value #"
                }
            });
        }

        $(document).ready(createChart);
        $(document).bind("kendo:skinChange", createChart);


function FnGetDeviceTreeList(){
  console.log('Users:FnGetUsersList');
}
