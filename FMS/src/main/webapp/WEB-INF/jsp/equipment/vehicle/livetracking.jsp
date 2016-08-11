<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="ListVehicleUrl" expression="@propertyConfigurer.getProperty('fms.services.devicelocation')" />
<spring:eval var="SearchGeoTagUrl" expression="@propertyConfigurer.getProperty('fms.services.findGeotag')" />
<spring:eval var="GetDeviceDataUrl" expression="@propertyConfigurer.getProperty('fms.device.history')" />
<spring:eval var="GetAssetLatestUrl" expression="@propertyConfigurer.getProperty('fms.services.latestasset')" />
<spring:eval var="ListGeofenceUrl" expression="@propertyConfigurer.getProperty('fms.services.listGeoFence')" />
<spring:eval var="ViewGeofenceUrl" expression="@propertyConfigurer.getProperty('fms.services.findGeoFence')" />
<spring:eval var="VehicleFindUrl" expression="@propertyConfigurer.getProperty('fms.services.vehicleFind')" />


<style type="text/css">
	.skin-blue .main-header .navbar{
      background: radial-gradient(#197aa5 15%, #005180 60%);
	  }
   .skin-blue .main-header .logo {
    background-color: #158dc1;
	}
	
	.content{
	    margin: 10px 0px 0px 0px;
    border-top: 1px solid #989292;
	 border-bottom: 1px solid #989292;
    padding: 0px;
	}
	
	.monitoring-popup{
		width:300px;
	    display: inline-block;
	}
	
	.monitoring-popup figure {
		width: 33%;
		padding: 5px;
		height: 110px;
		float: left;
		text-align: center;
		background: #c3c4c3 url("resources/images/equipicon.svg") no-repeat;
		background-position: center center;
		background-size: 44px 44px;
		margin-top: 7px;
	}
	.monitoring-popup section {
		width: 63%;
		float: left;
		margin-left: 10px;
		overflow: hidden
	}
	
	.monitoring-popup section strong.popup-header{
		    padding: 4px 0px 2px 0px;
			color: #fff;
			float: left;
			font-size: 16px;
			text-transform: uppercase;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: pre-wrap;
			width: 200px;
	}
	
	.leaflet-popup-content-wrapper,.leaflet-popup-content-wrapper, .leaflet-popup-tip{background-color: rgba(49, 48, 48, 0.8);}
	.brand-success{color:#4df14d;padding-bottom:4px;font-size:14px;font-weight:bold;}
	.brand-default strong{font-size:13px!important;}
	.brand-default{color:#fff;font-size:13px;}
	.leaflet-popup-content-wrapper {
		border-radius: 3px;
	}
	.k-state-focused{color:#000!important}
	.leaflet-popup-content{color:#fff!important}
	
	.pin {
    width: 45px;
    height: 45px;
    border-radius: 50% 50% 50% 3%;
    background:#197aa5;;
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

.asset-icon-list a {
    display: inline-block;
    padding: 0.8em;
    color: #fff;
    font-family: "Open Sans", sans-serif;
    padding: 0px !important;
    margin-left: 62px;
    font-size: 13px;
    direction: ltr;
}
.asset-icon-list a i {
    vertical-align: middle;
}

.asset-icon-list a span {
    display: inline-block;
    margin-left: 10px;
    font-size: 12px;
    vertical-align: middle;
    font-weight: 700;
    letter-spacing: 1px;
}

.pincount {
    width: 37px;
    height: 37px;
    border-radius: 50% 50% 50% 3%;
    background: #14a5ae;
    position: relative;
    transform: rotate(-43deg);
    margin: 0;
    float: left;
}

.pincount:after {
    content: '';
    width: 29px;
    height: 29px;
    margin: 4px 0 0 -15px;
    background: #fff;
    position: absolute;
    border-radius: 50%;
    overflow: hidden;
}

.pincount img {
    position: absolute;
    z-index: 3;
    transform: rotate(43deg);
    left: 8px;
    top: 8px;
}

.pincount:before {
    background: rgba(255, 255, 255, 0.2);
    border-radius: 50%;
    height: 24px;
    width: 7px;
    position: absolute;
    margin: 11px 0px 0px -12px;
    transform: rotateZ(134deg);
    top: 15px;
    left: 9px;
    content: "";
    filter: blur(16px);
    -webkit-filter: blur(0.1em);
}

span.k-in.k-state-hover{cursor: pointer!important;}

@-moz-keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-43deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-43deg);
  }
  80% {
    transform: translateY(-10px) rotate(-43deg);
  }
  100% {
    transform: translateY(0) rotate(-43deg);
  }
}
@-webkit-keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-43deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-42deg);
  }
  80% {
    transform: translateY(-10px) rotate(-42deg);
  }
  100% {
    transform: translateY(0) rotate(-42deg);
  }
}
@-o-keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-42deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-42deg);
  }
  80% {
    transform: translateY(-10px) rotate(-42deg);
  }
  100% {
    transform: translateY(0) rotate(-42deg);
  }
}
@keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-43deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-43deg);
  }
  80% {
    transform: translateY(-10px) rotate(-43deg);
  }
  100% {
    transform: translateY(0) rotate(-43deg);
  }
}

