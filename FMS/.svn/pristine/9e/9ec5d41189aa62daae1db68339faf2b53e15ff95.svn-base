<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<script src="resources/javascript/device.js"></script>
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

<form id="device_home" action="device_home" method="get"></form>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Device Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="/FMS/device_home">Device</a></li>
			<li class="active">View</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left pageTitleTxt">View Device</h4>
			<div class="fms-btngroup text-right">
				<ul class="btnanimation">
					<li class="default"><i class="fa fa-times" aria-hidden="true"></i><button
						href="javascript:void(0)" id="gapp-device-cancel"
						onclick="FnCancelDevice()"><i>Cancel</i></button>
					</li>
					<c:if test="<%=hasPermissionAccess(\"device_management\",\"edit\")%>">
					<li class="edit hidden"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>
					<button class="" href="Javascript:void(0)" id="BtnEditDevice" title="Edit the device" onclick="FnEditDevice()">
												<i class="icon-note icons"></i> Edit</button>
					</li>
					</c:if>
					 <c:if test="<%=hasPermissionAccess(\"device_management\",new String[]{\"create\",\"edit\"})%>">
					<li class="primary hidden"><i class="fa fa-floppy-o" aria-hidden="true"></i>
					<button	href="javascript:void(0)" name="gapp-device-save" id="gapp-device-save"
						 onclick="FnSaveDevice()" tabindex="18" disabled="disabled"><i>Save</i></button>
					</li>
					 </c:if>
				</ul>
			</div>
		</div>



		<!-- SELECT2 EXAMPLE -->
		<form:form role="form" action="device_view" commandName="device_view"
			id="device_view" autocomplete="off" novalidate="novalidate">

			<div class="box box-info">
				<div class="box-header with-border">
					<h4 class="box-title">Device Configuration</h4>

					<div class="box-tools pull-right">
						<button type="button" class="btn btn-box-tool hidden"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
				</div>
				<!-- /.box-header -->

				<div class="box-body">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="exampleInputEmail1">Device Name</label>
								<form:input type="text" class="form-control" path="deviceName"
									id="deviceName" placeholder="Enter Device Name" />
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Device Location</label>
								<form:input type="text" class="form-control" path="latitude"
									id="latitude"/>
									<form:input type="text" class="form-control" path="longitude"
									id="longitude" />
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Source Identifier</label>
								<form:input type="text" class="form-control" path="sourceId"
									id="sourceId" placeholder="Enter Source Identifier" />
							</div>
							<div class="form-group">
								<label>Tags</label> 
								<form:input type="text" path="tags" class="form-control"
									id="tags" />
							</div>
							<div class="form-group">
							<label for="" style="margin: 0 12px 0 0;">Status</label>
							<form:radiobutton path="status" class="text-green" value="ACTIVE"/><span  class="text-green">Active</span> 
							<form:radiobutton path="status" class="text-red" value="INACTIVE"/><span  class="text-red">In Active</span> 
						</div>
						</div>
						<!-- /.col -->
						<div class="col-md-6">
							<div class="form-group">
								<label>Make</label>
								<form:input type="text" path="make" class="form-control"
									id="make" />
							</div>
							<!-- /.form-group -->
							<div class="form-group">
								<label>Device type</label>
								<form:input type="text" path="deviceType" class="form-control"
									id="deviceType" />
							</div>
							<!-- /.form-group -->
							<div class="form-group">
								<label>Model</label>
								<form:input type="text" path="model" class="form-control"
									id="model" />
							</div>
							<!-- /.form-group -->
							<div class="form-group">
								<label>Protocol</label>
								<form:input type="text" path="protocol" class="form-control"
									id="protocol" />
								</select>
							</div>
							<!-- /.form-group -->
							<div class="form-group">
								<label>Protocol Version</label>
								<form:input type="text" path="version" class="form-control"
									id="version" />
							</div>
							<!-- /.form-group -->
							<div class="form-group">
								<label>Configuration template</label>
									<form:input type="text" path="configuration" class="form-control"
									id="configuration" />
							</div>
							<!-- /.form-group -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.box-body -->
			</div>
			<div class="">
				<div class="">
					<div class="box box-info">
						<div class="box-header">
							<h3 class="box-title">IP Configuration</h3>
							<div class="box-tools pull-right hidden">
								<button type="button" class="btn btn-box-tool"
									data-widget="collapse">
									<i class="fa fa-minus"></i>
								</button>
							</div>
						</div>
						<div class="box-body">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Network Protocol</label>
										<form:input type="text" path="nwProtocol" class="form-control"
									id="nwProtocol" />
									</div>
									
									<div class="form-group">
										<label>Device ip</label>

										<div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-laptop"></i>
											</div>
											<form:input type="text" path="deviceIp" class="form-control"
									id="deviceIp" />
										</div>
										<!-- /.input group -->
									</div>
								</div>
								<div class="col-md-6">

									<div class="form-group">
										<label for="exampleInputEmail1">Device port</label>

										<form:input type="text" path="devicePort" class="form-control"
									id="devicePort" />
									</div>

									<div class="form-group">
										<label for="exampleInputEmail1">Writeback port</label>
										<form:input type="text" path="wbPort" class="form-control"
									id="wbPort" />
									</div>
								</div>
							</div>









						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->

				</div>
				<!-- /.col (left) -->
			</div>
			<!-- /.row -->

		</form:form>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->


<!--commonFunctions -->
<spring:url value="resources/js/device/add.js" var="deviceListJS" />
<script src="${deviceListJS}"></script>


<script type="text/javascript">
	function FnCancelDevice(){
		$("#device_home").submit();
	}
</script>