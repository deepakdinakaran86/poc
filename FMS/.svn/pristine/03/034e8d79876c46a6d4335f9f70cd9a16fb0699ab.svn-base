$(document).ready(function(){
	//alert("inside ready");
	
	//Fix for Create tag button disabled in create view 
	//$('#gapp-tag-create').attr('disabled','true');
	$("#tagType").bind("input",function(){
        this.value= this.value.toUpperCase();
	});
	
	$("#fieldsContainer").find('input[type=text]').bind("input",function(){
        this.value= this.value.toLowerCase();
	});
	
	//Form Validation
	$("#gapp-tag-create-form").kendoValidator({
										validateOnBlur : true,
										errorTemplate: "<span class='help-block'><code>#=message#</code></span>"											
	});

	
});

var TagId;

$(window).load(function() {
	//FnInitializeTagList();
	FnGetAssignableTemplates();
	if(tagTypesListResp != undefined) {
		FnResTagTypesList(tagTypesListResp);
	}
	if(tagViewResp != undefined) {
		FnResViewTag(tagViewResp);
		FnResViewMappedTemplates(viewMappedTemplatesResp);
	}
	if(createTagFields != undefined) {
		FnErrorCreateTagFields(createTagFields);
	}
	
	//FnResAssignableTemplates(listOfTemplatesResp);
	//FnGetTagList();
});

//function FnGetTagTypes(){
//	$("#GBL_loading").show();
//	var VarUrl = "ajax/galaxy-tags/1.0.0/tagTypes?domain_name=" + UserInfo.getCurrentDomain();
//	//VarUrl = VarUrl.replace("{domain}",UserInfo.getCurrentDomain());
//	//alert("in fngettagTypeslist");
//	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResTagTypesList);
//}

function FnResTagTypesList(response){
	$("#GBL_loading").hide();
	var ArrResponse = response;
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		var VarTagTypesTxt = '<option value=""> Select Tag Type </option>';
		$.each(ArrResponse,function(key,val){
			VarTagTypesTxt += '<option value="'+val.entityTemplateName+'">'+val.entityTemplateName+'</option>';
		});
		$('#tagCategory').html(VarTagTypesTxt);
		$('#gapp-tag-edit').attr('disabled',true);
	}
}

function FnGetAssignableTemplates(){
	var response = [
	                {
	                  "platformEntityType": "TENANT",
	                  "displayName": "Tenant",
	                  "entityTemplateName": "DefaultTenant"
	                },
	                {
	                  "platformEntityType": "USER",
	                  "displayName": "User",
	                  "entityTemplateName": "DefaultUser"
	                },
	                /*{
	                  "platformEntityType": "MARKER",
	                  "displayName": "Device",
	                  "entityTemplateName": "Device"
	                },*/
	                {
	                  "platformEntityType": "MARKER",
	                  "displayName": "Point",
	                  "entityTemplateName": "Point"
	                },
	                {
	                  "platformEntityType": "MARKER",
	                  "displayName": "Asset",
	                  "entityTemplateName": "Asset"
	                }
	              ];

	FnResAssignableTemplates(response);
}

//function FnResAssignableTemplates(response){
//	var ArrResponse = response;
//	if(ArrResponse.errorCode == undefined){
//		$.each(ArrResponse,function(key,obj){
//			var ObjRole = {};
//			ObjRole.value  = obj.entityTemplateName;
//			ObjRole.name = obj.displayName;
//			ArrResData.push(ObjRole);
//		});
//	}
//	var templates = $("#templateFields").data("kendoMultiSelect");
//	templates.setDataSource(ArrResData);
//}

function FnResAssignableTemplates(response){
	$("#GBL_loading").hide();
	var ArrResponse = response;
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		var VarTemplates = {};
		$.each(ArrResponse,function(key,val){
			if(val.displayName == 'Asset'){
				VarTemplates+= '<option id="'+val.entityTemplateName+'" value="'+val.entityTemplateName+'">'+'Vehicle'+'</option>' ;
			} else {
				VarTemplates+= '<option id="'+val.entityTemplateName+'" value="'+val.entityTemplateName+'">'+val.displayName+'</option>' ;
			}
		});
		$('#templateFields').html(VarTemplates);
	}
}

function FnResViewTag(response){
	$("#GBL_loading").hide();
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		//Get Tag Type Value
		tagType = ObjResponse.entityTemplate.entityTemplateName;
		FnViewSetTagType(tagType);
		
		//Populate the Tag Type FieldValues
		FnViewTagTypeFieldValues(ObjResponse)
		
		$('#gapp-tag-create').attr('disabled',true);
	} else {
		if(ObjResponse.errorCode != undefined){
			FnShowNotificationMessage(ObjResponse);
		}
	}
}

