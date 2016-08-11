"use strict";

$(document).ready(
		
		function() {
			$(".k-grid-content").css("overflow", "scroll").css("overflow-x",
					"auto").scroll(function() {
				var left = $(this).scrollLeft();
				var wrap = $(".k-grid-header-wrap");
				if (wrap.scrollLeft() != left)
					wrap.scrollLeft(left);
			});

			//FnInitializeAssetListGrid();
});

$(window).load(function() {
	FnGetPoiTypes();
	FnGetPoiList();
});

function FnInitializeAssetListGrid() {
	var ArrGridColumns = FnGetGridColumns();
			
	var ObjGridConfig = {
		"scrollable" : true,
		"height":420,
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
	FnDrawGridView('#poi-list', [], ArrGridColumns, ObjGridConfig);
}

function FnGetPoiTypes() {
	var VarUrl = 'ajax/galaxy-poi/1.0.0/poiType?domain_name='+ UserInfo.getCurrentDomain();
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '','application/json; charset=utf-8', 'json', FnResPoiTypes);
}

function FnResPoiTypes(response) {
	var ArrResponse = response.entity;
	if ($.isArray(ArrResponse)) {
		var VarAssetTypesTxt = '<option value=""> All</option>';
		$.each(ArrResponse, function(key, val) {
			VarAssetTypesTxt += '<option value="' + val.poiType + '">'
					+ val.poiType + '</option>';
		});
		$('#assetCategory').html(VarAssetTypesTxt);
	}

}

function FnCreatePOI() {
	$('#poi_create').submit();
}
function FnGetPoiList(VarPoiType) {
	
	$("#GBL_loading").show();

	if (VarPoiType != undefined) {
		if (VarPoiType != '') {
			var VarUrl = 'ajax/galaxy-poi/1.0.0/poi?domain_name='+ UserInfo.getCurrentDomain() + "&poi_type=" + VarPoiType;
		} else {
			var VarUrl = 'ajax/galaxy-poi/1.0.0/poi?domain_name='+ UserInfo.getCurrentDomain();
		}
	} else {
		var VarUrl = 'ajax/galaxy-poi/1.0.0/poi?domain_name='+ UserInfo.getCurrentDomain();
	}

	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '','application/json; charset=utf-8', 'json', FnResPoiList);
}

function FnResPoiList(response) {
	
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

				if (this.poiTypeValues != undefined) {
					$.each(this.poiTypeValues, function() {
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
				element['poiName'] = this.poiName;
				element['poiType'] = this.poiType;
				
				if (typeof(this.poiIdentifier) != "undefined")
				{					
					element['identifier'] = this.poiIdentifier.value;
				}
				else{ 
					console.log(this.poiIdentifier);
				}
			
				element['description'] = this.description;
				element['latitude'] = this.latitude;
				element['longitude'] = this.longitude;
				element['radius'] = this.radius;
				ArrResponseData.push(element);

			});
		} else {

			$.each(ArrResponse, function() {
				var element = {};
				element['poiName'] = this.poiName;
				element['poiType'] = this.poiType;				
				element['identifier'] = this.poiIdentifier.value;
				element['description'] = this.description;
				element['latitude'] = this.latitude;
				element['longitude'] = this.longitude;
				element['radius'] = this.radius;
				ArrResponseData.push(element);
			});

		}

	} else {
		var ArrResponseData = [];
	}

	
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
		field : "poiName",
		dir : "asc"
	}

	var grid = $("#poi-list").data("kendoGrid");
	if (grid != undefined) {
		grid.destroy();
		$('#poi-list').html('');
	}

	//$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#poi-list', objDatasource, ArrGridColumns, ObjGridConfig);

	$("#poi-list").data("kendoGrid").tbody.on("click", ".grid-asset-view",
			function(e) {
				var tr = $(this).closest("tr");
				var data = $("#poi-list").data('kendoGrid').dataItem(tr);
				data = data.identifier;
				$("#update_value").val(data);
				$("#domain").val(UserInfo.getCurrentDomain());
				$("#poi_view").submit();
			});

}

function FnGetGridColumns() {

	var ViewLink = "<a class='grid-asset-view capitalize'>#=poiName#</a>";
	var PointMapLink = " <a class='grid-asset-mappoints' data-toggle='tooltip' data-placement='left' title='Map points'>Map Points</a>";

	var VarActionsLink = '<div class="asset-actions">';
	VarActionsLink += '<a  href="javascript:void(0);" class="grid-asset-dashboard"  data-toggle="tooltip" data-placement="bottom" title="Asset Dashboard"><i class="smicon sm-metericon"></i></a>'
			+ PointMapLink;
	VarActionsLink += '</div>';

	var ArrColumns = [ {
		field : "poiName",
		title : "POI Name",
		template : ViewLink
	}, {
		field : "poiType",
		title : "POI Type"
	}, {
		field : "description",
		title : "Description"
	}, {
		field : "latitude",
		title : "Latitude"
	}, {
		field : "longitude",
		title : "Longitude"
	},

	{
		field : "radius",
		title : "Radius"
	} ];

	return ArrColumns;
}
