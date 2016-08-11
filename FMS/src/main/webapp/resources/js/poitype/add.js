var $uploadCrop;
$(document)
		.ready(function() {					
					

					$('.add_button')
							.click(
									function() {										
										if (poiAction != 'edit') {
											if ($('.add_button').attr('disabled') == 'disabled') {
												return;
											}
											var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="categoryFields[]" value="" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle"></i></a></div>';
											$('.field_wrapper').append(fieldHTML);								
											
										}
										
										
										
									});

					$('.field_wrapper')
							.on(
									'click',
									'.remove_button',
									function(e) {
										if (poiAction != 'edit') {
											if ($('.remove_button')
													.attr('disabled') == 'disabled') {
												return;
											}
											e.preventDefault();
											$(this).parent('div').remove();
											
										}
										else{
											
											console.log('Edit mode: No Deletion');
										}
										
									

									});

					
					$("#fieldsContainer").find('input[type=text]').bind(
							"input", function() {
								this.value = this.value.toLowerCase();
							});

					// Form Validation
					$("#poi_type_save").kendoValidator({
										validateOnBlur : true,
										errorTemplate : "<span class='help-block'><code>#=message#</code></span>"
					});
					
					$("#poitype-logo").change(function() {
						readFile(this);

					});
					
					$uploadCrop = $('#upload-demo').croppie({
						viewport: {
							width: 50,
							height: 50,
							type: 'circle'
						},
						boundary: {
							width: 200,
							height: 200
						},
						exif: true
					});

					if (poiAction == 'edit') {				
						FnResPOITypeDetails();	
						$('#fms-poitype-save').prop('disabled', true);
						FnCheckEmptyFrmFields('#poiType','#fms-poitype-save');						
					}
					else if (poiAction == 'error') {				
						FnResPOITypeOnError();						
					}

				});

function readFile(input) {
	
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		
		reader.onload = function (e) {
			$uploadCrop.croppie('bind', {
				url: e.target.result
			});
			$('.upload-demo').addClass('ready');
		}
		
		reader.readAsDataURL(input.files[0]);
	}
	else {
		swal("Sorry - you're browser doesn't support the FileReader API");
	}
}


function FnResPOITypeOnError() {	
	
	var i = 0;
	if (!$.isEmptyObject($('#poiTypeValues').val())) {
		var stringArray = $('#poiTypeValues').val().split(',');
		$
				.each(
						stringArray,
						function(key, val) {
							if (i == 0) {
								$('#fieldsContainer').find('input:first').val(
										val);
							} else {

								var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="categoryFields[]" value="'
										+ val
										+ '" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle" style="color: red;"></i></a></div>';
								$('.field_wrapper').append(fieldHTML);
							}
							i++;
						});
	}
}

function FnResPOITypeDetails() {	

	var i = 0;
	if (!$.isEmptyObject($('#poiTypeValues').val())) {
		var jsonobj = $.parseJSON($('#poiTypeValues').val());
		$
				.each(
						jsonobj,
						function(key, val) {
							if (i == 0) {
								$('#fieldsContainer').find('input:first').val(
										val);
							} else {

								var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="categoryFields[]" value="'
										+ val
										+ '" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle" style="color: red;"></i></a></div>';
								$('.field_wrapper').append(fieldHTML);
							}
							i++;
						});

		$("#poi_type_save :input").prop("disabled", true);
		$("#fieldsContainer :input").prop("disabled", true);
		$('#poitype-save').hide();
		
		FnDisplayPOITypeImage();
	}
}

function FnDisplayPOITypeImage(){
	
	if($('#image').val() != ''){
		$('.cr-image').attr('src',"data:image/png;base64," + $('#image').val()).css({"opacity":"1","transform":"translate3d(-412px, -284px, 0px) scale(0.260417)","transform-origin":"512.001px 384px 0px"});
		$('.cr-slider').attr({min:"0.0651",max:"1.5000"});
	}
}