function FnErrorCreateTagFields(response){
	$("#GBL_loading").hide();
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		//Get Tag Type Value
		tagType = ObjResponse.tagType;
		var VarTagTypeTxt = '<option value="'+tagType+'">'+tagType+'</option>';
		$('#tagCategory').html(VarTagTypeTxt);
		
		//Populate the Tag Type FieldValues
		$('#fieldsSection').show();
		var VarTagTypeFieldTxt = '';
		var VarTabIndex = 6;
		$.each($.parseJSON(ObjResponse.tagFieldValues),function(key,val){
			if(val.key != "identifier"){
				VarTagTypeFieldTxt += '<div class="form-group form-md-line-input col-md-6"><label class="col-md-12 control-label capitalize" style="padding:0;margin:0" for="'+val.key+'"> '+val.key+' </label><input type="text" class="form-control input-sm" name="'+val.key+'" id="'+val.key+'" value="'+val.value+'"tabindex="'+VarTabIndex+'" /><div class="form-control-focus"></div></div>';
				VarTabIndex++;
			}
			else{
				TagId = val.key;
			}
		});
		$('#fieldsContainer').html(VarTagTypeFieldTxt);
		
		if($.isArray(ObjResponse.assignToTemplates)){
			var VarResLength = ObjResponse.assignToTemplates.length;
			var VarTemplates = {};
			if(VarResLength>0){	
				$.each(ObjResponse.assignToTemplates,function(index,value){
					//alert( index + ": " + value );
					if(value == "Asset"){
						$("#templateFields option[value='Asset']").prop("selected", true);
					} else {
						$("#templateFields option[value='"+value + "']").prop("selected", true);
					}
				});
			}
		}
	}
}

function FnViewSetTagType(response){
	var VarTagTypeTxt = '<option value="'+response+'">'+response+'</option>';
	$('#tagCategory').html(VarTagTypeTxt);
	$('#tagCategory').prop("readonly", true);
}

function FnViewTagTypeFieldValues(response){
	$('#fieldsSection').show();
	var VarTagTypeFieldTxt = '';
	var VarTabIndex = 6;
	$.each(response.dataprovider,function(key,val){
		if(val.key != "identifier"){
			VarTagTypeFieldTxt += '<div class="form-group form-md-line-input col-md-6"><label class="col-md-12 control-label capitalize" style="padding:0;margin:0" for="'+val.key+'"> '+val.key+' </label><input type="text" class="form-control input-sm" name="'+val.key+'" id="'+val.key+'" value="'+val.value+'"tabindex="'+VarTabIndex+'" readonly/><div class="form-control-focus"></div></div>';
			VarTabIndex++;
		}
		else{
			TagId = val.key;
		}
	});
	$('#fieldsContainer').html(VarTagTypeFieldTxt);
}

function FnGetTagTypeDetails(tagTypeValue) {
	if(tagTypeValue != undefined){
		if(tagTypeValue != ''){
			var VarUrl = 'ajax/galaxy-tags/1.0.0/tagTypes/' + tagTypeValue + '?domain_name=' + UserInfo.getCurrentDomain();
			//VarUrl = VarUrl.replace("{tag_type_value}",tagTypeValue);
			FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResTagTypeFields);
		} else {
			$('#fieldsContainer').html('');
			$('#fieldsSection').hide();
		}
	}
}

function FnResViewMappedTemplates(response) {
	$("#GBL_loading").hide();
	var ArrResponse = response;
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		var VarTemplates = {};
		$.each(ArrResponse,function(key,val){
			if(val.entityTemplateName == "Asset"){
				$("#templateFields option[value='Asset']").prop("selected", true);
				$("#templateFields option[value='Asset']").prop("disabled", true);
			} else {
				$("#templateFields option[value='"+ val.entityTemplateName + "']").prop("selected", true);
				$("#templateFields option[value='"+ val.entityTemplateName + "']").prop("disabled", true);
			}
		});
		$("#templateFields").prop("disabled", true);
	}
}

function FnResTagTypeFields(response){
	var ObjResponse = response.entity;
	if(!$.isEmptyObject(ObjResponse)){
		$('#fieldsSection').show();
		var VarTagTypeFieldTxt = '';
		var VarTabIndex = 6;
		$.each(ObjResponse.tagTypeValues,function(key,val){
			
			VarTagTypeFieldTxt += '<div class="form-group form-md-line-input col-md-6"><label style="padding:0"  class="col-md-12 control-label capitalize" for="'+val+'"> '+val+' </label><input type="text" class="form-control input-sm" name="'+val+'" id="'+val+'" tabindex="'+VarTabIndex+'"/><div class="form-control-focus"></div></div>';
			
			VarTabIndex++;
		});
		$('#fieldsContainer').html(VarTagTypeFieldTxt);
	} else {
		if(ArrResponse.errorCode != undefined){
			FnShowNotificationMessage(ArrResponse);
		}
	}
}

