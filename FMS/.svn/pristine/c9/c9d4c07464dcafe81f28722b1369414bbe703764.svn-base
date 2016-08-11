<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>


<style type="text/css"> 
#grid-view .k-grid-header .k-header {
	background-color: #f44336;
}

#grid-view .k-header a.k-link,.k-grid-header th {
	color: #000 !important;
}

.paddingRight15 {
	padding-right: 15px;
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

</style>

<script type="text/javascript">
</script>

<!-- Content Wrapper. Contains page content -->
<style>
aside.control-sidebar {
	
}

.search-input {
	font-family: Courier
}

.search-input,.leaflet-control-search {
	max-width: 400px;
}
</style>
<spring:url value="resources/plugins/jquery.form.js" var="fomrJS" />
<script src="${fomrJS}"></script>
<spring:url value="resources/js/equipment/asset/add.js"	var="vehicleaddJS" /><script src="${vehicleaddJS}"></script>
<!-- Scripts for Tag(Geo Map) section -->

<script id="rowTemplate" type="text/x-kendo-tmpl">		  
		<tr role="row" id="#: rowid#" class="geofence_grid">
		  <td role="gridcell">#: latitude#</td>
		  <td role="gridcell">#: longitude #</td>
		  <td role="gridcell" class="formelement_addition"><a href="javascript:void(0);" class="remove_marker" title="Remove Marker"><i class="fa fa-minus-circle"></i></a></td>
		</tr>     
	</script>

<form action="point_list" id="point_list" method="post">
<input class="form-control" type="hidden" id="selectedPoints" name="value" />
</form>

<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Vehicle Management</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/vehicle_list">Vehicles</a></li>
			<li class="active">Create</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left">Create Vehicle</h4>
			<div class="fms-btngroup text-right">
							
				<ul class="btnanimation">
				<li class="no-btnanimation">
					<a href="javascript:void(0);" style="padding: 10px; text-decoration: none;" name="gapp-show-tags" id="gapp-show-tags" onclick="FnShowTags()"><i
					class="fa fa-tags aria-hidden="true"></i>Tags </a>
				</li>
					<li class="primary form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
					<button name="gapp-asset-cancel" id="gapp-asset-cancel" class="" onclick="FnCancelAsset()" tabindex="15"><i>Cancel</i></button></li>
						
						
					<c:if test="<%=hasPermissionAccess(\"vehicle_management\",\"edit\")%>">
					<li class="delete form-action-edit ripplelink" id="poidef-edit"><i class="fa fa-pencil-square-o"
						aria-hidden="true"></i>
						<button type="button" name="gapp-asset-edit" id="gapp-asset-edit" data-toggle="modal" onclick="FnEditAsset()" tabindex="15" ><i>Edit</i></button></li>
						</c:if>
					 <c:if test="<%=hasPermissionAccess(\"vehicle_management\",new String[]{\"create\",\"edit\"})%>">	
					<li class="primary form-action-save ripplelink"><i class="fa fa-check" aria-hidden="true"></i>
					<button	href="#"  name="gapp-asset-save" id="gapp-asset-save" onclick="FnSaveAsset()"><i>Save</i></button></li>
					 </c:if>
				</ul>
				
			</div>
		</div>
		<div class="box box-info">
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="col-md-12">
					<form:form name="gapp-asset-form" id="gapp-asset-form" method="post" role="form" 
							autocomplete="off" action="vehicle_home" commandName="vehicle_home" enctype="multipart/form-data">
							<form:hidden path="image" />
							<form:hidden path="assetType" />
							<form:hidden path="assetTypeValues"/>
							<div class="row">
								<div class="col-md-12">
									<!-- Begin: Col-1 -->
									<div class="col-md-2">
										<!-- BEGIN PROFILE IMAGE UPLOAD -->
										<div class="form-group">
											<div class="fileUpload">
												<div class="upload-addicon">
													<span class="custom-span">+</span>
													<p class="custom-para">Add Image</p>
												</div>
												<form:input type="file" name="asset-logo" id="asset-logo" class="upload k-valid" accept="image/*" path="fileDatas"/>
												<img src=""	id="asset-logo-preview" width="200" height="200">
				
											</div>
										</div>
										
										<!-- END PROFILE IMAGE UPLOAD -->
									</div>
									<!-- End: Col-1 -->
									<!-- Begin: Col-2 -->
									<div class="col-md-5">
										<!-- BEGIN Asset Name -->
										<div class="form-group form-md-line-input">
											<label for="assetName">
												Vehicle Name <span class="required" aria-required="true">
													* </span>
											</label> 
											<form:input type="text" class="form-control" path="assetName" name="assetName" 
												id="assetName" required="true" 
												data-available="true"
 												data-required-msg="Please enter asset name"  
 												data-available-url="/markers/1.0.0/markers/validate" 
 												data-available-msg="Vehicle already exists" tabindex="1" 
												placeholder=" Please vehicle name" 
 												onkeypress="return FnAllowAlphaNumericOnly(event)" /> 
																	
											<div class="form-control-focus"></div>
										</div>
										<!-- END Asset Name -->

										<!-- Type -->
										<div class="form-group form-md-line-input">
											<label for="assetType">Vehicle
												Type <span class="required" aria-required="true"> * </span>
											</label> <select class="form-control" name="assetTypeList" id="assetTypeList"
												required="true" data-required-msg="Please select a category"
												placeholder=" Please select vehicle category" tabindex="2"
												onchange="FnGetAssetFields()">
												<option value="">Please select a category</option>
											</select>
											<div class="form-control-focus"></div>
										</div>
										<!-- END Type -->

										<!-- BEGIN Asset Id -->
										<div class="form-group form-md-line-input">
											<label  for="assetId">
												Vehicle Id <span class="required" aria-required="true">
													* </span>
											</label> <form:input type="text" class="form-control " path="assetId" name="assetId"
												id="assetId" tabindex="3"
												placeholder=" Please enter vehicle id" required="true"
												data-required-msg="Please enter vehicle id" />
											<div class="form-control-focus"></div>
										</div>
										<!-- END Asset Id -->

									</div>
									<!-- End: Col-2 -->

									<!-- Begin: Col-3 -->
									<div class="col-md-5">
										<!-- BEGIN Asset Id -->
										<div class="form-group form-md-line-input">
											<label  for="serialNumber">
												Serial Number </label> <form:input type="text" class="form-control"
												path="serialNumber" name="serialNumber" id="serialNumber" tabindex="4"
												placeholder=" Please enter serial number" />
											<div class="form-control-focus"></div>
										</div>
										<!-- END Asset Id -->

										<!-- BEGIN Chiller Rating -->
										<div class="form-group form-md-line-input">
											<label  for="description">Description</label>
											<form:textarea  type="text" rows="5" class="form-control "
												name="description" path="description" id="description" tabindex="5"
												style="border: 1px solid #D4CCCC;"
												placeholder=" Please enter description" />
											<div class="form-control-focus"></div>
										</div>
										<!-- END Chiller Rating -->

									</div>
									<!-- End: Col-3 -->
									<hr/>
									<div style="clear: both"></div>
									<div class="col-md-12" id="fieldsSection" style="display: none">
										<div class="portlet1 dark bordered">
											<div class="portlet-body form">
												<div class="form-body">
													<div class="col-md-12" id="fieldsContainer"></div>
													<div class="clearfix"></div>
												</div>
											</div>
										</div>
									</div>
	<button type="button" class="btn btn-danger pull-right ripplelink" style="margin-top:10px;" name="btn-asset-points" id="btn-asset-points" title="View Point Details" onclick="FnShowAssetPointDetails()" disabled><i class="fa fa-bars" aria-hidden="true" style="padding-right: 10px;"></i>Points</button>
								</div>
							</div>
							<input type="hidden" id="latitude" name="latitude" value="" />
							<input type="hidden" id="longitude" name="longitude" value="" />
							<input type="hidden" id="tagArray" name="tagArray" value="" />
							
							<input type="hidden" id="tagDetails" name="tagDetails" value="" />
							<input type="hidden" id="geoDetails" name="geoDetails" value="" />
							<input type="hidden" id="gblAssetTypeId"
								name="gblAssetTypeId" value="" /> <input type="hidden"
								name="tag_update_flag" id="tag_update_flag" value="false" /> <input
								type="hidden" id="vehicle_mode" name="vehicle_mode"
								value="update" /> <input type="hidden" id="vehicle_id"
								name="vehicle_id" /> <input type="hidden" id="vehicle_name"
								name="vehicle_name" value="" /> <input type="hidden"
								id="vehicle_type" name="vehicle_type" value="" />
						<!-- *** START TAGGING SECTION *** -->
						</form:form>
						<jsp:include page="../../getTagTemplate.jsp" />
					</div>
				</div>
				<!-- /.row -->
			</div>
			
			<!-- /.box-body -->
		</div>
		<!-- /.box -->

		

	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<c:if test="${not empty tags}">
	<script>
					 tags= ${tags};
					</script>
</c:if>
<c:if test="${not empty geoDetails}">
	<script>
					 geoDetails= ${geoDetails};
					</script>
</c:if>
<c:if test="${not empty vehicleType}">
	<script>
					 vehicleType= ${vehicleType};
					</script>
</c:if>
<script>VarVehicleId ='<c:out value='${vehicleId}'/>'</script>
<script>VarEditAssetId ='<c:out value='${vehicleEdit}'/>'</script>
<c:if test="${not empty vehicleDetails}">
	<script>
					 vehicleDetails= ${vehicleDetails};
					</script>
</c:if>
<c:if test="${not empty image}">
	<script>
					 image= ${image};
					</script>
</c:if>
<c:if test="${not empty tagResponse}">
	<script>
					 tagResponse= ${tagResponse};
					</script>
</c:if>
<c:if test="${not empty pointList}">
	<script>
	pointList= ${pointList};
					</script>
</c:if>

<c:if test="${not empty onerrorTag}">
	<script>
	varTagArray= ${onerrorTag};
					</script>
</c:if>

<c:if test="${not empty onerrorLat}">
	<script>
	varOnErrLat= ${onerrorLat};
					</script>
</c:if>


<c:if test="${not empty onerrorLng}">
	<script>
	varOnErrLng= ${onerrorLng};
					</script>
</c:if>

<c:if test="${not empty error}">
	<script>
	errorFlag= ${error};
					</script>
</c:if>



<script>domainName ='<c:out value='${domainName}'/>'</script>
<form id="gapp-asset-list" role="form" action="vehicle_cancel" name="gapp-asset-list" method="get"></form>
<script>
		var vehicleType;	
		var tags;
		var VarEditAssetId;
		var VarVehicleId;
		var geoDetails;
		var vehicleDetails;
		var image;
		var tagResponse;
		var pointList;
		var varTagArray;
		var varOnErrLat;
		var varOnErrLng;
		var errorFlag;

</script>
