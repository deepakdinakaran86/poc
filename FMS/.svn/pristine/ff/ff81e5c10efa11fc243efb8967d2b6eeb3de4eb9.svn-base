function checkUniqueness(serviceURL,field, value) {

	var status = false;
	var json = {
			"fieldValues" : [ {
				"key" : field,
				"value" : value
			} ]
	};

	$.ajax({
		url : "ajax/" + serviceURL,
		dataType : 'json',
		type : "POST",
		async : false,
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		data : JSON.stringify(json),
		success : function(response) {
			var message = response.entity;
			if (message != null && message.status == 'SUCCESS') {
				status = true;
			}
		},
		error : function(xhr, status, error) {
			console.log("Error in Checking Uniqueness"+error);
			status = true;
		}
	});

	return status;

}

function checkUniquenessWithTemplate(serviceURL,field, value,template) {

	var status = false;
	var json = {
			"fieldValues" : [ {
				"key" : field,
				"value" : value
			} ],
			"entityTemplate":{
				"entityTemplateName":template
			}
	};

	$.ajax({
		url : "ajax/" + serviceURL,
		dataType : 'json',
		type : "POST",
		async : false,
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		data : JSON.stringify(json),
		success : function(response) {
			var message = response.entity;
			if (message != null && message.status == 'SUCCESS') {
				status = true;
			}
		},
		error : function(xhr, status, error) {
			console.log("Error in Checking Uniqueness"+error);
			status = true;
		}
	});

	return status;

}

function FnResTagList(response){
	var message = response.entity;
	if (message != null && message.status == 'SUCCESS') {
		status = true;
	}else{
		status = false;
	}
}	


