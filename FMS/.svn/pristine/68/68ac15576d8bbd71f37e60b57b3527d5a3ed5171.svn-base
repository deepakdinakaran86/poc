<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>
<spring:eval var="getListUserUrl"
	expression="@propertyConfigurer.getProperty('fms.services.listUser')" />
<style>
a.grid-viewuser{
 padding: 0 0 0 5px;
    cursor: pointer;
    text-transform:capitalize
}
</style>
<script type="text/javascript">		
</script>

<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>   Users Management   </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>       
        <li class="active"> User</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="box-title fms-title">
      			 <h4 class="pull-left">User</h4>
      			 <div class="fms-btngroup text-right">
      			  <c:if test="<%=hasPermissionAccess(\"user_management\",\"delete\")%>">
      			  	<button type="button" class="btn btn-default form-action-delete  ripplelink" id="gapp-user-delete" onclick="FnDeleteUser()" disabled=""> Delete</button>
      				 </c:if>
      				 <c:if test="<%=hasPermissionAccess(\"user_management\",\"create\")%>">
      				  <button type="button" class="btn btn-primary  ripplelink"  onclick="FnCreateUser()"> Create</button>
      				   </c:if>
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
          <div class="row">
            <div class="col-md-12">  <div id="gapp-users-list"> </div></div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>



    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <form id="gapp-user-create" role="form" action="<%=request.getContextPath()%>/user_form" name="gapp-user-create" method="post">
									</form>
  <form id="gapp-user-delete-form" role="form" action="<%=request.getContextPath()%>/user_delete" name="gapp-user-delete-form" method="post">
		<input type="hidden" id="delete_user_id" name="delete_user_id" />
  </form>				
  <form id="gapp-user-view" role="form" action="<%=request.getContextPath()%>/user_form" name="gapp-user-view" method="post">
										<input type="hidden" id="user_id" name="user_id" />
   </form>
	<script id="delete-confirmation" type="text/x-kendo-template">
		<p class="delete-message">Are you sure want to delete the user?</p>
		<button class="delete-confirm k-button" style="width: 80px;">Yes</button>
		<button class="delete-cancel k-button" style="width: 80px;">No</a>
	</script>
									
  <spring:url value="resources/js/users/list.js" var="clientCreateJS"/><script src="${clientCreateJS}"></script>
  
  <script type="text/javascript">
	var getListUserUrl = "${getListUserUrl}";
	var domainName = "<%=session.getAttribute("domainName")%>";
  </script>