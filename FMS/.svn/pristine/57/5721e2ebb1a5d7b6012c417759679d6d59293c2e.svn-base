<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="checkUsernameValidUrl" expression="@propertyConfigurer.getProperty('fms.services.validateUser')" />

<style type="text/css">
	body {
		background-color: #ecf0f5;
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
	
	.wrapper,.main-sidebar,.left-side {
		background-color: #222d32;
		background-color: #ecf0f5;
	}
	
	span.k-invalid-msg {
		color: red;
		/*display: block !important;*/
		padding: 6px 0;
		font-size: 12px;
	}
	
	.input-group {
		position: relative;
		display: table;
		border-collapse: separate;
		width: 320px !important;
	}
	#tenant_admin_user_form label {
    display: block;
}

.required {
    color: #e02222;
    font-size: 12px;
    padding-left: 2px;
    font-size: 14px;
}
.input-group {
    position: relative;
    display: table;
    border-collapse: separate;
    width: 64% !important;
    margin: 18px 0px;
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
			<h1>Client Admin Management</h1>
		</section>
		<section class="content">
			<div class="box-title fms-title hidden">
				<h4 class="pull-left">Please enter the details</h4>
				<div class="fms-btngroup text-right"></div>
			</div>
			<div class="box box-info">
				<div class="box-header with-border hidden">
					<h4 class="box-title"></h4>

					<div class="box-tools pull-right hidden">
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
						<button type="button" class="btn btn-box-tool"
							data-widget="remove">
							<i class="fa fa-remove"></i>
						</button>
					</div>
				</div>
				<div class="box-body">
					<div class="row">
						<div class="col-md-6" style="margin: 0 0 0 16px;">
							<c:choose>
								<c:when test="${not empty error}">
									<p class="dc_errorbox_spacing bg-danger">${error.getErrorMessage()}</p>
								</c:when>
							<c:otherwise>
								<form:form role="form" action="../create_tenant_admin"
									commandName="tenant_admin_user_form" id="tenant_admin_user_form"
									autocomplete="off" novalidate="novalidate" method="post">
										<form:hidden path="action" />
										<form:hidden path="identifier" />
										<form:hidden path="domain" />
										<form:hidden path="parentDomain" />
										<form:hidden path="tenantAdminEmail" />
										<div class="input-group input-group-md">
											<label for="entityname">Username<span
												class="required">*</span>
											</label>
											<form:input path="userName" id="userName" class="form-control"  placeholder="Enter Username"  onkeypress="return FnAllowAlphaNumericOnly(event)" required="true" data-required-msg="User Name not specified" data-available="true" data-available-url="${checkUsernameValidUrl}" data-available-msg="User Name already exists"/>
											<label id="" for="userDomain"></label>
											<!-- userDomainLabel -->
										</div>
										<div class="input-group input-group-md">
											<label>First Name </label>
											<form:input path="firstName" placeholder="Enter First Name" id="firstName"
												class="form-control " />
										</div>
										<div class="input-group input-group-md">
											<label>Last Name<span class="required">*</span>
											</label>
											<form:input path="lastName" id="lastName"	placeholder="Enter Last Name" class="form-control" required="true" data-required-msg="Last Name not specified" />
										</div>
										<div class="input-group input-group-md">
											<label>Email Id<span class="required">*</span></label>
											<form:input type="email" path="emailId" id="emailId"  placeholder="Enter Email Id" class="form-control " disabled="true" />
										</div>
										<div class="input-group input-group-md">
											<label>Contact Number </label>
											<form:input path="contactNumber" id="contactNumber"
												class="form-control " placeholder="Enter Contact Number" onkeypress="return FnAllowNumbersOnly(event)" />
										</div>
										<form:hidden path="status" id="status" value="true" />
										<form:hidden id="roleName" path="roleName" value="TenantAdmin" />
								</form:form>
							</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<div class="box-footer ">
					<div class="col-md-4">
					<button class="btn  btn-primary pull-right"	onclick="return submitAction()" id="fms-clientadmin-save">Save</button>
				</div>
			</div>
		</section>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
		
			// Form Validation
			$("#tenant_admin_user_form").kendoValidator({
													validateOnBlur : true,
													errorTemplate: "<span class='help-block'><code>#=message#</code></span>",
													rules : {
														available:function(input){
															var validate = input.data('available');
															var VarExist = true;
															if (typeof validate !== 'undefined' && validate !== false) {
																var url = input.data('available-url');
																var VarParam = {"domain": {"domainName": $('#domain').val()},"fieldValues": [{"key": "userName","value": FnTrim(input.val())}]};
																$.ajax({
																	type:'POST',
																	cache: true,
																	async: false,
																	contentType: 'application/json; charset=utf-8',
																	url: "../ajax/" + url,
																	data: JSON.stringify(VarParam),
																	dataType: 'json',
																	success: function(result) {
																		var ObjExistStatus = result.entity;																		
																		if(ObjExistStatus.status == 'SUCCESS'){ // Exist in db
																			VarExist = true;
																		} else if(ObjExistStatus.status == 'FAILURE') { // Does not exist in db
																			VarExist = false;
																		}
																	},
																	error : function(xhr, status, error) {
																	
																	}
																});
															}
															return VarExist;
														}
													},
													messages : {
														available: function(input) { 
															return input.data("available-msg");
														}
													}
													
			});
			
			
		});
		
		function submitAction() {
			$('#fms-clientadmin-save').attr('disabled',true);
			var validator = $("#tenant_admin_user_form").data("kendoValidator");
			validator.hideMessages();
			$("#GBL_loading").show();
			if (validator.validate()) {
				$("#tenant_admin_user_form").submit();
				return true;
			} else {
				$('#fms-clientadmin-save').attr('disabled',false);
				$("#GBL_loading").hide();
				var errors = validator.errors();
				console.log(errors);
				return false;
			}
		}
	</script>