var poiInfo = [];
var tempArr=[];	
var route = {};
var totalStoppage = 0;
var contain;
var autocomplete;
var selId=0;
var gridId=0
$(document).ready(function() {


	FnInitiateRouteGrid();
	 $("#gapp-route-form").kendoValidator({
	 validateOnBlur : true,
	 errorTemplate: "<span class='help-block'><code>#=message#</code></span>",
	 rules : {
	 available:function(input){
	 var validate = input.data('available');
	 var VarExist = true;
	 if (typeof validate !== 'undefined' && validate !== false) {
	 var VarThisFieldValue = FnTrim(input.val());
	 var url = input.data('available-url');
	 var VarParam = {"domain": {"domainName":UserInfo.getCurrentDomain()},"fieldValues": [{"key": "routeName","value":VarThisFieldValue}]};
	 $.ajax({
	 type:'POST',
	 cache: true,
	 async: false,
	 contentType: 'application/json; charset=utf-8',
	 url: "/FMS/ajax" + url,
	 data: JSON.stringify(VarParam),
	 dataType: 'json',
	 success: function(result) {
	 var ObjExistStatus = result.entity;
	 if(ObjExistStatus.status == 'SUCCESS'){ // Does not exist in db
	 VarExist = true;
	 } else if(ObjExistStatus.status == 'FAILURE') { // Exist in db
	 VarExist = false;
	 }
	 },
	 error : function(xhr, status, error) {
															
	 }
	 });
														
	 }
	 return VarExist;
	 }
	 },
	 messages : {
	 available: function(input) {
	 return input.data("available-msg");
	 }
	 }
												
	 });

	 $('#drawRoute').prop('disabled', true);	
	 $('#saveRoute').prop('disabled', true);
	 FnCheckEmptyFrmFields('#routeName','#saveRoute','#drawRoute');

//$(".autoComplete k-input").on("change",function(e){
//	container =e.target.parentElement.parentElement;
//	console.log(e.target.value);
//	geocoder.geocode(e.target.value, fnResult,e);
//	
//});	
	
});

function FnCheckTheAction(VarGetCode){		
	//alert(VarGetCode);
	if (VarGetCode ==1){	
		return ' &nbsp&nbsp<a href="javascript:void(0);" class="add-point" ><i class="fa fa-plus-circle"></i></a> ';	
	}else{
		return ' &nbsp&nbsp<a href="javascript:void(0);" class="remove-point" ><i style="color:red" class="fa fa-minus-circle"></i></a>';
		
	}
		
}

var organizations_arr = [{id:'org1',name:'Org 1'},{id:'org2',name:'Org 2'},{id:'org3',name:'Org 3'},{id:'org4',name:'Org 4'}];

function FnInitiateRouteGrid() {  
	
	console.log('Route:Grid init');
	// Dummy data for visual
	var ObjPointData = [ {
		"Id" : 0,
		"PointName" : "-",
		"address" : "-",
		"stoppageTime" : 0,
		"tolerance" : 0,
		"Action":1
		
	} ];
	// Define data source
	var ds = {
		// data : createRandomData(20),
		data : ObjPointData,
		pageSize : 5,
		schema : {
			model : {
				fields : {
					Id : {
						type : 'number'
					},
					PointName : {
						type : 'string',
				        validation: {
                            required: true,
                            pointNamevalidation: function (input) {
                            	return markerValidation(input);
                            }
                        }
					},
					latitude : {
						type : 'string'
					},
					longitude : {
						type : 'string'
					},
					address : {
						type : 'string'
					},
					stoppageTime : {
						type : 'number'
					},
					tolerance : {
						type : 'number'
					},
					Action : {
						type : 'number',
						editable: false,
						nullable: true
					}
					
				}
			}
		}
	};

	// Defining action btn
	var VarActionButtons = '<a href="javascript:void(0);" class="add-point" ><i class="fa fa-plus-circle"></i></a> &nbsp';
	
	// Grid init
	var grid = $("#draw-route-grid").kendoGrid({
		autoBind : true,
		dataSource : ds,
		editable : true,
		pageable : true,
		selectable : true,
		navigatable : true,		
		pageable : {
			pageSize : 5,
			previousNext : true,
			messages : {
				empty : "No Records"
			}
		},

		height : 330,
		noRecords : true,
		messages : {
			noRecords : "No Records"
		},
		columns : [ 
//		{
//			field : "latitude",
//			title : "Latitude"
//		}, {
//			field : "longitude",
//			title : "Longitude"}, 
		{
			field : "address",
			title : "Address",
			editor:serviceItemAutoCompleteEditor
		},
		{
			field : "PointName",
			title : "Point Name",
			width : 75
			
		},
		{
			field : "tolerance",
			title : "Tolerance",
			width : 55
		},{
			field : "stoppageTime",
			title : "Stoppage Time",
			width : 55
		},
		{
			width : 30,
			field: "Action",
			title : "",
			template :"#= FnCheckTheAction(Action) #"
				
		} ],
	}).data("kendoGrid");

	
	// Add new row
	var ptCount=0;
	$("#draw-route-grid").data("kendoGrid").tbody.on("click", ".add-point", function(e) {
		
		//var VarActionRemoveButton = ' &nbsp&nbsp<a href="javascript:void(0);" class="remove-point" ><i style="color:red" class="fa fa-minus-circle"></i></a>';
		
		ptCount++;
		var grid = $("#draw-route-grid").data("kendoGrid");
		grid.dataSource.add({
			Id:ptCount,
			stoppageTime : 0,
			tolerance:0,
			Action : 0,
			pointName:""
			
		});
	});

	// remove current row
	$("#draw-route-grid").data("kendoGrid").tbody.on("click", ".remove-point",	function(e) {
		
		var tr = $(this).closest("tr");
		var data = $("#draw-route-grid").data('kendoGrid')
				.dataItem(tr);
		FnRemoveRouteMarker(data);
		
					
//				var grid = $("#draw-route-grid").data("kendoGrid");
//				var sel = grid.select();
//				var sel_idx = sel.index();
//				var item = grid.dataItem(sel);
//				var idx = grid.dataSource.indexOf(item);
//				grid.dataSource.remove(item);
			});

}

