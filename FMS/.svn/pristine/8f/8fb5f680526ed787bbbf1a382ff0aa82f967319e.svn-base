"use strict";

$(document).ready(function(){
	FnInitiateMap();
	FnGetFeaturesList();
	
	$('.gllpLatitude').val(var_lat);
	$('.gllpLongitude').val(var_long);
	$('#gllpLatitude').val(var_lat);
	$('#gllpLongitude').val(var_long);
	
	if($('#image').val() != ''){
		document.getElementById("client-logo-preview").src = "data:image/png;base64," + $('#image').val();
	}
	
	
	//---For name-tag --//
	$("#name-tag-placeholder").kendoMultiSelect({
		dataTextField : "name",
		dataValueField : "value",
		animation: false,
		enable: true
	});
	//--Get all tags available to be assigned-//
	FnGetTagList();
});


$(window).load(function(){
	$('#tenantFeatures').multiSelect({
		selectableHeader: "<div class='features-header'>Features Available</div>",
		selectionHeader: "<div class='features-header'>Features Selected</div>",
	});	
	var varIdentifier = $('#identifier').val(); 
	//--Get tags for selected tenant-//
	if(varIdentifier!= ""){	
		$('.map-content').addClass('disableGeoTagMap');
		$('#client-edit').show();
		$('#client-save').hide();
		FnGetTagNames();
		FnResSavedFeaturesList(savedFeatures);	
		FnPlotGeotag();
		
	}
	else{				
		$('#client-edit').hide();
	}//Error on create
	if(pageError!="" && varIdentifier==""){
		FnResSavedFeaturesList(savedFeatures);	
		FnResNameTagsOnError();
		FnPlotGeotag();
	}
});


function FnGetFeaturesList(){
	//TODO change domain
	var varUrl2 = "ajax/"+ getTenantFeaturesUrl;
	varUrl2 	   = varUrl2.replace("{domain}",currentDomain);
	FnMakeAjaxRequest(varUrl2, 'GET', '', 'application/json; charset=utf-8', 'json', FnResFeaturesList);
}

function FnResFeaturesList(response){
	var ArrResponse = response.entity;
	var ArrResData = [];
	var select = document.getElementById("tenantFeatures");
	
	if($.isArray(ArrResponse)){
		for (var i in ArrResponse) {
			var option = document.createElement('option');
			option.value = ArrResponse[i];
			option.text = ArrResponse[i];
			var val = ArrResponse[i];
			if(val == 'Geo-service Management' || val == 'Tag Management' || val == 'Tenant Management' || val == 'User Management'){
				option.selected = val;
				option.style="pointer-events: none;opacity:0.8"; 
			}
			select.add(option);	
		}
	}	
	var my_options = $("#tenantFeatures option");

	my_options.sort(function(a,b) {
		if (a.text > b.text && a.value!="") return 1;
		else if (a.text < b.text && a.value!="") return -1;
		else return 0
	})
	$("#tenantFeatures").append(my_options);

}
//TODO change the domain
function FnGetTagList(){
	var VarUrl = "ajax/"+getNamedTagsToAssign;
//	alert(VarUrl);
	var VarMainParam = {
			"actorType": "TEMPLATE",
			"actor": {
				"template": {
					"domain": {
						"domainName": currentDomain
					},
					"entityTemplateName": "DefaultTenant",
					"platformEntityType": "TENANT"
				}
			}
	};
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResTagList);
}

