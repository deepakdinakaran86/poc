<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>
<style type="text/css" >
a.grid-viewgeofence {
    padding: 0 0 0 5px;
    cursor: pointer;
    text-transform: capitalize;
}

.window-footer {
    padding: 12px;
    position: relative;
    text-align: center;
    margin: 0;
    top: 22%;
}
</style>

<script type="text/javascript">
var geoListresponse=${geoListresponse};
</script>
 <!-- Content Wrapper. Contains page content -->	
	
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1> Geofence Management   </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>       
        <li class="active"> Geofence</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="box-title fms-title">
      			 <h4 class="pull-left"> GeoFence</h4>
      			 <div class="fms-btngroup text-right">
      			 <c:if test="<%=hasPermissionAccess(\"geofence\",\"delete\")%>">
      			  	<button type="button" class="btn  form-action-delete ripplelink"  id="gapp-geofence-delete" onclick="FnDeleteGeofence()" disabled=""> Delete</button>
      			  </c:if>
      			  	<c:if test="<%=hasPermissionAccess(\"geofence\",\"create\")%>">
      				  <button type="button" class="btn btn-primary ripplelink "  onclick="FnCreateGeofence()" id="createGeofence"> Create</button>
      				 </c:if>
      		  </div>
      		  </div>
				<div class="box box-info">
				  <div class="box-header with-border hidden">
				          <h4 class="box-title"></h4>
				
				          <div class="box-tools pull-right hidden">
				            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
				          </div>
				        </div>
				        <!-- /.box-header -->
				        
				          <div class="box-body">
					          <div class="row">
					            <div class="col-md-12">  <div id="gapp-geofence-list"></div></div>
					          </div>
					          <!-- /.row -->
					        </div>
												
									<form id="gapp-geofence-create" role="form" action="geofence_home" name="gapp-geofence-create" method="get">
<%-- 										<input type="hidden" id="gapp-tenant-key" name="gapp-tenant-key" value="<%=VarTenantKey%>" /> --%>
									</form>
									<form id="geofence-delete" role="form" action="geofence_list" name="gapp-geofence-delete" method="post">
										<input type="hidden" id="geofence_delete_id" name="geofence_delete_id" />
									</form>
									<form id="gapp-geofence-view" role="form" action="geofence_home" name="gapp-geofence-view" method="post">
										<input type="hidden" id="geofence_id" name="geofence_id" />
										<input type="hidden" id="geofence_mode" name="geofence_mode" value="edit" />
<%-- 										<input type="hidden" id="gapp-tenant-key" name="gapp-tenant-key" value="<%=VarTenantKey%>" /> --%>
									</form>
								</div>
							
		    </section>		
		<script>domainName ='<c:out value='${domainName}'/>'</script>

      		</div>
      		
      		
      		
      		
 
  
 <spring:url value="resources/js/geofence/list.js" var="geoFenceJS"/><script src="${geoFenceJS}"></script>
 
 	<script id="delete-confirmation" type="text/x-kendo-template">
		<p class="delete-message">Are you sure want to delete the geofence?</p>
		<div class="window-footer">
		<button class="delete-confirm k-button" style="width: 80px;">Yes</button>
		<button class="delete-cancel k-button" style="width: 80px;">No</a>
		</div>
	</script>
 