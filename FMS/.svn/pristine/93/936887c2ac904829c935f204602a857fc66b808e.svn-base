<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.pcs.fms.web.constants.FMSWebConstants"%>


<!--  <script src="resources/javascript/home.js"></script> -->
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<style type="text/css">
		
	.right-arrow {
		/*
		position: relative;
		background-color: #0683b3;
		padding: 15px;
		*/
	}
	.right-arrow:after {
		/*
		content: '';
		display: block;  
		position: absolute;
		left: 100%;
		top: 50%;
		margin-top: -6px;
		width: 0;
		height: 0;
		border-top: 6px solid transparent;
		border-right: 6px solid transparent;
		border-bottom: 6px solid transparent;
		border-left: 6px solid #0683b3;
		*/
	}
	
	.overlay {
      font-size: 20px;
	  font-family: "open_sanslight",arial;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 40%;
      left: -51px;
      text-align: center;
	  /*color:#00c6fc;*/
	  color:#12457A;
    }
    
    /*.overlay {
        position: absolute;
        top: 50%;
        left: 50%;
        width: 100px;
        height: 100px;
        margin-top: -50px;
        margin-left: -50px;
        font-size: 16px;
        line-height: 100px;
        vertical-align: middle;
        text-align: center;
        color: #a2ccef;
      }*/
    
 
