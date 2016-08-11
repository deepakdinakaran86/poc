<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="VehiclePointsURL"
	expression="@propertyConfigurer.getProperty('fms.device.children')" />
<spring:eval var="VehiclePointHistoryURL"
	expression="@propertyConfigurer.getProperty('fms.device.history')" />

<style>
.skin-blue .main-header .navbar {
	background: radial-gradient(#197aa5 15%, #005180 60%);
}

.skin-blue .main-header .logo {
	background-color: #158dc1;
}

#sidebar-wrapper {
	z-index: 1000;
	position: fixed;
	width: 250px;
	height: 100%;
	overflow-y: auto;
	background: rgba(0, 0, 0, 0.8);
	-webkit-transition: all 0.5s ease;
	-moz-transition: all 0.5s ease;
	-o-transition: all 0.5s ease;
	transition: all 0.5s ease;
}

#sidebar-wrapper {
	box-shadow: 0px -25px 15px #080808 inset;
	padding-left: 0px;
}

.sidebar-title .caption {
	padding: 15px 10px 10px 0px;
	color: #fff;
	font-size: 14px;
	line-height: 22px;
	width: 250px;
}

.caption-title {
	font-size: 13px;
	padding: 10px 10px 10px 12px;
	line-height: 0px;
	display: inline-block;
	position: absolute;
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
	color:#fff;
	/* border-right: 1px solid #616161; */
}

.sidebar-title .fa-angle-double-down:before {
	font-size: 19px;
	padding: 14px 18px 12px 18px;
	background: #484848;
	line-height: 0px;
}

.k-group, .k-flatcolorpicker.k-group, .k-menu, .k-menu .k-group,
	.k-popup.k-widget.k-context-menu {
	color: #fdfdfd;
	background-color: #ffffff;
}

.leftbar-clientlist {
	margin: 18px;
}

.leftbar-clientlist .treeview .k-treeview {
	overflow: hidden
}

/* Styles for Map play,pause,stop */
		
button[disabled=disabled], button:disabled {
	background-color: #dbdbdb;
	text-decoration: none;
	border: 1px solid #ccc;
	/*text-shadow: -1px -1px 0 rgba(0,0,0,0.3);*/
	text-align: center;
	color: rgb(56, 50, 50);
}

button{ 
	border: none;
    background-color: #205081;
    padding: 6px 10px;
}

button i{
	color:#ffffff;
	font-size:14px!important;
}	

button:disabled i{
	color:rgba(0, 0, 0, 0.41);
}

button[disabled=disabled]:hover {
	background-color: #dbdbdb;
	text-decoration: none;
	border: 1px solid #ccc;
	/*text-shadow: -1px -1px 0 rgba(0,0,0,0.3);*/
	text-align: center;
	color: rgb(56, 50, 50);
}

#vehicle-history-location-map-section span{
	position: absolute;
	margin-top: 9px;
	margin-left: 9px;
	z-index: 9999;
	padding: 7px;
	background: #fff;
	border-radius: 5px;
	opacity: 0.9; 
	text-align: center;
	box-shadow: 0 1px 5px rgba(0,0,0,0.4);
}
</style>

