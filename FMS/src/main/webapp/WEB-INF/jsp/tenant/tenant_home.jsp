<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<spring:eval var="listTenantsUrl"
	expression="@propertyConfigurer.getProperty('fms.services.listTenant')" />
	
<style>
	a.grid-viewtenanthome {
	    cursor: pointer !important;
	}
</style>

<form action="sub_tenant_view" id="sub_tenant_view" method="post">
	<input class="form-control" type="hidden" id="subTenant" name="value" />
</form>

<form action="tenant_view" id="tenant_view" method="post">
	<input class="form-control" type="hidden" id="tenant_name" name="value" />
</form>

<form action="tenant_send_email_view" id="tenant_send_email_view"
	method="post">
	<input class="form-control" type="hidden" id="tenant_send_email"
		name="value" />
</form>

<form action="tenant_create" id="tenant_create" method="get"></form>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Client Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>			
			<li class="active">Client</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left">Client</h4>
			<div class="fms-btngroup text-right">
			<c:if test="<%=hasPermissionAccess(\"client_management\",\"create admin\")%>">
				<button type="button" class="btn btn-primary ripplelink" id="createadmin-btn"
					onclick="FnCreateClientAdmin()" disabled="">Create Admin</button>
			</c:if>
			<c:if test="<%=hasPermissionAccess(\"client_management\",\"create\")%>">
				<button type="button" class="btn btn-primary ripplelink"
					onclick="FnCreateClient()">Create Client</button>
			</c:if>
			</div>



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
			<!-- /.box-header -->
			<div class="box-body" style="height: 480px !important;">
				<div class="row">
					<div class="col-md-12">
						<div id="clients-list"></div>
					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /.box-body -->
		</div>
		<!-- Modal -->
		<div class="modal fade" id="createAdminNotification" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Create admin</h4>
					</div>
					<div class="modal-body">Please select a row from the grid to
						create admin user</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default btn-primary"
							data-dismiss="modal">OK</button>
					</div>
				</div>
			</div>
		</div>

		<!----  client confirmation model popup  --->
		<div id="clientConfirmation" class="modal fade" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog modal-xs">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 class="modal-title">Confirm</h4>
					</div>
					<div class="modal-body">
						<div class="scroller" data-always-visible="1"
							data-rail-visible1="1">
							<div class="row">
								<div class="col-md-12">
									<p>Already you are in Client session.Do you want to
										continue?</p>
								</div>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn  btn-raised"
							onclick="FnClientConfirmNo()">No</button>
						<button type="button" id="submitBackBtn"
							class="btn btn-raised btn-danger" value="yes"
							onclick="FnClientConfirmYes()">Yes</button>
						<div class="modal-footer"></div>
					</div>
				</div>
			</div>
		</div>
		<!----  client confirmation model popup  --->

	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<script type="text/javascript">
var listTenantsUrl = "${listTenantsUrl}";
var currentDomain = "${currentDomain}";
</script>

<spring:url value="resources/js/clients/list.js" var="clientHomeJS" />
<script src="${clientHomeJS}"></script>