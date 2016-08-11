"use strict";

$(document).ready(function(){

	$('#gapp-role-save').prop('disabled', true);
	FnCheckEmptyFrmFields('#roleName', '#gapp-role-save');	
	// Form Validation
	$("#gapp-addrole-form").kendoValidator({
											validateOnBlur : true,
											errorTemplate: "<span class='help-block'><code>#=message#</code></span>"
	});
	
	$('.portlet > .portlet-title > .tools > .collapse, .portlet .portlet-title > .tools').removeClass("expand").addClass("collapse");
	
	$('body').on('click', '.portlet > .portlet-title > .tools > .collapse, .portlet .portlet-title > .tools > .expand', function(e) {
		e.preventDefault();
		//alert('click');
		var el = $(this).closest(".portlet").children(".portlet-body");
		if ($(this).hasClass("collapse")) {
			$(this).removeClass("collapse").addClass("expand");
			el.slideUp(200);
		} else {
			$(this).removeClass("expand").addClass("collapse");
			el.slideDown(200);
		}
	});
	
});

function FnSaveRole(){
	$('#gapp-role-save, #gapp-role-cancel').attr('disabled',true);
	var validator = $("#gapp-addrole-form").data("kendoValidator");
	validator.hideMessages();
	//kendo.ui.progress($("#gapp-addrole-form"), true);
	$("#GBL_loading").show();
	if (validator.validate()) {
		
		if(VarEditRoleId == "null"){ // Create Role
		
			var SaveRoleUrl = 'ajax/'+ getSaveRoleUrl;
			var VarParam = {};
			VarParam['roleName'] = FnTrim($('#roleName').val());
			VarParam['Description'] = FnTrim($('#Description').val());
			VarParam['domainName'] = domainName;
			VarParam['resources'] = FnGetResourcePermissionsList();
			try{
			FnMakeAsyncAjaxRequest(SaveRoleUrl, 'POST', JSON.stringify(VarParam), 'application/json; charset=utf-8', 'json', FnResSaveRole);
			}catch(err){
				alert("An Error has occurred")
			}
		} else { // Update Role
			var EditRoleUrl = 'ajax/' + VarViewRoleUrl;
			EditRoleUrl = EditRoleUrl.replace("{role_name}",VarEditRoleId);
			EditRoleUrl = EditRoleUrl.replace("{domain_name}",domainName);
			var VarParam = {};
			VarParam['Description'] = FnTrim($('#Description').val());
			VarParam['resources'] = FnGetResourcePermissionsList();
			//console.log(JSON.stringify(VarParam));
			//return false;
			FnMakeAsyncAjaxRequest(EditRoleUrl, 'PUT', JSON.stringify(VarParam), 'application/json; charset=utf-8', 'json', FnResUpdateRole);
		
		}
		
	} else {
		$('#gapp-role-save, #gapp-role-cancel').attr('disabled',false);
		//kendo.ui.progress($("#gapp-addrole-form"), false);
		$("#GBL_loading").hide();
		var errors = validator.errors();
		console.log(errors);
		return false;
	}
}

function FnResSaveRole(response){
	var ArrResponse = response.entity;
	$('#gapp-role-save, #gapp-role-cancel').attr('disabled',false);
	$("#GBL_loading").hide();
	$('#gapp-view-role-form').submit();
	
}

function FnResUpdateRole(response){
	var ArrResponse = response.entity;
	$('#gapp-role-save, #gapp-role-cancel').attr('disabled',false);
	$("#GBL_loading").hide();
	$('#gapp-view-role-form').submit();
}

function FnCancelRole(){
		$('#gapp-view-role-form').submit();
	// if(GBLcancel > 0){
		// $('#GBLModalcancel #hiddencancelFrm').val('gapp-role-list');
		// $('#GBLModalcancel').modal('show');
	// } else {
		// $('#gapp-role-list').submit();
	// }
}

function FnNavigateRoleList(){
	$('#gapp-role-list').submit();
}

function FnGetResourcesList(){
	var VarUrl = 'ajax/galaxy-um/1.0.0/resources?domain_name=' +UserInfo.getCurrentDomain();
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResResourcesList);
}