function FnResTagList(response){
	var ArrResponse = response.entity;
	var ArrResData = [];
	if($.isArray(ArrResponse)){
		$.each(ArrResponse,function(key,val){
			var ObjTag = {};
			ObjTag.name = val.name;
			ObjTag.value = val.name;
			ObjTag.tagType = val.tagType;
			ObjTag.domain = val.domain.domainName;
			ArrResData.push(ObjTag);
		});
	}	

	var dataNameTag = $("#name-tag-placeholder").data("kendoMultiSelect");
	dataNameTag.setDataSource(ArrResData);
}
function FnGetTagNames(){	
	var VarUrl = "ajax/"+getNamedTagsToAssign;	
	var varIdentifier = $('#identifier').val(); 
	var varDomain = $('#parentDomain').val(); 
	
	var VarMainParam ={
			"actorType": "ENTITY",
			"actor": {
				"entity": {
					"platformEntity": {
						"platformEntityType": "TENANT"
					},
					"domain": {
						"domainName": varDomain
					},
					"entityTemplate": {
						"entityTemplateName": "DefaultTenant"
					},
					"identifier": {
						"key": "tenantId",
						"value": varIdentifier
					}
				}
			}
	};
	FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarMainParam), 'application/json; charset=utf-8', 'json', FnResFindNameTags);
}

