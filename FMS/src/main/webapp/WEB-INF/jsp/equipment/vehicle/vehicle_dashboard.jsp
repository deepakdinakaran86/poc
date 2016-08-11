<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<style type="text/css">
#grid-view .k-grid-header .k-header {
	background-color: #f44336;
}

#grid-view .k-header a.k-link,.k-grid-header th {
	color: #000 !important;
}

.paddingRight15 {
	padding-right: 15px;
}

#submenu-selection {
	position: absolute;
	top: 14px;
	right: 15%;
	display: inline-block;
}

.list-group-item {
	position: relative;
	display: block;
	padding: 3px 15px;
}

.uberAccordion>li>div {
	background-color: #cee7f2;
	padding: 0px 0 6px 16px !important;
	border: 1px solid #197aa5;
}

.uberAccordion.accordion-vertical {
	height: 620px;
}

.title {
	background: #197aa5 !important;
	border-bottom: 1px solid #fff !important;
	color: #fff
}


/*  bhoechie tab */
div.bhoechie-tab-container{
  z-index: 10;
  opacity: 0.97;
  filter: alpha(opacity=97);
  apdding-left:0px;
}

.bhoechie-tab{
    -webkit-box-shadow: 1px 3px 5px rgba(202, 202, 202, 0.7); /*0 6px 12px rgba(0,0,0,.175);*/
    box-shadow: 1px 3px 5px rgba(202, 202, 202, 0.7);
    -moz-box-shadow: 1px 3px 5px rgba(202, 202, 202, 0.7);
    background-color: #efefef;
    background-color: #fff;
    border-radius: 4px;
    -moz-border-radius: 4px;
    border: 1px solid #d6d2d2;
        border-top: 0px solid #d4d4d4;
}



div.bhoechie-tab-menu{
  padding-right: 0;
  padding-left: 0;
  padding-bottom: 0;
}
div.bhoechie-tab-menu div.list-group{
  margin-bottom: 0;
}
div.bhoechie-tab-menu div.list-group>a{
  margin-bottom: 0;
}
div.bhoechie-tab-menu div.list-group>a .glyphicon,
div.bhoechie-tab-menu div.list-group>a .fa {
  color: #5A55A3;
}

      .overlay {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        opacity: .2;
        filter: alpha(opacity=60);
        background-color: #6495ed;      
        text-align: center;
      }

      .overlay div {
        position: relative;
	    font-size: 19px;
	    margin-top: -46px;
	    top: 50%;
      }
      
/*div.bhoechie-tab-menu div.list-group>a:first-child{
  border-top-right-radius: 0;
  -moz-border-top-right-radius: 0;
}
div.bhoechie-tab-menu div.list-group>a:last-child{
  border-bottom-right-radius: 0;
  -moz-border-bottom-right-radius: 0;
}*/
div.bhoechie-tab-menu div.list-group>a.active,
div.bhoechie-tab-menu div.list-group>a.active .glyphicon,
div.bhoechie-tab-menu div.list-group>a.active .fa{
  background-color: #5A55A3;
  background-image: #5A55A3;
  color: #ffffff;
  border-left: 3px solid #505051;
  
}
div.bhoechie-tab-menu div.list-group>a.active:after{
  content: '';
  position: absolute;
  right: 100%;
  top: 50%;
  margin-top: -13px;
  border-right: 0;
  border-bottom: 13px solid transparent;
  border-top: 13px solid transparent;
  border-right: 10px solid #4b4b4c
  
}

div.bhoechie-tab-content{
 /* background-color: #ffffff;
   border: 1px solid #eeeeee; */
  padding-top: 10px;
}

div.bhoechie-tab-content.active{
     -webkit-animation: fadein 2s; /* Safari, Chrome and Opera > 12.1 */
       -moz-animation: fadein 2s; /* Firefox < 16 */
        -ms-animation: fadein 2s; /* Internet Explorer */
         -o-animation: fadein 2s; /* Opera < 12.1 */
            animation: fadein 2s;
}

div.bhoechie-tab div.bhoechie-tab-content:not(.active){
  display: none;
}

@keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}

.bhoechie-tab-menu .list-group-item{
/*position: absolute;
    top: 0px;
    bottom: 0px;
        overflow: hidden;
        */
   width: 2em;
    white-space: nowrap;
    display: block;
    height: 130px;
    box-shadow: 3px 0px 9px -1px rgba(0,0,0,0.4) inset;
    margin-bottom: 6px!important;
    border-radius: 0px 4px 4px 0px;
    border: 0px;
    position: relative;
    left: -1px;
}

