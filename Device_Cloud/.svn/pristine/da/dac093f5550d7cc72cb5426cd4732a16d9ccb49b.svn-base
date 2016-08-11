var parameterArray;
var deviceName = '40077f6a-00d5-4580-b95f-784a5065fe9b';
var datasourceName;
var webSocketUrl = 'ws://10.234.31.202:8001/jms';
var destination;

function liveDevice(datasourceNameParam) {
	datasourceName = datasourceNameParam;
	$("#deviceName").text(datasourceName);
	getWebsocketConn();
	getDsParameters();
} 

var getWebsocketConn = function(){
	if(datasourceName){
		var ds = new Array();
		ds[0] =datasourceName;
		$
		.ajax({
			url : "ajax" + window.wsUrl1,
			type : 'POST',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(ds),
			success : function(response) {
				console.log(response.entity);
				webSocketUrl = response.entity.webSocketUrl;
				destination = response.entity.destination;
				handleConnect(webSocketUrl,datasourceName);
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});
	}
};

var getDsParameters = function(){
	//this IF will evaluate to true if value is not:(null,undefined,NaN,empty String,0,false)
	if(datasourceName){
		$
		.ajax({
			url : "ajax" + window.deviceUrl1+"/" +datasourceName,
			dataType : 'json',
			type : 'GET',
			success : function(response) {
				parameterArray = response.entity;
				console.log( '> '+parameterArray);

				//clear existing parameters in the combobox
				document.getElementById("parameter").options.length=0;

				for(var i=0; i<parameterArray.length; i++) {
					var obj = parameterArray[i];
					$('#parameter'+ '').append(
							'<option value=' + obj.id + '>' + obj.name  + '</option>');
				}
				//createDivs();
				//createCharts();
				createSlider();
				createFirstCharts();
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
					message: errorMessage
				}, "error");
			}
		});

		console.log("starting to connect....");
	}
};

var glbSlider;

var createSlider = function(){
	glbSlider =  $('.slider-wrapper').bxSlider({
		mode: 'horizontal',
		slideWidth: 530,		   
		minSlides: 3,
		maxSlides: 4,
		moveSlides: 1,
		slideMargin: 0,		    
		adaptiveHeight:true,
		infiniteLoop:false

	});	
};

function FnReloadSlider() {	
	if(hasNewParam){
		console.log('FnReloadSlider starts...');
		glbSlider.reloadSlider();
		hasNewParam = false;
	}
}
var ObjGblParamList = {};
var VarLimit = 6;
var dsColl = new Array();
var hasNewParam = false;

var updateChart = function(paramName,paramValue,paramType,paramTime){
	var VarDate = FnTimestampToDate(paramTime);
	if(paramType=="BOOLEAN"){
		paramValue = updateData(paramValue);
	}
	if(ObjGblParamList[paramName] != undefined){
	ObjGblParamList[paramName].push({"value":paramValue,"time":VarDate});
		dsColl[paramName].add({"value":paramValue,"time":VarDate});
		if(ObjGblParamList[paramName].length > VarLimit){
			dsColl[paramName].remove(dsColl[paramName].at(0));
		}
	}else {
		ObjGblParamList[paramName] = [];
		ObjGblParamList[paramName].push({"value":paramValue,"time":VarDate});
		dsColl[paramName].add({"value":paramValue,"time":VarDate});
		
	}
	FnDrawChart(paramName,paramType,{"value":paramValue,"time":VarDate});
};

function updateData(paramValue){
	if(paramValue=="true" || paramValue=="active" || paramValue=="open"){
		paramValue = "1";
	}else{
		paramValue="0";
	}
	return paramValue;
}

function FnDrawChart(VarChartId,paramType,dsData){
	VarChartId = VarChartId.replace(/ /g,'_');
	var chartLen = $('.slider-wrapper #chart_'+VarChartId).length;
	if(chartLen == 0){
		hasNewParam = true;
		$('.slider-wrapper').append('<div class="slide"><div style="border:2px solid black; margin-right:10px" id="chart_'+VarChartId+'" ></div></div>');

		var ds = new kendo.data.DataSource();
		ds.add(dsData);
		dsColl[VarChartId] = ds;

		paramType = paramType.toUpperCase();
		if(paramType=="BOOLEAN"){
			createBooleanChart(VarChartId,ds);
		}else if(paramType=="LONG" || paramType=="NUMBER" || paramType=="INTEGER" || paramType=="FLOAT" || paramType=="DOUBLE" || paramType=="SHORT"){
			createLineChart(VarChartId,ds);
		}
	} else {
		if($(".bx-clone #chart_"+VarChartId).length > 0){
			$(".bx-clone #chart_"+VarChartId).removeAttr('id').attr('data-ref',VarChartId);
		}
	}
}

function createBooleanChart(VarChartId,arrChartSource,ds){
	createBooleanChart(VarChartId,arrChartSource,ds);
}

function FnTimestampToDate(VarTimestamp){
	var date = new Date(Number(VarTimestamp));
	var dateTime = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	return dateTime;
}

var createFirstCharts = function(){
	if(parameterArray.length>=1){
		for(var i=0; i<parameterArray.length; i++) {
			var obj = parameterArray[i];
			var chartId = obj.name.replace(/ /g,'_');
			drawFirstCharts(chartId,obj.dataType);
		}
		hasNewParam = true;
		FnReloadSlider();
	}
};

var drawFirstCharts = function(chartId,paramType){

	//$('.slider-wrapper').append('<div class="slide"><div style="border:2px solid black; margin-right:10px" id="chart_'+chartId+'" ></div><div class="caption1">'+chartId+'</div></div>');
	$('.slider-wrapper').append('<div class="slide"><div style="border:2px solid black; margin-right:10px" id="chart_'+chartId+'" ></div></div>');
	var ds = new kendo.data.DataSource();
	dsColl[chartId] = ds;
	if(paramType=="BOOLEAN"){
		createBooleanChart(chartId,ds);
	}else if(paramType=="LONG" || paramType=="NUMBER" || paramType=="INTEGER" || paramType=="FLOAT" || paramType=="DOUBLE" || paramType=="SHORT"){
		createLineChart(chartId,ds);
	}
};
