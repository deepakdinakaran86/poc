<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

 
    <style>
  .skin-blue .main-header .navbar{
      background: radial-gradient(#197aa5 15%, #005180 60%);
	  }
   .skin-blue .main-header .logo {
    background-color: #158dc1;
	}
  div.fileUpload {
    position: relative;
    overflow: hidden;
    margin: 10px;
    background-image: url("https://10.234.60.46:9445/portal/images/domains/default/users//user.png");
    background-size: 200px 200px;
    background-color: #BDBDBD;
    height: 200px;
    width: 200px;
    text-align: center;
}
.upload-addicon {
    display: inline-block;
    position: absolute;
    bottom: 0;
    width: 40px;
    /* height: 40px; */
    width: 100%;
    z-index: 99;
    left: 0;
    text-align: left;
    background: rgba(0, 0, 0, 0.7);
    height: 32px;
    line-height: 30px;
    padding: 0px 10px;
}
div.fileUpload input.upload {
    position: absolute;
    top: 0;
    right: 0;
    margin: 0;
    padding: 0;
    font-size: 20px;
    cursor: pointer;
    opacity: 0;
    filter: alpha(opacity=0);
    height: 100%;
    text-align: center;
    z-index: 99;
}
div.fileUpload input.upload + img {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 9;
}
 
  </style>
  
  <div class="col-split-12">
		<c:if test="${not empty serviceItemsResp}">
				<script type="text/javascript">
				var serviceItemsListResp = ${serviceItemsResp};	
				if(serviceItemsListResp != undefined){
					if(serviceItemsListResp != '') {
						//alert(serviceItemsListResp);
					}
				}
				</script> 
		</c:if>	
	</div>

<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header ">
      <h1> Service Management</h1>
      <ol class="breadcrumb">
        <li><a href="/FMS/"><i class="fa fa-dashboard"></i>Home</a></li>      
		<li class="active">Service Item</li>
      </ol>      
    </section>
    <!-- Main content -->
    <section class="content">
		      <div class="box-title fms-title">
      			 <h4 class="pull-left"> <i class="pageheadericon pageheader-usericon"></i>Service Item</h4>
      			 <div class="fms-btngroup text-right">
      			 <c:if test="<%=hasPermissionAccess(\"service_items\",\"create\")%>">
      			 	<a href="/FMS/item_add" id="gapp-user-save" class="btn btn-primary  ripplelink" tabindex="7">Create</a>
      				  <!--  <button type="button" class="btn btn-primary" id="gapp-user-save" name="gapp-user-save" >Create</button> -->
      				  </c:if>
      		  </div>
      		</div>
      <!-- SELECT2 EXAMPLE -->
      <div class="box box-info">
        <div class="box-header with-border hidden">
          <h4 class="box-title"></h4>

          <div class="box-tools pull-right ">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
          <div class="row">
            <div class="col-md-12">
			<div id="serviceitem-list"> </div>
			</div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>
		
		<div>
			<form id="gapp-item-view" role="form" action="/FMS/item_view" name="gapp-item-view" method="post">
			<input type="hidden" id="itemDomain" name="itemDomain" />
			<input type="hidden" id="itemIdentifier" name="itemIdentifier" />
			</form>
		</div>


    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<spring:url value="resources/js/service/serviceitem/list.js" var="serviceitemlistJS"/><script src="${serviceitemlistJS}"></script>

<script>
  $(function () {

    });
</script>
