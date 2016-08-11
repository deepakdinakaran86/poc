<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>
<style>
div#geofence-actions {
	text-align: center;
}

.md-radio-inline .md-radio {
	display: inline-block;
	margin-right: 20px;
}

.btn.btn-xs {
	font-size: 11px;
	padding: 3px 8px 3px 8px;
}

#geofence-page #geofence-actions {
	margin: 0;
	text-align: right;
	display: block;
}

.geofence-actions-pane {
	min-height: 35px;
	/* padding: 10px; */
	margin-bottom: 20px;
	background-color: #051b28;
	border: 1px solid #636f74;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
	height: auto;
	display: inline-block;
	width: 100%;
}

.create-route-grid {
	border: 1px solid #aaaaaa;
	height: 200px;
	margin: 10px 0px;
}

.route-box {
	height: 160px;
	background-color: #333333;
	margin-top: 34px;
}

#right-bar-control {
	overflow: visible !important;
}

.form-horizontal .control-label {
	/*color: #d3cdcd !important;*/
	font-weight: 100;
	color:#fff !important;
	font-size:13px;
}

.create-route-grid {
	padding: 0;
}

#kms {
	position: absolute;
    top: 0;
    color: #baa7a9;
    top: -21px;
    left: 25%;
    font-size: 12px;
}

#kms span {
	color: #fff
}

#mins {
	position: absolute; 
	top : 0;	
	color: #baa7a9;
	top: 7px;
	left: 25%;
    font-size: 12px;
}

#mins span {
	color: #fff
}

h1.page-title {
    font-weight: 400;
    font-size: 13px;
    padding: 6px 0;
    margin-bottom: 6px;
    color: #FFF !important;
}
#layout-wrapper{
	position: relative;
    display: block;
    /* margin: 22px 0; */
    /* background: red; */
    width: 100%;
    height: 130px;

}
.md-radio-inline {
    padding: 7px 0 0 0;
}
.form-horizontal .control-label {
  
    text-align: left !important;
}
.form-actions.geofence-actions-pane.wall-blue {
    margin-top: 12px;
}

#geo-panel-menu {
    list-style: none;
}

#tab_Circle{
color:#000;
}

section.rightbar-geofence {
    overflow-y: scroll;
    height: 700px;
    
}
section.rightbar-routing{
overflow-y: inherit;
    height: 700px;
}

#gapp-route-form .form-group {  margin: 0;}
.portlet {
    margin-top: 0px;
    margin-bottom: 25px;
    padding: 0px;
    -webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    -ms-border-radius: 2px;
    -o-border-radius: 2px;
    border-radius: 2px;
}

    
    
 .portlet {
     box-shadow: 0px 2px 5px 2px rgba(0, 0, 0, 0.1);
    /* border: 0 !important; */
    padding: 10px;
    border-top: 5px solid #00c0ef;
}
label.col-md-5.control-label {
    color: #000 !important;
}

#layout-wrapper label.col-md-5.control-label {
    color: #fff !important;
}
#geofence-style-settings  label.col-md-5.control-label {
    color: #FFF !important;
}
.tabbable-custom > .nav-tabs > li.active {
    /*border-top: 3px solid #F3565D;
    border-bottom: 3px solid #00c0ef;*/
    margin-top: 0;
    position: relative;
}
</style>
<!-- Control Sidebar -->

