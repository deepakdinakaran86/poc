<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<style type="text/css">
.skin-blue .main-header .navbar {
	background: radial-gradient(#197aa5 15%, #005180 60%);
}

.skin-blue .main-header .logo {
	background-color: #158dc1;
}

div.fileUpload {
	position: relative;
	overflow: hidden;
	margin: 10px;
	background-image:
		url("https://10.234.60.46:9445/portal/images/domains/default/users//user.png");
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
	filter: alpha(opacity = 0);
	height: 100%;
	text-align: center;
	z-index: 99;
}

div.fileUpload input.upload+img {
	position: absolute;
	top: 0;
	left: 0;
	z-index: 9;
}
</style>

<!-- <script type="text/javascript"> -->
// 	$(document).ready(function(){
			
// 	});
	
	
<!-- </script> -->
	
	
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>    Tag Management   </h1>
      <ol class="breadcrumb">
        <li><a href="/FMS"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/FMS/tag_home">Tag</a></li>
        <li class="active">Create</li>
      </ol>
    </section>
    <div class="col-split-12">
		<c:if test="${not empty listOfTagTypesResp}">
				<script type="text/javascript">
				var tagTypesListResp = ${listOfTagTypesResp};	
				if(tagTypesListResp != undefined){
					if(tagTypesListResp != '') {
						tagViewResp = undefined;
						var createTagFields = undefined;
						console.log(JSON.stringify(tagTypesListResp));
					}
				}
				</script> 
		</c:if>	
			
		<c:choose>
			<c:when test="${not empty mappedTemplatesResp}">
				<script type="text/javascript">
				var viewMappedTemplatesResp = ${mappedTemplatesResp};
				if(viewMappedTemplatesResp != undefined){
					if(viewMappedTemplatesResp != '') {
						tagTypesListResp = undefined;
						var createTagFields = undefined;
						//alert(viewMappedTemplatesResp);
						console.log(JSON.stringify(viewMappedTemplatesResp));
					}
				}
				var tagViewResp = ${viewTagResp};
				if(tagViewResp != undefined){
					if(tagViewResp != '') {
						tagTypesListResp = undefined;
						var createTagFields = undefined;
						//alert(tagViewResp);
						console.log(JSON.stringify(tagViewResp));
					}
				}
				</script>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty viewTagResp}">
					<script type="text/javascript">
					var tagViewResp = ${viewTagResp};
					if(tagViewResp != undefined){
						if(tagViewResp != '') {
							tagTypesListResp = undefined;
							var viewMappedTemplatesResp = undefined;
							var createTagFields = undefined;
						}
					}
					</script>
				</c:if>
				<c:if test="${not empty createTagFields}">
						<script type="text/javascript">
						var createTagFields = ${createTagFields};	
						if(createTagFields != undefined){
							if(createTagFields != '') {
								tagViewResp = undefined;
								console.log(JSON.stringify(createTagFields));
							}
						}
							</script>  
				</c:if>	
				
			</c:otherwise>
		</c:choose>
	</div>
    <!-- Main content -->
    <!-- Main content -->
    <section class="content">
	    
      <div class="box-title fms-title">
      			 <h4 class="pull-left">Create Tag</h4>
      			 <div class="fms-btngroup text-right">


					<ul class="btnanimation">
					  <li class=" form-action-cancel"><i class="fa fa-times" aria-hidden="true"></i>
					  		<button type="button" class="btn " name="gapp-tag-cancel" id="gapp-tag-cancel" onclick="FnCancelTag()">Cancel</button>
					  </li>
					  
					  <c:if test="<%=hasPermissionAccess(\"tag\",\"edit\")%>">
					   <li class=" form-action-edit" >
						<i class="fa fa-pencil-square-o"aria-hidden="true"></i><button type="button"    href="Javascript:void(0)" id="gapp-tag-edit" onclick="FnEditTag()">Edit</button>
						</li>
						</c:if>
					  
					  	<c:if test="<%=hasPermissionAccess(\"tag\",new String[]{\"create\",\"edit\"})%>">
					  	<li class="primary form-action-save" >
					<i class="fa fa-check" aria-hidden="true"></i><button type="button" class="btn " name="gapp-tag-create" id="gapp-tag-create" onclick="return FnSaveTag()" tabindex="7">Save</button>
				</li>
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
          <div class="row">					
              <div class="col-md-6">
                   <div class="form-group ">
                    <label for="tagtypelist">Tag Type</label>
							<select class="form-control" style="width: 100%;" name="tagCategory" id="tagCategory" onchange="FnGetTagTypeDetails(this.value)">
							  <option selected="selected">Please Select a Tag Type </option>
							</select>
                  <!--   <span class="help-block">First name entered correct</span>-->
                  </div>
             

               </div>
			    <div class="col-md-6">
				     <div class="form-group">
		                <label>Assign To</label>
		                <select class="form-control select2" id="templateFields" multiple="multiple" data-placeholder="Select Tag" style="width: 100%;">
		                </select>
              		</div>
				</div>
           	</div>
           <div class="col-md-8" id="fieldsSection" style="display: none">
				<div class="portlet1 dark bordered">
					<div class="portlet-body form">
						<div class="form-body">
							<div class="col-md-10" id="fieldsContainer"></div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
          </div>
          <!-- /.row -->
          
        </div>
        <!-- /.box-body -->
		</div>
		
		<form:form action="tag_create_service" commandName="tag_create_service" id="gapp-tag-create-form" method="post">
			<form:input class="form-control" type="hidden" id="assignToTemplates"
				name="assignToTemplates" path="assignToTemplates"/> 
 			<form:input class="form-control" type="hidden" id="tagName" 
				name="tagName" path="tagName"/> 
 			<form:input class="form-control" type="hidden" id="tagType"
 				name="tagType" path="tagType"/>
			<form:input class="form-control" type="hidden" id="tagFieldValues" 
				name="tagFieldValues" path="tagFieldValues"/> 
			<form:input class="form-control" type="hidden" id="tagId" 
 				name="tagId" path="tagId"/> 
 		</form:form> 

		</section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->


<spring:url value="resources/js/tag/create.js" var="clientCreateJS" />
<script src="${clientCreateJS}"></script>