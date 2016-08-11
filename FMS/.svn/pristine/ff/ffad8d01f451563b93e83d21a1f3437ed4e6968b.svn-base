<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

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

.search-client {
    height: 35px;
    padding: 6px 12px;
    font-size: 14px;
    color: #555;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 85%;
    margin-bottom: 15px;
}

span.k-in > span.highlight {
    background: #7EA700;
    color: #ffffff;
    border: 1px solid green;
    padding: 1px;
}

span.k-in > span.highlight {
    background:orange
}

.box-body{
padding-top:10px;
}
</style>

<spring:eval var="getDevicesOfTenant"
	expression="@propertyConfigurer.getProperty('fms.device.children')" />
<spring:eval var="toAssignUnassign"
	expression="@propertyConfigurer.getProperty('fms.device.toassign.unassign')" />
<spring:eval var="toAssignSearch"
	expression="@propertyConfigurer.getProperty('fms.device.toassign.search')" />
<spring:eval var="assignBuldk"
	expression="@propertyConfigurer.getProperty('fms.device.assign.bulk')" />


<form id="device_home" action="device_home" method="get"></form>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Device Management
      </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/FMS/device_home">Devices</a></li>
        <li class="active"> Assign Device</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
		<div class="box-title fms-title">
			 <h4 class="pull-left">Assign Device</h4>
			 <div class="fms-btngroup text-right">
			 	<ul class="btnanimation">               
                        <li class=" form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
                                <button id="poi-def-cancel" onclick="FnCancelDevice()"><i>Cancel</i></button>
                        </li>
                         <c:if test="<%=hasPermissionAccess(\"devices\",\"assign device\")%>">
	                        <li class="primary form-action-save ripplelink" id="poidef-save">
	                                <i class="fa fa-check" aria-hidden="true"></i>
	                                <button onclick="FnSaveDevices()"><i>Save</i></button>
	                        </li> 
                        </c:if>       
                </ul>
		</div>
		</div>
	   <div class="row">
      <!-- Search client -->
	   <div class="col-md-3">
      <div class="box box-info" style="margin-bottom:0px;">
        <div class="box-header">
		<h3 class="box-title">Clients</h3>
          <!--  <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
          </div>-->
        </div>
        <!-- /.box-header -->
        <div class="box-body">
					<div class="">
						<div class="row">
							<div class="col-md-12">
								<input id="treeViewSearchInput" class="search-client" placeholder="Search Client" />
								<div class="treeview margintop10 "
									style="height: 456px; overflow: auto;">									
									<div id="gapp-clients-list"></div>
								</div>
							</div>
						</div>
						<!-- /.row -->
					</div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>
		
		</div> <!-- End Search client -->
		
      <div class="col-md-9">
          <div class="box box-info">
            <div class="box-header">
				<h3 class="box-title">Devices</h3>
			   <!-- <div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				</div>-->
            </div>
            <div class="box-body">
				<div class="row">
					<div class="col-md-12">
						<div id="gapp-devices-list"> </div>
					</div>
				</div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
      </div><!-- /.col 9 -->
	  </div>
      <!-- /.row -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->


<!--commonFunctions -->	
<spring:url value="resources/js/device/assign.js?v=2" var="deviceListJS"/><script src="${deviceListJS}"></script>


<script type="text/javascript">
$('body').addClass('sidebar-collapse');
var toAssignUnassign = "${toAssignUnassign}";
var toAssignSearch = "${toAssignSearch}";
var assignBuldk = "${assignBuldk}";
var getDevicesOfTenant = "${getDevicesOfTenant}";
var payload = ${payload};
var identifier = ${identifier};

function FnCancelDevice(){
	$("#device_home").submit();
}
</script>