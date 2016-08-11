"use strict";
$(document).ready(function() {
	FnIntiateAccordions();// Accordions initiation

	// hide the vehicle active status
	$('#vehicle-inactive').hide();
	$('#vehicle-active').hide();

	if ((UserInfo.hasPermission('vehicle_details', 'vehicle details'))) {
		
		FnPointInitializeGrid(); // intialize point grid
	}

	FnAlertsChart();
	FnAlertsGrid();

	if ((UserInfo.hasPermission('vehicle_details', 'service schedule'))) {
		FnserviceSchedulingGrid();
	}

	if ((UserInfo.hasPermission('vehicle_details', 'documents'))) {
		FnducmentGrid();
	}

	if ((UserInfo.hasPermission('vehicle_details', 'fuel and mileage'))) {
		mileageList();
		FuelList();
		summaryList();
	}

})

$(window).load(function() {
	
	FnResAssetList(vehicleListresponse);
	FnApplyPermission();
});

$(window).bind("beforeunload", function() {
	if (GblIsSubcribe == true) {
		FnUnSubscribe();
		GblDSInfo ='';
	}
});

function FnApplyPermission(){
	var permissionsMap = UserInfo.getPremissions();
	var vehicleDetailsPerm = permissionsMap.vehicle_details;
	if($.isArray(vehicleDetailsPerm)){
		var found = $.inArray('vehicle details', vehicleDetailsPerm) > -1;
		if(found){
			$("div.vehicleDetailsAct").removeClass('vehicleDetailsAct').addClass('active');
			$("a.vehicleDetailsAct").removeClass('vehicleDetailsAct').addClass('active');
			$("#tab_1").addClass('active');
			
			if(map == null){
				FnInitiateMap();
			}
			
			
		}else{
			var firstItem = vehicleDetailsPerm[0];
			if(firstItem != undefined && firstItem != ''){
				if (firstItem == 'documents') {
					$("div.vehicleDocAct").removeClass('vehicleDocAct').addClass('active');
					$("a.vehicleDocAct").removeClass('vehicleDocAct').addClass('active');
				}
				if (firstItem == 'alert') {
					$("div.vehicleAlertAct").removeClass('vehicleAlertAct').addClass('active');
					$("a.vehicleAlertAct").removeClass('vehicleAlertAct').addClass('active');
				}
				if (firstItem == 'service schedule') {
					$("div.vehicleSSAct").removeClass('vehicleSSAct').addClass('active');
					$("a.vehicleSSAct").removeClass('vehicleSSAct').addClass('active');
				}
				if (firstItem == 'fuel and mileage') {
					$("div.vehicleFMAct").removeClass('vehicleFMAct').addClass('active');
					$("a.vehicleFMAct").removeClass('vehicleFMAct').addClass('active');
				}
			}
		}
	}
	if(vehicleDetailsPerm == undefined){
		$("div.vehicleDetailsAct").removeClass('vehicleDetailsAct').addClass('active');
		$("a.vehicleDetailsAct").removeClass('vehicleDetailsAct').addClass('active');
		$("#tab_1").addClass('active');
		
		if(map == null){
			FnInitiateMap();
		}
	}
}

