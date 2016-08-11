
$(window).load(function(){
	FnGetClientList();
	FnInitializeDeviceGrid([]);
	var ObjSelClient = FnGetSelectedTreeInfo();
	
	var parent = [{
		  "tenantDomain": payload.currentDomain,
		  "parentDomain": payload.domainName,
		  "time": "1466937606261",
		  "tenantId": payload.tenantId
		}];
	FnGetUnassignedData(parent,ObjSelClient);
});

function FnInitializeDeviceGrid(ArrDS){
	
	var grid = $('#gapp-devices-list').data("kendoGrid");
	if(grid != undefined){
		grid.destroy();
	}

	var deviceData = new kendo.data.DataSource({
					data : ArrDS,
					pageSize : 10,
					page : 1,
					serverPaging : false,
					serverFiltering : false,
					serverSorting : false,
					sort : {
						field : "sourceId",
						dir : "asc"
					}
				});
				
	
	var grid = $("#gapp-devices-list").kendoGrid({
					autoBind : true,
					dataSource : deviceData,
					selectable: false,
					resizable: true,
					sortable: {
						mode: "single",
						allowUnsort: true
					},
					filterable : {
						mode : "row"
					},
					pageable : {
						refresh : false,
						pageSizes : true,
						previousNext : true
					},
					noRecords: {
						template: "<div class='Metronic-alerts alert alert-info fade in'><button type='button' class='close' aria-hidden='true'> </button> <i class='fa-lg fa fa-warning'></i>No data available on current page.</div>"
					},
					dataBound: onDataBound,
					//height:480,
					height : 514,
					columns : [
								{
									headerTemplate: '<input type="checkbox" name="checkAll" id="checkAll" onClick="checkAllBoxClick();">',
									template: "<input type='checkbox' id='#=sourceId#' onClick='checkBoxClick(this,\"#=tenantId#\");'>",
									width: 30
								},
								{
									field: "sourceId",
									title: "Source Id"
								},
								{
									field: "deviceName",
									title: "Device Name"
								},
								{
									field: "tenantId",
									title: "Client Id"
								}
							]
				});
				
}	

function onDataBound(e){
	var view = this.dataSource.view();
	var VarChkCnt = 0;
	for(var i = 0; i < view.length;i++){
		if(view[i].tenantId === 'Unclaimed'){
			if($.inArray(view[i].sourceId, ArrSelClaimDevices) != -1){
				this.tbody.find("input[id='" + view[i].sourceId + "']").prop("checked",true).closest("tr").addClass('k-state-selected');
				VarChkCnt++;
			}
		} else {
			if($.inArray(view[i].sourceId, ArrSelAssignDevices) != -1){
				this.tbody.find("input[id='" + view[i].sourceId + "']").prop("checked",true).closest("tr").addClass('k-state-selected');
				VarChkCnt++;
			}
		}
	}
	
	var VarViewLength = view.length;
		
	if(VarViewLength>0 && VarViewLength == VarChkCnt){
		$("#checkAll").prop("checked", true);
	} else {
		$("#checkAll").prop("checked", false);
	}
	
}

var ArrSelClaimDevices = [];
var ArrSelAssignDevices = [];
function checkAllBoxClick() {
	var grid = $('#gapp-devices-list').data("kendoGrid");
	if ($("#checkAll").is(":checked")) {
		
		//Select all rows on screen
		grid.tbody.children('tr').addClass('k-state-selected');
		$('#gapp-devices-list').find('input[type=checkbox]').prop("checked", true);
		var ArrDisplayedData = grid.dataSource.view().toJSON();
		$.each(ArrDisplayedData,function(){
			if(this.tenantId != 'Unclaimed'){
				if($.inArray(this.sourceId, ArrSelAssignDevices) == -1){
					ArrSelAssignDevices.push(this.sourceId);
				}
			} else {
				if($.inArray(this.sourceId, ArrSelClaimDevices) == -1){
					ArrSelClaimDevices.push(this.sourceId);
				}
			}
		});
		
	} else {
					
		grid.tbody.children('tr').removeClass('k-state-selected');
		$('#gapp-devices-list').find('input[type=checkbox]').prop("checked", false);
		var ArrDisplayedData = grid.dataSource.view().toJSON();
		$.each(ArrDisplayedData,function(){
			if(this.tenantId != 'Unclaimed'){
				ArrSelAssignDevices.splice($.inArray(this.rowid, ArrSelAssignDevices),1);
			} else {
				ArrSelClaimDevices.splice($.inArray(this.rowid, ArrSelClaimDevices),1);
			}
		});
	}
	
	if(ArrSelClaimDevices==0 && ArrSelAssignDevices==0){
		$('#device_save_btn').attr('disabled',true);
	} else {
		$('#device_save_btn').attr('disabled',false);
	}	
}

