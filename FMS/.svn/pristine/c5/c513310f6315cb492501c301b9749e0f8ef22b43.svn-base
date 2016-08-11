<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>

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
a.grid-tag-type-view.capitalize {
    padding: 0 0 0 15px;
    cursor: pointer;
}
</style>

<spring:eval var="getPOITypeList"
	expression="@propertyConfigurer.getProperty('fms.poitype.list')" />

<form action="poi_type_view" id="poi_type_view" method="post">
	<input class="form-control" type="hidden" id="poi_type_name"
		name="value" />
</form>

<form action="poi_type_create" id="poi_type_create" method="post">
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
		<h1>POI Management</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			 <li><a href="#">POI Type</a></li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
	<h4 class="pull-left">POI Type</h4>
		<div class="box-title fms-title">
			<div class="fms-btngroup text-right">
			 <c:if test="<%=hasPermissionAccess(\"poi_types\",\"create\")%>">
				<button type="button" class="btn btn-primary ripplelink"
					onclick="FnCreatePOIType()">Create</button>
					</c:if>
			</div>
		</div>
		
		<!--Grid starts -->
     <div class="box box-info">
        <div class="box-header hidden">
          <h4 class="box-title"></h4>

          <div class="box-tools pull-right hidden">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
          <div class="row">
            <div class="col-md-12"> <div id="poi-type-list"></div></div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>


	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->


<spring:url value="resources/js/poitype/list.js" var="poityprListJS" />
<script src="${poityprListJS}"></script>


<script type="text/javascript">
var getPOITypeList = "${getPOITypeList}";
</script>