var GblVehicleInfoObj = {};

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
		
	map = L.map('vehicleGeoMap', {
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

var GblDSArr = [];
var GblDSInfo;
//var GblAssetsList = {};
var GblAssetsInfo = {};
var GblImageList = {};
var GblidentifierId;
function FnResAssetList(response){	
	var ArrResponse = response;
	$("#GBL_loading").hide();
	if($.isArray(ArrResponse)){
		
		var vehicle = {};
		var VarResLength = ArrResponse.length;
		var ArrData = [];
		ArrData.push({'id':payload.tenantId,'text':(payload.tenantId).toUpperCase()});
		if(VarResLength > 0){
			ArrData[0]['items'] = [];
			$.each(ArrResponse,function(){
				 var tmp = 	{'id':this.assetIdentifier.value,'text':(this.assetName).toUpperCase(),'datasourceName':this.datasourceName,'assetName':this.assetName,'template':this.templateName,'templateType':this.assetTemplate};
				 if(this.location != undefined){
					 tmp['latitude'] = parseFloat(this.location.latitude);
					 tmp['longitude'] = parseFloat(this.location.longitude);
				 }
				 ArrData[0]['items'].push(tmp);
				 if(this.assetIdentifier.value == vehicle_id){
					 vehicle = tmp;
					 tmp['selected'] = true;
				 }
			});
		}
		
		$("#gapp-assets-list").kendoTreeView({
			select: function(e){
				//console.log(' GblDSInfo : '+GblDSInfo);
				if(GblIsSubcribe == true){
					FnUnSubscribe();
					GblDSInfo ='';
				}
				
				$.each(Arrmarkers,function(key,obj){
					map.removeLayer(obj);
				});
				
				Arrmarkers = {};
				var tree = $("#gapp-assets-list").getKendoTreeView();
				var dataItem = tree.dataItem(e.node);		
				if(dataItem.template != undefined){
					GblidentifierId = dataItem.id;
					GblVehicleInfoObj = {"id":dataItem.id,"templateType":dataItem.template,'assetName':dataItem.assetName};
					FnMapLocationOnMap(dataItem);
					
					var dsOfSelectedVehicle = dataItem.datasourceName;
					if(dsOfSelectedVehicle != undefined && dsOfSelectedVehicle != '' ){
						GblDSInfo = dsOfSelectedVehicle;
						FnSubscriberDS();
					}
					
					$("#alerts-list").data("kendoGrid").dataSource.data([]);
					$("#vehicle-parameter-grid").data("kendoGrid").dataSource.data([]);
					FnGetVehicleInfo(GblidentifierId);
				}
				
			},
			dataSource: ArrData,
		});
		
		$('#gapp-assets-list #'+payload.tenantId).attr('disabled',true);
		var treeview = $("#gapp-assets-list");
		var kendoTreeView = treeview.data("kendoTreeView")
		
		treeview.on("click", ".k-top.k-bot span.k-in", function(e) {
			kendoTreeView.toggle($(e.target).closest(".k-item"));
		});	
		kendoTreeView.expand(".k-item");
		
		FnInitSearch("#gapp-assets-list", "#treeViewSearchInput");
		
		if (vehicle.id == undefined) {
			vehicle = ArrData[0]['items'][0];
			var treeview = $("#gapp-assets-list").data("kendoTreeView");
			treeview.select($("#gapp-assets-list").find(".k-first").find('li')
					.eq(0));
		}
		
		GblidentifierId = vehicle.id;
		
		GblVehicleInfoObj = {"id":GblidentifierId,"templateType":vehicle.templateName,'assetName':vehicle.assetName};
		
		if((UserInfo.hasPermission('vehicle_details','vehicle details'))){
			FnMapLocationOnMap(vehicle);
		}
		
		
		var VarDSName = (vehicle.datasourceName != undefined) ? vehicle.datasourceName : '';
		if(VarDSName != ''){
			GblDSInfo = VarDSName;
			FnSubscriberDS();
			GblAssetsInfo[VarDSName] = {"name":vehicle.assetName,"id":GblidentifierId,'template':vehicle.template,'templateType':vehicle.templateType};
		}
		
		if((UserInfo.hasPermission('vehicle_details','vehicle details'))){
			FnGetVehicleInfo(GblidentifierId);
		}
	} else {
		if(ArrResponse.errorCode != undefined){
			FnShowNotificationMessage(ArrResponse);
		}
	}
		
}

function FnMapLocationOnMap(obj){
	if(obj.latitude != undefined && obj.longitude != undefined && obj.latitude != '' && obj.longitude != ''){
		
		GblVehicleInfoObj["latitude"]=obj.latitude;
		GblVehicleInfoObj["longitude"]=obj.longitude;
		//marker on map
		FnHighlightMarker();
	}else{
		//get location and plot on map
		FnGetGeoTag(payload.currentDomain, obj.template, GblidentifierId);
	}
}

function FnSubscriberDS(){
	if(GblIsSubcribe == true){
		FnUnSubscribe();
		GblDSInfo ='';
	}
	if (GblDSInfo != undefined && GblDSInfo != '') {
		
		var dsArry = [];
		dsArry.push(GblDSInfo);
		FnGetSubscriptionInfo(dsArry, "mqtt");
	} else {
		var ObjError = {
		"errorCode" : "500",
		"errorMessage" : "Datasource is not available"
		};
		FnShowNotificationMessage(ObjError);
	}
}

function FnSetDataUI(ObjData, isInitialData) {
	if (!$.isEmptyObject(ObjData)) {
		
		var grid = $("#vehicle-parameter-grid").data("kendoGrid");
		if (ObjData.messageType == "MESSAGE") {
			var ArrP = ObjData.parameters;
//			console.log("ArrParameters.length: "+ArrP.length);
			if ($.isArray(ArrP)) {
				var VarDSName = ObjData.datasourceName;
				var element = {};
				var len = ArrP.length;
				for (var i = 0; i < len; i++) {
					if (ArrP[i].name == 'Latitude'
							&& ArrP[i].value != undefined
							&& ArrP[i].value != '') {
						element["latitude"] = ArrP[i].value;

					} else {
						if (ArrP[i].name == 'Longitude'
								&& ArrP[i].value != undefined
								&& ArrP[i].value != '') {
							element["longitude"] = ArrP[i].value;

						} else {
							// map points
							var VarId = VarDSName.replace(/ /g, '_') + "_" + ArrP[i].name.replace(/ /g, '_');
							var isGExist = false;
							var glen = grid.dataSource.data().length;
							if (glen > 0) {
								for (var k = 0; k < glen; k++) {
									var VarChkId = grid.dataSource.data()[k].rowid;
									if (VarId == VarChkId) {
										isGExist = true;
										var item = grid.dataSource.data()[k];
										item.set('value', ArrP[i].value);
										item.set('time', ArrP[i].time);
									}
								}
							}
						}
					}

				}
				if (!$.isEmptyObject(element) && element.latitude != undefined
						&& element.latitude != '' && element.longitude != undefined
						&& element.longitude != '') {
					FnMapLocationOnMap(element)
				}
			}
		}

		
		if (ObjData.messageType == "ALARM") {
			FnSetAlertGridUI(ObjData)
		}
	}
}

// -- helper functions ------------------------------------------------------------------------------------------------------

function FnGetGeoTag(vehicleDomainName, vechicleTemplateName, vehicleIdentifier) {
	var data = {
			"domain" : {
				"domainName" : vehicleDomainName
			},
			"entityTemplate" : {
				"entityTemplateName" : vechicleTemplateName
			},
			"identifier" : {
				"key" : "identifier",
				"value" : vehicleIdentifier
			}
		};

	var VarUrl = '/FMS/ajax/geo-services/1.0.0/geotags/find';
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(data),'application/json; charset=utf-8', 'json',FnResFindGeoTag);
}

function FnResFindGeoTag(response){
	if(response.errorMessage == undefined){
		response = response.entity;
	}else{
		var ObjError = response.errorMessage;
		FnShowNotificationMessage(ObjError);
	}
	
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		GblVehicleInfoObj["latitude"]=ObjResponse.geotag.latitude;
		GblVehicleInfoObj["longitude"]=ObjResponse.geotag.longitude;
		
		FnHighlightMarker();
		//plot on map
	}
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

//for getting asset details
function FnGetVehicleInfo(GblidentifierId) {
	$("#GBL_loading").appendTo('.asset-details-loading').show();
	$("#GBL_loading_2").appendTo('.map-location-loading').show();
	// $("#GBL_loading").show();

	var VarUrl = '/FMS/ajax/galaxy-am/1.0.0/assets/find';
	var data = {
		"domain" : {
			"domainName" : payload.currentDomain
		},
		"platformEntity" : {
			"platformEntityType" : "MARKER"
		},
		"entityTemplate" : {
			"entityTemplateName" : "Asset"
		},
		"identifier" : {
			"key" : "identifier",
			"value" : GblidentifierId
		}
	};

	$('.genset-pic, .genset-name, .div#genset-info').hide();
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(data),
			'application/json; charset=utf-8', 'json', FnResGetVehicleInfo);
}