function checkBoxClick(VarThis,VarTenantId) {
	$("#checkAll").prop("checked", false);
	var check = $('#gapp-devices-list').find('input[type="checkbox"]:checked').length;
	var checkAll = $('#gapp-devices-list').find('input[type="checkbox"]').length;
	var VarSelItem = $(VarThis).attr('id');
	if($(VarThis).is(':checked') == true){
		if(VarTenantId != 'Unclaimed'){
			if($.inArray(VarSelItem, ArrSelAssignDevices) == -1){
				$(VarThis).closest("tr").addClass('k-state-selected');
				ArrSelAssignDevices.push(VarSelItem);
			}
		} else {
			if($.inArray(VarSelItem, ArrSelClaimDevices) == -1){
				$(VarThis).closest("tr").addClass('k-state-selected');
				ArrSelClaimDevices.push(VarSelItem);
			}
		}
		
	} else {
		$(VarThis).closest("tr").removeClass('k-state-selected');
		if(VarTenantId != 'Unclaimed'){
			ArrSelAssignDevices.splice($.inArray(VarSelItem, ArrSelAssignDevices),1);
		} else {
			ArrSelClaimDevices.splice($.inArray(VarSelItem, ArrSelClaimDevices),1);
		}
	}
		
	//Check the header checkbox if all row checkboxes on screen are selected
	if (check == (checkAll - 1)){
		$("#checkAll").prop("checked", true);
	}
	
	if(ArrSelClaimDevices==0 && ArrSelAssignDevices==0){
		$('#device_save_btn').attr('disabled',true);
	} else {
		$('#device_save_btn').attr('disabled',false);
	}
	
}

function FnGetClientList(){

	var ObjClientDataSource = new kendo.data.HierarchicalDataSource({
		transport: {
			read: function(options) {
					
				if (options.data.hasOwnProperty('treeidentifier')) {																		
					var data = options.data;
					var tree = $("#gapp-clients-list").data("kendoTreeView");
					var node = tree.dataSource.get(data.treeidentifier);
					var VarUrl = 'ajax' + getDevicesOfTenant;
					
					var VarParam = {};
					VarParam['actor'] = {
						"platformEntity": {"platformEntityType": "TENANT"},
						"domain": {"domainName": node.parentDomain},
						"entityTemplate": {"entityTemplateName": "DefaultTenant"},
						"identifier": {"key": "tenantId","value": node.treeidentifier}
					};
					VarParam['searchTemplateName'] = 'DefaultTenant';
					VarParam['searchEntityType'] = 'TENANT';
					VarParam['statusName'] = 'ACTIVE';
								
					$.ajax({
						url: VarUrl,
						contentType: 'application/json; charset=utf-8',
						dataType: 'json',
						data: JSON.stringify(VarParam),
						type: "POST",						
						success: function(response) {
							options.success(response.entity);
						},
						error: function(xhr, status, error) {
							options.success({});
						}
						
					});
				} else {
					var reponse = [
					               {
					            	    "dataprovider": [
					            	      {
					            	        "key": "domain",
					            	        "value": payload.currentDomain
					            	      },
					            	      {
					            	        "key": "tenantId",
					            	        "value": payload.tenantId
					            	      }
					            	    ],
					            	    "identifier": {
					            	      "key": "tenantId",
					            	      "value": payload.tenantId
					            	    },
					            	    "tree": {
					            	      "key": "tenantId",
					            	      "value": payload.tenantId
					            	    },
					            	    "domain": {
					            	      "domainName": payload.domainName
					            	    }
					            	  }
					            	];
					
					options.success(reponse);
				}
			}
		},
		schema: {
			data: function(response) {	
				var ArrResponseData = [];
				var VarResLength = response.length;
				if (VarResLength > 0) {
					$.each(response,function() {
						var element = {};
						$(this.dataprovider).each(function () {
							element[this.key] = (this.value != undefined) ? this.value : '';
						});
						element["parentDomain"] = (this.domain != undefined && this.domain.domainName != undefined) ? this.domain.domainName : '';
						element["treeidentifier"] = (this.identifier != undefined && this.identifier.value != undefined) ? this.identifier.value : '';
						element["treevalue"] = (this.tree != undefined && this.tree.value != undefined) ? this.tree.value : '';
						
						if(element["treevalue"] != '' && element["treeidentifier"] !=''){
							ArrResponseData.push(element);
						}
					});
				}
						
				return ArrResponseData;
			},
			model: {
				id: "treeidentifier",
				hasChildren: true
			}
		},
		error : function(e) {
			$("#GBL_loading").hide();
			var ObjErrorResponse = $.parseJSON(e.xhr.responseText);
			console.log(ObjErrorResponse);
			FnShowNotificationMessage(ObjErrorResponse.errorMessage);
		}
	});
	
	$("#gapp-clients-list").kendoTreeView({
		dataSource: ObjClientDataSource,
		dataTextField: "treevalue",
		template:"<span id='#= item.treeidentifier #' title='#= item.treevalue #'>#= item.treevalue #</span>",
		select: function(e) {
				ArrSelClaimDevices = [];
				ArrSelAssignDevices = [];
				$("#gapp-devices-list").data("kendoGrid").dataSource.data([]);
				var tree = $("#gapp-clients-list").getKendoTreeView();
				$("#GBL_loading").show();
				FnGetParentItems(tree,e.node);
		}
	});
	
	var treeview = $("#gapp-clients-list").data("kendoTreeView");
	treeview.expand(treeview.findByText(payload.tenantId));
	FnSelectRootNode();
	FnInitSearch("#gapp-clients-list", "#treeViewSearchInput");
}

