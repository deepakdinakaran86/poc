<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasMenuAccess"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasAnyMenuAccess"%>
<!-- Left side column. contains the logo and sidebar -->
<style>
/*.sidebar-menu .treeview-menu > li > a > .fa-angle-left, .sidebar-menu .treeview-menu > li > a > .fa-angle-down{width:20px!important}*/
.treeview span,.treeview a{font-size:13px!important}
.treeview i{font-size:14px}
</style>
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->

		<!-- search form -->


		<ul class="sidebar-menu">
		<c:if test="<%=hasAnyMenuAccess(new String[]{\"client_management\",\"user_management\",\"role_management\",\"devices\",\"audit_logs\"})%>">
			<li class="treeview"><a href="#"> <i
					class="fa fa-user" aria-hidden="true"></i> <span> Admin</span> <i class="fa fa-angle-left pull-right"></i></a>
				<ul class="treeview-menu">
					<c:if test="<%=hasMenuAccess(\"client_management\")%>">
						<li><a href="/FMS/tenant_home"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Client Management</a></li>
					</c:if>
					<c:if test="<%=hasAnyMenuAccess(new String[]{\"user_management\",\"role_management\"})%>">
						<li><a href="#"><i class="fa fa-angle-double-right" aria-hidden="true"></i> User Management<i class="fa fa-angle-left pull-right"></i></a><ul class="treeview-menu">
							<c:if test="<%=hasMenuAccess(\"user_management\")%>">
								<li class="treeview"><a href="/FMS/user_home"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Users</a></li>
							</c:if>
							<c:if test="<%=hasMenuAccess(\"role_management\")%>">
								<li class="treeview"><a href="/FMS/role_home"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Roles</a></li>
							</c:if>
						</ul></li>
					</c:if>
					<c:if test="<%=hasMenuAccess(\"devices\")%>">
						<li class="treeview"><a href="/FMS/device_home"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Device Management </a></li></li>
					</c:if>
					<c:if test="<%=hasMenuAccess(\"audit_logs\")%>">
						<li><a href="/FMS/auditlog"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Audit Logs</a></li>
					</c:if>
				</ul></li>
			</c:if>
			
			<c:if test="<%=hasAnyMenuAccess(new String[]{\"vehicle_types\",\"vehicle_management\",\"poi_types\",\"poi_management\",\"tag_type\",\"tag\",\"geofence\"})%>">
				<li class="treeview "><a href="#"> <i class="fa fa-cog"	aria-hidden="true"></i> <span> Configuration</span><i class="fa fa-angle-left pull-right"></i></a>
					<ul class="treeview-menu">
					<c:if test="<%=hasAnyMenuAccess(new String[]{\"vehicle_types\",\"vehicle_management\"})%>">
						<li><a href="#"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Vehicle Configuration<i class="fa fa-angle-left pull-right"></i></a>
							<ul class="treeview-menu">
								<c:if test="<%=hasMenuAccess(\"vehicle_types\")%>">
									<li class="treeview"><a href="/FMS/vehicle_type_home"> <i class="fa fa-angle-double-right" aria-hidden="true"></i>Vehicle Type </a></li>
								</c:if>
								<c:if test="<%=hasMenuAccess(\"vehicle_management\")%>">
									<li class="treeview"><a href="/FMS/vehicle_list"> <i class="fa fa-angle-double-right" aria-hidden="true"></i> Vehicle Onboarding</a></li>
								</c:if>
							</ul></li>
					</c:if>
					<c:if test="<%=hasAnyMenuAccess(new String[]{\"poi_types\",\"poi_management\"})%>">
						<li><a href="#"><i class="fa fa-angle-double-right" aria-hidden="true"></i> POI Configuration<i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<c:if test="<%=hasMenuAccess(\"poi_types\")%>">
								<li class="treeview"><a href="poi_type_home"> <i class="fa fa-angle-double-right" aria-hidden="true"></i>POI Type </a></li>
							</c:if>
							<c:if test="<%=hasMenuAccess(\"poi_management\")%>">
								<li class="treeview"><a href="poi_def_home"> <i class="fa fa-angle-double-right" aria-hidden="true"></i>POI Definition </a></li>
							</c:if>
						</ul></li>
					</c:if>
					<c:if test="<%=hasAnyMenuAccess(new String[]{\"tag_type\",\"tag\"})%>">
						<li><a href="#"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Tag Configuration<i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<c:if test="<%=hasMenuAccess(\"tag_type\")%>">
								<li class="treeview"><a href="/FMS/tagtype_home"><i class="fa fa-angle-double-right" aria-hidden="true"></i>Tag Type</a></li>
							</c:if>
							<c:if test="<%=hasMenuAccess(\"tag\")%>">
								<li class="treeview"><a href="/FMS/tag_home"> <i class="fa fa-angle-double-right" aria-hidden="true"></i>Tag Definition </a></li>
							</c:if>
						</ul></li>
					</c:if>
					<c:if test="<%=hasMenuAccess(\"geofence\")%>">
						<li><a href="#"><i class="fa fa-angle-double-right" aria-hidden="true"></i> Geofence Configuration<i class="fa fa-angle-left pull-right"></i></a>
						<ul class="treeview-menu">
							<li class="treeview"><a href="/FMS/geofence_list"> <i class="fa fa-angle-double-right" aria-hidden="true"></i>Geofence</a></li>
						</ul></li>
					</c:if>
				</ul></li>
			</c:if>

			<c:if test="<%=hasAnyMenuAccess(new String[]{\"live_tracking\",\"vehicle_details\",\"vehicle_history\"})%>">
				<li class="treeview ">
				<a href="#"> <i class="fa fa-location-arrow" aria-hidden="true"></i> <span>	Tracking</span><i class="fa fa-angle-left pull-right"></i></a>
					<ul class="treeview-menu active">
						<c:if test="<%=hasMenuAccess(\"live_tracking\")%>">
							<li><a href="/FMS/vehicle_tracking"><i class="fa fa-angle-double-right"></i> Live Tracking</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"vehicle_details\")%>">
							<li><a href="/FMS/vehicle_details"><i class="fa fa-angle-double-right"></i> Vehicle Details</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"vehicle_history\")%>">
							<li><a href="../FMS/vehicle_history"><i class="fa fa-angle-double-right"></i> Vehicle History</a></li>
						</c:if>
					</ul>
				</li>
			</c:if>
			
			<c:if test="<%=hasAnyMenuAccess(new String[]{\"alarm_definition\",\"alarm_console\",\"alarm_logs\"})%>">
				<li class="treeview">
				<a href="#"> <i class="fa fa-bell" aria-hidden="true"></i> <span> Alert Management</span><i class="fa fa-angle-left pull-right"></i> </a>
					<ul class="treeview-menu">
						<c:if test="<%=hasMenuAccess(\"alarm_definition\")%>">
							<li><a href="alert_definition"><i class="fa fa-angle-double-right"></i> Alert Definition</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"alarm_console\")%>">
							<li><a href="alert_console"><i class="fa fa-angle-double-right"></i> Alert Console</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"alarm_logs\")%>">
							<li><a href="alert_logs"><i class="fa fa-angle-double-right"></i> Alert logs</a></li>
						</c:if>
					</ul></li>
			</c:if>

			<c:if test="<%=hasAnyMenuAccess(new String[]{\"service_items\",\"service_components\",\"service_scheduling\"})%>">			
				<li class="treeview"><a href="#"> <i
						class="fa  fa-wrench" aria-hidden="true"></i> <span> Service Maintenance</span><i class="fa fa-angle-left pull-right"></i> </a>
					<ul class="treeview-menu">
						<c:if test="<%=hasMenuAccess(\"service_items\")%>">
							<li><a href="/FMS/item_list"><i class="fa fa-angle-double-right"></i> Service Items</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"service_components\")%>">
							<li><a href="/FMS/component_list"><i class="fa fa-angle-double-right"></i> Service Components</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"service_scheduling\")%>">
							<li><a href="/FMS/schedule_list"><i class="fa fa-angle-double-right"></i> Service Scheduling</a></li>
						</c:if>
						<!--<c:if test="<%=hasMenuAccess(\"geofence\")%>">
							<li><a href="#"><i class="fa fa-circle-o"></i> Assign & Notify </a></li>
						</c:if> -->
					</ul></li>
			</c:if>
			
			<c:if test="<%=hasAnyMenuAccess(new String[]{\"routing\",\"route_scheduling\"})%>">
				<li class="treeview"><a href="#"> <i
						class="fa fa-street-view" aria-hidden="true"></i> <span> Routing & Scheduling</span><i class="fa fa-angle-left pull-right"></i> </a>
					<ul class="treeview-menu">
						<c:if test="<%=hasMenuAccess(\"routing\")%>">
							<li><a href="/FMS/route_home"><i class="fa fa-angle-double-right"></i> Routes</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"route_scheduling\")%>">
							<li><a href="#"><i class="fa fa-angle-double-right"></i> Route Scheduling</a></li>
						</c:if>
						<!--<c:if test="<%=hasMenuAccess(\"geofence\")%>">
							<li><a href="#"><i class="fa fa-circle-o"></i> Rostering</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"geofence\")%>">
							<li><a href="#"><i class="fa fa-circle-o"></i> Assign & Notify </a></li>
						</c:if>-->
					</ul></li>
			</c:if>
			
			<!--<c:if test="<%=hasAnyMenuAccess(new String[]{\"config_template\",\"asset_types\",\"asset_management\",\"poi_types\"})%>">
				<li class="treeview"><a href="#"> <i class="fa fa-file-text"
						aria-hidden="true"></i> <span> Reporting</span> </a>
					<ul class="treeview-menu">
						<c:if test="<%=hasMenuAccess(\"geofence\")%>">
							<li><a href="../repots/vehicle.html"><i class="fa fa-circle-o"></i> Vehicle Reports</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"geofence\")%>">
							<li><a href="../repots/driver.html"><i class="fa fa-circle-o"></i> Driver Reports</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"geofence\")%>">
							<li><a href="../repots/alert.html"><i class="fa fa-circle-o"></i> Alert reports</a></li>
						</c:if>
						<c:if test="<%=hasMenuAccess(\"geofence\")%>">
							<li><a href="../repots/trip.html"><i class="fa fa-circle-o"></i> Trip Reports</a></li>
						</c:if>
					</ul></li>
			</c:if> -->
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>
<script type="text/javascript">
var userInfo= $.parseJSON('<%out.print(FMSAccessManager.userInfotoUI());%>');
UserInfo.init(userInfo);
</script>
