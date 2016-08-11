<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<style>
.skin-blue .main-header .navbar {
	background: radial-gradient(#197aa5 15%, #005180 60%);
}

.skin-blue .main-header .logo {
	background-color: #158dc1;
}


</style>


<div class="content-wrapper">
	<section class="content-header">
		<h1>Alert Management</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li>Alert Definition</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box-title fms-title">
			<h4 class="pull-left">Alert Definition</h4>
			<div class="fms-btngroup text-right hidden">
				<button type="button" class="btn bg-olive">Assign Device</button>
				<button type="button" class="btn bg-primary">Create Device</button>
			</div>
		</div>
		<div class="row" style="clear: both">
			<!-- Search client -->
			<div class="col-md-3">
				<div class="box box-info">
					<div class="box-header with-border">
						<h3 class="box-title">Assets</h3>
						<div class="box-tools pull-right">
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="row">
							<div class="col-md-12">
								<input id="treeViewSearchInput" class="search-client col-md-12" placeholder="Search Asset"></input>
								<div id="gapp-assets-list" style='clear:both; padding: 15px 10px;'></div>
							</div>
						</div>
						<!-- /.row -->
					</div>
					<!-- /.box-body -->
				</div>

			</div>
			<!-- End Search client -->

			<div class="col-md-9" style="padding-left:0px">
				<div class="box box-info">
					<div class="box-header">
						<h3 class="box-title">Alarms</h3>
						<div class="box-tools pull-right">
						</div>
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
		<!-- /.row -->

	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<span id="notificationMsg"></span>

<spring:url value="resources/js/alarm/alertdefinition.js" var="alertdefinitionJS" />
<script src="${alertdefinitionJS}"></script>

<script>
	var assetListresponse = ${assetListresponse};
	var payload = ${payload};
</script>
