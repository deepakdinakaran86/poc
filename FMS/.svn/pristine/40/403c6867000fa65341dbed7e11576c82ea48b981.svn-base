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

div.fileUpload {
	position: relative;
	overflow: hidden;
	margin: 10px;
	background-image:url("https://10.234.60.46:9445/portal/images/domains/default/users//user.png");
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
</style>
<form action="schedule_list" id="schedule_list" method="get"></form>

<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Service Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/schedule_list">Service Schedule</a></li>
			<li class="active">Create </li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left pageTitleTxt">Create Service Schedule</h4>
			<div class="fms-btngroup text-right">
			<ul class="btnanimation">
					<li class="form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
						<button name="gapp-user-cancel"  id="gapp-user-cancel" onclick="FnCancelSchedule()"><i>Cancel</i>
						</button>
					</li>
					<c:if test="<%=hasPermissionAccess(\"service_scheduling\",\"edit\")%>">
					<li class="  form-action-edit ripplelink" id="schedule-edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>
						<button name="schedule-edit" onclick="FnEditSchedule()" ><i>Edit</i></button>
					</li>
					</c:if>
					<c:if test="<%=hasPermissionAccess(\"service_scheduling\",new String[]{\"create\",\"edit\"})%>">
					<li class="primary form-action-save ripplelink" id="schedule-save" ><i class="fa fa-check" aria-hidden="true"></i>
						<button  name="schedule-save" onclick="return FnSaveSchedule()">
							<i>Save</i>
						</button>
					</li>
					</c:if>

				</ul>

				<!--<button type="button" class="btn btn-default" name="gapp-user-cancel" id="gapp-user-cancel" onclick="FnCancelUser()"> Cancel</button>
      				  <button type="button" class="btn btn-primary"  name="gapp-user-save" id="gapp-user-save" onclick="return FnSaveUser()"
                tabindex="7">Save</button>-->
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
				<div class="row">
					<form:form role="form" action="schedule_add"
						commandName="schedule_add" id="schedule_add" autocomplete="off"
						novalidate="novalidate">
						<form:hidden path="action" />
						<form:hidden path="identifier" />
						<form:hidden path="serviceScheduleNameEdit" />
						<form:hidden path="serviceComponentList" />
						<form:hidden path="selectedListOnEdit" />

						<div class="col-md-5">
							<div class="form-group">
								<label for="serviceScheduleName">Schedule Name<span
									class="required text-red" aria-required="true">* </span></label>
								<form:input type="text" path="serviceScheduleName"
									class="form-control" id="serviceScheduleName" tabindex="1"
									required="true"
									onkeypress="return FnAllowAlphaNumericWithSpace(event)"
									aria-invalid="true" placeholder="Enter Schedule Name" />
							</div>
							<div class="form-group ">
								<label for="occurenceType">Occurrence Type<span
									class="required text-red" aria-required="true">* </span></label>
								<form:select path="occurenceType" class="form-control"
									name="occurenceType" id="occurenceType">
									<form:option value="" label="Please Select Type" />
									<form:option value="One Time" label="One Time" />
									<form:option value="Recurring" label="Recurring" />
								</form:select>
							</div>
							<div class="form-group">
								<label for="description">Description </label>
								<form:textarea class="form-control" rows="7"
									placeholder="Enter Description" path="description"
									id="description"></form:textarea>
							</div>

						</div>
						<div class="col-md-5">
						<!-- BEGIN Components -->
						<div class="form-group form-md-line-input">
							<label class="col-md-12 control-label" for="form_control_1">Service
								Component<span class="required" aria-required="true">* </span>
							</label>
							<form:select multiple="true" path="components" name="components"
								class="form-control" style="height: 220px; width: 170px;">
							</form:select>

							<div class="form-control-focus"></div>
						</div>
						</div>
						<!-- END Components -->
						</form:form>
				</div>
			</div>

		</div>
		<!-- /.row -->
</div>
<!-- /.box-body -->


<spring:url value="resources/js/service/serviceschedule/add.js?v=1"
	var="servicescheduleJS" />
<script src="${servicescheduleJS}"></script>
<script src="resources/js/common/uniqueness.validation.js"></script>


<script type="text/javascript">
	var pageError;
	function setFormValidation() {

		$("#occurenceType").attr('mandatory', 'Occurence Type not specified');

		$("#serviceScheduleName").attr('mandatory','Schedule Name not specified');
		$("#serviceScheduleName").attr('unique','Schedule Name already exists');
		$("#components").attr('mandatory', 'Components not specified');

		$("#schedule_add")
				.kendoValidator(
						{

							validateOnBlur : true,
							errorTemplate : "<span style='color:red'>#=message#</span>",
							rules : {
								mandatory : function(input) {

									if (input.attr('id') == 'occurenceType'
											&& (input.val() == '' || input
													.val().trim() == "")) {
										return false;
									} else if (input.attr('id') == 'serviceScheduleName'
											&& (input.val() == '' || input
													.val().trim() == "")) {
										return false;
									}
									return true;
								},
								unique : function(input) {

									var value = input.val();

									if (input.attr('id') == 'serviceScheduleName'
											&& input.val().trim() != "") {
										if ('${schedule_add.serviceScheduleNameEdit}' != input
														.val().trim()) {
											return checkUniquenessWithTemplate(
													"${checkUniqueFieldUrl}",
													"serviceScheduleName",
													input.val(),"ServiceSchedule");
										}
									}
									return true;
								}
							},
							messages : {
								unique : function(input) {
									return input.attr("unique");
								},
								mandatory : function(input) {
									return input.attr("mandatory");
								},
								illegalFormat : function(input) {
									return input.attr("illegalFormat");
								}
							}
						});
	}
</script>
<c:if test="${not empty componentsToSave}">
	<script>
	componentsToSave= ${componentsToSave};
					</script>
</c:if>	
<c:if test="${not empty selected_component_list}">
	<script>
	selectedComponentList= ${selected_component_list};
					</script>
</c:if>	

<c:if test="${not empty component_list}">
	<script>
	componentList= ${component_list};
					</script>
</c:if>	
<c:if test="${not empty pageError}">
	<script>
	pageError= ${pageError};
					</script>
</c:if>	
<script type="text/javascript">
setFormValidation();
</script>