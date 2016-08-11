$(document)
		.ready(
				function() {
					$('#fieldsSection').hide();
					$('#fContainer').hide();

					FnGetPoiTypes();
					$('#poi-def-save').prop('disabled', true);
					$('#radius').on('blur', function() {
						$('#poi-def-save').prop('disabled', false);
					});

					$('#deviceLocModel').click(function() {
						$('#draggable').modal('show');
						setTimeout(function() {
							$(".gllpLatlonPicker").each(function() {
								$(document).gMapsLatLonPicker().init($(this));
							});

						}, 1000);

					});

					if (poiAction == 'create' || poiAction == 'createerror') {
						$('#poidef-delete').hide();
						$('#poidef-edit').hide();
						$('#poi-def-save').prop('disabled', false);

					} else if(poiAction == 'editerror'){
							$('#poidef-edit').hide();
							$("#poi-def-save").prop("disabled", false);
							$('#poiName').prop("disabled", true);
							$('#poiTypeDummy').prop("disabled", true);
							$('.pageTitleTxt').text('Edit POI');
					} else {
						$("#poi_view :input").prop("disabled", true);
						$("#poi-def-save").prop("disabled", true);
						$('#poidef-delete').show();
					}
					FnDisplayAssetImage()
					// Form Validation
					var VarEditAssetId = '';
					$("#gapp-asset-form")
							.kendoValidator(
									{
										validateOnBlur : true,
										errorTemplate : "<span class='help-block'><code>#=message#</code></span>",
										rules : {
											available : function(input) {
												var validate = input
														.data('available');
												var VarExist = true;
												if (typeof validate !== 'undefined'
														&& validate !== false) {
													var VarThisFieldValue = FnTrim(input
															.val());
													if (VarEditAssetId != ''
															&& VarThisFieldValue === GblEditAssetName) {
														return VarExist;
													}

													var url = input
															.data('available-url');
													var VarParam = {
														"domain" : {
															"domainName" : VarCurrentTenantInfo.tenantDomain
														},
														"fieldValues" : [ {
															"key" : "poiName",
															"value" : VarThisFieldValue
														} ]
													};
													$
															.ajax({
																type : 'POST',
																cache : true,
																async : false,
																contentType : 'application/json; charset=utf-8',
																url : GblAppContextPath
																		+ "/ajax"
																		+ url,
																data : JSON
																		.stringify(VarParam),
																dataType : 'json',
																success : function(
																		result) {
																	var ObjExistStatus = result;
																	if (ObjExistStatus.status == 'SUCCESS') { // Does
																												// not
																												// exist
																												// in
																												// db
																		VarExist = true;
																	} else if (ObjExistStatus.status == 'FAILURE') { // Exist
																														// in
																														// db
																		VarExist = false;
																	}
																},
																error : function(
																		xhr,
																		status,
																		error) {

																}
															});

												}
												return VarExist;
											}
										},
										messages : {
											available : function(input) {
												return input
														.data("available-msg");
											}
										}

									});

					$("#poi-logo").change(function() {
						readURL(this, $(this).attr('id'));
					});
				});


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
					$('#poi-image-preview').attr('src', e.target.result);
					$('#poi-image-preview').css('background-color', 'whitesmoke');
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

function FnGetPoiTypes() {
	// alert(UserInfo.getTenant().currentDomain);
	var VarUrl = 'ajax' + getPOITypeList + '?domain_name='
			+ UserInfo.getCurrentDomain();
	// console.log('ll >'+VarUrl);
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8',
			'json', FnResPoiTypes);
}

function FnResPoiTypes(response) {
	if ($('#image').val() != '') {
		document.getElementById("poi-image-preview").src = "data:image/png;base64,"
				+ $('#image').val();
	}
	// console.log(response);
	var ArrResponse = response.entity;
	if (!$.isEmptyObject(ArrResponse)) {

		if ($.isArray(ArrResponse)) {
			var VarPoiTypesTxt = '<option value=""> Please select POI type </option>';
			$.each(ArrResponse, function(key, val) {
				VarPoiTypesTxt += '<option value="' + val.poiType + '">'
						+ val.poiType + '</option>';
			});
			$('#poiTypeDummy').html(VarPoiTypesTxt);
		}
		if ($('#poiType').val() != '') {
			$('#poiTypeDummy').val($('#poiType').val());
			FnSetPoiFields($('#poiTypeValues').val());
		} else {
			FnGetAssetFields();
		}
	}
}

/* create poi : POI type change dropdown */
function FnGetAssetFields() {
	var VarAssetType = $('#poiTypeDummy').val();
	if (VarAssetType != '') {
		var VarUrl = 'ajax' + getPOIType + '?domain_name='
				+ UserInfo.getCurrentDomain();
		VarUrl = VarUrl.replace("{poi_type_name}", VarAssetType);
		FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8',
				'json', FnResAssetFields);
	} else {
		$('#fieldsContainer').html('');
		$('#fieldsSection').hide();
	}
}

function FnResAssetFields(response) {
	var ObjResponse = response.entity;
	if (!$.isEmptyObject(ObjResponse)) {
		$('#fieldsSection').show();
		var VarAssetFieldTxt = '';
		var VarTabIndex = 4;
		$
				.each(
						ObjResponse.poiTypeValues,
						function(key, val) {

							VarAssetFieldTxt += '<div class="form-group form-md-line-input col-md-6"><label  style="padding:0" class="col-md-12 control-label capitalize" for="'
									+ val
									+ '"> '
									+ val
									+ ' </label> <input type="text" class="form-control input-sm" name="'
									+ val
									+ '" id="'
									+ val
									+ '" tabindex="'
									+ VarTabIndex
									+ '"/><div class="form-control-focus"></div></div>';

							VarTabIndex++;
						});
		$('#fieldsContainer').html(VarAssetFieldTxt);
	} else {
		if (ArrResponse.errorCode != undefined) {
			FnShowNotificationMessage(ArrResponse);
		}
	}
}