ul.durationlist {
  
    padding: 5px;
    width: 120px;
    /* position: absolute; */
    /* right: 17px; */
    display: inline-block;
    /*float: right; */
     margin-left: 55%!important;
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
		padding: 5px;

		background: #0AC9FF; /* For browsers that do not support gradients */
		background: -webkit-linear-gradient(left, #0AC9FF , #A5EBFE); /* For Safari 5.1 to 6.0 */
		background: -o-linear-gradient(right, #0AC9FF, #A5EBFE); /* For Opera 11.1 to 12.0 */
		background: -moz-linear-gradient(right, #0AC9FF, #A5EBFE); /* For Firefox 3.6 to 15 */
		background: linear-gradient(to right, #0AC9FF , #A5EBFE); /* Standard syntax */
	
    }
	
	ul.durationlist li a {    
	color: #000;
	text-decoration:none;
	}
	
	
	
    
    .clientlistblock {
    	height: 422px;
		overflow-y: scroll;
    }
    
    ul#clientlist {
    	list-style-type:none;
	    padding: 0;
	    margin: 0;
	    display: inline-block;
	    width: 100%;
		
	}
	
	ul#clientlist li {
	    height: 30px;
	   
	    padding: 5px 20px 0px 20px;
	   
	    width: 95%;
	    text-transform: capitalize;
		border-bottom:1px solid #cfcfcf;
		border-left:1px solid #cfcfcf;
		border-right:1px solid #cfcfcf;
	}
	
	ul#clientlist li:first-child {
    border-top:1px solid #cfcfcf;
	}
	
	ul#clientlist li:nth-child(odd) {
		/*
		background-color:#CED2D6;
		*/
		/* IE10+ */ 
background-image: -ms-linear-gradient(right, #FFFFFF 25%, #CED2D6 100%);

/* Mozilla Firefox */ 
background-image: -moz-linear-gradient(right, #FFFFFF 25%, #CED2D6 100%);

/* Opera */ 
background-image: -o-linear-gradient(right, #FFFFFF 25%, #CED2D6 100%);

/* Webkit (Safari/Chrome 10) */ 
background-image: -webkit-gradient(linear, right top, left top, color-stop(25, #FFFFFF), color-stop(100, #CED2D6));

/* Webkit (Chrome 11+) */ 
background-image: -webkit-linear-gradient(right, #FFFFFF 25%, #CED2D6 100%);

/* W3C Markup */ 
background-image: linear-gradient(to left, #FFFFFF 25%, #CED2D6 100%);
		
		
	}
	
	ul#clientlist li a {
		text-decoration: none;
		margin: 0px;
		cursor: pointer;
		color:#666;
		display: inline-block;
		width: 100%;
		height: 100%;
		
		
	}
	
	ul#clientlist li a:hover {color:#fff;}
	
	ul#clientlist li:hover{
	
	text-transform: capitalize;
		
		
	/*background-color:#0AC9FF;*/
	/* IE10+ */ 
background-image: -ms-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Mozilla Firefox */ 
background-image: -moz-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Opera */ 
background-image: -o-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Webkit (Safari/Chrome 10) */ 
background-image: -webkit-gradient(linear, left top, right top, color-stop(0, #0AC9FF), color-stop(100, #97E8FE));

/* Webkit (Chrome 11+) */ 
background-image: -webkit-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* W3C Markup */ 
background-image: linear-gradient(to right, #0AC9FF 0%, #97E8FE 100%);
	
		
		
		}
	ul#clientlist li.active {
		
		text-transform: capitalize;
		/*background-color:#0AC9FF;*/
		color:#fff;
	/* IE10+ */ 
background-image: -ms-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Mozilla Firefox */ 
background-image: -moz-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Opera */ 
background-image: -o-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Webkit (Safari/Chrome 10) */ 
background-image: -webkit-gradient(linear, left top, right top, color-stop(0, #0AC9FF), color-stop(100, #97E8FE));

/* Webkit (Chrome 11+) */ 
background-image: -webkit-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* W3C Markup */ 
background-image: linear-gradient(to right, #0AC9FF 0%, #97E8FE 100%);
	}
	
	
	ul#clientlist li.active a {
    
	/*background-color:#0AC9FF;*/
/* IE10+ */ 
background-image: -ms-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Mozilla Firefox */ 
background-image: -moz-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Opera */ 
background-image: -o-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* Webkit (Safari/Chrome 10) */ 
background-image: -webkit-gradient(linear, left top, right top, color-stop(0, #0AC9FF), color-stop(100, #97E8FE));

/* Webkit (Chrome 11+) */ 
background-image: -webkit-linear-gradient(left, #0AC9FF 0%, #97E8FE 100%);

/* W3C Markup */ 
background-image: linear-gradient(to right, #0AC9FF 0%, #97E8FE 100%);
		color:#fff;
}
	
	.bg-gray{background-color: #f2efef;}
	.bg-white{background-color: #fff;}
	
	#client_summary_info{
/* IE10+ */ 
background-image: -ms-linear-gradient(left, #5FDCFF 0%, #A5EBFE 50%);

/* Mozilla Firefox */ 
background-image: -moz-linear-gradient(left, #5FDCFF 0%, #A5EBFE 50%);

/* Opera */ 
background-image: -o-linear-gradient(left, #5FDCFF 0%, #A5EBFE 50%);

/* Webkit (Safari/Chrome 10) */ 
background-image: -webkit-gradient(linear, left top, right top, color-stop(0, #5FDCFF), color-stop(50, #A5EBFE));

/* Webkit (Chrome 11+) */ 
background-image: -webkit-linear-gradient(left, #5FDCFF 0%, #A5EBFE 50%);

/* W3C Markup */ 
background-image: linear-gradient(to right, #5FDCFF 0%, #A5EBFE 50%);
    /*height:34px;
  width:100%;
  display:table;
  
  background: #97E8FE;*/
    padding-bottom: 0px;
    margin: 0px;
    border: 0px;
    margin-bottom: 9px;
    height: 35px;
    width: 100%;
    display: table;
    
	padding-left:0px!important;
  
	}
	#client_summary_info h4{ 
		/*
		font-size:14px;
		color:#fff;
		display:table;
		vertical-align:middle;
		*/
		
		  padding-left: 15px;
    color: #fff;
    font-size: 14px;
    /* width: 10%; */
    display: inline-block;
    vertical-align: middle;
    /* max-width: 135px; */
    /* margin: 0px; */
    height: 35px;
    margin: 0px!important;
    padding: 10px 20px 6px 16px;
    background-color: #5FDCFF;
		
	}

	span#tenant_name {    color: #fff; font-size:14px;/*text-transform:uppercase*/}
	/*
	#tenants_summary_wrapper {    position: relative;   width: 96.5%;    margin-left: 15px;}
	*/
	#tenants_summary_wrapper {    
	position: relative;    width: 97%;    margin-left: 15px;

	
	}
	
	div#tenants_summary_wrapper > div:first-child {
		border-left: 1px solid #cfcfcf; border-right: 1px solid #cfcfcf;border-bottom: 1px solid #cfcfcf;

		border-bottom-right-radius: 5px;
		border-bottom-left-radius: 5px;
	}
	
	.zeromargin{padding:0; margin:0}
	
		.bg-gray{background-color: #f2efef; background:none}
		.bg-white{ background:none}
		
		

/* Calendar styling */

#calendar {
    width: 400px;
    margin: 0 auto;
    font-size: 10px;
}
.fc-toolbar {
    font-size: .9em;
}
.fc-toolbar h2 {
    font-size: 12px;
    white-space: normal !important;
}
/* click +2 more for popup */
.fc-more-cell a {
    display: block;
    width: 95%;
    margin: 1px auto 0 auto;
    border-radius: 3px;
    background: grey;
    color: transparent;
    overflow: hidden;
    height: 4px;
}
.fc-more-popover {
    width: 100px;
}
.fc-view-month .fc-event, .fc-view-agendaWeek .fc-event, .fc-content {
    font-size: 0;
    overflow: hidden;
    height: 2px;
}
.fc-view-agendaWeek .fc-event-vert {
    font-size: 0;
    overflow: hidden;
    width: 2px !important;
}
.fc-agenda-axis {
    width: 20px !important;
    font-size: .7em;
}

.fc-button-content {
    padding: 0;
}

th.fc-day-header {
    color: #000;
    font-size: 12px;
}


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

<spring:eval var="tenantsCountUrl"
	expression="@propertyConfigurer.getProperty('mc.services.tenantsCount')" />
<spring:eval var="usersCountUrl"
	expression="@propertyConfigurer.getProperty('mc.services.userCount')" />
<spring:eval var="devicesCountUrl"
	expression="@propertyConfigurer.getProperty('mc.services.deviceCount')" />
<spring:eval var="tenantListUrl"
	expression="@propertyConfigurer.getProperty('mc.services.listTenant')" />
<spring:eval var="childrenCountUrl"
	expression="@propertyConfigurer.getProperty('mc.hierarchy.children.count')" />
<spring:eval var="userStatusCountUrl"
	expression="@propertyConfigurer.getProperty('mc.services.userStatusCount')" />
<spring:eval var="deviceStatusCountUrl"
	expression="@propertyConfigurer.getProperty('mc.hierarchy.status.count')" />	
<spring:eval var="getAllMake"
	expression="@propertyConfigurer.getProperty('mc.device.make')" />
<spring:eval var="getAllDevices"
	expression="@propertyConfigurer.getProperty('mc.services.listFilterDevice')" />	
	
		
<script type="text/javascript">
	var VarTenantsCountUrl = "${tenantsCountUrl}";
	var VarUsersCountUrl = "${usersCountUrl}";
	var VarDevicesCountUrl = "${devicesCountUrl}";
	var VarTenantListUrl = "${tenantListUrl}";
	var VarChildrenCountUrl = "${childrenCountUrl}";
	var VarUserStatusCountUrl = "${userStatusCountUrl}";
	var VarDeviceStatusCountUrl = "${deviceStatusCountUrl}";
	var VarDeviceTemplateName = "<%=FMSWebConstants.DEVICE_TEMPLATE%>";
	var VarAllMakeUrl = "${getAllMake}";
	var VarDeviceUrl = "${getAllDevices}";
</script>


  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
  	 <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>      Dashboard    <small id="logged-user"></small>    </h1>
      <ol class="breadcrumb hidden">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Dashboard</li>
      </ol>
    </section>
      <!-- Main content -->
    <section class="content">
    
    		 <!-- Small boxes (Stat box) -->
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
        </div>
        <!-- ./col -->
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
      </div>
      <!-- /.row -->
    
    
    
    
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
		  <div class="box box-solid bg-light-blue-gradient">

			
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
			</div>
			
			
			
    

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
              <h3 class="box-title">Alert count based On Criticality(Last week)</h3>

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
		</section><!-- . content -->
          <!-- Calendar -->
          
          <!-- /.box -->

     
        <!-- right col -->
      </div>
      <!-- /.row (main row) -->
    
  
  </div>  <!-- /.content-wrapper -->


<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
     
      <div class="modal-body">
        <div id="largemap" style="height:500px;width:auto;z-index:99;"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>



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

<style type="text/css">
	.modal {
		visibility: hidden;
		display: block;
	}

	
	.modal[aria-hidden='false'] {
		visibility: visible;
		display: block;
	}
</style>
<script type = 'text/javascript'>
	$('#myModal').on('show.bs.modal', function () {
		/*
		$(this).find('.modal-body').css({
		width:'auto', 
		height:500px, 
		'max-height':'100%'
		});*/
		//setTimeout(function() {
		//	map.invalidateSize();
		//}, 10);
	});
</script>