function FnGetTemplateFields() {
	var ArrTemplateFields = '';
	$('#fieldsContainer').find('input[type=text]').each(function() {
		var VarFieldValue = FnTrim($(this).val());
		if (VarFieldValue != '') {
			VarFieldValue = VarFieldValue.toLowerCase();
			if (ArrTemplateFields != '') {
				ArrTemplateFields = ArrTemplateFields + "," + VarFieldValue;
			} else {
				ArrTemplateFields = VarFieldValue;
			}
		}
	});
	return ArrTemplateFields;
}

function FnSavePOIType() { 
	$('#fms-poitype-save, #gapp-user-cancel').attr('disabled', true);
	var validator = $("#poi_type_save").data("kendoValidator");
	validator.hideMessages();
	if (validator.validate()) {
		var ArrTemplateFields = FnGetTemplateFields();		
		console.log(ArrTemplateFields);
		if (ArrTemplateFields != '') {
			$("#GBL_loading").show();
			$('#poiTypeValues').val(ArrTemplateFields);
			
			$uploadCrop.croppie('result', {
				type: 'canvas',
				size: 'original'
			}).then(function (resp) {		
				
				var split = resp.split(',');
				$('#poitype-icon').val(split[1]);
				$('#poi_type_save').submit();
				return true;
			});
		} else {
			$("#alertModal").modal('show').find(".modalMessage").text("Please enter at least one field.");
			$('#fms-poitype-save, #gapp-user-cancel').attr('disabled',false);
			return false;
		}
	} else {
		
		$('#fms-poitype-save, #gapp-user-cancel').attr('disabled', false);
		$("#GBL_loading").hide();
		var errors = validator.errors();
		console.log(errors);
		return false;
	}
}

function dataURItoBlob(dataURI) {
	var split = dataURI.split(','), dataTYPE = split[0].match(/:(.*?);/)[1], binary = atob(split[1]), array = []
	for (var i = 0; i < binary.length; i++)
		array.push(binary.charCodeAt(i))
	return new Blob([ new Uint8Array(array) ], {
		type : dataTYPE
	})
}

function FnResUpdateAssetType(response) {

	var ArrResponse = response;
	$('#gapp-template-save, #gapp-template-cancel').attr('disabled', false);
	$("#GBL_loading").hide();
	if (ArrResponse.status == 'SUCCESS') {

		notificationMsg.show({
			message : 'Asset Type updated successfully'
		}, 'success');

		FnFormRedirect('gapp-template-list', GBLDelayTime);

	} else {
		FnShowNotificationMessage(ArrResponse);
	}

}

function FnResImageUpload(response, status) {
	$('#assettype-logo').val('');
	$('#assettype-logo-info').html('');
}

function FnCancelAssetType() {

	if (GBLcancel > 0) {
		$('#GBLModalcancel #hiddencancelFrm').val('gapp-template-list');
		$('#GBLModalcancel').modal('show');
	} else {
		$('#gapp-template-list').submit();
	}
}

function FnNavigateTemplateList() {
	$('#gapp-template-list').submit();
}

function FnEditAssetType() {
	$("#gapp-template-form :input").prop("disabled", false);
	$("#gapp-template-save").prop("disabled", false);
	$('#gapp-template-edit').hide();
}

function FnCancelPOIType() {
	$('#poi_type_cancel').submit();
}

function changeToUpperCase(el) {
	el.value = el.value.trim().toUpperCase();
}

function readURL(input, id) {
	var ArrAllowedImgTypes = [ "image/jpeg", "image/png", "image/gif" ];
	var VarImageType = input.files[0]['type'];
	if (ArrAllowedImgTypes.indexOf(VarImageType) != -1) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#poi-image-preview').attr('src', e.target.result);
				$('#poi-image-preview').css('background-color', 'whitesmoke');
			}
			reader.readAsDataURL(input.files[0]);
		}
	} else {
		alert('Invalid File');
	}
}