function FnSetPoiFields(response) {
	var ObjResponse = $.parseJSON(response);
	var disabled = "";
	if(poiAction == 'edit'){
		disabled = "disabled";
	}
	if (!$.isEmptyObject(ObjResponse)) {
		$('#fieldsSection').show();
		var VarAssetFieldTxt = '';
		var VarTabIndex = 4;
		$
				.each(
						ObjResponse,
						function(key, val) {
							var value = '';
							if (val.value != undefined) {
								value = val.value;
							}
							VarAssetFieldTxt += '<div class="form-group form-md-line-input col-md-6"><label class="col-md-12 control-label capitalize" style="padding:0" for="'
									+ val.key
									+ '"> '
									+ val.key
									+ ' </label><input type="text" class="form-control input-sm" name="'
									+ val.key
									+ '" id="'
									+ val.key
									+ '" value="'
									+ value
									+ '" tabindex="'
									+ VarTabIndex
									+ '" ' 
									+ disabled + ' /><div class="form-control-focus"></div></div>';

							VarTabIndex++;
						});
		$('#fieldsContainer').html(VarAssetFieldTxt);
	} else {
		if (ArrResponse.errorCode != undefined) {
			FnShowNotificationMessage(ArrResponse);
		}
	}
}

function FnDisplayAssetImage(VarAssetName) {
	var ObjImage = new Image;
	ObjImage.src = "http://10.234.60.61:8080/FMS/resources/images/user2-160x160.jpg";
	console.log(ObjImage.src);
	//

	ObjImage.onload = function() {
		$('#poi-img-preview').attr('src', ObjImage.src);
		$('#poi-img-preview').css('background-color', 'whitesmoke');
	}
	ObjImage.onerror = function() {
		// $('#poi-logo-preview').attr('src',GblAppContextPath + VarAppImagePath
		// + VarAppDefaultImagePath + VarAppAssetImagePath + "/" +
		// "assets.jpg");
		$('#poi-img-preview').attr('src', '');
	}
}

function FnCheckForUnitless(VarUnit) {
	if (VarUnit == 'unitless') {
		VarUnit = ' - '
	}
	return VarUnit;
}

function FnGetAssetFieldValues() {
	var ArrFieldValuesJSONObj = [];
	$('#fieldsContainer').find('input[type=text]').each(function() {
		var ObjFieldMap = {};
		var VarFieldName = $(this).attr('name');
		var VarFieldValue = FnTrim($(this).val());
		if (!(typeof VarFieldValue === "undefined")) {
			if ($(this).is("[type='text']")) {
				ObjFieldMap["key"] = VarFieldName;
				ObjFieldMap["value"] = VarFieldValue;
			}
			ArrFieldValuesJSONObj.push(ObjFieldMap);
		}
	});

	return ArrFieldValuesJSONObj;
}

function FnSavePoi() {
	setFormValidation();
	var validator = $("#poi_view").kendoValidator({errorTemplate: "<span class='help-block'><code>#=message#</code></span>"}).data("kendoValidator"), status = $(".status");
	$('#domainName').val(UserInfo.getCurrentDomain());
	$('#poiNameSub').val($('#poiName').val());
	$('#poiTypeValues').val(JSON.stringify(FnGetAssetFieldValues()));
	$('#poiType').val($('#poiTypeDummy').val());

	if (poiAction == 'create') {
		if (validator.validate()) {
			$('#poi_view').submit();
		}
	} else {
		if (validator.validate()) {
			$('#poi_view').submit();
		}
	}
}

function FnResImageUpload(response, status) {
	$('#poi-logo').val('');
}

function FnCancelPOI() {
	$('#poi_cancel').submit();
}

function FnNavigateAssetList() {
	$('#gapp-asset-list').submit();
}

function FnEditPoi() {
	$("#poi_view :input").prop("disabled", false);
	$("#fieldsContainer :input").prop("disabled", false);
	$('#poidef-edit').hide();
	$("#poi-def-save").prop("disabled", false);
	$('#poiName').prop("disabled", true);
	$('#poiTypeDummy').prop("disabled", true);
	$('.pageTitleTxt').text('Edit POI');
}


function FnDeletePoi() {
	$('#domain').val(UserInfo.getCurrentDomain());
	$('#identifier').val($('#poiIdentifier').val());
	$('#poiNameTemp').val($('#poiName').val());

	$('#delete-poi-def').modal('show');
	$('#btnYes').on('click', function(e) {
		var VarActionID = $(this).attr("data-value");
		if (VarActionID == 1) {			
			$('#poi_delete').submit();
		} else {
			// alert('canceled');
		}
		$('#delete-poi-def').modal('hide');
	});

	/*
	 * $('#domain').val(UserInfo.getCurrentDomain());
	 * $('#identifier').val($('#poiIdentifier').val());
	 * $('#poiNameTemp').val($('#poiName').val()); $('#poi_delete').submit();
	 */
}

function setFormValidation() {
	// Form validation
	//alert(0);

	// $("#poiName").attr('mandatory', 'poiName Name is required');

	$("#poi_view")
			.kendoValidator(
					{
						validateOnBlur : true,
						errorTemplate: "<span class='help-block'><code>#=message#</code></span>",
						rules : {
							mandatory : function(input) {
								if (input.attr('id') == 'poiName'
										&& (input.val() == '' || input.val()
												.trim() == "")) {
									return false;
								}
								return true;

							}
						},
						message : {
							mandatory : function(input) {
								return input.attr("mandatory");
							}
						}
					});
}
