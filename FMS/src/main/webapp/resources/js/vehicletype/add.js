var $uploadCrop;
$(document).ready(function(){
	
	$('.add_button').click(function(){
		if($('.add_button').attr('disabled') == 'disabled'){
			return;
		}
						
		var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="categoryFields[]" value="" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle"></i></a></div>'; 
		$('.field_wrapper').append(fieldHTML);
	});
	
	$('.field_wrapper').on('click', '.remove_button', function(e){
		if($('.remove_button').attr('disabled') == 'disabled'){
			return;
		}
		e.preventDefault();
		$(this).parent('div').remove();
		
	});
	
	$("#categoryName").bind("input",function(){
        this.value= this.value.toUpperCase();
	});
	
	$("#fieldsContainer").find('input[type=text]').bind("input",function(){
        this.value= this.value.toLowerCase();
	});
	
	// Form Validation
	$("#gapp-template-form").kendoValidator({
										validateOnBlur : true,
										errorTemplate: "<span class='help-block'><code>#=message#</code></span>"											
	});
		
	$('#gapp-template-save').prop('disabled', true);
	FnCheckEmptyFrmFields('#categoryName, #lastName, #emailId, #roleName', '#gapp-template-save');

	$("#vehicletype-logo").change(function(){
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
	
	if(vehicleAction == 'view'){
		FnResVehicleTypeDetails();
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


function FnDisplayAssetTypeImage(){
	
	if($('#image').val() != ''){
		$('.cr-image').attr('src',"data:image/png;base64," + $('#image').val()).css({"opacity":"1","transform":"translate3d(-412px, -284px, 0px) scale(0.260417)","transform-origin":"512.001px 384px 0px"});
		$('.cr-slider').attr({min:"0.0651",max:"1.5000"});
	}
}


function FnResVehicleTypeDetails(){
	
	console.log($('#vehicleTypeValues').val());
	if(!$.isEmptyObject($('#vehicleTypeValues').val())){
		var jsonobj = $.parseJSON($('#vehicleTypeValues').val());
		var i=0;
		$.each(jsonobj,function(key,val){
			if(i==0){
				$('#fieldsContainer').find ('input:first').val(val);
			} else {
				
//				var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="categoryFields[]" value="'+val+'" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle"></i></a></div>'; 
//				$('.field_wrapper').append(fieldHTML);
				
				var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="categoryFields[]" value="'
					+ val
					+ '" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle" style="color: red;"></i></a></div>';
			$('.field_wrapper').append(fieldHTML);
			}
			i++;
		});
		
		$("#vehicle_type_save :input").prop("disabled", true);
		$("#fieldsContainer :input").prop("disabled", true);
		$('#vehicletype-save').hide();
		
		FnDisplayAssetTypeImage();
		
	}	
}

function FnGetTemplateFields(){
	var ArrTemplateFields = '';
	$('#fieldsContainer').find('input[type=text]').each(function(){
		var VarFieldValue = FnTrim($(this).val());
		if(VarFieldValue != ''){
			VarFieldValue = VarFieldValue.toLowerCase();
			if(ArrTemplateFields != ''){
				ArrTemplateFields = ArrTemplateFields + "," + VarFieldValue;
			}else{
				ArrTemplateFields = VarFieldValue;
			}
		}
	});
	return ArrTemplateFields;
}

function FnSaveVehicleType(){
	$('#gapp-template-save, #gapp-template-cancel').attr('disabled',true);
	//var validator = $("#gapp-template-form").data("kendoValidator");
	//validator.hideMessages();
	//if (validator.validate()) {
		if (true) {
			var ArrTemplateFields = FnGetTemplateFields();
			if(ArrTemplateFields != ''){
				$("#GBL_loading").show();
				$('#vehicleTypeValues').val(ArrTemplateFields);
				
				$uploadCrop.croppie('result', {
					type: 'canvas',
					size: 'original'
				}).then(function (resp) {		
					
					var split = resp.split(',');
					$('#vehicletype-icon').val(split[1]);
					$('#vehicle_type_save').submit();
					return true;
				});

			} else {
				$("#alertModal").modal('show').find(".modalMessage").text("Please enter at least one field.");
				$('#gapp-template-save, #gapp-template-cancel').attr('disabled',false);
				return false;
			}
			
	} else {
		$('#gapp-template-save, #gapp-template-cancel').attr('disabled',false);
		$("#GBL_loading").hide();
		var errors = validator.errors();
		console.log(errors);
		return false;
	}
}


function FnResUpdateAssetType(response){

	var ArrResponse = response;
	$('#gapp-template-save, #gapp-template-cancel').attr('disabled',false);
	$("#GBL_loading").hide();
	if(ArrResponse.status == 'SUCCESS'){
				
		notificationMsg.show({
			message : 'Asset Type updated successfully'
		}, 'success');
		
		FnFormRedirect('gapp-template-list',GBLDelayTime);
		
	} else {
		FnShowNotificationMessage(ArrResponse);
	}

}

function FnResImageUpload(response,status){
	$('#assettype-logo').val('');
	$('#assettype-logo-info').html('');
}

function FnCancelAssetType(){
	
	if(GBLcancel > 0){
		$('#GBLModalcancel #hiddencancelFrm').val('gapp-template-list');
		$('#GBLModalcancel').modal('show');
	} else {
		$('#gapp-template-list').submit();
	}
}

function FnNavigateTemplateList(){
	$('#gapp-template-list').submit();
}

function FnEditAssetType(){
	$("#gapp-template-form :input").prop("disabled", false);
	$("#gapp-template-save").prop("disabled", false);
	$('#gapp-template-edit').hide();
}

function FnCancelVehicleType(){
	$('#vehicle_type_cancel').submit();
}
function changeToUpperCase(el){
    el.value =el.value.trim().toUpperCase();
}

function readURL(input,id) {
	var ArrAllowedImgTypes = ["image/jpeg","image/png","image/gif"];
	console.log(input.files);
	var VarImageType = input.files[0]['type'];
	if(ArrAllowedImgTypes.indexOf(VarImageType) != -1){
		if (input.files && input.files[0]) {
			var VarFileSize = input.files[0].size;
			if(VarFileSize <= GBLUPLOADIMAGESIZE){
				var reader = new FileReader();			
				reader.onload = function (e) {
					$('#vehicle-image-preview').attr('src', e.target.result);
					$('#vehicle-image-preview').css('background-color','whitesmoke');
				}			
				reader.readAsDataURL(input.files[0]);
			} else {
				$("#alertModal").modal('show').find(".modalMessage").text("Your file is larger than the maximum allowed of 2MB/file. Please select another image ");
				return false;
			}
		}		
	} else {
		$("#alertModal").modal('show').find(".modalMessage").text("Invalid file type.");
		return false;	
	}
	
}