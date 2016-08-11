$(document)
		.ready(
				function() {
					$(".k-grid-content").css("overflow", "scroll").css(
							"overflow-x", "auto").scroll(function() {
						var left = $(this).scrollLeft();
						var wrap = $(".k-grid-header-wrap");
						if (wrap.scrollLeft() != left)
							wrap.scrollLeft(left);
					});

					$('body')
							.tooltip(
									{
										selector : '.grid-asset-mappoints,.grid-asset-dashboard,.grid-asset-view'
									});
				});

$(window).load(function() {
	$("#vehicle_id").val('');
	FnInitializeAssetListGrid();
	// FnGetAssetTypes();
	FnResAssetTypes(vehicleType);
	FnGetAssetList();
});

function FnInitializeAssetListGrid() {
	var ArrGridColumns = FnGetGridColumns();

	// var PointMapLink = (ObjPageAccess['mappoint']=='1') ? " &nbsp;<a
	// class='grid-asset-mappoints btn btn-sm btn-raised default'
	// data-toggle='tooltip' data-placement='left' title='Map points'><i
	// class='smicon sm-linkicon'></i></a>" : "";
	var PointMapLink = " &nbsp;<a class='grid-asset-mappoints btn btn-sm btn-raised default' data-toggle='tooltip' data-placement='left' title='Map points'><i class='ion-shuffle'></i></a>";
	// var Actionlink = (ObjPageAccess['dashboard']=='1') ? '<a
	// href="javascript:void(0);" class="grid-asset-dashboard btn btn-sm
	// btn-raised default" data-toggle="tooltip" data-placement="bottom"
	// title="Asset dashboard"><i class="smicon sm-metericon"></i></a>' : "";
	//var Actionlink = '<a  href="javascript:void(0);" class="grid-asset-dashboard btn btn-sm btn-raised default"  data-toggle="tooltip" data-placement="bottom"  title="Asset dashboard"><i class="fa fa-tachometer" aria-hidden="true"></i></a>';
	var VarActionsLink = '<div class="asset-actions">';

	VarActionsLink +=  PointMapLink;

	VarActionsLink += '</div>';

	ArrGridColumns.push({
		title : "Action",
		template : VarActionsLink,
		filterable : false,
		sortable : false,
		width : 150
	});
	var ObjGridConfig = {
		"scrollable" : true,
		"filterable" : {
			mode : "row"
		},
		"sortable" : true,
		"pageable" : {
			pageSize : 10,
			numeric : true,
			pageSizes : true,
			refresh : false
		},
		"selectable" : true
	};
	FnDrawGridView('#gapp-asset-list', [], ArrGridColumns, ObjGridConfig);
}

function FnGetAssetTypes() {
	var VarUrl = GblAppContextPath + '/ajax' + VarListTemplateUrl
			+ "?domain_name=" + VarCurrentTenantInfo.tenantDomain;
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '',
			'application/json; charset=utf-8', 'json', FnResAssetTypes);
}

function FnResAssetTypes(response) {
	var ArrResponse = response;
	if ($.isArray(ArrResponse)) {
		var VarAssetTypesTxt = '<option value=""> All</option>';
		$.each(ArrResponse, function(key, val) {
			VarAssetTypesTxt += '<option value="' + val.assetType + '">'
					+ val.assetType + '</option>';
		});
		$('#assetCategory').html(VarAssetTypesTxt);
	}

}

function FnGetAssetList(VarAssetType) {
	$("#GBL_loading").show();
	if (VarAssetType != undefined) {
		if (VarAssetType != '') {
			var VarUrl = '/FMS/ajax/galaxy-am/1.0.0/assets' + "?domain_name="
					+ UserInfo.getCurrentDomain() + "&asset_type="
					+ VarAssetType;
			FnMakeAsyncAjaxRequest(VarUrl, 'GET', '',
					'application/json; charset=utf-8', 'json', FnResAssetList);
		} else {
			if (undefined != vehicle) {
				FnResAssetList(vehicle)
			}

		}
	} else if (undefined != vehicle) {
		FnResAssetList(vehicle)
	} else {
		$("#GBL_loading").hide();
	}

	// FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json;
	// charset=utf-8', 'json', FnResAssetList);
}

