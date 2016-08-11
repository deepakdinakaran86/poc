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

aside.control-sidebar {
}

.rightbar-geofence{
display:none;
}

section.rightbar-geofence{display:none}

.route-circlea{
    background-image: url("resources/plugins/leaflet/marker/blue_marker.png");
    background-repeat: no-repeat;
    border-radius: 50%;
    padding: 5px;
    float: left;
    width: 26px;
    text-align: center;
    height:40px;
    }
.route-circleb{
    background-image: url("resources/plugins/leaflet/marker/red_marker.png");
    background-repeat: no-repeat;
    border-radius: 50%;
    padding: 5px;
    float: left;
    width: 26px;
    height:40px;
    text-align: center;
    }
    .route-line{
        background-color: #baa7a9;
    float: left;
    width: 140px;
    height: 5px;
    position: relative;
    top: 12px;
    }
    


.route-routechartheader {
    position: relative;
    width: 100%;
    margin-top: 13%;
    margin-left: 26%;
}
.route-note-distance{
    position: relative;
    left: 63px;
    top: 7px;
}
.route-note-time{
    clear: both;
    position: relative;
    left: 64px;
    top: -8px;
}
.leaflet-top leaflet-right{
display:none;
}
</style>
	<script id="rowTemplate" type="text/x-kendo-tmpl">		  
		<tr role="row" id="#: rowid#" class="geofence_grid">
		  <td role="gridcell">#: latitude#</td>
		  <td role="gridcell">#: longitude #</td>
		  <td role="gridcell">#: tolerance #</td>
		  <td role="gridcell">#: stoppageTime #</td>
		  <td role="gridcell">#: address #</td>
		  <td role="gridcell" class="formelement_addition"><a href="javascript:void(0);" class="remove_marker" title="Remove Marker"><i class="fa fa-minus-circle"></i></a></td>
		</tr>     
	</script>
	

  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header ">
      <h1>   Routing   </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/FMS/route_home"> Routing</a></li>
        <li class="active"> Create</li>
      </ol>      
    </section>
    <!-- Main content -->
    <section class="content">
		<div class="col-split-12">
			<section id="routecreationmap"  style="width:100%; height:660px"></section>
		</div>
      <!-- /.row -->

    </section>
  </div>
   <c:if test="${not empty routeResponse}">
	<script>
					 routeResponse= ${routeResponse};
					</script>
</c:if>
   <c:if test="${not empty routeView}">
	<script>
					 routeView= ${routeView};
					</script>
</c:if>

  <!-- /.content-wrapper -->
   <spring:url value="resources/js/route/route.js" var="routesJS"/><script src="${routesJS}"></script>
 <spring:url value="resources/js/route/map.js" var="mapJs"/><script src="${mapJs}"></script>
 <script>

	var routeResponse;
	var routeView;
	
	$(document).ready(function(){	
	//$('aside.control-sidebar').show().addClass('control-sidebar-open');
	$('body').addClass('sidebar-collapse control-sidebar-open');
});
	$('#right-bar-control').on('click',function(){		
		 $("body").toggleClass("control-sidebar-close control-sidebar-open");
		});
</script>