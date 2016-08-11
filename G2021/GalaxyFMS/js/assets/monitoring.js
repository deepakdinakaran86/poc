$(document).ready(function(){
FnInitiateMap();
FnInitializeAssetTree();
});

var map = null;
var streets;
var hybrid;
var marker;

function FnInitiateMap(){
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
	
	map = L.map('assetsmonitoringmap', {
		zoom: 5,
		center: [22.20, 55.27], // Dubai LatLng 25.20, 55.27
		layers: [hybrid,streets],
		zoomControl: true,
		attributionControl: true
	});
	
	var VarIcon = FnGetMarkerHtmlIcon();
	
	//var marker = L.marker([this.latitude, this.longitude], {icon: VarIcon}).bindPopup(FnConstructMapContent(this)).addTo(map);
	marker = L.marker([25.20, 55.27], {icon: VarIcon}).bindPopup(FnConstructMapContent()).addTo(map),
	map.zoomControl.setPosition('bottomright');

	var baseMaps = {
		"Satellite": hybrid,
		"Streets": streets
	};

	L.control.layers(baseMaps).addTo(map);
}

function FnInitializeAssetTree(){
 $("#treeview").kendoTreeView();
 }


function FnGetMarkerHtmlIcon(){
	var icon = L.divIcon({
		className: '',
		iconSize: [45, 45],
		iconAnchor:   [22, 45],
		popupAnchor:  [0, -37],
		html:'<div class="pin"></div>'
   });
   
   return icon;
   
}

function FnConstructMapContent(){
	
	var VarTxt = "";
	VarTxt += "<div class='monitoring-popup myAssetSlide'>";
	VarTxt += "<figure></figure>";
	VarTxt += "<section>";
	VarTxt += "<strong class='popup-header' title='Title'>Car</strong>";
	VarTxt += "<aside class='brand-success'>Active</aside>";
	VarTxt += "<aside class='brand-default'><strong>Latitude: </strong> 25.20</aside>";
	VarTxt += "<aside class='brand-default' style='margin-bottom:10px;'><strong>Longitude: </strong> 55.27</aside>";
	
	VarTxt += "<button class='btn btn-sm btn-info'> Asset details <i class='fa fa-edit'></i></button>";
	
	VarTxt += "<div style='position:absolute;bottom:25px;right:10px;'>";

	VarTxt += "</div>";
	VarTxt += "</section>";
	VarTxt += "</div>";		
	
	
	VarTxt += "<div id='gapp-assetlive-container' style='display:none;left:340px;width:0px;opacity:0'>";
	VarTxt += "<div class='pull-left' style='margin: 9px 11px;font-weight: bold;'>Live Points List</div>";
	VarTxt += "<button type='button' class='close gapp-assetliveclose' id='gapp-assetlive-close' aria-label='Close' onclick='FnliveAssetClose()'><span aria-hidden='true'>&times;</span></button>";
	VarTxt += "<div id='gapp-livegrid' style='margin-top: 35px;'></div>";
	VarTxt += "</div>";
	return VarTxt;
	
}