var GblAssetName;
function FnResGetVehicleInfo(response) {
	if(response.errorMessage == undefined){
		response = response.entity;
	}else{
		var ObjError = response.errorMessage;
		FnShowNotificationMessage(ObjError);
	}
	$('.genset-pic, .genset-name, .div#genset-info').show();
	var ArrResponse = response;
	var VarAssetInfoText = '';
	if (!$.isEmptyObject(ArrResponse)) {
		// var VarAssetInfoText = '';
		$.each(
						ArrResponse.asset.fieldValues,
						function(key, obj) {

							if (obj['key'] == 'assetName') {
								$('#genset-name').text(obj['value']);
								GblAssetName = obj['value'];

							}
							if (obj['key'] == 'assetType') {
								$('#genset-name-label').text(obj['value']);
							}
							if (obj['key'] == 'description') {
								$('#genset-description').text(obj['value']);
							}

							if (obj['key'] == 'assetId') {

								if (obj['value'] != undefined
										&& obj['value'] != '') {
									GblVehicleInfoObj["assetId"]=obj['value'];
									VarAssetInfoText += '<li><span class="genset-properties-name" ><span class="dashboardtextleft-length" title="Asset Id"><strong>Asset Id</strong></span></span><span class="genset-properties-value" ><span class="dashboardtext-length" title="'
											+ obj['value']
											+ '">&nbsp '
											+ obj['value']
											+ ' </span></span></li>';
								}
							}

							if (obj['key'] == 'serialNumber') {
								if (obj['value'] != undefined
										&& obj['value'] != '') {
									VarAssetInfoText += '<li><span class="genset-properties-name" ><span class="dashboardtextleft-length" title="Serial Number"><strong>Serial Number</strong></span></span><span class="genset-properties-value" ><span class="dashboardtext-length" title="'
											+ obj['value']
											+ '">&nbsp '
											+ obj['value']
											+ ' </span></span></li>';
								}

							}

						});

		if (!$.isEmptyObject(ArrResponse.assetType.fieldValues)) {
			$
					.each(
							ArrResponse.assetType.fieldValues,
							function(key, val) {
								if (val.value != '' && val.value != undefined
										&& val.key != 'identifier') {
									VarAssetInfoText += '<li><span class="genset-properties-name" ><span class="dashboardtextleft-length capitalize" title="'
											+ val.key
											+ '"><strong>'
											+ val.key
											+ ' </strong></span></span><span class="genset-properties-value" ><span class="dashboardtext-length" title="'
											+ val.value
											+ '">&nbsp '
											+ val.value
											+ ' </span></span></li>';
								}

							});
		}

	}
	$('#equipmentInfo').html(VarAssetInfoText);
	$("#GBL_loading").appendTo('.asset-details-loading').hide();

	FnGetVehicleImg();
	FnGetPointsOfVehicle(ArrResponse.points);
	
	
//	FnGetLatestAlarm(GblVehicleInfoObj.assetName);
}

