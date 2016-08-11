"use strict";

$(document).ready(function(){
  FnIntiateAccordions();
  FnInitializeGrid();

})

$(window).load(function(){
  FnGetClientsList();
});

function FnClickedSlide(){ 
	
alert('hi');}
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









