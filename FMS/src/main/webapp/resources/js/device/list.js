$(document).ready(function () {

	$('body').tooltip({
		selector: '.grid-writeback,.grid-editdevice'
	});
			
	FnGetClientList();
	FnGetDeviceList({
		  "tenantDomain": payload.currentDomain,
		  "parentDomain": payload.domainName,
		  "time": "1466937606261",
		  "tenantId": payload.tenantId
		});

});

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
							if(this.key === 'domain'){
								element['tenantDomain'] = (this.value != undefined) ? this.value : '';
							} else {
								element[this.key] = (this.value != undefined) ? this.value : '';
							}
						});
						element["parentDomain"] = (this.domain != undefined && this.domain.domainName != undefined) ? this.domain.domainName : '';
						element["treeidentifier"] = (this.identifier != undefined && this.identifier.value != undefined) ? this.identifier.value : '';
						element["treevalue"] = (this.tree != undefined && this.tree.value != undefined) ? this.tree.value : '';

						if(element["treeidentifier"] != '' && element["treevalue"] != ''){
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
		}
	});
	
	$("#gapp-clients-list").kendoTreeView({
		dataSource: ObjClientDataSource,
		dataTextField: "treevalue",
		template:"<span id='#= item.treeidentifier #' title='#= item.treevalue #'>#= item.treevalue #</span>",
		select: function(e) {
			
			var tree = $("#gapp-clients-list").getKendoTreeView();
			var dataItem = tree.dataItem(e.node);		
			FnGetDeviceList(dataItem);
		}
	});
	
	var treeview = $("#gapp-clients-list").data("kendoTreeView");
	treeview.expand(treeview.findByText(payload.tenantId));
	FnSelectRootNode();
	FnInitSearch("#gapp-clients-list", "#treeViewSearchInput");
}

function FnSelectRootNode(){
	var el = $('#gapp-clients-list');
	var tree = el.data('kendoTreeView');
	var firstNode = el.find('.k-first');
	tree.select(firstNode);
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
		tv.expand(".k-item");
    });
}

function FnGetTenantDeviceParams(ObjTenantInfo){
	var ObjParam = identifier;
		
	ObjParam['domain'] = {"domainName" : ObjTenantInfo.parentDomain};
	ObjParam["entityTemplate"] = {"entityTemplateName" : "DefaultTenant"};
	ObjParam["platformEntity"] = {"platformEntityType" : "TENANT"};		
	ObjParam["identifier"] = {"key" : "tenantId","value" : ObjTenantInfo.tenantId};
	
	return ObjParam;
}

function FnGetDeviceList(ObjTenantInfo) {
	$("#GBL_loading").show();
	$('#gapp-device-list').show();
		
	var grid = $("#gapp-device-list").data("kendoGrid");
	if (grid != null) {
		grid.destroy();
	}
		
	var ObjParam = FnGetTenantDeviceParams(ObjTenantInfo);
	$("#gapp-device-list").kendoGrid({
		height : 495,
		dataSource : {
			type : "json",
			transport : {
				read : {
					type : "POST",
					dataType : "json",
					contentType : "application/json",
					url : "ajax" + getAllDevices,
					data : ObjParam
				},
				parameterMap : function (options, operation) {
					$("#gapp-device-list").data('kendoGrid').dataSource.data([]);
					if (!$.isEmptyObject(ObjParam)) {
						var data = JSON.stringify(options);

					} else {
						var data = options;
					}
					return data;
				}
			},
			schema : {
				data : function (response) {
					$("#GBL_loading").hide();
					console.log(response);
					var ArrResponse = response.entity;
					var VarResLength = ArrResponse.length;

					if (VarResLength > 0) {
						var responseData = [];
						$(ArrResponse).each(function (index) {
							var element = {};
							$(this.dataprovider).each(function () {
								var key = this.key;
								if (key === 'entityName') {
									element['sourceId'] = this.value;
								} else {
									element[key] = this.value;
								}
							});
							element["identifier"] = this.identifier.value;
							responseData.push(element);
						});
												
						return responseData;
					}

				},
				total : function (response) {
					return response.entity.length;
				},
				model : {
					fields : {
						sourceId : {
							type : "string"
						}
					}
				}
			},
			error : function(e) {
				$("#GBL_loading").hide();
				var ObjErrorResponse = $.parseJSON(e.xhr.responseText);
				console.log(ObjErrorResponse);
				FnShowNotificationMessage(ObjErrorResponse.errorMessage);
			},
			pageSize : 10,
			page : 1,
			serverPaging : false,
			serverFiltering : false,
			serverSorting : false,
			sort : {
				field : "sourceId",
				dir : "asc"
			}
		},
		selectable : "row",
		resizable : true,
		sortable : {
			mode : "single",
			allowUnsort : true
		},
		filterable : {
			mode : "row",
			extra : false
		},
		detailInit : FnGetConfigurations,
		pageable : {
			refresh : true,
			pageSizes : true,
			previousNext : true
		},
		columnMenu : {
			filterable : false,
			sortable : true
		},
		noRecords : {
			template : "<div class='Metronic-alerts alert alert-info fade in'><button type='button' class='close' aria-hidden='true'> </button> <i class='fa-lg fa fa-warning'></i> No data available on current client </div>"
		},
		columns : [{
				field : "sourceId",
				title : "Source Id",
				width:190
			}, {
				field : "deviceName",
				title : "Device Name",
				width:200
			},
			{
				field : "tags",
				title : "Tags"
			}, {
				field : "make",
				title : "Make"
			}, {
				field : "deviceType",
				title : "Type"
			}, {
				field : "model",
				title : "Model"
			}, {
				field : "protocol",
				title : "Protocol"
			}, {
				field : "version",
				title : "Version"
			}, {
				field : "networkProtocol",
				title : "Network Protocol"
			}
		]

	}).data("kendoGrid");

	$("#gapp-device-list").data("kendoGrid").tbody.on("click", ".grid-editdevice", function (e) {
		var tr = $(this).closest("tr");
		var data = $("#gapp-device-list").data('kendoGrid').dataItem(tr);
		$('#update_value').val(data.identifier);
		$('#managedevice').submit();
	});

	$("#gapp-device-list").data("kendoGrid").tbody.on("click", ".grid-writeback", function (e) {
		var tr = $(this).closest("tr");
		var data = $("#gapp-device-list").data('kendoGrid').dataItem(tr);
		var VarDSName = '';
		if (data.publish == 'true') {
			VarDSName = data.datasourceName;
		}
		$('#gapp-device-writeback #ds_name').val(VarDSName);
		$('#gapp-device-writeback #source_id').val(data.sourceId);
		$("#gapp-device-writeback").submit();
	});

	$(window).resize(function () {
		$("#gapp-device-list").data("kendoGrid").resize();
	});
}