function FnGetVehicleImg() {
	var VarUrl = '/FMS/downloadOneFile?domain_name={domain_name}&id={id}';
	VarUrl = VarUrl.replace("{domain_name}", payload.currentDomain);
	VarUrl = VarUrl.replace("{id}", GblVehicleInfoObj.assetId);
	$('.genset-pic, .genset-name, .div#genset-info').hide();
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json', 'json',
			FnResGetVehicleImg);
}

function FnResGetVehicleImg(response) {
	$("#vehicle-image").attr('src',
			'data:image/png;base64,' + response.imgString);
}

function FnGetPointsOfVehicle(pointsResponse) {
	var ArrResponse = pointsResponse;
	if ($.isArray(ArrResponse)) {
		var ArrParams = [];
		var VarDSName = '';
		$.each(
				ArrResponse,function(key, ObjLatestPoint) {
							if (ObjLatestPoint.entityStatus.statusName == 'ALLOCATED') {
								var element = {};
								$.each(
												ObjLatestPoint.dataprovider,
												function(k,
														ObjLatestPointDetails) {

													if (ObjLatestPointDetails.key == 'dataSourceName') {
														VarDSName = ObjLatestPointDetails.value;
													}
													if (ObjLatestPointDetails.key == 'pointName') {
														element['name'] = ObjLatestPointDetails.value;
													}
													if (ObjLatestPointDetails.key == 'data') {
														element['value'] = ObjLatestPointDetails.value;
													}
													if (ObjLatestPointDetails.key == 'deviceTime') {
														element['time'] = ObjLatestPointDetails.value;
													}
													if (ObjLatestPointDetails.key == 'unit') {
														element['unit'] = ObjLatestPointDetails.value;
													}
												});
//								GblAssetsInfo[VarDSName] = {"name" : element['equipName'],"id" : element['equipIdentifier']};
								ArrParams.push(element);
							}
						});
		if(GblDSInfo == undefined || GblDSInfo == ''){
			GblDSInfo = VarDSName;
			FnSubscriberDS();
		}
		if (ArrParams.length > 0) {
		} else {
			$(".form-basic").html("No Data");
		}

		if (ArrParams.length > 0) {
			var grid = $("#vehicle-parameter-grid").data("kendoGrid");
			grid.dataSource.data([]);
			var len = ArrParams.length;
			for (var i = 0; i < len; i++) {
				var VarId = VarDSName.replace(/ /g, '_') + "_" + ArrParams[i].name.replace(/ /g, '_');
				grid.dataSource.add({
					time : ArrParams[i].time,
					value : ArrParams[i].value,
					unit : ArrParams[i].unit,
					rowid : VarId,
					name : ArrParams[i].name
				});
			}
			grid.dataSource.sync();
			
			
		}else{
			$("#alerts-list").data("kendoGrid").dataSource.data([]);
			$("#vehicle-parameter-grid").data("kendoGrid").dataSource.data([]);
		}

	}
}

