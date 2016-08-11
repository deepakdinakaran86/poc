"use strict";

$(document).ready(function(){
	FnInitiateMap();
	
	GblStartEnd.start = $("#startDate").kendoDatePicker({
		change: startChange,
		format: "dd/MM/yyyy"
	}).data("kendoDatePicker");
	
	GblStartEnd.end = $("#endDate").kendoDatePicker({
		change: endChange,
		format: "dd/MM/yyyy"
	}).data("kendoDatePicker");
	
	var VarDateToday   = FnGetTodaysDate();
	var VarDateAddDays = FnAddDaysToDate(1);
	var VarDateSubDays = FnSubtractDaysToDate(7);
	GblStartEnd.start.max(VarDateToday);
	GblStartEnd.end.min(GblStartEnd.start.value());
	GblStartEnd.end.max(VarDateToday);
	$('#startDate').mask("00/00/0000", {placeholder: "DD/MM/YYYY"});
	$('#endDate').mask("00/00/0000", {placeholder: "DD/MM/YYYY"});
	$("#startDate").val(VarDateToday);
	$("#endDate").val(VarDateToday);
	
	$('body').tooltip({
		selector: '#map-assetlive, #map-history'
	});
	
		
	$('body').on('click', 'a.leaflet-popup-close-button', function() {
		FnliveAssetClose();
	});
		
});

$(window).load(function() {
	FnGetVehicleList();
	FnGetGeofenceList();
});

var GblStartEnd = {};
function startChange() {
	var startDate = GblStartEnd.start.value(),
		endDate = GblStartEnd.end.value();

	if (startDate) {
		startDate = new Date(startDate);
		startDate.setDate(startDate.getDate());
		GblStartEnd.end.min(startDate);
	} else if (endDate) {
		GblStartEnd.start.max(new Date(endDate));
	} else {
		endDate = new Date();
		GblStartEnd.start.max(endDate);
		GblStartEnd.end.min(endDate);
	}
}

