<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>


<spring:eval var="getAllDevices"
	expression="@propertyConfigurer.getProperty('mc.services.listFilterDevice')" />
<spring:eval var="getDevicesOfTenant"
	expression="@propertyConfigurer.getProperty('mc.hierarchy.get.children')" />


<style>
.Normal {
	background-color: #28d8b4;
	color: #fff;
}

.Alarm {
	background-color: #fe6c6c;
	color: #fff;
}

.FALSE td,.TRUE td {
	box-shadow: 0px 0px 5px -2px #000 inset
}

.hideRowAlarm {
	display: none
}
</style>

<body>

	<div class="content-wrapper">
		<section class="content-header">
			<h1>Alert Management</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
				<li class="activs">Alert Console</li>
			</ol>
		</section>
		<!-- Main content -->
		<section class="content">
			<div class="box-title fms-title">
				<h4 class="pull-left">Alert Console</h4>
				<div class="fms-btngroup text-right hidden">
					<button type="button" class="btn bg-olive">Assign Device</button>
					<button type="button" class="btn bg-primary">Create Device</button>
				</div>
			</div>
			<div class="row" style="clear: both">
				<div class="col-md-12">
					<!-- Chart - Alarm Count -->
				      <div class="box box-info">
					       
					        <!-- /.box-header -->
					        <div class="box-body">
									<div class="chart-wrapper">
										<div id="alarmconsolechartsection">
											<div id="gapp-alarmconsole-chart" style="margin-bottom: 20px; height: 260px"></div>
										</div>
									</div>
					          <!-- /.row -->
					        </div>
					        <!-- /.box-body -->
						</div>
						
								<!-- Chart - Alarm Count -->
				      <div class="box  box-info">
					        <div class="box-header with-border hidden">
					          <h4 class="box-title"></h4>
					          <div class="box-tools pull-right ">
					            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					          </div>
					        </div>
					        <!-- /.box-header -->
					        <div class="box-body">
									<div class="chart-wrapper">
										<div id="alarmconsolelistsection">
											<div id="gapp-alarmconsole-list"></div>
										</div>
									</div>
					          <!-- /.row -->
					        </div>
					        <!-- /.box-body -->
						</div>
				
				
				
				
				</div><!-- /.col-md-12  -->
			</div><!-- /.row -->
			
		
		</section>
		
	
	</div>


	<script id="rowTemplate" type="text/x-kendo-tmpl">		  
		<tr class="#:status#" role="row" id="#: rowid#">
		  <td role="gridcell">#: alarmName#</td>
		  <td role="gridcell">#: alarmMsg #</td>
		  <td role="gridcell">#: assetInfo #</td>
		  <td role="gridcell">#:  toDateFormat(time, 'F') #</td>
		  <td role="gridcell">#:  toDateFormat(starttime, 'F') #</td>
		  <td role="gridcell">#: value #</td>
		</tr>     
	</script>

	<!-- Scripts for Websocket Connection-->
	<script src="resources/plugins/websocket/websocket.js"></script>
	<script src="resources/plugins/websocket/mqttws31-min.js"></script>
	<script src="resources/plugins/websocket/WebORB.js"></script>

	<spring:url value="resources/js/alarm/alertconsole.js"
		var="alertconsoleJS" />
	<script src="${alertconsoleJS}"></script>

	<script>
	var payload = ${payload};
</script>

</body>