function FnGetSelectedTreeInfo(){
	var tree = $("#gapp-clients-list").getKendoTreeView();
	var selected = tree.select();
	var ObjSelClient = tree.dataItem(selected);
	return ObjSelClient;
}

function FnInitSearch(treeViewId, searchInputId) {

    var tv = $(treeViewId).data('kendoTreeView');

    $(searchInputId).on('keyup', function () {

        $(treeViewId + ' li.k-item').show();

        $('span.k-in > span.highlight').each(function () {
            $(this).parent().text($(this).parent().text());
        });

        // ignore if no search term
        if ($.trim($(this).val()) === '') {
            return;
        }

        var term = this.value.toUpperCase();
        var tlen = term.length;

        $(treeViewId + ' span.k-in').each(function (index) {
            var text = $(this).text();
            var html = '';
            var q = 0;
            var p;

            while ((p = text.toUpperCase().indexOf(term, q)) >= 0) {
                html += text.substring(q, p) + '<span class="highlight">' + text.substr(p, tlen) + '</span>';
                q = p + tlen;
            }

            if (q > 0) {
                html += text.substring(q);
                $(this).html(html);

                $(this).parentsUntil('.k-treeview').filter('.k-item').each(function (index, element) {
                    tv.expand($(this));
                    $(this).data('SearchTerm', term);
                });
            }
        });

        $(treeViewId + ' li.k-item:not(:has(".highlight"))').hide();
        //$(treeViewId + ' li.k-item').expand(".k-item");
		tv.expand(".k-item");
    });
}



function FnSelectRootNode(){
	var el = $('#gapp-clients-list');
	var tree = el.data('kendoTreeView');
	var firstNode = el.find('.k-first');
	tree.select(firstNode);
}

function FnGetParentItems(ObjTree,ObjNode){
	var ArrTenantInfo = [];
	var dataItem = ObjTree.dataItem(ObjNode);
	var VarSelNodeUID = dataItem.uid;
	
	do {
			var ObjParent = ObjTree.parent(ObjTree.findByUid(VarSelNodeUID));
			var i = ObjParent.length;
			if(ObjParent.length >0){
				var ObjDataItem = ObjTree.dataItem(ObjParent);
				ArrTenantInfo.push({'tenantId':ObjDataItem.treeidentifier,"parentDomain":ObjDataItem.parentDomain,"tenantDomain":ObjDataItem.domain});
				VarSelNodeUID = ObjDataItem.uid;
			} else {
				if(ArrTenantInfo.length == 0){
					var ObjDataItem = ObjTree.dataItem(ObjTree.findByUid(VarSelNodeUID));
					ArrTenantInfo.push({'tenantId':ObjDataItem.treeidentifier,"parentDomain":ObjDataItem.parentDomain,"tenantDomain":ObjDataItem.domain});
				}
			}
	}
	while(i!=0);
	
	FnGetUnassignedData(ArrTenantInfo,dataItem);
}

