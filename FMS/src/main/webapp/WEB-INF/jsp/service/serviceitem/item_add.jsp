<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<spring:eval var="listOfServiceTagsUrl"
	expression="@propertyConfigurer.getProperty('fms.hierarchy.range.markers')" /> 
<spring:eval var="fmsClientId"
	expression="@propertyConfigurer.getProperty('fms.client.id')" />
<spring:eval var="fmsClientDomainName"
	expression="@propertyConfigurer.getProperty('fms.client.domainName')" />
 
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

.k-widget.k-tooltip-validation {
    border-color: #ffffff;
    background-color: #ffffff;
    color: #F44336;
    text-transform: capitalize;
    font-size: 12px;
    /* background: aliceblue; */
    text-align: left;
    width: 100%;
    display: block;
}

.k-icon.k-warning{display:none}
 
  </style>
	
	<c:choose>
			<c:when test="${not empty serviceItemResp}">
				<script type="text/javascript">
				var viewServiceItem = ${serviceItemResp};	
				if(viewServiceItem != undefined){
					if(viewServiceItem != '') {
						//tagViewResp = undefined;
					}
				}
				</script>
			</c:when>
			<c:otherwise>
				<script type="text/javascript">
					var serviceItemViewResp;
				//var viewMappedTemplatesResp;
				</script>
			</c:otherwise>
		</c:choose>
		
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header ">
      <h1> Service Management</h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
       <li><a href="/FMS/item_list">Service Item</a></li>
		<li class="active">Create </li>
      </ol>      
    </section>
        <!-- Main content -->
    <section class="content">
      <div class="box-title fms-title">
      			 <h4 class="pull-left">Service Item</h4>
      			 <div class="fms-btngroup text-right">
				<ul class="btnanimation">				
						
					<li class=" form-action-cancel ripplelink" id="user-cancel"><i class="fa fa-times" aria-hidden="true"></i>
                                <button href="javascript:void(0)" name="gapp-item-cancel" id="gapp-item-cancel" onclick="FnCancel()">
							<i>Cancel</i>
						</button>
						</li>	
						
						
					<c:if test="<%=hasPermissionAccess(\"service_items\",\"edit\")%>">
					<li class=" form-action-edit ripplelink" id="user-edit"><i
						class="fa fa-pencil-square-o" aria-hidden="true"></i>
						<button href="javascript:void(0)" name="gapp-item-edit" id="gapp-item-edit" onclick="FnEdit()">
							<i>Edit</i>
						</button></li>
					</c:if>
					<c:if test="<%=hasPermissionAccess(\"service_items\",new String[]{\"create\",\"edit\"})%>">
					<li class="primary form-action-save ripplelink"><i class="fa fa-check" aria-hidden="true"></i>
						<button href="#" name="gapp-item-save" id="gapp-item-save" onclick="return FnSave()">
							<i>Save</i>
						</button></li>
					</c:if>
				</ul>
				<!--<button type="button" class="btn btn-default" name="gapp-user-cancel" id="gapp-user-cancel" onclick="FnCancelUser()"> Cancel</button>
      				  <button type="button" class="btn btn-primary"  name="gapp-user-save" id="gapp-user-save" onclick="return FnSaveUser()"
                tabindex="7">Save</button>-->
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
        <form>
        <div class="box-body" id="service-item-wrapper">
          <div class="row">					
              <div class="col-md-4">
                  <div class="form-group" id="validateServiceName">
                    <label for="serviceItem">Service Item Name <span class="required  text-red" aria-required="true">* </span></label>
                    <input type="text" name="serviceItemName" class="form-control" id="serviceItemName" tabindex="1" required="true" validationMessage="Enter Service item name" aria-invalid="true" placeholder="Enter Service Item Name">
                  <!--  <span class="help-block">Service Item Name not specified</span>-->
                  </div>
                  <div class="form-group">
                <label>Service Tags</label>
                <select class="form-control" multiple="multiple" data-placeholder="Select Tag" id="serviceTagsMulti" style="width: 100%;">
                </select>
              </div>
              <div class="form-group">
                <label for="desc">Description  <span class="required  text-red" aria-required="true">* </span></label>
                <textarea class="form-control" rows="3" placeholder="Enter Description" id="description"></textarea>
              <!--  <span class="help-block">Description not specified</span>-->
              </div>

               </div>

           </div>

          </div>
          </form>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->

		<form action="/FMS/item_update" id="gapp-item-update-form" method="post">
			<input class="form-control" type="hidden" id="serviceItemUpdatePayload"
				name="value" />
		</form>
		
		<form action="/FMS/item_add" id="gapp-item-save-form" method="post">
			<input class="form-control" type="hidden" id="serviceItemCreatePayload"
				name="value" />
		</form>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<spring:url value="resources/js/service/serviceitem/add.js" var="serviceitemaddJS"/><script src="${serviceitemaddJS}"></script>

<script type="text/javascript">
	var listOfServiceTagsUrl = "${listOfServiceTagsUrl}";
	var fmsClientId = "${fmsClientId}";
	var fmsClientDomainName = "${fmsClientDomainName}";
</script>
