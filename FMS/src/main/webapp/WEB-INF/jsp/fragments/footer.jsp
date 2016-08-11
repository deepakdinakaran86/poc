<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.pcs.fms.web.client.FMSAccessManager"%>

<!--  common modal dialog box  -->

<div id="alertModal" class="modal fade" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-xs">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">Alert</h4>
			</div>
			<div class="modal-body">
				<div class="scroller" data-always-visible="1" data-rail-visible1="1">
					<div class="row">
						<div class="col-md-12">														
							<p class="modalMessage">Error</p>							
						</div>					
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
			<button type="button" data-dismiss="modal" aria-hidden="true" class="btn  btn-raised"><i class="icon-check icons" aria-hidden="true"></i>Ok</button>
				<div class="modal-footer">	</div>
			</div>
		</div>
	</div>
</div>
<!--  End cancel model popup  -->

<!-- Token Expired Notification-->
<div class="modal fade" id="tokenexpiremodal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header" style="background: #3c8dbc; color: #fff;">
				<h4 class="modal-title"> <i class="fa fa-frown-o"></i> &nbsp; Notification</h4>
			</div>
			<div class="modal-body" style="min-height:220px">
				<div class="row">
				<div id="tokenexpiremodal-icon" clas="bg-info" style="margin:0 auto; text-align:center">
					<i class="fa fa-exclamation-triangle " ></i>
				</div>
				
				<div id="tokenexpiremodal-content"></div>
				<div id="tokenexpiremodal-text"> Please re-login to continue with the application. if you have unsaved changes, unfortunately they will not be saved.</div>
				
				</div>
			</div>
		</div>
	</div>
</div>

<footer class="main-footer">
	<div class="pull-right hidden-xs"><b></b></div>
    Copyright &copy; 2015-2016 <a href="#">GalaxyFMS</a>. All rights reserved.
</footer>
  
<script type="text/javascript">
	var GBLUPLOADIMAGESIZE = 2048;
	var ObjPageActionResponse = '${response}';
	FnHandlePageActionNotification(ObjPageActionResponse);	
	$(document).ready(function(){
	   $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
   checkboxClass: 'icheckbox_minimal-blue',
   radioClass: 'iradio_minimal-blue'
 });
    //Red color scheme for iCheck
 $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
   checkboxClass: 'icheckbox_minimal-red',
   radioClass: 'iradio_minimal-red'
 });
	 });
</script>
  
