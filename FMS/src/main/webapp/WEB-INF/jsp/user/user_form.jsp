<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<spring:eval var="validateUserUrl"
	expression="@propertyConfigurer.getProperty('fms.services.validateUser')" />
<spring:eval var="gatAllRolesUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.listUser')" />
<spring:eval var="getNamedTagsToAssign"
	expression="@propertyConfigurer.getProperty('fms.services.findTagsForEntities')" />
<spring:eval var="VarFindGeoTagUrl"
	expression="@propertyConfigurer.getProperty('fms.services.findGeotag')" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
div.fileUpload {
	position: relative;
	overflow: hidden;
	margin: 10px;
	background-image: url("resources/images/user.png");
	background-size: 200px 200px;
	background-color: #BDBDBD;
	height: 200px;
	width: 200px;
	text-align: center;
}

.upload-addicon {
	display: inline-block;
	position: absolute;
	bottom: 0;
	width: 40px;
	/* height: 40px; */
	width: 100%;
	z-index: 99;
	left: 0;
	text-align: left;
	background: rgba(0, 0, 0, 0.7);
	height: 32px;
	line-height: 30px;
	padding: 0px 10px;
}

div.fileUpload input.upload {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity = 0);
	height: 100%;
	text-align: center;
	z-index: 99;
}

div.fileUpload input.upload+img {
	position: absolute;
	top: 0;
	left: 0;
	z-index: 9;
}

.custom-span {
	font-family: Arial;
	font-weight: bold;
	font-size: 25px;
	color: #FFFFFF;
	/* padding: 10px; */
	float: left;
}

.custom-para {
	font-family: arial;
	/* font-weight: bold; */
	font-size: 13px;
	color: #FFFFFF;
	text-align: left;
	float: left;
	margin: 0;
	padding: 0px 6px;
}

.tag-wrapper {
	/*display:none;*/
	position: relative;
	background-color: #fff;
	clear: both;
}

.tag-wrapper hr {
	border: 0;
	height: 0;
	border-top: 1px solid rgba(0, 0, 0, 0.1);
	border-bottom: 1px solid rgba(255, 255, 255, 0.3);
}

.gllpMap {
	width: 100% !important;
}

.geo-tag-inputs input {
	border: none;
	height: 25px;
	text-align: center;
}

.tag-wrapper .k-multiselect.k-header {
	margin-left: 12px;
	margin-right: 12px;
	padding-top: 10px;
	background-color: #e5e5e5;
	border: none;
}

.tag-wrapper .k-multiselect-wrap {
	padding: 0px 10px 8px 10px;
	/*border-radius: 5px;*/
}


/*
select[multiple],select[size] {
	margin: 6px;
	width: 98%;
	height: 26px !important;
}*/
.disableGeoTagMap {
	pointer-events: none;
}
</style>



