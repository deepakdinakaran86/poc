var VarDashbardId;
var VarAssetIdentifier='';

$(document).ready(function(){
	//$("#GBL_loading").show();
	ObjUserData =JSON.parse(ObjUserData);
	
	console.clear();
	console.log(ObjUserData);
	var ArrHisAssets=[];
	$.each(ObjUserData.permissions, function( k,HisAssets){	
		var HisAssets = HisAssets.substr(HisAssets.indexOf("/") + 1)
		ArrHisAssets.push(HisAssets);
	});
	
	FnLoadAssetList(ArrHisAssets); 
});

$('#select-asset').change(function(){	 
	$("#select-asset option:selected").text();
	VarDashbardId=$(this).attr('value');
	$('#asset-name').html(VarDashbardId);
	
	FnGetAssetIdentifier(VarDashbardId);
});

function FnLoadAssetList(ArrHisAssets){
	var ArrHisAssets=ArrHisAssets;
	var option = '';
	for (var i=0;i<ArrHisAssets.length;i++){
		if(VarDashbardId == ArrHisAssets[i]){
			option += '<option selected="selected" value="'+ ArrHisAssets[i] + '">' + ArrHisAssets[i] + '</option>';
		}else{
			option += '<option value="'+ ArrHisAssets[i] + '">' + ArrHisAssets[i] + '</option>';
		}
	}	
	$('#select-asset').append(option);
}
	
$(window).bind("beforeunload", function() { 
	if(GblIsSubcribe == true){
		FnUnSubscribe();
	}
});

function FnDashboardInit(){
	$('#asset-name').html(VarDashbardId);
	FnGetAssetIdentifier(VarDashbardId);		
}

var globlalArrflag = false;

function FnResetDataUI(){
	FnBatteryStatus(0);
	FnMemoryStatus(0);
	FnCreateRadialGauge(0,0,0);
	stocks = [[], [], []];
	FnCreateChart();
	
}