function FnGetUnassignedData(ArrTenantInfo,ObjSelClient){
	var i=0;
	$.each(ArrTenantInfo,function(){
		if(ObjSelClient.tenantId != this.tenantId){
			$("#GBL_loading").show();
			kendo.ui.progress($("#gapp-devices-list"), true);
			var ObjInputs = FnConstructUnassignedPayload(this);
			var VarTenantDomain = this.tenantDomain;
			var VarTenantId = this.tenantId;
			var VarParentDomain = this.parentDomain;
			$.ajax({
				type: 'POST',
				cache: true,
				async: true,
				contentType: 'application/json; charset=utf-8',
				url: ObjInputs['url'],
				data: JSON.stringify(ObjInputs['param']),
				dataType: 'json',
				success:function(response){
					i++;
					if(ArrTenantInfo.length == i){
						kendo.ui.progress($("#gapp-devices-list"), false);
						$("#GBL_loading").hide();
					}
					if(response.errorCode === undefined){
						FnParseUnassignedResponse(response,VarTenantDomain,VarTenantId,VarParentDomain);
					}
				},
				error:function(xhr){
					kendo.ui.progress($("#gapp-devices-list"), false);
					$("#GBL_loading").hide();
					var ObjErrorResponse = $.parseJSON(xhr.responseText);
					FnShowNotificationMessage(ObjErrorResponse.errorMessage);
				},
			});
		} else {
			$("#GBL_loading").hide();
			kendo.ui.progress($("#gapp-devices-list"), false);
		}
	});
}

function FnConstructUnassignedPayload(ObjTenantInfo){
	var VarUrl = "ajax" + toAssignUnassign;
	var ObjInputs = {
						"param" : {
							"actor" : {
								"domain" : {
									"domainName" : ObjTenantInfo.parentDomain
								},
								"identifier" : {
									"key" : "tenantId",
									"value" : ObjTenantInfo.tenantId
								},
								"platformEntity" : {
									"platformEntityType" : "TENANT"
								},
								"entityTemplate" : {
									"entityTemplateName" : "DefaultTenant"
								}
							},
							"searchTemplateName" : "Point",
							"markerTemplateName" : "Device"
						},
						"url" : VarUrl
	};
	return ObjInputs;
}

function FnParseUnassignedResponse(ArrResponse,VarTenantDomain,VarTenantId,VarParentDomain){
	var grid = $("#gapp-devices-list").data("kendoGrid");
	var ArrDS = [];
	if (VarTenantDomain == payload.currentDomain) {
		$(ArrResponse.entity).each(function () {
			var element = {};
			$(this.dataprovider).each(function () {
				if (this.key == 'entityName') {
					element['sourceId'] = this.value;
				} else {
					element[this.key] = this.value;
				}
			});
			element['deviceId'] = this.identifier.value;
			element['domain'] = this.domain.domainName;
			element['tenantId'] = VarTenantId;
			element['tenantDomain'] = VarParentDomain;
			//grid.dataSource.add(element);
			ArrDS.push(element);
		});
	} else {
		$(ArrResponse.entity).each(function () {
			var element = {};
			$(this.dataprovider).each(function() {
				if (this.key == 'entityName') {
					element['sourceId'] = this.value;
				} else {
					element[this.key] = this.value;
				}
			});
			element['deviceId'] = this.identifier.value;
			element['domain'] = this.domain.domainName;
			element['tenantId'] = VarTenantId;
			element['tenantDomain'] = VarParentDomain;
			//grid.dataSource.add(element);
			ArrDS.push(element);
		});
	}
	FnInitializeDeviceGrid(ArrDS);
	//grid.dataSource.sync();
	//grid.dataSource.read();
}

