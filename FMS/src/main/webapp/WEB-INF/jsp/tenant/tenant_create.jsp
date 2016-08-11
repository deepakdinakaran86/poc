<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>
<!-- Style sheet for Tag(Geo Map) section -->
<!-- 	<link rel="stylesheet" type="text/css" href="resources/css/jquery-gmaps-latlon-picker.css" /> -->

<spring:eval var="checkTenantFieldUrl"
	expression="@propertyConfigurer.getProperty('fms.services.validateTenant')" />
<spring:eval var="getTenantFeaturesUrl"
	expression="@propertyConfigurer.getProperty('fms.services.findTenantFeatures')" />
<spring:eval var="getNamedTagsToAssign"
	expression="@propertyConfigurer.getProperty('fms.services.findTagsForEntities')" />

<form action="tenant_home" id="tenant_home" method="post">
	<input class="form-control" type="hidden" id="domain" name="value" />
</form>
<link rel="stylesheet" type="text/css"
	href="resources/plugins/multi-select-master/css/multi-select.css" />
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
	background-image: url("resources/images/client_placeholder.png");
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

.tag-wrapper .k-multiselect-wrap li.k-button {
	background-color: #3c8dbc;
	color: #FFFFFF;
	border-radius: 5px;
	font-size: 13px;
	margin-top: 9px;
}


select[multiple], select[size] {
    
    margin: 6px;
    width: 98%;
    height: 26px !important;
}
.disableGeoTagMap{  pointer-events: none;}
</style>

<form action="createtenant" id="createtenant"></form>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Client Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/tenant_home">Client</a></li>
			<li class="active">Create</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left pageTitleTxt">${pageTitle}</h4>
			<div class="fms-btngroup text-right">
				<ul class="btnanimation">
					<li style=" background: none !important;">
					
							<a href="javascript:void(0);"
								style="padding: 10px; text-decoration: none; background: none !important;" name="gapp-show-tags"
								id="gapp-show-tags" onclick="FnShowTags()"> <i
								class="fa fa-tags aria-hidden=" true"=""></i>Tags
						</a>
					
					</li>
						
					<li class=" form-action-cancel ripplelink" id="user-cancel"><i class="fa fa-times" aria-hidden="true"></i>
								<button href="javascript:void(0)" id="gapp-user-cancel"
							onclick="FnCancelClient()">
							<i>Cancel</i>
						</button></li>	
						
						
					<c:if test="<%=hasPermissionAccess(\"client_management\",\"edit\")%>">
					<li class=" form-action-edit ripplelink" id="client-edit"><i
						class="fa fa-pencil-square-o" aria-hidden="true"></i>
						<button href="javascript:void(0)" id="gapp-client-edit"
							onclick="FnEditClient()" name="gapp-client-edit">
							<i>Edit</i>
						</button></li>
					</c:if>
					<c:if test="<%=hasPermissionAccess(\"client_management\",new String[]{\"create\",\"edit\"})%>">
					<li class="primary form-action-save ripplelink" id="client-save"><i class="fa fa-check" aria-hidden="true"></i>
						<button href="#" onclick="return FnSaveClient()" id="user-save"
							name="client-save">
							<i>Save</i>
						</button></li>
						</c:if>
				</ul>
				<!--<button type="button" class="btn btn-default" name="gapp-user-cancel" id="gapp-user-cancel" onclick="FnCancelUser()"> Cancel</button>
      				  <button type="button" class="btn btn-primary"  name="gapp-user-save" id="gapp-user-save" onclick="return FnSaveUser()"
                tabindex="7">Save</button>-->
			</div>
		</div>

		<div style="height: 1px;">
			<form id="tenantHome" action="tenant_home" method="get"></form>
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

			<div class="box-body">
				<form:form role="form" action="tenant_create"
					commandName="tenant_create" id="tenant_create" autocomplete="off"
					novalidate="novalidate" enctype="multipart/form-data">
					<form:hidden path="action" />
					<form:hidden path="identifier" />
					<form:hidden path="domainNameOnEdit" />
					<form:hidden path="currentDomain" />
					<form:hidden path="parentDomain" />
					<form:hidden path="geotagPresent" />
					<form:hidden path="selectedTags" />
					<form:hidden path="image" />
					<form:hidden path="latitude" />
					<form:hidden path="longitude" />
					
					<div class="row">

						<div class="col-md-2">
							<!-- BEGIN PROFILE IMAGE UPLOAD -->
							<div class="form-group">
								<div class="fileUpload">
									<div class="upload-addicon">
										<span class="custom-span">+</span>
										<p class="custom-para">Add Image</p>
									</div>

									<form:input type="file" name="client-logo" id="client-logo"
										class="upload k-valid" accept="image/*" path="fileDatas" />
									<img src="" id="client-logo-preview" width="200" height="200">

								</div>
							</div>
							<!-- END PROFILE IMAGE UPLOAD -->
						</div>

						<div class="col-md-5">
							<div class="form-group  ">
								<label for="tenantname">Client Name<span
									class="required" aria-required="true">* </span></label>
								<form:input type="text" path="tenantName" class="form-control"
									id="tenantName" tabindex="1" required=""
									onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
									aria-invalid="true" placeholder="Enter Client Name" />
							</div>
							<div class="form-group ">
								<label for="firstName">First Name <span class="required"
									aria-required="true">* </span></label>
								<form:input type="text" path="firstName" class="form-control"
									id="firstName" tabindex="1" required=""
									onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
									aria-invalid="true" placeholder="Enter First Name" />
							</div>
							<div class="form-group">
								<label for="lastName">Last Name <span class="required"
									aria-required="true">* </span></label>
								<form:input type="text" path="lastName" class="form-control"
									id="lastName" tabindex="1" required=""
									onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
									aria-invalid="true" placeholder="Enter Last Name" />

							</div>
							<div class="form-group">
								<label>Email id <span class="required"
									aria-required="true">* </span></label>
								<div class="input-group">
								
									<form:input path="contactEmail" id="contactEmail"
										class="form-control" type="email" tabindex="1"  placeholder="Enter Email Id"/>
											<div class="input-group-addon">
										<i class="fa fa-envelope"></i>
									</div>
								</div>
								<!-- /.input group -->
							</div>
							<div class="form-group">
								<label for="tenantId">Client id <span class="required"
									aria-required="true">* </span></label>

								<form:input type="text" path="tenantId" class="form-control"
									id="tenantId" tabindex="1" required=""
									onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
									aria-invalid="true" placeholder="Enter Client Id" />

							</div>
						</div>

						<div class="col-md-5">

							<!-- BEGIN FEATURES -->
							<div class="form-group form-md-line-input">
								<label class="col-md-12 control-label" for="form_control_1">Features<span
									class="required" aria-required="true">* </span></label>
								<form:select multiple="true" path="tenantFeatures"
									name="tenantFeatures" class="form-control"
									style="height: 220px; width: 170px;">
								</form:select>
								<div class="form-control-focus"></div>
							</div>
							<!-- END FEATURES -->

							<!-- 							<div class="form-group col-md-12"> -->
							<!-- 								<label for="">Status</label>&nbsp; &nbsp;&nbsp; &nbsp; <label -->
							<%-- 									for="r1" class="text-green"> <form:radiobutton --%>
							<%-- 										path="status" class="minimal" name="r1" checked="checked" value="ACTIVE"/>&nbsp; --%>
							<!-- 									Active -->
							<%-- 								</label> </label>&nbsp;&nbsp; <label for="r2" class="text-red"> <form:radiobutton --%>
							<%-- 										path="status" class="minimal-red" name="r2"  value="INACTIVE"/>&nbsp; InActive --%>
							<!-- 								</label> -->
							<!-- 							</div> -->
							<div class="form-group">
								<label for="" style="margin-right:12px;">Status</label>
								<form:radiobutton path="status" value="ACTIVE" class="minimal" />
								<span class="text-green">Active</span>&nbsp;&nbsp;
								<form:radiobutton path="status" value="INACTIVE"  class="minimal-red"/>
								<span class="text-red">InActive</span>
							</div>
						</div>
					</div>
					
					