/*function invoked when saving the route(btn click)*/
function fnSaveRoute() {
	$("#gapp-route-form").submit();

}


/*function after drawing the route, getting the route info from direction service(GOOGLE)*/
function fnCreateRouteInfo(event) {
	var tmpArr = [];
	poiInfo=[];
	routeString=null;
	totalDistance=null;
	totalDuration=null;
	totalStoppage=0
	var poiTime = 0; // initialy time b.w POI
	var poiDistance = 0;// initialy distance b.w POI
	var routeArray = event.routes[0].coordinates; // an array of L.LatLngs
													// that can be used to
													// visualize the route
	var overview = event.routes[0].overview;
	
	
	var routeLegs=event.routes[0].legs;
	
	var routeInstruction = event.routes[0].instructions;// instruction for the
														// route
	console.log("routeInstruction");
	console.log(routeInstruction);

	var inputWaypoints = event.routes[0].inputWaypoints;
	var length = inputWaypoints.length;
	var startAddress = inputWaypoints[0].name;
	var endAddress = inputWaypoints[length - 1].name;

	for (var i = 0; i < routeArray.length; i++) {
		var obj = {}
		obj["lat"] = routeArray[i].lat;
		obj["lng"] = routeArray[i].lng;
		tmpArr.push(obj);
	}

	for (var i = 0; i < routeLegs.length; i++) {
		var obj = {}
																		// POI
			obj["poiTime"] = routeLegs[i].duration.value;
			obj["poiDistance"] = routeLegs[i].distance.value;
			poiInfo.push(obj);
	}
	console.log("poiInfo");
	console.log(poiInfo);

	routeString =overview;
	totalDistance = event.routes[0].summary.totalDistance; // route total
															// distance
	console.log("totalDistance" + totalDistance);
	totalDuration = event.routes[0].summary.totalTime; // route total duration
	console.log("totalDuration" + totalDuration);

	fnCreateRoutePayload(totalDistance, totalDuration, startAddress,
			endAddress, routeString, poiInfo);
	var distance = totalDistance + " Meters";
	var time = parseInt(totalDuration) + totalStoppage;
	
	var strTime=millisecondsToStr(time*1000)
	
	
	
	$(" #kms").html(distance);
	$(" #mins").html(strTime);

}