function FnGetClaimDeviceParams(){
	var ArrDevices = [];
	var ArrData = $('#gapp-devices-list').data("kendoGrid").dataSource.data();
	var VarDataCount = ArrData.length;
	if(VarDataCount>0){
		$.each(ArrData,function(){
			if($.inArray(this.sourceId,ArrSelClaimDevices) != -1 && this.tenantId === 'Unclaimed'){
				var element = {
					"make" : this.make,
					"deviceType" : this.deviceType,
					"model" : this.model,
					"protocol" : this.protocol,
					"version" : this.version,
					"nwProtocol" : this.networkProtocol,
					"tags" : "",
					"isPublish" : "true",
					"deviceIp" : this.deviceIp,
					"devicePort" : this.devicePort,
					"wbPort" : this.wbPort,
					"allocated" : "true",
					"status" : true,
					"sourceId" : this.sourceId,
					"deviceName": this.deviceName,
					"dsName": this.datasourceName
				};
			
				ArrDevices.push(element);
			}
		});
	}

	var ObjSelClient = FnGetSelectedTreeInfo();
	var ObjClient = {
		"platformEntity": {"platformEntityType": "TENANT"},
		"domain": {"domainName": ObjSelClient.parentDomain},
		"entityTemplate": {"entityTemplateName": "DefaultTenant"},
		 "identifier": {"key": "tenantId","value": ObjSelClient.tenantId}
	};
	
	var ObjParam = {};
	ObjParam['client'] = ObjClient;
	ObjParam['devices'] = ArrDevices;
	return ObjParam;
}

function FnAssignDeviceParams(){
	var ObjParams = {};
	
	var tree = $("#gapp-clients-list").getKendoTreeView();
	var selected = tree.select();
	var ObjSelClient = tree.dataItem(selected);
	
	ObjParams['owner'] = {
							"domain" : {"domainName" : ObjSelClient.parentDomain},
							"entityTemplate": {"entityTemplateName": "DefaultTenant"},
							"platformEntity": {"platformEntityType": "TENANT"},
							"identifier": {"key": "tenantId","value": ObjSelClient.tenantId}							
						};
	
	ObjParams['clientDevices'] = [];
	var ArrAssignData = $('#gapp-devices-list').data("kendoGrid").dataSource.data();
	var VarAssignDataCount = ArrAssignData.length;
	if(VarAssignDataCount>0){
		var ObjTmpDevices = {};
		$.each(ArrAssignData,function(){
			if($.inArray(this.sourceId,ArrSelAssignDevices) != -1 && this.tenantId != 'Unclaimed'){
			
				if(ObjTmpDevices[this.tenantId] == undefined){
					ObjTmpDevices[this.tenantId] = {};
					ObjTmpDevices[this.tenantId]['devices'] = [];
					ObjTmpDevices[this.tenantId]['parent'] = {
																"platformEntity": {"platformEntityType": "TENANT"},
																"domain" : {"domainName" : this.tenantDomain},
																"entityTemplate": {"entityTemplateName": "DefaultTenant"},
																"identifier": {"key": "tenantId","value": this.tenantId}
															};
															
					var element = {
								"platformEntity": {"platformEntityType": "MARKER"},
								"domain": {"domainName": this.domain },
								"entityTemplate": {"entityTemplateName": "Device"},								
								"identifier": {"key": "identifier","value": this.deviceId }
					};
					
				} else {
					
					var element = {
								"platformEntity": {"platformEntityType": "MARKER"},
								"domain": {"domainName": this.domain },
								"entityTemplate": {"entityTemplateName": "Device"},								
								"identifier": {"key": "identifier","value": this.deviceId }
					};
					
				}
				
				ObjTmpDevices[this.tenantId]['devices'].push(element);
			}
		});
		
		if(!$.isEmptyObject(ObjTmpDevices)){
			$.each(ObjTmpDevices,function(key,obj){
				ObjParams['clientDevices'].push(obj);
			});
		}
		
	}
	
	return ObjParams;
}

