<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

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

a.remove_button {
	position: absolute;
	top: 9px;
	right: 26px;
}

a.add_button {
	margin-left: 12px;
	position: absolute;
	top: 15px;
	z-index: 100;
	left: 30px;
}

.formelement_addition {
	margin: 6px 0;
}

div.fileUpload {
     position: relative;
    overflow: hidden;
    margin: 10px;
   /* background-image: url("https://localhost:9445/portal/images/domains/default/users//user.png");*/
  	background-image: url("resources/images/POI.png");
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


</style>

<spring:eval var="getPOIType"
	expression="@propertyConfigurer.getProperty('fms.poitype.get')" />
<spring:eval var="getPOITypeList"
	expression="@propertyConfigurer.getProperty('fms.poitype.list')" />
	
<form action="poi_delete" id="poi_delete" method="post">
	<input class="form-control" type="hidden" id="identifier"
		name="poiIdentifier" />
	<input class="form-control" type="hidden" id="domain"
		name="domainName" />
	<input class="form-control" type="hidden" id="poiNameTemp"
		name="poiName" />
</form>

<form action="poi_def_home" id="poi_cancel" method="get">
</form>

<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>POI Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/poi_def_home">POI</a></li>
			<li class="active">Create</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left pageTitleTxt">Create POI</h4>
			<div class="fms-btngroup text-right">
				<ul class="btnanimation">				
						<li class=" form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
								<button id="poi-def-cancel" onclick="FnCancelPOI()"><i>Cancel</i></button></li>
									
						 <c:if test="<%=hasPermissionAccess(\"poi_management\",\"delete\")%>">
								<li class="delete form-action-delete ripplelink"  id="poidef-delete">
								<i class="fa fa-trash" aria-hidden="true"></i>
								<button  href="javascript:void(0)"
								id="poi-def-delete" onclick="FnDeletePoi()" style="color:#000" ><i>Delete</i></button></li>
						 </c:if>
						 <c:if test="<%=hasPermissionAccess(\"poi_management\",\"edit\")%>">
								<li class="delete form-action-edit ripplelink"  id="poidef-edit">
								<i class="fa fa-pencil-square-o"aria-hidden="true"></i>
								<button href="javascript:void(0)"
								id="poi-def-edit" onclick="FnEditPoi()"><i>Edit</i></button></li>
						</c:if>
						
					 <c:if test="<%=hasPermissionAccess(\"poi_management\",new String[]{\"create\",\"edit\"})%>">					 
								<li class="primary form-action-save ripplelink" id="poidef-save">
								<i class="fa fa-check" aria-hidden="true"></i>
								<button	href="#"  id="poi-def-save" onclick="FnSavePoi();"><i>Save</i></button></li>					
					 </c:if>
				</ul>
			</div>



		</div>
		<!-- SELECT2 EXAMPLE -->
		<div class="box box-info">

			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
				<form:form role="form" action="poi_save" commandName="poi_view"
						id="poi_view" autocomplete="off" novalidate="novalidate"  enctype="multipart/form-data">
					<div class="col-md-3">
						<!-- BEGIN PROFILE IMAGE UPLOAD -->
							<div class="form-group">
								<div class="fileUpload">
									<div class="upload-addicon">
										<span class="custom-span">+</span>
										<p class="custom-para">Add Image</p>
									</div>

									<form:input type="file" name="poi-logo" id="poi-logo"
										class="upload k-valid" accept="image/*" path="fileDatas" />
									<img src="" id="poi-image-preview" width="200" height="200">

								</div>
							</div>
							<!-- END PROFILE IMAGE UPLOAD -->
					</div>
					<div class="col-md-5">
					<form:hidden path="image" />
					<form:input type="hidden" path="poiNameSub" id="poiNameSub" name="poiNameSub"/>
						<div class="form-group  "><!--  has-error -->
							<label for="userName">POI Name <span class="required"
								aria-required="true">* </span></label>
								<form:input type="text" class="form-control" path="poiName"
									id="poiName" placeholder="Enter POI name" onkeypress="return FnAllowAlphaNumericOnly(event)"   required="true" />
							<!--  <span class="help-block">User name not specified</span>-->
						</div>
						<div class="form-group ">
							<label for="poiTypeDummy">POI Type</label> 
							<select id="poiTypeDummy" onchange="FnGetAssetFields();"
								class="form-control select2" name="POI Type" style="width: 100%;" required="true">
								<option selected="selected">Please Select POI Type</option>
							</select>
							<form:input type="hidden" class="form-control" path="poiType"
									id="poiType" placeholder="Enter POI Name" />
						</div>
						<!-- BEGIN LOCATION  : longitude,latitude-->
						<div class="form-group">
							<label class="col-md-12 control-label form-label"  style="padding-left: 0; !important" for="">
								Location <span class="required" aria-required="true" > * </span>
							</label>

							<div class="col-md-4 ">
								<form:input type="text" class="form-control" path="latitude"
									id="latitude" onkeypress="return FnAllowNumbersOnly(event)"
									placeholder="latitude"  required="true"  />
							</div>
							<div class="col-md-4">
									<form:input type="text" class="form-control" path="longitude"
									id="longitude" onkeypress="return FnAllowNumbersOnly(event)"
									placeholder="longitude"  required="true" />
							</div>
							<span class="input-group-btn btn-right col-md-4">
								<button class="btn btn-block btn-primary"
									style="width: 100px; margin: 0 0 12px 0" id="deviceLocModel"
									type="button" data-toggle="modal"
									title="Location picker to get lat and long">
									<i class="fa fa-map-marker"></i> picker
								</button>
							</span>
							<div class="clearfix"></div>
						</div>
						<!-- END LOCATION  : longitude,latitude-->

						<div class="form-group">
							<label>Radius <span class="required text-red "
								aria-required="true">* </span></label>
								<form:input type="text" class="form-control" path="radius"
									id="radius"  onkeypress="return FnAllowNumbersOnly(event)"  placeholder=" Please enter radius" required="true"/>
								
						</div>
						<div class="form-group">
							<label for="" style="margin: 0 12px 0 0;">Status</label>
							<form:radiobutton path="status" class="minimal" value="ACTIVE"/><span  class="text-green">&nbsp;Active</span>&nbsp;&nbsp; 
							<form:radiobutton path="status" class="minimal-red" value="INACTIVE"/><span  class="text-red">&nbsp;In Active</span> 
						</div>
						<div class="form-group">
							<label for="contactNumber">Description </label>
							<form:textarea class="form-control" path="description"
									id="description" placeholder="Enter Description" rows="5"  />
							<form:input type="hidden" class="form-control" path="poiIdentifier"
									id="poiIdentifier" placeholder="Enter POI Name" />
							<form:input type="hidden" class="form-control" path="poiTypeValues"
									id="poiTypeValues" placeholder="Enter POI Name" />
							<form:input type="hidden" class="form-control" path="action"
									id="action" placeholder="Enter POI Name" />
							<form:input type="hidden" class="form-control" path="domainName"
									id="domainName" placeholder="Enter POI Name" />
						</div>
					</div>
					</form:form>
					<div class="col-md-4" id="fieldsSection" style="display: none">
						<div class="portlet1 dark bordered">
							<div class="portlet-body form">
								<div class="form-body">
									<div class="col-md-12" id="fieldsContainer"></div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>

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
<div class="modal fade" id="draggable" tabindex="-1" role="draggable"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header bg-green-meadow">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">Location</h4>
			</div>
			<div class="modal-body">
				<form>
					<fieldset class="gllpLatlonPicker">

						<div class="form-group col-md-12">
							<div class="input-group">
								<div class="input-icon">
									<i class="fa fa-street-view"></i> <input type="text"
										class="form-control gllpSearchField"
										placeholder="Type the location name" name="password"
										placeholder="password">
								</div>
								<span class="input-group-btn">
									<button class="btn default gllpSearchButton" type="button"
										value="search">
										<i class="fa fa-arrow-left fa-fw"></i> Search
									</button>
								</span>
							</div>


						</div>
						<div class="col-md-12">
							<div class="gllpMap" style="height: 320px">Google maps
								loading...</div>
						</div>

						<div class="col-md-12" style="margin: 16px 0 0 0;">
							<div class="col-md-4">
								<input type="text" class="gllpLatitude" id="gllpLatitude"
									value="25.2048493" />
							</div>
							<div class="col-md-6">
								<input type="text" class="gllpLongitude" id="gllpLongitude"
									value="55.270784" />
							</div>
							<div class="col-md-3 hidden">
								<input type="text" class="gllpZoom" value="3" />
							</div>
							<div class="col-md-2">
								<input type="button" class="gllpUpdateButton" value="update" />
							</div>


						</div>

					</fieldset>
				</form>

			</div>
			<div class="modal-footer">
				<!-- <button type="button" class="btn default" data-dismiss="modal">Close</button> -->
				<button type="button" id="deviceLocationSave" class="btn green"
					data-dismiss="modal">Save</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>


<!-- delete-poi-def modal -->
<div class="modal fade" id="delete-poi-def" role="dialog">
          <div class="modal-dialog modal-md">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <i class="fa fa-times-circle-o" aria-hidden="true"></i></button>
                <h4 class="modal-title">POI: Delete</h4>
              </div>
              <div class="modal-body">
                <p>You are about to delete. Do you want to proceed?</p>
              </div>
              <div class="modal-footer">                
                <button type="button" id="btnYes" class="btn btn-primary" data-value="1">Yes</button>
                <button type="button"   class="btn btn-default " data-dismiss="modal" data-value="0">No</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
 </div>



<spring:url value="resources/js/poidef/add.js" var="poiAddListJS" />
<script src="${poiAddListJS}"></script>


<script type="text/javascript">

	$('#deviceLocationSave').on('click', function() {
		console.log('username : ' + $("#gllpLatitude").val());
		console.log('result : ' + $("#gllpLongitude").val());

		var lat = $("#gllpLatitude").val();
		var long = $("#gllpLongitude").val();
		//alert(lat + ' ' + long);

		lat = precise_round(lat, 7);
		long = precise_round(long, 7);

		$('#latitude').val(lat);
		$('#longitude').val(long);

		$('#latitude').focus();
		$('#longitude').focus();

	});

	$('#deviceLocModel').click(function() {
		//alert('deviceLocModel');
		$('#draggable').modal('show');
		//$("#draggable").css("z-index", "9999999999");
		setTimeout(function() {
			$(".gllpLatlonPicker").each(function() {
				$(document).gMapsLatLonPicker().init($(this));
			});

		}, 10000);

	});

	var poiAction = "${action}";
	var getPOITypeList = "${getPOITypeList}";
	var getPOIType = "${getPOIType}";
	
	

</script>