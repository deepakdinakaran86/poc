

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!-- Bootstrap core CSS -->
<!-- <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet"/> -->

<!--     <link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.common-bootstrap.min.css" /> -->
<!--     <link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.bootstrap.min.css" /> -->
<!--     <link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.dataviz.min.css" /> -->
<!--     <link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.dataviz.bootstrap.min.css" /> -->

<!-- 	<link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.common-material.min.css" /> -->
<!--     <link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.material.min.css" /> -->
<!--     <link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.dataviz.min.css" /> -->
<!--     <link rel="stylesheet" href="http://cdn.kendostatic.com/2015.1.429/styles/kendo.dataviz.material.min.css" /> -->

<!--     <script src="http://cdn.kendostatic.com/2015.1.429/js/jquery.min.js"></script> -->
<!--     <script src="http://cdn.kendostatic.com/2015.1.429/js/kendo.all.min.js"></script>   -->



<spring:url value="resources/plugins/leaflet/leaflet-search.css"
	var="leafSearch" />
<link rel="stylesheet" href="${leafSearch}">
<spring:url value="resources/css/bootstrap/bootstrap.min.css"
	var="bootstrapMINCSS" />
<link rel="stylesheet" href="${bootstrapMINCSS}">
<spring:url value="resources/css/AdminLTE.min.css" var="adminLTEMINCSS" />
<link rel="stylesheet" href="${adminLTEMINCSS}">

<spring:url value="resources/css/skins/_all-skins.min.css"
	var="allSkinsMINCSS" />
<link rel="stylesheet" href="${allSkinsMINCSS}">

<!-- iCheck -->
<spring:url value="resources/plugins/iCheck/flat/blue.css" var="blueCSS" />
<link rel="stylesheet" href="${blueCSS}">

<!-- DatePicker Range -->
<spring:url
	value="resources/plugins/daterangepicker/daterangepicker-bs3.css"
	var="dateRangePickerMINCSS" />
<link rel="stylesheet" href="${dateRangePickerMINCSS}">

<!-- DatePicker -->
<spring:url value="resources/plugins/datepicker/datepicker3.css"
	var="datePickerMINCSS" />
<link rel="stylesheet" href="${datePickerMINCSS}">

<!-- iCheck for checkboxes and radio inputs -->
<spring:url value="resources/plugins/iCheck/all.css" var="iCheckCSS" />
<link rel="stylesheet" href="${iCheckCSS}">

<!-- Bootstrap Color Picker -->
<spring:url
	value="resources/plugins/colorpicker/bootstrap-colorpicker.min.css"
	var="colorPickerCSS" />
<link rel="stylesheet" href="${colorPickerCSS}">

<!-- Bootstrap time Picker -->
<spring:url
	value="resources/plugins/timepicker/bootstrap-timepicker.min.css"
	var="timePickerCSS" />
<link rel="stylesheet" href="${timePickerCSS}">
<!-- Select2 -->
<spring:url value="resources/plugins/select2/select2.min.css"
	var="selectMINCSS" />
<link rel="stylesheet" href="${selectMINCSS}">

<!-- bootstrap wysihtml5 - text editor -->
<spring:url
	value="resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css"
	var="wysihtml5MINCSS" />
<link rel="stylesheet" href="${wysihtml5MINCSS}">

<!-- jquery.uberAccordion  https://github.com/gbbr/UberAccordion -->
<spring:url value="resources/plugins/accordion/jquery.uberAccordion.css"
	var="uberAccordionMINCSS" />
<link rel="stylesheet" href="${uberAccordionMINCSS}">

<!-- font-awesome.min -->
<spring:url value="resources/fonts/font-awesome.min.css"
	var="fontAwesomeMINCSS" />
<link rel="stylesheet" href="${fontAwesomeMINCSS}">

<!-- ionicons.min -->
<spring:url value="resources/fonts/ionicons.min.css"
	var="ioniconsMINCSS" />
