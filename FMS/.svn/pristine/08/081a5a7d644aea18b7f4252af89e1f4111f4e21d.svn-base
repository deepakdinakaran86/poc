"use strict";

$(document).ready(function(){
	$('body').tooltip({
		selector: '.grid-viewconftemplate'
	});
	FnInitializeGrid();
});

$(window).load(function(){
	FnGetConfTemplateList();
});

function FnInitializeGrid(){
	//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewconftemplate' style='text-transform: capitalize;'>#=name#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=name#</a>";
	
	var ArrColumns = [{field: "name",title: "Template Name" },{field: "deviceType",title: "Device Type"},{field: "deviceMake",title: "Make"},{field: "deviceModel",title: "Model"},{field: "deviceProtocol",title: "Protocol"},{field: "deviceProtocolVersion",title: "Protocol Version"}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var ArrValue = [
	                {name:"Temp1",deviceType:"Telematics",deviceMake:"Teltonika",deviceModel:"FMS",deviceProtocol:"FM5300",deviceProtocolVersion:"v2.13"},
	                {name:"Temp1",deviceType:"Telematics",deviceMake:"Teltonika",deviceModel:"FMS",deviceProtocol:"FM5300",deviceProtocolVersion:"v2.13"}
	                
	                ]
	FnDrawGridView('#gapp-conftemplate-list',ArrValue,ArrColumns,ObjGridConfig);
}

function FnGetConfTemplateList(){
	
}

function FnResConfTemplateList(){
	
	}
	


function FnCreateConfTemplate(){
	
}



