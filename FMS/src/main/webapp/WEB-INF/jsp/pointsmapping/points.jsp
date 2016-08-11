<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="getOwnedDevicesUrl"
	expression="@propertyConfigurer.getProperty('fms.devices.ownedlist')" />
<spring:eval var="getDevicePointsUrl"
	expression="@propertyConfigurer.getProperty('fms.devices.points')" />
<spring:eval var="mapPointsUrl"
	expression="@propertyConfigurer.getProperty('fms.devices.mapPoints')" />
	
<style>

html, body{height:100%;}
.skin-blue .main-header .navbar {
	background: radial-gradient(#197aa5 15%, #005180 60%);
}

.skin-blue .main-header .logo {
	background-color: #158dc1;
}


.box-body{
padding-top:0px;}

.search-device {
    height: 35px;
    padding: 6px 12px;
    font-size: 14px;
    color: #555;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 92%;
    margin-bottom: 15px;
}

</style>

<form action="vehicle_list" id="vehicle_list" method="get">
</form>

<form id="gapp-asset-edit" role="form" action="vehicle_home"
											name="gapp-asset-create" method="post">
											<input type="hidden" id="vehicle_id" name="vehicle_id" /> <input
												type="hidden" id="vehicle_mode" name="vehicle_mode"
												value="edit" />
										</form>

<div class="content-wrapper">
	<section class="content-header">
		<h1>Map Points</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="#">Map Points</a></li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left">Map Points</h4>
			<div class="fms-btngroup text-right">
			<ul class="btnanimation">
					<li class="primary form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
					<button id="poi-def-cancel" name="gapp-mappoint-cancel" id="gapp-mappoint-cancel" onclick="FnCancelMapPoints()"><i>Cancel</i></button></li>
					<li class="primary form-action-save ripplelink" id="poidef-save"><i class="fa fa-check" aria-hidden="true"></i>
					<button	name="gapp-mappoint-save" id="gapp-mappoint-save" onclick="return FnSaveMapPoints()"><i>Save</i></button></li>
				</ul>
			</div>
		</div>
		<form:form role="form" action="tenant_create" commandName="tenant_create" id="tenant_create" autocomplete="off"
			novalidate="novalidate">
			<div class="row" style="clear: both">
				<!-- Search client -->
				<div class="col-md-3">
					<div class="box box-info">
						<div class="box-header">
							<h3 class="box-title">Devices</h3>
							<div class="box-tools pull-right"></div>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div class="row">
								<div class="col-md-12">
									<input id="searchDeviceId" class="search-device" placeholder="Search Device" />
									<div class="treeview margintop10" style="height: 435px; overflow: auto;">
										<div id="gapp-devices-list"></div>
									</div>
								</div>
							</div>
							<!-- /.row -->
						</div>
						<!-- /.box-body -->
					</div>

				</div>
				<!-- End Search client -->

				<div class="col-md-9" style="padding-left: 0px">
					<div class="box box-info">
						<div class="box-header">
							<h3 class="box-title">Points</h3>
							<div class="box-tools pull-right"></div>
						</div>
						<div class="box-body">
							<div class="row">
								<div class="col-md-12">
									<div id="gapp-points-list"></div>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
				<!-- /.col 9 -->
			</div>
			</form:form>
				<!-- /.row -->
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<spring:url value="resources/js/pointsmapping/asset.js" var="pointMappingJS" />
<script type="text/javascript">
	var equipName = "${equipName}";
	var equipment = ${equipment};
	var getOwnedDevicesUrl = "${getOwnedDevicesUrl}";
	var getDevicePointsUrl = "${getDevicePointsUrl}";
	var mapPointsUrl = "${mapPointsUrl}";

	function FnCancelMapPoints(){
		$("#vehicle_list").submit();
	}
</script>

<script src="${pointMappingJS}"></script>