.leaflet-div-icon {
    background: transparent;
    border: 0px solid #666;
}

#sidebar-wrapper {
		z-index: 1000;
		position: fixed; 
    width: 250px;
    height: 100%;
		overflow-y: auto;
		background: rgba(0,0,0,0.8);
		-webkit-transition: all 0.5s ease;
		-moz-transition: all 0.5s ease;
		-o-transition: all 0.5s ease;
		transition: all 0.5s ease;
	}
	
	#sidebar-wrapper{
	     box-shadow: 0px -25px 15px #080808 inset;
	     padding-left: 0px;

	}
	
	.sidebar-title .caption{
		padding: 15px 10px 10px 0px;
		color: #fff;
		font-size: 14px;
		line-height: 22px;
		width: 250px;
	}

	.caption-title{
	    font-size: 13px;
    padding: 10px 10px 10px 12px;
    line-height: 0px;
    display: inline-block;
	position:absolute;
	}

	.sidebar-title .fa-list:before {
		font-size: 19px;
		padding: 14px 17px 12px 12px;
		background: #484848;
		line-height: 0px;
	}
	.sidebar-title .fa-angle-double-right:before {
		font-size: 19px;
    padding: 6px 20px 9px 17px;
    background: #404040;
    line-height: 0px;
    margin-left: 0px;
    /* border-right: 1px solid #616161; */
	}
	.sidebar-title .fa-angle-double-down:before {
		font-size: 19px;
		padding: 14px 18px 12px 18px;
		background: #484848;
		line-height: 0px;
	}
	
	.k-group, .k-flatcolorpicker.k-group, .k-menu, .k-menu .k-group, .k-popup.k-widget.k-context-menu {
    color: #fdfdfd;
    background-color: #ffffff;
}

.leftbar-clientlist{
    margin: 18px;
}

.leftbar-clientlist .treeview .k-treeview{overflow:hidden}

.search-asset{
	padding: 6px 12px;
	font-size: 14px;
	color: #555;
	border: 1px solid #ccc;
	border-radius: 4px;
	width: 85%;
}

@keyframes glowing {
	  0% { background-color: #004A7F; box-shadow: 0 0 3px #004A7F; }
	  50% { background-color: #0094FF; box-shadow: 0 0 10px #0094FF; }
	  100% { background-color: #004A7F; box-shadow: 0 0 3px #004A7F; }
	}

.pin.highlight {
	animation: glowing 1500ms infinite;
}

#assetcountdetails {
	position: absolute;
	bottom: 50px;
	width: 100%;
	margin: 0 auto;
	text-align: center;
	color:#fff;
	bottom:45px;
}

#assettotal{
		height:145px;
		width:90px;
		border-radius: 90px 90px 0 0;
		-moz-border-radius: 90px 90px 0 0;
		-webkit-border-radius: 90px 90px 0 0;
		background-color: #28303b;
		padding: 8px;opacity: 0.9;

	}
	#assettotal a{ 
		color: #FFFFFF;
		text-decoration: none;
		font-size: 16px;
		padding: 20px;
		opacity: 0.9;
	}

	#assetcountpanel {
		display: none;
		position:relative;
		height: 250px;
		background-color: rgba(0,0,0,0.8);
		padding: 0px 0px 10px 0px;
		margin: 6px 0 0 0;
		width:100%;
		
		z-index:9999; /* for override map zoom & zoom out icon*/
	}
	
	#assetcountpanel p {
	
		width: 64%;
	    position: relative;
	    margin: 0 auto;
	    text-align: left;
	}	
	
	.map-history-btn{ position:relative; display: block;    margin: 6px 0 !important;}


 .modal-dialog-history {
		width: 100%;
		height: 100%;
		margin: 0;
		padding: 0;
	}

	.modal-content-history {
		height: auto;
		min-height: 100%;
		border-radius: 0;
		width: 100%;
	}
	
	.gn-menu-wrapper{top:84px;}

