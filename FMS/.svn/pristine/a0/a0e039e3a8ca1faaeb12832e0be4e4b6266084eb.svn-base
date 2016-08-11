<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<%-- <form action="createtenant" id="createtenant"></form> --%>


  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>   Client Management    </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>Create</a></li>
        <li class="active">Admin</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
    
    
    <div class="box box-info">
  <div class="box-header with-border hidden">
    <h3 class="box-title">Create Admin</h3>
    
  </div><!-- /.box-header -->
  <div class="box-body" >

<div class="container2 theme-showcase dc_main" role="main" style="margin: 0px 10px;">
	<section class="dc_maincontainer animate-panel">
		
		<div style="height: 1px;">
			<form id="tenantHome" action="tenant_home" method="get"></form>
		</div>

		<form:form role="form" action="tenant_send_email"
			commandName="tenant_send_email_form" id="tenant_send_email_form"
			autocomplete="off" novalidate="novalidate" method="post">
			<form:hidden path="action" />
			<form:hidden path="identifier" />
			<form:hidden path="domain" />
			<div class="col-md-5">
				<div class="form-group">
					<label>Email Id<span class="required">*</span></label>
					<form:input path="contactEmail" id="contactEmail"
						class="form-control dc_inputstyle" type="email"
						data-email-msg="Email Id format is not valid" />
				</div>
			</div>
		</form:form>
		<div class="col-md-12">
			<section class="col-md-5 text-right dc_groupbtn">
			<c:if test="<%=hasPermissionAccess(\"client_management\",\"create admin\")%>">
				<button type="submit" id="tenant-btn-save" class="btn btn-primary  ripplelink"
					onclick="submitAction();">Send Email</button>
					</c:if>
				<button type="submit" id="btn-create" class="btn  btn-default  ripplelink"
					onclick="submitBackAction();" style="color:#000">Cancel</button>
			</section>
		</div>

	</section>
</div>








  </div><!-- /.box-body -->
</div><!-- /.box -->

     


    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->










<script type="text/javascript">

	var contactEmail;

	function submitAction() {
		$("#GBL_loading").show();
		$("#tenant_send_email_form").submit();
	}
	function submitBackAction() {
		$("#tenantHome").submit();
	}

	function setFormValidation() {

		$("#contactEmail").attr('mandatory', 'Email Id not specified');

		$("#tenant_send_email_form")
				.kendoValidator(
						{

							validateOnBlur : true,
							errorTemplate : "<span style='color:red'>#=message#</span>",
							rules : {
								mandatory : function(input) {

								 if (input.attr('id') == 'contactEmail'
											&& input.val() == '') {
										return false;
									}

									return true;
								}
							},
							messages : {
								mandatory : function(input) {
									return input.attr("mandatory");
								}
							}
						});
	}


		setFormValidation();

</script>
