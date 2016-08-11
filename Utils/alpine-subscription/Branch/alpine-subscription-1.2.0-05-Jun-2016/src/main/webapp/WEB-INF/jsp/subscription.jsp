<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
<title>Alpine Subscription</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<spring:eval var="storeUrl"
	expression="@propertyConfigurer.getProperty('alpine.subscription.storeurl')" />
<spring:eval var="tier"
	expression="@propertyConfigurer.getProperty('alpine.subscription.storetier')" />
<spring:eval var="subscription"
	expression="@propertyConfigurer.getProperty('alpine.subscription.subscrption')" />

<script src="resources/themes/default/js/jquery.min.js"></script>
<script src="resources/themes/default/js/kendo.all.min.js"></script>
<script src="resources/themes/default/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="resources/themes/default/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/kendo/css/kendo.material.min.css" />
<style>
.requiredAstrix {
	color: #FF3333
}

.successTemp {
	border-width: 1px;
	border-style: solid;
	width: 400px;
	height: 100px
}

.successTemp h3 {
	color: #FFFFFF;
	font-size: 1.2em;
	padding: 32px 10px 5px;
	font-weight: normal
}

.successTemp img {
	float: left;
	margin: 30px 15px 30px 30px;
}

.errorTemp {
	border-width: 1px;
	border-style: solid;
	width: 400px;
	height: 100px
}

.errorTemp h3 {
	color: #FFFFFF;
	font-size: 1.2em;
	padding: 32px 10px 5px;
	font-weight: normal
}

.errorTemp img {
	float: left;
	margin: 30px 15px 30px 30px;
}
</style>

<span id="staticNotification"></span>

<script>
	var staticNotification;
	function initNotifications() {
		staticNotification = $("#staticNotification").kendoNotification({
			position : {
				pinned : true,
				top : 230,
				left : 630
			},
			hideOnClick : true,
			allowHideAfter : 0,
			width : 400,
			height : 100,
			//stacking: "down",                      
			templates : [ {
				type : "error",
				template : $("#errorTemplate").html()
			}, {
				type : "success",
				template : $("#successTemplate").html()
			} ]

		}).data("kendoNotification");
	}

	function validateSubscription(){
			$("#sign-up").submit();
		}
	
	function saveSubscription() {
		initNotifications();
		url = "${subscription}";

		var sample = saveData();
		console.log
		$.ajax({
			url : "ajax" + url,
			contentType : 'application/json',
			dataType : 'json',
			data : JSON.stringify(sample),
			type : "POST",
			success : function(response) {
				clearFields()
				if(response.entity.status){
					 staticNotification.show({
						 message:response.entity.status
					 }, "success");
					}
				else if (response.entity.errorMessage){
						 staticNotification.show({
							 message:response.entity.errorMessage
						 }, "error");
					}
				
			},
			error : function(xhr, status, error) {
				console.log("error");
				 staticNotification.show({
					 message:"error"
				 }, "error");
			}
		});

	}

	function saveData() {

		var data = {};
		data["userName"] = $("#username").val();
		data["password"] = $("#password").val();
		data["firstName"] = $("#firstname").val();
		data["lastName"] = $("#lastname").val();
		data["email"] = $("#email").val();
		data["description"] = $("#description").val();
		data["application"] = $("#application").val();

		data["tier"] = "${tier}"
		data["content"] = "${storeUrl}";

		return data;

	}

	function clearFields() {
		$("#username").val("");
		$("#password").val("");
		$("#passwordCfm").val("");
		$("#firstname").val("");
		$("#lastname").val("");
		$("#email").val("");
		$("#description").val("");
		$("#application").val("");
	}

	function setFormValidation() {
		$("#passwordCfm").attr('mandatory','Enter valid Re-type Password');
		$("#passwordCfm").attr('match',
				'Password and Re-type Password should be mach');

		$("#username").attr('required', '');
		$("#username").attr('validationMessage', 'Enter valid Username');

		$("#password").attr('required', '');
		$("#password").attr('validationMessage', 'Enter valid password');

		$("#email").attr('required', '');
		$("#email").attr('validationMessage', 'Enter valid Email');

		$("#firstname").attr('required', '');
		$("#firstname").attr('validationMessage', 'Enter valid First Name');

		$("#lastname").attr('required', '');
		$("#lastname").attr('validationMessage', 'Enter valid Last Name');

		$("#application").attr('required', '');
		$("#application").attr('validationMessage', 'Enter valid Application');

		$("#sign-up")
				.kendoValidator(
						{
							validateOnBlur : true,
							errorTemplate : "<span style='color:red'>#=message#</span>",
							rules : {
								mandatory : function(input) {

									if (input.attr('id') == 'passwordCfm'
											&& input.val() == '') {
											return false;
									}
									return true;
								},
								match : function(input) {

									if (input.attr('id') == 'passwordCfm'
											&& input.val() != '') {
										if($("#password").val() == $("#passwordCfm").val()){
											return true;
										}
										else{
											return false;
											}
									}
									return true;
								}
							},
							messages : {
								mandatory : function(input) {
									return input.attr("mandatory");
								},
								match : function(input) {
									return input.attr("match");
								}
							}
						});
	}
