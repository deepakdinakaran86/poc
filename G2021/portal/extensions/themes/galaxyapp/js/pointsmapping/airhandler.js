"use strict";

$(document).ready(function(){
	//Splitter initiation from kendo         
	var splitterElement = $("#splitter").kendoSplitter({
		panes : [ {
					collapsible : true,
					size : '20%'
				}
				]
	});
	
	FnGetDeviceList();
			
});

var isMainGrid = true;
function FnGetDeviceList() {
	var ObjParam = {};
	ObjParam["domain"] = {"domainName":VarCurrentTenantInfo.parentDomain};
	ObjParam["entityTemplate"] = {"entityTemplateName":"DefaultTenant"};
	ObjParam["globalEntity"] = {"globalEntityType":"TENANT"};
	ObjParam["identifier"] = { "key": "tenantName", "value": VarCurrentTenantInfo.tenantName};
	
	var VarUrl = GblAppContextPath +'/ajax' + VarListDeviceUrl;
	
	$("#gapp-devices-list").kendoGrid({
								toolbar : kendo.template($("#deviceSearchtemplate").html()),
								dataSource: {
									type: "json",
									transport: {
										read: {
											type : "POST",
											dataType : "json",
											contentType : "application/json",
											url : VarUrl,
											data : ObjParam
										},
										parameterMap : function(options, operation) {
											if(!$.isEmptyObject(ObjParam)){
												var data = JSON.stringify(options);
											} else {
												var data = options;
											}											
											return data;
										}
									},
									schema : {
										data : function(response) {
											var ArrResponse = response;
											var VarResLength = ArrResponse.length;
											var ArrResponseData = [];
											if (VarResLength >0) {											
												
												$(ArrResponse).each(function(){
													var element = {};
													element['identity'] = {};
													element['identity']['globalEntity'] = this.globalEntity;
													element['identity']['domain'] = this.domain;
													element['identity']['entityTemplate'] = {"entityTemplateName":this.entityTemplate.entityTemplateName};
													element['identity']['identifier'] = this.identifier;
													$(this.dataprovider).each(function() {
														var key = this.key;
														if(key === 'entityName'){
															element['sourceId'] = this.value;
														}
													});
													element["identifier"] = this.identifier.value;
													ArrResponseData.push(element);
												});
											
											} else {
												
												notificationMsg.show({
													message : ArrResponse['errorMessage']
												}, 'error');
											}
											return ArrResponseData;
										},
										total : function(response) {
											return response.length;
										
										}
									},
									change: function(e) {
									
									}
								},
								sortable : false,
								scrollable : true,
								mobile: true,
								columns : [									
						           {
						        	   field : "sourceId",
						        	   title : "Device",
						        	   width : 50,
						        	   editable : false
						           },

								],
								selectable : true,
								change : function(e) {
									var entityGrid = $("#gapp-devices-list").data("kendoGrid");
									var rows = entityGrid.select();
									var selectedItem = entityGrid.dataItem(rows);
									//console.log(selectedItem);
									if(selectedItem == null){
									
									} else {
										isMainGrid = false;
										var ObjParam = {};
										checkedIds = {};
										ObjParam['identity'] = selectedItem.identity;
										ObjParam['sourceId'] = selectedItem.sourceId;
										FnGetDevicePointsList(ObjParam);
									}
								}
	
	});
	
	$("#searchDeviceId").keyup(function() {
		var val = $("#searchDeviceId").val();
		$("#gapp-devices-list").data("kendoGrid").dataSource.filter({
			logic : "or",
			filters : [ {
				field : "sourceId",
				operator : "contains",
				value : val
			} ]
		});
	});
	
}

function FnGetDevicePointsList(ObjParam){
	//console.log(ObjParam);
	var VarUrl = GblAppContextPath + '/ajax' + VarListDevicePointsUrl;
	
	$("#gapp-points-list").kendoGrid({
							dataSource: {
								type: "json",
								transport: {
									read: {
										type : "POST",
										dataType : "json",
										contentType : "application/json",
										url : VarUrl,
										data : ObjParam
									},
									parameterMap : function(options, operation) {
										if(!$.isEmptyObject(ObjParam)){
											var data = JSON.stringify(options);
										} else {
											var data = options;
										}
										
										return data;
									}

								},
								schema: {
									data: function(response) {
										var ArrResponse = response;
										var VarResLength = ArrResponse.length;
											
										if (VarResLength >0) {
										
											var responseData = [];
											$(ArrResponse).each(function() {
												if(this.entityStatus != undefined){
													var VarPointStatus = this.entityStatus.statusName;
													if(VarPointStatus != 'ALLOCATED'){
														var element = {};
														element['fieldValues'] = [];
														$(this.dataprovider).each(function() {
															var key = this.key;
															element[key] = this.value;
															
															if(key=='pointId' || key=='pointName' || key=='physicalQuantity' || key=='unit' || key=='dataType'){
																element['fieldValues'].push({"key":key,"value":this.value});
															}
															
														});
														element["identifier"] = this.identifier.value;
														responseData.push(element);
													}
												} else {
													var element = {};
													element['fieldValues'] = [];
													$(this.dataprovider).each(function() {
														var key = this.key;
														element[key] = this.value;
														if(key=='pointId' || key=='pointName' || key=='physicalQuantity' || key=='unit' || key=='dataType'){
															element['fieldValues'].push({"key":key,"value":this.value});
														}
													});
													responseData.push(element);
												}												
												
											});
											return responseData;
										
										} else {
											var responseData = [];
											notificationMsg.show({
												message : ArrResponse['errorMessage']
											}, 'error');
											return responseData;
										}
									},
									total: function(response) {
										var VarTotalCount = 0;										
										if (response.errorCode == undefined) {
											$(response).each(function() {
												if(this.entityStatus != undefined){
													var VarPointStatus = this.entityStatus.statusName;
												
													if(VarPointStatus != 'ALLOCATED'){
														VarTotalCount++;
													}
												} else {
													VarTotalCount++;
												}
											});
										}
										return VarTotalCount;
									},
								},
								pageSize: 10,
								page: 1,
								serverPaging: false,
								serverFiltering: false,
								serverSorting: false,
							},
							selectable: "row",
							resizable: true,
							sortable: {
								mode: "single",
								allowUnsort: true
							},
							filterable : {
								mode : "row",
								extra: false
							},
							pageable: {
								refresh: true,
								pageSizes: true,
								previousNext: true
							},
							columnMenu: {
								filterable: false,
								sortable:false
							},
							columns: [
								{
									template: "<input type='checkbox' class='checkbox' />",
									width: 30
								},
								{
									field: "pointId",
									title: "Point id"
								},
								{
									field: "pointName",
									title: "Point Name"
								},
								{
									field: "dataType",
									title: "Data Type"
								},
								{
									field: "physicalQuantity",
									title: "Physical Quantity"
								},
								{
									field: "unit",
									title: "Unit of Measurement"
								}
							]
	});
	
	//bind click event to the checkbox	
	var pointlist = $("#gapp-points-list").data("kendoGrid");
	pointlist.table.on("click", ".checkbox" , selectRow);
	
}

