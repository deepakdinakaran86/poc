<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<style>
.skin-blue .main-header .navbar {
	background: radial-gradient(#197aa5 15%, #005180 60%);
}

.skin-blue .main-header .logo {
	background-color: #158dc1;
}

span.k-picker-wrap.k-state-default {
    border: 1px solid #c4c8d0 !important;
}

button.btn.btn-primary {
    top: -10px;
    position: relative;
}

div#alarmhistorylistsection {
    padding: 16px;
}
</style>


  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Alert Management</h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="activs">Alert Log</li>
      </ol>
    </section>

        <!-- Main content -->
    <section class="content">
	
					
		      <div class="box-title fms-title">
      			 <h4 class="pull-left"> <i class="pageheadericon pageheader-usericon"></i>Alert Log</h4>
      			
      		</div>
      <!-- SELECT2 EXAMPLE -->
	  
      <div class="box box-info" style="clear:both">
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
			  <form class="form-horizontal" style="padding:10px 0px">
				<div class="col-md-3">
					  <div class="form-group" style="margin-left:0px">
					  <label for="fromdate">From Date &nbsp&nbsp</label>
						<input type="date" class="form-control" name="fromdate" id="fromdate" placeholder="Select" />
					  </div>
				 </div>
				 <div class="col-md-3">
					  <div class="form-group" style="margin-left:0px">
					  <label for="todate">To Date &nbsp&nbsp</label>
						<input type="date" class="form-control" name="todate" id="todate" placeholder="Select" />
					  </div>
				 </div>
			  </form>
				  <div class="col-md-3">
						<button class="btn btn-primary" onclick="FnSearchAlarmHistory()">Search</button>
				 </div>
			</div>
          </div>
          
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
        
              <div class="row">
				<div class="col-md-12">
				<div id="alarmhistorylistsection">
					<div id="gapp-alarmhistory-list"></div>
				</div>
				</div>
			</div>
        
		</div>
	
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<spring:url value="resources/js/alarm/alertlogs.js" var="alertlogsJS" />
<script src="${alertlogsJS}"></script>

<script>
	var payload = ${payload};
</script>
