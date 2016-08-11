<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="getAuditListUrl"
	expression="@propertyConfigurer.getProperty('fms.services.getAppAudit')" />

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
      <h1>Audit Log</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Audit Log</li>
      </ol>      
    </section>
    <!-- Main content -->
    <section class="content">
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
          <div class="row">
            <div class="col-md-12">
			<div id="audit-list"> </div>
			</div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>



    </section>
  </div>
  <!-- /.content-wrapper -->
  
 <spring:url value="resources/js/auditlog.js" var="auditlogJS"/><script src="${auditlogJS}"></script>

  

  <script>
	var getAuditListUrl = "${getAuditListUrl}";
	


//$('aside.control-sidebar').addClass('control-sidebar-open');

</script>
	
	