var checkedIds = {};
function selectRow() {
    var checked = this.checked,
    row = $(this).closest("tr"),
    grid = $("#gapp-points-list").data("kendoGrid"),
    dataItem = grid.dataItem(row);
    checkedIds[dataItem.pointId] = checked;
    if (checked) {
        row.addClass("k-state-selected");
    } else {
        row.removeClass("k-state-selected");
    }
}

function FnGetSelectedDeviceInfo(){
	var ObjParam = {};
	var grid = $("#gapp-devices-list").data("kendoGrid");
	var selectedItem = grid.dataItem(grid.select());
	ObjParam['identifier'] = {"key": "identifier","value": selectedItem.identifier};
	ObjParam['domain'] = selectedItem.identity.domain;
	//console.log(selectedItem);
	return ObjParam;
}

function FnGetPoints(){
	var ArrPointList = [];
	var ArrData = $('#gapp-points-list').data("kendoGrid").dataSource.data();
	//console.log(ArrData);
	//return false;
	var VarDataCount = ArrData.length;
	
	if(VarDataCount > 0){
		$.each(ArrData,function(key,obj){
			var element = {};
			if(obj['identifier'] != undefined){
				element['identifier'] = {"key": "identifier","value": obj['identifier']};
			}
			element['fieldValues'] = obj['fieldValues'];
			element['isSelected'] = ((checkedIds[obj['pointId']] != undefined && checkedIds[obj['pointId']] == true) ? true : false);
									
			ArrPointList.push(element);
		});
	}
	return ArrPointList;
}

function FnSaveMapPoints(){
	$('#gapp-mappoint-save, #gapp-mappoint-cancel').attr('disabled',true);
	if(!$.isEmptyObject(checkedIds)){
		$("#GBL_loading").show();
		var VarUrl = GblAppContextPath+ '/ajax' + VarPointsMappingUrl;
		var VarParam = {};
		VarParam['equipment'] = VarEquipEntity.equipment;
		VarParam['equipmentId'] = VarEquipEntity.equipmentId;
		VarParam['facilityName'] = VarEquipEntity.facilityName;
		VarParam['tenantName'] = VarCurrentTenantInfo.tenantName;
		VarParam['device'] = FnGetSelectedDeviceInfo();
		VarParam['points'] = FnGetPoints();
		//console.log(VarParam);
		//return false;
		FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarParam), 'application/json; charset=utf-8', 'json', FnResMapPoints);
		
	} else {
		$("#alertModal").modal('show').find(".modalMessage").text("Please select atleast one point.");
		$('#gapp-mappoint-save, #gapp-mappoint-cancel').attr('disabled',false);
		return false;
	}	
}

function FnResMapPoints(response){
	//console.log(response);
	var ObjResponse = response;
	$('#gapp-mappoint-save, #gapp-mappoint-cancel').attr('disabled',false);
	$("#GBL_loading").hide();
	if(ObjResponse.status == 'SUCCESS'){
		
		notificationMsg.show({
			message : 'Points mapped successfully'
		}, 'success');
		
		$( "#equip_id").val(VarEquipEntity.equipment.identifier);
		$("#gapp-equipment-view").attr("action",VarEquipView);
		
		FnFormRedirect('gapp-equipment-view',GBLDelayTime);
		
	} else {
		notificationMsg.show({
			message : ObjResponse['errorMessage']
		}, 'error');
	}
	
}

function FnCancelMapPoints(){
	$("#gapp-equipment-view").attr("action",VarEquipList);
	if(GBLcancel > 0){
		$('#GBLModalcancel #hiddencancelFrm').val('gapp-equipment-view');
		$('#GBLModalcancel').modal('show');
	} else {
		$('#gapp-equipment-view').submit();
	}
	
}