</script>
<script id="errorTemplate" type="text/x-kendo-template">
                <div class="errorTemp">
                   <img src="resources/themes/default/images/icons/white/error-icon.png" />            
                    <h3>#= message #</h3>
                </div>
          </script>

<script id="successTemplate" type="text/x-kendo-template">
                <div class="successTemp">
                    <img src="resources/themes/default/images/icons/white/success-icon.png" />
                    <h3>#= message #</h3>
                </div>
          </script>

</head>
<body>

	<br>
	<div class="col-md-2"></div>
	<div class="col-md-8">
		<div class="well" style="background: #E9ECEF">
			<div class="container" style="margin: 0px auto;">
				<br>
				<form id="sign-up">
					<div class="form-group col-md-5">
						<label for="username">Username:</label><span
							class="requiredAstrix">*</span> <input id="username"
							name="username" minlength="5" type="text"
							class="form-control required validName noSpace">
					</div>
					<label class="lblTenantDomainName" for="username"> </label> <input
						id="hiddenTenantDomain" type="hidden" value="carbon.super">
					<div class="form-group col-md-5">
						<label for="password">Password:</label><span
							class="requiredAstrix">*</span> <input type="password"
							class="form-control password" id="password" name="password">
					</div>
					<div class="form-group col-md-5">
						<label for="firstname">First Name:</label><span
							class="requiredAstrix">*</span> <input type="text"
							class="form-control " id="firstname" name="firstname">
					</div>
					<div class="form-group col-md-5">
						<label for="passwordCfm">Re-type Password:</label><span
							class="requiredAstrix">*</span> <input type="password"
							class="form-control password" id="passwordCfm" name="passwordCfm">
					</div>

					<div class="form-group col-md-5">
						<label for="lastname">Last Name:</label><span
							class="requiredAstrix">*</span> <input type="text"
							class="form-control " id="lastname" name="lastname">
					</div>
					<div class="form-group col-md-5">
						<label for="email">Email:</label><span class="requiredAstrix">*</span>
						<input type="email" class="form-control email" id="email"
							name="email" value='riyas@pacificcontrols.net'>
					</div>
					<div class="form-group col-md-5">
						<label for="application ">Application:</label><span
							class="requiredAstrix">*</span> <input type="text"
							class="form-control" id="application" name="application">
					</div>
					<div class="form-group col-md-5">
						<label for="description">Desctription:</label>
						<textarea class="form-control" name="description" row="3"
							path="description" id="description" tabindex="14" maxlength="250"></textarea>
					</div>
					<div class="col-md-10">
						<button type="button" id="btn-create"
							class="btn btn-default pull-right" onclick="validateSubscription()">Save</button>
					</div>
				</form>
			</div>
		</div>

	</div>
	<div class="col-md-2"></div>

</body>
<script type="text/javascript">
	setFormValidation();

	$("#sign-up").submit(function( event ) {
		  event.preventDefault();
		  saveSubscription();
		});
</script>
</html>