function FnResAssetList(response) {
	$("#GBL_loading").hide();
	var ArrResponse = response.entity;
	var ArrGridColumns = FnGetGridColumns();
	if ($.isArray(ArrResponse)) {
		var ArrResponseData = [];
		if ($('#assetCategory').val() != '') {
			var i = 0;
			var column_count = 0;
			$.each(ArrResponse, function() {
				var element = {};
				if (this.assetTypeValues != undefined) {
					$.each(this.assetTypeValues, function() {
						element[this.key] = this.value;
						if (column_count < 4 && i == 0) {
							ArrGridColumns.push({
								title : this.key,
								field : this.key
							});
							column_count++;
						}
					});
					i++;
				}
				element['assetName'] = this.assetName;
				element['description'] = this.description;
				element['assetId'] = this.assetId;
				element['serialNumber'] = this.serialNumber;
				element['assetType'] = this.assetType;
				element['identifier'] = this.assetIdentifier.value;
				ArrResponseData.push(element);
			});
		} else {

			$.each(ArrResponse, function() {
				var element = {};
				element['assetName'] = this.assetName;
				element['description'] = this.description;
				element['assetId'] = this.assetId;
				element['serialNumber'] = this.serialNumber;
				element['assetType'] = this.assetType;
				element['identifier'] = this.assetIdentifier.value;
				ArrResponseData.push(element);
			});

		}
	} else {
		var ArrResponseData = [];
	}

	var PointMapLink = (UserInfo.hasPermission('vehicle_management',
			'map points')) ? " &nbsp;<a href='Javascript:void(0)' class='grid-asset-mappoints' data-toggle='tooltip' title='Map Points'><i class='ion-shuffle'></i></a>"
			: "";
	// var PointMapLink = "<a href='Javascript:void(0)'
	// class='grid-asset-mappoints' data-toggle='tooltip' title=' Vehicle Map
	// Points'><i class='smicon sm-linkicon'></i></a>";
	// var PointMapLink = " &nbsp;<a href='Javascript:void(0)'
	// class='grid-asset-mappoints' data-toggle='tooltip' title='Map Points'><i
	// class='ion-shuffle'></i></a>";
	// var Actionlink = (ObjPageAccess['dashboard']=='1') ? '<a
	// href="javascript:void(0);" class="grid-asset-dashboard"
	// data-toggle="tooltip" title="Vehicle Dashboard"><i class="smicon
	// sm-metericon"></i></a>' : "";
	// var Actionlink ='<a href="javascript:void(0);"
	// class="grid-asset-dashboard" data-toggle="tooltip" title="Asset
	// Dashboard"><i class="smicon sm-metericon"></i></a>';
	//var Actionlink = '<a  href="javascript:void(0);" class="grid-asset-dashboard"  data-toggle="tooltip" title="Vehicle Dashboard"><i class="fa fa-tachometer" aria-hidden="true"></i></a>';
	var VarActionsLink = '<div class="asset-actions">';

	VarActionsLink += PointMapLink;

	VarActionsLink += '</div>';

	ArrGridColumns.push({
		title : "Action",
		template : VarActionsLink,
		filterable : false,
		sortable : false,
		width : 150
	});

	var ObjGridConfig = {
		"scrollable" : true,
		"filterable" : {
			mode : "row"
		},
		"sortable" : true,
		"pageable" : {
			pageSize : 10,
			numeric : true,
			pageSizes : true,
			refresh : false
		},
		"selectable" : true
	};

	var objDatasource = {};
	objDatasource['data'] = ArrResponseData;
	objDatasource['sort'] = {
		field : "assetName",
		dir : "asc"
	}

	var grid = $("#gapp-asset-list").data("kendoGrid");
	if (grid != undefined) {
		grid.destroy();
		$('#gapp-asset-list').html('');
	}

	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#gapp-asset-list', objDatasource, ArrGridColumns,
			ObjGridConfig);

	$("#gapp-asset-list").data("kendoGrid").tbody
			.on("click", ".grid-asset-view",
					function(e) {
						var tr = $(this).closest("tr");
						var data = $("#gapp-asset-list").data('kendoGrid')
								.dataItem(tr);
						data = data.identifier;
						$("#vehicle_id").val(data);
						$("#gapp-asset-edit").submit();
					});

	$("#gapp-asset-list").data("kendoGrid").tbody
			.on("click", ".grid-asset-mappoints",
					function(e) {
						var tr = $(this).closest("tr");
						var data = $("#gapp-asset-list").data('kendoGrid')
								.dataItem(tr);
						FnSubmitEquipmentMapPoints(data);
					});

	$("#gapp-asset-list").data("kendoGrid").tbody
			.on("click", ".grid-asset-dashboard",
					function(e) {
						var tr = $(this).closest("tr");
						var data = $("#gapp-asset-list").data('kendoGrid')
								.dataItem(tr);
						var equipmentId = data.identifier;
						$('#dashboard_equip_id').val(equipmentId);
						$('#gapp-asset-dashboard').submit();
					});

}