function FnGetDatasourceName(VarEquipName) {

	var VarDSName = '';
	$.each(GblAssetsInfo, function(dsname, objequip) {
		if (objequip['name'] == VarEquipName) {
			VarDSName = dsname;
		}
	});

	return VarDSName;
}

function FnHighlightMarker(){
	
	if(GblVehicleInfoObj.id != undefined){
		FnApplyAssetMarkers();
	}else{
		var ObjError = {"errorCode" : "500", "errorMessage" : "No location mapped to the asset."};
		FnShowNotificationMessage(ObjError);
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

function FnPointInitializeGrid(pointData) {

	var ArrColumns = [
	// {field: "pointName",title: "Point Name",template:
	// ViewLink },
	{
		field : "name",
		title : "Point Name"
	}, {
		field : "value",
		title : "Value"
	}, {
		field : "time",
		template :"#: FnCheckTimeEmpty(time)#",	
		title : "Time"
	}, {
		field : "unit",
		title : "Unit"
	} ];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : {
			mode : "row"
		},
		"sortable" : true,
		"pageable" : {
			pageSize : 10,
			numeric : true,
			pageSizes : true,
			refresh : false
		},
		"selectable" : true
	};

	var isGExist = false;

	var grid = $("#vehicle-parameter-grid").data("kendoGrid");

	if (grid != null) {
		$("#vehicle-parameter-grid").data("kendoGrid").dataSource
				.data(pointData);
	} else {
		FnDrawGridView('#vehicle-parameter-grid', pointData, ArrColumns,
				ObjGridConfig);
	}
}

function FnCheckTimeEmpty(VarTime){
	if (VarTime =='' || VarTime==null) {
		VarTime=' - ';			
	} else {
		VarTime =toDateFormatRemoveGMT(VarTime, 'F');			
	}
	return VarTime;
}

var Arrmarkers = {};
function FnApplyAssetMarkers() {
	var IsMarkerExist = Arrmarkers[GblidentifierId];
	if(IsMarkerExist != undefined){
	
		var newLatLng = new L.LatLng(GblVehicleInfoObj.latitude, GblVehicleInfoObj.longitude);
		IsMarkerExist.setLatLng(newLatLng);
		
	} else {
		var VarIcon = FnGetMarkerHtmlIcon();
		var marker = L.marker(
				[ GblVehicleInfoObj.latitude, GblVehicleInfoObj.longitude ], {
					icon : VarIcon
				}).addTo(map);
		
		var ObjMarkerPos = marker.getLatLng();
		map.setView(new L.LatLng(parseFloat(ObjMarkerPos.lat),
				parseFloat(ObjMarkerPos.lng)), 5);
		marker.bounce({
			duration : 1000,
			height : 100
		});
		Arrmarkers[GblidentifierId] = marker;
	}
}


function FnDocumentDetails() {

	var ViewLink = "<a class='grid-viewtenant' style='text-transform: capitalize;'>alertName1</a>";
	var ArrColumns = [ {
		field : "docName",
		title : "Doc Name",
		template : ViewLink
	}, {
		field : "docHolderName",
		title : "Holder Name"
	}, {
		field : "activatedDate",
		title : "Activated Date"
	}, {
		field : "expiryDate",
		title : "Expiry Date"
	}, {
		field : "attachments",
		title : "Attachments"
	}

	];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : {
			mode : "row"
		},
		"sortable" : true,
		"height" : 0,
		"pageable" : {
			pageSize : 10,
			numeric : true,
			pageSizes : true,
			refresh : false
		},
		"selectable" : true
	};

	var ArrGridData = [ {
		docName : "Driver List",
		docHolderName : "Sam",
		activatedDate : "01/02/2016 12:34:00",
		expiryDate : "06/06/2016 3:34:00",
		attachments : "PDF"
	}, {
		docName : "Vehicle List",
		docHolderName : "John",
		activatedDate : "01/02/2016 12:34:00",
		expiryDate : "06/06/2016 3:34:00",
		attachments : "PDF, DOC"
	} ];
	FnDrawGridView('#documents-list', ArrGridData, ArrColumns, ObjGridConfig);
}

