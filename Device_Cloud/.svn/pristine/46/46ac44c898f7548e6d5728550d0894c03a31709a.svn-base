function writebackValidatorCreate(input){
	if (input.attr('id') == 'c_deviceIP' && input.val() != '') {
		return validateIp(input.val().trim());
	}
	else if (input.attr('id') == 'c_connectedPort' && input.val() != '') {
		return validatePort(input.val().trim());
	}
	else if (input.attr('id') == 'c_writebackPort' && input.val() != '') {
		return validatePort(input.val().trim());
	}
	else if(input.attr('id') == 'createDsName' && input.val() == ''){
		if($('#createPublish').is(':checked')){
			return false;
		}
	}
	return true;
}

function isDSExists(input){
	var VarExist = true;
	if(input.attr('id') == 'createDsName' && input.val() != '' && $('#createPublish').is(':checked')){
	var validate = input.data('available');
	var url = input.data('available-url');
	if (typeof validate !== 'undefined' && validate !== false) {
		url = url.replace("{datasource_name}", input.val().trim());
			
		$.ajax({
				url: "ajax" + url,
				dataType:'json',
			type:'GET',
			async:false,
				success: function(result) {
					
				if (result.entity != null) {
					var ObjExistStatus = result.entity;
					if(ObjExistStatus.status == 'SUCCESS'){ // Exist in db
						VarExist = false;
					} else if(ObjExistStatus.status == 'FAILURE') { // Does not exist in db
						VarExist = true;
					}  
				}
		    },
		    error : function(xhr, status, error) {
		    
		    }
		});
	}}
	return VarExist;
}
	
function writebackValidatorUpdate(input){
	if (input.attr('id') == 'm_deviceIP' && input.val() != '') {
		return validateIp(input.val().trim());
	}
	else if (input.attr('id') == 'm_connectedPort' && input.val() != '') {
		return validatePort(input.val().trim());
	}
	else if (input.attr('id') == 'm_writebackPort' && input.val() != '') {
		return validatePort(input.val().trim());
	}
	else if(input.attr('id') == 'dsName' && input.val() == ''){
		if($('#publish').is(':checked')){
			return false;
		}
	}
	return true;
}

function validateIp(input) {
	var ip = input.split(".");
	return ip.length == 4 && ip.every(function(segment) {
		return validateNum(segment, 0, 255);
	});
}

function validatePort(port) {
	return validateNum(port, 1, 65535);
}

function validateNum(input, min, max) {
	var num = +input;
	return num >= min && num <= max && input === num.toString();
}

function createSomeGrid(data, button) {
	$('#' + button).html('');
	$('#' + button).kendoGrid({
		dataSource : {
			data : data,
			pageSize : 6
		},
		selectable : "single",
		pageable : {
			buttonCount : 5
		},
		scrollable : false,
		navigatable : true,
		columns : [ {
			field : "id",
			title : "ID",
			width : 300
		}, {
			field : "countryId",
			title : "Country Id",
			width : 300
		}, {
			field : "name",
			title : "Country Name"
		} ]
	});

}

function fetchDataSourceName(sourceId){
	$
			.ajax({
				url : "ajax/datasources/devices/"+sourceId+"/datasourcename",
				dataType : 'json',
				contentType : "application/json",
				type : "GET",
				success : function(result) {
					if (result.entity != null) {
						$('#dsName').val(result.entity.datasourceName);
						$('#prevdsName').val(result.entity.datasourceName);
						$('#dsName').attr("readonly", true);
					}
				},
				error : function(xhr, status, error) {
					$('#dsName').attr("readonly", false);
				}
			});

}