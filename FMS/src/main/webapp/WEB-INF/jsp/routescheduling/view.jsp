<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>
<%@ page import="static com.pcs.fms.web.client.FMSAccessManager.hasPermissionAccess"%>

<link rel="stylesheet" href="resources/plugins/croppie/croppie.css" type="text/css"/>
<style type="text/css">
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



  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>    Route Scheduling  </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Route Scheduling</a></li>
        <li class="active"> Create Scheduling</li>
      </ol>
    </section>

    <!-- Main content -->
    <!-- Main content -->
    <section class="content">
      <div class="box-title fms-title">
      			 <h4 class="pull-left">Create Scheduling</h4>
      			 <div class="fms-btngroup text-right">
					<ul class="btnanimation">
						<li class="default"><i class="fa fa-times" aria-hidden="true"></i><a href="javascript:void(0)"  id="gapp-user-cancel" onclick="FnCancelUser()"><i>Cancel</i></a></li>
						<li class="edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i><a href="javascript:void(0)"  id="gapp-user-cancel" onclick="FnEditUser()"><i>Edit</i></a></li>
						<li class="primary"><i class="fa fa-check" aria-hidden="true"></i><a href="#"  onclick="return FnSaveUser()"><i>Save</i></a></li>
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
        <div class="box-body">
          <div class="">					
			  <form class="form-horizontal">
			  <div class="col-md-5">
                  <div class="form-group">
                    <label for="userName">Schedule Name <span class="required text-red" aria-required="true">* </span></label>
                    <input type="text" name="componentName" class="form-control" id="componentName" tabindex="1" required=""   onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)" aria-invalid="true" placeholder="Enter User Name">
                  <!--  <span class="help-block">User name not specified</span>-->
                  </div>
                  <div class="form-group ">
                    <label for="firstName">Routes</label>
							<select class="form-control select2" style="width: 100%;">
							  <option selected="selected">Please Select a Item </option>
							  <option>option 1</option>
							</select>
                  <!--   <span class="help-block">First name entered correct</span>-->
                  </div>
				  	<div class="bootstrap-timepicker">
							<div class="form-group">
							  <label>Start time:</label>

							  <div class="input-group col-md-8">
							  							<div class="input-group-addon">
								  <i class="fa fa-clock-o"></i>
								</div>
								<input type="text" class="form-control timepicker">


								<span class="input-group-btn" style="left: 24px;">
								  <button type="button" class="btn btn-info btn-flat">Calculate</button>
								</span>
							  </div>
							</div>
							<!-- /.form group -->
					</div>
				  </div>
				  <div class="col-md-5" style="padding-left:40px">
				  <div class="form-group">
						<label for="lastName">Max Duration </label>
						<div class="input-group">
							<input type="text" class="form-control" >
							<span class="input-group-addon" style="background-color: #f39c12;border:1px solid #e08e0b; color:#fff">Min</span>
						</div>	
                  </div>
				  				    <div class="form-group">
                    <label for="lastName">Total</label>
						<div class="row">
							<div class="col-md-6 clearfix">
								<small for="lastName" class="col-md-3 control-label"><strong>Duration</strong></small>
								<div class="col-md-8"><input type="text" name="location" class="form-control" id="location" tabindex="1" required=""   onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)" aria-invalid="true" placeholder="Eg:150" disabled></div>
							</div>
							<div class="col-md-6 clearfix">
								<small for="lastName" class="col-md-3 control-label"><strong>Distance</strong></small>
								<div class="col-md-8"><input type="text" name="location" class="form-control" id="location" tabindex="1" required=""   onkeypress="return FnAllowAlphaNumericOnlyNospaceBetween(event)" aria-invalid="true" placeholder="Eg:100" disabled></div>
							</div>
						</div>
                  </div>

				  
               </div>
			   </form>

           </div>
		   
          <div class="row">
            <div class="col-md-12" style="margin-top:30px">
			<div id="schedule-list"> </div>
			</div>
          </div>		   
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>
<!-- /.content-wrapper -->

<spring:url value="resources/js/routescheduling/add.js" var="routeschedulingAddJS" />
<script src="${routeschedulingAddJS}"></script>

