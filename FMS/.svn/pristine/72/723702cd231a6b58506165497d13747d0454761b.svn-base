<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

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
a.grid-tag-view.capitalize{
    padding: 0 0 0 15px !important;
    cursor: pointer;
    text-transform:capitalize;
}

</style>

<spring:eval var="GetTagListURL"
	expression="@propertyConfigurer.getProperty('fms.services.listTags')" />



<form action="device_create" id="createdevice"></form>
<form action="device_view" id="managedevice" method="post">
	<input class="form-control" type="hidden" id="update_value"
		name="value" />
</form>

<form action="assign_device" id="assign_device" method="post">
	<input class="form-control" type="hidden" id="tenant_name" name="value" />
</form>

<form action="write_back" id="wb-form" method="post" hidden="true">
	<input class="form-control" type="hidden" id="wb_value" name="value" />
</form>

<form action="live_track" id="live-form" method="post" hidden="true">
	<input class="form-control" type="hidden" id="live_value" name="value" />
	<input class="form-control" type="hidden" id="live_key" name="key" />
</form>

<form action="historytracking" id="hist-form" method="post"
	hidden="true">
	<input class="form-control" type="hidden" id="hist_value" name="value" />
</form>

<form action="event_conf" id="event_conf-form" method="post" hidden="true">
	<input class="form-control" type="hidden" id="ec_value" name="value" />
</form>

<script type="text/x-kendo-template" id="tenantSearchtemplate">
	<div class="toolbar" >
		<input type="text" id="searchTenantId" class="form-control-custom" placeholder="Search tenant"/>
	</div>
</script>


<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1> Tag Management</h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Tag</li>
      </ol>
    </section>
<div class="col-split-12">
		<c:if test="${not empty listOfTagTypesResp}">
				<script type="text/javascript">
				var tagTypesListResp = ${listOfTagTypesResp};	
				if(tagTypesListResp != undefined){
					if(tagTypesListResp != '') {
						//alert(tagTypesListResp);
					}
				}
					</script> 
		</c:if>	
	</div>
    <!-- Main content -->
    <section class="content">
		      <div class="box-title fms-title">
      			 <h4 class="pull-left"> <i class="pageheadericon pageheader-usericon"></i>Tag List</h4>
      			 <div class="fms-btngroup text-right">
      			 	<c:if test="<%=hasPermissionAccess(\"tag\",\"create\")%>">
      				  <button type="button" class="btn btn-primary ripplelink"  name="gapp-tag-create" id="gapp-tag-create" tabindex="7" onClick="FnCreateTag()">Create</button>
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
					<div class="col-md-4 margin">	
						<div class="form-group">
							<label class="pull-left">Tag Types</label>
							<div class="col-sm-8">
								<select class="form-control" name="tagCategory" id="tagCategory" onchange="FnGetTagList(this.value)">
												<option value="">Select Tag Type</option>
											</select>
							</div>
						</div>
					</div>
            <div class="col-md-12">			  
				<div id="gapp-tag-list"> 
				</div>
			</div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>
		<div>
			<form id="gapp-tag-view" role="form" action="/FMS/tag_view" name="gapp-tag-view" method="post">
			<input type="hidden" id="tagName" name="tagName" />
			<input type="hidden" id="tagType" name="tagType" />
			</form>
		</div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
   
	
	<script type="text/javascript">
		var GetTagListURL = "${GetTagListURL}";
		//alert(GetTagListURL);
	</script>
  
    <spring:url value="resources/js/tag/list.js" var="tagListJS"/><script src="${tagListJS}"></script>