<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="VarForgotPasswordUrl"
	expression="@propertyConfigurer.getProperty('fms.services.forgotPassword')" />
	
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="resources/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/AdminLTE.min.css">
<link rel="stylesheet" href="resources/plugins/BigVideo/css/bigvideo.css">

<style>
body {
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 14px;
	line-height: 1.42857143;
	color: #333;
	background-color: #2f8dce;
}

a {
	color: #3c8dbc;
	font-size: 13px;
}

.btn {
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
}

.icheckbox_square-blue,.iradio_square-blue {
	display: inline-block;
	*display: inline;
	vertical-align: middle;
	margin: 0;
	padding: 0;
	width: 22px;
	height: 22px;
	background: url(blue.png) no-repeat;
	border: none;
	cursor: pointer;
}

.icheckbox_square-blue {
	background-position: 0 0;
}

.icheckbox_square-blue.hover {
	background-position: -24px 0;
}

.icheckbox_square-blue.checked {
	background-position: -48px 0;
}

.icheckbox_square-blue.disabled {
	background-position: -72px 0;
	cursor: default;
}

.icheckbox_square-blue.checked.disabled {
	background-position: -96px 0;
}

.iradio_square-blue {
	background-position: -120px 0;
}

.iradio_square-blue.hover {
	background-position: -144px 0;
}

.iradio_square-blue.checked {
	background-position: -168px 0;
}

.iradio_square-blue.disabled {
	background-position: -192px 0;
	cursor: default;
}

.iradio_square-blue.checked.disabled {
	background-position: -216px 0;
}

.login-box-msg,.register-box-msg {
	margin: 0;
	text-align: center;
	padding: 0 20px 20px 20px;
}

.social-auth-links {
	margin: 10px 0;
}

.vertical-center {
	min-height: 100%;
	min-height: 100vh;
	display: flex;
	align-items: center;
	/* background-image: url('resources/themes/default/images/template/loginbg.jpg'); 
    background: #19a2c6 url("resources/themes/default/images/template/bg567.png");
	background:#19a2c6 url("resources/themes/default/images/template/slide_img1.jpeg");*/
	background-repeat: no-repeat;
	background-size: cover;
	background-position: left 10%;
	/* background: linear-gradient(#19a2c6, #c8aad6); */
}

.container-center {
	background-color: #fff;
	margin: 0 auto;
	/* padding: 80px 30px;*/
	padding: 30px 30px;
	border-radius: 2px;
	border: 2px solid rgba(255, 255, 255, 0.6);
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.16), 0 2px 10px
		rgba(0, 0, 0, 0.12);
}

.container-center:before {
	content: "";
	background-image:
		url('resources/themes/default/images/template/galaxy-2021-logo.png');
	position: absolute;
	width: 170px;
	top: 13%;
	height: 100%;
	background-size: contain;
	background-repeat: no-repeat;
	left: 30%;
	background-position: top left;
}

.login-logo {
	background-image:
		url('resources/themes/default/images/template/galaxy-2021-logo.png');
}

.input-group-addon {
	border: 1px solid #9b9a9b !important;
	border-radius: 4px 0px 0px 4px;
	background-color: #fff;
}

.glyphicon-user,.glyphicon-lock {
	color: #464646;
}

.btn-primary {
	background: #00B8F1;
	border: 0px;
}

.dc_errorbox_spacing {
	padding: 5px 8px;
	border-radius: 3px;
}

.form-control {
	border-radius: 0;
	box-shadow: none;
	border-color: #d2d6de;
}

form-control {
	display: block;
	width: 100%;
	height: 34px;
	padding: 6px 12px;
	font-size: 14px;
	line-height: 1.42857143;
	color: #555;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow
		ease-in-out .15s;
	-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out
		.15s;
	transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}

.btn-primary {
	background-color: #3c8dbc;
	border-color: #367fa9
}

.btn-primary:hover,.btn-primary:active,.btn-primary.hover {
	background-color: #367fa9
}

.has-feedback .form-control {
	padding-right: 42.5px;
}

.checkbox,.radio {
	position: relative;
	display: block;
	margin-top: 10px;
	margin-bottom: 10px;
}

.outer-container {
	background-image:
		url('resources/themes/default/images/template/Galaxy_Interface.jpg');
	background-size: cover;
}

.main-footer {
	display: none
}

.login-box-body a#logo-img {
	margin: 0 auto;
	text-align: center;
	position: relative;
	display: block;
	margin: 0 0 12px 0;
}

.dc_errorbox_spacing {
	padding: 5px 8px;
	border-radius: 3px;
	color: #dd4b39;
	text-align: center;
	font-size: 13px;
	background: transparent;
}

.hide {
	display: none !important;
}

.show {
	display: block !important;
}
section.form-group {
    margin: 0 auto !important;
    display:block;
}
section.form-group input {   
    margin: 6px 0;
}