function FnSaveTag(){
	var assignToTemplates = FnGetSelectedTemplates();
	var tagType = FnGetSelectedTagType();
	var tagFieldValues = FnGetTagFieldValues();
	var tagName = FnGetTagName(tagFieldValues);
	var object = {};
	object['tagName'] = tagName;
	object['tagType'] = tagType;
	object['assignToTemplates'] = assignToTemplates;
	object['tagFieldValues'] = tagFieldValues;
	//console.log(JSON.stringify(object))
	
	$('#tagName').val(tagName);
	$('#tagType').val(tagType);
	$('#assignToTemplates').val(assignToTemplates);
	$('#tagFieldValues').val(JSON.stringify(tagFieldValues));
	
	if(TagId != undefined){
		$('#tagId').val(TagId);
	} 
//	else {
	$('#gapp-tag-create-form').submit();
//	}
	
} 

function FnGetSelectedTemplates(){
	var selectedTemplates = [];    
	$("#templateFields :selected").each(function(){
			selectedTemplates.push($(this).val()); 
	});
	//alert(selectedTemplates);
	return selectedTemplates;
}

function FnGetSelectedTagType(){
	var selectedTagType = {};
	$("#tagCategory :selected").each(function(){
		selectedTagType = ($(this).val()); 
	});
	//alert(selectedTagType);
	return selectedTagType;
	
}

function FnGetTagFieldValues(){
	var ArrFieldValuesJSONObj = [];
	$('#fieldsContainer').find('input[type=text]').each(function(){
		var ObjFieldMap = {};
		var VarFieldName = $(this).attr('name');
		var VarFieldValue = FnTrim($(this).val());
		if (!(typeof VarFieldValue === "undefined")) {
			if ($(this).is("[type='text']")){
				ObjFieldMap["key"] = VarFieldName;
				ObjFieldMap["value"] = VarFieldValue;
			}
			ArrFieldValuesJSONObj.push(ObjFieldMap);
		}
	});
	//alert(ArrFieldValuesJSONObj);
	return ArrFieldValuesJSONObj;
}

function FnGetTagName(fieldValues){
	$.each(fieldValues,function(key,obj){
		if(obj['key'] == 'name'){
			if(undefined == obj['value'] || obj['value']==''){
				FlagIsEmpty=true;
			}else{
				tagName = obj['value'];
			}
			
		}
	});
	return tagName;
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

function FnEditTag(){
	
	$("#templateFields").prop("disabled", false);
	$("#gapp-tag-create").attr("disabled",false);

}

//------Remove this if Not WORKING ON IE, FIREFOX or is causing other Mousedown elements to stop!------
window.onmousedown = function (e) {
    var el = e.target;
    if (el.tagName.toLowerCase() == 'option' && el.parentNode.hasAttribute('multiple')) {
        e.preventDefault();

        // toggle selection
        if (el.hasAttribute('selected')){
        	el.removeAttribute('selected');
        }
        else {
        	el.setAttribute('selected', '');
        }

        var select = el.parentNode.cloneNode(true);
        el.parentNode.parentNode.replaceChild(select, el.parentNode);
    }
}

function FnGetStatus(){
	var varStatus = 'ACTIVE';
	$("input[name='status']").each(function(){
		if($(this).is(':checked') == true){
			varStatus = $(this).val();
			alert(varStatus);
		}
	});
	return varStatus
}

function FnCancelTag(){
	window.location.href = "tag_home";
}

function FnResTagTypeTypeDetails(response){
	alert(":INSIDE:")
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		$('#gapp-tag-type-create-form #tagType').val(ObjResponse['tagType']);
		var i=0;
		$.each(ObjResponse.tagTypeValues,function(key,val){
			if(i==0){
				$('#fieldsContainer').find ('input:first').val(val);
			} else {
				
				var fieldHTML = '<div class="formelement_addition col-md-6" style="padding: 0; margin: 0;"><input type="text" class="form-control" name="tagTypeValues[]" value="'+val+'" onkeypress="return FnAllowAlphabetsOnly(event)"/><a href="javascript:void(0);" class="remove_button" title="Remove field"><i class="fa fa-minus-circle"></i></a></div>'; 
				$('.field_wrapper').append(fieldHTML);
			}
			i++;
		});
		
		$("#gapp-tag-type-create-form :input").prop("disabled", true);
		$('.add_button, .remove_button').attr('disabled',true);
		$('#gapp-tag-type-save').removeAttr('onclick');

	}	
}