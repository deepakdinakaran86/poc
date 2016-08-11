<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<style type="text/css">
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
	background-image:
		url("https://10.234.60.46:9445/portal/images/domains/default/users//user.png");
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

a.add_button {   
    position: absolute;
    top: 15px;
    z-index: 100;
    left: 10px;
}

a.remove_button {
    position: absolute;
    top: 9px;
    right: 26px;
}
.formelement_addition.col-md-6 {
    margin: 0px 0 0 0;
}
.formelement_addition input {
    margin: 4px 0 0 0;
}
a.add_button i {
    font-size: 17px !important;
}

section.field_container {
    margin: 12px 0 0 0;
}
</style>

<script type="text/javascript">
		$(document).ready(function(){
		
			var viewTagTypeResponse='${viewTagTypeResponse}';
				if(viewTagTypeResponse != '') {
				var viewTagTypeRes =  $.parseJSON(viewTagTypeResponse);
					//alert(viewTagTypeRes);
					FnResTagTypeDetails(viewTagTypeRes);
				}
				
					var createTagTypeFields='${createTagTypeFields}';
				if(createTagTypeFields != '') {
				var createTagTypeF =  $.parseJSON(createTagTypeFields);
					//alert(createTagTypeF);
					FnPopulateTagTypeFields(createTagTypeF);
				}
			
		});
		
		
	</script>
	
	


<!-- <script type="text/x-kendo-template" id="tenantSearchtemplate"> -->
<!-- 	<div class="toolbar" > -->
<!-- 		<input type="text" id="searchTenantId" class="form-control-custom" placeholder="Search tenant"/> -->
<!-- 	</div> -->
<!-- </script> -->


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Tag Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/tagtype_home">Tag Type</a></li>
			<li class="active">Create </li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left">Create Tag Type</h4>
			<div class="fms-btngroup text-right">
				<ul class="btnanimation">
					<li class=" form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i><button href="javascript:void(0)" class="gapp-tag-type-cancel" id="gapp-tag-type-cancel" onclick="FnCancel()"><i>Cancel</i></button></li>
					<!--<li class="edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i><a href="javascript:void(0)"  id="gapp-user-cancel" onclick="FnEditTagTye()"><i>Edit</i></a></li> -->
					
					
					<c:if test="<%=hasPermissionAccess(\"tag_type\",\"create\")%>">
						<li class="primary form-action-save ripplelink"><i class="fa fa-check" aria-hidden="true"></i><button href="#" id="gapp-tag-type-save" class="gapp-tag-type-save" onclick="return FnSaveTagType()"><i>Save</i></button></li>
					</c:if>
				</ul>
				<!--<button type="button" class="btn btn-default" name="gapp-user-cancel" id="gapp-user-cancel" onclick="FnCancelCreate()"> Cancel</button>
      				  <button type="button" class="btn btn-primary"  name="gapp-user-save" id="gapp-user-save" onclick="return FnSaveTag()"
                tabindex="7">Save</button>-->
                
			</div>

		</div>
		<!-- /.box-header -->
		<form name="gapp-tag-type-create-form" id="gapp-tag-type-create-form"
			 enctype="multipart/form-data" method="post" role="form" autocomplete="off" action="/FMS/tagtype_create_service">
			<div class="row">
				<div class="col-md-4">
					<div class="box box-info">
						<div class="box-header hidden">
							<h3 class="box-title "></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-box-tool"
									data-widget="collapse">
									<i class="fa fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="box-body" style="height:420px;">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label for="tagTypeName" style="margin: 0 0 18px 0 !important;">Tag Type Name <span class="required text-red" aria-required="true" >* </span></label> 
										<input type="text" name="Tag Type" class="form-control" id="tagType"
											tabindex="1" required=""
											onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)"
											aria-invalid="true" placeholder="Enter Tag Type Name">
										<!--  <span class="help-block">Tag Type Name not specified</span> -->
									</div>
<!-- 									<div class="form-group" style="margin:34px 0 0 0"> -->
<!-- 										<label for="">Status</label>&nbsp; &nbsp;&nbsp; &nbsp; <label -->
<!-- 											for="status_active" class="text-green"> <input -->
<!-- 											type="radio" name="status" class="minimal" value="ACTIVE" -->
<!-- 											checked>&nbsp; Active -->
<!-- 										</label>&nbsp;&nbsp; <label for="status_inactive" class="text-red"> -->
<!-- 											<input type="radio" name="status" class="minimal-red" -->
<!-- 											value="INACTIVE">&nbsp; In Active -->
<!-- 										</label> -->
<!-- 									</div> -->
								</div>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="col-md-8 field_category">	
				
						<div class="box box-info">				
						<div class="box-header hidden">
							<h3 class="box-title "></h3>
							<div class="box-tools pull-right">
								<button type="button" class="btn btn-box-tool"
									data-widget="collapse">
									<i class="fa fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="box-body">							
							<label for="form_control_1"><strong>Tag Type Fields</strong></label> 
							<a href="javascript:void(0);" class="add_button"	title="Add field"	style=" position: relative; top: -1px;">
							<i class="fa fa-plus-circle" title="Add new field"></i></a>
						
							<section class="field_container">
								<div class="field_wrapper" style="display: block; clear: both;"
									id="fieldsContainer">
									<div class="col-md-6">
										<input type="text" class="form-control" name="tagTypeValues" value="name" readonly />
									</div>
								</div>
							</section>
							</div>
				
				</div>
				
				
				</div>
			</div>
		</form>
		<!-- /.row -->
		<!-- /.box-body -->

		<form action="/FMS/tagtype_create_service" id="tag_type_create_service" method="post">
			<input class="form-control" type="hidden" id="tagTypeName"
				name="tagTypeName" />
<!-- 			<input class="form-control" type="hidden" id="status" -->
<!-- 				name="status" /> -->
			<input class="form-control" type="hidden" id="tagTypeValues"
				name="tagTypeValues" />
		</form>

	</section>
	<!-- /.content -->
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<spring:url value="resources/js/tagtype/create.js" var="clientCreateJS" />
<script src="${clientCreateJS}"></script>