.login-box-body {
    box-shadow: 1px 1px #cfcfcf;
}

#gapp-forgotpassword-container span.glyphicon.glyphicon-user.form-control-feedback,
#gapp-forgotpassword-container span.glyphicon.glyphicon-envelope  {
    margin: 6px 15px 0 0 !important;
}
.form-actions {
    margin: 12px 0 0 0;
}
</style>
</head>
<body class="hold-transition login-page outer-container"
	style="background-image: url('resources/themes/default/images/template/Galaxy_Interface.jpg'); background-size: cover;">
<div class="home-top-menu">e</div>
	<div class="login-box" id="overview">
		<div class="login-logo"">
			<!--   <a href="#"><img src="resources/themes/default/images/template/galaxy-2021-fms-logo.png" /></a> -->
		</div>

		<!-- /.login-logo -->
		<div class="login-box-body" style="position: relative;">
			<!--   <p class="login-box-msg">Sign in to start your session</p>-->
			<a href="#" id="logo-img"><img
				src="resources/themes/default/images/template/galaxy-2021-fms-logo.png" /></a>
			<div id="gapp-login-container">
				<form id="loginForm" role="form" action="authenticate" name="form"
					method="post">
					<c:if test="${not empty error}">
						<p class="dc_errorbox_spacing bg-danger">Error:
							${error.getErrorMessage()}</p>
					</c:if>
					<%
						String statusResponse = request.getParameter("response");
						if (statusResponse != null) {
					%>
					<%=statusResponse%>
					<span id="staticNotification"></span>
					<%
						}
					%>
					<div class="form-group has-feedback">
						<input class="form-control dc_inputstyle" type="text"
							name="userName" id="userName" value=" "
							placeholder="Username" required /> <span
							class="glyphicon glyphicon-user form-control-feedback autocomplete-off" autocomplete="false"></span>
					</div>
					<div class="form-group has-feedback">
					
						<input class="form-control dc_inputstyle" type="password"
							name="password" id="password" value="" id="inputPassword"
							placeholder="Password" required /> <span
							class="glyphicon glyphicon-lock form-control-feedback autocomplete-off" autocomplete="false"></span>
					</div>
				
				
				
					<div class="row">
						<div class="col-xs-8">
							<div class="checkbox icheck" style="margin-left: 20px;">
								<label style="font-size: 13px;"> <input type="checkbox"
									id="remembeMe" name="remembeMe" tabindex="13"> Remember
									Me
								</label>
							</div>
						</div>
						<!-- /.col -->
						<div class="col-xs-4">
							<button type="submit" class="btn btn-primary btn-block btn-flat"
								onclick="login()" id="fms-signin-submit" >Sign
								In</button>

						</div>
						<!-- /.col -->
					</div>
				</form>

				<div class="social-auth-links text-center"></div>
				<!-- /.social-auth-links -->

				<a href="#" id="gapp-forgotpassword-link" disabled>I forgot my
					password</a><br>
				<!--   <a href="#" class="text-center">Register a new membership</a>-->
			</div>
			<!-- gapp-login-container -->
			<div id="gapp-forgotpassword-container" class="hide">
				<form:form class="form-horizontal " name="gapp-forgorpassword-form"
					id="gapp-forgorpassword-form" autocomplete="off"
					data-role="validator" novalidate="novalidate" commandName="forgot_password" action="forgot_password" method="post">
					<fieldset>
						<h3 style="  color: #666; margin-top: 0px; font-size: 18px;">Forgot	Password</h3>
						<p style="  color: #666; font-size: 11px;">Enter your Username	and E-mail address below to reset your password.</p>
						<p id="forgot_notification" style="color: #666;;" class="col-md-11"></p>
						
						<section class="form-group login-inputbox col-md-12 is-empty">									
							<form:input class="form-control dc_inputstyle" type="text"	name="userName"
							id="userName"  value=""	placeholder="Username" required="true" path="userName"/> 
							<span class="glyphicon glyphicon-user form-control-feedback"></span>							
						</section>
						
						<section class="form-group login-inputbox col-md-12 is-empty">						
							 <form:input
									class="form-control placeholder-no-fix" type="email"
									placeholder="Email"  id="emailId" path="emailId"  name="emailId"
									required="true" data-required-msg="Please enter emailid"
									data-email-msg="Please enter valid emailid"/>
									<span class="glyphicon glyphicon-envelope form-control-feedback" style="margin: 6px 15px 0 0 !important"></span>
						</section>
						
				
						
						
						
						<section class="form-group col-md-12">
							<div class="form-actions">
								<button type="button" class="btn  pull-left btn btn-info  btn-flat" name="gapp-forgotpass-cancel"
									id="gapp-forgotpass-cancel">
									<i class="m-icon-swapleft"></i> Back
									<div class="ripple-container">
										<div class="ripple ripple-on ripple-out"
											style="left: 49px; top: 16px; transform: scale(9.25); background-color: rgb(51, 51, 51);"></div>
										<div class="ripple ripple-on ripple-out"
											style="left: 46px; top: 25px; transform: scale(9.25); background-color: rgb(51, 51, 51);"></div>
									</div>
								</button>
								<button type="button" class="btn  pull-right btn btn-primary  btn-flat"
									name="gapp-forgotpass-submit" id="gapp-forgotpass-submit"
									onclick="return FnForgotPassword()">
									Submit <i class="m-icon-swapright m-icon-white"></i>
								</button>
							</div>
						</section>
					</fieldset>
				</form:form>


			</div>
			<!-- gapp-forgotpassword-container -->


		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->


	<script src="resources/plugins/video.js/video.js"></script>
	<script src="resources/plugins/BigVideo/lib/bigvideo.js"></script>

	<script type="text/javascript">
	// $('.autocomplete-off').val('');
	
	$(document).ready(function(){
	
	  setTimeout(function(){			
	 		$('#userName').attr('placeholder','Username');
	 		$('#password').attr('placeholder','Password');
	 		$('#userName').val('');
		    $('.autocomplete-off').val('');
		  }, 15);
	
	 var BV = new $.BigVideo();
			BV.init();
            if (Modernizr.touch) {
                BV.show('resources/video/background-dock.jpg');
            } else {
               // BV.show('resources/video/dock.mp4',{ambient:true});
               BV.show('resources/video/dock.mp4',{ambient:true});
            }
            
	
		$("#gapp-forgorpassword-form").kendoValidator({
											validateOnBlur : true,
											errorTemplate: "<span class='help-block'><code>#=message#</code></span>"
	});
	
		$("#loginForm").kendoValidator({
					validateOnBlur : true,
					errorTemplate : "<span class='help-block'><code>#=message#</code></span>"
		});
		$('#fms-signin-submit').prop('disabled',true);
		
		FnCheckEmptyFrmFields('#loginForm #userName, #loginForm #password', '#fms-signin-submit');
		
		$('body').on('click','#gapp-forgotpassword-link',function(e){
			e.preventDefault();	
		//	$('#gapp-forgotpassword-container').addClass('animated pulse');
			//$('#gapp-login-container').removeClass('show').fadeOut(500).addClass('hide');
			
			$('#gapp-login-container').removeClass('show').delay(500).fadeOut().addClass('hide');
			$('#gapp-forgotpassword-container').removeClass('hide').addClass('show');
			
			$('#forgot_notification').removeClass('alert-danger alert-success').text('');
		});
		
		$('body').on('click','#gapp-forgotpass-cancel',function(e){
			e.preventDefault();
			$('#forgotemailid').val('');
			$('#gapp-forgotpassword-container').removeClass('show').addClass('hide');
			$('#gapp-login-container').addClass('show');
		
		});
		
	
	});


		
