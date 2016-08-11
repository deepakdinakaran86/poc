var ArrPolyMarkers = [];
var ArrPolyLines = [];
var objLine = {};
var startBtn;
var destBtn;
var control;
var startMarker;
var objectMarker={};
var endMarker;
var intermedMarker;
var toleranceVal;
var stoppageVal;
var controlPlan;
var routeString;
var totalDistance=0;
var totalDuration=0;
var count = 0;
var validation;
var geocoder;
var searchResult=[];
var mapOnclick;
var ArrPolyMarkersTest = [];

$(document).ready(function() {
	FnInitiateMap();
	 $('.leaflet-top leaflet-right').hide();
	//FnInitRouteGrid();
	FnInitializeAssetTree();
	FnMapOnClick();
	
	if(routeView=='1'){
		
	fnReDrawRoute(routeResponse);	
	}
	 $('.leaflet-top leaflet-right').hide();

});

var map = null;
var streets;
var hybrid;
var marker;
var ArrRouteMarkers = [];

function FnInitiateMap() {

//	var osmUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
//	var osmAttrib = 'Map data © <a href="http://openstreetmap.org">OpenStreetMap</a>contributors';
//
//	var osm = new L.TileLayer(osmUrl, {
//		minZoom : 1,
//		maxZoom : 12,
//		attribution : osmAttrib
//	});

	streets = L.tileLayer('https://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
        id: 'Street',
		minZoom: 1,
        maxZoom: 18,
        subdomains:['mt0','mt1','mt2','mt3'],
        attribution: ""});

    hybrid = L.tileLayer('https://{s}.google.com/vt/lyrs=s,h&x={x}&y={y}&z={z}', {
        id: 'Satellite',
		minZoom: 1,
        maxZoom: 18,
        subdomains:['mt0','mt1','mt2','mt3'],
        attribution: ""});

	map = L.map('routecreationmap', {
		zoom : 16,
		center: [25.20, 55.27],
		layers: [hybrid,streets],// Dubai LatLng 25.20, 55.27
		zoomControl : true,
		waypoints : [],
		closePopupOnClick:false,
		route:{
			name:"my route"
		},
		attributionControl : true
	});
	
	  var baseMaps = {
		        "Satellite": hybrid,
		        "Streets": streets
		    };

	  var waypts = [];	  
	  
	map.addControl(new L.Control.Search({
		url : 'http://nominatim.openstreetmap.org/search?format=json&q={s}',
		jsonpParam : 'json_callback',
		propertyName : 'display_name',
		propertyLoc : [ 'lat', 'lon' ],
		markerLocation : false,
		autoCollapse : true,
		autoType : false,
		minLength : 2
	}));

	 control = L.Routing.control({
		 router: L.Routing.google({
			    provideRouteAlternatives: false
			  }),
		plan : L.Routing.plan(map.waypoints, {
			createMarker : function(i, wp) {
				if(i==0)
				
				return null;
				},
			geocoder : L.Control.Geocoder.nominatim(),
			waypointNameFallback: function(latLng) { // if address not present
		        function zeroPad(n) {
		            n = Math.round(n);
		            return n < 10 ? '0' + n : n;
		        }
		        function sexagesimal(p, pos, neg) {
		            var n = Math.abs(p),
		                degs = Math.floor(n),
		                mins = (n - degs) * 60,
		                secs = (mins - Math.floor(mins)) * 60,
		                frac = Math.round((secs - Math.floor(secs)) * 100);
		            return (n >= 0 ? pos : neg) + degs + '°' +
		                zeroPad(mins) + '\'' +
		                zeroPad(secs) + '.' + zeroPad(frac) + '"';
		        }

		        return sexagesimal(latLng.lat, 'N', 'S') + ' ' + sexagesimal(latLng.lng, 'E', 'W');
		    },
			
		}),
		routeWhileDragging : true,
		lineOptions:{
			styles:[
			    {
			    color:'green',
			    weight:9
			    }    
			        ]
		},
			
		routeLine : function(route,lineOptions) {
			var line = L.Routing.line(route, {
				addWaypoints : false,
				extendToWaypoints : false,
				routeWhileDragging : true,
				autoRoute : false,
				useZoomParameter : false,
				draggableWaypoints : true,
				addWaypoints : false
			});
			line.eachLayer(function(l) {
				l.on('click', function(e) {
					console.log(e);
				});
			});
			console.log(line);
			return line;
		},
		autoRoute:false, // to disbale control.route
		routeWhileDragging : true,
		reverseWaypoints : false,
		showAlternatives : false,
		altLineOptions : {
			styles : [ {
				color : 'black',
				opacity : 0.15,
				weight : 9
			}, {
				color : 'white',
				opacity : 0.8,
				weight : 6
			}, {
				color : 'blue',
				opacity : 0.5,
				weight : 2
			} ]
		}	
			
	}).addTo(map);
	 geocoder=control.getPlan().options.geocoder;
	 controlPlan=control.getPlan(); //get plan of the route control for geocoding

	map.zoomControl.setPosition('bottomright');
	
	

	  L.control.layers(baseMaps,null,{position: 'topleft'}).addTo(map);

	control.on("routingstart", function(e) {
		console.log(e);
		//alert("inside routingstart event");
	});

	control.on("routesfound", function(e) {
		console.log(e);
		fnCreateRouteInfo(e);
		//alert("inside routesfound event");
	});

	control.on("routingerror", function(e) {
		console.log(e);
		//alert("inside routingerror event");
	});
	
	controlPlan.on("waypointgeocoded",function(e){
		objectMarker["geoName"]=e.waypoint.name;
	});

}