<link rel="stylesheet" href="${ioniconsMINCSS}">

<!-- multiSelectCSS   -->
<spring:url
	value="resources/plugins/multi-select-master/css/multi-select.css"
	var="multiSelectCSS" />
<link rel="stylesheet" href="${multiSelectCSS}">
   <spring:url value="resources/plugins/modernizr/modernizr-2.5.3.min.js" var="modernizrJS"/><script src="${modernizrJS}"></script>

<!-- FMS Theme css -->
<spring:url value="resources/plugins/kendoui/css/kendo.common.min.css"
	var="kendoCommonMINCSS" />
<link rel="stylesheet" href="${kendoCommonMINCSS}">
<spring:url value="resources/plugins/kendoui/css/kendo.material.css"
	var="kendoMaterialMINCSS" />
<link rel="stylesheet" href="${kendoMaterialMINCSS}">
<spring:url value="resources/plugins/kendoui/css/kendo.custom.css"
	var="kendoCustomMINCSS" />
<link rel="stylesheet" href="${kendoCustomMINCSS}">
<spring:url value="resources/css/styles.css" var="styleCSS" />
<link rel="stylesheet" href="${styleCSS}">
<spring:url value="resources/plugins/leaflet/leaflet.css"
	var="leafLetCSS" />
<link rel="stylesheet" href="${leafLetCSS}">

<spring:url	value="resources/plugins/leaflet/routing/leaflet-routing-machine.css" var="leafLetRouteCSS" />
<link rel="stylesheet" href="${leafLetRouteCSS}">

  <!-- fullCalendar 2.2.5-->
  <spring:url	value="resources/plugins/fullcalendar/fullcalendar.min.css" var="fullCalendarCSS" />
  <link rel="stylesheet" href="${fullCalendarCSS}">
  <spring:url	value="resources/plugins/fullcalendar/fullcalendar.print.css" var="fullCalendarPrintCSS" />
  <link rel="stylesheet" href="${fullCalendarPrintCSS }" media="print">


<style type="text/css">
      .k-i-calendar {
          background-position: -32px -176px;
          margin-top: 5px !important;
      }
      .k-i-clock {
          background-position: -32px -192px;
          margin-top: 5px !important;
      }
      .GBL_loading{
			position: absolute;
			display: flex;
			display: -webkit-flex;
			 display: -webkit-box; 
			-webkit-align-items: center;
			-webkit-box-align:center;
			align-items: center;
			-webkit-justify-content: center;
			-webkit-box-pack: center;
			justify-content: center;
			width: 100%;
			height: 100%;
			background: rgba(247,247,247,0.5);
			z-index: 99999;
			top:0;
	}
	#GBL_loading{display:none}
  </style>
  
  
<!-- - FMS JS files -->
<!-- Javascript css -->
<spring:url value="resources/plugins/jQuery/jQuery-2.2.0.min.js"
	var="jqueryJS" />
<script src="${jqueryJS}"></script>

<!-- Bootstrap 3.3.6 -->
<spring:url value="resources/js/bootstrap/bootstrap.min.js"
	var="bootstrapJS" />
<script src="${bootstrapJS}"></script>

<!-- Bootstrap WYSIHTML5 -->
<spring:url
	value="resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"
	var="wysihtml5JS" />
<script src="${wysihtml5JS}"></script>
<!-- Slimscroll -->
<spring:url
	value="resources/plugins/slimScroll/jquery.slimscroll.min.js"
	var="slimJS" />
<script src="${slimJS}"></script>
<!-- FastClick -->
<spring:url value="resources/plugins/fastclick/fastclick.js"
	var="fastClickJS" />
<script src="${fastClickJS}"></script>
<!-- jquery.uberAccordion -->
<spring:url
	value="resources/plugins/accordion/jquery.uberAccordion.min.js"
	var="uberAccordionJS" />
