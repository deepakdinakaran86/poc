

<span id="notificationMsg"></span>

<div id="GBL_loading">
	<div class="GBL_loading">
		<div style="max-width: 50%">
			<img src="resources/images/preloading01.GIF">
		</div>
	</div>
</div>


<script id="errorTemplate" type="text/x-kendo-template">
	<div class="alert alert-danger">
		<strong> <img  src="resources/images/error-icon.png" /></strong>  #= message #		
	</div>
</script>

<script id="successTemplate" type="text/x-kendo-template">  
	<div class="alert alert-success">
		<strong> <img  src="resources/images/success-icon.png" /></strong>  #= message #
	</div>
</script>

<script id="infoTemplate" type="text/x-kendo-template">  
	<div class="alert alert-info" role="alert">
		<strong> <img  src="resources/images/info-icon.png" /></strong>  #= message #
	</div>  	
</script>

<script type="text/javascript">
	initNotifications();
</script>