function FnInitializeAssetTree() {
	$("#treeview").kendoTreeView();
}

function FnGetMarkerHtmlIcon(name) {
	var icon = L.divIcon({
		className : '',
		iconSize : [ 45, 45 ],
		iconAnchor : [ 22, 45 ],
		popupAnchor : [ 0, -37 ],
		html : '<div class="pin">s</div>'
	});

	return icon;

}


function FnMapOnClick() {
	map.on('click', function(e) {
		mapOnclick=e;
		var container = createDiv("btnDiv");
		fnCreatePopup(container,e);
		L.DomEvent.on(startBtn, 'click', function() {
				fnCreateStartMarker(e.latlng,true);							
		});

	});
	map.on('zoomend', function(e) {
		//if(GblEdit==1){	return false; }
		var VarZoomVal = map.getZoom();
		$('#zoom').val(VarZoomVal);
	});
}

function FnGetMarkerIcon(VarColor) {

	var icon = new L.Icon({
		iconUrl : 'resources/plugins/leaflet/marker/' + VarColor
				+ '_marker.png',
		shadowUrl : 'resources/plugins/leaflet/images/marker-shadow.png',
		iconSize : [ 25, 41 ],
		iconAnchor : [ 12, 41 ],
		popupAnchor : [ 1, -34 ],
		shadowSize : [ 41, 41 ]
	});
	return icon;
}

function FnInitRouteGrid() {

	var grid = $("#draw-route-grid").kendoGrid({
		autoBind : true,
		dataSource : [],
		rowTemplate : kendo.template($("#rowTemplate").html()),
		pageable : {
			pageSize : 5,
			numeric : false,
			previousNext : true,
			messages : {
				empty : "No Records"
			}
		},
		height : 220,
		noRecords : true,
		messages : {
			noRecords : "No Records"
		},
		columns : [ {
			field : "latitude",
			title : "Latitude"
		}, {
			field : "longitude",
			title : "Longitude"
		}, {
			field : "tolerance",
			title : "Tolerance"
		},{
			field : "stoppageTime",
			title : "StoppageTime"
		},{
			field : "address",
			title : "address"
		},{
			field : "markerid",
			title : "Action",
			width : 60
		} ],
		selectable : true
	});

	$("#draw-route-grid .k-grid-content").mCustomScrollbar({
		setWidth : false,
		setHeight : false,
		setTop : 0,
		setLeft : 0,
		axis : "y",
		scrollbarPosition : "inside",
		autoExpandScrollbar : true,
		alwaysShowScrollbar : 0,
		live : "on",
		autoHideScrollbar : true,
		theme : "minimal-dark"
	});

	var routelist = $("#draw-route-grid").data("kendoGrid");
	routelist.bind("change", FnGridChangeEvent);

}