<aside class="control-sidebar control-sidebar-dark" id="right-bar-control" >

	<!-- Create the tabs -->

	<!-- Tab panes -->
	<ul id="geo-panel-menu">
		<li><a href="#" data-toggle="control-sidebar" id="right-bar-icon">
			<i class="fa fa-gears"> </i></a>
		</li>
	</ul>
	<!-- Stats tab content -->
	<div class="tab-pane" id="control-sidebar-stats-tab"></div>
	<!-- /.tab-pane -->
	<!-- Settings tab content -->
	<div class="tab-pane" id="control-sidebar-settings-tab"	style="padding: 0 5px 0 8px;">
		<section class="rightbar-geofence">
			<h4 class="control-sidebar-heading">Layout Options</h4>
			<div class="form-group">
			<form id="gapp-geofence-form-cancel" role="form" action="geofence_cancel" name="gapp-geofence-form-cancel" method="get"></form>
				<form class="form-horizontal" name="gapp-geofence-form"
					id="gapp-geofence-form" method="post" role="form"
					style="color: #42454A;" data-role="validator"
					novalidate="novalidate">
					<div class="form-actions geofence-actions-pane"
						id="geofence-actions">

							<c:if test="<%=hasPermissionAccess(\"geofence\",\"edit\")%>">
								<button type="button" class="btn btn-xs   form-action-edit ripplelink"
									name="gapp-geofence-edit" id="gapp-geofence-edit"
									onclick="FnEditGeofence()">
									<i class="icon-note icons"></i>Edit
								</button>
							</c:if>

						<button type="button" class="btn btn-xs t  form-action-delete ripplelink"
							name="gapp-geofence-clear" id="gapp-geofence-clear"
							onclick="FnClearGeofence()">
							<i class="icon-close icons" aria-hidden="true"></i>Clear map
							<div class="ripple-container"></div>
						</button>
						<button type="button" class="btn btn-xs default ripplelink"
							name="gapp-geofence-cancel" id="gapp-geofence-cancel"
							onclick="FnCancelGeofence()" style="color:#000">
							<i class="icon-close icons" aria-hidden="true"></i>Cancel
						</button>
					<c:if test="<%=hasPermissionAccess(\"geofence\",new String[]{\"create\",\"edit\"})%>">
						<button type="button" class="btn  btn-xs  form-action-save ripplelink"
							name="gapp-geofence-save" id="gapp-geofence-save"
							onclick="return FnSaveGeofence()">
							<i class="icon-check icons" aria-hidden="true"></i>Save
						</button>
					</c:if>
					</div>
					<div class="clearfix margin-bottom-10"></div>
					<div id="layout-wrapper">
					<div class="form-group0">
						<label class="col-md-5 control-label">Geofence Name <span
							class="required" aria-required="true">* </span></label>
							
						<div class="col-md-7">
							<input type="text" name="geofenceName" id="geofenceName"
								class="form-control input-sm k-valid"
								placeholder="Enter geofence Name" required=""
								data-required-msg="Specify geofence name" data-available=""
								data-available-url="/markers/1.0.0/markers/validate"
								data-available-msg="Geofence name already exists"> <input
								type="hidden" id="fenceName" name="fenceName" value="">
							<span class="help-block"></span>
						</div>
						<span class="material-input"></span>
					</div>
					<div class="form-group0">
						<label class="col-md-5 control-label text-left">Zoom Level </label>
						<div class="col-md-7">
							<input type="number" class="form-control input-sm "
								name="zoom" id="zoom" min="0" max="18" step="1" value="8">
							<span class="help-block"></span>
						</div>
						<span class="material-input"></span>
					</div>
					<div class="form-group0">
						<label class="col-md-5 control-label" for="form_control_1">Status</label>
						<div class="col-md-7">
							<div class="md-radio-inline">
								<div class="md-radio has-error">
									<input type="radio" id="statusName_active"
										name="geofenceStatus" class="md-radiobtn minimal" checked=""
										value="ACTIVE"> 
										<label for="statusName_active"  class="text-green">	Active	</label>
								</div>
								<div class="md-radio has-warning">
									<input type="radio" id="statusName_inactive" name="geofenceStatus"
										class="md-radiobtn minimal-red" value="INACTIVE"> 
										<label	for="statusName_inactive"  class="text-red">In Active 	</label>
								</div>
							</div>
						</div>
					</div>
				</div><!-- layout-wrapper -->
					<div class="form-actions geofence-actions-pane wall-blue"
						style="width: 100%; padding: 10px 10px 0 10px; margin-bottom: 1px;">

						<div class="tabbable-custom nav-justified ">
							<ul class="nav nav-tabs" style="margin: 0;" id="geotypeHead">
								<li class="active"><a href="#tab_Polygon" data-toggle="tab"
									aria-expanded="true" onclick="FnDefineGeofenceType('Polygon')">
										Polygon
										<div class="ripple-container"></div>
								</a></li>
								<li class=""><a href="#tab_Circle" data-toggle="tab"
									aria-expanded="false" onclick="FnDefineGeofenceType('Circle')">
										Circle
										<div class="ripple-container"></div>
								</a></li>
								<li class=""><a href="#tab_Route" data-toggle="tab"
									aria-expanded="false" onclick="FnDefineGeofenceType('Route')">
										Road Map
										<div class="ripple-container"></div>
								</a></li>
							</ul>
						</div>
						<div class="tab-content">
							<div class="tab-pane fade active in" id="tab_Polygon">
								<div class="portlet box">
									<div class="portlet-title hidden">
										<div class="caption">
											<i class="fa fa-cogs"></i>Draw Polygon
										</div>
										<div class="tools"></div>
									</div>
									<div class="portlet-body" style="min-height: 220px">
										<div class="form-body">
											<div id="draw-polygon-grid"></div>

										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane fade" id="tab_Circle">
								<div class="portlet box ">
									<div class="portlet-title hidden">
										<div class="caption">
											<i class="fa fa-cogs"></i>Draw Circle
										</div>
										<div class="tools"></div>
									</div>
									<div class="portlet-body" style="min-height: 220px">
										<div class="form-body" style="padding:6px;">
											<div class="form-group is-empty">
												<label class="col-md-5 control-label">Latitude <span
													class="required" aria-required="true">* </span></label>
												<div class="col-md-7">
													<input type="text" class="form-control input-small"
														name="latitude" id="latitude" placeholder="Enter Latitude"
														readonly=""> <span class="help-block"></span>
												</div>
												<span class="material-input"></span>
											</div>

											<div class="form-group is-empty">
												<label class="col-md-5 control-label">Longitude <span
													class="required" aria-required="true">* </span></label>
												<div class="col-md-7">
													<input type="text" class="form-control input-small"
														name="longitude" id="longitude"
														placeholder="Enter Longitude" readonly=""> <span
														class="help-block"></span>
												</div>
												<span class="material-input"></span>
											</div>

											<div class="form-group is-empty">
												<label class="col-md-5 control-label">Radius(km)<span
													class="required" aria-required="true">* </span>
												</label>
												<div class="col-md-7">
													<input type="text" class="form-control input-small"
														name="radius" id="radius" placeholder="Enter Radius"
														readonly=""> <span class="help-block"></span>
												</div>
												<span class="material-input"></span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane fade" id="tab_Route">
								<div class="portlet box">
									<div class="portlet-body" style="min-height: 220px">
										<div class="form-body">
											<div class="form-group">
												<label class="col-md-5 control-label">Sideways(m) <span
													class="required" aria-required="true">* </span></label>
												<div class="col-md-7">
													<input type="number" class="form-control input-small"
														name="geofence_road_width" id="geofence_road_width"
														min="10" max="100" step="10" value="20"> <span
														class="help-block"></span>
												</div>
												<span class="material-input"></span>
											</div>

											<div id="draw-roadmap-grid"></div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--form-actions geofence-actions-pane-->


					<h1 class="page-title">	Style setttings</h1>
					<div class="well wall-blue-dark geofence-actions-pane" id="geofence-style-settings"
						style="color: #555555;">
						<div class="form-group clearfix is-empty">
							<label class="col-md-5 control-label">Border Color</label>
							<div class="col-md-7">
								<input type="text" class="form-control input-small" name="geofence_border_color" id="geofence_border_color" />
														<span class="help-block"></span>
							</div>
							<span class="material-input"></span>
						</div>
						<div class="form-group clearfix">
							<label class="col-md-5 control-label">Border Thickness</label>
							<div class="col-md-7">
								<input type="number" class="form-control input-small"
									name="geofence_border_weight" id="geofence_border_weight"
									min="1" max="10" step="1" value="3"
									validationmessage="Border Thickness should be greater than or equal to 1">
							</div>
							<span class="material-input"></span>
						</div>
						<div class="form-group clearfix">
							<label class="col-md-5 control-label">Border Alpha</label>
							<div class="col-md-7">
								<input type="number" class="form-control input-small"
									name="geofence_border_opacity" id="geofence_border_opacity"
									min="0" max="1" step="0.1" value="1"
									validationmessage="Border Alpha should be smaller than or equal to 1">
								<span class="help-block"></span>
							</div>
							<span class="material-input"></span>
						</div>
						<div class="form-group is-empty">
							<label class="col-md-5 control-label">Fill Color</label>
							<div class="col-md-7">
									<input type="text" class="form-control input-small" name="geofence_fill_color" id="geofence_fill_color" />
														<span class="help-block"></span>
							</div>
							<span class="material-input"></span>
						</div>

						<div class="form-group">
							<label class="col-md-5 control-label">Fill Alpha</label>
							<div class="col-md-7">
								<input type="number" class="form-control input-small"
									name="geofence_fill_opacity" id="geofence_fill_opacity" min="0"
									max="1" step="0.1" value="0.2"
									validationmessage="Fill Alpha should be smaller than or equal to 1">
								<span class="help-block"></span>
							</div>
							<span class="material-input"></span>
						</div>
					</div>
					<input type="hidden" id="geofenceFields" name="geofenceFields"
						value=""> <input type="hidden" id="geofenceType"
						name="geofenceType" value=""> <input type="hidden"
						id="geofence_mode" name="geofence_mode" value="" /> <input
						type="hidden" id="geofence_id" name="geofence_id" />
				</form>
			</div>
		</section>
		
		
		<!-- - Routing part starts here -->
		<section class="rightbar-routing" style="min-height: 700px">
			<h5 class="">Create Route</h5>
			<div class="form-group">
				<form class="form-horizontal" name="gapp-route-form"
					id="gapp-route-form" method="post" style="color: #42454A;"
					data-role="validator" novalidate="novalidate">
					<div class="form-group">
						<label class="col-md-5 control-label" style=" color:#fff !important">Route Name <span
							class="required" aria-required="true">* </span></label>
						<div class="col-md-7">
							<input type="text" name="routeName" id="routeName"
								class="form-control input-sm"
								placeholder="Enter Route name" required="true"
								data-required-msg="Route Name not specified" data-available=""
								data-available-url="/markers/1.0.0/markers/validate"
								data-available-msg="Route name already exists"> <span
								class="help-block"></span>
						</div>
						<span class="material-input"></span>
					</div>

					<div class="form-group ">
						<label class="col-md-5 control-label" style=" color:#fff !important">Description <span
							class="required" aria-required="true"></span></label>
						<div class="col-md-7">
							<input type="text" name="description" id="description"
								class="form-control input-sm"
								placeholder="Enter Route Description" required=""
								data-required-msg="Route Description not specified"> <span
								class="help-block"></span>
						</div>
						<span class="material-input"></span>
					</div>
					
					

					<div class="col-md-12 create-route-grid " >