/*function create payload for route api*/
function fnCreateRoutePayload(totalDistance, totalDuration, startAddress,
		endAddress, routeString, poiInfo) {
	var destinations = [];
	route["startAddress"] = startAddress;
	route["endAddress"] = endAddress;
	route["routeString"] = routeString;
	route["mapProvider"] = "google";

	 grid = $("#draw-route-grid").data("kendoGrid");
	for (var k = 1; k < grid.dataSource.data().length; k++) { // k=1 , added since grid is having a default raw in begining
		var element = {};
		totalStoppage += parseInt(grid.dataSource.data()[k].stoppageTime);
		element['latitude'] = grid.dataSource.data()[k].latitude;
		element['longitude'] = grid.dataSource.data()[k].longitude;
		element['name'] = grid.dataSource.data()[k].PointName;
		element['index'] = k;
		element['stopageTime'] = grid.dataSource.data()[k].stoppageTime;
		element['radius'] = grid.dataSource.data()[k].tolerance;
		element['address']=grid.dataSource.data()[k].address;
		if (k == 1) {
			element['duration'] = "";
			route["startAddress"] = grid.dataSource.data()[k].address;
			
		} else {
			element['duration'] = poiInfo[k - 2].poiTime;
			route["endAddress"] = grid.dataSource.data()[k].address;
			
			// element['distance'] = poiInfo[k].poiDistance; //distance not
			// added currently in api for poiTYPE
		}
		destinations.push(element);
	}
	console.log("totalStoppage" + totalStoppage);
	route["destinationPoints"] = destinations;
	route["totalDistance"] = totalDistance;
	route["totalDuration"] = parseInt(totalDuration) + totalStoppage;

	console.log("route");
	console.log(route);
	$("#routeDetails").val(JSON.stringify(route));
}



/*function invoked when editor of the column address is invoked*/
function serviceItemAutoCompleteEditor(container,options) {
	gridId=options.model.Id;
	var geocoder = new L.Control.Geocoder.nominatim(); 
	console.log(geocoder);
	
	var maps = new kendo.data.DataSource({
		transport: {
	      read: function(options) {
	        // call the geocoder
	        geocoder.geocode(autocomplete.val(), 
	          function(results, status) {
		        	searchResult=results;
	              return options.success(results);
	        });
	      }
	    },                                     
	    serverSorting: true
	  });
	
	
	
autocomplete=$('<input data-text-field="name"  data-bind="value:"address""/>')
	    .appendTo(container)
	    .kendoAutoComplete({
	        placeholder: "Select an item",
	        filter: "contains",
	        change:onchange,
	        dataSource: maps,
	        select: onSelect
	    })	
	}


/*function invoked when selecting the address from autocomplete*/
function onSelect(e) {
	console.log("onselect" );
        var dataItem = this.dataItem(e.item.index());
        console.log(dataItem);
        console.log(searchResult);
        var searchObj=searchResult[e.item.index()];
        console.log(searchObj );
        var geomarker=fnCreatetMarkerFromGrid(searchObj.center);
		//ArrPolyMarkers.push(searchObj.center);
		map.panTo(searchObj.center);
		var grid = $("#draw-route-grid").data("kendoGrid");
		console.log(grid );
		grid.dataSource.data()[gridId].markerid='marker_'+geomarker._leaflet_id;
		grid.dataSource.data()[gridId].address=dataItem.name;
		grid.dataSource.data()[gridId].latitude=searchObj.center.lat;
		grid.dataSource.data()[gridId].longitude=searchObj.center.lng;
		 console.log(objectMarker.marker );
		//FnManageRouteGrid(objectMarker.marker,grid.dataSource.data()[selId].stoppageTime,grid.dataSource.data()[selId].tolerance,dataItem.name);
}


/*function to populate the grid back on viewing the route*/
function fnReDrawGrid(finalwayobj){
	
	var grid = $("#draw-route-grid").data("kendoGrid");
	for (var k = 0; k < finalwayobj["setWayPoints"].length; k++) {
		//var VarChkId = grid.dataSource.data()[k].rowid;
		var VarChkId = grid.dataSource.data()[k].Id;		
			grid.dataSource.add({
				latitude : finalwayobj["setWayPoints"][k].latlng.lat,
				longitude : finalwayobj["setWayPoints"][k].latlng.lng,			
				stoppageTime:finalwayobj["setWayPoints"][k].stopageTime,
				tolerance:finalwayobj["setWayPoints"][k].tolerance,
				address:finalwayobj["setWayPoints"][k].address,
				Id:finalwayobj["setWayPoints"][k].index,
				PointName:finalwayobj["setWayPoints"][k].name,
				Action:0,
			});

	}
	
}

