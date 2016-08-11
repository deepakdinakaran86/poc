<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<link rel="stylesheet" href="resources/plugins/croppie/croppie.css" type="text/css"/>
<style type="text/css">
.tenanthead {
	background-color: #ccc;
	margin-left: -15px;
	padding: 10px 10px;
	text-align: center;
	font-size: 16px;
	font-weight: bold;
}

a.disabled {
	cursor: default;
	background-image: none;
	opacity: 0.35;
	filter: alpha(opacity = 65);
	-webkit-box-shadow: none;
	-moz-box-shadow: none;
	box-shadow: none;
	color: #333;
	background-color: #E6E6E6;
}

.form-control-custom {
	height: 20px;
	padding: 6px 12px;
	font-size: 14px;
	color: #555;
	border: 1px solid #ccc;
	border-radius: 4px;
	width: 85%;
}

#tenantGrid th.k-header {
	display: none;
}

.diviceactions a {
	margin: 2px;
}



.formelement_addition {
	margin: 6px 0;
}

div.fileUpload {
    position: relative;
    overflow: hidden;
    margin: 10px;
    background-image: url("resources/images/vehicle.png");
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
    filter: alpha(opacity=0);
    height: 100%;
    text-align: center;
    z-index: 99;
}
div.fileUpload input.upload + img {
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

.add_button{
}

.box.box-info{min-height:550px;}

.upload-demo #upload-demo, .upload-demo .upload-result, .upload-demo.ready .upload-msg {
	display: none;
}

.croppie-container {
	padding: 30px;
}

.cr-boundary {
	margin: 0px !important;
}

.croppie-container .cr-slider-wrap {
	width: 48%;
	margin: 10px 0 0 0;
}

</style>

<spring:eval var="getVehicleTypeList"
	expression="@propertyConfigurer.getProperty('fms.vehicletype.list')" />
<form action="vehicle_type_home" id="vehicle_type_cancel" method="get">
</form>

<script type="text/x-kendo-template" id="tenantSearchtemplate">
	<div class="toolbar" >
		<input type="text" id="searchTenantId" class="form-control-custom" placeholder="Search tenant"/>
	</div>
</script>


<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Vehicle Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/vehicle_type_home">Vehicle Type</a></li>
			<li class="active">Create Vehicle Type</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left">Create Vehicle Type</h4>
			<div class="fms-btngroup text-right">
				<ul class="btnanimation">
				
					<li class="form-action-cancel"><i class="fa fa-times" aria-hidden="true"></i>
						<button id="gapp-user-cancel" onclick="FnCancelVehicleType()"><i>Cancel</i></button></li>
						
					 <c:if test="<%=hasPermissionAccess(\"vehicle_types\",\"create\")%>">	
					<li class="primary  form-action-save"  id="vehicletype-save"><i class="fa fa-check" aria-hidden="true"></i>
					<button	onclick="return FnSaveVehicleType()" id="fms-template-save"><i>Save</i></button></li>
					 </c:if>
				</ul>			
			</div>
		</div>
		<!-- /.box-header -->
		<div class="row">
			<div class="col-md-4">
			<form:form name="vehicle_type_save" id="vehicle_type_save" method="post" role="form" 
				autocomplete="off" action="vehicle_type_save" commandName="vehicle_type_view" enctype="multipart/form-data">
				<div class="box box-info">
					<div class="box-header hidden">
						<h3 class="box-title "></h3>
						<div class="box-tools pull-right">
							<!--  	<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>-->
						</div>
					</div>
					<div class="box-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="userName">Category Name <span class="required text-red" aria-required="true">* </span></label>
								<form:input type="text"
								path="vehicleType" class="form-control" id="vehicleType" name="vehicleType"
								tabindex="1" required=""
								onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
								aria-invalid="true" placeholder="Enter Vehicle Type Name" onkeyup="changeToUpperCase(this)"/>
								<form:hidden path="image" value=""/>
								<form:hidden path="vehicleTypeValues" />
								</div>
							<div class="form-group">
								<label for="" style="margin: 0 12px 0 0;">Status</label>
								<div class="col-md-12" style="padding-left:0px">
								<form:radiobutton path="status" class="minimal" value="ACTIVE"/><span  class="text-green" style="padding-left:5px;padding-right:5px;">Active</span> 
								<form:radiobutton path="status" class="minimal-red" value="INACTIVE"/><span  class="text-red" style="padding-left:5px;">In Active</span>
								</div> 
							</div>
							
								<!-- BEGIN PROFILE IMAGE UPLOAD -->
								
								<div class="form-group" style="padding-top:20px;">
									<label class="col-md-12 control-label" style="padding-left:0px" for="form_control_1">Upload
										
									</label>
									<a class='btn btn-primary' href='javascript:void(0)' style="position:relative">
									   <i class="fa fa-plus"></i>  <span>	 Vehicle Type Logo </span>
										<form:input type="file"  name="vehicletype-logo" id="vehicletype-logo" size="40"  onchange='$("#vehicletype-logo-info").html($(this).val());' accept="image/*" path="fileDatas" style='position:absolute;z-index:2;top:0;left:0;filter: alpha(opacity=0);-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";opacity:0;background-color:transparent;color:transparent;' />
									</a>&nbsp;<span class='label label-info' id="vehicletype-logo-info"></span>
								</div>													
								
								<!-- END PROFILE IMAGE UPLOAD -->	
								
								<div id="upload-demo" class="croppie-container"></div>							
								
							</div>
						</div>
					</div>
				</div>
				<form:hidden path="icon" name="vehicletype-icon" id="vehicletype-icon" value="" />
			</form:form>
			</div>
			<div class="col-md-8 field_category" style="padding-left: 0px">

				<div class="box box-info">
					<div class="box-header">
						<h3 class="box-title">Category Fields</h3>
						<a href="javascript:void(0);" class="add_button"
									title="Add field">
									<i	class="fa fa-plus-circle" aria-hidden="true"></i></a>
					</div>
					<div class="box-body">
						<div class="row">
							<div class="col-md-12">
								

								<section class="field_container">
									<div class="field_wrapper" style="display: block; clear: both;"
										id="fieldsContainer">
										<div class="col-md-6">
											<input type="text" class="form-control formelement_addition"
												name="categoryFields[]" value=""
												onkeypress="return FnAllowAlphabetsOnly(event)" placeholder="Enter Category" />
										</div>
									</div>
								</section>
							</div>
						</div>
					</div>


				</div>


			</div>

		</div>
		<!-- /.row -->
		<!-- /.box-body -->



	</section>
	<!-- /.content -->
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<spring:url value="resources/plugins/croppie/croppie.js" var="deviceListJS" />
<script src="${deviceListJS}"></script>
<spring:url value="resources/js/vehicletype/add.js" var="deviceListJS" />
<script src="${deviceListJS}"></script>


<script type="text/javascript">
	var vehicleAction='${action}';
	 //Flat red color scheme for iCheck
	 $(document).ready(function(){
	   $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
      checkboxClass: 'icheckbox_minimal-blue',
      radioClass: 'iradio_minimal-blue'
    });
       //Red color scheme for iCheck
    $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
      checkboxClass: 'icheckbox_minimal-red',
      radioClass: 'iradio_minimal-red'
    });
	 });
  
</script>