<!-- 						<div id="draw-route-grid" class="hidden"></div> -->
						<div id="draw-route-grid" class=""></div>
					</div>
					<div class="col-md-12 " style="text-align: right; padding: 0; margin:132px 0 0 0 ">

						<button type="button" id="drawRoute" onclick="fnDrawRoute()" class="btn btn-info rippleLink">Draw

						<button type="button" onclick="fnEditRoute()" class="btn  form-action-edit rippleLink" style="box-shadow:none" disabled>Edit
							Route</button>

							Route</button>
						
					</div>
					<div class="col-md-12 route-box create-route-grid">
						<div class="draw-distance-info">
							<section class="route-routechartheader">
								<div class="route-note-distance"></div>
								<aside class="route-circlea" title="Start point"></aside>
								<aside class="route-line">
									<div id="kms">
										<span></span> 
									</div>
									<div id="mins">
										<span></span> 
									</div>
								</aside>
								<aside class="route-circleb" title="End point"></aside>
								<div class="route-note-time"></div>
							</section>
							<p
								style="clear: both; text-align: center; padding-top: 10px; font-size:13px;color: #fff">Total
								Distance/Duration</p>
						</div>
					</div>
					<div class="fms-btngroup text-right">
						<button type="button" id="saveRoute" onclick="fnSaveRoute()"
							class="btn btn-info rippleLink" style="box-shadow:none" >Save Route</button>
					</div>
					<input type="hidden" name="routeDetails" id="routeDetails">
				</form>
			</div>
		</section>
	</div>
	<!-- /.tab-pane -->