.bhoechie-tab-menu .list-group-item span{
       display: block;
    transform: rotate(90deg) translate(-27%, 37%);
    height: 2em;
    margin: auto;
    line-height: 2em;
    /* padding: 1em 1em 1em 0; */
    /* width: 2em; */
    white-space: nowrap;
    /* overflow: hidden; */
    padding: 0px 0.7em;
    vertical-align: middle;
    text-align: center;
    color:#fff;
    
    }
    
    .bg-clr1,.bg-clr1:hover{
    background-color: #00c0ef!important;
   border-color: #0682a0;
    }
    .bg-clr2{
    background-color: #f39c12!important;
        border-color: #bb770b;
    }
    .bg-clr3{
    background-color: #00a65a!important;
    border-color: #067b45;
    }
    .bg-clr4{
       background-color: #3c8dbc!important;
       border-color: #296a90;
    }
    .bg-clr5{
    background-color: #dd4b39!important;
        border-color: #9a2e20;
    }
    
    
     .border-clr1{border-bottom: 2px solid #08c2ef;}
      .border-clr2{border-bottom: 2px solid #f39c12;}
       .border-clr3{border-bottom: 2px solid #00a65a;}
        .border-clr4{border-bottom: 2px solid #3c8dbc;}
     .border-clr5{border-bottom: 2px solid #dd4b39;}

.pin {
    width: 45px;
    height: 45px;
    border-radius: 50% 50% 50% 3%;
    background: #14a5ae;
    /* background: linear-gradient(#14a5ae, #189097); */
    position: relative;
    transform: rotate(-43deg);
		margin:0;
		animation-name: bounce;
		animation-fill-mode: both;
		animation-duration: 1s;
float:left;
		
}
.pin:after {
    content: '';
    width: 33px;
    height: 33px;
    margin: 6px 0 0 6px;
    background: #fff;
    position: absolute;
    border-radius: 50%;
    overflow: hidden;
}
.pin img{
position: absolute;
    z-index: 3;
    transform: rotate(43deg);
       left: 11px;
    top: 11px;
}
.pin:before {
       background: rgba(0,0,0,0.4);
    border-radius: 50%;
    height: 24px;
    width: 7px;
    position: absolute;
    margin: 11px 0px 0px -12px;
    transform: rotateZ(136deg);
    /* z-index: -2; */
    top: 22px;
    left: 9px;
    content: "";
    filter: blur(16px);
    -webkit-filter: blur(0.1em);
}

.tab-content-heading{
    padding-bottom: 10px;
    margin: 5px 0px 13px;
    margin-top: 0px;
    padding-left: 15px;
    background-color: #efefef;
    padding-top: 10px;
    position: relative;
    top: -10px;
    }
    
    .dashboardtextleft-length,.dashboardtext-length {
    padding: 4px 0px;
    display: inline-block;
}

#equipmentInfo{
padding: 20px 0px 0px 0px
}

#equipmentInfo li{
        padding-left: 10px;
    clear: both;
    height: 31px;
}
#equipmentInfo li:nth-child(odd){
background-color: #efefef;
    }
   .genset-properties-name{width:37%;float:left;font-size: 12px;}
   .genset-properties-value{width:63%;float:left;font-size: 12px;}
    
    html,body{
    height:100%;
    width:100%;}
</style>


<!-- Content Wrapper. Contains page content -->

<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Vehicle</h1>
		<ol class="breadcrumb">
			<li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Vehicle Dashboard</li>
		</ol>
	</section>

	<section>
		<!-- Small boxes (Stat box) -->
		
		<!-- New Tab design -->
		
			<div class="row" style="padding:0px 10px;margin-top: 10px;">
			
						<!-- Left Tree begins-->
			<div class="col-md-2">
				<div class="box box-info" style="height: 695px">
					<div class="box-header">
						<h3 class="box-title">Vehicle List</h3>
						<div class="box-tools pull-right hidden">
							<button type="button" class="btn btn-box-tool"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="row">
							<div class="col-md-12">
								<input type="text" class="form-control" id="treeViewSearchInput"
									placeholder="Search Vehicle">
								<div class="treeview margintop10">
									<div id="assetListSection">
										<div id="gapp-assets-list" ></div>
<!-- 										<form id="gapp-assets-list-form" role="form" -->
<!-- 											action="/equipments/vehicle/dashboard" -->
<!-- 											name="gapp-assets-list-form" method="post"> -->
<!-- 											<input type="hidden" id="vehicle_id" -->
<!-- 												name="vehicle_id" /> -->
<%-- 																					<input type="hidden" id="gapp-tenant-key" name="gapp-tenant-key" value="<%=VarTenantKey%>" /> --%>

<!-- 										</form> -->
									</div>
								</div>
							</div>
						</div>
						<!-- /.row -->
					</div>
					<!-- /.box-body -->
				</div>



			</div>
			<!-- End Search client -->
			<!-- Left Tree Ends-->
			
        <div class="col-lg-10 col-md-10 col-sm-10 col-xs-9 bhoechie-tab-container" style="padding-left:0;margin-bottom: 28px;">
            <div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 bhoechie-tab" style="width:97%;height: 698px;overflow:hidden">
                <!-- flight section -->
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"vehicle details\")%>">
                <div class="bhoechie-tab-content vehicleDetailsAct">
                    <!-- Vehicle Details -->
                    		<div>
                    			<div class="row"><h4 class="tab-content-heading border-clr1"  style="background: #c1f1fd;">Vehicle Details</h4></div>

								<div class="col-md-3" style="padding:0;">
								
									<div class="box box-primary box-solid" >
										<div class="box-header with-border">
											<h5 style="margin:0px">Vehicle Info & Location</h5>
												<div class="box-tools pull-right">
													<!--<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>-->
												</div>
											</div>
											<div class="box-body box-profile">												
											<img id="vehicle-image" class="profile-user-img img-responsive img-circle" src=""/> 

												<!-- 												<h3 class="profile-username text-center">Car - TNX4578 -->
												<!-- 												</h3> -->
												<div class="genset-name">
													<span id="genset-name-label"><strong>Asset </strong></span> &nbsp: &nbsp <span
														id="genset-name"></span>
												</div>

												<div class="genset-description"></div>
												<div class="genset-status">
													<span class="btn btn-circle gray btn-sm red"
														id="vehicle-inactive" style="cursor: default;">InActive</span>
													<span class="btn btn-circle green btn-sm"
														id="vehicle-active" style="cursor: default;">Active</span>
												</div>
												<div class="genset-info row" id="genset-info">
													<ul class="nav" id="equipmentInfo"></ul>
												</div>
												
											</div>
										<!--.box-header-->
									</div>
									<!--.box-->


									<div class="box box-info" style="border: 1px solid #3c8dbc;">
										<div id="vehicleGeoMap" style="height: 281px; width: 100%"></div>
									</div>
									<!--.box-->
									<!--Slider-->

								</div>


								<div class="col-md-9">
									<div class="box   box-info">
										<div class="box-header with-border">
											<h5 style="margin:0px"><strong>Parameters Detail</strong></h5>
											<div class="box-tools pull-right hidden">
												<button type="button" class="btn btn-box-tool"
													data-widget="collapse">
													<i class="fa fa-minus"></i>
												</button>
											</div>

										</div>
										<div class="box-body">

											<!-- Custom Tabs -->
											<div class="">
												<!-- <ul class="nav nav-tabs">
													<li class="active"><a href="#tab_1" data-toggle="tab"
														aria-expanded="true">Engine</a></li>
													<li class=""><a href="#tab_2" data-toggle="tab"
														aria-expanded="false">Recirculation</a></li>
													<li class=""><a href="#tab_3" data-toggle="tab"
														aria-expanded="false">Transmission</a></li>
													<li class=""><a href="#tab_4" data-toggle="tab"
														aria-expanded="false">Body</a></li>
													<li class="dropdown hidden"><a class="dropdown-toggle"
														data-toggle="dropdown" href="#" aria-expanded="false">
															Dropdown <span class="caret"></span>
													</a>
														<ul class="dropdown-menu">
															<li role="presentation"><a role="menuitem"
																tabindex="-1" href="#">Action</a></li>
															<li role="presentation"><a role="menuitem"
																tabindex="-1" href="#">Another action</a></li>
															<li role="presentation"><a role="menuitem"
																tabindex="-1" href="#">Something else here</a></li>
															<li role="presentation" class="divider"></li>
															<li role="presentation"><a role="menuitem"
																tabindex="-1" href="#">Separated link</a></li>
														</ul></li>
													<li class="pull-right"><a href="#" class="text-muted"><i
															class="fa fa-gear"></i></a></li>
												</ul> -->
												<div>
														<div id="vehicle-parameter-grid"></div>
												</div>
												<!-- /.tab-content -->
											</div>
											<!-- nav-tabs-custom -->
										</div>
									</div>
									<!-- .box-->

								</div>
								<!-- Asset details ends-->



							</div>
                    <!-- End: vehicle details -->
                </div>
                </c:if>
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"alert\")%>">
                <!-- train section -->
                <div class="bhoechie-tab-content vehicleAlertAct">
                   <!-- Alerts -->
                   							<div id="alert-detais">
                   							<div class="row"><h4 class="tab-content-heading border-clr2" style="background: #ffe9c7;">Alert</h4></div>
								<!-- <div class="col-md-3">
									<div class="box box-info" style="width: 340px">
										<div class="box-header">
											<div class="box-tools pull-right"></div>
										</div>
										<div class="box-body">
											<div id="alert-chart"></div>
										</div>
									</div>

								</div>-->
								<div class="">
									<div class="box box-default" style="background: #eff9fb;">
										<div class="box-body" style="height: 120px; text-align: center;">
											<div >
												<a class="btn btn-boxapp  progress-bar-striped">
													<figure class="alertimg"></figure> <!--<h5>Idle Alert</h5>-->
													<div class="count text-blue">12</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="speedimg"></figure> <!--<h5> Idle Speeding</h5>-->
													<div class="count text-blue">15</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="maintenanceimg"></figure> <!--<h5>Maintenance</h5>-->
													<div class="count text-blue">18</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="alertimg"></figure> <!--<h5>Off Hours</h5>-->
													<div class="count text-blue">22</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="geofenceimg"></figure> <!--<h5>Geofence</h5>-->
													<div class="count text-blue">19</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="seatbeltimg"></figure> <!--<h5>Seat Belt</h5>-->
													<div class="count text-blue">14</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="accimg"></figure> <!--<h5>Harsh Acceleration</h5>-->
													<div class="count text-blue">28</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="breakimg"></figure> <!--<h5>Harsh Breaking</h5>-->
													<div class="count text-blue">25</div>
												</a> <a class="btn btn-boxapp progress-bar-striped">
													<figure class="fuel"></figure> <!--<h5>Harsh Breaking</h5>-->
													<div class="count text-blue">14</div>
												</a>
											</div>
										</div>
										<!-- /.box-body -->
									</div>
									<!-- /.box -->

								</div>
								<div class="clearfix"></div>
								<div class="">
									<div class="box box-default">
										
										<div class="box-body">
											<div class="row">
												<div class="col-md-12">
													<div id="alerts-list"></div>
												</div>
											</div>
										</div>
										<!-- /.box-body -->
									</div>
									<!-- /.box -->
								</div>
							</div>
                   <!-- End: Alerts -->
                </div>
                </c:if>
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"service schedule\")%>">
                <!-- hotel search -->
                <div class="bhoechie-tab-content vehicleSSAct">
                    <!-- Service Scheduling -->
                    <div class="row"><h4 class="tab-content-heading border-clr3" style="background: #daffee;">Service Schedule</h4></div>
                    				<div class="box box-default">
										
										<div class="box-body">
											<div class="row">
												<div class="col-md-12">
												<div id="serviceScheduling-grid"></div>
												</div>
											</div>
										</div>
									</div>
                    
                    <!-- End: Service Scheduling -->
                </div>
                </c:if>
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"documents\")%>">
                <div class="bhoechie-tab-content vehicleDocAct">
                    <!-- Document -->
					<div class="row"><h4 class="tab-content-heading border-clr4" style="background: #cce4f3;">Documents</h4></div>
						<div class="box box-default">
							<div class="box-body">
								<div class="row">
									<div class="col-md-12">
									<div id="documents-list"></div>
									</div>
								</div>
							</div>
						</div>
                   <!-- End: Document -->
                </div>
                 </c:if>
                 <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"fuel and mileage\")%>">
                <div class="bhoechie-tab-content vehicleFMAct">
                    <!-- Fuel & Mileage -->
                    <div>
                    	<div class="row"><h4 class="tab-content-heading border-clr5" style="background: #f9d6d2;">Fuel & Mileage</h4></div>
								
								<div class="col-md-6">
									<div class="box box-default" style="background: #eff9fb;">
									<div class="box-header with-border">
												<h4 class="box-title">Fuel Consumption (last week)</h4>
										</div>
										<div class="box-body">
											<div class="row">
												<div class="col-md-12">
													<div id="fuel-list"></div>
													<div class="overlay"><div>No data available</div></div>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-md-6">
									<div class="box box-default" style="background: #eff9fb;">
										<div class="box-header with-border">
												<h4 class="box-title">Mileage (last week)</h4>
										</div>
										
										<div class="box-body">
											<div class="row">
												<div class="col-md-12">
													<div id="mileage-list"></div>
													<div class="overlay"><div>No data available</div></div>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-md-6">
									<div class="box box-default" style="background: #eff9fb;">
										<div class="box-header with-border">
												<h4 class="box-title">Summary</h4>
										</div>
										<div class="box-body">
											<div class="row">
												<div class="col-md-12">
													<div id="summary-list"></div>
													<div class="overlay"><div>No data available</div></div>
												</div>
											</div>
										</div>
									</div>
								</div>
								

							</div>
                    <!-- End: Fuel & Mileage -->
                </div>
                 </c:if>
            </div>
             <div class="bhoechie-tab-menu" style="width:3%;float:left;margin: 10px 0px;">
              <div class="list-group">
              <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"vehicle details\")%>">
                <a href="#" class="list-group-item text-center bg-clr1 vehicleDetailsAct">
                  <!-- <h4 class="glyphicon glyphicon-plane"></h4><br/>  -->
                  <span>Vehicle Details</span>
                </a>
                </c:if>
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"alert\")%>">
                <a href="#" class="list-group-item text-center bg-clr2 vehicleAlertAct">
                  <!-- <h4 class="glyphicon glyphicon-road"></h4> -->
                  <span>Alert</span>
                </a>
                 </c:if>
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"service schedule\")%>">
                <a href="#" class="list-group-item text-center bg-clr3 vehicleSSAct">
                  <!-- <h4 class="glyphicon glyphicon-home"></h4> -->
                  <span>Service Schedule</span>
                </a>
                 </c:if>
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"documents\")%>">
                <a href="#" class="list-group-item text-center bg-clr4 vehicleDocAct">
                  <!-- <h4 class="glyphicon glyphicon-cutlery"></h4> -->
                  <span>Documents</span>
                </a>
                 </c:if>
                <c:if test="<%=hasPermissionAccess(\"vehicle_details\",\"fuel and mileage\")%>">
                <a href="#" class="list-group-item text-center bg-clr5 vehicleFMAct">
                  <!-- <h4 class="glyphicon glyphicon-credit-card"></h4> -->
                  <span>Fuel & Mileage</span>
                </a>
                 </c:if>
              </div>
            </div>
        </div>
  </div>
		
		
		
		<!-- End New Tab design -->
		

	</section>
</div>


<spring:url value="resources/js/equipment/vehicle/vehicledashboard.js" var="vehicleDashboardJS" />
<script src="${vehicleDashboardJS}"></script>

<spring:url value="resources/js/equipment/vehicle/vehicledashboardAlerts.js" var="vehicleDashboardAlertsJS" />
<script src="${vehicleDashboardAlertsJS}"></script>

<spring:url value="resources/js/equipment/vehicle/vehicledashboardServices.js" var="vehicledashboardServicesJS" />
<script src="${vehicledashboardServicesJS}"></script>

<spring:url value="resources/js/equipment/vehicle/vehicledashboardDocuments.js" var="vehicledashboardDocumentsJS" />
<script src="${vehicledashboardDocumentsJS}"></script>

<script>
	var vehicleListresponse = ${vehicleListresponse};
	var payload = ${payload};
	var vehicle_id = '${vehicle_id}';
</script>
<!-- <script -->
<!-- 	src="js/jquery-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script> -->
	<script src="resources/plugins/websocket/websocket.js"></script>
	<script src="resources/plugins/websocket/mqttws31-min.js"></script>
	<script src="resources/plugins/websocket/WebORB.js"></script>

	<script id="rowTemplate" type="text/x-kendo-tmpl">		  
		<tr class="#:status#" role="row" id="#: rowid#">
		  <td role="gridcell">#: alertName#</td>
		  <td role="gridcell">#: message #</td>
		  <td role="gridcell">#: assetInfo #</td>
		  <td role="gridcell">#:  toDateFormat(time, 'F') #</td>
		  <td role="gridcell">#:  toDateFormat(starttime, 'F') #</td>
		  <td role="gridcell">#: value #</td>
		</tr>     
	</script>
<script>

$(document).ready(function() {
    $("div.bhoechie-tab-menu>div.list-group>a").click(function(e) {
        e.preventDefault();
        $(this).siblings('a.active').removeClass("active");
        $(this).addClass("active");
        var index = $(this).index();
        $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active");
        $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
    });
});

	$('aside.control-sidebar').show().addClass('control-sidebar-open');
	$('body').addClass('sidebar-collapse');

	$('.main-sidebar').resize(function() {
		$('.accordionContainer').uberAccordion({
			headerClass : 'title',
			contentClass : 'content'
		});
	});
</script>
