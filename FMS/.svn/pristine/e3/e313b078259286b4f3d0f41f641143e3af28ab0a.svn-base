<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>


<style type="text/css">
#grid-view .k-grid-header .k-header {
	background-color: #f44336;
}

#grid-view .k-header a.k-link, .k-grid-header th {
	color: #000 !important;
}

.paddingRight15 {
	padding-right: 15px;
}
.k-grid-content {
    overflow-y: auto;
}



a.grid-asset-view.capitalize{
 	padding: 0 0 0 5px;
    cursor: pointer;
    text-transform:capitalize
}
.box-body {
    padding-top: 10px;
}
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!-- Content Wrapper. Contains page content -->
<script>
 var vehicle;
 var vehicleType;
</script>
<style>
aside.control-sidebar {
	
}

.smicon {
	/* use !important to prevent issues with browser extensions that change fonts */
	font-family: 'icomoon' !important;
	speak: none;
	font-style: normal;
	font-variant: normal;
	text-transform: none;
	line-height: 1;
	font-size: 20px;
	font-weight: 600;
	text-decoration: none;
	/* Better Font Rendering =========== */
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
}

.sm-homeicon:before, .page-breadcrumb .fa-home:before {
	content: "\e900";
}

.sm-dashboardicon:before {
	content: "\e902";
}

.sm-addicon:before {
	content: "\e902";
}

.sm-linesicon:before {
	content: "\e902";
}

.sm-nexticon:before {
	content: "\e903";
}

.sm-editicon:before {
	content: "\e917";
}

.sm-metericon:before {
	content: "\e915";
}

.sm-linkicon:before {
	content: "\e916";
}
</style>

<script id="rowTemplate" type="text/x-kendo-tmpl">		  
		<tr role="row" id="#: rowid#" class="geofence_grid">
		  <td role="gridcell">#: latitude#</td>
		  <td role="gridcell">#: longitude #</td>
		  <td role="gridcell" class="formelement_addition"><a href="javascript:void(0);" class="remove_marker" title="Remove Marker"><i class="fa fa-minus-circle"></i></a></td>
		</tr>     
	</script>

<form action="points" id="points" method="post">
	<input class="form-control" type="hidden" id="points_payload"
		name="value" />
</form>

	<c:if test="${not empty vehicle}">
		<script>
					 vehicle= ${vehicle};
					</script>
	</c:if>

	<c:if test="${not empty vehicleType}">
		<script>
					 vehicleType= ${vehicleType};
					</script>
	</c:if>

<div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>    Vehicle Management   </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Vehicles</a></li>
      </ol>
    </section>

<!-- Main content -->
	<section class="content">
	<div class="box-title fms-title">
			<h4 class="pull-left">Vehicles</h4>
			<div class="fms-btngroup text-right">
    	<button type="button" class="btn btn-default hidden" id="gapp-user-delete" onclick="FnDeleteUser()" disabled=""> Delete</button>
    	<c:if test="<%=hasPermissionAccess(\"vehicle_management\",\"create\")%>">
      				<button type="button" class="btn btn-primary ripplelink"	onclick="FnCreateAsset()" id="createVehicle">Create</button>
      	</c:if>		
      			
	  
	  </div>
	</div>
	<div class="box box-info">
	<div class="box-body">
			<div class="row">
									<div class="col-md-12 margin">
										<form class="form-inline ">
												<label for="fromdate"	style=" padding-right: 10px;">Vehicle Type</label> 
													<select class="form-control" name="assetCategory"
													id="assetCategory" onchange="FnGetAssetList(this.value)">
													<option value="">Select Asset Type</option>
												</select>
										</form>
										<form id="gapp-asset-create" role="form" action="vehicle_home"
											name="gapp-asset-create" method="get"></form>
										<form id="gapp-asset-edit" role="form" action="vehicle_home"
											name="gapp-asset-create" method="post">
											<input type="hidden" id="vehicle_id" name="vehicle_id" /> <input
												type="hidden" id="vehicle_mode" name="vehicle_mode"
												value="edit" />
										</form>
									</div>
								
	

	
		 
		 <div class="col-md-12">
		   	<div id="assetListSection">
									<div class="k-ltl">
										<div id="gapp-asset-list"></div>								
										</div>
									</div>
		  </div><!-- /.box-body -->
</div>
		</div>
	</div>
	</section>
</div>  <!-- /.content-wrapper -->
<form id="gapp-asset-view" role="form" action="/equipments/asset/view"
	name="gapp-asset-view" method="post">
	<input type="hidden" id="entity_id" name="entity_id" />
	<%-- 										<input type="hidden" id="gapp-tenant-key" name="gapp-tenant-key" value="<%=VarTenantKey%>" /> --%>
</form>

<form id="gapp-asset-dashboard" role="form"
	action="/equipments/asset/dashboard" name="gapp-asset-dashboard"
	method="post">
	<input type="hidden" id="dashboard_equip_id" name="dashboard_equip_id" />
	<%-- 										<input type="hidden" id="gapp-tenant-key" name="gapp-tenant-key" value="<%=VarTenantKey%>" /> --%>

</form>


<form id="gapp-asset-pointmapping" role="form"
	action="/pointsmapping/asset" name="gapp-asset-pointmapping"
	method="post">
	<input type="hidden" id="equip_entity" name="equip_entity" /> <input
		type="hidden" id="equip_view" name="equip_view"
		value="/equipments/asset/view" /> <input type="hidden"
		id="equip_list" name="equip_list" value="/equipments/asset/list" />
	<%-- 										<input type="hidden" id="gapp-tenant-key" name="gapp-tenant-key" value="<%=VarTenantKey%>" /> --%>
</form>

<%-- 	<% include(utils.resolvePath('templates/global-header.jag')); %> --%>
<!-- END CONTAINER -->
<%-- 	<% include(utils.resolvePath('templates/global-footer.jag')); %> --%>
<%-- 	<%  --%>
<!-- // 		include(utils.resolvePath('templates/portal-footer.jag')); -->
<!-- // 		include('/controllers/includes/theme-portal-scripts.jag'); -->
<%-- 	%> --%>
<!-- 	<script type="text/javascript"> -->
<%-- 		var VarListAssetsUrl = '<%=APICONF.GAPP_SERVICES.equipments.asset.list%>'; --%>
<%-- 		var VarListTemplateUrl = '<%=APICONF.GAPP_SERVICES.equipments.template.list%>'; --%>
<%-- 		var VarLUDomainName = '<%=usr.domainName()%>'; --%>
<%-- 		var VarCurrentTenantInfo = $.parseJSON('<%=VarCurrentTenantInfo%>'); --%>
<%-- 		var ObjPageAccess = $.parseJSON('<%=ObjPageAccess%>'); --%>
<!-- 	</script> -->
<spring:url value="resources/js/equipment/asset/list.js" var="assetListJS" />
<script src="${assetListJS}"></script>




<%--  <spring:url value="resources/js/assets/monitoring.js" var="geoFenceJS"/><script src="${geoFenceJS}"></script> --%>

<script>
$('aside.control-sidebar').show().addClass('control-sidebar-open');
$('body').addClass('sidebar-collapse');

//$('aside.control-sidebar').addClass('control-sidebar-open');

</script>