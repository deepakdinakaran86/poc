"use strict";
$(document).ready(function(){		
	console.log('Device: Ready');
	$("#device_view :input").prop("disabled", true);
	});







function FnEditDevice(){	
	$('#BtnEditDevice').hide();
	//.content > .fms-title h4
	$('.content > .fms-title h4').text('Edit Device');
	$("#device_view :input").prop("disabled", false);
	$('#gapp-device-save').attr('disabled',false);
	
	$('#sourceId').prop("disabled", true);
	$('#dsName').prop("disabled", true);			
	
	$('#gapp-adddevice-edit').hide();
	var tags = $("#tags").data("kendoMultiSelect");
	tags.enable(true);
}


function FnSaveDevice(){
	$('#gapp-device-save').text('Save');	
	$('#gapp-device-save, #gapp-device-cancel').attr('disabled',true);
	$("#GBL_loading").show();
	
	
}