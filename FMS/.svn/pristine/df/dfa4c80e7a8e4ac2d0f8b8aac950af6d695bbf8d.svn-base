"use strict";

$(document).ready(function(){
	FnInitiateMap();
	
});

$(window).load(function() {
	FnGetVehicleList();
});

var map = null;
var streets;
var hybrid;
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
		
	map = L.map('vehiclesmonitoringmap', {
		zoom: 5,
		center: [40.9743827,-97.6000859], // Dubai LatLng 25.20, 55.27
		layers: [hybrid,streets], // hybrid,streets
		zoomControl: true,
		attributionControl: true
	});
	map.zoomControl.setPosition('bottomright');

	var baseMaps = {
		"Satellite": hybrid,
		"Streets": streets
	};

	L.control.layers(baseMaps).addTo(map);
}

function FnGetVehicleList(){
	var VarUrl = "ajax" + VarListVehicleUrl;
	VarUrl = VarUrl + "?domain_name="+ UserInfo.getCurrentDomain() + "&mode=Asset";
	$("#GBL_loading").show();
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResVehicleList);
}

var GblAssetsList = {};
var GblAssetsInfo = {};
var GblImageList = {};
function FnResVehicleList(response){
	var ArrResponse = response.entity;
	$("#GBL_loading").hide();
	console.log(ArrResponse);
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		
		var ArrAssetLocations = [];
		var ArrDestinations = [];
		if(VarResLength > 0){
			
			GblImageList['vehicletype'] = FnGetImageList();
			GblImageList['vehicle'] = {};
			
			$.each(ArrResponse,function(){
				var VarDSName = (this.datasourceName != undefined) ? this.datasourceName : '';
								
				if(this.location != undefined){
					ArrAssetLocations.push({'latitude':parseFloat(this.location.latitude),'longitude':parseFloat(this.location.longitude),'datasourceName':VarDSName,'assetName':this.assetName,'template':this.templateName,'templateType':this.assetTemplate});
				}
								
				if(GblAssetsList[this.assetTemplate] == undefined){
					GblAssetsList[this.assetTemplate] = [];
					GblAssetsList[this.assetTemplate].push(this.assetName);
				} else {
					GblAssetsList[this.assetTemplate].push(this.assetName);
				}
				
				if(VarDSName != ''){
					if($.inArray(VarDSName,ArrDestinations) == -1){
						ArrDestinations.push(VarDSName);
					}
					GblAssetsInfo[VarDSName] = {"name":this.assetName,"id":this.assetIdentifier.value,'template':this.templateName,'templateType':this.assetTemplate};
				}
				
			});
		}
		
		FnApplyAssetMarkers(ArrAssetLocations,0);
		
		if(ArrDestinations.length > 0){
			FnGetSubscriptionInfo(ArrDestinations,'mqtt');
		}
				
	} else {
		if(ArrResponse.errorMessage != undefined){
			FnShowNotificationMessage(ArrResponse.errorMessage);
		}
	}
}

