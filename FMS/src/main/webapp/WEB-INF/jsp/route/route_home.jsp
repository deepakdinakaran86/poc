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
 display:none 
}
</style>


  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header ">
      <h1>   Routing   </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">  Routing</li>
      </ol>      
    </section>
<form action="route_map" id="route_map" method="get" hidden="true">
</form>
    <!-- Main content -->
    <section class="content">
		      <div class="box-title fms-title">
      			 <h4 class="pull-left"> <i class="pageheadericon pageheader-usericon"></i>Routing</h4>
      			 <div class="fms-btngroup text-right">
      				  <button type="button" class="btn btn-primary" onClick="FnNavigateCreateRoute()"  name="gapp-user-save" id="gapp-user-save" tabindex="7">Create</button>
      		  </div>
      		</div>
      <!-- SELECT2 EXAMPLE -->
      <div class="box box-info">
        <div class="box-header with-border hidden">
          <h4 class="box-title"></h4>

          <div class="box-tools pull-right ">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
     	<form id="fms-route-view" 
	action="route_view" name="fms-route-view"
	method="post">
          <div class="row">
            <div class="col-md-12">
			<div id="route-list"> </div>
			</div>
          </div>
          <!-- /.row -->
          <input type="hidden" id="route_name" name="route_name" />
         </form>
        </div>
        <!-- /.box-body -->
		</div>

	




    </section>
  </div>
  <!-- /.content-wrapper -->
  
 <spring:url value="resources/js/route/list.js" var="routelistJS"/><script src="${routelistJS}"></script>
 
<script>
var routelist = ${routelist};

</script>
	
	