#gapp-assetlive-container{
    /*width: 440px;
    height: 348px;*/
    background-color: rgba(10, 10, 10, 0.9);
    position: absolute;
    top: -80px;
    left: 343px;
    box-shadow: 0px 2px 10px #000;
    border-radius: 9px;
}

html, body, #vehiclesmonitoringmap {
    overflow: hidden;
}

 #vehiclesmonitoringmap {
    position:absolute!important;
}

.main-footer{position:relative;z-index: 9999!important;}
</style>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Vehicle Monitoring</h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"> Vehicle Monitoring</li>
      </ol>
    </section>
    
    <!-- Main content -->
    <section class="content">
    	<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<div class="sidebar-title">
				<div class="caption" id="leftbar" style="padding:6px 10px 6px 0px;color: #fff;font-size: 14px;line-height: 22px;border-top: 1px solid #616161;background-color: #484848;border-bottom: 1px solid #616161;"> 
				<a href="javascript:void(0);" id="menu-toggle-2"><i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
					<div class="caption-title">Vehicle List</div>
					<span id="fms_vehiclecount" style="float: right;padding: 0px 15px;background-color: #08cebb;border-radius: 4px;color: #000;font-weight: bold;font-size: 13px;">0</span>
				</div>	
			</div>
			
			<div class="sidebar-body">
				<div id="assetSection">
					<div class="leftbar-clientlist">
						<input id="treeSearchInput" class="search-asset" placeholder="Search Vehicle" />
					</div>
					<div class="treeview" style="margin:18px;height: 521px;overflow-y: auto;">
						<div id="treeview"></div>
					</div>																								
				</div>
			</div>		
		</div><!-- /#sidebar-wrapper -->
		
		<div class="col-split-12">
			<section id="vehiclesmonitoringmap"  style="width:100%; height:100%;position:static!important"></section>
			
			<section id="assetcountdetails">
				<span id="assettotal"><a href="Javascript:void(0)" id="asset-toggle" onclick="FnManageAssetInfo()">0</a></span>
				<div id="assetcountpanel">
					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active"><a href="#assettype_details_count" aria-controls="profile" role="tab" data-toggle="tab" id="assettype_details_count-id"><span>Assets List</span></a></li>
						<li role="presentation" ><a href="#gapp-chart" aria-controls="home" role="tab" data-toggle="tab" id="gapp-chart-id"><span >Points chart</span></a></li>
						<li role="presentation" ><a href="#gapp-geofence-list" aria-controls="home" role="tab" data-toggle="tab" id="gapp-geofence-head"><span>Geofence</span></a></li>
					</ul>
					<div class="tab-content">
						<div role="tabpanel" class="asset-icon-list tab-pane fade in active" id="assettype_details_count">
							<div id="assettype_details_list">No Records to display</div>
						</div>
						<div role="tabpanel" class="tab-pane fade" id="gapp-chart">
							<div class="btn-group" role="group" style="float:left; margin:5px 0 0 10px">
								<button type="button" class="btn btn-info" onclick="FnManagePointHistoryChart(0)">History</button>
  								<button type="button" class="btn btn-info" style="margin-left:1px;" onclick="FnManagePointHistoryChart(1)">Live</button>
							</div>
							<div id="gapp_point_history">No Records to display</div>
							<div id="gapp_point_live" style="display:none;">No Records to display</div>
						</div>
						<div role="tabpanel" class="tab-pane fade" id="gapp-geofence-list">
							<div id="gapp_geofence">No Records to display</div>
						</div>
					</div>
				</div>
			</section>
			
			<form id="vehicle_info" role="form" action="vehicle_details" name="vehicle_info" method="post">									
				<input type="hidden" id="vehicle_id" name="vehicle_id" />
			</form>
			
		</div>	
    
    </section>
    <!-- /.content -->

</div>
<!-- /.content-wrapper -->