<script type="text/javascript">
var getTenantFeaturesUrl = "${getTenantFeaturesUrl}";
var getNamedTagsToAssign = "${getNamedTagsToAssign}";
var savedFeatures=${selectedFeatures};
var pageTitle= "${pageTitle}";
var pageError = "${pageError}";
var operation = "${operation}";


var var_lat=$("#latitude").val();
var var_long=$("#longitude").val();
var currentDomain = "${currentDomain}";
console.log("getTenantFeaturesUrl"+getTenantFeaturesUrl);

function FnCancelClient(){
	$('#domain').val(currentDomain);
	$("#tenant_home").submit();
}
</script>					
				<c:if test="${not empty tagsOnError}">
	<script>
	tagsOnError= ${tagsOnError};
					</script>
</c:if>	
					
					
					
	<script type="text/javascript">
	var tenantName;
	var firstName;
	var lastName;
	var contactEmail;
	var domain;

	function submitAction() {
		$("#tenant_create").submit();
	}

	function submitUpdate() {
		$("#tenant_create :input").prop("disabled", false);
		$("#btn-back").prop("disabled", false);
		$("#tenant-btn-save").show();
		$("#btn-update").hide()
		var a = document.getElementById("action");
		a.value = "Update";
		$("#domain").prop("disabled", true);
		$("#tenantName").prop("disabled", true);
	}
	
	$("#client-logo").change(function(){
		readURL(this,$(this).attr('id'));			
        
    });
    
    function readURL(input,id) {
		var ArrAllowedImgTypes = ["image/jpeg","image/png","image/gif"];
		console.log(input.files);
		var VarImageType = input.files[0]['type'];
		if(ArrAllowedImgTypes.indexOf(VarImageType) != -1){
			if (input.files && input.files[0]) {
				var VarFileSize = Math.round((input.files[0].size) / 1024);
				if(VarFileSize <= GBLUPLOADIMAGESIZE){
					var reader = new FileReader();			
					reader.onload = function (e) {
						$('#client-logo-preview').attr('src', e.target.result);
						$('#client-logo-preview').css('background-color','whitesmoke');
					}			
					reader.readAsDataURL(input.files[0]);
				} else {
					$("#alertModal").modal('show').find(".modalMessage").text("Your file is larger than the maximum allowed of 2MB/file. Please select another image ");
					return false;
				}
			}		
		} else {
			$("#alertModal").modal('show').find(".modalMessage").text("Invalid file type.");
			return false;	
		}
		
}

