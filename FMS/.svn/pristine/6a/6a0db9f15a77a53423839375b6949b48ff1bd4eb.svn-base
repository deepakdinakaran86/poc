"use strict";

$(document).ready(function(){
	
	// Form Validation
	$("#gapp-resetpassword-form").kendoValidator({
											validateOnBlur : true,
											errorTemplate: "<span class='help-block'><code>#=message#</code></span>",
											rules : {
												matches : function(input) {
													var matches = input.data('matches');
													if (matches) {
														var match = $(matches);
														if (input.is('[name=retypepassword]')) {
															return FnTrim(input.val()) == FnTrim(match.val());
														}
													}
													return true;
												}
										
											},
											messages : {
												matches : function(input) {
													return input.data("matchesMsg");
												}
											}
	});

});


function FnResetPassword(){	
	$('#gapp-password-save').attr('disabled',true);
	var validator = $("#gapp-resetpassword-form").data("kendoValidator");
	validator.hideMessages();
	if (validator.validate()) {
		$("#GBL_loading").show();
		$('#userName').val(varUserName);
		$('#resetPasswordIdentifierLink').val(VarIdentifier);
		$('#domain').val(varDomainName);
		$('#gapp-resetpassword-form').submit();
		return true;
	} else {
		$('#gapp-password-save').attr('disabled',false);
		$("#GBL_loading").hide();
		var errors = validator.errors();
		console.log(errors);
		return false;
	}
}