function FnManageRouteGrid(ObjMarker,geoName) {
	var VarLatitude = (ObjMarker.getLatLng()).lat;
	//alert(VarLatitude);
	var VarLongitude = (ObjMarker.getLatLng()).lng;
	//alert(VarLongitude);
	var VarMarkerId = ObjMarker._leaflet_id;
	var VarRowMakerId = 'marker_'+ObjMarker._leaflet_id;
	//var VarRowId = 'marker_' + ObjMarker._leaflet_id;
	var VarRowId = count;
	var isGExist = false;
	var grid = $("#draw-route-grid").data("kendoGrid");
	var VarId = VarRowMakerId;
	for (var k = 0; k < grid.dataSource.data().length; k++) {
		var VarChkId = grid.dataSource.data()[k].markerid;
		//var VarChkId = grid.dataSource.data()[k].Id;
		if (VarId == VarChkId) {
			isGExist = true;
			var item = grid.dataSource.data()[k];
			item.set('latitude', VarLatitude);
			item.set('longitude', VarLongitude);
			if(null!=geoName){
				item.set('address', geoName);	
			}
			item.set('Action',0);
			item.set('Id',count);
			item.set('markerid',VarRowMakerId);
			item.set('rowid',VarMarkerId);
			//item.set('pointName',pointName);
			
		}
	}
	if (!isGExist) {
		grid.dataSource.add({
			latitude : VarLatitude,
			longitude : VarLongitude,			
			markerid : VarRowMakerId,
			address:geoName,
			tolerance:0,
			stoppageTime:0,
			rowid : VarMarkerId,
			Id:count,
			Action:0
			//pointName:pointName

		});
	}
	grid.dataSource.sync();
}

function FnRemoveRouteMarker(ObjItem) {
	
	console.log("Removing marker");
	var VarMarkerId = ObjItem.rowid
	var VarMarker = objLine[VarMarkerId];
	map.removeLayer(VarMarker);
//	/var latlng = L.latLng(ObjItem.latitude, ObjItem.longitude);
	var VarMarkerPos = $.inArray(VarMarker.getLatLng(), ArrPolyMarkers);
	console.log(VarMarkerPos)
	ArrPolyMarkers.splice(VarMarkerPos, 1);
	ArrPolyMarkersTest.splice(VarMarkerPos, 1);
	console.log(ArrPolyMarkers)
	delete objLine[VarMarkerId];

	var VarRowId = ObjItem.rowid;

	var grid = $("#draw-route-grid").data("kendoGrid");
	var sel = grid.select();
	var sel_idx = sel.index();
	var item = grid.dataItem(sel);
	grid.dataSource.remove(item);
	
	
	if(ArrPolyMarkers.length>=2){
		$('#drawRoute').prop('disabled', false);	 
	}else{
		$('#drawRoute').prop('disabled', true);	
	}
	
	
	
	
	if (ArrPolyMarkers.length > 1) {
		//fnDrawRoute();
	} else {
//		if (ArrPolyLines.length > 0) {
//			for (var j = 0; j < ArrPolyLines.length; j++) {
//				map.removeLayer(ArrPolyLines[j]);
//			}
//			ArrPolyLines = [];
//		}
	}

}


function FnMouseoverEvent(e) {
	var marker = e.target;
	var VarMarkerId = marker._leaflet_id;
	var VarRowId = 'marker_' + VarMarkerId;
	$('#' + VarRowId).css('background-color', '#f44336');
}

function FnMouseoutEvent(e) {
	var marker = e.target;
	var VarMarkerId = marker._leaflet_id;
	var VarRowId = 'marker_' + VarMarkerId;
	$('#' + VarRowId).css('background-color', '');
}


function createButton(label, container,background) {
    var btn = L.DomUtil.create('button', '', container);
    btn.id=label;
    //btn.style="color:black;background:"+background+";"; to add color to buttons
    btn.style="background-color: #64a1d5;background-image: linear-gradient(to bottom, #D564B2, #428bca);color: #fff;border-color: #5342CA;min-width: 70px;"
    btn.setAttribute('type', 'button');
    btn.className="k-button primary";
    btn.innerHTML = label;
    return btn;
}


function createDiv(id){
	var div=L.DomUtil.create('div');
	div.id=id;
	return div;
}


var startIcon = L.icon({
    iconUrl: 'resources/images/map-marker-blue.png',
    iconSize: [25, 41],
    popupAnchor: [-3, -76],
});


var endIcon = L.icon({
    iconUrl: 'resources/images/map-marker-red.png',
    iconSize: [30, 50],
    popupAnchor: [-3, -76],
});

