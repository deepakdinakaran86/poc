
var points = [];
$(document).ready(function(){

	FnInitiateMap();
	// Form Validation
	$("#gapp-asset-form").kendoValidator({
										validateOnBlur : true,
										errorTemplate: "<span class='help-block'><code>#=message#</code></span>",
										rules : {
											available:function(input){
												var validate = input.data('available');
												var VarExist = true;
												if (typeof validate !== 'undefined' && validate !== false) {
													var VarThisFieldValue = FnTrim(input.val());
													if(VarEditAssetId != '' && VarThisFieldValue === GblEditAssetName){
														return VarExist;
													}
													
													var url = input.data('available-url');
													var VarParam = {"domain": {"domainName": UserInfo.getCurrentDomain()},"fieldValues": [{"key": "assetName","value": VarThisFieldValue}]};
													$.ajax({
														type:'POST',
														cache: true,
														async: false,
														contentType: 'application/json; charset=utf-8',
														url: "/FMS/ajax" + url,
														data: JSON.stringify(VarParam),
														dataType: 'json',
														success: function(result) {
															var ObjExistStatus = result.entity;																
															if(ObjExistStatus.status == 'SUCCESS'){ // Does not exist in db 
																VarExist = true;
															} else if(ObjExistStatus.status == 'FAILURE') { // Exist in db
																VarExist = false;
															}
														},
														error : function(xhr, status, error) {
														
														}
													});
													
												}
												return VarExist;
											}
										},
										messages : {
											available: function(input) { 
												return input.data("available-msg");
											}
										}
											
	});
	
	FnGetAssetTypes();	

	
	
	//---For name-tag --//
	$("#name-tag-placeholder").kendoMultiSelect({
		dataTextField : "name",
		dataValueField : "value",
		animation: false,
		enable: true
	});
	FnGetTagList(); // will list all the tags for the domain
	//---//
	
	
	
	if(VarEditAssetId != ''){
		FnGetAssetDetails();
	}else if(errorFlag!=undefined){
			
		FnReDrawFields();
	}

	$('#gapp-asset-save').prop('disabled', true);
	FnCheckEmptyFrmFields('#assetName, #assetTypeList, #assetId', '#gapp-asset-save');

	$("#asset-logo").change(function(){
		readURL(this,$(this).attr('id'));			
        
    });

	
	
});
$(window).load(function(){
	$("#tagDetails").val('');
	$("#geoDetails").val('');
	$("#assetTypeValues").val('');
	
});

function FnGetTagList(){
	FnResTagList(tags);
}
function FnResTagList(response){
	
	var ArrResponse = response;
	var ArrResData = [];
	if($.isArray(ArrResponse)){
		$.each(ArrResponse,function(key,val){
			var ObjRole = {};
			$.each(val,function(key,val){ 
			//	console.log("key="+key+" Value="+val);
				if(key == 'name'){
					ObjRole.name = val;
					ObjRole.value = val;
				}
				if(key == 'tagType'){
					ObjRole.tagType = val;
				}

			});
			
			ArrResData.push(ObjRole);
			
		});
	}	
	//console.log(ArrResData);
	var dataNameTag = $("#name-tag-placeholder").data("kendoMultiSelect");
	dataNameTag.setDataSource(ArrResData);
}