function FnGenerateAction(publish, VarSourceId) {
	var VarActions = "<div class='diviceactions'>";
	//alert(VarSourceId);
	
	ObjPageAccess = {'view':'1','edit':'1'};
	if (ObjPageAccess['view'] == '1' && ObjPageAccess['edit'] == '1') {
		VarActions += "<a href='Javascript:void(0)' class='grid-editdevice'  data-toggle='tooltip' title='View and Edit'>"+VarSourceId+"</span></a>";
	}

	VarActions += "</div>";
	return VarActions;
}

function FnGetConfigurations(e) {
	var url = getAllPoints.replace("{source_id}", e.data.sourceId);
	$("<div/>").appendTo(e.detailCell).kendoGrid({
		resizable : true,
		dataSource : {
			type : "json",
			transport : {
				read : "ajax" + url
			},
			schema : {
				data : function (response) {
					var ObjResponse = response.entity;
					if (!$.isEmptyObject(ObjResponse)) {
						var responseData = ObjResponse.configPoints;
						return responseData;
					}

				},
				total : function (response) {
					var ObjResponse = response.entity;
					if (!$.isEmptyObject(ObjResponse)) {
						if (ObjResponse.configPoints != undefined) {
							var ArrConfPoints = ObjResponse.configPoints;
							return ArrConfPoints.length;
						} else {
							return 0;
						}
					} else {
						return 0;
					}

				},
				model : {
					fields : {
						pointId : {
							type : "string"
						},
						pointName : {
							type : "string"
						},
						type : {
							type : "json"
						},
						physicalQuantity : {
							type : "json"
						},
						unit : {
							type : "json"
						}
					}
				}
			},
			pageSize : 10,
			page : 1,
			serverPaging : false,
			serverFiltering : false,
			serverSorting : false,
			sort : {
				field : "pointName",
				dir : "asc"
			}
		},
		selectable : "row",
		sortable : {
			mode : "single",
			allowUnsort : true
		},
		pageable : {
			refresh : true,
			pageSizes : true,
			previousNext : true
		},
		columnMenu : false,
		columns : [{
				field : "pointId",
				title : "Point Id",
				filterable : false,
			}, {
				field : "pointName",
				title : "Point Name",
				filterable : false,
			},{
				field : "displayName",
				title : "Display Name",
				filterable : false,
			}, {
				field : "type",
				title : "Data Type",
				filterable : false,
			}, {
				field : "physicalQuantity",
				title : "Physical Quantity",
				filterable : false,
			}, {
				field : "unit",
				title : "Unit Of Measurement",
				filterable : false,
			}
		]

	});
}

function FnNavigateClientPage(){
	$("#assign_device").submit();
}
