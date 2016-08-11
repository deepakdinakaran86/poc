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
</style>

<spring:eval var="getVehicleTypeList"
	expression="@propertyConfigurer.getProperty('fms.vehicleType.list')" />

<form action="vehicle_type_view" id="vehicle_type_view" method="post">
	<input class="form-control" type="hidden" id="vehicle_type_name" name="value" />
</form>

<form action="vehicle_type_create" id="vehicle_type_create" method="post">
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
		<h1>Vehicle Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="#">Vehicle Type</a></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
		<h4 class="pull-left pageTitleTxt">Vehicle Type</h4>
			<div class="fms-btngroup text-right">
		<c:if test="<%=hasPermissionAccess(\"vehicle_types\",\"create\")%>">
				<button type="button" class="btn btn-primary ripplelink"
					onclick="FnCreateVehicleType()">Create</button>
		 </c:if>	
			</div>
		</div>
		<div class="box box-info">
			<div class="box-header with-border hidden">
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse">
						<i class="fa fa-minus"></i>
					</button>
				</div>
			</div>
					<div class="box-body">
						<div class="row">
							<div class="col-md-12">
								<div id="vehicle-type-list"></div>
							</div>
						</div>
					</div>
		
					<!-- /.col 9 -->


		<!-- /.row -->
		</div>

	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->


<spring:url value="resources/js/vehicletype/list.js" var="deviceListJS" />
<script src="${deviceListJS}"></script>


<script type="text/javascript">
var getVehicleTypeList = "${getVehicleTypeList}";
var domainCon = '${domain}';
</script>
<c:if test="${not empty vehicleTemplate}">
	<script>
	vehicleTemplate= "${vehicleTemplate}";
					</script>
</c:if>	