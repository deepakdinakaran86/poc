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
			alert("Error in Checking Uniqueness");
			status = true;
		}
	});

	return status;

}