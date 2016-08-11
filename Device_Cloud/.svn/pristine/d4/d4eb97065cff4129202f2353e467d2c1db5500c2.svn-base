/**
 * parameterconfiguration.js 7/13/2015 - PCSEG339 Kenny Chavez
 */

$(document).on('click', '#mapParameterTemplateGrid .k-i-refresh', function() {
	$("#mapParameterTemplateGrid").data('kendoGrid').dataSource.data([]);
	$("#mapParameterTemplateGrid").data('kendoGrid').dataSource.data(data);
	// getAllLogData();
});

var grid = $("#mapParameterTemplateGrid")
		.kendoGrid(
				{
					dataSource : {
						pageSize : 20

					},
					// toolbar: kendo.template($("#buttonTemplate").html()),
					height : 500,
					sortable : true,
					pageable : true,
					// pageable: {
					// refresh: true,
					// pageSizes: true,
					// previousNext: true
					// },
					// editable: {
					// confirmation: false
					// },
					scrollable : true,
					columns : [
							{
								field : "parameterName",
								title : "Parameter Name",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Parameter Name<strong>"
							},
							{
								field : "physicalQuantity",
								title : "Physical Quantity",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Physical Quantity<strong>"
							},
							{
								field : "dataType",
								title : "Data Type",
								width : 50,
								editable : false,
								headerTemplate : "<strong style='color:black ;' >Data Type<strong>"
							} ]
				});

$("#createConfigTemplate").on('click', function() {
	// var button = {
	// "text" : "CONFIGURATION",
	// "id" : "configTemplate",
	// "url" : "configTemplate"
	// };
	// addNewTab(button);
});

// drop down for Physical Quantity
$("#paramConfigPhysicalQuantity").kendoDropDownList({
	dataTextField : "text",
	dataValueField : "value",
	dataSource : [ // test data
	{
		text : "paramConfigPhysicalQuantity1",
		value : "paramConfigPhysicalQuantity1"
	}, {
		text : "paramConfigPhysicalQuantity2",
		value : "paramConfigPhysicalQuantity2"
	}, {
		text : "paramConfigPhysicalQuantity3",
		value : "paramConfigPhysicalQuantity3"
	} ],
	index : 0
// ,change: onChange
});

// drop down for Data Type
$("#paramConfigDataType").kendoDropDownList({
	dataTextField : "text",
	dataValueField : "value",
	dataSource : [ // test data
	{
		text : "paramConfigDataType1",
		value : "paramConfigDataType1"
	}, {
		text : "paramConfigDataType2",
		value : "paramConfigDataType2"
	}, {
		text : "paramConfigDataType3",
		value : "paramConfigDataType3"
	} ],
	index : 0
// ,change: onChange
});
