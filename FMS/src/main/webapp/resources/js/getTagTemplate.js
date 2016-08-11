"use strict";
var ArrPolyMarkers = [];
var clickCircle;

$(document).ready(function() {
	console.log('getTagTemplate js called');

	map.on('click', function(e) {
		// if(GblEdit==1){ return false; }
		var VarIcon = FnGetMarkerIcon('blue');
		if (ArrPolyMarkers.length == 0) {
			var geomarker = L.marker(e.latlng, {
				icon : VarIcon,
				draggable : true
			}).addTo(map);
			ArrPolyMarkers.push(geomarker);
			console.log(ArrPolyMarkers);
			var c = e.latlng;
			var fc = ArrPolyMarkers[0].getLatLng();
			var dis = fc.distanceTo(c);
			// var ObjCircleOptions = FnGetStyleProperties();
			clickCircle = L.circle(fc, dis, null).addTo(map);
			$('#gllpLatitude').val(fc.lat);
			$('#gllpLongitude').val(fc.lng);
			geomarker.on("drag", FnDragEvent);
		} else if (ArrPolyMarkers.length != 0) {
			for (var i = 0; i < ArrPolyMarkers.length; i++) {
				map.removeLayer(ArrPolyMarkers[i]);
			}
			ArrPolyMarkers = [];
			var geomarker = L.marker(e.latlng, {
				icon : VarIcon,
				draggable : true
			}).addTo(map);
			geomarker.on("drag", FnDragEvent);
			ArrPolyMarkers.push(geomarker);
			var fc = ArrPolyMarkers[0].getLatLng();
			var dis = $('#radius').val() * 1000;
			clickCircle = L.circle(fc, dis, null).addTo(map);
			$('#gllpLatitude').val(fc.lat);
			$('#gllpLongitude').val(fc.lng);
			if (clickCircle != undefined) {
				map.removeLayer(clickCircle);
			}
		}
	});

	map.on('zoomend', function(e) {
		// if(GblEdit==1){ return false; }
		var VarZoomVal = map.getZoom();
		$('#gllpZoom').val(VarZoomVal);
	});

});

var map = null;
var streets;
var hybrid;
function FnInitiateMap() {
	console.log('FnInitiateMap called');
	// var VarZoomLevel = parseInt($('#zoom').val());
	var osmUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
	var osmAttrib = 'Map data © <a href="http://openstreetmap.org">OpenStreetMap</a>contributors';

	var osm = new L.TileLayer(osmUrl, {
		minZoom : 1,
		maxZoom : 12,
		attribution : osmAttrib
	});

	// streets =
	// L.tileLayer('https://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
	// id: 'Street',
	// minZoom: 1,
	// maxZoom: 18,
	// subdomains:['mt0','mt1','mt2','mt3'],
	// attribution: ""});
	//
	// hybrid =
	// L.tileLayer('https://{s}.google.com/vt/lyrs=s,h&x={x}&y={y}&z={z}', {
	// id: 'Satellite',
	// minZoom: 1,
	// maxZoom: 18,
	// subdomains:['mt0','mt1','mt2','mt3'],
	// attribution: ""});

	map = L.map('vehicleGeoMap', {
		zoom : 8,
		center : [ 25.20, 55.27 ],
		layers : osm,
		zoomControl : true,
		attributionControl : true
	});
	map.zoomControl.setPosition('bottomleft');

	// var baseMaps = {
	// "Satellite": hybrid,
	// "Streets": streets
	// };

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

	L.control.layers(osm, null, {
		position : 'topleft'
	}).addTo(map);

}

function FnGetMarkerIcon(VarColor) {

	var icon = new L.Icon({
		iconUrl : 'resources/plugins/leaflet/marker/' + VarColor+ '_marker.png',
		shadowUrl : 'resources/plugins/leaflet/images/marker-shadow.png',
		iconSize : [ 25, 41 ],
		iconAnchor : [ 12, 41 ],
		popupAnchor : [ 1, -34 ],
		shadowSize : [ 41, 41 ]
	});

	return icon;

}

function FnDragEvent(e) {
	// if(GblEdit==1){ return false; }
	var marker = e.target;
	var VarMarkerPos = $.inArray(marker, ArrPolyMarkers);
	ArrPolyMarkers.splice(VarMarkerPos, 1);
	ArrPolyMarkers.splice(VarMarkerPos, 0, marker);
	if (clickCircle != undefined) {
		map.removeLayer(clickCircle);

		// var ObjCircleOptions = FnGetStyleProperties();
		var fc = ArrPolyMarkers[0].getLatLng();
		var dis = $('#radius').val() * 1000;
		clickCircle = L.circle(fc, dis, null).addTo(map);
		$('#gllpLatitude').val(fc.lat);
		$('#gllpLongitude').val(fc.lng);
	}
}

// Map windows toggle actions ****************

function FnShowTags() {
	$("#tag-wrapper").slideToggle();

	setTimeout(function() {
		$(".gllpLatlonPicker").each(function() {
			$(document).gMapsLatLonPicker().init($(this));
		});
	}, 1000);
}

document.addEventListener('DOMContentLoaded', function() {
	// setTimeout(function() {
	// $(".gllpLatlonPicker").each(function() {
	// $(document).gMapsLatLonPicker().init( $(this) );
	// });
	// }, 1000);

	$('#add-geo-tag').on('click', function() {
		var var_lat = $("#gllpLatitude").val();
		var var_long = $("#gllpLongitude").val();
		//var_lat = precise_round(var_lat, 4);
		//var_long = precise_round(var_long, 4);
		
		var_lat = round(var_lat,4);
		var_long = round(var_long,4);
		//alert(var_lat+' /'+ var_long);
		$('#tag-latitude').val(var_lat);
		$('#tag-longitude').val(var_long);

		// $('#tag-latitude-display-value').html(lat);
		// $('#tag-longitude-display-value').html(long);
		var VarLatLongDisplay = round(var_lat,5) + ' , ' + round(var_long,5) + ' ';
		

		$('#tag-latitude-longitude-display-value').html(VarLatLongDisplay);
	});

	$('#clear-geo-tag').on('click', function() {
		$('#tag-latitude').val('');
		$('#tag-longitude').val('');
		// $('#tag-latitude-display-value').html('n/a');
		// $('#tag-longitude-display-value').html('n/a');

		$('#tag-latitude-longitude-display-value').html('');
	});
});


