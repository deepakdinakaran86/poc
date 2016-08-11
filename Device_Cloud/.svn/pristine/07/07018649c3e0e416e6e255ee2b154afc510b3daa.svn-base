var sourceId ;
function loadPointsGrid(url) {

	// Kendogrid for device list
	$("#write_back_grid")
			.kendoGrid(
					{
						resizable : true,
						dataSource : {
							type : "json",
							transport : {
								read : "ajax" + url
							},
							schema : {
								data : function(response) {
									if (response.entity != null) {
										return response.entity;
									} else {
										staticNotification.show({
							                message: "No Parameters found"
							            }, "error");
									}

								},
								total : function(response) {
									return $(response.entity).length;
								},
								model : {
									fields : {
										customTag : {
											type : "string",
											editable : false
										},
										type : {
											type : "string",
											editable : false
										},
										value : {
											type : "string",
											editable : true
										}
									}
								}
							},
							error : function(xhr, status, error) {
								//var errorMessage = jQuery.parseJSON(xhr.xhr.responseText).errorMessage.errorMessage;
								staticNotification.show({
					                message: "No Parameters found"
					            }, "error");
							},
							pageSize : 10,
							page : 1,
							serverPaging : false,
							serverFiltering : false,
							serverSorting : false,
							sort : {
								field : "customTag",
								dir : "asc"
							},
							filter : {
								logic : 'and',
								filters : [ {
									field : "pointAccessType",
									operator : "eq",
									value : 'WRITEABLE'
								} ]
							}

						},
						selectable : "single",
						height : 540,
						sortable : {
							mode : "single",
							allowUnsort : true
						},
						pageable : {
							refresh : false,
							pageSizes : true,
							previousNext : true
						},
						columnMenu : false,
						editable : true,
						columns : [
								{
									field : "customTag",
									title : "Custom Tag",
									editable : false,
								    headerTemplate :"<strong style='color:black ;' >Custom Tag<strong>"
								},
								{
									field : "type",
									title : "Datatype",
									editable : false,
								    headerTemplate :"<strong style='color:black ;' >Datatype<strong>"
								},
								{
									field : "value",
									title : "Value",
									editable : true,
								    headerTemplate :"<strong style='color:black ;' >Value<strong>"
								},
								{
									command : {
										text : "Execute",
										click : function(e) {
											var tr = $(e.currentTarget).closest("tr");
											var row = $("#write_back_grid")
													.data("kendoGrid")
													.dataItem(tr);
											writeBack(row);
										}
									},
									title : "Execute",
								    headerTemplate :"<strong style='color:black ;' >Execute<strong>"
								} ]

					}).data("kendoGrid");

}

function writeBack(row) {
	var value = row.value;
	if (value == undefined) {
		staticNotification.show({
            message:"Value not specified"
        }, "error");
	} 
	else{
	var data = {};
	data.sourceId = sourceId;
	data.command = '0x22';
	data.requestId = 12;
	data.pointId = row.pointId;
	data.data = value;
	data.priority = 8 ;

	var dataString=JSON.stringify(data);
	console.log(dataString);
	$.ajax({
		url : "ajax/datasources/g2021/commands",
		dataType : 'json',
		type : 'POST',
		contentType : "application/json",
		data : dataString,
		success : function(response) {
			 staticNotification.show({
                 message:"Command has been queued successfully"
             }, "success");

		},
		error : function(xhr, status, error) {
			console.log("error"+xhr.responseText);
			var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
			staticNotification.show({
                message: errorMessage
            }, "error");
		}
	});
	}
}