function FnConstructMapContent(){
	
var VarTxt="";
VarTxt+="<div>";
VarTxt+="<form id='inputForm' method='POST'>";
VarTxt+="<label for='tolerance' class='required'>Tolerance(mts)</label>";
VarTxt+="<input   class='k-textbox' style='margin-left: 3px;'  required validationMessage='Enter {0}'  name ='tolerance' type='number' onchange=fnChangeTolerance(this.value)  id ='tolerance' placeholder='enter tolerance'/>";
VarTxt+="<label for='stopageTime'  class='required' >StoppageTime</label>";
VarTxt+="<input class='k-textbox' style='margin-left: 7px;' type='number' name='stopageTime' required validationMessage='Enter {0}' onchange=fnChangeStoppage(this.value) id ='stopageTime' placeholder='enter stoppage time'/>";
VarTxt+="<button  id ='submit' class='k-button k-primary' type='button'>Ok</button>";
VarTxt+="</form>";
VarTxt+="</div>";

return VarTxt;
	
}

function fnDrawRoute(){
	//if drawing route from grid else comment this code
	console.log("waypointsssss");
	console.log(ArrPolyMarkers);
	control.setWaypoints(ArrPolyMarkers);
	control.route();	
	//ArrPolyMarkersTest=ArrPolyMarkers.slice();
}

function validateForm(){
	
	 var validator = $("#inputForm").kendoValidator().data("kendoValidator"),
     status = $(".status");	
	 
	$("#submit").on('click',function(event) {
        event.preventDefault();
        if (validator.validate()) {
        	validation=true;
        	objectMarker.marker.closePopup()
        } else {
        	validation=false;
            status.text("Oops! There is invalid data in the form.")
                .removeClass("valid")
                .addClass("invalid");
        }
    });
}

function fnCreatePopup(container,e){
	startBtn = createButton('Plot here', container,"green");	
	var popup = L.popup().setContent(container).setLatLng(e.latlng).openOn(
			map);
}

function fnCreatetMarkerFromGrid(latLng){
	count++;
	//control.setWaypoints(latlng);
	var geomarker = L.marker(latLng,{icon: FnGetMarkerIcon('blue'), draggable: true,bounceOnAdd:true}).addTo(map);
	objLine[geomarker._leaflet_id] = geomarker;
	ArrPolyMarkers.push(latLng);
	geomarker.on("drag", FnDragEvent);
	objectMarker={};
	objectMarker["marker"]=geomarker;	
	//validateForm();
	map.panTo(latLng);
	map.closePopup();
	return geomarker;
	
}

/* function plotting marker on the map */
function fnCreateStartMarker(latLng,isReverse){
	count++;
	//control.setWaypoints(latlng);
	ArrPolyMarkers.push(latLng);
	ArrPolyMarkersTest.push(latLng);
	var geomarker = L.marker(latLng,{icon: FnGetMarkerIcon('blue'), draggable: true,bounceOnAdd:false}).addTo(map);
	objLine[geomarker._leaflet_id] = geomarker;
	geomarker.on("drag", FnDragEvent);
	if(isReverse==true){
		geocoder.reverse(latLng,0,fnReverseResult,mapOnclick);	
	}
	
	objectMarker={};
	objectMarker["marker"]=geomarker;	
	if(ArrPolyMarkers.length>=2){
		$('#drawRoute').prop('disabled', false);	 
	}else{
		$('#drawRoute').prop('disabled', true);	
	}
	//validateForm();
	map.panTo(latLng);
	map.closePopup();
	
}

/* on reverse geocoding result comes to this function */
function fnReverseResult(result){
	console.log("result");
console.log(result.name);
objectMarker["geoName"]=result[0].name;
FnManageRouteGrid(objectMarker.marker,objectMarker.geoName);
	}

function FnDragEvent(e){
	var marker = e.target;
	var VarMarkerPos = $.inArray((marker._origLatlng), ArrPolyMarkersTest);
	//console.log(marker.getLatLng());
	if(VarMarkerPos!==-1){
		console.log(VarMarkerPos);
//		ArrPolyMarkers.splice(VarMarkerPos,1);
		ArrPolyMarkers.splice(VarMarkerPos, 1, (marker.getLatLng()));
	}

	
	FnManageRouteGrid(marker,null);
}