function FnGetGridColumns() {

	var ViewLink = (UserInfo.hasPermission('vehicle_management', 'view')) ? "<a href='Javascript:void(0)' class='grid-asset-view capitalize' data-toggle='tooltip' title='View #=assetName# details'>#=assetName#</a>"
			: "<a href='Javascript:void(0)' class='capitalize'>#=assetName#</a>";
	// var ViewLink = "<a href='Javascript:void(0)' class='grid-asset-view
	// capitalize' data-toggle='tooltip' title='View #=assetName#
	// details'>#=assetName#</a>";

	var PointMapLink = " &nbsp;<a class='grid-asset-mappoints btn btn-sm btn-raised default' data-toggle='tooltip' data-placement='left' title='Map points'><i class='ion-shuffle'></i></a>";
	// var Actionlink = (ObjPageAccess['dashboard']=='1') ? '<a
	// href="javascript:void(0);" class="grid-asset-dashboard btn btn-sm
	// btn-raised default" data-toggle="tooltip" data-placement="bottom"
	// title="Asset dashboard"><i class="smicon sm-metericon"></i></a>' : "";
	//var Actionlink = '<a  href="javascript:void(0);" class="grid-asset-dashboard btn btn-sm btn-raised default"  data-toggle="tooltip" data-placement="bottom"  title="Asset dashboard"><i class="fa fa-tachometer" aria-hidden="true"></i></a>';

	var VarActionsLink = '<div class="asset-actions">';
	VarActionsLink += PointMapLink;
	VarActionsLink += '<a  href="javascript:void(0);" class="grid-asset-dashboard"  data-toggle="tooltip" data-placement="bottom" title="Asset Dashboard"><i class="smicon sm-metericon"></i></a>'
			+ PointMapLink;
	VarActionsLink += '</div>';

	var ArrColumns = [ {
		field : "assetName",
		title : "Vehicle Name",
		template : ViewLink
	}, {
		field : "assetType",
		title : "Vehicle Type"
	}, {
		field : "description",
		title : "Description"
	}, {
		field : "assetId",
		title : "Vehicle Id"
	}, {
		field : "serialNumber",
		title : "Serial Number"
	} ];

	return ArrColumns;
}

function FnSubmitEquipmentMapPoints(ObjEquipment) {

	var ObjEquipmentEntity = {};
	// ObjEquipmentEntity['equipment'] = {};
	ObjEquipmentEntity['identifier'] = {
		"key" : "identifier",
		"value" : ObjEquipment.identifier
	};
	ObjEquipmentEntity['domain'] = {
		"domainName" : UserInfo.getCurrentDomain()
	};
	ObjEquipmentEntity['entityTemplate'] = {
		"entityTemplateName" : "Asset"
	};
	ObjEquipmentEntity['platformEntity'] = {
		"platformEntityType" : "MARKER"
	};
	ObjEquipmentEntity['equipName'] = ObjEquipment.assetName;
	$('#points_payload').val(JSON.stringify(ObjEquipmentEntity));
	$("#points").submit();
}

function FnCreateAsset() {
	$('#gapp-asset-create').submit();
}