/* function which redraw route  onm view*/
function fnReDrawRoute(routeResponse){
	var routeString=routeResponse.routeString;
	var routeName=routeResponse.routeName;
	var routeDesc=routeResponse.routeDescription;
	$("#routeName").val(routeName);
	$("#description").val(routeDesc);
	
	var distance = routeResponse.totalDistance + " Meters";
	var time = millisecondsToStr((routeResponse.totalDuration)*1000);
	
	$(" #kms").html(distance);
	$(" #mins").html(time);
	
	var router=control.getRouter()
	var coordinates=router._decodePolyline(routeString);
	var destination=routeResponse.destinationPoints;
	var finalWayObj=fnCreateWaypoints(destination);
	var arrDrawPoint=finalWayObj["setWayPoints"];
	
	
	var polylineOptions = {
			color: '#009FD8',
			weight: 5,
			opacity: 0.7,
			lineJoin: 'round',
			clickable: true
		};
		
	for(var i=0; i<arrDrawPoint.length;i++){
			fnCreateStartMarker(arrDrawPoint[i].latlng,false);
	}
	
	var polyline = new L.Polyline(coordinates, polylineOptions);
	map.addLayer(polyline); 
	map.fitBounds(polyline.getBounds());
	
	fnReDrawGrid(finalWayObj);	
}

function fnCreateWaypoints(destination){
	var finalWayObj={}; // it has i/p for bth route and setwaypoints
	var waypointArr=[];
	console.log(destination);
	var points=[];
	for(var i=0;i<destination.length;i++){
		var latlng = L.latLng(destination[i].latitude, destination[i].longitude);
		points.push(latlng); // for map.route
		var waypointObj={};
		waypointObj["index"]=destination[i].index;
		waypointObj["latlng"]=latlng;
		waypointObj["name"]=destination[i].name;
		waypointObj["address"]=destination[i].address;
		waypointObj["stopageTime"]=destination[i].stopageTime;
		waypointObj["tolerance"]=destination[i].radius;
		waypointArr.push(waypointObj);
	}
	console.log("before sort");
	console.log(waypointArr);
	
	waypointArr=waypointArr.sort(function(a, b){
	    var keyA =a.index,
	        keyB =b.index;
	    if(keyA < keyB) return -1;
	    if(keyA > keyB) return 1;
	    return 0;
	})
	
	console.log("after sort");
	console.log(waypointArr);
	finalWayObj["route"]=points;
	finalWayObj["setWayPoints"]=waypointArr;
	
	return finalWayObj;
	
}


function millisecondsToStr (milliseconds) {
    // TIP: to find current time in milliseconds, use:
    // var  current_time_milliseconds = new Date().getTime();
    function numberEnding (number) {
        return (number > 1) ? 's' : '';
    }

    var temp = Math.floor(milliseconds / 1000);
    var years = Math.floor(temp / 31536000);

    if (years) {
        return years + ' year' + numberEnding(years);
    }
    //TODO: Months! Maybe weeks? 
    var days = Math.floor((temp %= 31536000) / 86400);
    if (days) {
        return days + ' day' + numberEnding(days);
    }
    var hours = Math.floor((temp %= 86400) / 3600);
    if (hours) {
        return hours + ' hour' + numberEnding(hours);
    }
    var minutes = Math.floor((temp %= 3600) / 60);
    if (minutes) {
        return minutes + ' minute' + numberEnding(minutes);
    }
    var seconds = temp % 60;
    if (seconds) {
        return seconds + ' second' + numberEnding(seconds);
    }
    return 'less than a second'; //'just now' //or other string you like;
}


function markerValidation(input)
{
	var VarExist = true;
    if (input.is("[name='PointName']") && input.val() != "") {
        input.attr("data-pointNamevalidation-msg", "PointName already exist");
        
        var VarParam = {"domain": {"domainName":UserInfo.getCurrentDomain()},"fieldValues": [{"key": "poiName","value":input.val()}]};
   	 $.ajax({
   	 type:'POST',
   	 cache: true,
   	 async: false,
   	 contentType: 'application/json; charset=utf-8',
   	 url: "/FMS/ajax/markers/1.0.0/markers/validate",
   	 data: JSON.stringify(VarParam),
   	 dataType: 'json',
   	 success: function(result) {
   	 var ObjExistStatus = result.entity;
   	 if(ObjExistStatus.status == 'SUCCESS'){ // Does not exist in db
   	 VarExist = true;
   	 } else if(ObjExistStatus.status == 'FAILURE') { // Exist in db
   	 VarExist = false;
   	 }
   	 },
   	 error : function(xhr, status, error) {
   															
   	 }
   	 });
    }
 	 return VarExist;
 }