"use strict";


var GblPriority = 1;
var GblFlashColor ="#000000";
var GblDeviceTorchStatus ;
//var GblSourceId="A0F9D5C0-4488-48DD-B0F3-85E93B10390F";


console.log(VarLUDomainName +' / '+JSON.stringify(VarCurrentTenantInfo)+' / '+VarAppRootDomain);

// Get the asset id
var ArrPermissions = JSON.parse(UserCurrentPermissions);
var GblSourceId = (ArrPermissions[ArrPermissions.length-1]);
GblSourceId = GblSourceId.substring(GblSourceId.indexOf("/") + 1);
GblSourceId='8b81143b-186f-4928-9483-2a67433b8b46';
console.log(UserCurrentPermissions);

//alert(GblSourceId);




//["asset_access/90a16910-9b59-40fc-b915-a0a5cd79f0b2", "asset_access/a0f9d5c0-4488-48dd-b0f3-85e93b10390f"]
//demo.galaxy{"tenantName":"demo","tenantDomain":"demo.galaxy","parentDomain":"pcs.galaxy","time":"1465709338407","tenantId":"demo"} pcs.galaxy

$(document).ready(function(){
	/*
	$("#picker").kendoColorPicker({
	preview: false,
	value: "#ffffff",
	buttons: false            
	});	
	*/	
    var VarCurrentColor;	
	
        $("#writeback-color-picker").kendoFlatColorPicker({
            preview: false,
            value: "#000000",
           
        });		
		$('#writeback-color-picker-value').show();
			
			
	//$('.profile-usertitle-name').text(VarCurrentTenantInfo.tenantName);
	//alert(VarCurrentTenantInfo);
	FnGetFlashColorCode();
	FnGetDeviceList(VarCurrentTenantInfo);
	
	// device torch on off
	
	 $('#device-torch-btn-write-back').click(function() {
    $('img', this).attr('src', function(i, oldSrc) {
		
	if (oldSrc == VarWriteBackOnSrc){
		oldSrc =VarWriteBackOffSrc;		
		console.log('off');
		$('#mobile-flash').css("background-color", "#000");
		GblDeviceTorchStatus=false;
	}else{
		$('#mobile-flash').css("background-color", "#fff");
		
		oldSrc =VarWriteBackOnSrc;     console.log('on');
		GblDeviceTorchStatus=true;
		
	};
		
	return oldSrc;
        //return oldSrc == VarWriteBackOnSrc ? VarWriteBackOffSrc : VarWriteBackOnSrc;
    });
  
   // return false;
	var ObjParam = [
		  {
			"sourceId": GblSourceId,
			"payload": {
			  "command": "WRITE_COMMAND",
			  "pointId": 17,
			  "pointName": "DeviceTorch",
			  "value": GblDeviceTorchStatus,
			  "customSpecs": {
				"priority": GblPriority
			  }
			}
		  }
		 
		];
	var VarUrl = GblAppContextPath+'/ajax' + VarAssetWriteBackCommandUrl ;
	//VarUrl = "http://10.234.31.152:8282/saffron/1.0.0/write_back/commands";
	//console.log(JSON.stringify(ObjParam));	
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(ObjParam), 'application/json; charset=utf-8', 'json', FnResSendWriteBack);
		
  });
	
	 // $('#deviceTorchBtn').click(function() {
    // $('img', this).attr('src', function(i, oldSrc) {
		
	// if (oldSrc == VarWriteBackOnSrc){
		// oldSrc =VarWriteBackOffSrc;		
		// console.log('off');
		// GblDeviceTorchStatus=false;
	// }else{
		// oldSrc =VarWriteBackOnSrc;     console.log('on');
		// GblDeviceTorchStatus=true;
		
	// };
		
	// return oldSrc;
        // //return oldSrc == VarWriteBackOnSrc ? VarWriteBackOffSrc : VarWriteBackOnSrc;
    // });
  
   // // return false;
	// var ObjParam = [
		  // {
			// "sourceId": GblSourceId,
			// "payload": {
			  // "command": "WRITE_COMMAND",
			  // "pointId": 17,
			  // "pointName": "DeviceTorch",
			  // "value": GblDeviceTorchStatus,
			  // "customSpecs": {
				// "priority": GblPriority
			  // }
			// }
		  // }
		 
		// ];
	// var VarUrl = GblAppContextPath+'/ajax' + VarAssetWriteBackCommandUrl ;
	// //console.log(JSON.stringify(ObjParam));	
	// FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(ObjParam), 'application/json; charset=utf-8', 'json', FnResSendWriteBack);
		
  // });
  $("#mobile-inner").css("background-color", "#000");
	
});





//VarAssetWriteBackCommandUrl
function FnGetWritebackPriority(VarPriority){	
		GblPriority = $('#priority').find(":selected").text();
		//console.log(VarPriority);	
		//GblPriority =VarPriority;
}

/*
function FnGetFlashColorCode(){		
		var colorPicker = $("#picker").data("kendoColorPicker");		
		colorPicker.bind({
                select: function(e) {
                   // console.log("Select in picker #" + this.element.attr("id") + " :: " + e.value);
					GblFlashColor=e.value;
					FnChangeTheFlashColor();
                },
                change: function(e) {
                   // console.log("Change in picker #" + this.element.attr("id") + " :: " + e.value);
                },
                open: function() {
                   // console.log("Open in picker #" + this.element.attr("id"));
                },
                close: function() {
                   // console.log("Close in picker #" + this.element.attr("id"));
					
                }
			});

}
*/

