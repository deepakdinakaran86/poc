/**
 * parameterdeviceconfiguration.js 7/13/2015 - PCSEG339 Kenny Chavez
 */

function insertItemTomapParameterTemplateGrid() {
	var grid = $("#mapParameterTemplateGrid").data("kendoGrid");
	grid.addRow();
	$(".k-grid-edit-row").appendTo("#mapParameterTemplateGrid tbody");

}

// create grid
function mapParameterTemplateGrid() {

	var testdata = [ {
		"deviceIO" : "Polyester",
		"pointName" : "GH_453",
		"parameterName" : "",
		"systemTag" : "i1",
		"unitOfMeasurement" : "Unitless",
		"access" : ""
	}, {
		"deviceIO" : "test2",
		"pointName" : "LG_765",
		"parameterName" : "",
		"systemTag" : "i2",
		"unitOfMeasurement" : "Unitless",
		"access" : ""
	} ];

	templateGridName = $("#templateContainer").val();
	var grid = $("#mapParameterTemplateGrid")
			.kendoGrid(
					{
						dataSource : {
							data : testdata,
							pageSize : 20,
							schema : {
								model : {
									id : "deviceIO",
									fields : {
										deviceIO : {
											editable : false,
											nullable : true,
											type : 'string'
										},
										pointName : {
											editable : false,
											nullable : true,
											type : 'string'
										},
										parameterName : {
											editable : true,
											nullable : true,
											type : 'string'
										},
										systemTag : {
											editable : true,
											nullable : true,
											type : 'string'
										},
										access : {
											editable : true,
											nullable : true,
											type : 'string'
										},
										dataType : {
											editable : true,
											nullable : true,
											type : 'string'
										},
										physicalQuantity : {
											editable : true,
											nullable : true
										},
										customTag : {
											editable : true,
											nullable : true,
											type : 'string'
										},
										unitOfMeasurement : {
											editable : true,
											nullable : true,
											type : 'string',
											defaultValue : {
												"name" : "Unitless"
											}
										},
										acquisition : {
											editable : true,
											nullable : true,
											type : 'string'
										}
									}
								}
							}
						},
						toolbar : kendo.template($("#filterTemplate").html()),
						// toolbar: ["create"],
						height : 500,
						sortable : true,
						pageable : true,
						// filterable: {
						// extra: false

						// },
						// pageable: {
						// refresh: true,
						// pageSizes: true,
						// previousNext: true
						// },
						navigatable : true,
						editable : {
							confirmation : false,
							createAt : "bottom"
						},
						scrollable : true,
						columns : [
								{
									field : "deviceIO",
									title : "Device I/O",
									width : 50,
									headerTemplate : "<strong style='color:black ;' >Device I/O<strong>",
									filterable : true,
									editable : false,
									editor : deviceIoDropDownEditor
								},
								{
									field : "pointName",
									title : "Point Name",
									width : 50,
									editable : false,
									headerTemplate : "<strong style='color:black ;' >Point Name<strong>",
								},
								{
									field : "parameterName",
									title : "Parameter Name",
									width : 50,
									editable : true,
									headerTemplate : "<strong style='color:black ;' >Parameter Name<strong>",
									editor : parameterNameDropDownEditor,
									attributes : {
										style : "  background-color: rgb(190, 253, 164)"
									}
								},
								{
									field : "customTag",
									title : "Custom Tag",
									width : 50,
									editable : true,
									headerTemplate : "<strong style='color:black ;' >Custom Tag<strong>",
									editor : customTagDropDownEditor,
									attributes : {
										style : "  background-color: rgb(190, 253, 164)"
									}
								},
								{
									field : "systemTag",
									title : "System Tag",
									width : 50,
									editable : true,
									headerTemplate : "<strong style='color:black ;' >System Tag<strong>",
									editor : systemTagDropDownEditor,
									attributes : {
										style : "  background-color: rgb(190, 253, 164)"
									}
								},
								{
									field : "physicalQuantity",
									title : "Physical Quantity",
									width : 50,
									editable : false,
									headerTemplate : "<strong style='color:black ;' >Physical Quantity<strong>"
								},
								{
									field : "unitOfMeasurement",
									title : "Unit",
									width : 50,
									editable : true,
									headerTemplate : "<strong style='color:black ;' >Unit<strong>",
									editor : unitDropDownEditor,
									attributes : {
										style : "  background-color: rgb(190, 253, 164)"
									}
								},
								{
									field : "dataType",
									title : "Data Type",
									width : 50,
									editable : false,
									headerTemplate : "<strong style='color:black ;' >Data Type<strong>"
								// editor: dataTypeDropDownEditor

								},
								{
									field : "access",
									title : "Access",
									width : 50,
									editable : true,
									headerTemplate : "<strong style='color:black ;' >Access<strong>",
									editor : accessDropDownEditor,
									attributes : {
										style : "  background-color: rgb(190, 253, 164)"
									}
								},
								{
									field : "acquisition",
									title : "Acquisition",
									width : 50,
									editable : false,
									headerTemplate : "<strong style='color:black ;' >Acquisition<strong>",
									attributes : {
										style : "  background-color: rgb(190, 253, 164)"
									}
								},
								{
									command : [ {
										name : "destroy",
										text : " "
									} ],
									headerTemplate : "<strong style='color:black ;' >Action<strong>",
									width : 70
								}

						],
						remove : function(e) {
							var grid = $("#mapParameterTemplateGrid").data(
									"kendoGrid");
							var dataSource = grid.dataSource;

							// records on current view / page
							var recordsOnCurrentView = dataSource.view().length;
							if (recordsOnCurrentView == 1) {
								insertItemTomapParameterTemplateGrid();
							}
						}

					}).data("kendoGrid");

}

mapParameterTemplateGrid();

$(document).ready(function() {
	// insertItemTomapParameterTemplateGrid();

	$("#filterTemplateGrid").keyup(function() {
		var val = $("#filterTemplateGrid").val();

		$("#mapParameterTemplateGrid").data("kendoGrid").dataSource.filter({
			logic : "or",
			filters : [ {
				field : "deviceIO",
				operator : "contains",
				value : val
			}, {
				field : "pointName",
				operator : "contains",
				value : val
			}, {
				field : "parameterName",
				operator : "contains",
				value : val
			}, {
				field : "customTag",
				operator : "contains",
				value : val
			}, {
				field : "systemTag",
				operator : "contains",
				value : val
			}, {
				field : "physicalQuantity",
				operator : "contains",
				value : val
			}, {
				field : "unitOfMeasurement",
				operator : "contains",
				value : val
			}, {
				field : "dataType",
				operator : "contains",
				value : val
			}, {
				field : "access",
				operator : "contains",
				value : val
			}, {
				field : "acquisition",
				operator : "contains",
				value : val
			} ]
		});

	});

	$("#createConfigTemplate").on('click', function() {
		// save config
	});

});