$('#submitBackBtn').click(function() {
           
    var getsubmitBack =$(this).attr("value"); 
    if (getsubmitBack =='submitBack'){            
    	$('#tenantHome').submit();
    }
    
    
});

	function submitBackAction() {
		if ('${tenant_create.tenantName}' != $('#tenantName').val()
				|| '${tenant_create.domain}' != $('#domain').val()
				|| '${tenant_create.firstName}' != $('#firstName').val()
				|| '${tenant_create.lastName}' != $('#lastName').val()
				|| '${tenant_create.contactEmail}' != $('#contactEmail').val()
				||  ${tenant_create.status} != $('#status').is(":checked")) {	
						
			$('#submitBackActionModal').modal('show');
			
			/* var r = confirm("Do you want to continue?");
			if (r == true) {$('#tenantHome').submit();	}
			*/		
			} else {
			$('#tenantHome').submit();
		}
	}

	function isValidChar(inputtxt) {
		var regex = /[a-zA-Z0-9_.+-]+$/;
		var validChar = regex.test(inputtxt);
		
		return validChar;
	}

	function setFormValidation() {

		$("#tenantName").attr('mandatory', 'Client Name is required');

		$("#firstName").attr('mandatory', 'First Name is required');

		$("#lastName").attr('mandatory', 'Last Name is required');
		$("#contactEmail").attr('mandatory', 'Email Id is required');

		$("#tenantId").attr('mandatory', 'Client Id is required');
		$("#tenantId").attr('unique', 'Client Id already exists');

		$("#tenant_create")
				.kendoValidator(
						{

							validateOnBlur : true,
							//errorTemplate : "<span style='color:red'>#=message#</span>",
							errorTemplate: "<span class='help-block'><code>#=message#</code></span>",
							rules : {
								mandatory : function(input) {

									if (input.attr('id') == 'tenantName'
											&& (input.val() == '' || input.val().trim() == "")) {
										return false;
									} else if (input.attr('id') == 'firstName'
										&& (input.val() == '' || input.val().trim() == "")) {
										return false;
									} else if (input.attr('id') == 'lastName'
										&& (input.val() == '' || input.val().trim() == "")) {
										return false;
									} else if (input.attr('id') == 'contactEmail'
										&& (input.val() == '' || input.val().trim() == "")) {
										return false;
									} else if (input.attr('id') == 'tenantId'
										&& (input.val() == '' || input.val().trim() == "")) {
										return false;
									}

									return true;
								},
								unique : function(input) {

									var value = input.val();
									
									if (input.attr('id') == 'tenantId' && input.val().trim() != "") {
										if ('${action}' != 'Update' && '${tenant_create.identifier}'  != input.val().trim()) {
											return checkUniquenessAcrossApp(
													"${checkTenantFieldUrl}",
													"tenantId", input.val());
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

<script type="text/javascript">
	if ('${action}' === 'View') {
		$("#tenant_create :input").prop("disabled", true);
		$("#btn-back").prop("disabled", false);
		$("#tenant-btn-save").hide();
		$("#btn-update").show();
		$("#btn-update").prop("disabled", false);
	//	handleStatus(${tenant_create.status});

	} else {
		handleStatus(true);
		tenantName = '${tenant_create.tenantName}';
		firstName = '${tenant_create.firstName}';
		lastName = '${tenant_create.lastName}';
		contactEmail = '${tenant_create.contactEmail}';
		domain = '${tenant_create.domain}';
		if ('${action}' === 'Update') {
			$("#domain").prop("disabled", true);
			$("#tenantName").prop("disabled", true);
		}
		$("#btn-update").hide();
	}
	setFormValidation();
	
	function handleStatus(flag){
		$("#status").kendoMobileSwitch({
		    onLabel: "ACTIVE",
		    offLabel: "INACTIVE",
		    checked: flag
		});
	}
</script>
					<script type='text/javascript' src="resources/plugins/jquery.form.js"></script>
					<script type='text/javascript'	src="resources/plugins/multi-select-master/js/jquery.multi-select.js"></script>
					<spring:url value="resources/js/clients/add.js" var="clientCreateJS" /> <script src="${clientCreateJS}"></script>
					
					
					
					
					<div class="col-md-12">
						<jsp:include page="../getTagTemplate.jsp" />
					</div>
					
				</form:form>
	</section>
</div>


<!-- Modal -->
<div class="modal fade" id="submitBackActionModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Cancel</h4>
			</div>
			<div class="modal-body">Are you sure you want to leave, you
				will lose your data if you continue!</div>
			<div class="modal-footer">

				<button type="button" class="btn btn-primary" id="submitBackBtn"
					value="submitBack">OK</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</div>
		</div>
	</div>
</div>







