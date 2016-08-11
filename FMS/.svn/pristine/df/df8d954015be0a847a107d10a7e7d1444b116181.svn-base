<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="VarResetPasswordUrl"	expression="@propertyConfigurer.getProperty('fms.services.user.reset.password')" />

<style type="text/css">
.vertical-center {
	min-height: 100vh; 
	min-height: 100vh; 
/*	background-image:
		url('../resources/themes/default/images/template/loginbg.jpg');*/
}

label {
	width: 150px;
	display: inline-block;
}

.dc_inputstyle {
	box-shadow: 0px 1px 13px -4px #888888 inset !important;
	width: 200px;
	box-sizing: border-box;
}

.btn-primary {
	background-color: #1d88aa;
	border: 0px;
	border-radius: 3px 3px 3px 3px;
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0
		rgba(0, 0, 0, 0.12);
	transition: all .3s ease-out;
	color: #fff;
}

.btn-primary:hover {
	will-change: opacity, transform;
	transition: all .3s ease-out;
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0
		rgba(0, 0, 0, 0.12);
}

.dc_maincontainer .page-header {
	padding-bottom: 0px;
	margin: 0px;
	border: 0px;
	background-color: #183e55;
	border-radius: 4px;
}

.dc_maincontainer .page-header>h1 {
	color: #fff;
	font-size: 20px;
	padding: 8px;
}
 .navbar {
    background-color: #3c8dbc;
}

.main-header .logo {
    background-color: #158dc1;
}
.main-header .logo .logo-lg {
    display: block;
    color: #fff;
}
.wrapper, .main-sidebar, .left-side {
    background-color: #222d32;
    background-color: #ecf0f5;
    
}
#gapp-resetpassword-form label {
    display: block;
}
.input-group {
		position: relative;
		display: table;
		border-collapse: separate;
		width: 320px !important;
	}
</style>

<header class="main-header">
	<!-- Logo -->
	<a href="#" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"></span> <!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>Galaxy</b>FMS</span>
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top">
		<!-- Sidebar toggle button-->


		<div class="navbar-custom-menu"></div>
	</nav>
</header>
<aside class="main-sidebar"></aside>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Reset Password</h1>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title hidden">
			<h4 class="pull-left ">Reset Password</h4>
			<div class="fms-btngroup text-right">
				<button type="button" class="btn btn-default hidden"
					id="gapp-user-delete" disabled="">Delete</button>
				<button type="button" class="btn btn-primary hidden" disabled="">
					Create</button>
			</div>
		</div>
		<div class="box box-info">
			<div class="box-header with-border hidden">
				<h3 class="box-title">Password</h3>
				<div class="box-tools pull-right hidden">
					<button class="btn btn-box-tool" data-widget="collapse">
						<i class="fa fa-minus"></i>
					</button>
				</div>
				<!-- /.box-tools -->
			</div>
			<div class="box-body">
				<form:form role="form" action=""
				commandName="set_password" autocomplete="off"
				novalidate="novalidate" method="post" name="gapp-resetpassword-form" id="gapp-resetpassword-form">
					<form:hidden path="domain" />
					<form:hidden path="resetPasswordIdentifierLink" />
					<form:hidden path="userName" />
					<div class="input-group input-group-md"	style="margin-bottom: 20px;">
						 <label for="username">User	Name<span class="required">*</span>
						</label>
						<form:input path="userName" id="userName" class="form-control"	disabled="true" value="${varUserName}"/>
						<!-- <label id="userDomainLabel" for="userDomain"></label> -->
					</div>
					<div class="input-group input-group-md"	style="margin-bottom: 20px;">
						 <label for="password">Password
							<span class="required">*</span>
						</label>
						<form:password path="password"  class="form-control" name="pass" id="pass" tabindex="1" required="true" data-required-msg="Please enter new password" />
					</div>
					<div class="input-group input-group-md"	style="margin-bottom: 20px;">
						<label
							for="confirmPassword">Confirm Password<span	class="required">*</span></label>
							<input  type="password" class="form-control" name="retypepassword" id="retypepassword" tabindex="2" required 	data-required-msg="Please enter retype password" data-matches="#pass" data-matches-msg="Passwords doesn't match" />	
					</div>
					<div align="left" style="margin-top: 10px;">
								<button class="btn  btn-primary" onclick="return FnResetPassword()" id="gapp-password-save">
									Save</button>
							</div>
				</form:form>
			</div>
		</div>
	</section>
</div>

<script type="text/javascript">
	var varUserName = '${varUserName}';
	var VarIdentifier = '${VarIdentifier}';
	var varDomainName = '${varDomainName}';	
</script>
<spring:url value="../resources/js/resetpassword.js" var="resetPasswordJS" />
<script src="${resetPasswordJS}"></script>