function endChange() {
	var endDate = GblStartEnd.end.value(),
	startDate = GblStartEnd.start.value();
	
	if (endDate) {
		endDate = new Date(endDate);
		endDate.setDate(endDate.getDate());
		GblStartEnd.start.max(endDate);
	} else if (startDate) {
		GblStartEnd.end.min(new Date(startDate));
	} else {
		endDate = new Date();
		GblStartEnd.start.max(endDate);
		GblStartEnd.end.min(endDate);
	}
}

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
		var ArrData = [];
		if(VarResLength > 0){
			
			GblImageList['vehicletype'] = FnGetImageList();
			GblImageList['vehicle'] = {};
			
			var ObjTypeRef = {}; 
			$.each(ArrResponse,function(){
				
				var VarDSName = (this.datasourceName != undefined) ? this.datasourceName : '';
				if(ObjTypeRef[this.assetTemplate] == undefined){
					
					if(GblImageList['vehicletype'][this.assetTemplate+".png"] != undefined){
						var VarImageUrl = "data:image/png;base64,"+GblImageList['vehicletype'][this.assetTemplate+".png"];
					} else {
						var VarImageUrl = "resources/images/noimage.png";
					}
					
					ArrData.push({'id':this.assetTemplate,'text':this.assetTemplate,'items':[],'imageUrl': VarImageUrl});
					ObjTypeRef[this.assetTemplate] = (ArrData.length - 1);
				}				
				
				var VarTypeRef = ObjTypeRef[this.assetTemplate];
				ArrData[VarTypeRef]['items'].push({'id':this.assetIdentifier.value,'text':(this.assetName).toUpperCase(),'datasourceName':VarDSName,'assetName':this.assetName,'template':this.templateName,'templateType':this.assetTemplate});
								
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
		
				
		$("#treeview").kendoTreeView({
			checkboxes: {
				template: "# if(item.hasChildren){# <input type='checkbox'  name='checkedFiles[]' value='#= item.id #' />#}#"
			},
			select: function(e){
				
				clearPlotHistoryDetails();
				
				var tree = $("#treeview").getKendoTreeView();
				var dataItem = tree.dataItem(e.node);
				if(dataItem.template != undefined){
					
					GblMonitorPointHistory = '';
					GblMonitorPoint = '';
					
					var grid = $('#gapp-livegrid').data("kendoGrid");
					if(grid != undefined){
						grid.destroy();
					}
					
					var chart = $("#gapp_point_history").data("kendoChart");
					if(chart != undefined){
						chart.destroy();
					}
					
					if ($('#assetcountpanel').css('display') == 'block') {
						var height = '-=' + $('#assetcountpanel').height();
						$("#assetcountpanel").slideToggle("fast");
						$("#upper").animate({
							bottom: height
						}, "slow");
					}
					
					var ObjAssetParam = {
							'datasourceName' : dataItem.datasourceName,
							'template' : dataItem.template,
							'assetName' : dataItem.assetName,
							'assetId' : dataItem.id,
							'templateType' : dataItem.templateType,
					};
					
					FnHighlightMarker(ObjAssetParam);
				}
			},
			check: function(e) {
				//var tree = $("#treeview").getKendoTreeView();
				//var dataItem = tree.dataItem(e.node);
				//console.log(dataItem);
				FnSearchAssetTypeMarkers(1);
			},
			dataSource: ArrData
		});
		
		$('#treeview #'+UserInfo.getCurrentTenantId()).attr('disabled',true);
		var treeview = $("#treeview");
		var kendoTreeView = treeview.data("kendoTreeView");
		
		treeview.on("click", ".k-top.k-bot span.k-in", function(e) {
			kendoTreeView.toggle($(e.target).closest(".k-item"));
		});	
		kendoTreeView.expand(".k-item");
		
		$("#fms_vehiclecount").text(VarResLength);
		FnApplyAssetMarkers(ArrAssetLocations,0);
		
		if(ArrDestinations.length > 0){ 
			FnGetSubscriptionInfo(ArrDestinations,'mqtt');
		}
		
		FnInitSearch("#treeview", "#treeSearchInput");
		
	} else {
		if(ArrResponse.errorMessage != undefined){
			FnShowNotificationMessage(ArrResponse.errorMessage);
		}
	}
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

function FnInitSearch(treeViewId, searchInputId) {

    var tv = $(treeViewId).data('kendoTreeView');

    $(searchInputId).on('keyup', function () {

        $(treeViewId + ' li.k-item').show();

        $('span.k-in > span.highlight').each(function () {
            $(this).parent().text($(this).parent().text());
        });

        // ignore if no search term
        if ($.trim($(this).val()) === '') {
            return;
        }

        var term = this.value.toUpperCase();
        var tlen = term.length;

        $(treeViewId + ' span.k-in').each(function (index) {
            var text = $(this).text();
            var html = '';
            var q = 0;
            var p;

            while ((p = text.toUpperCase().indexOf(term, q)) >= 0) {
                html += text.substring(q, p) + '<span class="highlight">' + text.substr(p, tlen) + '</span>';
                q = p + tlen;
            }

            if (q > 0) {
                html += text.substring(q);
                $(this).html(html);

                $(this).parentsUntil('.k-treeview').filter('.k-item').each(function (index, element) {
                    tv.expand($(this));
                    $(this).data('SearchTerm', term);
                });
            }
        });

        $(treeViewId + ' li.k-item:not(:has(".highlight"))').hide();
		tv.expand(".k-item");
    });
}

function FnHighlightMarker(ObjAssetParam){	
	var marker = Arrmarkers[ObjAssetParam['assetName']];
	if(marker != undefined){	
		marker.openPopup();
		var VarZoom = parseInt(map.getZoom());
		var ObjMarkerPos = marker.getLatLng();
		map.setView(new L.LatLng(parseFloat(ObjMarkerPos.lat), parseFloat(ObjMarkerPos.lng)),VarZoom);
		//map.panTo(new L.LatLng(parseFloat(ObjMarkerPos.lat), parseFloat(ObjMarkerPos.lng)));
		marker.bounce({duration: 1000, height: 100});
		
	} else {
	
		var ObjParam = {
				"domain" : {
					"domainName" : UserInfo.getCurrentDomain()
				},
				"identifier" : {
					"key": "identifier",
					"value" : ObjAssetParam.assetId
				},
				"entityTemplate" : {
					"entityTemplateName" : "Asset"
				}
		};
		
		$("#GBL_loading").show();
		$.ajax({
			type:'POST',
			cache: true,
			async: true,
			contentType: 'application/json; charset=utf-8',
			url: "ajax/" + VarSearchGeoTagUrl,
			data: JSON.stringify(ObjParam),
			dataType: 'json',
			success: function(response) {
				
				var ObjRes = response.entity;
				console.log(ObjRes);
				
				if(ObjRes.geotag != undefined){
					var VarDSName = FnFindDatasourceName(ObjAssetParam);
					$("#GBL_loading").hide();
					console.log(VarDSName);
					if(VarDSName != ''){
						GblAssetsInfo[VarDSName] = {"name":ObjAssetParam.assetName,"id":ObjAssetParam.assetId,'template':ObjAssetParam.template,'templateType':ObjAssetParam.templateType};
						var ArrDestinations = [];
						$.each(GblAssetsInfo,function(key,Obj){
							if($.inArray(key,ArrDestinations) == -1){
								ArrDestinations.push(key);
							}
						});
						if(GblIsSubcribe === true){
							FnUnSubscribe();
						}
						FnGetSubscriptionInfo(ArrDestinations,'mqtt');
					}
					
					var ArrAssetLocations = [];
					var element = {
							"datasourceName" : VarDSName,
							"assetName" : ObjAssetParam.assetName,
							"template" : ObjAssetParam.template,
							"templateType" : ObjAssetParam.templateType,
							"latitude" : ObjRes.geotag.latitude,
							"longitude" : ObjRes.geotag.longitude
							
					};
					ArrAssetLocations.push(element);
					FnApplyAssetMarkers(ArrAssetLocations,1);
					
				} else {
					$("#GBL_loading").hide();
					var ObjError = {"errorCode" : "500", "errorMessage" : "No location mapped to the asset."};
					FnShowNotificationMessage(ObjError);
				}
				
			},
			error : function(jqXHR,textStatus) {
				$("#GBL_loading").hide();
				console.log('Error');
				var VarResponse = $.parseJSON(jqXHR.responseText);
				var ObjError = {"errorCode" : "500", "errorMessage" : "No location mapped to the asset."};
				FnShowNotificationMessage(ObjError);
			}
		
		});
	}
}

function FnFindDatasourceName(ObjAssetParam){
	var VarDSName = '';
	var ObjParam = {
			"domain" : {
				"domainName" : UserInfo.getCurrentDomain()
			},
			"identifier" : {
				"key": "identifier",
				"value" : ObjAssetParam.assetId
			},
			"entityTemplate" : {
				"entityTemplateName" : "Asset"
			}
	};
	
	$.ajax({
		type:'POST',
		cache: true,
		async: false,
		contentType: 'application/json; charset=utf-8',
		url: "ajax" + VarVehicleFindUrl,
		data: JSON.stringify(ObjParam),
		dataType: 'json',
		success: function(response) {
			var ObjResponse = response.entity;
			console.log(ObjResponse);
			if(ObjResponse.points != undefined && (ObjResponse.points).length > 0){
				var ObjVehiclePoints = ObjResponse.points[0];
				$.each(ObjVehiclePoints.dataprovider,function(key,obj){
					if(obj['key'] == 'dataSourceName' && obj['value'] != ''){
						VarDSName = obj['value'];
					}
				});
			}
		},
		error : function(jqXHR,textStatus) {
			console.log('Error');
		}
		
	});

	return VarDSName;
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
					//marker.openPopup();
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
		FnConstructCountDetails();
	}
	
}

function FnGetMarkerHtmlIcon(VarAssetTemplate){

	var VarAssetTypeImageSrc = '';
	if(GblImageList['vehicletype'][VarAssetTemplate+".png"] != undefined){
		var VarImage = "data:image/png;base64,"+GblImageList['vehicletype'][VarAssetTemplate+".png"];
		VarAssetTypeImageSrc = '<img src='+VarImage+' style="height:23px" />';
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
	var Action = '';
	var VarAssetIdentifier='',AssetDetailActionTxt='',LivePointsTxt='',HistoryViewTxt = '';
	if(ObjResponse.datasourceName != ''){
		VarAssetIdentifier = GblAssetsInfo[ObjResponse.datasourceName]['id'];
		AssetDetailActionTxt = "<button class='btn btn-primary' onclick='FnAssetDetailsPage(\""+VarAssetIdentifier+"\")'> Asset details <i class='fa fa-edit' style='position: relative;top: 2px;padding-left: 3px;'></i></button>";
		LivePointsTxt = "<span class='map-assetlive-btn'><button  class='btn btn-info' style='padding: 6px 12px 3px;' id='map-assetlive' onclick='FnliveAssetPoint(\""+VarAssetIdentifier+"\",\""+ObjResponse.datasourceName+"\")' data-toggle='tooltip' title='Live Points'><i class='fa fa-edit' aria-hidden='true'></i></button></span>";
		HistoryViewTxt = "<span class='map-history-btn'><button  class='btn btn-info map-history' id='map-history' style='padding: 5px 13px 3px;'  onclick='FnPopUpPlotAssetHistory(\""+VarAssetIdentifier+"\",\""+ObjResponse.assetName+"\",\""+ObjResponse.datasourceName+"\")' data-toggle='tooltip' title='History'><i class='fa fa-history' aria-hidden='true'></i></button></span>";
	}
		
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
	VarTxt += "<figure id='vehicle_"+ObjResponse.assetName+"'>"+VarAssetImageSrc+"</figure>";
	VarTxt += "<section>";
	VarTxt += "<strong class='popup-header' title="+ObjResponse.assetName+">"+ObjResponse.assetName+"</strong>";
	VarTxt += "<aside class='brand-success'>Active</aside>";
	VarTxt += "<aside class='brand-default'><strong style='padding-right: 14px;'>Latitude: </strong> "+ObjResponse.latitude+"</aside>";
	VarTxt += "<aside class='brand-default' style='margin-bottom:10px;'><strong>Longitude: </strong> "+ObjResponse.longitude+"</aside>";
	if(ObjResponse.time != undefined && ObjResponse.time != ''){
		VarTxt += "<aside class='brand-default' style='margin-bottom:10px;'><strong>Timestamp: </strong> "+ObjResponse.time+"</aside>";
	}
	VarTxt += AssetDetailActionTxt;
	
	VarTxt += "<div style='position:absolute;bottom:32px;right:10px;'>";
	//if(ObjPageAccess['livegrid'] === '1'){
		VarTxt += LivePointsTxt;
	//}
	
	//if(ObjPageAccess['historyview'] === '1'){
		VarTxt += HistoryViewTxt;
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

function FnConstructCountDetails(){
	var VarMarkerCount = (Object.keys(Arrmarkers)).length;
	$('#asset-toggle').text(VarMarkerCount);
	$('#assettype_details_list').html('');
	var VarCountDetailsTxt = '';
	if(VarMarkerCount > 0){
		VarCountDetailsTxt += '<div class="row" style="margin: 20px 0px;"><div class="col-md-12">';
		$.each(GblAssetsMarkers,function(templatename,ArrMarkers){
			var VarAssetTypeImageSrc = '';
			
			if(GblImageList['vehicletype'][templatename+".png"] != undefined){
				var VarImage = "data:image/png;base64,"+GblImageList['vehicletype'][templatename+".png"];
				VarAssetTypeImageSrc = '<img src='+VarImage+' style="height:23px" />';
			} else {
				var VarAssetTypeImage = "resources/images/noimage.png";
				VarAssetTypeImageSrc = '<img src='+VarAssetTypeImage+' style="height:23px" />';
			}
					
			var VarAssetCount = ArrMarkers.length;
			
			VarCountDetailsTxt += '<a href="Javascript:void(0)"><div class="pincount">'+VarAssetTypeImageSrc+'</div><section style="float:right;text-align: left;margin: 6px 0px 10px 10px;"><div><label style="font-size: 15px; margin-right: 5px;">'+templatename+'</label><label class="control control--checkbox"><input type="checkbox" name="assetTypes[]" value="'+templatename+'" onclick="FnSearchAssetTypeMarkers(0)"/><div class="control__indicator"></div></label></div><div style="color: #37DCBF;font-size: 15px;">'+VarAssetCount+'</div></section></a>';
			
		});
		VarCountDetailsTxt += '</div></div>';
	} else {
		VarCountDetailsTxt += 'No Records to display';
	}
		
	$('#assettype_details_list').html(VarCountDetailsTxt);
}

function FnSearchAssetTypeMarkers(VarFlag){
	$('.pin').removeClass('highlight');
	var txtElement = (VarFlag == 1) ? '#treeview input[name="checkedFiles[]"]' : '#assettype_details_list input[name="assetTypes[]"]';
	
	$(txtElement).each(function() {
		if($(this).is(':checked') == true){
			var VarAssetType = $(this).val();
			$('.pin.'+VarAssetType).addClass('highlight');
			
		}
	});	
}

function FnInitAssetPointsGrid() {
	
	var deviceData = new kendo.data.DataSource({
			data : [],
			pageSize : 5,
			page : 1,
			serverPaging : false,
			serverFiltering : false,
			serverSorting : false,			
			sort : {
				field : "name",
				dir : "asc"
			}
		});	
		
	var ArrColumns = [					
				{
					field : "displayid",
					title : "Point Id",
					sortable: false,
					width: 60
				},
				{
					field : "pointName",
					title : "Point name",
					sortable: true,
					filterable:true,
					width: 70
				}, 
				{
					field : "name",
					title : "Display name",
					sortable: true,
					filterable:true,
					width: 70
				},
				{
					field : "value" ,
					title : "Value",
					filterable:false,
					sortable: false,
					width: 60,
					template :"#: FnCheckForValue(value)#",
				},
				{
					field : "unit" ,
					title : "Unit",
					filterable:false,
					sortable: false,					
					template :"#: FnCheckForUnitless(unit)#",
					width: 80
				
				},{
					field : "time",
					title : "Time stamp (GMT)",
					template :"#: FnCheckTimeEmpty(time)#",		
					sortable: false,
					filterable:false,
					width: 100
				}
				
			];

			
	var ObjGridConfig = {
		"scrollable" : true,
		"filterable" : { mode : "row", extra: false },
		"sortable": { mode: "single",	"allowUnsort": true},
		"pageable" : { pageSize: 5,numeric: true, pageSizes: true,refresh: false },
		"selectable" : true,
		"resizable": true		
	};
	
	FnDrawGridView('#gapp-livegrid',deviceData,ArrColumns,ObjGridConfig);	
		
	/* grid change event */		
	var kendoGrid = $("#gapp-livegrid").data('kendoGrid');
	kendoGrid.bind("change", FnGridChangeEvent);
	
}

var GblMonitorPointHistory = '';
function FnGridChangeEvent(){
	GblMonitorPointHistory = '';
	var kendoGrid = $("#gapp-livegrid").data('kendoGrid');
	var row = kendoGrid.select();
	var data = kendoGrid.dataItem(row);
	
	if ($('#assetcountpanel').css('display') == 'none') {
        var height = '-=' + $('#assetcountpanel').height();
		$("#assetcountpanel").slideToggle("fast");
		$("#upper").animate({
			bottom: height
		}, "slow");
    }
    	
	$('#gapp-chart-id').trigger('click');
	FnInitHistroyData(data);
}

var GblSelPointHistory = '';
function FnInitHistroyData(ObjSelecetdPoint){	
	//kendo.ui.progress($("#gapp_point_history"), true);
	var ObjParam = {
		"sourceId" : ObjSelecetdPoint.dsname,
		"startDate" : FnConvertLocalToUTC(FnAssetDateProcess('current')),
		"endDate" : FnConvertLocalToUTC(FnAssetDateProcess('next',1)),
		"displayNames" : [ObjSelecetdPoint.name]
	};			
	GblSelPointHistory = ObjSelecetdPoint.name;
	var VarUrl = 'ajax/' + VarGetDeviceDataUrl;	
	GblMonitorPointHistory = GblSelPointHistory + "@" + GblMonitorPoint;
	FnMakeAsyncAjaxRequest(VarUrl,'POST', JSON.stringify(ObjParam), 'application/json; charset=utf-8', 'json', FnResInitHistroyData);
}

function FnResInitHistroyData(response){
	//kendo.ui.progress($("#gapp_point_history"), false);	
	var ObjResponse = response.entity;	
	var ErrorMsgHistroyData =" Point cannot be plotted";
	
	if (!$.isEmptyObject(ObjResponse)) {
		if(ObjResponse.errorCode == undefined && ObjResponse.displayNames != undefined && (ObjResponse.displayNames).length > 0){
			var VarSourceId = ObjResponse.sourceId;
			var ArrResponseData = [];
			
			$.each(ObjResponse.displayNames, function () {
				GblMonitorPointHistory = this.displayName + "@" + VarSourceId;
				$.each(this.values,function(){
					ArrResponseData.push({"deviceTime":this.deviceTime,"data":this.data});
				});
			});
			
			FnInitPointChartHistory(ArrResponseData);
			
		} else {
			notificationMsg.hide();
			if (ObjResponse.errorCode =="508"){
			
				notificationMsg.show({
					message : "Point name is invalid"
				}, 'error');
				
			}
			else if(ObjResponse.errorCode =="500"){
				
				notificationMsg.show({
					message : "Requested data is not available"
				}, 'info');
				
			} else {
			
				notificationMsg.show({
					message : ObjResponse.errorMessage
				}, 'error');
			}
			GblMonitorPointHistory = GblSelPointHistory + "@" + GblMonitorPoint;
			FnInitPointChartHistory([]);
		
		}
	}
}

function FnInitPointChartHistory(response) {
	var ArrResponse = response;
	var chart = $("#gapp_point_history").data("kendoChart");
	if(chart != undefined){
		chart.destroy();
	}	
			
	var chartDate = toDateFormatChart(new Date());
	var VarTitleTxt = '';
	if(GblMonitorPointHistory != ''){
		var ArrHis = GblMonitorPointHistory.split('@');
		VarTitleTxt = ArrHis[0] + '('+chartDate+')';
	}
		 
	$("#gapp_point_history").kendoChart({
		title : {
			text: VarTitleTxt,
			align: "center",
			background: "transparent",
			color: "#000000"
		},
		chartArea: {
			background: "#FFFFFF"
		},
		dataSource: {
			data: ArrResponse
		},
		legend : {
			visible: false
		},
		seriesDefaults : {			
			style : "smooth",
			type : "line",
			labels: {
				visible: false,
				background: "transparent"
			},
			area: {
				line: {
					style: "smooth"
				}
			}
		},
		valueAxis: {
			majorGridLines: {
				visible: true
			},
			visible: true
		},
		series: [{
			field: "data",
			color: "#FF3E01",
			tooltip: {
				visible: true
			}
		}],
		seriesColors:["#FF5733"],
		categoryAxis: {
			field: "deviceTime",
			majorGridLines: {
				visible: false
			},
			line: {
				visible: true
			},
			labels: {
			  visible: false
			},
			title: { 
				text: 'Time',
				visible: false
			}
		},
		tooltip: {
			visible: true,
			format: "{0}%",					
			template: "  #= toDateFormatRemoveGMT(category,':')# || #= value #"
		}

	});
	
	var chartlive = $("#gapp_point_live").data("kendoChart");
	if(chartlive != undefined){
		chartlive.destroy();
	}
			 
	$("#gapp_point_live").kendoChart({
		title : {
			text: VarTitleTxt,
			align: "center",
			background: "transparent",
			color: "#000000"
		},
		chartArea: {
			background: "#FFFFFF"
		},
		dataSource: {
			data: []
		},
		legend : {
			visible: false
		},
		seriesDefaults : {			
			style : "smooth",
			type : "line",
			labels: {
				visible: false,
				background: "transparent"
			},
			area: {
				line: {
					style: "smooth"
				}
			}
		},
		valueAxis: {
			majorGridLines: {
				visible: true
			},
			visible: true
		},
		series: [{
			field: "data",
			color: "#FF3E01",
			tooltip: {
				visible: true
			}
		}],
		seriesColors:["#FF5733"],
		categoryAxis: {
			field: "deviceTime",
			majorGridLines: {
				visible: false
			},
			line: {
				visible: true
			},
			labels: {
			  visible: false
			},
			title: { 
				text: 'Time',
				visible: false
			}
		},
		tooltip: {
			visible: true,
			format: "{0}%",					
			template: "  #= toDateFormatRemoveGMT(category,':')# || #= value #"
		}

	});

}

function FnSetDataUI(ObjData,isInitialData){
	console.log('--------live data--------');
	console.log(ObjData);
	if(!$.isEmptyObject(ObjData)){
		if(ObjData.messageType!='MESSAGE'){ return; }
		
		var grid = $("#gapp-livegrid").data("kendoGrid");
		var pointchart = $("#gapp_point_history").data("kendoChart");
		var pointlivechart = $("#gapp_point_live").data("kendoChart");
		
		var VarDataSourceName = ObjData.datasourceName;
		var ArrParameters = ObjData.parameters;
		var VarTimestamp = ObjData.receivedTime;
		var element = {};
		
		for(var i=0; i<ArrParameters.length; i++){
			
			if(ArrParameters[i].name == 'Latitude' && ArrParameters[i].value != undefined && ArrParameters[i].value != ''){
				element['latitude'] = ArrParameters[i].value;
			} else if(ArrParameters[i].name == 'Longitude' && ArrParameters[i].value != undefined && ArrParameters[i].value != ''){
				element['longitude'] = ArrParameters[i].value;
			} else {
				if(GblMonitorPoint != '' && GblMonitorPoint === VarDataSourceName){
					
					var VarParamName = ArrParameters[i].name;
					var VarId = VarDataSourceName.replace(/ /g, '_') +"_"+ VarParamName.replace(/ /g, '_');
					var isGExist = false;
				
					if(grid != undefined){
					
						for (var k = 0; k < grid.dataSource.data().length; k++) {
							var VarChkId = grid.dataSource.data()[k].rowid;
							
							if (VarId == VarChkId) {
								isGExist = true;
								var item = grid.dataSource.data()[k];
								item.set('value', ArrParameters[i].value);
								item.set('time', ArrParameters[i].time);
							}
						}
					
						/*if (!isGExist) {
							grid.dataSource.add({
									time : ArrParameters[i].time,
									value : ArrParameters[i].value,
									unit : ArrParameters[i].unit,
									rowid : VarId,
									datatype : ArrParameters[i].dataType,
									name: ArrParameters[i].name,
									displayid: ArrParameters[i].displayid,
									dsname : VarDataSourceName,
									pointName: ArrParameters[i].pointName
									
							});
						}*/
						grid.dataSource.sync();
					}
					
					if(GblMonitorPointHistory != ''){
						var ArrHis = GblMonitorPointHistory.split('@');
						if(ArrHis[1] === VarDataSourceName){
									
							if(pointchart != undefined && ArrHis[0] === VarParamName){								
								pointchart.dataSource.add({"deviceTime":ArrParameters[i].time,"data":ArrParameters[i].value});
								pointlivechart.dataSource.add({"deviceTime":ArrParameters[i].time,"data":ArrParameters[i].value});
								
								//pointchart.dataSource.sync();
							} else if(pointchart == undefined && ArrHis[0] === VarParamName){
								FnInitPointChartHistory([{"deviceTime":ArrParameters[i].time,"data":ArrParameters[i].value}]);
								pointlivechart.dataSource.add({"deviceTime":ArrParameters[i].time,"data":ArrParameters[i].value});
								//pointchart.dataSource.sync();
							}
						}
					}
					
				}
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

function FnManageAssetInfo(){
	$("#sidebar-wrapper").css({width:"40px"});
	$(".sidebar-body").hide();
	$(".caption-title").css({"transform":"translate(-86px, -105px) rotate(90deg) scale(1)","transition": "transform 0.2s","transform-origin": "left center","width": "115px","height": "203px","left":"10px","top":"35px"});
	if ($('#assetcountpanel').css('display') == 'block') {
        var height = '-=' + $('#assetcountpanel').height();
    } else {
        var height = '+=' + $('#assetcountpanel').height();
    }
    $("#assetcountpanel").slideToggle("fast");
    $("#upper").animate({
        bottom: height
    }, "slow");
}

function FnCheckForValue(VarValue){
	if (VarValue == '' || VarValue== null) {
			VarValue=' - ';			
	}			
	return VarValue;	
}

function FnCheckForUnitless(VarUnit){		
	if (VarUnit =='unitless') {VarUnit=' - '};
	return VarUnit;
}

function FnCheckTimeEmpty(VarTime){
	if (VarTime =='' || VarTime==null) {
		VarTime=' - ';			
	} else {
		VarTime =toDateFormatRemoveGMT(VarTime, 'F');			
	}
	return VarTime;
}

var GblMonitorPoint = '';
function FnGetAssetPointsLatestInfo(VarAssetIdentifier,VarDsName){	
	FnInitAssetPointsGrid();
	kendo.ui.progress($("#gapp-livegrid"), true);
	var VarUrl = 'ajax' + VarGetAssetLatestUrl;
	
	var data = {
		"domain" : {
			"domainName" : UserInfo.getCurrentDomain()
		},
		"platformEntity" : {
			"platformEntityType" : "MARKER"
		},
		"entityTemplate" : {
			"entityTemplateName" : "Asset"
		},
		"identifier" : {
			"key" : "identifier",
			"value" : VarAssetIdentifier
		}
	};
	
	FnMakeAsyncAjaxRequest(VarUrl, 'POST',JSON.stringify(data), 'application/json; charset=utf-8', 'json', FnResAssetPointsLatestInfo);
}

function FnResAssetPointsLatestInfo(response) {
	var ArrResponse = response.entity;
	kendo.ui.progress($("#gapp-livegrid"), false);
	
	if($.isArray(ArrResponse)){			
		var ArrParams = [];
		var VarDSName = '';
						
		$.each(ArrResponse, function(){
			var element = {};
			$.each(this.dataprovider, function(){
				if(this.key === 'dataSourceName'){
					VarDSName = (this.value != undefined) ? this.value : '';
				} else if(this.key === 'pointId'){
					element['displayid'] = (this.value != undefined) ? this.value : '';
				} else if(this.key === 'unit'){
					element['unit'] = (this.value != undefined) ? this.value : '';
				} else if(this.key === 'displayName'){
					element['name'] = (this.value != undefined) ? this.value : '';
				} else if(this.key === 'data'){
					element['value'] = (this.value != undefined) ? stripquotes(this.value) : '';
				} else if(this.key === 'deviceTime'){
					element['time'] = (this.value != undefined) ? this.value : '';
				} else if(this.key === 'pointName'){
					element['pointName'] = (this.value != undefined) ? this.value : '';
				}				
			});
			
			ArrParams.push(element);
		});
		
		if(ArrParams.length > 0){
			GblMonitorPoint = VarDSName;
			/*var message = {"body":[{"datasourceName":VarDSName,"messageType":"MESSAGE","parameters":ArrParams}]};
			FnHandleLatestData(message);*/
			
			var grid = $("#gapp-livegrid").data("kendoGrid");
			for(var i=0; i<ArrParams.length; i++){
				var VarParamName = ArrParams[i].name;
				var VarId = VarDSName.replace(/ /g, '_') +"_"+ VarParamName.replace(/ /g, '_');
				if(grid != undefined){
					grid.dataSource.add({
						time : ArrParams[i].time,
						value : ArrParams[i].value,
						unit : ArrParams[i].unit,
						rowid : VarId,
						datatype : ArrParams[i].dataType,
						name: ArrParams[i].name,
						displayid: ArrParams[i].displayid,
						dsname : VarDSName,
						pointName: ArrParams[i].pointName
						
					});
				}
			}
		}
	}
			
}


function FnliveAssetPoint(VarAssetIdentifier,VarDsName){
	GblMonitorPoint = '';
	GblMonitorPointHistory = '';
	var assetslideWidth = 343;
	var grid = $('#gapp-livegrid').data("kendoGrid");
	if(grid != undefined){
		grid.destroy();
		$('#gapp-livegrid').html('');
	}
	
	$("#gapp-assetlive-container").show().animate({opacity: 0.9,left: + assetslideWidth, width:'750px'  },200);
	FnGetAssetPointsLatestInfo(VarAssetIdentifier,VarDsName);
}

function FnliveAssetClose(){
	
	if ($('#assetcountpanel').css('display') == 'block') {
        var height = '-=' + $('#assetcountpanel').height();
		$("#assetcountpanel").slideToggle("fast");
		$("#upper").animate({
			bottom: height
		}, "slow");
    }
	
	var chart = $("#gapp_point_history").data("kendoChart");
	if(chart != undefined){
		chart.destroy();
	}
			
	GblMonitorPointHistory = '';
	GblMonitorPoint = '';
	//$("#gapp-assetlive-close").parent().hide();
	$("#gapp-assetlive-container").hide();
}

function FnPopUpPlotAssetHistory(VarIdentifier, VarAssetName, VarDataSourceName){
	
	//clearPlotHistoryDetails();
	$('#myModal').modal('show');  
	$('#VarIdentifier').val('');
	$('#VarAssetName').val('');
	$('#VarDataSourceName').val('');
	$('#asset-name-history').text('');
	$('#VarIdentifier').val(VarIdentifier);
	$('#VarAssetName').val(VarAssetName);
	$('#VarDataSourceName').val(VarDataSourceName);
	$('#asset-name-history').text('PLOT HISTORY - '+VarAssetName.toUpperCase());
	
	$('#startDate').val('');
	$('#endDate').val('');
	GblStartEnd.start.destroy();
	GblStartEnd.end.destroy();
	GblStartEnd.start = $("#startDate").kendoDatePicker({
		change: startChange,
		format: "dd/MM/yyyy"
	}).data("kendoDatePicker");

	GblStartEnd.end = $("#endDate").kendoDatePicker({
		change: endChange,
		format: "dd/MM/yyyy"
	}).data("kendoDatePicker");
	
	var VarDateToday = FnGetTodaysDate();
	$("#startDate").val(VarDateToday);
	$("#endDate").val(VarDateToday);
	
	GblStartEnd.start.max(VarDateToday);
	GblStartEnd.end.min(GblStartEnd.start.value());
	GblStartEnd.end.max(VarDateToday);
}

function FnPlotAssetHistory(){
	var VarDate1          = $('#startDate').val();
	var VarDate2          = $('#endDate').val();
	var VarIdentifier     = $('#VarIdentifier').val();
	var VarAssetName      = $('#VarAssetName').val();
	var VarDataSourceName = $('#VarDataSourceName').val();
	var VarDate1Check  	  = isValidDate(VarDate1);
	var VarDate2Check  	  = isValidDate(VarDate2);
	
	if(VarDate1=='' || VarDate2==''){
		notificationMsg.show({
			message : 'Please select dates'
		}, 'error');
			
	}else if(!VarDate1Check || !VarDate2Check){
		notificationMsg.show({
			message : 'Invalid Date'
		}, 'error');
		
		if(!VarDate1Check)  $('#startDate').val('');
		if(!VarDate2Check)  $('#endDate').val('');
	}
	else if(VarDataSourceName !=='' && VarDataSourceName!==null && VarDataSourceName!== undefined){			
		
		VarDate1 = VarDate1.split("/");
		var VarDate1Format = VarDate1[1]+"/"+VarDate1[0]+"/"+VarDate1[2];
		//var VarDate1TimeStamp = new Date(VarDate1Format).getTime();
		var VarDate1TimeStamp = FnConvertLocalToUTC(VarDate1Format);
		
		VarDate2 = VarDate2.split("/");
		var VarDate2Format = VarDate2[1]+"/"+VarDate2[0]+"/"+VarDate2[2];
		//var VarDate2TimeStamp = new Date(VarDate2Format).getTime();
		var VarDate2TimeStamp = FnConvertLocalToUTCTime(VarDate2Format);
		
		var date1 = new Date(VarDate1Format);
		var date2 = new Date(VarDate2Format);
		var timeDiff = Math.abs(date2.getTime() - date1.getTime());
		var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
		
		if(date1.getTime() > date2.getTime()){
			$('#startDate').val('');
			
			notificationMsg.show({
				message : 'Invalid Date'
			}, 'error');
		
		}
		else if(diffDays>7){
			notificationMsg.show({
				message : 'Only maximum 7 days can be selected'
			}, 'error');
			
		} else{
			$("#GBL_loading").show();
			var VarUrl = GblAppContextPath+'/ajax' + VarAssetsHistoryUrl;
			var VarMainParam = {
				"sourceId": VarDataSourceName,
				"startDate": VarDate1TimeStamp,
				"endDate": VarDate2TimeStamp,
				"customTags": ["Latitude","Longitude"]
			};
			FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResPlotAssetHistory);
		}
		
	}else{
		notificationMsg.show({
			message : 'Invalid Data Source'
		}, 'error');
	}	
}


	var ArrHistoryMarkersLayer = {};
	var ArrPolylineLayer= {};
	function FnResPlotAssetHistory(response){
		//newly added
		clearPlotHistoryDetails();
		//if(undefined !== response.customTags && response.customTags.length > 0)
		if(undefined !== response.displayNames && response.displayNames.length > 0){	
			$('#assetsmonitoringmapsearch').hide();
			$("#GBL_loading").hide();
			$("#myModal").modal('toggle');
			
			if(response.displayNames[0].values.length != response.displayNames[1].values.length){
				notificationMsg.show({
					message : "Map plotting failed due to data mismatch!"
				}, 'error');
				
			}else{
				
				var ArrResponseData = [];
				$.each(response,function(key,obj) {
					if(key == 'displayNames'){
						$.each(obj,function(key,obj2){
							var checkLatLong;
							$.each(obj2,function(key,obj3){
								if(key == 'displayName' && obj3 == 'Latitude'){ // check if Latitude
									checkLatLong = true;
								}else if(key == 'displayName' && obj3 == 'Longitude'){ // check if Longitude
									checkLatLong = false;
								}

								if(key == 'values'){
									var i = 0;
									$.each(obj3,function(key,obj4){
										if(checkLatLong == true){ //Latitude Values									
											ArrResponseData[i] = [];
											var timeInSec = parseInt(obj4.deviceTime/1000);
											//console.log(timeInSec);
											//console.log('--------');
											
											if(obj4.data !== undefined && !isNaN(obj4.data) && obj4.data != 'NaN') {
												//alert('Latitude = '+ obj4.data);
												ArrResponseData[i]['deviceTime'] = timeInSec;
												ArrResponseData[i]['Latitude'] = obj4.data;
											}
											
										}
										else if(checkLatLong == false){ //Longitude Values
											var timeInSec = parseInt(obj4.deviceTime/1000);
											
											if(obj4.data !== undefined && !isNaN(obj4.data) && obj4.data != 'NaN') {
												//alert('Longitude = '+ obj4.data);
												if(ArrResponseData[i]['deviceTime'] === timeInSec){
													ArrResponseData[i]['Longitude'] = obj4.data;
												}else{
													ArrResponseData[i]['Longitude'] = '';
												}
											}
										}
										i++;
									});
								}
							});
						});
					}		
				});	
		
				//console.log('------------------------------');
				//alert(ArrResponseData);
				
				var LeafIcon = L.Icon.extend({
					options: {
						iconSize:     [12, 12]
						
					}
				});
				var LeafIconStart = L.Icon.extend({
					options: {
						iconSize:     [18, 18]
						
					}
				});
				var LeafIconEnd = L.Icon.extend({
					options: {
						iconSize:     [18, 18]
						
					}
				});
				
				var LeafIconPlay = L.Icon.extend({
					options: {
						iconSize:     [32, 32]
						
					}
				});
				
				var VarStartIcon   = new LeafIconStart({iconUrl: GblAppContextPath+'/plugins/leaflet/marker/map-green-dot.png'});
				var VarEndIcon     = new LeafIconEnd({iconUrl: GblAppContextPath+'/plugins/leaflet/marker/map-red-dot.png'});
				var VarDefaultIcon = new LeafIcon({iconUrl: GblAppContextPath+'/plugins/leaflet/marker/marker-history-default-blue.png'});
				
				var VarPlayIcon    = new LeafIconPlay({iconUrl: GblAppContextPath+'/plugins/leaflet/marker/marker-history-start.png'});
				var VarCnt=0;
				var VarResponseLength = ArrResponseData.length;
				//alert(VarResponseLength);
				var historyMarkers = new L.layerGroup();
				
				var movingMarkers = new L.layerGroup();
				var polylinePoints = [];
				var movingMarkerPoints = [];
				$.each(ArrResponseData,function(key,obj) {
					
					VarCnt++;
					var VarTimeStamp = toDateFormatSeconds(obj.deviceTime, 'F');
					var VarAssetName = $("span.k-in.k-state-selected").text();
					//var VarAssetName = '';
					
					if(obj.Latitude != '' && obj.Longitude !== '' && !isNaN(obj.Latitude) && !isNaN(obj.Longitude)){
						if(VarCnt == 1){
							var marker = new L.marker([obj.Latitude, obj.Longitude], {icon: VarStartIcon}).bindPopup('Start point: '+VarAssetName +' - '+VarTimeStamp+'').openPopup();
							
							
						}else if(VarCnt==VarResponseLength){
							var marker = new L.marker([obj.Latitude, obj.Longitude], {icon: VarEndIcon}).bindPopup('End point: '+VarAssetName +' - '+VarTimeStamp+'').openPopup();
						}
						else{
							var marker = new L.marker([obj.Latitude, obj.Longitude], {icon: VarDefaultIcon}).bindPopup(''+VarTimeStamp+'').openPopup();
						}
									
						marker.on('mouseover', function (e) {
							this.openPopup();
													
						});

						marker.on('mouseout', function (e) {
							
						});
						marker.on('click', function (e) {
							this.openPopup();
							
						});
						marker.addTo(historyMarkers);
						
						
						ArrHistoryMarkersLayer[obj.deviceTime] = marker;
						polylinePoints.push(new L.LatLng(obj.Latitude, obj.Longitude));
						
						movingMarkerPoints.push([obj.Latitude, obj.Longitude]);
					}
				});
				

				map.addLayer(historyMarkers);  // history layer 1
				
				var polylineOptions = {
					color: '#009FD8',
					weight: 5,
					opacity: 1,
					lineJoin: 'round',
					clickable: false
				};
				
				var polylineOptions2 = {
					color: '#f44336',
					weight: 5,
					opacity: 1,
					lineJoin: 'round',
					clickable: false
				};
				
				var polyline = new L.Polyline(polylinePoints, polylineOptions);
			
				var movingPoliline = new L.Polyline(polylinePoints,polylineOptions2);
				map.addLayer(polyline);    		// history layer 2
							
				ArrPolylineLayer['polyline']=polyline;
				ArrPolylineLayer['movingPoliline']=movingPoliline;
				// zoom the map to the polyline
				map.fitBounds(polyline.getBounds());
				
				
				var VarMovingMarker = L.Marker.movingMarker(
				movingMarkerPoints,
				99999, {autostart: false}).addTo(movingMarkers);
				
				ArrHistoryMarkersLayer['moving_marker'] = VarMovingMarker;
				map.addLayer(movingMarkers);    // history layer 3
				
				VarMovingMarker.on('click', function() {
					if (VarMovingMarker.isRunning()) {
						VarMovingMarker.pause();
					} else {
						VarMovingMarker.start();
					}
					//map.removeLayer(historyMarkers);
					map.removeLayer(polyline);

					map.addLayer(movingPoliline);
					movingPoliline.snakeIn();
					map.removeLayer(movingMarkers);
				});
				
				/*
				VarMovingMarker.once('click', function () {
					//VarMovingMarker.start();
					VarMovingMarker.closePopup();
					VarMovingMarker.unbindPopup();
				
					VarMovingMarker.on('click', function() {
						if (VarMovingMarker.isRunning()) {
							VarMovingMarker.pause();
						} else {
							VarMovingMarker.start();
						}
						//map.removeLayer(historyMarkers);
						map.removeLayer(polyline);
						
						map.addLayer(movingPoliline);
						movingPoliline.snakeIn();
						map.removeLayer(movingMarkers);
					});
					
				});*/
				
			}

		}else{
			$("#GBL_loading").hide();		
			notificationMsg.show({
				message : 'No data available'
			}, 'error');	
		}
		
}
	
function FnRemoveMarkers(ArrRes){
	
	for(var i=0; i<ArrRes.length; i++){
		var marker = Arrmarkers[ArrRes[i]['assetName']];
		
		if(marker != undefined){
			map.removeLayer(marker);
			var VarGrpMarkerName = FnGetGrpMarkerName(ArrRes[i]['assetName']);
			var ArrTmp = GblAssetsMarkers[VarGrpMarkerName];
			ArrTmp.splice($.inArray(marker,ArrTmp),1);
			GblAssetsMarkers[VarGrpMarkerName] = ArrTmp;
		}
	} 
}

function FnAssetDetailsPage(VarIdentifier){
	$('#vehicle_id').val(VarIdentifier);
	$('#vehicle_info').submit();
}


function FnGetTodaysDate(){
	var today = new Date();
	//var today = new Date('Thu Mar 31 2016 09:05:20 GMT+0400 (Arabian Standard Time)');
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	
	if(dd<10) {
		dd='0'+dd
	} 
	if(mm<10) {
		mm='0'+mm
	} 
	today = dd+'/'+mm+'/'+yyyy;
	return today;
}

function FnAddDaysToDate(numberOfdaysToAdd){
	var someDate = new Date();
	//var someDate = new Date('Thu Mar 31 2016 09:05:20 GMT+0400 (Arabian Standard Time)');
	//var numberOfDaysToAdd = 1;
	someDate.setDate(someDate.getDate() + parseInt(numberOfdaysToAdd)); 
	
	//Formatting to dd/mm/yyyy :
	var dd = someDate.getDate();
	var mm = someDate.getMonth() + 1;
	var yyyy = someDate.getFullYear();
	
	if(dd<10) {
		dd='0'+dd
	} 
	if(mm<10) {
		mm='0'+mm
	} 
	var someFormattedDate = dd+'/'+mm+'/'+ yyyy;
	return someFormattedDate;
}

function FnSubtractDaysToDate(numberOfdaysToSubtract){
	var someDate = new Date();
	//var someDate = new Date('Thu Mar 31 2016 09:05:20 GMT+0400 (Arabian Standard Time)');
	//var numberOfdaysToSubtract = 1;
	someDate.setDate(someDate.getDate() - parseInt(numberOfdaysToSubtract)); 
	
	//Formatting to dd/mm/yyyy :
	var dd = someDate.getDate();
	var mm = someDate.getMonth() + 1;
	var yyyy = someDate.getFullYear();
	
	if(dd<10) {
		dd='0'+dd
	} 
	if(mm<10) {
		mm='0'+mm
	} 
	var someFormattedDate = dd+'/'+mm+'/'+ yyyy;
	return someFormattedDate;
}

function isValidDate(text){
	//var text = '29/02/2011';
	var comp = text.split('/');
	var d = parseInt(comp[0], 10);
	var m = parseInt(comp[1], 10);
	var y = parseInt(comp[2], 10);
	var date = new Date(y,m-1,d);
	if (date.getFullYear() == y && date.getMonth() + 1 == m && date.getDate() == d) {
		return true;
	} else {
		return false;
	}
}

function clearPlotHistoryDetails() {
	if((Object.keys(ArrHistoryMarkersLayer)).length > 0){
		$.each(ArrHistoryMarkersLayer,function(key7,obj7){
			map.removeLayer(obj7);
		});
		ArrHistoryMarkersLayer = {};
	}
	if((Object.keys(ArrPolylineLayer)).length > 0){
		$.each(ArrPolylineLayer,function(key7,obj7){
			map.removeLayer(obj7);
		});
		ArrPolylineLayer ={};
	}
	//historyMarkers//polyline//movingMarkers
	if($('#assetsmonitoringmapsearch').hide()){
		$('#assetsmonitoringmapsearch').show();
	}
}

function FnGetGeofenceList(){
	var VarUrl = 'ajax/' + VarListGeofenceUrl;
	VarUrl = VarUrl.replace("{domain}",UserInfo.getCurrentDomain());
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResGeofenceList);
}

function FnResGeofenceList(response){
	var ArrResponse = response;
	var VarResLength = ArrResponse.length;
	var ObjResponseData = {};
	if(VarResLength > 0){
		$.each(ArrResponse,function(){
			var element = {};
			$(this.dataprovider).each(function() {
				var key = this.key;
				element[key] = this.value;
			});
			element['id'] = this.identifier.value;
			if(ObjResponseData[element['type']] == undefined){
				ObjResponseData[element['type']] = [];
				ObjResponseData[element['type']].push(element);
			} else {
				ObjResponseData[element['type']].push(element);
			}
			
		});
		
		var VarGeofenceTxt = '';
		$.each(ObjResponseData,function(key,ArrVal){
			VarGeofenceTxt += '<div class="row" style="margin: 20px 0px;"><div class="col-md-1"><label style="font-size: 18px;">'+key+'</label></div>';
			if(ArrVal.length >0){
				VarGeofenceTxt += '<div class="col-md-11">';
				$.each(ArrVal,function(key1,Obj1){
					VarGeofenceTxt += '<label class="control control--checkbox" for="geolist_'+Obj1['id']+'">'+Obj1['geofenceName']+'<input type="checkbox" name="geolist[]" id="geolist_'+Obj1['id']+'" value="'+Obj1['id']+'" onclick="FnGetGeofenceDetails(\''+Obj1['id']+'\',this)"><div class="control__indicator"></div></label>';
				});
				VarGeofenceTxt += '</div>';
			}
			VarGeofenceTxt += '</div>';
		});
		$('#gapp_geofence').html(VarGeofenceTxt);
	}
}

var ObjGeofenceInfo = {};
function FnGetGeofenceDetails(VarGeofenceId,VarThis){
	if($(VarThis).is(':checked') == true){
		if(ObjGeofenceInfo[VarGeofenceId] == undefined){
			var VarUrl = 'ajax/' + VarViewGeofenceUrl;
			var VarParam = {
				"domain" : {"domainName" : UserInfo.getCurrentDomain()},
				"identifier" : {"key": "identifier","value": VarGeofenceId}
			};
			$("#GBL_loading").show();
			FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarParam), 'application/json; charset=utf-8', 'json', FnResGeofenceDetails);
		} else {
			var ObjCoords = ObjGeofenceInfo[VarGeofenceId];
			FnDrawGeofence(ObjCoords);
		}
	} else {
		if(ObjGeofenceLayer[VarGeofenceId] != undefined){
			for(var j=0; j<ObjGeofenceLayer[VarGeofenceId].length; j++){
				map.removeLayer(ObjGeofenceLayer[VarGeofenceId][j]);
			}
			ObjGeofenceLayer[VarGeofenceId] = [];
		}
	}
}

function FnResGeofenceDetails(response){
	$("#GBL_loading").hide();
	var ObjResponse = response.entity;
	
	if(!$.isEmptyObject(ObjResponse)){
		if(ObjResponse.errorCode == undefined){
			var ObjStyles = {"borderColor":"color","borderThickness":"weight","borderAlpha":"opacity","fillColor":"fillColor","fillAlpha":"fillOpacity","width":"width"};
			var VarGeofenceId = ObjResponse['identifier']['value'];
			var VarGeofenceType = ObjResponse['type'];
			var ArrGeofenceFields = ObjResponse['geofenceFields'];
			var ObjCoords = {};
			var element = {};
			$.each(ArrGeofenceFields,function(key,obj){
				if((VarGeofenceType==='Polygon' || VarGeofenceType==='Route') && obj['key']==='coordinates'){
					ObjCoords['coordinates'] = JSON.parse(stripslashes(obj['value']));
				} else if(VarGeofenceType==='Circle' && (obj['key']=='latitude' || obj['key']=='longitude' || obj['key']=='radius')){
					element[obj['key']] = obj['value'];
				} else if(ObjStyles[obj['key']] != undefined){
					ObjCoords[ObjStyles[obj['key']]] = obj['value'];
				}
			});
			
			if(VarGeofenceType==='Circle'){
				ObjCoords['coordinates'] = [];
				ObjCoords['coordinates'].push(element);
			}
			
			ObjCoords['type'] = VarGeofenceType;
			ObjCoords['id'] = VarGeofenceId;
			ObjGeofenceInfo[ObjResponse.identifier.value] = ObjCoords;
			FnDrawGeofence(ObjCoords);
			
		} else {
			FnShowNotificationMessage(ObjResponse);
		}
	}
}

var ObjGeofenceLayer = {};
function FnDrawGeofence(ObjCoords){
	if(ObjCoords['type'] === 'Circle'){
		
		var VarRadious = parseFloat(ObjCoords['coordinates'][0]['radius']);
		var fc = new L.LatLng(parseFloat(ObjCoords['coordinates'][0]['latitude']), parseFloat(ObjCoords['coordinates'][0]['longitude']));
		var ObjCircleOptions = FnGetStyleProperties(ObjCoords);
		var clickCircle = L.circle(fc, (VarRadious * 1000), ObjCircleOptions).addTo(map);
		map.setView(new L.LatLng(parseFloat(ObjCoords['coordinates'][0]['latitude']), parseFloat(ObjCoords['coordinates'][0]['longitude'])), parseInt(map.getZoom()));
		ObjGeofenceLayer[ObjCoords['id']] = [];
		ObjGeofenceLayer[ObjCoords['id']].push(clickCircle);
		
	} else if(ObjCoords['type'] === 'Polygon'){
	
		var polygonPoints = [];
		$.each(ObjCoords['coordinates'],function(key,obj){
			polygonPoints.push(new L.LatLng(obj['latitude'], obj['longitude']));
		});
		var ObjPolygonOptions = FnGetStyleProperties(ObjCoords);
		var polygon = new L.Polygon(polygonPoints,ObjPolygonOptions).addTo(map);
		ObjGeofenceLayer[ObjCoords['id']] = [];
		ObjGeofenceLayer[ObjCoords['id']].push(polygon);
		map.setView(new L.LatLng(parseFloat(ObjCoords['coordinates'][0]['latitude']), parseFloat(ObjCoords['coordinates'][0]['longitude'])), parseInt(map.getZoom()));
		
	} else if(ObjCoords['type'] === 'Route'){
	
		var coords = [];
		$.each(ObjCoords['coordinates'],function(key,obj){
			coords.push(new L.LatLng(obj['latitude'], obj['longitude']));
		});
		
		var ObjStyleOptions = FnGetStyleProperties(ObjCoords);
		var VarOffset = parseInt(ObjCoords['width'] / 2);
		ObjGeofenceLayer[ObjCoords['id']] = [];
		
		var originPolyline = L.polyline(coords,ObjStyleOptions).addTo(map);
		originPolyline.setStyle({color :'#000000'});
		ObjGeofenceLayer[ObjCoords['id']].push(originPolyline);
		
		var leftPolyline = L.polyline(coords, ObjStyleOptions).addTo(map);
		leftPolyline.setStyle({dashArray :'5,10'});
		leftPolyline.setOffset(-VarOffset);
		ObjGeofenceLayer[ObjCoords['id']].push(leftPolyline);
		
		var rightPolyline = L.polyline(coords, ObjStyleOptions).addTo(map);
		rightPolyline.setStyle({dashArray :'5,10'});
		rightPolyline.setOffset(VarOffset);
		ObjGeofenceLayer[ObjCoords['id']].push(rightPolyline);
		map.setView(new L.LatLng(parseFloat(ObjCoords['coordinates'][0]['latitude']), parseFloat(ObjCoords['coordinates'][0]['longitude'])), parseInt(map.getZoom()));
		
	}
}

function FnGetStyleProperties(ObjCoords){
	var ObjProperties = {};
		
	if(ObjCoords['type'] === 'Polygon' || ObjCoords['type'] === 'Circle'){
	
		ObjProperties = {
					stroke : true,
					color : (ObjCoords.color != undefined) ? ObjCoords.color : '#3388ff',
					weight : (ObjCoords.weight != undefined) ? ObjCoords.weight : 3,
					opacity : (ObjCoords.opacity != undefined) ? ObjCoords.opacity : 1,
					fill : true,
					fillColor : (ObjCoords.fillColor != undefined) ? ObjCoords.fillColor : '#3388ff',
					fillOpacity : (ObjCoords.fillOpacity != undefined) ? ObjCoords.fillOpacity : 0.2,
					className : ''
		};
		
	} else {
	
		ObjProperties = {
					stroke : true,
					color : (ObjCoords.color != undefined) ? ObjCoords.color : '#3388ff',
					weight : (ObjCoords.weight != undefined) ? ObjCoords.weight : 3,
					opacity : (ObjCoords.opacity != undefined) ? ObjCoords.opacity : 1,
					fill : false,
					fillColor : (ObjCoords.fillColor != undefined) ? ObjCoords.fillColor : '#3388ff',
					fillOpacity : (ObjCoords.fillOpacity != undefined) ? ObjCoords.fillOpacity : 0.2,
					className : '',
					lineCap : 'round',
					lineJoin : 'round'
		};
	
	}
	
	return ObjProperties;
}

function stripslashes(str) {
    str = str.replace(/\"/g, '"');
    return str;
}

function stripquotes(str) {
	str = str.replace(/"/g,"");
	return str;
}

function FnManagePointHistoryChart(VarVlag){
	if(VarVlag == 1){
		$('#gapp_point_history').hide();
		$('#gapp_point_live').show();
	} else {
		$('#gapp_point_live').hide();
		$('#gapp_point_history').show();
	}
}