function FnGetAssetIdentifier(VarDashbardId){
	
	FnResetDataUI();
	
	if(GblIsSubcribe == true){
		FnUnSubscribe();
	}
	
	var VarAssetId = VarDashbardId;
	
	var VarUrl = GblAppContextPath + '/ajax' + VarGetAssetIdentifier;

	var data ={
		"searchFields": [
			{
				"key": "assetName",
				"value": VarAssetId
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
	FnMakeAjaxRequest(VarUrl, 'POST',JSON.stringify(data), 'application/json; charset=utf-8', 'json', FnResAssetIdentifier);
}

function FnResAssetIdentifier(response){
	var ArrResponse = response;
	if($.isArray(ArrResponse)){
		$.each(ArrResponse, function(key, ObjVal){
			VarAssetIdentifier = ObjVal.identifier.value;
		});
		FnGetLatestPointInfoOfDevice(VarAssetIdentifier);
	}else{
		var ObjError = {"errorCode" : "500", "errorMessage" : "No Identifier available for this asset"};
		FnShowNotificationMessage(ObjError);
		return false;
	}	
}
	
var ArrDestinations = [];

function FnGetLatestPointInfoOfDevice(VarAssetIdentifier){
	var VarUrl = GblAppContextPath + '/ajax' + VarGetAssetLatestUrl;
	var data = {
		"domain" : {
			"domainName" : VarCurrentTenantInfo.tenantDomain
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
	FnMakeAsyncAjaxRequest(VarUrl, 'POST',JSON.stringify(data), 'application/json; charset=utf-8', 'json',FnResPointsOfSelectedGenset);
}
	
function FnResPointsOfSelectedGenset(response){
	var ArrResponse = response;
	//console.log(JSON.stringify(ArrResponse));
	if($.isArray(ArrResponse)){
		var ArrParams = [];	
		var VarDSName = '';		
		$.each(ArrResponse, function(key, ObjLatestPoint){
			var element = {};
			$.each(ObjLatestPoint.dataprovider, function(k, ObjLatestPointDetails){
				if(ObjLatestPointDetails.key == 'dataSourceName'){ 
					VarDSName = ObjLatestPointDetails.value;
				}
				if(ObjLatestPointDetails.key == 'pointId'){ 
					element['displayid'] = ObjLatestPointDetails.value;
				}
				if(ObjLatestPointDetails.key == 'dataType'){ 
					element['datatype'] = ObjLatestPointDetails.value;
				}	
				if(ObjLatestPointDetails.key == 'unit'){ 
					element['unit'] = ObjLatestPointDetails.value; 
				}
				if(ObjLatestPointDetails.key == 'displayName'){ 
					element['name'] = ObjLatestPointDetails.value; 
				}
				if(ObjLatestPointDetails.key == 'data'){ 
					element['value'] = ObjLatestPointDetails.value; 
				}
				if(ObjLatestPointDetails.key == 'deviceTime'){ 
					element['time'] = ObjLatestPointDetails.value; 
				}	
			});				
			ArrParams.push(element);	
		});
				
		var message = {"body":[{"datasourceName":VarDSName,"messageType":"MESSAGE","parameters":ArrParams}]};
		
		console.clear();
		console.log('Response: Points of selected asset');
		console.log(JSON.stringify(message));
		
		//calling websocket	
		var ds = new Array();
		ds[0] = VarDSName;
		FnGetSubscriptionInfo(ds,VarWebSocketType);
		
		FnHandleLatestData(message);
				
	}else {
		var ObjError = {"errorCode" : "500", "errorMessage" : "No Points available for this asset"};
		FnShowNotificationMessage(ObjError);
		return false;
	}
	
}

function FnSetDataUI(data,isInitialData) {
	var VarDSName = data.datasourceName;
	var ArrParameters = data.parameters;
	
	var ObjGyro = {};
	var ArrMagneticField = [];
	//var ArrMagneticField['x'];
	//var ArrMagneticField['y'];
	//var ArrMagneticField['z'];
	var ArrAccelerationAxis = [];
	// var ArrAccelerationAxis['x'] = 0;
	// var ArrAccelerationAxis['y'] = 0;
	// var ArrAccelerationAxis['z'] = 0;
	
	var ArrMagneticFieldX;
	var ArrMagneticFieldY;
	var ArrMagneticFieldZ;
	
	var ArrAccelerationAxisX;
	var ArrAccelerationAxisY;
	var ArrAccelerationAxisZ;
	
	var ArrGyroRotationAxisX;
	var ArrGyroRotationAxisY;
	var ArrGyroRotationAxisZ;
	
	
	var VarBatteryStatus;
	var VarMemoryStatus;
	var VarDataAvailable = true;
				
	for (var i = 0; i < ArrParameters.length; i++) {	
		
		// Battery status
		if (ArrParameters[i].name == 'Battery') {
			if(ArrParameters[i].value !== undefined){
				VarBatteryStatus = ArrParameters[i].value;	
			} else{
				VarDataAvailable = false;
			}		
		}
		
		// FreeSpace status 
		if (ArrParameters[i].name == 'FreeSpace') {
			if(ArrParameters[i].value !== undefined){
				VarMemoryStatus = ArrParameters[i].value;		
			} else{
				VarDataAvailable = false;
			}
		}	
		
		//GyroRotation
		if (ArrParameters[i].name == 'GyroRotationX') {
			if(ArrParameters[i].value !== undefined){
				ArrGyroRotationAxisX=ArrParameters[i].value;
			} else{
				VarDataAvailable = false;
			}
			
		}else if (ArrParameters[i].name == 'GyroRotationY') {
			if(ArrParameters[i].value !== undefined){
				ArrGyroRotationAxisY=ArrParameters[i].value;
			} else{
				VarDataAvailable = false;
			}
			
		}else if (ArrParameters[i].name == 'GyroRotationZ') {
			if(ArrParameters[i].value !== undefined){
				ArrGyroRotationAxisZ=ArrParameters[i].value;
			} else{
				VarDataAvailable = false;
			}
		}
		
		//Accelerometer
		 if (ArrParameters[i].name == 'AccelerationX') {
			 if(ArrParameters[i].value !== undefined){
				 ArrAccelerationAxisX=ArrParameters[i].value;
			 } else{
				 VarDataAvailable = false;
			 }
			
		 }else if (ArrParameters[i].name == 'AccelerationY') {
			 if(ArrParameters[i].value !== undefined){
				 ArrAccelerationAxisY=ArrParameters[i].value;
			 } else{
				 VarDataAvailable = false;
			 }
			
		 }else if (ArrParameters[i].name == 'AccelerationZ') {
			 if(ArrParameters[i].value !== undefined){
				ArrAccelerationAxisZ=ArrParameters[i].value;
			 } else{
				VarDataAvailable = false;
			 }
		 }
		
		//Magnetometer Sensor
		/*if(ArrParameters[i].name == 'MagneticFieldX'){
			if(ArrParameters[i].value !== undefined){
				ArrMagneticFieldX = ArrParameters[i].value;
			} else{
				VarDataAvailable = false;
			}
		}
		if(ArrParameters[i].name == 'MagneticFieldY'){
			if(ArrParameters[i].value !== undefined){
				ArrMagneticFieldY = ArrParameters[i].value;
			} else{
				VarDataAvailable = false;
			}
		}
		if(ArrParameters[i].name == 'MagneticFieldZ'){
			if(ArrParameters[i].value !== undefined){
				ArrMagneticFieldZ = ArrParameters[i].value;
			} else{
				VarDataAvailable = false;
			}
		} */
	}
						
	if(!VarDataAvailable){
		var ObjError = {"errorCode" : "500", "errorMessage" : "No Data available for this asset"};
		FnShowNotificationMessage(ObjError);
		return false;
	}
						
	FnBatteryStatus(VarBatteryStatus);
	
	FnMemoryStatus(VarMemoryStatus);	
				

	 if(ArrGyroRotationAxisX !== 0 || ArrGyroRotationAxisY !== 0 || ArrGyroRotationAxisZ !== 0){
		FnUpdateRadialGauge(ArrGyroRotationAxisX,ArrGyroRotationAxisY,ArrGyroRotationAxisZ);
	 }
	
	
	 if(ArrAccelerationAxisX !== undefined || ArrAccelerationAxisY !== undefined || ArrAccelerationAxisZ !== undefined){
		setData(ArrAccelerationAxisX,ArrAccelerationAxisY,ArrAccelerationAxisZ);
	 }
	
	
	
}


		function FnBatteryStatus(VarBStatus){
			if(undefined != VarBStatus){
				$("#status").width(VarBStatus);
				var statusWidth = $('#status').width();
				$('#status-number').html(precise_round(VarBStatus,0)+'%');
			}
		}
		
		function FnMemoryStatus(VarMStatus){
			if(undefined !== VarMStatus){
				$("#memory-status").width(VarMStatus);
				var statusWidth = $('#memory-status').width();
				$('#memory-status-number').html(precise_round(VarMStatus,0)+'%');		
			}
		}
		function FnUpdateRadialGauge(Ax,Ay,Az) {
			var Ax = parseFloat(Ax);
			var Ay = parseFloat(Ay);
			var Az = parseFloat(Az);
			
			if(undefined !== Ax  && !isNaN(Ax)){
				$("#rpm").data("kendoRadialGauge").value(Ax);
			}
			
			if(undefined !== Ay  && !isNaN(Ay)){
				$("#kmh").data("kendoRadialGauge").value(Ay);
			}
			
			if(undefined !== Az  && !isNaN(Az)){
				$("#fuel").data("kendoRadialGauge").value(Az);
			}
			
		}
		function FnCreateRadialGauge(Ax,Ay,Az) {
            var Ax = parseFloat(Ax);
			var Ay = parseFloat(Ay);
			var Az = parseFloat(Az);
			
			$("#rpm").kendoRadialGauge({
                theme: "black",
                pointer: {
                    value: Ax,
                    color: "#ea7001"
                },
                scale: {
                    startAngle: -45,
                    endAngle: 120,
                    min: -10,
                    max: 10,
					minorUnit: 1,
					labels: {
						font: "11px Arial,Helvetica,sans-serif"
                    }
                    /*
                    majorTicks: {
						width: 1,
						size: 7
                    },
                    minorUnit: 0.2,
					majorUnit: 1,
					*/
                }
            });

            $("#kmh").kendoRadialGauge({
                theme: "black",
                pointer: {
                    value: Ay,
                    color: "#ea7001"
                },
                scale: {
					startAngle: -60,
					endAngle: 240,
					min: -10,
					max: 10,
					majorTicks: {
						width: 1,
						size: 14
					},
					majorUnit: 1.5,
					minorTicks: {
						size: 10
					},
					minorUnit: 1
                }
            });

            $("#fuel").kendoRadialGauge({
                theme: "black",
                pointer: {
                    value: Az,
                    color: "#ea7001"
                },
                scale: {
                    startAngle: 90,
                    endAngle: 180,
                    min: -10,
                    max: 10,
                    majorUnit: 5,
                    majorTicks: {
						width: 1,
                        size: 12
                    },
                    minorUnit: 1,
                    minorTicks: {
                        size: 5
                    },
                    ranges: [{
                        from: 0,
                        to: 0.1,
                        color: "#c20000"
                    }],
                    labels: {
                        font: "9px Arial,Helvetica,sans-serif"
                    }
                }
            });
			
        }

		
		var charts = ['line','bar','area','bubble'];
	
		var TICKS_PER_DAY = 86400000,
			POINTS = 20,
			lastDate = new Date("2000/01/20"),
			playing,
			frames,
			fpsUpdateInterval,
			categoryList = [],
			stocks = [[], [], []];
	
			function FnCreateChart() {
				$("#line-chart").kendoChart({
					//renderAs: "canvas",
					title: {
						text: " "/*,
						color: "#fff",
						font: "13px Open Sans,sans-serif"*/
						
					},
					color: "#fff",
					legend: {
						visible: true,
						labels: {
							
							color: "#FFFFFF"
						},
					},
					chartArea: {
						background: "transparent"
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
					series: [{
						type: "line",
						name: "X Value",
						color: "#F1353D",
						overlay: null,
						data: stocks[0],
						tooltip: {
							visible: true
						}
					}, {
						type: "line",
						name: "Y Value",
						color: "#1CAF9A",
						data: stocks[1],
						tooltip: {
							visible: true
						}
					}, {
						type: "line",
						name: "Z Value",
						color: "#0F628A",
						data: stocks[2],
						tooltip: {
							visible: true
						}
					}],
					valueAxis: {
						labels: {
							format: "{0}",
							color: "#FFFFFF"
						},
						line: {
							visible: true
						}
					},
					categoryAxis: {
						categories: categoryList,
						majorGridLines: {
							visible: false
						}
					},
					transitions: false
				});
			}

			function addSeries(seriesName){
				stocks.push([]);
				var chartIndex = stocks.length%charts.length;
				 $("#line-chart").data("kendoChart").options.series.push({
						type: charts[chartIndex-1],
						name: charts[chartIndex]+stocks.length,
						color: "#14529A",
						overlay: null,
						data: stocks[stocks.length-1]
					});
					$("#line-chart").data("kendoChart").refresh();
					
			}
			
			function initializeData() {
				/*
				for (var i = 0; i < POINTS; i++) {
					addPoint();
				}
				*/
			}

			function play() {
				var start = new Date();

				frames = 0;
				playing = true;
				step();

				fpsUpdateInterval = setInterval(function() {
					var time = (new Date() - start) / 1000,
						fps = (frames / time),
						delay = Math.round(1000 / fps);
						/*
						$("#fpsCount").html(kendo.format(
							"FPS: {0:N1} ({1} ms/frame)",
							fps, delay
						));
						*/
					start = new Date();
					frames = 0;
				}, 1000);
			}

			function stop() {
				clearInterval(fpsUpdateInterval);
				playing = false;
				fpsUpdateInterval = null;
			}

			function step() {
				addPoint();
				$("#line-chart").data("kendoChart").refresh();
				frames++;
				if (playing) {
					kendo.animationFrame(setAlert);
				}
			}
			function setAlert(){
				addPoint();
				$("#line-chart").data("kendoChart").refresh();
				frames++;
			}
				
			function setData(Mx,My,Mz){
				var Mx = Mx;
				var My = My;
				var Mz = Mz;
                
				
				Mx = parseFloat(Mx);
				My = parseFloat(My);
				Mz = parseFloat(Mz);
				
				console.clear();
				console.log('Mx' + Mx);
				console.log('My' + My);
				console.log('Mz' + My);
				addData(Mx,My,Mz);

				$("#line-chart").data("kendoChart").refresh();
				frames++;
			}
				
				
			var h= 0;
			function addPoint() {
				var stockData,
					change,
					lastValue;

				// Shift existing categories to the left and add the next date at the end
				lastDate = new Date(lastDate.getTime() + TICKS_PER_DAY);
				categoryList.push((lastDate.getMonth() + 1) + "/" + (lastDate.getDay() + 1));

				if (categoryList.length > POINTS) {
					categoryList.shift();
				}

				for (var i = 0; i < stocks.length; i++) {
					stockData = stocks[i];

					change = (Math.random() > 0.5 ? 1 : - 1) * Math.random() * 10;
					//change = 0;
					
					lastValue = stockData[stockData.length - 1] || Math.random() * 10;

					// Add a new pseudo-random data point
					stockData.push(Math.min((i + 1) * 20, Math.max((i + 1) * 10, lastValue + change)));

					// Shift the data points of each series to the left
					if (stockData.length > POINTS) {
						stockData.shift();
					}
				}
				//for (var i = 0; i < stocks.length; i++) {
				//}
			}
				
			function addData(Mx,My,Mz) {
				var stockData,stockDataX,stockDataY,stockDataZ,
					change,
					lastValue,
					Mx,
					My,
					Mz;
					
				for (var i = 0; i < stocks.length; i++) {
					stockData = stocks[i];
					if(i==0){ 
					    if(undefined !== Mx && !isNaN(Mx)){
							stockData.push(Mx); 
						}else{
							stockData.push(stockData[stockData.length -1]);
						}
						
					}
					if(i==1){ 
						 if(undefined !== My && !isNaN(My)){
							stockData.push(My); 
						}else{
							stockData.push(stockData[stockData.length -1]);
						}
					}
					if(i==2){
						 if(undefined !== Mz && !isNaN(Mz)){
							stockData.push(Mz); 
						}else{
							stockData.push(stockData[stockData.length -1]);
						}
					}

					// Shift the data points of each series to the left
					if (stockData.length > POINTS) {
						stockData.shift();
					}
				}
			}

			$("#playButton").click(function() {
				var button = $(this);
				if (playing) {
					stop();
					button.text("Start update");
				} else {
					play();
					button.html("Pause update");
				}
			});
				
function precise_round(num, decimals) {
	var t=Math.pow(10, decimals);   
	return (Math.round((num * t) + (decimals>0?1:0)*(Math.sign(num) * (10 / Math.pow(100, decimals)))) / t).toFixed(decimals);
}	
