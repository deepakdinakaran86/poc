<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!-- Content Wrapper. Contains page content -->
<style>

#geo-panel-menu {
    position: absolute;
    right: 99%;
    top: 18%;
    background: #222d32;
    border-radius: 6px;
    width: 40px;
    height: 40px;
    padding: 10px;
}
.geofence-actions-pane{padding:10px !important}

section.rightbar-routing{display:none}

</style>
<script>
var VarViewResponse;
var geoFenceFields;
</script>

	<script id="rowTemplate" type="text/x-kendo-tmpl">		  
		<tr role="row" id="#: rowid#" class="geofence_grid">
		  <td role="gridcell">#: latitude#</td>
		  <td role="gridcell">#: longitude #</td>
		  <td role="gridcell" class="formelement_addition"><a href="javascript:void(0);" class="remove_marker" title="Remove Marker"><i class="fa fa-minus-circle"></i></a></td>
		</tr>     
	</script>


  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header ">
      <h1>   Geofence   </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
         <li><a href="/FMS/geofence_list">Geofence</a></li>
        <li class="active">  Create</li>
      </ol>      
    </section>
    <!-- Main content -->
    <section class="content">
		<div class="col-split-12">
			<section id="geofencegmap"  style="width:100%; height:660px"></section>
			<script>VarGeofenceId ='<c:out value='${geoFenceId}'/>'</script>
			<script>VarEditGeofenceId ='<c:out value='${geoFenceEdit}'/>'</script>
			<c:if test="${not empty viewResponse}">
					<script>
					 VarViewResponse = ${viewResponse};
					 geoFenceFields = ${geofenceFields};
					</script>
			</c:if>	
		</div>
      <!-- /.row -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  

<%--  <spring:url value="resources/js/assets/monitoring.js" var="geoFenceJS"/><script src="${geoFenceJS}"></script> --%>
  <spring:url value="resources/js/geofence/add.js" var="geoFenceAddJS"/><script src="${geoFenceAddJS}"></script>

  <script>

	$(document).ready(function(){	
	//$('aside.control-sidebar').show().addClass('control-sidebar-open');
	$('body').addClass('sidebar-collapse control-sidebar-open');
});

$('#right-bar-control').on('click',function(){		
		 $("body").toggleClass("control-sidebar-close control-sidebar-open");
		});

</script>
	
	