<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<spring:eval var="getSaveRoleUrl"
	expression="@propertyConfigurer.getProperty('fms.services.createRole')" />
<spring:eval var="VarViewRoleUrl"
	expression="@propertyConfigurer.getProperty('fms.services.findRole')" />	
	
	
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

a.remove_button {
	position: absolute;
	top: 9px;
	right: 26px;
}
a.add_button {
   margin-left:12px;
    position: absolute;
    top: 15px;
    z-index: 100;
    left: 30px;
}

.formelement_addition {
	margin: 6px 0;
}
.resourceTitle {
    text-transform: capitalize;
    font-size: 14px;
}
.splitpermissionDIV {
    width: 246px;
    float: left;
    display: inline-block;
    margin: 10px 15px;
}
.permission-wrapper {
    display: inline-block;
    float: left;
    width: 100%;
}
.pwrapper {
    width: 100%;
    overflow-x: hidden;
    overflow-y: auto;
    height: 530px;
    /* white-space: nowrap; */
    padding: 12px 0;
    border-top: 1px solid #cfcfcf !important;
    /* overflow: auto; */
}
.md-checkbox label {
    display: inline-block;
    max-width: 100%;
    margin-bottom: 5px;
    /* font-weight: 700; */
    padding: 0 0 0 6px !important;
    text-transform: capitalize;
    letter-spacing: 1px;
    font-size: 14px;
}
</style>


<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Role Management </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/FMS/role_home">Role</a></li>
        <li class="active pageTitleTxt"> Create</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="box-title fms-title">
      			 <h4 class="pull-left pageTitleTxt">Create Role</h4>
      			<div class="fms-btngroup text-right">
					<ul class="btnanimation">
					
					<li class=" form-action-cancel ripplelink"><i class="fa fa-times" aria-hidden="true"></i>
                                <button href="javascript:void(0)"  id="gapp-user-cancel" onclick="FnCancelRole()"><i>Cancel</i></button>
                              </li>
                                
						
						<li class=" delete form-action-edit ripplelink" id="role-edit-listitem">
						 <c:if test="<%=hasPermissionAccess(\"role_management\",\"edit\")%>">
						<i class="fa fa-pencil-square-o" aria-hidden="true"></i><button href="javascript:void(0)"  id="gapp-role-edit" onclick="return FnEditRole()"><i>Edit</i></button></li>
						 </c:if>
						 
						  <c:if test="<%=hasPermissionAccess(\"role_management\",new String[]{\"create\",\"edit\"})%>">
						<li class="primary delete form-action-save ripplelink"><i class="fa fa-check" aria-hidden="true"></i><button href="#" id="gapp-role-save" onclick="return FnSaveRole()"><i>Save</i></button></li>
					 </c:if>
					</ul>
      			
      		  </div>


      		</div>
      <!-- SELECT2 EXAMPLE -->
      <div class="box box-info">
        <div class="box-header with-border hidden">
          <h4 class="box-title"></h4>
          <div class="box-tools pull-right hidden">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
		<form id="gapp-addrole-form" role="form" action="<%=request.getContextPath()%>/role_view" name="gapp-addrole-form" method="get">
								
          <div class="row">
            <div class="col-md-4">
				<!-- BEGIN PROFILE IMAGE UPLOAD -->
				 <div class="form-group  ">
                    <label for="roleName">Role Name <span class="required" aria-required="true">* </span></label>
                    <input type="text" name="roleName" class="form-control" id="roleName" tabindex="1" required=""   onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)" aria-invalid="true" placeholder="Enter role name">
                  <!--  <span class="help-block">User name not specified</span>-->
                  </div>
				<!-- END PROFILE IMAGE UPLOAD -->
				
				   <div class="form-group">
                  <label for="Description">Descriptions</label>
                  	<textarea class="form-control" name="Description" id="Description" rows="3" style="border: 1px solid #e5e5e5;" tabindex="2" placeholder="Enter role Description"></textarea>
                </div>
			</div>
              <div class="col-md-8">
               <div class="form-group">
                  <label>Permissions</label>
                 <div class="pwrapper well" style="display:block" id="permissionsContainer"></div>
                </div>

              </div>
               <div class="clearfix"></div>
               
			
  
			
          </div>
		  </form>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>
<form id="gapp-view-role-form" role="form" action="<%=request.getContextPath()%>/role_home" name="gapp-view-role-form" method="get">
</form>


    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<spring:url value="resources/js/roles/add.js" var="roleViewJS" />
<script src="${roleViewJS}"></script>

<script type="text/javascript">
	var getSaveRoleUrl = "${getSaveRoleUrl}";
	var VarViewRoleUrl = "${VarViewRoleUrl}";

	var domainName = "<%=session.getAttribute("domainName")%>";
	var VarEditRoleId = "<%=request.getParameter("role_id")%>";
	if(VarEditRoleId !== "null"){
		FnGetRoleDetails(VarEditRoleId);
	} else {
		FnGetResourcesList();
	}
</script>
	
	
	
	


