$(document).ready(function(){
	//alert("inside ready");
	$("#tagType").bind("input",function(){
        this.value= this.value.toUpperCase();
	});
	
	$("#fieldsContainer").find('input[type=text]').bind("input",function(){
        this.value= this.value.toLowerCase();
	});
	
	$('.add_button').click(function(){
		if($('.add_button').attr('disabled') == 'disabled'){
			return;
		}
		//alert("CLICK");				
		var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="tagTypeValues[]" value="" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle"></i></a></div>'; 
		$('.field_wrapper').append(fieldHTML);
	});
	
	$('.field_wrapper').on('click', '.remove_button', function(e){
		if($('.remove_button').attr('disabled') == 'disabled'){
			return;
		}
		e.preventDefault();
		$(this).parent('div').remove();
		
	});
	
	//Form Validation
	$("#gapp-tag-type-create-form").kendoValidator({
										validateOnBlur : true,
										errorTemplate: "<span class='help-block'><code>#=message#</code></span>"											
	});
	
//	if(createTagTypeFields != '') {
//		//alert(viewTagTypeResponse);
//		FnPopulateTagTypeFields(createTagTypeFields);
//	}
//	
//	if(viewTagTypeResponse != '') {
//		//alert(viewTagTypeResponse);
//		FnResTagTypeDetails(viewTagTypeResponse);
//	}

});

function FnSaveTagType(){
	
	var validator = $("#gapp-tag-type-create-form").data("kendoValidator");
	validator.hideMessages();
	if (validator.validate()) {
		//alert("Validator successfull");
		var tagType = $('#tagType').val();
		var tagTypeValues = FnGetTemplateFields();
		//var status = FnGetStatus();
		//alert(tagType);
		$('#tagTypeName').val(tagType);
		//$('#status').val(status);
		$('#tagTypeValues').val(tagTypeValues);
		$('#tag_type_create_service').submit();
	}
} 

function FnGetTemplateFields(){
	var ArrTemplateFields = [];
	$('#fieldsContainer').find('input[type=text]').each(function(){
		var VarFieldValue = FnTrim($(this).val());
		if(VarFieldValue != ''){
			VarFieldValue = VarFieldValue.toLowerCase();
			ArrTemplateFields.push(VarFieldValue);
		}
	});
	//alert(ArrTemplateFields);
	return ArrTemplateFields;
}

function FnGetStatus(){
	var varStatus = 'ACTIVE';
	$("input[name='status']").each(function(){
		if($(this).is(':checked') == true){
			varStatus = $(this).val();
			//alert(varStatus);
		}
	});
	return varStatus
}

function FnCancel(){
	window.location.href = "tagtype_home";
}

function FnResSaveTagType(response){
	
	var ObjResponse = response;
	//$('#gapp-tag-save, #gapp-tag-cancel').attr('disabled',false);
	$('#gapp-tag-save, #gapp-tag-cancel').removeAttr('onclick');
	$("#GBL_loading").hide();
	if(!$.isEmptyObject(ObjResponse)){
	
		if(ObjResponse.status == 'SUCCESS'){
			notificationMsg.show({
				message : 'Tag Type created successfully.'
			}, 'success');
			
			FnFormRedirect('tag_type_view',GBLDelayTime);
						
		} else {
			//FnShowNotificationMessage(ObjResponse);
		}
	
	} else {
	
		notificationMsg.show({
			message : 'Error'
		}, 'error');
		
	}
	
}



function FnResTagTypeDetails(response){
	//alert(":INSIDE:")
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		$('#gapp-tag-type-create-form #tagType').val(ObjResponse['tagType']);
		var i=0;
		$.each(ObjResponse.tagTypeValues,function(key,val){
			if(i==0){
				$('#fieldsContainer').find ('input:first').val(val);
			} else {
				
				var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="tagTypeValues[]" value="'+val+'" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle"></i></a></div>'; 
				$('.field_wrapper').append(fieldHTML);
			}
			i++;
		});
		
		$("#gapp-tag-type-create-form :input").prop("disabled", true);
		$('.add_button, .remove_button').attr('disabled',true);
		$('#gapp-tag-type-save').removeAttr('onclick');
		//$("#gapp-template-cancel").prop("disabled", false);
		//$('#gapp-template-edit').hide();
		
	}	
}


function FnPopulateTagTypeFields(response){
	
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		$('#gapp-tag-type-create-form #tagType').val(ObjResponse['tagTypeName']);
		var i=0;
		$.each(ObjResponse.tagTypeValues,function(key,val){
			if(i==0){
				$('#fieldsContainer').find ('input:first').val(val);
			} else {
				
				var fieldHTML = '<div class="formelement_addition col-md-6"><input type="text" class="form-control" name="tagTypeValues[]" value="'+val+'" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle"></i></a></div>'; 
				$('.field_wrapper').append(fieldHTML);
			}
			i++;
		});
		
	}	
	
}