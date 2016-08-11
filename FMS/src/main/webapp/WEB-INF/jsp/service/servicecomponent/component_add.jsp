<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<spring:eval var="checkUniqueFieldUrl"
	expression="@propertyConfigurer.getProperty('fms.services.validation')" />
	
<style>
.skin-blue .main-header .navbar {
	background: radial-gradient(#197aa5 15%, #005180 60%);
}

.skin-blue .main-header .logo {
	background-color: #158dc1;
}


</style>

<form action="component_list" id="component_list" method="get">
</form>

<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header ">
		<h1>Service Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/component_list">Service Components</a></li>
			<li class="active">Create</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left pageTitleTxt">Create Service Component</h4>
			<div class="fms-btngroup text-right">
				<ul class="btnanimation">
					<li class=" form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
						<button name="gapp-user-cancel"  id="gapp-user-cancel" onclick="FnCancelComponent()"><i>Cancel</i>
						</button>
					</li>
					<c:if test="<%=hasPermissionAccess(\"service_components\",\"edit\")%>">
					<li class=" form-action-edit ripplelink" id="component-edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>
						<button name="component-edit"  onclick="FnEditComponent()" ><i>Edit</i></button>
					</li>
					</c:if>
					<c:if test="<%=hasPermissionAccess(\"service_components\",new String[]{\"create\",\"edit\"})%>">
					<li class="primary form-action-save ripplelink" id="component-save"><i class="fa fa-check" aria-hidden="true"></i>
						<button id="component-save-btn" name="component-save" onclick="return FnSaveComponent()">
							<i>Save</i>
						</button>
					</li>
					</c:if>
				</ul>

			</div>



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
				<div class="">
					<form:form role="form" action="component_add"
						commandName="component_add" id="component_add" autocomplete="off"
						novalidate="novalidate">
						<form:hidden path="action" />
						<form:hidden path="identifier" />
						<form:hidden path="serviceComponentList" />

						<div class="col-md-5">
							<div class="form-group">
								<label for="serviceComponentName">Component Name <span
									class="required text-red" aria-required="true">* </span></label>
								<form:input type="text" path="serviceComponentName"
									class="form-control" id="serviceComponentName" tabindex="1"
									required="true" data-required-msg="Please enter component name"
									onkeypress="return FnAllowAlphaNumericWithSpace(event)"
									placeholder="Enter Component Name" />
								
							</div>
							<div class="form-group ">
								<label>Service Item <span class="required"
									aria-required="true">* </span></label>
								<form:select id="serviceItemName" path="serviceItemName" 
									class="form-control" name="serviceItems" required="true" data-required-msg="Please select service item" >
									<form:option value="NONE" label="Select"/>
  	 								<form:options items="${serviceItems}" />
								</form:select>
							</div>
							<div class="form-group">
								<label for="frequencyInTime">Frequency<span
									class="required text-red " aria-required="true"> * </span></label>
								<div class="row">
									<div class="col-md-3 clearfix">
										<div class="col-md-12">
											<form:input type="number" path="frequencyInTime"
												class="form-control" id="frequencyInTime" tabindex="1"
												onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
												placeholder="In Days" />
										</div>
									</div>
									<div class="col-md-1 clearfix"><strong>(or)</strong></div>
									<div class="col-md-3 clearfix">
										<div class="col-md-12">
											<form:input type="number" path="frequencyInDistance"
												class="form-control" id="frequencyInDistance" tabindex="1"
												onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
												placeholder="In KM" />
										</div>
									</div>
									<div class="col-md-1 clearfix"><strong>(or)</strong></div>
									<div class="col-md-3 clearfix">
										<div class="col-md-12">
											<form:input type="number" path="frequencyInRunningHours"
												class="form-control" id="frequencyInRunningHours"
												tabindex="1" onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
												placeholder=" In Hrs" />
										</div>
									</div>
								</div>
								<span id="frequencymsg" class="k-invalid-msg" data-for="frequencyInTime"></span>
							</div>

							<div class="form-group">
								<label for="notificationInTime">Notify Before<span
									class="required text-red " aria-required="true"> * </span></label>
								<div class="row">
									<div class="col-md-3 clearfix">
										
										<div class="col-md-12">
											<form:input type="number" path="notificationInTime"
												class="form-control" id="notificationInTime" tabindex="1"
												onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
												placeholder="Days" />
										</div>
									</div>
									<div class="col-md-1 clearfix"><strong>(or)</strong></div>
									<div class="col-md-3 clearfix">
										<div class="col-md-12">
											<form:input type="number" path="notificationInDistance"
												class="form-control" id="notificationInDistance"
												tabindex="1" onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
												placeholder="KM" />
										</div>
									</div>
									<div class="col-md-1 clearfix"><strong>(or)</strong></div>
									<div class="col-md-3 clearfix">
										<div class="col-md-12">
											<form:input type="number" path="notificationInRunningHours"
												class="form-control" id="notificationInRunningHours"
												tabindex="1" onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
												placeholder="Hrs"  />
										</div>
									</div>
								</div>
								<span id="Notifymsg" class="k-invalid-msg" data-for="notificationInTime"></span>
							</div>
							<div class="form-group">
								<label for="description">Description </label>
								<form:textarea class="form-control" rows="6"
									placeholder="Enter Description" path="description"
									id="description"></form:textarea>
								<!--  <span class="help-block">User name not specified</span>-->
							</div>
							
						</div>
						
					</form:form>

				</div>

			</div>
			<!-- /.row -->
		</div>
		<!-- /.box-body -->
</div>



</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<spring:url value="resources/js/service/servicecomponent/add.js"
	var="servicecomponentaddJS" />
<script src="${servicecomponentaddJS}"></script>
<script type="text/javascript">
var checkUniqueFieldUrl = "${checkUniqueFieldUrl}";
var serviceComponentNameEdit = "${component_add.serviceComponentNameEdit}";
</script>
<script src="resources/js/common/uniqueness.validation.js"></script>