var Arrmarkers = {};
var GblAssetsMarkers = {};
function FnApplyAssetMarkers(ArrRes,VarFlag){
	if(ArrRes.length > 0){	
										
		$.each(ArrRes,function(){
			
			var IsMarkerExist = Arrmarkers[this.assetName];
			if(IsMarkerExist != undefined){
			
				var newLatLng = new L.LatLng(this.latitude, this.longitude);
				IsMarkerExist.setLatLng(newLatLng);
				
			} else {
			
				var VarIcon = FnGetMarkerHtmlIcon(this.templateType);
				var marker = L.marker([this.latitude, this.longitude], {icon: VarIcon}).bindPopup(FnConstructMapContent(this)).addTo(map);
			
				var VarMarkerAssetName = this.assetName;
				marker.on('click', function() {
					if(GblImageList['vehicle'][VarMarkerAssetName+'.png'] == undefined){
						var VarVehicleImage = FnGetVehicleImage(VarMarkerAssetName);
						GblImageList['vehicle'][VarMarkerAssetName+'.png'] = VarVehicleImage;
					}					
					
					if(GblImageList['vehicle'][VarMarkerAssetName+'.png'] != undefined){
						var VarImage = "data:image/png;base64,"+GblImageList['vehicle'][VarMarkerAssetName+'.png'];
						$('#vehicle_'+VarMarkerAssetName).html('<img src='+VarImage+' width="89" height="100" />');
					}
					
										
					$('ul.k-group li span.k-in').each(function () {				
						if($(this).attr("class") == 'k-in k-state-selected'){
							$(this).removeClass('k-state-selected');
						}
						if(VarMarkerAssetName.toUpperCase() == $(this).html()){
							$(this).addClass('k-state-selected');
						}
					});
				});
			
				Arrmarkers[this.assetName] = marker;

				if(VarFlag == 1){
					marker.openPopup();
					var ObjMarkerPos = marker.getLatLng();
					map.setView(new L.LatLng(parseFloat(ObjMarkerPos.lat), parseFloat(ObjMarkerPos.lng)),5);
					marker.bounce({duration: 1000, height: 100});
				}
			
				var VarGrpMarkerName = FnGetGrpMarkerName(this.assetName);
				if(GblAssetsMarkers[VarGrpMarkerName] == undefined){
					GblAssetsMarkers[VarGrpMarkerName] = [];
					GblAssetsMarkers[VarGrpMarkerName].push(marker);
				} else {
					GblAssetsMarkers[VarGrpMarkerName].push(marker);
				}
			}
						
		});
		
		FnCreateLayerGroup();
		
	}
	
}

function FnGetMarkerHtmlIcon(VarAssetTemplate){

	var VarAssetTypeImageSrc = '';
	if($.inArray(VarAssetTemplate+".png",GblImageList['assetType']) != -1){
		var VarAssetTypeImage = GblAppContextPath + VarAppImagePath + "/" + VarCurrentTenantInfo.tenantDomain + VarAppAssetTypeImagePath + VarAppMarkerImagePath + "/" + VarAssetTemplate+".png";
		VarAssetTypeImageSrc = '<img src='+VarAssetTypeImage+' style="height:23px" />';
	} else {
		var VarAssetTypeImage = "resources/images/noimage.png";
		VarAssetTypeImageSrc = '<img src='+VarAssetTypeImage+' style="height:23px" />';
	}

	var icon = L.divIcon({
		className: '',
		iconSize: [45, 45],
		iconAnchor:   [22, 45],
		popupAnchor:  [0, -37],
		html:'<div class="pin '+VarAssetTemplate+'">'+VarAssetTypeImageSrc+'</div>'
   });
   
   return icon;
   
}

