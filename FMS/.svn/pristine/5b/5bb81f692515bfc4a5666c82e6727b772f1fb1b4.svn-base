<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="static  com.pcs.fms.web.client.FMSAccessManager.getCurrentTenantName"%>
<%@ page import="static  com.pcs.fms.web.client.FMSAccessManager.getUserName"%>
<%@ page import="static  com.pcs.fms.web.client.FMSAccessManager.getTenantImage"%>
<%@ page import="static  com.pcs.fms.web.client.FMSAccessManager.getUserImage"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.getIsSubClientSelected"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.dropdown-menu{
width: 210px!important;
}
.navbar-nav > .user-menu > .dropdown-menu > li.user-header{
    height: 155px;
}
.user-footer{
    padding: 5px!important;
}
.home-border{
    border-right: 1px solid #083a56;
    border-left: 1px solid #083a56;
    }

</style>
<header class="main-header">
    <!-- Logo -->
    <a href="/FMS/current_home" class="logo ripplelink">
      <!-- mini logo for sidebar mini 50x50 pixels -->
    <span class="logo-mini"><img src="<%=getTenantImage()%>" class="img-responsive " style="padding:3px;" alt="" /></span>
      <!-- logo for regular state and mobile devices -->
     <span class="logo-lg capitalize" ><img src="<%=getTenantImage()%>" style="width:60px;padding-right:10px;" class="tenant-image"><b><%=getCurrentTenantName()%></b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="ripplelink sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu ">
      
        <ul class="nav navbar-nav">
        	<c:if test="<%=getIsSubClientSelected()%>">
          		<li class="home-border" data-toggle="tooltip" data-placement="bottom" title="My Home"><a href="/FMS/myown_home" class="ripplelink"><i class="fa fa-home" aria-hidden="true"></i></a></li>
          	</c:if>
		  <li class="dropdown user user-menu">
            <a href="#" class="ripplelink dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              <img src="<%=getUserImage()%>" class="user-image" alt="">  
              <span class="hidden-xs capitalize"><%=getUserName()%></span>
            </a>
            <ul class="dropdown-menu" style="box-shadow:1px 1px 8px #bbbaba;">
              <!-- User image -->
              <li class="user-header">
                 <img src="<%=getUserImage()%>" class="img-circle" alt="">  
                <p class="capitalize"><%=getUserName()%> </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="logout" class="btn btn-flat"><i class="fa fa-sign-out" aria-hidden="true"></i> Sign out</a>
                </div>
              </li>
            </ul>
          </li>   
         <!--  <li><a href="/FMS/current_home"> Current Home</a></li>
          <li> <a href="logout" class="btn btn-flat">Logout</a></li> -->
          <!-- Control Sidebar Toggle Button -->
        </ul>
      </div>
    </nav>
  </header>
<script>
    $(document).ready(function () {
        $('.dropdown-toggle').dropdown();
    });
</script>


