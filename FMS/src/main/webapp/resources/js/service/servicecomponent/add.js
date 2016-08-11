"use strict";


$(document).ready(function(){
	
	// Form Validation
	$("#component_add").kendoValidator({
										rules: {
											frequency: function(input) {
												if(((input.attr('id') == 'frequencyInTime' && input.val().trim() == "") && $('#frequencyInDistance').val() == "" && $('#frequencyInRunningHours').val() == "") || ((input.attr('id') == 'frequencyInDistance' && input.val().trim() == "") && $('#frequencyInTime').val() == "" && $('#frequencyInRunningHours').val() == "") || ((input.attr('id') == 'frequencyInRunningHours' && input.val().trim() == "") && $('#frequencyInTime').val() == "" && $('#frequencyInDistance').val() == "")){
													if(input.attr('id') == 'frequencyInTime'){
														return false;
													} else {
														
														return true;
													}
													
												}																								
												return true;
											},
											notify: function(input) {
												if(((input.attr('id') == 'notificationInTime' && input.val().trim() == "") && $('#notificationInDistance').val() == "" && $('#notificationInRunningHours').val() == "") || ((input.attr('id') == 'notificationInDistance' && input.val().trim() == "") && $('#notificationInTime').val() == "" && $('#notificationInRunningHours').val() == "") || ((input.attr('id') == 'notificationInRunningHours' && input.val().trim() == "") && $('#notificationInTime').val() == "" && $('#notificationInDistance').val() == "")){
													if(input.attr('id') == 'notificationInTime'){
														return false;
													} else {
														return true;
													}
													
												}																								
												return true;
											},
											unique : function(input) {
												var value = input.val();
												if (input.attr('id') == 'serviceComponentName'
														&& input.val().trim() != "") {
													
													if (serviceComponentNameEdit != input
																	.val().trim()) {
														return checkUniquenessWithTemplate(
																checkUniqueFieldUrl,
																"serviceComponentName",
																input.val(),"ServiceComponent");
													}
												}
												return true;
											},
											mandatorySelect: function(input) {
												if (input.attr('id') == 'serviceItemName'
													&& (input.val() == '' || input
															.val().trim() == "" || input
															.val().trim() == "NONE")) {
												return false;
											}																							
												return true;
											}
										},
										messages: {
											frequency : "Please enter atleast one Frequency value",
											notify : "Please enter atleast one notify value",
											unique : "Component name is not unique",
											mandatorySelect: "Select service item"
										},
										validateOnBlur : true,
										errorTemplate: "<span class='help-block'><code>#=message#</code></span>",
	});
	
	
	var varIdentifier = $('#identifier').val(); 
	if(varIdentifier!= ""){	
		console.log('Selected a component');
		$('#component-edit').show();
		$('#component-save').hide();
		$("#component_add :input").prop("disabled", true);
		$('li.active').text('View');
		
	}else{	
		$('#component-edit').hide();
		$('.edit').hide();		
	}
})

function FnEditComponent(){	
	console.log('Clients:FnEditComponent');
	$("#component_add :input").prop("disabled", false);
	$('#serviceItems').prop("disabled", true);
	
	$('.pageTitleTxt').text('Edit Service Component');
	$('li.active').text('Edit');
	$('#component-edit').hide();
	$('#component-save').show();
	
	var a = document.getElementById("action");
	a.value = "Update";
}

function FnCancelComponent(){
	$("#component_list").submit();
}

function FnSaveComponent(){
	$('#component-save, #gapp-user-cancel').attr('disabled', true);
	var validator = $("#component_add").data("kendoValidator");
	validator.hideMessages();
	if (validator.validate()) {
		$("#GBL_loading").show();
		$("#component_add").submit();
		return true;
	} else {
		$('#component-save, #gapp-user-cancel').attr('disabled', false);
		$("#GBL_loading").hide();
		var errors = validator.errors();
		console.log(errors);
		return false;
	}
	
}
	
