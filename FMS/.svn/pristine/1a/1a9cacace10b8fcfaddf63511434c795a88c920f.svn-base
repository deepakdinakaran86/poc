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
a.grid-tag-type-view.capitalize {
    padding: 0 0 0 15px !important;
    cursor: pointer;
    text-transform:capitalize;
}
.formelement_addition input {
    margin: 4px 0 0 0;
}

</style>

<spring:eval var="GetTagTypesListURL"
	expression="@propertyConfigurer.getProperty('fms.services.listTagTypes')" />

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
        <li><a class="active">Tag Type</a></li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
		      <div class="box-title fms-title">
      			 <h4 class="pull-left"> <i class="pageheadericon pageheader-usericon"></i>Tag Type</h4>
      			 <div class="fms-btngroup text-right">
      			 <c:if test="<%=hasPermissionAccess(\"tag_type\",\"create\")%>">
      				  <button type="button" class="btn btn-primary rippleLink"  name="gapp-user-save" id="gapp-user-save" tabindex="7" onClick="FnCreateTagType()">Create</button>
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
					<div class="col-md-4 margin hidden">	
						<div class="form-group">
							<label class="pull-left"></label>
							<div class="col-sm-8">
								<select class="form-control select2 select2-hidden-accessible" style="width: 100%;" tabindex="-1" aria-hidden="true">
									  <option selected="selected">Select Tag Type</option>
									  <option>option 1</option>
								</select>
							</div>
						</div>
					</div>
            <div class="col-md-12">
			  
			<div id="tag-list"> </div>
			</div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>
		<div>
			<form id="tag_type_view" role="form" action="/FMS/tag_type_view" name="tag_type_view" method="post">
			<input type="hidden" id="tag_type_name" name="tag_type_name" />
			</form>
		</div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  	<script type="text/javascript">
		var GetTagTypesListURL = "${GetTagTypesListURL}";
	</script>
  
    <spring:url value="resources/js/tagtype/list.js" var="clientCreateJS"/><script src="${clientCreateJS}"></script>