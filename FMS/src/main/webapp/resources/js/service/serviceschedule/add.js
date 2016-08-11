"use strict";


$(document).ready(function(){
	FnGetComponentList(componentList);
	 $('#components').multiSelect({
		selectableHeader: "<div class='features-header'>Components Available</div>",
		selectionHeader: "<div class='features-header'>Components Selected</div>",
	});
	//var element = document.getElementById('components');
	//element.setAttribute('disabled', 'disabled');
	
})

$(window).load(function(){	
	//$('#components').prop("disabled", false);	
	var varIdentifier = $('#identifier').val(); 
	//--Get tags for selected tenant-//
	if(varIdentifier!= ""){	
		$('#schedule-edit').show();
		$('#schedule-save').hide();
		$("#schedule_add :input").prop("disabled", true);
						
		$('li.active').text('View');
		FnSavedComponentList(selectedComponentList);			
	}
	else{	
		$('#schedule-edit').hide();
		$('#schedule-save').show();
		$('.edit').hide();		
	}
	
	if(pageError != undefined && pageError != ''){
		$("#schedule_add :input").prop("disabled", false);
		FnComponentListOnError(componentsToSave);	
	}

});

function FnGetComponentList(componentList){
	var ArrResponse = componentList;
	var ArrResData = [];
	var select = document.getElementById("components");

	if($.isArray(ArrResponse)){
		for (var i in ArrResponse) {
			var option = document.createElement('option');
			option.value = ArrResponse[i];
			option.text = ArrResponse[i];			
			select.add(option);	
		}
	}	
	var my_options = $("#components option");

	my_options.sort(function(a,b) {
		if (a.text > b.text && a.value!="") return 1;
		else if (a.text < b.text && a.value!="") return -1;
		else return 0
	})
	$("#components").append(my_options);

}

function FnSaveSchedule(){
	var varObjComponents= JSON.stringify($('#components').val());
	if(varObjComponents=="null" || varObjComponents==null){
		$("#alertModal").modal('show').find(".modalMessage").text("Please select at least one component.");
		return false;
	} else {
		$("#GBL_loading").show();
		$("#schedule_add").submit();
		return true;
	}
}


function FnEditSchedule(){	
	$("#schedule_add :input").prop("disabled", false);
	$('#ms-components li').removeClass('disabled');
	$('#schedule-edit').hide();
	$('#schedule-save').show();
	$('.pageTitleTxt').text('Edit Service Schedule');
	$('li.active').text('Edit');

	var a = document.getElementById("action");
	a.value = "Update";


	
}

function FnCancelSchedule(){
	$("#schedule_list").submit();
}

function FnSavedComponentList(selectedComponentList){
	var ArrResponse = selectedComponentList;
	var ArrSaveFeatureData = [];
	if($.isArray(ArrResponse)){
		$.each(ArrResponse,function(key,val){
			ArrSaveFeatureData.push(val)
		});
		
		
		$("#components > option").each(function() {
			
			if($.inArray(this.value, ArrSaveFeatureData) >= 0){
				$(this).attr("selected","selected");
				
					$(this).css("pointer-events","none");
					$(this).css("opacity","0.8");
				
			}
		});
	}	
	$('#components').multiSelect('refresh');
}

function FnComponentListOnError(componentsToSave){
	var ArrResponse = componentsToSave;
	var ArrSaveFeatureData = [];
	if($.isArray(ArrResponse)){
		$.each(ArrResponse,function(key,val){
			ArrSaveFeatureData.push(val)
		});
				
		$("#components > option").each(function() {
			
			if($.inArray(this.value, ArrSaveFeatureData) >= 0){
				$(this).attr("selected","selected");
				
					//$(this).css("pointer-events","none");
					//$(this).css("opacity","0.8");
				
			}
		});
	}	
	$('#components').multiSelect('refresh');

	//$( "#components" ).prop( "disabled", true );
	/*
	$( "#tenantFeatures" ).prop( "disabled", true );
	$('.ms-selectable').css({"pointer-events": "none","opacity": 0.8});
	 */
}


