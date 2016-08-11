<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Configuration Templates</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="#">Configuration Templates</a></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left">Device</h4>
			<div class="fms-btngroup text-right">
				<button type="button" class="btn bg-olive">Create Template</button><!-- onclick="FnNavigateClientPage()" -->
			</div>
		</div>
		<div class="row">
			<!-- Search client -->
			<div class="col-md-12">
				<div class="box box-info">
					<!-- /.box-header -->
						<div class="box-body ">
							<div class="row">
								<div class="col-md-12">
									<div class="treeview margintop10" style="height:480px;overflow: auto;">
										<div id="gapp-conftemplate-list"></div>
	
									</div>
								</div>
							</div>
							<!-- /.row -->
						</div>
					</div>
					<!-- /.box-body -->
				</div>

			</div>
			<!-- End Search client -->

	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->


<spring:url value="resources/js/configurationTemplates/list.js" var="configurationtemplateJS" />
<script src="${configurationtemplateJS}"></script>