function FnConstructMapContent(ObjResponse){
	
	var VarAssetIdentifier = GblAssetsInfo[ObjResponse.datasourceName]['id'];
	var Action = '';
	
	var VarAssetImageSrc = '';
	if(GblImageList['vehicle'] != undefined && GblImageList['vehicle'][ObjResponse.assetName+".png"] != undefined){
		var VarAssetImage = "";
		var VarImage = "data:image/png;base64,"+GblImageList['vehicle'][ObjResponse.assetName+".png"];
		VarAssetImageSrc = '<img src='+VarImage+' width="89" height="100" />';
	} else {
		VarAssetImageSrc = '';
	}
	
	var VarTxt = "";
	VarTxt += "<div class='monitoring-popup myAssetSlide'>";
	VarTxt += "<figure>"+VarAssetImageSrc+"</figure>";
	VarTxt += "<section>";
	VarTxt += "<strong class='popup-header' title="+ObjResponse.assetName+">"+ObjResponse.assetName+"</strong>";
	VarTxt += "<aside class='brand-success'>Active</aside>";
	VarTxt += "<aside class='brand-default'><strong>Latitude: </strong> "+ObjResponse.latitude+"</aside>";
	VarTxt += "<aside class='brand-default' style='margin-bottom:10px;'><strong>Longitude: </strong> "+ObjResponse.longitude+"</aside>";
	if(ObjResponse.time != undefined && ObjResponse.time != ''){
		VarTxt += "<aside class='brand-default' style='margin-bottom:10px;'><strong>Timestamp: </strong> "+ObjResponse.time+"</aside>";
	}
	VarTxt += "<button class='btn btn-xs green' onclick='FnAssetDetailsPage(\""+VarAssetIdentifier+"\",\""+Action+"\")'> Asset details <i class='fa fa-edit'></i></button>";
	
	VarTxt += "<div style='position:absolute;bottom:25px;right:10px;'>";
	//if(ObjPageAccess['livegrid'] === '1'){
		VarTxt += "<span class='map-assetlive-btn'><button  class='btn btn-xs yellow' id='map-assetlive' onclick='FnliveAssetPoint(\""+VarAssetIdentifier+"\",\""+ObjResponse.datasourceName+"\")' data-toggle='tooltip' title='Live Points'><i class='fa fa-edit' aria-hidden='true'></i></button></span>";
	//}
	
	//if(ObjPageAccess['historyview'] === '1'){
		VarTxt += "<span class='map-history-btn'><button  class='btn btn-xs yellow map-history' id='map-history'  onclick='FnPopUpPlotAssetHistory(\""+VarAssetIdentifier+"\",\""+ObjResponse.assetName+"\",\""+ObjResponse.datasourceName+"\")' data-toggle='tooltip' title='History'><i class='fa fa-history' aria-hidden='true'></i></button></span>";
	//}
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

var GblArrAssetLayers = {};
function FnCreateLayerGroup(){
	$.each(GblAssetsMarkers,function(name,Arr){
		var VarLayerName = L.layerGroup(Arr);
		GblArrAssetLayers[name] = VarLayerName;
	});
	
}

function FnSetDataUI(ObjData,isInitialData){
	console.log('--------Live Data---------');
	console.log(ObjData);
	if(!$.isEmptyObject(ObjData)){
		if(ObjData.messageType!='MESSAGE'){ return; }
				
		var VarDataSourceName = ObjData.datasourceName;
		var ArrParameters = ObjData.parameters;
		var VarTimestamp = ObjData.receivedTime;
		var element = {};
		
		for(var i=0; i<ArrParameters.length; i++){
			
			if(ArrParameters[i].name == 'Latitude' && ArrParameters[i].value != undefined && ArrParameters[i].value != ''){
				element['latitude'] = ArrParameters[i].value;
			} else if(ArrParameters[i].name == 'Longitude' && ArrParameters[i].value != undefined && ArrParameters[i].value != ''){
				element['longitude'] = ArrParameters[i].value;
			}			
		}
		
		if(!$.isEmptyObject(element) && element['latitude'] !='' && element['longitude'] != '' && element['latitude']!== undefined && element['longitude'] !== undefined){
			var ArrAssetLocations = [];
			element['datasourceName'] = VarDataSourceName;
			element['assetName'] = GblAssetsInfo[VarDataSourceName]['name'];
			element['template'] = GblAssetsInfo[VarDataSourceName]['template'];
			element['templateType'] = GblAssetsInfo[VarDataSourceName]['templateType'];
			element['time'] = VarTimestamp;
			ArrAssetLocations.push(element);
			FnApplyAssetMarkers(ArrAssetLocations,0);
			element = {};
				
		}
	}
}

function FnGetGrpMarkerName(VarAssetName){
	var VarGrpMarkerName = '';
	$.each(GblAssetsList,function(templatename,ArrAssets){
		if($.inArray(VarAssetName,ArrAssets) != -1){
			VarGrpMarkerName = templatename;
		}
	});
	
	return VarGrpMarkerName;
}

function FnGetImageList(){
	var ObjImages = {};
	$.ajax({
		type: 'GET',
		async: false,
		url: 'vehicle_icons',
		processData: false,
		contentType: 'application/json; charset=utf-8',
		dataType: 'text',
		success:function(response){
			if(response != ''){
				var ObjResponse = $.parseJSON(response);
				ObjImages = ObjResponse;	
			}
				
		},
		error:function(xhr, status, error){
			//console.log(xhr);
		}
	});
	
	
	return ObjImages;
}

function FnGetVehicleImage(VarAssetName){
	var VarVehicleImage;
	$.ajax({
		type: 'POST',
		async: false,
		url: 'vehicle_image',
		processData: false,
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: JSON.stringify({'key':VarAssetName}),
		success:function(response){
			var ObjResponse = response;
			if(!$.isEmptyObject(ObjResponse) && ObjResponse['key'] == VarAssetName){
				VarVehicleImage = ObjResponse['value'];
			}
				
		},
		error:function(xhr, status, error){
			//console.log(xhr);
		}
	});
	
	return VarVehicleImage;
}