function FnResFindNameTags(response){
	var ArrResponse = response.entity;
	var ArrResData = [];

	var name_tag_multiselect = $("#name-tag-placeholder").data("kendoMultiSelect");
	if($.isArray(ArrResponse)){
		var arrVal=[];
		$.each(ArrResponse,function(key,val){
			arrVal.push(val.name);


		});
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

function FnResNameTagsOnError(){
	var ArrResponse = tagsOnError;
	var ArrResData = [];

	var name_tag_multiselect = $("#name-tag-placeholder").data("kendoMultiSelect");
	if($.isArray(ArrResponse)){
		var arrVal=[];
		$.each(ArrResponse,function(key,val){
			arrVal.push(val.name);


		});
		name_tag_multiselect.value(arrVal);
//		$('#name-tag-placeholder :selected').each(function(i, selected) {
//			$("#name-tag-placeholder_listbox .k-item")[$(selected).index()].disabled = true;
//			$(".k-multiselect-wrap.k-floatwrap").find('.k-icon.k-delete');
//		});

		$('#nametag-update-flag').val("true");
	}

	name_tag_multiselect.enable(true);
}

function FnSaveClient(){
	
	var ArrFieldValuesJSONObj = FnGetSelectedTags();
	$('#selectedTags').val(JSON.stringify(ArrFieldValuesJSONObj));
	var VarObjTenantFeatures = JSON.stringify($('#tenantFeatures').val()); 
	console.log(VarObjTenantFeatures);
	var varIdentifier = $('#identifier').val(); 
	var VarGeoLatitude  = $('#tag-latitude').val();
	var VarGeoLongitude = $('#tag-longitude').val();
	$('#latitude').val(VarGeoLatitude);
	$('#longitude').val(VarGeoLongitude);
	$("#GBL_loading").show();
	$("#tenant_create").submit();
}

function FnGetSelectedTags(){
	var VarDataCheckFlag = false;
	var roles = $("#name-tag-placeholder").data("kendoMultiSelect");
	var ArrFieldValuesJSONObj = [];
	//TODO update the domain
	$('#name-tag-placeholder :selected').each(function(i, selected) {

		if(undefined === $("#name-tag-placeholder_listbox .k-item")[$(selected).index()].disabled){
			VarDataCheckFlag = true;
			var ObjFieldMap = {};
			ObjFieldMap["name"] = roles.dataItem(selected.index).name;
			ObjFieldMap["tagType"] = roles.dataItem(selected.index).tagType;
			ObjFieldMap["domain"] = {"domainName":roles.dataItem(selected.index).domain};
			ArrFieldValuesJSONObj.push(ObjFieldMap);
		}	
	});
	return ArrFieldValuesJSONObj;
}



function FnShowTags(){
	$("#tag-wrapper").slideToggle();
	setTimeout(function() {					  
		$(".gllpLatlonPicker").each(function() {
			$(document).gMapsLatLonPicker().init( $(this) );
		});				  
	}, 1000);
}

function FnGetClientDetails(VarClientId){
	$("#gapp-addclient-form :input").prop("disabled", true);
	$('#gapp-client-save').attr('disabled',true);
	$("#gapp-client-cancel").prop("disabled", false);
	var VarUrl = GblAppContextPath+'/ajax' + VarViewClientUrl;
	VarUrl = VarUrl.replace("{tenant_id}",VarClientId);
	VarUrl = VarUrl.replace("{domain}",VarCurrentTenantInfo.tenantDomain);	
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResClientDetails);


}

function FnResClientDetails(savedFeatures){
	var ArrResponse = response;
	if(!$.isEmptyObject(ArrResponse)){
		$.each(ArrResponse['fieldValues'],function(key,obj){
			if($('#gapp-addclient-form #'+obj['key'])){
				$('#gapp-addclient-form #'+obj['key']).val(obj['value']);
			}

			if(obj['key'] == 'domain'){
				GblClientDomain = obj['value'];
			}

		});

		if(ArrResponse['entityStatus']['statusName'] == 'ACTIVE'){
			$('#gapp-addclient-form #statusName_active').attr('checked',true);
		} else {
			$('#gapp-addclient-form #statusName_inactive').attr('checked',true);
		}


	}
}

function FnEditClient(){	
	console.log('Clients:FnEditClient');
	$('#client-save').attr('disabled',false);	
	$('#client-edit').hide();
	$("#tenant_create :input").prop("disabled", false);
	$('#tenantId').prop("disabled", true);
	$('.pageTitleTxt').text('Edit Client');
	$('#client-edit').hide();
	$('.map-content').removeClass('disableGeoTagMap');
	$('#user-edit').hide();
	$('#client-save').show();

	//geo tag: label change // name-tag enable
	$("#form-gapp-tag-management :input").prop("disabled", false);
	if($('#tag-update-flag').val() == 'true'){
		$('#geo-tag-label').html('Update Tag');
	}

	var a = document.getElementById("action");
	a.value = "Update";
	
	pageTitle = "Edit Client";
	
	var name_tags = $("#name-tag-placeholder").data("kendoMultiSelect");
	name_tags.enable(true);
}

function FnCancelTenant(){
	//TODO pass logged in domain or selected domain
	$('#domain').val(currentDomain);
	$("#tenant_home").submit();
}

function FnResSavedFeaturesList(savedFeatures){
	var ArrResponse = savedFeatures;
	var ArrSaveFeatureData = [];
	if($.isArray(ArrResponse)){
		$.each(ArrResponse,function(key,val){
			ArrSaveFeatureData.push(val)
		});
		console.log(ArrSaveFeatureData);
		$("#tenantFeatures > option").each(function() {
		//	console.log("current Value"+this.value);
			if($.inArray(this.value, ArrSaveFeatureData) >= 0){
				$(this).attr("selected","selected");
			}
		});
	}	
	$('#tenantFeatures').multiSelect('refresh');
	/*
	$( "#tenantFeatures" ).prop( "disabled", true );
	$('.ms-selectable').css({"pointer-events": "none","opacity": 0.8});
	*/
}

function checkUniquenessAcrossApp(serviceURL,field, value,template) {

	var status = false;
	var json = {
			"fieldValues" : [ {
				"key" : field,
				"value" : value
			} ],
			"entityTemplate":{
				"entityTemplateName":template
			},
			  "uniqueAcross":"application"
	};

	$.ajax({
		url : "ajax/" + serviceURL,
		dataType : 'json',
		type : "POST",
		async : false,
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		data : JSON.stringify(json),
		success : function(response) {
			var message = response.entity;
			if (message != null && message.status == 'SUCCESS') {
				status = true;
			}
		},
		error : function(xhr, status, error) {
			console.log("Error in Checking Uniqueness"+error);
			status = true;
		}
	});

	return status;

}

function FnPlotGeotag(){
	var latitude = $('#latitude').val(); 
	var longitude = $('#longitude').val(); 
	
	if(latitude!="" && longitude!=""){
	
	$('#tag-latitude-longitude-display-value').html(latitude+' , '+longitude);
	
	$('#gllpLatitude').val(latitude);
	$('#gllpLongitude').val(longitude);
	var VarLatitude = parseFloat($('#gllpLatitude').val());
	var VarLongitude = parseFloat($('#gllpLongitude').val());
	var object={}
	object["latlng"]= {"lat" : VarLatitude,"lng":VarLongitude};
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