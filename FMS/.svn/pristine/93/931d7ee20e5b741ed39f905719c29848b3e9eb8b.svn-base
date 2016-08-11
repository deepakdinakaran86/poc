$(document).ready(function(){

	$("#serviceTagsMulti").kendoMultiSelect({
		dataTextField : "name",
		dataValueField : "name",
		animation: false,
		placeholder: "Select Tag...",
		enable: true
	});
	
});

//Global Variables
var serviceItemId = {};
var update = false;

$(window).load(function() {
	FnGetServiceTags();
	//alert("after check for serviceItem View Resp");
	$("#gapp-item-edit").attr("disabled",true);
	$("#user-edit").hide();
	if(viewServiceItem != undefined) {
		FnResViewServiceItem(viewServiceItem);
	}
});

function FnGetServiceTags() {
	$("#GBL_loading").show();
	var VarUrl = "ajax/"+listOfServiceTagsUrl;
	//alert("in FnGetServiceTags");
	reqPayload = {
					"startEntity": {
					    "platformEntity": {
					      "platformEntityType": "TENANT"
					    },
					    "domain": {
					      "domainName": UserInfo.getCurrentParentDomain()
					    },
					    "entityTemplate": {
					      "entityTemplateName": "DefaultTenant"
					    },
					    "identifier": {
					      "key": "tenantId",
					      "value": UserInfo.getCurrentTenantId()
					    }
					  },
					  "endEntity": {
					    "platformEntity": {
					      "platformEntityType": "TENANT"
					    },
					    "domain": {
					      "domainName": fmsClientDomainName
					    },
					    "entityTemplate": {
					      "entityTemplateName": "DefaultTenant"
					    },
					    "identifier": {
					      "key": "tenantId",
					      "value": fmsClientId
					    }
					  },
					  "searchTemplateName": "ServiceTags"
				}
	FnMakeAjaxRequest(VarUrl, 'POST', JSON.stringify(reqPayload), 'application/json; charset=utf-8', 'json', FnResServiceTagsList);
}

function FnResServiceTagsList(response) {
	$("#GBL_loading").hide();
	//alert("Blah");
	var ArrResponse = response.entity;
	if($.isArray(ArrResponse)){
		var VarResLength = ArrResponse.length;
		var ServiceTags = [];
		
		$.each(ArrResponse,function(key,val){
			var element = {};
			$.each(val.dataprovider,function(key,val){
				if(val.key == "name") {
					//console.log("key="+val.key+" Value="+val.value);
					element["name"] = val.value;				
				}
			});
			
			element["tagType"] = val.entityTemplate.entityTemplateName;
			element["domain"] = {"domainName" : val.domain.domainName};
			ServiceTags.push(element);
			
		});
		var dataNameTag = $('#serviceTagsMulti').data("kendoMultiSelect");
		dataNameTag.setDataSource(ServiceTags);
	}
}

function FnResViewServiceItem(response) {
	$("#GBL_loading").hide();
	$('#serviceItemName').val(response.serviceItemName);
	$('#description').val(response.description);
	
	var attachedServiceTags = [];
	var arrResp = response.listOfTags;
	$.each(arrResp,function(key,val){
		//alert(val.name);
		attachedServiceTags.push(val.name);
	});

	var serviceTagsMultiS = $('#serviceTagsMulti').data("kendoMultiSelect");
	serviceTagsMultiS.value(attachedServiceTags);
	
	$('#serviceTagsMulti :selected').each(function(i, selected) {
			$("#serviceTagsMulti_listbox .k-item")[$(selected).index()].disabled = true;
			$(".k-multiselect-wrap.k-floatwrap").find('.k-icon.k-delete').remove();
			//$(".k-multiselect-wrap .k-floatwrap").find('.k-select').remove();
	});
	
	serviceTagsMultiS.enable(false);	
	$("#gapp-item-edit").attr("disabled",false);
	$("#user-edit").show();
	$("#gapp-item-save").attr("disabled",true);
	$('#serviceItemName').prop("readonly", true);
	$('#description').prop("readonly", true);
	
	serviceItemId['identifier'] = response.serviceItemIdentifier.value;
	serviceItemId['domain'] = {"domainName" : response.domain.domainName};
	
}

function FnEdit() {
	
	var serviceTagsMulti = $('#serviceTagsMulti').data("kendoMultiSelect");
	serviceTagsMulti.enable(true);
	$("#gapp-item-save").attr("disabled",false);
	$('#serviceItemName').prop("readonly", false);
	$('#description').prop("readonly", false);
	update = true;
	
}

function FnSave() {
	//setFormValidation();
	var validator = $("#validateServiceName").kendoValidator().data("kendoValidator");
	
	var itemName = $('#serviceItemName').val();
	var description = $('#description').val();
	var listOfTags = FnGetAttachedServiceTags();
	
	var object = {};
	object['serviceItemName'] = itemName;
	object['description'] = description;
	
	if(listOfTags != undefined){
		object['listOfTags'] = listOfTags;
	}
	
	if(update) {
		object['serviceItemIdentifier'] = {"key": "identifier", "value": serviceItemId.identifier}	
		object["domain"] = {"domainName": serviceItemId.domain.domainName};
		console.log(JSON.stringify(object));
		var payload = JSON.stringify(object);
		$('#serviceItemUpdatePayload').val(payload);
		if (validator.validate()) {
			$('#gapp-item-update-form').submit();
		}
		
	} else {
		console.log(JSON.stringify(object));
					var payload = JSON.stringify(object);
			$('#serviceItemCreatePayload').val(payload);
		
		if (validator.validate()) {
			$('#gapp-item-save-form').submit();
		}
		
		
	}
}

function FnGetAttachedServiceTags() {	
	//var VarDataCheckFlag = false;
	var serviceTag = $("#serviceTagsMulti").data("kendoMultiSelect");
	var ArrServiceTagsJSONObj = [];
	$('#serviceTagsMulti :selected').each(function(i, selected) {
		
		//To check for disabled items which are not to be taken in list of tags
		if(undefined === $("#serviceTagsMulti_listbox .k-item")[$(selected).index()].disabled){
			//VarDataCheckFlag = true;
			var ObjFieldMap = {};
			ObjFieldMap["name"] = serviceTag.dataItem(selected.index).name;
			ObjFieldMap["tagType"] = serviceTag.dataItem(selected.index).tagType;
			ObjFieldMap["domain"] = {"domainName": serviceTag.dataItem(selected.index).domain.domainName};
			ArrServiceTagsJSONObj.push(ObjFieldMap);
		}	
	});
	
	//if no tags are specified then return undefined
	if(!ArrServiceTagsJSONObj.length) {
		ArrServiceTagsJSONObj = undefined;
	}
	
	return ArrServiceTagsJSONObj;
}

function FnCancel() {
	window.location.href = "item_list";
}

/*function setFormValidation() {
	// Form validation

	 $("#serviceItemName").attr('mandatory', 'poiName Name is required');

	$("#gapp-item-save-form")
			.kendoValidator(
					{
						validateOnBlur : true,
						errorTemplate : "<span style='color:red'>#=message#</span>",
						rules : {
							mandatory : function(input) {
								if (input.attr('id') == 'serviceItemName'
										&& (input.val() == '' || input.val().trim() == "")) {
									alert("null");
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
*/