<script src="${uberAccordionJS}"></script>
<!-- AdminLTE App -->
<spring:url value="resources/js/app.js" var="uberAccordionJS" />
<script src="${uberAccordionJS}"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<spring:url value="resources/js/dashboard-portal.js"
	var="uberAccordionJS" />
<script src="${uberAccordionJS}"></script>

<!-- kendoCAllJS css -->
<spring:url value="resources/plugins/kendoui/js/kendo.all.min.js"
	var="kendoAllJS" />
<script src="${kendoAllJS}"></script>
<!-- kendoCustomJS css -->
<spring:url value="resources/plugins/kendoui/js/kendo.custom.js"
	var="kendoCustomJS" />
<script src="${kendoCustomJS}"></script>

<!--CustomScrollBar -->
<spring:url
	value="resources/plugins/jquery-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"
	var="customScrollBarJS" />
<script src="${customScrollBarJS}"></script>
<!--commonFunctions -->
<spring:url value="resources/plugins/iCheck/icheck.js" var="iCheckJS" />
<script src="${iCheckJS}"></script>

<spring:url value="resources/js/commonFunctions.js"
	var="commonFunctionsJS" />
<script src="${commonFunctionsJS}"></script>
<!--demo -->
<spring:url value="resources/js/demo.js" var="demosJS" />
<script src="${demosJS}"></script>



<!--gmaps  API KEY : https://maps.googleapis.com/maps/api/js?key=AIzaSyBnoObQJX2uoDSfRnjpVVXgjSrd6oz8oVM-->
<%-- <spring:url --%>
<%-- 	value="https://maps.googleapis.com/maps/api/js?key=AIzaSyBnoObQJX2uoDSfRnjpVVXgjSrd6oz8oVM" --%>
<%-- 	var="gmapsJS" /> --%>
<%-- <script src="${gmapsJS}"></script> --%>

<spring:url
	value="https://maps.googleapis.com/maps/api/js?key=AIzaSyAIXCNeqkexwQ3q31K-Qz32Ph1fheK_4xk&libraries=geometry"
	var="gmapsDirectionJS" />
<script src="${gmapsDirectionJS}"></script>



<!--gmapsLatlonPickerJS -->
<spring:url value="resources/plugins/jquery-gmaps-latlon-picker.js"
	var="gmapsLatlonPickerJS" />
<script src="${gmapsLatlonPickerJS}"></script>

<!--jqueryFormJS -->
<spring:url value="resources/plugins/jquery.form.js" var="jqueryFormJS" />
<script src="${jqueryFormJS}"></script>

<!--jqueryMultiSelectJS -->
<spring:url
	value="resources/plugins/multi-select-master/js/jquery.multi-select.js"
	var="jqueryMultiSelectJS" />
<script src="${jqueryMultiSelectJS}"></script>
<spring:url value="resources\js\common\UserInfo.js" var="userInfoJS" />
<script src="${userInfoJS}"></script>

<!-- -old style -->
<!-- <spring:url value="resources/kendo/css/kendo.bootstrap.mobile.min.css" 	var="kendoBootstrapmobileCSS" />  <link rel="stylesheet" href="${kendoBootstrapmobileCSS}">  -->
<spring:url
	value="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"
	var="momentJS" />
<script src="${momentJS}"></script>
<spring:url value="resources/themes/gx/scripts/jqueryEasing.js"
	var="jqueryEasing" />
<script src="${jqueryEasing}"></script>
<spring:url value="resources/themes/default/js/bootstrap.min.js"
	var="bootstrapMin" />
<script src="${bootstrapMin}"></script>
<spring:url value="resources/kendo/scripts/kendo.all.min.js"
	var="kendoJS" />
<script type="text/javascript" src="${kendoJS}"></script>
<script type="text/javascript" src="${jsConstants}"></script>
<spring:url value="resources/themes/gx/scripts/galaxy.js" var="galaxyJS" />
<script type="text/javascript" src="${galaxyJS}"></script>