var ObjResIdentifier = {};
function FnResResourcesList(response){
	var ArrResponse = response.entity;
	var VarResourceTxt = '';
	if(ArrResponse.resources != undefined && $.isArray(ArrResponse.resources)){
		var ArrResources = ArrResponse.resources;
		var VarResLength = ArrResources.length;
		
		if(VarResLength > 0){
			var VarColCnt = Math.ceil(VarResLength/3);
			
			var i=0;
			VarResourceTxt += "<div class='splitpermissionDIV'>";
		$.each(
							ArrResources,
							function(key, val) {
								val = val.toLowerCase();
								var VarResourceCustomTxt = val.replace(/_/g,
										" ");
								VarResourceTxt += '<div class="permission-wrapper resorceCon" id="resorceCon_'
										+ key
										+ '"><div class="box box-solid box-primary collapsed-box" ><div class="box-header with-border"><i class="fa fa-lock" aria-hidden="true"></i><h3 class="box-title"><span class="resourceTitle" id="resourceTitle_'
										+ key
										+ '" data-resname="'
										+ val
										+ '">'
										+ VarResourceCustomTxt
										+ '</span></h3> <div class="box-tools pull-right">  <button class="btn btn-box-tool" data-widget="collapse"  href="Javascript:void(0)" class="expand"   title="" data-original-title=""onclick="FnGetResourcePermissions(\''+ val+'\')"><i class="fa fa-plus"></i></button> '
										
										+ '</button></div></div> <div class="box-body form resourcePermissions" id="resourcePermissions_'
										+ key + '"></div></div></div>';

								if (i != 0 && ((i+1) % VarColCnt) == 0) {
									VarResourceTxt += "</div>";
									VarResourceTxt += "<div class='splitpermissionDIV'>";
								} else if (i == VarResLength - 1) {
									VarResourceTxt += "</div>";
								}
								
								if (ObjResIdentifier[val] == undefined) {
									ObjResIdentifier[val] = {};
									ObjResIdentifier[val]['resPer'] = [];
								}
								ObjResIdentifier[val]['resCon'] = 'resourcePermissions_'
										+ key;
								ObjResIdentifier[val]['resFlag'] = 0;
								i++;

							});	
			
		} else {
			if(ArrResponse.errorCode != undefined){
				FnShowNotificationMessage(ArrResponse);
			}
		}
	} else {
		if(ArrResponse.errorCode != undefined){
			FnShowNotificationMessage(ArrResponse);
		}
	}
	$('#permissionsContainer').html(VarResourceTxt);
}

function FnGetResourcePermissions(VarResourceName){
	if(ObjResIdentifier[VarResourceName] != undefined){
		if(ObjResIdentifier[VarResourceName]['resFlag'] == 0){
			var VarPermissionsUrl = 'ajax/galaxy-um/1.0.0/resources/{resource_name}/permissions?domain_name={domain_name}';
			var VarUrl = VarPermissionsUrl;
			VarUrl = VarUrl.replace("{resource_name}",VarResourceName);
			VarUrl = VarUrl.replace("{domain_name}",UserInfo.getCurrentDomain());
			FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResResourcePermissions);
		} else {
			$('#permissionsContainer #'+ObjResIdentifier[VarResourceName]['resCon']).css('display','block');
		}
		
	} else {
	
		notificationMsg.show({
			message : 'Error'
		}, 'error');
	
	}
	
}

function FnResResourcePermissions(response){
	var ArrResponse = response.entity;
	var VarResourceName = ArrResponse.resourceName;
	var VarPermissionsContainer = ObjResIdentifier[VarResourceName]['resCon'];
	ObjResIdentifier[VarResourceName]['resFlag'] = 1;
	var VarPermissionsTxt = '';
	VarPermissionsTxt += '<div class="md-checkbox-list checkbox-inner-padding">';
	$.each(ArrResponse.permissions,function(key,val){
		var VarChkBoxId = encodeURIComponent(VarResourceName)+'_'+val;
		var VarChecked = '';
		if($.inArray(val.toLowerCase(),ObjResIdentifier[VarResourceName]['resPer']) != -1){
			VarChecked = 'checked';
		}
		VarPermissionsTxt += '<div class="md-checkbox"><input type="checkbox" id="'+VarChkBoxId+'" class="md-check" data-pername="'+val+'" value="'+val+'" '+VarChecked+' /><label for="'+VarChkBoxId+'"><span class="inc"></span><span class="check"></span><span class="box"></span>'+val+'</label></div>';
		
	});
	VarPermissionsTxt += '</div>';
	$('#'+VarPermissionsContainer).html(VarPermissionsTxt);
}

