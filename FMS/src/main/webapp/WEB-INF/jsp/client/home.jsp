<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style type="text/css">
			
	.overlay {
      font-size: 20px;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 40%;
      left: -40px;
      text-align: center;
    }  
  
	 
ul.durationlist {
    background: #fff;
    padding: 5px;
    width: 120px;
    /* position: absolute; */
    /* right: 17px; */
    display: inline-block;
    /* float: right; */
    margin-left: 70%!important;
    text-align: right;
}
        
    .durationlist li{
    	display: inline;
		list-style-type: none;
		padding: 2px;
    }
    
     .durationlist li.active{
    	display: inline;
		list-style-type: none;
		padding: 2px;
		background-color: #3bb1cb;
    }
    	.bg-gray{background-color: #f2efef;}
	.bg-white{background-color: #fff;}
	#client_summary_info h4{}

	span#tenant_name {color: #337ab7; text-transform:uppercase}
	/*
	#tenants_summary_wrapper {    position: relative;   width: 96.5%;    margin-left: 15px;}
	*/
	#tenants_summary_wrapper {    position: relative;    width: 96.5%;    margin-left: 15px;}
	.zeromargin{padding:0; margin:0}
    	
   #tenantListContainer span {
    position: relative;
    margin: 0 auto;
    top: 92px;
    width: 100%;
    text-align: center;
    display: inline-block;
    font-size: 12px;
    color: gray;
}		




</style>

<spring:eval var="userStatusCountUrl"
	expression="@propertyConfigurer.getProperty('mc.services.userStatusCount')" />
<spring:eval var="deviceStatusCountUrl"
	expression="@propertyConfigurer.getProperty('mc.hierarchy.status.count')" />
<spring:eval var="childrenCountUrl"
	expression="@propertyConfigurer.getProperty('mc.hierarchy.children.count')" />
<spring:eval var="getAllMake"
	expression="@propertyConfigurer.getProperty('mc.device.make')" />
<spring:eval var="getDevicesOfTenant"
	expression="@propertyConfigurer.getProperty('mc.hierarchy.get.children')" />
	
<script type="text/javascript">
	
	var VarUserStatusCountUrl = "${userStatusCountUrl}";
	var VarDeviceStatusCountUrl = "${deviceStatusCountUrl}";
	var VarChildrenCountUrl = "${childrenCountUrl}";
	var VarAllMakeUrl = "${getAllMake}";
	var VarDeviceUrl = "${getDevicesOfTenant}";
</script>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
  
  	  	 <!-- Content Header (Page header) -->
    <!-- <section class="content-header">
      <h1>      Dashboard    <small id="logged-user hidden"></small>    </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Dashboard</li>
      </ol>
    </section> -->
      <!-- Main content -->
    <section class="content">
     <div class="row">
     	<div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-aqua">
            <div class="inner" id="total-vehicle-connected">
              <h3></h3>

              <p>Total Vehicles Connected</p>
            </div>
            <div class="icon">
              <i class="ion ion-android-subway"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>  <!-- ./col -->
                <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner"  id="total-running-vehicle">
              <h3>53<sup style="font-size: 20px"></sup></h3>

              <p>Running Vehicles</p>
            </div>
            <div class="icon">
              <i class="ion ion-model-s"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
                <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner" id="total-idle-vehicle" >
              <h3></h3>

              <p>Idle Vehicles</p>
            </div>
            <div class="icon">
              <i class="ion ion-android-bus"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
                <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner" id="total-stopped-vehicle">
              <h3></h3>

              <p>Stopped Vehicles</p>
            </div>
            <div class="icon">
              <i class="ion ion-android-car"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->     
     
     </div><!--  .row -->
     
     <!-- Main row -->
      <div class="row">
        <!-- Left col -->
        <section class="col-lg-7 connectedSortable">
          <!-- Custom tabs (Charts with tabs)-->
          <div class="nav-tabs-custom">
            <!-- Tabs within a box -->
            <ul class="nav nav-tabs pull-right">
            <!--  <li class="active"><a href="#revenue-chart" data-toggle="tab">Area</a></li>
              <li><a href="#sales-chart" data-toggle="tab">Donut</a></li> 
			  -->
              <li class="pull-left header"><i class="fa fa-tint" aria-hidden="true"></i> Mileage vs Fuel Consumption</li>
            </ul>
            <div class="tab-content no-padding">
              <!-- Morris chart - Sales -->
             <!-- <div class="chart tab-pane active" id="revenue-chart1" style="position: relative; height: 300px;"></div>
              <div class="chart tab-pane" id="sales-chart1" style="position: relative; height: 300px;"></div> -->
			  <div class="demo-section k-content wide">
						<div  class="chart tab-pane active" id="mileage-chart" style="position: relative; height: 300px;">Loading...</div>
						</div>
            </div>
          </div>
          <!-- /.nav-tabs-custom -->
          <!-- Calendar -->
          <div class="box box-solid bg-green-gradient hidden">
            <div class="box-header">
              <i class="fa fa-calendar"></i>

              <h3 class="box-title">Calendar</h3>
              <!-- tools box -->
              <div class="pull-right box-tools">
                <!-- button with a dropdown -->
                <div class="btn-group">
                  <button type="button" class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-bars"></i></button>
                  <ul class="dropdown-menu pull-right" role="menu">
                    <li><a href="#">Add new event</a></li>
                    <li><a href="#">Clear events</a></li>
                    <li class="divider"></li>
                    <li><a href="#">View calendar</a></li>
                  </ul>
                </div>
               
              </div>
              <!-- /. tools -->
            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
              <!--The calendar -->
              <div id="calendar-small" style="width: 100%"></div>
            </div>
            <!-- /.box-body -->
           
          </div>
          <!-- /.box -->
          
          
 <div class="col-md-12 ">
          <div class="box box-solid bg-light-blue-gradient">
            <div class="box-body no-padding">
              <!-- THE CALENDAR -->
              <div id="calendar" ></div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /. box -->
        </div>
        <!-- /.col -->
    

        </section>
        <!-- /.Left col -->
        <!-- right col (We are only adding the ID to make the widgets sortable)-->
        <section class="col-lg-5 connectedSortable">
          <!-- Map box -->
          <div class="box box-solid bg-green-gradient">
            <div class="box-header">
              <!-- tools box -->
              <div class="pull-right box-tools">
              
                
              </div>
			  <i class="fa fa-map-marker"></i>
              <h3 class="box-title">  Asset Count Based On Type </h3>
            </div>
            <div class="box-body">
				<div id="asset-count-chart"  style="margin: 0 auto;" >Loading...</div>			  
            </div>
            <!-- /.box-body  style="height: 250px; width: 100%;"-->    
          </div>
          <!-- /.box -->
		  
		  

          <!-- solid sales graph -->
          <div class="box box-solid bg-teal-gradient">
            <div class="box-header">
              <i class="fa fa-bell" aria-hidden="true"></i>
              <h3 class="box-title">Alert count based on Criticality(Last week)</h3>

              <div class="box-tools pull-right">
               <!--   <button type="button" class="btn bg-teal btn-sm" data-widget="collapse"><i class="fa fa-minus"></i>-->
                </button>
               
              </div>
            </div>
            <div class="box-body border-radius-none">				
				  <div id="alert-count-chart" style="height: 250px;">Loading...</div>
            </div>
            </div>
            <!-- /.box-body -->
            <div class="box-footer no-border hidden">
              <div class="row">
                <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">.</div>               
              </div>
              <!-- /.row -->
            </div>
            <!-- /.box-footer -->
          </div>
          <!-- /.box -->
    
    </section> <!-- . section-2 content -->
  
  </div>
  <style type="text/css">
        .ui-dialog .ui-dialog-content
        {
            position: relative;
            border: 0;
            padding: .5em 1em;
            background: none;
            overflow: auto;
            zoom: 1;
            background-color: #ffd;
            border: solid 1px #ea7;z-index: 300;
        }

        .ui-dialog .ui-dialog-titlebar
        {
            display:block;
        }

        .ui-widget-content
        {
            border:none;
        }
        
        button.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close {
    float: right;
    right: 7px;
    position: absolute;
    z-index: 400;
    top: 27px;
    border: 1px solid #ef955e;
    font-size: 11px;
    background: white;
    box-shadow: 1px 1px #bfbfbf;
}
    </style>

  <!-- Calendar widget: popup -->
<div id="eventContent">
    <span class="ui-state-default"><span class="ui-icon ui-icon-info" ></span></span>
    <div style="margin-left: 23px;">
         <p>            
           <div id="eventInfo"></div>  		 
          
        </p>
        <p><strong><a href="eventLink" target="_blank">Read More</a></strong></p></div>
</div>







  <script src="http://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
<spring:url value="resources/js/dashboard/dashboard.js" var="demosJS"/><script src="${demosJS}"></script>
<script type="text/javascript">
	var response = '${response}';
	var type = '${type}';
	if (response != undefined && response != '') {
		staticNotification.show({
			message : response
		}, type);
	}
</script>