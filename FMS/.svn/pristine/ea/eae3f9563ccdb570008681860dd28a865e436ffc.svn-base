<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style type="text/css">
	body {
		background-color: #ecf0f5;
	}
	
	.navbar {
		background-color: #3c8dbc;
	}
	
	.main-header .logo {
		background-color: #158dc1;
	}
	
	.main-header .logo .logo-lg {
		display: block;
		color: #fff;
	}
	
	.wrapper,.main-sidebar,.left-side {
		background-color: #222d32;
		background-color: #ecf0f5;
	}
	.bg-danger {
	    background-color: #fff !important;
	    color:#dd4b39 !important;
	    font-size: 18px;
	}

</style>

<header class="main-header">
		<!-- Logo -->
		<a href="#" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
			<span class="logo-mini"></span> <!-- logo for regular state and mobile devices -->
			<span class="logo-lg"><b>Galaxy</b>FMS</span>
		</a>
		<!-- Header Navbar: style can be found in header.less -->
		<nav class="navbar navbar-static-top">
			<!-- Sidebar toggle button-->


			<div class="navbar-custom-menu"></div>
		</nav>
</header>
<aside class="main-sidebar"></aside>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1 class ="capitalize" id="res_head"></h1>		
		</section>
		 <section class="content">
		<div class="box box-info">
			<div class="box-header with-border hidden">
				<h4 class="box-title"></h4>
				<div class="box-tools pull-right hidden"></div>
			</div>
			<div class="box-body">
				<div class="row">
					<div class="col-md-6" style="margin: 0 0 0 16px;">
						<div class="alert ">
							<strong><img  src="../resources/images/error-icon.png" /></strong><div id="res_content" style="display: inline;  margin: 0 0 0 13px;"></div>	
						</div>
					</div>
				</div>
				<!-- /.row -->
			</div>
			<div class="box-footer ">
					<div class="col-md-3 hidden"></div>
			</div>
		</div>
		</section>
</div>
<script type="text/javascript">
	var ObjPageActionResponse = '${response}';
	if(ObjPageActionResponse != ''){
		ObjPageActionResponse = $.parseJSON(ObjPageActionResponse);
		$('#res_head').text(ObjPageActionResponse.status);
		$('#res_content').parent('div').addClass('alert-'+ObjPageActionResponse.status);
		$('#res_content').html(ObjPageActionResponse.message);
	}
</script>