function FnGetResourcePermissionsList(){
	var ArrResourceList = [];
	$('.resorceCon').each(function(){
		var VarResourceTitle = $(this).find('.resourceTitle').data('resname');
		
		var ObjPerThis = $(this).find('.resourcePermissions .md-checkbox input[type="checkbox"]:checked');
		var VarChkBoxLength = ObjPerThis.length;
		if(VarChkBoxLength > 0){
			var ObjResoures = {};
			ObjResoures['resourceName'] = VarResourceTitle;
			var ArrPermissions = [];
			$(ObjPerThis).each(function(){
				ArrPermissions.push(($(this).val()).toLowerCase());
			});
			ObjResoures['permissions'] = ArrPermissions;
			
			ArrResourceList.push(ObjResoures);
		}
	});
	
	if(VarEditRoleId != ''){
		ArrResourceList = FnCheckExistResouces(ArrResourceList);
	}
	return ArrResourceList;
}

function FnCheckExistResouces(ArrResourceList){
	$.each(ObjResIdentifier,function(VarResourceName,ObjVal){
		var VarPerCnt = (ObjVal['resPer']).length;
		if(VarPerCnt > 0){
			if(ObjResIdentifier[VarResourceName]['resFlag'] == 0){
				ArrResourceList.push({"resourceName":VarResourceName,"permissions":ObjVal['resPer']});
			}
		}
		
	});
		
	return ArrResourceList;
}

function FnGetRoleDetails(VarRoleName){
	$("#gapp-role-save").prop("disabled", true);
	var VarUrl = 'ajax/' + VarViewRoleUrl;
	VarUrl = VarUrl.replace("{role_name}",VarRoleName);
	VarUrl = VarUrl.replace("{domain_name}",domainName);
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResRoleDetails);
}

function FnResRoleDetails(response){
	
	var ArrResponse = response.entity;
	if(!$.isEmptyObject(ArrResponse)){
		$('#roleName').val(ArrResponse['roleName']);
		$('#Description').val(ArrResponse['description']);
		var ArrRoleResources = ArrResponse['resources'];
		if(ArrRoleResources.length > 0){
			var VarResourceTxt = '';
			$.each(ArrRoleResources,function(key,obj){
				var VarResourceCustomTxt = (obj['resourceName']).replace(/_/g," ");
								
				ObjResIdentifier[obj['resourceName']] = {};
				ObjResIdentifier[obj['resourceName']]['resPer'] = [];				
				
				
				VarResourceTxt += '<div class="permission-wrapper col-md-3 resorceCon" id="resorceCon_'+key+'"><div class="box box-solid box-primary"><div class="box-header with-border"><i class="fa fa-lock" aria-hidden="true"></i><h3 class="box-title"><span class="resourceTitle" id="resourceTitle_'+key+'">'+VarResourceCustomTxt+'</span></h3></div><div class="box-body form resourcePermissions" id="resourcePermissions_'+key+'"><div class="md-checkbox-list checkbox-inner-padding">';
				
				$.each(obj['permissions'],function(key1,val){
					
					VarResourceTxt += '<div class="md-checkbox"><input type="checkbox" class="md-check" checked /><label for="'+val+'"><span class="inc"></span><span class="check"></span><span class="box"></span>'+val+'</label></div>';
					
					ObjResIdentifier[obj['resourceName']]['resPer'].push(val.toLowerCase());
					
				});
				
				VarResourceTxt += '</div></div></div></div>';
				
			});
			
			$('#permissionsContainer').html(VarResourceTxt);
		}
		
		$("#gapp-addrole-form :input").prop("disabled", true);
		$('#gapp-role-save').attr('disabled',true);
		$("#gapp-role-cancel").prop("disabled", false);
	}
	
}

function FnEditRole(){
	FnGetResourcesList();
	$('#caption-display-role-breadcrumb').text('Edit Role');
	$('#caption-display-role-subject').text('Edit Role');
	$("#gapp-addrole-form :input").prop("disabled", false);
	$("#gapp-role-save").prop("disabled", false);
	$('#roleName').prop("disabled", true);
	$('#gapp-role-edit').hide();
}