function readURL(input,id) {
	var ArrAllowedImgTypes = ["image/jpeg","image/png","image/gif"];
	console.log(input.files);
	var VarImageType = input.files[0]['type'];
	if(ArrAllowedImgTypes.indexOf(VarImageType) != -1){
		if (input.files && input.files[0]) {
			var VarFileSize = Math.round((input.files[0].size) / 1024);
			if(VarFileSize <= GBLUPLOADIMAGESIZE){
				var reader = new FileReader();			
				reader.onload = function (e) {
					$('#asset-logo-preview').attr('src', e.target.result);
					$('#asset-logo-preview').css('background-color','whitesmoke');
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

function FnGetAssetTypes(){

//	var VarUrl = GblAppContextPath+'/ajax' + VarListTemplateUrl + "?domain_name="+ VarCurrentTenantInfo.tenantDomain;
	//FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResAssetTypes);
	FnResAssetTypes(vehicleType)
}

function FnResAssetTypes(response){
	var ArrResponse = response;
	if($.isArray(ArrResponse)){
		var VarAssetTypesTxt = '<option value=""> Please select asset type </option>';
		$.each(ArrResponse,function(key,val){
			VarAssetTypesTxt += '<option value="'+val.assetType+'">'+val.assetType+'</option>';
		});
		$('#assetTypeList').html(VarAssetTypesTxt);
		$('#assetTypeList').val($("#assetType").val());
	}
	
}
function FnGetAssetFields(){
	var VarAssetType = $('#assetTypeList').val();
	if(VarAssetType != ''){
		var VarUrl = '/FMS/ajax/galaxy-am/1.0.0/assetType/{asset_type_name}?domain_name='+UserInfo.getCurrentDomain()+'&parent_type=VEHICLE';
		VarUrl = VarUrl.replace("{asset_type_name}",VarAssetType);
		FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResAssetFields);
	} else {
		$('#fieldsContainer').html('');
		$('#fieldsSection').hide();
	}
}

function FnResAssetFields(response){
	var ObjResponse = response.entity;
	if(!$.isEmptyObject(ObjResponse)){
		$('#fieldsSection').show();
		var VarAssetFieldTxt = '';
		var VarTabIndex = 4;
		$.each(ObjResponse.assetTypeValues,function(key,val){
			
			VarAssetFieldTxt += '<div class="form-group form-md-line-input col-md-4"><label class="col-md-12 control-label capitalize" for="'+val+'"> '+val+' </label><input type="text" class="form-control input-sm" name="'+val+'" id="'+val+'" tabindex="'+VarTabIndex+'"/><div class="form-control-focus"></div></div>';
			
			VarTabIndex++;
		});
		$('#fieldsContainer').html(VarAssetFieldTxt);
	} else {
		if(ArrResponse.errorCode != undefined){
			FnShowNotificationMessage(ArrResponse);
		}
	}

}

var GblAssetTypeId = '';
var GblEditAssetName = '';
function FnGetAssetDetails(){
	$("#GBL_loading").show();	
	FnResAssetDetails();
}

function FnResAssetDetails(){
	if($('#image').val() != ''){
		document.getElementById("asset-logo-preview").src = "data:image/png;base64," + $('#image').val();
	}
	$("#GBL_loading").hide();
	var assetTypeValues = $.parseJSON($('#assetTypeValues').val());
	if(!$.isEmptyObject(assetTypeValues)){
	
		//GblEditAssetName = ArrResponse['assetName'];
		//GblAssetTypeId = ArrResponse.assetType.identifier.value;
		var VarAssetFieldTxt = '';
		var VarTabIndex = 4;

		$.each(assetTypeValues,function(key,obj){
			if(obj['key'] != 'identifier'){
				var VarValue = (obj['value'] != undefined) ? obj['value'] : '';
				VarAssetFieldTxt += '<div class="form-group form-md-line-input col-md-4"><label class="col-md-12 control-label capitalize"  style="padding:0" for="'+obj['key']+'"> '+obj['key']+' </label><input type="text" class="form-control input-sm" name="'+obj['key']+'" id="'+obj['key']+'" tabindex="'+VarTabIndex+'" value="'+VarValue+'"/><div class="form-control-focus"></div></div>';
				
				VarTabIndex++;
			}
			else{
				GblAssetTypeId = obj['value'];
			}
		});
		
		$('#fieldsContainer').html(VarAssetFieldTxt);
		$('#fieldsSection').show();
		
		$("#gapp-asset-form :input").prop("disabled", true);
		$('#gapp-asset-save').attr('disabled',true);
		$("#gapp-asset-cancel").prop("disabled", false);
		
		//---Find geo-tag---//	
		$("#form-gapp-tag-management :input").prop("disabled", true);
		
		//$('#tag-domain-name').val(response.asset.domain.domainName);
		var key="identifier"
		$('#tag-identifier-key').val(key);
		$('#tag-identifier-value').val(VarVehicleId);
		//$('#tag-entity-template-name').val(response.asset.entityTemplate.entityTemplateName);
		

		FnResFindGeoTag(geoDetails);	
		FnResFindNameTags(tagResponse) // watever tags assigned to the vehicle when its created
		
		var ArrPoints = [];
		var ArrMappedPoints = pointList;
		points = pointList;
		if(ArrMappedPoints != undefined){
			$.each(ArrMappedPoints,function(){
				var element = {};
				$(this.dataprovider).each(function() {
					var key = this.key;
					var value = ((this.value != undefined)? this.value : "");
					element[key] = value;
				});
				element["identifier"] = this.identifier.value;
				element["status"] = this.entityStatus.statusName;
				ArrPoints.push(element);
			});
		}
		$("#vehicle_type").val($("#assetType").val());
		$("#vehicle_name").val($("#assetName").val());
		//FnDisplayAssetImage(ArrResponse['assetName']);
		//FnDisplayAssetImage(image);		
		FnResAssetPointList(pointList);
	}	
}


function FnResFindNameTags(response){
	var ArrResponse = response;
	var ArrResData = [];
	var name_tag_multiselect = $("#name-tag-placeholder").data("kendoMultiSelect");
	
	
	if($.isArray(ArrResponse)){
		var arrVal=[];
		$.each(ArrResponse,function(key,val){
			var ObjRole = {};
			$.each(val,function(key,val){ 
				console.log("key tags="+key+" Value tags="+val);
				if(key == 'name'){
					arrVal.push(val);	
				}
			});			
		});
		console.log(arrVal);
		name_tag_multiselect.value(arrVal);
		$('#name-tag-placeholder :selected').each(function(i, selected) {
			$("#name-tag-placeholder_listbox .k-item")[$(selected).index()].disabled = true;
			//$("#roleName_listbox .k-item").addClass("k-state-disabled");
			$(".k-multiselect-wrap.k-floatwrap").find('.k-icon.k-delete').remove();
			//$(".k-multiselect-wrap.k-floatwrap").find('.k-button').addClass("k-state-disabled");
			
		});
		
		$('#nametag-update-flag').val("true");
	}
	name_tag_multiselect.enable(false);			
}

function FnDisplayAssetImage(VarAssetName){
	
	document.getElementById("asset-logo-preview").src = "data:image/png;base64," + VarAssetName;
	
	$.ajax({
		url: "/FMS/downloadOneFile",
		type: "GET",
		processData: false,
		contentType: false,
		success:FnResImageUpload
	});	
//	var ObjImage = new Image;
//	ObjImage.src= GblAppContextPath + VarAppImagePath + "/" + VarCurrentTenantInfo.tenantDomain + VarAppAssetImagePath + "/" + VarAssetName+".png";
//	ObjImage.onload=function(){
//		$('#asset-logo-preview').attr('src',ObjImage.src);
//		$('#asset-logo-preview').css('background-color','whitesmoke');
//	}
//	ObjImage.onerror=function(){
//		//$('#asset-logo-preview').attr('src',GblAppContextPath + VarAppImagePath + VarAppDefaultImagePath + VarAppAssetImagePath + "/" + "assets.jpg");
//		$('#asset-logo-preview').attr('src','');
//	}
}

function FnCheckForUnitless(VarUnit){		
	if (VarUnit =='unitless') {VarUnit=' - '};
	return VarUnit;
}

function FnResAssetPointList(ArrPoints){
	if(ArrPoints!=undefined){
	if(ArrPoints.length > 0){
		$("#btn-asset-points").prop("disabled", false);
	}else{
		$("#btn-asset-points").prop("disabled", true);
	}
	}else{
		$("#btn-asset-points").prop("disabled", true);
	}
}

function FnGetAssetFieldValues(){
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
	
	return ArrFieldValuesJSONObj;
}

function FnSaveAsset(){
	$('#gapp-asset-save, #gapp-asset-cancel').attr('disabled',true);
	var validator = $("#gapp-asset-form").data("kendoValidator");
	validator.hideMessages();
	$("#GBL_loading").show();
	if (validator.validate()) {
			
		if(VarEditAssetId == ''){
			
			var VarGeoLatitude  = $('#tag-latitude').val();
			var VarGeoLongitude = $('#tag-longitude').val();
			
			$("#latitude").val(VarGeoLatitude);
			$("#longitude").val(VarGeoLongitude);
			
			if((VarGeoLatitude =="" && VarGeoLongitude=="") || (VarGeoLatitude =='null' && VarGeoLongitude=='null') || (undefined == VarGeoLatitude && undefined==VarGeoLongitude)){ 			 
			}else if(VarGeoLatitude !="" && VarGeoLongitude!=""){
				var VarMainParam = {
					"entity": {
						"domain": {
							"domainName": UserInfo.getCurrentDomain()
						},
						"entityTemplate": {
							"entityTemplateName": "Asset"
						}
					},
					"geotag": {
						"latitude": VarGeoLatitude,
						"longitude":  VarGeoLongitude
					}
				};
				$("#geoDetails").val(JSON.stringify(VarMainParam));
				} 	
			 if(undefined == $('#name-tag-placeholder').find('option:selected').val() || $('#name-tag-placeholder').find('option:selected').val()=='' || $('#name-tag-placeholder').find('option:selected').val() == 'null'){				
				 }else{
					 FnSaveNameTagDetails();
				 }		
			var assetTypeValues=FnGetAssetFieldValues();
			$("#assetTypeValues").val(JSON.stringify(assetTypeValues));
			$("#assetType").val($("#assetTypeList").val());
			$("#gapp-asset-form").submit();
			
			
		}
		 else {
			var vehicleName=$('#assetName').val();
			$("#vehicle_name").val(vehicleName);
			$("#vehicle_id").val(VarVehicleId) 
			var assetType=$('#assetType').val();
			$("#vehicle_type").val(assetType);
			$("#gblAssetTypeId").val(GblAssetTypeId);
			var assetTypeValues=FnGetAssetFieldValues();
			$("#assetTypeValues").val(JSON.stringify(assetTypeValues))
			
			//Update geo-tag
		var VarGeoLatitude  = $('#tag-latitude').val();
		var VarGeoLongitude = $('#tag-longitude').val();
		
		$("#latitude").val(VarGeoLatitude);
		$("#longitude").val(VarGeoLongitude);
		
		
		
		if((VarGeoLatitude =="" && VarGeoLongitude=="") || (VarGeoLatitude =='null' && VarGeoLongitude=='null') || (undefined == VarGeoLatitude && undefined==VarGeoLongitude)){}
		else if(VarGeoLatitude !="" && VarGeoLongitude!=""){
			var VarMainParam = {
				"entity": {
					"domain": {
						"domainName": UserInfo.getCurrentDomain()
					},
					"identifier": {
						"key": $('#tag-identifier-key').val(),
						"value": $('#tag-identifier-value').val()
					},
					"entityTemplate": {
						"entityTemplateName": "Asset"
					}
				},
				"geotag": {
					"latitude": VarGeoLatitude,
					"longitude":  VarGeoLongitude
				}
			};
			$("#geoDetails").val(JSON.stringify(VarMainParam));		
		}		
		 if(undefined == $('#name-tag-placeholder').find('option:selected').val() || $('#name-tag-placeholder').find('option:selected').val()=='' || $('#name-tag-placeholder').find('option:selected').val() == 'null'){
			 }else{
				  FnUpdateNameTagDetails();
			 }
		 $("#assetType").val($("#assetTypeList").val());
		 $("#gapp-asset-form").submit();		
		 }
	}
	else {
		$('#gapp-asset-save, #gapp-asset-cancel').attr('disabled',false);
		$("#GBL_loading").hide();
		var errors = validator.errors();
		console.log(errors);
		return false;
	}
}
var GblObjUserResponse;
function FnResSaveAsset(response){
	var ObjResponse = response;
	GblObjUserResponse = response;
	$('#gapp-asset-save, #gapp-asset-cancel').attr('disabled',false);
	$("#GBL_loading").hide();
	if(!$.isEmptyObject(ObjResponse)){
	
		if(ObjResponse.errorCode == undefined){
		
			var ObjImageFile = $("#asset-logo").prop("files")[0];
			if(!$.isEmptyObject(ObjImageFile)){ // Upload Asset Image
			
				var fd = new FormData();
				fd.append("asset-logo", ObjImageFile);
				fd.append("assetName", $('#assetName').val());
				fd.append("gapp-tenant-domain", $('#gapp-tenant-domain').val());
				
				$.ajax({
					url: GblAppContextPath+"/upload/asset",
					type: "POST",
					processData: false,
					contentType: false,
					enctype: 'multipart/form-data',
					data: fd,
					success:FnResImageUpload
				});	
			
			}
			
		
			/*
			 *------------START: Save geo tag ------------*
			 */
			var ObjResForGeoTag = response;
			var VarGeoLatitude  = $('#tag-latitude').val();
			var VarGeoLongitude = $('#tag-longitude').val();
			
			//alert(VarGeoLatitude);
			//alert(VarGeoLongitude);
			
			if((VarGeoLatitude =="" && VarGeoLongitude=="") || (VarGeoLatitude =='null' && VarGeoLongitude=='null') || (undefined == VarGeoLatitude && undefined==VarGeoLongitude)){ //no geo tag
				 if(undefined == $('#name-tag-placeholder').find('option:selected').val() || $('#name-tag-placeholder').find('option:selected').val()=='' || $('#name-tag-placeholder').find('option:selected').val() == 'null'){
					//No Name tags
					notificationMsg.show({
					message : 'Asset created successfully.'
					}, 'success');

					FnFormRedirect('gapp-asset-list',GBLDelayTime);
					
				 }else{
					 FnSaveNameTagDetails(GblObjUserResponse);
				 }
				 
			}else if(VarGeoLatitude !="" && VarGeoLongitude!=""){
				var VarUrl = GblAppContextPath+'/ajax' + VarCreateGeoTagUrl;
				var VarMainParam = {
					"entity": {
						"domain": {
							"domainName": ObjResForGeoTag.domain.domainName
						},
						"identifier": {
							"key": ObjResForGeoTag.identifier.key,
							"value": ObjResForGeoTag.identifier.value
						},
						"entityTemplate": {
							"entityTemplateName": ObjResForGeoTag.entityTemplate.entityTemplateName
						}
					},
					"geotag": {
						"latitude": VarGeoLatitude,
						"longitude":  VarGeoLongitude
					}
				};
				//FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResCreateGeoTag);
				FnMakeAjaxRequest(VarUrl, 'POST', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResCreateGeoTag);
				
			} 
		
		
		
				
		} else {
			FnShowNotificationMessage(ObjResponse);
		}
	
	} else {
		
		notificationMsg.show({
			message : 'Error'
		}, 'error');
		
	}
	
}

function FnResCreateGeoTag(response){
	var ObjResForGeoTag = response;
	if(!$.isEmptyObject(ObjResForGeoTag)){
		if(ObjResForGeoTag.errorCode == undefined){
			
			 if(undefined == $('#name-tag-placeholder').find('option:selected').val() || $('#name-tag-placeholder').find('option:selected').val()=='' || $('#name-tag-placeholder').find('option:selected').val() == 'null'){
			 
				notificationMsg.show({
					message : 'Asset created and tagged successfully.'
				}, 'success');
		
				FnFormRedirect('gapp-asset-list',GBLDelayTime);
				
			 }else{
				FnSaveNameTagDetails(GblObjUserResponse);
			 }	
			
			
			
			

		}else{
			
			if(undefined == $('#name-tag-placeholder').find('option:selected').val() || $('#name-tag-placeholder').find('option:selected').val()=='' || $('#name-tag-placeholder').find('option:selected').val() == 'null'){
				notificationMsg.show({
					message : 'Asset created successfully.'
				}, 'success');
		
				
				notificationMsg.show({
					message : ObjResForGeoTag['errorMessage']
				}, 'error');
				
				FnFormRedirect('gapp-asset-list',GBLDelayTime);
			} else{
				
				notificationMsg.show({
					message : ObjResForGeoTag['errorMessage']
				}, 'error');
				
				FnSaveNameTagDetails(GblObjUserResponse);
				
			}
			
			
		}
	}
}

function FnSaveNameTagDetails(){
	//var ObjResponse = GblObjUserResponse
	var roles = $("#name-tag-placeholder").data("kendoMultiSelect");
	var ArrFieldValuesJSONObj = [];
	var tagArray=[];
	
	$('#name-tag-placeholder :selected').each(function(i, selected) {
		tagArray.push(roles.dataItem(selected.index).name);
		var ObjFieldMap = {};
		ObjFieldMap["domain"] = {"domainName": UserInfo.getCurrentDomain()};
		ObjFieldMap["tagType"] = roles.dataItem(selected.index).tagType;
		ObjFieldMap["name"] = roles.dataItem(selected.index).name;
		console.log(ArrFieldValuesJSONObj);
		ArrFieldValuesJSONObj.push(ObjFieldMap);
		
	});
	
	$("#tagArray").val(JSON.stringify(tagArray));
	
	var VarMainParam = {
	  "tags": ArrFieldValuesJSONObj,
	   "entity": 
			{
			  
			  "platformEntity": {
			  "platformEntityType": "MARKER"
				},
			  "entityTemplate": {
				"entityTemplateName": "Asset"
			},
			"domain": {
			      "domainName": UserInfo.getCurrentDomain()
		    },			
//			  "identifier": {
//				"key": ObjResponse.identifier.key,
//				"value": ObjResponse.identifier.value
//			  }
			}
		 
	};
	///galaxy-tags/1.0.0/tags/actors		
	$("#tagDetails").val(JSON.stringify(VarMainParam))		
  // var VarUrl = GblAppContextPath+'/ajax' + VarMapTagsEntities;		

   //FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResNameTagDetails);
		
}

			
		
	

function FnResNameTagDetails(ObjResponse){
	
	if(!$.isEmptyObject(ObjResponse)){
		
		if(ObjResponse.status.status == 'SUCCESS'){
				notificationMsg.show({
					message : 'Asset created and tagged successfully.'
				}, 'success');
		
				FnFormRedirect('gapp-asset-list',GBLDelayTime);
	
		}else {
			notificationMsg.show({
			message : 'Error in tagging'
			}, 'error');
		}
	}else{
		notificationMsg.show({
			message : 'Error in tagging'
		}, 'error');
	}
	//alert(response);
	/*
	var response = "";
	
	{
		"generalResponse": [   {
		"reference": "Tag",
		"status": "FAILURE",
		"remarks": "Tag entity with identifier : 58d8e777-cad6-4162-9f07-b3dc832cf024, does not have template :Asset tagged."
		}],
		"status": {"status": "SUCCESS"}
	}
	*/

}

function FnResImageUpload(response,status){
	alert(response);
	$('#asset-logo').val('');
}

function FnResUpdateAsset(response){

//	var ArrResponse = response;
//	$('#gapp-asset-save, #gapp-asset-cancel').attr('disabled',false);
//	
//	if(ArrResponse.status == 'SUCCESS'){	
//		var ObjImageFile = $("#asset-logo").prop("files")[0];
//		if(!$.isEmptyObject(ObjImageFile)){ // Upload Asset Image
//		
//			var fd = new FormData();
//			fd.append("asset-logo", ObjImageFile);
//			fd.append("assetName", $('#assetName').val());
//			fd.append("gapp-tenant-domain", $('#gapp-tenant-domain').val());
//			
//			$.ajax({
//				url: GblAppContextPath+"/upload/asset",
//				type: "POST",
//				processData: false,
//				contentType: false,
//				enctype: 'multipart/form-data',
//				data: fd,
//				success:FnResImageUpload
//			});	
//		
//		} else if(GblEditAssetName != FnTrim($('#assetName').val()) && $('#asset-logo-preview').attr('src') != ''){
//			
//			var fd = new FormData();
//			fd.append("assetName", $('#assetName').val());
//			fd.append("editassetName", GblEditAssetName);
//			fd.append("gapp-tenant-domain", $('#gapp-tenant-domain').val());
//			$.ajax({
//				url: GblAppContextPath+"/upload/asset",
//				type: "POST",
//				processData: false,
//				contentType: false,
//				enctype: 'multipart/form-data',
//				data: fd,
//				success:FnResImageUpload
//			});
//		}			
//		
//			if($('#tag_update_flag').val() == 'true'){
//				FnMakeAjaxRequest(VarUrl, 'PUT', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResUpdateGeoTag);
//			}else{
//				FnMakeAjaxRequest(VarUrl, 'POST', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResUpdateGeoTag);
//			}      
//			
//		} else {
//			notificationMsg.show({
//				message : 'Asset updated successfully'
//			}, 'success');
//			
//			FnFormRedirect('gapp-asset-list',GBLDelayTime);
//		}	
//		
//	} else {
//		notificationMsg.show({
//			message : ArrResponse['errorMessage']
//		}, 'error');
//	}
//
//	$("#GBL_loading").hide();
}

function FnResUpdateGeoTag(response){
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		if(ObjResponse.errorCode == undefined){
			notificationMsg.show({
				message : 'Asset and tag details updated successfully'
			}, 'success');
			
			FnFormRedirect('gapp-asset-list',GBLDelayTime);

		}else{
			notificationMsg.show({
				message : 'Asset updated successfully'
			}, 'success');
			
			notificationMsg.show({
				message : ObjResponse['errorMessage']
			}, 'error');
			
			FnFormRedirect('gapp-asset-list',GBLDelayTime);
		}
	}
}




function FnCancelAsset(){
		
//	if(GBLcancel > 0){
//		$('#GBLModalcancel #hiddencancelFrm').val('gapp-asset-list');
//		$('#GBLModalcancel').modal('show');
//	} else {
		$('#gapp-asset-list').submit();
//	}
}

function FnNavigateAssetList(){
	$('#gapp-asset-list').submit();
}

function FnResFindGeoTag(response){
	var ObjResponse = response;
	if(!$.isEmptyObject(ObjResponse)){
		if(ObjResponse.errorCode == undefined){
			//console.log(ObjResponse);
			$('#tag-latitude-longitude-display-value').html(ObjResponse.geotag.latitude+' , '+ObjResponse.geotag.longitude);
			$('#gllpLatitude').val(ObjResponse.geotag.latitude);
			$('#gllpLongitude').val(ObjResponse.geotag.longitude);
			var VarLatitude = parseFloat($('#gllpLatitude').val());
			var VarLongitude = parseFloat($('#gllpLongitude').val());
			var object={}
			object["latlng"]= {"lat" : VarLatitude,"lng":VarLongitude};
			// console.log(object);
			var VarIcon = FnGetMarkerIcon('blue');
			var geomarker=L.marker(object.latlng,{icon: VarIcon, draggable: true}).addTo(map);
			ArrPolyMarkers.push(geomarker);	
			geomarker.on("drag", FnDragEvent);
			var c = object.latlng;	
			var fc = ArrPolyMarkers[0].getLatLng();		
			var dis = fc.distanceTo(c);
			clickCircle = L.circle(fc, dis, null).addTo(map);
			map.setView(new L.LatLng(VarLatitude, VarLongitude),4);
			$('#tag_update_flag').val("true");
			
		}
	}
}

function FnEditAsset(){
	$("#gapp-asset-form :input").prop("disabled", false);
	$("#gapp-asset-save").prop("disabled", false);
	$('#assetName').prop("disabled", true);
	$('#assetTypeList').prop("disabled", true);
	//$('#assetName').prop("disabled", true);
	$('.pageTitleTxt').text('Edit Vehicle');
	$('#gapp-asset-edit').hide();
	
	//geo tag: label change // name-tag enable
	$("#form-gapp-tag-management :input").prop("disabled", false);
	if($('#tag_update_flag').val() == 'true'){
		$('#geo-tag-label').html('Update Tag');
	}
	
	var name_tags = $("#name-tag-placeholder").data("kendoMultiSelect");
	name_tags.enable(true);
	FnResAssetPointList(points);
}


function FnUpdateNameTagDetails(){

	var VarDataCheckFlag = false;
	var roles = $("#name-tag-placeholder").data("kendoMultiSelect");
	var ArrFieldValuesJSONObj = [];
	var tagArray=[];
	$('#name-tag-placeholder :selected').each(function(i, selected) {
		tagArray.push(roles.dataItem(selected.index).name);
		if(undefined === $("#name-tag-placeholder_listbox .k-item")[$(selected).index()].disabled){
			VarDataCheckFlag = true;
			var ObjFieldMap = {};
			ObjFieldMap["domain"] = {"domainName": UserInfo.getCurrentDomain()};
			ObjFieldMap["tagType"] = roles.dataItem(selected.index).tagType;
			ObjFieldMap["name"] = roles.dataItem(selected.index).name;
			ArrFieldValuesJSONObj.push(ObjFieldMap);
		}	
	});
	
	$("#tagArray").val(JSON.stringify(tagArray));
	
	if(VarDataCheckFlag){
		var VarMainParam = {
			"tags": ArrFieldValuesJSONObj,
			"entity": 
				{
				  
				  "platformEntity": {
				  "platformEntityType": "MARKER"
					},
				  "domain": {
					"domainName": UserInfo.getCurrentDomain()
				  },
				  "entityTemplate": {
					"entityTemplateName": "Asset"
				},
				  "identifier": {
					"key": $('#tag-identifier-key').val(),
					"value": $('#tag-identifier-value').val()
				  }
				}		 
		 
		};
		$("#tagDetails").val(JSON.stringify(VarMainParam))			
	}else{
		$("#tagDetails").val('')
	}

}
function FnShowAssetPointDetails(){
	var ArrMappedPoints = points;
	var selectedPoints = JSON.stringify(ArrMappedPoints); 
	//var varIdentifier = "dsds"; 
	var ObjTenantInfo = {};
	ObjTenantInfo['selectedPoints'] = ArrMappedPoints;
	ObjTenantInfo['assetId'] = VarVehicleId;
	console.log("ObjTenantInfo"+VarVehicleId);
	$('#selectedPoints').val(JSON.stringify(ObjTenantInfo));
	$('#point_list').submit();
}

function FnReDrawFields(){
	
	if(varOnErrLat!=undefined && varOnErrLng!=undefined){
		$('#tag-latitude-longitude-display-value').html(varOnErrLat+' , '+varOnErrLng);
		$('#gllpLatitude').val(varOnErrLat);
		$('#gllpLongitude').val(varOnErrLng);
		var VarLatitude = parseFloat($('#gllpLatitude').val());
		var VarLongitude = parseFloat($('#gllpLongitude').val());
		var object={}
		object["latlng"]= {"lat" : VarLatitude,"lng":VarLongitude};
		// console.log(object);
		var VarIcon = FnGetMarkerIcon('blue');
		var geomarker=L.marker(object.latlng,{icon: VarIcon, draggable: true}).addTo(map);
		ArrPolyMarkers.push(geomarker);	
		geomarker.on("drag", FnDragEvent);
		var c = object.latlng;	
		var fc = ArrPolyMarkers[0].getLatLng();		
		var dis = fc.distanceTo(c);
		clickCircle = L.circle(fc, dis, null).addTo(map);
		map.setView(new L.LatLng(VarLatitude, VarLongitude),4);
		$('#tag_update_flag').val("true");
	}
	
	if(varTagArray!=''){
		
		var name_tag_multiselect = $("#name-tag-placeholder").data("kendoMultiSelect");
		name_tag_multiselect.value(varTagArray);
		$('#name-tag-placeholder :selected').each(function(i, selected) {
			$("#name-tag-placeholder_listbox .k-item")[$(selected).index()].disabled = true;
			//$("#roleName_listbox .k-item").addClass("k-state-disabled");
			$(".k-multiselect-wrap.k-floatwrap").find('.k-icon.k-delete').remove();
			//$(".k-multiselect-wrap.k-floatwrap").find('.k-button').addClass("k-state-disabled");
			
		});
		
		$('#nametag-update-flag').val("true");
		name_tag_multiselect.enable(false);		
	}
	
	
	var assetTypeValues = $.parseJSON($('#assetTypeValues').val());
	if(!$.isEmptyObject(assetTypeValues)){
	
		//GblEditAssetName = ArrResponse['assetName'];
		//GblAssetTypeId = ArrResponse.assetType.identifier.value;
		var VarAssetFieldTxt = '';
		var VarTabIndex = 4;

		$.each(assetTypeValues,function(key,obj){
			if(obj['key'] != 'identifier'){
				var VarValue = (obj['value'] != undefined) ? obj['value'] : '';
				VarAssetFieldTxt += '<div class="form-group form-md-line-input col-md-4"><label class="col-md-12 control-label capitalize"  style="padding:0" for="'+obj['key']+'"> '+obj['key']+' </label><input type="text" class="form-control input-sm" name="'+obj['key']+'" id="'+obj['key']+'" tabindex="'+VarTabIndex+'" value="'+VarValue+'"/><div class="form-control-focus"></div></div>';
				
				VarTabIndex++;
			}
			else{
				GblAssetTypeId = obj['value'];
			}
		});
	
	}
	$('#fieldsContainer').html(VarAssetFieldTxt);
	$('#fieldsSection').show();
	
}