function FnClickedSlide() {
}

function FnIntiateAccordions() {
	$('.accordionContainer').uberAccordion({
		headerClass : 'title',
		contentClass : 'content',
		orientation : "vertical",// "vertical"
		animationSpeed : 1000,
		slideEvent : 'click' // Open slide event

	});

}

function mileageList() {
	$("#mileage-list").kendoChart({
		title : {
			text : ""
		},
		legend : {
			visible : true,
			position : "bottom"
		},
		chartArea : {
			background : "",
			height : 215
		},
		seriesDefaults : {
			type : "bar"
		},
		series : [ {
			name : "Mileage",
			data : []
		} ],
		valueAxis : {
			max : 600,
			line : {
				visible : false
			},
			minorGridLines : {
				visible : true
			},
			labels : {
				rotation : "auto"
			},
			title : {
				text : "Mileage"
			}
		},
		categoryAxis : {
			categories : [ "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
			majorGridLines : {
				visible : false
			},
			title : {
				text : "Days"
			}
		},
		tooltip : {
			visible : true,
			template : "#= series.name #: #= value #"
		},
        dataBound: function(e) {
            var view = e.sender.dataSource.view();
            $(".overlay").toggle(view.length === 0);
          }
	});
}

function FuelList() {
	$("#fuel-list").kendoChart({
		title : {
			text : ""
		},
		legend : {
			visible : true,
			position : "bottom"
		},
		chartArea : {
			background : "",
			height : 215
		},
		seriesDefaults : {
			type : "column"
		},
		series : [ {
			name : "Mileage",
			data : [],
			color : "#cacc26"
		} ],
		valueAxis : {
			max : 60,
			line : {
				visible : false
			},
			minorGridLines : {
				visible : true
			},
			labels : {
				rotation : "auto"
			},
			title : {
				text : "In Hours"
			}
		},
		categoryAxis : {
			categories : [ "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
			majorGridLines : {
				visible : false
			},
			title : {
				text : "Days"
			}
		},
		tooltip : {
			visible : true,
			template : "#= series.name #: #= value #"
		},
        dataBound: function(e) {
            var view = e.sender.dataSource.view();
            $(".overlay").toggle(view.length === 0);
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
			height:215
        },
        seriesDefaults: {
            type: "donut",
            startAngle: 150
        },
        series: [{
            name: "Summary",
            data: []
        }],
        tooltip: {
            visible: true,
            template: "#= category # (#= series.name #): #= value #%"
        },
        dataBound: function(e) {
            var view = e.sender.dataSource.view();
            $(".overlay").toggle(view.length === 0);
          }
    });
}

function FnIntiateAccordions() {
	$('.accordionContainer').uberAccordion({
		headerClass : 'title',
		contentClass : 'content',
		orientation : "vertical",// "vertical"
		animationSpeed : 1000,
		slideEvent : 'click' // Open slide event

	});

}
