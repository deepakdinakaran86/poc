"use strict";

var IsTenantAdmin=false; //check the which user
var ArrGetAssets=[];

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
			
});


$(window).load(function() {
	ObjUserData =JSON.parse(ObjUserData);
	
	FnGetInitialAssetsList();	
	
	
	
	//FnGetAssetList();	
	//FnGetGeofenceList();
});


function FnGetInitialAssetsList(){	
//alert(JSON.parse(UserCurrentRoleName));

	if(jQuery.inArray("TenantAdmin", JSON.parse(UserCurrentRoleName)) !== -1){			
			console.log(JSON.parse(UserCurrentRoleName));	
		IsTenantAdmin=true;			
			FnGetAssetList();
			//FnGetSubscriptionInfo(ArrGetAssets,'mqtt');
  
	} else {
			
			console.log(JSON.parse(UserCurrentRoleName));
			IsTenantAdmin=false;			
			$.each(ObjUserData.permissions, function( k, HisAssets){
					var HisAssets = HisAssets.substr(HisAssets.indexOf("/") + 1);
					ArrGetAssets.push(HisAssets);		
			});	
			FnGetSubscriptionInfo(ArrGetAssets,'mqtt');
			FnPlotAssets();	
		}

}


$(window).bind("beforeunload", function() { 
	if(GblIsSubcribe == true){
		FnUnSubscribe();
	}
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

var ArrHisAssets=[];
var ArrTemp = '';
function FnGetHisAssetList(ArrAssetLocations){		
		//var ArrHisAssets=[];
		var element= {};
		var ArrData = [];
		var ArrLoc =[];
		var ArrDestinations = [];
		ArrData.push({'id':VarCurrentTenantInfo.tenantId,'text':(VarCurrentTenantInfo.tenantName).toUpperCase()});
		ArrData[0]['items'] = [];
		
		$.each(ObjUserData.permissions, function( k, HisAssets){	
			//var VarDSName = (this.datasourceName != undefined) ? this.datasourceName : '';		
			if(HisAssets != undefined){
				var HisAssets = HisAssets.substr(HisAssets.indexOf("/") + 1);				
				ArrHisAssets.push(HisAssets);				
			}			
			
			//ArrData[0]['items'].push({'datasourceName':HisAssets,"text":HisAssets,"template":"Asset","templateType":"MOBILE"});
			if(ArrAssetLocations[HisAssets] != undefined && ArrAssetLocations[HisAssets] != ''){
				ArrData[0]['items'].push(ArrAssetLocations[HisAssets]);
				var temp = ArrAssetLocations[HisAssets];
				if(temp.latitude != undefined && temp.longitude != undefined){
					ArrLoc.push(temp);
				}
				
			}
			
		});	

			$("#treeview").kendoTreeView({			
				select: function(e){
					clearPlotHistoryDetails();					
		
					var tree = $("#treeview").getKendoTreeView();
					var dataItem = tree.dataItem(e.node);					
					if(dataItem.template != undefined){	
					
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
					
						var ObjAssetParam = {};
						ObjAssetParam['datasourceName'] = dataItem.datasourceName;
						ObjAssetParam['assetName'] = dataItem.assetName;					
						ObjAssetParam['template'] = dataItem.template;					
						ObjAssetParam['templateType'] = dataItem.templateType;					
						ObjAssetParam['latitude'] = dataItem.latitude;
						ObjAssetParam['longitude'] = dataItem.longitude;					
						if(ObjAssetParam.latitude != undefined && ObjAssetParam.longitude != undefined){
							FnHighlightMarker(ObjAssetParam); /// 
						}
						else{
							//Fix Me
							notificationMsg.show({
								message : "Requested location data is not available"
							}, 'info');
						}

					}
				},			
				dataSource: ArrData
			});
			
			$('#treeview #'+VarCurrentTenantInfo.tenantId).attr('disabled',true);			
			var treeview = $("#treeview");			
			var kendoTreeView = treeview.data("kendoTreeView")
			
			treeview.on("click", ".k-top.k-bot span.k-in", function(e) {
				kendoTreeView.toggle($(e.target).closest(".k-item"));
			});
			kendoTreeView.expand(".k-item");
			//console.log('##');			
			//console.log(JSON.stringify(ArrLoc));
			$("#gapp-assetcount").text(ArrLoc.length);
			FnApplyAssetMarkers(ArrLoc,0); //cretae marker 1:open popup           /0
			
			if(ArrLoc.length > 0){
				$("#gapp-assetcount").text(ArrLoc.length);
					//FnGetSubscriptionInfo(ArrLoc);
				}
		
			FnInitSearch("#treeview", "#treeSearchInput");
		
		}


function FnPlotAssets(){	
		var element= {};
		var ArrData = [];
		ArrData.push({'id':VarCurrentTenantInfo.tenantId,'text':(VarCurrentTenantInfo.tenantName).toUpperCase()});
		ArrData[0]['items'] = [];
		
		$.each(ObjUserData.permissions, function( k, HisAssets){	
			var VarDSName = (this.datasourceName != undefined) ? this.datasourceName : '';		
			var HisAssets = HisAssets.substr(HisAssets.indexOf("/") + 1);
			ArrHisAssets.push(HisAssets);
			ArrData[0]['items'].push({'datasourceName':HisAssets,"text":HisAssets,"template":"Asset","templateType":"MOBILE"});
		});	
		
	var VarUrl = GblAppContextPath+'/ajax' + VarPlotLatestDataUrl;	
	var ArrData = [];
	$.each(ArrHisAssets, function( k,m){		
		ArrData.push({'sourceId':m,'points': ['Longitude','Latitude']});
		});	
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(ArrData), 'application/json; charset=utf-8', 'json', FnResPlotAssets);
}


function FnResPlotAssets(response){
	var ArrResponse = response;
	//console.log(JSON.stringify(ArrResponse.length));
	var VarDSName =[];
	var ArrAssetLocations = [];
	var element={};
	
	if($.isArray(ArrResponse)){
		//var VarResLength = ArrResponse.length; console.info(VarResLength);		
		var elementArra = [];
		var eee = {};
		
		$.each(ArrResponse,function(kP,propertyP){
		var element={};
		element.datasourceName = propertyP.sourceId;
		element.assetName = propertyP.sourceId;
		element.text = propertyP.sourceId;
		element.template = "Asset";
		element.templateType = "MOBILE";
		
			$.each(propertyP.data,function(k,property){
				var dName = property.displayName;
				element[dName.toLowerCase()] = parseFloat(property.data);
		
				
			});	
		eee[element.datasourceName] = element;
		ArrAssetLocations.push(element);
		
		});		

			FnGetHisAssetList(eee);
				
	} else {
		if(ArrResponse.errorCode != undefined){
			FnShowNotificationMessage(ArrResponse);
		}
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
	
	map = L.map('assetsmonitoringmap', {
		zoom: 5,
		center: [40.9743827,-97.6000859], // Dubai LatLng 25.20, 55.27
		layers: [hybrid,streets],
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
 
function FnGetAssetList(){
	var VarUrl = GblAppContextPath+'/ajax' + VarListAssetsUrl;
	VarUrl = VarUrl + "?domain_name="+ VarCurrentTenantInfo.tenantDomain + "&mode=Asset";
	$("#GBL_loading").show();
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResAssetList);
}

var GblAssetsList = {};
var GblAssetsInfo = {};
var GblImageList = {};
function FnResAssetList(response){	
	var ArrResponse = response;
	$("#GBL_loading").hide();
	if($.isArray(ArrResponse)){
		
		var VarResLength = ArrResponse.length;
		var ArrData = [];
		ArrData.push({'id':VarCurrentTenantInfo.tenantId,'text':(VarCurrentTenantInfo.tenantName).toUpperCase()});
		var ArrAssetLocations = [];
		var ArrDestinations = [];
		if(VarResLength > 0){
			ArrData[0]['items'] = [];
			$.each(ArrResponse,function(){
				var VarDSName = (this.datasourceName != undefined) ? this.datasourceName : '';
				ArrData[0]['items'].push({'id':this.assetIdentifier.value,'text':(this.assetName).toUpperCase(),'datasourceName':VarDSName,'assetName':this.assetName,'template':this.templateName,'templateType':this.assetTemplate});
				
				if(this.latitude != undefined && this.longitude != undefined){
					ArrAssetLocations.push({'latitude':parseFloat(this.latitude),'longitude':parseFloat(this.longitude),'datasourceName':VarDSName,'assetName':this.assetName,'template':this.templateName,'templateType':this.assetTemplate});
				}
				
			//	[{'latitude':parseFloat(this.latitude),'longitude':parseFloat(this.longitude),'datasourceName':VarDSName,'assetName':this.assetName,'template':this.templateName,'templateType':this.assetTemplate}]
				
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
			
			GblImageList = FnGetImageList();
			
		}
		
		$("#treeview").kendoTreeView({
			select: function(e){
				clearPlotHistoryDetails();
				
				var tree = $("#treeview").getKendoTreeView();
				var dataItem = tree.dataItem(e.node);	
				console.log(JSON.stringify(dataItem));			

				//{"id":"361879fb-d002-4cf7-916e-97e95c8bbd2a","text":"F690F419-B21B-4078-B41A-0AEE5AFDB91D","datasourceName":"f690f419-b21b-4078-b41a-0aee5afdb91d","assetName":"f690f419-b21b-4078-b41a-0aee5afdb91d","template":"Asset","templateType":"MOBILE","index":4}	
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
				
					var ObjAssetParam = {};
					ObjAssetParam['datasourceName'] = dataItem.datasourceName;
					ObjAssetParam['template'] = dataItem.template;
					ObjAssetParam['assetName'] = dataItem.assetName;
					FnHighlightMarker(ObjAssetParam);
				}
				
			},
			dataSource: ArrData,
		});
		
		$('#treeview #'+VarCurrentTenantInfo.tenantId).attr('disabled',true);
		var treeview = $("#treeview");
		var kendoTreeView = treeview.data("kendoTreeView")
		
		treeview.on("click", ".k-top.k-bot span.k-in", function(e) {
			kendoTreeView.toggle($(e.target).closest(".k-item"));
		});	
		kendoTreeView.expand(".k-item");
		
		FnApplyAssetMarkers(ArrAssetLocations,0); 
				
		if(ArrDestinations.length > 0){
		$("#gapp-assetcount").text(ArrDestinations.length);
			//FnGetSubscriptionInfo(ArrDestinations);
			FnGetSubscriptionInfo(ArrDestinations,'mqtt');
		}
		
		FnInitSearch("#treeview", "#treeSearchInput");
		
	} else {
		if(ArrResponse.errorCode != undefined){
			FnShowNotificationMessage(ArrResponse);
		}
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

function FnGetImageList(){
	var ObjImages = {};
	$.ajax({
		type: 'GET',
		cache: true,
		async: false,
		contentType: 'application/json; charset=utf-8',
		url: GblAppContextPath+'/getimage/all/'+VarCurrentTenantInfo.tenantId,
		dataType: 'json',
		success:function(response){
			var ObjResponse = response;
			ObjImages = ObjResponse;		
		},
		error:function(xhr, status, error){
			console.log(xhr);
		}
	});
	
	return ObjImages;
}



function FnSetDataUI(ObjData){
	
		//if(ObjData.messageType!='MESSAGE'){ return; }
		
		if(!$.isEmptyObject(ObjData)){
			var grid = $("#gapp-livegrid").data("kendoGrid");
			var pointchart = $("#gapp_point_history").data("kendoChart");
			
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
						//alert(VarParamName);
						if(VarParamName !== undefined && VarParamName !==''){
							var VarId = VarDataSourceName.replace(/ /g, '_') +"_"+ VarParamName.replace(/ /g, '_');
						}
						//console.log(VarId);
						
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
						
							if (!isGExist) {
								var abc = {
										time : ArrParameters[i].time,
										value : ArrParameters[i].value,
										unit : ArrParameters[i].unit,
										rowid : VarId,
										datatype : ArrParameters[i].dataType,
										name: ArrParameters[i].name,
										displayid: ArrParameters[i].displayid,
										dsname : VarDataSourceName,
										pointName: ArrParameters[i].pointName
										
								};
								
								grid.dataSource.add(abc);
							}
							grid.dataSource.sync();
						}
						
						if(GblMonitorPointHistory != ''){
							var ArrHis = GblMonitorPointHistory.split('@');
							if(ArrHis[1] === VarDataSourceName){
								if(pointchart != undefined && ArrHis[0] === VarParamName){								
									pointchart.dataSource.add({"deviceTime":ArrParameters[i].time,"data":ArrParameters[i].value});
									//pointchart.dataSource.sync();
								}
							}
						}
						
					}
					
				}
				
			}
				
			if(!$.isEmptyObject(element) && element['latitude'] !='' && element['longitude'] != ''){
				var ArrAssetLocations = [];
				element['datasourceName'] = VarDataSourceName;
				//element['assetName'] = GblAssetsInfo[VarDataSourceName]['name'];
				element['assetName'] = VarDataSourceName;
				//element['template'] = GblAssetsInfo[VarDataSourceName]['template'];
				element['template'] = VarDataSourceName.template;
				//element['templateType'] = GblAssetsInfo[VarDataSourceName]['templateType'];
				element['templateType'] = VarDataSourceName.templateType;
				element['time'] = VarTimestamp;
				ArrAssetLocations.push(element);
				//FnApplyAssetMarkers(ArrAssetLocations,0);
				element = {};
			}
				
		}
	
}


function FnHighlightMarker(ObjAssetParam){
	//console.log('ObjAssetParam 2');		//{"assetName":"1462e6fe-8292-48ea-b477-c0e2b0676bb9","template":"Asset","templateType":"MOBILE","latitude":24.9392502,"longitude":55.0520526}
	
	//{"datasourceName":"f8ae8668-dbcd-4110-925b-7db9d6732ddf","template":"Asset","assetName":"f8ae8668-dbcd-4110-925b-7db9d6732ddf"}
	//console.log(JSON.stringify(ObjAssetParam));	
	var marker = Arrmarkers[ObjAssetParam['assetName']];
	//var marker = ObjAssetParam['assetName'];
	//alert(ObjAssetParam.latitude);

	
	if(marker != undefined){
	//	alert('!undefined');		
		marker.openPopup();
		var VarZoom = parseInt(map.getZoom());
		var ObjMarkerPos = marker.getLatLng();
		//alert(ObjMarkerPos);
		map.setView(new L.LatLng(parseFloat(ObjMarkerPos.lat), parseFloat(ObjMarkerPos.lng)),VarZoom);
		marker.bounce({duration: 1000, height: 100});
		
	} else {
	
	//alert('!undefined 2 '+ObjAssetParam.datasourceName);
		if(ObjAssetParam.datasourceName != undefined && ObjAssetParam.datasourceName != ''){
			var VarParam = [];
			VarParam.push(ObjAssetParam.datasourceName);
			$("#GBL_loading").show();
			
			//	if(!$.isEmptyObject(ObjAssetParam)){
			/*		var ArrAssetLocations = [];
					$.each(ObjAssetParam,function(){
						var element = {};
								//alert('in0');
									element['datasourceName'] = ObjAssetParam.datasourceName;
									element['assetName'] = ObjAssetParam.assetName;
									element['template'] = ObjAssetParam.template;
									element['templateType'] = ObjAssetParam.templateType;
									element['latitude'] = ObjAssetParam.latitude;
									element['longitude'] = ObjAssetParam.longitude;
									ArrAssetLocations.push(element);
						
					});
				//}			
					*/
				/*	if(ArrAssetLocations.length > 0){
						//alert('in1');
								FnApplyAssetMarkers(ArrAssetLocations,1);
							} else {
								var ObjError = {"errorCode" : "500", "errorMessage" : "No location mapped to the asset."};
							}				
			*/
			
			$.ajax({
					type:'POST',
					cache: true,
					async: true,
					contentType: 'application/json; charset=utf-8',
					url: GblAppContextPath +"/ajax" + VarSearchDeviceUrl,
					//var VarSearchDeviceUrl = '<%=APICONF.GAPP_SERVICES.assets.searchdevice%>';
					data: JSON.stringify(VarParam),
					dataType: 'json',
					success: function(result) {
						$("#GBL_loading").hide();
						var ArrRes = result;
						if($.isArray(ArrRes) && ArrRes.length > 0){
							var ArrAssetLocations = [];
							$.each(ArrRes,function(){
								var element = {};
								$.each(this.dataprovider,function(key,obj){
									if((obj['key'] == 'latitude' || obj['key'] == 'longitude') && obj['value'] != undefined && obj['value'] != ''){
										element[obj['key']] = parseFloat(obj['value']);
									}
								});
								
								if(!$.isEmptyObject(element)){
									element['datasourceName'] = ObjAssetParam.datasourceName;
									element['assetName'] = GblAssetsInfo[ObjAssetParam.datasourceName]['name'];
									element['template'] = GblAssetsInfo[ObjAssetParam.datasourceName]['template'];
									element['templateType'] = GblAssetsInfo[ObjAssetParam.datasourceName]['templateType'];
									ArrAssetLocations.push(element);
								}
							});
							
							if(ArrAssetLocations.length > 0){
								FnApplyAssetMarkers(ArrAssetLocations,1);
							} else {
								var ObjError = {"errorCode" : "500", "errorMessage" : "No location mapped to the asset."};
								FnShowNotificationMessage(ObjError);
							}
							
						}
					},
					error : function(xhr, status, error) {
						$("#GBL_loading").hide();
						console.log('Error');
					}
				});
				
			
		} else {		
			var ObjError = {"errorCode" : "500", "errorMessage" : "No location mapped to the asset."};
			FnShowNotificationMessage(ObjError);
		}
	}
}

var Arrmarkers = {};
var GblAssetsMarkers = {};
function FnApplyAssetMarkers(ArrRes,VarFlag){
	//alert(''+VarFlag);
	//console.log(JSON.stringify(ArrRes));
	if(ArrRes.length > 0){	
		if(!$.isEmptyObject(Arrmarkers) && GblIsSubcribe==true){
			FnRemoveMarkers(ArrRes);
		}
								
		$.each(ArrRes,function(){			
			var IsMarkerExist = Arrmarkers[this.assetName];
			//var IsMarkerExist = this.assetName;
			//alert(IsMarkerExist);
			if(IsMarkerExist != undefined){
			
			//	alert(this.latitude +'  /  ' +this.longitude);
				if(this.latitude != undefined && this.longitude != undefined){
					var newLatLng = new L.LatLng(this.latitude, this.longitude);
					IsMarkerExist.setLatLng(newLatLng);
				}else{
					//alert(this.latitude +'  /  ' +this.longitude);
					var ObjError = {"errorCode" : "500", "errorMessage" : "No location mapped to the asset."};
					FnShowNotificationMessage(ObjError);
				}
			
				
			} else {
			
				var VarIcon = FnGetMarkerHtmlIcon(this.templateType);
				var marker = L.marker([this.latitude, this.longitude], {icon: VarIcon}).bindPopup(FnConstructMapContent(this)).addTo(map);
			
				var VarMarkerAssetName = this.assetName;
				marker.on('click', function() {
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
		
		//FnCreateLayerGroup();
		//FnConstructCountDetails();
	}
	
}

function FnGetMarkerHtmlIcon(VarAssetTemplate){

	var VarAssetTypeImageSrc = '';
	if($.inArray(VarAssetTemplate+".png",GblImageList['assetType']) != -1){
		var VarAssetTypeImage = GblAppContextPath + VarAppImagePath + "/" + VarCurrentTenantInfo.tenantDomain + VarAppAssetTypeImagePath + VarAppMarkerImagePath + "/" + VarAssetTemplate+".png";
		VarAssetTypeImageSrc = '<img src='+VarAssetTypeImage+' style="height:23px" />';
	} else {
		var VarAssetTypeImage = GblAppContextPath + VarAppImagePath + VarAppDefaultImagePath + VarAppAssetTypeImagePath + VarAppMarkerImagePath + "/" + "noimage.png";
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

function FnGetMarkerImageIcon(Latitude, Longitude){

	var LeafIcon = L.Icon.extend({
		options: {
			iconSize:     [46, 70],
			iconAnchor:   [23, 70], // Latitude, Longitude
			shadowAnchor: [4, 62],
			popupAnchor:  [0, -62]
		}
	});
	
	var VarIcon = new LeafIcon({iconUrl: GblAppContextPath+'/plugins/leaflet/marker/common.png'});

	return VarIcon;
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
			if($.inArray(templatename+".png",GblImageList['assetType']) != -1){
				var VarAssetTypeImage = GblAppContextPath + VarAppImagePath + "/" + VarCurrentTenantInfo.tenantDomain + VarAppAssetTypeImagePath + VarAppMarkerImagePath + "/" + templatename+".png";
				VarAssetTypeImageSrc = '<img src='+VarAssetTypeImage+' style="height:21px" />';
			} else {
				var VarAssetTypeImage = GblAppContextPath + VarAppImagePath + VarAppDefaultImagePath + VarAppAssetTypeImagePath + VarAppMarkerImagePath + "/" + "noimage.png";
				VarAssetTypeImageSrc = '<img src='+VarAssetTypeImage+' style="height:23px" />';
			}
		
			var VarAssetCount = ArrMarkers.length;
			
			VarCountDetailsTxt += '<a href="Javascript:void(0)"><div class="pincount">'+VarAssetTypeImageSrc+'</div><section style="float:right;text-align: left;margin: 6px 0px 10px 10px;"><div><label style="font-size: 15px; margin-right: 5px;">'+templatename+'</label><label class="control control--checkbox"><input type="checkbox" name="assetTypes[]" value="'+templatename+'" onclick="FnSearchAssetTypeMarkers()"/><div class="control__indicator"></div></label></div><div style="color: #37DCBF;font-size: 15px;">'+VarAssetCount+'</div></section></a>';
			
		});
		VarCountDetailsTxt += '</div></div>';
	} else {
		VarCountDetailsTxt += 'No Records to display';
	}
	
	$('#assettype_details_list').html(VarCountDetailsTxt);
}

function FnSearchAssetTypeMarkers(){
	$('.pin').removeClass('highlight');
	$('#assettype_details_list input[name="assetTypes[]"]').each(function() {
		if($(this).is(':checked') == true){
			var VarAssetType = $(this).val();
			$('.pin.'+VarAssetType).addClass('highlight');
			
		}
	});	
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

//****************************************************************/

//****************************************************************/


function FnConstructMapContent(ObjResponse){
	//console.log(1);
	//console.log(JSON.stringify(ObjResponse));
	//{"datasourceName":"076b2b68-8996-4c92-8c94-c2cd975bf6a3","assetName":"076b2b68-8996-4c92-8c94-c2cd975bf6a3","template":"Asset","templateType":"MOBILE"}
	//var VarAssetIdentifier = GblAssetsInfo[ObjResponse.datasourceName]['id'];
	var VarAssetIdentifier=ObjResponse.datasourceName;
	
	/*if(GblAssetsInfo[ObjResponse.datasourceName]['template'] == 'Asset'){
		var Action = GblAppContextPath+'/dashboard';
	} else {
		var Action = GblAppContextPath+'/dashboard';
	}	*/
	
	var Action = GblAppContextPath+'/dashboard';	
	var VarAssetImageSrc = '';
	if($.inArray(ObjResponse.assetName+".png",GblImageList['asset']) != -1){
		var VarAssetImage = GblAppContextPath + VarAppImagePath + "/" + VarCurrentTenantInfo.tenantDomain + VarAppAssetImagePath + "/" + ObjResponse.assetName+".png";
		VarAssetImageSrc = '<img src='+VarAssetImage+' width="89" height="100" />';
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
	
	<!--	VarTxt += "<aside class='brand-default' style='margin-bottom:10px;'><strong>Timestamp: </strong> "+ObjResponse.time+"</aside>";
	-->
	VarTxt += "<button class='btn btn-xs green' onclick='FnAssetDetailsPage(\""+VarAssetIdentifier+"\",\""+Action+"\")'> Asset details <i class='fa fa-edit'></i></button>";
	
	VarTxt += "<div style='position:absolute;bottom:25px;right:10px;'>";
	
		VarTxt += "<span class='map-assetlive-btn'><button  class='btn btn-xs yellow' id='map-assetlive' onclick='FnliveAssetPoint(\""+ObjResponse.datasourceName+"\")' data-toggle='tooltip' title='Live Points'><i class='fa fa-edit' aria-hidden='true'></i></button></span>";
	
	
	
		VarTxt += "<span class='map-history-btn'><button  class='btn btn-xs yellow map-history' id='map-history'  onclick='FnPopUpPlotAssetHistory(\""+VarAssetIdentifier+"\",\""+ObjResponse.assetName+"\",\""+ObjResponse.datasourceName+"\")' data-toggle='tooltip' title='History'><i class='fa fa-history' aria-hidden='true'></i></button></span>";
	
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
	kendo.ui.progress($("#gapp_point_history"), true);
	var ObjParam = {
		"sourceId" : ObjSelecetdPoint.dsname,
		"startDate" : FnConvertLocalToUTC(FnAssetDateProcess('current')),
		"endDate" : FnConvertLocalToUTC(FnAssetDateProcess('next',1)),
		"customTags" : [ObjSelecetdPoint.name]
	};			
	GblSelPointHistory = ObjSelecetdPoint.name;
	var VarUrl = GblAppContextPath + '/ajax' + VarGetDeviceDataUrl;			
	FnMakeAsyncAjaxRequest(VarUrl,'POST', JSON.stringify(ObjParam), 'application/json; charset=utf-8', 'json', FnResInitHistroyData);
}

function FnResInitHistroyData(response){
	kendo.ui.progress($("#gapp_point_history"), false);
	
	var ObjResponse = response;
	
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
		transitions: false,
		tooltip: {
			visible: true,
			format: "{0}%",					
			template: "  #= toDateFormatRemoveGMT(category,':')# || #= value #"
		}

	});

}

function FnManageAssetInfo(){
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
function FnGetAssetPointsLatestInfo(VarDsName){	
	FnInitAssetPointsGrid();
	kendo.ui.progress($("#gapp-livegrid"), true);
	var VarUrl = GblAppContextPath + '/ajax' + VarLivePointLatestUrl;	
	
	var data={
		  "searchFields": [
			{
			  "key": "assetName",
			  "value": VarDsName
			}
		  ],
		  "returnFields": [
			"identifier"
		  ],
		  "domain": {
			"domainName": VarCurrentTenantInfo.tenantDomain
		  },
		  "entityTemplate": {
			"entityTemplateName": "Asset"
		  }
		};

	FnMakeAsyncAjaxRequest(VarUrl,'POST',JSON.stringify(data),'application/json; charset=utf-8','json', FnResAssetPointsLatestInfo);
}

function FnResAssetPointsLatestInfo(response) {
	kendo.ui.progress($("#gapp-livegrid"), false);
	//console.log('rr');
	//console.log(JSON.stringify(response));
	if($.isArray(response)){
		var VarDSName =response[0].identifier.value;
		VarDSName = (VarDSName != undefined) ? VarDSName  : '';
		
		FnGetLivePointsLatestData(VarDSName);
		
		/*if(ArrParams.length > 0){		
			var message = {"body":[{"datasourceName":VarDSName,"messageType":"MESSAGE","parameters":ArrParams}]};
			GblMonitorPoint = VarDSName;
			FnHandleMessage(message,0);		
		}*/
	}
				
}


function FnGetLivePointsLatestData(getVarDSName){
	//alert('VarDSName: '+getVarDSName);
	var VarUrl = GblAppContextPath + '/ajax' + VarGetAssetLatestUrl;
	var data={
		  "domain": {
			"domainName": VarCurrentTenantInfo.tenantDomain
		  },
		  "platformEntity": {
			"platformEntityType": "MARKER"
		  },
		  "entityTemplate": {
			"entityTemplateName": "Asset"
		  },
		  "identifier": {
			"key": "identifier",
			"value": getVarDSName
		  }
		};	
	FnMakeAsyncAjaxRequest(VarUrl,'POST',JSON.stringify(data),'application/json; charset=utf-8','json', FnResGetLivePointsLatestData);	
}

function FnResGetLivePointsLatestData(response){
	
	var ArrResponse = response;
	kendo.ui.progress($("#gapp-livegrid"), false);
	if($.isArray(response)){
		var ArrParams = [];
		var VarDSName = '';
			$.each(response, function(){
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
					element['value'] = (this.value != undefined) ? this.value : '';
				} else if(this.key === 'deviceTime'){
					element['time'] = (this.value != undefined) ? this.value : '';
				} else if(this.key === 'pointName'){
					element['pointName'] = (this.value != undefined) ? this.value : '';
				}				
			});
			
			ArrParams.push(element);
		});
		
		if(ArrParams.length > 0){
		
			var message = {"body":[{"datasourceName":VarDSName,"messageType":"MESSAGE","parameters":ArrParams}]};
			
			GblMonitorPoint = VarDSName;
			//FnHandleMessage(message,0);	
			FnHandleLatestData(message);
			
			
		}
		
		
	}else{
		console.log('not an array');
	}
	
}

function FnliveAssetPoint(VarDsName){
	
	console.log( 'FnliveAssetPoint '+VarDsName);
	GblMonitorPoint = '';
	GblMonitorPointHistory = '';
	var assetslideWidth = 343;
	var grid = $('#gapp-livegrid').data("kendoGrid");
	if(grid != undefined){
		grid.destroy();
		$('#gapp-livegrid').html('');
	}
	
	$("#gapp-assetlive-container").show().animate({opacity: 0.9,left: + assetslideWidth, width:'750px'  },200);
	FnGetAssetPointsLatestInfo(VarDsName);
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
		
			
		
		//{"errorCode":"500","errorMessage":"Requested data is not available"}
		if(undefined !== response.customTags && response.customTags.length > 0){
			$('#assetsmonitoringmapsearch').hide();
			$("#GBL_loading").hide();
			$("#myModal").modal('toggle');
			var ArrResponseData = [];
			$.each(response,function(key,obj) {
				if(key == 'customTags'){
					$.each(obj,function(key,obj2){
						var checkLatLong;
						$.each(obj2,function(key,obj3){
							if(key == 'customTag' && obj3 == 'Latitude'){ // check if Latitude
								checkLatLong = true;
							}else if(key == 'customTag' && obj3 == 'Longitude'){ // check if Longitude
								checkLatLong = false;
							}

							if(key == 'values'){
								var i = 0;
								$.each(obj3,function(key,obj4){
									if(checkLatLong == true){ //Latitude Values									
										ArrResponseData[i] = [];
										ArrResponseData[i]['deviceTime'] = obj4.deviceTime;
										ArrResponseData[i]['Latitude'] = obj4.data;
									}
									else if(checkLatLong == false){ //Longitude Values
										if(ArrResponseData[i]['deviceTime'] === obj4.deviceTime){
											ArrResponseData[i]['Longitude'] = obj4.data;
										}else{
											ArrResponseData[i]['Longitude'] = '';
										}
									}
									i++;
								});
							}
						});
					});
				}		
			});	
			
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

			var historyMarkers = new L.layerGroup();
			
			var movingMarkers = new L.layerGroup();
			var polylinePoints = [];
			var movingMarkerPoints = [];
			$.each(ArrResponseData,function(key,obj) {
				
				VarCnt++;
				var VarTimeStamp = toDateFormat(obj.deviceTime, 'F');
				var VarAssetName = $("span.k-in.k-state-selected").text();
				//var VarAssetName = '';
				
				if(obj.Latitude != '' && obj.Longitude !== ''){
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
				
			});
			
		}else{
			$("#GBL_loading").hide();		
			notificationMsg.show({
				message : response.errorMessage
			}, 'error');	
		}
		
	/*	if(response.errorCode=="500"){
					notificationMsg.show({
					message : response.errorMessage
					}, 'error');
				
			}
		*/
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

function FnAssetDetailsPage(VarIdentifier,VarAction){
	$('#dashboard_equip_id').val(VarIdentifier);
	$('#gapp-genset-dashboard').attr('action',VarAction);
	$('#gapp-genset-dashboard').submit();
}

/*-------------------------------------------------------*/
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
 
/*-------------------------------------------------------*/

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
	var VarUrl = GblAppContextPath+'/ajax' + VarListGeofenceUrl;
	VarUrl = VarUrl.replace("{domain}",VarCurrentTenantInfo.tenantDomain);
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResGeofenceList);
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
			var VarUrl = GblAppContextPath+'/ajax' + VarViewGeofenceUrl;
			var VarParam = {
				"domain" : {"domainName" : VarCurrentTenantInfo.tenantDomain},
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
	var ObjResponse = response;
	
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
    //str = str.replace(/\\'/g, '\'');
    str = str.replace(/\"/g, '"');
    //str = str.replace(/\\0/g, '\0');
    //str = str.replace(/\\\\/g, '\\');
    return str;
}


function FnAssetDetailsPage(VarIdentifier,VarAction){	
	//alert(VarIdentifier);
	$('#dashboard_equip_id').val(VarIdentifier);
	$('#gapp-genset-dashboard').attr('action',VarAction);
	$('#gapp-genset-dashboard').submit();
}