<!-- Content Wrapper -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>User Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/user_home">User</a></li>
			<li class="active pageTitleTxt">Create</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left pageTitleTxt">Create User</h4>
			<div class="fms-btngroup text-right">
			
 				 
			<ul class="btnanimation">
					<li style="background: none !important;">
					
							<a href="javascript:void(0);"
								style="padding: 10px; text-decoration: none; background: none !important;" name="gapp-show-tags"
								id="gapp-show-tags" onclick="FnShowTags()"> <i
								class="fa fa-tags aria-hidden=" true"=""></i>Tags
						</a>
					
					</li>
	
 				    <li class=" form-action-cancel  ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
                                <button type="button" class="btn "
					name="gapp-user-cancel" id="gapp-user-cancel"
					onclick="FnCancelUser()">Cancel</button></li>
					
				<c:if test="<%=hasPermissionAccess(\"user_management\",\"create\")%>">
					<li class="delete form-action-edit  ripplelink" id="user-edit-button"   id="poidef-edit"><i class="fa fa-pencil-square-o"
					aria-hidden="true"></i><button href="javascript:void(0)"
					id="gapp-user-edit" onclick="FnEditUser()" style="color:#fff"><i>Edit</i></button></li>
				</c:if>	 
		
			<c:if test="<%=hasPermissionAccess(\"user_management\",new String[]{\"create\",\"edit\"})%>">
				<li class="primary form-action-save  ripplelink">	<i class="fa fa-check" aria-hidden="true"></i>
				<button href="#"  name="gapp-user-save"
					id="gapp-user-save" onclick="return FnSaveUser()" tabindex="7"
					disabled=""><i>Save</i></button></li>
			 </c:if>
	
					 
				 </ul>
			</div>
			<form id="gapp-user-list" role="form"
				action="<%=request.getContextPath()%>/user_home"
				name="gapp-user-list" method="get"></form>
		</div>
		<!-- SELECT2 EXAMPLE -->
		<div class="box box-info">
			<div class="box-header with-border hidden">
				<h4 class="box-title"></h4>
				<div class="box-tools pull-right hidden">
					<button type="button" class="btn btn-box-tool"
						data-widget="collapse">
						<i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove">
						<i class="fa fa-remove"></i>
					</button>
				</div>
			</div>
			<!-- /.box-header -->

			<div class="box-body">
				<form:form name="gapp-adduser-form" id="gapp-adduser-form"
					method="post" role="form" autocomplete="off" action="user_create"
					commandName="user_create" enctype="multipart/form-data">
					<form:hidden path="action" />
					<form:hidden path="latitude" />
					<form:hidden path="longitude" />
					<form:hidden path="selectedTags" />
					<form:hidden path="geotagPresent" />

					<form:hidden path="image" />
					<div class="row">
						<div class="col-md-2">
							<!-- BEGIN PROFILE IMAGE UPLOAD -->
							<div class="form-group">
								<div class="fileUpload">
									<div class="upload-addicon">
										<span class="custom-span">+</span>
										<p class="custom-para">Add Image</p>
									</div>
									<form:input type="file" name="user-image" id="user-image"
										class="upload k-valid" accept="image/*" path="fileDatas" />
									<img src="" id="user-image-preview" width="200" height="200">

								</div>
							</div>
							<!-- END PROFILE IMAGE UPLOAD -->
						</div>
						<div class="col-md-5">
							<div class="form-group">
								<label for="userName">User Name <span class="required"
									aria-required="true">* </span></label> <input type="text"
									path="userName" class="form-control" name="userName"
									id="userName" tabindex="1" required=""
									data-required-msg="User name not specified" data-available=""
									data-available-url="${validateUserUrl}"
									data-available-msg="User name already exists"
									onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
									aria-invalid="true" placeholder="Enter User Name" />
								<!--  <span class="help-block">User name not specified</span>-->
							</div>
							<div class="form-group ">
								<label for="firstName">First Name </label>
								<form:input type="text" path="firstName" class="form-control"
									id="firstName" name="firstName" tabindex="1" 
									onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
									aria-invalid="true" placeholder="Enter First Name" />
								<!--   <span class="help-block">First name entered correct</span>-->
							</div>
							<div class="form-group">
								<label for="lastName">Last Name <span class="required"
									aria-required="true">* </span></label>
								<form:input type="text" path="lastName" class="form-control"
									name="lastName" id="lastName" tabindex="1" required="true"
									onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
									aria-invalid="true" placeholder="Enter Last Name" />
							</div>

						</div>
						<div class="col-md-5">
							<div class="form-group">
								<label>Email <span class="required" aria-required="true">*
								</span></label>
								<div class="input-group">
									
									<form:input type="email" class="form-control" placeholder="Enter Email Id"
										path="emailId" id="emailId" required="true"
										data-required-msg="Email Id not specified" tabindex="4"
										data-email-msg="Invalid email id" />
										<div class="input-group-addon">
										<i class="fa fa-envelope"></i>
									</div>
								</div>
								<!-- /.input group -->
							</div>


							<div class="form-group">
								<label>Role Name <span class="required"
									aria-required="true">* </span></label>
								<form:select class="form-control" path="roleName" id="roleName"
									multiple="multiple" required=""
									data-required-msg="Role not specified" tabindex="5">
									<form:options items="${roleList}" />
								</form:select>
							</div>
							<div class="form-group">
								<label for="contactNumber">Contact Number </label>
								<form:input type="text" class="form-control"
									path="contactNumber" id="contactNumber" tabindex="6"
									placeholder="Enter Contact Number"
									onkeypress="return FnAllowNumbersOnly(event)" />
								<!--  <span class="help-block">User name not specified</span>-->
							</div>

							<div class="form-group">
									<label for="" style="margin-right:12px;">Status</label>
									<label
									for="r1" class="text-green"> <form:radiobutton
										path="status" value="true" class="md-radiobtn  minimal" name="active"
										checked="checked" />&nbsp; Active
										
								</label>&nbsp;&nbsp; <label for="r2" class="text-red"> <form:radiobutton
										path="status" value="false" class="md-radiobtn  minimal-red"
										name="inactive" />&nbsp; In Active
								</label>
							</div>
						</div>

					</div>
					
					
					<script type="text/javascript">
						var VarEditUserId='${VarEditUserId}';
						var varUserName='${varUserName}';
						var domainName = "<%=session.getAttribute("domainName")%>";
						var getNamedTagsToAssign = "${getNamedTagsToAssign}";
						var VarFindGeoTagUrl = "${VarFindGeoTagUrl}";
						var userDTO = '${userDTO}';
						</script>
					<script type='text/javascript'	src="resources/plugins/jquery.form.js"></script>
					<script type='text/javascript'	src="resources/plugins/multi-select-master/js/jquery.multi-select.js"></script>
					<spring:url value="resources/js/users/add.js" var="userCreateJS" /> <script src="${userCreateJS}"></script>
					<jsp:include page="../getTagTemplate.jsp" />
				</form:form>


			</div>

			<!-- /.row -->
		</div>
		<!-- /.box-body -->
</div>



</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->


<style></style>