function FnSaveDevices(){

	$('#device_save_btn').attr('disabled',true);
	var VarClaimDevicesLength = ArrSelClaimDevices.length;
	var VarAssignDevicesLength = ArrSelAssignDevices.length;
	if(VarClaimDevicesLength !=0 || VarAssignDevicesLength !=0){
		var ObjSelClient = FnGetSelectedTreeInfo();		
		if(VarAssignDevicesLength>0 && ObjSelClient.tenantId == payload.tenantId){
			$("#alertModal").modal('show').find(".modalMessage").text("You can not assign devices to Root Client.");
			$('#device_save_btn').attr('disabled',false);
			return false;
		}
	
		$("#GBL_loading").show();
		kendo.ui.progress($("#gapp-devices-list"), true);
		
		if(VarClaimDevicesLength >0){
			
			var ObjClaimDeviceParams = FnGetClaimDeviceParams();
			var VarClaimUrl = GblAppContextPath+ '/ajax' + VarDeviceClaimUrl;
						
			$.ajax({
					type: 'PUT',
					cache: true,
					async: true,
					contentType: 'application/json; charset=utf-8',
					url: VarClaimUrl,
					data: JSON.stringify(ObjClaimDeviceParams),
					dataType: 'json',
					success:function(response){
						if(response.status === 'SUCCESS'){
							var ObjSelClient = FnGetSelectedTreeInfo();							
							if(ObjSelClient.domain === VarAppRootDomain){
								$("#GBL_loading").hide();
								notificationMsg.show({
									message : 'Device claimed successfully'
								}, 'success');								
								FnFormRedirect('gapp-device-list',GBLDelayTime);
							} else {
								var ObjParams = FnAssignDeviceParams();
								if(ObjParams['clientDevices'].length > 0){
									FnAssignDevices(ObjParams);
								} else {
									$("#GBL_loading").hide();
									kendo.ui.progress($("#gapp-devices-list"), false);
									FnDeviceMappingConfirmation();
								}
							}
						} else {
							
							$("#GBL_loading").hide();
							kendo.ui.progress($("#gapp-devices-list"), false);
							notificationMsg.show({
								message : response.errorMessage
							}, 	'error');
							return false;
						}
					},
					error:function(){
						console.log('Error');
					},
				});		
		}
		
		if(VarClaimDevicesLength == 0 && VarAssignDevicesLength>0){
			var ObjParams = FnAssignDeviceParams();
			if(ObjParams['clientDevices'].length > 0){
				FnAssignDevices(ObjParams);
			}
		}		
				
		
	} else {
		$("#alertModal").modal('show').find(".modalMessage").text("Please select at least one Device.");
		$('#device_save_btn').attr('disabled',false);
		return false;
	}
}

function FnAssignDevices(ObjParams){

	var VarAssignUrl = "ajax" + assignBuldk;
	
	$.ajax({
		type: 'POST',
		cache: true,
		async: true,
		contentType: 'application/json; charset=utf-8',
		url: VarAssignUrl,
		data: JSON.stringify(ObjParams),
		dataType: 'json',
		success:function(resp){
			response = resp.entity;
			if(!$.isEmptyObject(response)){
				if(response.errorCode == undefined){
					kendo.ui.progress($("#gapp-devices-list"), false);
					$("#GBL_loading").hide();
					FnCancelDevice()
					notificationMsg.show({
							message : 'Devices assigned successfully'
						}, 'success');
					//FnDeviceMappingConfirmation(); 
				} else {
					kendo.ui.progress($("#gapp-devices-list"), false);
					$("#GBL_loading").hide();
					notificationMsg.show({
						message : response.errorMessage
					}, 'error');
				}
			} else {
				kendo.ui.progress($("#gapp-devices-list"), false);
				$("#GBL_loading").hide();
				notificationMsg.show({
					message : 'Error'
				}, 'error');
			}
			
		},
		error:function(){
			console.log('Error');
		},
	});
	
}

function FnDeviceMappingConfirmation(){
			
	var kendoWindow = $("<div />").kendoWindow({
								title: "Confirm",
								height:100,
								width: 190,
								resizable: false,
								modal: true
							});
							
	kendoWindow.data("kendoWindow").content($("#mapping-confirmation").html()).center().open();
	kendoWindow.find(".mapping-confirm,.mapping-cancel").click(function() {
							if ($(this).hasClass("mapping-confirm")) {
								var ObjSelClient = FnGetSelectedTreeInfo();
								$('#gapp-device-map #tenant_map_info').val(JSON.stringify({"tenantId":ObjSelClient.tenantId,"tenantId":ObjSelClient.tenantId,"domain":ObjSelClient.domain,"parentDomain":ObjSelClient.parentDomain}));
								FnFormRedirect('gapp-device-map',GBLDelayTime);
								
							} else {
								kendoWindow.data("kendoWindow").close();
								FnFormRedirect('gapp-device-list',GBLDelayTime);
							}
						}).end();
	
}

function FnNavigateDeviceList() {
	$('#gapp-device-list').submit();
}