function FnGetFlashColorCode(){		

		var colorPicker = $("#writeback-color-picker").data("kendoFlatColorPicker");		
		colorPicker.bind({
                select: function(e) {

                },
                change: function(e) {
                  					GblFlashColor=e.value;
					console.log("test "+GblFlashColor);
				$("#writeback-color-picker-value").css("background-color", e.value);
				$("#mobile-inner").css("background-color", e.value);
				
					FnChangeTheFlashColor();
				   
                },
                open: function() {
                   // console.log("Open in picker #" + this.element.attr("id"));
                },
                close: function() {
                   // console.log("Close in picker #" + this.element.attr("id"));
					
                }
			});

}


function FnGetDeviceList(ObjTenantInfo){
	//alert('FnGetDeviceList: ObjTenantInfo');
	var VarUrl = GblAppContextPath+'/ajax' + VarListDeviceUrl ;
	var ObjParam = FnGetTenantDeviceParams(ObjTenantInfo);	
	console.log('FnGetDeviceList');
	console.log(JSON.stringify(ObjParam));	
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(ObjParam), 'application/json; charset=utf-8', 'json', FnResGetDeviceList);
	
}


function FnResGetDeviceList(response){
	var ArrResponse = response;
	var VarResLength = ArrResponse.length;
	if (VarResLength > 0) {		
		//console.log('ArrResponse');
	//	console.log(JSON.stringify(ArrResponse));
		//need to find the sourceid for command post service
		}	
}


function FnGetTenantDeviceParams(ObjTenantInfo){
	var ObjParam = {};		
	if(ObjTenantInfo.tenantDomain != VarAppRootDomain){
		ObjParam['domain'] = {"domainName" : ObjTenantInfo.parentDomain};
		ObjParam["entityTemplate"] = {"entityTemplateName" : "DefaultTenant"};
		ObjParam["platformEntity"] = {"platformEntityType" : "TENANT"};		
		ObjParam["identifier"] = {"key" : "tenantId","value" : ObjTenantInfo.tenantId};
	}
	
	return ObjParam;
}

function FnChangeTheFlashColor(){	
	var ObjParam = [
 {
    "sourceId": GblSourceId,
    "payload": {
      "command": "WRITE_COMMAND",
      "pointId": 18,
      "pointName": "FlashColor",
      "value": GblFlashColor,
      "customSpecs": {
        "priority": 1
      }
    }
  }
];
var VarUrl = GblAppContextPath+'/ajax' + VarAssetWriteBackCommandUrl ;
	console.log(JSON.stringify(ObjParam));	
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(ObjParam), 'application/json; charset=utf-8', 'json', FnResSendWriteBack);
	
	
}


function FnSendWriteBack(){		
	GblDeviceTorchStatus = $("input[name=torchStatus]:checked").val();
	console.log(GblDeviceTorchStatus+" | " +GblPriority +" | "+ GblFlashColor );
		
	var ObjParam = [
  {
    "sourceId": GblSourceId,
    "payload": {
      "command": "WRITE_COMMAND",
      "pointId": 17,
      "pointName": "DeviceTorch",
      "value": GblDeviceTorchStatus,
      "customSpecs": {
        "priority": GblPriority
      }
    }
  }
 
];
	var VarUrl = GblAppContextPath+'/ajax' + VarAssetWriteBackCommandUrl ;
	console.log(JSON.stringify(ObjParam));	
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(ObjParam), 'application/json; charset=utf-8', 'json', FnResSendWriteBack);
}


function FnResSendWriteBack(response){
	var ObjResponse = response;
	//{"errorCode":"504","errorMessage":"The server encountered an internal error"}
	if(!$.isEmptyObject(ObjResponse)){
			if (ObjResponse.errorCode =="504"){			
				notificationMsg.show({
					message : "The server encountered an internal error"
				}, 'error');
				
			}
		
		
		//console.log(JSON.stringify(ObjResponse.errorMessage));	
		//FnShowNotificationMessage(ObjResponse);		
	}	
}

$('input[name="check-row"]').on('click', function(){
    if ( $(this).is(':checked') ) {
		$('input:checkbox').removeAttr('checked');
		$(this).attr('checked', true);
       
    } 
    
});
  var ArrSelectedCellRow=[];
function FnSelectRowCheckbox(currentTag){	
	$('#seatCharts-container input ').removeAttr('checked');	
	console.log(currentTag);
	var $cart = currentTag;
	var returnedRow = $cart.id;
	$('#selected-row').css('font-weight','bold').text(' Row: '+returnedRow);
	//$(this).attr('checked', true);  
	//$('input:checkbox').removeAttr('checked');
 
 
 var currentli = $('#'+$cart.id).parent().attr('class'); 
// console.log(currentli);
 
 //$(this).closest('.box').children('.something1')
  var cRow,loopRowValue,count=0;
  $('#selected-cells').html('');
  

	  $('div.'+currentli+'> div.available').each(function(key,val){		  
			loopRowValue=$(this).attr('id');
			cRow= loopRowValue.substr(0, loopRowValue.indexOf('_'));	
			if (cRow == returnedRow){				
				count++;
					$('ul#selected-cells').append('<li>'+$(this).attr('id')+'</li>');
					ArrSelectedCellRow.push($(this).attr('id'));
			}
	  });
	   $('#counter').html('Counter = '+count);
	   
	    
	   
	   
	   console.log(ArrSelectedCellRow +' count: ' +ArrSelectedCellRow.length);
	   
	   sc.get(['2_6', '1_8']).node().css({
        'background-color': '#ffcfcf'
    });
	   
	   console.clear();
	   console.log('SELECTIONS'+ ArrSelectedCellRow);
	   
	   // sc.get(ArrSelectedCellRow).status('unavailable'); 
		//sc.status(ArrSelectedCellRow, 'unvailable');
	 // $('#selected-row').append( ' | Count: '+count);

}
	

