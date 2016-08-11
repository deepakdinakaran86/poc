<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:eval var="checkMarkerFieldUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.validateUser')" />
<spring:eval var="gatAllRolesUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.listRole')" />	
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="resources/themes/default/js/jquery.min.js"></script>

<link rel="stylesheet"
	href="resources/kendo/css/kendo.common-material.min.css" />
<link rel="stylesheet" href="resources/kendo/css/kendo.material.min.css" />

<script src="resources/themes/default/js/jquery.min.js"></script>
<script src="resources/themes/default/js/kendo.all.min.js"></script>
<script src="resources/js/common/asset.validation.js"></script>
<style>
.requiredAstrix {
	color: #FF3333
}
</style>

<script type="text/javascript">
	function isEmail(email) {
		var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z])+\.)+([a-zA-Z]{2,4})+$/;
		return regex.test(email);
	}
	function isMobileno(number) {
		var regex = /^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/
		return regex.test(number);
	}
		function isUserName(userName) {
		var regex = /^[^~!#$;%^*+={}\|\\<>,'"]{5,30}$/;
		return regex.test(userName);
	}
	var mobile;
	var email;
	initNotifications();
	var response = '${response}';
	if (response != undefined && response != '') {
		 staticNotification.show({
			 message:response
		 }, "success");
	}
	function submitBackAction() {
		$("#userHome").submit();
	}

	function populateRole() {
		var roleList = [];
		$.ajax({
			url : "ajax" + "${gatAllRolesUrl}",
			dataType : 'json',
			type : "GET",
			async: false,
			success : function(response) {

				if (response.entity != null) {
					$.each(response.entity,
							function(index, object) {
						roleList.push(object.roleName);
					});
				}
			},
			error : function(xhr, status, error) {
				options.success({});
			}
		});
		
		var required = $("#roleNameList").kendoMultiSelect({
			  dataSource: {
				    data: roleList
				  }
		});

		required.data("kendoMultiSelect").value($("#roleName").val().split(','));

	}

	function populateStatus() {
		var status = [ {
			"status" : "ACTIVE"
		}, {
			"status" : "INACTIVE"
		} ]
		var data = $("#status").kendoDropDownList({
			dataTextField : "status",
			dataValueField : "status"
		}).data().kendoDropDownList;
		data.dataSource.data(status);
		if ('${action}' != 'Create') {
			var status = '${user_form.status}';
			if (status != '') {
				data.text(status);
				data.value(status);
			}
		}
	}

	function setFormValidation() {
		$("#userName").attr('mandatory', 'Enter valid User Name');
		$("#userName").attr('format', 'User Name is not valid');
		$("#userName").attr('unique', 'User Name is not unique');

		$("#firstName").attr('required', '');
		$("#firstName").attr('validationMessage', 'Enter valid First Name');

		$("#lastName").attr('required', '');
		$("#lastName").attr('validationMessage', 'Enter valid Last Name');

		$("#contactNumber").attr('mandatory', 'Enter valid  Contact Number');
		$("#contactNumber").attr('format', 'Enter valid  Contact Number');
		$("#contactNumber").attr('unique', ' Contact Number is not unique');

		$("#emailId").attr('unique', 'Email Id is not unique');
		$("#emailId").attr('format', 'Enter valid Email Id');
		$("#emailId").attr('mandatory', 'Enter valid Email Id');

		$("#user_form")
				.kendoValidator(
						{
							validateOnBlur : true,
							errorTemplate : "<span style='color:red'>#=message#</span>",
							rules : {
								mandatory : function(input) {

									if (input.attr('id') == 'userName'
											&& input.val() == '') {
										return false;
									} else if (input.attr('id') == 'contactNumber'
											&& input.val() == '') {
										return false;
									} else if (input.attr('id') == 'emailId'
											&& input.val() == '') {
										return false;
									}
									return true;
								},
								format : function(input) {
									if (input.attr('id') == 'emailId') {
										return isEmail(input.val());
									}else if (input.attr('id') == 'contactNumber') {
										return isMobileno(input.val());
									}else if (input.attr('id') == 'userName') {
										return isUserName(input.val());
									}
									return true;
								},
								unique : function(input) {

									if (input.attr('id') == 'userName') {
										return checkUniqueness(
												"${checkMarkerFieldUrl}", input
														.attr('id'), input
														.val());
									} else if (input.attr('id') == 'contactNumber'
											&& mobile != input.val()) {
										return checkUniqueness(
												"${checkMarkerFieldUrl}", input
														.attr('id'), input
														.val());
									} else if (input.attr('id') == 'emailId'
											&& email != input.val()) {
										return checkUniqueness(
												"${checkMarkerFieldUrl}", input
														.attr('id'), input
														.val());
									}
									return true;
								}

							},
							messages : {
								unique : function(input) {
									return input.attr("unique");
								},
								format : function(input) {
									return input.attr("format");
								},
								mandatory : function(input) {
									return input.attr("mandatory");
								}
							}
						});
	}
	function submitAction() {
		var roleNameList = $("#roleNameList").data("kendoMultiSelect");
		$("#roleName").val(roleNameList.value());
		$("#user_form").submit();
	}
</script>
<div style="height: 1px;">
	<form id="userHome" action="user_home" method="get"></form>
</div>

<form:form role="form" action="user_create" commandName="user_form"
	id="user_form" autocomplete="off" novalidate="novalidate" method="post">
	<form:hidden path="action" />
	<form:hidden path="identifier" />
	<div style="width: 60%">
		<div class="grid" style="border-width: 10px;">
			<div class="row cells2">
				<div class="cell">
					<label for="entityname">User Name<span
						class="requiredAstrix">*</span></label> 
					<div class="input-control text input-control text full-size">
						<form:input path="userName" id="userName" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label>First Name<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="firstName" id="firstName" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell">
					<label>Last Name<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="lastName" id="lastName" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label>Email Id<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="emailId" id="emailId"
							class="form-control" />
					</div>
				</div>

			</div>

			<div class="row cells2">
				<div class="cell">
					<label>Contact Number<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="contactNumber" id="contactNumber"
							class="form-control" />
					</div>
				</div>

								<div class="cell">
					<label>Role Name<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:hidden id="roleName" path="roleName" class="form-control" />
						
       					<select id="roleNameList" multiple="multiple" data-placeholder="Select roles...">
       					</select>
        
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell">
					<label>Status</label>
					<div class="input-control text input-control text full-size">
						<form:input id="status" path="status" class="form-control" />
					</div>
				</div>
				<div class="cell"></div>

			</div>


		</div>

	</div>
	</div>
</form:form>
<div>
	<button class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;" id="user-btn-save"
		onclick="submitAction();">Save</button>
	<button onclick="submitBackAction();"
		class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;">Back</button>
</div>

<script type="text/javascript">
	if ('${action}' === 'View') {
		$("#user_form :input").prop("disabled", true);
		$("#user-btn-save").hide();
	} else {
		mobile = '${user_form.contactNumber}';
		email = '${user_form.emailId}';
		setFormValidation();
	}
	if ('${action}' === 'Update') {
		$("#userName").prop('readonly', true);
	}
	populateRole();
	populateStatus();
</script>