function login() {		
			$("#loginForm").submit();
}
		
function FnForgotPassword(){
	$('#gapp-forgotpass-submit, #gapp-forgotpass-cancel').attr('disabled',true);
	var validator = $("#gapp-forgorpassword-form").data("kendoValidator");
	validator.hideMessages();
	$("#GBL_loading").show();
	if (validator.validate()) {

		$('#gapp-forgorpassword-form').submit();
		// var VarUrl = 'ajax/' + VarForgotPasswordUrl;
		// var VarParam = {};
		// alert(VarForgotPasswordUrl);
		// VarParam['userName'] = FnTrim($('#forgotuserName').val()) + ".galaxy";
		// VarParam['emailId'] = FnTrim($('#forgotemailId').val());
		// VarParam['emailLink'] = VarAPPServerPath+'/resetpassword';
		// FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarParam), 'application/json; charset=utf-8', 'json', FnResForgotPassword);
	} else {
		$('#gapp-forgotpass-submit, #gapp-forgotpass-cancel').attr('disabled',false);
		$("#GBL_loading").hide();
		var errors = validator.errors();
		console.log(errors);
		return false;
	}
}

function FnResForgotPassword(response){
console.log(response.entity)
console.log(response)
	$("#GBL_loading").hide();
	$('#gapp-forgotpass-submit, #gapp-forgotpass-cancel').attr('disabled',false);
	if(response.status == 'SUCCESS'){
		$('#forgot_notification').text('mail sent successfully.').addClass('alert-success');
		setTimeout(function(){ $('#gapp-forgotpass-cancel').trigger('click'); }, 1000);
	} else if(response.errorCode != undefined) {
		$('#forgot_notification').text(response.errorMessage).addClass('alert-danger');
	}
}

	</script>


	<script src="resources/js/common/uniqueness.validation.js"></script>

</body>
</html>
	<script type="text/javascript">		
		var VarForgotPasswordUrl = "${VarForgotPasswordUrl}";
		var VarAPPServerPath='${VarAPPServerPath}';
	</script>