<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header" style="padding-bottom: 15px;">
		<h1>Tracking</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
 			<li><a href="#">Vehicle History</a></li>
		</ol>
	</section>

	<!-- Sidebar -->
	<div id="sidebar-wrapper">
		<div class="sidebar-title">
			<div class="caption" id="leftbar"
				style="padding: 6px 10px 6px 0px; color: #fff; font-size: 14px; line-height: 22px; border-top: 1px solid #616161; background-color: #484848; border-bottom: 1px solid #616161;">
				<a href="javascript:void(0);" id="menu-toggle-2"><i
					class="fa fa-angle-double-right" aria-hidden="true"></i></a>
				<div class="caption-title">Asset List</div>
				<span id="gapp-assetcount"
					style="float: right; padding: 0px 15px; background-color: #08cebb; border-radius: 4px; color: #000; font-weight: bold; font-size: 13px;"></span>
			</div>
		</div>

		<div class="sidebar-body">
			<div id="assetSection">
				<div class="leftbar-clientlist">
					<div class="treeview margintop10">
						<ul id="treeview">
						</ul>
						<!--<input id="treeSearchInput" class="search-asset" placeholder="Search Asset"></input>-->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<spring:url value="resources/js/equipment/vehicle/history.js"
		var="vehicleHistoryJS" />
	<script src="${vehicleHistoryJS}"></script>

	<!-- /#sidebar-wrapper -->
	<!-- Main content -->
	<section class="content">

		<div class="col-split-12">
			<c:if test="${not empty vehicleListResp}">
				<script type="text/javascript">
					var vehicleListResp = ${vehicleListResp};	
				</script>
			</c:if>
		</div>
		
		<div class="box-title fms-title">
			<h4 class="pull-left">
				<i class="pageheadericon pageheader-usericon"></i>Vehicle History
			</h4>

		</div>
		<!-- SELECT2 EXAMPLE -->

		<div class="box box-info" style="clear: both">
			<div class="box-header with-border hidden">
				<h4 class="box-title"></h4>

				<div class="box-tools pull-right ">
					<button type="button" class="btn btn-box-tool"
						data-widget="collapse">
						<i class="fa fa-minus"></i>
					</button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="col-md-12" >
						<form class="form-inline" style="padding: 10px 0px" >
								<div class="form-group" style="margin-left: 15px">
									<label style="padding-top: 6px;">Point</label> 
									<select id="vehicle-points" class="form-control" style="width: 160px;" onchange="FnChangePoints()">
										<option selected="selected">Choose a Point</option>
									</select>
									</div>
								<div class="form-group" style="margin-left: 15px">
									<label>Start Date</label> 
									<input type="date" class="form-control" name="startDate" id="startDate" />
								</div>
								<div class="form-group" style="margin-left: 15px">
									<label>End Date</label> 
									<input type="date" class="form-control" name="endDate" id="endDate" />
								</div>
								<button type="button" class="btn btn-primary"
									id="gapp-point-search" onclick="FnVehiclePointHistory();" style="margin-left: 15px">Search</button>
								<button type="button" id="btnExport" name="btnExport" class="btn btn-default pull-right"
									onclick="return ExportToCsvAsset();">Export to CSV</button>
						</form>


					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /.box-body -->
		</div>

		<div class="box" style="clear: both">
			<div class="box-body">
				<div class="row">
					<div class="col-md-12">
						<div id="vehicle-history-list" style="clear: both"></div>
					</div>
				</div>
			</div>
		</div>

		<div class="box" style="clear: both">
			<div class="box-body">
				<div class="row">
					<div class="col-md-12">
						<div id="vehicle-history-chart"></div>
					</div>
				</div>
				<section class="row" id="vehicle-history-location-map-section" style="padding-left:15px;padding-right: 15px;">
				<span style="">
					<button id="play" type="button" class="button" value="Play" onclick="fnPlay();">
						<i class="fa fa-play" aria-hidden="true"></i></button>&nbsp;
					<button id="pause" type="button" class="button" value="Pause"  onclick="fnPause();" disabled>
						<i class="fa fa-pause" aria-hidden="true"></i></button>&nbsp;
					<button id="stop" type="button" class="button" value="Stop"  onclick="fnStop();" disabled>
						<i class="fa fa-stop" aria-hidden="true"></i></button>
				</span>
				<div id="vehicle-history-location-map" style="height:515px"></div>
			</section>
			</div>
		</div>

	

	</section>
	
	
	<!-- /.content -->
</div>


<script>
  $(function () {

	$('body').addClass('sidebar-collapse');
		   TriggerClick = 0;
		   $(".content").css({"margin-left":"22%"});
		   
		   $("#sidebar-wrapper").animate({width:"40px"}, 200);
		   $(".content").animate({"margin-left":"3%"}, 800);
		    $(".caption-title").css({"transform":"translate(-86px, -105px) rotate(90deg) scale(1)","transition": "transform 0.2s","transform-origin": "left center","width": "115px","height": "203px","left":"10px","top":"35px"});
			
				$("#leftbar").click(function(){
						if(TriggerClick==0){
							TriggerClick=1;
							$("#sidebar-wrapper").animate({width:"250px"}, 200);
							$(".caption-title").css({"transform":"rotate(0deg) scale(1)","transition": "all 0.2s","height": "0px","left":"48px","top":"8px","transform-origin": "left left"});
							//$(".left_container_content").hide();
							//$(".left_container_content").hide();
							$(".content").animate({"margin-left":"21%"}, 500);
							//$(".home_btn").hide();
						} else {
							TriggerClick=0;							
							$("#sidebar-wrapper").animate({width:"40px"}, 200);
							 $(".caption-title").css({"transform":"translate(-86px, -105px) rotate(90deg) scale(1)","transition": "transform 0.2s","transform-origin": "left center","width": "115px","height": "203px","left":"10px","top":"35px"});
							 //$(".left_container_content").show();
							// $(".home_btn").show('slow');	
							$(".content").animate({"margin-left":"3%"}, 800);
							
						}
					});
				});
</script>

<script src="http://kendo.cdn.telerik.com/2016.2.607/js/jszip.min.js"></script>

<script type="text/javascript">
	var VehiclePointsURL = "${VehiclePointsURL}";
	var VehiclePointHistoryURL = "${VehiclePointHistoryURL}";
</script>

<script type='text/javascript' src="resources/plugins/jquery.form.js"></script>
<script type='text/javascript' src="resources/js/mask.js"></script>
<script type='text/javascript' src="resources/plugins/leaflet/beta/leaflet-src.js"></script>
<script type='text/javascript' src="resources/plugins/leaflet/L.Polyline.SnakeAnim.Customized.js"></script>