<spring:url value="resources/plugins/leaflet/leaflet.js" var="leafLetJS" />
<script src="${leafLetJS}"></script>
<spring:url value="resources/plugins/leaflet/leaflet-search.js"
	var="leafLetSearchJS" />
<script src="${leafLetSearchJS}"></script>
<spring:url value="resources/plugins/leaflet/bouncemarker.js"
	var="leafLetBounceJS" />
<script src="${leafLetBounceJS}"></script>
<spring:url value="resources/plugins/leaflet/MovingMarker.js"
	var="leafLetMovingMarker" />
<script src="${leafLetMovingMarker}"></script>
<spring:url value="resources/plugins/leaflet/L.Polyline.SnakeAnim1.js"
	var="leafLetSnakeAnim1" />
<script src="${leafLetSnakeAnim1}"></script>
<spring:url value="resources/plugins/leaflet/leaflet.polylineoffset.js"
	var="leafLetpolylineoffset" />
<script src="${leafLetpolylineoffset}"></script>

<spring:url
	value="resources/plugins/leaflet/routing/leaflet-routing-machine.js"
	var="leafRouting" />
<script src="${leafRouting}"></script>
<spring:url
	value="resources/plugins/leaflet/routing/lrm-google.js"
	var="lrmgoogle" />
<script src="${lrmgoogle}"></script>

<spring:url
	value="resources/plugins/leaflet/routing/polyline.js"
	var="polyline" />
<script src="${polyline}"></script>

<spring:url
	value="resources/plugins/leaflet/routing/Control.Geocoder.js"
	var="geoCoder" />
<script src="${geoCoder}"></script>

<spring:url
	value="resources/plugins/fullcalendar/fullcalendar.min.js"	var="fullCalendarJS" />
<script src="${fullCalendarJS}"></script>

<script>
	// Page Animation code
	
	$(document).ready(function() {
		$('.animate-panel').animatePanel();
		$('.km-switch').parent(".form-group").css({
			"margin-top" : "15px"
		});
		
			var ink, d, x, y;
	$(".ripplelink").click(function(e){
    if($(this).find(".ink").length === 0){
        $(this).prepend("<span class='ink'></span>");
    }
         
    ink = $(this).find(".ink");
    ink.removeClass("animate");
     
    if(!ink.height() && !ink.width()){
        d = Math.max($(this).outerWidth(), $(this).outerHeight());
        ink.css({height: d, width: d});
    }
     
    x = e.pageX - $(this).offset().left - ink.width()/2;
    y = e.pageY - $(this).offset().top - ink.height()/2;
     
    ink.css({top: y+'px', left: x+'px'}).addClass("animate");
});
	});

	//Animate panel function
	$.fn['animatePanel'] = function() {

		var element = $(this);
		var effect = $(this).data('effect');
		var delay = $(this).data('delay');
		var child = $(this).data('child');

		// Set default values for attrs
		if (!effect) {
			effect = 'fadeIn'
		}
		;
		if (!delay) {
			delay = 0.02
		} else {
			delay = delay / 10
		}
		;
		if (!child) {
			child = 'div'
		} else {
			child = "." + child
		}
		;

		//Set defaul values for start animation and delay
		var startAnimation = 0;
		var start = Math.abs(delay) + startAnimation;

		// Get all visible element and set opactiy to 0
		var panel = element.find(child);
		panel.addClass('opacity-0');

		// Get all elements and add effect class
		panel = element.find(child);
		panel.addClass('animated-panel').addClass(effect);
		// Add delay for each child elements
		panel.each(function(i, elm) {

			start += delay;
			var rounded = Math.round(start * 10) / 10;
			$(elm).css('animation-delay', rounded + 's')
			// Remove opacity 0 after finish
			$(elm).removeClass('opacity-0');
		});
	}
</script>

