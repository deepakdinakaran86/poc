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
span.k-in.k-state-selected {
    background: #158dc1;
    color: #fff;
    /*padding: 0 5px;*/
    text-transform: capitalize;
}

.search-client {
    height: 35px;
    padding: 6px 12px;
    font-size: 14px;
    color: #555;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 92%;
    margin-bottom: 15px;
}

span.k-in > span.highlight {
    background: #7EA700;
    color: #ffffff;
    border: 1px solid green;
    padding: 1px;
}

span.k-in > span.highlight {
    background:orange
}

.box-body{
padding-top:10px;}
</style>

<spring:eval var="getAllDevices"
	expression="@propertyConfigurer.getProperty('fms.device.list')" />
<spring:eval var="getAllPoints"
	expression="@propertyConfigurer.getProperty('fms.device.configurations')" />
<spring:eval var="getDevicesOfTenant"
	expression="@propertyConfigurer.getProperty('fms.device.children')" />

<form action="device_view" id="managedevice" method="post">
	<input class="form-control" type="hidden" id="update_value"
		name="value" />
</form>

<form action="device_assign" id="assign_device" method="post" hidden="true">
</form>

<form action="write_back" id="wb-form" method="post" hidden="true">
	<input class="form-control" type="hidden" id="wb_value" name="value" />
</form>

<form action="live_track" id="live-form" method="post" hidden="true">
	<input class="form-control" type="hidden" id="live_value" name="value" />
	<input class="form-control" type="hidden" id="live_key" name="key" />
</form>

<form action="historytracking" id="hist-form" method="post"
	hidden="true">
	<input class="form-control" type="hidden" id="hist_value" name="value" />
</form>

<form action="event_conf" id="event_conf-form" method="post"
	hidden="true">
	<input class="form-control" type="hidden" id="ec_value" name="value" />
</form>

<script type="text/x-kendo-template" id="tenantSearchtemplate">
	<div class="toolbar" >
		<input type="text" id="searchTenantId" class="form-control-custom" placeholder="Search tenant"/>
	</div>
</script>


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Device Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="#">Devices</a></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			 <h4 class="pull-left">Device List</h4>
			<div class="fms-btngroup text-right">
			 <c:if test="<%=hasPermissionAccess(\"devices\",\"assign device\")%>">
				<button type="button" class="btn btn-primary ripplelink"
					onclick="FnNavigateClientPage()">Assign
					Device</button>
					</c:if>
			</div>
		</div>
		<div class="row">
			<!-- Search client -->
			<div class="col-md-3">
				<div class="box box-info" style="height: 560px">
					<div class="box-header">
						<h3 class="box-title">Clients</h3>
						<div class="box-tools pull-right">
							<button type="button" class="btn btn-box-tool hidden"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body ">
						<div class="row">
							<div class="col-md-12">
								<input id="treeViewSearchInput" class="search-client" placeholder="Search Client" />
								<div class="treeview margintop10 "
									style="height: 435px; overflow: auto;">
									<div id="gapp-clients-list"></div>

								</div>
							</div>
						</div>
						<!-- /.row -->
					</div>
					<!-- /.box-body -->
				</div>

			</div>
			<!-- End Search client -->

			<div class="col-md-9">
				<div class="box box-info">
					<div class="box-header">
						<h3 class="box-title">Devices</h3>
						<div class="box-tools pull-right">
							<button type="button" class="btn btn-box-tool hidden"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<div class="box-body">
						<div class="row">
							<div class="col-md-12">
								<div id="gapp-device-list"></div>
							</div>
						</div>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col 9 -->
		</div>
		<!-- /.row -->

	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->


<spring:url value="resources/js/device/list.js" var="deviceListJS" />
<script src="${deviceListJS}"></script>


<script type="text/javascript">
$('body').addClass('sidebar-collapse');
var getAllDevices = "${getAllDevices}";
var getAllPoints = "${getAllPoints}";
var getDevicesOfTenant = "${getDevicesOfTenant}";

var payload = ${payload};
var identifier = ${identifier};
</script>