<!-- Start Small modal -->
<div class="bootbox modal fade in" tabindex="-1"  id="myModal" role="dialog" aria-hidden="false" style=" padding-right: 15px;">
	<div class="modal-dialog" style="max-width:575px;">
		<div class="modal-content">
		
			<div class="modal-header">
				<button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="asset-name-history">Custom title</h4>
			</div>
			
			<div class="modal-body" style="">				
				<div class="bootbox-body text-center">
					<form class="form-inline" role="form">
						<div class="form-group"><span class='pull-left'>From &nbsp;&nbsp;</span>
							
							
							<input type="text" class="form-control style="margin-right:15px;" date-picker" name="startDate" id="startDate" tabindex="2"  placeholder="Select Date" required/>
						</div>
						<div class="form-group">
							<span class='pull-left'>&nbsp;&nbsp;To&nbsp;&nbsp;</span>
							
							<input type="text" class="form-control date-picker" name="endDate" id="endDate" tabindex="3" placeholder="Select Date" required/>
						</div>
						&nbsp;<button class="btn green" type="button"  id="BtnPlotAsset" onclick="return FnPlotAssetHistory()">Plot</button>
						
						<input type="hidden" name="VarIdentifier" id="VarIdentifier" value="">
						<input type="hidden" name="VarAssetName" id="VarAssetName" value="">
						<input type="hidden" name="VarDataSourceName" id="VarDataSourceName" value="">
						
					</form>
				</div>
			</div>
			
			<div class="modal-footer">
				<!--<button data-bb-handler="success" type="button" class="btn green">Success!</button>-->
				
			</div>
		</div>
	</div>
</div>
<!-- End Small Modal-->


<script type="text/javascript">
	var VarListVehicleUrl = "${ListVehicleUrl}";
	var VarSearchGeoTagUrl = "${SearchGeoTagUrl}";
	var VarGetDeviceDataUrl = "${GetDeviceDataUrl}";
	var VarGetAssetLatestUrl = "${GetAssetLatestUrl}";
	var VarListGeofenceUrl = "${ListGeofenceUrl}";
	var VarViewGeofenceUrl = "${ViewGeofenceUrl}";
	var VarVehicleFindUrl = "${VehicleFindUrl}";
</script>

<script type="text/javascript" src="resources/plugins/websocket/mqttws31-min.js"></script>
<script type="text/javascript" src="resources/plugins/websocket/websocket.js"></script>
<script type="text/javascript" src="resources/js/mask.js"></script>
<script type="text/javascript" src="resources/js/equipment/vehicle/livetracking.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	$('body').addClass('sidebar-collapse');
		var TriggerClick = 0;
		$("#leftbar").click(function(){
			if(TriggerClick==0){
				TriggerClick=1;
				$("#sidebar-wrapper").css({width:"40px"});
				$(".sidebar-body").hide();
				$(".caption-title").css({"transform":"translate(-86px, -105px) rotate(90deg) scale(1)","transition": "transform 0.2s","transform-origin": "left center","width": "115px","height": "203px","left":"10px","top":"35px"});
			} else {
				TriggerClick=0;
				$("#sidebar-wrapper").css({width:"250px"});
				$("#assetcountpanel").hide();
				$(".sidebar-body").show();
					$(".caption-title").css({"transform":"rotate(0deg) scale(1)","transition": "all 0.2s","height": "0px","left":"48px","top":"8px","transform-origin": "left left"});
			}
		});
		
		
		
		$("#menu-toggle-2").click(function(e) {	 
			e.preventDefault();
			map.invalidateSize();
			$("#wrapper").toggleClass("toggled-2");
			$('#menu ul').hide();
			
			if($( ".caption-title" ).hasClass( "rotate" )){
				$( ".sidebar-title .caption-title" ).removeClass( "rotate" );
			 	$(".leftbar-clientlist").show();
			} else {
				$( ".sidebar-title .caption-title" ).addClass( "rotate" );
				$(".leftbar-clientlist").hide();
			}
		
			if($( ".sidebar-title .fa" ).hasClass( "fa-angle-double-right" )){
				$( ".sidebar-title .fa" ).removeClass( "fa-angle-double-right" );
				$( ".sidebar-title .fa" ).addClass( "fa-angle-double-down" );
			} else {
				$( ".sidebar-title .fa" ).removeClass( "fa-angle-double-down" );
				$( ".sidebar-title .fa" ).addClass( "fa-angle-double-right" );
			}
		});
		
	});
</script>
