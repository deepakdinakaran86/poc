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
label.pull-left {
    line-height: 36px;
}
a.grid-asset-view {
    padding: 0 0 0 5px;
    cursor: pointer;
    text-transform: capitalize;
}
</style>

<spring:eval var="getAllDevices"
	expression="@propertyConfigurer.getProperty('fms.device.list')" />

<form action="poi_view" id="poi_view" method="post">
	<input class="form-control" type="hidden" id="update_value"
		name="value" /> <input class="form-control" type="hidden" id="domain"
		name="key" />
</form>

<form action="poi_create" id="poi_create" method="post"></form>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>POI Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="#">POI</a></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left"">
				<i class="pageheadericon pageheader-usericon"></i>POI
			</h4>
			<div class="fms-btngroup text-right">
			 <c:if test="<%=hasPermissionAccess(\"poi_management\",\"create\")%>">
				<button type="button" class="btn btn-primary ripplelink"
					onclick="FnCreatePOI()" name="gapp-user-save" id="gapp-user-save"
					tabindex="7">Create</button>
			 </c:if>
			</div>
		</div>
		<!-- SELECT2 EXAMPLE -->
		<div class="box box-info">
			<div class="box-header with-border hidden">
				<h4 class="box-title"></h4>

				<div class="box-tools pull-right ">
					<button type="button" class="btn btn-box-tool"
						data-widget="collapse">
						<i class="fa fa-minus"></i>
					</button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="col-md-4 margin">
						<div class="form-group">
							<label class="pull-left">POI Type</label>
							<div class="col-sm-8">
								<select class="form-control" name="assetCategory"
									id="assetCategory" onchange="FnGetPoiList(this.value)">
									<option value="">Select POI Type</option>
								</select>

							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div id="poi-list"></div>
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

<spring:url value="resources/js/poidef/list.js" var="deviceListJS" />
<script src="${deviceListJS}"></script>


<script type="text/javascript">
	var getAllDevices = "${getAllDevices}";
</script>